package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.History_WenShiDu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryWenShiDuRepository extends JpaRepository<History_WenShiDu,Long>{

    @Query(value = "select * from sys_history_wenshidu where wenshidu_id=?1 order by shijian asc",nativeQuery = true)
    List<History_WenShiDu> findAllHistory(Long id);
}
