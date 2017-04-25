package ses.dao.bms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Qualification;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>资质管理mapper
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public interface QualificationMapper {
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param qualification  {@link Qualification}对象
     */
    public void save(Qualification qualification);
    
    /**
     * 
     *〈简述〉更新
     * @author myc
     * @param qualification {@link Qualification}对象
     */
    public void update(Qualification qualification);
    
    /**
     * 
     *〈简述〉查询
     *〈详细描述〉
     * @author myc
     * @param name 名称
     * @param type 类型
     * @return Qualification集合
     */
    public List<Qualification> findList(@Param("name")String name, @Param("type")Integer type);
    
    /**
     * 
     *〈简述〉根据Id主键查询 Qualification对象
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return Qualification 对象
     */
    public Qualification getQualification(@Param("id")String id);
    
    String getIdByName(@Param("name")String name);
    
    
}
