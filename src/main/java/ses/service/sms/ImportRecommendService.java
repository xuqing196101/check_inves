package ses.service.sms;

import java.util.List;

import ses.model.sms.ImportRecommend;

/**
 * 版权：(C) 版权所有 
 * <简述>进口代理商服务层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface ImportRecommendService {
    /**
     *〈简述〉进口代理商新增
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 实体
     */
    void register(ImportRecommend ir);

    /**
     *〈简述〉更新
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 实体类
     */
    void update(ImportRecommend ir);
    
    /**
     *〈简述〉按照id查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 逐渐
     * @return ImportRecommend
     */
    ImportRecommend findById(String id);

    /**
     *〈简述〉
     *〈详细描述〉
     * @author Song Biaowei
     * @param ir 实体
     * @param page 当前页
     * @return List<ImportRecommend>
     */
    List<ImportRecommend> selectByRecommend(ImportRecommend ir, Integer page);
}
