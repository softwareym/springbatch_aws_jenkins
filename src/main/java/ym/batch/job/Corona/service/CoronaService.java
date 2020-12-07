package ym.batch.job.Corona.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.common.service.ApiCommonService;
import ym.batch.job.Corona.item.CoronaVo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
public class CoronaService extends ApiCommonService {

    @Value("${openapi.korona.servicekey}")
    private String servicekey;

    @Value("${openapi.koronaDataUrl}")
    private String url;

    public CoronaService(){
        super();
    }

    // corona 정보 api 호출
    public List<CoronaVo> callCoronaData() throws UnsupportedEncodingException{

        UriComponentsBuilder callUrl = urlMake(url, servicekey, null);          //요청 url&파라미터 생성
        LinkedHashMap response = (LinkedHashMap)getResponse(callUrl);        //요청한 응답데이터 get

        List<CoronaVo> result = new ArrayList<>();
        for( Object key : response.keySet() ){
            if(response.get(key) instanceof LinkedHashMap){
                CoronaVo koronaDto = new CoronaVo();
                koronaDto.setCountryName((String)((LinkedHashMap)response.get(key)).get("countryName"));
                koronaDto.setNewCase(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"newCase")));
                koronaDto.setTotalCase(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"totalCase")));
                koronaDto.setRecovered(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"recovered")));
                koronaDto.setDeath(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"death")));
                koronaDto.setNewCcase(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"newCcase")));
                koronaDto.setNewFcase(Integer.parseInt(numberFormatRemove((LinkedHashMap)response.get(key),"newFcase")));
                koronaDto.setPercentage(Float.parseFloat(numberFormatRemove((LinkedHashMap)response.get(key),"percentage")));
                result.add(koronaDto);
            }
        }
        return result;
    }

    private String numberFormatRemove(LinkedHashMap numberData, String key){
        String result;
        try{
            result = (numberData).get(key).toString().replaceAll(",","");
            Double.parseDouble(result);
            return result;
        }catch (NumberFormatException e){
            return "0";
        }
    }
}
