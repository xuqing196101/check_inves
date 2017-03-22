package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.service.bms.CategoryService;
import ses.service.bms.EngCategoryService;
import ses.service.ems.ExpertCategoryService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
@Service("expertCategoryService")
public class ExpertCategoryServiceImpl implements ExpertCategoryService {
	
	@Autowired
	private ExpertCategoryMapper mapper;
	@Autowired
	private  DictionaryDataMapper dictionaryDataMapper;
	@Autowired
    private CategoryService categoryService; //品目
    @Autowired
    private EngCategoryService engCategoryService; //工程专业信息
	
	
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
	public void save(Expert expert,String ids,String typeId, String engin_type) {
	    if(ids!=null && StringUtils.isNotEmpty(ids)){
            String[] code = ids.split(",");
            ExpertCategory expertCategory = new ExpertCategory();
            //循环品目id集合
            for (String id : code) {
            	
            	Category cata1 = categoryService.findById(id);
            	Category cata2 = engCategoryService.selectByPrimaryKey(id);
            	if ((cata1 != null && cata1.getCode().length() == 7) || (cata2 != null && cata2.getCode().length() == 7)) {
            		expertCategory.setLevels("1");
				} else {
					List<Category> treeList1 = categoryService.findByParentId(id);
					List<Category> treeList2 = engCategoryService.selectParentId(id);
					if ((treeList1 != null && treeList1.size() > 0) || (treeList2 != null && treeList2.size() > 0)) {
						expertCategory.setLevels("0");
					} else {
						expertCategory.setLevels("1");
					}
				}
            	
                //根据编码查询id
                //String id = DictionaryDataUtil.getId(string);
                expertCategory.setCategoryId(id);
                expertCategory.setExpertId(expert.getId());
                expertCategory.setTypeId(typeId);
                expertCategory.setEngin_type(engin_type == null ? "1": engin_type);
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
		List<ExpertCategory> list = mapper.selectListByExpertId1(expertId, typeId);
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
	
	@Override
	public List<ExpertCategory> getListCount(String expertId, String typeId) {
		// TODO Auto-generated method stub
		return mapper.selectListByExpertId(expertId, typeId);
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
	
	@Override
	public List<ExpertCategory> selectListByExpertId(String expertId) {
		// TODO Auto-generated method stub
		return mapper.selectListByExpertId(expertId, null);
	}
	
	/**
	 * 删除非选中树节点
	 */
	@Override
	public void delNoTree(String expertId, List<DictionaryData> list) {
		// TODO Auto-generated method stub
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("expertId", expertId);
		
		if (list != null && list.size() >0) {
			int listSize = list.size();
			String[] array = new String[listSize+1];
			String engin_type ="0";
			for (int i = 0; i < listSize; i++) {
				String listId = list.get(i).getId();
				String code = DictionaryDataUtil.findById(listId).getCode();
				if (code != null) {
					if (code.equals("GOODS_PROJECT")) {
						engin_type = "1";
						map.put("engin_type", engin_type);
						array[i] = DictionaryDataUtil.getId("ENG_INFO_ID");
						array[listSize] = DictionaryDataUtil.getId("PROJECT");
					} else if (code.equals("PROJECT") ) {
						engin_type = "2";
						map.put("engin_type", engin_type);
						array[i] = DictionaryDataUtil.getId("ENG_INFO_ID");
						array[listSize] = DictionaryDataUtil.getId("PROJECT");
					} else {
						array[i] = listId;
					}
				}
			}
			if (array[listSize] == null || ("").equals(array[listSize])) {
				array[listSize] = "111";   // 111没有意义
			}

			if (engin_type.equals("2") || engin_type.equals("1")) {
				map.put("array", null);
			} else {
				map.put("array", array);
			}
			mapper.delNoTree(map);
		}
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
	
    @Override
    public List<ExpertCategory> findEnginId(String expertId, String engin_type) {
    	// TODO Auto-generated method stub
    	Map<String, Object> map = new HashMap<>();
    	map.put("expertId", expertId);
    	map.put("engin_type", engin_type);
    	return mapper.findEnginId(map);
    }
	
	
}
 