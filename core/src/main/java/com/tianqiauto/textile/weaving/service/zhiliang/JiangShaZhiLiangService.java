package com.tianqiauto.textile.weaving.service.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.JiangSha_ZhiLiang;
import com.tianqiauto.textile.weaving.repository.JiangShaZhiLiangRepository;
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
 * @ClassName JiangShaZhiLiangService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/22 9:38
 * @Version 1.0
 **/
@Service
public class JiangShaZhiLiangService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JiangShaZhiLiangRepository jiangShaZhiLiangRepository;


    public Specification getSpecification(String heyuehao){
        return (Specification<JiangSha_ZhiLiang>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(heyuehao)) {
                predicates.add(criteriaBuilder.equal(root.get("heyuehao"),heyuehao));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    //查询浆纱质量数据
    public Page<JiangSha_ZhiLiang> findAll(String heyuehao, Pageable pageable){
        Page<JiangSha_ZhiLiang> jiangSha_zhiLiangs = jiangShaZhiLiangRepository.findAll(getSpecification(heyuehao),pageable);
        return jiangSha_zhiLiangs;
    }

    //根据合约号查询没有浆纱质量的缸号
    public List<Map<String,Object>> findGangHao(String heyuehao_id){
        String sql = "select a.id,b.ganghao from sys_heyuehao a join sys_gongyi_gang b on a.gongyi_id=b.gongyi_id where a.id=? " +
                "except " +
                "select heyuehao_id,ganghao from sys_jiangsha_zhiliang where heyuehao_id=?";
        return jdbcTemplate.queryForList(sql,heyuehao_id,heyuehao_id);
    }

    //修改浆纱质量
    public void updJiangShaZhiLiang(JiangSha_ZhiLiang jiangSha_zhiLiang){
        jiangShaZhiLiangRepository.save(jiangSha_zhiLiang);
    }



}
