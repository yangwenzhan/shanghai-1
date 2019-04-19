package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/11 15:41
 */
@Service
public class YuanshakucunchakanServer {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    public Page<YuanSha> findAll(YuanSha yuanSha, Pageable pageable) {
        ModelUtil mu = new ModelUtil(yuanSha);
        Specification<YuanSha> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //批号
            if (!mu.paramIsEmpty("pihao")) {
                andPredicates.add(criteriaBuilder.like(root.get("pihao"), "%" + yuanSha.getPihao() + "%"));
            }
            //品名
            if (!mu.paramIsEmpty("pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("pinming"), "%" + yuanSha.getPinming() + "%"));
            }
            //支数
            if (!mu.paramIsEmpty("zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("zhishu"), yuanSha.getZhishu()));
            }
            //色别
            if (!mu.paramIsEmpty("sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("sebie"), yuanSha.getSebie()));
            }
            //供应商
            if (!mu.paramIsEmpty("gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("gongyingshang").get("id"), yuanSha.getGongyingshang().getId()));
            }
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaRepository.findAll(specification, pageable);
    }

}
