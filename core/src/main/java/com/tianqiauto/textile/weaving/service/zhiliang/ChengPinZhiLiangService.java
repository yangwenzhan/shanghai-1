package com.tianqiauto.textile.weaving.service.zhiliang;

import com.tianqiauto.textile.weaving.model.sys.ChengPin_ZhiLiang;
import com.tianqiauto.textile.weaving.repository.ChengPinZhiLiangRepository;
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
 * @ClassName ChengPinZhiLiangService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/22 15:17
 * @Version 1.0
 **/
@Service
public class ChengPinZhiLiangService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ChengPinZhiLiangRepository chengPinZhiLiangRepository;


    //查询，根据日期查询成品入库的合约号
    public List<Map<String,Object>> getHeYueHao(String ksrq, String jsrq){
        String sql = "";
        if(StringUtils.isEmpty(ksrq) || StringUtils.isEmpty(jsrq)){
            sql = "select distinct b.id,b.name from sys_chengpin_ruku a join sys_heyuehao b on a.heyuehao_id=b.id " +
                    "order by b.name desc";
            return jdbcTemplate.queryForList(sql);
        }else{
            sql = "select distinct b.id,b.name from sys_chengpin_ruku a join sys_heyuehao b on a.heyuehao_id=b.id " +
                    "where cangkuquerenshijian between ? and ? " +
                    "order by b.name desc";
            return jdbcTemplate.queryForList(sql,ksrq,jsrq);
        }
    }

    public Specification getSpecification(ChengPin_ZhiLiang chengPin_zhiLiang){
        ModelUtil mu = new ModelUtil(chengPin_zhiLiang);
        return (Specification<ChengPin_ZhiLiang>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!mu.paramIsEmpty("heyuehao.id")) {
                predicates.add(criteriaBuilder.equal(root.get("heyuehao").get("id"),chengPin_zhiLiang.getHeyuehao().getId()));
            }
            if(!mu.paramIsEmpty("jitaihao.id")){
                predicates.add(criteriaBuilder.equal(root.get("jitaihao").get("id"),chengPin_zhiLiang.getJitaihao().getId()));
            }
            if(!StringUtils.isEmpty(chengPin_zhiLiang.getKaishiriqi()) || !StringUtils.isEmpty(chengPin_zhiLiang.getJieshuriqi())){
                predicates.add(criteriaBuilder.between(root.get("riqi"),chengPin_zhiLiang.getKaishiriqi(),chengPin_zhiLiang.getJieshuriqi()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }

    //查询成品质量
    public Page<ChengPin_ZhiLiang> findAll(ChengPin_ZhiLiang chengPin_zhiLiang, Pageable pageable){
        Page<ChengPin_ZhiLiang> chengPin_zhiLiangs = chengPinZhiLiangRepository.findAll(getSpecification(chengPin_zhiLiang),pageable);
        return chengPin_zhiLiangs;
    }

    //批量新增，根据日期查询当日新入库的数据
    public List<Map<String,Object>> findRuKu(String riqi){
        String sql = "select DISTINCT CONVERT(varchar(100), a.cangkuquerenshijian, 23) riqi,b.id as hyh_id,b.name as heyuehao,c.pibuguige " +
                "from sys_chengpin_ruku a join sys_heyuehao b on a.heyuehao_id=b.id " +
                "join sys_order c on b.order_id=c.id " +
                "where CONVERT(varchar(100), a.cangkuquerenshijian, 23)=? " +
                "order by riqi,heyuehao desc ";

        return jdbcTemplate.queryForList(sql,riqi);
    }

    //批量新增，根据日期，合约号，获取机台
    public List<Map<String,Object>> findJiTaiHao(String riqi,String banci_id,String hyh_id){
        String sql = "select distinct b.id,b.jitaihao " +
                "from sys_shift_buji a join base_shebei b on a.shebei_id=b.id " +
                "where CONVERT(varchar(100), riqi, 23)=? and heyuehao_id=? and banci_id=?";
        return jdbcTemplate.queryForList(sql,riqi,hyh_id,banci_id);
    }

    //批量新增
    public void batchSave(Iterable<ChengPin_ZhiLiang> chengPin_zhiLiangs){
        chengPinZhiLiangRepository.saveAll(chengPin_zhiLiangs);
    }

    //删除
    public void delete(ChengPin_ZhiLiang chengPin_zhiLiang){
        chengPinZhiLiangRepository.delete(chengPin_zhiLiang);
    }

    //单条新增/修改
    public void save(ChengPin_ZhiLiang chengPin_zhiLiang){
        chengPinZhiLiangRepository.save(chengPin_zhiLiang);
    }


}
