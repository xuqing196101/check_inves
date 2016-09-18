package bss.formbean;

import java.util.List;

import bss.model.pms.PurchaseRequired;
/**
 * 
 * @Title: PurchaseRequiredFormBean
 * @Description: 对采购需求类重新封装 
 * @author Li Xiaoxiao
 * @date  2016年9月13日,下午4:54:18
 *
 */
public class PurchaseRequiredFormBean {
	private List<PurchaseRequired> list;

	public List<PurchaseRequired> getList() {
		return list;
	}

	public void setList(List<PurchaseRequired> list) {
		this.list = list;
	}
	
 
//	public PurchaseRequiredFormBean(List<PurchaseRequired> list) {
//		super();
//		this.list = list;
//	}
//
//	public PurchaseRequiredFormBean(){
//		super();
//	}

}
