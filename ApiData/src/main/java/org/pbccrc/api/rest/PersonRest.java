package org.pbccrc.api.rest;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.pbccrc.api.bean.PersonLog;
import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.PersonBiz;
import org.pbccrc.api.dao.PersonLogDao;
import org.pbccrc.api.util.Constants;
import org.pbccrc.api.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
@Path("/person")
public class PersonRest {
	
	@Autowired
	private PersonBiz personBiz;
	
	@Autowired
	private PersonLogDao personLogDao;
	
	@POST
	@Path("/add")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response add(@Context HttpServletRequest request) throws Exception{
		
		String name = request.getParameter("name");
		name = java.net.URLDecoder.decode(name, "UTF-8");
		String idCardNo = request.getParameter("idCardNo");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		address = java.net.URLDecoder.decode(address, "UTF-8");
		
		String filepath = request.getSession().getServletContext().getRealPath("/") + Constants.FILE_PATH_BASE + File.separator + Constants.FILE_PATH_PHOTO;
//		String filepath = request.getSession().getServletContext().getContextPath() + File.separator + Constants.FILE_PATH_BASE + File.separator + Constants.FILE_PATH_PHOTO;
		File dir = new File(filepath);
		// 建立目录
		if (!dir.exists()) {
			dir.mkdirs();
		}
		DiskFileUpload fu = new DiskFileUpload();
		// 设置最大文件尺寸，这里是10MB
		fu.setSizeMax(10485760);
		// 设置缓冲区大小，这里是4kb
		fu.setSizeThreshold(4096);
		
		FileItem item = fu.parseRequest(request).get(0);
		String fileName = item.getName();
		File photo = null;
		if (!StringUtil.isNull(fileName)) {
			fileName = fileName.substring(fileName.indexOf(Constants.POINT), fileName.length());
			fileName = System.currentTimeMillis() + fileName;
			photo = new File(filepath + File.separator + fileName);
			item.write(photo);
		}
		
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		JSONObject returnJson = personBiz.addPerson(name, idCardNo, tel, address, photo, user.getUserName());
		
		// 记录日志
		PersonLog personLog = new PersonLog();
		// ip地址
		personLog.setIpAddress(request.getRemoteAddr());
		// 用户ID
		personLog.setUserID(String.valueOf(user.getID()));
		// 操作类型
		personLog.setOperatorType(Constants.OPERATOR_TYPE_ADD_STEP1);
		// 总操作数
		personLog.setTotalCount(Constants.STR_ONE);
		// 成功操作数
		personLog.setSuccessCount(Constants.STR_ONE);
		// 失败操作数
		personLog.setFailCount(Constants.STR_ZERO);
		// 查询时间
		personLog.setQueryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 备注
		personLog.setNote(returnJson.toJSONString());
		personLogDao.addLog(personLog);
		
		return Response.ok(returnJson.getString("personID")).build();
	}
	
