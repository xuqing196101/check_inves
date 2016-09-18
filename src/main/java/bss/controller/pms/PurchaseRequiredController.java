package bss.controller.pms;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;
import bss.util.ExcelUtil;
/**
 * 
 * @Title: PurcharseRequiredController
 * @Description:  采购需求计划类
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:54:34
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaser")
public class PurchaseRequiredController extends BaseController{

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	 
	/**
	 * 
	* @Title: queryPlan
	* @Description: 条件查询分页
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(PurchaseRequired purchaseRequired,Integer page,Model model){
		
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "bss/pms/purchaserequird/list";
	}
	/**
	 * 
	* @Title: getById
	* @Description: 根据计划编号查询明细
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/queryByNo")
	public String getById(String planNo,Model model){
		PurchaseRequired p=new PurchaseRequired();
		p.setPlanNo(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.query(p,0);
		model.addAttribute("list", list);
		return "bss/pms/purchaserequird/edit";
	}
	
	/**
	 * 
	* @Title: updateById
	* @Description: 根据id修改 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list){
		
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for( PurchaseRequired p:list.getList()){
					if( p.getId()!=null){
						purchaseRequiredService.update(p);
						if(p.getParentId()!=null){
							p.setParentId(p.getParentId());
						}
					
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						p.setId(id);
						Integer s=Integer.valueOf(purchaseRequiredService.queryByNo(p.getPlanNo()))+1;
						p.setHistoryStatus(String.valueOf(s));
						purchaseRequiredService.add(p);	
					}
				
					
				}
			}
		}
//		purchaseRequiredService.update(purchaseRequired);
		return "redirect:/list.html";
	}
	/**
	 *   
	 * 
	* @Title: add
	* @Description: 添加跳转页面
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws  
	 */
	@RequestMapping("/add")
	public String add(Model model,String type) {
		model.addAttribute("type", type);
		return "bss/pms/purchaserequird/add";
	}
	/**
	 *   
	 * 
	* @Title: uploadFile
	* @Description: 导入excel表格数据
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response,String type) throws Exception{
		response.setContentType("text/xml;charset=UTF-8");  
		String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
        if(!fileName.endsWith(".xlsx")||!fileName.endsWith(".xlsx")){
         
        	return "ERROR";
        }
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
		List<PurchaseRequired> list = (List<PurchaseRequired>) ExcelUtil.readExcel(targetFile);
		for(PurchaseRequired r:list){
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			r.setId(id);
			r.setPlanType(type);
			purchaseRequiredService.add(r);
		}
		System.out.println(list.size());
		System.out.println(type+"-----------------------");
		targetFile.delete();
		
		return "上传成功";
	}
	/**
	 * 
	* @Title: addReq
	* @Description: 添加数据
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/adddetail")
	@ResponseBody
	public String addReq(PurchaseRequiredFormBean list,String type,String planNo,String planName){
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for(PurchaseRequired p:list.getList()){
					if(p!=null){
				 
							String id = UUID.randomUUID().toString().replaceAll("-", "");
							p.setGoodsType(type);
							p.setPlanNo(planNo);
							p.setPlanName(planName);
							p.setId(id);
							p.setPlanType(type);
							p.setHistoryStatus("0");
							purchaseRequiredService.add(p);	
						 
						
					}

			}
		}
	 
	}

		return "redirect:/list.html";
	}
	
	
	
	
}
