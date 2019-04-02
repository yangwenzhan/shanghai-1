package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import com.tianqiauto.textile.weaving.repository.PanCunYueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PanCunYueService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/1 18:59
 * @Version 1.0
 **/
@Service
public class PanCunYueService {

    @Autowired
    private PanCunYueRepository panCunYueRepository;

    public Page<PanCunYue> findAll(PanCunYue panCunYue, Pageable pageable){

        Specification<PanCunYue> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            if(!StringUtils.isEmpty(panCunYue.getNian())) {
                predicates.add(criteriaBuilder.equal(root.get("nian"),panCunYue.getNian()));
            }
            if(!StringUtils.isEmpty(panCunYue.getYue())) {
                predicates.add(criteriaBuilder.equal(root.get("yue"),panCunYue.getYue()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return panCunYueRepository.findAll(specification,pageable);
    }

}
