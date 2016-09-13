package org.pbccrc.api.test;


import java.io.FileOutputStream;

import org.pbccrc.api.util.pdf.pubElements;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class pdfBuilder {
    public static void main(String[] args) {

        try 
        {
            Document document = new Document(PageSize.A4); 
            PdfWriter.getInstance(document, new FileOutputStream("D:\\pdftest\\YingzeReport.PDF"));
            document.open();
            
            //添加Header
            document.add(pubElements.Pheader());
           
            //添加信用评分
            PdfPTable table9 = new PdfPTable(3);
            int width9[] = {30,50,20};
            table9.setWidths(width9);
            PdfPCell cell91 = new PdfPCell(new Paragraph("",pubElements.FontChinese12));
            PdfPCell cell92 = new PdfPCell(new Paragraph("信用评分",pubElements.FontChinese12));
            PdfPCell cell93 = new PdfPCell(new Paragraph("100分",pubElements.FontChinese24));
            cell92.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell92.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell93.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell93.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell91.setBorder(0);
            cell92.setBorder(0);
            cell93.setBorder(0);
            table9.addCell(cell91); 
            table9.addCell(cell92); 
            table9.addCell(cell93); 
            document.add(table9);
            
            //加入空行
            document.add(pubElements.emptyRow());
            
            //添加基本信息块
            document.add(pubElements.PsubTitle("基本信息",pubElements.FontChinese16));
            document.add(pubElements.emptyRow());
            
            //添加双列表格
            String[] cnBase = {"姓名","证件号码","出生日期","性别","学历","学位","婚姻状况","手机号码","电子邮箱","职业","行业","开始工作年份","职务","居住信息","邮政编码","家庭住址"};
  	      	String[] keyBase = {"姓名","证件号码","出生日期","性别","学历","学位","婚姻状况","手机号码","电子邮箱","职业","行业","开始工作年份","职务","居住信息","邮政编码","家庭住址"};
            document.add(pubElements.obj2Table(null, cnBase, keyBase));
            
            //添加信用卡信息块
            document.add(pubElements.PsubTitle("信用卡信息",pubElements.FontChinese16));
            document.add(pubElements.emptyRow());
            String[] cnName = {"信用卡张数","授信总额度","最大授信额度","最大负债","发卡省市数","逾期总期数","最大逾期期数","最早用卡时间"};
  	      	String[] key = {"信用卡张数","授信总额度","最大授信额度","最大负债","发卡省市数","逾期总期数","最大逾期期数","最早用卡时间"};
            document.add(pubElements.obj2Table(null, cnName, key));

            //添加贷款信息块
            document.add(pubElements.PsubTitle("贷款信息",pubElements.FontChinese16));
            document.add(pubElements.emptyRow());
            
            //添加贷款信息Grid
            String[] cnGrid = {"编号","贷款类型","业务发生地","机构","贷款额度","借款日期","贷款到期日","贷款状态"};
            String[] dataKey = {"编号","贷款类型","业务发生地","机构","贷款额度","借款日期","贷款到期日","贷款状态"};
            document.add(pubElements.obj2Grid(null, cnGrid, dataKey));
            //加入空行
            document.add(pubElements.emptyRow());

            document.close();

        } catch (Exception ex) 
        {
          ex.printStackTrace();
        }
    }
}
