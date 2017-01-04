package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;

public interface AdvancedProjectMapper {
    /**
     * 
     *〈删除〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 
     *〈新增〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    int insertSelective(AdvancedProject record);

    /**
     * 
     *〈根据id查询〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @return
     */
    AdvancedProject selectAdvancedProjectByPrimaryKey(String id);

    /**
     * 
     *〈修改〉
     *〈详细描述〉
     * @author Administrator
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AdvancedProject record);

    /**
     * 
     *〈集合〉
     *〈详细描述〉
     * @author Administrator
     * @param advancedProject
     * @return
     */
    List<AdvancedProject> selectByList(HashMap<String, Object> map);
    
    List<AdvancedProject> verifyByProject(AdvancedProject advancedProject);
}