package bss.formbean;

import java.io.Serializable;
/**
 * 
 * @Title: Category
 * @Description: 封装图形数据 
 * @author Li Xiaoxiao
 * @date  2016年9月27日,上午9:42:06
 *
 */
public class Category implements Serializable {
	private static final long serialVersionUID = -3947560771347239102L;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
