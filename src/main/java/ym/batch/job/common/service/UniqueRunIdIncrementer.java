package ym.batch.job.common.service;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;


/*
배치 실행시 유니크한 version 값을 줘서 파라미터 값이 중복되지 처리하는 방법이 번거로워 개선
 */
public class UniqueRunIdIncrementer extends RunIdIncrementer {
    private static final String RUN_ID = "run.id";

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;
        return new JobParametersBuilder()
                .addLong(RUN_ID, params.getLong(RUN_ID, 0L) + 1)
                .toJobParameters();
    }
}
