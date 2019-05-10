package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.model.base.Dict;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存 bjw
 * @Date 2019/3/7 11:04
 */
public class Cache {

    //织机主机信息缓存
    public static List<List<PicanolHost>> picanolHost = new ArrayList<>();

    public static Dict tingzhi;
    public static Dict yunxing;

    public static final int ONLINEFLAG_ZAIXIAN = 1; //在线
    public static final int ONLINEFLAG_LIXIAN = 0; //离线

}
