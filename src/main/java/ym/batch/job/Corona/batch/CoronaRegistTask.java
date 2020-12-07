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
import ym.batch.job.Corona.repository.CoronaMapper;
import ym.batch.job.Corona.service.CoronaService;

import java.util.List;


@Slf4j
@Component
public class CoronaRegistTask implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(CoronaRegistTask.class);

    CoronaService koronaService;
    CoronaMapper koronaMapper;

    public CoronaRegistTask(CoronaService koronaService, CoronaMapper koronaMapper) {
        this.koronaService = koronaService;
        this.koronaMapper = koronaMapper;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<CoronaVo> list = koronaService.callCoronaData();

        list.stream().forEach(koronaMapper::insertCoronaRegist);

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask afterStep");
        return ExitStatus.COMPLETED;
    }
}
