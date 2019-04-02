package com.tianqiauto.textile.weaving.service.common;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.Dict_Type;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CommonService
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/25 15:14
 * @Version 1.0
 **/
@Service
public class CommonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Dict_TypeRepository dict_typeRepository;

    public List<Map<String,Object>> findUserZu(){
        String sql = "select distinct zu from base_user_yuangong";
        return jdbcTemplate.queryForList(sql);
    }

    @Transactional
    public Map<String,Set<Dict>> DictFindAllByCodes(Set<String> codes) {
        Map<String,Set<Dict>> map = new HashMap<>();
        for (String code:codes){
            Dict_Type dist_type = dict_typeRepository.findByCode(code);
            map.put(code,dist_type.getDicts());
        }
        return map;
    }
}
