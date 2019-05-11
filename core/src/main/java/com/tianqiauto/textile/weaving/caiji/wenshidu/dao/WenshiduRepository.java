package com.tianqiauto.textile.weaving.caiji.wenshidu.dao;

import com.tianqiauto.textile.weaving.model.sys.WenShiDu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/9 15:34
 */
public interface WenshiduRepository extends JpaRepository<WenShiDu,Long> ,JpaSpecificationExecutor<WenShiDu> {

}
