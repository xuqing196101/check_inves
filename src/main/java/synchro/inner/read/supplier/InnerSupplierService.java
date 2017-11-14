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
    
    
    public void importInner(final File file, String flag);
    
    
    public void importTempSupplier(final File file);
    
    
    public void importBackSupplier(final File file);

    /**
     *
     * Description:查询注销供应商导入
     *
     * @author Easong
     * @version 2017/10/16
     * @param startTime
     * @param endTime
     * @since JDK1.7
     */
    public void importLogoutSupplier(final File file);


    /**
     * 
     * <简述>导入供应商 
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-10上午11:00:57
     * @param f
     */
	public void importSupplierLevel(File f);


	/**
	 * 
	 * <简述>查询供应商等级信息导出 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-10下午4:51:19
	 * @param startTime
	 * @param endTime
	 */
	public void selectSupplierLevelOfExport(String startTime, String endTime);

}
