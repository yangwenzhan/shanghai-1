package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.ModelUtil;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/12 8:25
 */
@Service
@Transactional
public class YuanshachukuquerenServer {

    @Autowired
    private YuanShaChuKuShenQingRepository yuanShaChuKuShenQingRepository;

    public Page<YuanSha_ChuKu_Shenqing> findAll(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing, Pageable pageable) {
        ModelUtil mu = new ModelUtil(yuanSha_chuKu_shenqing);
        Specification<YuanSha_ChuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //批号
            if (!mu.paramIsEmpty("yuanSha.pihao")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pihao"), "%" + yuanSha_chuKu_shenqing.getYuanSha().getPihao() + "%"));
            }
            //品名
            if (!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pinming"), "%" + yuanSha_chuKu_shenqing.getYuanSha().getPinming() + "%"));
            }
            //支数
            if (!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("zhishu"), yuanSha_chuKu_shenqing.getYuanSha().getZhishu()));
            }
            //色别
            if (!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("sebie"), yuanSha_chuKu_shenqing.getYuanSha().getSebie()));
            }
            //供应商
            if (!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("gongyingshang").get("id"), yuanSha_chuKu_shenqing.getYuanSha().getGongyingshang().getId()));
            }
            //来源
            if (!mu.paramIsEmpty("chukuleixing.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("chukuleixing").get("id"), yuanSha_chuKu_shenqing.getChukuleixing().getId()));
            }
            //状态
            andPredicates.add(criteriaBuilder.equal(root.get("status").get("value"), "10"));
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaChuKuShenQingRepository.findAll(specification, pageable);
    }
    @Autowired
    private YuanShaChuKuRepository yuanShaChuKuRepository;

    @Autowired
    private UserRepository userRepository;
    public YuanSha_ChuKu_Shenqing update(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing) {
        YuanSha_ChuKu_Shenqing yuanShaChuKuShenqingDB = yuanShaChuKuShenQingRepository.findById(yuanSha_chuKu_shenqing.getId()).get();
        YuanSha_ChuKu yuanSha_chuKuBD = yuanShaChuKuShenqingDB.getYuanShaChuKu();
        YuanSha_ChuKu yuanSha_chuKu = yuanSha_chuKu_shenqing.getYuanShaChuKu();
        MyCopyProperties.copyProperties(yuanSha_chuKu,yuanSha_chuKuBD, Arrays.asList("baoshu","baozhong","zongzhong","beizhu"));
        yuanSha_chuKuBD.setLingyongshijian(new Date());
        yuanSha_chuKuBD.setLingyongren(userRepository.findById(yuanSha_chuKu.getLingyongren().getId()).get());
        yuanShaChuKuRepository.save(yuanSha_chuKuBD);
        yuanShaChuKuShenqingDB.setStatus(findByTypeAndVlaue("ys_chukushenqingzhuangtai","20"));
        return yuanShaChuKuShenqingDB;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Dict findByTypeAndVlaue(String typeCode, String value){
        String sql = "SELECT dict.id, dict.name, dict.[value] FROM base_dict dict\n" +
                "LEFT JOIN base_dict_type type on type.id = dict.type_id \n" +
                "where type.code = ? AND dict.[value] = ? ";
        List<Dict> dicts = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Dict>(Dict.class),typeCode,value);
        if (dicts.isEmpty()){
            return null;
        }else{
            return dicts.get(0);
        }
    }


    public YuanSha_ChuKu_Shenqing findById(Long id) {
        return yuanShaChuKuShenQingRepository.findById(id).get();
    }
}
