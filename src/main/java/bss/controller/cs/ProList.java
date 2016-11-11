package bss.controller.cs;

import java.util.List;

import bss.model.cs.ContractRequired;
/**
 * 
 *@Title:ProList
 *@Description: 用来接受后台传的list
 *@author QuJie
 *@date 2016-11-11下午3:06:49
 */
public class ProList {
	private List<ContractRequired> proList;

	public List<ContractRequired> getProList() {
		return proList;
	}

	public void setProList(List<ContractRequired> proList) {
		this.proList = proList;
	}
}
