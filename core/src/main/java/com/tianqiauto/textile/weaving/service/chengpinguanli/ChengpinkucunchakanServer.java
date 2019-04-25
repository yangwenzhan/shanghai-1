package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.repository.ChengpincurrentRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
@Transactional
public class ChengpinkucunchakanServer {

    @Autowired
    private ChengpincurrentRepository chengpincurrentRepository;

    public Page<Chengpin_Current> findAll(Chengpin_Current chengpin_current, Pageable pageable) {
        Specification<Chengpin_Current> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil<Chengpin_Current> mu = new ModelUtil<>(chengpin_current,root);
            //订单号
            Param dingdanhao = mu.initParam("heyuehao.order.dingdanhao");
            if(!dingdanhao.isEmpty()) {
                mu.addPred(criteriaBuilder.like(dingdanhao.getPath(), "%" +dingdanhao.getValue() + "%"));
            }
            //合约号
            Param heyuehao = mu.initParam("heyuehao.name");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.like(heyuehao.getPath(), "%" +heyuehao.getValue() + "%"));
            }
            //客户编号描述
            Param kehubianhaomiaoshu = mu.initParam("heyuehao.kehubianhaomiaoshu");
            if(!kehubianhaomiaoshu.isEmpty()) {
                mu.addPred(criteriaBuilder.like(kehubianhaomiaoshu.getPath(),"%" +kehubianhaomiaoshu.getValue() + "%"));
            }
//            mu.addPred(criteriaBuilder.gt(root.get("changdu"),0));
            return criteriaBuilder.and(mu.getPred());
        };
        return chengpincurrentRepository.findAll(specification,pageable);
    }
}
