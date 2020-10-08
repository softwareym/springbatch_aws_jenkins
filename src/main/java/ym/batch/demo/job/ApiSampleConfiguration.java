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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.demo.job.item.MicroDust;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
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
        return jobBuilderFactory.get("apiSampleJob")
                .start(collectStep())
                .build();
    }

    @Bean
    public Step collectStep(){
        /*
        return stepBuilderFactory.get("collectStep")
                .<MicroDust, MicroDust>chunk(5)
                .reader(restCollectReader())
                .build();
*/

        return stepBuilderFactory.get("collectStep")
                .<MicroDust, MicroDust>chunk(5)
                .reader(restCollectReader())
                .writer(collectWriter())
                .build();


    }

    //Rest API로 데이터를 가져온다.
    @Bean
    public ItemReader<MicroDust> restCollectReader(){
        return new ItemReader<MicroDust>(){
            @Override
            public MicroDust read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if (checkRestCall == false){//한번도 호출 않았는지 체크

                    // url 을 넘겨주는 아규먼트가 String 타입이면 restTemplate.getForObject.. URL 인코딩이 자동으로 발생
                    //String uri = "http://openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?ServiceKey=서비스키&year=2020&numOfRows=10&pageNo=1&_returnType=json"; //로컬에서 Rest Server용 프로그램을 하나 생성후 기동해둔다.
/*
                    String keyParam ="qfkFclseeogGMHMInM5T9naoiRhtjxGK6feqrIz4WqK4Nw68DkyuSLQlwVghnRLKg0HFCIGughqC7f3WFMHKgQ%3D%3D";

                    StringBuilder builder = new StringBuilder("http://");
                    builder.append("openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo");
                    builder.append("?ServiceKey=qfkFclseeogGMHMInM5T9naoiRhtjxGK6feqrIz4WqK4Nw68DkyuSLQlwVghnRLKg0HFCIGughqC7f3WFMHKgQ%3D%3D");
                    builder.append("&year=2020&numOfRows=10&pageNo=1&_returnType=json");

                    URI uri = URI.create(builder.toString());

                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));    //Response Header to UTF-8

                    MicroDust[] retArray = restTemplate.getForObject(uri, MicroDust[].class);   //호출 결과를 우선 배열로 받고, 리스트로 변환
                    collectData = Arrays.asList(retArray);//배열을 리스트로 변환
                    log.info("Rest Call result : >>>>>>>" + collectData);
                    checkRestCall = true;//다음 read() 부터는 재호출 방지하기 위해 true로 변경
*/

                    String url = "http://openapi.airkorea.or.kr/openapi/services/rest/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo";
                    String serviceKey = "qfkFclseeogGMHMInM5T9naoiRhtjxGK6feqrIz4WqK4Nw68DkyuSLQlwVghnRLKg0HFCIGughqC7f3WFMHKgQ%3D%3D";

                    // UnsupportedEncodingException 예외 처리를 해주지 않으면 여기에서 ERROR
                    String decodeServiceKey = URLDecoder.decode(serviceKey, "UTF-8");

                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    // Response Header to UTF-8
                    httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


                    UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("serviceKey", decodeServiceKey)
                            .queryParam("year", "2020")
                            .queryParam("numOfRows", "10")
                            .queryParam("pageNo", "1")
                            .queryParam("_returnType", "json")
                            .build(false); // 자동 Encoding 막기

                    //Object response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, new HttpEntity<String>(httpHeaders), String.class);
                    Object response = restTemplate.getForObject(uri.toUriString(), String.class);
                    System.out.println("response");

 /*
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(rjsonData);
                    String access_token = element.getAsJsonObject().get("access_token").getAsString();
                    String token_type = element.getAsJsonObject().get("token_type").getAsString();
*/

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
                .sql("insert into MicroDust (dataDate, itemCode, districtName, moveName, issueDate, issueTime, issueVal, issueGbn, clearDate, clearTime, clearVal) VALUES (:dataDate, :itemCode, :districtName, :moveName, :issueDate, :issueTime, :issueVal, :issueGbn, :clearDate, :clearTime, :clearVal)")
                .beanMapped()
                .build();
    }


}
