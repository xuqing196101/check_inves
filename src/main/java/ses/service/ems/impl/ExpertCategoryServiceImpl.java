package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.service.ems.ExpertCategoryService;
import ses.util.DictionaryDataUtil;
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
	public void save(Expert expert,String ids) {
	    if(ids!=null && StringUtils.isNotEmpty(ids)){
            String[] code = ids.split(",");
            ExpertCategory expertCategory = new ExpertCategory();
            //循环品目id集合
            for (String string : code) {
                //根据编码查询id
                String id = DictionaryDataUtil.getId(string);
                expertCategory.setCategoryId(id);
                expertCategory.setExpertId(expert.getId());
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
	public List<String> getListByExpertId(String expertId) {
		
		List<ExpertCategory> list = mapper.selectListByExpertId(expertId);
		List<String> dataList = new ArrayList<>();
		//关联表的所有id  再根据id查询出所有的code
		for (ExpertCategory expertCategory : list) {
			DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(expertCategory.getCategoryId());
			dataList.add(data.getCode());
		}
		return dataList;
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
}
 