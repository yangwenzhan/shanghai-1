package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.sys.CheWeiCurrent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CheWeiCurrentRepository extends JpaRepository<CheWeiCurrent,Long>,JpaSpecificationExecutor<CheWeiCurrent> {

    CheWeiCurrent findCheWeiCurrentByJitaihao(SheBei jitaihao);

}
