package synchro.inner.read.supplier.impl;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.sms.SupplierAddressMapper;
import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierAuditMapper;
import ses.dao.sms.SupplierAuditNotMapper;
import ses.dao.sms.SupplierAuditOpinionMapper;
import ses.dao.sms.SupplierBranchMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierCertProMapper;
import ses.dao.sms.SupplierCertSellMapper;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierEngQuaMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierHistoryMapper;
import ses.dao.sms.SupplierItemMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierMatProMapper;
import ses.dao.sms.SupplierMatSellMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.dao.sms.SupplierModifyMapper;
import ses.dao.sms.SupplierRegPersonMapper;
import ses.dao.sms.SupplierSignatureMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.dao.sms.SupplierTypeRelateMapper;
import ses.formbean.SupplierAuditFormBean;
import ses.model.bms.RoleUser;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierSignature;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

import java.io.File;
import java.util.List;

/**
 * 版权：(C) 版权所有
 * <简述>读取供应商信息service
 * <详细描述>
 *
 * @author myc
 * @see
 */
@Service
public class InnerSupplierServiceImpl implements InnerSupplierService {

    /**
     * 记录service
     **/
    @Autowired
    private SynchRecordService synchRecordService;

    /**
     * 用户service
     **/
    @Autowired
    private UserServiceI userService;

    /**
     * 供应商 service
     **/
    @Autowired
    private SupplierService supplierSerice;

    @Autowired
    private SupplierFinanceMapper supplierFinanceMapper;

    /**
     * 生产经营地址 service
     */
    @Autowired
    private SupplierAddressService supplierAddressService;

    @Autowired
    private SupplierBranchMapper supplierBranchMapper;

    @Autowired
    private SupplierStockholderMapper supplierStockholderMapper;

    @Autowired
    private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper;

    @Autowired
    private SupplierTypeRelateMapper supplierTypeRelateMapper;

    @Autowired
    private SupplierMatProMapper supplierMatProMapper;

    @Autowired
    private SupplierMatEngMapper supplierMatEngMapper;

    @Autowired
    private SupplierMatSellMapper supplierMatSellMapper;

    @Autowired
    private SupplierMatServeMapper supplierMatServeMapper;

    @Autowired
    private SupplierCertProMapper supplierCertProMapper;

    @Autowired
    private SupplierCertSellMapper supplierCertSellMapper;

    @Autowired
    private SupplierAptituteMapper supplierAptituteMapper;

    @Autowired
    private SupplierCertEngMapper supplierCertEngMapper;

    @Autowired
    private SupplierRegPersonMapper supplierRegPersonMapper;

    @Autowired
    private SupplierCertServeMapper supplierCertServeMapper;
    
    @Autowired
    private SupplierEngQuaMapper supplierEngQuaMapper;

    @Autowired
    private SupplierItemMapper supplierItemMapper;


    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private SupplierHistoryMapper supplierHistoryMapper;

    @Autowired
    private SupplierModifyMapper supplierModifyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TodosMapper todosMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SupplierAuditMapper supplierAuditMapper;


    @Autowired
    private SupplierAuditNotMapper supplierAuditNotMapper;

    @Autowired
    private SupplierSignatureMapper supplierSignatureMapper;

    @Autowired
    private SupplierAddressMapper supplierAddressMapper;

    @Autowired
    private SupplierAuditOpinionMapper supplierAuditOpinionMapper;

