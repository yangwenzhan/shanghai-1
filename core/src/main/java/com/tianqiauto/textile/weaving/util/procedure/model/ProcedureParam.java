package com.tianqiauto.textile.weaving.util.procedure.model;


public class ProcedureParam {

	/**
	 * 
	 */
	private int index;//1
	
	private String value;//abc
	
	private Integer paramType;//
	
	private String inOut;

	public int getIndex() {
		return index;
	}
	public ProcedureParam(){
		
	}
	
	public ProcedureParam(int index, String value, Integer paramType,
			String inOut) {
		super();
		this.index = index;
		this.value = value;
		this.paramType = paramType;
		this.inOut = inOut;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	@Override
	public String toString() {
		return "ProcedureParam [index=" + index + ", value=" + value
				+ ", paramType=" + paramType + "]";
	}
	
}
