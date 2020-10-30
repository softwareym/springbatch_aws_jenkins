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

    List<AirData> collectAirData = new ArrayList<>();

    @GetMapping("/airDataTest")
    public List<AirData> callApiAirData(String url, String serviceKey) throws UnsupportedEncodingException, ParseException, java.text.ParseException {

        List<String> stationList = airKoreaMapper.selectStationName();

        for(int i=0; i<stationList.size(); i++){
            HashMap<String, String> qParam = new HashMap<>();
            qParam.put("stationName", stationList.get(i).toString());   //db 조회-ex)종로구
            qParam.put("dataTerm", "DAILY");
            qParam.put("numOfRows", "500");
            qParam.put("pageNo", "1");
            qParam.put("_returnType", "json");

            UriComponentsBuilder callUrl = urlMake(airDataUrl, servicekey, qParam);          //요청 url&파라미터 생성
            String response = getResponse(callUrl);        //요청한 응답데이터 get
            collectAirData = (List<AirData>) getAirDataParse(response, stationList.get(i).toString());     //json data parsing

            for(int k=0; k<collectAirData.size(); k++){
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("stationName", stationList.get(i).toString());
                hm.put("dataTime", collectAirData.get(k).getDataTime());

                airKoreaMapper.insertAirdata(hm);
            }
            System.out.println(collectAirData);
        }

        return collectAirData;
    }

    //api 호출 응답 json 파싱
    public List<AirData> getAirDataParse(String response, String stationName) throws ParseException, java.text.ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);      //JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JSONArray listInfoArray = (JSONArray) jsonObject.get("list");        //list 배열을 추출

        AirData[] retArray = new AirData[listInfoArray.size()];         //호출 결과를 우선 배열에 넣고, 리스트로 변환할 예정
        for (int i = 0; i < listInfoArray.size(); i++) {
            JSONObject listObject = (JSONObject) listInfoArray.get(i);   //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
            retArray[i] = new AirData();

            String dt = jsonNullChek(listObject.get("dataTime").toString(),"string" );
            SimpleDateFormat transFormatDataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date datatime = transFormatDataTime.parse(dt);
            retArray[i].setDataTime(datatime);

            //System.out.println(listObject.get("clearDate")); //JSON name으로 추출
            retArray[i].setStationName(stationName);
//                retArray[i].setDataTime(jsonNullChek(listObject.get("dataTime").toString(),"string" ));
            retArray[i].setSo2Value(Double.parseDouble(jsonNullChek(listObject.get("so2Value").toString(),"number")));
            retArray[i].setCoValue(Double.parseDouble(jsonNullChek(listObject.get("coValue").toString(),"number")));
            retArray[i].setO3Value(Double.parseDouble(jsonNullChek(listObject.get("o3Value").toString(),"number")));
            retArray[i].setNo2Value(Double.parseDouble(jsonNullChek(listObject.get("no2Value").toString(),"number")));
            retArray[i].setPm10Value(Double.parseDouble(jsonNullChek(listObject.get("pm10Value").toString(),"number")));
            retArray[i].setPm10Value24(Double.parseDouble(jsonNullChek(listObject.get("pm10Value24").toString(),"number")));
            retArray[i].setPm25Value(Double.parseDouble(jsonNullChek(listObject.get("pm25Value").toString(),"number")));
            retArray[i].setPm25Value24(Double.parseDouble(jsonNullChek(listObject.get("pm25Value24").toString(),"number")));
            retArray[i].setKhaiValue(Double.parseDouble(jsonNullChek(listObject.get("khaiValue").toString(),"number")));
            retArray[i].setKhaiGrade(Double.parseDouble(jsonNullChek(listObject.get("khaiGrade").toString(),"number")));
            retArray[i].setSo2Grade(Double.parseDouble(jsonNullChek(listObject.get("so2Grade").toString(),"number")));
            retArray[i].setCoGrade(Double.parseDouble(jsonNullChek(listObject.get("coGrade").toString(),"number")));
            retArray[i].setO3Grade(Double.parseDouble(jsonNullChek(listObject.get("o3Grade").toString(),"number")));
            retArray[i].setNo2Grade(Double.parseDouble(jsonNullChek(listObject.get("no2Grade").toString(),"number")));
            retArray[i].setPm10Grade(Double.parseDouble(jsonNullChek(listObject.get("pm10Grade").toString(),"number")));
            retArray[i].setPm25Grade(Double.parseDouble(jsonNullChek(listObject.get("pm25Grade").toString(),"number")));
            retArray[i].setPm10Grade1h(Double.parseDouble(jsonNullChek(listObject.get("pm10Grade1h").toString(),"number")));
            retArray[i].setPm25Grade1h(Double.parseDouble(jsonNullChek(listObject.get("pm25Grade1h").toString(),"number")));
            //retArray[i].setRegdate(regdate); //DB 입력시 등록하도록 함
        }
        collectAirData = Arrays.asList(retArray);//배열을 리스트로 변환
        //log.info("Rest Call result : >>>>>>>" + collectAirData);

        return collectAirData;
    }


}
