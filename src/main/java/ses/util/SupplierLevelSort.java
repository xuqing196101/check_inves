package ses.util;

import java.util.Comparator;

import ses.model.sms.Supplier;
/**
 * 
 * Description:自定义排序 顺序
 * 
 * @author YangHongLiang
 * @version 2017-6-21
 * @since JDK1.7
 */
public class SupplierLevelSort implements Comparator<Supplier> {
	@Override
	public int compare(Supplier one, Supplier two) {
		Integer init=Integer.valueOf(one.getGrade());
		Integer check=Integer.valueOf(two.getGrade());
		if(init == null &&check==null){
			return 0;
		}else{
			return init-check;
		}
	}

}
