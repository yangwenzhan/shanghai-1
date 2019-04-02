package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PanCunYueRepository extends JpaRepository<PanCunYue,Long> ,JpaSpecificationExecutor<PanCunYue> {


}
