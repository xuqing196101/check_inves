package ses.service.ems;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import ses.model.bms.DictionaryData;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtracts;

/**
 * @Description:获取专家抽取记录
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月27日下午4:31:12
 * @since  JDK 1.7
 */
public interface ExpExtractRecordService {
    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    void insert(ExpExtractRecord record);

    /**
     * @Description:获取集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:33:36  
     * @param @return      
     * @return List<ExpExtractRecord>
     */
    List<ExpExtractRecord> listExtractRecord(ExpExtractRecord expExtractRecord,Integer pageNum);

    /**
     * @Description:单个记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:19:50  
     * @param @param expExtractRecordService
     * @param @return      
     * @return ExpExtractRecord
     */
    List<ExpExtractRecord> showExpExtractRecord(ExpExtractRecord expExtractRecord);

    /**
     * 
     *〈简述〉添加临时专家
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param expExtractRecordService
     * @return
     */
    Map<String, String> addTemporaryExpert(Expert expert,String projectId,String packageId,String loginName,String loginPwd,HttpServletRequest request);

    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    void update(ExpExtractRecord extracts);

    /**
     * 
     *〈简述〉供应商类型
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<DictionaryData> ddList();

    /**
     * 
     *〈简述〉返回抽取结果
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    List<ProjectExtract> resultProjectExtract(HttpServletRequest sq, String[] ids);

    /**
     * 
     *〈简述〉
     *〈详细描述〉抽取展示
     * @author Wang Wenshuai
     * @param model
     * @param listCon
     * @param mapcount
     * @param list
     * @return
     */
    void showResultProjectExt(ExpExtCondition condition, Map<String, Object> map,
                              Map<String, Integer> mapcount, List<ProjectExtract> list);
    
    /**
     * 
     * Description: 修改临时专家
     * 
     * @author zhang shubin
     * @data 2017年6月27日
     * @param 
     * @return
     */
    Map<String, String> editTemporaryExpert(Expert expert,String projectId,String packageId,String loginName,String loginPwd,HttpServletRequest request,String oldPackageId);


}
