/**
 * 
 */
package ses.controller.sys.ems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamQuestionType;
import ses.model.ems.ExamRule;
import ses.model.ems.ExamUserAnswer;
import ses.model.ems.ExamUserScore;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAudit;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExamQuestionTypeServiceI;
import ses.service.ems.ExamRuleServiceI;
import ses.service.ems.ExamUserAnswerServiceI;
import ses.service.ems.ExamUserScoreServiceI;
import ses.service.ems.ExpertAuditService;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;


/**
 * @Title:ExpertExamController 
 * @Description: 专家考试Controller
 * @author ZhaoBo
 * @date 2016-9-7上午9:51:01
 */
@Controller
@RequestMapping("/expertExam")
public class ExpertExamController extends BaseSupplierController{
	@Autowired
	private ExamQuestionServiceI examQuestionService;
	@Autowired
	private ExamQuestionTypeServiceI examQuestionTypeService;
	@Autowired
	private ExamUserScoreServiceI examUserScoreService;
	@Autowired
	private ExamRuleServiceI examRuleService;
	@Autowired
	private ExamUserAnswerServiceI examUserAnswerService;
	@Autowired
	private ExpertService expertService;
	@Autowired
	private UserServiceI userService;
	@Autowired
	private ExpertAuditService expertAuditService;
	/**
	 * 
	 * @Title: returnLawExpert
	 * @author ZhaoBo
	 * @date 2016-9-7 上午10:55:35  
	 * @Description: 跳转到法律类专家题库 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/returnLawExpert")
	public String returnLawExpert(){
		return "ses/ems/exam/expert/law/list";
	}

	/**
	 * 
	 * @Title: searchTecExpPool
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:01:41  
	 * @Description: 查询技术类专家题库 
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/searchTecExpPool")
	public String searchTecExpPool(@CurrentUser User user,Model model,Integer page,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String questionTypeId = request.getParameter("questionTypeId");
		String topic = request.getParameter("topic");
		map.put("kind", "0");
		if(questionTypeId !=null && !questionTypeId.equals("")){
			map.put("questionTypeId", Integer.parseInt(questionTypeId));
		}
		if(topic != null && !topic.equals("")){
			map.put("topic", "%"+topic+"%");
		}
		if(page==null){
			page = 1;
		}
		if(null != user && "4".equals(user.getTypeName())){
	        //判断是否 是资源服务中心 
			map.put("page", page.toString());
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<ExamQuestion> technicalList = examQuestionService.findExpertQuestionList(map);
			model.addAttribute("technicalList",new PageInfo<ExamQuestion>(technicalList));
			model.addAttribute("authType", 4);
	    }else{
	    	model.addAttribute("technicalList",new PageInfo<ExamQuestion>(new ArrayList<ExamQuestion>()));
	    }

		
		model.addAttribute("topic", topic);
		model.addAttribute("questionTypeId", questionTypeId);
		return "ses/ems/exam/expert/technical/list";
	}

	/**
	 * 
	 * @Title: searchComExpPool
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:02:26  
	 * @Description: 查询商务类专家题库  经济类专家题库
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/searchComExpPool")
	public String searchComExpPool(@CurrentUser User user,Model model,Integer page,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String questionTypeId = request.getParameter("questionTypeId");
		String topic = request.getParameter("topic");
		map.put("kind", "1");
		if(questionTypeId !=null && !questionTypeId.equals("")){
			map.put("questionTypeId", Integer.parseInt(questionTypeId));
		}
		if(topic != null && !topic.equals("")){
			map.put("topic", "%"+topic+"%");
		}
		if(page==null){
			page = 1;
		}
		if(null != user && "4".equals(user.getTypeName())){
	        //判断是否 是资源服务中心 
			map.put("page", page.toString());
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<ExamQuestion> commerceList = examQuestionService.findExpertQuestionList(map);
			model.addAttribute("commerceList",new PageInfo<ExamQuestion>(commerceList));
		    model.addAttribute("authType", 4);
	    }else{
	    	model.addAttribute("commerceList",new PageInfo<ExamQuestion>(new ArrayList<ExamQuestion>()));
	    }

		
		model.addAttribute("topic", topic);
		model.addAttribute("questionTypeId", questionTypeId);
		return "ses/ems/exam/expert/commerce/list";
	}

	/**
	 * 
	 * @Title: searchLawExpPool
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:02:45  
	 * @Description: 查询法律类专家题库 
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/searchLawExpPool")
	public String searchLawExpPool(Model model,Integer page,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String questionTypeId = request.getParameter("questionTypeId");
		String topic = request.getParameter("topic");
		map.put("kind", 2);
		if(questionTypeId !=null && !questionTypeId.equals("")){
			map.put("questionTypeId", Integer.parseInt(questionTypeId));
		}
		if(topic != null && !topic.equals("")){
			map.put("topic", "%"+topic+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamQuestion> lawList = examQuestionService.findExpertQuestionList(map);
		model.addAttribute("list",new PageInfo<ExamQuestion>(lawList));
		model.addAttribute("topic", topic);
		model.addAttribute("questionTypeId", questionTypeId);
		return "ses/ems/exam/expert/law/list";
	}

	/**
	 * 
	 * @Title: addLaw
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:06:21  
	 * @Description: 跳转到法律题库增加页面  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addLaw")
	public String addLaw(Model model){
		optionNum(model);
		return "ses/ems/exam/expert/law/add";
	}

	/**
	 * 
	 * @Title: optionNum
	 * @author ZhaoBo
	 * @date 2016-10-13 上午10:17:07  
	 * @Description: 选项数量 
	 * @param @param model      
	 * @return void
	 */
	public void optionNum(Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("three", "A,B,C");
		map.put("four", "A,B,C,D");
		map.put("five", "A,B,C,D,E");
		map.put("six", "A,B,C,D,E,F");
		map.put("seven", "A,B,C,D,E,F,G");
		map.put("eight", "A,B,C,D,E,F,G,H");
		map.put("nine", "A,B,C,D,E,F,G,H,I");
		map.put("ten", "A,B,C,D,E,F,G,H,I,J");
		model.addAttribute("opt", JSONSerializer.toJSON(map).toString());
	}

