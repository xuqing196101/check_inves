package bss.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import bss.model.pms.PurchaseRequired;

public class test2 {

	@Test
	public void test2() throws Exception{
		 FileInputStream fis = new FileInputStream("E:\\123123.xlsx");
	        Workbook workbook = WorkbookFactory.create(fis);
	        if (fis != null) {
	            fis.close();
	        }
	        Sheet sheet = workbook.getSheetAt(0);
	        int lastRowNum = sheet.getLastRowNum();
//	        for(int i = 0 ; i <= lastRowNum ; i++)
//		    {
//			  for(int j = 0; j < 17; j++){
//				  sheet.get
//			  }
//			 }
	        for (Row row : sheet) {
//				// 每一行
	        	
//				Object[] rowObject = null;
	        	if(row.getRowNum()>0){
	        		for (Cell cell : row) {
	        			if(cell.getColumnIndex()==0){
	        				System.out.println(row.getCell(0).getStringCellValue());
	        			}
	        		
//	        			 int type = row.getCell(0).getCellType();
//	        			 if(type==HSSFCell.CELL_TYPE_STRING){
//	        				 System.out.println(cell.getStringCellValue());
//	        			 }
//						switch (cell.getCellType()) {
//						case HSSFCell.CELL_TYPE_STRING:
//							System.out.println(cell.getStringCellValue());
//							break;

//						case HSSFCell.CELL_TYPE_NUMERIC:
//							System.out.println(cell.getNumericCellValue());
//							break;
//						
//						default:
//							break;
						}
	        			
	        			
					}
	        	}
				
	        	
		
	}
//	        }
	
	@Test
	public void add() throws InvalidFormatException, IOException{
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		 FileInputStream fis = new FileInputStream("E:\\123123.xlsx");
	        Workbook workbook = WorkbookFactory.create(fis);
	        if (fis != null) {
	            fis.close();
	        }
	        Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	        	PurchaseRequired rq=new PurchaseRequired();
	        	if(row.getRowNum()>0){
	        		
	        
	        	if(row.getRowNum()>0){
	        		for (Cell cell : row) {
	        		
	        			 if(cell.getColumnIndex()==0){
	        				 System.out.println(cell.getStringCellValue());
	        				 rq.setSeq(cell.getStringCellValue());
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
	        				 double value = cell.getNumericCellValue();
//	        				String value = cell.getStringCellValue();
	        				 System.out.println(value);
//	        				 rq.setPurchaseNum(cell.getStringCellValue());
	        			 }
	        			 if(cell.getColumnIndex()==7){
	        				 rq.setPrice(new BigDecimal(cell.getNumericCellValue()));
	        			 }
	        			 if(cell.getColumnIndex()==8){
	        				 rq.setBudget(new BigDecimal(cell.getNumericCellValue()));
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
						}
	        	}
	        		list.add(rq);
					}
	        	}
				System.out.println(list.size());
	        
	}
	
	@Test
	public void test3() throws IllegalAccessException, InvocationTargetException{
		/*Aaa aaa = new Aaa();
		Bbb bbb = new Bbb();
		aaa.setAaa("aaa");
		aaa.setBbb("bbb");
		aaa.setCcc("ccc");
		bbb.setDdd("ddd");
		BeanUtils.copyProperties(bbb, aaa);*/
	/*	double first = 3000/2999*100;
		 BigDecimal b = new BigDecimal(first); 
		  double a   = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(a);*/
		String str = "http://localhost:8080/member_arab/order_arab.html";
		String substring = str.substring(0,str.lastIndexOf("/"));
		String substring2 = substring.substring(0, substring.lastIndexOf("/"));
		System.out.println(substring2);
		
	}
	
	@Test
	public void test5(){
		//A D

		
		int n=10;
		boolean b=false;
		if((b==true)&&((n+=10)==20)){
			System.out.println("hhh");
		}else{
			System.out.println("kkk");
		}
	}
}
