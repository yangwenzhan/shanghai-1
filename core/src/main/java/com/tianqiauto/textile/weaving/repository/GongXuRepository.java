package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Gongxu;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GongXuRepository extends JpaRepository<Gongxu,Long> {

    @Query(value = "select * from base_gongxu where parent_id is null order by sort",nativeQuery = true)
    List<Gongxu> findAllGX();

    @Query(value = "select * from base_gongxu where parent_id is not null and parent_id=isnull(?1,parent_id)",nativeQuery = true)
    List<Gongxu> findAllByParentGongxu(Long gongxu);

}
