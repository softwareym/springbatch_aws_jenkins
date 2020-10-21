package ym.batch.job.api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public abstract class ApiServiceRestTemplate implements ApiServiceIntertace{

   // private RestTemplate restTemplate;

    /**
     * api 요청 / 응답데이터 get
     * @param uri
     * @return
     */
    @Override
    public String getResponse(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    /**
     * 요청 url&파라미터 생성
     * @param url url
     * @param qParam 요청 파라미터
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String urlMake(String url, Map<String, String> qParam) throws UnsupportedEncodingException {
        String decodeServiceKey = URLDecoder.decode(qParam.get("serviceKey").toString(), "UTF-8");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //Query string를 사용하는 Get의 경우엔 직접 URL 파라미터를 만들거나 / MultiValueMap 을 사용해야만 한다
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url);

        //요청파라미터 생성
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.setAll(qParam);
        uri.queryParams(requestParam);
        uri.build(false);

        return uri.toUriString();
    }


}
