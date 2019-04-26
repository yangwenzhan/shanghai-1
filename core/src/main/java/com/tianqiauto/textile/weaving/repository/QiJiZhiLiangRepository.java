package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.QiJi_ZhiLiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QiJiZhiLiangRepository extends JpaRepository<QiJi_ZhiLiang,Long>,JpaSpecificationExecutor<QiJi_ZhiLiang> {
}
