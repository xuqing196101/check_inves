package ses.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 供应商等级工具类
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0
 * @since    2017年2月14日 14:27:28
 * @see
 */
@Component
public class SupplierLevelUtil {
    
    @Autowired
    /** 供应商Service **/
    private SupplierService supplierServiceImpl;
    
    @Autowired
    /** 品目Service **/
    private CategoryService categoryServiceImpl;
    
    @Autowired
    /** 供应商品目Service **/
    private SupplierItemService supplierItemServiceImpl;
    
    private static SupplierLevelUtil supplierLevelUtil;
    
    /**
     *〈简述〉
     * Service实例化
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierServiceImpl
     * @param categoryServiceImpl
     * @param supplierItemServiceImpl
     */
    public void setDdService(SupplierService supplierServiceImpl, CategoryService categoryServiceImpl, SupplierItemService supplierItemServiceImpl){
        this.supplierServiceImpl = supplierServiceImpl;
        this.categoryServiceImpl = categoryServiceImpl;
        this.supplierItemServiceImpl = supplierItemServiceImpl;
    }
    
    /**
     *〈简述〉初始化
     *〈详细描述〉
     * @author WangHuijie
     */
    @PostConstruct 
    public void init(){
        supplierLevelUtil = this;
        supplierLevelUtil.supplierServiceImpl = this.supplierServiceImpl;
        supplierLevelUtil.categoryServiceImpl = this.categoryServiceImpl;
        supplierLevelUtil.supplierItemServiceImpl = this.supplierItemServiceImpl;
    }
    
    /**
     *〈简述〉
     * 计算供应商分级要素得分
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierId 供应商编号
     * @param typeCode 品目类别
     * @return 供应商等级Integer
     */
    public Integer getLevel(String supplierId, String typeCode) {
        Integer score = null;
        Supplier supplier = supplierServiceImpl.get(supplierId);
        // 物资生产
        if (typeCode.equals("PRODUCT")) {
            // 获取最早成立时间
            // 获取最大平均净资产
            // 获取最大营业收入
        }
        return score;
    }
}
