package ym.batch.job.common.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;

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
    //@SneakyThrows 어노테이션을 사용한 메소드에서 예외가 발생하면 catch문에서 e.printStackTrace(); 메소드를 호출한 것과 같이 예외가 출력된다
    @SneakyThrows
    @Override
    public String getResponse(UriComponentsBuilder uri) {
//        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

//        final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//        factory.setConnectTimeout(500000); // api 호출 타임아웃
//        factory.setReadTimeout(500000);   // api 읽기 타임아웃

//        RestTemplate restTemplate = new RestTemplate(factory);
        RestTemplate restTemplate = new RestTemplate();

        UriComponents uriComp = uri.build(false);
        String response = restTemplate.getForObject(uriComp.toUriString(), String.class);
        Thread.sleep(2000); //1000 : 1초

        if("<".equals(String.valueOf(response.charAt(0)))){             //정상적인 응답 아닐경우 xml 리턴함
            response = null;
        }
        return  Optional.ofNullable(response)
                .orElseThrow(() -> new org.json.simple.parser.ParseException(2));
    }

    @Override
    public String jsonNullChek(String paramData, String type) {
        String returnData = "";
        if("number".equals(type)){
            returnData = jsonNumberCheck(paramData);
        }else{
            if(paramData == null || paramData.trim().equals("")){
                returnData = "데이터없음";
            }else{
                returnData = paramData;
            }
        }
        return returnData;
    }

    @Override
    public String jsonNumberCheck(String paramData) {
        String returnData = "";
        if("-".equals(paramData) || paramData == null || paramData.trim().equals("")){
            returnData = "0";
        }else{
            returnData = paramData;
        }
        return returnData;
    }
}
