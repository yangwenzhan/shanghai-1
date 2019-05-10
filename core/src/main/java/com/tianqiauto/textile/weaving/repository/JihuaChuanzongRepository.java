package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface JihuaChuanzongRepository extends JpaRepository<JiHua_ChuanZong,Long> ,JpaSpecificationExecutor<JiHua_ChuanZong> {

}
