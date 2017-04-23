package ses.controller.sys.sms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
//	@Autowired
//	private DictionaryTypeMapper dictionaryTypeMapper;
//	@Autowired
//	private QualificationMapper qualificationMapper;
	
	@RequestMapping("/app")
	public String exlce() throws Exception{
//		 FileInputStream fis = new FileInputStream("E:\\资质与等级关联表(1).xlsx");
//	        Workbook workbook = WorkbookFactory.create(fis);
//	        if (fis != null) {
//	            fis.close();
//	        }
//	        int count=0;
//	        Sheet sheet = workbook.getSheetAt(0);
//	        
//	        StringBuffer sb=new StringBuffer();
//	        for (Row row : sheet) {
//	        	count++;
//	        		for (Cell cell : row) {
//	        			if(cell.getColumnIndex()==1){
//	        				
//	        				String quas = row.getCell(0).getStringCellValue();
//	        				String[] qua = quas.split("或");
//	        				String value = row.getCell(1).getStringCellValue();
//	        				String[] strs = value.split("、");
//	        				
//	        				for(String q:qua){
//	        					for(String s:strs){
//		        					String level = dictionaryTypeMapper.getIdByName(s);
//		        					String id = UUID.randomUUID().toString().replaceAll("-", "");
//		        					String qid = qualificationMapper.getIdByName(q);
//		        					String str="insert into  T_SES_BMS_QUALIFCATE_LEVEL (ID, QUALIFCATION_ID ,GRADE ) values ('"+id+"', '"+qid+"','"+level+"')";
//		        					sb.append(str).append(";");
//		        				}
//	        				}
//	        				
//	        			}
//	        			
//	    			}
//        			
//        	}
	        
//	        System.out.println(sb.toString()+"--");
		return "";
	}

}