    /**
     * @see synchro.inner.read.supplier.InnerSupplierService#importSupplierInfo(java.io.File)
     */
    @Override
    public void importSupplierInfo(final File file) {
        List<Supplier> list = getSupplier(file);
        for (Supplier supplier : list) {
//    	   Supplier unSupplier = supplierSerice.selectById(supplier.getId());
//    	   if(unSupplier==null){
            if (supplier.getListSupplierFinances().size() > 0) {
                // 先删除操作
                supplierFinanceMapper.deleteFinanceBySupplierId(supplier.getId());
                for (SupplierFinance sf : supplier.getListSupplierFinances()) {
                    SupplierFinance unfinance = supplierFinanceMapper.selectByPrimaryKey(sf.getId());
                    if (unfinance == null) {
                        supplierFinanceMapper.insertSelective(sf);
                    } else {
                        supplierFinanceMapper.updateByPrimaryKeySelective(sf);
                    }
                }
            }

            if (supplier.getTodoList() != null && supplier.getTodoList().size() > 0) {
                for (Todos to : supplier.getTodoList()) {
                    Todos td = todosMapper.selectByPrimaryKey(to.getId());
                    if (td == null) {
                        todosMapper.insertSelective(to);
                    } else {
                        todosMapper.updateByPrimaryKeySelective(to);
                    }
                }
            }

            // 供应商上传附件
            if (supplier.getAttchList().size() > 0) {
                // 获取供应商附件存储表
                String tabName = Constant.fileSystem.get(Constant.SUPPLIER_SYS_KEY);
                for (UploadFile uf : supplier.getAttchList()) {
                    UploadFile ufile = fileUploadMapper.queryById(uf.getId(), tabName);
                    uf.setTableName(tabName);
                    if (ufile == null) {
                        fileUploadMapper.saveFile(uf);
                    } else {
                        fileUploadMapper.updateFileById(uf);
                    }
                }
            }

            if (supplier.getAddressList().size() > 0) {
                supplierAddressMapper.deleteBySupplierId(supplier.getId());
                supplierAddressService.addList(supplier.getAddressList(), supplier.getId());
            }
            if (supplier.getBranchList().size() > 0) {
                supplierBranchMapper.deleteBySupplierId(supplier.getId());
                for (SupplierBranch sb : supplier.getBranchList()) {
                    SupplierBranch branch = supplierBranchMapper.queryById(sb.getId());
                    if (branch == null) {
                        supplierBranchMapper.insertSelective(sb);
                    } else {
                        supplierBranchMapper.updateByPrimaryKeySelective(sb);
                    }
                }
            }
            if (supplier.getListSupplierStockholders().size() > 0) {
                supplierStockholderMapper.deleteStockholderBySupplierId(supplier.getId());
                for (SupplierStockholder ss : supplier.getListSupplierStockholders()) {
                    SupplierStockholder stockholder = supplierStockholderMapper.selectByPrimaryKey(ss.getId());
                    if (stockholder == null) {
                        supplierStockholderMapper.insertSelective(ss);
                    } else {
                        supplierStockholderMapper.updateByPrimaryKeySelective(ss);
                    }
                }
            }
            if (supplier.getListSupplierAfterSaleDep().size() > 0) {
                for (SupplierAfterSaleDep sa : supplier.getListSupplierAfterSaleDep()) {
                    SupplierAfterSaleDep saleDep = supplierAfterSaleDepMapper.selectByPrimaryKey(sa.getId());
                    if (saleDep == null) {
                        supplierAfterSaleDepMapper.insertSelective(sa);
                    } else {
                        supplierAfterSaleDepMapper.updateByPrimaryKeySelective(sa);
                    }
                }
            }
            if (supplier.getListSupplierTypeRelates().size() > 0) {
                supplierTypeRelateMapper.deleteBySupplierId(supplier.getId());
                for (SupplierTypeRelate str : supplier.getListSupplierTypeRelates()) {
                    SupplierTypeRelate relate = supplierTypeRelateMapper.selectByPrimaryKey(str.getId());
                    if (relate == null) {
                        supplierTypeRelateMapper.insertSelective(str);
                    }
                }
            }

            /**
             * 物资生产信息
             */
            if (supplier.getSupplierMatPro() != null) {
                // 先做删除操作
                //SupplierMatPro matPro = supplierMatProMapper.getMatProBySupplierId(supplier.getId());
                //if (matPro == null) {
                //    supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
                //}
                //if (matPro != null) {
                //    supplierMatProMapper.updateByPrimaryKeySelective(supplier.getSupplierMatPro());
                //    supplierCertProMapper.deleteByMatProId(matPro.getId()); //删除生产证书
                //}
                // 删除生产基本信息
                supplierMatProMapper.deleteBySupplierId(supplier.getId());
                // 重新插入生产基本信息
                supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
                // 删除生产证书信息
                supplierCertProMapper.deleteByMatProId(supplier.getSupplierMatPro().getId());
                if (supplier.getSupplierMatPro().getListSupplierCertPros().size() > 0) {
                    for (SupplierCertPro sc : supplier.getSupplierMatPro().getListSupplierCertPros()) {
                        // 插入生产证书信息
                        supplierCertProMapper.insertSelective(sc);
                    }
                }
            }

            /**
             * 物资销售信息
             */
            if (supplier.getSupplierMatSell() != null) {
                /*SupplierMatSell matSell = supplierMatSellMapper.getMatSellBySupplierId(supplier.getId());
                if (matSell == null) {
                    supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
                }
                if (matSell != null) {
                    supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
                    supplierCertSellMapper.deleteByMatSellId(matSell.getId());//删除供应商销售证书
                }*/

                // 删除物资销售基本信息
                supplierMatSellMapper.deleteBySupplierId(supplier.getId());
                // 重新插入物资销售基本信息
                supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
                // 删除物资销售证书
                supplierCertSellMapper.deleteByMatSellId(supplier.getSupplierMatSell().getId());
                if (supplier.getSupplierMatSell().getListSupplierCertSells().size() > 0) {
                    for (SupplierCertSell sc : supplier.getSupplierMatSell().getListSupplierCertSells()) {
                        supplierCertSellMapper.insertSelective(sc);
                    }
                }
            }

            /**
             * 工程信息
             */
            if (supplier.getSupplierMatEng() != null) {
                /*SupplierMatEng matEng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
                if (matEng == null) {
                    supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
                }
                if (matEng != null) {
                    supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
                    supplierAptituteMapper.deleteByMatEngId(matEng.getId());
                    supplierCertEngMapper.deleteByMatEngId(matEng.getId());
                    supplierRegPersonMapper.deleteByMatEngId(matEng.getId());
                    //删除工程的相关证书
                }*/
                // 删除工程基本信息
                supplierMatEngMapper.deleteBySupplierId(supplier.getId());
                // 重新插入工程基本信息
                supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
                // 删除取得注册资质的人员信息
                supplierRegPersonMapper.deleteByMatEngId(supplier.getSupplierMatEng().getId());
                // 删除供应商资质（认证）证书信息
                supplierCertEngMapper.deleteByMatEngId(supplier.getSupplierMatEng().getId());
                // 删除供应商资质证书详细信息
                supplierAptituteMapper.deleteByMatEngId(supplier.getSupplierMatEng().getId());
                // 删除供应商资质证书信息
                supplierEngQuaMapper.deleteByMatEngId(supplier.getSupplierMatEng().getId());
                // 重新插入取得注册资质的人员信息
                if (supplier.getSupplierMatEng().getListSupplierRegPersons().size() > 0) {
                    for (SupplierRegPerson sp : supplier.getSupplierMatEng().getListSupplierRegPersons()) {
                        supplierRegPersonMapper.insertSelective(sp);
                    }
                }
                // 重新插入供应商资质（认证）证书信息
                if (supplier.getSupplierMatEng().getListSupplierCertEngs().size() > 0) {
                    for (SupplierCertEng sce : supplier.getSupplierMatEng().getListSupplierCertEngs()) {
                        supplierCertEngMapper.insertSelective(sce);
                    }
                }
                // 重新插入供应商资质证书详细信息
                if (supplier.getSupplierMatEng().getListSupplierAptitutes().size() > 0) {
                    for (SupplierAptitute sb : supplier.getSupplierMatEng().getListSupplierAptitutes()) {
                        supplierAptituteMapper.insertSelective(sb);
                    }
                }
                // 重新插入供应商资质证书信息
                if (supplier.getSupplierMatEng().getListSupplierEngQuas().size() > 0) {
                	for (SupplierEngQua sb : supplier.getSupplierMatEng().getListSupplierEngQuas()) {
                		supplierEngQuaMapper.insertSelective(sb);
                	}
                }
            }

            /**
             * 服务信息
             */
            if (supplier.getSupplierMatSe() != null) {
                /*SupplierMatServe serve = supplierMatServeMapper.selectByPrimaryKey(supplier.getSupplierMatSe().getId());
                if (serve == null) {
                    supplierMatServeMapper.insertSelective(supplier.getSupplierMatSe());
                } else if (serve != null) {
                    supplierMatServeMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSe());
                    supplierCertServeMapper.deleteByMatServeId(serve.getId());
                    //删除服务的相关帧数
                }*/
                // 删除服务基本信息
                supplierMatServeMapper.deleteBySupplierId(supplier.getId());
                // 插入服务基本信息
                supplierMatServeMapper.insertSelective(supplier.getSupplierMatSe());
                supplierCertServeMapper.deleteByMatServeId(supplier.getSupplierMatSe().getId());
                if (supplier.getSupplierMatSe().getListSupplierCertSes().size() > 0) {
                    for (SupplierCertServe sc : supplier.getSupplierMatSe().getListSupplierCertSes()) {
                        supplierCertServeMapper.insertSelective(sc);
                    }
                }
            }

            /**
             *  供应商选择品目信息
             */
            if (supplier.getListSupplierItems() != null && supplier.getListSupplierItems().size() > 0) {
                // 先做删除操作
                supplierItemMapper.deleteBySupplierId(supplier.getId());
                for (SupplierItem st : supplier.getListSupplierItems()) {
                    SupplierItem item = supplierItemMapper.selectByPrimaryKey(st.getId());
                    if(item != null){
                        supplierItemMapper.updateByPrimaryKeySelective(st);
                    }else {
                        supplierItemMapper.insertSelective(st);
                    }
                }
            }

            /**
             *  供应商提交时的审核记录修改
             */
            List<SupplierAudit> supplierAudits = supplier.getSupplierAudits();
            if(!supplierAudits.isEmpty()){
                for (SupplierAudit supplierAudit : supplierAudits){
                    SupplierAudit audit = supplierAuditMapper.selectById(supplierAudit.getId());
                    if(audit != null){
                        supplierAuditMapper.updateByIdSelective(supplierAudit);
                    }
                }
            }

            List<RoleUser> roles = supplier.getUserRoles();
            if (roles.size() > 0) {
                for (RoleUser ur : roles) {
                    RoleUser us = new RoleUser();
                    us.setRoleId(ur.getRoleId());
                    us.setUserId(ur.getUserId());
                    List<RoleUser> queryByUserId = userMapper.queryByUserId(ur.getUserId(), ur.getRoleId());
                    if (queryByUserId.size() < 1) {
                        userMapper.saveUserRole(us);
                    }
                }
            }


//    	   if(supplier.getHistorys().size()>0){
//    		   for(SupplierHistory sh:supplier.getHistorys()){
//    			   supplierHistoryMapper.insertSelective(sh);
//    		   }
//    	   }
            if (supplier.getModifys() != null && supplier.getModifys().size() > 0) {
                for (SupplierModify sh : supplier.getModifys()) {
                    SupplierModify modify = supplierModifyMapper.selectById(sh.getId());
                    if (modify == null) {
                        supplierModifyMapper.insertSelective(sh);
                    } else {
                        supplierModifyMapper.updateModify(sh);
                    }

                }
            }
            User us = userMapper.queryNameAndNote(supplier.getUser().getLoginName(), supplier.getUser().getNetType());
            if (us == null) {
                saveUser(supplier.getUser());
            }

            Supplier supp = supplierSerice.selectById(supplier.getId());
            if (supp == null) {
                saveSupplier(supplier);
            } else {
                // 设置审核人
                supplier.setAuditor(supp.getAuditor());
                // 先做删除操作
                supplierMapper.deleteByPrimaryKey(supplier.getId());
                // 再做插入操作
                saveSupplier(supplier);
            }
        }
        synchRecordService.importNewSupplierRecord(new Integer(list.size()).toString());
    }

