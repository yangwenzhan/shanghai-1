package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.sys.CheWei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CheWeiRepository extends JpaRepository<CheWei,Long>,JpaSpecificationExecutor<CheWei> {

    CheWei findCheWeiByJitaihaoAndLunban(SheBei jitaihao, Dict lunban);


}
