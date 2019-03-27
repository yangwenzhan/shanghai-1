package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName Dict_TypeRepository
 * @Author xingxiaoshuai
 * @Date 2019-03-16 17:53
 * @Version 1.0
 **/
public interface Dict_TypeRepository extends JpaRepository<Dict_Type,Long>, JpaSpecificationExecutor<Dict_Type> {


     Dict_Type findByCode(String code);


}
