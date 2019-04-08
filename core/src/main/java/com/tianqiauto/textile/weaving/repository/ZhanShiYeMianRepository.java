package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.TV_ZhanShiYeMian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ZhanShiYeMianRepository extends JpaRepository<TV_ZhanShiYeMian,Long> {

    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update sys_tv_zhanshiyemian set sort=?1,tingliushichang=?2 where id=?3",nativeQuery = true)
    void updZSYM(Integer sort,Integer tingliushichang,Long id);

}
