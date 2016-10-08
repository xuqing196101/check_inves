
package bss.controller.pqims;


import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ses.util.PathUtil;


import com.github.pagehelper.PageInfo;

import bss.model.pqims.PqInfo;
import bss.service.pqims.PqInfoService;

/**
 * @Title:PqInfoController 
 * @Description:质检信息管理控制类 
 * @author Liyi
 * @date 2016-9-19上午9:50:25
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/pqinfo")
public class PqInfoController {
	@Resource
	private PqInfoService pqInfoService;
	
	
	/**
	 * 
	 * @Title: getAll
	 * @author Liyi 
	 * @date 2016-9-19 上午9:54:15  
	 * @Description:获取质检信息列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model,Integer page){
		List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/list";
	}
	
	/**
	 * 
	 * @Title: add
	 * @author Liyi 
	 * @date 2016-9-19 上午9:55:09  
	 * @Description:跳转至新增编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){	
		return "bss/pqims/pqinfo/add";
	}
	
	/**
	 * 
	 * @Title: save
	 * @author Liyi 
	 * @date 2016-9-19 上午9:56:44  
	 * @Description:保存新增信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,@RequestParam("contract_code") String contract_code,@RequestParam("dateString") String dateString,@RequestParam("attaattach") MultipartFile attaattach,PqInfo pqInfo){
		 if (attaattach.getOriginalFilename() != null && !attaattach.getOriginalFilename().equals("")) {
		 String rootpath = (PathUtil.getWebRoot() +"picUplode/").replace("\\", "/");
	        /** 创建文件夹 */
			File rootfile = new File(rootpath);
			if (!rootfile.exists()) {
				rootfile.mkdirs();
			}
	        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+ "_" + attaattach.getOriginalFilename();
	        String filePath = rootpath+fileName;
	        File file = new File(filePath);
	        try {
	        	attaattach.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        pqInfo.setReport("picUplode/"+fileName);
		 }
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        ParsePosition pos = new ParsePosition(0);
	        Date date = formatter.parse(dateString, pos);
	        pqInfo.setDate(date);
	        
	        pqInfoService.add(pqInfo);
		return "redirect:getAll.html";
	}
	
	/**
	 * 
	 * @Title: edit
	 * @author Liyi 
	 * @date 2016-9-19 上午9:57:51  
	 * @Description:跳转修改编辑页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String id){
		model.addAttribute("pqinfo",pqInfoService.get(id));
		return "bss/pqims/pqinfo/edit";
	}
	
	/**
	 * 
	 * @Title: update
	 * @author Liyi 
	 * @date 2016-9-19 上午9:58:25  
	 * @Description:更新修改信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request,@RequestParam("contract_code") String contract_code,@RequestParam("date") String dateString,@RequestParam("attaattach") MultipartFile attaattach,PqInfo pqInfo){
		 if (attaattach.getOriginalFilename() != null && !attaattach.getOriginalFilename().equals("")) {
			System.out.println(1111111);
			 String rootpath = (PathUtil.getWebRoot() +"picUplode/").replace("\\", "/");
			 /** 创建文件夹 */
			 File rootfile = new File(rootpath);
			 if (!rootfile.exists()) {
				 rootfile.mkdirs();
			 }
			 String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+ "_" + attaattach.getOriginalFilename();
			 String filePath = rootpath+fileName;
			 File file = new File(filePath);
			 try {
				 attaattach.transferTo(file);
			 } catch (IllegalStateException e) {
				 e.printStackTrace();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
			 pqInfo.setReport("picUplode/"+fileName);
		}
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        ParsePosition pos = new ParsePosition(0);
	        Date date = formatter.parse(dateString, pos);
	        pqInfo.setDate(date);
	        
	        pqInfoService.update(pqInfo);
		return "redirect:getAll.html";
	}
	
	
	
	/**
	 * 
	 * @Title: delete
	 * @author Liyi 
	 * @date 2016-9-19 上午10:00:06  
	 * @Description:批量删除质检信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			pqInfoService.delete(str);
		}
		return "redirect:getAll.html";
	}
	
	/**
	 * 
	 * @Title: view
	 * @author Liyi 
	 * @date 2016-9-19 上午10:12:36  
	 * @Description:跳转查看页面
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/view")
	public String view(Model model,String id){
		model.addAttribute("pqinfo",pqInfoService.get(id));
		return "bss/pqims/pqinfo/view";
	}
	
	/**
	 * 
	 * @Title: search
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:07  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/search")
	public String search(Model model,HttpServletRequest request,PqInfo pqInfo,Integer page){
		List<PqInfo> pqInfos = pqInfoService.selectByCondition(pqInfo,page==null?1:page);
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/list";
	}
	
	/**
	 * 
	 * @Title: getAllInfo
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:12  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAllResult")
	public String getAllResult(Model model,Integer page){
		List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/resultList";
	}
	
	/**
	 * 
	 * @Title: searchResult
	 * @author Liyi 
	 * @date 2016-9-29 下午1:33:07  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/searchResult")
	public String searchResult(Model model,HttpServletRequest request,PqInfo pqInfo,Integer page){
		List<PqInfo> pqInfos = pqInfoService.selectByCondition(pqInfo,page==null?1:page);
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/resultList";
	}
	
	/**
	 * 
	 * @Title: getAllSupplierPqInfo
	 * @author Liyi 
	 * @date 2016-9-29 下午2:19:08  
	 * @Description:
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/getAllSupplierPqInfo")
	public String getAllSupplierPqInfo(Model model,Integer page){
		List<PqInfo> pqInfos = pqInfoService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<PqInfo>(pqInfos));
		return "bss/pqims/pqinfo/list";
	}
}
