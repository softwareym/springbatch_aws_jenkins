package ym.batch.job.Corona.batch;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import ym.batch.job.Corona.item.CoronaVo;
import ym.batch.job.Corona.producer.CoronaSendProducer;
import ym.batch.job.Corona.producer.item.CoronaData;
import ym.batch.job.Corona.repository.CoronaMapper;
import ym.batch.job.Corona.service.CoronaService;
import ym.batch.job.common.item.UserDto;
import ym.batch.job.common.item.UserVo;
import ym.batch.job.common.repository.CommonMapper;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Component
public class CoronaRegistTask implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(CoronaRegistTask.class);

    CoronaService coronaService;
    CoronaMapper coronaMapper;
    CommonMapper commonMapper;
    CoronaSendProducer coronaSendProducer;

    public CoronaRegistTask(CoronaService coronaService, CoronaMapper coronaMapper, CommonMapper commonMapper, CoronaSendProducer coronaSendProducer) {
        this.coronaService = coronaService;
        this.coronaMapper = coronaMapper;
        this.commonMapper = commonMapper;
        this.coronaSendProducer = coronaSendProducer;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<CoronaVo> list = coronaService.callCoronaData();

        UserVo userVo = new UserVo();
        for(CoronaVo coronaVo : list){
            if(coronaVo.getNewCase() >= 50){
                userVo.setStationName(coronaVo.getCountryName());
                List<UserDto> users = commonMapper.selectUsersByStationName(userVo);
                this.publishCoronaInform(coronaVo, users);
            }
            coronaMapper.insertCoronaRegist(coronaVo);
        }

        return RepeatStatus.FINISHED;
    }

    // rabbimq에 coronainform publish
    private void publishCoronaInform(CoronaVo coronaVo, List<UserDto> users){
        CoronaData coronaData = new CoronaData();
        for(UserDto userDto : users){
            coronaData.setCreateDate(LocalDate.now());
            coronaData.setEmail(userDto.getEmail());
            coronaData.setStationName(coronaVo.getCountryName());
            coronaData.setNewCase(coronaVo.getNewCase());
            coronaSendProducer.sendMessage(coronaData);
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask afterStep");
        return ExitStatus.COMPLETED;
    }
}
