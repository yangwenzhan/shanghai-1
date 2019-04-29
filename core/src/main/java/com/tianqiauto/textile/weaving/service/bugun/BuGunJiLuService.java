package com.tianqiauto.textile.weaving.service.bugun;

import com.tianqiauto.textile.weaving.model.sys.BuGun;
import com.tianqiauto.textile.weaving.repository.BuGunRepository;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BuGunJiLuService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/27 15:07
 * @Version 1.0
 **/
@Service
public class BuGunJiLuService {

    @Autowired
    private BuGunRepository buGunRepository;

    @Autowired
    private BaseService baseService;

    public Specification getSpecification(BuGun buGun){
        ModelUtil mu = new ModelUtil(buGun);
        return (Specification<BuGun>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!mu.paramIsEmpty("heyuehao.id")) {
                predicates.add(criteriaBuilder.equal(root.get("heyuehao").get("id"),buGun.getHeyuehao().getId()));
            }
            if(!mu.paramIsEmpty("jitaihao.id")){
                predicates.add(criteriaBuilder.equal(root.get("jitaihao").get("id"),buGun.getJitaihao().getId()));
            }
            if(!StringUtils.isEmpty(buGun.getKaishixuhao()) || !StringUtils.isEmpty(buGun.getJieshuxuhao())){
                predicates.add(criteriaBuilder.between(root.get("xuhao"),buGun.getKaishixuhao(),buGun.getJieshuxuhao()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }

    //查询布辊产出记录
    public Page<BuGun> findAll(BuGun buGun, Pageable pageable){
        Page<BuGun> buGuns = buGunRepository.findAll(getSpecification(buGun),pageable);
        return buGuns;
    }

    //布辊追溯  type:1左轴，2右轴
    public Result buGunZhuiSu(String shift_zhou_id,String type){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(shift_zhou_id).addInVarchar(type);
        ProcedureContext pro=baseService.callProcedure("pc_bugun_zhuisu", ppu.getList());
        return Result.ok(pro.getDatas());
    }


}
