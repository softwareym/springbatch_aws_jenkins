package ym.batch.job.korona.batch;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ym.batch.job.airkorea.item.ApiCallManageVo;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.common.status.CallDiv;
import ym.batch.job.common.status.TreateStts;
import ym.batch.job.korona.item.KoronaVo;
import ym.batch.job.korona.repository.KoronaMapper;
import ym.batch.job.korona.service.KoronaService;

import java.util.List;


@Slf4j
@Component
public class KoronaRegistTask implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(KoronaRegistTask.class);

    KoronaService koronaService;
    KoronaMapper koronaMapper;

    public KoronaRegistTask(KoronaService koronaService, KoronaMapper koronaMapper) {
        this.koronaService = koronaService;
        this.koronaMapper = koronaMapper;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask beforeStep");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<KoronaVo> list = koronaService.callCoronaData();

        list.stream().forEach(koronaMapper::insertCoronaRegist);

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("KoronaRegistTask afterStep");
        return ExitStatus.COMPLETED;
    }
}
