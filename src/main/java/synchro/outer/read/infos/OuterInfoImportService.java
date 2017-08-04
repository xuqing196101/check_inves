package synchro.outer.read.infos;

import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 外网导入同步的信息
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface OuterInfoImportService {

    /**
     * 
     *〈简述〉外网导入信息
     *〈详细描述〉
     * @author myc
     */
    public void importInfos(final File file);
    
    /**
     * 
     * Description: 导入公告品目
     * 
     * @author zhang shubin
     * @data 2017年8月4日
     * @param 
     * @return
     */
    public void importArticleCategory(final File file);
}
