package ym.batch.job.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ym.batch.job.api.item.MicroDust;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiServiceTest {

    @Rule
    ExpectedException expectedException =  ExpectedException.none();

    @Autowired
    public ApiService apiService;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.microdustUrl}")
    private String microDustUrl;


    public void mysql_db연동_호출() throws Exception {
        HashMap<String,Object> result = apiService.selectAllJobInstance();
        assertNotNull(result);
    }

    //@Test
    public void 미세먼지api_호출_성공() throws Exception{
        //given
        //when
        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, servicekey);

        //then
        assertEquals(true, result.size() > 0);
    }


    public void 미세먼지api_호출_실패_잘못된인증키() throws Exception{
        //give
        //when
        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, "wrong_servicekey");

        //then
        assertEquals(result.size(),0);
    }

    /*
    public void 미세먼지api_호출_정상적이지않은_url인경우(){

    }

    public void 미세먼지api_호출_실패_잘못된인증키경우_exception확인(){
        expectedException.expect(Exception.class);
        expectedException.expectMessage("");

        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, servicekey);

    }
    */


    public void 미세먼지api_이미호출() throws Exception{
        //given
        //when
        List<MicroDust> result = apiService.callApiMicroDustData(microDustUrl, servicekey);

        //then
        assertEquals(result.size(), 0);
    }

}