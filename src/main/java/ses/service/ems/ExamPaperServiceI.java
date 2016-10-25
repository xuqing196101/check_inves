/**
 * 
 */
package ses.service.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExamPaper;


/**
 * 
* @Title:ExamPaperServiceI 
* @Description: 考卷管理接口类
* @author ZhaoBo
* @date 2016-9-7上午10:31:22
 */
public interface ExamPaperServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 上午10:31:46  
	* @Description: 根据主键ID删除考卷 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
     * 
    * @Title: insertSelective
    * @author zb
    * @date 2016-9-5 下午5:58:03  
    * @Description: 新增考卷 
    * @param @param examPaper
    * @param @return      
    * @return int
     */
	int insertSelective(ExamPaper examPaper);
	
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 上午10:32:00  
	* @Description: 根据主键ID查找考卷 
	* @param @param id
	* @param @return      
	* @return ExamPaper
	 */
	ExamPaper selectByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-9-7 上午10:32:15  
	* @Description: 根据主键ID更新考卷 
	* @param @param examPaper
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExamPaper examPaper);
	
	/**
     * 
    * @Title: queryAllPaper
    * @author zb
    * @date 2016-9-5 下午5:59:30  
    * @Description: 查询所有的考卷 
    * @param @return      
    * @return List<ExamPaper>
     */
    List<ExamPaper> queryAllPaper(ExamPaper examPaper,Integer pageNum);
    
    /**
     * 
    * @Title: selectByPaperNo
    * @author ZhaoBo
    * @date 2016-9-6 下午1:19:39  
    * @Description: 根据考试编号查找考卷 
    * @param @param paperNo
    * @param @return      
    * @return ExamPaper
     */
    List<ExamPaper> selectByPaperNo(HashMap<String,Object> map);
}
