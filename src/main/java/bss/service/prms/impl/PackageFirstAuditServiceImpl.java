package bss.service.prms.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PathUtil;
import ses.util.PropUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import bss.dao.prms.PackageFirstAuditMapper;
import bss.model.prms.PackageFirstAudit;
import bss.service.prms.PackageFirstAuditService;
@Service("packageFirstAuditService")
public class PackageFirstAuditServiceImpl implements PackageFirstAuditService {
	@Autowired
	private PackageFirstAuditMapper mapper;
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:36:16  
	  * @Description: TODO 保存全部
	  * @param @param record      
	  * @return void
	 */
	public void save(PackageFirstAudit record){
		mapper.insert(record);
	}
	/**
	 * 
	  * @Title: delete
	  * @author ShaoYangYang
	  * @date 2016年10月17日 下午3:50:43  
	  * @Description: TODO 根据包id删除数据
	  * @param @param packageId      
	  * @return void
	 */
	public void delete(String packageId){
		mapper.deleteByPackageId(packageId);
	}
	/**
     * 
      * @Title: selectList
      * @author ShaoYangYang
      * @date 2016年10月17日 下午5:13:41  
      * @Description: TODO 根据项目id 和包id查询关联表集合
      * @param @param map
      * @param @return      
      * @return List<PackageFirstAudit>
     */
   public List<PackageFirstAudit>selectList(Map<String,Object> map){
    	return mapper.selectList(map);
    }
   /**
    * 
     * @Title: update
     * @author ShaoYangYang
     * @date 2016年10月26日 下午8:06:25  
     * @Description: TODO 根据传递的id 修改符合条件的内容
     * @param @param record      
     * @return void
    */
   public void update(PackageFirstAudit record){
	   mapper.update(record);
   }
   
   @Override
   public List<PackageFirstAudit> findByProAndPackage(Map<String, Object> map) {
        return mapper.findByProAndPackage(map);
   }
  @Override
  public void deleteByFirstAuditId(String id) {
    mapper.deleteByFirstAuditId(id);
  }
    
    /**
     * 
     * @see bss.service.prms.PackageFirstAuditService#downLoadBiddingDoc(java.lang.String, java.lang.String, java.lang.String, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public String downLoadBiddingDoc(String projectId, String projectName,String projectNo, HttpServletRequest request ) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", projectName);
        dataMap.put("projectNo", projectNo);
        return productionDoc(request, dataMap);
    }
    
    /**
     * 
     *〈简述〉生成word
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 文件名称
     */
    private String productionDoc(HttpServletRequest request, Map<String,Object> dataMap){
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
        String filePath = "";
        try {
            Template t = configuration.getTemplate("biddingdocument.ftl");
            String basePath = PropUtil.getProperty("file.base.path");
            String temp = PropUtil.getProperty("file.temp.path");
            String path = basePath + File.separator + temp;
            String fileName = System.currentTimeMillis()+ ".doc";
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            File rootFile = new File(path,fileName);
            if(!rootFile.exists()){
                rootFile.createNewFile();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rootFile),"UTF-8"));
            t.process(dataMap, out);
            out.flush();
            out.close();
            filePath = rootFile.getPath();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return filePath;
    }
  
  
}
