/**
 * 
 */
package ses.controller.sys.ems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.ems.ExamPaper;
import ses.model.ems.ExamPaperReference;
import ses.model.ems.ExamPaperUser;
import ses.model.ems.ExamQuestion;
import ses.model.ems.ExamQuestionType;
import ses.model.ems.ExamUserAnswer;
import ses.model.ems.ExamUserScore;
import ses.model.oms.PurchaseInfo;
import ses.service.ems.ExamPaperServiceI;
import ses.service.ems.ExamPaperUserServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExamQuestionTypeServiceI;
import ses.service.ems.ExamUserAnswerServiceI;
import ses.service.ems.ExamUserScoreServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.PathUtil;
import ses.util.PropertiesUtil;


/**
 * @Title:PurchaserExamController 
 * @Description: 采购人考试Controller层
 * @author ZhaoBo
 * @date 2016-9-7上午10:39:06
 */
@Controller
@RequestMapping("/purchaserExam")
public class PurchaserExamController extends BaseSupplierController{
	@Autowired
	private ExamQuestionServiceI examQuestionService;
	@Autowired
	private ExamQuestionTypeServiceI examQuestionTypeService;
	@Autowired
	private ExamUserScoreServiceI examUserScoreService;
	@Autowired
	private ExamPaperServiceI examPaperService;
	@Autowired
	private ExamPaperUserServiceI examPaperUserService;
	@Autowired
	private ExamUserAnswerServiceI examUserAnswerService;
	@Autowired
	private PurchaseServiceI purchaseService;
	
	
	/**
	 * 
	* @Title: purchaserList
	* @author ZhaoBo
	* @date 2016-9-23 上午9:46:19  
	* @Description: 采购人题库管理页面 
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/purchaserList")
	public String purchaserList(Model model,Integer page,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String questionTypeId = request.getParameter("questionTypeId");
		String topic = request.getParameter("topic");
		if(questionTypeId !=null && questionTypeId!=""){
			map.put("questionTypeId", Integer.parseInt(questionTypeId));
		}
		if(topic != null && topic!=""){
			map.put("topic", "%"+topic+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamQuestion> queryList = examQuestionService.queryPurchaserByTerm(map);
		model.addAttribute("purchaserQuestionList", new PageInfo<ExamQuestion>(queryList));
		model.addAttribute("topic", topic);
		model.addAttribute("questionTypeId", questionTypeId);
		return "ses/ems/exam/purchaser/question/list";
	}
	
	/**
	 * 
	* @Title: addPurQue
	* @author ZhaoBo
	* @date 2016-9-7 上午11:27:18  
	* @Description: 采购人新增题库页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPurQue")
	public String addPurQue(){
		return "ses/ems/exam/purchaser/question/add";
	}
	
	/**
	 * 
	* @Title: saveToPurPool
	* @author ZhaoBo
	* @date 2016-9-7 上午11:28:10  
	* @Description: 采购人新增题库方法 
	* @param @param model
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToPurPool")
	public String saveToPurPool(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		examQuestion.setQuestionTypeId(Integer.parseInt(request.getParameter("queType")));
		examQuestion.setTopic(request.getParameter("queTopic"));
		String[] queOption = request.getParameterValues("option");
		StringBuffer sb_option = new StringBuffer();
		sb_option.append("A."+queOption[0].trim()+";");
		sb_option.append("B."+queOption[1].trim()+";");
		sb_option.append("C."+queOption[2].trim()+";");
		sb_option.append("D."+queOption[3].trim()+";");
		examQuestion.setItems(sb_option.toString());
		examQuestion.setPersonType(2);
		examQuestion.setCreatedAt(new Date());
		StringBuffer sb = new StringBuffer();
		if(request.getParameter("que")!=null){
			String[] queSelect = request.getParameterValues("que");
			for(int i = 0;i<queSelect.length;i++){
				sb.append(queSelect[i]);
			}
		}
		if(request.getParameter("judge")!=null){
			String[] queJudge = request.getParameterValues("judge");
			for(int i = 0;i<queJudge.length;i++){
				sb.append(queJudge[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestionService.insertSelective(examQuestion);
		return "redirect:purchaserList.html";
	}
	
	/**
	 * 
	* @Title: editPurQue
	* @author ZhaoBo
	* @date 2016-9-7 上午11:28:50  
	* @Description: 采购人修改题库页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPurQue")
	public String editPurQue(HttpServletRequest request,Model model){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("purchaserQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("purchaserAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examQuestion.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
		return "ses/ems/exam/purchaser/question/edit";
	}
	
	/**
	 * 
	* @Title: editToPurchaser
	* @author ZhaoBo
	* @date 2016-9-7 上午11:29:36  
	* @Description: 采购人题库修改保存 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editToPurchaser")
	public String editToPurchaser(HttpServletRequest request,ExamQuestion examQuestion){
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
		if(request.getParameter("judge")!=null){
			String[] queJudge = request.getParameterValues("judge");
			for(int i = 0;i<queJudge.length;i++){
				sb.append(queJudge[i]);
			}
		}
		examQuestion.setAnswer(sb.toString());
		examQuestionService.updateByPrimaryKeySelective(examQuestion);
		return "redirect:purchaserList.html";
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
	public Integer deleteById(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		Integer id = null;
		for(int i = 0;i<ids.length;i++){
			id = examQuestionService.deleteByPrimaryKey(ids[i]);
		}
		return id;
	}
	
	private static final String uploadFolderName = "uploadFiles"; //上传到服务器的文件夹名 
	private static String [] extensionPermit = {"xls","xlsx"}; //允许上传的文件格式
	
	/**
	 * 
	* @Title: importExcel
	* @author ZhaoBo
	* @date 2016-9-7 上午11:31:37  
	* @Description: 导入Excel(采购人题库)  
	* @param @param file
	* @param @param session
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException      
	* @return String
	 */
	@RequestMapping(value="/importExcel",method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		File excelFile = poiExcel(session,file);
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		Sheet sheet = workbook.getSheetAt(0);
		for (int j=1;j<=sheet.getPhysicalNumberOfRows();j++) {
			Row row = sheet.getRow(j);
			if (row==null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queOption = row.getCell(2);
				Cell queAnswer = row.getCell(3);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(2);
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setItems(queOption.toString());
				examQuestion.setAnswer(queAnswer.toString());
				if(queType.toString().equals("单选题")) {
					examQuestion.setQuestionTypeId(1);
				}else{
					examQuestion.setQuestionTypeId(2);
				}
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
			if(queType.toString().equals("判断题")){
				ExamQuestion examQuestion = new ExamQuestion();
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(3);
				Cell quePoint = row.getCell(4);
				examQuestion.setPersonType(2);
				examQuestion.setItems(" ");
				examQuestion.setTopic(queTopic.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setQuestionTypeId(3);
				examQuestion.setCreatedAt(new Date());
				examQuestionService.insertSelective(examQuestion);
			}
		}
		return "1";
	}
	
	/**
	 * 
	* @Title: timing
	* @author ZhaoBo
	* @date 2016-9-6 下午2:33:17  
	* @Description: 采购人开始考试之前等待页面  
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/timing")
	public String timing(Model model,HttpServletRequest request){
		String paperNo = request.getParameter("paperNo");
		ExamPaper examPaper = examPaperService.selectByPaperNo(paperNo);
		model.addAttribute("paperId", examPaper.getId());
		return "ses/ems/exam/purchaser/timing";
	}
	
	/**
	 * 
	* @Title: test
	* @author ZhaoBo
	* @date 2016-9-6 下午2:39:01  
	* @Description: 采购人开始考试  
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/test")
	public String test(Model model,HttpServletRequest request){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		ExamQuestion single = new ExamQuestion();
		single.setSingleNum(singleNum);
		ExamQuestion multiple = new ExamQuestion();
		multiple.setMultipleNum(multipleNum);
		ExamQuestion judge = new ExamQuestion();
		judge.setJudgeNum(judgeNum);
		List<ExamQuestion> singleQue = examQuestionService.selectSingleRandom(single);
		List<ExamQuestion> multipleQue = examQuestionService.selectMultipleRandom(multiple);
		List<ExamQuestion> judgeQue = examQuestionService.selectJudgeRandom(judge);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(purchaserQue.size()%5==0){
			for(int i=0;i<purchaserQue.size()/5;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<purchaserQue.size()/5+1;i++){
				pageNum.add(i);
			}
		}
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("purQueType",sb_queTypes);
		model.addAttribute("purQueAnswer", sb_answers);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("purchaserQue",purchaserQue);
		model.addAttribute("paperId", paperId);
		model.addAttribute("purQueId", sb_questionIds);
		model.addAttribute("pageSize", pageNum.size());
		model.addAttribute("examPaper", examPaper);
		model.addAttribute("queCount", singleQue.size()+multipleQue.size()+judgeQue.size());
		return "ses/ems/exam/purchaser/test";
	}
	
	/**
	 * 
	* @Title: paperManage
	* @author ZhaoBo
	* @date 2016-9-23 下午3:19:43  
	* @Description: 历史考卷管理页面 
	* @param @param model
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/paperManage")
	public String paperManage(Model model,Integer page){
		List<ExamPaper> paperList = examPaperService.queryAllPaper(null,page==null?1:page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<paperList.size();i++){
			paperList.get(i).setStartTrueDate(sdf.format(paperList.get(i).getStartTime()));
//			String[] expiryDate = paperList.get(i).getExpiryDate().split(",");
//			Integer hour = Integer.parseInt(expiryDate[0]);
//			Integer second = Integer.parseInt(expiryDate[1]);
			String testTime = paperList.get(i).getTestTime();
			Date startTime = paperList.get(i).getStartTime();
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startTime); 
		    calendar.add(calendar.MINUTE,45+Integer.parseInt(testTime));
		    Date endTime = calendar.getTime();
		    if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=endTime.getTime()){
		    	paperList.get(i).setStatus("正在考试中");
			}else if(new Date().getTime()<startTime.getTime()){
				paperList.get(i).setStatus("未开始");
			}else if(new Date().getTime()>endTime.getTime()){
				paperList.get(i).setStatus("已结束");
			}
		}
		model.addAttribute("paperList", new PageInfo<ExamPaper>(paperList));
		return "ses/ems/exam/purchaser/paper/list";
	}
	
	/**
	 * 
	* @Title: addPaper
	* @author ZhaoBo
	* @date 2016-9-7 上午11:35:33  
	* @Description: 采购人新增考卷 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPaper")
	public String addPaper(Model model){
		List<Integer> hour = new ArrayList<Integer>();
		List<Integer> second = new ArrayList<Integer>();
		for(int i=0;i<24;i++){
			hour.add(i);
		}
		for(int i=0;i<60;i++){
			second.add(i);
		}
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		return "ses/ems/exam/purchaser/paper/add";
	}
	
	/**
	 * 
	* @Title: saveToExamPaper
	* @author ZhaoBo
	* @date 2016-9-5 下午4:19:29  
	* @Description: 新增考卷并保存 
	* @param @param request
	* @param @param model
	* @param @param examPaper
	* @param @return      
	* @return String
	 * @throws ParseException 
	 */
	@RequestMapping("/saveToExamPaper")
	public String saveToExamPaper(HttpServletRequest request,Model model,ExamPaper examPaper) throws ParseException{
		examPaper.setCreatedAt(new Date());
		examPaper.setName(request.getParameter("paperName"));
		examPaper.setCode(request.getParameter("paperNo"));
		examPaper.setScore(request.getParameter("totalPoint"));
		examPaper.setTestTime(request.getParameter("useTime"));
		String startTime = request.getParameter("startTime");
		String newHour = null;
		String newSecond = null;
		String hour = request.getParameter("hour");
		String second = request.getParameter("second");
		if(hour.length()==1){
			newHour = "0"+hour;
		}else{
			newHour = hour;
		}
		if(second.length()==1){
			newSecond = "0"+second;
		}else{
			newSecond = second;
		}
		String examStartTime = startTime+" "+newHour+":"+newSecond+":"+"00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		examPaper.setStartTime(sdf.parse(examStartTime));
		String[] isAllow = request.getParameterValues("isAllow");
		if(isAllow[0].equals("是")){
			examPaper.setIsAllowRetake(1);
		}else{
			examPaper.setIsAllowRetake(0);
		}
		examPaper.setYear(startTime.substring(0, 4));
//		String expiryHour = request.getParameter("expiryHour");
//		String expirySecond = request.getParameter("expirySecond");
//		examPaper.setExpiryDate(expiryHour+","+expirySecond);
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
//		String type = "{\"singleNum\":\""+singleNum+"\",\"singlePoint\":\""+singlePoint+"\",\"multipleNum\":\"+multipleNum+\","+
//		  "\"multiplePoint\":\""+multiplePoint+"\",\"judgeNum\":\""+judgeNum+"\",\"judgePoint\":\""+judgePoint+"\"}";
//		JSONObject typeDistribution = new JSONObject(type);
//		examPaper.setTypeDistribution(typeDistribution.toString());
		Map<String,String> map = new HashMap<String,String>();
		map.put("singleNum", singleNum);
		map.put("singlePoint", singlePoint);
		map.put("multipleNum", multipleNum);
		map.put("multiplePoint", multiplePoint);
		map.put("judgeNum", judgeNum);
		map.put("judgePoint", judgePoint);
		JSONSerializer.toJSON(map);
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaperService.insertSelective(examPaper);
		return "redirect:paperManage.html";
	}
	
	/**
	 * 
	* @Title: editSelectedPaper
	* @author ZhaoBo
	* @date 2016-9-6 上午10:18:23  
	* @Description: 判断当前选中考卷是否可编辑 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editSelectedPaper")
	@ResponseBody
	public String editSelectedPaper(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
		String str = null;
//		String[] expiryDate = examPaper.getExpiryDate().split(",");
//		Integer hour = Integer.parseInt(expiryDate[0]);
//		Integer second = Integer.parseInt(expiryDate[1]);
		String testTime = examPaper.getTestTime();
		Date startTime = examPaper.getStartTime();
	    Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(startTime); 
	    calendar.add(calendar.MINUTE,45+Integer.parseInt(testTime));
	    Date endTime = calendar.getTime();
		if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=endTime.getTime()){
			str = "1";
		}else if(new Date().getTime()<startTime.getTime()){
			str = "2";
		}else if(new Date().getTime()>endTime.getTime()){
			str = "3";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: editNoTestPaper
	* @author ZhaoBo
	* @date 2016-9-6 上午10:55:31  
	* @Description: 编辑考卷页面
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editNoTestPaper")
	public String editNoTestPaper(Model model,HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(examPaper.getStartTime());
		String startTime = time.substring(0,10);
		String hour = time.substring(11, 13);
		String second = time.substring(14, 16);
		model.addAttribute("startTime", startTime);
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		List<Integer> hours = new ArrayList<Integer>();
		List<Integer> seconds = new ArrayList<Integer>();
		for(int i=0;i<24;i++){
			hours.add(i);
		}
		for(int i=0;i<60;i++){
			seconds.add(i);
		}
		model.addAttribute("hours", hours);
		model.addAttribute("seconds", seconds);
		model.addAttribute("examPaper", examPaper);
		return "ses/ems/exam/purchaser/paper/edit";
	}
	
	/**
	 * 
	* @Title: editToExamPaper
	* @author ZhaoBo
	* @date 2016-9-5 下午4:19:29  
	* @Description: 编辑考卷并保存 
	* @param @param request
	* @param @param model
	* @param @param examPaper
	* @param @return      
	* @return String
	 * @throws ParseException 
	 */
	@RequestMapping("/editToExamPaper")
	public String editToExamPaper(HttpServletRequest request,Model model,ExamPaper examPaper) throws ParseException{
		examPaper.setId(request.getParameter("paperId"));
		examPaper.setName(request.getParameter("paperName"));
		examPaper.setCode(request.getParameter("paperNo"));
		examPaper.setScore(request.getParameter("totalPoint"));
		examPaper.setTestTime(request.getParameter("useTime"));
		String startTime = request.getParameter("startTime");
		String newHour = null;
		String newSecond = null;
		String hour = request.getParameter("hour");
		String second = request.getParameter("second");
		if(hour.length()==1){
			newHour = "0"+hour;
		}else{
			newHour = hour;
		}
		if(second.length()==1){
			newSecond = "0"+second;
		}else{
			newSecond = second;
		}
		String examStartTime = startTime+" "+newHour+":"+newSecond+":"+"00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		examPaper.setStartTime(sdf.parse(examStartTime));
		String[] isAllow = request.getParameterValues("isAllow");
		if(isAllow[0].equals("是")){
			examPaper.setIsAllowRetake(1);
		}else{
			examPaper.setIsAllowRetake(0);
		}
		examPaper.setYear(startTime.substring(0, 4));
//		String expiryHour = request.getParameter("expiryHour");
//		String expirySecond = request.getParameter("expirySecond");
//		examPaper.setExpiryDate(expiryHour+","+expirySecond);
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
		Map<String,String> map = new HashMap<String,String>();
		map.put("singleNum", singleNum);
		map.put("singlePoint", singlePoint);
		map.put("multipleNum", multipleNum);
		map.put("multiplePoint", multiplePoint);
		map.put("judgeNum", judgeNum);
		map.put("judgePoint", judgePoint);
		JSONSerializer.toJSON(map);
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaperService.updateByPrimaryKeySelective(examPaper);
		return "redirect:paperManage.html";
	}
	
	/**
	 * 
	* @Title: entryPaperNumber
	* @author ZhaoBo
	* @date 2016-9-6 下午1:16:07  
	* @Description: 判断输入的考试编号是否正确 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/entryPaperNumber")
	@ResponseBody
	public String entryPaperNumber(HttpServletRequest request){
		String paperNo = request.getParameter("paperNo");
		ExamPaper examPaper = examPaperService.selectByPaperNo(paperNo);
		String str = null;
		if(examPaper==null){
			str = "0";
		}else if(examPaper!=null){
			String testTime = examPaper.getTestTime();
//			Integer hour = Integer.parseInt(expiryDate[0]);
//			Integer second = Integer.parseInt(expiryDate[1]);
			Date startTime = examPaper.getStartTime();
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startTime); 
		    calendar.add(calendar.MINUTE,15);
		    Date endTime = calendar.getTime();
		    
		    Calendar endcalendar = new GregorianCalendar(); 
		    endcalendar.setTime(startTime); 
		    endcalendar.add(calendar.MINUTE,15+Integer.parseInt(testTime));
		    Date relEndTime = endcalendar.getTime();
		    
			if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=endTime.getTime()){
				str = "1";
			}else if(new Date().getTime()<startTime.getTime()){
				str = "2";
			}else if(new Date().getTime()>endTime.getTime()){
				if(new Date().getTime()>relEndTime.getTime()){
					str = "3";
				}else{
					str = "4";
				}
			}
		}
		return str;
	}
	
	/**
	 * 
	* @Title: purReadyExam
	* @author ZhaoBo
	* @date 2016-9-6 下午1:31:10  
	* @Description: 采购人考试最初页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/ready")
	public String ready(){
		return "ses/ems/exam/purchaser/ready";
	}
	
	/**
	 * 
	* @Title: savePurchaserScore
	* @author ZhaoBo
	* @date 2016-9-6 下午7:01:41  
	* @Description: 采购人答题算总分 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/savePurchaserScore")
	public String savePurchaserScore(Model model,HttpServletRequest request){
		String[] purQueAnswer = request.getParameter("purQueAnswer").split(",");
		String[] purQueType = request.getParameter("purQueType").split(",");
		String[] purQueId = request.getParameter("purQueId").split(",");
		String paperId = request.getParameter("paperId");
		ExamPaper userExamPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = userExamPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleP = (String) obj.get("singlePoint");
		Integer singlePoint = Integer.parseInt(singleP);
		String multipleP = (String) obj.get("multiplePoint");
		Integer multiplePoint = Integer.parseInt(multipleP);
		String judgeP = (String) obj.get("judgePoint");
		Integer judgePoint = Integer.parseInt(judgeP);
		Integer score = 0;
		for(int i=0;i<purQueAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(" ");
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setQuestionId(purQueId[i]);
				examUserAnswer.setUserType(2);
				examUserAnswer.setPaperId(paperId);
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
				examUserAnswer.setQuestionId(purQueId[i]);
				examUserAnswer.setUserType(2);
				examUserAnswer.setPaperId(paperId);
				examUserAnswerService.insertSelective(examUserAnswer);
				if(purQueAnswer[i].equals(sb.toString())){
					if(purQueType[i].equals("单选题")){
						score = score + singlePoint;
					}else if(purQueType[i].equals("多选题")){
						score = score + multiplePoint;
					}else if(purQueType[i].equals("判断题")){
						score = score + judgePoint;
					}
				}
			}
		}
		model.addAttribute("isAllowRetake", userExamPaper.getIsAllowRetake());
		model.addAttribute("score", score);
		model.addAttribute("paperId", paperId);
		String time = request.getParameter("time");
		if(time!=null&&time!=""){
			model.addAttribute("time", request.getParameter("time"));
		}
		return "ses/ems/exam/purchaser/score";
	}
	
	/**
	 * 
	* @Title: result
	* @author ZhaoBo
	* @date 2016-9-23 下午5:28:26  
	* @Description: 采购人考试成绩查询页面 (可以按条件查询)
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return      
	* @return String
	 */
	@RequestMapping("/result")
	public String result(Model model,HttpServletRequest request,Integer page){
		List<ExamPaperUser> reference = examPaperUserService.findAll();
		for(int i=0;i<reference.size();i++){
			ExamPaperUser paperUser = reference.get(i);
			String paperId = paperUser.getPaperId();
			ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
			String testTime = examPaper.getTestTime();
			Date startTime = examPaper.getStartTime();
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startTime); 
		    calendar.add(calendar.MINUTE,45+Integer.parseInt(testTime));
		    Date endTime = calendar.getTime();
		    if(new Date().getTime()>endTime.getTime()){
		    	if(paperUser.getIsDo()==0){
		    		ExamUserScore examUserScore = new ExamUserScore();
		    		examUserScore.setCreatedAt(new Date());
					examUserScore.setUserType(2);
					examUserScore.setUserId(paperUser.getUserId());
					examUserScore.setScore("0");
					examUserScore.setPaperId(paperUser.getPaperId());
					examUserScore.setStatus("不及格");
					examUserScoreService.insertSelective(examUserScore);
					ExamPaperUser paperOfUser = new ExamPaperUser();
					paperOfUser.setId(paperUser.getId());
					paperOfUser.setIsDo(2);
					examPaperUserService.updateByPrimaryKeySelective(paperOfUser);
		    	}
		    	
		    }
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		String relName = request.getParameter("relName");
		String status = request.getParameter("status");
		String code = request.getParameter("code");
		if(relName!=null&&relName!=""){
			map.put("relName", "%"+relName+"%");
		}
		if(code!=null&&code!=""){
			map.put("code", code);
		}
		if(status!=null&&status!=""){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamUserScore> purchaserResultList = examUserScoreService.selectPurchaserResultByCondition(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<purchaserResultList.size();i++){
			if(purchaserResultList.get(i).getTestDate()==null){
				purchaserResultList.get(i).setFormatDate(" ");
			}else{
				purchaserResultList.get(i).setFormatDate(sdf.format(purchaserResultList.get(i).getTestDate()));
			}
		}
		model.addAttribute("purchaserResultList", new PageInfo<ExamUserScore>(purchaserResultList));
		model.addAttribute("relName", relName);
		model.addAttribute("code", code);
		model.addAttribute("status", status);
		return "ses/ems/exam/purchaser/result";
	}
	
	/**
	 * 
	* @Title: printReView
	* @author ZhaoBo
	* @date 2016-9-22 下午2:16:50  
	* @Description: 打印预览
	* @param @return      
	* @return String
	 */
	@RequestMapping("/printReView")
	public String printReView(HttpServletRequest request,ExamPaperUser examPaperUser,Model model){
		examPaperUser.setPaperId(request.getParameter("paperId"));
		List<ExamPaperUser> paperUserList = examPaperUserService.selectPrintYesReference(examPaperUser);
		model.addAttribute("paperUserList",paperUserList);
		return "ses/ems/exam/purchaser/print";
	}
	
	/**
	 * 
	* @Title: reTake
	* @author ZhaoBo
	* @date 2016-9-14 下午2:56:38  
	* @Description: 采购人重考方法 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/reTake")
	public String reTake(Model model,HttpServletRequest request){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		ExamQuestion single = new ExamQuestion();
		single.setSingleNum(singleNum);
		ExamQuestion multiple = new ExamQuestion();
		multiple.setMultipleNum(multipleNum);
		ExamQuestion judge = new ExamQuestion();
		judge.setJudgeNum(judgeNum);
		List<ExamQuestion> singleQue = examQuestionService.selectSingleRandom(single);
		List<ExamQuestion> multipleQue = examQuestionService.selectMultipleRandom(multiple);
		List<ExamQuestion> judgeQue = examQuestionService.selectJudgeRandom(judge);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(purchaserQue.size()%5==0){
			for(int i=0;i<purchaserQue.size()/5;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<purchaserQue.size()/5+1;i++){
				pageNum.add(i);
			}
		}
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("purQueType",sb_queTypes);
		model.addAttribute("purQueAnswer", sb_answers);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("purchaserQue",purchaserQue);
		model.addAttribute("paperId", paperId);
		model.addAttribute("purQueId", sb_questionIds);
		//model.addAttribute("countDown", 1);
		model.addAttribute("time",request.getParameter("time"));
		model.addAttribute("pageSize", pageNum.size());
		model.addAttribute("examPaper", examPaper);
		model.addAttribute("queCount", singleQue.size()+multipleQue.size()+judgeQue.size());
		return "ses/ems/exam/purchaser/test";
	}
	
	/**
	 * 
	* @Title: view
	* @author ZhaoBo
	* @date 2016-9-18 下午5:18:00  
	* @Description: 采购人查看题库页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request,Model model){
		ExamQuestion examPool = examQuestionService.selectByPrimaryKey(request.getParameter("id"));
		model.addAttribute("purchaserQue",examPool);
		String queAnswer = examPool.getAnswer();
		model.addAttribute("purchaserAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examPoolType);
		String[] queOption = examPool.getItems().split(";");
		model.addAttribute("optionA", queOption[0].substring(2));
		model.addAttribute("optionB", queOption[1].substring(2));
		model.addAttribute("optionC", queOption[2].substring(2));
		model.addAttribute("optionD", queOption[3].substring(2));
		return "ses/ems/exam/purchaser/question/view";
	}
	
	/**
	 * 
	* @Title: viewPaper
	* @author ZhaoBo
	* @date 2016-9-19 下午5:18:33  
	* @Description: 查看考卷页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewPaper")
	public String viewPaper(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id);
		model.addAttribute("examPaper", examPaper);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(examPaper.getStartTime());
		String startTime = time.substring(0,10);
		String hour = time.substring(11, 13);
		String second = time.substring(14, 16);
		model.addAttribute("startTime", startTime);
		model.addAttribute("hour", hour);
		model.addAttribute("second", second);
		List<Integer> hours = new ArrayList<Integer>();
		List<Integer> seconds = new ArrayList<Integer>();
		for(int i=0;i<24;i++){
			hours.add(i);
		}
		for(int i=0;i<60;i++){
			seconds.add(i);
		}
		model.addAttribute("hours", hours);
		model.addAttribute("seconds", seconds);
		return "ses/ems/exam/purchaser/paper/view";
	}
	
	/**
	 * 
	* @Title: viewReference
	* @author ZhaoBo
	* @date 2016-9-20 上午9:16:10  
	* @Description: 查看考卷的参考人员(已考和未考) 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewReference")
	public String viewReference(HttpServletRequest request,Model model,Integer page){
		String path = null;
		HashMap<String,Object> map = new HashMap<String,Object>();
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
//		String[] expiryDate = examPaper.getExpiryDate().split(",");
//		Integer hour = Integer.parseInt(expiryDate[0]);
//		Integer second = Integer.parseInt(expiryDate[1]);
		String testTime = examPaper.getTestTime();
		Date startTime = examPaper.getStartTime();
	    Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(startTime); 
	    calendar.add(calendar.MINUTE,45+Integer.parseInt(testTime));
	    Date endTime = calendar.getTime();
	    map.put("paperId", id[0]);
	    if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
	    List<ExamPaperUser> paperUserList = new ArrayList<ExamPaperUser>();
		if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=endTime.getTime()){
			paperUserList = examPaperUserService.getAllByPaperId(map);
			model.addAttribute("paperUserList",new PageInfo<ExamPaperUser>(paperUserList));
			model.addAttribute("id", id[0]);
			path = "ses/ems/exam/purchaser/paper/view_test_reference";
		}else if(new Date().getTime()<startTime.getTime()){
			model.addAttribute("examPaper", examPaper);
			paperUserList = examPaperUserService.getAllByPaperId(map);
			model.addAttribute("id", id[0]);
			model.addAttribute("paperUserList",new PageInfo<ExamPaperUser>(paperUserList));
			path = "ses/ems/exam/purchaser/paper/view_no_reference";
		}else if(new Date().getTime()>endTime.getTime()){
			paperUserList = examPaperUserService.selectPurchaserYesReference(map);
			for(int i=0;i<paperUserList.size();i++){
				if(paperUserList.get(i).getScore()==null||paperUserList.get(i).getScore()==""){
					paperUserList.get(i).setScore("0");
					ExamUserScore userScore = new ExamUserScore();
					userScore.setCreatedAt(new Date());
					userScore.setUserType(2);
					userScore.setUserId(paperUserList.get(i).getUserId());
					userScore.setScore("0");
					userScore.setPaperId(id[0]);
					userScore.setStatus("不及格");
					examUserScoreService.insertSelective(userScore);
					ExamPaperUser paperUser = new ExamPaperUser();
					paperUser.setId(paperUserList.get(i).getId());
					paperUser.setIsDo(2);
					examPaperUserService.updateByPrimaryKeySelective(paperUser);
				}
				
			}
			model.addAttribute("examPaper", examPaper);
			model.addAttribute("id", id[0]);
			model.addAttribute("paperUserList",new PageInfo<ExamPaperUser>(paperUserList));
			path = "ses/ems/exam/purchaser/paper/view_yes_reference";
		}
		return path;
	}
	
	/**
	 * 
	* @Title: addReferenceById
	* @author ZhaoBo
	* @date 2016-9-21 上午9:59:44  
	* @Description: 添加考卷的参考人员  
	* @param @param request
	* @param @param examPaperUser
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addReferenceById")
	@ResponseBody
	public String addReferenceById(HttpServletRequest request,ExamPaperUser examPaperUser){
		String userName = request.getParameter("userName");
		String card = request.getParameter("card");
		String paperId = request.getParameter("paperId");
		ExamPaper paperDo = examPaperService.selectByPrimaryKey(paperId);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("relName", userName);
		map.put("idCard", card);
		String str = null;
		List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
		if(purchaser.size()==0){
			HashMap<String,Object> relName = new HashMap<String,Object>();
			relName.put("relName", userName);
			List<PurchaseInfo> relNames = purchaseService.findPurchaseList(relName);
			if(relNames.size()==0){
				str = "0";
			}else{
				if(relNames.get(0).getIdCard().equals(card)){
					
				}else{
					str = "4";
				}
			}
		}else{
			ExamPaperUser userId = new ExamPaperUser();
			userId.setUserId(purchaser.get(0).getUserId());
			List<ExamPaperUser> userOfPaper = examPaperUserService.getAllPaperByUserId(userId);
			HashMap<String, Object> p_id = new HashMap<String,Object>();
			p_id.put("paperId", paperId);
			List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(p_id);
			if(paperUserList.size()==0){
				if(userOfPaper.size()==0){
					insertReference(purchaser.get(0).getUserId(), paperId, purchaser.get(0).getPurchaseDepName(),purchaser.get(0).getIdCard(),paperDo.getCode());
					str = "1";
				}else{
				for(int i=0;i<userOfPaper.size();i++){
					ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(i).getPaperId());
//					String[] expiryDate = paper.getExpiryDate().split(",");
//					Integer hour = Integer.parseInt(expiryDate[0]);
//					Integer second = Integer.parseInt(expiryDate[1]);
					Date startTime = paper.getStartTime();
				    Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(startTime); 
				    calendar.add(calendar.MINUTE,45+Integer.parseInt(paper.getTestTime()));
				    Date endTime = calendar.getTime();
				    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
//				    String[] newExpiryDate = selectedPaper.getExpiryDate().split(",");
//					Integer newHour = Integer.parseInt(newExpiryDate[0]);
//					Integer newSecond = Integer.parseInt(newExpiryDate[1]);
					Date newStartTime = selectedPaper.getStartTime();
				    Calendar newCalendar = new GregorianCalendar(); 
				    newCalendar.setTime(newStartTime); 
				    newCalendar.add(newCalendar.MINUTE,45+Integer.parseInt(selectedPaper.getTestTime()));
				    Date newEndTime = newCalendar.getTime();
				    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newEndTime.getTime())
				    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=endTime.getTime())){
				    	str = "3";
				    	break;
				    }else if(i==userOfPaper.size()-1){
				    	insertReference(purchaser.get(0).getUserId(), paperId, purchaser.get(0).getPurchaseDepName(),purchaser.get(0).getIdCard(),paperDo.getCode());
						str = "1";
				    }
				}
				}
			}else{
				for(int i=0;i<paperUserList.size();i++){
					if(paperUserList.get(i).getUserId().equals(purchaser.get(0).getUserId())){
						str = "2";
						break;
					}else if(i==paperUserList.size()-1){
						if(userOfPaper.size()==0){
							insertReference(purchaser.get(0).getUserId(), paperId, purchaser.get(0).getPurchaseDepName(),purchaser.get(0).getIdCard(),paperDo.getCode());
							str = "1";
						}else{
							for(int j=0;j<userOfPaper.size();j++){
								ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(i).getPaperId());
//								String[] expiryDate = paper.getExpiryDate().split(",");
//								Integer hour = Integer.parseInt(expiryDate[0]);
//								Integer second = Integer.parseInt(expiryDate[1]);
								Date startTime = paper.getStartTime();
							    Calendar calendar = new GregorianCalendar(); 
							    calendar.setTime(startTime); 
							    calendar.add(calendar.MINUTE,45+Integer.parseInt(paper.getTestTime()));
							    Date endTime = calendar.getTime();
							    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
//							    String[] newExpiryDate = selectedPaper.getExpiryDate().split(",");
//								Integer newHour = Integer.parseInt(newExpiryDate[0]);
//								Integer newSecond = Integer.parseInt(newExpiryDate[1]);
								Date newStartTime = selectedPaper.getStartTime();
							    Calendar newCalendar = new GregorianCalendar(); 
							    newCalendar.setTime(newStartTime); 
							    newCalendar.add(newCalendar.MINUTE,45+Integer.parseInt(selectedPaper.getTestTime()));
							    Date newEndTime = newCalendar.getTime();
							    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newEndTime.getTime())
							    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=endTime.getTime())){
							    	str = "3";
							    	break;
							    }else if(j==userOfPaper.size()-1){
							    	insertReference(purchaser.get(0).getUserId(), paperId, purchaser.get(0).getPurchaseDepName(),purchaser.get(0).getIdCard(),paperDo.getCode());
									str = "1";
							    }
							}
						}
					}
				}
			}
		}
		return str;
	}
	
	/**
	 * 
	* @Title: deleteByPaperUserId
	* @author ZhaoBo
	* @date 2016-9-21 下午3:13:33  
	* @Description: 删除考卷的参考人员 
	* @param @param request
	* @param @return      
	* @return Integer
	 */
	@RequestMapping("/deleteByPaperUserId")
	@ResponseBody
	public Integer deleteByPaperUserId(HttpServletRequest request){
		String[] ids = request.getParameter("ids").split(",");
		Integer id = null;
		for(int i = 0;i<ids.length;i++){
			id = examPaperUserService.deleteByPrimaryKey(ids[i]);
		}
		return id;
	}
	
	/**
	 * 
	* @Title: importReference
	* @author ZhaoBo
	* @date 2016-9-22 上午8:59:08  
	* @Description: 导入参考人员 
	* @param @param file
	* @param @param session
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException      
	* @return String
	 */
	@RequestMapping(value="/importReference",method = RequestMethod.POST)
	@ResponseBody
	public String importReference(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
        List<ExamPaperReference> examPaperReference = new ArrayList<ExamPaperReference>();
		File excelFile = poiExcel(session,file);
        String paperId = request.getParameter("paperId");
        ExamPaper paperDo = examPaperService.selectByPrimaryKey(paperId);
        String str = null;
		Workbook workbook = null;
		//判断Excel是2007以下还是2007以上版本
		try {
			workbook = new XSSFWorkbook(excelFile);
		}catch (Exception ex) {
			workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		outer:
		for (int j=1;j<=sheet.getPhysicalNumberOfRows();j++) {
			Row row = sheet.getRow(j);
			if (row==null) {
				continue;
			}
			Cell relName = row.getCell(0);
			Cell idCard = row.getCell(1);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("relName", relName.toString());
			if(idCard.toString().indexOf(".")>-1){
				map.put("idCard", idCard.toString().substring(0, idCard.toString().indexOf(".")));
			}else{
				map.put("idCard", idCard.toString());
			}
			List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
			if(purchaser.size()==0){
				str = "0";
				break outer;
			}else{
				ExamPaperUser userId = new ExamPaperUser();
				userId.setUserId(purchaser.get(0).getUserId());
				List<ExamPaperUser> userOfPaper = examPaperUserService.getAllPaperByUserId(userId);
				HashMap<String, Object> p_id = new HashMap<String,Object>();
				p_id.put("paperId", paperId);
				List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(p_id);
				if(paperUserList.size()==0){
					if(userOfPaper.size()==0){
						ExamPaperReference paperReference = new ExamPaperReference();
						paperReference.setUserId(purchaser.get(0).getUserId());
						paperReference.setPaperId(paperId);
						paperReference.setUnitName(purchaser.get(0).getPurchaseDepName());
						examPaperReference.add(paperReference);
					}else{
					for(int i=0;i<userOfPaper.size();i++){
						ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(i).getPaperId());
//						String[] expiryDate = paper.getExpiryDate().split(",");
//						Integer hour = Integer.parseInt(expiryDate[0]);
//						Integer second = Integer.parseInt(expiryDate[1]);
						Date startTime = paper.getStartTime();
					    Calendar calendar = new GregorianCalendar(); 
					    calendar.setTime(startTime); 
					    calendar.add(calendar.MINUTE,45+Integer.parseInt(paper.getTestTime()));
					    Date endTime = calendar.getTime();
					    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
//					    String[] newExpiryDate = selectedPaper.getExpiryDate().split(",");
//						Integer newHour = Integer.parseInt(newExpiryDate[0]);
//						Integer newSecond = Integer.parseInt(newExpiryDate[1]);
						Date newStartTime = selectedPaper.getStartTime();
					    Calendar newCalendar = new GregorianCalendar(); 
					    newCalendar.setTime(newStartTime); 
					    newCalendar.add(newCalendar.MINUTE,45+Integer.parseInt(selectedPaper.getTestTime()));
					    Date newEndTime = newCalendar.getTime();
					    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newEndTime.getTime())
					    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=endTime.getTime())){
					    	str = "3";
					    	break outer;
					    }else if(i==userOfPaper.size()-1){
					    	ExamPaperReference paperReference = new ExamPaperReference();
							paperReference.setUserId(purchaser.get(0).getUserId());
							paperReference.setPaperId(paperId);
							paperReference.setUnitName(purchaser.get(0).getPurchaseDepName());
							examPaperReference.add(paperReference);
					    }
					}
					}
				}else{
					for(int i=0;i<paperUserList.size();i++){
						if(paperUserList.get(i).getUserId().equals(purchaser.get(0).getUserId())){
							str = "2";
							break outer;
						}else if(i==paperUserList.size()-1){
							if(userOfPaper.size()==0){
								ExamPaperReference paperReference = new ExamPaperReference();
								paperReference.setUserId(purchaser.get(0).getUserId());
								paperReference.setPaperId(paperId);
								paperReference.setUnitName(purchaser.get(0).getPurchaseDepName());
								examPaperReference.add(paperReference);
							}else{
								for(int k=0;k<userOfPaper.size();k++){
									ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(i).getPaperId());
//									String[] expiryDate = paper.getExpiryDate().split(",");
//									Integer hour = Integer.parseInt(expiryDate[0]);
//									Integer second = Integer.parseInt(expiryDate[1]);
									Date startTime = paper.getStartTime();
								    Calendar calendar = new GregorianCalendar(); 
								    calendar.setTime(startTime); 
								    calendar.add(calendar.MINUTE,45+Integer.parseInt(paper.getTestTime()));
								    Date endTime = calendar.getTime();
								    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
//								    String[] newExpiryDate = selectedPaper.getExpiryDate().split(",");
//									Integer newHour = Integer.parseInt(newExpiryDate[0]);
//									Integer newSecond = Integer.parseInt(newExpiryDate[1]);
									Date newStartTime = selectedPaper.getStartTime();
								    Calendar newCalendar = new GregorianCalendar(); 
								    newCalendar.setTime(newStartTime); 
								    newCalendar.add(newCalendar.MINUTE,45+Integer.parseInt(selectedPaper.getTestTime()));
								    Date newEndTime = newCalendar.getTime();
								    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newEndTime.getTime())
								    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=endTime.getTime())){
								    	str = "3";
								    	break outer;
								    }else if(j==userOfPaper.size()-1){
								    	ExamPaperReference paperReference = new ExamPaperReference();
										paperReference.setUserId(purchaser.get(0).getUserId());
										paperReference.setPaperId(paperId);
										paperReference.setUnitName(purchaser.get(0).getPurchaseDepName());
										paperReference.setCard(purchaser.get(0).getIdCard());
										//paperReference.setCode(code)
										examPaperReference.add(paperReference);
								    }
								}
							}
						}
					}
				
			}
		}
		}
		if(str==null){
			for(int i=0;i<examPaperReference.size();i++){
				insertReference(examPaperReference.get(i).getUserId(), paperId, examPaperReference.get(i).getUnitName(),examPaperReference.get(i).getCard(),paperDo.getCode());
			}
			return "1";
		}else{
			return str;
		}
		
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
	
	/**
	 * 
	* @Title: insertReference
	* @author ZhaoBo
	* @date 2016-9-22 上午9:20:15  
	* @Description: 新增考卷的参考人员 
	* @param @param userId
	* @param @param paperId
	* @param @param unitName      
	* @return void
	 */
	public void insertReference(String userId,String paperId,String unitName,String card,String code){
		ExamPaperUser examPaperUser = new ExamPaperUser();
		examPaperUser.setCreatedAt(new Date());
		examPaperUser.setIsDo(0);
		examPaperUser.setUserId(userId);
		examPaperUser.setPaperId(paperId);
		examPaperUser.setUnitName(unitName);
		examPaperUser.setCard(card);
		examPaperUser.setCode(code);
		examPaperUserService.insertSelective(examPaperUser);
	}
	
	/**
	 * 
	* @Title: loadPurchaserTemplet
	* @author ZhaoBo
	* @date 2016-9-24 下午9:19:08  
	* @Description: 采购人题目模板下载 
	* @param @param request
	* @param @return
	* @param @throws IOException      
	* @return ResponseEntity<byte[]>
	 */
	@RequestMapping("/loadPurchaserTemplet")
	public ResponseEntity<byte[]> loadPurchaserTemplet(HttpServletRequest request) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		String path = PathUtil.getWebRoot() + "excel/采购人题库模板.xlsx";
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", new String("采购人题库模板.xlsx".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED));  
	}
	
	/**
	 * 
	* @Title: loadReferenceTemplet
	* @author ZhaoBo
	* @date 2016-9-24 下午9:19:08  
	* @Description: 人员模板下载 
	* @param @param request
	* @param @return
	* @param @throws IOException      
	* @return ResponseEntity<byte[]>
	 */
	@RequestMapping("/loadReferenceTemplet")
	public ResponseEntity<byte[]> loadReferenceTemplet(HttpServletRequest request) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		String path = PathUtil.getWebRoot() + "excel/参考人员模板.xls";
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", new String("参考人员模板.xls".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED));  
	}
}
