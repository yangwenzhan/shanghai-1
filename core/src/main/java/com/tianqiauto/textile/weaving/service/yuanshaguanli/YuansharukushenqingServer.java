package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/2 16:08
 */
@Service
@Transactional
public class YuansharukushenqingServer {

    @Autowired
    private YuanShaRuKuShenQingRepository yuanShaRuKuShenQingRepository;

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private YuanShaRuKuRepository yuanShaRuKuRepository;

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
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pihao"), "%" + yuanSha_ruKu_shenqing.getYuanSha().getPihao() + "%"));
            }
            //品名
            if(!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pinming"), "%" + yuanSha_ruKu_shenqing.getYuanSha().getPinming()+ "%"));
            }
            //支数
            if(!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("zhishu"), yuanSha_ruKu_shenqing.getYuanSha().getZhishu()));
            }
            //色别
            if(!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("sebie"), yuanSha_ruKu_shenqing.getYuanSha().getSebie()));
            }
            //供应商
            if(!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("gongyingshang").get("id"),yuanSha_ruKu_shenqing.getYuanSha().getGongyingshang().getId()));
            }
            //来源
            if(!mu.paramIsEmpty("laiyuan.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("laiyuan").get("id"),yuanSha_ruKu_shenqing.getLaiyuan().getId()));
            }
            //状态
            if(!mu.paramIsEmpty("status.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("status").get("id"),yuanSha_ruKu_shenqing.getStatus().getId()));
            }
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaRuKuShenQingRepository.findAll(specification,pageable);
    }

    public YuanSha_RuKu_Shenqing save(YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing) {
        Dict status = new Dict();
        status.setId(22L);
        yuanSha_ruKu_shenqing.setStatus(status);
        YuanSha yuanSha = yuanSha_ruKu_shenqing.getYuanSha();
        YuanSha yuanshaDB = yuanShaRepository.findByPihao(yuanSha.getPihao());
        if(null != yuanshaDB){
            //更新原纱库存
            yuanSha.setKucunliang(yuanshaDB.getKucunliang());
            yuanSha.setId(yuanshaDB.getId());
            yuanSha = yuanShaRepository.save(yuanSha);
            YuanSha ys = new YuanSha();
            ys.setId(yuanSha.getId());
            yuanSha_ruKu_shenqing.setYuanSha(ys);
            yuanSha_ruKu_shenqing = yuanShaRuKuShenQingRepository.save(yuanSha_ruKu_shenqing);
        }else{
            yuanSha = yuanShaRepository.save(yuanSha);
            yuanSha_ruKu_shenqing.setYuanSha(yuanSha);
            yuanSha_ruKu_shenqing = yuanShaRuKuShenQingRepository.save(yuanSha_ruKu_shenqing);
        }
        YuanSha_RuKu yuanShaRuKu = new YuanSha_RuKu();
        yuanShaRuKu.setLaiyuan(yuanSha_ruKu_shenqing.getLaiyuan());
        yuanShaRuKu.setYuanSha_ruKu_shenqing(yuanSha_ruKu_shenqing);
        YuanSha ys = new YuanSha();
        ys.setId(yuanSha.getId());
        yuanShaRuKu.setYuanSha(ys);
        yuanShaRuKuRepository.save(yuanShaRuKu);
        return yuanSha_ruKu_shenqing;
    }

    public void deleteById(Long id) {
        yuanShaRuKuShenQingRepository.deleteById(id);
    }

    public YuanSha_RuKu_Shenqing update(YuanSha_RuKu_Shenqing yuanSha_ruKu_shenqing) {
        YuanSha yuansha = yuanSha_ruKu_shenqing.getYuanSha();
        YuanSha yuanShaDB = yuanShaRepository.findByPihao(yuansha.getPihao());
        MyCopyProperties.copyProperties(yuansha,yuanShaDB, Arrays.asList("pinming","gongyingshang","zhishu","sehao","sebie","baozhuangxingshi"));
        yuanShaDB = yuanShaRepository.save(yuanShaDB);
        yuanSha_ruKu_shenqing.setYuanSha(yuanShaDB);
        YuanSha_RuKu_Shenqing yuansharukushenqingDB = yuanShaRuKuShenQingRepository.findById(yuanSha_ruKu_shenqing.getId()).get();
        MyCopyProperties.copyProperties(yuanSha_ruKu_shenqing,yuansharukushenqingDB, Arrays.asList("baoshu","baozhong","zongzhong","beizhu"));
        yuansharukushenqingDB = yuanShaRuKuShenQingRepository.save(yuansharukushenqingDB);
        return yuansharukushenqingDB;
    }
}
