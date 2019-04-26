package com.tianqiauto.textile.weaving.service.jiaobandengji;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_Zhengjing;
import com.tianqiauto.textile.weaving.repository.ShiftZhengJingRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName ZhengJingDengJiService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/24 9:01
 * @Version 1.0
 **/
@Service
@Transactional
public class ZhengJingDengJiService {

    @Autowired
    private ShiftZhengJingRepository shiftZhengJingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Specification getSpecification(Shift_Zhengjing shift_zhengjing){
        return (Specification<Shift_Zhengjing>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(shift_zhengjing.getRiqi())) {
                predicates.add(criteriaBuilder.equal(root.get("riqi"),shift_zhengjing.getRiqi()));
            }
            if(!StringUtils.isEmpty(shift_zhengjing.getBanci())){
                predicates.add(criteriaBuilder.equal(root.get("banci").get("id"),shift_zhengjing.getBanci().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("shifouwancheng"),shift_zhengjing.getShifouwancheng()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    //查询整经登记
    public Page<Shift_Zhengjing> findAll(Shift_Zhengjing shift_zhengjing, Pageable pageable){
        Page<Shift_Zhengjing> shift_zhengjings = shiftZhengJingRepository.findAll(getSpecification(shift_zhengjing),pageable);
        return shift_zhengjings;
    }

    //新增或修改
    public Shift_Zhengjing save(Shift_Zhengjing shift_zhengjing){
        Set<User> userSet = new HashSet<>();
        for(User user : shift_zhengjing.getUsers()){
            User userInDB = userRepository.getOne(user.getId());
            userSet.add(userInDB);
        }
        if(!StringUtils.isEmpty(shift_zhengjing.getId())){
            Shift_Zhengjing db_shiftZhengjing = shiftZhengJingRepository.getOne(shift_zhengjing.getId());
            db_shiftZhengjing.setUsers(null);
            shiftZhengJingRepository.save(db_shiftZhengjing);
            MyCopyProperties.copyProperties(shift_zhengjing, db_shiftZhengjing, Arrays.asList("id","riqi","banci","jitaihao","heyuehao","flag","changdu","shifouwancheng","tiaoshu","beizhu"));
            db_shiftZhengjing.setUsers(userSet);
            return shiftZhengJingRepository.save(db_shiftZhengjing);
        }else{
            shift_zhengjing.setUsers(userSet);
            return shiftZhengJingRepository.save(shift_zhengjing);
        }
    }

    //删除
    public void delete(Shift_Zhengjing shift_zhengjing){
        Shift_Zhengjing db_shiftZhengjing = shiftZhengJingRepository.getOne(shift_zhengjing.getId());
        db_shiftZhengjing.setUsers(null);
        shiftZhengJingRepository.save(db_shiftZhengjing);
        shiftZhengJingRepository.delete(db_shiftZhengjing);
    }

    //查询订单未完成的合约号
    public List<Map<String,Object>> findHeYueHao(){
        String sql = "select distinct a.id,name,pibuguige from sys_heyuehao a join ( " +
                " select id,pibuguige from sys_order where status_id in ( " +
                " select id from base_dict where type_id=( " +
                " select id from base_dict_type where code='dingdanzhuangtai' " +
                " ) and name !='已完成' " +
                " ) " +
                " ) as b on a.order_id=b.id ";
        return jdbcTemplate.queryForList(sql);
    }


}
