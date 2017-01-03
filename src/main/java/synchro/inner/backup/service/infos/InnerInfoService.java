package synchro.inner.backup.service.infos;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>导出信息服务
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface InnerInfoService {

    /**
     * 
     *〈简述〉导出已经发布的信息
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param synchDate 同步时间
     */
    public void backUpInfos(String startTime, String endTime, Date synchDate);
}
