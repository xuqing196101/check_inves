/**
 * 
 */
package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.service.sms.SupplierQuoteService;

import com.alibaba.fastjson.JSON;


import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.PackageService;
import bss.service.ppms.SupplierCheckPassService;

/**
 * @Description: 中标供应商
 *
 * @author Wang Wenshuai
 * @version 2016年10月11日下午2:51:13
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/winningSupplier")
public class WinningSupplierController extends BaseController {
    /**
     * 评审通过供应商
     */
    @Autowired
    SupplierCheckPassService checkPassService; 
    
    @Autowired
    PackageService packageService;
    
    @Autowired
    SupplierQuoteService supplierQuoteService;
    
    @Autowired
    AduitQuotaService aduitQuotaService;
    
    
    /**
     *〈简述〉选择中标供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param model
     * @return
     */
    @RequestMapping("/selectSupplier")
    public String selectWinningSupplier(Model model, String projectId, String flowDefineId){
        List<SupplierCheckPass> listSupplierCheckPass = packageService.listSupplierCheckPass(projectId);
        model.addAttribute("listSupplierCheckPass", listSupplierCheckPass);
//        //项目分包信息
//        HashMap<String,Object> pack = new HashMap<String,Object>();
//        pack.put("projectId", projectId);
//        List<Packages> packages = packageService.findPackageById(pack);
//        for (Packages pg : packages) {
//            //获取该包下初审通过参与报价的供应商
//            List<Supplier> supplierList = new ArrayList<Supplier>(); 
//            
//            //改包下供应商报价信息
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("projectId", projectId);
//            map.put("packageId", pg);
//            map.put("", );
//            supplierQuoteService.find(map);
//            //改包下供应商得分信息
//            SupplierCheckPass supplierCheckPass = new SupplierCheckPass();
//            pg.getSupplierList();
//        }
//        JSON.parseObject(text, clazz);
//        model.addAttribute("packages", listPackage);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/winning_supplier/list";
    }
    /**
     * @Description:上传
     *
     * @author Wang Wenshuai
     * @version 2016年10月11日 下午3:48:56  
     * @param       
     * @return void
     */
    @RequestMapping("/upload")
    public String upload(){
        return "bss/ppms/winning_supplier/upload";
    } 

    /**
     * @Description:打开中标模板
     *
     * @author Wang Wenshuai
     * @version 2016年10月11日 下午4:46:42  
     * @param projectId    项目id  
     * @return String
     */
    @RequestMapping("/template")
    public String template(Model model, String projectId){
        List<Packages> packageName = checkPassService.getPackageName(projectId);
        model.addAttribute("packageName", packageName);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/winning_supplier/template";
    }
    
    /**
     * @Description:打开未中标模板
     *
     * @author Wang Wenshuai
     * @version 2016年10月11日 下午4:46:42  
     * @param projectId 项目id      
     * @return String
     */
    @RequestMapping("/notTemplate")
    public String notTemplate(Model model, String projectId){
        List<Packages> packageName = checkPassService.getPackageName(projectId);
        model.addAttribute("packageName", packageName);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/winning_supplier/not_template";
    }
    
    /**
     * 
     *〈简述〉根据包id获取供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param model ui
     * @param packageId 项目id
     * @param isWonBid 是否中标 0未中标 1中标 
     * @return 地址
     */
    @ResponseBody
    @RequestMapping("/getSupplierJosn")
    public String getSupplier(Model model, String packageId, String isWonBid){
        SupplierCheckPass checkPass = new SupplierCheckPass();
         //是否发送  1发送 2未发送
        checkPass.setIsSendNotice((short) 0);
        //包id 
        checkPass.setPackageId(packageId);
        //是否中标 0未中标 1中标 
        checkPass.setIsWonBid(new Short("0"));
        List<SupplierCheckPass> supplierCheckPasses = checkPassService.listSupplierCheckPass(checkPass);
        return JSON.toJSONString(supplierCheckPasses);
    }

    /**
     * 
     *〈简述〉修改中标供应商状态
     *〈详细描述〉对比评审通过后的供应商是否和选中供应商一致。
     * @author Wang Wenshuai
     * @param id id集合
     * @return json
     */
    @ResponseBody
    @RequestMapping("/updateBid")
    public String updateBid(String id){
        checkPassService.updateBid(id);
        return JSON.toJSONString("sccuess");
    }
    
    /**
     * 
     *〈简述〉 修改方法
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @RequestMapping("/update")
    public String update(SupplierCheckPass checkPass){
        checkPassService.update(checkPass);
        return null;
    }
}