	/**
	 * 
	 * @Title: addTechnical
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:06:21  
	 * @Description: 跳转到技术题库增加页面  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addTechnical")
	public String addTechnical(Model model){
		optionNum(model);
		return "ses/ems/exam/expert/technical/add";
	}

	/**
	 * 
	 * @Title: addCommerce
	 * @author ZhaoBo
	 * @date 2016-9-9 上午11:24:21  
	 * @Description: 跳转到商务题库增加页面  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addCommerce")
	public String addCommerce(Model model){
		optionNum(model);
		return "ses/ems/exam/expert/commerce/add";
	}

	public String[] saveOption(){
		String items[] = new String[11];
		items[3] = "A,B,C";
		items[4] = "A,B,C,D";
		items[5] = "A,B,C,D,E";
		items[6] = "A,B,C,D,E,F";
		items[7] = "A,B,C,D,E,F,G";
		items[8] = "A,B,C,D,E,F,G,H";
		items[9] = "A,B,C,D,E,F,G,H,I";
		items[10] = "A,B,C,D,E,F,G,H,I,J";
		return items;
	}

	/**
	 * 
	 * @Title: saveToLaw
	 * @author ZhaoBo
	 * @date 2016-10-13 下午1:33:10  
	 * @Description: 增加法律类的题库 
	 * @param @param model
	 * @param @param request
	 * @param @param examQuestion
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveToLaw")
	public String saveToLaw(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String queType = request.getParameter("queType");
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/law/add";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			HashMap<String,Object> lmap = new HashMap<String,Object>();
			lmap.put("questionTypeId", Integer.parseInt(queType));
			lmap.put("topic", topic.trim());
			lmap.put("personType", 1);
			lmap.put("kind", 2);
			List<ExamQuestion> topicOnly = examQuestionService.selectByTopic(lmap);
			if(topicOnly.size()!=0){
				for(int i=0;i<topicOnly.size();i++){
					if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
						model.addAttribute("ERR_topic", "该题干已存在");
						error = "topic";
						break;
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			map.put("option",sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					error = "option";
					model.addAttribute("ERR_option", "选项内容不能为空");
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							error = "option";
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i = 0;i<answer.length;i++){
					sb.append(answer[i]);
				}
				map.put("answer",sb.toString());
			}
		}
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			optionNum(model);
			map.put("type",queType);
			map.put("topic",topic);
			map.put("options",request.getParameter("options"));
			model.addAttribute("errData", map);
			return "ses/ems/exam/expert/law/add";
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setPersonType(1);
		examQuestion.setKind("2");
		examQuestion.setCreatedAt(new Date());
		examQuestion.setUpdatedAt(new Date());
		examQuestion.setAnswer(sb.toString());
		examQuestionService.insertSelective(examQuestion);
		return "redirect:searchLawExpPool.html";
	}


	/**
	 * 
	 * @Title: saveToTec
	 * @author ZhaoBo
	 * @date 2016-10-13 下午4:04:49  
	 * @Description: 增加技术类的题库  
	 * @param @param request
	 * @param @param model
	 * @param @param examQuestion
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveToTec")
	public String saveToTec(HttpServletRequest request,Model model,ExamQuestion examQuestion){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String queType = request.getParameter("queType");
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/technical/add";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			HashMap<String,Object> tmap = new HashMap<String,Object>();
			tmap.put("topic", topic.trim());
			tmap.put("questionTypeId", Integer.parseInt(queType));
			List<ExamQuestion> topicOnly = examQuestionService.selectByTecTopic(tmap);
			if(topicOnly.size()!=0){
				for(int i=0;i<topicOnly.size();i++){
					if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
						model.addAttribute("ERR_topic", "该题干已存在");
						error = "topic";
						break;
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			map.put("option",sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					error = "option";
					model.addAttribute("ERR_option", "选项内容不能为空");
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							error = "option";
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i = 0;i<answer.length;i++){
					sb.append(answer[i]);
				}
				map.put("answer",sb.toString());
			}
		}
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			optionNum(model);
			map.put("type",queType);
			map.put("topic",topic);
			map.put("options",request.getParameter("options"));
			model.addAttribute("errData", map);
			return "ses/ems/exam/expert/technical/add";
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setPersonType(1);
		examQuestion.setKind("0");
		examQuestion.setCreatedAt(new Date());
		examQuestion.setUpdatedAt(new Date());
		examQuestion.setAnswer(sb.toString());
		examQuestionService.insertSelective(examQuestion);
		return "redirect:searchTecExpPool.html";
	}

	/**
	 * 
	 * @Title: saveToCom
	 * @author ZhaoBo
	 * @date 2016-10-13 下午4:05:24  
	 * @Description: 增加商务类的题库 
	 * @param @param request
	 * @param @param examQuestion
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveToCom")
	public String saveToCom(HttpServletRequest request,ExamQuestion examQuestion,Model model){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String queType = request.getParameter("queType");
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/commerce/add";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			HashMap<String,Object> cmap = new HashMap<String,Object>();
			cmap.put("questionTypeId", Integer.parseInt(queType));
			cmap.put("topic", topic.trim());
			cmap.put("personType", 1);
			cmap.put("kind", 1);
			List<ExamQuestion> topicOnly = examQuestionService.selectByTopic(cmap);
			if(topicOnly.size()!=0){
				for(int i=0;i<topicOnly.size();i++){
					if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
						model.addAttribute("ERR_topic", "该题干已存在");
						error = "topic";
						break;
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			map.put("option",sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					error = "option";
					model.addAttribute("ERR_option", "选项内容不能为空");
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							error = "option";
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i = 0;i<answer.length;i++){
					sb.append(answer[i]);
				}
				map.put("answer",sb.toString());
			}
		}
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			optionNum(model);
			map.put("type",queType);
			map.put("topic",topic);
			map.put("options",request.getParameter("options"));
			model.addAttribute("errData", map);
			return "ses/ems/exam/expert/commerce/add";
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setPersonType(1);
		examQuestion.setKind("1");
		examQuestion.setCreatedAt(new Date());
		examQuestion.setUpdatedAt(new Date());
		examQuestion.setAnswer(sb.toString());
		examQuestionService.insertSelective(examQuestion);
		return "redirect:searchComExpPool.html";
	}

	/**
	 * 
	 * @Title: editLaw
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:06:58  
	 * @Description: 修改法律类的题库页面  
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editLaw")
	public String editLaw(Model model,HttpServletRequest request){
		lawQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/law/edit";
	}

	/**
	 * 
	 * @Title: editTechnical
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:06:58  
	 * @Description: 修改技术类的题库页面 
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editTechnical")
	public String editTechnical(Model model,HttpServletRequest request){
		tecQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/technical/edit";
	}

	/**
	 * 
	 * @Title: editCommerce
	 * @author ZhaoBo
	 * @date 2016-9-8 上午9:16:05  
	 * @Description: 修改商务类的题库页面 
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editCommerce")
	public String editCommerce(Model model,HttpServletRequest request){
		comQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/commerce/edit";
	}

	/**
	 * 
	 * @Title: editToLaw
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:07:55  
	 * @Description: 修改并保存法律类的题库  
	 * @param @param model
	 * @param @param request
	 * @param @param examPool
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editToLaw")
	public String editToLaw(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		String id = request.getParameter("id");
		examQuestion.setId(id);
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String content = request.getParameter("content");
		String queType = request.getParameter("queType");
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/law/edit";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			if(!content.trim().equals(topic.trim())){
				HashMap<String,Object> tmap = new HashMap<String,Object>();
				tmap.put("questionTypeId", Integer.parseInt(queType));
				tmap.put("topic", topic.trim());
				tmap.put("kind",2);
				tmap.put("personType", 1);
				List<ExamQuestion> topicOnly = examQuestionService.selectByTopic(tmap);
				if(topicOnly.size()!=0){
					for(int i=0;i<topicOnly.size();i++){
						if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
							model.addAttribute("ERR_topic", "该题干已存在");
							error = "topic";
							break;
						}
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			model.addAttribute("optNum", option.length);
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			model.addAttribute("optContent", sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					model.addAttribute("ERR_option", "选项内容不能为空");
					error = "option";
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							error = "option";
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i=0;i<answer.length;i++){
					sb.append(answer[i]);
				}
			}
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setAnswer(sb.toString());
		examQuestion.setUpdatedAt(new Date());
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			model.addAttribute("lawQue",examQuestion);
			model.addAttribute("lawAnswer",sb.toString());
			optionNum(model);
			return "ses/ems/exam/expert/law/edit";
		}
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:searchLawExpPool.html";
	}

	/**
	 * 
	 * @Title: lawQuestion
	 * @author ZhaoBo
	 * @date 2016-10-13 下午4:06:57  
	 * @Description: 拿到要修改的法律题目 
	 * @param @param model
	 * @param @param id      
	 * @return void
	 */
	public void lawQuestion(Model model,String id){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("lawQue",examQuestion);
		String answer = examQuestion.getAnswer();
		model.addAttribute("lawAnswer",answer);
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		String[] option = examQuestion.getItems().split(";");
		model.addAttribute("optNum", option.length);
		List<String> sb_opt = new ArrayList<String>();
		for(int i=0;i<option.length;i++){
			sb_opt.add(option[i].substring(2));
		}
		model.addAttribute("optContent", sb_opt);
		optionNum(model);
	}

