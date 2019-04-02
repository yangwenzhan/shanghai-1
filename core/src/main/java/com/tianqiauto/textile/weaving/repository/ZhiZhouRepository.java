package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Beam_ZhiZhou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ZhiZhouRepository extends JpaRepository<Beam_ZhiZhou,Long>,JpaSpecificationExecutor<Beam_ZhiZhou> {

    boolean existsByZhouhao(String zhihao);
}
