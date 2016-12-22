/**
 * 
 */
package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bss.model.ppms.SaleTender;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月20日下午2:02:34
 * @since  JDK 1.7
 */
public interface SaleTenderService   {
	/**
	 * @Description:插入记录
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午2:04:10  
	 * @param @param saleTender      
	 * @return void
	 */
	public String insert(SaleTender saleTender);
	

	/**   
	 * @Description:上传文件
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午3:55:49  
	 * @param @param bill
	 * @param @param voucher
	 * @param @param project      
	 * @return void     
	 */
	String upload(String projectid,String saleId,String statusBid);
	
	/**
	 * @Description:list
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月20日 下午4:42:52  
	 * @param @param saleTender
	 * @param @return      
	 * @return List<SaleTender>
	 */
	List<SaleTender> list(SaleTender saleTender,Integer pagenum);
	
	/**
	 * @Description:下载
	 *
	 * @author Wang Wenshuai
	 * @version 2016年10月21日 上午10:01:10  
	 * @param @param projectId      
	 * @return void
	 */
	void download(String projectId,String Id);
	
	/**
	 *〈简述〉条件查询
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param saleTender
	 * @return
	 */
	List<SaleTender> find(SaleTender saleTender);


    /**
     *〈简述〉更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param std
     */
    public void update(SaleTender std);
    
    /**
     *〈简述〉发售标书列表加上包名
     *〈详细描述〉
     * @author Song Biaowei
     * @param stList
     * @return
     */
    public List<SaleTender> getPackageNames(List<SaleTender> stList);
    
    /**
    * @Title: getPackegeSupplier
    * @author Shen Zhenfei 
    * @date 2016-12-12 下午4:51:54  
    * @Description: 根据项目包名，获取供应商
    * @param @return      
    * @return List<SaleTender>
     */
    public List<SaleTender> getPackegeSupplier(SaleTender record);
    
    
    /**
    * @Title: getPackegeSuppliers
    * @author Shen Zhenfei 
    * @date 2016-12-18 上午11:10:20  
    * @Description: 根据项目包名，获取对应的供应商
    * @param @param record
    * @param @return      
    * @return SaleTender
     */
    public List<SaleTender> getPackegeSuppliers(SaleTender record);
    
    /**
     *〈简述〉查询项目下面包信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param projectId
     * @return
     */
    public List<String> getPackageIds(String projectId);


    /**
     *〈简述〉根据供应商下载标书
     *〈详细描述〉
     * @author Ye Maolin
     * @param projectId
     * @param request
     * @param supplierId
     */
    public HashMap<String, String> downloadBidFile(String projectId, HttpServletRequest request, String supplierId);


    /**
     *〈简述〉根据id、projectId、supplierId查询
     *〈详细描述〉
     * @author Ye Maolin
     * @param saleTender
     */
    public List<SaleTender> findByCon(SaleTender saleTender);
    
}
