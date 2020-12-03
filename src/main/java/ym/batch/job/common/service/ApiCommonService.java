package ym.batch.job.common.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ym.batch.job.common.error.ErrorMessage;
import ym.batch.job.common.exception.ApiException;

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
    //@SneakyThrows 어노테이션을 사용한 메소드에서 예외가 발생하면 catch문에서 e.printStackTrace(); 메소드를 호출한 것과 같이 예외가 출력된다
    @SneakyThrows
    @Override
    public Object getResponse(UriComponentsBuilder uri) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        RestTemplate restTemplate = new RestTemplate();

        UriComponents uriComp = uri.build(false);
        Object response = restTemplate.getForObject(uriComp.toUriString(), Object.class);
        Thread.sleep(5000); //1000 : 1초

        return response;
    }

    public void checkBadResponse(String response){
        if("<".equals(String.valueOf(response.charAt(0)))){ //정상적인 응답 아닐경우 xml 리턴
            response = null;

            if(response.contains("SERVICE KEY IS NOT REGISTERED ERROR")){
                throw new ApiException(
                        String.format(ErrorMessage.INVALID_WRONG_SERVICEKEY.getErrorMessage())
                        , HttpStatus.BAD_REQUEST);
            }else if(response.contains("LIMITED")){
                throw new ApiException(
                        String.format(ErrorMessage.INVALID_WRONG_RESPONSE.getErrorMessage())
                        , HttpStatus.BAD_REQUEST);
            }else{
                throw new ApiException(
                        String.format(ErrorMessage.INVALID_WRONG_ETC.getErrorMessage())
                        , HttpStatus.BAD_REQUEST);
            }
        }
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
