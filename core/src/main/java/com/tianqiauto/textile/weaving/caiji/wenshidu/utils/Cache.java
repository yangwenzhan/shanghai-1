package com.tianqiauto.textile.weaving.caiji.wenshidu.utils;

import com.tianqiauto.textile.weaving.model.sys.WenShiDu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/5/11 9:48
 */
public class Cache {

    public static List<WenShiDu> wenShiDus;
    static{
        if(null == wenShiDus){
            wenShiDus= new ArrayList<>();
        }
    }

}
