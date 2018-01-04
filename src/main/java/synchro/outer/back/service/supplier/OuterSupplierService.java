package synchro.outer.back.service.supplier;

import java.io.File;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>供应商信息同步
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface OuterSupplierService {
    
    /**
     * 
     *〈简述〉获取提交的供应商
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param synchDate 同步时间
     */
    public void exportCommitSupplier(String startTime, String endTime, Date synchDate);
    
    /**
     * 
    * @Title: modify
    * @Description: 备份修退回修改的供应商
    * author: Li Xiaoxiao 
    * @param @param startTime
    * @param @param endTime
    * @param @param synchDate     
    * @return void     
    * @throws
     */
    public void modify(String startTime, String endTime, Date synchDate);
    
    
    
    /**
     * 
    * @Title: auditPass
    * @Description: 审核通过 
    * author: Li Xiaoxiao 
    * @param @param startTime
    * @param @param endTime     
    * @return void     
    * @throws
     */
    public void auditPass(String startTime, String endTime);
    
    
    public void tempSupplier(String startTime, String endTime);
    
    
    
    public void backSupplierExport(String startTime, String endTime);

    /**
     *
     * Description: 按时间导出公示供应商
     *
     * @author Easong
     * @version 2017/7/9
     * @param startTime
     * @param endTime
     * @since JDK1.7
     */
    void selectSupByPublictyOfExport(String startTime, String endTime);

    /**
     *
     * Description:查询注销供应商导出
     *
     * @author Easong
     * @version 2017/10/16
     * @param startTime
     * @param endTime
     * @since JDK1.7
     */
    void selectLogoutSupplierOfExport(String startTime, String endTime);

    /**
     * 
     * <简述> 查询供应商等级导出
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-3下午4:34:31
     * @param startTime
     * @param endTime
     */
	void selectSupplierLevelOfExport(String startTime, String endTime);

	/**
	 * 外网导入供应商等级
	 * <简述> 
	 *
	 * @author Jia Chengxiang
	 * @dateTime 2017-11-10下午5:02:33
	 * @param f
	 */
	public void importSupplierLevel(File f);

	/**
	 *〈简述〉供应商复核结果导入外网
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param f
	 */
	public void importCheckResult(File f);

	/**
	 *〈简述〉供应商实地考察结果导入外网
	 *〈详细描述〉
	 * @author Ye Maolin
	 * @param f
	 */
	public void importInvestResult(File f);
}
