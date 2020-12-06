package ym.batch.job.korona.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ym.batch.job.airkorea.item.ApiCallManageDto;
import ym.batch.job.airkorea.item.ApiCallManageVo;
import ym.batch.job.korona.item.KoronaVo;

import java.util.HashMap;
import java.util.List;

@Repository
public class KoronaMapper {

    private final SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.repository.KoronaMapper.";

    public KoronaMapper(@Qualifier("sqlSession") SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }


    public void insertCoronaRegist(KoronaVo koronaVo){
        sqlSession.insert(NAMESPACE+"insertCoronaRegist", koronaVo);
    }

}
