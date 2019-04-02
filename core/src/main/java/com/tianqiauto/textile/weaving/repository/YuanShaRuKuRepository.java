package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 14:42
 */
public interface YuanShaRuKuRepository extends JpaRepository<YuanSha_RuKu,Long>,JpaSpecificationExecutor<YuanSha_RuKu> {
    
}
