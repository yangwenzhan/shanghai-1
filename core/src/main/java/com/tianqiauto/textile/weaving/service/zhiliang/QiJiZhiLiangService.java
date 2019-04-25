package com.tianqiauto.textile.weaving.service.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.QiJi_ZhiLiang;
import com.tianqiauto.textile.weaving.repository.QiJiZhiLiangRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName QiJiZhiLiangService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/23 15:54
 * @Version 1.0
 **/
@Service
public class QiJiZhiLiangService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QiJiZhiLiangRepository qiJiZhiLiangRepository;


    public Specification getSpecification(QiJi_ZhiLiang qiJi_zhiLiang){
        ModelUtil mu = new ModelUtil(qiJi_zhiLiang);
        return (Specification<QiJi_ZhiLiang>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!mu.paramIsEmpty("heyuehao.id")) {
                predicates.add(criteriaBuilder.equal(root.get("heyuehao").get("id"),qiJi_zhiLiang.getHeyuehao().getId()));
            }
            if(!mu.paramIsEmpty("jitaihao.id")){
                predicates.add(criteriaBuilder.equal(root.get("jitaihao").get("id"),qiJi_zhiLiang.getJitaihao().getId()));
            }
            if(!StringUtils.isEmpty(qiJi_zhiLiang.getKaishiriqi()) || !StringUtils.isEmpty(qiJi_zhiLiang.getJieshuriqi())){
                predicates.add(criteriaBuilder.between(root.get("riqi"),qiJi_zhiLiang.getKaishiriqi(),qiJi_zhiLiang.getJieshuriqi()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }

    //查询起机质量
    public Page<QiJi_ZhiLiang> findAll(QiJi_ZhiLiang qiJi_zhiLiang, Pageable pageable){
        Page<QiJi_ZhiLiang> qiJi_zhiLiangs = qiJiZhiLiangRepository.findAll(getSpecification(qiJi_zhiLiang),pageable);
        return qiJi_zhiLiangs;
    }

    //删除
    public void delete(QiJi_ZhiLiang qiJi_zhiLiang){
        qiJiZhiLiangRepository.delete(qiJi_zhiLiang);
    }

    //修改/新增
    public void save(QiJi_ZhiLiang qiJi_zhiLiang){
        qiJiZhiLiangRepository.save(qiJi_zhiLiang);
    }

    //根据日期班次机台查询对应合约号
    public List<Map<String,Object>> findHeYueHao(String riqi,Long banci_id,Long jitaihao){
        String sql = "select distinct heyuehao_id as id,b.name,c.pibuguige  " +
                "from sys_shift_buji a join sys_heyuehao b on a.heyuehao_id=b.id " +
                "left join sys_order c on b.order_id=c.id " +
                "where shebei_id=? and convert(varchar(100),riqi,23)=? and banci_id=?";
        return jdbcTemplate.queryForList(sql,jitaihao,riqi,banci_id);
    }

}
