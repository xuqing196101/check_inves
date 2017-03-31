package ses.dao.ems;

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

}
