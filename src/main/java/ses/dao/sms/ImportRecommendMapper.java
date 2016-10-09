package ses.dao.sms;

import java.util.List;

import ses.model.sms.ImportRecommend;

public interface ImportRecommendMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImportRecommend record);

    int insertSelective(ImportRecommend record);

    ImportRecommend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportRecommend record);

    int updateByPrimaryKey(ImportRecommend record);
    
    /**
    * @Title: selectByFsInfo
    * @author sbw
    * @date 2016-10-4 下午1:42:20  
    * @Description: 条件查询返回List 
    * @param @param record
    * @param @return      
    * @return List<ImportSupplierWithBLOBs>
     */
    List<ImportRecommend> selectByRecommend(ImportRecommend record);
}