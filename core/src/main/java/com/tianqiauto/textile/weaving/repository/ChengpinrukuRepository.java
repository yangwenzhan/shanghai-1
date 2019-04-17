package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface ChengpinrukuRepository extends JpaRepository<Chengpin_RuKu,Long> ,JpaSpecificationExecutor<Chengpin_RuKu> {

}
