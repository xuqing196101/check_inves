/**
 * 
 */
package bss.controller.ppms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierQuoteService;
import bss.model.ppms.Packages;
import bss.model.ppms.SaleTender;
import bss.service.ppms.SaleTenderService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;

/**
 * @Description: 发售标书
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月19日下午2:27:04
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleTender")
public class SaleTenderController {
    @Autowired
    private SupplierExtRelateService extRelateService; //关联表
    @Autowired
    private SaleTenderService saleTenderService; //关联表
    @Autowired
    private SupplierAuditService auditService;//查询所有供应商
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;//TypeId
    @Autowired
    private SupplierQuoteService supplierQuoteService;

    /**
     * @Description:展示发售标书列表
     *
     * @author Wang Wenshuai
     * @version 2016年10月19日 下午2:39:16  
     * @param @param prjectId      
     * @return void
     */
    @RequestMapping("/list")
    public String  list(Model model,String projectId, String page, SaleTender saleTender,String supplierName){
        saleTender.setProjectId(projectId);
        Supplier supplier=new Supplier();
        supplier.setSupplierName(supplierName);
        saleTender.setSuppliers(supplier);
        List<SaleTender> list = saleTenderService.list(saleTender,page==null?1:Integer.valueOf(page));
        model.addAttribute("list", new PageInfo<>(list));
        model.addAttribute("projectId", projectId);
        model.addAttribute("saleTender",saleTender);
        model.addAttribute("supplierName",supplierName);
        return "bss/ppms/sall_tender/list";
    }


    /**
     * @Description:展示供应商列表
     *
     * @author Wang Wenshuai
     * @version 2016年10月19日 下午2:41:09  
     * @param @return      
     * @return String
     */
    @RequestMapping("/showSupplier")
    public  String showSupplier(Model model, String projectId,String page,Supplier supplier){
    	//查询list方法里面的供应商id 为了过滤供应商 已经有的就不显示了
    	SaleTender saleTender=new SaleTender();
    	saleTender.setProjectId(projectId);
    	List<SaleTender> list = saleTenderService.list(saleTender,page==null?1:Integer.valueOf(page));
    	List<String> stsupplierIds=new ArrayList<String>();
    	if(list.size()>0){
	    	for(SaleTender st:list){
	    		stsupplierIds.add(st.getSuppliers().getId());
	    	}
	    	supplier.setStsupplierIds(stsupplierIds);
    	}
        List<Supplier> allSupplier = auditService.getAllSupplier(supplier, page == null || page.equals("") ? 1 : Integer.valueOf(page));
        //当前项目的所有包
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        model.addAttribute("listPackage", listPackage);
        model.addAttribute("list", new PageInfo<>(allSupplier));
        model.addAttribute("projectId", projectId);
        model.addAttribute("supplierName", supplier.getSupplierName());
        return "bss/ppms/sall_tender/supplier_list";
    }
    /**
     * 
     * @Description:修改状态
     *
     * @author Wang Wenshuai
     * @version 2016年10月19日 下午2:43:04  
     * @param @return      
     * @return String
     */
    @RequestMapping("/uploadDeposit")
    public String uploadDeposit(){
        return null;
    }

    /**
     * @Description:打开upload
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午1:39:13  
     * @param @return      
     * @return String
     */
    @RequestMapping("/showUpload")
    public String showUpload(String projectId,Model model,String id){


        //打印凭证
        DictionaryData dd = new  DictionaryData();
        dd.setCode("SALE_TENDER_DYPZ");
        List<DictionaryData> find = dictionaryDataServiceI.find(dd);
        if(find != null && find.size() !=0 ){
            model.addAttribute("saleTenderDypz", find.get(0).getId());
        }
        //发票上传
        dd = new  DictionaryData();
        dd.setCode("SALE_TENDER_FPSC");
        find = dictionaryDataServiceI.find(dd);
        if(find != null && find.size() !=0 ){
            model.addAttribute("saleTenderFpsc", find.get(0).getId());
        }
        //招标系统key
        Integer tenderKey = Constant.TENDER_SYS_KEY;
        model.addAttribute("projectId", projectId);
        model.addAttribute("saleId", id);
        model.addAttribute("tenderKey", tenderKey);


        return "bss/ppms/sall_tender/upload";
    }
    /**
     * @Description:缴费
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午1:56:17  
     * @param @return      
     * @return String
     */
    @ResponseBody
    @RequestMapping("/upload")
    public String paymentUpload(String projectId,String saleId,String statusBid){
        String upload = saleTenderService.upload(projectId,saleId,statusBid);
        return JSON.toJSONString(upload);
    }
    /**
     * @Description:保存供应商信息
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午2:39:12  
     * @param @return      
     * @return String
     */
    @ResponseBody
    @RequestMapping("/save")
    public Object save(String ids,String packages,String status,HttpServletRequest sq,String projectId){
        User attribute = (User) sq.getSession().getAttribute("loginUser");
        String info = "";
        if(packages==null||"".equals(packages.trim())){
        	info="error";
        }else{
        	if (attribute != null){
        		List<String> listIds=Arrays.asList(ids.split(","));
        		for(String str:listIds){
        			saleTenderService.insert(new SaleTender(projectId, (short)1, str, (short)1, attribute.getId(),packages));
        		}
            }
        }
        return JSON.toJSONString(info);
    }

    /**
     * @Description:下载
     *
     * @author Wang Wenshuai
     * @version 2016年10月21日 上午9:59:02  
     * @param @return      
     * @return String
     */
    @RequestMapping("/download")
    public String download(String projectId,String id){
        saleTenderService.download(projectId,id);
        return "redirect:list.html?projectId="+projectId;
    }
}
