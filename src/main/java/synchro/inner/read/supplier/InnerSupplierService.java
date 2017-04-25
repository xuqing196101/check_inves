package synchro.inner.read.supplier;

import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface InnerSupplierService {
    
    /**
     * 
     *〈简述〉解析新注册的供应商信息
     *〈详细描述〉
     * @author myc
     * @param file 新注册的供应商文件
     */
    public void importSupplierInfo(final File file);
    
    
    public void immportInner(final File file);
    
    
    public void importTempSupplier(final File file);
    
    
    public void importBackSupplier(final File file);
}
