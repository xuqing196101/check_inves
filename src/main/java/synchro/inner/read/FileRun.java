package synchro.inner.read;

import java.io.File;

import synchro.inner.read.expert.InnerExpertService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.util.FileUtils;
import synchro.util.SpringBeanUtil;

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
public class FileRun implements Runnable {
    
    /** 属性 **/
    private  File file = null;
    
    /** 获取供应商service **/
    private InnerSupplierService supplierService;
    
    /** 获取专家service **/
    private InnerExpertService expertService;
    

    /** 构造方法 **/
    public FileRun(final File file){
        this.file = file;
        this.supplierService = SpringBeanUtil.getBean(InnerSupplierService.class);
        this.expertService = SpringBeanUtil.getBean(InnerExpertService.class);
    }
    
    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (file != null){
            if (file.getName().contains(FileUtils.C_SUPPLIER_FILENAME)){
                supplierService.readNewSupplierInfo(file);
            }
            if (file.getName().contains(FileUtils.M_SUPPLIER_FILENAME)){
                
            }
            if (file.getName().contains(FileUtils.C_EXPERT_FILENAME)){
                expertService.readNewExpertInfo(file);
            }
            if (file.getName().contains(FileUtils.M_EXPERT_FILENAME)){
                expertService.readModifyExpertInfo(file);
            }
        }
    }

}
