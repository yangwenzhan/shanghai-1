package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface HeYueHaoRepository extends JpaRepository<Heyuehao,Long>, JpaSpecificationExecutor<Heyuehao> {

    List<Heyuehao> findByOrder(Order order);

    List<Heyuehao> findAllByName(String name);



    List<Heyuehao> findAllByGongYiIsNull();


}
