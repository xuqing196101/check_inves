package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.service.ems.ExpertCategoryService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
@Service("expertCategoryService")
public class ExpertCategoryServiceImpl implements ExpertCategoryService {
	
	@Autowired
	private ExpertCategoryMapper mapper;
	@Autowired
	private  DictionaryDataMapper dictionaryDataMapper;
	 /**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 上午10:24:33  
      * @Description: TODO 保存
      * @param @param expertId
      * @param @return      
      * @return List<ExpertCategory>
     */
	@Override
	public void save(Expert expert,String ids,String typeId) {
	    if(ids!=null && StringUtils.isNotEmpty(ids)){
            String[] code = ids.split(",");
            ExpertCategory expertCategory = new ExpertCategory();
            //循环品目id集合
            for (String id : code) {
                //根据编码查询id
                //String id = DictionaryDataUtil.getId(string);
                expertCategory.setCategoryId(id);
                expertCategory.setExpertId(expert.getId());
                expertCategory.setTypeId(typeId);
                //逐条保存
                mapper.insert(expertCategory);
            }
        }
		
	}
	
	/**
     * 
      * @Title: selectListByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 上午10:24:33  
      * @Description: TODO 根据专家id查询中间表集合
      * @param @param expertId
      * @param @return      
      * @return List<ExpertCategory>
     */
	@Override
	public List<ExpertCategory> getListByExpertId(String expertId, String typeId) {
		List<ExpertCategory> list = mapper.selectListByExpertId(expertId, typeId);
		return list;
	}
	
	@Override
    public List<ExpertCategory> getListByExpertId(String expertId, String typeId, Integer pageNum) {
	    if (pageNum != null) {
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        }
        List<ExpertCategory> list = mapper.selectListByExpertId(expertId, typeId);
        return list;
    }
	
	/**
     *〈简述〉
     * 根据专家id和品目id删除
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
	@Override
    public void deleteByMap(Map<String, Object> map) {
	    mapper.deleteByMap(map);
    }
	
	@Override
	public List<ExpertCategory> findByExpertId(String map) {
		return mapper.findByExpertId(map);
	}

    /**
     * 
      * @Title: deleteByExpertId
      * @author ShaoYangYang
      * @date 2016年9月28日 下午6:37:48  
      * @Description: TODO 根据专家id删除数据
      * @param @param expertId      
      * @return void
     */
	@Override
   public void deleteByExpertId(String expertId){
		if(expertId!=null && StringUtils.isNotEmpty(expertId)){
			mapper.deleteByExpertId(expertId);
		}
	}
	
	/**
	 * 
	 * @see ses.service.ems.ExpertCategoryService#getExpertCategory(java.lang.String, java.lang.String)
	 */
    @Override
    public ExpertCategory getExpertCategory(String expertId, String categoryId) {
        
        return mapper.getCategoryByExpertId(expertId, categoryId);
    }
	
	
	
}
 