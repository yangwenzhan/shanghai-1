package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    List<Permission> findAllByParent_idOrderByPermissionName(Long parent_id);

    @Query(value = "update base_permission set permission_name=?1 where id=?2",nativeQuery = true)
    @Modifying
    void updatePermission(String name,Long id);

    @Query(value = "delete from base_role_permission where permission_id=?1",nativeQuery = true)
    @Modifying
    void deleteRoleByPermission(Long id);

    @Query(value = "delete from base_role_permission where permission_id=?1 and role_id=?2",nativeQuery = true)
    @Modifying
    void deleteRole(Long permission_id,Long role_id);


}
