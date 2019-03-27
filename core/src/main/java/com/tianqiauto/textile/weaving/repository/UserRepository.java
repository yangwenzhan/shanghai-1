package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    //修改是否在职字段
    @Modifying
    @Transactional
    @Query(value = "update base_user set shifouzaizhi=?1 where id=?2",nativeQuery = true)
    void updateUserZaiZhi(int zaizhi,Long id);

    //重置密码
    @Modifying
    @Transactional
    @Query(value = "update base_user set password=?1 where id=?2",nativeQuery = true)
    void updateUserPwd(String pwd,Long id);

    boolean existsByUsername(String username);

    User findAllById(Long id);

}
