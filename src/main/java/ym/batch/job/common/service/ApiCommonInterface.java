package ym.batch.job.common.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ApiCommonInterface {

    UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException; //url생성
    Object getResponse(UriComponentsBuilder url);            //api호출
    String jsonNullChek(String paramName, String type);      //json data 항목 null 체크
    String jsonNumberCheck(String paramName);                //json data 항목 number타입 체크

}
