package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.History_Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryParamRepository extends JpaRepository<History_Param,Long> , JpaSpecificationExecutor<History_Param> {

    @Query(value = "select * from sys_history_param where param_id=?1 and jitai_id=?2 order by create_time",nativeQuery = true)
    List<History_Param> findByParam(Long param_id,Long jth_id);

}
