package com.tianqiauto.textile.weaving.caiji.wenshidu.service;

import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.tianqiauto.textile.weaving.caiji.wenshidu.utils.Cache;
import com.tianqiauto.textile.weaving.caiji.wenshidu.utils.Dict;
import com.tianqiauto.textile.weaving.caiji.wenshidu.utils.Modbus4jUtils;
import com.tianqiauto.textile.weaving.model.sys.WenShiDu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 1000	当前温度
 * 1001	开机以后最小温度值
 * 1002	开机以后最大温度值
 * 1003	当前湿度值
 * 1004	开机以后最小湿度值
 * 1005	开机以后最大湿度值
 * 1006	温度修正系数
 * 1007	湿度修正系数
 * 1008	温度系数
 * 1009	湿度系数
 * 1010	工作状态
 * 1011	0表示摄氏度1表示华氏度
 *
 * @Author bjw
 * @Date 2019/5/11 9:59
 */
@Slf4j
@Service
public class CaijiService {

    /**
     * 采集
     *
     * @Author bjw
     * @Date 2019/5/11 10:30
     **/
    public void caiji() throws InterruptedException {

        List<WenShiDu> wsdList = Cache.wenShiDus;
        for (WenShiDu wenShiDu : wsdList) {
            switch (wenShiDu.getName()) {
                case Dict.T1:
                    batchRead(1,Modbus4jUtils.getTCPMaster(Dict.IP1, Dict.port),wenShiDu);break;
                case Dict.T2:
                    batchRead(2,Modbus4jUtils.getTCPMaster(Dict.IP1, Dict.port),wenShiDu);break;
                case Dict.T3:
                    batchRead(3,Modbus4jUtils.getTCPMaster(Dict.IP1, Dict.port),wenShiDu);break;
                case Dict.T4:
                    batchRead(4,Modbus4jUtils.getTCPMaster(Dict.IP1, Dict.port),wenShiDu);break;
                case Dict.T5:
                    batchRead(5,Modbus4jUtils.getTCPMaster(Dict.IP1, Dict.port),wenShiDu);break;
                case Dict.T6:
                    batchRead(6,Modbus4jUtils.getTCPMaster(Dict.IP2, Dict.port),wenShiDu);break;
                case Dict.T7:
                    batchRead(7,Modbus4jUtils.getTCPMaster(Dict.IP2, Dict.port),wenShiDu);break;
                case Dict.T8:
                    batchRead(8,Modbus4jUtils.getTCPMaster(Dict.IP2, Dict.port),wenShiDu);break;
                case Dict.T9:
                    batchRead(9,Modbus4jUtils.getTCPMaster(Dict.IP2, Dict.port),wenShiDu);break;
                case Dict.T10:
                    batchRead(10,Modbus4jUtils.getTCPMaster(Dict.IP2, Dict.port),wenShiDu);break;
            }
            Thread.sleep(2000);
        }
    }

    /**
     * 批量读取温湿度
     *
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     * @throws ModbusInitException
     */
    private static void batchRead(int slaveId, ModbusMaster master, WenShiDu wenShiDu){
        BatchRead<Integer> batch = new BatchRead<Integer>();
        batch.addLocator(0, BaseLocator.holdingRegister(slaveId, 1000, DataType.TWO_BYTE_INT_SIGNED));//当前温度
        batch.addLocator(3, BaseLocator.holdingRegister(slaveId, 1003, DataType.TWO_BYTE_INT_SIGNED));//当前湿度值
        batch.setContiguousRequests(false);
        BatchResults<Integer> results = null;
        try {
            results = master.send(batch);
            wenShiDu.setShidu(Double.valueOf(results.getValue(3).toString()));
            wenShiDu.setWendu(Double.valueOf(results.getValue(0).toString()));
            wenShiDu.setLastModifiedDate(new Date());
        } catch (ModbusTransportException e) {
            log.error("采集点["+wenShiDu.getName()+"]异常："+e.getMessage());
        } catch (ErrorResponseException e) {
            log.error("采集点["+wenShiDu.getName()+"]异常："+e.getMessage());
        }
    }


}
