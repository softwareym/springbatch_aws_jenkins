package ym.batch.job.api.repository;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ApiMapper {

    @Autowired
    @Qualifier("sqlSession")
    private SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.api.repository.ApiMapper.";

    public HashMap<String, Object> selectAllJobInstance() throws Exception{
        return sqlSession.selectOne(NAMESPACE+"selectAllJobInstance");
    }
}



