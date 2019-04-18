package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/2 16:08
 */
@Service
@Transactional
public class YuansharukuquerenServer {

    @Autowired
    private YuanShaRuKuShenQingRepository yuanShaRuKuShenQingRepository;

    @Autowired
    private YuanShaRuKuRepository yuanShaRuKuRepository;

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private UserRepository userRepository;

    public Object findAll(YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing, Pageable pageable) {
        ModelUtil mu = new ModelUtil(yuanSha_ruKu_shenqing);
        Specification<YuanSha_RuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //开始日期和结束日期
            if(!StringUtils.isEmpty(yuanSha_ruKu_shenqing.getKaishiriqi()) || !StringUtils.isEmpty(yuanSha_ruKu_shenqing.getJieshuriqi())) {
                andPredicates.add(criteriaBuilder.between(root.get("createTime"), yuanSha_ruKu_shenqing.getKaishiriqi(),yuanSha_ruKu_shenqing.getJieshuriqi()));
            }
            //批号
            if(!mu.paramIsEmpty("yuanSha.pihao")) {
                andPredicates.add(criteriaBuilder.like(root.join("yuanSha", JoinType.LEFT).get("pihao"), "%" + yuanSha_ruKu_shenqing.getYuanSha().getPihao() + "%"));
            }
            //品名
            if(!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.join("yuanSha", JoinType.LEFT).get("pinming"), "%" + yuanSha_ruKu_shenqing.getYuanSha().getPinming()+ "%"));
            }
            //支数
            if(!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.join("yuanSha", JoinType.LEFT).get("zhishu"), yuanSha_ruKu_shenqing.getYuanSha().getZhishu()));
            }
            //色别
            if(!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.join("yuanSha", JoinType.LEFT).get("sebie"), yuanSha_ruKu_shenqing.getYuanSha().getSebie()));
            }
            //供应商
            if(!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.join("yuanSha", JoinType.LEFT).get("gongyingshang").get("id"),yuanSha_ruKu_shenqing.getYuanSha().getGongyingshang().getId()));
            }
            //来源
            if(!mu.paramIsEmpty("laiyuan.id")) {
                andPredicates.add(criteriaBuilder.equal(root.join("laiyuan", JoinType.LEFT).get("id"),yuanSha_ruKu_shenqing.getLaiyuan().getId()));
            }
            andPredicates.add(criteriaBuilder.equal(root.join("status", JoinType.LEFT).get("value"),"10"));
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaRuKuShenQingRepository.findAll(specification,pageable);
    }

    public YuanSha_RuKu_Shenqing dengji(YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing) {
        //原纱信息，需要更新库存量
        //原纱入库，原纱入库信息要与原纱入库申请信息关联
        YuanSha_RuKu_Shenqing yuansharukushenqingDB = yuanShaRuKuShenQingRepository.findById(yuanSha_ruKu_shenqing.getId()).get();
        YuanSha  yuanshaDB = yuansharukushenqingDB.getYuanSha();
        Double dqkcl = yuanshaDB.getKucunliang() == null ? 0 : yuanshaDB.getKucunliang();
        Double djkcl = yuanSha_ruKu_shenqing.getYuanShaRuKu().getZongzhong();
        yuanshaDB.setKucunliang(dqkcl+djkcl);
        yuanShaRepository.save(yuanshaDB);//修改库存量
        YuanSha_RuKu yuanShaRuKu = yuanSha_ruKu_shenqing.getYuanShaRuKu();
        YuanSha_RuKu yuansharukuDB = yuansharukushenqingDB.getYuanShaRuKu();
        yuansharukuDB.setBaoshu(yuanShaRuKu.getBaoshu());
        yuansharukuDB.setBaozhong(yuanShaRuKu.getBaozhong());
        yuansharukuDB.setZongzhong(yuansharukuDB.getZongzhong());
        yuansharukuDB.setBeizhu(yuanShaRuKu.getBeizhu());
        yuansharukuDB.setLingyongren(userRepository.findById(yuanShaRuKu.getLingyongren().getId()).get());
        yuanShaRuKuRepository.save(yuansharukuDB);
        Dict status = new Dict();
        status.setId(23L);
        yuansharukushenqingDB.setStatus(status);
        yuanShaRuKuShenQingRepository.save(yuansharukushenqingDB);
        return yuansharukushenqingDB;
  }

}
