package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
@Transactional
public class YuanshachukudengjiServer {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private YuanShaChuKuRepository yuanShaChuKuRepository;

    @Autowired
    private BaseService baseService;

    public Page<YuanSha_ChuKu> findAll(YuanSha_ChuKu yuanSha_chuKu, Pageable pageable) {
        ModelUtil mu = new ModelUtil(yuanSha_chuKu);
        Specification<YuanSha_ChuKu> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //开始日期和结束日期
            if(!mu.paramIsEmpty("kaishiriqi") || !mu.paramIsEmpty("jieshuriqi")) {
                andPredicates.add(criteriaBuilder.between(root.get("createTime"), yuanSha_chuKu.getKaishiriqi(),yuanSha_chuKu.getJieshuriqi()));
            }
            //批号
            if(!mu.paramIsEmpty("yuanSha.pihao")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pihao"), "%" + yuanSha_chuKu.getYuanSha().getPihao() + "%"));
            }
            //品名
            if(!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pinming"), "%" + yuanSha_chuKu.getYuanSha().getPinming()+ "%"));
            }
            //支数
            if(!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("zhishu"), yuanSha_chuKu.getYuanSha().getZhishu()));
            }
            //色别
            if(!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("sebie"), yuanSha_chuKu.getYuanSha().getSebie()));
            }
            //供应商
            if(!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("gongyingshang").get("id"),yuanSha_chuKu.getYuanSha().getGongyingshang().getId()));
            }
            //出库类型
            if(!mu.paramIsEmpty("chukuleixing.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("chukuleixing").get("id"),yuanSha_chuKu.getChukuleixing().getId()));
            }
            andPredicates.add(criteriaBuilder.isNull(root.get("yuanSha_chuKu_shenqing")));
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaChuKuRepository.findAll(specification,pageable);
    }


    public YuanSha_ChuKu save(YuanSha_ChuKu yuanSha_chuKu) {
        YuanSha yuanShaDB = yuanShaRepository.findById(yuanSha_chuKu.getYuanSha().getId()).get();
        Double zongZhong = yuanSha_chuKu.getZongzhong();
        yuanShaDB.setKucunliang(yuanShaDB.getKucunliang()-zongZhong);
        yuanShaRepository.save(yuanShaDB);
        yuanSha_chuKu.setYuanSha(yuanShaDB);
        yuanSha_chuKu = yuanShaChuKuRepository.save(yuanSha_chuKu);
        return yuanSha_chuKu;
    }

    public void deleteById(Long id) {
        YuanSha_ChuKu yuanshachukuDB = yuanShaChuKuRepository.findById(id).get();
        YuanSha yuansha = yuanshachukuDB.getYuanSha();
        yuansha.setKucunliang(yuanshachukuDB.getZongzhong()+yuansha.getKucunliang());
        yuanShaRepository.save(yuansha);
        yuanShaChuKuRepository.deleteById(id);
    }

    public void update(YuanSha_ChuKu yuanSha_chuKu) {
        YuanSha_ChuKu yuanSha_chuKuDB = yuanShaChuKuRepository.findById(yuanSha_chuKu.getId()).get();
        YuanSha yuanShaDB = yuanSha_chuKuDB.getYuanSha();
        yuanShaDB.setKucunliang(yuanShaDB.getKucunliang()+yuanSha_chuKuDB.getZongzhong());
        yuanShaRepository.save(yuanShaDB);
        YuanSha yuanSha = yuanSha_chuKu.getYuanSha();
        yuanSha.setKucunliang(yuanSha.getKucunliang()-yuanSha_chuKu.getZongzhong());
        yuanShaRepository.save(yuanSha);
        MyCopyProperties.copyProperties(yuanSha_chuKu,yuanSha_chuKuDB, Arrays.asList("yuanSha","chukuleixing","baoshu","baozhong","zongzhong","beizhu","heyuehao"));
        yuanShaChuKuRepository.save(yuanSha_chuKu);
    }
}
