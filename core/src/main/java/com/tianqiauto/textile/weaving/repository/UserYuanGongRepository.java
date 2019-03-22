package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.User_YuanGong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserYuanGongRepository extends JpaRepository<User_YuanGong,Long> {
}
