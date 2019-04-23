package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.JiangSha_ZhiLiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JiangShaZhiLiangRepository extends JpaRepository<JiangSha_ZhiLiang,Long>,JpaSpecificationExecutor<JiangSha_ZhiLiang> {
}
