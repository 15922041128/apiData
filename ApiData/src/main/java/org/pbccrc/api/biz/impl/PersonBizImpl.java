package org.pbccrc.api.biz.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pbccrc.api.bean.DBEntity;
import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.PersonBiz;
import org.pbccrc.api.biz.QueryApiBiz;
import org.pbccrc.api.dao.DBOperator;
import org.pbccrc.api.dao.PBaseInfoDao;
import org.pbccrc.api.dao.PPersonDao;
import org.pbccrc.api.dao.PReditDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.StringUtil;
import org.pbccrc.api.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class PersonBizImpl implements PersonBiz {
	
	@Autowired
	private PPersonDao pPersonDao;
	
	@Autowired
	private PBaseInfoDao pBaseInfoDao;
	
	@Autowired
	private PReditDao pReditDao;
	
	@Autowired
	private DBOperator dbOperator;
	
	@Autowired
	private QueryApiBiz queryApiBiz;
	
	@Autowired
	private Validator validator;
	
	/**
	 * @param name          姓名
	 * @param idCardNo		身份证号
	 * @param tel			电话
	 * @param address		地址
	 * @param photo			照片
	 * @param currentUser	当前用户
	 * @return
	 */
	public String addPerson(String name, String idCardNo, String tel, String address, File photo, String currentUser) {
		
		Map<String, String> person = new HashMap<String, String>();
		person.put("name", name);
		person.put("idCardNo", idCardNo);
		
		String personID;
		
		/** p_person表操作 */
		if(pPersonDao.isExist(person) > 0) {
			// 当前person已存在,查询出personID
			Map<String, Object> resPerson = pPersonDao.selectOne(person);
			personID = String.valueOf(resPerson.get(Constants.PERSON_ID));
		} else {
			// 当前person不存在,插入person表数据并返回personID
			pPersonDao.addPerson(person);
			personID = String.valueOf(person.get(Constants.PERSON_ID));
		}
		
		Map<String, Object> pBaseInfo = new HashMap<String, Object>();
		pBaseInfo.put("personID", personID);
		pBaseInfo.put("tel", tel);
		pBaseInfo.put("address", address);
		pBaseInfo.put("photo", photo != null ? photo.getPath() : "");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		/** p_baseInfo表操作 */
//		if(pBaseInfoDao.isExist(personID) > 0) {
//			// 当前数据在pBaseInfo中已存在,update
//			pBaseInfo.put("updateUser", currentUser);
//			pBaseInfo.put("updateTime", format.format(new Date()));
//			pBaseInfoDao.updatePerson(pBaseInfo);
//		} else {
//			// 当前数据在pBaseInfo中不存在,insert
//			pBaseInfo.put("createUser", currentUser);
//			pBaseInfo.put("createTime", format.format(new Date()));
//			pBaseInfoDao.addPerson(pBaseInfo);
//		}
		// 即使存在相同数据,依然保留
		pBaseInfo.put("createUser", currentUser);
		pBaseInfo.put("createTime", format.format(new Date()));
		pBaseInfo.put("updateUser", currentUser);
		pBaseInfo.put("updateTime", format.format(new Date()));
		pBaseInfoDao.addPerson(pBaseInfo);
		
		return personID;
		
	}
	
	/**
	 * @param personID 			personID
	 * @param contactDate 		合同日期
	 * @param hireDate			起租日
	 * @param expireDate		到期日
	 * @param type				业务类型
	 * @param loanUsed			贷款用途
	 * @param totalAmount		总金额
	 * @param balance			余额
	 * @param status			状态
	 * @param user				当前用户
	 * @return
	 */
	public boolean addPersonRedit(String personID, String contactDate, String hireDate, String expireDate, 
			String type, String loanUsed, String totalAmount, String balance, String status, User user) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String, Object> pRedit = new HashMap<String, Object>();
		pRedit.put("personID", personID);
		pRedit.put("contactDate",contactDate);
		pRedit.put("hireDate", hireDate);
		pRedit.put("expireDate", expireDate);
		pRedit.put("type", type);
		pRedit.put("loanUsed", loanUsed);
		pRedit.put("totalAmount", totalAmount);
		pRedit.put("balance", balance);
		pRedit.put("status", status);
		pRedit.put("bizOccurOrg", user.getCompName());
		pRedit.put("createUser", user.getUserName());
		pRedit.put("createTime", format.format(new Date()));
		pRedit.put("updateUser", user.getUserName());
		pRedit.put("updateTime", format.format(new Date()));
		
		return pReditDao.addPRedit(pRedit) > 0;
	}
	
	/**
	 * @param name 				姓名
	 * @param idCardNo			身份证号
	 * @return
	 */
	public JSONObject getReditList(String name, String idCardNo) throws Exception{
		
		JSONObject returnObj = new JSONObject();
		
		// 获取用户ID
		Map<String, String> person = new HashMap<String, String>();
		person.put("name", name);
		person.put("idCardNo", idCardNo);
		Map<String, Object> resPerson = pPersonDao.selectOne(person);
		if (null == resPerson) {
			returnObj.put("status", "error");
			return returnObj;
		}
		String personID = String.valueOf(resPerson.get(Constants.PERSON_ID));
		returnObj.put("person", person);
		
		// 获取用户信用评分信息
		String score = "暂无分数"; 
		String service = Constants.SERVICE_S_QUERYSCORE;
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("identityCard", new String[]{String.valueOf(resPerson.get("idCardNo"))});
		String result = String.valueOf(queryApiBiz.query(service, params).get("result"));
		JSONObject resultObj = JSONObject.parseObject(result);
		String resultScore = resultObj.getString("score");
		if (!StringUtil.isNull(resultScore)) {
			score = resultScore;
		}
		returnObj.put("score", score);
		
		// 获取用户基本信息
		List<Map<String, Object>> pBaseInfoList = pBaseInfoDao.queryByPersonID(personID);
		if (null == pBaseInfoList || pBaseInfoList.size() == 0) {
			returnObj.put("pBaseInfo", null);
		} else {
			returnObj.put("pBaseInfo", pBaseInfoList.get(0));
		}
		
		// 获取用户信贷信息
		List<Map<String, Object>> reditList = pReditDao.queryAll(personID);
		JSONArray array = new JSONArray();
		for (int i = 0 ; i < reditList.size(); i++) {
			Map<String, Object> map = reditList.get(i);
			array.add(map);
		}
		returnObj.put("reditList", array);
		
		// 被失信人被执行信息
		DBEntity entity = new DBEntity();
		entity.setTableName("ldb_dishonest_info");
		List<String> fields = new ArrayList<String>();
		fields.add("INAME");
		fields.add("CARDNUM");
		List<String> values = new ArrayList<String>();
		values.add(name);
		values.add(idCardNo);
		entity.setFields(fields);
		entity.setValues(values);
		List<Map<String, Object>> dishonestList = dbOperator.queryDatas(entity);
		array = new JSONArray();
		for (Map<String, Object> map : dishonestList) {
			array.add(map);
		}
		
		returnObj.put("dishonestList", array);
		
		return returnObj;
	}
	
	/**
	 * 批量报送
	 * @param fileItem
	 * @throws Exception
	 */
	public String addAll(FileItem fileItem, HttpServletRequest request) throws Exception {
		InputStream inputStream = fileItem.getInputStream();
		// 获取excel文件
		XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
		// 获取sheet页(第一页)
		XSSFSheet workSheet = workBook.getSheetAt(0);
		
		// 总条数
		int totalCount = 0;
		// 成功条数
		int successCount = 0;
		// 失败条数
		int failCount = 0;
		
		// 错误文件
		XSSFWorkbook errorBook = new XSSFWorkbook();
		XSSFSheet errorSheet = errorBook.createSheet("上传文件错误信息");
		XSSFRow errorRow = errorSheet.createRow(failCount++);
		errorRow.createCell(0).setCellValue("姓名");
		errorRow.createCell(1).setCellValue("身份证号");
		errorRow.createCell(2).setCellValue("手机号");
		errorRow.createCell(3).setCellValue("省");
		errorRow.createCell(4).setCellValue("市");
		errorRow.createCell(5).setCellValue("区");
		errorRow.createCell(6).setCellValue("详细地址");
		errorRow.createCell(7).setCellValue("合同日期");
		errorRow.createCell(8).setCellValue("起租日");
		errorRow.createCell(9).setCellValue("到期日");
		errorRow.createCell(10).setCellValue("业务类型");
		errorRow.createCell(11).setCellValue("用途");
		errorRow.createCell(12).setCellValue("总金额");
		errorRow.createCell(13).setCellValue("余额");
		errorRow.createCell(14).setCellValue("状态");
		errorRow.createCell(15).setCellValue("错误信息");
		
		// 遍历第一页所有数据
		for (int i = 1; i <= workSheet.getLastRowNum(); i++) {
			 totalCount++;
			 XSSFRow row = workSheet.getRow(i);
			 if (null != row) {
				 // 姓名
				 String name = getValue(row.getCell(0));
				 // 身份证号
				 String idCardNo = getValue(row.getCell(1));
				 // 手机号
				 String tel = getValue(row.getCell(2));
				 // 省
				 String province = getValue(row.getCell(3));
				 // 市
				 String city = getValue(row.getCell(4));
				 // 区
				 String area = getValue(row.getCell(5));
				 // 详细地址
				 String address = getValue(row.getCell(6));
				 // 合同日期
				 String contactDate = getValue(row.getCell(7));
				 // 起租日
				 String hireDate = getValue(row.getCell(8));
				 // 到期日
				 String expireDate = getValue(row.getCell(9));
				 // 业务类型
				 String type = getValue(row.getCell(10));
				 // 用途
				 String loanUsed = getValue(row.getCell(11));
				 // 总金额
				 String totalAmount = getValue(row.getCell(12));
				 // 余额
				 String balance = getValue(row.getCell(13));
				 // 状态
				 String status = getValue(row.getCell(14));
				 

				 // 验证姓名
				 String message = validator.validateName(name);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证身份证号
				 message = validator.validateIDCard(idCardNo);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证手机号
				 message = validator.validateMobile(tel);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证省
				 message = validator.validateProvince(province);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证市
				 message = validator.validateCity(city);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证区
				 message = validator.validateArea(area);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证详细地址
				 message = validator.validateAddress(address);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证合同日期
				 message = validator.validateDate(contactDate);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证起租日
				 message = validator.validateDate(hireDate);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证到期日
				 message = validator.validateDate(expireDate);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证业务类型
				 message = validator.validateType(type);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证用途
				 message = validator.validateLoanUsed(loanUsed);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证总金额
				 message = validator.validateNumber(totalAmount);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 验证余额
				 message = validator.validateNumber(balance);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 // 余额小于总金额验证
				 if (Integer.parseInt(balance) > Integer.parseInt(totalAmount)) {
					 message = "余额应小于等于总金额。";
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 row.createCell(15).setCellValue(message);
					 continue;
				 }
				 
				 // 验证状态
				 message = validator.validateStatus(status);
				 if (!Constants.BLANK.equals(message)) {
					 createErrorRow(errorSheet, failCount++, 
							 name, idCardNo, tel, 
							 province, city, area, address, 
							 contactDate, hireDate, expireDate, 
							 type, loanUsed, totalAmount, balance, status, message);
					 continue;
				 }
				 
				 address = province + city + area + address;
				 
				 // TODO insertDB
				 // 获取当前时间
				 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String currentDate = format.format(new Date());
				 // 获取当前用户
				 User currentUser = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
				 Map<String, String> person = new HashMap<String, String>();
				 person.put("name", name);
				 person.put("idCardNo", idCardNo);

				 String personID;

				 /** p_person表操作 */
				 if (pPersonDao.isExist(person) > 0) {
					// 当前person已存在,查询出personID
					Map<String, Object> resPerson = pPersonDao.selectOne(person);
					personID = String.valueOf(resPerson.get(Constants.PERSON_ID));
				 } else {
					// 当前person不存在,插入person表数据并返回personID
					pPersonDao.addPerson(person);
					personID = String.valueOf(person.get(Constants.PERSON_ID));
				 }

				 Map<String, Object> pBaseInfo = new HashMap<String, Object>();
				 pBaseInfo.put("personID", personID);
				 pBaseInfo.put("tel", tel);
				 pBaseInfo.put("address", address);

				 /** p_baseInfo表操作 */
				 // 即使存在相同数据,依然保留
				 pBaseInfo.put("createUser", currentUser.getUserName());
				 pBaseInfo.put("createTime", currentDate);
				 pBaseInfo.put("updateUser", currentUser.getUserName());
				 pBaseInfo.put("updateTime", currentDate);
				 pBaseInfoDao.addPerson(pBaseInfo);
				 
				 Map<String, Object> pRedit = new HashMap<String, Object>();
				 pRedit.put("personID", personID);
				 pRedit.put("contactDate",contactDate);
				 pRedit.put("hireDate", hireDate);
				 pRedit.put("expireDate", expireDate);
				 pRedit.put("type", type);
				 pRedit.put("loanUsed", loanUsed);
				 pRedit.put("totalAmount", totalAmount);
				 pRedit.put("balance", balance);
				 pRedit.put("status", status);
				 pRedit.put("bizOccurOrg", currentUser.getCompName());
				 pRedit.put("createUser", currentUser.getUserName());
				 pRedit.put("createTime", currentDate);
				 pRedit.put("updateUser", currentUser.getUserName());
				 pRedit.put("updateTime", currentDate);
				
				pReditDao.addPRedit(pRedit);
				 
				 successCount++;
 			 }
		}
		
		errorRow = errorSheet.createRow(failCount++);
		errorRow.createCell(0).setCellValue("总计" + totalCount + "条");
		errorRow = errorSheet.createRow(failCount++);
		errorRow.createCell(0).setCellValue("成功" + successCount + "条");
		errorRow = errorSheet.createRow(failCount++);
		errorRow.createCell(0).setCellValue("失败" + (totalCount - successCount) + "条");
		
		// 判断是否全部成功
		String exportPath = Constants.BLANK;
		// 生成返回信息文件
		OutputStream os = null;
		try {
			String basePath = request.getSession().getServletContext().getRealPath(Constants.UPLOAD_ERROR_FILE);
			
			File file = new File(basePath);
			if(!file.exists()){
				file.mkdirs();
			}
			exportPath = basePath + File.separator + System.currentTimeMillis() + ".xls";
			os = new FileOutputStream(exportPath);
			errorBook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return exportPath;
		
	}
	
	/**
	 * 
	 * @param errorSheet
	 * @param failCount
	 * @param name
	 * @param idCardNo
	 * @param tel
	 * @param province
	 * @param city
	 * @param area
	 * @param address
	 * @param contactDate
	 * @param hireDate
	 * @param expireDate
	 * @param type
	 * @param loanUsed
	 * @param totalAmount
	 * @param balance
	 * @param status
	 * @param message
	 */
	private void createErrorRow(XSSFSheet errorSheet, int failCount, 
			String name, String idCardNo, String tel, 
			String province, String city, String area, String address, 
			String contactDate, String hireDate, String expireDate, 
			String type, String loanUsed, String totalAmount, String balance, String status, String message) {
		 XSSFRow errorRow = errorSheet.createRow(failCount);
		 errorRow.createCell(0).setCellValue(StringUtil.null2Blank(name));
		 errorRow.createCell(1).setCellValue(StringUtil.null2Blank(idCardNo));
		 errorRow.createCell(2).setCellValue(StringUtil.null2Blank(tel));
		 errorRow.createCell(3).setCellValue(StringUtil.null2Blank(province));
		 errorRow.createCell(4).setCellValue(StringUtil.null2Blank(city));
		 errorRow.createCell(5).setCellValue(StringUtil.null2Blank(area));
		 errorRow.createCell(6).setCellValue(StringUtil.null2Blank(address));
		 errorRow.createCell(7).setCellValue(StringUtil.null2Blank(contactDate));
		 errorRow.createCell(8).setCellValue(StringUtil.null2Blank(hireDate));
		 errorRow.createCell(9).setCellValue(StringUtil.null2Blank(expireDate));
		 errorRow.createCell(10).setCellValue(StringUtil.null2Blank(type));
		 errorRow.createCell(11).setCellValue(StringUtil.null2Blank(loanUsed));
		 errorRow.createCell(12).setCellValue(StringUtil.null2Blank(totalAmount));
		 errorRow.createCell(13).setCellValue(StringUtil.null2Blank(balance));
		 errorRow.createCell(14).setCellValue(StringUtil.null2Blank(status));
		 errorRow.createCell(15).setCellValue(StringUtil.null2Blank(message));
	}
	
	@SuppressWarnings("static-access")
	private String getValue(XSSFCell cell) {
		if (null == cell) {
			return Constants.STR_NULL;
		}
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	
}
