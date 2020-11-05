package ym.batch.job.apisample.service;

import jdk.nashorn.internal.runtime.ParserException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.apisample.repository.ApiMapper;
import ym.batch.job.apisample.item.MicroDust;
import ym.batch.job.common.service.ApiCommonService;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Transactional
@Service
public class ApiService extends ApiCommonService {

    //생성자 - 초기화
    public ApiService() {
        super();
    }

    @Autowired
    ApiMapper apiMapper;

    /*
    @Override
    public UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException {
        return super.urlMake(url, serviceKey, qParam);
    }

    @Override
    public String getResponse(UriComponentsBuilder uri) {
        return super.getResponse(uri);
    }
    */

    //미세먼지 api 호출
    public List<MicroDust> callApiMicroDustData(String url, String serviceKey) throws UnsupportedEncodingException, ParseException {

        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("year", "2020");
        qParam.put("numOfRows", "10");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        UriComponentsBuilder callUrl = urlMake(url, serviceKey, qParam);          //요청 url&파라미터 생성
        String response = getResponse(callUrl);        //요청한 응답데이터 get

        List<MicroDust> collectData = new ArrayList<>();
        collectData = (List<MicroDust>) getDataParse(response);     //json data parsing
        return collectData;
    }

    //api 호출 응답 json 파싱
    public List<MicroDust> getDataParse(String response) throws ParseException {
        List<MicroDust> collectData = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);      //JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JSONArray listInfoArray = (JSONArray) jsonObject.get("list");        //list 배열을 추출

        MicroDust[] retArray = new MicroDust[listInfoArray.size()];         //호출 결과를 우선 배열에 넣고, 리스트로 변환할 예정
        for (int i = 0; i < listInfoArray.size(); i++) {
            JSONObject listObject = (JSONObject) listInfoArray.get(i);   //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
            retArray[i] = new MicroDust();

            //System.out.println(listObject.get("clearDate")); //JSON name으로 추출
            retArray[i].setDataDate(listObject.get("dataDate").toString());
            retArray[i].setClearDate(listObject.get("clearDate").toString());
            retArray[i].setItemCode(listObject.get("itemCode").toString());
            retArray[i].setDistrictName(listObject.get("districtName").toString());
            retArray[i].setMoveName(listObject.get("moveName").toString());
            retArray[i].setIssueDate(listObject.get("issueDate").toString());
            retArray[i].setIssueTime(listObject.get("issueTime").toString());
            retArray[i].setIssueVal(Integer.parseInt(listObject.get("issueVal").toString()));
            retArray[i].setIssueGbn(listObject.get("issueGbn").toString());
            retArray[i].setClearDate(listObject.get("clearDate").toString());
            retArray[i].setClearTime(listObject.get("clearTime").toString());
            retArray[i].setClearVal(Integer.parseInt(listObject.get("clearVal").toString()));
        }
        collectData = Arrays.asList(retArray);//배열을 리스트로 변환
        log.info("Rest Call result : >>>>>>>" + collectData);

        return collectData;
    }

    public HashMap<String, Object> selectAllJobInstance() throws Exception {
        return apiMapper.selectAllJobInstance();
    }

}
