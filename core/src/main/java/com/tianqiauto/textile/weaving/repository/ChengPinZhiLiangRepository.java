package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.ChengPin_ZhiLiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChengPinZhiLiangRepository extends JpaRepository<ChengPin_ZhiLiang,Long>,JpaSpecificationExecutor<ChengPin_ZhiLiang> {
}
