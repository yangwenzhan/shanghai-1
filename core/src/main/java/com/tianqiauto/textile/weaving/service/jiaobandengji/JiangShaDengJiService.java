package com.tianqiauto.textile.weaving.service.jiaobandengji;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_JiangSha;
import com.tianqiauto.textile.weaving.repository.ShiftJiangShaRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName JiangShaDengJiService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 8:38
 * @Version 1.0
 **/
@Service
@Transactional
public class JiangShaDengJiService {

    @Autowired
    private ShiftJiangShaRepository shiftJiangShaRepository;

    @Autowired
    private UserRepository userRepository;

    public Specification getSpecification(Shift_JiangSha shift_jiangSha){
        return (Specification<Shift_JiangSha>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(shift_jiangSha.getRiqi())) {
                predicates.add(criteriaBuilder.equal(root.get("riqi"),shift_jiangSha.getRiqi()));
            }
            if(!StringUtils.isEmpty(shift_jiangSha.getBanci())){
                predicates.add(criteriaBuilder.equal(root.get("banci").get("id"),shift_jiangSha.getBanci().getId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("shifouwancheng"),shift_jiangSha.getShifouwancheng()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    //查询浆纱登记
    public Page<Shift_JiangSha> findAll(Shift_JiangSha shift_jiangSha, Pageable pageable){
        Page<Shift_JiangSha> shift_jiangShas = shiftJiangShaRepository.findAll(getSpecification(shift_jiangSha),pageable);
        return shift_jiangShas;
    }

    //新增或修改
    public Shift_JiangSha save(Shift_JiangSha shift_jiangSha){
        Set<User> userSet = new HashSet<>();
        for(User user : shift_jiangSha.getUsers()){
            User userInDB = userRepository.getOne(user.getId());
            userSet.add(userInDB);
        }
        if(!StringUtils.isEmpty(shift_jiangSha.getId())){
            Shift_JiangSha db_shift_jiangsha = shiftJiangShaRepository.getOne(shift_jiangSha.getId());
            db_shift_jiangsha.setUsers(null);
            shiftJiangShaRepository.save(db_shift_jiangsha);
            MyCopyProperties.copyProperties(shift_jiangSha,db_shift_jiangsha, Arrays.asList("id","riqi","banci","jitaihao","heyuehao","changdu","shifouwancheng","beizhu"));
            db_shift_jiangsha.setUsers(userSet);
            return shiftJiangShaRepository.save(db_shift_jiangsha);
        }else{
            shift_jiangSha.setUsers(userSet);
            return shiftJiangShaRepository.save(shift_jiangSha);
        }
    }

    //删除
    public void delete(Shift_JiangSha shift_jiangSha){
        Shift_JiangSha db_shift_jiangsha = shiftJiangShaRepository.getOne(shift_jiangSha.getId());
        db_shift_jiangsha.setUsers(null);
        shiftJiangShaRepository.save(db_shift_jiangsha);
        shiftJiangShaRepository.delete(db_shift_jiangsha);
    }



}