	@GET
	@Path("/add2")
	public Response add2(@Context HttpServletRequest request,
			@QueryParam("contactDate") String contactDate,
			@QueryParam("hireDate") String hireDate,
			@QueryParam("expireDate") String expireDate,
			@QueryParam("type") String type,
			@QueryParam("loanUsed") String loanUsed,
			@QueryParam("totalAmount") String totalAmount,
			@QueryParam("balance") String balance,
			@QueryParam("status") String status,
			@QueryParam("personID") String personID) throws Exception{
		
		String retData = Constants.RET_STAT_ERROR;
		
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		JSONObject returnJson = personBiz.addPersonRedit(personID, contactDate,
				hireDate, expireDate, type, loanUsed, totalAmount, balance, status, user);
		
		if (returnJson.getBooleanValue("isSuccess")) {
			retData = Constants.RET_STAT_SUCCESS;
		}
		
		// 记录日志
		PersonLog personLog = new PersonLog();
		// ip地址
		personLog.setIpAddress(request.getRemoteAddr());
		// 用户ID
		personLog.setUserID(String.valueOf(user.getID()));
		// 操作类型
		personLog.setOperatorType(Constants.OPERATOR_TYPE_ADD_STEP2);
		// 总操作数
		personLog.setTotalCount(Constants.STR_ONE);
		// 成功操作数
		personLog.setSuccessCount(Constants.STR_ONE);
		// 失败操作数
		personLog.setFailCount(Constants.STR_ZERO);
		// 查询时间
		personLog.setQueryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 备注
		personLog.setNote(returnJson.getJSONObject("pRedit").toJSONString());
		personLogDao.addLog(personLog);
		
		return Response.ok(retData).build();
	}
	
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	} 
	
	@POST
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@QueryParam("name") String name, @QueryParam("idCardNo") String idCardNo, @Context HttpServletRequest request) throws Exception{
		
		JSONObject obj = personBiz.getReditList(name, idCardNo);
		
		// 获取当前用户
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		// 记录日志
		PersonLog personLog = new PersonLog();
		// ip地址
//		personLog.setIpAddress(getIpAddr(request));
		personLog.setIpAddress(request.getRemoteAddr());
		// 用户ID
		personLog.setUserID(String.valueOf(user.getID()));
		// 操作类型
		personLog.setOperatorType(Constants.OPERATOR_TYPE_QUERY);
		// 总操作数
		personLog.setTotalCount(Constants.STR_ONE);
		// 判断是否查询成功
		if (StringUtil.isNull(obj.getString("status"))) {
			// 查询成功
			// 成功操作数
			personLog.setSuccessCount(Constants.STR_ONE);
			// 失败操作数
			personLog.setFailCount(Constants.STR_ZERO);
		} else {
			// 查询失败
			// 成功操作数
			personLog.setSuccessCount(Constants.STR_ZERO);
			// 失败操作数
			personLog.setFailCount(Constants.STR_ONE);
		}
		// 查询时间
		personLog.setQueryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 备注
		JSONObject noteJson = new JSONObject();
		noteJson.put("name", name);
		noteJson.put("idCardNo", idCardNo);
		personLog.setNote(noteJson.toJSONString());
		personLogDao.addLog(personLog);
		
		return Response.ok(obj).location(new URI("page/credit/queryResult.html")).build();
	}

	@POST
	@Path("/addAll")
	@Produces({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	public Response addAll(@Context HttpServletRequest request) throws Exception {
		DiskFileUpload fu = new DiskFileUpload();
		FileItem fileItem = fu.parseRequest(request).get(0);
		String exportPath = personBiz.addAll(fileItem, request);
		JSONObject result = new JSONObject();
		exportPath = exportPath.substring(exportPath.indexOf("\\ApiData"));
		result.put("filePath", exportPath);
		
		// 记录日志
		PersonLog personLog = new PersonLog();
		// ip地址
		personLog.setIpAddress(request.getRemoteAddr());
		// 用户ID
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		personLog.setUserID(String.valueOf(user.getID()));
		// 操作类型
		personLog.setOperatorType(Constants.OPERATOR_TYPE_ADDALL);
		// 总操作数
		personLog.setTotalCount(Constants.STR_ONE);
		// 成功操作数
		personLog.setSuccessCount(Constants.STR_ONE);
		// 失败操作数
		personLog.setFailCount(Constants.STR_ZERO);
		// 查询时间
		personLog.setQueryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 备注
		personLog.setNote(exportPath);
		personLogDao.addLog(personLog);
		
		return Response.ok(result).build();
	}
	
	@POST
	@Path("/queryAll")
	@Produces({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	public Response queryAll(@Context HttpServletRequest request) throws Exception {
		DiskFileUpload fu = new DiskFileUpload();
		FileItem fileItem = fu.parseRequest(request).get(0);
		String exportPath = personBiz.queryAll(fileItem, request);
		JSONObject result = new JSONObject();
		exportPath = exportPath.substring(exportPath.indexOf("\\ApiData"));
		result.put("filePath", exportPath);
		
		// 记录日志
		PersonLog personLog = new PersonLog();
		// ip地址
		personLog.setIpAddress(request.getRemoteAddr());
		// 用户ID
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		personLog.setUserID(String.valueOf(user.getID()));
		// 操作类型
		personLog.setOperatorType(Constants.OPERATOR_TYPE_QUERYALL);
		// 总操作数
		personLog.setTotalCount(Constants.STR_ONE);
		// 成功操作数
		personLog.setSuccessCount(Constants.STR_ONE);
		// 失败操作数
		personLog.setFailCount(Constants.STR_ZERO);
		// 查询时间
		personLog.setQueryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 备注
		personLog.setNote(exportPath);
		personLogDao.addLog(personLog);
		return Response.ok(result).build();
	}
	
	@GET
	@Path("/downloadTemplate/{type}")
	public Response downloadTemplate(@PathParam("type") String type, @Context HttpServletRequest request) throws Exception {
		
		String filePath;
		String fileName;
		
		// 根据类型返回不同模板路径
		if ("query".equals(type)) {
			filePath = request.getSession().getServletContext().getRealPath(Constants.BATCH_QUERY_TEMPLATE_FILE);
			fileName = "batch_query_template.xlsx";
		} else {
			filePath = request.getSession().getServletContext().getRealPath(Constants.BATCH_ADD_TEMPLATE_FILE);
			fileName = "batch_report_template.xlsm";
		}
		
		fileName = URLEncoder.encode(fileName, "UTF-8"); 
		
		File file = new File(filePath);
		String mt = new MimetypesFileTypeMap().getContentType(file);
		
		return Response.ok(file, mt)
                .header("Content-disposition","attachment;filename=" + fileName)
                .header("ragma", "No-cache").header("Cache-Control", "no-cache").build();

	}
}
