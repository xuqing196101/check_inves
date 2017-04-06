package ses.dao.ems;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.ems.ExpertTitle;
import bss.model.cs.ContractRequired;

public interface ExpertTitleMapper {
	/**
     *〈简述〉
     * 根据主键查询
     *〈详细描述〉
     * @author LiChenHao
     * @param id
     * @return AfterSaleSer对象
     */
    public ExpertTitle selectByPrimaryKey(String id);
    
    public ContractRequired selectConRequByPrimaryKey(String id);
    /**
     * 
     * @Title: insert
     * @author LiChenHao 
     * @Description:插入数据
     * @param:     
     * @return:
     */
    int insert(ExpertTitle record);
    /**
     *〈简述〉
     * 新增数据不得为空
     *〈详细描述〉
     * @author LiChenHao
     */
    public void insertSelective(ExpertTitle record);
    
    /**
     *〈简述〉
     * 修改不为空
     *〈详细描述〉
     * @author LiChenHao
     */
    public void updateByPrimaryKeySelective(ExpertTitle record);
    
    /**
     * 
     * @Title: updateByPrimaryKey
     * @author LiChenHao 
     * @Description:更新数据
     * @param:     
     * @return:
     */
    int updateByPrimaryKey(ExpertTitle record);
    
    /**
     * 
    * @Title: queeyByExpertId
    * @Description: 根据专家ID查询和专家类型查询
    * author: Li Xiaoxiao 
    * @param @param expertId
    * @param @return     
    * @return List<ExpertTitle>     
    * @throws
     */
    List<ExpertTitle> queryByExpertId(@Param("expertId")String expertId,@Param("expertTypeId")String typeId);
    
    
    /**
     * 
    * @Title: delete
    * @Description: 根据ID删除
    * author: Li Xiaoxiao 
    * @param @param id     
    * @return void     
    * @throws
     */
    public void deleteById(@Param("id")String id);
    
    
}
