package synchro.controller;

import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.bean.ResponseBean;
import extract.service.expert.ExpertExtractProjectService;
import iss.service.hl.ServiceHotlineService;
import iss.service.ps.DataDownloadService;
import iss.service.ps.TemplateDownloadService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.bms.QualificationService;
import ses.service.ems.ExpertBlackListService;
import ses.service.sms.SMSProductLibService;
import ses.service.sms.SupplierBlacklistService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.oc.ComplaintService;
import synchro.inner.read.expert.InnerExpertService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.model.SynchRecord;
import synchro.outer.read.att.OuterAttachService;
import synchro.outer.read.infos.OuterInfoImportService;
import synchro.service.SynchService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 版权：(C) 版权所有
 * <简述>数据导入
 * <详细描述>
 *
 * @author myc
 * @see
 */
@Controller
@RequestMapping("/synchImport")
public class SynchImportController {

    /**
     * 同步控制层service
     **/
    @Autowired
    private SynchService synchService;

    /**
     * 同步信息数据service
     **/
    @Autowired
    private OuterInfoImportService infoService;
    /**
     * 供应商 名录
     ***/
    @Autowired
    private SupplierService supplierService;
    @Autowired
    /** 附件导入 **/
    private OuterAttachService attachService;
    /**
     * 同步 竞价定型产品
     **/
    @Autowired
    private OBProductService OBProductService;
    /**
     * 竞价供应商
     **/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**
     * 竞价信息
     **/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 同步供应商数据service
     **/
    @Autowired
    private InnerSupplierService innerSupplierService;
    /**
     * 产品库
     **/
    @Autowired
    private SMSProductLibService smsProductLibService;
    /**
     * 产品目录
     **/
    @Autowired
    private CategoryService categoryService;
    /**
     * 产品目录参数
     **/
    @Autowired
    private CategoryParameterService categoryParameterService;
    /**
     * 门户模板管理
     **/
    @Autowired
    private TemplateDownloadService templateDownloadService;
    @Autowired
    private InnerExpertService innerExpertService;
    /**
     * 资料数据
     **/
    @Autowired
    private DataDownloadService dataDownloadService;
    /**
     * 产品资质
     **/
    @Autowired
    private QualificationService qualificationService;
    /**
     * 设置数据类型
     **/
    private static final Integer DATA_TYPE_KIND = 29;

    /**
     * 网上投诉信息
     */
    @Autowired
    private ComplaintService complaintService;

    /**
     * 供应商黑名单
     **/
    @Autowired
    private SupplierBlacklistService supplierBlacklistService;

    /**
     * 专家黑名单
     **/
    @Autowired
    private ExpertBlackListService expertBlackListService;
    
    /** 服务热线 **/
    @Autowired
	private ServiceHotlineService serviceHotlineService;

