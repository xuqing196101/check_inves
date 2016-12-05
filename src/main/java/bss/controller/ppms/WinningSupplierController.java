/**
 * 
 */
package bss.controller.ppms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.StationMessage;
import ses.service.bms.StationMessageService;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;
import ses.util.WordUtil;

import com.alibaba.fastjson.JSON;
























import common.constant.Constant;
import common.service.UploadService;
import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.PackageExpert;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.SupplierCheckPassService;
import bss.util.PropUtil;

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

    @Autowired
    private StationMessageService stationMessageService;

    @Autowired
    private UserServiceI userServiceI;
    
    @Autowired
    private FlowMangeService flowMangeService;//环节

    /**
     * 文件上传
     */
    @Autowired
    UploadService  uploadService;
    /**
     *〈简述〉选择查看包
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param model
     * @return
     */
    @RequestMapping("/selectSupplier")
    public String selectWinningSupplier(Model model, String projectId, String flowDefineId){
        List<Packages> packList = packageService.listSupplierCheckPass(projectId);
        model.addAttribute("packList", packList);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        //获取已有中标供应商的包组
        String[] packcount = checkPassService.selectWonBid(projectId);
        if (packList.size() != packcount.length){
            model.addAttribute("error", "ERROR");
        }
        return "bss/ppms/winning_supplier/list";
    }


    /**
     * 
     *〈简述〉获取包下所有供应商信息
     *〈详细描述〉
     * @author Wang Wenshuai 
     * @param model
     * @param projectId
     * @param flowDefineId
     * @return 路径
     */
    @RequestMapping("/packageSupplier")
    public String selectpackage(Model model, String packageId, String flowDefineId,String projectId,HttpServletRequest sq){
        SupplierCheckPass checkPass = new SupplierCheckPass();
        checkPass.setPackageId(packageId);
        List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listCheckPass(checkPass);
        model.addAttribute("supplierCheckPass", listSupplierCheckPass);
        model.addAttribute("supplierCheckPassJosn",JSON.toJSONString(listSupplierCheckPass));
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("projectId", projectId);
        //             //修改流程状态
        flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
        return "bss/ppms/winning_supplier/supplier_list";
    }

    /**
     * 
     *〈简述〉比较是否是按照排名来的
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @ResponseBody
    @RequestMapping("/comparison")
    public String comparison(String[] checkPassId, String jsonCheckPass){
        int type = 0; 
        List<SupplierCheckPass> supplierCheckPass = JSON.parseArray(jsonCheckPass, SupplierCheckPass.class);
        for (int i = 0; i < checkPassId.length; i++ ) {
            if (!checkPassId[i].equals(supplierCheckPass.get(i).getId()) ) {
                type = 1;
                break;
            }
        }
        //按照排名不需要上传变更依据
        if (type != 1){
            checkPassService.updateBid(checkPassId);
            return JSON.toJSONString("SCCUESS");
        } else {
            return JSON.toJSONString("ERROR");
        }


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
    public String upload(Model model,String packageId){
        //凭证上传
        String id = DictionaryDataUtil.getId("CHECK_PASS_BGYJ");
        model.addAttribute("checkPassBgyj", id);

        //招标系统key
        Integer tenderKey = Constant.TENDER_SYS_KEY;
        model.addAttribute("packageId", packageId);
        model.addAttribute("tenderKey", tenderKey);

        return "bss/ppms/winning_supplier/upload";
    } 
    
    /**
     * 
     *〈简述〉执行完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param projectId 项目id 
     * @param flowDefineId 流程id 
     * @return
     */
    @ResponseBody
    @RequestMapping("/executeFinish")
    public String executeFinish(String  projectId, String flowDefineId,HttpServletRequest sq){
        HashMap<String, Object> map = new HashMap<String, Object>();
        //获取已有中标供应商的包组
        String[] packList = checkPassService.selectWonBid(projectId);
        //查看项目下有多少包
        map.put("projectId", projectId);
        List<Packages> findPackageById = packageService.findPackageById(map);
        //对比
        if (findPackageById != null && findPackageById.size() != 0){
            if (findPackageById.size() != packList.length){
                return JSON.toJSONString("ERROR");
            } else {
                flowMangeService.flowExe(sq, flowDefineId, projectId, 1);
            }
        }
        return JSON.toJSONString("SCCUESS");
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
        SupplierCheckPass checkPass = new SupplierCheckPass();
        checkPass.setProjectId(projectId);
        checkPass.setIsSendNotice((short)0);
        checkPass.setIsWonBid((short)1);
        List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listSupplierCheckPass(checkPass);
        model.addAttribute("listSupplierCheckPass", listSupplierCheckPass);
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
        SupplierCheckPass checkPass = new SupplierCheckPass();
        checkPass.setProjectId(projectId);
        checkPass.setIsSendNotice((short)0);
        checkPass.setIsWonBid((short)0);
        List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listSupplierCheckPass(checkPass);
        model.addAttribute("listSupplierCheckPass", listSupplierCheckPass);
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
        //是否发送  0未发送 1发送   
        checkPass.setIsSendNotice((short) 0);
        //包id 
        checkPass.setPackageId(packageId);
        //是否中标 0未中标 1中标 
        checkPass.setIsWonBid(new Short(isWonBid));
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
        //        checkPassService.updateBid(id);
        return JSON.toJSONString("sccuess");
    }

    /**
     * 发布
     *〈简述〉
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     * @throws Exception 
     */
    @RequestMapping("/publish")
    public String  publish(String supplierId,Integer isWon,String projectId,String content,HttpServletRequest sq) throws Exception{
        String bidNotice = PropUtil.getProperty("bidNotice");
        if (supplierId != null &&  !"".equals(supplierId)){
            String[] supplierIds = supplierId.split("\\^");
            SupplierCheckPass checkPass = new SupplierCheckPass();
            checkPass.setIsSendNotice((short) 1);

            checkPass.setSupplierId(supplierIds[0]);
            checkPassService.update(checkPass);

            //获取供应商登录id
            ses.model.bms.User findByTypeId = userServiceI.findByTypeId(supplierIds[0]);



            ses.model.bms.User  login = (ses.model.bms.User ) sq.getSession().getAttribute("loginUser");
            if (login != null){
                StationMessage stationMessage = new StationMessage();
                stationMessage.setReceiverId("869CB7FA88DD4C55A228D94F17A7CD71");
                String pro = PropUtil.getProperty("bidNotice");
                stationMessage.setName(pro);
                stationMessage.setUrl("downloadabiddocument");
                stationMessage.setSenderId(login.getId());
                stationMessageService.insertStationMessage(stationMessage);
                //                if (findByTypeId != null){
                //招标系统key
                Integer tenderKey = Constant.TENDER_SYS_KEY;
                uploadService.uploadFileByContext(stationMessage.getId(), tenderKey.toString(), content); 
                //                }
            }
        }
        if (isWon == 1){
            return "redirect:template.html?projectId=" + projectId;
        }else{
            return "redirect:notTemplate.html?projectId=" + projectId;
        }

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
