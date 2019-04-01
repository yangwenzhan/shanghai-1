package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Param;
import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SheBeiParamRepository extends JpaRepository<Param,Long>,JpaSpecificationExecutor<Param> {

    boolean existsByNameAndLeiBie(String name,Param_LeiBie param);

}
