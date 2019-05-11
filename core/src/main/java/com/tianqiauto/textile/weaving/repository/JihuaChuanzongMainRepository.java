package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong_Main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface JihuaChuanzongMainRepository extends JpaRepository<JiHua_ChuanZong_Main,Long> ,JpaSpecificationExecutor<JiHua_ChuanZong_Main> {

}
