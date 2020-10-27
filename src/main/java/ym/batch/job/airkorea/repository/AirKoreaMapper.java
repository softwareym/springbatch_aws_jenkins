package ym.batch.job.airkorea.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AirKoreaMapper {

    private final SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.repository.AirKoreaMapper.";

    public AirKoreaMapper(@Qualifier("sqlSession") SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public String selectStationName() throws Exception{
        return sqlSession.selectOne(NAMESPACE+"selectStationName");
    }
}
