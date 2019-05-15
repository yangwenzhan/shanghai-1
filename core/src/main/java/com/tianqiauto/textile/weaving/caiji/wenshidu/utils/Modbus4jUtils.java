package com.tianqiauto.textile.weaving.caiji.wenshidu.utils;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Modbus4jUtils {
	static ModbusFactory modbusFactory;
	static {
		if (modbusFactory == null) {
			modbusFactory = new ModbusFactory();
		}
	}

	public static ModbusMaster getTCPMaster(String host,int port) {
		IpParameters params = new IpParameters();
		params.setHost(host);
		params.setPort(port);
		ModbusMaster master = modbusFactory.createTcpMaster(params, false);
		master.setTimeout(1000);
		master.setRetries(3);
		try {
			master.init();
		} catch (ModbusInitException e) {
			log.error("modbus init error!ip:"+host+":"+port);
		}
		return master;
	}

	public static Boolean readCoilStatus(int slaveId, int offset,ModbusMaster modbusMaster)
			throws ModbusTransportException, ErrorResponseException  {
		BaseLocator<Boolean> loc = BaseLocator.coilStatus(slaveId, offset);
		Boolean value = modbusMaster.getValue(loc);
		return value;
	}

	public static Boolean readInputStatus(int slaveId, int offset,ModbusMaster modbusMaster)
			throws ModbusTransportException, ErrorResponseException  {
		BaseLocator<Boolean> loc = BaseLocator.inputStatus(slaveId, offset);
		Boolean value = modbusMaster.getValue(loc);
		return value;
	}

	public static Number readHoldingRegister(int slaveId, int offset, int dataType,ModbusMaster modbusMaster)
			throws ModbusTransportException, ErrorResponseException  {
		BaseLocator<Number> loc = BaseLocator.holdingRegister(slaveId, offset, dataType);
		Number value = modbusMaster.getValue(loc);
		return value;
	}

	public static Number readInputRegisters(int slaveId, int offset, int dataType,ModbusMaster modbusMaster)
			throws ModbusTransportException, ErrorResponseException {
		BaseLocator<Number> loc = BaseLocator.inputRegister(slaveId, offset, dataType);
		Number value = modbusMaster.getValue(loc);
		return value;
	}

}