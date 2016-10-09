package org.pbccrc.api.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pbccrc.api.bean.ResultContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class Validator {
	
	@Autowired
	private IDCardValidator idCardValidator;
	
	/**	api验证
	 * @param userID     		用户ID
	 * @param apiKey     		apiKey
	 * @param localApi     		本地api
	 * @param urlParams  		url参数
	 * @param resultContent     查询返回对象
	 * @return           		是否通过验证
	 */
	@SuppressWarnings("rawtypes")
	public boolean validateRequest(String userID, String apiKey, Map<String, Object> localApi, Map urlParams, ResultContent resultContent) {
		
		// 验证userID是否存在
		if (StringUtil.isNull(userID)) {
			resultContent.setErrNum(Constants.ERR_MISSING_USER_ID);
			resultContent.setRetMsg(Constants.RET_MSG_MISSING_USER_ID);
			return false;
		}
		
		StringBuilder relationKey = new StringBuilder("relation");
		relationKey.append(Constants.UNDERLINE + userID);
		relationKey.append(Constants.UNDERLINE + apiKey);
		relationKey.append(Constants.UNDERLINE + String.valueOf(localApi.get("ID")));
		JSONObject relation = JSONObject.parseObject(String.valueOf(RedisClient.get(relationKey.toString())));
		
		// 验证APIKEY是否存在
		if (StringUtil.isNull(apiKey)) {
			resultContent.setErrNum(Constants.ERR_MISSING_APIKEY);
			resultContent.setRetMsg(Constants.RET_MSG_MISSING_APIKEY);
			return false;
		}
		
		// 验证APIKEY是否有效
		if (null == relation) {
			resultContent.setErrNum(Constants.ERR_APIKEY_USER_INVALID);
			resultContent.setRetMsg(Constants.RET_MSG_APIKEY_USER_INVALID);
			return false;
		}
		
		// 获取计费方式
		String costType = relation.getString("costType");
		// 判断计费类型
		if (Constants.COST_TYPE_COUNT.equals(costType)) {
			// 按次数计费
			// 验证访问次数
			int count = Integer.parseInt(String.valueOf(relation.get("count")));
			if (count == 0) {
				resultContent.setErrNum(Constants.ERR_CNT);
				resultContent.setRetMsg(Constants.RET_MSG_CNT);
				return false;
			}
		} else if (Constants.COST_TYPE_PRICE.equals(costType)) {
			// 按金额计费
			// 验证余额和信用额
			// 获取apiUser
			String apiUserKey = "apiUser" + Constants.UNDERLINE + userID;
			JSONObject apiUser = JSONObject.parseObject(String.valueOf(RedisClient.get(apiUserKey).toString()));
			// 余额
			BigDecimal blance = new BigDecimal(apiUser.getString("blance"));
			// 获取用户价格
			BigDecimal price = new BigDecimal(relation.getString("price"));
			if (blance.compareTo(price) < 0) {
				resultContent.setErrNum(Constants.ERR_BLANCE_NOT_ENOUGH);
				resultContent.setRetMsg(Constants.RET_MSG_BLANCE_NOT_ENOUGH);
				return false;
			}
		} else {
			// to be extended
		}
		
		String dbAPIKEY = String.valueOf(relation.get(Constants.API_KEY));
		
		if (!apiKey.equals(dbAPIKEY)) {
			resultContent.setErrNum(Constants.ERR_APIKEY_INVALID);
			resultContent.setRetMsg(Constants.RET_MSG_APIKEY_INVALID);
			return false;
		}
		
		// 验证参数是否与api匹配
		String params = (String) localApi.get("params");
		JSONArray array = JSONArray.parseArray(params);
		for (Object o : array) {
			
			JSONObject object = (JSONObject)o;
			
			String paramName = String.valueOf(object.get("paramName"));
			String paramType = String.valueOf(object.get("paramType"));
			String notNull = String.valueOf(object.get("notNull"));
			
			if (Constants.PARAM_TYPE_URL.equals(paramType) && Constants.PARAM_REQUIRED_Y.equals(notNull)) {
				if (null == urlParams.get(paramName)) {
					resultContent.setErrNum(Constants.ERR_URL_INVALID);
					resultContent.setRetMsg(Constants.RET_MSG_URL_INVALID + paramName);
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 验证姓名
	 * @param name
	 * @return
	 */
	public String validateName(String name) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(name)) {
			resultMessage = "姓名不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证身份证号
	 * @param idCardNo
	 * @return
	 */
	public String validateIDCard(String idCardNo) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(idCardNo)) {
			resultMessage = "身份证号不能为空。";
			return resultMessage;
		}
		
		resultMessage = idCardValidator.IDCardValidate(idCardNo);
		
		return resultMessage;
	}
	
	/**
	 * 验证手机号
	 * @param mobile
	 * @return
	 */
	public String validateMobile(String mobile) {
		
		String resultMessage = Constants.BLANK;
		
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		
		Matcher m = p.matcher(mobile);
		
		boolean isMobile = m.matches();
		
		if (!isMobile) {
			resultMessage = "手机号码不合法。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证省
	 * @param province
	 * @return
	 */
	public String validateProvince(String province) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(province)) {
			resultMessage = "省不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证市
	 * @param city
	 * @return
	 */
	public String validateCity(String city) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(city)) {
			resultMessage = "市不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证区
	 * @param area
	 * @return
	 */
	public String validateArea(String area) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(area)) {
			resultMessage = "区不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证详细地址
	 * @param address
	 * @return
	 */
	public String validateAddress(String address) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(address)) {
			resultMessage = "详细地址不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证业务类型
	 * @param type
	 * @return
	 */
	public String validateType(String type) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(type)) {
			resultMessage = "业务类型不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证用途
	 * @param loanUsed
	 * @return
	 */
	public String validateLoanUsed(String loanUsed) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(loanUsed)) {
			resultMessage = "用途不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证状态
	 * @param status
	 * @return
	 */
	public String validateStatus(String status) {
		
		String resultMessage = Constants.BLANK;
		
		if (StringUtil.isNull(status)) {
			resultMessage = "状态不能为空。";
		}
		
		return resultMessage;
	}
	
	/**
	 * 验证日期格式
	 * @param date
	 * @return
	 */
	public String validateDate(String date) {
		
		String resultMessage = Constants.BLANK;
		
		boolean isDate = true;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(date);
		} catch (Exception e) {
			isDate = false;
		}
		
		if (!isDate) {
			resultMessage = "日期不合法";
		}
		
		return resultMessage;
	}
	
	/**
	 * 数字验证
	 * @param number
	 * @return
	 */
	public String validateNumber(String number) {
		
		String resultMessage = Constants.BLANK;
		
		Pattern p = Pattern.compile("^[0-9]*$");
		
		Matcher m = p.matcher(number);
		
		boolean isMobile = m.matches();
		
		if (!isMobile) {
			resultMessage = "非法数字格式。";
		}
		
		return resultMessage;
	}

}
