package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import com.tianqiauto.textile.weaving.repository.ChengpinrukuRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
@Transactional
public class ChengpinrukudengjiServer {

    @Autowired
    private ChengpinrukuRepository chengpinrukuRepository;

    public Page<Chengpin_RuKu> findAll(Chengpin_RuKu chengpinRuKu, Pageable pageable) {
        Specification<Chengpin_RuKu> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil mu = new ModelUtil(chengpinRuKu,root);
            //成品等级
            Param chengpindengji = mu.initParam("chengpindengji.id");
            if(!chengpindengji.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(chengpindengji.getPath(),chengpindengji.getValue()));
            }
            //合约号
            Param heyuehao = mu.initParam("heyuehao.id");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(), heyuehao.getValue()));
            }
            //成品来源
            Param laiyuan = mu.initParam("laiyuan.id");
            if(!laiyuan.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(laiyuan.getPath(), laiyuan.getValue()));
            }
            Param shenqing = mu.initParam("chengpinRuKuShenqing.id");
            mu.addPred(criteriaBuilder.isNull(shenqing.getPath()));
            return  criteriaBuilder.and(mu.getPred());
        };
        return chengpinrukuRepository.findAll(specification,pageable);
    }

    @Autowired
    private UserRepository userRepository;

    public Chengpin_RuKu save(Chengpin_RuKu chengpinRuKu) {
        chengpinRuKu.setCangkuquerenshijian(new Date());
        chengpinRuKu.setCangkuquerenren(userRepository.findAllById(chengpinRuKu.getCangkuquerenren().getId()));
        chengpinRuKu = chengpinrukuRepository.save(chengpinRuKu);
        return chengpinRuKu;
    }

    public void deleteById(Long id) {
        chengpinrukuRepository.deleteById(id);
    }

    public void update(Chengpin_RuKu chengpinRuKu) {
        chengpinRuKu.setCangkuquerenren(userRepository.findAllById(chengpinRuKu.getCangkuquerenren().getId()));
        Chengpin_RuKu chengpinRuKuDB = chengpinrukuRepository.findById(chengpinRuKu.getId()).get();
        MyCopyProperties.copyProperties(chengpinRuKu,chengpinRuKuDB, Arrays.asList("heyuehao","laiyuan","changdu","cangkuquerenren","beizhu","chengpindengji"));
        chengpinrukuRepository.save(chengpinRuKuDB);
    }
}
