package ym.batch.job.airkorea.batch;

import lombok.RequiredArgsConstructor;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ym.batch.job.airkorea.item.ApiCallManageVo;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.common.status.CallDiv;
import ym.batch.job.common.status.TreateStts;

import java.util.List;


@Slf4j
@Component
public class AirDataCallRegistTask implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(AirDataCallRegistTask.class);

    AirKoreaMapper airKoreaMapper;

    public AirDataCallRegistTask(AirKoreaMapper airKoreaMapper) {
        this.airKoreaMapper = airKoreaMapper;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("AirDataCallRegistTask beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<String> stationNames = airKoreaMapper.selectStationName();
        ApiCallManageVo apiCallManageVo = new ApiCallManageVo();
        for(String stationName : stationNames){
            apiCallManageVo.setCallDiv(CallDiv.AIRDATA.getStatusCode());
            apiCallManageVo.setParam(stationName);
            apiCallManageVo.setTreateStts(TreateStts.WAIT.getTreateSttsCode());
            airKoreaMapper.insertAirdata(apiCallManageVo);
        }
        logger.info("AirDataCallRegistTask execute");
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("AirDataCallRegistTask afterStep");
        return ExitStatus.COMPLETED;
    }
}
