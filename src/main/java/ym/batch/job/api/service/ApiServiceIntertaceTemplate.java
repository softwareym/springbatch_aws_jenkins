package ym.batch.job.api.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public abstract class ApiServiceIntertaceTemplate implements ApiServiceIntertace{
    private RestTemplate restTemplate;

    @Override
    public List getList(String url) {
        // 기타필요 정보
        //restTemplate.exchange(url)
        return null;
    }

    @Override
    public String urlMake(Map<String, Object> params) {
        return "";
    }


}
