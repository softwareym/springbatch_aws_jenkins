package ym.batch.job.apisample.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ym.batch.job.apisample.item.MicroDust;
import ym.batch.job.common.service.ApiCommonInterface;
import ym.batch.job.common.service.ApiCommonService;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiServiceTest extends ApiCommonService {

    /*
        @Rule 애노테이션을 사용해서 ExpectedException인스턴스를 만들어 준뒤 테스트 메서드에서 expect()로 확인하는 예외클래스를 적어주고
        expectMessage()를 통해서 예외에 담겨진 메세지를 확인할 수 있습니다.
    */
    @Rule
    public ExpectedException expectedException =  ExpectedException.none();

    @Autowired
    public ApiService apiService;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.microdustUrl}")
    private String microDustUrl;

    public ApiServiceTest() {
        super();
    }

    public void mysql_db연동_호출() throws Exception {
        HashMap<String,Object> result = apiService.selectAllJobInstance();
        assertNotNull(result);
    }

    public void 미세먼지api_호출_성공() throws Exception{
        //given
        //when
        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, servicekey);

        //then
        assertEquals(true, result.size() > 0);
    }

    @Test
    public void 미세먼지api_호출_실패_잘못된인증키경우_exception확인() throws UnsupportedEncodingException, ParseException {
        expectedException.expect(ParseException.class);
        //expectedException.expectMessage("msg");

        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, "wrongservicekey");
    }

}