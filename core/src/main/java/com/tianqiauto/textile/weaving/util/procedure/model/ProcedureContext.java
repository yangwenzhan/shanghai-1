package com.tianqiauto.textile.weaving.util.procedure.model;


import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class ProcedureContext {

	private List<ProcedureParam> params;

	private JSONArray datas;

	private JSONArray columns;
	
	private List<JSONArray> otherDatas;
 
	public List<ProcedureParam> getParams() {
		return params;
	}

	public void setParams(List<ProcedureParam> params) {
		this.params = params;
	}

	public JSONArray getDatas() {
		return datas;
	}

	public void setDatas(JSONArray datas) {
		this.datas = datas;
	}

	public JSONArray getColumns() {
		return columns;
	}

	public void setColumns(JSONArray columns) {
		this.columns = columns;
	}

	public List<JSONArray> getOtherDatas() {
		return otherDatas;
	}

	public void setOtherDatas(List<JSONArray> otherDatas) {
		this.otherDatas = otherDatas;
	}

	@Override
	public String toString() {
		return "ProcedureContext [params=" + params + ", datas=" + datas
				+ ", columns=" + columns + ", otherDatas=" + otherDatas + "]";
	}

}
