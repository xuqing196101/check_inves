package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.ImportSupplierMapper;
import ses.model.sms.ImportSupplierWithBLOBs;
import ses.service.sms.ImportSupplierService;
import ses.util.PropertiesUtil;

/**
 * 版权：(C) 版权所有 
 * <简述>进口供应商服务层实现层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Service
public class ImportSupplierServiceImpl implements ImportSupplierService {
    /**
     * 金扣供应商持久层
     */
    @Autowired
    private ImportSupplierMapper isMapper;
    
    /**
     * @see ses.service.sms.ImportSupplierService#register(ses.model.sms.ImportSupplierWithBLOBs)
     */
    @Override
    public void register(ImportSupplierWithBLOBs is) {
        isMapper.insertSelective(is);
    }
    
    /**
     * @see ses.service.sms.ImportSupplierService#updateRegisterInfo(ses.model.sms.ImportSupplierWithBLOBs)
     */
    @Override
    public void updateRegisterInfo(ImportSupplierWithBLOBs is) {
        isMapper.updateByPrimaryKeySelective(is);
    }
    
    /**
     * @see ses.service.sms.ImportSupplierService#findById(java.lang.String)
     */
    @Override
    public ImportSupplierWithBLOBs findById(String id) {
        ImportSupplierWithBLOBs is = isMapper.selectByPrimaryKey(id);
        return is;
    }
    
    /**
     * @see ses.service.sms.ImportSupplierService#getCount(ses.model.sms.ImportSupplierWithBLOBs)
     */
    @Override
    public int getCount(ImportSupplierWithBLOBs is) {
        int count = isMapper.getCount(is);
        return count;
    }
    
    /**
     * @see ses.service.sms.ImportSupplierService#selectByFsInfo(ses.model.sms.ImportSupplierWithBLOBs, java.lang.Integer)
     */
    @Override
    public List<ImportSupplierWithBLOBs> selectByFsInfo(
        ImportSupplierWithBLOBs is, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        List<ImportSupplierWithBLOBs> sfiList = isMapper.selectByFsInfo(is);
        return sfiList;
    }

    /**
     * @see ses.service.sms.ImportSupplierService#selectByPrimaryKey(ses.model.sms.ImportSupplierWithBLOBs)
     */
    @Override
    public ImportSupplierWithBLOBs selectByPrimaryKey(ImportSupplierWithBLOBs is) {
        ImportSupplierWithBLOBs importSupplierWithBLOBs = isMapper.selectByPrimaryKey(is.getId());
        return importSupplierWithBLOBs;
    }
    
    /**
     * @see ses.service.sms.ImportSupplierService#selectIdByLoginName(ses.model.sms.ImportSupplierWithBLOBs)
     */
    @Override
    public String selectIdByLoginName(ImportSupplierWithBLOBs is) {
        String id = isMapper.selectIdByLoginName(is);
        return id;
    }

    /**
     * @see ses.service.sms.ImportSupplierService#delete(java.lang.String)
     */
    @Override
    public void delete(String id) {
        isMapper.deleteByPrimaryKey(id);
    }
}
