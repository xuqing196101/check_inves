package ses.service.sms;

import java.util.List;

import ses.model.sms.ImportRecommend;

public interface ImportRecommendService {
	
	/**
	 * @Title: register
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:01:31  
	 * @Description: 进口代理商登记 
	 * @param @param ir      
	 * @return void
	 */
	void register(ImportRecommend ir);
	
	/**
	 * @Title: update
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:01:47  
	 * @Description: 进口代理商修改
	 * @param @param ir      
	 * @return void
	 */
	void update(ImportRecommend ir);
	
	/**
	 * @Title: findById
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:01:59  
	 * @Description: 主键查询 
	 * @param @param id
	 * @param @return      
	 * @return ImportRecommend
	 */
	ImportRecommend findById(String id);
	
	/** 
	 * @Title: selectByFsInfo
	 * @author Song Biaowei
	 * @date 2016-10-4 上午11:02:11  
	 * @Description: 进口代理商列表 
	 * @param @param ir
	 * @param @param page
	 * @param @return      
	 * @return List<ImportRecommend>
	 */
	List<ImportRecommend> selectByRecommend(ImportRecommend ir,Integer page);
	
}
