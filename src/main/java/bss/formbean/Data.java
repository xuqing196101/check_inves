package bss.formbean;

import java.io.Serializable;

/**
 * @Title: Data
 * @Description: fusioncharts 的键值对
 * @Company: yggc 
 * @author: Poppet_Brook
 * @date: 2016-7-15上午9:58:57
 */
public class Data implements Serializable {
	
	private static final long serialVersionUID = 5748066855114919719L;

	/** 键 */
	private String label;

	/** 值 */
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
