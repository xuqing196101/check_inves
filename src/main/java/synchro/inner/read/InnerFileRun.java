package synchro.inner.read;

import java.io.File;

import synchro.inner.read.att.InnerAttachService;
import synchro.inner.read.infos.InnerInfoImportService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.util.Constant;
import synchro.util.FileUtils;
import synchro.util.OperAttachment;
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
public class InnerFileRun implements Runnable {
    
    /** 属性 **/
    private  File file = null;
    
    /** 信息文件导入service **/
    private InnerInfoImportService infoService;
    
    /** 附件导入service **/
    private InnerAttachService attachService;
    
    /** 供应商service **/
    private InnerSupplierService supplierService;

    /** 构造方法 **/
    public InnerFileRun(final File file){
        this.file = file;
        this.infoService = SpringBeanUtil.getBean(InnerInfoImportService.class);
        this.attachService = SpringBeanUtil.getBean(InnerAttachService.class);
        this.supplierService = SpringBeanUtil.getBean(InnerSupplierService.class);
    }
    
    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (file != null){
            if (file.getName().contains(FileUtils.C_INFOS_FILENAME)){
                infoService.importInfos(file);
            }
            if (file.getName().contains(FileUtils.C_ATTACH_FILENAME)){
                attachService.importAttach(file);
            }
            if (file.getName().contains(FileUtils.C_SUPPLIER_FILENAME)){
                supplierService.importSupplierInfo(file);
            }
            if (file.isDirectory()){
                if (file.getName().equals(Constant.ATTACH_FILE_TENDER)){
                    OperAttachment.moveFolder(file);
                }
            }
        }
    }

}
