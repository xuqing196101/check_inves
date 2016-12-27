package synchro.dao;

import synchro.model.SynchRecord;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>数据同步记录表
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface SynchRecordMapper {

    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param synchRecord {@link SynchRecord}
     */
    public void save(SynchRecord synchRecord);
}
