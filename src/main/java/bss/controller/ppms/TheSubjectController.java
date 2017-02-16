package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.controller.base.BaseController;
import bss.model.ppms.theSubject;
import bss.service.ppms.theSubjectService;
/**
 * 
 * 版权：(C) 版权所有 
 * <简述>标的控制层
 * <详细描述>
 * @author   Wang Wenshuai
 * @version  
 * @since
 * @see
 */
public class TheSubjectController extends BaseController {
  /** SCCUESS */
  private static final String SCCUESS = "SCCUESS";
  /** FAILED */
  private static final String FAILED = "FAILED"; 
  /** ERROR */
  private static final String ERROR = "ERROR"; 
  
  @Autowired
  private theSubjectService theSubjectService;
  
  /**
   * 
   *〈简述〉插入标的
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @RequestMapping
  public  String  insert(List<theSubject> subjectList) {
    theSubjectService.insertList(subjectList);
    return "";
  }
 
  /**
   * 
   *〈简述〉集合
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param subject
   * @return
   */
  public String list(theSubject subject) {
    
     List<theSubject> list = theSubjectService.list(subject);
     
     return "";
  }
  
}
