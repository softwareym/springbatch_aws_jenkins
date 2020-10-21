package ym.batch.job.api.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ApiServiceIntertace{

    String getResponse(String url);
    String urlMake(String url, Map<String, String> qParam) throws UnsupportedEncodingException;

}
