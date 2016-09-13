package org.pbccrc.api.util.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class pubElements {
	  public static BaseFont bfChinese; 
	  public static Font FontChinese24;
	  public static Font FontChinese18;
	  public static Font FontChinese16;
	  public static Font FontChinese12;
	  public static Font FontChinese11Bold;
	  public static Font FontChinese8Bold;
	  public static Font FontChinese11;
	  public static Font FontChinese11Normal;
	  public static Font FontChinese8Normal;
	  
	  static {
		  try {
			 bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 
		     FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
	         FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font.BOLD); 
	         FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.BOLD);
	         FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.NORMAL);
	         FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.BOLD);
	         FontChinese8Bold = new com.itextpdf.text.Font(bfChinese, 8, com.itextpdf.text.Font.BOLD);
	         FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.ITALIC);
	         FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);
	         FontChinese8Normal = new com.itextpdf.text.Font(bfChinese, 8, com.itextpdf.text.Font.NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	  /**
	   * 生成pdf Header
	   * @return
	   */
	  public static PdfPTable Pheader() {
		  PdfPTable table = new PdfPTable(2);
		  try {
			  PdfPCell cell11 = new PdfPCell(new Paragraph("个人信用信息查询",FontChinese24));
			  cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
			  cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			  cell11.setBorder(0);
			  String imagePath = "D:/pdftest/yingze.jpg";
			  Image image1 = Image.getInstance(imagePath); 
			  
			  //设置每列宽度比例   
			  int width11[] = {35,75};
			  table.setWidths(width11); 
			  table.getDefaultCell().setBorder(0);
			  table.addCell(image1);  
			  table.addCell(cell11);  
			  table.addCell("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return table;
	}
		
	/**
	 * 二级标题
	 * @param title
	 * @param font
	 * @return
	 */
	public static PdfPTable PsubTitle(String title , Font font) {
		//table2 基本信息头
		PdfPTable table = new PdfPTable(2);
		try{
        //设置每列宽度比例   
        int width21[] = {3,97};
        table.setWidths(width21); 
        table.getDefaultCell().setBorder(0);
        PdfPCell cell21 = new PdfPCell(new Paragraph(title, font));
        String imagePath2 = "D:/pdftest/boder.jpg";
        Image image21 = Image.getInstance(imagePath2); 
        cell21.setBorder(0);
        table.addCell(image21);
        table.addCell(cell21); 
        }catch (Exception ex) {
            ex.printStackTrace();
        }
		return table;
	}
	
	/**
	 * 插入空行
	 * @param font
	 * @return
	 */
	public static Paragraph  emptyRow() {
		Paragraph blankRow = new Paragraph(18f, " ", FontChinese18);
		return blankRow; 
	}
	
	/**
	 * 根据json对象拼装为两列显示
	 * @param jsonObj
	 * @param cnName 显示中文名称数组
	 * @param key 对应json字段名称
	 * @return
	 */
	public static PdfPTable obj2Table(JSONObject jsonObj, String[] cnName, String[] key) {
		PdfPTable table = new PdfPTable(2);
		try {
			  //table2 基本信息头
	      //table3 基本信息内容
	      int width[] = {50,50};
	      table.setWidths(width); 
	      for (int i = 0; i < cnName.length; i++) {
	    	  PdfPCell cell = new PdfPCell(new Paragraph(cnName[i]+"："+key[i],FontChinese8Normal));
	    	  cell.setPaddingBottom(18);
	    	  cell.setBorder(0);
	    	  table.addCell(cell);
	    	  
	      }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	/**
	 * jsonArray转为Grid
	 * @param dataArray
	 * @param cnName
	 * @param key
	 * @return
	 */
	public static PdfPTable obj2Grid(JSONArray dataArray, String[] cnName, String[] key) {
        PdfPTable table = new PdfPTable(8);
        try {
	        BaseColor lightGrey = new BaseColor(0xCC,0xCC,0xCC);
	        int width7[] = {5,15,15,20,12,12,12,10};
	        table.setWidths(width7); 
	        //生成表头
	        for (int i = 0; i < cnName.length; i++) {
	        	PdfPCell cell = new PdfPCell(new Paragraph(cnName[i],FontChinese8Bold));
	        	cell.setFixedHeight(25);
	        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        	cell.setBorderColor(lightGrey);
	        	if (i!=0)
	        		cell.disableBorderSide(4);
	        	if (i!=cnName.length-1)
	        		cell.disableBorderSide(8);
	        	table.addCell(cell);
			}
	        for (int i = 0; i < dataArray.size(); i++) {
	        	JSONObject dataObj = dataArray.getJSONObject(i);
	        	fillGridData(dataObj, table, key, FontChinese8Normal);
			}
        } catch (Exception e) {
        	e.printStackTrace();
		}
        return table;
	}
	
	/**
	 * 填充Grid数据
	 * @param obj
	 * @param table
	 * @param key
	 * @param font
	 */
	private static void fillGridData(JSONObject obj,PdfPTable table, String[] key, Font font) {
		 BaseColor lightGrey = new BaseColor(0xCC,0xCC,0xCC);
		 for (int i = 0; i < key.length; i++) {
	        	PdfPCell cell = new PdfPCell(new Paragraph(obj.getString(key[i]),font));
	        	cell.setFixedHeight(25);
	        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        	cell.setBorderColor(lightGrey);
	        	if (i!=0)
	        		cell.disableBorderSide(4);
	        	if (i!=key.length-1)
	        		cell.disableBorderSide(8);
	        	table.addCell(cell);
			}
	}
        
	
	
	
}
