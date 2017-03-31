package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.theSubject;

public interface theSubjectService  {

  /**
   * 
   *〈简述〉插入数据
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param subject
   */
  public void insert(theSubject subject); 
  
  /**
   * 
   *〈简述〉修改
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param subject
   */
  public void update(theSubject subject);
  
  /**
   * 
   *〈简述〉查询
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param subject
   */
  public List<theSubject> list(theSubject subject);
  
  /**
   * 
   *〈简述〉批量插入
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param list
   */
  public void insertList(List<theSubject> list);
  
  public List<theSubject> selectBysupplierIdAndPackagesId(HashMap<String, Object> map);
  
}
