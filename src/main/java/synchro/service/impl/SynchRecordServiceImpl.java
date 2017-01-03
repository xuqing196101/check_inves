package synchro.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import synchro.dao.SynchRecordMapper;
import synchro.model.SynchRecord;
import synchro.service.SynchRecordService;
import synchro.util.Constant;

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
    
    
    
    /** 记录表mapper **/
    @Autowired
    private SynchRecordMapper mapper;
    
    /**
     * 
     * @see synchro.record.service.SynchRecordService#newSupplierRecord(java.lang.String)
     */
    @Override
    public void backNewSupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_SUPPLIER_REG, Constant.OPER_TYPE_EXPORT, 
                        Constant.NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.record.service.SynchRecordService#newExpertRecord(java.lang.String)
     */
    @Override
    public void backNewExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_REG, Constant.OPER_TYPE_EXPORT, 
                        Constant.NEW_COMMIT_EXPERT + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#modifySupplierRecord(java.lang.String)
     */
    @Override
    public void backModifySupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_SUPPLIER_AUDIT, Constant.OPER_TYPE_EXPORT,
                Constant.NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#modifyExpertRecord(java.lang.String)
     */
    @Override
    public void backModifyExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_MODIFY, Constant.OPER_TYPE_EXPORT, 
                Constant.MODIFY_COMMIT_EXPERT + content);
        mapper.save(sr);
    }

    /**
     * 
     * @see synchro.service.SynchRecordService#importSupplierRecord(java.lang.String)
     */
    @Override
    public void importNewSupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_SUPPLIER_REG, Constant.OPER_TYPE_IMPORT, 
                Constant.NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importModifySupplierRecord(java.lang.String)
     */
    @Override
    public void importModifySupplierRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_SUPPLIER_MODIFY, Constant.OPER_TYPE_IMPORT, 
                Constant.NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }

    /**
     * 
     * @see synchro.service.SynchRecordService#importNewExpertRecord(java.lang.String)
     */
    @Override
    public void importNewExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_REG, Constant.OPER_TYPE_IMPORT, 
                        Constant.NEW_COMMIT_EXPERT + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importModifyExpertRecord(java.lang.String)
     */
    @Override
    public void importModifyExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_MODIFY, Constant.OPER_TYPE_IMPORT, 
                         Constant.MODIFY_COMMIT_EXPERT + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#backupInfos(java.lang.String)
     */
    @Override
    public void backupInfos(Date date, String content) {
        SynchRecord sr  = packSynchRecord(Constant.DATA_TYPE_INFOS_CREATED, Constant.OPER_TYPE_EXPORT, 
                        Constant.CREATED_COMMIT_INFOS + content, date);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importInfos(java.lang.String)
     */
    @Override
    public void importInfos(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_INFOS_CREATED, Constant.OPER_TYPE_IMPORT, 
                        Constant.CREATED_COMMIT_INFOS + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#getSynchTime(java.lang.Integer)
     */
    @Override
    public String getSynchTime(Integer operType,Integer dataType) {
        return mapper.getSynchTime(operType, dataType);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#backupAttach(java.lang.String)
     */
    @Override
    public void backupAttach(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_ATTACH, Constant.OPER_TYPE_EXPORT, 
                Constant.CREATED_COMMIT_ATTACH + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importAttach(java.lang.String)
     */
    @Override
    public void importAttach(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_ATTACH, Constant.OPER_TYPE_IMPORT, 
                Constant.CREATED_COMMIT_ATTACH + content);
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
        return packSynchRecord(dataType, operType, desc, new Date());
    }
    
    /**
     * 
     *〈简述〉封装对象
     *〈详细描述〉
     * @author myc
     * @param dataType 数据类型
     * @param operType 操作类型
     * @param desc 描述
     * @param date 时间
     * @return
     */
    private SynchRecord packSynchRecord(Integer dataType, Integer operType, String desc, Date date){
        SynchRecord sr = new SynchRecord();
        sr.setDataType(dataType);
        sr.setOperType(operType);
        sr.setSynchDate(new Date());
        sr.setDescriptions(desc);
        return sr;
    }
    
    
}
