package bss.model.ppms;


public class Reason {
  
  
  
  //采购部门意见
  private String pcReason; 
  //事业部门意见
  private String causeReason;
  //财务部门意见
  private String financeReason;
  //最终意见
  private String finalReason;

  
  
  
  /**
   * @return Returns the pcReason.
   */
  public String getPcReason() {
    return pcReason;
  }
  /**
   * @param pcReason The pcReason to set.
   */
  public void setPcReason(String pcReason) {
    this.pcReason = pcReason;
  }
  /**
   * @return Returns the causeReason.
   */
  public String getCauseReason() {
    return causeReason;
  }
  /**
   * @param causeReason The causeReason to set.
   */
  public void setCauseReason(String causeReason) {
    this.causeReason = causeReason;
  }
  /**
   * @return Returns the financeReason.
   */
  public String getFinanceReason() {
    return financeReason;
  }
  /**
   * @param financeReason The financeReason to set.
   */
  public void setFinanceReason(String financeReason) {
    this.financeReason = financeReason;
  }
  /**
   * @return Returns the finalReason.
   */
  public String getFinalReason() {
    return finalReason;
  }
  /**
   * @param finalReason The finalReason to set.
   */
  public void setFinalReason(String finalReason) {
    this.finalReason = finalReason;
  }
}
