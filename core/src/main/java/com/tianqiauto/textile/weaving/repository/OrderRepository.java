package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> ,JpaSpecificationExecutor<Order> {

    List<Order> findAllByDingdanhao(String dingdanhao);

    List<Order> findAllByDingdanhaoAndDeleted(String dingdanhao,Integer deleted);

}
