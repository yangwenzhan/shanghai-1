package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface ChengpinchukushenqingRepository extends JpaRepository<Chengpin_ChuKu_Shenqing,Long> ,JpaSpecificationExecutor<Chengpin_ChuKu_Shenqing> {

}
