package ym.batch.job.api.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ApiServiceIntertace{

    UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException;
    String getResponse(UriComponentsBuilder url);

}
