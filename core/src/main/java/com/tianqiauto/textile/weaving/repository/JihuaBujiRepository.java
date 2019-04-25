package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.JiHua_BuJi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface JihuaBujiRepository extends JpaRepository<JiHua_BuJi,Long> ,JpaSpecificationExecutor<JiHua_BuJi> {

}
