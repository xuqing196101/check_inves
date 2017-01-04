/**
 * 
 */
package ses.util;

import java.util.Comparator;

import bss.model.ppms.AdvancedDetail;

/**
 * @Title:ComparatorDetail
 * @Description: 集合排序
 * @author ZhaoBo
 * @date 2016-12-9下午12:54:44
 */
@SuppressWarnings("rawtypes")
public class ComparatorDetails implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        AdvancedDetail detail1 = (AdvancedDetail) o1;
        AdvancedDetail detail2 = (AdvancedDetail) o2;
        //比较排序
        int flag = detail1.getPosition().compareTo(detail2.getPosition());
        return flag;
    }

}