    /**
     * 〈简述〉保存用户
     * 〈详细描述〉
     *
     * @param user 用户
     * @author myc
     */
    private void saveUser(User user) {
        if (user != null) {
            userService.saveUser(user);
        }
    }

    /**
     * 〈简述〉保存供应商
     * 〈详细描述〉
     *
     * @param supplier 供应商
     * @author myc
     */
    private void saveSupplier(Supplier supplier) {
        if (supplier != null) {
            supplierSerice.saveSupplier(supplier);
        }
    }

    /**
     * 〈简述〉获取供应商list
     * 〈详细描述〉
     *
     * @param file 文件
     * @return
     * @author myc
     */
    private List<Supplier> getSupplier(final File file) {
        List<Supplier> supplierList = FileUtils.getSupplier(file, Supplier.class);
        return supplierList;
    }

    /**
     * Description:供应商退回修改导入外网
     *
     * @param file：文件, flag：标识 区分公示和退回修改
     * @author Easong
     * @version 2017/9/30
     * @since JDK1.7
     */
    @Override
    public void importInner(File file, String flag) {
        List<SupplierAuditFormBean> list = getSupplierFormBaean(file);
        for (SupplierAuditFormBean sb : list) {
            User user = sb.getUser();
            if (user != null) {
                User user2 = userMapper.queryById(user.getId());
                if (user2 != null) {
                    // 不导入密码，防止覆盖供应商新重置的密码
                    user.setPassword(null);
                    userMapper.updateByPrimaryKeySelective(user);
                }
            }
            // 退回修改供应商基本信息导入外网
            //supplierMapper.updateSupplierStatus(sb.getSupplierId(), sb.getStatus(), sb.getAuditDate());
            supplierMapper.updateByPrimaryKeySelectiveOfBack(sb.getSupplier());

            List<SupplierAuditNot> auditNots = sb.getSupplierAuditNot();
            for (SupplierAuditNot sa : auditNots) {
                SupplierAuditNot not = supplierAuditNotMapper.selectById(sa.getId());
                if (not == null) {
                    supplierAuditNotMapper.insertAcitive(sa);
                }
            }
            List<SupplierAudit> supplierAudits = sb.getSupplierAudits();
            if(sb.getSupplier() != null){
                supplierAuditMapper.deleteBySupplierId(sb.getSupplier().getId());
            }
            for (SupplierAudit sat : supplierAudits) {
                SupplierAudit audit = supplierAuditMapper.selectById(sat.getId());
                if (audit == null) {
                    supplierAuditMapper.inserActive(sat);
                } else {
                    supplierAuditMapper.updateByIdSelective(sat);
                }
            }
            List<SupplierModify> supplierModify = sb.getSupplierModify();
            for (SupplierModify sm : supplierModify) {
                SupplierModify smf = supplierModifyMapper.selectById(sm.getId());
                if (smf == null) {
                    supplierModifyMapper.add(sm);
                }
            }
            List<SupplierHistory> historys = sb.getSupplierHistory();
            for (SupplierHistory sh : historys) {
                SupplierHistory history = supplierHistoryMapper.queryById(sh.getId());
                if (history == null) {
                    supplierHistoryMapper.inserActive(sh);
                }
            }
            List<SupplierSignature> signatures = sb.getSupplierSignature();
            for (SupplierSignature ss : signatures) {
                SupplierSignature singature = supplierSignatureMapper.queryById(ss.getId());
                if (singature == null) {
                    supplierSignatureMapper.insertActive(ss);
                }
            }
            if ("publicity".equals(flag)) {
                // 获取审核意见
                SupplierAuditOpinion supplierAuditOpinion = sb.getSupplierAuditOpinions();
                if (supplierAuditOpinion != null) {
                    // 先判断表中是否有该数据
                    // 判断是不是原有的数据
                    if (StringUtils.isNotEmpty(supplierAuditOpinion.getId())) {
                        // 查询此条数据
                        SupplierAuditOpinion byPrimaryKey = supplierAuditOpinionMapper.findByPrimaryKey(supplierAuditOpinion.getId());
                        if (byPrimaryKey != null) {
                            // 更新数据
                            supplierAuditOpinionMapper.updateByPrimaryKeySelective(supplierAuditOpinion);
                        } else {
                            // 插入数据
                            supplierAuditOpinionMapper.insertSelective(supplierAuditOpinion);
                        }
                    }
                }
            }
        }
        // 公示信息导入（外网）
        if ("publicity".equals(flag)) {
            synchRecordService.synchBidding(null, new Integer(list.size()).toString(), synchro.util.Constant.SYNCH_PUBLICITY_SUPPLIER, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.IMPORT_SYNCH_PUBLICITY_SUPPLIER);
        } else{
            synchRecordService.synchBidding(null, new Integer(list.size()).toString(), synchro.util.Constant.DATA_TYPE_SUPPLIER_CODE, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.NEW_COMMIT_SUPPLIER_IMPORT);
        }
    }


