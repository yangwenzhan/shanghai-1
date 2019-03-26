package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Beam_JingZhou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JingZhouRepository extends JpaRepository<Beam_JingZhou,Long> {

    @Query(value = "update sys_beam_jingzhou set zhoukuan=?1,beizhu=?2 where id=?3",nativeQuery = true)
    @Modifying
    void updateJingZhou(Integer zhoukuan, String beizhu, Long id);

}