    /**
     * 〈简述〉初始化导入
     * 〈详细描述〉
     *
     * @param model
     * @param request {@link HttpServletRequest}
     * @return
     * @author myc
     */
    @RequestMapping("/initImport")
    public String initImport(@CurrentUser User user, Model model, HttpServletRequest request) {
        //声明标识是否是资源服务中心
        String authType = null;
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            authType = "4";
            model.addAttribute("authType", authType);
            model.addAttribute("operType", Constant.OPER_TYPE_IMPORT);
            List<DictionaryData> list = DictionaryDataUtil.find(DATA_TYPE_KIND);
            //获取是否内网标识 1外网 0内网
            String ipAddressType = PropUtil.getProperty("ipAddressType");
            //过滤附件类型
            Iterator<DictionaryData> iter = list.iterator();
            while (iter.hasNext()) {
                DictionaryData dd = (DictionaryData) iter.next();
                if (dd.getCode().equals(Constant.DATA_TYPE_ATTACH_CODE)) {
                    iter.remove();
                    continue;
                }
                // 过滤专家抽取信息  定时任务自动导入导出
                if (dd.getCode().equals(Constant.DATE_SYNCH_EXPERT_EXTRACT)) {
                    iter.remove();
                    continue;
                }
                if (dd.getCode().equals(Constant.DATE_SYNCH_EXPERT_EXTRACT_RESULT)) {
                    iter.remove();
                    continue;
                }
                //过滤军队专家信息
                if (dd.getCode().equals(Constant.DATE_SYNCH_MILITARY_EXPERT)) {
                    iter.remove();
                    continue;
                }
                //内网时
                if (ipAddressType.equals("0")) {
                    //过滤外网导出  	竞价定型产品导出  只能是内网导出外网
                    if (dd.getCode().equals(Constant.DATE_SYNCH_BIDDING_PRODUCT)) {
                        iter.remove();
                        continue;
                    }
                    //竞价供应商导出  只能是内网导出外网
                    if (dd.getCode().equals(Constant.DATE_SYNCH_BIDDING_SUPPLIER)) {
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.DATA_TYPE_BIDDING_CODE)) {
                        /**竞价信息导出  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.SYNCH_CATEGORY)) {
                        /**产品目录管理 数据导出  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.SYNCH_CATE_PARAMTER)) {
                        /**产品目录参数管理 数据导出  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.SYNCH_DATA)) {
                        /**资料管理 数据导出  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.SYNCH_TEMPLATE_DOWNLOAD)) {
                        /**门户模板管理 数据导出  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.DATA_SYNCH_CATEGORY_QUA)) {
                        /**目录资质关联表  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.DATA_SYNCH_QUALIFICATION)) {
                        /**产品资质表  只能是内网导出外网**/
                        iter.remove();
                        continue;
                    }
                    if (dd.getCode().equals(Constant.SYNCH_PUBLICITY_SUPPLIER)) {
                        /**公示供应商**/
                        iter.remove();
                        continue;
                    }

                    if (dd.getCode().equals(Constant.SYNCH_PUBLICITY_EXPERT)) {
                        /**公示专家**/
                        iter.remove();
                        continue;
                    }
                }
                //外网时
                if (ipAddressType.equals("1")) {
                    if (dd.getCode().equals(Constant.DATA_TYPE_BIDDING_RESULT_CODE)) {
                        /**竞价结果导出  只能是外网导出内网**/
                        iter.remove();
                        continue;
                    }
                }
            }

