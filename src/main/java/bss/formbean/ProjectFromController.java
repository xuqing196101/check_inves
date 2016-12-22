package bss.formbean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.WordUtil;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

@Controller
@Scope("prototype")
@RequestMapping("/projectFrom")
public class ProjectFromController {
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private ProjectService projectService;
    
    
    /**
     * 
     *〈上传采购实施方案〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/purchaseEmbodiment")
    public ResponseEntity<byte[]> purchaseEmbodiment(String id, String type, HttpServletRequest request) throws Exception{
        Project project = projectService.selectById(id);
        String downFileName = null;
        // 文件存储地址
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
        String fileName = createWordMethod(project, type,request);
        // 下载后的文件名
        if("1".equals(type)){
           downFileName = new String("采购实施方案.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("2".equals(type)){
            downFileName = new String("招标文件.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("3".equals(type)){
            downFileName = new String("组织文件.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("4".equals(type)){
            downFileName = new String("招标公告.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("5".equals(type)){
            downFileName = new String("评审文件.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("6".equals(type)){
            downFileName = new String("评审专家抽取记录.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("7".equals(type)){
            downFileName = new String("评审专家邀请函.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("8".equals(type)){
            downFileName = new String("开标记录.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("9".equals(type)){
            downFileName = new String("公正邀请函.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("10".equals(type)){
            downFileName = new String("投标文件受领登记表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("11".equals(type)){
            downFileName = new String("投标保证金收取登记表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("12".equals(type)){
            downFileName = new String("投标人开标签到表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("13".equals(type)){
            downFileName = new String("预备会议记录.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("14".equals(type)){
            downFileName = new String("评标记录.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("15".equals(type)){
            downFileName = new String("评标报告.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("16".equals(type)){
            downFileName = new String("中标供应商审批书.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
        if("17".equals(type)){
            downFileName = new String("采购合同审批表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        }
       return projectService.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     * 
     *〈生成word文档提供下载〉
     *〈详细描述〉
     * @author Administrator
     * @param project
     * @param request
     * @return
     * @throws Exception
     */
    private String createWordMethod(Project project, String type, HttpServletRequest request) throws Exception {
        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", project.getName() == null ? "" : project.getName());
        dataMap.put("projectNumber", project.getProjectNumber() == null ? "" : project.getProjectNumber());
        dataMap.put("purchaseType", project.getPurchaseType() == null ? "" : project.getPurchaseType());
        dataMap.put("purchaseDep", orgnization.getName() == null ? "" : orgnization.getName());
        String newFileName = null;
        // 文件名称
        if("1".equals(type)){
            String fileName = new String(("采购实施方案.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "purchaseEmbodiment.ftl", fileName, request);
        }
        if("2".equals(type)){
            String fileName = new String(("招标文件.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "biddingNotice.ftl", fileName, request);
        }
        if("3".equals(type)){
            String fileName = new String(("组织文件.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "organizationalDocument.ftl", fileName, request);
        }
        if("4".equals(type)){
            String fileName = new String(("招标公告.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "biddingAnnouncement.ftl", fileName, request);
        }
        if("5".equals(type)){
            String fileName = new String(("评审文件.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "reviewFile.ftl", fileName, request);
        }
        if("6".equals(type)){
            String fileName = new String(("评审专家抽取记录.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "ExOfEvalExperts.ftl", fileName, request);
        }
        if("7".equals(type)){
            String fileName = new String(("评审专家邀请函.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "evalExpertsInLetter.ftl", fileName, request);
        }
        if("8".equals(type)){
            String fileName = new String(("开标记录.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidOpening.ftl", fileName, request);
        }
        if("9".equals(type)){
            String fileName = new String(("公正邀请函.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "notarizedInvitation.ftl", fileName, request);
        }
        if("10".equals(type)){
            String fileName = new String(("投标文件受领登记表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidDocuments.ftl", fileName, request);
        }
        if("11".equals(type)){
            String fileName = new String(("投标保证金收取登记表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidBond.ftl", fileName, request);
        }
        if("12".equals(type)){
            String fileName = new String(("投标人开标签到表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidOpeningAttendance.ftl", fileName, request);
        }
        if("13".equals(type)){
            String fileName = new String(("预备会议记录.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "preparatory.ftl", fileName, request);
        }
        if("14".equals(type)){
            String fileName = new String(("评标记录.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidAssessmentRecord.ftl", fileName, request);
        }
        if("15".equals(type)){
            String fileName = new String(("评标报告.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "bidReport.ftl", fileName, request);
        }
        if("16".equals(type)){
            String fileName = new String(("中标供应商审批书.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "winningSupplier.ftl", fileName, request);
        }
        if("17".equals(type)){
            String fileName = new String(("采购合同审批表.doc").getBytes("UTF-8"), "UTF-8");
            /** 生成word 返回文件名 */
            newFileName = WordUtil.createWord(dataMap, "procurement.ftl", fileName, request);
        }
        return newFileName;
    }
   

}
