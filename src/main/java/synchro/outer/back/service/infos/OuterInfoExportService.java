package synchro.outer.back.service.infos;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 外网信息同步接口
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface OuterInfoExportService {

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
