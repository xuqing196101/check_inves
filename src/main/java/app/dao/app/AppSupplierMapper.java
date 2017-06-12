package app.dao.app;

import java.util.List;
import java.util.Map;

import app.model.AppSupplier;

/**
 * 
 * Description: 供应商、专家名录
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AppSupplierMapper {

    /**
     * 
     * Description: 查询供应商名录
     * 
     * @author zhang shubin
     * @data 2017年6月5日
     * @param 
     * @return
     */
    List<AppSupplier> selectAppSupplierList(Map<String, Object> map);
    
    /**
     * 
     * Description: 专家名录
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    List<AppSupplier> selectAppExpertList(Map<String, Object> map);
}
