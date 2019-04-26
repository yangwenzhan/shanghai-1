package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.base.PB_Current;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaiBanCurrentRepository  extends JpaRepository<PB_Current,Long>,JpaSpecificationExecutor<PB_Current> {

    PB_Current findByGongxu(Gongxu gongxu);
}
