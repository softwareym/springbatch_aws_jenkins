package ym.batch.job.common.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ym.batch.job.common.item.UserDto;
import ym.batch.job.common.item.UserVo;

import java.util.List;

@Repository
public class CommonMapper {
    private final SqlSessionTemplate sqlSession;
    private final static String NAMESPACE = "ym.batch.job.repository.CommonMapper.";

    public CommonMapper(@Qualifier("sqlSession") SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void insertUser(UserVo userVo){
        sqlSession.insert(NAMESPACE+"insertUser", userVo);
    }

    public List<UserDto> selectUsersByStationName(UserVo userVo) {
        return sqlSession.selectList(NAMESPACE+"selectUsersByStationName",userVo);
    }

}
