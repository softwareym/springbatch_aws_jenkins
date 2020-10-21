package ym.batch.job.airkorea.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.airkorea.item.Station;
import ym.batch.job.apisample.item.MicroDust;
import ym.batch.job.apisample.repository.ApiMapper;
import ym.batch.job.common.service.ApiCommonService;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Transactional
@Service
public class AirKoreaService extends ApiCommonService {

    //생성자 - 초기화
    public AirKoreaService() {
        super();
    }

    @Autowired
    ApiMapper apiMapper;

    @Override
    public UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException {
        return super.urlMake(url, serviceKey, qParam);
    }

    @Override
    public String getResponse(UriComponentsBuilder uri) {
        return super.getResponse(uri);
    }


    //미세먼지 api 호출
    public List<Station> callApiStationData(String url, String serviceKey) throws Exception {

        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("numOfRows", "1000");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        UriComponentsBuilder callUrl = urlMake(url, serviceKey, qParam);          //요청 url&파라미터 생성
        String response = getResponse(callUrl);        //요청한 응답데이터 get

        List<Station> collectData = new ArrayList<>();
        collectData = (List<Station>) getDataParse(response);     //json data parsing

        return collectData;
    }

    //api 호출 응답 json 파싱
    public List<Station> getDataParse(String response){
        List<Station> collectData = new ArrayList<>();


        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);      //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONArray listInfoArray = (JSONArray) jsonObject.get("list");        //list 배열을 추출

            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String regdate = dayTime.format(new Date(time));

            Station[] retArray = new Station[listInfoArray.size()];         //호출 결과를 우선 배열에 넣고, 리스트로 변환할 예정
            for (int i = 0; i < listInfoArray.size(); i++) {
                JSONObject listObject = (JSONObject) listInfoArray.get(i);   //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
                retArray[i] = new Station();

                //System.out.println(listObject.get("clearDate")); //JSON name으로 추출
                retArray[i].setStationName(listObject.get("stationName").toString());
                retArray[i].setAddr(listObject.get("addr").toString());

                if(!"".equals(listObject.get("year").toString())) {
                    retArray[i].setYear(Integer.parseInt(listObject.get("year").toString()));
                }
                retArray[i].setOperationAgency(listObject.get("oper").toString());
                retArray[i].setStationPhoto(listObject.get("photo").toString());
                retArray[i].setStationVrml(listObject.get("vrml").toString());
                retArray[i].setMap(listObject.get("map").toString());
                retArray[i].setMangName(listObject.get("mangName").toString());
                retArray[i].setMeasureItem(listObject.get("item").toString());

                if(!"".equals(listObject.get("dmX").toString())) {
                    retArray[i].setDmx(Double.parseDouble(listObject.get("dmX").toString()));
                }
                if(!"".equals(listObject.get("dmY").toString())) {
                    retArray[i].setDmx(Double.parseDouble(listObject.get("dmY").toString()));
                }
                retArray[i].setRegdate(regdate);
            }
            collectData = Arrays.asList(retArray);//배열을 리스트로 변환
            //log.info("Rest Call result : >>>>>>>" + collectData);
        } catch (Exception e) {
            log.info("api not accessible(wrong request)");
        }
        return collectData;
    }

    public HashMap<String, Object> selectAllJobInstance() throws Exception {
        return apiMapper.selectAllJobInstance();
    }

}
