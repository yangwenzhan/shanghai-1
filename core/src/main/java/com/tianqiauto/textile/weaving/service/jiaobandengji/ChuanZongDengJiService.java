package com.tianqiauto.textile.weaving.service.jiaobandengji;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_ChuanZong;
import com.tianqiauto.textile.weaving.repository.ShiftChuanZongRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName ChuanZongDengJiService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 10:30
 * @Version 1.0
 **/
@Service
public class ChuanZongDengJiService {

    @Autowired
    private ShiftChuanZongRepository shiftChuanZongRepository;

    @Autowired
    private UserRepository userRepository;

    public Specification getSpecification(Shift_ChuanZong shift_chuanZong){
        return (Specification<Shift_ChuanZong>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(shift_chuanZong.getRiqi())) {
                predicates.add(criteriaBuilder.equal(root.get("riqi"),shift_chuanZong.getRiqi()));
            }
            if(!StringUtils.isEmpty(shift_chuanZong.getBanci())){
                predicates.add(criteriaBuilder.equal(root.get("banci").get("id"),shift_chuanZong.getBanci().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("shifouwancheng"),shift_chuanZong.getShifouwancheng()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    //查询穿综登记
    public Page<Shift_ChuanZong> findAll(Shift_ChuanZong shift_chuanZong, Pageable pageable){
        Page<Shift_ChuanZong> shift_chuanZongs = shiftChuanZongRepository.findAll(getSpecification(shift_chuanZong),pageable);
        return shift_chuanZongs;
    }

    //新增或修改
    public Shift_ChuanZong save(Shift_ChuanZong shift_chuanZong){
        Set<User> userSet = new HashSet<>();
        for(User user : shift_chuanZong.getUsers()){
            User userInDB = userRepository.getOne(user.getId());
            userSet.add(userInDB);
        }
        if(!StringUtils.isEmpty(shift_chuanZong.getId())){
            Shift_ChuanZong db_shift_chuanzong = shiftChuanZongRepository.getOne(shift_chuanZong.getId());
            db_shift_chuanzong.setUsers(null);
            shiftChuanZongRepository.save(db_shift_chuanzong);
            MyCopyProperties.copyProperties(shift_chuanZong,db_shift_chuanzong, Arrays.asList("id","riqi","banci","jitaihao","heyuehao","genshu","zhiZhou","shifouwancheng","beizhu"));
            db_shift_chuanzong.setUsers(userSet);
            return shiftChuanZongRepository.save(db_shift_chuanzong);
        }else{
            shift_chuanZong.setUsers(userSet);
            return shiftChuanZongRepository.save(shift_chuanZong);
        }
    }


    //删除
    public void delete(Shift_ChuanZong shift_chuanZong){
        Shift_ChuanZong db_shift_chuanzong = shiftChuanZongRepository.getOne(shift_chuanZong.getId());
        db_shift_chuanzong.setUsers(null);
        shiftChuanZongRepository.save(db_shift_chuanzong);
        shiftChuanZongRepository.delete(db_shift_chuanzong);
    }





}
