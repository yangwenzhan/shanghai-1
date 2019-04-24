package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.ChengpinrukuRepository;
import com.tianqiauto.textile.weaving.repository.ChengpinrukushenqingRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.dao.ChengpinCurrentDao;
import com.tianqiauto.textile.weaving.repository.dao.DictDao;
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
public class ChengpinrukuquerenServer {

    @Autowired
    private ChengpinrukuRepository chengpinrukuRepository;

    @Autowired
    private ChengpinrukushenqingRepository chengpinrukushenqingRepository;


    public Page<Chengpin_RuKu_Shenqing> findAll(Chengpin_RuKu_Shenqing chengpinRuKuShenqing, Pageable pageable) {

        Specification<Chengpin_RuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil mu = new ModelUtil(chengpinRuKuShenqing,root);
            //开始日期和结束日期
            if(!mu.paramIsEmpty("kaishiriqi") || !mu.paramIsEmpty("jieshuriqi")) {
                mu.addPred(criteriaBuilder.between(root.get("createTime"), chengpinRuKuShenqing.getKaishiriqi(),chengpinRuKuShenqing.getJieshuriqi()));
            }
            //成品等级
            Param chengpindengji = mu.initParam("chengpinruku.chengpindengji.id");
            if(!chengpindengji.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(chengpindengji.getPath(),chengpindengji.getValue()));
            }
            //合约号
            Param heyuehao = mu.initParam("chengpinruku.heyuehao.id");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(),heyuehao.getValue()));
            }
            //申请状态
            Param status = mu.initParam("status.value");
            mu.addPred(criteriaBuilder.equal(status.getPath(),"10"));
            return criteriaBuilder.and(mu.getPred());
        };
        return chengpinrukushenqingRepository.findAll(specification,pageable);
    }

    @Autowired
    private DictDao dictDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChengpinCurrentDao chengpinCurrentDao;

    public void update(Chengpin_RuKu_Shenqing chengpinRuKuShenqing) {
        Chengpin_RuKu chengpinRuKu = chengpinRuKuShenqing.getChengpinruku();
        Chengpin_RuKu chengpinRuKuDB = chengpinrukuRepository.findById(chengpinRuKu.getId()).get();
        chengpinRuKu.setCangkuquerenren(userRepository.findAllById(chengpinRuKu.getCangkuquerenren().getId()));
        MyCopyProperties.copyProperties(chengpinRuKu,chengpinRuKuDB, Arrays.asList("changdu","cangkuquerenren","beizhu"));
        chengpinRuKuDB.setCangkuquerenshijian(new Date());
        Chengpin_RuKu_Shenqing chengpinRuKuShenqingDB = chengpinrukushenqingRepository.findById(chengpinRuKuShenqing.getId()).get();
        chengpinRuKuShenqingDB.setChengpinruku(chengpinRuKuDB);
        Dict dict = dictDao.findByTypecodeAndValue("cp_rukushenqingzhuangtai","20");
        chengpinRuKuShenqingDB.setStatus(dict);
        chengpinrukushenqingRepository.save(chengpinRuKuShenqingDB);
        //更新当前库存量
        chengpinCurrentDao.addChengpin(chengpinRuKuDB.getHeyuehao(),chengpinRuKuDB.getChangdu());
    }
}
