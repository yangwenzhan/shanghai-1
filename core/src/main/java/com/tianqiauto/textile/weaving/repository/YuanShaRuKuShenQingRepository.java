package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 14:42
 */
public interface YuanShaRuKuShenQingRepository extends JpaRepository<YuanSha_RuKu_Shenqing,Long>,JpaSpecificationExecutor<YuanSha_RuKu_Shenqing> {



}
