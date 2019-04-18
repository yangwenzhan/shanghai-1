package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.ChengpinrukuRepository;
import com.tianqiauto.textile.weaving.repository.ChengpinrukushenqingRepository;
import com.tianqiauto.textile.weaving.repository.DictRepository;
import com.tianqiauto.textile.weaving.repository.dao.DictDao;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
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
public class ChengpinrukushenqingServer {

    @Autowired
    private ChengpinrukuRepository chengpinrukuRepository;

    @Autowired
    private ChengpinrukushenqingRepository chengpinrukushenqingRepository;


    public Page<Chengpin_RuKu_Shenqing> findAll(Chengpin_RuKu_Shenqing chengpinRuKuShenqing, Pageable pageable) {

        Specification<Chengpin_RuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil mu = new ModelUtil(chengpinRuKuShenqing,root);
            List<Predicate> andPredicates = Lists.newArrayList();
            //开始日期和结束日期
            if(!mu.paramIsEmpty("kaishiriqi") || !mu.paramIsEmpty("jieshuriqi")) {
                andPredicates.add(criteriaBuilder.between(root.get("createTime"), chengpinRuKuShenqing.getKaishiriqi(),chengpinRuKuShenqing.getJieshuriqi()));
            }
            //成品等级
            if(!mu.paramIsEmpty("chengpinruku.chengpindengji.id")) {
                andPredicates.add(criteriaBuilder.equal(mu.getRoot("chengpinruku.chengpindengji.id"),mu.getValue("chengpinruku.chengpindengji.id")));
            }
            //合约号
            if(!mu.paramIsEmpty("chengpinruku.heyuehao.id")) {
                andPredicates.add(criteriaBuilder.equal(mu.getRoot("chengpinruku.heyuehao.id"), mu.getValue("chengpinruku.heyuehao.id")));
            }
            //申请状态
            if(!mu.paramIsEmpty("status.id")) {
                andPredicates.add(criteriaBuilder.equal(mu.getRoot("status.id"), mu.getValue("status.id")));
            }
            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return chengpinrukushenqingRepository.findAll(specification,pageable);
    }

    @Autowired
    private DictDao dictDao;

    public Chengpin_RuKu_Shenqing save(Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
        Dict dict = dictDao.findByTypecodeAndValue("cp_rukushenqingzhuangtai","10");
        chengpinRuKuShenqing.setStatus(dict);
        chengpinRuKuShenqing = chengpinrukushenqingRepository.save(chengpinRuKuShenqing);
        Chengpin_RuKu chengpinRuKu = chengpinRuKuShenqing.getChengpinruku();
        chengpinRuKu.setChengpinRuKuShenqing(chengpinRuKuShenqing);
        chengpinrukuRepository.save(chengpinRuKu);
        return chengpinRuKuShenqing;
    }

    public void deleteById(Long id) {
        chengpinrukushenqingRepository.deleteById(id);
    }

    public void update(Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
        Chengpin_RuKu chengpinRuKu = chengpinRuKuShenqing.getChengpinruku();
        Chengpin_RuKu chengpinRuKuDB = chengpinrukuRepository.findById(chengpinRuKu.getId()).get();
        MyCopyProperties.copyProperties(chengpinRuKu,chengpinRuKuDB, Arrays.asList("heyuehao","laiyuan","chengpindengji"));
        //chengpinrukuRepository.save(chengpinRuKuDB);
        Chengpin_RuKu_Shenqing chengpinRuKuShenqingDB = chengpinrukushenqingRepository.findById(chengpinRuKuShenqing.getId()).get();
        MyCopyProperties.copyProperties(chengpinRuKuShenqing,chengpinRuKuShenqingDB, Arrays.asList("changdu","beizhu"));
        chengpinRuKuShenqingDB.setChengpinruku(chengpinRuKuDB);
        chengpinrukushenqingRepository.save(chengpinRuKuShenqingDB);
    }
}
