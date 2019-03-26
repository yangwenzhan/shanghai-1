package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "update base_role set name=?1,beizhu=?2 where id=?3",nativeQuery = true)
    @Modifying
    void updateRole(String name,String beizhu,Long id);


    boolean existsByName(String name);

}
