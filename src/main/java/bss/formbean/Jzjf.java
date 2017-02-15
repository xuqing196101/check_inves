package bss.formbean;

/**
 * 基准价法展示字段
 * @author this'me
 *
 */
public class Jzjf {
  
    //中标参考价
    String bidPrice;
    
    //供应商报价
    String supplierPrice;
    
    //有效平均报价
    String effectiveAverageQuotation;
    
    //排名
    String rank;
    
    //浮动比例
    String floatingRatio;
    
    //基准价
    String benchmarkPrice;
    
    //是否参与排名
    String isRank;
    
    //有效经济技术平均分
    String effectiveAverageScore;
    
    public String getBidPrice() {
      return bidPrice;
    }
    public void setBidPrice(String bidPrice) {
      this.bidPrice = bidPrice;
    }
    public String getSupplierPrice() {
      return supplierPrice;
    }
    public void setSupplierPrice(String supplierPrice) {
      this.supplierPrice = supplierPrice;
    }
    public String getEffectiveAverageQuotation() {
      return effectiveAverageQuotation;
    }
    public void setEffectiveAverageQuotation(String effectiveAverageQuotation) {
      this.effectiveAverageQuotation = effectiveAverageQuotation;
    }
    public String getRank() {
      return rank;
    }
    public void setRank(String rank) {
      this.rank = rank;
    }
    public String getFloatingRatio() {
      return floatingRatio;
    }
    public void setFloatingRatio(String floatingRatio) {
      this.floatingRatio = floatingRatio;
    }
    public String getBenchmarkPrice() {
      return benchmarkPrice;
    }
    public void setBenchmarkPrice(String benchmarkPrice) {
      this.benchmarkPrice = benchmarkPrice;
    }
    public String getIsRank() {
      return isRank;
    }
    public void setIsRank(String isRank) {
      this.isRank = isRank;
    }
    public String getEffectiveAverageScore() {
      return effectiveAverageScore;
    }
    public void setEffectiveAverageScore(String effectiveAverageScore) {
      this.effectiveAverageScore = effectiveAverageScore;
    }
    
}
