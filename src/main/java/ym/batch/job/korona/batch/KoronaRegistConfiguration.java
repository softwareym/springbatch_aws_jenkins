package ym.batch.job.korona.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ym.batch.job.airkorea.batch.AirDataCallRegistTask;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.common.service.UniqueRunIdIncrementer;
import ym.batch.job.korona.repository.KoronaMapper;
import ym.batch.job.korona.service.KoronaService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class KoronaRegistConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final KoronaService koronaService;
    private final KoronaMapper koronaMapper;

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
                .tasklet(new KoronaRegistTask(koronaService , koronaMapper))
                .build();
    }

}
