/**
 * 
 */
package bss.service.ppms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import bss.model.ppms.Packages;
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
	  * 
	  * Description:根据参数 获取项目状态
	  * 
	  * @author YangHongLiang
	  * @version 2017-5-24
	  * @param projectId招标项目id
	  * @param supplierId供应商id
	  * @return
	  */
	  List<String> getBidFinish(String projectId,String supplierId);


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
    public List<Packages> getPackageIds(String projectId);


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


    public void updateResult(HashMap<String, Object> stMap);
    
    /**
     *〈简述〉
     * 更改SaleTender的移除状态
     *〈详细描述〉
     * @author Dell
     * @param map
     */
    void removeSaleTender(Map<String, Object> map);
    
    /**
     *〈简述〉
     * 根据项目id查询所有saleTender
     *〈详细描述〉
     * @author WangHuijie
     * @param projectId
     * @return
     */
    List<SaleTender> selectListByProjectId(String projectId);
    
    /**
     *〈简述〉
     * 根据包id和供应商id设置经济技术总分
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    void editSumScore(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉删除发售标书中的供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param supplierId
     * @param packagesId
     */
    String delSale(String supplierId, String packagesId);


     /**
     *〈简述〉插入供应商排名
     *〈详细描述〉
     * @author Ye Maolin
     * @param ranMap
     */
    public void updateRank(HashMap<String, Object> ranMap);
    
    /**
     *〈简述〉插入供应商是否到场信息
     *〈详细描述〉
     * @author Song Biao Wei
     * @param void
     */
    public void batchUpdate(List<SaleTender> stList);
    
    List<SaleTender> finds(SaleTender saleTender);
    /**
     * 
     * Description:根据供应商id 和项目id获取参与项目的有效包数据
     * 
     * @author YangHongLiang
     * @version 2017-5-25
     * @param supplierId
     * @param projectId
     * @return
     */
    List<SaleTender> findPackages(String supplierId,String projectId);
    
    /**
     * 
     *〈根据预研项目ID和包ID查询供应商〉
     *〈详细描述〉
     * @author FengTian
     * @param map
     * @return
     */
    List<SaleTender> getAdPackegeSuppliers(HashMap<String, Object> map);
}
