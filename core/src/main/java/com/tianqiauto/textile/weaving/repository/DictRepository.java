package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DictRepository extends JpaRepository<Dict,Long>, JpaSpecificationExecutor<Dict> {

    @Modifying
    @Transactional
    @Query(value = "update base_dict set value=?1,sort=?2 where id=?3",nativeQuery = true)
    void updateDict(String value, Integer sort, Long id);

}
