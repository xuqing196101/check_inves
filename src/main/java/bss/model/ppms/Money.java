package bss.model.ppms;

import java.math.BigDecimal;

/**
 * 版权：(C) 版权所有 
 * <简述>封装包和对应的报价和报价转换成的大写金额
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public class Money {
    /**
     * 包名
     */
    private String packageName;
    /**
     * 总报价
     */
    private BigDecimal totalMoney;
    /**
     * 大写金额
     */
    private String UpperName;
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    public String getUpperName() {
        return UpperName;
    }
    public void setUpperName(String upperName) {
        UpperName = upperName;
    }
    
    
}
