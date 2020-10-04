package ym.batch.demo.job.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ym.batch.demo.job.api.repository.ApiMapper;

import java.util.HashMap;

@Slf4j
@Transactional
@Service
public class ApiService {

    @Autowired
    ApiMapper apiMapper;

   public HashMap<String,Object> selectAllJobInstance() throws Exception {
       return apiMapper.selectAllJobInstance();
   }
}
