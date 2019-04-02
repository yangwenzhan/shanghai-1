package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Beam_ZhiZhou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ZhiZhouRepository extends JpaRepository<Beam_ZhiZhou,Long>{

    @Transactional
    @Modifying
    @Query(value = "update sys_beam_zhizhou set jixing_id=?1,zhoukuan=?2,beizhu=?3 where id=?4",nativeQuery = true)
    void updateZhiZhou(Long jixing_id, Double zhoukuan, String beizhu, Long id);

}
