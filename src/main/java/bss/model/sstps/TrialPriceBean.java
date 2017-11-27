package bss.model.sstps;

import java.util.List;

import bss.model.ppms.ProjectAdvice;

public class TrialPriceBean {
   private List<AccessoriesCon> listAcc;
   private List<OutproductCon> listOutPro;
   private List<OutsourcingCon> listOutSou;
   private List<SpecialCost> listSpec;
   private List<BurningPower> listBurn;
   private List<WagesPayable> listWages;
   private List<ManufacturingCost> listManu;
   private List<PeriodCost> listPerio;
   private List<YearPlan> listYear;
   private List<ProductQuota> listPro;
   
   private List<ProjectAdvice> projectAdviceList;
   

public List<ProjectAdvice> getProjectAdviceList() {
    return projectAdviceList;
  }

  public void setProjectAdviceList(List<ProjectAdvice> projectAdviceList) {
    this.projectAdviceList = projectAdviceList;
  }

public List<ProductQuota> getListPro() {
	return listPro;
}

public void setListPro(List<ProductQuota> listPro) {
	this.listPro = listPro;
}

public List<YearPlan> getListYear() {
	return listYear;
}

public void setListYear(List<YearPlan> listYear) {
	this.listYear = listYear;
}

public List<PeriodCost> getListPerio() {
	return listPerio;
}

public void setListPerio(List<PeriodCost> listPerio) {
	this.listPerio = listPerio;
}

public List<ManufacturingCost> getListManu() {
	return listManu;
}

public void setListManu(List<ManufacturingCost> listManu) {
	this.listManu = listManu;
}

public List<BurningPower> getListBurn() {
	return listBurn;
}

public void setListBurn(List<BurningPower> listBurn) {
	this.listBurn = listBurn;
}

public List<SpecialCost> getListSpec() {
	return listSpec;
}

public void setListSpec(List<SpecialCost> listSpec) {
	this.listSpec = listSpec;
}

public List<OutsourcingCon> getListOutSou() {
	return listOutSou;
}

public void setListOutSou(List<OutsourcingCon> listOutSou) {
	this.listOutSou = listOutSou;
}

public List<OutproductCon> getListOutPro() {
	return listOutPro;
}

public void setListOutPro(List<OutproductCon> listOutPro) {
	this.listOutPro = listOutPro;
}

public List<AccessoriesCon> getListAcc() {
	return listAcc;
}

public void setListAcc(List<AccessoriesCon> listAcc) {
	this.listAcc = listAcc;
}

public List<WagesPayable> getListWages() {
	return listWages;
}

public void setListWages(List<WagesPayable> listWages) {
	this.listWages = listWages;
}
   
}
