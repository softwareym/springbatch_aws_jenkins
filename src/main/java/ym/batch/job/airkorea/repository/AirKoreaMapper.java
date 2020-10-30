package ym.batch.job.airkorea.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ym.batch.job.airkorea.item.AirData;

import java.util.HashMap;
import java.util.List;

@Repository
public class AirKoreaMapper {

    private final SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.repository.AirKoreaMapper.";

    public AirKoreaMapper(@Qualifier("sqlSession") SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<String> selectStationName() {
        return sqlSession.selectList(NAMESPACE+"selectStationName");
    }

    public void insertAirdata(HashMap<String, Object> param){
        sqlSession.insert(NAMESPACE+"insertAirdata", param);
    }
}
