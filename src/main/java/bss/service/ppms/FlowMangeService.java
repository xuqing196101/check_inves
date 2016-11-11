package bss.service.ppms;

import java.util.List;

import bss.model.ppms.FlowDefine;

/**
 * 版权：(C) 版权所有 
 * <简述>项目实施流程环节业务处理接口
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public interface FlowMangeService {

    /**
     *〈简述〉查询list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd
     * @return
     */
    List<FlowDefine> find(FlowDefine fd);

    /**
     *〈简述〉更新数据
     *〈详细描述〉
     * @author Ye MaoLin
     * @param flowDefine
     */
    void update(FlowDefine flowDefine);

    /**
     *〈简述〉查询流程环节分页list
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd
     * @param i
     * @return
     */
    List<FlowDefine> listByPage(FlowDefine fd, int i);

    /**
     *〈简述〉保存流程环节
     *〈详细描述〉
     * @author Ye MaoLin
     * @param fd
     */
    void save(FlowDefine fd);
    
}
