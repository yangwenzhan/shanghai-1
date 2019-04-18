package com.tianqiauto.textile.weaving.repository.dao;

import com.tianqiauto.textile.weaving.model.base.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DictDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Dict findByTypecodeAndValue(String typeCode,String value){
        String sql = "SELECT dict.id, dict.name, dict.[value] FROM base_dict dict\n" +
                "LEFT JOIN base_dict_type type on type.id = dict.type_id \n" +
                "where type.code = ? AND dict.[value] = ? ";
        List<Dict> dicts = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Dict>(Dict.class),typeCode,value);
        if (dicts.isEmpty()){
            return null;
        }else{
            return dicts.get(0);
        }
    }

}
