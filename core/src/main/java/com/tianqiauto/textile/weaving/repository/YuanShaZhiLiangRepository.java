package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ZhiLiang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface YuanShaZhiLiangRepository extends JpaRepository<YuanSha_ZhiLiang,Long>,JpaSpecificationExecutor<YuanSha_ZhiLiang> {


}
