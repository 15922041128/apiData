package org.pbccrc.api.rest;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.pbccrc.api.bean.User;
import org.pbccrc.api.biz.PersonBiz;
import org.pbccrc.api.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
@Path("/person")
public class PersonRest {
	
	@Autowired
	private PersonBiz personBiz;
	
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
//		fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		fileName = fileName.substring(fileName.indexOf(Constants.POINT), fileName.length());
		fileName = System.currentTimeMillis() + fileName;
		File photo = new File(filepath + File.separator + fileName);
		item.write(photo);
//		List fileItems = fu.parseRequest(request);
//		Iterator iter = fileItems.iterator();
//
//		while (iter.hasNext()) {
//			FileItem item = (FileItem) iter.next();
//			String fileName = item.getName();
//			// 判断是否为文件
//			if (fileName != null) {
//				// 取文件名
//				String fName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
//				if (fileName != null && !fileName.equals(Constants.BLANK)) {
//					File file = new File(filepath + File.separator + fName);
//					// 上传文件
//					item.write(file);
//				}
//			}
//		}
		
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		String personID = personBiz.addPerson(name, idCardNo, tel, address, photo, user.getUserName());
		
		return Response.ok(personID).build();
	}
	
	@GET
	@Path("/add2")
	public Response add2(@Context HttpServletRequest request,
			@QueryParam("contactDate") String contactDate,
			@QueryParam("loanDate") String loanDate,
			@QueryParam("hireDate") String hireDate,
			@QueryParam("expireDate") String expireDate,
			@QueryParam("loanUsed") String loanUsed,
			@QueryParam("totalAmount") String totalAmount,
			@QueryParam("balance") String balance,
			@QueryParam("status") String status,
			@QueryParam("personID") String personID) throws Exception{
		
		String retData = Constants.RET_STAT_ERROR;
		
		User user = (User) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		if(personBiz.addPersonRedit(personID, contactDate, loanDate, 
				hireDate, expireDate, loanUsed, totalAmount, balance, status, user)){
			retData = Constants.RET_STAT_SUCCESS;
		}
		
		return Response.ok(retData).build();
	}
	
	@GET
	@Path("/query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@QueryParam("name") String name, @QueryParam("idCardNo") String idCardNo) throws Exception{
		
		JSONObject obj = personBiz.getReditList(name, idCardNo);
		
		return Response.ok(obj).build();
	}

}
