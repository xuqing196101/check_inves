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

import org.apache.commons.lang.StringUtils;
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
	@Test
	public void subStr(){
		String path="E:\\web\\attach\\uploads\\supplier\\20170306";
//		path.
		String before = StringUtils.substringBeforeLast( path, "tmp0")+"tmp0";
		System.out.println(before);
	}
	
//	public void test() throws DocumentException{
//		//获取reader对象
//		SAXReader reader = new SAXReader();
//		//读取文件路径
//		File file = new File("d:/student.xml");
//		//读取文件
//		Document document = reader.read(file);
//		//获取根节点
//		Element  studentList= document.getRootElement();
//		//获取根节点的子标签student,得到的是一个List集合
//		List<Element> studentElementList = studentList.elements();
//		//遍历子标签student里的信息
//		for (Element student : studentElementList) {
//			//获取student标签id属性的值
//			String id = student.attributeValue("id");
//			//获取student标签子标签stuName的值
//			String stuName = student.elementText("stuName");
//			//获取student标签子标签stuAge的值
//			String stuAge = student.elementText("stuAge");
//			//获取student标签子标签sex的值
//			String sex = student.elementText("sex");
//			//输出获取的值
//			System.out.println(id+":"+stuName+":"+stuAge+":"+sex);
//		}
//	}
//	
//	@Test
//	public   void  replace( ) {
		// TODO Auto-generated method stub
//		String a ="a'b'c\"";
//		if(a.indexOf("'")>-1){
//			a = a.replace("'", "\\'");
//		} 
//		if(a.indexOf("\"")>-1){
//			a = a.replace("\"","\\\"");
//		}
//		System.out.println(a);
//		String str="passwd\"; ";
//		if(str.indexOf(";")>-1){
//			str = str.replace(";", "a");
//			System.out.println(str);
//		}
		
		/*String str="stty -echo";
		String b64 = getBase64(str);
		String f64 = getFromBase64(b64);
		System.out.println(b64);
		System.out.println(f64);
	}
*/
	
	/*
	   public  String getBase64(String str) {  
	        byte[] b = null;  
	        String s = null;  
	        try {  
	            b = str.getBytes("utf-8");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        if (b != null) {  
	            s = new BASE64Encoder().encode(b);  
	        }  
	        return s;  
	    }
	   
	   
	   // 解密  
	    public   String getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    } */
	    
	    @Test
	    public void testPath(){
	    String str=	test2.class.getClass().getClassLoader().getResource("/").getPath();
	    System.out.println(str);
	    }

	    
	    @Test
	    public void file() throws Exception{
	    	String path="E:\\web\\attach\\uploads\\expert\\20170405\\1491368693948.jpg ";
	    	
	  	String fileCopeToPath="E:\\项目需要";
	  	  	File file =new File(path);
	    	if(file.exists()){
	    	FileInputStream fis = new FileInputStream(file);
	    	int indexOf = path.lastIndexOf("\\");
	    	int length = path.length();
	    	System.out.println(length+"============="+path.substring(indexOf, length));
	    	FileOutputStream fos= new FileOutputStream(fileCopeToPath+ File.separator +path.substring(indexOf, length));
	    	byte[] b = new byte[fis.available()];
	    	int len = 0;
	    	while ((len = fis.read(b)) != -1)
	    	{
	    	fos.write(b, 0, len);
	    	fos.flush();
	    	}
	    	fos.close();
	    	fis.close();
	    	}
	    	  /* FileInputStream fis = new FileInputStream(path);
	           FileOutputStream fos = new FileOutputStream("d:\\01.mp3");
	    
	           int len = 0;
	           byte[] buf = new byte[1024];
	           while ((len = fis.read(buf)) != -1) {
	               fos.write(buf, 0, len);
	           }
	           fis.close();
	           fos.close();*/
	           
	    }
//	    @Test
//	    public void ee() throws DocumentException{
//	    	File xmlFile = new File("E:/项目需要/student.xml"); 
//			SAXReader reader = new SAXReader(); 
//			Document doc = reader.read(xmlFile); 
//			Element root = doc.getRootElement();
//			List<Element> nodes = root.elements();
//			List<Student> list=new ArrayList<Student>();
//			for(Element e : nodes) { 
//				Student student=new Student();
//				String elementText = e.elementText("stuName");
//				String id = e.attributeValue("id");
//				student.setId(Integer.valueOf(id));
//				student.setNaem(elementText);
//				list.add(student);
//		 
//			} 
//			   Collections.sort(list, new Comparator<Student>(){  
//		            public int compare(Student o1, Student o2) {  
//		                if(Integer.valueOf(o1.getId())>Integer.valueOf(o2.getId())){  
//		                    return 1;  
//		                }  
//		                if(o1.getId() == o2.getId()){  
//		                    return 0;  
//		                }  
//		                return -1;  
//		            }  
//		        });  
//			   
//			   for(Student s:list){
//				   System.out.println(s.getId());
//			   }
//			   
//	    }
}
