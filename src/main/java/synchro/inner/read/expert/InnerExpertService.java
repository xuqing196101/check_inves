package synchro.inner.read.expert;

import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 读取专家信息service
 * <详细描述>
 * @author   WangHuijie
 * @version  
 * @since
 * @see
 */
public interface InnerExpertService {
    
    /**
     *〈简述〉解析新注册的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param file 新注册的专家文件
     */
    public void readNewExpertInfo(final File file);
    
    /**
     *〈简述〉解析修改的专家信息
     *〈详细描述〉
     * @author WangHuijie
     * @param file 修改的专家文件
     */
    public void readModifyExpertInfo(final File file);
}
