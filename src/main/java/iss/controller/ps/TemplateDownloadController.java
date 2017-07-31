/**
 * 
 */
package iss.controller.ps;

import iss.model.ps.TemplateDownload;
import iss.service.ps.ArticleService;
import iss.service.ps.TemplateDownloadService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

/**
 * @Title:TemplateDownloadController
 * @Description: 模板下载控制类
 * @author tianzhiqiang
 * @date 2017-1-5上午10:01:56
 */
@Controller
@RequestMapping("/templateDownload")
public class TemplateDownloadController {
	/** 模板下载service */
	@Autowired
	private TemplateDownloadService TemplateDownloadService;
	
	/** 数据字典service */
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	/** 上传service */
	@Autowired
	private UploadService uploadService;
	
	/** 文章service */
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 
	* @Title: getList
	* @author tianzhiqiang
	* @date 2017-1-5 下午5:07:33  
	* @Description: 模板下载列表 
	* @param @param page
	* @param @param TemplateDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getList")
	public String getList(@CurrentUser User user,Integer page,TemplateDownload templateDownload,Model model,HttpServletRequest request){
		//声明标识是否是资源服务中心
        String authType = null;
        //判断是否 是资源服务中心 
        if(user != null && "4".equals(user.getTypeName())){
            authType = "4";
			HashMap<String,Object> map = new HashMap<>();
			if(templateDownload!=null){
				if(templateDownload.getName()!=null && !templateDownload.getName().equals("")){
					map.put("name", templateDownload.getName());
				}
			}
			if(page==null){
				page = 1;
			}
			map.put("page", page.toString());
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
			String ipAddressType=config.getString("ipAddressType");
			// 1外网 0内网
			if("1".equals(ipAddressType)){
				map.put("ipAddressType", ipAddressType);
			}
			List<TemplateDownload> list = TemplateDownloadService.findDataByCondition(map);
			
		    Integer num = 0;
		    StringBuilder groupUpload = new StringBuilder("");
		    StringBuilder groupShow = new StringBuilder("");
		    for (TemplateDownload a : list) {
		      num++;
		      groupUpload = groupUpload.append("data_secret_show" + num + ",");
		      groupShow = groupShow.append("data_secret_show" + num + ",");
		      a.setGroupsUpload("data_secret_show" + num);
		      a.setGroupShow("data_secret_show" + num);
		    }
		    String groupUploadId = "";
		    String groupShowId = "";
		    if (!"".equals(groupUpload.toString())) {
		      groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length() - 1);
		    }
		    if (!"".equals(groupShow.toString())) {
		      groupShowId = groupShow.toString().substring(0, groupShow.toString().length() - 1);
		    }
		    for (TemplateDownload act : list) {
		      act.setGroupsUploadId(groupUploadId);
		      act.setGroupShowId(groupShowId);
		    }
			
			
			model.addAttribute("list", new PageInfo<TemplateDownload>(list));
			model.addAttribute("name", request.getParameter("name"));
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
		    if (dlist.size() > 0) {
		      model.addAttribute("dataTypeId", dlist.get(0).getId());
		    }
		    model.addAttribute("authType", authType);
        }
		return "iss/ps/templateDownload/list";
	}
	
	/**
	 * 
	* @Title: add
	* @author tianzhiqiang
	* @date 2017-1-5 下午5:07:49  
	* @Description: 新增 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(@CurrentUser User user,Model model){
		//判断是否 是资源服务中心 
        if(user != null && "4".equals(user.getTypeName())){
			String dataId = UUID.randomUUID().toString().replaceAll("-", "");
			model.addAttribute("dataId", dataId);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
			return "iss/ps/templateDownload/add";
        }
        return "";
	}
	
	/**
	 * 
	* @Title: save
	* @author tianzhiqiang
	* @date 2017-1-5 下午9:52:53  
	* @Description: 保存模板
	* @param @param TemplateDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(@CurrentUser User user,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()==null||TemplateDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能为空");
			}else if(TemplateDownload.getName()!=null&&TemplateDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能超过200个文字");
			}
		}
//		String ipAddress = null;
		//String[] ipAddressType = request.getParameterValues("ipAddressType");
//		if(ipAddressType==null){
//			flag = false;
//			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
//		}else{
//			ipAddress = ipAddressType[0];
//		}
		String id = request.getParameter("id");
		List<UploadFile> zlfj = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
		if(zlfj.size()<1){
			flag = false;
			model.addAttribute("ERR_dataFile", "请上传附件");
		}
		if(TemplateDownload.getIpAddressType()==null){
			flag = false;
			model.addAttribute("ERR_IpAddressType", "请选择发布范围");
		}
		if(TemplateDownload.getIpAddressType()!=null&&"".equals(TemplateDownload.getIpAddressType())){
			flag = false;
			model.addAttribute("ERR_IpAddressType", "请选择发布范围");
		}
		if(flag==false){
			model.addAttribute("dataId", id);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
		    model.addAttribute("data", TemplateDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/templateDownload/add";
		}
		//TemplateDownload.setIpAddressType(Integer.parseInt(ipAddressType[0]));
		TemplateDownload.setCreatedAt(new Date());
		TemplateDownload.setIsDeleted(0);
		TemplateDownload.setStatus(1);
		TemplateDownload.setUpdatedAt(new Date());
		TemplateDownload.setUserId(user.getId());
		TemplateDownloadService.insertSelective(TemplateDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: view
	* @author tianzhiqiang
	* @date 2017-1-5 下午9:44:53  
	* @Description: 查看 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(@CurrentUser User user,Model model,HttpServletRequest request){
		//判断是否 是资源服务中心 
        if(user != null && "4".equals(user.getTypeName())){
			String id = request.getParameter("id");
			TemplateDownload TemplateDownload = TemplateDownloadService.selectByPrimaryKey(id);
			model.addAttribute("data", TemplateDownload);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
			return "iss/ps/templateDownload/view";
        }
        return "";
	}
	
	/**
	 * 
	* @Title: judgeEdit
	* @author tianzhiqiang
	* @date 2017-1-6 下午12:45:31  
	* @Description: 判断模板能不能修改 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/judgeEdit")
	@ResponseBody
	public String judgeEdit(HttpServletRequest request){
		TemplateDownload TemplateDownload = TemplateDownloadService.selectByPrimaryKey(request.getParameter("id"));
		if(TemplateDownload.getStatus()==2||TemplateDownload.getStatus()==3){
			return "0";
		}else{
			return "1";
		}
	}
	
	/**
	 * 
	* @Title: edit
	* @author tianzhiqiang
	* @date 2017-1-5 下午9:50:06  
	* @Description: 修改模板
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,HttpServletRequest request){
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
	    if (list.size() > 0) {
	      model.addAttribute("dataTypeId", list.get(0).getId());
	    }
		String id = request.getParameter("id");
		TemplateDownload TemplateDownload = TemplateDownloadService.selectByPrimaryKey(id);
		model.addAttribute("data", TemplateDownload);
		return "iss/ps/templateDownload/edit";
	}
	
	/**
	 * 
	* @Title: deleteById
	* @author tianzhiqiang
	* @date 2017-1-5 下午10:36:51  
	* @Description: 删除模板
	* @param       
	* @return void
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public void deleteById(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			TemplateDownloadService.deleteByPrimaryKey(id[i]);
		}
	}
	
	/**
	 * 
	* @Title: getIndexList
	* @author tianzhiqiang
	* @date 2017-1-6 上午10:05:08  
	* @Description: 首页展示模板
	* @param @param page
	* @param @param TemplateDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping(value = "/getIndexList", produces = "text/html;charset=UTF-8")
	public String getIndexList(Integer page,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<>();
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()!=null && !TemplateDownload.getName().equals("")){
				 try {
						TemplateDownload.setName(new String(TemplateDownload.getName().getBytes("ISO-8859-1"),"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				map.put("name", TemplateDownload.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<TemplateDownload> list = TemplateDownloadService.findPublishedDataByCondition(map);
		if(list.size()==0){
			model.addAttribute("notData", "暂无数据");
		}
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (TemplateDownload a : list) {
	      num++;
	      groupUpload = groupUpload.append("data_secret_show" + num + ",");
	      groupShow = groupShow.append("data_secret_show" + num + ",");
	      a.setGroupsUpload("data_secret_show" + num);
	      a.setGroupShow("data_secret_show" + num);
	    }
	    String groupUploadId = "";
	    String groupShowId = "";
	    if (!"".equals(groupUpload.toString())) {
	      groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length() - 1);
	    }
	    if (!"".equals(groupShow.toString())) {
	      groupShowId = groupShow.toString().substring(0, groupShow.toString().length() - 1);
	    }
	    for (TemplateDownload act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
	   
		model.addAttribute("list", new PageInfo<TemplateDownload>(list));
		model.addAttribute("data", TemplateDownload);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
	    if (dlist.size() > 0) {
	      model.addAttribute("dataTypeId", dlist.get(0).getId());
	    }
	    Map<String,Object> indexMapper = articleService.topNews();
	    model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/templateDownload/template_list";
	}
	
	/**
	 * 
	* @Title: publish
	* @author tianzhiqiang
	* @date 2017-1-6 上午10:43:27  
	* @Description: 发布 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/publish")
	public String publish(@CurrentUser User user,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()==null||TemplateDownload.getName().trim().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能为空");
			}else if(TemplateDownload.getName()!=null&&TemplateDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能超过200个文字");
			}
		}
//		String ipAddress = null;
		//String[] ipAddressType = request.getParameterValues("ipAddressType");
//		if(ipAddressType==null){
//			flag = false;
//			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
//		}else{
//			ipAddress = ipAddressType[0];
//		}
		String id = request.getParameter("id");
		List<UploadFile> zlfj = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
		if(zlfj.size()<1){
			flag = false;
			model.addAttribute("ERR_dataFile", "请上传附件");
		}
		if(TemplateDownload.getIpAddressType()==null){
			flag = false;
			model.addAttribute("ERR_IpAddressType", "请选择发布范围");
		}
		if(TemplateDownload.getIpAddressType()!=null&&"".equals(TemplateDownload.getIpAddressType())){
			flag = false;
			model.addAttribute("ERR_IpAddressType", "请选择发布范围");
		}
		if(flag==false){
			if(TemplateDownload != null && TemplateDownload.getName() != null){
				TemplateDownload.setName(TemplateDownload.getName().trim());
			}
			model.addAttribute("dataId", id);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
		    model.addAttribute("data", TemplateDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/templateDownload/add";
		}
		TemplateDownload.setName(TemplateDownload.getName().trim());
		TemplateDownload.setCreatedAt(new Date());
		TemplateDownload.setPublishAt(new Date());
		TemplateDownload.setIsDeleted(0);
		TemplateDownload.setStatus(2);
		TemplateDownload.setUpdatedAt(new Date());
		TemplateDownload.setUserId(user.getId());
		TemplateDownloadService.insertSelective(TemplateDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: publishData
	* @author tianzhiqiang
	* @date 2017-1-6 下午12:09:24  
	* @Description: 发布模板
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/publishData")
	@ResponseBody
	public void publishData(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			TemplateDownload TemplateDownload = new TemplateDownload();
			TemplateDownload.setId(id[i]);
			TemplateDownload.setStatus(2);
			TemplateDownload.setUpdatedAt(new Date());
			TemplateDownload.setPublishAt(new Date());
			TemplateDownloadService.updateByPrimaryKeySelective(TemplateDownload);
		}
	}
	
	/**
	 * 
	* @Title: publishCancel
	* @author tianzhiqiang
	* @date 2017-1-6 下午12:14:14  
	* @Description: 取消发布判断 
	* @param       
	* @return void
	 */
	@RequestMapping("/publishCancel")
	@ResponseBody
	public String publishCancel(HttpServletRequest request){
		String str = null;
		String[] id = request.getParameter("id").split(",");
		List<TemplateDownload> list = new ArrayList<>();
		for(int i=0;i<id.length;i++){
			TemplateDownload TemplateDownload = TemplateDownloadService.selectByPrimaryKey(id[i]);
			list.add(TemplateDownload);
		}
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStatus()==1||list.get(i).getStatus()==3){
				str = "0";
				break;
			}else if(i==list.size()-1){
				str = "1";
			}
		}
		return str;
	}
	
	/**
	 * 
	* @Title: cancelPublish
	* @author tianzhiqiang
	* @date 2017-1-6 下午12:23:23  
	* @Description: 取消发布模板
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/cancelPublish")
	@ResponseBody
	public void cancelPublish(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			TemplateDownload TemplateDownload = new TemplateDownload();
			TemplateDownload.setId(id[i]);
			TemplateDownload.setUpdatedAt(new Date());
			TemplateDownload.setStatus(3);
			TemplateDownload.setPublishAt(null);
			TemplateDownloadService.updateByPrimaryKeySelective(TemplateDownload);
		}
	}
	
	/**
	 * 
	* @Title: editData
	* @author tianzhiqiang
	* @date 2017-1-5 下午9:52:53  
	* @Description: 修改并暂存模板
	* @param @param TemplateDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editData")
	public String editData(@CurrentUser User user,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()==null||TemplateDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能为空");
			}else if(TemplateDownload.getName()!=null&&TemplateDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能超过200个文字");
			}
		}
		//String ipAddress = null;
		//String[] ipAddressType = request.getParameterValues("ipAddressType");
//		if(ipAddressType==null){
//			flag = false;
//			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
//		}else{
//			ipAddress = ipAddressType[0];
//		}
		String id = request.getParameter("id");
		List<UploadFile> zlfj = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
		if(zlfj.size()<1){
			flag = false;
			model.addAttribute("ERR_dataFile", "请上传附件");
		}
		if(flag==false){
			model.addAttribute("dataId", id);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
		    model.addAttribute("data", TemplateDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/templateDownload/edit";
		}
		TemplateDownload.setUpdatedAt(new Date());
		TemplateDownloadService.updateByPrimaryKeySelective(TemplateDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: editPublish
	* @author tianzhiqiang
	* @date 2017-1-6 上午10:43:27  
	* @Description: 修改模板页面发布 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPublish")
	public String editPublish(@CurrentUser User user,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()==null||TemplateDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能为空");
			}else if(TemplateDownload.getName()!=null&&TemplateDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "模板名称不能超过200个文字");
			}
		}
//		String ipAddress = null;
//		String[] ipAddressType = request.getParameterValues("ipAddressType");
//		if(ipAddressType==null){
//			flag = false;
//			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
//		}else{
//			ipAddress = ipAddressType[0];
//		}
		String id = request.getParameter("id");
		List<UploadFile> zlfj = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
		if(zlfj.size()<1){
			flag = false;
			model.addAttribute("ERR_dataFile", "请上传附件");
		}
		if(flag==false){
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
		    model.addAttribute("data", TemplateDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/templateDownload/edit";
		}
		TemplateDownload.setPublishAt(new Date());
		TemplateDownload.setStatus(2);
		TemplateDownload.setUpdatedAt(new Date());
		TemplateDownloadService.updateByPrimaryKeySelective(TemplateDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: judgeDelete
	* @author tianzhiqiang
	* @date 2017-1-8 下午8:24:55  
	* @Description: 判断能不能删除 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/judgeDelete")
	@ResponseBody
	public String judgeDelete(HttpServletRequest request){
		String str = null;
		String[] id = request.getParameter("id").split(",");
		List<TemplateDownload> list = new ArrayList<>();
		for(int i=0;i<id.length;i++){
			TemplateDownload TemplateDownload = TemplateDownloadService.selectByPrimaryKey(id[i]);
			list.add(TemplateDownload);
		}
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStatus()==2){
				str = "0";
				break;
			}else if(i==list.size()-1){
				str = "1";
			}
		}
		return str;
	}
	
	
	
	
	/**
	 * 
	* @Title: getTemplateList
	* @author tian zhiqiang 
	* @date 2017-3-22 上午10:05:08  
	* @Description: 首页模板下载
	* @param @param page
	* @param @param TemplateDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getTemplateList")
	public String getTemplateList(Integer page,TemplateDownload TemplateDownload,Model model,HttpServletRequest request){
		
		HashMap<String,Object> map = new HashMap<>();
		if(TemplateDownload!=null){
			if(TemplateDownload.getName()!=null && !TemplateDownload.getName().equals("")){
				map.put("name", TemplateDownload.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<TemplateDownload> list = TemplateDownloadService.findPublishedDataByCondition(map);
		if(list.size()==0){
			model.addAttribute("notData", "暂无数据");
		}
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (TemplateDownload a : list) {
	      num++;
	      groupUpload = groupUpload.append("data_secret_show" + num + ",");
	      groupShow = groupShow.append("data_secret_show" + num + ",");
	      a.setGroupsUpload("data_secret_show" + num);
	      a.setGroupShow("data_secret_show" + num);
	    }
	    String groupUploadId = "";
	    String groupShowId = "";
	    if (!"".equals(groupUpload.toString())) {
	      groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length() - 1);
	    }
	    if (!"".equals(groupShow.toString())) {
	      groupShowId = groupShow.toString().substring(0, groupShow.toString().length() - 1);
	    }
	    for (TemplateDownload act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
		
		model.addAttribute("list", new PageInfo<TemplateDownload>(list));
		model.addAttribute("data", TemplateDownload);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
	    if (dlist.size() > 0) {
	      model.addAttribute("dataTypeId", dlist.get(0).getId());
	    }
	    Map<String,Object> indexMapper = articleService.topNews();
	    model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/templateDownload/index_list";
	}
}
