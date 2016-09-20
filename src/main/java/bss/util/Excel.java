package bss.util;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
/**
 * 
 * @Title: Excel
 * @Description:  导出excel工具类
 * @author Li Xiaoxiao
 * @date  2016年9月18日,下午4:58:11
 *
 */
public class Excel<T> {

	 /**
	  * 
	 * @Title: generateHeader
	 * @Description: 表头设置
	 * author: Li Xiaoxiao 
	 * @param @param workbook
	 * @param @param sheet
	 * @param @param headerColumns     
	 * @return void     
	 * @throws
	  */
    public  void generateHeader(HSSFWorkbook workbook,HSSFSheet sheet,String[] headerColumns){

        Row row = sheet.createRow(0);

        for(int i=0;i<headerColumns.length;i++){
            Cell cell = row.createCell(i);
            String[] column = headerColumns[i].split("_#_");

            cell.setCellValue(column[0]);

        }
    }
   
    /**
     * 
    * @Title: creatAuditSheet
    * @Description: 导出excel表格
    * author: Li Xiaoxiao 
    * @param @param workbook
    * @param @param sheetName
    * @param @param dataset
    * @param @param headerColumns
    * @param @param fieldColumns
    * @param @return
    * @param @throws NoSuchMethodException
    * @param @throws IllegalAccessException
    * @param @throws IllegalArgumentException
    * @param @throws InvocationTargetException     
    * @return HSSFSheet     
    * @throws
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public  HSSFSheet creatAuditSheet(HSSFWorkbook workbook,String sheetName,
            List<T> dataset,String[] headerColumns,String[] fieldColumns) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        HSSFSheet sheet = workbook.createSheet(sheetName);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        
        
        generateHeader(workbook,sheet,headerColumns); 
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int rowNum = 0;
        for(T t:dataset){
        	rowNum++ ;
            Row row = sheet.createRow(rowNum);
            row.setHeightInPoints(20);
            Class clazz = t.getClass();
            sheet.autoSizeColumn((short)fieldColumns.length);
            for(int i = 0; i < fieldColumns.length; i++){              
            String fieldName = fieldColumns[i] ; 
            String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);                  
                try {                    
                
                    Method getMethod;
                    getMethod = clazz.getMethod(getMethodName, null );
                    Object value = getMethod.invoke(t, null);
                    String cellValue = "";
                    Double num=null;
 
                    Cell cell = row.createCell(i);
                    if (value instanceof Date){
                        Date date = (Date)value;
                        cellValue = sd.format(date);
                        cell.setCellValue(cellValue); 
                    }else if(value instanceof BigDecimal){
                    	Double bd=Double.valueOf( value.toString());
                    	num=bd;
                   	 cell.setCellValue(num);
                	 cell.setCellStyle(cellStyle);
                    }else if( value instanceof Integer ){
                    	Integer in=Integer.valueOf(value.toString());
                   	 cell.setCellValue(in);
                	 cell.setCellStyle(cellStyle);
                    }
                    else{
                        cellValue = null != value ? value.toString() : "";
                        cell.setCellValue(cellValue); 
                    }                    
                 
//                  if(){
                	 
//                  }else{
                	 
//                  }
                    
                  
                } catch (Exception e) {
                    
                }
            }            
        }
        return sheet;        
    }
    
}
