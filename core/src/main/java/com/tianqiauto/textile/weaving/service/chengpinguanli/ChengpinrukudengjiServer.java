package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.repository.ChengpincurrentRepository;
import com.tianqiauto.textile.weaving.repository.ChengpinrukuRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.dao.ChengpinCurrentDao;
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
import java.util.Optional;

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

    @Autowired
    private ChengpinCurrentDao chengpinCurrentDao;

    public Chengpin_RuKu save(Chengpin_RuKu chengpinRuKu) {
        chengpinRuKu.setCangkuquerenshijian(new Date());
        chengpinRuKu.setCangkuquerenren(userRepository.findAllById(chengpinRuKu.getCangkuquerenren().getId()));
        chengpinRuKu = chengpinrukuRepository.save(chengpinRuKu);
        //更新事实表
        Heyuehao heyuehao = chengpinRuKu.getHeyuehao();
        chengpinCurrentDao.addChengpin(heyuehao,chengpinRuKu.getChangdu());
        return chengpinRuKu;
    }

    public void deleteById(Long id) {
        Chengpin_RuKu chengpinRuKuDB = chengpinrukuRepository.findById(id).get();
        //更新事实表
        Heyuehao heyuehao = chengpinRuKuDB.getHeyuehao();
        chengpinCurrentDao.deleteChengpin(heyuehao,chengpinRuKuDB.getChangdu());
        chengpinrukuRepository.deleteById(id);
    }

    public void update(Chengpin_RuKu chengpinRuKu) {
        chengpinRuKu.setCangkuquerenren(userRepository.findAllById(chengpinRuKu.getCangkuquerenren().getId()));
        Chengpin_RuKu chengpinRuKuDB = chengpinrukuRepository.findById(chengpinRuKu.getId()).get();
        //更新事实表先减去原来的
        Heyuehao heyuehao = chengpinRuKuDB.getHeyuehao();
        chengpinCurrentDao.deleteChengpin(heyuehao,chengpinRuKuDB.getChangdu());
        MyCopyProperties.copyProperties(chengpinRuKu,chengpinRuKuDB, Arrays.asList("heyuehao","laiyuan","changdu","cangkuquerenren","beizhu","chengpindengji"));
        chengpinRuKuDB = chengpinrukuRepository.save(chengpinRuKuDB);
        //更新事实表
        Heyuehao heyuehaoDB = chengpinRuKuDB.getHeyuehao();
        chengpinCurrentDao.addChengpin(heyuehaoDB,chengpinRuKuDB.getChangdu());
    }
}
