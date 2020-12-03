package ym.batch.job.airkorea.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.airkorea.item.AirData;
import ym.batch.job.airkorea.item.ApiCallManageDto;
import ym.batch.job.airkorea.item.ApiCallManageVo;
import ym.batch.job.airkorea.item.Station;
import ym.batch.job.airkorea.repository.AirKoreaMapper;
import ym.batch.job.common.constant.DateFormat;
import ym.batch.job.common.service.ApiCommonService;
import ym.batch.job.common.status.CallDiv;
import ym.batch.job.common.status.TreateStts;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class AirKoreaService extends ApiCommonService {

    //생성자 - 초기화
    public AirKoreaService() {
        super();
    }

    @Autowired
    AirKoreaMapper airKoreaMapper;

    //측정소 목록정보 api 호출
    public List<Station> callApiStationData(String url, String serviceKey) throws UnsupportedEncodingException, ParseException {

        HashMap<String, String> qParam = new HashMap<>();
        qParam.put("numOfRows", "1000");
        qParam.put("pageNo", "1");
        qParam.put("_returnType", "json");

        UriComponentsBuilder callUrl = urlMake(url, serviceKey, qParam);          //요청 url&파라미터 생성
        String response = (String)getResponse(callUrl);        //요청한 응답데이터 get
        checkBadResponse(response);

        List<Station> collectData = new ArrayList<>();
        collectData = (List<Station>) getStationDataParse(response);     //json data parsing

        return collectData;
    }

    //api 호출 응답 json 파싱.
    public List<Station> getStationDataParse(String response) throws ParseException {
        List<Station> collectData = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);      //JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JSONArray listInfoArray = (JSONArray) jsonObject.get("list");        //list 배열을 추출

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
            //retArray[i].setRegdate(regdate);        //DB 입력시 등록하도록 함
        }
        collectData = Arrays.asList(retArray);//배열을 리스트로 변환
        //log.info("Rest Call result : >>>>>>>" + collectData);

        return collectData;
    }

    //측정소 목록정보 조회
    public List<String> selectStationName(){
        return airKoreaMapper.selectStationName();
    }

    //대기오염정보 api 호출
    private List<AirData> parseAirData = new ArrayList<>();

    public List<AirData> callApiAirData(String url, String serviceKey) throws UnsupportedEncodingException, ParseException, java.text.ParseException, InterruptedException {
        ApiCallManageVo apiCallManageVo = new ApiCallManageVo();
        apiCallManageVo.setCallDiv(CallDiv.AIRDATA.getStatusCode());
        apiCallManageVo.setTreateStts(TreateStts.WAIT.getTreateSttsCode());
        apiCallManageVo.setWorkDate(LocalDate.now());

        List<ApiCallManageDto> stationList = airKoreaMapper.selectStationNamesForCall(apiCallManageVo);

        for(int i=0; i<stationList.size(); i++){
            HashMap<String, String> qParam = new HashMap<>();
            qParam.put("stationName", stationList.get(i).getParam());   //db 조회-ex)종로구
            qParam.put("dataTerm", "DAILY");
            qParam.put("numOfRows", "500");
            qParam.put("pageNo", "1");
            qParam.put("_returnType", "json");

            UriComponentsBuilder callUrl = urlMake(url, serviceKey, qParam);          //요청 url&파라미터 생성
            String response = (String)getResponse(callUrl);        //요청한 응답데이터 get
            checkBadResponse(response);
      //            Thread.sleep(2000); //1000 : 1초//            parseAirData =  getAirDataParse(response, stationList.get(i).toString());     //json data parsing
            getAirDataParse(response, stationList.get(i).toString());     //json data parsing
      //      System.out.println("[***]  : " +parseAirData.size());
        }

        List<Long> apiCallManageSrls = stationList.stream()
                                                    .map(station -> station.getApiCallManageSeq())
                                                    .collect(Collectors.toList());
        // 정상적으로 호출된 api_call은 상태값을 WT => DN 처리
        if(!apiCallManageSrls.isEmpty()){
            airKoreaMapper.updateTreeteStts(apiCallManageSrls);
        }
        return parseAirData;
    }

    //api 호출 응답 json 파싱, 리스트에 추가
    public void getAirDataParse(String response, String stationName) throws ParseException, java.text.ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);      //JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JSONArray listInfoArray = (JSONArray) jsonObject.get("list");        //list 배열을 추출

        AirData[] retArray = new AirData[listInfoArray.size()];         //호출 결과를 우선 배열에 넣고, 리스트로 변환할 예정+++


        for (int i = 0; i < listInfoArray.size(); i++) {
            JSONObject listObject = (JSONObject) listInfoArray.get(i);   //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
            retArray[i] = new AirData();

            String dt = jsonNullChek(listObject.get("dataTime").toString(),"string" );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime datatime = LocalDateTime.parse(dt, formatter);
            retArray[i].setDataTime(datatime);

            //System.out.println(listObject.get("clearDate")); //JSON name으로 추출
            retArray[i].setStationName(stationName);
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
            parseAirData.add(retArray[i]);
        }

        //collectAirData = Arrays.asList(retArray);//배열을 리스트로 변환
        log.info("Rest Call result : >>>>>>>" + parseAirData.size());
      //  return parseAirData;
    }

}
