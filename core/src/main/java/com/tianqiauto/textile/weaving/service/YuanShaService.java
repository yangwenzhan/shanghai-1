package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/19 15:45
 */
@Service
public class YuanShaService {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    public Page<YuanSha> findAll(YuanSha yuanSha,Pageable pageable) {
        Specification<YuanSha> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            //品名
            if(!StringUtils.isEmpty(yuanSha.getPinming())) {
                predicates.add(criteriaBuilder.like(root.get("pinming"), "%" + yuanSha.getPinming() + "%"));
            }
            //批号
            if(!StringUtils.isEmpty(yuanSha.getPihao())){
                predicates.add(criteriaBuilder.like(root.get("pihao"), "%" + yuanSha.getPihao()+ "%"));
            }
            //支数
            if(!StringUtils.isEmpty(yuanSha.getZhishu())){
                predicates.add(criteriaBuilder.equal(root.get("zhishu"), yuanSha.getZhishu()));
            }
            //色号
            if(!StringUtils.isEmpty(yuanSha.getSehao())){
                predicates.add(criteriaBuilder.like(root.get("sehao"), "%" + yuanSha.getSehao()+ "%"));
            }
            //色别
            if(!StringUtils.isEmpty(yuanSha.getSebie())){
                predicates.add(criteriaBuilder.like(root.get("sebie"), "%" + yuanSha.getSebie()+ "%"));
            }
            //库存量
            predicates.add(criteriaBuilder.notEqual(root.get("kucunliang"), 0));
            criteriaBuilder.desc(root.get("createTime"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return yuanShaRepository.findAll(specification,pageable);
    }
}
