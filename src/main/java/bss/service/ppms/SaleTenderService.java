/**
 * 
 */
package bss.service.ppms;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import bss.model.ppms.Project;
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
}
