package ses.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierFinance;
import ses.service.bms.CategoryService;
import ses.service.ems.ExpertService;
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
    /** 专家Service **/
    private ExpertService expertServiceImpl;
    
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
    public void setDdService(ExpertService expertServiceImpl, SupplierService supplierServiceImpl, CategoryService categoryServiceImpl, SupplierItemService supplierItemServiceImpl){
        this.expertServiceImpl = expertServiceImpl;
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
        supplierLevelUtil.expertServiceImpl = this.expertServiceImpl;
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
     * @throws ParseException 
     */
    public BigDecimal getLevel(String supplierId, String typeCode) throws ParseException {
        BigDecimal score = new BigDecimal(0);
        Supplier supplier = supplierServiceImpl.get(supplierId);
        
        /** 分级要素总分:成立时间 **/
        BigDecimal dateScoreSum = new BigDecimal(20);
        /** 分级要素总分:平均净资产 **/
        BigDecimal totalNetAssetsScoreSum = new BigDecimal(30);
        /** 分级要素总分:平均营业收入 **/
        BigDecimal takingScoreSum = new BigDecimal(50);
        
        if (typeCode.equals("PRODUCT")) {
            totalNetAssetsScoreSum = new BigDecimal(40);
            takingScoreSum = new BigDecimal(40);
        }
        
        // 供应商成立时间
        Date foundDate = supplier.getFoundDate();
        
        List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
        // 计算供应商平均净资产分数和平均营业收入
        BigDecimal totalNetAssets = new BigDecimal(0);
        BigDecimal taking = new BigDecimal(0);
        for (SupplierFinance finance : listSupplierFinances) {
            // 循环计算总值
            totalNetAssets = totalNetAssets.add(finance.getTotalNetAssets());
            taking = taking.add(finance.getTaking());
        }
        // 算出平均值
        totalNetAssets = totalNetAssets.divide(new BigDecimal(3), 5, RoundingMode.HALF_UP);
        taking = taking.divide(new BigDecimal(3), 5, RoundingMode.HALF_UP);
        
        BigDecimal dateScore = new BigDecimal(0);
        BigDecimal totalNetAssetsScore = new BigDecimal(0);
        BigDecimal takingScore = new BigDecimal(0);
            
        // 获取最早成立时间
        Date minFoundDate = supplierServiceImpl.getMinFoundDate();
        // 计算成立时间分数
        int minFoundDateScore = expertServiceImpl.daysBetween(minFoundDate);
        int foundDateScore = expertServiceImpl.daysBetween(foundDate);
        dateScore = new BigDecimal(minFoundDateScore).divide(new BigDecimal(foundDateScore), 5, RoundingMode.HALF_UP).multiply(dateScoreSum);
        
        // 获取最大平均净资产
        BigDecimal maxTotalNetAssets = supplierServiceImpl.getMaxTotalNetAssets();
        // 计算最大平均净资产分数
        totalNetAssetsScore = totalNetAssets.divide(maxTotalNetAssets, 5, RoundingMode.HALF_UP).multiply(totalNetAssetsScoreSum);
        
        // 获取最大营业收入
        BigDecimal maxTaking = supplierServiceImpl.getMaxTaking();
        // 计算最大营业收入分数
        takingScore = taking.divide(maxTaking, 5, RoundingMode.HALF_UP).multiply(takingScoreSum);
        
        // 总分
        score = dateScore.add(totalNetAssetsScore).add(takingScore);
            
        return score;
    }
}
