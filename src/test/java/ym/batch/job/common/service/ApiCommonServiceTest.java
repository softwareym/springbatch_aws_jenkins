package ym.batch.job.common.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiCommonServiceTest {

    @Qualifier("airKoreaService")
    @Autowired
    public ApiCommonService apiCommonService;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.airDataUrl}")
    private String airDataUrl;


    @Test
    public void urlMake_성공() throws UnsupportedEncodingException {

        //given
        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("numOfRows", "1000");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        //when
        UriComponentsBuilder uri = apiCommonService.urlMake(airDataUrl, servicekey, qParam);

        //then
        assertNotNull(uri);
    }

    @Test
    public void getResponse_api응답성공() throws UnsupportedEncodingException {
        //given
        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("numOfRows", "1000");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        UriComponentsBuilder uri = apiCommonService.urlMake(airDataUrl, servicekey, qParam);

        //when
        String response = (String)apiCommonService.getResponse(uri);

        //then
        assertNotNull(response);
    }

    @Test
    public void jsonNullChek_string타입_null일경우() {
        //given
        String paramData = "";
        String type = "string";

        //when
        String returnData = apiCommonService.jsonNullChek(paramData,type);

        //then
        assertEquals("데이터없음",returnData);
    }

    @Test
    public void jsonNullChek_string타입_null아닐경우() {
        //given
        String paramData = "데이터";
        String type = "string";

        //when
        String returnData = apiCommonService.jsonNullChek(paramData,type);

        //then
        assertEquals("데이터", returnData);
    }

    @Test
    public void jsonNullChek_number타입_null일경우() {
        //given
        String paramData = "-";
        String type = "number";

        //when
        String returnData = apiCommonService.jsonNullChek(paramData,type);

        //then
        assertEquals("0", returnData);
    }

    @Test
    public void jsonNullChek_number타입_null아닐경우() {
        //given
        String paramData = "100";
        String type = "number";

        //when
        String returnData = apiCommonService.jsonNullChek(paramData,type);

        //then
        assertEquals("100", returnData);
    }

}