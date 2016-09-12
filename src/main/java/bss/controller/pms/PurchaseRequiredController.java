package bss.controller.pms;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
public class PurchaseRequiredController {

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	 
	/**
	 * 
	* @Title: queryPlan
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(PurchaseRequired purchaseRequired,Model model){
		
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired);
		model.addAttribute("list", list);
		return "purchaserequird/list";
	}
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查看
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/queryById")
	public String getById(String id,Model model){
		PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
		model.addAttribute("purchaseRequired", purchaseRequired);
		return "";
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
	public String updateById(PurchaseRequired purchaseRequired){
		purchaseRequiredService.update(purchaseRequired);
		return "";
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
	public String add() {
		
		return "purchaserequird/add";
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
	public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request) throws Exception{
	
		String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
//        String fileName = new Date().getTime()+".jpg";  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
		
//		System.out.println(file.getOriginalFilename()+"--------------");
//		File targetFile = new File(file); 
		List<PurchaseRequired> list = (List<PurchaseRequired>) ExcelUtil.readExcel(targetFile);
		System.out.println(list.size());
		targetFile.delete();
		
		return "";
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
	@RequestMapping("/addOne")
	public String addReq(PurchaseRequired purchaseRequired){
		
		return "redirect:queryplan";
	}
}
