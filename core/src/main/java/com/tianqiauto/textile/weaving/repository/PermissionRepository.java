package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    List<Permission> findAllByParentId(Long parent_id);

    @Modifying
    @Transactional
    @Query(value = "update base_permission set permission_name=?1 where id=?2",nativeQuery = true)
    void updatePermission(String name,Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from base_role_permission where permission_id=?1",nativeQuery = true)
    void deleteRoleByPermission(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from base_role_permission where permission_id=?1 and role_id=?2",nativeQuery = true)
    void deleteRole(Long permission_id,Long role_id);

    boolean existsByParentIdAndPermissionCodeAndPermissionName(Long parentId,String permissionCode,String permissionName);

}
