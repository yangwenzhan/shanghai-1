package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface YuanShaChuKuRepository extends JpaRepository<YuanSha_ChuKu,Long> ,JpaSpecificationExecutor<YuanSha_ChuKu> {

}
