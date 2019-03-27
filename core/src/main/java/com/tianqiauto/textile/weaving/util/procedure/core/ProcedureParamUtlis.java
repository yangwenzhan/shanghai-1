package com.tianqiauto.textile.weaving.util.procedure.core;

import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureParam;
import org.springframework.util.StringUtils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *  利用JdbcTemplet调用存储过程时用来封装传入参数的工具类。
 *
 * @author BJW
 * @version 2018-11-19
 */
public class ProcedureParamUtlis {

    /**
     * 存放ProcedureParam 的容器
     */
    private List<ProcedureParam> pm;

    /**
     * List<ProcedureParam>参数位置指针
     */
    private int index;

    /**
     *  获取容器的大小
     * @return 容器长度
     */
    public int size(){
        return pm.size();
    }

    /**
     * 无参构造器，创建初始化ProcedureParamUtlis对象。
     */
    public ProcedureParamUtlis(){
        this.pm = new ArrayList<>();
        this.index = 1;
    }

    /**
     * 批量添加输入参数，<em> 注意！输入参数是有序的，一定要对应存储过程输入参数顺序。 </em>
     * @param parms 输入参数集
     * @return 当前对象
     */
    public  ProcedureParamUtlis batchInAdd(String... parms){
        for (String param:parms) {
            addIn(param,Types.VARCHAR);
        }
        return this;
    }

    /**
     * 批量添加输出参数，<em> 注意！输入参数是有序的，一定要对应存储过程输出参数顺序。 </em>
     * @param parms 输出参数集
     * @return 当前对象
     */
    public ProcedureParamUtlis batchOutAdd(String... parms){
        for (String param:parms) {
            addOut(param,Types.VARCHAR);
        }
        return this;
    }

    /**
     *  添加单个输入参数 <em> 注意！输入参数的顺序。 </em>
     * @param param 参数值
     * @param type 参数类型（可以用java.sql.Types定义的常量）
     * @return 当前对象
     */
    public ProcedureParamUtlis addIn(String param, int type){
        ProcedureParam param1 = new ProcedureParam(index, StringUtils.isEmpty(param)?null:param, type, IN);
        pm.add(param1);
        index++;
        return this;
    }

    /**
     *  添加输入参数类型是 <em> INTEGER </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addInInteger(String param){
        return addIn(param, Types.INTEGER);
    }

    /**
     * 添加输入参数类型是 <em> DECIMAL </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addInDecimal(String param){
        return addIn(param, Types.DECIMAL);
    }

    /**
     *  添加输入参数类型是 <em> DOUBLE </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addInDouble(String param){
        return addIn(param, Types.DOUBLE);
    }

    /**
     * 添加输入参数类型是 <em> VARCHAR </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addInVarchar(String param){
        return addIn(param, Types.VARCHAR);
    }

    /**
     *  添加单个输出参数 <em> 注意！输出参数的顺序。 </em>
     * @param param 参数
     * @param type 参数类型（可以用java.sql.Types定义的常量）
     * @return 当前对象
     */
    public ProcedureParamUtlis addOut(String param, int type){
        ProcedureParam param1 = new ProcedureParam(index, StringUtils.isEmpty(param)?null:param, type, OUT);
        pm.add(param1);
        index++;
        return this;
    }

    /**
     *  添加输出参数类型是 <em> INTEGER </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addOutInteger(String param){
        return addOut(param, Types.INTEGER);
    }

    /**
     *  添加输出参数类型是 <em> DECIMAL </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addOutDecimal(String param){
        return addOut(param, Types.DECIMAL);
    }

    /**
     *  添加输出参数类型是 <em> DOUBLE </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addOutDouble(String param){
        return addOut(param, Types.DOUBLE);
    }

    /**
     *  添加输出参数类型是 <em> VARCHAR </em>
     * @param param 参数
     * @return 当前对象
     */
    public ProcedureParamUtlis addOutVarchar(String param){
        return addOut(param, Types.VARCHAR);
    }

    /**
     *  单个添加输出参数，默认值是null，默认参数INTEGER
     * @return 当前对象
     */
    public ProcedureParamUtlis addOut(){
        ProcedureParam param1 = new ProcedureParam(index, null, Types.INTEGER, OUT);
        pm.add(param1);
        index++;
        return this;
    }

    /**
     * 获取当前参数List集合
     * @return 参数集合
     */
    public List<ProcedureParam> getList(){
        return  pm;
    }


    //------------------------------------------------------------------------------------------------------------------
    /**
     * 参数类型--输入参数IN
     */
    private final String IN = "IN";
    /**
     * 参数类型--输出参数OUT
     */
    private final String OUT = "OUT";

}
