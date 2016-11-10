package bss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import bss.model.pms.PurchaseRequired;

/**
 *
 * @Title: ExcelUtil
 * @Description: excel工具类读取
 * @author Li Xiaoxiao
 * @date  2016年9月12日,上午9:18:29
 *
 */
public class ExcelUtil {
	/**
	 * @throws FileNotFoundException 
	 *
	* @Title: readExcel
	* @Description: 读取 excel表格内容
	* author: Li Xiaoxiao 
	* @param @param path
	* @param @return     
	* @return List<PurchaseRequired>     
	 */
	public static List<?> readExcel(File path) throws Exception{
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		 FileInputStream fis = new FileInputStream(path);
	        Workbook workbook = WorkbookFactory.create(fis);
	        if (fis != null) {
	            fis.close();
	        }
	        Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	        	PurchaseRequired rq=new PurchaseRequired();
	        	if(row.getRowNum()>1){
	        		
	        
	        	 
	        		for (Cell cell : row) {
	        		
	        			 if(cell.getColumnIndex()==0){
//	        		
	        				 rq.setSeq(cell.getRichStringCellValue().toString());
	        			 }
	        			 if(cell.getColumnIndex()==1){
	        				
	        				 rq.setDepartment(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==2){
	        				 rq.setGoodsName(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==3){
	        				 rq.setStand(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==4){
	        				 rq.setQualitStand(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==5){
	        				 rq.setItem(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==6){

	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 Double value = cell.getNumericCellValue();
	 	        				if(value!=null){ 
	 		        				 rq.setPurchaseCount(new BigDecimal(cell.getNumericCellValue())); 
	 	        				 }
	        					
	        				 }
	        				
	        			 }
	        			 if(cell.getColumnIndex()==7){
	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 rq.setPrice(new BigDecimal(cell.getNumericCellValue()));
	        				 }
	        				 
	        			 }
	        			 if(cell.getColumnIndex()==8){
	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 rq.setBudget(new BigDecimal(cell.getNumericCellValue()));
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==9){
	        				 rq.setDeliverDate(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==10){
	        				 rq.setPurchaseType(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==11){
	        				 rq.setSupplier(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==12){
	        				 rq.setIsFreeTax(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==13){
	        				 rq.setGoodsUse(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==14){
	        				 rq.setUseUnit(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==15){
	        				 rq.setMemo(cell.getStringCellValue());
	        			 }
	        			 rq.setStatus("1");
	        			 rq.setHistoryStatus("0");
						}
	        		list.add(rq);
					}
	        	}
	        	
		
	
		
		return list;
		
	}

}
