package com.tianqiauto.textile.weaving.util.procedure.service;


import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {

	private static final Logger log = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取数据库内的存储过程--返回一个ProcedureContext
	 * @param procedureName  存储过程名
	 * @param pm
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ProcedureContext callProcedure(final String procedureName,
										  final List<ProcedureParam> pm) {
		final List<ProcedureParam> inParams = new ArrayList();
		final List<ProcedureParam> outParams = new ArrayList();
		// 将输入in输出out参数分开
		if (pm.size() > 0) {
			for (ProcedureParam param : pm) {
				if ("IN".equals(param.getInOut().toUpperCase())) {
					inParams.add(param);
				} else if ("OUT".equals(param.getInOut().toUpperCase())) {
					outParams.add(param);
				}
			}
		}
		ProcedureContext context = (ProcedureContext) jdbcTemplate.execute(
				new CallableStatementCreator() {
					public CallableStatement createCallableStatement(
							Connection con) throws SQLException {
						int inSize = (inParams == null ? 0 : inParams.size());
						int outSize = (outParams == null ? 0 : outParams.size());
						StringBuffer sbsql = new StringBuffer();
						sbsql.append("{call " + procedureName).append("(");
						for (int i = 0; i < (inSize + outSize); i++) {
							if (i == 0) {
								sbsql.append("?");
							} else {
								sbsql.append(",?");
							}
						}
						sbsql.append(")}");
						CallableStatement cs = con.prepareCall(sbsql.toString());
						// 设置输入参数的值
						if (inSize > 0) {
							for (int i = 0; i < inSize; i++) {
								ProcedureParam param = inParams.get(i);
								cs.setObject(param.getIndex(), param.getValue());
							}
						}
						// 注册输出参数的类型
						for (int i = 0; i < outSize; i++) {
							ProcedureParam param = outParams.get(i);
							cs.registerOutParameter(param.getIndex(),param.getParamType());
						}
						return cs;
					}
				}, new CallableStatementCallback() {
					public ProcedureContext doInCallableStatement(
							CallableStatement cs) throws SQLException,
                            DataAccessException {

						ProcedureContext pro = new ProcedureContext();
						List<Object> array = new ArrayList<>();
						boolean hashResult = cs.execute();
						while (true) {
							// 判断本次循环是否为数据集
							if (hashResult) {
								ResultSet rs = cs.getResultSet();
								// 获取列数
								ResultSetMetaData metaData = rs.getMetaData();
								// json数组
								List<Object> innerResult = new ArrayList<>();
								while (rs.next()) {
									// 遍历每一列
									Map<String,Object> jsonObj = new HashMap<>();
									for (int i = 1; i <= metaData.getColumnCount(); i++) {
										String columnName = metaData.getColumnLabel(i);
										jsonObj.put(columnName,rs.getObject(columnName));
									}
									innerResult.add(jsonObj);
								}
								array.add(innerResult);
							} else {
								int updateCount = cs.getUpdateCount();
								if (updateCount == -1)
									break;
							}
							/* 每次判断下一个是否为了数据集 */
							/* stmt.getMoreResults() 为 true表示下一次循环为数据集，false为空 */
							hashResult = cs.getMoreResults();
						}
						// 注册输出参数的类型
						for (int i = 0; i < outParams.size(); i++) {
							ProcedureParam param = outParams.get(i);
							param.setValue(cs.getString(param.getIndex()));
						}
						pro.setParams(pm);
						if (array.size() == 1) {
							pro.setDatas((List<Object>) array.get(0));
						} else if (array.size() == 2) {
							pro.setColumns((List<Object>) array.get(0));
							pro.setDatas((List<Object>) array.get(1));
						} else if (array.size() > 2) {
							pro.setColumns((List<Object>) array.get(0));
							pro.setDatas((List<Object>) array.get(1));
							List<Object> otherDatas = new ArrayList<>();
							for (int i = 2; i < array.size(); i++) {
								otherDatas.add((List<Object>) array.get(i));
							}
							pro.setOtherDatas(otherDatas);
							log.info("存储过程【"+procedureName+"】返回多个数据集，请注意数据的使用顺序！");
						}
						return pro;
					}
				});
		return context;
	}

	
	/**
	* @Title: callProcedureWithOutParams 
	* @Description: 调用无参数的存储过程
	* @param @param procedureName
	* @return ProcedureContext
	* @throws 
	*/
	public ProcedureContext callProcedureWithOutParams(String procedureName) {
		List<ProcedureParam> params = new ArrayList<ProcedureParam>();
		return this.callProcedure(procedureName, params);
	}
	
	
	@Transactional
	public int[] batchUpdate(List<String> sqls){
		if(sqls.size() > 0){
			return jdbcTemplate.batchUpdate(sqls.toArray(new String[sqls.size()]));
		}else {
			return new int[0];
		}
	}
}
