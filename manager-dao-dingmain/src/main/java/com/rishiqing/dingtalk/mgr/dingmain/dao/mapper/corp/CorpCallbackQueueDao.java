package com.rishiqing.dingtalk.mgr.dingmain.dao.mapper.corp;

import com.rishiqing.dingtalk.api.model.domain.corp.CorpCallbackQueueDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Wallace on 17-1-3.
 */
@Repository("corpCallbackQueueDao")
public interface CorpCallbackQueueDao {

    /**
     * 创建一个企业通讯录信息获取失败的对象
     *
     * @param corpCallbackQueueDO
     */
    void saveCorpCallbackQueueDO(CorpCallbackQueueDO corpCallbackQueueDO);

    void updateCorpCallbackQueueDO(CorpCallbackQueueDO corpCallbackQueueDO);

    List<CorpCallbackQueueDO> getCorpCallbackQueueList(
            @Param("offset") Integer offset, @Param("limit") Integer limit);

    void deleteById(@Param("id") Long id);

    List<CorpCallbackQueueDO> listCorpCallbackQueueByStatusOrderByTimestampWithLimit(
            @Param("status") Long status,
            @Param("limit") Long limit);
}
