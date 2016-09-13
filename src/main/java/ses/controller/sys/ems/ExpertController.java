package ses.controller.sys.ems;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.util.Encrypt;
import ses.util.WfUtil;
import ses.util.WordUtil;


@Controller
@RequestMapping("/expert")
public class ExpertController {
	@Autowired
	private UserServiceI userService;
	@Autowired
	private ExpertService service;
	/**
	 * 
	  * @Title: toExpert
	  * @author lkzx 
	  * @date 2016年8月31日 下午7:04:16  
	  * @Description: TODO 跳转到评审专家注册页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping(value="/toExpert")
	public String toExpert(){
		
		return "ems/expert/expertRegister";
	}
	
	/**
	 * 
	  * @Title: toExpert
	  * @author lkzx 
	  * @date 2016年8月31日 下午7:04:16  
	  * @Description: TODO 跳转到评审专家注册须知页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping(value="/toRegisterNotice")
	public String toRegisterNotice(){
		
		return "ems/expert/basic_info";
	}
	
	/**
	 * 
	  * @Title: add
	  * @author lkzx 
	  * @date 2016年8月31日 下午6:36:19  
	  * @Description: TODO 注册评审专家用户
	  * @param @param expert
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/register")
	public String register( User expert,HttpSession session, Model model,HttpServletRequest request,@RequestParam String token2){
		Object tokenValue = session.getAttribute("tokenSession");
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
			//判断用户名密码是否合法
			String loginName = expert.getLoginName();
			String password = expert.getPassword();
			String regex="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
			Pattern p = Pattern.compile(regex);
			Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(loginName);
			Matcher m2 = p2.matcher(loginName);
			Matcher matcher = p.matcher(password);
			Matcher matcher2 = p2.matcher(password);
			if(loginName.trim().length()<3 || m.find() || m2.find()){
				model.addAttribute("message", "用户名不符合规则");
				return "ems/expert/expertRegister";
			}else if(password.trim().length()<6 || matcher.find() || matcher2.find()){
				model.addAttribute("message", "密码不符合规则");
				return "ems/expert/expertRegister";
			}
		expert.setId(UUID.randomUUID().toString());
		expert.setCreatedAt(new Date());
		expert.setIsDeleted(0);
		//密码加密
		String md5AndSha = Encrypt.md5AndSha(expert.getLoginName()+expert.getPassword());
		expert.setPassword(md5AndSha);
		request.setAttribute("user", expert);
		//model.addAttribute("expert", expert);
		userService.save(expert);
		return "ems/expert/basic_info";
		} else{
			return "ems/expert/basic_info";
		}
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到填写 、修改个人信息
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toBasicInfo")
	public String toBasicInfo(@RequestParam("id")String id,HttpServletRequest request,HttpServletResponse response,  Model model){
		Expert expert = service.selectByPrimaryKey(id);
		model.addAttribute("expert", expert);
		return "ems/expert/basic_info";
	}
	/**
	 * 
	  * @Title: toBasicInfo
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:12:55  
	  * @Description: TODO 跳转到审核页面
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHe")
	public String toShenHe(@RequestParam("id") String id,HttpServletRequest request,HttpServletResponse response,  Model model){
		Expert expert = service.selectByPrimaryKey(id);
		request.setAttribute("expert", expert);
		return "ems/expert/shenHe";
	}
	/**
	 * 
	  * @Title: shenhe
	  * @author lkzx 
	  * @date 2016年9月5日 下午2:12:19  
	  * @Description: TODO 审核专家信息
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/shenhe")
	public String shenhe(@RequestParam("isPass")String isPass, Expert expert,@RequestParam("remark")String remark){
		expert.setStatus(isPass);
		service.updateByPrimaryKeySelective(expert);
		return "redirect:findAllExpert.html";
	}
	/**
	 * 
	  * @Title: edit
	  * @author lkzx 
	  * @date 2016年9月1日 上午11:14:38  
	  * @Description: TODO 新增、修改个人信息
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/edit")
	public String edit(@RequestParam("files")MultipartFile[] files,Expert expert,@RequestParam("userId")String userId,Model model,HttpSession session,@RequestParam String token2 ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Object tokenValue = session.getAttribute("tokenSession");
		String expertId = UUID.randomUUID().toString();
		if (tokenValue != null && tokenValue.equals(token2)) {
			// 正常提交
			session.removeAttribute("tokenSession");
			//判断file数组不能为空并且长度大于0 
		if(files!=null && files.length>0){
			 for(MultipartFile myfile : files){  
		            if(myfile.isEmpty()){  
		            	
		            }else{  
		                String filename = myfile.getOriginalFilename();
		                String uuid = WfUtil.createUUID();
		                //文件名处理
		                filename=uuid+filename;
		                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
		                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
		                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
		                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));  
		            }  
		        }  
			}
		//个人信息关联用户
		if(userId!=null && userId.length()>0){
			//直接注册完之后填写个人信息
			User user = service.getUserById(userId);
			user.setTypeName(5);
			if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
				user.setTypeId(expertId);
			}else{
				user.setTypeId(expert.getId());
			}
			userService.update(user);
		}else{
			//注册完账号  过段时间又填写个人信息
			/*User user = (User)session.getAttribute("loginUser");
			if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
				user.setTypeId(expertId);
			}else{
				user.setTypeId(expert.getId());
			}
			userService.update(user);*/
		}
		if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
			//id为空 说明为新增数据
			expert.setId(expertId);
			//已提交
			expert.setIsSubmit("1");
			//修改时间
			expert.setUpdatedAt(new Date());
			service.insertSelective(expert);
		}else{
			//否则为修改
			//已提交
			expert.setIsSubmit("1");
			//修改时间
			expert.setUpdatedAt(new Date());
			service.updateByPrimaryKeySelective(expert);
		}
		//关联机构信息
		
			//查询出所有信息放进model中
			Expert expert2 = service.selectByPrimaryKey(expert.getId());
			model.addAttribute("expert", expert2); 
			//判断是暂存还是下一步
		/*if(zancun!=null && zancun.equals("1")){
			return "redirect:/";
		}*/
		return "redirect:/";
		}else{//重复提交
			//查询出所有信息放进model中
		/*if(zancun!=null && zancun.equals("1")){
			return "redirect:/";
		}*/
		return "redirect:/";
		}
	}
	/**
	 * 
	  * @Title: deleteAll
	  * @author ShaoYangYang
	  * @date 2016年9月8日 下午3:53:36  
	  * @Description: TODO 删除
	  * @param       
	  * @return void
	 */
	@RequestMapping("/deleteAll")
	public String deleteAll(@RequestParam("ids") String ids){
		String[] id = ids.split(",");
		for (String string : id) {
			service.deleteByPrimaryKey(string);
		}
		return "redirect:findAllExpert.html";
	}
	
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 查询所有专家  可以条件查询
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/findAllExpert")
	public String findAllExpert(  Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		
		request.setAttribute("result", new PageInfo<>(allExpert));
		request.setAttribute("expert", expert);
		return "ems/expert/list";
	}
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 查询所有审核专家  可以条件查询
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/findAllExpertShenHe")
	public String findAllExpertShenHe(  Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		
		request.setAttribute("result", new PageInfo<>(allExpert));
		request.setAttribute("expert", expert);
		return "ems/expert/expertList";
	}
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到未审核专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert")
	public String toShenHeExpert( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("0");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		
		request.setAttribute("result", new PageInfo<>(allExpert));
		request.setAttribute("expert", expert);
		return "ems/expert/expertList";
	}
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到审核通过专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert2")
	public String toShenHeExpert2( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("1");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		
		request.setAttribute("result", new PageInfo<>(allExpert));
		request.setAttribute("expert", expert);
		return "ems/expert/expertList";
	}
	/**
	 * 
	  * @Title: findAllExpert
	  * @author lkzx 
	  * @date 2016年9月2日 下午5:44:37  
	  * @Description: TODO 跳转到审核未通过专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toShenHeExpert3")
	public String toShenHeExpert3( Expert expert,Integer page,HttpServletRequest request,HttpServletResponse response){
		expert.setStatus("2");
		List<Expert> allExpert = service.selectAllExpert(page==null?1:page,expert);
		
		request.setAttribute("result", new PageInfo<>(allExpert));
		request.setAttribute("expert", expert);
		return "ems/expert/expertList";
	}
	 /**
	  * 
	   * @Title: toDaiBan
	   * @author ShaoYangYang
	   * @date 2016年9月12日 下午4:01:22  
	   * @Description: TODO 跳转到待办页面
	   * @param @param model
	   * @param @return      
	   * @return String
	  */
	 @RequestMapping("/toDaiBan")
	 public String toDaiBan(Expert expert,Model model){
		 expert.setStatus("0");
		 Integer weishenhe = service.getCount(expert);
		 expert.setStatus("1");
		 Integer tongguo = service.getCount(expert);
		 expert.setStatus("2");
		 Integer pass = service.getCount(expert);
		  model.addAttribute("weishenhe", weishenhe);
		  model.addAttribute("tongguo", tongguo);
		  model.addAttribute("pass", pass);
		 return "ems/expert/daiban";
	 }
	 /**
	  * 
	   * @Title: upLoadExpertTable
	   * @author ShaoYangYang
	   * @date 2016年9月6日 下午5:17:45  
	   * @Description: TODO 上传专家申请表和承诺书
	   * @param @param id
	   * @param @param files
	   * @param @param request
	   * @param @return
	   * @param @throws IOException      
	   * @return String
	  */
	 @RequestMapping("/upLoadExpertTable")
	 public String upLoadExpertTable(@RequestParam("id") String id,@RequestParam("files") MultipartFile[] files,HttpServletRequest request) throws IOException{
		 
		 if(files!=null && files.length>0){
			 for(MultipartFile myfile : files){  
		            if(myfile.isEmpty()){  
		                //System.out.println("文件未上传");  
		            }else{  
		                String filename = myfile.getOriginalFilename();
		                String uuid = WfUtil.createUUID();
		                //文件名处理
		                filename=uuid+filename;
		                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
		                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
		                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
		                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));  
		            }  
		        }  
			}
		 return "redirect:/";
	 }
	 /**
	  * 
	   * @Title: download
	   * @author ShaoYangYang
	   * @date 2016年9月7日 下午6:53:12  
	   * @Description: TODO 下载申请表
	   * @param @param id
	   * @param @param request
	   * @param @return
	   * @param @throws Exception      
	   * @return ResponseEntity<byte[]>
	  */
	 @RequestMapping("download")
	 public ResponseEntity<byte[]> download(Expert expert,HttpServletRequest request) throws Exception{
		 //Expert expert = service.selectByPrimaryKey(id);
		 String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
		 String fileName = createWordMethod(expert, request);
		 File file=new File(filePath+"/"+fileName);  
	        HttpHeaders headers = new HttpHeaders(); 
	        String downFileName=new String("军队评标专家申请表.doc".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        headers.setContentDispositionFormData("attachment", downFileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
	        file.delete();
	        return entity;
	 }
	 
	 
	 /**
		 * 
		 * 
		 * @Title: createWordMethod
		 * @author: lkzx
		 * @date: 2016-9-7 下午3:25:38
		 * @Description: TODO  生成word下载
		 * @param: @param expert
		 * @return: String
		 * @throws Exception
		 */
		private String createWordMethod(Expert expert,HttpServletRequest request) throws Exception {
			/** 用于组装word页面需要的数据 */
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("name", expert.getRelName()== null ? "" : expert.getRelName());
			dataMap.put("sex",expert.getGender()== null ? "" : expert.getGender());
			dataMap.put("birthday",expert.getBirthday()== null ? "" :new SimpleDateFormat(
					"yyyy-MM-dd").format(expert.getBirthday()));
			dataMap.put("face",expert.getPoliticsStatus()== null ? "" : expert.getPoliticsStatus());
			dataMap.put("address",expert.getAddress()== null ? "" : expert.getAddress());
			
			dataMap.put("zhi",expert.getProfessTechTitles()== null ? "" : expert.getProfessTechTitles());
			
			dataMap.put("number",expert.getIdNumber()== null ? "" : expert.getIdNumber());
			String expertType="";
			String expertsTypeId = expert.getExpertsTypeId();
			if(expertsTypeId!=null && expertsTypeId.equals("1")){
				expertType="技术";
			}else if(expertsTypeId!=null && expertsTypeId.equals("2")){
				expertType="法律";
			}else if(expertsTypeId!=null && expertsTypeId.equals("2")){
				expertType="商务";
			}
			dataMap.put("type",expertType);
			dataMap.put("date",expert.getTimeStartWork()== null ? "" :new SimpleDateFormat(
					"yyyy-MM-dd").format(expert.getTimeStartWork()));
			dataMap.put("xueli",expert.getHightEducation() == null ? "" : expert.getHightEducation());
			dataMap.put("xuewei",expert.getDegree() == null ? "" : expert.getDegree());
			dataMap.put("phone", expert.getMobile() == null ? "" : expert.getMobile());
			dataMap.put("teliphone", expert.getTelephone() == null ? "" : expert.getTelephone());
			dataMap.put("school", expert.getGraduateSchool() == null ? "" : expert.getGraduateSchool());
			dataMap.put("unitName", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
			dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getUnitAddress());
			dataMap.put("zipCode", expert.getPostCode() == null ? "" : expert.getPostCode());
			
			// 文件名称
			String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"), "UTF-8");
			/** 生成word 返回文件名 */
			String newFileName = WordUtil.createWord(dataMap, "expert.ftl",fileName,request);
			return newFileName;
		}
}
