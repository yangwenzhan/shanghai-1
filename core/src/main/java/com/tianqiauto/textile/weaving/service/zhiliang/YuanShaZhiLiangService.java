package com.tianqiauto.textile.weaving.service.zhiliang;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ZhiLiang;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaZhiLiangRepository;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName YuanShaZhiLiangService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/20 14:53
 * @Version 1.0
 **/
@Service
public class YuanShaZhiLiangService {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private YuanShaZhiLiangRepository yuanShaZhiLiangRepository;


    public Specification getSpecification(String pihao, String pinming){
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();
            if(!StringUtils.isEmpty(pinming)) {
                predicates.add(criteriaBuilder.like(root.get("pinming"),"%"+pinming+"%"));
            }
            if(!StringUtils.isEmpty(pihao)) {
                predicates.add(criteriaBuilder.like(root.get("pihao"),"%"+pihao+"%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<YuanSha> findAll(String pihao, String pinming, Pageable pageable){
        Page<YuanSha> yuanShas = yuanShaRepository.findAll(getSpecification(pihao, pinming),pageable);
        return yuanShas;
    }

    public void updYuanShaZhiLiang(YuanSha yuanSha){

        //获取数据库中的原纱
        YuanSha yuanShaDB = yuanShaRepository.getOne(yuanSha.getId());
        //将前台传的原纱yuanSha属性id 复制给数据库中的原纱yuanShaDB
        MyCopyProperties.copyProperties(yuanSha,yuanShaDB, Arrays.asList("id"));
        //获取前台传的原纱质量信息
        YuanSha_ZhiLiang yuanSha_zhiLiang = yuanSha.getYuanSha_zhiLiang();
        //判断原纱质量是否为空，若为空，说明没有录入过该质量信息
        if(StringUtils.isEmpty(yuanSha_zhiLiang.getId())){
            //保存原纱质量
            YuanSha_ZhiLiang new_yuanSha_ZhiLiang = yuanShaZhiLiangRepository.save(yuanSha_zhiLiang);
            yuanShaDB.setYuanSha_zhiLiang(new_yuanSha_ZhiLiang);
            yuanShaRepository.save(yuanShaDB);
        }else{
            yuanShaZhiLiangRepository.save(yuanSha_zhiLiang);
        }
    }

}
