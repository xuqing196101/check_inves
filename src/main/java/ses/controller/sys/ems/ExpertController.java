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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.ems.Expert;
import ses.service.ems.ExpertService;
import ses.util.Encrypt;
import ses.util.WfUtil;
import ses.util.WordUtil;


@Controller
@RequestMapping("/expert")
public class ExpertController {

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
		
		return "ems/expert/registerNotice";
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
	public String register( Expert expert,HttpSession session, Model model,HttpServletRequest request,@RequestParam String token2){
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
		//密码加密
		String md5AndSha = Encrypt.md5AndSha(expert.getPassword());
		expert.setPassword(md5AndSha);
		request.setAttribute("expert", expert);
		//model.addAttribute("expert", expert);
		service.insertSelective(expert);
		return "ems/expert/basicInfo";
		} else{
			return "ems/expert/basicInfo";
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
		return "ems/expert/basicInfo";
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
	  * @Description: TODO 修改、填写个人信息
	  * @param @return      
	  * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/edit")
	public String edit(@RequestParam("files")MultipartFile[] files,@RequestParam("zancun")String zancun,Expert expert ,Model model,HttpSession session,@RequestParam String token2 ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Object tokenValue = session.getAttribute("tokenSession");
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
			//修改时间
			expert.setUpdatedAt(new Date());
			service.updateByPrimaryKeySelective(expert);
			//查询出所有信息放进model中
			Expert expert2 = service.selectByPrimaryKey(expert.getId());
			model.addAttribute("expert", expert2); 
			//判断是暂存还是下一步
		if(zancun!=null && zancun.equals("1")){
			return "redirect:/";
		}
		return "ems/expert/expertType";
		}else{
			//查询出所有信息放进model中
		Expert expert2 = service.selectByPrimaryKey(expert.getId());
		model.addAttribute("expert", expert2);
		if(zancun!=null && zancun.equals("1")){
			return "redirect:/";
		}
		return "ems/expert/expertType";
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
	  * @Description: TODO 查询所有专家
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/findAllExpert")
	public String findAllExpert(HttpServletRequest request,HttpServletResponse response){
		List<Expert> allExpert = service.selectAllExpert();
		request.setAttribute("expert", allExpert);
		return "ems/expert/list";
	}
	
  /**
   * 
    * @Title: findAllLoginName
    * @author lkzx 
    * @date 2016年9月1日 下午5:35:45  
    * @Description: TODO 用户名ajax校验
    * @param @param model
    * @param @return      
    * @return List<String>
   */
	@RequestMapping("/findAllLoginName")
	@ResponseBody
	public List<Expert> findAllLoginName(@RequestParam("loginName")String loginName, Model model){
		List<Expert> selectLoginNameList = service.selectLoginNameList(loginName);
		return selectLoginNameList;
	}
	/**
	 * 
	  * @Title: expertType
	  * @author ShaoYangYang
	  * @date 2016年9月6日 上午11:35:26  
	  * @Description: TODO 专家类型修改
	  * @param @param id
	  * @param @param expertsTypeId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/expertType")
	public String expertType(@RequestParam("zancun") String zancun,@RequestParam("id") String id,@RequestParam("expertsTypeId")String expertsTypeId,Model model){
		Expert expert = service.selectByPrimaryKey(id);
		if(expertsTypeId!=null && expertsTypeId.length()>0){
		expert.setExpertsTypeId(expertsTypeId);
		}
		service.updateByPrimaryKeySelective(expert);
		model.addAttribute("expert", expert);
		//查询采购机构信息
		
		//暂存 还是下一步
		if(zancun!=null && zancun.equals("1")){
			return "redirect:/";
		}else{
		return "ems/expert/caiGouJiGouList";
		}
	}
	/**
	 * 
	  * @Title: expertType
	  * @author ShaoYangYang
	  * @date 2016年9月6日 上午11:35:26  
	  * @Description: TODO 专家采购机构修改
	  * @param @param id
	  * @param @param expertsTypeId
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/expertJiGou")
	public String expertJiGou(@RequestParam("id") String id,@RequestParam("purchaseDepId")String purchaseDepId,Model model){
		Expert expert = service.selectByPrimaryKey(id);
		if(purchaseDepId!=null && purchaseDepId.length()>0){
		expert.setPurchaseDepId(purchaseDepId);
		}
		service.updateByPrimaryKeySelective(expert);
		model.addAttribute("expert", expert);
		//查询采购机构信息
		return "ems/expert/caiGouJiGouList";
	}
	/**
	 * 
	  * @Title: addJiGou
	  * @author ShaoYangYang
	  * @date 2016年9月6日 下午4:28:48  
	  * @Description: TODO 添加采购机构
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/addJiGou")
	public String addJiGou(@RequestParam("id") String id,@RequestParam("flag") Integer flag,@RequestParam("check")String check,Model model){
		Expert expert = service.selectByPrimaryKey(id);
		//暂存
		if(flag==1){
			expert.setPurchaseDepId(check);
			service.updateByPrimaryKeySelective(expert);
			return "redirect:/";
		}else{
			//下一步
			expert.setPurchaseDepId(check);
			service.updateByPrimaryKeySelective(expert);
			model.addAttribute("expert", expert);
			return "ems/expert/expertShenQing";
		}
	}
	/**
	 * 
	  * @Title: uploadExpertTable
	  * @author ShaoYangYang
	  * @date 2016年9月6日 下午5:07:12  
	  * @Description: TODO 跳转到上传申请表页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	 @RequestMapping("/toUploadExpertTable")
	 public String uploadExpertTable(@RequestParam("id") String id,Model model){
		 //采购机构信息？？
		 
		 model.addAttribute("id", id);
		 
		 return "ems/expert/uploadTable";
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
	 public ResponseEntity<byte[]> download(@RequestParam(value="id",required=false)String id,HttpServletRequest request) throws Exception{
		 Expert expert = service.selectByPrimaryKey(id);
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
			dataMap.put("sex",expert.getSex()== null ? "" : expert.getSex());
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
			dataMap.put("teliphone", expert.getFixPhone() == null ? "" : expert.getFixPhone());
			dataMap.put("school", expert.getGraduateSchool() == null ? "" : expert.getGraduateSchool());
			dataMap.put("unitName", expert.getWorkUnit() == null ? "" : expert.getWorkUnit());
			dataMap.put("unitAddress", expert.getUnitAddress() == null ? "" : expert.getUnitAddress());
			dataMap.put("zipCode", expert.getZipCode() == null ? "" : expert.getZipCode());
			
			// 文件名称
			String fileName = new String(("军队评标专家申请表.doc").getBytes("UTF-8"), "UTF-8");
			/** 生成word 返回文件名 */
			String newFileName = WordUtil.createWord(dataMap, "expert.ftl",fileName,request);
			return newFileName;
		}
}
