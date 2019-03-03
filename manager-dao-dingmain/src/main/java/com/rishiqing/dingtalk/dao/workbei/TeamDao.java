package com.rishiqing.dingtalk.dao.workbei;

import com.rishiqing.dingtalk.dao.model.workbei.TeamDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Yin Jian
 * @create: 2019-03-03 23:29
 */
@Repository
public interface TeamDao {
    List<TeamDO> getTeamActiveUserPercent(@Param("teamIdList") List<Long> teamIdList);
}




