package ym.batch.job.airkorea.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.airkorea.item.AirData;
import ym.batch.job.airkorea.item.Station;
import ym.batch.job.common.service.ApiCommonService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AirKoreaServiceTest extends ApiCommonService {

    @Autowired
    public AirKoreaService airKoreaService;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.measureStationUrl}")
    private String measureStationUrl;

    @Value("${openapi.airDataUrl}")
    private String airDataUrl;

    public AirKoreaServiceTest() {
        super();
    }

    @Test
    public void callApiStationData_측정소목록api호출성공() throws Exception {
        //when
        List<Station> collectData = airKoreaService.callApiStationData(measureStationUrl, servicekey);

        //then
        assertEquals(true, collectData.size() > 0);
    }

    @Test
    public void callApiAirData_일별대기api호출성공() throws Exception {
        //when
        List<AirData> collectData = airKoreaService.callApiAirData(airDataUrl, servicekey);

        //then
        assertEquals(true, collectData.size() > 0);
    }

    @Test
    public void api호출후_파싱성공() throws Exception {
        //given
        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("numOfRows", "1000");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        UriComponentsBuilder callUrl = urlMake(measureStationUrl, servicekey, qParam);          //요청 url&파라미터 생성
        String response = (String)getResponse(callUrl);
        checkBadResponse(response);

        //when
        List<Station> collectData = new ArrayList<>();
        collectData = (List<Station>) airKoreaService.getStationDataParse(response);     //json data parsing

        //then
        assertEquals(true, collectData.size() > 0);
    }


}