    private List<SupplierAuditFormBean> getSupplierFormBaean(final File file) {
        List<SupplierAuditFormBean> supplierList = FileUtils.getSupplier(file, SupplierAuditFormBean.class);

        return supplierList;
    }

    @Override
    public void importTempSupplier(File file) {
        List<Supplier> list = getSupplier(file);
        for (Supplier supplier : list) {
            List<RoleUser> roles = supplier.getUserRoles();
            if (roles.size() > 0) {
                for (RoleUser ur : roles) {
                    RoleUser us = new RoleUser();
                    us.setRoleId(ur.getRoleId());
                    us.setUserId(ur.getUserId());
                    List<RoleUser> queryByUserId = userMapper.queryByUserId(ur.getUserId(), ur.getRoleId());
                    if (queryByUserId.size() < 1) {
                        userMapper.saveUserRole(us);
                    }
                }
            }

            User us = userMapper.queryNameAndNote(supplier.getUser().getLoginName(), supplier.getUser().getNetType());
            if (us == null) {
                saveUser(supplier.getUser());
            }
        }

    }

    @Override
    public void importBackSupplier(File file) {
        List<Supplier> list = getSupplier(file);
        for (Supplier supplier : list) {
//	    	   Supplier unSupplier = supplierSerice.selectById(supplier.getId());
//	    	   if(unSupplier==null){
            if (supplier.getListSupplierFinances().size() > 0) {
                for (SupplierFinance sf : supplier.getListSupplierFinances()) {
                    SupplierFinance unfinance = supplierFinanceMapper.selectByPrimaryKey(sf.getId());
                    if (unfinance == null) {
//	    				   supplierFinanceMapper.insertSelective(sf);
                    } else {
                        supplierFinanceMapper.updateByPrimaryKeySelective(sf);
                    }
                }
            }

            if (supplier.getTodoList() != null && supplier.getTodoList().size() > 0) {
                for (Todos to : supplier.getTodoList()) {
                    Todos td = todosMapper.selectByPrimaryKey(to.getId());
                    if (td == null) {
//	    				   todosMapper.insertSelective(to);
                    } else {
                        todosMapper.updateByPrimaryKey(to);
                    }
                }
            }

            if (supplier.getAttchList().size() > 0) {
                for (UploadFile uf : supplier.getAttchList()) {
                    UploadFile ufile = fileUploadMapper.findById(uf.getId(), "T_SES_SMS_SUPPLIER_ATTACHMENT");
                    if (ufile == null) {
//						   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//		    			   fileUploadMapper.saveFile(uf);
                    } else {
                        uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
                        fileUploadMapper.updateFileById(uf);
                    }

                }
            }

            if (supplier.getAddressList().size() > 0) {
                supplierAddressService.addList(supplier.getAddressList(), supplier.getId());
            }
            if (supplier.getBranchList().size() > 0) {
                for (SupplierBranch sb : supplier.getBranchList()) {
                    SupplierBranch branch = supplierBranchMapper.queryById(sb.getId());
                    if (branch == null) {
//	    				   supplierBranchMapper.insertSelective(sb);
                    } else {
                        supplierBranchMapper.updateByPrimaryKeySelective(sb);
                    }
                }
            }
            if (supplier.getListSupplierStockholders().size() > 0) {
                for (SupplierStockholder ss : supplier.getListSupplierStockholders()) {
                    SupplierStockholder stockholder = supplierStockholderMapper.selectByPrimaryKey(ss.getId());
                    if (stockholder == null) {
//	    				   supplierStockholderMapper.insertSelective(ss); 
                    } else {
                        supplierStockholderMapper.updateByPrimaryKeySelective(ss);
                    }
                }
            }
            if (supplier.getListSupplierAfterSaleDep().size() > 0) {
                for (SupplierAfterSaleDep sa : supplier.getListSupplierAfterSaleDep()) {
                    SupplierAfterSaleDep saleDep = supplierAfterSaleDepMapper.selectByPrimaryKey(sa.getId());
                    if (saleDep == null) {
//	    				   supplierAfterSaleDepMapper.insertSelective(sa);
                    } else {
                        supplierAfterSaleDepMapper.updateByPrimaryKeySelective(sa);
                    }
                }
            }
            if (supplier.getListSupplierTypeRelates().size() > 0) {
                supplierTypeRelateMapper.deleteBySupplierId(supplier.getId());
                for (SupplierTypeRelate str : supplier.getListSupplierTypeRelates()) {
                    SupplierTypeRelate relate = supplierTypeRelateMapper.selectByPrimaryKey(str.getId());
                    if (relate == null) {
                        supplierTypeRelateMapper.insertSelective(str);
                    } else {
//	    				   supplierTypeRelateMapper.updateByPrimaryKeySelective(str);
                    }
                }
            }
            if (supplier.getSupplierMatPro() != null) {
                SupplierMatPro matPro = supplierMatProMapper.getMatProBySupplierId(supplier.getId());
                if (matPro == null) {
                    supplierMatProMapper.insertSelective(supplier.getSupplierMatPro());
                } else {
                    supplierMatProMapper.updateByPrimaryKeySelective(supplier.getSupplierMatPro());
                }
                if (supplier.getSupplierMatPro().getListSupplierCertPros().size() > 0) {
                    for (SupplierCertPro sc : supplier.getSupplierMatPro().getListSupplierCertPros()) {
//	    		   				if(sc.getFileList().size()>0){
//	    		   				 for(UploadFile uf:sc.getFileList()){
//	    	    	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//	    	    	    			   fileUploadMapper.insertFile(uf);
//	    	    	    		   }
//	    		   				}
                        SupplierCertPro certPro = supplierCertProMapper.selectByPrimaryKey(sc.getId());
                        if (certPro == null) {
//	    		   					supplierCertProMapper.insertSelective(sc);
                        } else {
                            supplierCertProMapper.updateByPrimaryKeySelective(sc);
                        }

                    }
                }

            }

            if (supplier.getSupplierMatSell() != null) {
                SupplierMatSell matSell = supplierMatSellMapper.getMatSellBySupplierId(supplier.getId());
                if (matSell == null) {
                    supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
                } else {
                    supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
                }
                if (supplier.getSupplierMatSell().getListSupplierCertSells().size() > 0) {
                    for (SupplierCertSell sc : supplier.getSupplierMatSell().getListSupplierCertSells()) {
//	    				   if(sc.getFileList().size()>0){
//	  		   				 for(UploadFile uf:sc.getFileList()){
//	  	    	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//	  	    	    			   fileUploadMapper.insertFile(uf);
//	  	    	    		   }
//	  		   				}
                        SupplierCertSell certSell = supplierCertSellMapper.selectByPrimaryKey(sc.getId());
                        if (certSell == null) {
                            supplierCertSellMapper.insertSelective(sc);
                        }
                    }
                }
            }
            if (supplier.getSupplierMatEng() != null) {
                SupplierMatEng matEng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
                if (matEng == null) {
                    supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
                } else {
                    supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
                }
                if (supplier.getSupplierMatEng().getListSupplierAptitutes().size() > 0) {
                    for (SupplierAptitute sb : supplier.getSupplierMatEng().getListSupplierAptitutes()) {
                        SupplierAptitute ap = supplierAptituteMapper.selectByPrimaryKey(sb.getId());
                        if (ap == null) {
                            supplierAptituteMapper.insertSelective(sb);
                        } else {
                            supplierAptituteMapper.updateByPrimaryKeySelective(sb);
                        }

                    }
                }
                if (supplier.getSupplierMatEng().getListSupplierCertEngs().size() > 0) {
                    for (SupplierCertEng sce : supplier.getSupplierMatEng().getListSupplierCertEngs()) {
//	    				   if(sce.getFileList().size()>0){
//	  		   				 for(UploadFile uf:sce.getFileList()){
//	  	    	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//	  	    	    			   fileUploadMapper.insertFile(uf);
//	  	    	    		   }
//	  		   				}
                        SupplierCertEng certEng = supplierCertEngMapper.selectByPrimaryKey(sce.getId());
                        if (certEng == null) {
                            supplierCertEngMapper.insertSelective(sce);
                        } else {
                            supplierCertEngMapper.updateByPrimaryKeySelective(sce);
                        }

                    }
                }
                if (supplier.getSupplierMatEng().getListSupplierRegPersons().size() > 0) {
                    for (SupplierRegPerson sp : supplier.getSupplierMatEng().getListSupplierRegPersons()) {
                        SupplierRegPerson regPerson = supplierRegPersonMapper.selectByPrimaryKey(sp.getId());
                        if (regPerson == null) {
                            supplierRegPersonMapper.insertSelective(sp);
                        } else {
                            supplierRegPersonMapper.updateByPrimaryKeySelective(sp);
                        }

                    }
                }
                if (supplier.getSupplierMatEng().getListSupplierEngQuas().size() > 0) {
                	for (SupplierEngQua seq : supplier.getSupplierMatEng().getListSupplierEngQuas()) {
                		SupplierEngQua engQua = supplierEngQuaMapper.selectByPrimaryKey(seq.getId());
                		if (engQua == null) {
                			supplierEngQuaMapper.insertSelective(seq);
                		} else {
                			supplierEngQuaMapper.updateByPrimaryKeySelective(seq);
                		}
                		
                	}
                }
            }
            if (supplier.getSupplierMatSe() != null) {
                SupplierMatServe serve = supplierMatServeMapper.selectByPrimaryKey(supplier.getSupplierMatSe().getId());
                if (serve == null) {
                    supplierMatServeMapper.insertSelective(supplier.getSupplierMatSe());
                } else if (serve != null) {
                    supplierMatServeMapper.updateByPrimaryKey(supplier.getSupplierMatSe());
                }

                if (supplier.getSupplierMatSe().getListSupplierCertSes().size() > 0) {
                    for (SupplierCertServe sc : supplier.getSupplierMatSe().getListSupplierCertSes()) {
//						  if(sc.getFileList().size()>0){
//	 		   				 for(UploadFile uf:sc.getFileList()){
//	 	    	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//	 	    	    			   fileUploadMapper.insertFile(uf);
//	 	    	    		   }
//	 		   				}
                        SupplierCertServe certServe = supplierCertServeMapper.selectByPrimaryKey(sc.getId());
                        if (certServe == null) {
                            supplierCertServeMapper.insertSelective(sc);
                        } else {
                            supplierCertServeMapper.updateByPrimaryKeySelective(sc);
                        }

                    }
                }
            }
            if (supplier.getListSupplierItems() != null && supplier.getListSupplierItems().size() > 0) {
                supplierItemMapper.deleteBySupplierId(supplier.getId());
                for (SupplierItem st : supplier.getListSupplierItems()) {
//	    			   if(st.getFileList().size()>0){
//			   				 for(UploadFile uf:st.getFileList()){
//		    	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//		    	    			   fileUploadMapper.insertFile(uf);
//		    	    		   }
//			   				}
                    SupplierItem item = supplierItemMapper.selectByPrimaryKey(st.getId());
                    if (item == null) {
                        supplierItemMapper.insertSelective(st);
                    }

//	    			   else if(item!=null){
//	    				   supplierItemMapper.updateByPrimaryKeySelective(item);
//	    			   }

                }
            }

//	    	   if(supplier.getAttchList().size()>0){
//	    		   for(UploadFile uf:supplier.getAttchList()){
//	    			   uf.setTableName("T_SES_SMS_SUPPLIER_ATTACHMENT");
//	    			   fileUploadMapper.insertFile(uf);
//	    		   }
//	    	   }

            List<RoleUser> roles = supplier.getUserRoles();
            if (roles.size() > 0) {
                for (RoleUser ur : roles) {
                    RoleUser us = new RoleUser();
                    us.setRoleId(ur.getRoleId());
                    us.setUserId(ur.getUserId());
                    List<RoleUser> queryByUserId = userMapper.queryByUserId(ur.getUserId(), ur.getRoleId());
                    if (queryByUserId.size() < 1) {
                        userMapper.saveUserRole(us);
                    }
                }
            }


//	    	   if(supplier.getHistorys().size()>0){
//	    		   for(SupplierHistory sh:supplier.getHistorys()){
//	    			   supplierHistoryMapper.insertSelective(sh);
//	    		   }
//	    	   }
            if (supplier.getModifys().size() > 0) {
                for (SupplierModify sh : supplier.getModifys()) {
                    supplierModifyMapper.insertSelective(sh);
                }
            }
            User us = userMapper.queryNameAndNote(supplier.getUser().getLoginName(), supplier.getUser().getNetType());
            if (us == null) {
                saveUser(supplier.getUser());
            }

            Supplier supp = supplierSerice.selectById(supplier.getId());
            if (supp == null) {
//	        	   saveSupplier(supplier);
            } else {
                supplierMapper.updateByPrimaryKey(supplier);
            }


        }
        synchRecordService.importNewSupplierRecord(new Integer(list.size()).toString());
    }

    /**
     *
     * Description:查询注销供应商导入
     *
     * @author Easong
     * @version 2017/10/16
     * @param file
     * @since JDK1.7
     */
    @Override
    public void importLogoutSupplier(File file) {
        List<User> list = FileUtils.getBeans(file, User.class);
        if(list != null && !list.isEmpty()){
            for (User user : list){
                // 更新用户表基本信息
                userMapper.updateByPrimaryKeySelective(user);
                // 更新供应商表基本信息
                supplierMapper.updateByPrimaryKeySelective(user.getSupplier());
            }
        }
        if(list != null){
            synchRecordService.synchBidding(null, new Integer(list.size()).toString(), synchro.util.Constant.SYNCH_LOGOUT_SUPPLIER, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.IMPORT_SYNCH_LOGOUT_SUPPLIER);
        }
    }

}
