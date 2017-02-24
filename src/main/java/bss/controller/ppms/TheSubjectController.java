package bss.controller.ppms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.ppms.theSubject;
import bss.service.ppms.theSubjectService;

/**
 * 
 * 版权：(C) 版权所有 <简述>标的控制层 <详细描述>
 * 
 * @author Wang Wenshuai
 * @version
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/theSubject")
public class TheSubjectController extends BaseController {
	/** SCCUESS */
	private static final String SCCUESS = "SCCUESS";
	/** FAILED */
	private static final String FAILED = "FAILED";
	/** ERROR */
	private static final String ERROR = "ERROR";

	@Autowired
	private theSubjectService theSubjectService;

	/**
	 * 
	 * 〈简述〉单行插入标的 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(theSubject subject) {
		theSubjectService.insert(subject);
		return "INSERT";
	}
	
	/**
	 * 
	 * 〈简述〉批量插入标的 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * 后期改动@author Ma Mingwei
	 * @return
	 */
	@RequestMapping("/batchInsert")
	@ResponseBody
	public String batchInsert(@RequestBody List<theSubject> subjectList) {
		
		for (theSubject subject : subjectList) {
			theSubjectService.insert(subject);
		}
		//theSubjectService.insertList(subjectList);
		return "INSERT";
	}
	
	/**
	 * 
	 * 〈简述〉批量插入标的 〈详细描述〉
	 * 
	 * @author Ma Mingwei
	 * @return
	 */
	@RequestMapping("/batchUpdate")
	@ResponseBody
	public String batchUpdate(@RequestBody List<theSubject> subjectChangeList) {
		
		for (theSubject subject : subjectChangeList) {
			theSubjectService.update(subject);
		}
		//theSubjectService.insertList(subjectList);
		return "UPDATE";
	}

	/**
	 * 
	 * 〈简述〉集合 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @param subject
	 * @return
	 */
	public String list(theSubject subject, String projectId, String packageId) {

		List<theSubject> list = theSubjectService.list(subject);

		return "";
	}

}
