package extract.service.expert;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import extract.model.expert.ExpertExtractProject;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractProjectService {

    /**
     * 
     * Description: 保存项目信息
     * 
     * @author zhang shubin
     * @data 2017年9月5日
     * @param 
     * @return
     */
    int save(ExpertExtractProject expertExtractProject,User user);
    
    /**
     * 
     * Description: 根据项目类型加载专家类别
     * 
     * @author zhang shubin
     * @data 2017年9月6日
     * @param 
     * @return
     */
    List<DictionaryData> loadExpertKind(String id);
    
    /**
     * 
     * Description: 条件查询抽取记录
     * 
     * @author zhang shubin
     * @data 2017年9月13日
     * @param 
     * @return
     */
    List<ExpertExtractProject> findAll(Map<String, Object> map ,ExpertExtractProject expertExtractProject);
    
    /**
     * 
     * Description: 下载抽取记录表
     * 
     * @author jia chengxiang
     * @param response 
     * @param request 
     * @param id 
     * @data 2017年9月18日
     * @param 
     * @return
     * @throws Exception 
     */
    ResponseEntity<byte[]> printRecord(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /**
     * 
     * Description: 项目编号唯一验证 
     * 
     * @author zhang shubin
     * @data 2017年9月19日
     * @param 
     * @return
     */
    String vaProjectCode(String code,String xmProjectId);
    
    /**
     * 
     * Description: 修改项目抽取状态
     * 
     * @author zhang shubin
     * @data 2017年9月20日
     * @param 
     * @return
     */
    int updataStatus(String projectId);
    
    /**
     * 
     * Description: 根据主键查询
     * 
     * @author zhang shubin
     * @data 2017年9月20日
     * @param 
     * @return
     */
    ExpertExtractProject selectByPrimaryKey(String id);
    
    /**
     * 
     * Description: 专家抽取信息导出
     * 
     * @author zhang shubin
     * @data 2017年9月29日
     * @param 
     * @return
     */
    void exportExpertExtract(String id);
    
    /**
     * 
     * Description: 导入专家抽取信息
     * 
     * @author zhang shubin
     * @data 2017年9月29日
     * @param 
     * @return
     */
    List<ExpertExtractProject> importExpertExtract(File file);
    
    /**
     * 
     * Description: 内网导入专家抽取结果
     * 
     * @author zhang shubin
     * @data 2017年10月19日
     * @param 
     * @return
     */
    void importExpertExtractResult(File file);
    
    /**
     * 
     * Description: 外网导出专家抽取结果
     * 
     * @author zhang shubin
     * @data 2017年10月19日
     * @param 
     * @return
     */
    void exportExpertExtractResult(String start, String end, Date synchDate);
}
