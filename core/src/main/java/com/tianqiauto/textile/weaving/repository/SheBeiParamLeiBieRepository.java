package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SheBeiParamLeiBieRepository extends JpaRepository<Param_LeiBie,Long> {

    List<Param_LeiBie> findAllByGongxuAndJixing(Gongxu gongxu, Gongxu jixing);

    @Modifying
    @Transactional
    @Query(value = "update sys_param_leibie set name=?1,xuhao=?2 where id=?3",nativeQuery = true)
    void updParamLeiBie(String name, Integer xuhao, Long id);

    boolean existsByName(String name);

}
