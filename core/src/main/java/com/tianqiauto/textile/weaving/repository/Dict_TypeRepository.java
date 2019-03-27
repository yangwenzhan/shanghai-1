package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName Dict_TypeRepository
 * @Author xingxiaoshuai
 * @Date 2019-03-16 17:53
 * @Version 1.0
 **/
public interface Dict_TypeRepository extends JpaRepository<Dict_Type,Long> {

    List<Dict_Type> findAllByCode(String code);

}
