package synchro.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import synchro.dao.SynchRecordMapper;
import synchro.model.SynchRecord;
import synchro.service.SynchRecordService;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 操作记录
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class SynchRecordServiceImpl implements SynchRecordService {
    
    /** 数据类型-供应商注册 **/
    private final static Integer DATA_TYPE_SUPPLIER_REG = 1;
    /** 数据类型-供应商修改 **/
    private final static Integer DATA_TYPE_SUPPLIER_MODIFY = 2;
    
    /** 数据类型-供应商修改 **/
    private final static Integer DATA_TYPE_SUPPLIER_AUDIT = 5;
    
    /** 操作类型-导出 **/
    private final static Integer OPER_TYPE_EXPORT = 1;
    /** 操作类型-导入**/
    private final static Integer OPER_TYPE_IMPORT = 2;
    /** 新提交供应商描述 **/
    private final static String NEW_COMMIT_SUPPLIER = "新提交供应商数量:";
    
    /** 数据类型-专家注册 **/
    private final static Integer DATA_TYPE_EXPERT_REG = 3;
    
    /** 数据类型-专家修改 **/
    private final static Integer DATA_TYPE_EXPERT_MODIFY = 4;
    
    /** 数据类型-专家导入 **/
    private final static Integer DATA_TYPE_EXPERT_IMPORT = 6;
    
    /** 新提交专家描述 **/
    private final static String NEW_COMMIT_EXPERT = "新提交专家数量:";
    
    /** 修改专家描述 **/
    private final static String MODIFY_COMMIT_EXPERT = "修改专家数量:";
    
    /** 导入专家描述 **/
    private final static String IMPORT_INTO_EXPERT = "导入专家数量:";
    
    /** 记录表mapper **/
    @Autowired
    private SynchRecordMapper mapper;
    
    /**
     * 
     * @see synchro.record.service.SynchRecordService#newSupplierRecord(java.lang.String)
     */
    @Override
    public void backNewSupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_SUPPLIER_REG, OPER_TYPE_EXPORT, NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.record.service.SynchRecordService#newExpertRecord(java.lang.String)
     */
    @Override
    public void backNewExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_EXPERT_REG, OPER_TYPE_EXPORT, NEW_COMMIT_EXPERT + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#modifySupplierRecord(java.lang.String)
     */
    @Override
    public void backModifySupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_SUPPLIER_AUDIT, OPER_TYPE_EXPORT, NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#modifyExpertRecord(java.lang.String)
     */
    @Override
    public void backModifyExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_EXPERT_MODIFY, OPER_TYPE_EXPORT, MODIFY_COMMIT_EXPERT + content);
        mapper.save(sr);
    }

    /**
     * 
     * @see synchro.service.SynchRecordService#importSupplierRecord(java.lang.String)
     */
    @Override
    public void importSupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_SUPPLIER_MODIFY, OPER_TYPE_IMPORT, NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importExpertRecord(java.lang.String)
     */
    @Override
    public void importExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(DATA_TYPE_EXPERT_IMPORT, OPER_TYPE_IMPORT, IMPORT_INTO_EXPERT + content);
        mapper.save(sr);
    }

    /**
     * 
     *〈简述〉组装对象
     *〈详细描述〉
     * @author myc
     * @param dataType 数据类型
     * @param operType 操作类型
     * @param desc 描述
     * @return
     */
    private SynchRecord getSynchRecord(Integer dataType, Integer operType, String desc){
        SynchRecord sr = new SynchRecord();
        sr.setDataType(dataType);
        sr.setOperType(operType);
        sr.setSynchDate(new Date());
        sr.setDescriptions(desc);
        return sr;
    }
    
}
