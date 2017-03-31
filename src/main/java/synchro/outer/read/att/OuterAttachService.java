package synchro.outer.read.att;

import java.io.File;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>附件
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface OuterAttachService {
    
    /**
     * 
     *〈简述〉导入附件
     *〈详细描述〉
     * @author myc
     */
    public void importAttach(final File file);
    /**
     * 
    * @Title: importSupplierAttach
    * @Description: 导入供应商附件 
    * author: Li Xiaoxiao 
    * @param @param file     
    * @return void     
    * @throws
     */
    public void importSupplierAttach(final File file);
    /**
     * 
    * @Title: importExpertAttach
    * @Description: 导入转件附件
    * author: Li Xiaoxiao 
    * @param @param file     
    * @return void     
    * @throws
     */
    public void importExpertAttach(final File file);
}
