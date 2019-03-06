package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.Sys_Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Sys_Log,Long> {

}
