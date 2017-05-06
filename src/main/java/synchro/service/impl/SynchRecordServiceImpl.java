package synchro.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
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
    public void commitSupplierRecord(String content, Date synchDate) {
        /*SynchRecord sr  = packSynchRecord(Constant.DATA_TYPE_SUPPLIER_REG +"", Constant.OPER_TYPE_EXPORT, 
                        Constant.NEW_COMMIT_SUPPLIER + content, synchDate);
        mapper.save(sr);*/
      DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_SUPPLIER_CODE);
      if (dd != null && StringUtils.isNotBlank(dd.getId())){
        SynchRecord sr  = packSynchRecord(dd.getId() +"", Constant.OPER_TYPE_EXPORT, 
            Constant.NEW_COMMIT_SUPPLIER + content, synchDate);
        mapper.save(sr);
      }
    }

    @Override
    public void commitExpertRecord(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_EXPERT_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId() +"", Constant.OPER_TYPE_EXPORT,
                    Constant.NEW_COMMIT_EXPERT + content);
            mapper.save(sr);
        }
    }

    /**
     * 
     * @see synchro.record.service.SynchRecordService#newExpertRecord(java.lang.String)
     */
    @Override
    public void backNewExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_REG +"", Constant.OPER_TYPE_EXPORT, 
                        Constant.NEW_COMMIT_EXPERT + content);
        mapper.save(sr);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#modifyExpertRecord(java.lang.String)
     */
    @Override
    public void backModifyExpertRecord(String content) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_EXPERT_MODIFY +"", Constant.OPER_TYPE_EXPORT, 
                Constant.MODIFY_COMMIT_EXPERT + content);
        mapper.save(sr);
    }

    /**
     * 
     * @see synchro.service.SynchRecordService#importSupplierRecord(java.lang.String)
     */
    @Override
    public void importNewSupplierRecord(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_SUPPLIER_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId() +"", Constant.OPER_TYPE_IMPORT,
                    Constant.NEW_COMMIT_SUPPLIER_IMPORT + content);
            mapper.save(sr);
        }
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importModifySupplierRecord(java.lang.String)
     */
    @Override
    public void importModifySupplierRecord(String content,Date sychDate) {
        SynchRecord sr  = getSynchRecord(Constant.DATA_TYPE_SUPPLIER_MODIFY +"", Constant.OPER_TYPE_IMPORT, 
                Constant.NEW_COMMIT_SUPPLIER + content);
        mapper.save(sr);
    }

    /**
     * 
     * @see synchro.service.SynchRecordService#importNewExpertRecord(java.lang.String)
     */
    @Override
    public void importNewExpertRecord(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_EXPERT_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId() +"", Constant.OPER_TYPE_IMPORT,
                    Constant.NEW_COMMIT_EXPERT + content);
            mapper.save(sr);
        }
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importModifyExpertRecord(java.lang.String)
     */
    @Override
    public void importModifyExpertRecord(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_EXPERT_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId() +"", Constant.OPER_TYPE_IMPORT,
                    Constant.MODIFY_COMMIT_EXPERT + content);
            mapper.save(sr);
        }
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#backupInfos(java.lang.String)
     */
    @Override
    public void backupInfos(Date date, String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_INFOS_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = packSynchRecord(dd.getId(), Constant.OPER_TYPE_EXPORT, 
                    Constant.CREATED_COMMIT_INFOS + content, date);
            mapper.save(sr);
        }
    }
    /**
     * 实现 保存信息数据
     * @param date 创建时间
     * @param content 导出数据的数量
     * @param dateCode 数据字典的类型
     * @param type 导入还是导出
     * @param commitInfos 信息描述
     */
    @Override
	public void synchBidding(Date date, String content,String dateCode,int type,String commitInfos) {
		// TODO Auto-generated method stub
    	 DictionaryData dd = DictionaryDataUtil.get(dateCode);
         if (dd != null && StringUtils.isNotBlank(dd.getId())){
             SynchRecord sr  = packSynchRecord(dd.getId(), type, 
            		 commitInfos + content, date);
             mapper.save(sr);
         }
	}
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importInfos(java.lang.String)
     */
    @Override
    public void importInfos(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_INFOS_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId(), Constant.OPER_TYPE_IMPORT, 
                    Constant.CREATED_COMMIT_INFOS + content);
            mapper.save(sr);
        }
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#getSynchTime(java.lang.String)
     */
    @Override
    public String getSynchTime(Integer operType,String dataType) {
        return mapper.getSynchTime(operType, dataType);
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#backupAttach(java.lang.String)
     */
    @Override
    public void backupAttach(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_ATTACH_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId(), Constant.OPER_TYPE_EXPORT, 
                    Constant.CREATED_COMMIT_ATTACH + content);
            mapper.save(sr);
        }
    }
    
    /**
     * 
     * @see synchro.service.SynchRecordService#importAttach(java.lang.String)
     */
    @Override
    public void importAttach(String content) {
        DictionaryData dd = DictionaryDataUtil.get(Constant.DATA_TYPE_ATTACH_CODE);
        if (dd != null && StringUtils.isNotBlank(dd.getId())){
            SynchRecord sr  = getSynchRecord(dd.getId(), Constant.OPER_TYPE_IMPORT, 
                    Constant.CREATED_COMMIT_ATTACH + content);
            mapper.save(sr);
        }
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
    private SynchRecord getSynchRecord(String dataType, Integer operType, String desc){
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
    private SynchRecord packSynchRecord(String dataType, Integer operType, String desc, Date date){
        SynchRecord sr = new SynchRecord();
        sr.setDataType(dataType);
        sr.setOperType(operType);
        sr.setSynchDate(new Date());
        sr.setDescriptions(desc);
        return sr;
    }

	
    
    
}
