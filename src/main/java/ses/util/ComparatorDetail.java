/**
 * 
 */
package ses.util;

import java.util.Comparator;

import bss.model.ppms.ProjectDetail;

/**
 * @Title:ComparatorDetail
 * @Description: 集合排序
 * @author ZhaoBo
 * @date 2016-12-9下午12:54:44
 */
@SuppressWarnings("rawtypes")
public class ComparatorDetail implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		ProjectDetail detail1 = (ProjectDetail) o1;
		ProjectDetail detail2 = (ProjectDetail) o2;
		//比较排序
		int flag = detail1.getPosition().compareTo(detail2.getPosition());
		return flag;
	}

}
