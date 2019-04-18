package com.tianqiauto.textile.weaving.service.dingdanguanli;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.Order;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import com.tianqiauto.textile.weaving.repository.HeYueHaoRepository;
import com.tianqiauto.textile.weaving.util.JPASql.Container;
import com.tianqiauto.textile.weaving.util.JPASql.DynamicUpdateSQL;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/3/19 9:26
 */
@Service
@Transactional
public class HeyuehaoService {
    @Autowired
    private HeYueHaoRepository heYueHaoRepository;


    @Autowired
    private Dict_TypeRepository dict_typeRepository;

    public List<Heyuehao> findByOrderid(Order order) {
        return heYueHaoRepository.findByOrder(order);
    }

    public Heyuehao findByid(Long id) {
        return heYueHaoRepository.findById(id).get();
    }

    public Heyuehao save(Heyuehao heyuehao) {
        return heYueHaoRepository.save(heyuehao);
    }

    public void deleteById(Long id) {
        heYueHaoRepository.deleteById(id);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int update(Heyuehao heyuehao) {
        Container RUSql = new DynamicUpdateSQL<>(heyuehao).getUpdateSql();
        return jdbcTemplate.update(RUSql.getSql(),RUSql.getParam());
    }

    @Autowired
    private BaseService baseService;

    /**
     * 获取生成的合约号
     * @Author bjw
     * @Date 2019/3/26 21:01
     **/
    public Object create_heyuehao(String order_id, String flag) {
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.batchInAdd(order_id,flag);
        ProcedureContext pro=baseService.callProcedure("pc_create_heyuehao", ppu.getList());
        return  pro.getDatas();
    }

    public Object findAllPage(Heyuehao heyuehao, Pageable pageable) {

        Specification<Heyuehao> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            //合约号
            if(!StringUtils.isEmpty(heyuehao.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + heyuehao.getName() + "%"));
            }
            //订单描述
            if(!StringUtils.isEmpty(heyuehao.getKehubianhaomiaoshu())) {
                predicates.add(criteriaBuilder.like(root.get("kehubianhaomiaoshu"), "%" + heyuehao.getKehubianhaomiaoshu() + "%"));
            }
            criteriaBuilder.desc(root.get("createTime"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return heYueHaoRepository.findAll(specification,pageable);
    }

    public List<Heyuehao> findByName(String name) {
        return heYueHaoRepository.findAllByName(name);
    }

    public List<Heyuehao> findAll(){
        return heYueHaoRepository.findAll();
    }
}
