package ym.batch.job.apisample.batch;

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
import ym.batch.job.apisample.service.ApiService;
import ym.batch.job.apisample.item.MicroDust;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
  -스프링 배치의 ItemReader는 파일/XML/DB에서 데이터를 읽기 위한 전용 클래스를 제공하지만
  RestAPI로 다른시스템의 데이터를 읽어오는 전용 클래스는 아직 없다고 한다. 따라서 커스텀 필요
  - @RequiredArgsConstructor :초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성.
                              의존성 주입(Dependency Injection)을 위해 사용
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApiSampleConfiguration {

    private final JobBuilderFactory jobBuilderFactory;//Job 생성자
    private final StepBuilderFactory stepBuilderFactory;//Step 생성자
    private final DataSource dataSource;//데이터 소스
    final ApiService apiService;

    /*
        *Chunk Size : 한번에 처리될 트랜잭션 단위
        [Reader와 > processor에서 가공된 데이터들을 별도의 공간에 모은 뒤,
        Chunk 단위만큼 쌓이게 되면 Writer에 전달하고 Writer는 일괄 저장한다.
        Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리]
     */
    private static final int CHUNKSIZE = 5; //쓰기 단위인 청크사이즈

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.microdustUrl}")
    private String microDustUrl;        //MicroDust api 호출 url

    private List<MicroDust> collectData = new ArrayList<>();    //Rest로 가져온 데이터를 리스트에 넣는다.
    private boolean checkRestCall = false; //RestAPI 호출여부 판단
    private int nextIndex = 0;//리스트의 데이터를 하나씩 인덱스를 통해 가져온다.

    @Bean
    public Job testJob(){
        return jobBuilderFactory.get("apiSampleJob")
                .start(collectStep())
                .build();
    }

    @Bean
    public Step collectStep(){
        return stepBuilderFactory.get("collectStep")
                .<MicroDust, MicroDust>chunk(CHUNKSIZE)     //첫번째 MicroDust는 Reader에서 반환할 타입이고, 두번째 MicroDust는 Writer에 파라미터로 넘어올 타입
                .reader(restCollectReader())
                .writer(collectWriter())
                .build();
    }

    //Rest API로 데이터를 가져온다.
    @Bean
    public ItemReader<MicroDust> restCollectReader(){
        return new ItemReader<MicroDust>(){
            @Override
            public MicroDust read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException, IllegalArgumentException {

                if (checkRestCall == false) {   //호출되었는지 체크
                    collectData = apiService.callApiMicroDustData(microDustUrl, servicekey);
                    checkRestCall = true; //다음 read() 부터는 재호출 방지하기 위해 true로 변경
                }
                MicroDust nextCollect = null; //ItemReader는 반복문으로 동작한다. 하나씩 Writer로 전달해야 한다.
                if (nextIndex < collectData.size()) {//전체 리스트에서 0부터 하나씩 추출해서, 하나씩 Writer로 전달
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
                .sql("insert into tblMicroDust (dataDate, itemCode, districtName, moveName, issueDate, issueTime, issueVal, issueGbn, clearDate, clearTime, clearVal) VALUES (:dataDate, :itemCode, :districtName, :moveName, :issueDate, :issueTime, :issueVal, :issueGbn, :clearDate, :clearTime, :clearVal)")
                .beanMapped()       //Pojo 기반으로 Insert SQL의 Values를 매핑
                .build();
    }

}