            model.addAttribute("dataTypeList", list);
        }
        return "/synch/import";
    }

    /**
     * 〈简述〉数据同步
     * 〈详细描述〉
     *
     * @param operType 操作类型
     * @return
     * @author myc
     */
    @ResponseBody
    @RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public ResponseBean list(@CurrentUser User user, Integer operType, Integer page, String searchType, String startTime, String endTime) {
        //声明标识是否是资源服务中心
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            ResponseBean bean = new ResponseBean();
            if (operType != null) {
                bean.setSuccess(true);
                List<SynchRecord> list = synchService.getList(operType, page, searchType, startTime, endTime);
                PageInfo<SynchRecord> pageInfo = new PageInfo<SynchRecord>(list);
                if (pageInfo.getList() != null && pageInfo.getList().size() > 0) {
                    pageInfo.setList(packageSynchRecord(pageInfo.getList()));
                }
                bean.setObj(pageInfo);
            } else {
                bean.setSuccess(false);
            }
            return bean;
        }
        return new ResponseBean();
    }


    /**
     * 〈简述〉数据导入
     * 〈详细描述〉
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author myc
     */
    @ResponseBody
    @RequestMapping(value = "/dataImport", produces = "application/json;charset=UTF-8")
    public ResponseBean dataImport(@CurrentUser User user, String startTime, String endTime, String synchType) {
        //声明标识是否是资源服务中心
        if (null != user && "4".equals(user.getTypeName())) {
            //判断是否 是资源服务中心 
            ResponseBean bean = new ResponseBean();
            if (StringUtils.isEmpty(synchType)) {
                bean.setSuccess(false);
                return bean;
            }
            //获取是否内网标识 1外网 0内网
            String ipAddressType = PropUtil.getProperty("ipAddressType");
            File file = FileUtils.getImportFile();
            if (file != null && file.exists()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (files.length < 1) {
                        bean.setSuccess(false);
                        return bean;
                    }

                    if (synchType.contains("img_inner")) {
                        synchService.imageImportHandler();
                    }

                    if (synchType.contains(Constant.DATA_TYPE_INFOS_CODE)) {
                        //信息导入
                        if (f.getName().contains(FileUtils.C_INFOS_FILENAME)) {
                            infoService.importInfos(f);
                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importAttach(f);
                        }
                        if(f.getName().contains(FileUtils.C_ARTICLE_CATEGORY_PATH_FILENAME)){
                        	infoService.importArticleCategory(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_TENDER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    //供应商新注册提交
                    if (synchType.contains(Constant.DATA_TYPE_SUPPLIER_CODE)) {
                        if (f.getName().contains(FileUtils.C_SUPPLIER_FILENAME)) {
                            innerSupplierService.importSupplierInfo(f);

                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importSupplierAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }
                    /**
                     * 退回修改，导入到外网
                     */
                    if (synchType.contains("inner_out")) {
                        if (f.getName().contains(FileUtils.C_SUPPLIER_ALL_FILE)) {
                            innerSupplierService.immportInner(f, null);
                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importSupplierAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }


                    /**
                     * 供应商退回修改之后导入到内网
                     */
                    if (synchType.contains("outter_in")) {
                        if (f.getName().contains(FileUtils.C_SUPPLIER_FILENAME)) {
                            innerSupplierService.importBackSupplier(f);
                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importSupplierAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }


                    /**
                     * 临时供应商导入到外网
                     */
                    if (synchType.contains("temp_in")) {
                        if (f.getName().contains(FileUtils.C_TMEP_SUPPLIER_FILE)) {
                            innerSupplierService.importBackSupplier(f);
                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importSupplierAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }
                    /**专家导入内网*/
                    if (synchType.contains(Constant.DATA_TYPE_EXPERT_CODE)) {
                        if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)) {
                            innerExpertService.readNewExpertInfo(f);
                        }
                        if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)) {
                            //						attachService.importExpertAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    /**将退回修改专家导出的文件数据入库到外网*/
                    if (synchType.contains("expert_out")) {
                        if (f.getName().contains(FileUtils.C_EXPERT_ALL_NOT)) {
                            innerExpertService.saveBackModifyExpertForOut(f);
                        }
                        if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)) {
                            attachService.importExpertAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    /**专家退回修改后重新提交审核导入内网*/
                    if (synchType.contains("expert_again_inner")) {
                        if (f.getName().contains(FileUtils.C_EXPERT_ALL_NOT)) {
                            innerExpertService.saveBackModifyExpertForOut(f);
                        }
                        if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)) {
                            attachService.importExpertAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }
                    if (synchType.contains(Constant.DATE_SYNCH_BIDDING_PRODUCT)) {
                        /**竞价定型产品导入  只能是外网导入内网数据**/
                        if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是 竞价产品 创建 数据名称
                                // 判断是否有竞价产品 更新数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_OB_PRODUCT_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_OB_PRODUCT_FILENAME)) {
                                    OBProductService.importProduct(file2);
                                }
                            }
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }


                    if (synchType.contains(Constant.DATE_SYNCH_BIDDING_SUPPLIER)) {
                        /**竞价供应商导入  只能是外网导入内网数据**/
                        if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)) {
                            for (File file3 : f.listFiles()) {
                                if (file3.getName().contains(
                                        FileUtils.C_OB_SUPPLIER_FILENAME)
                                        || file3.getName()
                                        .contains(
                                                FileUtils.M_OB_SUPPLIER_FILENAME)) {
                                    OBSupplierService.importSupplier(file3);
                                }
                            }
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.SUPPLIER_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    if (synchType.contains(Constant.DATA_TYPE_BIDDING_CODE)) {
                        /**竞价信息导入  只能是外网导入内网数据**/
                        //如果文件存在 那么删除
                        if (f.getName().equals(Constant.PROJECT_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                //判断文件名是否是 竞价信息  数据名称
                                if (file2.getName().contains(FileUtils.C_OB_PROJECT_STATUS_FILENAME)) {
                                    OBProjectServer.importProject(file2);
                                }
                                //判断文件是否是竞价信息 附件文件
                                if (file2.getName().contains(FileUtils.C_OB_PROJECT_FILE_FILENAME)) {
                                    OBProjectServer.importFile(file2, common.constant.Constant.OB_PROJECT_SYS_KEY);
                                }
                            }
                        }
                        if (f.getName().equals(Constant.PROJECT_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                if (f.isDirectory()) {
                                    OperAttachment.moveToPathFolder(file2, FileUtils.BASE_ATTCH_PATH + FileUtils.OB_PROJECT_PATH);
                                }
                            }
                        }
                    }


                    if (synchType.contains(Constant.DATA_TYPE_BIDDING_RESULT_CODE)) {
                        /**竞价结果导入  只能是内网导入数据**/
                        if (f.getName().equals(Constant.RESULT_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                if (file2.getName().contains(FileUtils.C_OB_PROJECT_RESULT_FILENAME)) {
                                    OBProjectServer.importProjectResult(file2);
                                }
                            }
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.RESULT_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    if (synchType.contains(Constant.SYNCH_PRODUCT_LIBRARY)) {
                        /** 产品库 **/
                        // 如果文件存在 那么删除
                        if (f.isDirectory()) {
                            /** 产品库管理导入 1外网 0内网 **/
                            if (ipAddressType.equals("1")) {
                                // 外网 只能导入 内网导出的 产品审核过的数据
                                if (f.getName().equals(Constant.INNER_PRODUCT_LIBRARY_EXPERT)) {
                                    for (File file2 : f.listFiles()) {
                                        // 判断文件名是否是 数据名称
                                        if (file2
                                                .getName()
                                                .contains(
                                                        FileUtils.C_SYNCH_INNER_PRODUCT_LIBRARY)) {
                                            smsProductLibService
                                                    .importCheckProjectData(file2);
                                        }
                                    }
                                }
                            } else {
                                // 内网 只能 导入 外网 导出的 产品录入需要审核 的数据
                                if (f.getName().equals(
                                        Constant.OUTER_PRODUCT_LIBRARY_EXPERT)) {
                                    for (File file2 : f.listFiles()) {
                                        // 判断文件名是否是 数据名称
                                        if (file2.getName().contains(
                                                FileUtils.C_SYNCH_OUTER_PRODUCT_LIBRARY)) {
                                            smsProductLibService.importAddProjectData(file2);
                                        }
                                        // 判断文件是否是 附件文件
                                        if (file2.getName().contains(
                                                FileUtils.C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY)) {
                                            OBProjectServer.importFile(
                                                    file2, common.constant.Constant.SUPPLIER_SYS_KEY);
                                        }
                                    }
                                }
                                // 图片移动
                                if (f.getName().equals(
                                        Constant.OUTER_FILE_PRODUCT_LIBRARY_EXPERT)) {
                                    for (File file2 : f.listFiles()) {
                                        if (f.isDirectory()) {
                                            OperAttachment
                                                    .moveToPathFolder(
                                                            file2,
                                                            FileUtils.BASE_ATTCH_PATH
                                                                    + FileUtils.SUPPLIER_ATTFILE_PATH);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (synchType.contains(Constant.SYNCH_CATEGORY)) {
                        /** 产品目录管理 只能是外网 导入 **/
                        if (f.getName().equals(Constant.T_SES_BMS_CATEGORY_PATH)) {
                            //便利文件夹 目录
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是 导出 创建 数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_CATEGORY_FILENAME)) {
                                    categoryService.importCategory(file2);
                                }
                                // 判断文件是否是 产品的 附件文件
                                if (file2.getName().contains(
                                        FileUtils.C_FILE_CATEGORY_FILENAME)) {
                                    OBProjectServer.importFile(file2, common.constant.Constant.TENDER_SYS_KEY);
                                }
                            }
                        }
                        if (f.getName().equals(Constant.FILE_T_SES_BMS_CATEGORY_PATH)) {
                            for (File file2 : f.listFiles()) {
                                if (f.isDirectory()) {
                                    OperAttachment
                                            .moveToPathFolder(
                                                    file2,
                                                    FileUtils.BASE_ATTCH_PATH
                                                            + FileUtils.TENDER_ATTFILE_PATH);
                                }
                            }
                        }
                    }

                    if (synchType.contains(Constant.SYNCH_CATE_PARAMTER)) {
                        /** 产品目录参数管理 只能是外网导入 **/
                        if (f.getName().equals(Constant.T_SES_BMS_CATEGORY_PARAMTER_PATH)) {
                            // 遍历文件夹中的所有文件
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是导出创建数据名称
                                if (file2.getName().contains(FileUtils.C_CATEGORY_PARAMTER_FILENAME)) {
                                    categoryParameterService.importCategoryParmter(file2);
                                }
                            }
                        }
                    }

                    //资料 管理 导入
                    if (synchType.contains(Constant.SYNCH_DATA)) {
                        //判断 是否有资料管理的数据 文档
                        if (f.getName().equals(Constant.ISS_PS_DATA_DOWNLOAD_PATH)) {
                            for (File file2 : f.listFiles()) {
                                //判断 是否有 资料管理 数据
                                if (file2.getName().contains(
                                        FileUtils.C_DATA_DOWNLOAD_FILENAME)) {
                                    dataDownloadService.importDate(file2);
                                }
                                //判断 是否有 附件 数据
                                if (file2.getName().contains(
                                        FileUtils.C_FILE_DATA_DOWNLOAD_FILENAME)) {
                                    OBProjectServer.importFile(file2, common.constant.Constant.TENDER_SYS_KEY);
                                }
                            }
                        }
                        if (f.getName().equals(Constant.FILE_ISS_PS_DATA_DOWNLOAD_PATH)) {
                            for (File file2 : f.listFiles()) {
                                if (file2.isDirectory()) {
                                    OperAttachment.moveToPathFolder(file2,
                                            FileUtils.BASE_ATTCH_PATH + FileUtils.TENDER_ATTFILE_PATH);
                                }
                            }
                        }
                    }

                    if (synchType.contains(Constant.SYNCH_TEMPLATE_DOWNLOAD)) {
                        /** 门户模板管理  只能是外网导入 **/
                        if (f.getName().equals(Constant.T_ISS_PS_TEMPLATE_DOWNLOAD_PATH)) {
                            // 遍历文件夹中的所有文件
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是导出创建数据名称
                                if (file2.getName().contains(FileUtils.C_TEMPLATE_DOWNLOAD_FILENAME)) {
                                    templateDownloadService.importTemplateDownload(file2);
                                }
                                if (file2.getName().contains(FileUtils.C_FILE_TEMPLATE_DOWNLOAD_FILENAME)) {
                                    OBProjectServer.importFile(file2, common.constant.Constant.TENDER_SYS_KEY);
                                }
                            }
                        }
                        // 附件目录复制
                        if (f.getName().equals(Constant.T_ISS_PS_TEMPLATE_DOWNLOAD_ATTFILE_PATH)) {
                            if (f.isDirectory()) {
                                for (File file2 : f.listFiles()) {
                                    OperAttachment.moveToPathFolder(file2, FileUtils.BASE_ATTCH_PATH
                                            + FileUtils.TENDER_ATTFILE_PATH);
                                }
                            }
                        }
                    }

                    /**
                     * 公示供应商
                     */
                    if (synchType.contains(Constant.SYNCH_PUBLICITY_SUPPLIER)) {
                        for (File file2 : f.listFiles()) {
                            if (file2.getName().contains(FileUtils.C_SYNCH_PUBLICITY_SUPPLIER_FILENAME)) {
                                innerSupplierService.immportInner(file2, "publicity");
                            }
                        }
                        /*if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importSupplierAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTACH_FILE_SUPPLIER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }*/
                    }

                    /**
                     * 公示专家
                     */
                    if (synchType.contains(Constant.SYNCH_PUBLICITY_EXPERT)) {
                        for (File file2 : f.listFiles()) {
                            if (file2.getName().contains(FileUtils.C_SYNCH_PUBLICITY_EXPERT_FILENAME)) {
                                innerExpertService.importExpOfPublicity(file2);
                            }
                        }
                        if (f.getName().contains(FileUtils.C_EXPERT_FILENAME)) {
                            attachService.importExpertAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    /** 网上投诉信息数据导入 **/
                    if (synchType.contains(Constant.DATE_SYNCH_ONLINE_COMPLAINTS)) {
                        if (f.getName().equals(Constant.ONLINE_COMPLAINTS_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是网上投诉信息数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_ONLINE_COMPLAINTS_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_ONLINE_COMPLAINTS_PATH_FILENAME)) {
                                    complaintService.importProduct(file2);
                                }

                            }
                        }
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.ONLINE_COMPLAINTS_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                            if (f.getName().equals(Constant.ATTACH_FILE_TENDER)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    /** 供应商黑名单信息数据导入 **/
                    if (synchType.contains(Constant.DATE_SYNCH_SUPPLIER_BLACKLIST)) {
                        if (f.getName().equals(Constant.SUPPLIER_BLACKLIST_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是供应商黑名单信息数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_SUPPLIER_BLACKLIST_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_SUPPLIER_BLACKLIST_PATH_FILENAME)) {
                                    supplierBlacklistService.importSupplierBlacklist(file2);
                                }

                            }
                        }
                        if (f.getName().equals(Constant.SUPPLIER_BLACKLIST_LOG_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是供应商黑名单记录信息数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_SUPPLIER_BLACKLIST_LOG_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_SUPPLIER_BLACKLIST_LOG_PATH_FILENAME)) {
                                    supplierBlacklistService.importSupplierBlacklistLog(file2);
                                }

                            }
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.SUPPLIER_BLACKLIST_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                            if (f.getName().equals(Constant.SUPPLIER_BLACKLIST_LOG_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }

                    /** 专家黑名单信息数据导入 **/
                    if (synchType.contains(Constant.DATE_SYNCH_EXPERT_BLACKLIST)) {
                        if (f.getName().equals(Constant.EXPERT_BLACKLIST_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是专家黑名单信息数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_EXPERT_BLACKLIST_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_EXPERT_BLACKLIST_PATH_FILENAME)) {
                                    expertBlackListService.importExpertBlacklist(file2);
                                }

                            }
                        }
                        if (f.getName().equals(Constant.EXPERT_BLACKLIST_LOG_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                // 判断文件名是否是专家黑名单记录信息数据名称
                                if (file2.getName().contains(
                                        FileUtils.C_EXPERT_BLACKLIST_LOG_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_EXPERT_BLACKLIST_LOG_PATH_FILENAME)) {
                                    expertBlackListService.importExpertBlacklistLog(file2);
                                }

                            }
                        }
                        //专家黑名单附件
                        if (f.getName().contains(FileUtils.C_ATTACH_FILENAME)) {
                            attachService.importExpertAttach(f);
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.EXPERT_BLACKLIST_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                            if (f.getName().equals(Constant.EXPERT_BLACKLIST_LOG_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                            if (f.getName().equals(Constant.ATTCH_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }
                    
                    /** 服务热线信息数据导入 **/
                    if (synchType.contains(Constant.DATE_SYNCH_HOT_LINE)) {
                        if (f.getName().equals(Constant.HOT_LINE_FILE_EXPERT)) {
                            for (File file2 : f.listFiles()) {
                                if (file2.getName().contains(
                                        FileUtils.C_HOT_LINE_PATH_FILENAME)
                                        || file2.getName()
                                        .contains(
                                                FileUtils.M_HOT_LINE_PATH_FILENAME)) {
                                	serviceHotlineService.importHotLine(file2);
                                }
                            }
                        }
                        if (f.isDirectory()) {
                            if (f.getName().equals(Constant.HOT_LINE_FILE_EXPERT)) {
                                OperAttachment.moveFolder(f);
                            }
                        }
                    }
                    /**目录资质关联表*/
                    categoryService.importCategoryQua(synchType, f);
                    /** 产品资质表*/
                    qualificationService.importQualification(synchType, f);

                }
            }
            bean.setSuccess(true);
            return bean;
        }
        return new ResponseBean();
    }

    /**
     * 〈简述〉封装为带有类型名称的集合
     * 〈详细描述〉
     *
     * @param list 数据list
     * @return 组装后的list
     * @author myc
     */
    private List<SynchRecord> packageSynchRecord(List<SynchRecord> list) {
        List<SynchRecord> dataList = new ArrayList<>();
        for (SynchRecord synchRecord : list) {
            if (StringUtils.isNotBlank(synchRecord.getDataType())) {
                DictionaryData dd = DictionaryDataUtil.findById(synchRecord.getDataType());
                if (dd != null && StringUtils.isNotBlank(dd.getName())) {
                    synchRecord.setDataTypeName(dd.getName());
                }
            }
            dataList.add(synchRecord);
        }
        return dataList;
    }
}
