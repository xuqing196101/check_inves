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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamQuestionType;
import ses.model.ems.ExamRule;
import ses.model.ems.ExamUserScore;
import ses.service.ems.ExamPaperServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExamQuestionTypeServiceI;
import ses.service.ems.ExamRuleServiceI;
import ses.service.ems.ExamUserScoreServiceI;
import ses.util.PathUtil;


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
	public String searchTecExpPool(Model model){
		List<ExamQuestion> technicalList = examQuestionService.searchTecExpPool();
		List<ExamQuestion> ntechnicalList = new ArrayList<ExamQuestion>();
		for(int i = 0;i<technicalList.size();i++){
			if(technicalList.get(i).getExamQuestionType().getName().equals("单选题")){
				ntechnicalList.add(technicalList.get(i));
			}
		}
		for(int i = 0;i<technicalList.size();i++){
			if(technicalList.get(i).getExamQuestionType().getName().equals("多选题")){
				ntechnicalList.add(technicalList.get(i));
			}
		}
		model.addAttribute("technicalList",ntechnicalList);
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
	public String searchComExpPool(Model model){
		List<ExamQuestion> commerceList = examQuestionService.searchComExpPool();
		List<ExamQuestion> ncommerceList = new ArrayList<ExamQuestion>();
		for(int i = 0;i<commerceList.size();i++){
			if(commerceList.get(i).getExamQuestionType().getName().equals("单选题")){
				ncommerceList.add(commerceList.get(i));
			}
		}
		for(int i = 0;i<commerceList.size();i++){
			if(commerceList.get(i).getExamQuestionType().getName().equals("多选题")){
				ncommerceList.add(commerceList.get(i));
			}
		}
		model.addAttribute("commerceList",ncommerceList);
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
//		List<ExamPool> nlawList = new ArrayList<ExamPool>();
//		for(int i = 0;i<lawList.size();i++){
//			if(lawList.get(i).getExamPoolType().getTypeName().equals("单选题")){
//				nlawList.add(lawList.get(i));
//			}
//		}
//		for(int i = 0;i<lawList.size();i++){
//			if(lawList.get(i).getExamPoolType().getTypeName().equals("多选题")){
//				nlawList.add(lawList.get(i));
//			}
//		}
		model.addAttribute("list",new PageInfo<ExamQuestion>(lawList));
//		System.out.println(nlawList);
//		System.out.println(111);
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
	* @date 2016-9-7 上午11:06:40  
	* @Description: 增加技术类的题库 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToTec")
	public String saveToTec(HttpServletRequest request,ExamQuestion examQuestion){
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
		examQuestion.setKind(0);
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
	public String saveScore(Model model,HttpServletRequest request,ExamUserScore examUserScore){
		String[] queAnswer = request.getParameter("lawAnswer").split(",");
		String[] quePoint = request.getParameter("lawPoint").split(",");
		Integer score = 0;
		for(int i=0;i<queAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				continue;
			}else{
				String[] queUserAnswer = request.getParameterValues("que"+(i+1));
				for(int j=0;j<queUserAnswer.length;j++){
					sb.append(queUserAnswer[j]);
				}
				if(queAnswer[i].equals(sb.toString())){
					score = Integer.parseInt(quePoint[i])+score;
				}
			}
		}
		model.addAttribute("score", score);
		List<ExamRule> examRule = examRuleService.select();
		String passStandard = examRule.get(0).getPassStandard();
		if(score>=Integer.parseInt(passStandard)){
			examUserScore.setStatus("及格");
			examUserScore.setTargetDate(new Date());
		}else{
			examUserScore.setStatus("不及格");
		}
		examUserScore.setTestDate(new Date());
		examUserScore.setScore(String.valueOf(score));
		examUserScoreService.insertSelective(examUserScore);
		return "ses/ems/exam/expert/score";
	}
	
	/**
	 * 
	* @Title: result
	* @author ZhaoBo
	* @date 2016-9-7 上午11:11:28  
	* @Description: 跳转到专家考试成绩查询页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/result")
	public String result(){
		return "ses/ems/exam/expert/result";
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
	public String test(Model model,HttpServletRequest request,ExamQuestion examPool){
		List<ExamRule> examRule = examRuleService.select();
		examPool.setQueNum(examRule.get(0).getQuestionCount());
		List<ExamQuestion> lawQueRandom = examQuestionService.selectLawRandom(examPool);
		Integer queNum = lawQueRandom.size();
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
		List<ExamQuestion> nlawQueRandom = new ArrayList<ExamQuestion>();
		for(int i=0;i<lawQueRandom.size();i++){
			if(lawQueRandom.get(i).getExamQuestionType().getName().equals("单选题")){
				nlawQueRandom.add(lawQueRandom.get(i));
			}
		}
		for(int i=0;i<lawQueRandom.size();i++){
			if(lawQueRandom.get(i).getExamQuestionType().getName().equals("多选题")){
				nlawQueRandom.add(lawQueRandom.get(i));
			}
		}
		StringBuffer sb_answer = new StringBuffer();
		StringBuffer sb_point = new StringBuffer();
		for(int i=0;i<nlawQueRandom.size();i++){
			sb_answer.append(nlawQueRandom.get(i).getAnswer()+",");
			sb_point.append(nlawQueRandom.get(i).getPoint()+",");
		}
		model.addAttribute("queAnswer", sb_answer.toString());
		model.addAttribute("quePoint", sb_point.toString());
		model.addAttribute("queRandom",nlawQueRandom);
		model.addAttribute("examRule", examRule.get(0));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", pageNum.size());
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
	public String createRule(){
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
		String testTime = request.getParameter("testTime");
		String passStandard = request.getParameter("passStandard");
		String queNum = request.getParameter("queNum");
		String paperScore = request.getParameter("paperScore");
		String testCycle = request.getParameter("testCycle");
		List<ExamRule> ruleList = examRuleService.select();
		Date now = new Date();   //当前时间
		Date dNow = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(now);//把当前时间赋给日历
		calendar.add(calendar.MONTH, 1);  
		dNow = calendar.getTime();   
		if(ruleList.size()==0){
			examRule.setPassStandard(passStandard);
			examRule.setQuestionCount(Integer.parseInt(queNum));
			examRule.setTestCycle(testCycle);
			examRule.setTestTime(testTime);
			examRule.setPaperScore(paperScore);
			examRule.setCreatedAt(new Date());
			examRule.setTestLong(dNow);
			examRuleService.insertSelective(examRule);
		}else{
			examRule.setPassStandard(passStandard);
			examRule.setQuestionCount(Integer.parseInt(queNum));
			examRule.setTestCycle(testCycle);
			examRule.setTestTime(testTime);
			examRule.setPaperScore(paperScore);
			examRule.setTestLong(dNow);
			examRuleService.updateByPrimaryKeySelective(examRule);
		}
		return "redirect:/login/index.html";
	}
	
	/**
	 * 
	* @Title: selectExpertResultByTerm
	* @author ZhaoBo
	* @date 2016-9-8 下午6:23:20  
	* @Description: 专家考试成绩(按条件查询) 
	* @param @param request
	* @param @param examUserScore
	* @param @return      
	* @return List<ExamUserScore>
	 */
	@RequestMapping("/selectExpertResultByTerm")
	@ResponseBody
	public List<ExamUserScore> selectExpertResultByTerm(HttpServletRequest request,ExamUserScore examUserScore,HttpServletResponse response){
		if(!request.getParameter("userName").isEmpty()){
			String name = request.getParameter("userName");
			examUserScore.setRelName("%"+name+"%");
		}
		if(!request.getParameter("userType").isEmpty()){
			String userType = request.getParameter("userType");
			if(userType.equals("1")){
				examUserScore.setUserDuty("技术");
			}else if(userType.equals("2")){
				examUserScore.setUserDuty("法律");
			}else if(userType.equals("3")){
				examUserScore.setUserDuty("商务");
			}
		}
		if(!request.getParameter("testState").isEmpty()){
			examUserScore.setStatus(request.getParameter("testState"));
		}
		List<ExamUserScore> userList = examUserScoreService.selectExpertResultByTerm(examUserScore);
		System.out.println(122345);
		return userList;
	}
	
	/**
	 * 
	* @Title: judgeQualy
	* @author ZhaoBo
	* @date 2016-9-9 下午2:46:36  
	* @Description: 判断当前时间是否过了考试周期 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/judgeQualy")
	@ResponseBody
	public String judgeQualy(){
		List<ExamRule> examRule = examRuleService.select();
		Date ruleDate = examRule.get(0).getTestLong();
		if(ruleDate.getTime()>new Date().getTime()){
			return "1";
		}else{
			return "0";
		}
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
}
