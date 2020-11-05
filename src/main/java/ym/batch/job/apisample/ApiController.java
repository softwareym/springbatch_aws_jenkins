package ym.batch.job.apisample;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.airkorea.item.AirData;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.airkorea.service.AirKoreaService;
import ym.batch.job.apisample.repository.ApiMapper;
import ym.batch.job.apisample.service.ApiService;
import ym.batch.job.common.service.ApiCommonInterface;
import ym.batch.job.common.service.ApiCommonService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController extends ApiCommonService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private AirKoreaService airKoreaService;

    @Autowired
    private AirKoreaMapper airKoreaMapper;

    @Value("${openapi.servicekey}")
    private String servicekey;

    @Value("${openapi.airDataUrl}")
    private String airDataUrl;

    /**
     * db연결 test
     */
    @GetMapping("/test")
    public HashMap<String, Object> selectAllJobInstance() throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> map = apiService.selectAllJobInstance();
        result.put("result", map);
        return result;

    }


}
