package org.pbccrc.api.util;

public class Constants {
	
	public static final int COUNT_MAX = -1;
	
	public static final String STR_ZERO = "0";
	public static final String STR_ONE = "1";
	
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
	
	// 文件类型
	public static final String FILE_TYPE_PDF = ".pdf";
	
	// pdf导出目录
	public static final String FILE_DOWNLOAD_SXR_PDF = "/files/download/sxr/pdf";//pdf导出目录
	// 上传错误文件目录
	public static final String UPLOAD_ERROR_FILE = "/files/download/uploadError";
	// 批量查询文件目录
	public static final String BATCH_QUERY_FILE = "/files/download/batchQuery";
	// 批量报送模板文件
	public static final String BATCH_ADD_TEMPLATE_FILE =  "/files/template/addTemplate.xlsm";
	// 批量查询模板文件
	public static final String BATCH_QUERY_TEMPLATE_FILE =  "/files/template/queryTemplate.xlsx";
	
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
	public static final String PARAM_TYPE_CONSTANT = "constant";
	public static final String PARAM_TYPE_SERVICE = "service";
	public static final String PARAM_TYPE_APIKEY = "apiKey";
	public static final String PARAM_TYPE_FORMAT = "format";
	
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
	
	// 查询接口不存在
	public static final String ERR_NO_SERVICE = "100108";
	public static final String RET_MSG_NO_SERVICE = "查询接口不存在";
	
	// 查询无结果
	public static final String ERR_NO_RESULT = "100109";
	public static final String RET_MSG_NO_RESULT = "查询无结果";
	
	// 余额或信用额不足
	public static final String ERR_BLANCE_NOT_ENOUGH = "100110";
	public static final String RET_MSG_BLANCE_NOT_ENOUGH = "余额或信用额不足";
	
	// 缺少service或service格式不正确
	public static final String ERR_SERVICE = "101001";
	public static final String RET_MSG_SERVICE = "缺少service或service格式不正确";

	// 查询成功
	public static final String CODE_ERR_SUCCESS = "102000";
	public static final String CODE_ERR_SUCCESS_MSG = "查询成功";
	// 查询失败
	public static final String CODE_ERR_FAIL = "102001";
	public static final String CODE_ERR_FAIL_MSG = "查询失败";
	
	/**  table filed   */
	public static final String API_KEY = "apiKey";
	/** ldb_dishonest_info */
	// 被执行人身份证号码
	public static final String LDB_DISHONEST_INFO_CARDNUM = "CARDNUM";
	// 执行法院
	public static final String LDB_DISHONEST_INFO_COURT_NAME = "COURT_NAME";
	// 案号
	public static final String LDB_DISHONEST_INFO_CASE_CODE = "CASE_CODE";
	// 被执行人姓名
	public static final String LDB_DISHONEST_INFO_INAME = "INAME";
	// 执行情况
	public static final String LDB_DISHONEST_INFO_PERFORMANCE = "PERFORMANCE";
	// 发布时间
	public static final String LDB_DISHONEST_INFO_PUBLISH_DATE = "PUBLISH_DATE";
	// 省份名称
	public static final String LDB_DISHONEST_INFO_AREA_NAME = "AREA_NAME";
	// 职责
	public static final String LDB_DISHONEST_INFO_DUTY = "DUTY";
	// 失信被执行人行为具体情形
	public static final String LDB_DISHONEST_INFO_DISREPUT_TYPE_NAME = "DISREPUT_TYPE_NAME";
	
	// person
	public static final String PERSON_ID = "ID";
	
	/** conditionType */
	public static final String CONDITION_TYPE_NOTNULL = "notNull";
	public static final String CONDITION_TYPE_TEXT = "text";
	public static final String CONDITION_TYPE_REGEX = "regex";
	
	/** cost type */
	public static final String COST_TYPE_COUNT = "0";
	public static final String COST_TYPE_PRICE = "1";
	
	/** 本地访问用service */
	// 个人信用分
	public static final String SERVICE_S_QUERYSCORE = "s-queryScore";
	// 公积金 
	public static final String SERVICE_S_QGJJD = "s-qgjjd";
	// 涉诉信息(从执行公告中查询某人)
	public static final String SERVICE_S_UCACCIND_ZXGG = "s-ucaccindZXGG";
	// 涉诉信息(从失信公告中查询某人)
	public static final String SERVICE_S_UCACCIND_SXGG = "s-ucaccindSXGG";
	// 涉诉信息(从裁判文书中查询某人)
	public static final String SERVICE_S_UCACCIND_CPWS = "s-ucaccindCPWS";
	
	/** 本地访问用url(local DB) */
	// 失信人被执行信息
	public static final String URL_LDB_GETSXR = "http://127.0.0.1:8080/ApiData/r/ldb/getSxr";
	// 本地api查询接口
	public static final String URL_LDB_QUERY = "http://127.0.0.1:8080/ApiData/r/ldb/query";
	
	/** 本地访问用service(local DB) */
	// 授信信息
	public static final String SERVICE_LDB_CREDIT = "credit";
	
	/** remote url */
	// 行为评分
	public static final String REMOTE_URL_SCORE = "http://www.qilingyz.com/api.php?m=open.score";
	
	/** 查询参数项 */
	// 人员基本信息
	public static final String ITEM_PERSON = "person";
	// 居住信息
	public static final String ITEM_ADDRESS = "address";
	// 职业信息
	public static final String ITEM_EMPLOYMENT = "employment";
	// 信用卡信息
	public static final String ITEM_CREDITCARD = "creditCard";
	// 贷款信息
	public static final String ITEM_LOAN = "loan";
	// 担保信息
	public static final String ITEM_GUARANTEE = "guarantee";
	// 公积金信息
	public static final String ITEM_GGJ = "ggj";
	// 涉法涉诉信息
	public static final String ITEM_SFSS = "sfss";
	// 失信人被执行信息
	public static final String ITEM_SXR = "sxr";
	
	/** 信用风险信息服务平台用户操作类型 */
	// 单次查询
	public static final String OPERATOR_TYPE_QUERY = "1";
	// 单次报送step1
	public static final String OPERATOR_TYPE_ADD_STEP1 = "2";
	// 单次报送step2
	public static final String OPERATOR_TYPE_ADD_STEP2 = "3";
	// 批量查询
	public static final String OPERATOR_TYPE_QUERYALL = "4";
	// 批量报送
	public static final String OPERATOR_TYPE_ADDALL = "5";
	
}
