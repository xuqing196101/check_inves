package ses.controller.sys.sms;

import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.dao.bms.CategoryMapper;
import ses.dao.bms.CategoryQuaMapper;
import ses.dao.bms.EngCategoryMapper;
import ses.dao.bms.QualificationLevelMapper;
import ses.dao.bms.QualificationMapper;
import ses.model.bms.CategoryQua;
import ses.model.bms.Qualification;
import ses.model.bms.QualificationLevel;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
//	@Autowired
//	private DictionaryTypeMapper dictionaryTypeMapper;
	@Autowired
	private QualificationMapper qualificationMapper;
//	@Autowired
//	private EngCategoryMapper engCategoryMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private QualificationLevelMapper qualificationLevelMapper;
	
	@Autowired
	private CategoryQuaMapper categoryQuaMapper;
	
	@RequestMapping("/app")
	public String exlce() throws Exception{
		 FileInputStream fis = new FileInputStream("C:\\Users\\Lee\\Desktop\\新建 Microsoft Excel 97-2003 工作表.xls");
	        Workbook workbook = WorkbookFactory.create(fis);
	        if (fis != null) {
	            fis.close();
	        }
	        int count=0;
	        Sheet sheet = workbook.getSheetAt(0);
	        
	        StringBuffer sb=new StringBuffer();
	        StringBuffer sbf=new StringBuffer();
	        for (Row row : sheet) {
	        	
	        		for (Cell cell : row) {
	        			if(cell.getColumnIndex()==2){
	        				count++;
	        				String code = row.getCell(0).getStringCellValue();
	        				String cate = row.getCell(1).getStringCellValue();
	        				
	        				String value = row.getCell(2).getStringCellValue();
	        				if(value!=null){
	        					String[] strs = value.split("或");
		        				
	        					for(String s:strs){
	        						String cid = categoryMapper.getId(cate.trim(),code.trim());
		        					String id = UUID.randomUUID().toString().replaceAll("-", "");
		        					String qid = qualificationMapper.getIdByName(s);
		        					List<CategoryQua> list = categoryQuaMapper.findListSupplier(cid,4);
		        					if(list.size()<1){
		        						sbf.append(cate).append(";");
		        						String str="insert into  T_SES_BMS_CATEGORY_QUA (ID, CATEGORY_ID,QUA_ID ,QUA_TYPE )  values ('"+id+"', '"+cid+"','"+qid+"','4')";
			        					sb.append(str).append(";");
		        					}	
		        				
		        				}
	        				}
	        				
	        				
	        			}
	        			
	    			}
        			
        	}
	        System.out.println(sbf.toString()+"--");
	        System.out.println(sb.toString()+"--");
		return "";
	}

	
	@RequestMapping("/qua")
	public String qualification() throws Exception{
		  StringBuffer snb=new StringBuffer();
		   StringBuffer sb=new StringBuffer();
//		 FileInputStream fis = new FileInputStream("C:\\Users\\Lee\\Desktop\\资质、文件等级去重 .xlsx");
//	        Workbook workbook = WorkbookFactory.create(fis);
//	        if (fis != null) {
//	            fis.close();
//	        }
//	        int count=0;
//	        Sheet sheet = workbook.getSheetAt(0);
//	        
//	        StringBuffer sb=new StringBuffer();
//	        StringBuffer snb=new StringBuffer();
//	        for (Row row : sheet) {
//	        	
//	        		for (Cell cell : row) {
//	        			if(cell.getColumnIndex()==0){
//	        				 
//	        				String name = row.getCell(0).getStringCellValue();
//	        				String qua = qualificationMapper.getIdByName(name);
//	        				if(qua==null){
////	        					snb.append(name).append(";");
//	        				}
//	        				List<QualificationLevel> list = qualificationLevelMapper.findList(qua);
//	        				if(list.size()<1){
////	        					String id = UUID.randomUUID().toString().replaceAll("-", "");
////	        					String str="insert into  T_SES_BMS_QUALIFCATE_LEVEL (ID, QUALIFCATION_ID ,GRADE ) values ('"+id+"', '"+qua+"','7AFF91A26FB046ECAD6CA751490BD098')";
//	        					
//	        					snb.append(name).append(";");
//	        				       }
//		        				}
//	        				}
//	        				
//	        				
//	        			
//        			
//        	}
//	        System.out.println(snb.toString()+"--");
//	        System.out.println(sb.toString()+"--");
//		List<Qualification> list = qualificationMapper.findList(null, null);
//		for(Qualification q:list){
//			List<QualificationLevel> ql = qualificationLevelMapper.findList(q.getId());
//			if(ql.size()<1){
//			String id = UUID.randomUUID().toString().replaceAll("-", "");
//			String str="insert into  T_SES_BMS_QUALIFCATE_LEVEL (ID, QUALIFCATION_ID ,GRADE ) values ('"+id+"', '"+q.getId()+"','7AFF91A26FB046ECAD6CA751490BD098')";
//			sb.append(str).append(";");
//			snb.append(q.getName()).append(";");
//		       }
//			}
//		}
//		System.out.println(sb.toString()+"--");
		
		
		return "";
	}
	
	
	
	
	
}
