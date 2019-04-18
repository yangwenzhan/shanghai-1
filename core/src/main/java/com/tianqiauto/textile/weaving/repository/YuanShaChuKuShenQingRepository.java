package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface YuanShaChuKuShenQingRepository extends JpaRepository<YuanSha_ChuKu_Shenqing,Long>  ,JpaSpecificationExecutor<YuanSha_ChuKu_Shenqing> {
    
}
