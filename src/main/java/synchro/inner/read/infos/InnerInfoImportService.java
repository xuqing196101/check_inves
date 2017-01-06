package synchro.inner.read.infos;

import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>内外信息导入
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface InnerInfoImportService {
    
    /**
     * 
     *〈简述〉内网信息导入
     *〈详细描述〉
     * @author myc
     * @param file 
     */
    public void importInfos(final File file);
}
