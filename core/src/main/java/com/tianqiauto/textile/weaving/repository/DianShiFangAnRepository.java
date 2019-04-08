package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.TV_DianShiFangAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DianShiFangAnRepository extends JpaRepository<TV_DianShiFangAn,Long>,JpaSpecificationExecutor<TV_DianShiFangAn> {
}
