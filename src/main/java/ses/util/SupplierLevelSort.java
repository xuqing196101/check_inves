package ses.util;

import java.math.BigDecimal;
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
		BigDecimal init = new BigDecimal(one.getGrade());
		BigDecimal check= new BigDecimal(two.getGrade());
        if(init.compareTo(check) == 1){  
          return 1;
        }
        if(init.compareTo(check) == 0){  
          return 0;
        }
        if(init.compareTo(check) == -1){  
          return -1;
        }
        return 0;
	}

}
