package ym.batch.job.common.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ApiCommonInterface {

    UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException; //url생성
    String getResponse(UriComponentsBuilder url);   //api호출

}
