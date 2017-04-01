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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
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
import ses.util.ValidateUtils;


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
	public String addPurQue(Model model){
		optionNum(model);
		return "ses/ems/exam/purchaser/question/add";
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
	* @Title: saveToPurPool
	* @author ZhaoBo
	* @date 2016-9-7 上午11:28:10  
	* @Description: 采购人新增题目方法 
	* @param @param model
	* @param @param request
	* @param @param examQuestion
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveToPurPool")
	public String saveToPurPool(Model model,HttpServletRequest request,ExamQuestion examQuestion){
		Map<String,Object> map = new HashMap<String,Object>();
		String[] items = saveOption();
		String queType = request.getParameter("queType");
		StringBuffer sb_answer = new StringBuffer();
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/purchaser/question/add";
		}
		String error = "无";
		String topic = request.getParameter("topic");
		if(topic==null||topic.trim().equals("")){
			error = "topic";
			model.addAttribute("ERR_topic","题干不能为空");
		}else{
			HashMap<String,Object> tmap = new HashMap<String,Object>();
			tmap.put("questionTypeId", Integer.parseInt(queType));
			tmap.put("topic", topic.trim());
			tmap.put("personType", 2);
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
		if(queType.equals("1")||queType.equals("2")){
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
						sb_opt.add(option[i]);
					}
				}
				map.put("option",sb_opt);
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
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能输入分号");
								break outer;
							}
						}
						StringBuffer sb_option = new StringBuffer();
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
						sb_answer.append(answer[i]);
					}
					map.put("answer",sb_answer.toString());
				}
			}
		}else{
			examQuestion.setItems(" ");
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i = 0;i<answer.length;i++){
					sb_answer.append(answer[i]);
				}
				map.put("answer",sb_answer.toString());
			}
		}
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			optionNum(model);
			map.put("type",queType);
			map.put("topic",topic);
			map.put("options",request.getParameter("options"));
			model.addAttribute("errData", map);
			return "ses/ems/exam/purchaser/question/add";
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic.trim());
		examQuestion.setPersonType(2);
		examQuestion.setCreatedAt(new Date());
		examQuestion.setUpdatedAt(new Date());
		examQuestion.setAnswer(sb_answer.toString());
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
		purchaserQuestion(request.getParameter("id"), model);
		return "ses/ems/exam/purchaser/question/edit";
	}
	
	/**
	 * 
	* @Title: purchaserQuestion
	* @author ZhaoBo
	* @date 2016-10-17 下午4:02:21  
	* @Description: 拿到要修改的采购人题目 
	* @param @param id
	* @param @param model      
	* @return void
	 */
	public void purchaserQuestion(String id,Model model){
		ExamQuestion examQuestion = examQuestionService.selectByPrimaryKey(id);
		model.addAttribute("purchaserQue",examQuestion);
		String queAnswer = examQuestion.getAnswer();
		model.addAttribute("purchaserAnswer",queAnswer);
		List<ExamQuestionType> examPoolType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examPoolType);
		if(!examQuestion.getItems().equals(" ")){
			String[] option = examQuestion.getItems().split(";");
			List<String> sb_opt = new ArrayList<String>();
			for(int i=0;i<option.length;i++){
				sb_opt.add(option[i].substring(2));
			}
			model.addAttribute("optContent", sb_opt);
			model.addAttribute("optNum", option.length);
		}
		optionNum(model);
	}
	
	/**
	 * 
	* @Title: editToPurchaser
	* @author ZhaoBo
	* @date 2016-9-7 上午11:29:36  
	* @Description: 修改采购人题库并保存 
	* @param @param request
	* @param @param examPool
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editToPurchaser")
	public String editToPurchaser(HttpServletRequest request,ExamQuestion examQuestion,Model model){
		List<ExamQuestionType> examQuestionType = examQuestionTypeService.selectPurchaserAll();
		model.addAttribute("examPoolType",examQuestionType);
		examQuestion.setId(request.getParameter("id"));
		String[] items = saveOption();
		String content = request.getParameter("content");
		String queType = request.getParameter("queType");
		StringBuffer sb_answer = new StringBuffer();
		if(queType==null||queType.equals("")){
			model.addAttribute("ERR_type","请选择题型");
			optionNum(model);
			return "ses/ems/exam/purchaser/question/edit";
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
				tmap.put("topic", request.getParameter("topic").trim());
				tmap.put("personType",2);
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
		if(queType.equals("1")||queType.equals("2")){
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
						sb_opt.add(option[i]);
					}
				}
				model.addAttribute("optContent", sb_opt);
				outer:for(int i=0;i<option.length;i++){
					if(option[i].isEmpty()){
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
								error = "option";
								model.addAttribute("ERR_option", "选项内容不能输入分号");
								break outer;
							}
						}
						StringBuffer sb_option = new StringBuffer();
						for(int j=0;j<opt.length;j++){
							sb_option.append(opt[j]+"."+option[j]+";");
						}
						examQuestion.setItems(sb_option.toString());
					}
				}
				String[] answer = request.getParameterValues("answer");
				if(answer==null){
					error = "answer";
					model.addAttribute("ERR_answer", "请选择答案");
				}else{
					for(int i = 0;i<answer.length;i++){
						sb_answer.append(answer[i]);
					}
				}
			}
		}else{
			examQuestion.setItems(" ");
			String[] answer = request.getParameterValues("answer");
			if(answer==null){
				model.addAttribute("ERR_answer", "请选择答案");
				error = "answer";
			}else{
				for(int i = 0;i<answer.length;i++){
					sb_answer.append(answer[i]);
				}
			}
		}
		examQuestion.setQuestionTypeId(Integer.parseInt(queType));
		examQuestion.setTopic(topic);
		examQuestion.setAnswer(sb_answer.toString());
		examQuestion.setUpdatedAt(new Date());
		if(error.equals("topic")||error.equals("option")||error.equals("answer")){
			model.addAttribute("purchaserQue",examQuestion);
			model.addAttribute("purchaserAnswer",sb_answer.toString());
			optionNum(model);
			return "ses/ems/exam/purchaser/question/edit";
		}
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
	public void importExcel(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
		File excelFile = poiExcel(session,file);
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
		for (int j=1;j<=sheet.getPhysicalNumberOfRows();j++) {
			Row row = sheet.getRow(j);
			if (row==null) {
				continue;
			}
			Cell queType = row.getCell(0);
			if (queType.toString().equals("单选题")
					|| queType.toString().equals("多选题")) {
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(2);
				ExamQuestion examQuestion = new ExamQuestion();
				examQuestion.setPersonType(2);
				StringBuffer sb_items = new StringBuffer();
				String item = items[row.getPhysicalNumberOfCells()-3];
				String[] opt = item.split(",");
				for(int i=3;i<row.getPhysicalNumberOfCells();i++){
					if(row.getCell(j).toString().indexOf(".")>-1){
						sb_items.append(opt[i-3]+"."+row.getCell(i).toString().substring(0, row.getCell(i).toString().indexOf("."))+";");
					}else{
						sb_items.append(opt[i-3]+"."+row.getCell(i).toString()+";");
					}
				}
				examQuestion.setItems(sb_items.toString());
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setCreatedAt(new Date());
				HashMap<String,Object> map = new HashMap<String,Object>();
				if(queType.toString().equals("单选题")) {
					map.put("questionTypeId", 1);
					examQuestion.setQuestionTypeId(1);
				}else{
					map.put("questionTypeId", 2);
					examQuestion.setQuestionTypeId(2);
				}
				map.put("topic", queTopic.toString().trim());
				map.put("personType", 2);
				List<ExamQuestion> sameTopic = examQuestionService.selectByTopic(map);
				if(sameTopic.size()!=0){
					for(int i=0;i<sameTopic.size();i++){
						if(queTopic.toString().trim().equals(sameTopic.get(i).getTopic().trim())){
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
			if(queType.toString().equals("判断题")){
				ExamQuestion examQuestion = new ExamQuestion();
				Cell queTopic = row.getCell(1);
				Cell queAnswer = row.getCell(2);
				examQuestion.setPersonType(2);
				examQuestion.setItems(" ");
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("questionTypeId", 3);
				map.put("topic", queTopic.toString().trim());
				map.put("personType", 2);
				List<ExamQuestion> sameTopic = examQuestionService.selectByTopic(map);
				if(sameTopic.size()!=0){
					for(int i=0;i<sameTopic.size();i++){
						if(queTopic.toString().trim().equals(sameTopic.get(i).getTopic().trim())){
							str="1";
							same.append(queType.toString()+","+queTopic.toString()+";");
							break;
						}
					}
				}else{
					examQuestion.setTopic(queTopic.toString().trim());
					question.add(examQuestion);
				}
				examQuestion.setAnswer(queAnswer.toString());
				examQuestion.setQuestionTypeId(3);
				examQuestion.setCreatedAt(new Date());
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
		HashMap<String,Object> code = new HashMap<String,Object>();
		code.put("code", paperNo);
		List<ExamPaper> paperList = examPaperService.selectByPaperNo(code);
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperList.get(0).getId());
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleN =  (String) obj.get("singleNum");
		Integer singleNum = Integer.parseInt(singleN);
		String multipleN = (String) obj.get("multipleNum");
		Integer multipleNum = Integer.parseInt(multipleN);
		String judgeN = (String) obj.get("judgeNum");
		Integer judgeNum = Integer.parseInt(judgeN);
		String singleP =  (String) obj.get("singlePoint");
		BigDecimal singlePoint = new BigDecimal(singleP);
		String multipleP = (String) obj.get("multiplePoint");
		BigDecimal multiplePoint = new BigDecimal(multipleP);
		String judgeP = (String) obj.get("judgePoint");
		BigDecimal judgePoint = new BigDecimal(judgeP);
		HashMap<String,Object> smap = new HashMap<String,Object>();
		smap.put("questionTypeId", 1);
		smap.put("queNum", singleNum);
		List<ExamQuestion> singleQue = examQuestionService.selectPurchaserQuestionRandom(smap);
		HashMap<String,Object> mmap = new HashMap<String,Object>();
		mmap.put("questionTypeId", 2);
		mmap.put("queNum", multipleNum);
		List<ExamQuestion> multipleQue = examQuestionService.selectPurchaserQuestionRandom(mmap);
		HashMap<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("questionTypeId", 3);
		jmap.put("queNum", judgeNum);
		List<ExamQuestion> judgeQue = examQuestionService.selectPurchaserQuestionRandom(jmap);
		List<ExamQuestion> purchaserQue = new ArrayList<ExamQuestion>();
		purchaserQue.addAll(singleQue);
		purchaserQue.addAll(multipleQue);
		purchaserQue.addAll(judgeQue);
		StringBuffer sb_answers = new StringBuffer();
		StringBuffer sb_queTypes = new StringBuffer();
		StringBuffer sb_questionIds =  new StringBuffer();
		for(int i=0;i<purchaserQue.size();i++){
			sb_answers.append(purchaserQue.get(i).getAnswer()+",");
			sb_queTypes.append(purchaserQue.get(i).getExamQuestionType().getName()+",");
			sb_questionIds.append(purchaserQue.get(i).getId()+",");
		}
		model.addAttribute("questionType",sb_queTypes);
		model.addAttribute("questionAnswer", sb_answers);
		model.addAttribute("questionId", sb_questionIds);
		model.addAttribute("examPaper", examPaper);
		model.addAttribute("queCount", singleQue.size()+multipleQue.size()+judgeQue.size());
		model.addAttribute("singlePoint", singlePoint);
		model.addAttribute("multiplePoint", multiplePoint);
		model.addAttribute("judgePoint", judgePoint);
		model.addAttribute("singleNum", singleNum);
		model.addAttribute("multipleNum", multipleNum);
		model.addAttribute("judgeNum", judgeNum);
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
		User user = (User) request.getSession().getAttribute("loginUser");
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
		HashMap<String,Object> smap = new HashMap<String,Object>();
		smap.put("questionTypeId", 1);
		smap.put("queNum", singleNum);
		List<ExamQuestion> singleQue = examQuestionService.selectPurchaserQuestionRandom(smap);
		HashMap<String,Object> mmap = new HashMap<String,Object>();
		mmap.put("questionTypeId", 2);
		mmap.put("queNum", multipleNum);
		List<ExamQuestion> multipleQue = examQuestionService.selectPurchaserQuestionRandom(mmap);
		HashMap<String,Object> jmap = new HashMap<String,Object>();
		jmap.put("questionTypeId", 3);
		jmap.put("queNum", judgeNum);
		List<ExamQuestion> judgeQue = examQuestionService.selectPurchaserQuestionRandom(jmap);
		List<ExamQuestion> question = new ArrayList<ExamQuestion>();
		question.addAll(singleQue);
		question.addAll(multipleQue);
		question.addAll(judgeQue);
		List<Integer> pageNum = new ArrayList<Integer>();
		if(question.size()%5==0){
			for(int i=0;i<question.size()/5;i++){
				pageNum.add(i);
			}
		}else{
			for(int i=0;i<question.size()/5+1;i++){
				pageNum.add(i);
			}
		}
		Date offTime = examPaper.getOffTime();
		if((offTime.getTime()-new Date().getTime())/1000/60<Integer.parseInt(examPaper.getTestTime())){
			model.addAttribute("second", (offTime.getTime()-new Date().getTime())/1000/60);
			model.addAttribute("minute", (offTime.getTime()-new Date().getTime())/1000%60);
		}
		model.addAttribute("user", user);
		model.addAttribute("questionType",request.getParameter("questionType"));
		model.addAttribute("questionAnswer", request.getParameter("questionAnswer"));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("question",question);
		model.addAttribute("questionId", request.getParameter("questionId"));
		model.addAttribute("pageSize", pageNum.size());
		model.addAttribute("examPaper", examPaper);
		model.addAttribute("queCount", request.getParameter("queCount"));
		model.addAttribute("singlePoint", request.getParameter("singlePoint"));
		model.addAttribute("multiplePoint", request.getParameter("multiplePoint"));
		model.addAttribute("judgePoint", request.getParameter("judgePoint"));
		model.addAttribute("singleNum", request.getParameter("singleNum"));
		model.addAttribute("multipleNum", request.getParameter("multipleNum"));
		model.addAttribute("judgeNum", request.getParameter("judgeNum"));
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
			paperList.get(i).setOffTrueDate(sdf.format(paperList.get(i).getOffTime()));
			Date startTime = paperList.get(i).getStartTime();
		    Date offTime = paperList.get(i).getOffTime();
		    if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=offTime.getTime()){
		    	paperList.get(i).setStatus("正在考试中");
			}else if(new Date().getTime()<startTime.getTime()){
				paperList.get(i).setStatus("未开始");
			}else if(new Date().getTime()>offTime.getTime()){
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
	public String addPaper(){
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
		Map<String,Object> errorData = new HashMap<String,Object>();
		String name = request.getParameter("name");
		String error = "无";
		if(name.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_name", "试卷名称不能为空");
		}else{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("name",name);
			List<ExamPaper> paper = examPaperService.selectByPaperNo(map);
			if(paper.size()!=0){
				error="error";
				model.addAttribute("ERR_name", "试卷名称已存在");
			}else{
				//int len = ValidateUtils.length(name);
				if(name.length()>10||name.length()<4){
					error="error";
					model.addAttribute("ERR_name", "试卷名称由4-10个字符组成");
				}
			}
		}
		String code = request.getParameter("code");
		if(code.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_code", "试卷编号不能为空");
		}else{
			if(!ValidateUtils.Number_code(code)){
				error="error";
				model.addAttribute("ERR_code", "试卷编号只能是由数字、英文和下划线组成");
			}else if(code.trim().length()<6||code.trim().length()>20){
				error="error";
				model.addAttribute("ERR_code", "试卷编号由6-20个字符组成");
			}else{
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("code",code);
				List<ExamPaper> paper = examPaperService.selectByPaperNo(map);
				if(paper.size()!=0){
					error="error";
					model.addAttribute("ERR_code", "试卷编号已存在");
				}
			}
		}
		String testTime = request.getParameter("testTime");
		if(testTime.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_testTime", "答题时间不能为空");
		}else{
			if(!ValidateUtils.Z_index(testTime)){
				error="error";
				model.addAttribute("ERR_testTime", "答题用时必须为正整数");
			}else{
				examPaper.setTestTime(testTime);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = request.getParameter("startTime");
		Date sTime = null;
		if(startTime.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_startTime","考试开始时间不能为空");
		}else{
			String examStartTime = startTime+":00";
			sTime = sdf.parse(examStartTime);
			if(sTime.getTime()<=new Date().getTime()){
				error="error";
				model.addAttribute("ERR_startTime","考试开始时间必须大于当前时间");
			}else{
				examPaper.setStartTime(sTime);
			}
		}
		String offTime = request.getParameter("offTime");
		if(offTime.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_offTime","考试截止时间不能为空");
		}else{
			String examOffTime = offTime+":00";
			Date oTime = sdf.parse(examOffTime);
			if(oTime.getTime()<=sTime.getTime()){
				error = "error";
				model.addAttribute("ERR_offTime","考试截止时间必须大于考试开始时间");
			}else{
				examPaper.setOffTime(oTime);
			}
		}
		String passStandard = request.getParameter("passStandard");
		String paperScore = request.getParameter("paperScore");
		if(passStandard.trim().isEmpty()){
			error = "error";
			model.addAttribute("ERR_passStandard","及格标准不能为空");
		}else{
			if(!ValidateUtils.PLUS_NUMBER(passStandard)){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分必须为大于0的正数");
			}else if(new BigDecimal(paperScore).compareTo(new BigDecimal(passStandard))<=0){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分要小于试卷分值");
			}
		}
		String[] single = request.getParameterValues("single");
		String[] multiple = request.getParameterValues("multiple");
		String[] judge = request.getParameterValues("judge");
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
		Map<String,String> map = new HashMap<String,String>();
		if(single!=null&&multiple!=null&&judge!=null){
			errorData.put("singleNum", singleNum);
			errorData.put("singlePoint", singlePoint);
			errorData.put("single", single[0]);
			errorData.put("multipleNum", multipleNum);
			errorData.put("multiplePoint", multiplePoint);
			errorData.put("multiple", multiple[0]);
			errorData.put("judgeNum", judgeNum);
			errorData.put("judgePoint", judgePoint);
			errorData.put("judge", judge[0]);
			if(single[0].equals("无")&&multiple[0].equals("无")&&judge[0].equals("无")){
				error = "error";
				model.addAttribute("ERR_judge", "请至少选择一种题型");
			}
		}
		if(single==null){
			error = "type";
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
						HashMap<String,Object> purSingle = new HashMap<String,Object>();
						purSingle.put("questionTypeId", 1);
						int purchaserSingle = examQuestionService.queryPurchaserQuestionCount(purSingle);
						if(purchaserSingle<Integer.parseInt(singleNum)){
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
			error = "type";
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
						HashMap<String,Object> purMultiple = new HashMap<String,Object>();
						purMultiple.put("questionTypeId", 2);
						int purchaserMultiple = examQuestionService.queryPurchaserQuestionCount(purMultiple);
						if(purchaserMultiple<Integer.parseInt(multipleNum)){
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
		if(judge==null){
			error = "error";
			model.addAttribute("ERR_judge", "请选择");
		}else{
			errorData.put("judgeNum", judgeNum);
			errorData.put("judgePoint", judgePoint);
			errorData.put("judge", judge[0]);
			if(judge[0].equals("有")){
				if(judgeNum.trim().isEmpty()||judgePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_judge", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(judgeNum)){
						error = "error";
						model.addAttribute("ERR_judge", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(judgePoint)){
						error = "error";
						model.addAttribute("ERR_judge", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> purJudge = new HashMap<String,Object>();
						purJudge.put("questionTypeId",3);
						int purchaserJudge = examQuestionService.queryPurchaserQuestionCount(purJudge);
						if(purchaserJudge<Integer.parseInt(judgeNum)){
							error = "error";
							model.addAttribute("ERR_judge", "题库中判断题数量不足");
						}else{
							map.put("judgeNum", judgeNum);
							map.put("judgePoint", judgePoint);
						}
					}
				}
			}else{
				map.put("judgeNum", "0");
				map.put("judgePoint", "0");
			}
		}
		if(error.equals("error")){
			errorData.put("name", name);
			errorData.put("code", code);
			errorData.put("passStandard", passStandard);
			errorData.put("score", paperScore);
			errorData.put("startTime", startTime);
			errorData.put("offTime", offTime);
			errorData.put("testTime", testTime);
			model.addAttribute("errorData", errorData);
			return "ses/ems/exam/purchaser/paper/add";
		}
		examPaper.setCreatedAt(new Date());
		examPaper.setUpdatedAt(new Date());
		examPaper.setName(name);
		examPaper.setCode(code);
		examPaper.setScore(paperScore);
		examPaper.setPassStandard(passStandard);
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaper.setYear(startTime.substring(0, 4));
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
		Date startTime = examPaper.getStartTime();
	    Date offTime = examPaper.getOffTime();
		if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=offTime.getTime()){
			str = "1";
		}else if(new Date().getTime()<startTime.getTime()){
			str = "2";
		}else if(new Date().getTime()>offTime.getTime()){
			str = "3";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: editPaper
	* @author ZhaoBo
	* @date 2016-9-6 上午10:55:31  
	* @Description: 修改考卷页面
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPaper")
	public String editPaper(Model model,HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id[0]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = sdf.format(examPaper.getStartTime());
		String examStartTime = startTime.substring(0,16);
		model.addAttribute("startTime", examStartTime);
		String offTime = sdf.format(examPaper.getOffTime());
		String examOffTime = offTime.substring(0,16);
		model.addAttribute("offTime", examOffTime);
		model.addAttribute("examPaper", examPaper);
		String typeDistribution = examPaper.getTypeDistribution();
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
		model.addAttribute("judgeNum", object.get("judgeNum"));
		if(Integer.parseInt(object.get("judgeNum").toString())>0){
			model.addAttribute("errorJudge", "有");
		}else{
			model.addAttribute("errorJudge", "无");
		}
		model.addAttribute("judgePoint", object.get("judgePoint"));
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
		String paperName = request.getParameter("paperName");
		String paperCode = request.getParameter("paperCode");
		String name = request.getParameter("name");
		String error = "无";
		if(name.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_name", "试卷名称不能为空");
		}else{
			if(!paperName.equals(name)){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("name",name);
				List<ExamPaper> paper = examPaperService.selectByPaperNo(map);
				if(paper.size()!=0){
					error="error";
					model.addAttribute("ERR_name", "试卷名称已存在");
				}else{
					//int len = ValidateUtils.length(name);
					if(name.length()>10||name.length()<4){
						error="error";
						model.addAttribute("ERR_name", "试卷名称由4-10个字符组成");
					}
				}
			}
		}
		String code = request.getParameter("code");
		if(code.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_code", "试卷编号不能为空");
		}else{
			if(!ValidateUtils.Number_code(code)){
				error="error";
				model.addAttribute("ERR_code", "试卷编号只能是由数字、英文和下划线组成");
			}else if(code.trim().length()<6||code.trim().length()>20){
				error="error";
				model.addAttribute("ERR_code", "试卷编号由6-20个字符组成");
			}else{
				if(!paperCode.equals(code)){
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("code",code);
					List<ExamPaper> paper = examPaperService.selectByPaperNo(map);
					if(paper.size()!=0){
						error="error";
						model.addAttribute("ERR_code", "试卷编号已存在");
					}
				}
			}
		}
		examPaper.setName(name);
		examPaper.setCode(code);
		String testTime = request.getParameter("testTime");
		if(testTime.isEmpty()){
			error="error";
			model.addAttribute("ERR_testTime", "答题用时不能为空");
		}else{
			if(!ValidateUtils.Z_index(testTime)){
				error="error";
				model.addAttribute("ERR_testTime", "答题用时必须为正整数");
			}
		}
		examPaper.setTestTime(testTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = request.getParameter("startTime");
		Date sTime = null;
		if(startTime.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_startTime","考试开始时间不能为空");
		}else{
			String examStartTime = startTime+":00";
			sTime = sdf.parse(examStartTime);
			if(sTime.getTime()<=new Date().getTime()){
				error="error";
				model.addAttribute("ERR_startTime","考试开始时间必须大于当前时间");
			}else{
				examPaper.setStartTime(sTime);
			}
		}
		String offTime = request.getParameter("offTime");
		if(offTime.trim().isEmpty()){
			error="error";
			model.addAttribute("ERR_offTime","考试截止时间不能为空");
		}else{
			String examOffTime = offTime+":00";
			Date oTime = sdf.parse(examOffTime);
			if(oTime.getTime()<=sTime.getTime()){
				error = "error";
				model.addAttribute("ERR_offTime","考试截止时间必须大于考试开始时间");
			}else{
				examPaper.setOffTime(oTime);
			}
		}
		String passStandard = request.getParameter("passStandard");
		String paperScore = request.getParameter("paperScore");
		examPaper.setScore(paperScore);
		examPaper.setPassStandard(passStandard);
		if(passStandard.trim().isEmpty()){
			error = "error";
			model.addAttribute("ERR_passStandard","及格标准不能为空");
		}else{
			if(!ValidateUtils.PLUS_NUMBER(passStandard)){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分必须为大于0的正数");
			}else if(new BigDecimal(paperScore).compareTo(new BigDecimal(passStandard))<=0){
				error = "error";
				model.addAttribute("ERR_passStandard", "及格标准分要小于试卷分值");
			}
		}
		String[] single = request.getParameterValues("single");
		String[] multiple = request.getParameterValues("multiple");
		String[] judge = request.getParameterValues("judge");
		String singleNum = request.getParameter("singleNum");
		String singlePoint = request.getParameter("singlePoint");
		String multipleNum = request.getParameter("multipleNum");
		String multiplePoint = request.getParameter("multiplePoint");
		String judgeNum = request.getParameter("judgeNum");
		String judgePoint = request.getParameter("judgePoint");
		Map<String,String> map = new HashMap<String,String>();
		if(single!=null&&multiple!=null&&judge!=null){
			model.addAttribute("singleNum", singleNum);
			model.addAttribute("singlePoint", singlePoint);
			model.addAttribute("errorSingle", single[0]);
			model.addAttribute("multipleNum", multipleNum);
			model.addAttribute("multiplePoint", multiplePoint);
			model.addAttribute("errorMultiple", multiple[0]);
			model.addAttribute("judgeNum", judgeNum);
			model.addAttribute("judgePoint", judgePoint);
			model.addAttribute("errorJudge", judge[0]);
			if(single[0].equals("无")&&multiple[0].equals("无")&&judge[0].equals("无")){
				error = "error";
				model.addAttribute("ERR_judge", "请至少选择一种题型");
			}
		}
		if(single==null){
			error = "type";
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
						HashMap<String,Object> purSingle = new HashMap<String,Object>();
						purSingle.put("questionTypeId", 1);
						int purchaserSingle = examQuestionService.queryPurchaserQuestionCount(purSingle);
						if(purchaserSingle<Integer.parseInt(singleNum)){
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
			error = "type";
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
						HashMap<String,Object> purMultiple = new HashMap<String,Object>();
						purMultiple.put("questionTypeId", 2);
						int purchaserMultiple = examQuestionService.queryPurchaserQuestionCount(purMultiple);
						if(purchaserMultiple<Integer.parseInt(multipleNum)){
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
		if(judge==null){
			error = "error";
			model.addAttribute("ERR_judge", "请选择");
		}else{
			model.addAttribute("judgeNum", judgeNum);
			model.addAttribute("judgePoint", judgePoint);
			model.addAttribute("errorJudge", judge[0]);
			if(judge[0].equals("有")){
				if(judgeNum.trim().isEmpty()||judgePoint.trim().isEmpty()){
					error = "error";
					model.addAttribute("ERR_judge", "请补充完整");
				}else{
					if(!ValidateUtils.Z_index(judgeNum)){
						error = "error";
						model.addAttribute("ERR_judge", "题目数量必须为正整数");
					}else if(!ValidateUtils.PLUS_NUMBER(judgePoint)){
						error = "error";
						model.addAttribute("ERR_judge", "分值必须为大于0的正数");
					}else{
						HashMap<String,Object> purJudge = new HashMap<String,Object>();
						purJudge.put("questionTypeId",3);
						int purchaserJudge = examQuestionService.queryPurchaserQuestionCount(purJudge);
						if(purchaserJudge<Integer.parseInt(judgeNum)){
							error = "error";
							model.addAttribute("ERR_judge", "题库中判断题数量不足");
						}else{
							map.put("judgeNum", judgeNum);
							map.put("judgePoint", judgePoint);
						}
					}
				}
			}else{
				map.put("judgeNum", "0");
				map.put("judgePoint", "0");
			}
		}
		if(error.equals("error")){
			model.addAttribute("examPaper", examPaper);
			model.addAttribute("startTime", startTime);
			model.addAttribute("offTime", offTime);
			model.addAttribute("singleNum", singleNum);
			model.addAttribute("singlePoint", singlePoint);
			model.addAttribute("multipleNum", multipleNum);
			model.addAttribute("multiplePoint", multiplePoint);
			model.addAttribute("judgeNum", judgeNum);
			model.addAttribute("judgePoint", judgePoint);
			return "ses/ems/exam/purchaser/paper/edit";
		}
		examPaper.setUpdatedAt(new Date());
		examPaper.setTypeDistribution(JSONSerializer.toJSON(map).toString());
		examPaper.setYear(startTime.substring(0, 4));
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
		User user = (User) request.getSession().getAttribute("loginUser");
		String str = null;
		String paperNo = request.getParameter("paperNo");
		HashMap<String,Object> code = new HashMap<String,Object>();
		code.put("code", paperNo);
		List<ExamPaper> paper = examPaperService.selectByPaperNo(code);
		if(paper.size()==0){
			str = "0";//没有该考卷
		}else{
			ExamPaper examPaper = paper.get(0);
			Date startTime = examPaper.getStartTime();
			Date offTime = examPaper.getOffTime();
			if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=offTime.getTime()){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("paperId", examPaper.getId());
				List<ExamPaperUser> examPaperUsers = examPaperUserService.getAllByPaperId(map);
				if(examPaperUsers.size()==0){
					str = "6";//该考卷没有设置参考人员
				}else{
					HashMap<String,Object> next = new HashMap<String,Object>();
					next.put("userId", user.getId());
					next.put("paperId", examPaper.getId());
					List<ExamPaperUser> userDo = examPaperUserService.findIsExamByCondition(next);
					if(userDo.get(0).getIsDo()!=0){
						str = "7";//判断用户有没有登录过
					}else{
						for(int i=0;i<examPaperUsers.size();i++){
							if(user.getId().equals(examPaperUsers.get(i).getUserId())){
								str = "1";//可以开始考试
								break;
							}else if(i==examPaperUsers.size()-1){
								str = "5";//不是该考卷的参考人员
							}
						}
					}
				}
			}else if(new Date().getTime()<startTime.getTime()){
				str = "2";//输入的考卷考试时间未开始
			}else if(new Date().getTime()>offTime.getTime()){
				str = "3";//考试时间已截止
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
		User user = (User) request.getSession().getAttribute("loginUser");
		String[] questionAnswer = request.getParameter("questionAnswer").split(",");
		String[] questionType = request.getParameter("questionType").split(",");
		String[] questionId = request.getParameter("questionId").split(",");
		String paperId = request.getParameter("paperId");
		ExamPaper paper = examPaperService.selectByPrimaryKey(paperId);
		String typeDistribution = paper.getTypeDistribution();
		JSONObject obj = JSONObject.fromObject(typeDistribution);
		String singleP = (String) obj.get("singlePoint");
		BigDecimal singlePoint = new BigDecimal(singleP);
		String multipleP = (String) obj.get("multiplePoint");
		BigDecimal multiplePoint = new BigDecimal(multipleP);
		String judgeP = (String) obj.get("judgePoint");
		BigDecimal judgePoint = new BigDecimal(judgeP);
		BigDecimal score = new BigDecimal(0);
		String passStandard = paper.getPassStandard();
		for(int i=0;i<questionAnswer.length;i++){
			StringBuffer sb = new StringBuffer();
			if(request.getParameterValues("que"+(i+1))==null){
				ExamUserAnswer examUserAnswer = new ExamUserAnswer();
				examUserAnswer.setContent(" ");
				examUserAnswer.setCreatedAt(new Date());
				examUserAnswer.setUpdatedAt(new Date());
				examUserAnswer.setQuestionId(questionId[i]);
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
				examUserAnswer.setUpdatedAt(new Date());
				examUserAnswer.setQuestionId(questionId[i]);
				examUserAnswer.setUserType(2);
				examUserAnswer.setPaperId(paperId);
				examUserAnswerService.insertSelective(examUserAnswer);
				if(questionAnswer[i].equals(sb.toString())){
					if(questionType[i].equals("单选题")){
						score = score.add(singlePoint); 
					}else if(questionType[i].equals("多选题")){
						score = score.add(multiplePoint);
					}else if(questionType[i].equals("判断题")){
						score = score.add(judgePoint);
					}
				}
			}
		}
		ExamUserScore examUserScore = new ExamUserScore();
		examUserScore.setCreatedAt(new Date());
		examUserScore.setUpdatedAt(new Date());
		examUserScore.setTestDate(new Date());
		examUserScore.setIsMax(1);
		examUserScore.setUserId(user.getId());
		examUserScore.setUserType(2);
		examUserScore.setScore(String.valueOf(score));
		examUserScore.setPaperId(paperId);
		if(score.compareTo(new BigDecimal(passStandard))<0){
			examUserScore.setStatus("不及格");
		}else{
			examUserScore.setStatus("及格");
		}
		examUserScoreService.insertSelective(examUserScore);
		ExamPaperUser examPaperUser = new ExamPaperUser();
		examPaperUser.setUserId(user.getId());
		examPaperUser.setPaperId(paperId);
		examPaperUser.setIsDo(1);
		examPaperUserService.updateByPaperIdAndUserID(examPaperUser);
		model.addAttribute("examPaper", paper);
		model.addAttribute("score", score);
		model.addAttribute("paperId", paperId);
		model.addAttribute("pass", paper.getPassStandard());
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
			//试卷id
			String paperId = paperUser.getPaperId();
			ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		    if(examPaper !=null){
				Date offTime = examPaper.getOffTime();
			    if(new Date().getTime()>offTime.getTime()){
			    	if(paperUser.getIsDo()==0){
			    		ExamUserScore examUserScore = new ExamUserScore();
			    		examUserScore.setCreatedAt(new Date());
			    		examUserScore.setUpdatedAt(new Date());
						examUserScore.setUserType(2);
						examUserScore.setUserId(paperUser.getUserId());
						examUserScore.setScore("0");
						examUserScore.setIsMax(1);
						examUserScore.setPaperId(paperUser.getPaperId());
						examUserScore.setStatus("不及格");
						examUserScoreService.insertSelective(examUserScore);
						ExamPaperUser paperOfUser = new ExamPaperUser();
						paperOfUser.setId(paperUser.getId());
						paperOfUser.setIsDo(2);
						paperOfUser.setUpdatedAt(new Date());
						examPaperUserService.updateByPrimaryKeySelective(paperOfUser);
			    	}
			    }
		    }
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		String relName = request.getParameter("relName");
		String status = request.getParameter("status");
		String code = request.getParameter("code");
		if(relName!=null&&!relName.equals("")){
			map.put("relName", "%"+relName+"%");
		}
		if(code!=null&&!code.equals("")){
			map.put("code", "%"+code+"%");
		}
		if(status!=null&&!status.equals("")){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString()); 
		//获取配置文件的信息 分页的
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
		String id = request.getParameter("paperId");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("paperId", id);
		List<ExamUserScore> paperUserList = examUserScoreService.findPurchaserScore(map);
		model.addAttribute("paperUserList", paperUserList);
		return "ses/ems/exam/purchaser/print";
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
		purchaserQuestion(request.getParameter("id"), model);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = sdf.format(examPaper.getStartTime());
		String examStartTime = startTime.substring(0,16);
		model.addAttribute("startTime", examStartTime);
		String offTime = sdf.format(examPaper.getOffTime());
		String examOffTime = offTime.substring(0,16);
		model.addAttribute("offTime", examOffTime);
		model.addAttribute("examPaper", examPaper);
		String typeDistribution = examPaper.getTypeDistribution();
		JSONObject object = JSONObject.fromObject(typeDistribution);
		int singleNum = Integer.parseInt(object.get("singleNum").toString());
		BigDecimal singlePoint = new BigDecimal(object.get("singlePoint").toString());
		int multipleNum = Integer.parseInt(object.get("multipleNum").toString());
		BigDecimal multiplePoint = new BigDecimal(object.get("multiplePoint").toString());
		int judgeNum = Integer.parseInt(object.get("judgeNum").toString());
		BigDecimal judgePoint = new BigDecimal(object.get("judgePoint").toString());
		if(singleNum!=0&&multipleNum!=0&&judgeNum!=0){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分；多选题"+multipleNum+"题，每题"+multiplePoint+"分；判断题"+judgeNum+"题，每题"+judgePoint+"分。");
		}else if(singleNum!=0&&multipleNum==0&&judgeNum==0 ){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分。");
		}else if(singleNum==0&&multipleNum!=0&&judgeNum==0){
			model.addAttribute("typeDistribution", "多选题"+multipleNum+"题，每题"+multiplePoint+"分。");
		}else if(singleNum==0&&multipleNum==0&&judgeNum!=0){
			model.addAttribute("typeDistribution", "判断题"+judgeNum+"题，每题"+judgePoint+"分。");
		}else if(singleNum!=0&&multipleNum!=0&&judgeNum==0){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分；多选题"+multipleNum+"题，每题"+multiplePoint+"分。");
		}else if(singleNum!=0&&multipleNum==0&&judgeNum!=0){
			model.addAttribute("typeDistribution", "单选题"+singleNum+"题，每题"+singlePoint+"分；判断题"+judgeNum+"题，每题"+judgePoint+"分。");
		}else if(singleNum==0&&multipleNum!=0&&judgeNum!=0 ){
			model.addAttribute("typeDistribution", "多选题"+multipleNum+"题，每题"+multiplePoint+"分；判断题"+judgeNum+"题，每题"+judgePoint+"分。");
		}
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
		String id = request.getParameter("id");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id);
		Date startTime = examPaper.getStartTime();
	    Date offTime = examPaper.getOffTime(); 
	    map.put("paperId", id);
	    if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=offTime.getTime()){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(map);
			model.addAttribute("paperUserList",new PageInfo<ExamPaperUser>(paperUserList));
			model.addAttribute("examPaper", examPaper);
			model.addAttribute("id", id);
			path = "ses/ems/exam/purchaser/paper/view_test_reference";
		}else if(new Date().getTime()<startTime.getTime()){
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(map);
			model.addAttribute("examPaper", examPaper);
			model.addAttribute("id", id);
			model.addAttribute("paperUserList",new PageInfo<ExamPaperUser>(paperUserList));
			path = "ses/ems/exam/purchaser/paper/view_no_reference";
		}else if(new Date().getTime()>offTime.getTime()){
			List<ExamPaperUser> paperUser = examPaperUserService.findNoTest(map);
			if(paperUser.size()!=0){
				for(int i=0;i<paperUser.size();i++){
					ExamUserScore userScore = new ExamUserScore();
					userScore.setCreatedAt(new Date());
					userScore.setUpdatedAt(new Date());
					userScore.setUserType(2);
					userScore.setUserId(paperUser.get(i).getUserId());
					userScore.setScore("0");
					userScore.setIsMax(1);
					userScore.setPaperId(id);
					userScore.setStatus("不及格");
					examUserScoreService.insertSelective(userScore);
					ExamPaperUser p = new ExamPaperUser();
					p.setId(paperUser.get(i).getId());
					p.setIsDo(2);
					examPaperUserService.updateByPrimaryKeySelective(p);
				}
			}
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<ExamUserScore> userScoreList = examUserScoreService.findPurchaserScore(map);
			model.addAttribute("examPaper", examPaper);
			model.addAttribute("id", id);
			model.addAttribute("paperUserList",new PageInfo<ExamUserScore>(userScoreList));
			path = "ses/ems/exam/purchaser/paper/view_yes_reference";
		}
		return path;
	}
	
	/**
	 * 
	* @Title: setReference
	* @author ZhaoBo
	* @date 2016-10-10 下午12:14:40  
	* @Description: 判断当前考卷是否可设置参考人员 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/setReference")
	@ResponseBody
	public String setReference(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(id);
		Date startTime = examPaper.getStartTime();
	    Date offTime = examPaper.getOffTime();
	    if(new Date().getTime()>=startTime.getTime()&&new Date().getTime()<=offTime.getTime()){
			str = "1";
		}else if(new Date().getTime()<startTime.getTime()){
			str = "2";
		}else if(new Date().getTime()>offTime.getTime()){
			str = "3";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: queryReferenceByCondition
	* @author ZhaoBo
	* @date 2016-10-22 下午5:11:50  
	* @Description: 按条件查找采购人 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/queryReferenceByCondition")
	@ResponseBody
	public String queryReferenceByCondition(HttpServletRequest request){
		String userName = request.getParameter("userName");
		String card = request.getParameter("card");
		String depName = request.getParameter("depName");
		HashMap<String,Object> map = new HashMap<String,Object>();
		String str = null;
		if(userName!=null&&!userName.equals("")){
			map.put("relName", userName);
		}
		if(card!=null&&!card.equals("")){
			map.put("idCard", card);
		}
		if(depName!=null&&!depName.equals("")){
			map.put("purchaseDepName",depName);
		}
		List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
		if(purchaser.size()==0){
			str = "0";
		}else{
			str = "1";
		}
		return str;
	}
	
	/**
	 * 
	* @Title: getReference
	* @author ZhaoBo
	* @date 2016-11-8 下午3:50:05  
	* @Description: 查找采购人 
	* @param @param request
	* @param @return      
	* @return List<PurchaseInfo>
	 */
	@RequestMapping("/getReference")
	@ResponseBody
	public List<PurchaseInfo> getReference(HttpServletRequest request){
		String userName = request.getParameter("userName");
		String card = request.getParameter("card");
		String depName = request.getParameter("depName");
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(userName!=null&&!userName.equals("")){
			map.put("relName", userName);
		}
		if(card!=null&&!card.equals("")){
			map.put("idCard", card);
		}
		if(depName!=null&&!depName.equals("")){
			map.put("purchaseDepName",depName);
		}
		List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
		return purchaser;
	}
	
	/**
	 * 
	* @Title: checkPurchaserInfo
	* @author ZhaoBo
	* @date 2016-11-18 下午1:07:10  
	* @Description: 核实采购人信息 
	* @param @return      
	* @return List<PurchaseInfo>
	 */
	@RequestMapping("/checkPurchaserInfo")
	@ResponseBody
	public List<PurchaseInfo> checkPurchaserInfo(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		List<PurchaseInfo> purchaser = new ArrayList<>();
		for(int i=0;i<id.length;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("userId", id[i]);
			PurchaseInfo info = purchaseService.findPurchaseList(map).get(0);
			purchaser.add(info);
		}
		return purchaser;
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
	public void addReferenceById(HttpServletRequest request,ExamPaperUser examPaperUser,HttpServletResponse response){
		String paperId = request.getParameter("paperId");
		String[] id = request.getParameter("id").split(","); 
		ExamPaper paperDo = examPaperService.selectByPrimaryKey(paperId);
		String str = "";
		String errorNews = "";
		outer:
		for(int i=0;i<id.length;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("userId", id[i]);
			List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
			HashMap<String, Object> p_id = new HashMap<String,Object>();
			p_id.put("paperId", paperId);
			//查询一份考卷所有的参看人员
			List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(p_id);
			if(paperUserList.size()!=0){
				first:
				for(int j=0;j<paperUserList.size();j++){
					if(id[i].equals(paperUserList.get(j).getUserId())){
						str = "1";
						errorNews = errorNews + purchaser.get(0).getRelName().toString()+","+purchaser.get(0).getPurchaseDepName().toString()+","+purchaser.get(0).getIdCard().toString()+";";
						break first;
					}
				}
			}
			if(i==id.length-1){
				if(str.equals("1")){
					errorNews = errorNews + "1";
					break outer;
				}else{
					for(int j=0;j<id.length;j++){
						HashMap<String,Object> mapT = new HashMap<String,Object>();
						mapT.put("userId", id[j]);
						List<PurchaseInfo> purchaserT = purchaseService.findPurchaseList(mapT);
						ExamPaperUser userId = new ExamPaperUser();
						userId.setUserId(id[j]);
						//获取用户参与的考卷
						List<ExamPaperUser> userOfPaper = examPaperUserService.getAllPaperByUserId(userId);
						if(userOfPaper.size()!=0){
							second:
							for(int k=0;k<userOfPaper.size();k++){
								ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(k).getPaperId());
								Date startTime = paper.getStartTime();
							    Date offTime = paper.getOffTime();
							    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
								Date newStartTime = selectedPaper.getStartTime();
							    Date newOffTime = selectedPaper.getOffTime();
							    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newOffTime.getTime())
							    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=offTime.getTime())){
							    	str = "2";
							    	errorNews = errorNews + purchaserT.get(0).getRelName().toString()+","+purchaserT.get(0).getPurchaseDepName().toString()+","+purchaserT.get(0).getIdCard().toString()+";";
								    break second;
							    }
							}
						}
						if(j==id.length-1){
							if(str.equals("2")){
								errorNews = errorNews+"2";
								break outer;
							}
						}
					}
				}
			}
		}
		List<ExamPaperReference> examPaperReference = new ArrayList<ExamPaperReference>();
		if(str.equals("")){
			 for(int i=0;i<id.length;i++) {
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("userId", id[i]);
				List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
				ExamPaperReference paperReference = new ExamPaperReference();
				paperReference.setUserId(purchaser.get(0).getUserId());
				paperReference.setPaperId(paperId);
				paperReference.setUnitName(purchaser.get(0).getPurchaseDepName());
				paperReference.setCard(purchaser.get(0).getIdCard());
				examPaperReference.add(paperReference);
			}	
			for(int i=0;i<examPaperReference.size();i++){
				insertReference(examPaperReference.get(i).getUserId(), paperId, examPaperReference.get(i).getUnitName(),examPaperReference.get(i).getCard(),paperDo.getCode());
			}
			errorNews = "0";
		}
		super.writeJson(response, errorNews);
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
	 */
	@RequestMapping(value="/importReference",method = RequestMethod.POST)
	@ResponseBody
	public void importReference(@RequestParam("file") CommonsMultipartFile file,
			 HttpSession session,HttpServletRequest request,HttpServletResponse response) throws FileNotFoundException, IOException{
        List<ExamPaperReference> examPaperReference = new ArrayList<ExamPaperReference>();
		File excelFile = poiExcel(session,file);
        String paperId = request.getParameter("paperId");
        ExamPaper paperDo = examPaperService.selectByPrimaryKey(paperId);
        String str = "";
        String referenceNews = "";
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
			Cell unitName = row.getCell(2);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("relName", relName.toString());
			if(idCard.toString().indexOf(".")>-1){
				map.put("idCard", idCard.toString().substring(0, idCard.toString().indexOf(".")));
			}else{
				map.put("idCard", idCard.toString());
			}
			List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
			if(purchaser.size()==0){
				str = "1";
				if(idCard.toString().indexOf(".")>-1){
					referenceNews = referenceNews + relName.toString()+","+unitName.toString()+","+idCard.toString().substring(0, idCard.toString().indexOf("."))+";";	
				}else{
					referenceNews = referenceNews + relName.toString()+","+unitName.toString()+","+idCard.toString()+";";
				}
			}
			if(j==sheet.getPhysicalNumberOfRows()-1){
				if(str.equals("1")){
					referenceNews = referenceNews+"1";
					break outer;
				}else{
					for(int k=1;k<sheet.getPhysicalNumberOfRows();k++){
						Row rowC = sheet.getRow(k);
						if (rowC==null) {
							continue;
						}
						Cell relNameC = rowC.getCell(0);
						Cell idCardC = rowC.getCell(1);
						HashMap<String,Object> mapC = new HashMap<String,Object>();
						mapC.put("relName", relNameC.toString());
						if(idCardC.toString().indexOf(".")>-1){
							mapC.put("idCard", idCardC.toString().substring(0, idCardC.toString().indexOf(".")));
						}else{
							mapC.put("idCard", idCardC.toString());
						}
						List<PurchaseInfo> purchaserC = purchaseService.findPurchaseList(mapC);
						HashMap<String, Object> p_id = new HashMap<String,Object>();
						p_id.put("paperId",paperId);
						List<ExamPaperUser> paperUserList = examPaperUserService.getAllByPaperId(p_id);
						if(paperUserList.size()!=0){
							second:for(int i=0;i<paperUserList.size();i++){
								if(paperUserList.get(i).getUserId().equals(purchaserC.get(0).getUserId())){
									str = "2";
									referenceNews = referenceNews + purchaserC.get(0).getRelName()+","+purchaserC.get(0).getPurchaseDepName()+","+purchaserC.get(0).getIdCard()+";";
									break second;
								}
							}
						}
						if(k==sheet.getPhysicalNumberOfRows()-1){
							if(str.equals("2")){
								referenceNews = referenceNews+"2";
								break outer;
							}else{
								for(int t=1;t<sheet.getPhysicalNumberOfRows();t++){
									Row rowT = sheet.getRow(t);
									if (rowT==null) {
										continue;
									}
									Cell relNameT = rowT.getCell(0);
									Cell idCardT = rowT.getCell(1);
									HashMap<String,Object> mapT = new HashMap<String,Object>();
									mapT.put("relName", relNameT.toString());
									if(idCardT.toString().indexOf(".")>-1){
										mapT.put("idCard", idCardT.toString().substring(0, idCardT.toString().indexOf(".")));
									}else{
										mapT.put("idCard", idCardT.toString());
									}
									List<PurchaseInfo> purchaserT = purchaseService.findPurchaseList(mapT);	
									ExamPaperUser userId = new ExamPaperUser();
									userId.setUserId(purchaserT.get(0).getUserId());
									List<ExamPaperUser> userOfPaper = examPaperUserService.getAllPaperByUserId(userId);
									if(userOfPaper.size()!=0){
										third:for(int i=0;i<userOfPaper.size();i++){
											ExamPaper paper = examPaperService.selectByPrimaryKey(userOfPaper.get(i).getPaperId());
											Date startTime = paper.getStartTime();
										    Date offTime = paper.getOffTime();
										    ExamPaper selectedPaper = examPaperService.selectByPrimaryKey(paperId);
											Date newStartTime = selectedPaper.getStartTime();
										    Date newOffTime = selectedPaper.getOffTime();
										    if((startTime.getTime()>=newStartTime.getTime()&&startTime.getTime()<=newOffTime.getTime())
										    		||(newStartTime.getTime()>=startTime.getTime()&&newStartTime.getTime()<=offTime.getTime())){
										    	str = "3";
												referenceNews = referenceNews + purchaserT.get(0).getRelName()+","+purchaserT.get(0).getPurchaseDepName()+","+purchaserT.get(0).getIdCard()+";";
												break third;
										    }
										}
									}
									if(t==sheet.getPhysicalNumberOfRows()-1){
										if(str.equals("3")){
											referenceNews = referenceNews+"3";
											break outer;
										}
									}
								}
							}
						}
					}
				}
			}else{
				continue outer;
			}
		}
			if(str.equals("")){
				 for(int k=1;k<=sheet.getPhysicalNumberOfRows();k++) {
					Row rowK = sheet.getRow(k);
					if (rowK==null) {
						continue;
					}
					Cell relNameK = rowK.getCell(0);
					Cell idCardK = rowK.getCell(1);
					HashMap<String,Object> mapK = new HashMap<String,Object>();
					mapK.put("relName", relNameK.toString());
					if(idCardK.toString().indexOf(".")>-1){
						mapK.put("idCard", idCardK.toString().substring(0, idCardK.toString().indexOf(".")));
					}else{
						mapK.put("idCard", idCardK.toString());
					}
					List<PurchaseInfo> purchaserK = purchaseService.findPurchaseList(mapK);
					ExamPaperReference paperReference = new ExamPaperReference();
					paperReference.setUserId(purchaserK.get(0).getUserId());
					paperReference.setPaperId(paperId);
					paperReference.setUnitName(purchaserK.get(0).getPurchaseDepName());
					paperReference.setCard(purchaserK.get(0).getIdCard());
					examPaperReference.add(paperReference);
				}	
				for(int i=0;i<examPaperReference.size();i++){
					insertReference(examPaperReference.get(i).getUserId(), paperId, examPaperReference.get(i).getUnitName(),examPaperReference.get(i).getCard(),paperDo.getCode());
				}
				referenceNews = "0";
			}
		super.writeJson(response, referenceNews);
	}
	
	/**
	 * 
	* @Title: reference
	* @author ZhaoBo
	* @date 2016-10-24 上午9:50:23  
	* @Description: 返回错误信息的采购人 
	* @param @param userId
	* @param @param paperId
	* @param @param unitName
	* @param @param card
	* @param @return      
	* @return ExamPaperReference
	 */
	public ExamPaperReference reference(String relName,String unitName,String card){
		ExamPaperReference paperReference = new ExamPaperReference();
		paperReference.setRelName(relName);
		paperReference.setUnitName(unitName);
		paperReference.setCard(card);
		return paperReference;
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
		examPaperUser.setUpdatedAt(new Date());
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
		String path = PathUtil.getWebRoot() + "excel/采购人参考人员模板.xls";
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", new String("采购人参考人员模板.xls".getBytes("UTF-8"), "iso-8859-1"));  
		return (new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED));  
	}
	
	/**
	 * 
	* @Title: personalResult
	* @author ZhaoBo
	* @date 2016-9-30 下午5:06:37  
	* @Description: 采购人查询自己的成绩 
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/personalResult")
	public String personalResult(Integer page,Model model,HttpServletRequest request,ExamPaperUser examPaperUser){
		User user = (User) request.getSession().getAttribute("loginUser");
		String code = request.getParameter("code");
		examPaperUser.setUserId(user.getId());
		List<ExamPaperUser> userPapers = examPaperUserService.getAllPaperByUserId(examPaperUser);
		if(userPapers.size()!=0){
			for(int i=0;i<userPapers.size();i++){
				ExamPaper examPaper = examPaperService.selectByPrimaryKey(userPapers.get(i).getPaperId());
				if(new Date().getTime()>examPaper.getOffTime().getTime()){
			    	if(userPapers.get(i).getIsDo()==0){
			    		ExamUserScore examUserScore = new ExamUserScore();
			    		examUserScore.setCreatedAt(new Date());
			    		examUserScore.setUpdatedAt(new Date());
						examUserScore.setUserType(2);
						examUserScore.setUserId(user.getId());
						examUserScore.setScore("0");
						examUserScore.setIsMax(1);
						examUserScore.setPaperId(userPapers.get(i).getPaperId());
						examUserScore.setStatus("不及格");
						examUserScoreService.insertSelective(examUserScore);
						ExamPaperUser paperOfUser = new ExamPaperUser();
						paperOfUser.setId(userPapers.get(i).getId());
						paperOfUser.setIsDo(2);
						examPaperUserService.updateByPrimaryKeySelective(paperOfUser);
			    	}
			    }
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		if(code!=null&&!code.equals("")){
			map.put("code", "%"+code+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamUserScore> userScores = examUserScoreService.findByUserIdAndCode(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(userScores.size()!=0){
			for(int i=0;i<userScores.size();i++){
				userScores.get(i).setRelName(user.getRelName());
				if(userScores.get(i).getTestDate()!=null){
					userScores.get(i).setFormatDate(sdf.format(userScores.get(i).getTestDate()));
				}
			}
		}
		model.addAttribute("list", new PageInfo<ExamUserScore>(userScores));
		model.addAttribute("code", code);
		return "ses/ems/exam/purchaser/personal_result";
	}
	
	/**
	 * 
	* @Title: exitExam
	* @author ZhaoBo
	* @date 2016-10-8 上午8:33:57  
	* @Description: 退出考试 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/exitExam")
	public String exitExam(){
		return "redirect:/login/home.html";
	}
	
	@RequestMapping("/testSchedule")
	public String testSchedule(HttpServletRequest request,Model model,Integer page){
		User user = (User) request.getSession().getAttribute("loginUser");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ExamPaperUser> schedule = examPaperUserService.findCurrentUserSchedule(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<schedule.size();i++){
			schedule.get(i).setStartDate(sdf.format(schedule.get(i).getStartTime()));
			schedule.get(i).setOffDate(sdf.format(schedule.get(i).getOffTime()));
		}
		model.addAttribute("testSchedule", new PageInfo<ExamPaperUser>(schedule));
		return "ses/ems/exam/purchaser/test_schedule";
	}
	
	/**
	 * 
	* @Title: backQuestion
	* @author ZhaoBo
	* @date 2016-10-31 上午10:03:51  
	* @Description: 返回到采购人题库列表 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backQuestion")
	public String backQuestion(){
		return "redirect:purchaserList.html";
	}
	
	/**
	 * 
	* @Title: backPaper
	* @author ZhaoBo
	* @date 2016-10-31 上午10:09:18  
	* @Description: 返回到考卷列表 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backPaper")
	public String backPaper(){
		return "redirect:paperManage.html";
	}
	
	/**
	 * 
	* @Title: userAdd
	* @author ZhaoBo
	* @date 2016-11-18 上午11:07:40  
	* @Description: 添加考试人员 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/userAdd")
	public String userAdd(HttpServletRequest request,Model model,Integer page){
		String paperId = request.getParameter("paperId");
		ExamPaper examPaper = examPaperService.selectByPrimaryKey(paperId);
		String relName = request.getParameter("relName");
		String card = request.getParameter("card");
		String depName = request.getParameter("depName");
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(relName!=null&&!relName.equals("")){
			map.put("relName", relName);
		}
		if(card!=null&&!card.equals("")){
			map.put("idCard", card);
		}
		if(depName!=null&&!depName.equals("")){
			map.put("purchaseDepName",depName);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseInfo> purchaser = purchaseService.findPurchaseList(map);
		model.addAttribute("examPaper", examPaper);
		model.addAttribute("id", paperId);
		model.addAttribute("relName", relName);
		model.addAttribute("card", card);
		model.addAttribute("depName", depName);
		model.addAttribute("purchaser", new PageInfo<PurchaseInfo>(purchaser));
		return "ses/ems/exam/purchaser/paper/user_add";
	}
	
}
