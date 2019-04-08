package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.sys.Param_LeiBie;
import com.tianqiauto.textile.weaving.repository.SheBeiParamLeiBieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SheBeiParamLeiBieService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/1 9:48
 * @Version 1.0
 **/
@Service
public class SheBeiParamLeiBieService {

    @Autowired
    private SheBeiParamLeiBieRepository sheBeiParamLeiBieRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Page<Param_LeiBie> findAll(String gxid,String jxid,Pageable pageable){
        Specification<Param_LeiBie> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(gxid)){
                predicates.add(criteriaBuilder.equal(root.get("gongxu").get("id"),gxid));
            }
            if(!StringUtils.isEmpty(jxid)){
                predicates.add(criteriaBuilder.equal(root.get("jixing").get("id"),jxid));
            }
            criteriaBuilder.asc(root.get("xuhao"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return sheBeiParamLeiBieRepository.findAll(specification,pageable);
    }

    //判断类别是否存在
    public boolean existsByGongxuAndJixingAndName(Long gxid,Long jxid,String name,Long id){
        String sql = "select count(*) from sys_param_leibie where gongxu_id=? and jixing_id=? and name=? and id != ?";
        int num = jdbcTemplate.queryForObject(sql,Integer.class,gxid,jxid,name,id);
        if(num>0)
            return false;
        return true;
    }


}
