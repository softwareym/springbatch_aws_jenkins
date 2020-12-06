package ym.batch.job.Corona.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ym.batch.job.Corona.item.CoronaVo;

@Repository
public class CoronaMapper {

    private final SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.repository.CoronaMapper.";

    public CoronaMapper(@Qualifier("sqlSession") SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }


    public void insertCoronaRegist(CoronaVo koronaVo){
        sqlSession.insert(NAMESPACE+"insertCoronaRegist", koronaVo);
    }

}
