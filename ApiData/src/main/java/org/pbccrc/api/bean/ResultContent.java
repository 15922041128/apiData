package org.pbccrc.api.bean;

public class ResultContent {
	
	/** 错误代码 */
	private String errNum;
	
	/** 返回信息 */
	private String retMsg;

	/** 返回数据 */
	private Object retData;
	
	public String toString() {
		
		return "{'errNum' : '" + errNum + "', 'retMsg' : '" + retMsg + "', 'retData' :'" + retData + "'}";
	}

	public String getErrNum() {
		return errNum;
	}

	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public Object getRetData() {
		return retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}
	
}