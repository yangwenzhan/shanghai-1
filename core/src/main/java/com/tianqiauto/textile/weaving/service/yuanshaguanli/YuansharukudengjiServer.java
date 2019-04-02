package com.tianqiauto.textile.weaving.service.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuRepository;
import com.tianqiauto.textile.weaving.util.ModelUtil;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
public class YuansharukudengjiServer {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private YuanShaRuKuRepository yuanShaRuKuRepository;

    @Autowired
    private BaseService baseService;

    public Page<YuanSha_RuKu> findAll(YuanSha_RuKu yuanSha_ruKu, Pageable pageable) {

        ModelUtil mu = new ModelUtil(yuanSha_ruKu);

        Specification<YuanSha_RuKu> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = Lists.newArrayList();
            //开始日期和结束日期
            if(!StringUtils.isEmpty(yuanSha_ruKu.getKaishiriqi()) || !StringUtils.isEmpty(yuanSha_ruKu.getJieshuriqi())) {
                andPredicates.add(criteriaBuilder.between(root.get("goururiqi"), yuanSha_ruKu.getKaishiriqi(),yuanSha_ruKu.getJieshuriqi()));
            }
            //批号
            if(!mu.paramIsEmpty("yuanSha.pihao")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pihao"), "%" + yuanSha_ruKu.getYuanSha().getPihao() + "%"));
            }
            //品名
            if(!mu.paramIsEmpty("yuanSha.pinming")) {
                andPredicates.add(criteriaBuilder.like(root.get("yuanSha").get("pinming"), "%" + yuanSha_ruKu.getYuanSha().getPinming()+ "%"));
            }
            //支数
            if(!mu.paramIsEmpty("yuanSha.zhishu")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("zhishu"), yuanSha_ruKu.getYuanSha().getZhishu()));
            }
            //色别
            if(!mu.paramIsEmpty("yuanSha.sebie")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("sebie"), yuanSha_ruKu.getYuanSha().getSebie()));
            }
            //供应商
            if(!mu.paramIsEmpty("yuanSha.gongyingshang.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("yuanSha").get("gongyingshang").get("id"),yuanSha_ruKu.getYuanSha().getGongyingshang().getId()));
            }
            //来源
            if(!mu.paramIsEmpty("laiyuan.id")) {
                andPredicates.add(criteriaBuilder.equal(root.get("laiyuan").get("id"),yuanSha_ruKu.getLaiyuan().getId()));
            }

            Predicate[] array = new Predicate[andPredicates.size()];
            Predicate preAnd = criteriaBuilder.and(andPredicates.toArray(array));
////-----------------------------------------------------------------------------------------------------------
//            List<Predicate> orPredicates = Lists.newArrayList();
//            Predicate sqid = criteriaBuilder.isNull(root.get("yuanSha_ruKu_shenqing").get("id"));
//            Predicate sqzt = criteriaBuilder.equal(root.get("yuanSha_ruKu_shenqing").get("status").get("value"),yuanSha_ruKu.getYuanSha_ruKu_shenqing().getStatus().getValue());
//            orPredicates.add(criteriaBuilder.and(sqid));
//            orPredicates.add(criteriaBuilder.and(sqzt));
////-----------------------------------------------------------------------------------------------------------fixme 排除申请未确认的。
//            Predicate[] array2 = new Predicate[orPredicates.size()];
//            Predicate preOr = criteriaBuilder.or(orPredicates.toArray(array2));
//            return criteriaQuery.where(preAnd,preOr).getRestriction();
            return criteriaQuery.where(preAnd).getRestriction();
        };
        return yuanShaRuKuRepository.findAll(specification,pageable);
    }

    public Object create_pihao() {
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ProcedureContext pro=baseService.callProcedure("pc_create_pihao", ppu.getList());
        return  pro.getDatas();
    }

    public YuanSha_RuKu save(YuanSha_RuKu yuanSha_ruKu) {
        YuanSha yuansha = yuanSha_ruKu.getYuanSha();
        yuansha.setKucunliang(yuanSha_ruKu.getZongzhong());
        YuanSha yuanshaDB = yuanShaRepository.findByPihao(yuansha.getPihao());
        if(null != yuanshaDB){
            //更新原纱库存
            yuanshaDB.setKucunliang(yuanshaDB.getKucunliang()+yuansha.getKucunliang());
            yuanShaRepository.save(yuanshaDB);
            YuanSha ys = new YuanSha();
            ys.setId(yuanshaDB.getId());
            yuanSha_ruKu.setYuanSha(ys);
            yuanSha_ruKu = yuanShaRuKuRepository.save(yuanSha_ruKu);
        }else{
            yuansha = yuanShaRepository.save(yuansha);
            yuanSha_ruKu.setYuanSha(yuansha);
            yuanSha_ruKu = yuanShaRuKuRepository.save(yuanSha_ruKu);
        }
        return yuanSha_ruKu;
    }

    public void deleteById(Long id) {
        yuanShaRuKuRepository.deleteById(id);
    }

    public void update(YuanSha_RuKu yuanSha_ruKu) {
        YuanSha yuanSha = yuanSha_ruKu.getYuanSha();
        yuanShaRepository.save(yuanSha);
        yuanShaRuKuRepository.save(yuanSha_ruKu);
    }
}
