package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBRule;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBRuleService;

import bss.util.ExcelUtil;
/**
 * 竞价信息管理控制
 * 
 * @author YangHongliang
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ob_project")
public class OBProjectController {
	@Autowired
	private OBProjectServer OBProjectServer;

	@Autowired
	private OrgnizationServiceI orgnizationService;
	@Autowired
	private  OBRuleService  OBRuleService;

	/***
	 * 获取竞价信息跳转 list页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
	public String list(@CurrentUser User user,Model model, HttpServletRequest request, Integer page,Date startTime,String name) {
		if(user !=null){
		if (page == null) {
			page = 1;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("page", page);
		map.put("uid", user.getId());
		map.put("startTime", startTime);
		map.put("name", name);
		List<OBProject> list = OBProjectServer.List(map);
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		model.addAttribute("info", info);
		}
		return "bss/ob/biddingInformation/list";
	}

	/**
	 * 发布竞价信息跳转 add页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String addBidding(@CurrentUser User user, Model model,
			HttpServletRequest request) {
		//默认规则
		OBRule obr=OBRuleService.selectByStatus();
		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		model.addAttribute("fileid", uuid);
		model.addAttribute("rule", obr);
		model.addAttribute("userId", user.getId());
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		// 标识 竞价附件
		model.addAttribute("typeId",
				DictionaryDataUtil.getId("BIDD_INFO_MANAGE_ANNEX"));
		return "bss/ob/biddingInformation/publish";
	}

    /**
     * 获取可用的采购机构 信息 并返回页面
     * @author YangHongLiang
     * @throws IOException 
     */
    @RequestMapping("mechanism")
    public void getMechanism(@CurrentUser User user,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    	    String json= orgnizationService.getMechanism();
    	    response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}
	/**
	 * 
	 * @Title: biddingInfoList
	 * @Description: 竞价信息列表显示
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return
	 * @param @throws ParseException 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/biddingInfoList")
	public String biddingInfoList(Model model, HttpServletRequest request,
			Integer page) throws ParseException {
		if (page == null) {
			page = 1;
		}

		// 竞价标题
		String name = request.getParameter("name");
		// 竞价开始时间
		String startTimeStr = request.getParameter("startTime");
		// 竞价结束时间
		String endTimeStr = request.getParameter("endTime");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startTime = null;
		if (StringUtils.isNotEmpty(startTimeStr)) {
			startTime = dateFormat.parse(startTimeStr);
		}
		Date endTime = null;
		if (StringUtils.isNotEmpty(endTimeStr)) {
			endTime = dateFormat.parse(endTimeStr);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("page", page);
		List<OBProject> list = OBProjectServer.selectAllOBproject(map);
		// 封装分页信息
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		// 将查询信息封装到model域中
		model.addAttribute("info", info);
		model.addAttribute("name", name);
		model.addAttribute("startTime", startTimeStr);
		model.addAttribute("endTime", endTimeStr);
		return "bss/ob/biddingSpectacular/list";

	}

	/**
	 * 
	* @Title: findBiddingResult 
	* @Description: 竞价结果查询
	* @author Easong
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request,
			Integer page) {
		// 获取竞价标题的id
		String id = request.getParameter("id");
		// TODO
		
		// 将竞价标题id封装到model中，打印使用
		model.addAttribute("id", id);
		return "bss/ob/biddingSpectacular/result";
	}

    
    /**
     * 获取可用的产品相关信息 并返回页面
     * @author YangHongLiang
     * @throws IOException 
     */
    @RequestMapping("product")
    public void getProduct(@CurrentUser User user,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    	    String json= OBProjectServer.getProduct();
    	    response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			response.getWriter().close();
	      }
    }
    /**
	 * 
	* @Title: downFile
	* @Description: 下载excel表格模板
	* author: YangHongLiang 
	* @param @param path
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("download")    
	public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException {
	    	String path = PathUtil.getWebRoot() + "excel/定型产品.xls";;  
	        File file=new File(path);
	        HttpHeaders headers = new HttpHeaders();    
	        String fileName=new String("定型产品模板.xls".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                                          headers, HttpStatus.OK);    
	}
	/** @Description: 竞价管理保存
	* author: YangHongLiang
	* @param 接收页面返回数据
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping("addProject")
	@ResponseBody
	public String addProject(@CurrentUser User user,OBProject obProject, HttpServletRequest request,
			String fileid){
		String msg="";
		if(user !=null){
			
			msg=OBProjectServer.saveProject(obProject,user.getId(),fileid);
		}
		return msg;
		
	}
	/**
	* @Title: uploadFile
	* @Description: 导入excel表格数据
	* author: YangHongLiang
	* @param @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/upload", produces="text/html;charset=UTF-8" )
	@ResponseBody
	public String uploadFile(@CurrentUser User user,String planDepName,MultipartFile file,String type,String planName,String planNo,Model model) throws Exception{
        String fileName = file.getOriginalFilename();  
        if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){  
        	return "1";
        }  
        
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		    Map<String,Object>  maps= (Map<String, Object>) ExcelUtil.readOBExcel(file);
		     list = (List<PurchaseRequired>) maps.get("list");
		     
		     String errMsg=(String) maps.get("errMsg");
		
		     if(errMsg!=null){
		          String jsonString = JSON.toJSONString(errMsg);
			   return jsonString;
			}
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
	
	
	
	/**
	 * 
	* @Title: printResult 
	* @Description: 打印竞价结果
	* @author Easong
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/printResult")
	public String printResult(Model model,HttpServletRequest request){
		
		return "bss/ob/biddingSpectacular/print";
	}
}
