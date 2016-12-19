package bss.service.prms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.ReviewFirstAuditMapper;
import bss.model.prms.ReviewFirstAudit;
import bss.service.prms.ReviewFirstAuditService;
@Service("reviewFirstAuditService")
public class ReviewFirstAuditServiceImpl implements ReviewFirstAuditService {

	@Autowired
	private ReviewFirstAuditMapper mapper;
	
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月20日 下午5:47:44  
	  * @Description: TODO 新增
	  * @param @param record      
	  * @return void
	 */
	public void save(ReviewFirstAudit record){
	  record.setIsBack(0);
		mapper.insert(record);
	}

	    /**
	     * 
	      * @Title: selectList
	      * @author ShaoYangYang
	      * @date 2016年10月20日 下午5:43:49  
	      * @Description: TODO 根据项目id和包id查询集合
	      * @param @param map
	      * @param @return      
	      * @return List<ReviewFirstAudit>
	     */
	  public  List<ReviewFirstAudit>  selectList(Map<String,Object> map){
		  return mapper.selectList(map);
	  }
	    /**
	     * 
	      * @Title: delete
	      * @author ShaoYangYang
	      * @date 2016年10月20日 下午5:44:27  
	      * @Description: TODO 根据项目id和包id删除
	      * @param @param map      
	      * @return void
	     */
	    public  void delete(Map<String,Object> map){
	    	mapper.delete(map);
	    }

      @Override
      public void update(ReviewFirstAudit record) {
        mapper.update(record);
      }
}
