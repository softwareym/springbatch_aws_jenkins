package ym.batch.demo.job;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ym.batch.demo.job.item.MicroDust;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApiSampleConfiguration {

    private final JobBuilderFactory jobBuilderFactory;//Job 생성자
    private final StepBuilderFactory stepBuilderFactory;//Step 생성자
    private final DataSource dataSource;//데이터 소스
    private static final int CHUNKSIZE = 5; //쓰기 단위인 청크사이즈


    private List<MicroDust> collectData = new ArrayList<>();    //Rest로 가져온 데이터를 리스트에 넣는다.
    private boolean checkRestCall = false; //RestAPI 호출여부 판단
    private int nextIndex = 0;//리스트의 데이터를 하나씩 인덱스를 통해 가져온다.

    @Bean
    public Job testJob(){
        return jobBuilderFactory.get("ApiSampleJob")
                .start(collectStep())
                .build();
    }

    @Bean
    public Step collectStep(){
        return stepBuilderFactory.get("collectStep")
                .<MicroDust, MicroDust>chunk(5)
                .reader(restCollectReader())
                .build();

        /*
        return stepBuilderFactory.get("collectStep")
                .<MicroDust, MicroDust>chunk(5)
                .reader(restCollectReader())
                .writer(collectWriter())            //todo ***
                .build();

         */
    }

    //Rest API로 데이터를 가져온다.
    @Bean
    public ItemReader<MicroDust> restCollectReader(){
        return new ItemReader<MicroDust>(){
            @Override
            public MicroDust read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (checkRestCall == false){//한번도 호출 않았는지 체크
//                    String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?ServiceKey=서비스키&year=2020&numOfRows=10&pageNo=1&_returnType=json"; //로컬에서 Rest Server용 프로그램을 하나 생성후 기동해둔다.
                    String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?";
                    MicroDust md = new MicroDust();

                    String keyParam = "ServiceKey="+md.getServicekey();
                    String param = "&year=2020&numOfRows=10&pageNo=1&_returnType=json";

                    uri = uri + keyParam + param;

                    System.out.println("sout >>> ServiceKey : " + keyParam);
                    System.out.println("sout >>> uri : " + uri);

                    RestTemplate restTemplate = new RestTemplate();
                    MicroDust[] retArray = restTemplate.getForObject(uri, MicroDust[].class); //호출 결과를 우선 배열로 받고, 리스트로 변환
                    collectData = Arrays.asList(retArray);//배열을 리스트로 변환
                    log.info("Rest Call result : >>>>>>>" + collectData);
                    checkRestCall = true;//다음 read() 부터는 재호출 방지하기 위해 true로 변경
                }

                MicroDust nextCollect = null; //ItemReader는 반복문으로 동작한다. 하나씩 Writer로 전달해야 한다.

                if (nextIndex < collectData.size()) {//전체 리스트에서 하나씩 추출해서, 하나씩 Writer로 전달
                    nextCollect = collectData.get(nextIndex);
                    nextIndex++;
                }
                return nextCollect;//MicroDust 하나씩 반환한다. Rest 호출시 데이터가 없으면 null로 반환.
            }
        };
    }

    @Bean // beanMapped()를 사용할때는 필수
    public JdbcBatchItemWriter<MicroDust> collectWriter(){              //db에 데이터를 쓴다.
        return new JdbcBatchItemWriterBuilder<MicroDust>()
                .dataSource(dataSource)
                .sql(" insert into MicroDust (dataDate, itemCode, districtName, moveName, issueDate, issueTime, issueVal, issueGbn, clearDate, clearTime, clearVal) VALUES (:dataDate, :itemCode, :districtName, :moveName, :issueDate, :issueTime, :issueVal, :issueGbn, :clearDate, :clearTime, :clearVal)")
                .beanMapped()
                .build();
    }


}
