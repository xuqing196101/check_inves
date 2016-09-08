package ses.controller.sys.ems;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
* <p>Title:ExpertExtractionController </p>
* <p>Description: 专家抽取控制类</p>
* <p>Company: ses </p> 
* @author Xu Qing
* @date 2016-9-6下午4:05:14
 */
@Controller
@RequestMapping("/expert")
public class ExpertExtractionController {
/*	@Autowired
	private ExpertExtractionService service;*/
	
	/**
	 * 
	* @Title: automaticExtractionProject
	* @author Xu Qing
	* @date 2016-9-6 下午4:05:01  
	* @Description: 自动抽取项目列表页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/automaticExtractionProject")
	public String automaticExtractionProject(){
		
		return "ems/expertExtraction/automaticExtractionProject";
	}
	
	/**
	 * 
	* @Title: manualExtractionExpert
	* @author Xu Qing
	* @date 2016-9-6 下午5:04:38  
	* @Description: 自动抽取专家列表页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/automaticExtractionSpecialist")
	public String automaticExtractionSpecialist(){
		
		return "ems/expertExtraction/automaticExtractionSpecialist";
	}
	
	/**
	 * 
	* @Title: extractRecord
	* @author Xu Qing
	* @date 2016-9-7 上午10:21:55  
	* @Description: 查询专家名单列表页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/extractRecord")
	public String extractRecord(){
		
		return "ems/expertExtraction/extractRecord";
	}
	/**
	 * 
	* @Title: recordSheet
	* @author Xu Qing
	* @date 2016-9-7 下午2:22:40  
	* @Description: 专家抽取的记录表页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/recordSheet")
	public String recordSheet(){
		
		return "ems/expertExtraction/recordSheet";
	}
}
