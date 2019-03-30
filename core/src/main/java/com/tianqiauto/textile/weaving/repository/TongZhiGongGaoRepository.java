package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.TV_TongZhiGongGao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TongZhiGongGaoRepository extends JpaRepository<TV_TongZhiGongGao,Long> {

    @Transactional
    @Modifying
    @Query(value = "update sys_tv_tongzhigonggao set neirong=? where id=?",nativeQuery = true)
    void updTongZhiGongGao(String neirong,Long id);

    boolean existsByName(String name);

}
