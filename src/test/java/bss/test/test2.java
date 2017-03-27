package bss.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import ses.model.bms.User;
import bss.model.pms.PurchaseRequired;

public class test2 {

    @Test
    public void test123(){
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
    
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

		
		/*int n=10;
		boolean b=false;
		if((b==true)&&((n+=10)==20)){
			System.out.println("hhh");
		}else{
			System.out.println("kkk");
		}*/
		Map<String,Object> map = new HashMap<>();
		map.put("aa", "aa");
		System.out.println(map.isEmpty());
	}
	
	@Test
	public void test6(){
		StringBuffer sb=new StringBuffer();
		sb.append("1").append(",");
		sb.append("2");
		System.out.print(sb.toString());
		
	}
	
	@Test
	public void testFile(){
		copyFolder("E:\\web\\attach\\uploads\\supplier\\20170306", "E:/2");  
		
		
	}
	
	
	public void copyFolder(String oldPath, String newPath) {  
        try {  
            // 如果文件夹不存在，则建立新文件夹  
            (new File(newPath)).mkdirs();  
            //读取整个文件夹的内容到file字符串数组，下面设置一个游标i，不停地向下移开始读这个数组  
            File filelist = new File(oldPath);  
            String[] file = filelist.list();  
            //要注意，这个temp仅仅是一个临时文件指针  
            //整个程序并没有创建临时文件  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                //如果oldPath以路径分隔符/或者\结尾，那么则oldPath/文件名就可以了  
                //否则要自己oldPath后面补个路径分隔符再加文件名  
                //谁知道你传递过来的参数是f:/a还是f:/a/啊？  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                  
                //如果游标遇到文件  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath  
                            + "/"  + (temp.getName()).toString());  
                    byte[] bufferarray = new byte[1024 * 64];  
                    int prereadlength;  
                    while ((prereadlength = input.read(bufferarray)) != -1) {  
                        output.write(bufferarray, 0, prereadlength);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                //如果游标遇到文件夹  
                if (temp.isDirectory()) {  
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("复制整个文件夹内容操作出错");  
        }  
    } 

	@Test
	public void quchong(){
		List<User> list=new ArrayList<User>();
		User u=new User();
		u.setLoginName("1");
		
		User u2=new User();
		u2.setLoginName("2");
		 
		list.add(u2);
		list.add(u);
		List<User> list2=new ArrayList<User>();
		User u3=new User();
		u3.setLoginName("2");
//		list2.add("a");
//		list2.add("c");
		list2.add(u3);
//		Map<String,Object> map=new HashMap<String,Object>();
		for(User us:list2){
			if(list.contains(us)){
				System.out.println("ss");
			}
		}
		
		System.out.println(list.size());
		
		   
//		list.
//		map.put(  "1", list);
		
	}
	
	
	 
	@Test
	public void resetpassowoord(){
		
		 String randomCode = "";
		  Md5PasswordEncoder md5 = new Md5PasswordEncoder();
          // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true 表示：生成24位的Base64版
          md5.setEncodeHashAsBase64(false);
          String pwd = md5.encodePassword("123456", "ZK35bihnlMddCAK");
		
          System.out.println(pwd);
	}
	
	@Test
	public void split(){
		double d = 1212312334.5678;
		DecimalFormat df = new DecimalFormat("#,###.00");
		 
		System.out.println(df.format(d));
	}

}
