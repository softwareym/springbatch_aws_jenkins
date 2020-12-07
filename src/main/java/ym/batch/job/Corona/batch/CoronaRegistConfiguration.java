package ym.batch.job.Corona.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ym.batch.job.common.service.UniqueRunIdIncrementer;
import ym.batch.job.Corona.repository.CoronaMapper;
import ym.batch.job.Corona.service.CoronaService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CoronaRegistConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CoronaService koronaService;
    private final CoronaMapper koronaMapper;

    @Bean
    public Job koronaRegistJob() {
        return jobBuilderFactory
                .get("koronaRegistJob")
                .start(koronaRegist())
                .incrementer(new UniqueRunIdIncrementer())
                .build();
    }

    @Bean
    protected Step koronaRegist() {
        return stepBuilderFactory
                .get("koronaRegist")
                .tasklet(new CoronaRegistTask(koronaService , koronaMapper))
                .build();
    }

}