	/**
	 * 
	 * @Title: tecQuestion
	 * @author ZhaoBo
	 * @date 2016-10-13 下午4:07:17  
	 * @Description: 拿到要修改的技术题目  
	 * @param @param model
	 * @param @param id      
	 * @return void
	 */
	public void tecQuestion(Model model,String id){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("tecQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("tecAnswer",queAnswer);
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		String[] option = examQuestion.getItems().split(";");
		model.addAttribute("optNum", option.length);
		List<String> sb_opt = new ArrayList<String>();
		for(int i=0;i<option.length;i++){
			sb_opt.add(option[i].substring(2));
		}
		model.addAttribute("optContent", sb_opt);
		optionNum(model);
	}

	/**
	 * 
	 * @Title: comQuestion
	 * @author ZhaoBo
	 * @date 2016-10-13 下午4:12:48  
	 * @Description: 拿到要修改的商务题目 
	 * @param @param model
	 * @param @param id      
	 * @return void
	 */
	public void comQuestion(Model model,String id){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("comQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("comAnswer",queAnswer);
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		String[] option = examQuestion.getItems().split(";");
		model.addAttribute("optNum", option.length);
		List<String> sb_opt = new ArrayList<String>();
		for(int i=0;i<option.length;i++){
			sb_opt.add(option[i].substring(2));
		}
		model.addAttribute("optContent", sb_opt);
		optionNum(model);
	}

	/**
	 * 
	 * @Title: editToTec
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:07:55  
	 * @Description: 修改并保存技术类的题库  
	 * @param @param model
	 * @param @param request
	 * @param @param examPool
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editToTec")
	public String editToTec(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		String id = request.getParameter("id");
		examQuestion.setId(id);
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String content = request.getParameter("content");
		String queType = request.getParameter("queType");
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/technical/edit";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			if(!content.trim().equals(topic.trim())){
				HashMap<String,Object> tmap = new HashMap<String,Object>();
				tmap.put("questionTypeId", Integer.parseInt(queType));
				tmap.put("topic",topic.trim());
				List<ExamQuestion> topicOnly = examQuestionService.selectByTecTopic(tmap);
				if(topicOnly.size()!=0){
					for(int i=0;i<topicOnly.size();i++){
						if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
							model.addAttribute("ERR_topic", "该题干已存在");
							error = "topic";
							break;
						}
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			model.addAttribute("optNum", option.length);
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			model.addAttribute("optContent", sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					model.addAttribute("ERR_option", "选项内容不能为空");
					error = "option";
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							error = "option";
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i=0;i<answer.length;i++){
					sb.append(answer[i]);
				}
			}
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setAnswer(sb.toString());
		examQuestion.setUpdatedAt(new Date());
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			model.addAttribute("tecQue",examQuestion);
			model.addAttribute("tecAnswer",sb.toString());
			optionNum(model);
			return "ses/ems/exam/expert/technical/edit";
		}
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:searchTecExpPool.html";
	}

	/**
	 * 
	 * @Title: editToCom
	 * @author ZhaoBo
	 * @date 2016-9-8 上午9:19:24  
	 * @Description: 修改并保存商务类的题库 
	 * @param @param model
	 * @param @param request
	 * @param @param examPool
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editToCom")
	public String editToCom(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examQuestionType);
		String id = request.getParameter("id");
		examQuestion.setId(id);
		String[] items = saveOption();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb_option = new StringBuffer();
		String content = request.getParameter("content");
		String queType = request.getParameter("queType");
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/expert/commerce/edit";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic", "题干不能为空");
		}else{
			if(!content.trim().equals(topic.trim())){
				HashMap<String,Object> tmap = new HashMap<String,Object>();
				tmap.put("questionTypeId", Integer.parseInt(queType));
				tmap.put("topic", topic.trim());
				tmap.put("kind",1);
				tmap.put("personType", 1);
				List<ExamQuestion> topicOnly = examQuestionService.selectByTopic(tmap);
				if(topicOnly.size()!=0){
					for(int i=0;i<topicOnly.size();i++){
						if(topic.trim().equals(topicOnly.get(i).getTopic().trim())){
							model.addAttribute("ERR_topic", "该题干已存在");
							error = "topic";
							break;
						}
					}
				}
			}
		}
		if(request.getParameter("options").isEmpty()){
			error = "option";
			model.addAttribute("ERR_option","请选择选项数量");
		}else{
			String[] option = request.getParameterValues("option");
			String item = items[option.length];
			String[] opt = item.split(",");
			model.addAttribute("optNum", option.length);
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					sb_opt.add("");
				}else{
					sb_opt.add(option[i].toString());
				}
			}
			model.addAttribute("optContent", sb_opt);
			outer:for(int i=0;i<option.length;i++){
				if(option[i].trim().isEmpty()){
					model.addAttribute("ERR_option", "选项内容不能为空");
					error = "option";
					break outer;
				}else if(i==option.length-1){
					for(int j=0;j<option.length;j++){
						for(int k=j+1;k<option.length;k++){
							if(option[j].trim().equals(option[k].trim())){
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能重复");
								break outer;
							}
						}
						if(option[j].indexOf(";")>-1||option[j].indexOf("；")>-1){
							model.addAttribute("ERR_option", "选项内容不能输入分号");
							error = "option";
							break outer;
						}
					}
					for(int j=0;j<opt.length;j++){
						sb_option.append(opt[j]+"."+option[j]+";");
					}
					examQuestion.setItems(sb_option.toString());
				}
			}
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i=0;i<answer.length;i++){
					sb.append(answer[i]);
				}
			}
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setAnswer(sb.toString());
		examQuestion.setUpdatedAt(new Date());
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			model.addAttribute("comQue",examQuestion);
			model.addAttribute("comAnswer",sb.toString());
			optionNum(model);
			return "ses/ems/exam/expert/commerce/edit";
		}
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:searchComExpPool.html";
	}

	/**
	 * 
	 * @Title: saveScore
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:08:35  
	 * @Description: 专家考试答题算总分 
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/saveScore",method=RequestMethod.POST)
	public String saveScore(Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		Expert expert = expertService.selectByPrimaryKey(user.getTypeId());
		String[] queAnswer = request.getParameter("queAnswer").split(",");
		String[] queId = request.getParameter("queId").split(",");
		String[] queType = request.getParameter("queType").split(",");
		BigDecimal score = new BigDecimal(0);
		List<ExamRule> examRule = examRuleService.selectInUseRule();
		String typeDistribution = examRule.get(0).getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleP = (String) obj.get("singlePoint");
		BigDecimal singlePoint = new BigDecimal(singleP);
		String multipleP = (String) obj.get("multiplePoint");
		BigDecimal multiplePoint = new BigDecimal(multipleP);
		for(int i=0;i<queAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				insertAnswer(" ", queId[i], user.getId());
				continue;
			}else{
				String[] queUserAnswer = request.getParameterValues("que"+(i+1));
				for(int j=0;j<queUserAnswer.length;j++){
					sb.append(queUserAnswer[j]);
				}
				insertAnswer(sb.toString(), queId[i], user.getId());
				if(queAnswer[i].equals(sb.toString())){
					if(queType[i].equals("单选题")){
						score = score.add(singlePoint);
					}else if(queType[i].equals("多选题")){
						score = score.add(multiplePoint);
					}
				}
			}
		}
		String passStandard = examRule.get(0).getPassStandard();
		List<ExamUserScore> maxScore = examUserScoreService.findMaxScoreOfUser(user.getId());
		if(maxScore.size()==0){
			ExamUserScore examUserScore = new ExamUserScore();
			Expert expertObject = new Expert();
			expertObject.setId(expert.getId());
			expertObject.setIsDo("1");
			if(score.compareTo(new BigDecimal(passStandard))>=0){
				examUserScore.setStatus("及格");
				expertObject.setIsPass((short)1);
			}else{
				examUserScore.setStatus("不及格");
				expertObject.setIsPass((short)0);
			}
			expertService.updateByPrimaryKeySelective(expertObject);
			if(expert.getExpertsTypeId().equals("1")){
				examUserScore.setUserDuty("技术");
			}else if(expert.getExpertsTypeId().equals("2")){
				examUserScore.setUserDuty("法律");
			}else if(expert.getExpertsTypeId().equals("3")){
				examUserScore.setUserDuty("商务");
			}
			examUserScore.setUserType(1);
			examUserScore.setIsMax(1);
			examUserScore.setUserId(user.getId());
			examUserScore.setCreatedAt(new Date());
			examUserScore.setUpdatedAt(new Date());
			examUserScore.setTestDate(new Date());
			examUserScore.setScore(String.valueOf(score));
			examUserScoreService.insertSelective(examUserScore);
		}else{
			BigDecimal maxS = new BigDecimal(maxScore.get(0).getScore());
			if(maxS.compareTo(score)>0){
				ExamUserScore examUserScore = new ExamUserScore();
				if(score.compareTo(new BigDecimal(passStandard))>=0){
					examUserScore.setStatus("及格");
				}else{
					examUserScore.setStatus("不及格");
				}
				if(expert.getExpertsTypeId().equals("1")){
					examUserScore.setUserDuty("技术");
				}else if(expert.getExpertsTypeId().equals("2")){
					examUserScore.setUserDuty("法律");
				}else if(expert.getExpertsTypeId().equals("3")){
					examUserScore.setUserDuty("商务");
				}
				examUserScore.setUserType(1);
				examUserScore.setIsMax(0);
				examUserScore.setUserId(user.getId());
				examUserScore.setCreatedAt(new Date());
				examUserScore.setUpdatedAt(new Date());
				examUserScore.setTestDate(new Date());
				examUserScore.setScore(String.valueOf(score));
				examUserScoreService.insertSelective(examUserScore);
			}else{
				ExamUserScore oldMax = new ExamUserScore();
				oldMax.setId(maxScore.get(0).getId());
				oldMax.setIsMax(0);
				examUserScoreService.updateByPrimaryKeySelective(oldMax);
				ExamUserScore examUserScore = new ExamUserScore();
				Expert expertObject = new Expert();
				expertObject.setId(expert.getId());
				if(score.compareTo(new BigDecimal(passStandard))>=0){
					examUserScore.setStatus("及格");
					expertObject.setIsPass((short)1);
				}else{
					examUserScore.setStatus("不及格");
					expertObject.setIsPass((short)0);
				}
				expertService.updateByPrimaryKeySelective(expertObject);
				if(expert.getExpertsTypeId().equals("1")){
					examUserScore.setUserDuty("技术");
				}else if(expert.getExpertsTypeId().equals("2")){
					examUserScore.setUserDuty("法律");
				}else if(expert.getExpertsTypeId().equals("3")){
					examUserScore.setUserDuty("商务");
				}
				examUserScore.setUserType(1);
				examUserScore.setIsMax(1);
				examUserScore.setUserId(user.getId());
				examUserScore.setCreatedAt(new Date());
				examUserScore.setUpdatedAt(new Date());
				examUserScore.setTestDate(new Date());
				examUserScore.setScore(String.valueOf(score));
				examUserScoreService.insertSelective(examUserScore);
			}
		}	
		model.addAttribute("score",score);
		model.addAttribute("rule",examRule.get(0));
		return "ses/ems/exam/expert/score";
	}

	/**
	 * 
	 * @Title: insertAnswer
	 * @author ZhaoBo
	 * @date 2016-11-28 下午2:07:49  
	 * @Description: 新增答题记录 
	 * @param       
	 * @return void
	 */
	public void insertAnswer(String content,String queId,String userId){
		ExamUserAnswer examUserAnswer =new ExamUserAnswer();
		examUserAnswer.setCreatedAt(new Date());
		examUserAnswer.setContent(content);
		examUserAnswer.setUpdatedAt(new Date());
		examUserAnswer.setQuestionId(queId);
		examUserAnswer.setUserType(1);
		
	}
	/**
	 * 
	 * @Title: deleteById
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:12:08  
	 * @Description: 删除题库中的题目 
	 * @param @param request
	 * @param @return      
	 * @return Integer
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public String deleteById(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		for(int i = 0;i<ids.length;i++){
			examQuestionService.deleteByPrimaryKey(ids[i]);
		}
		return "1";
	}
	/**
	 * 
	 * @Title: ready
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:16:04  
	 * @Description: 跳转到开始考试页面 
	 * @param @?return      
	 * @return String
	 */
	@RequestMapping("/ready")
	public String ready(Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		String ID = user.getTypeId();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", 5);
		map.put("id", ID);
		List<Expert> expert = expertService.findAllExpert(map);   //expertAuditService.findResultByExpertId(user.getTypeId());
		List<ExamRule> examRule = examRuleService.selectInUseRule();
		if(examRule.size()==0){
			model.addAttribute("message", "暂无考试安排");
		}else{
			if(expert.size()==0){
				model.addAttribute("message", "暂无考试安排");	
			}else{ 
				//String str=null;
				Date auditTime = expert.get(0).getAuditAt();
				Calendar calendar = Calendar.getInstance();
				//审核时间
				calendar.setTime(auditTime);
				calendar.add(Calendar.MONTH,3);//三个月后的日期
				Date offTime = calendar.getTime();//三个月后的日期（Date类型）
				model.addAttribute("offTime",offTime);
				//三月后时间比当前时间小 就是超出三个月 超出三个月删除用户 只是表中的状态
				//if(offTime.getTime() < new Date().getTime()){
				//获取用户id	
				//根据id删除专家组用户
			    //int i = expertService.deleteExpertsAccount(user.(getTypeId));	
					
					
				
				
				//}
					
				
				
			}
		}
		return "ses/ems/exam/expert/ready";
	}

	/**
	 * 
	 * @Title: test
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:17:53  
	 * @Description: 开始考试 
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/test")
	public String test(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		User user=(User) request.getSession().getAttribute("loginUser");
		String typeId = user.getTypeId();
		//根据id查评审专家
		Expert expert = expertService.selectByPrimaryKey(typeId);
		//用来记录专家考试的历史考试规则
		List<ExamRule> examRule = examRuleService.selectInUseRule();
		//
		List<ExamQuestion> questionList = new ArrayList<ExamQuestion>();
		//从专家考试规则中获取题型分布  typeDistribution
		String typeDistribution = examRule.get(0).getTypeDistribution();
		//json数据格式取出里面的数值
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");//单选
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");//多选
		Integer multipleNum = Integer.parseInt(multipleN);
		String singleP =  (String) obj.get("singlePoint");//单选每题分值
		BigDecimal singlePoint = new BigDecimal(singleP);
		String multipleP = (String) obj.get("multiplePoint");//多选每题分值 
		BigDecimal multiplePoint = new BigDecimal(multipleP);
		HashMap<String,Object> single = new HashMap<String,Object>();
		HashMap<String,Object> multiple = new HashMap<String,Object>();
		List<ExamQuestion> singleQuestion = new ArrayList<ExamQuestion>();
		List<ExamQuestion> multipleQuestion = new ArrayList<ExamQuestion>();
		//获取专家类型
		DictionaryData findById = DictionaryDataUtil.findById(expert.getExpertsTypeId().split(",")[0]);
		//经济类型 Integer.valueOf("6")转换类型
		if(Integer.valueOf("19").equals(findById.getKind())){
			single.put("questionTypeId", 1);
			//经济评审类型
			single.put("kind",DictionaryDataUtil.getId("ECONOMY"));
			single.put("queNum", singleNum);
			singleQuestion = examQuestionService.selectQuestionRandom(single);
			multiple.put("questionTypeId", 2);
			multiple.put("kind", DictionaryDataUtil.getId("ECONOMY"));
			multiple.put("queNum", multipleNum);
			multipleQuestion = examQuestionService.selectQuestionRandom(multiple);
		}
		//技术类型
		else if(Integer.valueOf("6").equals(findById.getKind())){
			single.put("questionTypeId", 1);
			//技术评审类型
			single.put("kind", DictionaryDataUtil.getId("TECHNOLOGY"));
			single.put("queNum", singleNum);
			singleQuestion = examQuestionService.selectQuestionRandom(single);
			multiple.put("questionTypeId", 2);
			multiple.put("kind", DictionaryDataUtil.getId("TECHNOLOGY"));
			multiple.put("queNum", multipleNum);
			multipleQuestion = examQuestionService.selectQuestionRandom(multiple);
	}
//		else if("3".equals(findById.getCode())){
//			single.put("questionTypeId", 1);
//			single.put("kind", 1);
//			single.put("queNum", singleNum);
//			singleQuestion = examQuestionService.selectQuestionRandom(single);
//			multiple.put("questionTypeId", 2);
//			multiple.put("kind", 1);
//			multiple.put("queNum", multipleNum);
//			multipleQuestion = examQuestionService.selectQuestionRandom(multiple);
//		}
		questionList.addAll(singleQuestion);
		questionList.addAll(multipleQuestion);
		Integer queNum = questionList.size();
		List<Integer> pageNum = new ArrayList<Integer>();
		if(queNum%5==0){
			for(int i=0;i<queNum/5;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<queNum/5+1;i++){
				pageNum.add(i);
			}
		}
		List<ExamQuestion> nQuestionList = new ArrayList<ExamQuestion>();
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getExamQuestionType().getName().equals("单选题")){
				nQuestionList.add(questionList.get(i));
			}
		}
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getExamQuestionType().getName().equals("多选题")){
				nQuestionList.add(questionList.get(i));
			}
		}
		StringBuffer sb_answer = new StringBuffer();
		StringBuffer sb_type = new StringBuffer();
		StringBuffer sb_id = new StringBuffer();
		for(int i=0;i<nQuestionList.size();i++){
			sb_answer.append(nQuestionList.get(i).getAnswer()+",");
			sb_type.append(nQuestionList.get(i).getExamQuestionType().getName()+",");
			sb_id.append(nQuestionList.get(i).getId()+",");
		}
		model.addAttribute("queAnswer", sb_answer.toString());
		model.addAttribute("queType", sb_type.toString());
		model.addAttribute("queId", sb_id.toString());
		model.addAttribute("queRandom",nQuestionList);
		model.addAttribute("examRule", examRule.get(0));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageNum.size());
		model.addAttribute("queCount", nQuestionList.size());
		model.addAttribute("user", user);
		model.addAttribute("singlePoint", singlePoint);
		model.addAttribute("multiplePoint", multiplePoint);
		model.addAttribute("singleNum", singleNum);
		model.addAttribute("multipleNum", multipleNum);
		return "ses/ems/exam/expert/test";
	}

	/**
	 * 
	 * @Title: viewLaw
	 * @author ZhaoBo
	 * @date 2016-9-7 下午2:32:39  
	 * @Description: 查看法律类专家题库 
	 * @param @param request
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewLaw")
	public String viewLaw(HttpServletRequest request,Model model){
		lawQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/law/view";
	}

	/**
	 * 
	 * @Title: viewTec
	 * @author ZhaoBo
	 * @date 2016-9-7 下午2:32:39  
	 * @Description: 查看技术类专家题库 
	 * @param @param request
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewTec")
	public String viewTec(HttpServletRequest request,Model model){
		tecQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/technical/view";
	}

	/**
	 * 
	 * @Title: viewCom
	 * @author ZhaoBo
	 * @date 2016-9-8 上午9:36:16  
	 * @Description: 查看商务类专家题库  
	 * @param @param request
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewCom")
	public String viewCom(HttpServletRequest request,Model model){
		comQuestion(model, request.getParameter("id"));
		return "ses/ems/exam/expert/commerce/view";
	}

	/**
	 * 
	 * @Title: createRule
	 * @author ZhaoBo
	 * @date 2016-9-8 上午10:28:43  
	 * @Description: 考试规则列表页面 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/ruleList")
	public String createRule(Model model,Integer page){
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamRule> list = examRuleService.selectById(map);
		for(int i=0;i<list.size();i++){
			String typeDistribution = list.get(i).getTypeDistribution();
			JSONObject obj = JSONObject.fromObject(typeDistribution);
			String singleN =  (String) obj.get("singleNum");
			Integer singleNum = Integer.parseInt(singleN);
			String multipleN = (String) obj.get("multipleNum");
			Integer multipleNum = Integer.parseInt(multipleN);
			String singleP =  (String) obj.get("singlePoint");
			BigDecimal singlePoint = new BigDecimal(singleP);
			String multipleP = (String) obj.get("multiplePoint");
			BigDecimal multiplePoint = new BigDecimal(multipleP);
			if(singleNum!=0&&multipleNum!=0){
				list.get(i).setDiscribution("单选题"+singleNum+"题，每题"+singlePoint+"分；多选题"+multipleNum+"题，每题"+multiplePoint+"分。");
			}else if(singleNum!=0&&multipleNum==0){
				list.get(i).setDiscribution("单选题"+singleNum+"题，每题"+singlePoint+"分。");
			}else if(singleNum==0&&multipleNum!=0){
				list.get(i).setDiscribution("多选题"+singleNum+"题，每题"+singlePoint+"分。");
			}
		}
		model.addAttribute("list", new PageInfo<ExamRule>(list));
		return "ses/ems/exam/expert/rule/list";
	}

	/**
	 * 
	 * @Title: createRule
	 * @author ZhaoBo
	 * @date 2016-11-16 上午9:28:36  
	 * @Description: 新增考试规则 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/createRule")
	public String createRule(){
		return "ses/ems/exam/expert/rule/add";
	}

	/**
	 * 
	 * @Title: saveExamRule
	 * @author ZhaoBo
	 * @date 2016-9-8 上午10:26:52  
	 * @Description: 保存考试规则 
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws ParseException 
	 */
	@RequestMapping("/saveExamRule") 
	public String saveExamRule(Model model,HttpServletRequest request,ExamRule examRule) throws ParseException{
		String error = "无";
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String[] single = request.getParameterValues("single");
		String[] multiple = request.getParameterValues("multiple");
		Map<String,String> errorData = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		if(single!=null&&multiple!=null){
			errorData.put("singleNum", singleNum);
			errorData.put("singlePoint", singlePoint);
			errorData.put("single", single[0]);
			errorData.put("multipleNum", multipleNum);
			errorData.put("multiplePoint", multiplePoint);
			errorData.put("multiple", multiple[0]);
			if(single[0].equals("无")&&multiple[0].equals("无")){
				error = "error";
				model.addAttribute("ERR_multiple", "请至少选择一种题型");
			}
		}
		if(single==null){
			error = "error";
			model.addAttribute("ERR_single", "请选择");
		}else{
			errorData.put("singleNum", singleNum);
			errorData.put("singlePoint", singlePoint);
			errorData.put("single", single[0]);
			if(single[0].equals("有")){
				if(singleNum.trim().isEmpty()||singlePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_single", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(singleNum)){
						error = "error";
						model.addAttribute("ERR_single", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(singlePoint)){
						error = "error";
						model.addAttribute("ERR_single", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> tecSingle = new HashMap<String,Object>();
						tecSingle.put("questionTypeId", 1);
						tecSingle.put("kind", "0");
						int tec = examQuestionService.queryQuestionCount(tecSingle);
						HashMap<String,Object> ComSingle = new HashMap<String,Object>();
						ComSingle.put("questionTypeId", 1);
						ComSingle.put("kind", "1");
						int com = examQuestionService.queryQuestionCount(ComSingle);
						HashMap<String,Object> lawSingle = new HashMap<String,Object>();
						lawSingle.put("questionTypeId", 1);
						lawSingle.put("kind", "2");
						int law = examQuestionService.queryQuestionCount(lawSingle);
						if(tec<Integer.parseInt(singleNum)||com<Integer.parseInt(singleNum)||law<Integer.parseInt(singleNum)){
							error = "error";
							model.addAttribute("ERR_single", "题库中单选题数量不足");
						}else{
							map.put("singleNum", singleNum);
							map.put("singlePoint", singlePoint);
						}
					}
				}
			}else{
				map.put("singleNum", "0");
				map.put("singlePoint", "0");
			}
		}
		if(multiple==null){
			error = "error";
			model.addAttribute("ERR_multiple", "请选择");
		}else{
			errorData.put("multipleNum", multipleNum);
			errorData.put("multiplePoint", multiplePoint);
			errorData.put("multiple", multiple[0]);
			if(multiple[0].equals("有")){
				if(multipleNum.trim().isEmpty()||multiplePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_multiple", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(multipleNum)){
						error = "error";
						model.addAttribute("ERR_multiple", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(multiplePoint)){
						error = "error";
						model.addAttribute("ERR_multiple", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> tecMultiple = new HashMap<String,Object>();
						tecMultiple.put("questionTypeId", 2);
						tecMultiple.put("kind", "0");
						int tec = examQuestionService.queryQuestionCount(tecMultiple);
						HashMap<String,Object> ComMultiple = new HashMap<String,Object>();
						ComMultiple.put("questionTypeId", 2);
						ComMultiple.put("kind", "1");
						int com = examQuestionService.queryQuestionCount(ComMultiple);
						HashMap<String,Object> lawMultiple = new HashMap<String,Object>();
						lawMultiple.put("questionTypeId", 2);
						lawMultiple.put("kind", "2");
						int law = examQuestionService.queryQuestionCount(lawMultiple);
						if(tec<Integer.parseInt(multipleNum)||com<Integer.parseInt(multipleNum)||law<Integer.parseInt(multipleNum)){
							error = "error";
							model.addAttribute("ERR_multiple", "题库中多选题数量不足");
						}else{
							map.put("multipleNum", multipleNum);
							map.put("multiplePoint", multiplePoint);
						}
					}
				}
			}else{
				map.put("multipleNum", "0");
				map.put("multiplePoint", "0");
			}
		}
		String paperScore = request.getParameter("paperScore");
		String passStandard = request.getParameter("passStandard");
		if(passStandard.trim().isEmpty()){
			error = "error";
			model.addAttribute("ERR_passStandard", "及格标准不能为空");
		}else{
			if(!ValidateUtils.PLUS_NUMBER(passStandard)){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分必须为大于0的正数");
			}else if(new BigDecimal(paperScore).compareTo(new BigDecimal(passStandard))<=0){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分要小于试卷分值");
			}
		}
		if(error.equals("error")){
			errorData.put("passStandard", passStandard);
			errorData.put("score", paperScore);
			model.addAttribute("errorData", errorData);
			return "ses/ems/exam/expert/rule/add";
		}
		examRule.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examRule.setPassStandard(passStandard);
		examRule.setPaperScore(paperScore);
		examRule.setCreatedAt(new Date());
		examRule.setUpdatedAt(new Date());
		examRule.setStatus(0);
		examRuleService.insertSelective(examRule);
		return "redirect:ruleList.html";
	}

	/**
	 * 
	 * @Title: editRule
	 * @author ZhaoBo
	 * @date 2016-11-16 下午12:38:08  
	 * @Description: 修改考试规则 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/editRule")
	public String editRule(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		ExamRule rule = examRuleService.selectById(map).get(0);
		model.addAttribute("examRule", rule);
		String typeDistribution = rule.getTypeDistribution();
		JSONObject object = JSONObject.fromObject(typeDistribution);
		model.addAttribute("singleNum", object.get("singleNum"));
		if(Integer.parseInt(object.get("singleNum").toString())>0){
			model.addAttribute("errorSingle", "有");
		}else{
			model.addAttribute("errorSingle", "无");
		}
		model.addAttribute("singlePoint", object.get("singlePoint"));
		model.addAttribute("multipleNum", object.get("multipleNum"));
		if(Integer.parseInt(object.get("multipleNum").toString())>0){
			model.addAttribute("errorMultiple", "有");
		}else{
			model.addAttribute("errorMultiple", "无");
		}
		model.addAttribute("multiplePoint", object.get("multiplePoint"));
		return "ses/ems/exam/expert/rule/edit";
	}

	/**
	 * 
	 * @Title: editToExamRule
	 * @author ZhaoBo
	 * @date 2016-11-16 下午1:21:25  
	 * @Description: 修改考试规则并保存 
	 * @param @return      
	 * @return String
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/editToExamRule")
	public String editToExamRule(HttpServletRequest request,ExamRule examRule,Model model) throws NumberFormatException, ParseException{
		examRule.setId(request.getParameter("ruleId"));
		String error = "无";
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String[] single = request.getParameterValues("single");
		String[] multiple = request.getParameterValues("multiple");
		Map<String,String> map = new HashMap<String,String>();
		if(single!=null&&multiple!=null){
			model.addAttribute("singleNum", singleNum);
			model.addAttribute("singlePoint", singlePoint);
			model.addAttribute("errorSingle", single[0]);
			model.addAttribute("multipleNum", multipleNum);
			model.addAttribute("multiplePoint", multiplePoint);
			model.addAttribute("errorMultiple", multiple[0]);
			if(single[0].equals("无")&&multiple[0].equals("无")){
				error = "error";
				model.addAttribute("ERR_single", "请至少选择一种题型");
			}
		}
		if(single==null){
			error = "error";
			model.addAttribute("ERR_single", "请选择");
		}else{
			model.addAttribute("singleNum", singleNum);
			model.addAttribute("singlePoint", singlePoint);
			model.addAttribute("errorSingle", single[0]);
			if(single[0].equals("有")){
				if(singleNum.trim().isEmpty()||singlePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_single", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(singleNum)){
						error = "error";
						model.addAttribute("ERR_single", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(singlePoint)){
						error = "error";
						model.addAttribute("ERR_single", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> tecSingle = new HashMap<String,Object>();
						tecSingle.put("questionTypeId", 1);
						tecSingle.put("kind", "0");
						int tec = examQuestionService.queryQuestionCount(tecSingle);
						HashMap<String,Object> ComSingle = new HashMap<String,Object>();
						ComSingle.put("questionTypeId", 1);
						ComSingle.put("kind", "1");
						int com = examQuestionService.queryQuestionCount(ComSingle);
						HashMap<String,Object> lawSingle = new HashMap<String,Object>();
						lawSingle.put("questionTypeId", 1);
						lawSingle.put("kind", "2");
						int law = examQuestionService.queryQuestionCount(lawSingle);
						if(tec<Integer.parseInt(singleNum)||com<Integer.parseInt(singleNum)||law<Integer.parseInt(singleNum)){
							error = "error";
							model.addAttribute("ERR_single", "题库中单选题数量不足");
						}else{
							map.put("singleNum", singleNum);
							map.put("singlePoint", singlePoint);
						}
					}
				}
			}else{
				map.put("singleNum", "0");
				map.put("singlePoint", "0");
			}
		}
		if(multiple==null){
			error = "error";
			model.addAttribute("ERR_multiple", "请选择");
		}else{
			model.addAttribute("multipleNum", multipleNum);
			model.addAttribute("multiplePoint", multiplePoint);
			model.addAttribute("errorMultiple", multiple[0]);
			if(multiple[0].equals("有")){
				if(multipleNum.trim().isEmpty()||multiplePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_multiple", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(multipleNum)){
						error = "error";
						model.addAttribute("ERR_multiple", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(multiplePoint)){
						error = "error";
						model.addAttribute("ERR_multiple", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> tecMultiple = new HashMap<String,Object>();
						tecMultiple.put("questionTypeId", 2);
						tecMultiple.put("kind", "0");
						int tec = examQuestionService.queryQuestionCount(tecMultiple);
						HashMap<String,Object> ComMultiple = new HashMap<String,Object>();
						ComMultiple.put("questionTypeId", 2);
						ComMultiple.put("kind", "1");
						int com = examQuestionService.queryQuestionCount(ComMultiple);
						HashMap<String,Object> lawMultiple = new HashMap<String,Object>();
						lawMultiple.put("questionTypeId", 2);
						lawMultiple.put("kind", "2");
						int law = examQuestionService.queryQuestionCount(lawMultiple);
						if(tec<Integer.parseInt(multipleNum)||com<Integer.parseInt(multipleNum)||law<Integer.parseInt(multipleNum)){
							error = "error";
							model.addAttribute("ERR_multiple", "题库中多选题数量不足");
						}else{
							map.put("multipleNum", multipleNum);
							map.put("multiplePoint", multiplePoint);
						}
					}
				}
			}else{
				map.put("multipleNum", "0");
				map.put("multiplePoint", "0");
			}
		}
		String paperScore = request.getParameter("paperScore");
		String passStandard = request.getParameter("passStandard");
		if(passStandard.trim().isEmpty()){
			error = "error";
			model.addAttribute("ERR_passStandard", "及格标准不能为空");
		}else{
			if(!ValidateUtils.PLUS_NUMBER(passStandard)){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分必须为大于0的正数");
			}else if(new BigDecimal(paperScore).compareTo(new BigDecimal(passStandard))<=0){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分要小于试卷分值");
			}
		}
		if(error.equals("error")){
			model.addAttribute("examRule", examRule);
			model.addAttribute("singleNum", singleNum);
			model.addAttribute("singlePoint", singlePoint);
			model.addAttribute("multipleNum", multipleNum);
			model.addAttribute("multiplePoint", multiplePoint);
			return "ses/ems/exam/expert/rule/edit";
		}
		examRule.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examRule.setPassStandard(passStandard);
		examRule.setPaperScore(paperScore);
		examRule.setUpdatedAt(new Date());
		examRuleService.updateByPrimaryKeySelective(examRule);
		return "redirect:ruleList.html";
	}

	/**
	 * 
	 * @Title: viewRule
	 * @author ZhaoBo
	 * @date 2016-11-16 下午2:18:37  
	 * @Description: 查看考试规则页面 
	 * @param @param request
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewRule")
	public String viewRule(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		ExamRule rule = examRuleService.selectById(map).get(0);
		model.addAttribute("examRule", rule);
		String typeDistribution = rule.getTypeDistribution();
		JSONObject object = JSONObject.fromObject(typeDistribution);
		int singleNum = Integer.parseInt(object.get("singleNum").toString());
		BigDecimal singlePoint = new BigDecimal(object.get("singlePoint").toString());
		int multipleNum = Integer.parseInt(object.get("multipleNum").toString());
		BigDecimal multiplePoint = new BigDecimal(object.get("multiplePoint").toString());
		if(singleNum!=0&&multipleNum!=0){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分；多选题"+multipleNum+"题，每题"+multiplePoint+"分。");
		}else if(singleNum!=0&&multipleNum==0){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分。");
		}else if(singleNum==0&&multipleNum!=0){
			model.addAttribute("typeDistribution", "多选题"+singleNum+"题，每题"+singlePoint+"分。");
		}
		return "ses/ems/exam/expert/rule/view";
	}

	/**
	 * 
	 * @Title: backRule
	 * @author ZhaoBo
	 * @date 2016-11-16 下午2:30:55  
	 * @Description: 返回考试规则列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backRule")
	public String backRule(){
		return "redirect:ruleList.html";
	}

	/**
	 * 
	 * @Title: result
	 * @author ZhaoBo
	 * @date 2016-9-23 下午3:52:00  
	 * @Description: 跳转到专家考试成绩查询页面 (也可以按条件查询)
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/result")
	public String result(@CurrentUser User user,Model model,HttpServletRequest request,Integer page){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ExpertAudit> list = expertAuditService.findAllPassExpert();
		for(int i=0;i<list.size();i++){
			Expert expert = expertService.selectByPrimaryKey(list.get(i).getExpertId());
			Date auditTime = list.get(i).getAuditAt();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(auditTime);
			calendar.add(Calendar.MONTH,3);//三个月后的日期
			Date offTime = calendar.getTime();//三个月后的日期（Date类型）
			if(new Date().getTime()>offTime.getTime()){
				if(expert.getIsDo()==null||expert.getIsDo().equals("0")){
					Expert expertObject = new Expert();
					expertObject.setId(expert.getId());
					expertObject.setIsDo("2");
					expertObject.setIsPass((short)0);
					expertService.updateByPrimaryKeySelective(expertObject);
					ExamUserScore examUserScore = new ExamUserScore();
					examUserScore.setStatus("不及格");
					if(expert.getExpertsTypeId().equals("1")){
						examUserScore.setUserDuty("技术");
					}else if(expert.getExpertsTypeId().equals("2")){
						examUserScore.setUserDuty("法律");
					}else if(expert.getExpertsTypeId().equals("3")){
						examUserScore.setUserDuty("商务");
					}
					examUserScore.setUserType(1);
					examUserScore.setIsMax(1);
					User want = userService.findByTypeId(expert.getId());
					if(want!=null){
						examUserScore.setUserId(want.getId());
					}
					examUserScore.setCreatedAt(new Date());
					examUserScore.setScore("0");
					examUserScoreService.insertSelective(examUserScore);
				}
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userName = request.getParameter("userName");
		String userType = request.getParameter("userType");
		String status = request.getParameter("status");
		if(userName!=null && !userName.equals("")){
			map.put("relName", "%"+userName+"%");
		}
		if(userType!=null && !userType.equals("")){
			if(userType.equals("1")){
				map.put("userDuty", "技术");
			}else if(userType.equals("2")){
				map.put("userDuty", "法律");
			}else if(userType.equals("3")){
				map.put("userDuty", "商务");
			}
		}
		if(status!=null && !status.equals("")){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		if(null != user && "4".equals(user.getTypeName())){
	        //判断是否 是资源服务中心 
			List<ExamUserScore> expertResultList = examUserScoreService.selectExpertResultByCondition(map);
			for(int i=0;i<expertResultList.size();i++){
				expertResultList.get(i).setFormatDate(sdf.format(expertResultList.get(i).getTestDate()));
			}
			model.addAttribute("expertResultList", new PageInfo<ExamUserScore>(expertResultList));
		    model.addAttribute("authType", 4);
	    }else{
	    	model.addAttribute("expertResultList", new PageInfo<ExamUserScore>());
	    }
		model.addAttribute("userName", userName);
		model.addAttribute("userType", userType);
		model.addAttribute("status", status);
		return "ses/ems/exam/expert/result";
	}

	/**
	 * 
	 * @Title: personalResult
	 * @author ZhaoBo
	 * @date 2016-9-29 下午4:51:13  
	 * @Description: 专家后台各专家查询自己的成绩 
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/personalResult")
	public String personalResult(Integer page,Model model,HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user = (User) request.getSession().getAttribute("loginUser");
		List<ExpertAudit> list = expertAuditService.findResultByExpertId(user.getTypeId());		
		if(list.size()!=0){
			Expert expert = expertService.selectByPrimaryKey(list.get(0).getExpertId());
			Date auditTime = list.get(0).getAuditAt();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(auditTime);
			calendar.add(Calendar.MONTH,3);//三个月后的日期
			Date offTime = calendar.getTime();//三个月后的日期（Date类型）
			if(new Date().getTime()>offTime.getTime()){
				if(expert.getIsDo()==null||expert.getIsDo().equals("0")){
					Expert expertObject = new Expert();
					expertObject.setId(expert.getId());
					expertObject.setIsDo("2");
					expertObject.setIsPass((short)0);
					expertService.updateByPrimaryKeySelective(expertObject);
					ExamUserScore examUserScore = new ExamUserScore();
					examUserScore.setStatus("不及格");
					if(expert.getExpertsTypeId().equals("1")){
						examUserScore.setUserDuty("技术");
					}else if(expert.getExpertsTypeId().equals("2")){
						examUserScore.setUserDuty("法律");
					}else if(expert.getExpertsTypeId().equals("3")){
						examUserScore.setUserDuty("商务");
					}
					examUserScore.setUserType(1);
					examUserScore.setIsMax(1);
					examUserScore.setUserId(user.getId());
					examUserScore.setCreatedAt(new Date());
					examUserScore.setScore("0");
					examUserScoreService.insertSelective(examUserScore);
				}
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamUserScore> scores = examUserScoreService.findByUserId(map);
		if(scores.size()!=0){
			for(int i=0;i<scores.size();i++){
				scores.get(i).setRelName(user.getRelName());
				if(scores.get(i).getTestDate()!=null){
					scores.get(i).setFormatDate(sdf.format(scores.get(i).getTestDate()));
				}
			}
		}
		model.addAttribute("list", new PageInfo<ExamUserScore>(scores));
		return "ses/ems/exam/expert/personal_result";
	}


	/**
	 * 
	 * @Title: judgeTest
	 * @author ZhaoBo
	 * @date 2016-9-9 下午2:46:36  
	 * @Description: 判断当前时间是否过了考试周期,并且判断当前用户是不是专家 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/judgeTest")
	@ResponseBody
	public String judgeTest(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", 5);
		map.put("id", user.getTypeId());
		//List 集合 expert.get(0)获取集合里第一条集合数据再.getAuditAt第一条数据里的某个值
		List<Expert> expert = expertService.findAllExpert(map);   
		String str = null;
		Date auditTime = expert.get(0).getAuditAt();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(auditTime);
		calendar.add(Calendar.MONTH,3);//三个月后的日期
		Date offTime = calendar.getTime();//三个月后的日期（Date类型）
		if(offTime.getTime() < new Date().getTime()){
			
			//通过id删 专家id和用户表里的id不一样 通过用户表要去user.getTypeId
			int i=expertService.deleteExpertsAccount(user.getTypeId());
		}else{
			str = "1";//可以开始考试
		}
		return str;
	}

	/**
	 * 
	 * @Title: loadExpertTemplet
	 * @author ZhaoBo
	 * @date 2016-9-9 下午3:42:00  
	 * @Description: 专家题库模板下载 
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/loadExpertTemplet")
	public ResponseEntity<byte[]> loadExpertTemplet(HttpServletRequest request) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		String path = PathUtil.getWebRoot() + "excel/专家题库模板.xlsx";
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", new String("专家题库模板.xlsx".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED));  
	}

	private static final String uploadFolderName = "uploadFiles"; //上传到服务器的文件夹名 
	private static String [] extensionPermit = {"xls","xlsx"}; //允许上传的文件格式

	/**
	 * 
	 * @Title: importTec
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:31:37  
	 * @Description: 导入技术类专家题库 
	 * @param @param file
	 * @param @param session
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws FileNotFoundException
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping(value="/importTec",method = RequestMethod.POST)
	public void importTec(@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{

		String curProjectPath = session.getServletContext().getRealPath("/");  
		String saveDirectoryPath = curProjectPath + "/" + uploadFolderName; 
		// 判断文件是否存在  
		String fileName = null;
		File excelFile = null;
		if (!file.isEmpty()) {
			fileName = file.getOriginalFilename();  
			String fileExtension = FilenameUtils.getExtension(fileName);   
			if(!Arrays.asList(extensionPermit).contains(fileExtension)){

			} 
			excelFile = new File(saveDirectoryPath,System.currentTimeMillis()+file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
		}
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		String str = "无";
		Sheet sheet = workbook.getSheetAt(0);
		String[] items = saveOption();
		StringBuffer same = new StringBuffer();
		List<ExamQuestion> question = new ArrayList<ExamQuestion>();
		for (int i=1;i<= sheet.getPhysicalNumberOfRows();i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(2);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind("0");
				StringBuffer sb_items = new StringBuffer();
				String item = items[row.getPhysicalNumberOfCells()-3];
				String[] opt = item.split(",");
				for(int j=3;j<row.getPhysicalNumberOfCells();j++){
					if(row.getCell(j).toString().indexOf(".")>-1){
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString().substring(0, row.getCell(j).toString().indexOf("."))+";");
					}else{
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString()+";");
					}
				}
				examQuestion.setItems(sb_items.toString());
				HashMap<String,Object> map = new HashMap<String,Object>();
				examQuestion.setCreatedAt(new Date());
				examQuestion.setAnswer(queAnswer.toString());
				if(queType.toString().equals("单选题")){
					map.put("questionTypeId", 1);
					examQuestion.setQuestionTypeId(1);
				}else{
					map.put("questionTypeId", 2);
					examQuestion.setQuestionTypeId(2);
				}
				map.put("topic", queTopic.toString().trim());
				List<ExamQuestion> sameTopic = examQuestionService.selectByTecTopic(map);
				if(sameTopic.size()!=0){
					for(int j=0;j<sameTopic.size();j++){
						if(queTopic.toString().trim().equals(sameTopic.get(j).getTopic().trim())){
							str="1";
							same.append(queType.toString()+","+queTopic.toString()+";");
							break;
						}
					}
				}else{
					examQuestion.setTopic(queTopic.toString().trim());
					question.add(examQuestion);
				}
			}
		}
		if(str.equals("1")){
			super.writeJson(response,same.toString());
		}else{
			for(int i=0;i<question.size();i++){
				examQuestionService.insertSelective(question.get(i));
			}
			super.writeJson(response,"0");
		}
	}

	/**
	 * 
	 * @Title: importLaw
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:31:37  
	 * @Description: 导入法律类专家题库 
	 * @param @param file
	 * @param @param session
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws FileNotFoundException
	 * @param @throws IOException      
	 */
	@RequestMapping(value="/importLaw",method = RequestMethod.POST)
	public void importLaw(@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
		String saveDirectoryPath = curProjectPath + "/" + uploadFolderName; 
		// 判断文件是否存在  
		String fileName = null;
		File excelFile = null;
		if (!file.isEmpty()) {  
			fileName = file.getOriginalFilename();  
			String fileExtension = FilenameUtils.getExtension(fileName);   
			if(!Arrays.asList(extensionPermit).contains(fileExtension)){

			} 
			excelFile = new File(saveDirectoryPath,System.currentTimeMillis()+file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
		}
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		String str = "无";
		Sheet sheet = workbook.getSheetAt(0);
		String[] items = saveOption();
		StringBuffer same = new StringBuffer();
		List<ExamQuestion> question = new ArrayList<ExamQuestion>();
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(2);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind("2");
				StringBuffer sb_items = new StringBuffer();
				String item = items[row.getPhysicalNumberOfCells()-3];
				String[] opt = item.split(",");
				for(int j=3;j<row.getPhysicalNumberOfCells();j++){
					if(row.getCell(j).toString().indexOf(".")>-1){
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString().substring(0, row.getCell(j).toString().indexOf("."))+";");
					}else{
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString()+";");
					}
				}
				examQuestion.setItems(sb_items.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setCreatedAt(new Date());
				HashMap<String,Object> map = new HashMap<String,Object>();
				if (queType.toString().equals("单选题")) {
					map.put("questionTypeId", 1);
					examQuestion.setQuestionTypeId(1);
				} else{
					map.put("questionTypeId", 2);
					examQuestion.setQuestionTypeId(2);
				}
				map.put("kind", 2);
				map.put("topic", queTopic.toString().trim());
				map.put("personType", 1);
				List<ExamQuestion> sameTopic = examQuestionService.selectByTopic(map);
				if(sameTopic.size()!=0){
					for(int j=0;j<sameTopic.size();j++){
						if(queTopic.toString().trim().equals(sameTopic.get(j).getTopic().trim())){
							str="1";
							same.append(queType.toString()+","+queTopic.toString()+";");
							break;
						}
					}
				}else{
					examQuestion.setTopic(queTopic.toString().trim());
					question.add(examQuestion);
				}
			}
		}
		if(str.equals("1")){
			super.writeJson(response,same.toString());
		}else{
			for(int i=0;i<question.size();i++){
				examQuestionService.insertSelective(question.get(i));
			}
			super.writeJson(response,"0");
		}


	}

	/**
	 * 
	 * @Title: importCom
	 * @author ZhaoBo
	 * @date 2016-9-7 上午11:31:37  
	 * @Description: 导入商务类专家题库 
	 * @param @param file
	 * @param @param session
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws FileNotFoundException
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping(value="/importCom",method = RequestMethod.POST)
	public void importCom(@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
		String saveDirectoryPath = curProjectPath + "/" + uploadFolderName; 
		// 判断文件是否存在  
		String fileName = null;
		File excelFile = null;
		if (!file.isEmpty()) {  
			fileName = file.getOriginalFilename();  
			String fileExtension = FilenameUtils.getExtension(fileName);   
			if(!Arrays.asList(extensionPermit).contains(fileExtension)){

			} 
			excelFile = new File(saveDirectoryPath,System.currentTimeMillis()+file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
		}
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		String str = "无";
		Sheet sheet = workbook.getSheetAt(0);
		String[] items = saveOption();
		StringBuffer same = new StringBuffer();
		List<ExamQuestion> question = new ArrayList<ExamQuestion>();
		for (int i=1;i<=sheet.getPhysicalNumberOfRows();i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(2);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind("1");
				StringBuffer sb_items = new StringBuffer();
				String item = items[row.getPhysicalNumberOfCells()-3];
				String[] opt = item.split(",");
				for(int j=3;j<row.getPhysicalNumberOfCells();j++){
					if(row.getCell(j).toString().indexOf(".")>-1){
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString().substring(0, row.getCell(j).toString().indexOf("."))+";");
					}else{
						sb_items.append(opt[j-3]+"."+row.getCell(j).toString()+";");
					}
				}
				examQuestion.setItems(sb_items.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setCreatedAt(new Date());
				HashMap<String,Object> map = new HashMap<String,Object>();
				if (queType.toString().equals("单选题")) {
					map.put("questionTypeId", 1);
					examQuestion.setQuestionTypeId(1);
				} else {
					map.put("questionTypeId", 2);
					examQuestion.setQuestionTypeId(2);
				}
				map.put("kind", 1);
				map.put("topic", queTopic.toString().trim());
				map.put("personType", 1);
				List<ExamQuestion> sameTopic = examQuestionService.selectByTopic(map);
				if(sameTopic.size()!=0){
					for(int j=0;j<sameTopic.size();j++){
						if(queTopic.toString().trim().equals(sameTopic.get(j).getTopic().trim())){
							str="1";
							same.append(queType.toString()+","+queTopic.toString()+";");
							break;
						}
					}
				}else{
					examQuestion.setTopic(queTopic.toString().trim());
					question.add(examQuestion);
				}
			}
		}
		if(str.equals("1")){
			super.writeJson(response,same.toString());
		}else{
			for(int i=0;i<question.size();i++){
				examQuestionService.insertSelective(question.get(i));
			}
			super.writeJson(response,"0");
		}
	}

	/**
	 * 
	 * @Title: exitExam
	 * @author ZhaoBo
	 * @date 2016-10-8 上午8:56:19  
	 * @Description: 退出考试 
	 * @param @return      
	 * @return String
	 */

	@RequestMapping("/exitExam")
	public String exitExam(){
		return "redirect:/login/home.html";
	}

	/**
	 * 
	 * @Title: testSchedule
	 * @author ZhaoBo
	 * @date 2016-10-10 下午1:06:02  
	 * @Description: 查看考试安排 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/testSchedule")
	public String testSchedule(HttpServletRequest request,Model model){
		List<ExamRule> examRule = examRuleService.selectById(null);
		if(examRule.size()==0){
			model.addAttribute("message", "暂无考试安排");
		}else{
			model.addAttribute("rule", examRule.get(0));
		}
		return "ses/ems/exam/expert/test_schedule";
	}

	/**
	 * 
	 * @Title: backLaw
	 * @author ZhaoBo
	 * @date 2016-10-31 上午9:52:27  
	 * @Description: 返回法律题库列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backLaw")
	public String backLaw(){
		return "redirect:searchLawExpPool.html";
	}

	/**
	 * 
	 * @Title: backTec
	 * @author ZhaoBo
	 * @date 2016-10-31 上午9:53:34  
	 * @Description: 返回技术题库列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backTec")
	public String backTec(){
		return "redirect:searchTecExpPool.html";
	}

	/**
	 * 
	 * @Title: backCom
	 * @author ZhaoBo
	 * @date 2016-10-31 上午9:54:05  
	 * @Description: 返回商务题库列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backCom")
	public String backCom(){
		return "redirect:searchComExpPool.html";
	}

	/**
	 * 
	 * @Title: judgeReTake
	 * @author ZhaoBo
	 * @date 2016-11-8 上午10:13:37  
	 * @Description: 判断是否可以重考 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/judgeReTake")
	@ResponseBody
	public String judgeReTake(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		String ID = user.getTypeId();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", 5);
		map.put("id", ID);
		List<Expert> expert = expertService.findAllExpert(map);
		String str = null;
//		List<ExpertAudit> expert = expertAuditService.findResultByExpertId(user.getTypeId());
		Date auditTime = expert.get(0).getAuditAt();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(auditTime);
		calendar.add(Calendar.MONTH,3);//三个月后的日期
		Date offTime = calendar.getTime();//三个月后的日期（Date类型）
		if(new Date().getTime()<=offTime.getTime()){
			str = "1";//可以重考
		}else{
			str = "0";//考试时间已截止
		}
		return str;
	}

	/**
	 * 
	 * @Title: startRule
	 * @author ZhaoBo
	 * @date 2016-11-27 下午3:49:55  
	 * @Description: 启用考试规则 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/startRule")
	public String startRule(HttpServletRequest request){
		String id = request.getParameter("id");
		List<ExamRule> ruleList = examRuleService.selectById(null);
		for(int i=0;i<ruleList.size();i++){
			ExamRule examRule = new ExamRule();
			if(id.equals(ruleList.get(i).getId())){
				examRule.setId(id);
				examRule.setStatus(1);
				examRuleService.updateByPrimaryKeySelective(examRule);
			}else{
				examRule.setId(ruleList.get(i).getId());
				examRule.setStatus(0);
				examRuleService.updateByPrimaryKeySelective(examRule);
			}
		}
		return "redirect:ruleList.html";
	}

	/**
	 * 
	 * @Title: poiExcel
	 * @author ZhaoBo
	 * @date 2016-9-22 上午9:05:46  
	 * @Description: 导入Excel的公共方法 
	 * @param @param session
	 * @param @param file
	 * @param @return
	 * @param @throws IOException      
	 * @return File
	 */
	
	public File poiExcel(HttpSession session,CommonsMultipartFile file) throws IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
		String saveDirectoryPath = curProjectPath + "/" + uploadFolderName; 
		// 判断文件是否存在  
		String fileName = null;
		File excelFile = null;
		if (!file.isEmpty()) {  
			fileName = file.getOriginalFilename();  
			String fileExtension = FilenameUtils.getExtension(fileName);   
			if(!Arrays.asList(extensionPermit).contains(fileExtension)){
 
			} 
			excelFile = new File(saveDirectoryPath,System.currentTimeMillis()+file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), excelFile);
		}
		return excelFile;
	}


}
