package com.tianqiauto.textile.weaving.repository.dao;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.repository.ChengpincurrentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author bjw
 * @Date 2019/4/19 9:05
 */
@Repository
public class ChengpinCurrentDao {

    @Autowired
    private ChengpincurrentRepository chengpincurrentRepository;

     public void addChengpin(Heyuehao heyuehao, Double changdu){
        Chengpin_Current chengpinCurrentDB = chengpincurrentRepository.findByHeyuehao(heyuehao);
        if(null == chengpinCurrentDB){
            chengpinCurrentDB = new Chengpin_Current();
            chengpinCurrentDB.setHeyuehao(heyuehao);
            chengpinCurrentDB.setChangdu(changdu);
        }else{
            Double currentChangdu = chengpinCurrentDB.getChangdu();
            chengpinCurrentDB.setChangdu(currentChangdu+changdu);
        }
        chengpincurrentRepository.save(chengpinCurrentDB);

    }

    public void deleteChengpin(Heyuehao heyuehao,Double changdu){
        Chengpin_Current chengpinCurrentDB = chengpincurrentRepository.findByHeyuehao(heyuehao);
        Double currentChangdu = chengpinCurrentDB.getChangdu();
        chengpinCurrentDB.setChangdu(currentChangdu-changdu);
        chengpincurrentRepository.save(chengpinCurrentDB);
    }

}
