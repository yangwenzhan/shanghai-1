package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeYueHaoRepository extends JpaRepository<Heyuehao,Long> {

    List<Heyuehao> findByOrder(Order order);

}
