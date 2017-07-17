/**
 * 
 */
package bss.controller.dms;

import bss.model.cs.PurchaseContract;
import bss.model.dms.ArchiveBorrow;
import bss.model.dms.ProbationaryArchive;
import bss.model.dms.PurchaseArchive;
import bss.model.ppms.Project;
import bss.service.cs.PurchaseContractService;
import bss.service.dms.ArchiveBorrowServiceI;
import bss.service.dms.ProbationaryArchiveServiceI;
import bss.service.dms.PurchaseArchiveServiceI;
import bss.service.ppms.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseInfo;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:PurchaseArchiveController
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-19上午9:08:12
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseArchive")
public class PurchaseArchiveController extends BaseSupplierController{
	@Autowired
	private PurchaseArchiveServiceI purchaseArchiveService;
	@Autowired
	private PurchaseServiceI purchaseService;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ArchiveBorrowServiceI archiveBorrowService;
	@Autowired
	private DownloadService downloadService;
	@Autowired
	private ProbationaryArchiveServiceI probationaryArchiveService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PurchaseContractService contractService;

	@Autowired
	private OrgnizationServiceI orgnizationServiceI;

	
	/**
	 * 
	 * @Title: archiveList
	 * @author ZhaoBo
	 * @date 2016-10-19 下午2:42:46  
	 * @Description: 采购档案列表页面 
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/archiveList")
	public String archiveList(Model model,HttpServletRequest request,Integer page){
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		String name = request.getParameter("name");
		if(name!=null&&!name.equals("")){
			map.put("name", name);
		}
		String archiveCode = request.getParameter("archiveCode");
		if(archiveCode!=null&&!archiveCode.equals("")){
			map.put("code", archiveCode);
		}
		String contractCode = request.getParameter("contractCode");
		if(contractCode!=null&&!contractCode.equals("")){
			map.put("contractCode", contractCode);
		}
		//		String planCode = request.getParameter("planCode");
		//		if(planCode!=null&&!planCode.equals("")){
		//			map.put("planCode", planCode);
		//		}
		String status = request.getParameter("status");
		if(status!=null&&!status.equals("")){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseArchive> archiveList = purchaseArchiveService.selectArchiveList(map);
		model.addAttribute("archiveList", new PageInfo<PurchaseArchive>(archiveList));
		model.addAttribute("name", name);
		model.addAttribute("archiveCode", archiveCode);
		model.addAttribute("contractCode", contractCode);
		//model.addAttribute("planCode", planCode);
		model.addAttribute("status", status);
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		return "bss/dms/purchaseArchive/list";
	}

	/**
	 * 
	 * @Title: queryArchive
	 * @author ZhaoBo
	 * @date 2016-10-19 下午2:42:49  
	 * @Description: 采购档案查询页面 
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/queryArchive")
	public String queryArchive(Model model,HttpServletRequest request,Integer page){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String name = request.getParameter("name");
		if(name!=null&&!name.equals("")){
			map.put("name", name);
		}
		String archiveCode = request.getParameter("archiveCode");
		if(archiveCode!=null&&!archiveCode.equals("")){
			map.put("archiveCode", archiveCode);
		}
		String contractCode = request.getParameter("contractCode");
		if(contractCode!=null&&!contractCode.equals("")){
			map.put("contractCode", contractCode);
		}
		String year = request.getParameter("year");
		if(year!=null&&!year.equals("")){
			map.put("year",year);
		}
		String purchaseDep = request.getParameter("purchaseDep");
		if(purchaseDep!=null&&!purchaseDep.equals("")){
			map.put("purchaseDep",purchaseDep);
		}
		//根据类型查询数据字典集合
		List<DictionaryData> data = DictionaryDataUtil.find(5);
		String purchaseType = request.getParameter("purchaseType");
		if(purchaseType!=null&&!purchaseType.equals("")){
			for(int i=0;i<data.size();i++){
				if(data.get(i).getName().equals(purchaseType)){
					map.put("purchaseType",data.get(i).getId());
					break;
				}
			}
		}
		String supplierName = request.getParameter("supplierName");
		if(supplierName!=null&&!supplierName.equals("")){
			map.put("supplierName",supplierName);
		}
		String status = request.getParameter("status");
		if(status!=null&&!status.equals("")){
			map.put("status", status);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseArchive> archiveList = purchaseArchiveService.selectArchiveList(map);
		for (PurchaseArchive purchaseArchive : archiveList) {
            Project project = projectService.selectById(purchaseArchive.getProjectId());
            if(project != null){
                purchaseArchive.setReportAt(project.getApprovalTime());
                purchaseArchive.setApplyAt(project.getReplyTime());
            }
            PurchaseContract contract = contractService.selectById(purchaseArchive.getContractId());
            if(contract != null){
                purchaseArchive.setFormalGitAt(contract.getFormalGitAt());
                purchaseArchive.setDraftGitAt(contract.getDraftGitAt());
                purchaseArchive.setDraftReviewedAt(contract.getDraftReviewedAt());
                purchaseArchive.setFormalReviewedAt(contract.getFormalReviewedAt());
            }
        }
		model.addAttribute("archiveList", new PageInfo<PurchaseArchive>(archiveList));
		model.addAttribute("name", name);
		model.addAttribute("archiveCode", archiveCode);
		model.addAttribute("contractCode", contractCode);
		model.addAttribute("year", year);
		model.addAttribute("purchaseDep", purchaseDep);
		model.addAttribute("purchaseType", purchaseType);
		model.addAttribute("supplierName", supplierName);
		model.addAttribute("status", status);
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		return "bss/dms/purchaseArchive/query_archive";
	}

	/**
	 * 
	 * @Title: archiveAuthorize
	 * @author ZhaoBo
	 * @date 2016-10-19 下午3:05:27  
	 * @Description: 采购档案授权 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/archiveAuthorize")
	public String archiveAuthorize(Integer page,HttpServletRequest request,Model model){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String name = request.getParameter("name");
		String depName = request.getParameter("depName");
		if(name!=null&&!name.equals("")){
			map.put("relName",name);
		}
		if(depName!=null&&!depName.equals("")){
			map.put("purchaseDepId",depName);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseInfo> purList = purchaseService.findPurchaseList(map);
		// 查询所有采购机构
        List<Orgnization> purchaseOrgList = orgnizationServiceI.findPurchaseOrgByPosition(null);
        model.addAttribute("name", name);
		model.addAttribute("depName", depName);
		model.addAttribute("purchaseList", new PageInfo<PurchaseInfo>(purList));
		model.addAttribute("purchaseOrgList", purchaseOrgList);
		return "bss/dms/purchaseArchive/authorize";
	}

	/**
	 * 
	 * @Title: add
	 * @author ZhaoBo
	 * @date 2016-10-25 下午3:55:05  
	 * @Description: 采购档案录入 
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/add")
	public String add(Integer page,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		//查询预备表里的所有数据
		List<ProbationaryArchive> contract = probationaryArchiveService.selectAll(map);
		//
		List<ProbationaryArchive> result= new ArrayList<ProbationaryArchive>();
		for(int j=0;j<contract.size();j++){
			result.add(contract.get(j));
		}
		List<PurchaseArchive> archive = purchaseArchiveService.selectArchiveList(null);
		if(archive.size()!=0){
			for(int j=0;j<contract.size();j++){
				ProbationaryArchive obj = contract.get(j);
				String id = obj.getId();
				for(int i=0;i<archive.size();i++){
					if(id.equals(archive.get(i).getPurchaseContractId())){
						result.remove(contract.get(j));			
					}
				}
			}
		}
							
		model.addAttribute("contract", new PageInfo<ProbationaryArchive>(result));
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		return "bss/dms/purchaseArchive/add";
	}

	/**
	 * 
	 * @Title: auditArchive
	 * @author ZhaoBo
	 * @date 2016-10-25 下午4:13:15  
	 * @Description: 审核档案 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/auditArchive")
	@ResponseBody
	public String auditArchive(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id);
		if(archive.getStatus()==5){
			str = "0";
		}else{
			str = "1";
		}
		return str;
	}

	/**
	 * 
	 * @Title: auditGitArchive
	 * @author ZhaoBo
	 * @date 2016-10-28 下午2:14:36  
	 * @Description: 审核已提交的档案 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/auditGitArchive")
	public String auditGitArchive(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id);
		model.addAttribute("archive", archive);
		return "bss/dms/purchaseArchive/audit";
	}

	/**
	 * 
	 * @Title: judgeArchive
	 * @author ZhaoBo
	 * @date 2016-11-7 上午10:16:50  
	 * @Description: 判断合同有没有生成采购档案 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/judgeArchive")
	@ResponseBody
	public String judgeArchive(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		List<PurchaseArchive> archive = purchaseArchiveService.selectArchiveList(null);
		if(archive.size()==0){
			str = "0";
		}else{
			for(int i=0;i<archive.size();i++){
				if(id.equals(archive.get(i).getPurchaseContractId())){
					str = "1";
					break;
				}else if(i==archive.size()-1){
					str = "0";
				}
			}
		}
		return str;
	}

	/**
	 * 
	 * @Title: addArchive
	 * @author ZhaoBo
	 * @date 2016-10-26 上午10:00:15  
	 * @Description: 生成采购档案 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/addArchive")
	@ResponseBody
	public String addArchive(HttpServletRequest request){
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String str = "无";
		Map<String,Object> map = new HashMap<String,Object>();
		if(name==null||name.trim().equals("")){
			str = "error";
			map.put("name", "档案名称不能为空");
		}else{
			HashMap<String,Object> mapName = new HashMap<String,Object>();
			mapName.put("name", name.trim());
			List<PurchaseArchive> archive = purchaseArchiveService.selectArchiveList(mapName);
			if(archive.size()!=0){
				str = "error";
				map.put("name", "档案名称已存在");
			}
		}
		if(code==null||code.trim().equals("")){
			str = "error";
			map.put("code", "档案编号不能为空");
		}else{
			HashMap<String,Object> mapCode = new HashMap<String,Object>();
			mapCode.put("code", code.trim());
			List<PurchaseArchive> archive = purchaseArchiveService.selectArchiveList(mapCode);
			if(archive.size()!=0){
				str = "error";
				map.put("name", "档案编号已存在");
			}
		}
		if(str.equals("error")){
			return JSONSerializer.toJSON(map).toString();
		}else{
			String id = request.getParameter("id");
			ProbationaryArchive archive = probationaryArchiveService.selectById(id);
			PurchaseArchive file = new PurchaseArchive();
			file.setName(name.trim());
			file.setCode(code.trim());
			file.setPurchaseContractId(id);                                                                                
			file.setContractCode(archive.getContractCode());
			file.setIsDeleted(0);
			file.setProjectCode(archive.getProjectCode());
			file.setProductName(archive.getProductName());
			file.setStatus(1);
			file.setSupplierName(archive.getSupplierName());
			file.setYear(archive.getYear());
			file.setPurchaseDep(archive.getPurchaseDep());
			file.setPurchaseType(archive.getPurchaseType());
			file.setCreatedAt(new Date());
			file.setProjectId(archive.getProjectId());
			purchaseArchiveService.insertSelective(file);
			return "1";
		}

	}

	/**
	 * 
	 * @Title: placeArchiveById
	 * @author ZhaoBo
	 * @date 2016-10-26 下午2:44:10  
	 * @Description: 归档 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/placeArchiveById")
	@ResponseBody
	public String placeArchiveById(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id);
		if(archive.getStatus()==2){
			str = "0";
		}else{
			str = "1";
		}
		return str;
	}

	/**
	 * 
	 * @Title: passArchive
	 * @author ZhaoBo
	 * @date 2016-10-28 下午12:57:48  
	 * @Description: 审核通过 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/passArchive")
	public String passArchive(HttpServletRequest request){
		String id = request.getParameter("id");
		PurchaseArchive archive = new PurchaseArchive();
		archive.setId(id);
		archive.setStatus(2);
		purchaseArchiveService.updateByPrimaryKeySelective(archive);
		return "redirect:archiveList.html";
	}

	/**
	 * 
	 * @Title: gitArchiveById
	 * @author ZhaoBo
	 * @date 2016-10-28 下午1:26:44  
	 * @Description: 提交档案 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/gitArchiveById")
	@ResponseBody
	public String gitArchiveById(HttpServletRequest request){
		String str = null;
		String[] id = request.getParameter("id").split(",");
		List<PurchaseArchive> archiveList = new ArrayList<PurchaseArchive>();
		for(int i=0;i<id.length;i++){
			PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id[i]);
			archiveList.add(archive);
		}
		for(int i=0;i<archiveList.size();i++){
			if(archiveList.get(i).getStatus()==2||archiveList.get(i).getStatus()==4||archiveList.get(i).getStatus()==5){
				str = "1";
				break;
			}else if(i==archiveList.size()-1){
				str = "0";
			}
		}
		return str;
	}

	/**
	 * 
	 * @Title: gitStatus
	 * @author ZhaoBo
	 * @date 2016-10-28 下午1:47:35  
	 * @Description: 修改为已提交状态 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/gitStatus")
	public String gitStatus(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			PurchaseArchive archive = new PurchaseArchive();
			archive.setId(id[i]);
			archive.setStatus(5);
			purchaseArchiveService.updateByPrimaryKeySelective(archive);
		}
		return "redirect:archiveList.html";
	}

	/**
	 * 
	 * @Title: leadArchive
	 * @author ZhaoBo
	 * @date 2016-10-28 下午3:56:02  
	 * @Description: 归档档案 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/leadArchive")
	public void leadArchive(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String str = "无";
		Map<String,Object> map = new HashMap<String,Object>();
		if(type==null||type.equals("")){
			str = "error";
			map.put("type", "请选择档案类型");
		}else{
			PurchaseArchive archive = new PurchaseArchive();
			archive.setId(id);
			archive.setStatus(4);
			archive.setType(Integer.parseInt(type));
			purchaseArchiveService.updateByPrimaryKeySelective(archive);
		}
		if(str.equals("error")){
			JSONObject object = JSONObject.fromObject(map);
			super.writeJson(response, object.toString());
		}else{
			super.writeJson(response, 1);
		}
	}

	/**
	 * 
	 * @Title: backReason
	 * @author ZhaoBo
	 * @date 2016-10-29 下午12:55:44  
	 * @Description:  审核的时候退回
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backReason")
	public String backReason(HttpServletRequest request){
		String id = request.getParameter("id");
		String reason = request.getParameter("reason");
		PurchaseArchive archive = new PurchaseArchive();
		archive.setId(id);
		archive.setStatus(3);
		archive.setReason(reason);
		purchaseArchiveService.updateByPrimaryKeySelective(archive);
		return "redirect:archiveList.html";
	}

	/**
	 * 
	 * @Title: backArchive
	 * @author ZhaoBo
	 * @date 2016-11-4 下午3:16:02  
	 * @Description: 返回采购档案列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/backArchive")
	public String backArchive(){
		return "redirect:archiveList.html";
	}

	/**
	 * 
	 * @Title: judgeFileByName
	 * @author ZhaoBo
	 * @date 2016-11-8 上午11:07:17  
	 * @Description: 判断输入的档案名称是否正确
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/judgeFileByName")
	@ResponseBody
	public String judgeFileByName(HttpServletRequest request){
		String str = null;
		String name = request.getParameter("name");
		List<PurchaseArchive> archive = purchaseArchiveService.findArchiveByName(name.trim());
		if(archive.size()==0){
			str = "0";
		}else{
			str = "1";
		}
		return str;
	}

	/**
	 * 
	 * @Title: authFileToPeople
	 * @author ZhaoBo
	 * @date 2016-11-10 下午1:16:57  
	 * @Description: 勾选档案进行授权 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/authFileToPeople")
	@ResponseBody
	public List<UploadFile> authFileToPeople(HttpServletRequest request,HttpServletRequest response){
		String id = request.getParameter("id");//要授权的档案
		String userId = request.getParameter("userId");//要授权的采购人
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("archiveId", id);
		List<ArchiveBorrow> fileList = archiveBorrowService.findArchiveById(map);
		PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id);
		List<UploadFile> uploadFiles = uploadService.getFilesOther(archive.getProjectId(), null, "2");
		if(fileList.size()!=0){
			for(int i=0;i<uploadFiles.size();i++){
				for(int j=0;j<fileList.size();j++){
					if(uploadFiles.get(i).getId().equals(fileList.get(j).getAttachmentId())){
						uploadFiles.get(i).setStatus(1);
						break;
					}
				}
			}
		}
		return uploadFiles;
	}

	/**
	 * 
	 * @Title: saveFile
	 * @author ZhaoBo
	 * @date 2016-11-21 上午8:58:41  
	 * @Description: 保存设置 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/saveFile")
	@ResponseBody
	public String saveFile(HttpServletRequest request){
		String id = request.getParameter("id");//要授权的档案
		String userId = request.getParameter("userId");//要授权的采购人
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("archiveId", id);
		List<ArchiveBorrow> fileList = archiveBorrowService.findArchiveById(map);
		if(fileList.size()!=0){
			for(int i=0;i<fileList.size();i++){
				ArchiveBorrow archiveBorrow = new ArchiveBorrow();
				archiveBorrow.setId(fileList.get(i).getId());
				archiveBorrow.setIsDeleted(1);
				archiveBorrowService.updateByPrimaryKeySelective(archiveBorrow);
			}
		}
		String[] fileId = request.getParameter("fileId").split(",");
		if(fileId==null || (fileId!=null&&fileId.length==0) || (fileId.length==1&&fileId[0]=="")) { 

		}else{
			for(int i=0;i<fileId.length;i++){
				UploadFile uploadFile = uploadService.findById(fileId[i],Constant.TENDER_SYS_KEY);
				ArchiveBorrow borrowObject = new ArchiveBorrow();
				borrowObject.setUserId(userId);
				borrowObject.setCreatedAt(new Date());
				borrowObject.setArchiveId(id);
				borrowObject.setIsDeleted(0);
				borrowObject.setAttachmentId(fileId[i]);
				borrowObject.setName(uploadFile.getName());
				borrowObject.setPath(uploadFile.getPath());
				archiveBorrowService.insertSelective(borrowObject);
			}
		}
		return "1";

	}

	/**
	 * 
	 * @Title: viewArchive
	 * @author ZhaoBo
	 * @date 2016-11-10 下午3:48:45  
	 * @Description:  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewArchive")
	public String viewArchive(HttpServletRequest request,Model model,Integer page){
		String id = request.getParameter("id");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", id);
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseArchive> archiveList = purchaseArchiveService.findArchiveByUserId(map);
		if(archiveList.size()!=0){
			model.addAttribute("archiveList",new PageInfo<PurchaseArchive>(archiveList));
		}else{
			model.addAttribute("resultMap", "暂无可浏览的档案");
		}
		model.addAttribute("userId", id);
		return "bss/dms/purchaseArchive/view";
	}

	/**
	 * 
	 * @Title: borrowArchive
	 * @author ZhaoBo
	 * @date 2016-11-11 下午2:19:06  
	 * @Description: 采购人借阅档案 
	 * @param @return
	 * @return String
	 */
	@RequestMapping("/borrowArchive")
	public String borrowArchive(HttpServletRequest request,Model model,HttpServletResponse response){
		String id = request.getParameter("id");
		PurchaseArchive archive = purchaseArchiveService.selectArchiveById(id);
		User user = (User) request.getSession().getAttribute("loginUser");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		map.put("archiveId", id);
		List<ArchiveBorrow> archiveList = archiveBorrowService.findArchiveById(map);
		for(int i=0;i<archiveList.size();i++){
			String name = archiveList.get(i).getName();
			if(name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".png")
					||name.endsWith(".gif")||name.endsWith(".bmp")||name.endsWith(".tiff")
					||name.endsWith(".ai")||name.endsWith(".cdr")||name.endsWith(".eps")){
				archiveList.get(i).setStatus(1);
			}else{
				archiveList.get(i).setStatus(0);
			}
		}
		model.addAttribute("archiveList", archiveList);
		model.addAttribute("archive",archive);
		model.addAttribute("user", user);
		return "bss/dms/purchaseArchive/borrow";
	}

	/**
	 * 
	 * @Title: judgeBorrow
	 * @author ZhaoBo
	 * @date 2016-11-14 下午12:32:46  
	 * @Description:  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/judgeBorrow")
	@ResponseBody
	public String judgeBorrow(HttpServletRequest request){
		String str = null;
		String id = request.getParameter("id");
		User user = (User) request.getSession().getAttribute("loginUser");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getId());
		map.put("archiveId", id);
		List<ArchiveBorrow> archiveList = archiveBorrowService.findArchiveById(map);
		if(archiveList.size()==0){
			str = "1";
		}else{
			str = "0";
		}
		return str;
	}

	/**
	 * 
	 * @Title: jumpToAuthorize
	 * @author ZhaoBo
	 * @date 2016-11-11 下午3:47:12  
	 * @Description: 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/jumpToAuthorize")
	public String jumpToAuthorize(HttpServletRequest request,Model model,Integer page){
		String id = request.getParameter("userId");
		HashMap<String,Object> map = new HashMap<String,Object>();
		String name = request.getParameter("name");
		if(name!=null&&!name.equals("")){
			map.put("name", name);
		}
		String code = request.getParameter("code");
		if(code!=null&&!code.equals("")){
			map.put("code", code);
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<PurchaseArchive> archiveList = purchaseArchiveService.selectArchiveList(map);
		model.addAttribute("archiveList", new PageInfo<PurchaseArchive>(archiveList));
		model.addAttribute("name", name);
		model.addAttribute("code", code);
		model.addAttribute("id", id);
		model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
		return "bss/dms/purchaseArchive/authorize_archive";
	}

	/**
	 * 
	 * @Title: viewAppointed
	 * @author ZhaoBo
	 * @date 2016-11-13 上午9:27:25  
	 * @Description: 查看某人指定档案可以浏览的扫描件 
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/viewAppointed")
	@ResponseBody
	public List<ArchiveBorrow> viewAppointed(HttpServletRequest request){
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("archiveId", id);
		List<ArchiveBorrow> borrowList = archiveBorrowService.findArchiveById(map);
		return borrowList;
	}

	/**
	 * 
	 * @Title: download
	 * @author ZhaoBo
	 * @date 2016-11-13 上午9:51:28  
	 * @Description: 下载 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		downloadService.downloadOther(request, response, id, "2");
	}
}
