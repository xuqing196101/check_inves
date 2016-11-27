package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ImportRecommendMapper;
import ses.model.sms.ImportRecommend;
import ses.service.sms.ImportRecommendService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>进口代理商服务层实现层
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Service
public class ImportRecommendServiceImpl implements ImportRecommendService {
    /**
     * 进口代理商持久层
     */
    @Autowired
    private ImportRecommendMapper irMapper;
    /**
     * @see ses.service.sms.ImportRecommendService#register(ses.model.sms.ImportRecommend)
     */
    @Override
    public void register(ImportRecommend ir) {
        irMapper.insertSelective(ir);
    }
    
    /**
     * @see ses.service.sms.ImportRecommendService#update(ses.model.sms.ImportRecommend)
     */
    @Override
    public void update(ImportRecommend ir) {
        irMapper.updateByPrimaryKeySelective(ir);
    }

    /**
     * @see ses.service.sms.ImportRecommendService#findById(java.lang.String)
     */
    @Override
    public ImportRecommend findById(String id) {
        ImportRecommend ir = irMapper.selectByPrimaryKey(id);
        return ir;
    }

    /**
     * @see ses.service.sms.ImportRecommendService#selectByRecommend(ses.model.sms.ImportRecommend, java.lang.Integer)
     */
    @Override
    public List<ImportRecommend> selectByRecommend(ImportRecommend ir, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        List<ImportRecommend> irList = irMapper.selectByRecommend(ir);
        return irList;
    }
}
