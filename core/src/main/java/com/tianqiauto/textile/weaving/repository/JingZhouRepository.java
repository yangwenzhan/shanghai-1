package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Beam_JingZhou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JingZhouRepository extends JpaRepository<Beam_JingZhou,Long> {

    @Modifying
    @Transactional
    @Query(value = "update sys_beam_jingzhou set zhoukuan=?1,beizhu=?2 where id=?3",nativeQuery = true)
    void updateJingZhou(Double zhoukuan, String beizhu, Long id);

    boolean existsByZhouhao(String zhouhao);

}
