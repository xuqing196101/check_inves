/**
 * 
 */
package ses.controller.sys.ems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamQuestionType;
import ses.model.ems.ExamRule;
import ses.model.ems.ExamUserAnswer;
import ses.model.ems.ExamUserScore;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExamQuestionTypeServiceI;
import ses.service.ems.ExamRuleServiceI;
import ses.service.ems.ExamUserAnswerServiceI;
import ses.service.ems.ExamUserScoreServiceI;
import ses.service.ems.ExpertService;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;


/**
 * @Title:ExpertExamController 
 * @Description: 专家考试Controller
 * @author ZhaoBo
 * @date 2016-9-7上午9:51:01
 */
@Controller
@RequestMapping("/expertExam")
public class ExpertExamController {
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
	public String searchTecExpPool(Model model,Integer page){
		List<ExamQuestion> technicalList = examQuestionService.searchTecExpPool(null,page==null?1:page);
		model.addAttribute("technicalList",new PageInfo<ExamQuestion>(technicalList));
		return "ses/ems/exam/expert/technical/list";
	}
	
	/**
	 * 
	* @Title: searchComExpPool
	* @author ZhaoBo
	* @date 2016-9-7 上午11:02:26  
	* @Description: 查询商务类专家题库 
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/searchComExpPool")
	public String searchComExpPool(Model model,Integer page){
		List<ExamQuestion> commerceList = examQuestionService.searchComExpPool(null,page==null?1:page);
		model.addAttribute("commerceList",new PageInfo<ExamQuestion>(commerceList));
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
	public String searchLawExpPool(Model model,Integer page){
		List<ExamQuestion> lawList = examQuestionService.searchLawExpPool(null,page==null?1:page);		
		model.addAttribute("list",new PageInfo<ExamQuestion>(lawList));
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
	public String addLaw(){
		return "ses/ems/exam/expert/law/add";
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
	public String addTechnical(){
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
	public String addCommerce(){
		return "ses/ems/exam/expert/commerce/add";
	}
	
	/**
	 * 
	* @Title: saveToLaw
	* @author ZhaoBo
	* @date 2016-9-7 上午11:06:40  
	* @Description: 增加法律类的题库 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToLaw")
	public String saveToLaw(HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		examQuestion.setPersonType(1);
		examQuestion.setKind(2);
		examQuestion.setCreatedAt(new Date());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
		examQuestionService.insertSelective(examQuestion);
		return "redirect:searchLawExpPool.html";
	}
	
	/**
	 * 
	* @Title: saveToTec
	* @author ZhaoBo
	* @date 2016-10-6 下午4:34:17  
	* @Description: 增加技术类的题库  
	* @param @param question
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToTec")
	public String saveToTec(HttpServletRequest request,Model model){
			ExamQuestion examQuestion = new ExamQuestion();
			examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
			examQuestion.setTopic(request.getParameter("topic"));
			String[] queOption = request.getParameterValues("option");
			StringBuffer sb_option = new StringBuffer();
			sb_option.append("A."+queOption[0].trim()+";");
			sb_option.append("B."+queOption[1].trim()+";");
			sb_option.append("C."+queOption[2].trim()+";");
			sb_option.append("D."+queOption[3].trim()+";");
			examQuestion.setItems(sb_option.toString());
			examQuestion.setPersonType(1);
			examQuestion.setKind(0);
			examQuestion.setCreatedAt(new Date());
			StringBuffer sb_answer = new StringBuffer();
			if(request.getParameter("que")!=null){
				String[] queSelect = request.getParameterValues("que");
				for(int i = 0;i<queSelect.length;i++){
					sb_answer.append(queSelect[i]);
				}
			}
			examQuestion.setAnswer(sb_answer.toString());
			examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
			examQuestionService.insertSelective(examQuestion);
			return "redirect:searchTecExpPool.html";
	}
	
	/**
	 * 
	* @Title: saveToCom
	* @author ZhaoBo
	* @date 2016-9-8 上午9:07:47  
	* @Description: 增加技术类的题库 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToCom")
	public String saveToCom(HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		examQuestion.setPersonType(1);
		examQuestion.setKind(1);
		examQuestion.setCreatedAt(new Date());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
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
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("lawQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("lawAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
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
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("tecQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("tecAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
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
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("comQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("comAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
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
		examQuestion.setId(request.getParameter("id"));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:searchLawExpPool.html";
	}
	
	/**
	 * 
	* @Title: editToTec
	* @author ZhaoBo
	* @date 2016-9-7 上午11:07:55  
	* @Description: 修改并保存法律类的题库  
	* @param @param model
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editToTec")
	public String editToTec(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setId(request.getParameter("id"));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:searchTecExpPool.html";
	}
	
	/**
	 * 
	* @Title: editToCom
	* @author ZhaoBo
	* @date 2016-9-8 上午9:19:24  
	* @Description: 修改并保存法律类的题库 
	* @param @param model
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editToCom")
	public String editToCom(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setId(request.getParameter("id"));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestion.setPoint(Integer.parseInt(request.getParameter("quePoint")));
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
	@RequestMapping(value="/saveScore",method =RequestMethod.POST)
	public String saveScore(Model model,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		Expert expert = expertService.selectByPrimaryKey(user.getTypeId());
		String[] queAnswer = request.getParameter("lawAnswer").split(",");
		String[] quePoint = request.getParameter("lawPoint").split(",");
		String[] queId = request.getParameter("lawId").split(",");
		Integer score = 0;
		for(int i=0;i<queAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(" ");
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setQuestionId(queId[i]);
				examUserAnswer.setUserType(1);
				examUserAnswer.setUserId(user.getId());
				examUserAnswerService.insertSelective(examUserAnswer);
				continue;
			}else{
				String[] queUserAnswer = request.getParameterValues("que"+(i+1));
				for(int j=0;j<queUserAnswer.length;j++){
					sb.append(queUserAnswer[j]);
				}
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(sb.toString());
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setQuestionId(queId[i]);
				examUserAnswer.setUserType(1);
				examUserAnswer.setUserId(user.getId());
				examUserAnswerService.insertSelective(examUserAnswer);
				if(queAnswer[i].equals(sb.toString())){
					score = Integer.parseInt(quePoint[i])+score;
				}
			}
		}
		List<ExamRule> examRule = examRuleService.select();
		String passStandard = examRule.get(0).getPassStandard();
		HashMap<String,Object> userId = new HashMap<String,Object>();
		userId.put("userId", user.getId());
		List<ExamUserScore> userScores = examUserScoreService.findByUserId(userId);
		if(userScores.size()==0){
			ExamUserScore examUserScore = new ExamUserScore();
			if(score>=Integer.parseInt(passStandard)){
				examUserScore.setStatus("及格");
				examUserScore.setTargetDate(new Date());
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
			examUserScore.setIsMax(1);
			examUserScore.setUserId(user.getId());
			examUserScore.setCreatedAt(new Date());
			examUserScore.setTestDate(new Date());
			examUserScore.setScore(String.valueOf(score));
			examUserScoreService.insertSelective(examUserScore);
		}else{
			for(int i=0;i<userScores.size();i++){
				Integer currentUserScore = Integer.parseInt(userScores.get(i).getScore());
				if(score<currentUserScore){
					ExamUserScore examUserScore = new ExamUserScore();
					if(score>=Integer.parseInt(passStandard)){
						examUserScore.setStatus("及格");
						examUserScore.setTargetDate(new Date());
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
					examUserScore.setTestDate(new Date());
					examUserScore.setScore(String.valueOf(score));
					examUserScoreService.insertSelective(examUserScore);
					break;
				}else if(i==userScores.size()-1){
					for(int j=0;j<userScores.size();j++){
						ExamUserScore examUserScoreTwo = new ExamUserScore();
						examUserScoreTwo.setUserId(user.getId());
						examUserScoreTwo.setIsMax(0);
						examUserScoreService.updateIsMaxByUserId(examUserScoreTwo);
					}
					ExamUserScore examUserScore = new ExamUserScore();
					if(score>=Integer.parseInt(passStandard)){
						examUserScore.setStatus("及格");
						examUserScore.setTargetDate(new Date());
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
					examUserScore.setIsMax(1);
					examUserScore.setUserId(user.getId());
					examUserScore.setCreatedAt(new Date());
					examUserScore.setTestDate(new Date());
					examUserScore.setScore(String.valueOf(score));
					examUserScoreService.insertSelective(examUserScore);
				}
			}
		}
		if(expert.getIsDo()==null){
			Expert personal = new Expert();
			personal.setId(user.getTypeId());
			personal.setIsDo("1");
			expertService.updateByPrimaryKeySelective(personal);
		}
		model.addAttribute("score", score);
		return "ses/ems/exam/expert/score";
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
	* @param @return      
	* @return String
	 */
	@RequestMapping("/ready")
	public String ready(Model model){
		List<ExamRule> examRule = examRuleService.select();
		model.addAttribute("testCycle", examRule.get(0).getTestCycle());
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
		Expert expert = expertService.selectByPrimaryKey(typeId);
		List<ExamQuestion> questionList = new ArrayList<ExamQuestion>();
		List<ExamRule> examRule = examRuleService.select();
		examQuestion.setQueNum(examRule.get(0).getQuestionCount());
		if(expert.getExpertsTypeId().equals("1")){
			questionList = examQuestionService.selectTecRandom(examQuestion);
		}else if(expert.getExpertsTypeId().equals("2")){
			questionList = examQuestionService.selectLawRandom(examQuestion);
		}else if(expert.getExpertsTypeId().equals("3")){
			questionList = examQuestionService.selectComRandom(examQuestion);
		}
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
		StringBuffer sb_point = new StringBuffer();
		StringBuffer sb_id = new StringBuffer();
		for(int i=0;i<nQuestionList.size();i++){
			sb_answer.append(nQuestionList.get(i).getAnswer()+",");
			sb_point.append(nQuestionList.get(i).getPoint()+",");
			sb_id.append(nQuestionList.get(i).getId()+",");
		}
		model.addAttribute("queAnswer", sb_answer.toString());
		model.addAttribute("quePoint", sb_point.toString());
		model.addAttribute("queId", sb_id.toString());
		model.addAttribute("queRandom",nQuestionList);
		model.addAttribute("examRule", examRule.get(0));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageNum.size());
		model.addAttribute("queCount", nQuestionList.size());
		model.addAttribute("user", user);
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
		String id = request.getParameter("id");
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("lawQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("lawAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
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
		String id = request.getParameter("id");
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("tecQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("tecAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
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
		String id = request.getParameter("id");
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("comQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("comAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectExpertAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
		return "ses/ems/exam/expert/commerce/view";
	}
	
	/**
	 * 
	* @Title: createRule
	* @author ZhaoBo
	* @date 2016-9-8 上午10:28:43  
	* @Description: 创建考试规则页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/createRule")
	public String createRule(Model model){
		List<ExamRule> ruleList = examRuleService.select();
		if(ruleList.size()>0){
			model.addAttribute("rule", ruleList.get(0));
		}
		List<ExamQuestion> examQuestion = examQuestionService.searchExpertPool();
		if(examQuestion.size()!=0){
			model.addAttribute("point", examQuestion.get(0).getPoint());
		}
		return "ses/ems/exam/expert/rule";
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
	 */
	@RequestMapping("/saveExamRule") 
	public String saveExamRule(HttpServletRequest request,ExamRule examRule){
//		String testTime = request.getParameter("testTime");
		String passStandard = request.getParameter("passStandard");
		String queNum = request.getParameter("queNum");
		String paperScore = request.getParameter("paperScore");
		String testCycle = request.getParameter("testCycle");
		List<ExamRule> ruleList = examRuleService.select();
		if(ruleList.size()==0){
			Date now = new Date();
			Date dNow = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(now);
			calendar.add(calendar.MONTH, Integer.parseInt(testCycle));  
			dNow = calendar.getTime();   
			examRule.setPassStandard(passStandard);
			examRule.setQuestionCount(Integer.parseInt(queNum));
			examRule.setTestCycle(testCycle);
//			examRule.setTestTime(testTime);
			examRule.setPaperScore(paperScore);
			examRule.setCreatedAt(new Date());
			examRule.setTestLong(dNow);
			examRuleService.insertSelective(examRule);
		}else{
			Date now = ruleList.get(0).getCreatedAt();
			Date dNow = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(now);
			calendar.add(calendar.MONTH, Integer.parseInt(testCycle));  
			dNow = calendar.getTime();   
			examRule.setPassStandard(passStandard);
			examRule.setQuestionCount(Integer.parseInt(queNum));
			examRule.setTestCycle(testCycle);
//			examRule.setTestTime(testTime);
			examRule.setPaperScore(paperScore);
			examRule.setTestLong(dNow);
			examRuleService.updateByPrimaryKeySelective(examRule);
		}
		return "redirect:/login/index.html";
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
	public String result(Model model,HttpServletRequest request,Integer page){
		List<ExamRule> examRule = examRuleService.select();
		if(examRule.get(0).getTestLong().getTime()<new Date().getTime()){
			List<Expert> expertList = examUserScoreService.findAllExpert();
			for(int i=0;i<expertList.size();i++){
				if(expertList.get(i).getIsDo()==null){
					ExamUserScore examUserScore = new ExamUserScore();
					User user = new User();
					user.setTypeId(expertList.get(i).getId());
					List<User> userList = userService.queryByList(user);
					examUserScore.setUserId(userList.get(0).getId());
					examUserScore.setCreatedAt(new Date());
					examUserScore.setIsMax(1);
					examUserScore.setUserType(1);
					examUserScore.setScore("0");
					examUserScore.setStatus("不及格");
					if(expertList.get(i).getExpertsTypeId().equals("1")){
						examUserScore.setUserDuty("技术");
					}else if(expertList.get(i).getExpertsTypeId().equals("2")){
						examUserScore.setUserDuty("法律");
					}else if(expertList.get(i).getExpertsTypeId().equals("3")){
						examUserScore.setUserDuty("商务");
					}
					examUserScoreService.insertSelective(examUserScore);
					Expert personalExpert = new Expert();
					personalExpert.setId(expertList.get(i).getId());
					personalExpert.setIsDo("2");
					expertService.updateByPrimaryKeySelective(personalExpert);
				}
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		String userName = request.getParameter("userName");
		String userType = request.getParameter("userType");
		String status = request.getParameter("status");
		if(userName!=null && userName!=""){
			map.put("relName", "%"+userName+"%");
		}
		if(userType!=null && userType!=""){
			if(userType.equals("1")){
				map.put("userDuty", "技术");
			}else if(userType.equals("2")){
				map.put("userDuty", "法律");
			}else if(userType.equals("3")){
				map.put("userDuty", "商务");
			}
		}
		if(status!=null && status!=""){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamUserScore> expertResultList = examUserScoreService.selectExpertResultByCondition(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for(int i=0;i<expertResultList.size();i++){
			expertResultList.get(i).setFormatDate(sdf.format(expertResultList.get(i).getTestDate()));
		}
		model.addAttribute("expertResultList", new PageInfo<ExamUserScore>(expertResultList));
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
	public String personalResult(Integer page,Model model,HttpServletRequest request,ExamUserScore examUserScore){
		User user = (User) request.getSession().getAttribute("loginUser");
		Expert expert = expertService.selectByPrimaryKey(user.getTypeId());
		List<ExamRule> examRule = examRuleService.select();
		if(examRule.get(0).getTestLong().getTime()<new Date().getTime()){
			if(expert.getIsDo()==null){
				ExamUserScore score = new ExamUserScore();
				score.setUserId(user.getId());
				score.setCreatedAt(new Date());
				score.setIsMax(1);
				score.setUserType(1);
				score.setScore("0");
				score.setStatus("不及格");
				if(expert.getExpertsTypeId().equals("1")){
					score.setUserDuty("技术");
				}else if(expert.getExpertsTypeId().equals("2")){
					score.setUserDuty("法律");
				}else if(expert.getExpertsTypeId().equals("3")){
					score.setUserDuty("商务");
				}
				examUserScoreService.insertSelective(score);
				Expert personal = new Expert();
				personal.setId(user.getTypeId());
				personal.setIsDo("2");
				expertService.updateByPrimaryKeySelective(personal);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<scores.size();i++){
			scores.get(i).setRelName(user.getRelName());
			if(scores.get(i).getTestDate()!=null){
				scores.get(i).setFormatDate(sdf.format(scores.get(i).getTestDate()));
			}
		}
		model.addAttribute("list", new PageInfo<ExamUserScore>(scores));
		return "ses/ems/exam/expert/personal_result";
	}
	
	
	/**
	 * 
	* @Title: judgeQualy
	* @author ZhaoBo
	* @date 2016-9-9 下午2:46:36  
	* @Description: 判断当前时间是否过了考试周期,并且判断当前用户是不是专家 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/judgeQualy")
	@ResponseBody
	public String judgeQualy(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		Integer type = user.getTypeName();
		String str = null;
		if(type==5){
			List<ExamRule> examRule = examRuleService.select();
			Date ruleDate = examRule.get(0).getTestLong();
			if(ruleDate.getTime()>=new Date().getTime()){
				str = "1";
			}else{
				str = "0";
			}
		}else{
			str = "2";
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
	@ResponseBody
	public String importTec(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
        String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;  
        // File newFileName = new File(saveDirectoryPath); 
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
        //File excelFile = new File(newFileName, fileName);
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		Sheet sheet = workbook.getSheetAt(0);
		for (int j = 1; j <= sheet.getPhysicalNumberOfRows(); j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queOption = row.getCell(2);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind(0);
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setItems(queOption.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setPoint((int) quePoint.getNumericCellValue());
				if (queType.toString().equals("单选题")) {
					examQuestion.setQuestionTypeId(1);
				} else {
					examQuestion.setQuestionTypeId(2);
				}
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
		}
		return "1";
	}
	
	/**
	 * 
	* @Title: importLaw
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
	@RequestMapping(value="/importLaw",method = RequestMethod.POST)
	@ResponseBody
	public String importLaw(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
        String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;  
        // File newFileName = new File(saveDirectoryPath); 
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
        //File excelFile = new File(newFileName, fileName);
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		Sheet sheet = workbook.getSheetAt(0);
		for (int j = 1; j <= sheet.getPhysicalNumberOfRows(); j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queOption = row.getCell(2);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind(2);
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setItems(queOption.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setPoint((int) quePoint.getNumericCellValue());
				if (queType.toString().equals("单选题")) {
					examQuestion.setQuestionTypeId(1);
				} else {
					examQuestion.setQuestionTypeId(2);
				}
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
		}
		return "1";
	}
	
	/**
	 * 
	* @Title: importCom
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
	@RequestMapping(value="/importCom",method = RequestMethod.POST)
	@ResponseBody
	public String importCom(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		String curProjectPath = session.getServletContext().getRealPath("/");  
        String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;  
        // File newFileName = new File(saveDirectoryPath); 
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
        //File excelFile = new File(newFileName, fileName);
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		Sheet sheet = workbook.getSheetAt(0);
		for (int j = 1; j <= sheet.getPhysicalNumberOfRows(); j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queOption = row.getCell(2);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(1);
				examQuestion.setKind(1);
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setItems(queOption.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setPoint((int) quePoint.getNumericCellValue());
				if (queType.toString().equals("单选题")) {
					examQuestion.setQuestionTypeId(1);
				} else {
					examQuestion.setQuestionTypeId(2);
				}
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
		}
		return "1";
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
}
