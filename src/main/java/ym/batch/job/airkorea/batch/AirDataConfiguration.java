package ym.batch.job.airkorea.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ym.batch.job.airkorea.item.AirData;
import ym.batch.job.airkorea.service.AirKoreaService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AirDataConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final AirKoreaService airKoreaService;

    private static final int CHUNKSIZE = 25;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.airDataUrl}")
    private String airDataUrl;

    protected List<AirData> collectAirData = new ArrayList<>();
    private boolean checkRestCall = false;
    private int nextIndex = 0;

    private int poolSize;

    @Value("${poolSize:10}") // (1)
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Bean(name = "airkoreaDataJob_taskPool")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // (2)
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    @Bean
    public Job airDataJob(){
        return jobBuilderFactory.get("airDataJob")
                .start(airDataStep())
                .build();
    }

    @Bean
    public Step airDataStep(){

        //싱글 쓰레드
        return stepBuilderFactory.get("airDataStep")
                .<AirData, AirData>chunk(CHUNKSIZE)     //첫번째는 Reader에서 반환할 타입이고, 두번째는 Writer에 파라미터로 넘어올 타입
                .reader(airDataRestCollectReader())
                .writer(airDataCollectWriter())
                .build();
        /*
        return stepBuilderFactory.get("airDataStep")
                .<AirData, AirData>chunk(CHUNKSIZE)
                .reader(airDataRestCollectReader())
                .writer(airDataCollectWriter())
                .taskExecutor(executor()) // (2)
                .throttleLimit(poolSize) // (3)
                .build();
        */

    }

    //Rest API로 데이터를 가져온다.
    @Bean
    public ItemReader<AirData> airDataRestCollectReader(){
        return new ItemReader<AirData>(){
            @Override
            public AirData read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException, IllegalArgumentException {

                if (checkRestCall == false) {   //호출되었는지 체크
                    collectAirData = airKoreaService.callApiAirData(airDataUrl, servicekey);
                    checkRestCall = true; //다음 read() 부터는 재호출 방지하기 위해 true로 변경
                }
                AirData nextCollect = null; //ItemReader는 반복문으로 동작한다. 하나씩 Writer로 전달해야 한다.
                if (nextIndex < collectAirData.size()) {//전체 리스트에서 0부터 하나씩 추출해서, 하나씩 Writer로 전달
                    nextCollect = collectAirData.get(nextIndex);
                    nextIndex++;
                }
                return nextCollect;
            }
        };
    }

    @Bean
    public JdbcBatchItemWriter<AirData> airDataCollectWriter(){
        return new JdbcBatchItemWriterBuilder<AirData>()
                .dataSource(dataSource)
                .sql("insert into tblAirData (stationName, dataTime, so2Value, coValue, o3Value, no2Value, pm10Value, pm10Value24, pm25Value, pm25Value24, khaiValue, khaiGrade, so2Grade, coGrade, o3Grade, no2Grade, pm10Grade, pm25Grade, pm10Grade1h, pm25Grade1h) VALUES (:stationName, :dataTime, :so2Value, :coValue, :o3Value, :no2Value, :pm10Value, :pm10Value24, :pm25Value, :pm25Value24, :khaiValue, :khaiGrade, :so2Grade, :coGrade, :o3Grade, :no2Grade, :pm10Grade, :pm25Grade, :pm10Grade1h, :pm25Grade1h)")
                .beanMapped()
                .build();
    }
}
