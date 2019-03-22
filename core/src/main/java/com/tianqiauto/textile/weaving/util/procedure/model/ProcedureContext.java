package com.tianqiauto.textile.weaving.util.procedure.model;


import java.util.List;

public class ProcedureContext {

	private List<ProcedureParam> params;

	private List<Object> datas;

	private List<Object> columns;
	
	private List<Object> otherDatas;

 
	public List<ProcedureParam> getParams() {
		return params;
	}

	public void setParams(List<ProcedureParam> params) {
		this.params = params;
	}

	public List<Object> getDatas() {
		return datas;
	}

	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}


	public List<Object> getColumns() {
		return columns;
	}

	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}


	public List<Object> getOtherDatas() {
		return otherDatas;
	}

	public void setOtherDatas(List<Object> otherDatas) {
		this.otherDatas = otherDatas;
	}

	@Override
	public String toString() {
		return "ProcedureContext [params=" + params + ", datas=" + datas
				+ ", columns=" + columns + ", otherDatas=" + otherDatas + "]";
	}

}
