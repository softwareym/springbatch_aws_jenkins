package ym.batch.job.airkorea.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.airkorea.service.AirKoreaService;

import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AirDataCallRegistConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final AirKoreaMapper airKoreaMapper;

    @Bean
    public Job airDataCallRegistJob() {
        return jobBuilderFactory
                .get("airDataCallRegistJob")
                .start(airDataCallRegist())
                .build();
    }

    @Bean
    protected Step airDataCallRegist() {
        return stepBuilderFactory
                .get("airDataCallRegist")
                .tasklet(new AirDataCallRegistTask(airKoreaMapper))
                .build();
    }

}
