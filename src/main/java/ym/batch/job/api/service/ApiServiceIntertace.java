package ym.batch.job.api.service;

import java.util.List;
import java.util.Map;

public interface ApiServiceIntertace{
    List getList(String url);
    String urlMake(Map<String,Object> params);
}
