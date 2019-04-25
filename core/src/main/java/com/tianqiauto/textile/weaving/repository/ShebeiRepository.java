package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.SheBei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface ShebeiRepository extends JpaRepository<SheBei,Long> ,JpaSpecificationExecutor<SheBei> {

}
