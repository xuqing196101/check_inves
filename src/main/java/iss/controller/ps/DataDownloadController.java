/**
 * 
 */
package iss.controller.ps;


import iss.model.ps.DataDownload;
import iss.service.ps.ArticleService;
import iss.service.ps.DataDownloadService;

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
 * @Title:DataDownloadController
 * @Description: 资料下载控制类
 * @author ZhaoBo
 * @date 2017-1-5上午10:01:56
 */
@Controller
@RequestMapping("/dataDownload")
public class DataDownloadController {
	/** 资料下载service */
	@Autowired
	private DataDownloadService dataDownloadService;
	
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
	* @author ZhaoBo
	* @date 2017-1-5 下午5:07:33  
	* @Description: 资料下载列表 
	* @param @param page
	* @param @param dataDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getList")
	public String getList(Integer page,DataDownload dataDownload,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<>();
		if(dataDownload!=null){
			if(dataDownload.getName()!=null && !dataDownload.getName().equals("")){
				map.put("name", dataDownload.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<DataDownload> list = dataDownloadService.findDataByCondition(map);
		
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (DataDownload a : list) {
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
	    for (DataDownload act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
		
		
		model.addAttribute("list", new PageInfo<DataDownload>(list));
		model.addAttribute("name", request.getParameter("name"));
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
	    if (dlist.size() > 0) {
	      model.addAttribute("dataTypeId", dlist.get(0).getId());
	    }
		return "iss/ps/dataDownload/list";
	}
	
	/**
	 * 
	* @Title: add
	* @author ZhaoBo
	* @date 2017-1-5 下午5:07:49  
	* @Description: 新增 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model){
		String dataId = UUID.randomUUID().toString().replaceAll("-", "");
		model.addAttribute("dataId", dataId);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
	    if (list.size() > 0) {
	      model.addAttribute("dataTypeId", list.get(0).getId());
	    }
		return "iss/ps/dataDownload/add";
	}
	
	/**
	 * 
	* @Title: save
	* @author ZhaoBo
	* @date 2017-1-5 下午9:52:53  
	* @Description: 保存资料 
	* @param @param dataDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(@CurrentUser User user,DataDownload dataDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(dataDownload!=null){
			if(dataDownload.getName()==null||dataDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能为空");
			}else if(dataDownload.getName()!=null&&dataDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能超过200个文字");
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
		if(flag==false){
			model.addAttribute("dataId", id);
			model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
			DictionaryData dataFile = new DictionaryData();
		    dataFile.setCode("ZLFJ");
		    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
		    if (list.size() > 0) {
		      model.addAttribute("dataTypeId", list.get(0).getId());
		    }
		    model.addAttribute("data", dataDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/dataDownload/add";
		}
		//dataDownload.setIpAddressType(Integer.parseInt(ipAddressType[0]));
		dataDownload.setCreatedAt(new Date());
		dataDownload.setIsDeleted(0);
		dataDownload.setStatus(1);
		dataDownload.setUpdatedAt(new Date());
		dataDownload.setUserId(user.getId());
		dataDownloadService.insertSelective(dataDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: view
	* @author ZhaoBo
	* @date 2017-1-5 下午9:44:53  
	* @Description: 查看 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		DataDownload dataDownload = dataDownloadService.selectByPrimaryKey(id);
		model.addAttribute("data", dataDownload);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> list = dictionaryDataServiceI.find(dataFile);
	    if (list.size() > 0) {
	      model.addAttribute("dataTypeId", list.get(0).getId());
	    }
		return "iss/ps/dataDownload/view";
	}
	
	/**
	 * 
	* @Title: judgeEdit
	* @author ZhaoBo
	* @date 2017-1-6 下午12:45:31  
	* @Description: 判断资料能不能修改 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/judgeEdit")
	@ResponseBody
	public String judgeEdit(HttpServletRequest request){
		DataDownload dataDownload = dataDownloadService.selectByPrimaryKey(request.getParameter("id"));
		if(dataDownload.getStatus()==2||dataDownload.getStatus()==3){
			return "0";
		}else{
			return "1";
		}
	}
	
	/**
	 * 
	* @Title: edit
	* @author ZhaoBo
	* @date 2017-1-5 下午9:50:06  
	* @Description: 修改资料 
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
		DataDownload dataDownload = dataDownloadService.selectByPrimaryKey(id);
		model.addAttribute("data", dataDownload);
		return "iss/ps/dataDownload/edit";
	}
	
	/**
	 * 
	* @Title: deleteById
	* @author ZhaoBo
	* @date 2017-1-5 下午10:36:51  
	* @Description: 删除资料 
	* @param       
	* @return void
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public void deleteById(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			dataDownloadService.deleteByPrimaryKey(id[i]);
		}
	}
	
	/**
	 * 
	* @Title: getIndexList
	* @author ZhaoBo
	* @date 2017-1-6 上午10:05:08  
	* @Description: 首页展示资料 
	* @param @param page
	* @param @param dataDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getIndexList")
	public String getIndexList(Integer page,DataDownload dataDownload,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<>();
		if(dataDownload!=null){
			if(dataDownload.getName()!=null && !dataDownload.getName().equals("")){
				map.put("name", dataDownload.getName());
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
		List<DataDownload> list = dataDownloadService.findPublishedDataByCondition(map);
		if(list.size()==0){
			model.addAttribute("notData", "暂无数据");
		}
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (DataDownload a : list) {
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
	    for (DataDownload act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
		
		model.addAttribute("list", new PageInfo<DataDownload>(list));
		model.addAttribute("data", dataDownload);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
	    if (dlist.size() > 0) {
	      model.addAttribute("dataTypeId", dlist.get(0).getId());
	    }
	    Map<String,Object> indexMapper = articleService.topNews();
	    model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/dataDownload/template_list";
	}
	
	/**
	 * 
	* @Title: publish
	* @author ZhaoBo
	* @date 2017-1-6 上午10:43:27  
	* @Description: 发布 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/publish")
	public String publish(@CurrentUser User user,DataDownload dataDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(dataDownload!=null){
			if(dataDownload.getName()==null||dataDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能为空");
			}else if(dataDownload.getName()!=null&&dataDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能超过200个文字");
			}
		}
		if(dataDownload.getIpAddressType()==null){
			flag = false;
			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
		}
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
		    model.addAttribute("data", dataDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/dataDownload/add";
		}
		dataDownload.setCreatedAt(new Date());
		dataDownload.setPublishAt(new Date());
		dataDownload.setIsDeleted(0);
		dataDownload.setStatus(2);
		dataDownload.setUpdatedAt(new Date());
		dataDownload.setUserId(user.getId());
		dataDownloadService.insertSelective(dataDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: publishData
	* @author ZhaoBo
	* @date 2017-1-6 下午12:09:24  
	* @Description: 发布资料 
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/publishData")
	@ResponseBody
	public void publishData(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			DataDownload dataDownload = new DataDownload();
			dataDownload.setId(id[i]);
			dataDownload.setStatus(2);
			dataDownload.setUpdatedAt(new Date());
			dataDownload.setPublishAt(new Date());
			dataDownloadService.updateByPrimaryKeySelective(dataDownload);
		}
	}
	
	/**
	 * 
	* @Title: publishCancel
	* @author ZhaoBo
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
		List<DataDownload> list = new ArrayList<>();
		for(int i=0;i<id.length;i++){
			DataDownload dataDownload = dataDownloadService.selectByPrimaryKey(id[i]);
			list.add(dataDownload);
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
	* @author ZhaoBo
	* @date 2017-1-6 下午12:23:23  
	* @Description: 取消发布资料 
	* @param @param request      
	* @return void
	 */
	@RequestMapping("/cancelPublish")
	@ResponseBody
	public void cancelPublish(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			DataDownload dataDownload = new DataDownload();
			dataDownload.setId(id[i]);
			dataDownload.setUpdatedAt(new Date());
			dataDownload.setStatus(3);
			dataDownload.setPublishAt(null);
			dataDownloadService.updateByPrimaryKeySelective(dataDownload);
		}
	}
	
	/**
	 * 
	* @Title: editData
	* @author ZhaoBo
	* @date 2017-1-5 下午9:52:53  
	* @Description: 修改并暂存资料 
	* @param @param dataDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editData")
	public String editData(@CurrentUser User user,DataDownload dataDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(dataDownload!=null){
			if(dataDownload.getName()==null||dataDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能为空");
			}else if(dataDownload.getName()!=null&&dataDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能超过200个文字");
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
		    model.addAttribute("data", dataDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/dataDownload/edit";
		}
		dataDownload.setUpdatedAt(new Date());
		dataDownloadService.updateByPrimaryKeySelective(dataDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: editPublish
	* @author ZhaoBo
	* @date 2017-1-6 上午10:43:27  
	* @Description: 修改资料页面发布 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPublish")
	public String editPublish(@CurrentUser User user,DataDownload dataDownload,Model model,HttpServletRequest request){
		boolean flag = true;
		if(dataDownload!=null){
			if(dataDownload.getName()==null||dataDownload.getName().equals("")){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能为空");
			}else if(dataDownload.getName()!=null&&dataDownload.getName().length()>200){
				flag = false;
				model.addAttribute("ERR_name", "资料名称不能超过200个文字");
			}
		}
		if(dataDownload.getIpAddressType()==null){
			flag = false;
			model.addAttribute("ERR_ipAddressType", "发布范围不能为空");
		}
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
		    model.addAttribute("data", dataDownload);
		    //model.addAttribute("ipAddressType", ipAddress);
			return "iss/ps/dataDownload/edit";
		}
		dataDownload.setPublishAt(new Date());
		dataDownload.setStatus(2);
		dataDownload.setUpdatedAt(new Date());
		dataDownloadService.updateByPrimaryKeySelective(dataDownload);
		return "redirect:getList.html";
	}
	
	/**
	 * 
	* @Title: judgeDelete
	* @author ZhaoBo
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
		List<DataDownload> list = new ArrayList<>();
		for(int i=0;i<id.length;i++){
			DataDownload dataDownload = dataDownloadService.selectByPrimaryKey(id[i]);
			list.add(dataDownload);
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
	* @param @param dataDownload
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getTemplateList")
	public String getTemplateList(Integer page,DataDownload dataDownload,Model model,HttpServletRequest request){
		
		HashMap<String,Object> map = new HashMap<>();
		if(dataDownload!=null){
			if(dataDownload.getName()!=null && !dataDownload.getName().equals("")){
				map.put("name", dataDownload.getName());
			}
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));
		List<DataDownload> list = dataDownloadService.findPublishedDataByCondition(map);
		if(list.size()==0){
			model.addAttribute("notData", "暂无数据");
		}
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (DataDownload a : list) {
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
	    for (DataDownload act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
		
		model.addAttribute("list", new PageInfo<DataDownload>(list));
		model.addAttribute("data", dataDownload);
		model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
		DictionaryData dataFile = new DictionaryData();
	    dataFile.setCode("ZLFJ");
	    List<DictionaryData> dlist = dictionaryDataServiceI.find(dataFile);
	    if (dlist.size() > 0) {
	      model.addAttribute("dataTypeId", dlist.get(0).getId());
	    }
	    Map<String,Object> indexMapper = articleService.topNews();
	    model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/dataDownload/index_list";
	}
}
