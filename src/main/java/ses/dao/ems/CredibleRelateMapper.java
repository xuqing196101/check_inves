package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.CredibleRelate;

public interface CredibleRelateMapper {
    int insert(CredibleRelate record);

    int insertSelective(CredibleRelate record);
    /**
     * 
      * @Title: selectAllByMap
      * @author ShaoYangYang
      * @date 2016年11月2日 下午4:21:50  
      * @Description: TODO 条件查询
      * @param @param map
      * @param @return      
      * @return List<CredibleRelate>
     */
    List<CredibleRelate> selectAllByMap(Map<String,Object> map);
    /**
     * 
      * @Title: updateByBean
      * @author ShaoYangYang
      * @date 2016年11月3日 上午11:15:42  
      * @Description: TODO 修改
      * @param @param record      
      * @return void
     */
    void updateByBean(CredibleRelate record);
}