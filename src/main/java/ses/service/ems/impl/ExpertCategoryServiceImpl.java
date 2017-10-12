package ses.service.ems.impl;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.sms.SupplierCateTree;
import ses.service.bms.CategoryService;
import ses.service.bms.EngCategoryService;
import ses.service.ems.ExpertCategoryService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		List<ExpertCategory> list = mapper.selectListByExpertId(expertId, typeId, null);
		return list;
	}
	
	@Override
    public List<ExpertCategory> getListByExpertId(String expertId, String typeId, Integer pageNum) {
	    if (pageNum != null) {
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSizeArticle"));
        }
        List<ExpertCategory> list = mapper.selectListByExpertId(expertId, typeId, "1");
        return list;
    }
	
	@Override
	public List<ExpertCategory> getListCount(String expertId, String typeId, String level) {
		// TODO Auto-generated method stub
		return mapper.selectListByExpertId(expertId, typeId, level);
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
	public List<ExpertCategory> findByExpertId(String expertId) {
		return mapper.findByExpertId(expertId);
	}
	
	@Override
	public List<ExpertCategory> selectListByExpertId(String expertId) {
		// TODO Auto-generated method stub
		return mapper.selectListByExpertId(expertId, null, null);
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

	@Override
	public List<ExpertCategory> getListCategory(String expertId, String categoryId, String typeId) {
		 
		return mapper.getCategory(expertId, categoryId, typeId);
	}
	/**
	 *
	 * Description:查询专家审核通过的类型
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param map
	 * @since JDK1.7
	 */
	@Override
	public List<String> selectCateByExpertId(String expertId) {
        // 定义查询条件
		Map<String, Object> map = new HashedMap();
		map.put("expertId", expertId);
		map.put("type", "seven");
		return mapper.selectNoPassCateByExpertId(map);
	}

	/**
	 *
	 * Description:查询专家审核通过的类型
	 *
	 * @author Easong
	 * @version 2017/7/7
	 * @param expertId
	 * @param categoryId
	 * @param typeId
	 * @since JDK1.7
	 */
    @Override
    public List<ExpertCategory> selectPassCateByExpertId(String expertId, String typeId, Integer pageNum) {
        if (pageNum != null) {
            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSizeArticle"));
        }
	    // 定义查询条件
        Map map = new HashedMap();
        map.put("expertId", expertId);
        map.put("typeId", typeId);
        map.put("type", "six");
        // 设置复审字段标识，只查询复审不通过的参评类别
        map.put("flag", 2);
        return mapper.selectPassCateByExpertId(map);
    }
    /**
	 *
	 * Description:查询专家审核通过的数量
	 *
	 * @author SS
	 * @version 2017/8/25
	 * @param expertId
	 * @param categoryId
	 * @param typeId
	 * @since JDK1.7
	 */
    public int selectPassCount(Map<String,Object> map){
    	List<ExpertCategory> list = mapper.selectPassCount(map);
		return list.size();
    	
    }
	/**
	 *
	 * Description:保存专家选择的小类
	 *
	 * @author Easong
	 * @version 2017/7/11
	 * @param [expertCategory]
	 * @since JDK1.7
	 */
	@Override
	public void insertSelective(ExpertCategory expertCategory) {
    	mapper.insertSelective(expertCategory);

	}


  @Override
  public List<ExpertCategory> selectListByExpertId1(String expertId, String typeId) {
    List<ExpertCategory> list = mapper.selectListByExpertId1(expertId, typeId);
    return list;
  }

  	/**
  	 * 更新审核状态 
  	 */
	@Override
	public void updateAuditStatus(ExpertCategory expertCategory) {
		mapper.updateAuditStatus(expertCategory);
		
	}

	/**
	 * 查询所有的参评类别
	 */
	@Override
	public List<SupplierCateTree> findExpertCatrgory(String expertId,
			String typeId) {
		return mapper.findExpertCatrgory(expertId,typeId);
	}

	/**
	 * 判断为第几级节点
	 */
	@Override
	public Integer findCountParent(Map<String,Object> map) {
		return mapper.findCountParent(map);
	}
	
	/**
     * 查询通过的类别（排除初审或复审中不通过的）
     * @param map
     * @return
     */
	@Override
	public List<ExpertCategory> findPassCateByExpertId(Map<String, Object> map) {
		Integer pageNum = (Integer) map.get("pageNum");
		 if (pageNum !=null) {
	            PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSizeArticle"));
	        }
		return mapper.findPassCateByExpertId(map);
	}

	@Override
	public List<ExpertCategory> selectCategoryListByCategoryId(ExpertCategory expertCategory) {
		// TODO Auto-generated method stub
		return mapper.selectCategoryListByCategoryId(expertCategory);
	}
}
 