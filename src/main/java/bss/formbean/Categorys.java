package bss.formbean;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Title: Categorys
 * @Description:  封装图形数据
 * @author Li Xiaoxiao
 * @date  2016年9月27日,上午9:54:14
 *
 */
public class Categorys implements Serializable {
	private static final long serialVersionUID = -4247452725576790065L;
	private List<Category> category;

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}
}
