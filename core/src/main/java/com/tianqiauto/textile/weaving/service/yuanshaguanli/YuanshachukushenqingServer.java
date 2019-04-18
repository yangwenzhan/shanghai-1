package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.ModelUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/12 8:25
 */
@Service
@Transactional
public class YuanshachukushenqingServer {

    @Autowired
    private YuanShaChuKuShenQingRepository yuanShaChuKuShenQingRepository;

    public Page<YuanSha_ChuKu_Shenqing> findAll(YuanSha_ChuKu_Shenqing yuanSha_chuKu_shenqing, Pageable pageable) {
        ModelUtil mu = new ModelUtil(yuanSha_chuKu_shenqing);
        Specification<YuanSha_ChuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //开始日期和结束日期
            if(!StringUtils.isEmpty(yuanSha_chuKu_shenqing.getKaishiriqi()) || !StringUtils.isEmpty(yuanSha_chuKu_shenqing.getJieshuriqi())) {
                andPredicates.add(criteriaBuilder.between(root.get("createTime"), yuanSha_chuKu_shenqing.getKaishiriqi(),yuanSha_chuKu_shenqing.getJieshuriqi()));
            }
            //批号
            if(!mu.paramIsEmpty("yuanSha.pihao")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pihao"), "%" + yuanSha_chuKu_shenqing.getYuanSha().getPihao() + "%"));
            }
            //品名
            if(!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pinming"), "%" + yuanSha_chuKu_shenqing.getYuanSha().getPinming()+ "%"));
            }
            //支数
            if(!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("zhishu"), yuanSha_chuKu_shenqing.getYuanSha().getZhishu()));
            }
            //色别
            if(!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("sebie"), yuanSha_chuKu_shenqing.getYuanSha().getSebie()));
            }
            //供应商
            if(!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("gongyingshang").get("id"),yuanSha_chuKu_shenqing.getYuanSha().getGongyingshang().getId()));
            }
            //来源
            if(!mu.paramIsEmpty("chukuleixing.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("chukuleixing").get("id"),yuanSha_chuKu_shenqing.getChukuleixing().getId()));
            }
            //状态
            if(!mu.paramIsEmpty("status.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("status").get("id"),yuanSha_chuKu_shenqing.getStatus().getId()));
            }
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaChuKuShenQingRepository.findAll(specification,pageable);
    }

}
