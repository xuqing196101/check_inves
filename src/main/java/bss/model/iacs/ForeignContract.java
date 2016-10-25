package bss.model.iacs;

import java.math.BigDecimal;

public class ForeignContract {
    private String id;

    private String importUser;

    private String contractNo;

    private String currency;

    private String tradeCountry;

    private BigDecimal contractAmount;

    private String originCountry;

    private String agencyCompany;

    private String supplier;

    private String goodsPort;

    private String transactionMode;

    private BigDecimal transportPremium;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getImportUser() {
        return importUser;
    }

    public void setImportUser(String importUser) {
        this.importUser = importUser == null ? null : importUser.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTradeCountry() {
        return tradeCountry;
    }

    public void setTradeCountry(String tradeCountry) {
        this.tradeCountry = tradeCountry == null ? null : tradeCountry.trim();
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry == null ? null : originCountry.trim();
    }

    public String getAgencyCompany() {
        return agencyCompany;
    }

    public void setAgencyCompany(String agencyCompany) {
        this.agencyCompany = agencyCompany == null ? null : agencyCompany.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getGoodsPort() {
        return goodsPort;
    }

    public void setGoodsPort(String goodsPort) {
        this.goodsPort = goodsPort == null ? null : goodsPort.trim();
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode == null ? null : transactionMode.trim();
    }

    public BigDecimal getTransportPremium() {
        return transportPremium;
    }

    public void setTransportPremium(BigDecimal transportPremium) {
        this.transportPremium = transportPremium;
    }
}