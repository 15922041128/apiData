package org.pbccrc.api.util;

public class Constants {

	public static final int COUNT_MAX = -1;
	
	public static final String BLANK = "";
	public static final String POINT = ".";
	public static final String UNDERLINE = "_";
	public static final String CONNECTOR_LINE = "-";
	public static final String COMMA = ",";
	public static final String AND = " and ";
	public static final String EQUAL = "=";
	public static final String SINGLE_QUOTES = "'";
	public static final String SPACE = " ";
	
	public static final String STR_NULL = "null";
	
	public static final String URL_CONNECTOR = "?";
	public static final String URL_PARAM_CONNECTOR = "&";
	public static final String URL_EQUAL = "=";
	
	public static final String RET_STAT_SUCCESS = "Y";
	public static final String RET_STAT_ERROR = "N";
	
	public static final String CURRENT_USER = "currentUser";
	
	public static final String PACKAGE_BIZ_IMP_QUERY = "org.pbccrc.api.biz.impl.query";
	public static final String QUERY_API_SINGLE = "QueryApiSingle";
	public static final String QUERY_API_MULTIPLE = "QueryApiMultiple";
	
	/** 返回code */
	public static final String RET_CODE_SUCCESS = "success";
	public static final String RET_CODE_ERROR = "error";
	// return code type
	public static final String RET_CODE_TYPE_CONTINUE = "continue";
	public static final String RET_CODE_TYPE_BREAK = "break";
	
	/** 加密方式 */
	// 不加密
	public static final String ENCRYPT_TYPE_NORMAL = "normal";
	// 全联加密
	public static final String ENCRYPT_TYPE_QL = "ql";
	
	/** 服务器文件相关 */
	public static final String FILE_PATH_BASE = "files";
	public static final String FILE_PATH_PHOTO = "photos";
	
	/** url参数相关 */
	// apiKey
	public static final String HEAD_APIKEY = "apiKey";
	public static final String HEAD_USER_ID = "userID";
	// 参数类型
	public static final String PARAM_TYPE_HEAD = "head";
	public static final String PARAM_TYPE_URL = "url";
	public static final String PARAM_TYPE_SERVICE = "service";
	public static final String PARAM_TYPE_APIKEY = "apiKey";
	
	// 是否必须
	public static final String PARAM_REQUIRED_Y = "yes";
	public static final String PARAM_REQUIRED_N = "no";
	
	// 返回信息
	public static final String RETMSG_SUCCESS = "success";
	
	// 前缀
	// 唯一外部api
	public static final String PREFIX_SINGLE = "s";
	// 多个外部api
	public static final String PREFIX_MULTIPLE = "m";
	
	
	/** return content */
	// 正常返回
	public static final String ERR_NOERR = "0";
	public static final String RET_MSG_SUCCESS = "success";
	
	// 缺少apiKey
	public static final String ERR_MISSING_APIKEY = "100101";
	public static final String RET_MSG_MISSING_APIKEY = "缺少apiKey";
	
	// apiKey与用户不匹配
	public static final String ERR_APIKEY_USER_INVALID = "100102";
	public static final String RET_MSG_APIKEY_USER_INVALID = "apiKey与用户不匹配";
	
	// apiKey无效
	public static final String ERR_APIKEY_INVALID = "100103";
	public static final String RET_MSG_APIKEY_INVALID = "无效的apiKey";
	
	// url参数不匹配
	public static final String ERR_URL_INVALID = "100104";
	public static final String RET_MSG_URL_INVALID = "缺少参数 : ";
	
	// 查询次数达到上限
	public static final String ERR_CNT = "100105";
	public static final String RET_MSG_CNT = "查询次数达到上限";
	
	// 服务器内部错误
	public static final String ERR_SERVER = "100106";
	public static final String RET_MSG_SERVER = "服务器内部错误";
	
	// 缺少userID
	public static final String ERR_MISSING_USER_ID = "100107";
	public static final String RET_MSG_MISSING_USER_ID = "缺少userID";
	
	// 缺少service或service格式不正确
	public static final String ERR_SERVICE = "101001";
	public static final String RET_MSG_SERVICE = "缺少service或service格式不正确";

	// 查询成功
	public static final String CODE_ERR_SUCCESS = "102000";
	// 查询失败
	public static final String CODE_ERR_FAIL = "102001";
	
	/**  table filed   */
	public static final String API_KEY = "apiKey";
	
	// person
	public static final String PERSON_ID = "ID";
}
