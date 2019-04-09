package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SheBeiParamLeiBieRepository extends JpaRepository<Param_LeiBie,Long>,JpaSpecificationExecutor<Param_LeiBie> {

    List<Param_LeiBie> findAllByGongxuAndJixing(Gongxu gongxu, Gongxu jixing);

    @Modifying
    @Transactional
    @Query(value = "update sys_param_leibie set gongxu_id=?1,jixing_id=?2,name=?3,xuhao=?4 where id=?5",nativeQuery = true)
    void updParamLeiBie(Long gxid,Long jxid,String name, Integer xuhao, Long id);

    boolean existsByGongxuAndJixingAndName(Gongxu gongxu,Gongxu jixing,String name);

}
