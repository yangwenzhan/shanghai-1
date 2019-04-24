package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author bjw
 * @Date 2019/3/19 15:50
 */
public interface ChengpincurrentRepository extends JpaRepository<Chengpin_Current,Long> ,JpaSpecificationExecutor<Chengpin_Current> {

    Chengpin_Current findByHeyuehao(Heyuehao heyuehao);

}
