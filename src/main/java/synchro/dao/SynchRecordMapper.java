package synchro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 
     *〈简述〉根据同步类型同步最近一次的的同步时间
     *〈详细描述〉
     * @author myc
     * @param operType 操作类型
     * @param dataType 同步类型
     * @return
     */
    public String getSynchTime(@Param("operType") Integer operType,@Param("dataType")String dataType);

    /**
     * 
     *〈简述〉获取同步记录
     *〈详细描述〉
     * @author myc
     * @param operType 操作类型
     * @return
     */
    public List<SynchRecord> getSynchRecordByOperType(@Param("operType")Integer operType);
}
