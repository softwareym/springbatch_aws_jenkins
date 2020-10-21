package ym.batch.job.common.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

@AllArgsConstructor
public abstract class ApiCommonService implements ApiCommonInterface {

    /**
     * 요청 url&파라미터 생성
     * @param url url0
     * @param qParam 요청 파라미터
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public UriComponentsBuilder urlMake(String url, String serviceKey, Map<String, String> qParam) throws UnsupportedEncodingException {
        String decodeServiceKey = URLDecoder.decode(serviceKey, "UTF-8");

        //Query string를 사용하는 Get의 경우엔 직접 URL 파라미터를 만들거나 / MultiValueMap 을 사용해야만 한다
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("serviceKey",decodeServiceKey);

        //요청파라미터 생성
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.setAll(qParam);
        uri.queryParams(requestParam);

        return uri;
    }

    /**
     * api 요청 / 응답데이터 get
     * @param uri
     * @return
     */
    @Override
    public String getResponse(UriComponentsBuilder uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UriComponents uriComp = uri.build(false);

        String response = restTemplate.getForObject(uriComp.toUriString(), String.class);
        return response;
    }
}
