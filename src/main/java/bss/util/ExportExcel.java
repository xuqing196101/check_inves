package bss.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportExcel {
  /*
   * 通用的Excel文件创建方法
   * title:首行标题:
   * sheets:sheet的tab标签页说明:
   * headers:表头：List存放表头
   * datas:数据行：list存放实体数据，map存放具体每一行数据，和headers对应。
   * rs:HttpServletResponse响应作用域，如果不为null，会直接将文件流输出到客户端，下载文件
   */
  public static void ExpExs(String title,String sheets,List headers,List<Map> datas,HttpServletResponse rs){
      try { 
          if(sheets== null || "".equals(sheets)){ sheets = "sheet"; }
            
          HSSFWorkbook workbook = new HSSFWorkbook(); 
          HSSFSheet sheet = workbook.createSheet(sheets); //+workbook.getNumberOfSheets()
           
          HSSFRow row;
          HSSFCell cell;
            
          // 设置这些样式
          HSSFFont font = workbook.createFont();
          font.setFontName(HSSFFont.FONT_ARIAL);//字体
          font.setFontHeightInPoints((short) 12);//字号 
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
          //font.setColor(HSSFColor.BLUE.index);//颜色
            
          HSSFCellStyle cellStyle= workbook.createCellStyle(); //设置单元格样式
         /* cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
          cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );*/
          cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
          cellStyle.setFont(font);
            
          //产生表格标题行       
          row = sheet.createRow(0);
          row.setHeightInPoints(20);
          for (int i = 0; i < headers.size(); i++) { 
              HSSFRichTextString text = new HSSFRichTextString(headers.get(i).toString());  
              cell = row.createCell(i);
              cell.setCellValue(text); 
              cell.setCellStyle(cellStyle);
              sheet.setColumnWidth(i, 3000);
          }  
          cellStyle= workbook.createCellStyle(); 
          cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
          cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
          cellStyle.setDataFormat((short)0x31);//设置显示格式，避免点击后变成科学计数法了
          //cellStyle.setWrapText(true);//设置自动换行
          if(datas!=null&&datas.size()>0){
           Map map;
           //遍历集合数据，产生数据行  
           for (int i=0; i <datas.size(); i++) { 
               row=sheet.createRow((i+1));
               row.setHeightInPoints(20);
               map = datas.get(i);
               for(int j=0;j<map.size();j++) {
                    cell = row.createCell(j);
                    cell.setCellStyle(cellStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if(map.get(j) != null) {
                        cell.setCellValue(new HSSFRichTextString(map.get(j).toString())); 
                    }else{
                        cell.setCellValue(new HSSFRichTextString(""));     
                   }
               }
           }   
          }
          //自动计算宽度，只针对数字，英文有效
          /*for (int i = 0; i < headers.size(); i++) { 
              sheet.autoSizeColumn((short)i);
          }*/
          rs.reset();
          rs.setContentType("multipart/form-data"); //自动识别
          rs.setHeader("Content-Disposition","attachment;filename=" + new String(title.getBytes("utf-8"),"iso8859-1")+".xls");
          //文件流输出到rs里
          workbook.write(rs.getOutputStream());
          rs.getOutputStream().flush();
          rs.getOutputStream().close();
      } catch (Exception e) {  
          System.out.println("#Error ["+e.getMessage()+"] ");
      } 
      System.out.println("["+sheets+"] 创建成功...");
  }   
   
   
  /*
   * 通用的Excel文件创建方法
   *    path:保存路径: 
   *   title:首行标题: 
   *  sheets:sheet的tab标签页说明: 
   * headers:表头：List存放表头  
   *   datas:数据行：list存放实体数据，map存放具体每一行数据，和headers对应。
   */
  public static void ExpExs(String path,String title,String sheets,List headers,List<Map> datas){
      try { 
          if(sheets== null || "".equals(sheets)){ sheets = "sheet"; }
            
          boolean isExist = new File(path).exists();
          if(!isExist){
              HSSFWorkbook workbook = new HSSFWorkbook();
              HSSFSheet sheet = workbook.createSheet(sheets);
                
              FileOutputStream out = new FileOutputStream(new File(path));
              workbook.write(out);
              out.flush();
              out.close();
          }
          FileInputStream file = new FileInputStream(new File(path));
          HSSFWorkbook workbook = new HSSFWorkbook(file);
            
          HSSFSheet sheet = null;
          if(!isExist){
              sheet = workbook.getSheetAt(0);
          }else{
              if(workbook.getSheet(sheets) == null){
                  sheet = workbook.createSheet(sheets); //+workbook.getNumberOfSheets()
              }else{
                  System.out.println("文件：["+path+"] ["+sheets+"] 已经存在...");
                  System.out.println("");
                  return;
              }
          }
          HSSFRow row;
          HSSFCell cell;
            
          // 设置这些样式
          HSSFFont font = workbook.createFont();
          font.setFontName(HSSFFont.FONT_ARIAL);//字体
          font.setFontHeightInPoints((short) 16);//字号 
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
          //font.setColor(HSSFColor.BLUE.index);//颜色
            
          HSSFCellStyle cellStyle= workbook.createCellStyle(); //设置单元格样式
          cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
          cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER );
          cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
          cellStyle.setFont(font);
            
          //产生表格标题行       
          row = sheet.createRow(0);
          row.setHeightInPoints(20);
          for (int i = 0; i < headers.size(); i++) { 
              HSSFRichTextString text = new HSSFRichTextString(headers.get(i).toString());  
              cell = row.createCell(i);
              cell.setCellValue(text); 
              cell.setCellStyle(cellStyle);
          }  
            
            
          cellStyle= workbook.createCellStyle(); 
          cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
          cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
          cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
          cellStyle.setDataFormat((short)0x31);//设置显示格式，避免点击后变成科学计数法了
          //cellStyle.setWrapText(true);//设置自动换行
          Map map;
          //遍历集合数据，产生数据行  
          for (int i=0; i <datas.size(); i++) { 
              row=sheet.createRow((i+1));
              row.setHeightInPoints(20);
              map = datas.get(i);

              for(int j=0;j<map.size();j++) {
                   cell = row.createCell(j);
                   cell.setCellStyle(cellStyle);

                   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                   if(map.get(j) != null) {
                       cell.setCellValue(new HSSFRichTextString(map.get(j).toString())); 
                   }else{
                       cell.setCellValue(new HSSFRichTextString(""));     
                  }
              }
          }   
            
          for (int i = 0; i < headers.size(); i++) { 
              sheet.autoSizeColumn((short)i);
          }
            
          FileOutputStream out = new FileOutputStream(new File(path));
          workbook.write(out);
          out.flush();
          out.close();
            
          /*
          HSSFRow row = sheet.createRow(sheets);
          HSSFCell cell = null;
          cell=row.createCell(sheets);
          cell.setCellValue(new HSSFRichTextString("-["+sheets+"]-"));
          sheets=sheets+2;//中间空一行
          row=sheet.createRow(sheets);
          */
            
      } catch (Exception e) {  
          System.out.println("#Error ["+e.getMessage()+"] ");
      } 
      System.out.println("文件：["+path+"] ["+sheets+"] 创建成功...");
      System.out.println("");
  }


	/**
	 *〈简述〉freemark根据模板生成excel
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param dataMap 封装业务数据
	 * @param templateName 模板名称
	 * @param tempPath 生成文件临时存放路径
	 * @param exceFileName 生成文件名称
	 * @param response
	 * @param request
	 */
	public static void createExcel(Map<String, Object> dataMap, String templateName,
		String tempPath, String exceFileName, HttpServletResponse response, HttpServletRequest request) {
  		try {  
  			//创建配置实例 
	    	Configuration configuration = new Configuration();
	    	
	    	//设置编码
	        configuration.setDefaultEncoding("UTF-8");
	            
	        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/ses/document/template/");  
            configuration.setDirectoryForTemplateLoading(new File(path));// 此处是本类Class.getResource()相对于模版文件的相对路径
	        //获取模板 
	        Template template1 = configuration.getTemplate(templateName, "UTF-8");
	        String pathLinShi = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/ses/document/template/");
	        String context = template1.toString();
	        String fileNam = UUID.randomUUID().toString() + ".ftl";
	        
	        File file = new File(pathLinShi + File.separator + fileNam);
	        
	        /* 创建写入对象 */
	        FileWriter fileWriter = new FileWriter(file);
	        /* 创建缓冲区 */
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
	        /* 写入字符串 */
	        writer.write(context);
	        
	        /* 关掉对象 */
	        writer.flush();
	        writer.close();
	        fileWriter.flush();
	        fileWriter.close();
	        
	        Template template = configuration.getTemplate(fileNam, "UTF-8");
	        
	        //封装文件名
	        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_export" ;
	           
	        //输出文件
	        File outFile = new File(tempPath+File.separator+fileName);
	        
	        //如果输出目标文件夹不存在，则创建
	        if (!outFile.getParentFile().exists()){
	            outFile.getParentFile().mkdirs();
	        }
	        
	        //将模板和数据模型合并生成文件 
	        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
	        template.process(dataMap, out);
	        
	        //关闭流
	        out.flush();
	        out.close();
	        outFile.setWritable(false);
	        file.delete();
	     // 设置response的编码方式  
            response.setContentType("application/x-msdownload");
            String userAgent = request.getHeader("user-agent").toLowerCase();  
            String outFileName = "";
            if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {  
                    // win10 ie edge 浏览器 和其他系统的ie  
            	outFileName = URLEncoder.encode(exceFileName, "UTF-8");  
            } else {  
                    // fe  
            	outFileName = new String(exceFileName.getBytes("UTF-8"), "iso-8859-1");  
            }  
            // 设置附加文件名  
            response.setHeader("Content-Disposition", "attachment;filename="  
                    + outFileName);  
  
            // 读出文件到i/o流  
            FileInputStream fis = new FileInputStream(outFile);  
            BufferedInputStream buff = new BufferedInputStream(fis);  
            byte[] b = new byte[1024];// 相当于我们的缓存  
  
            long k = 0;// 该值用于计算当前实际下载了多少字节  
  
            // 从response对象中得到输出流,准备下载  
  
            OutputStream myout = response.getOutputStream();  
            // 开始循环下载  
            while (k < outFile.length()) {  
                int j = buff.read(b, 0, 1024);  
                k += j;  
                // 将b中的数据写到客户端的内存  
                myout.write(b, 0, j);  
            }  
            fis.close();
            buff.close();
            // 将写入到客户端的内存的数据,刷新到磁盘  
            myout.flush();  
            myout.close();  
            outFile.delete();
      } catch (Exception e) { 
    	  e.printStackTrace();
      }  
	}
}
