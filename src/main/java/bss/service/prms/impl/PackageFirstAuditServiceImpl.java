package bss.service.prms.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.dao.prms.PackageFirstAuditMapper;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.prms.PackageFirstAuditService;
@Service("packageFirstAuditService")
public class PackageFirstAuditServiceImpl implements PackageFirstAuditService {
	@Autowired
	private PackageFirstAuditMapper mapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private PackageMapper packageMapper;
	
	@Autowired
	private ProjectDetailMapper projectDetailMapper;
	
	@Autowired
	private FirstAuditMapper firmapper;
	
	@Autowired
	private MarkTermMapper markTermMapper;
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
    	Project pro = projectMapper.selectProjectByPrimaryKey(projectId);
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("projectId", projectId);
    	List<Packages> packages = packageMapper.findPackageById(map);
    	HashMap<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if(projectName!=null){
        	dataMap.put("name", projectName);
        }else{
        	dataMap.put("name", "");
        }
        if(projectNo!=null){
        	dataMap.put("code", projectNo);
        }else{
        	dataMap.put("code", "");
        }
        if(packages.get(0).getName()!=null){
        	dataMap.put("pacn", packages.get(0).getName());
        }else{
        	dataMap.put("pacn", "");
        }
        if(pro.getBidUnit()!=null){
        	dataMap.put("zhaobiaopeople", pro.getBidUnit());
        }else{
        	dataMap.put("zhaobiaopeople", "");
        }
        
        List<Map<String, Object>> gaikuang = new ArrayList<Map<String,Object>>();
        List<List<Map<String, Object>>> allgaikuang = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allhuowu = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allzigelist = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allfuhelist = new ArrayList<List<Map<String, Object>>>();
        List<Map<String, Object>> huowu = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> zigelist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> fuhelist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> shangwulist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> jishulist = new ArrayList<Map<String,Object>>();
        List<List<Map<String, Object>>> allshangwulist = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> alljishulist = new ArrayList<List<Map<String, Object>>>();
        for(int i=0;i<packages.size();i++){
        	map1.put("packageId", packages.get(i).getId());
        	List<ProjectDetail> detaList = projectDetailMapper.selectById(map1);
        	for(ProjectDetail pd:detaList){
            	Map<String, Object> packmap = new HashMap<String, Object>();
            	if(packages.get(i).getName()!=null){
            		packmap.put("pacn", packages.get(i).getName());
    			}else{
    				packmap.put("pacn", "");
    			}
            	if(pd.getGoodsName()!=null){
            		packmap.put("goodsName", pd.getGoodsName());
    			}else{
    				packmap.put("goodsName", "");
    			}
            	if(pd.getStand()!=null){
            		packmap.put("tand", pd.getStand());
    			}else{
    				packmap.put("tand", "");
    			}
            	packmap.put("jishu", "");
            	if(pd.getItem()!=null){
            		packmap.put("item", pd.getItem());
    			}else{
    				packmap.put("item", "");
    			}
            	if(pd.getPurchaseCount()!=null){
            		packmap.put("count", pd.getPurchaseCount());
    			}else{
    				packmap.put("count", "");
    			}
            	if(pd.getDeliverDate()!=null){
            		packmap.put("jhsj", pd.getDeliverDate());
    			}else{
    				packmap.put("jhsj", "");
    			}
            	packmap.put("jhdd", "");
            	if(pd.getMemo()!=null){
            		packmap.put("mem", pd.getMemo());
    			}else{
    				packmap.put("mem", "");
    			}
            	gaikuang.add(packmap);
            	Map<String, Object> huowumap = new HashMap<String, Object>();
            	if(packages.get(0).getName()!=null){
            		huowumap.put("baohao", packages.get(0).getName());
    			}else{
    				huowumap.put("baohao", "");
    			}
            	if(pd.getGoodsName()!=null){
            		huowumap.put("mingcheng", pd.getGoodsName());
    			}else{
    				huowumap.put("mingcheng", "");
    			}
            	if(pd.getStand()!=null){
            		huowumap.put("xinghao", pd.getStand());
    			}else{
    				huowumap.put("xinghao", "");
    			}
            	huowumap.put("canshu", "");
            	if(pd.getItem()!=null){
            		huowumap.put("jiliang", pd.getItem());
    			}else{
    				huowumap.put("jiliang", "");
    			}
            	if(pd.getPurchaseCount()!=null){
            		huowumap.put("duoshao", pd.getPurchaseCount());
    			}else{
    				huowumap.put("duoshao", "");
    			}
            	if(pd.getMemo()!=null){
            		huowumap.put("beizhu", pd.getMemo());
    			}else{
    				huowumap.put("beizhu", "");
    			}
            	huowu.add(huowumap);
            }
        	allgaikuang.add(gaikuang);
        	allhuowu.add(huowu);
        }
        
        for(int i=0;i<packages.size();i++){
       	 FirstAudit firstAudit1 = new FirstAudit();
      	  firstAudit1.setKind(DictionaryDataUtil.getId("COMPLIANCE"));
      	  firstAudit1.setPackageId(packages.get(i).getId());
      	  List<FirstAudit> items1 = firmapper.find(firstAudit1);
          //资格性审查项
      	  FirstAudit firstAudit2 = new FirstAudit();
          firstAudit2.setKind(DictionaryDataUtil.getId("QUALIFICATION"));
          firstAudit2.setPackageId(packages.get(i).getId());
          List<FirstAudit> items2 = firmapper.find(firstAudit2);
          for(FirstAudit firstA:items1){
          	Map<String, Object> fuhemap = new HashMap<String, Object>();
          	if(firstA.getName()!=null){
          		fuhemap.put("accord", firstA.getName());
  			}else{
  				fuhemap.put("accord", "");
  			}
          	fuhelist.add(fuhemap);
          }
          allfuhelist.add(fuhelist);
          for(FirstAudit firstB:items2){
          	Map<String, Object> zigeemap = new HashMap<String, Object>();
          	if(firstB.getName()!=null){
          		zigeemap.put("rc", firstB.getName());
  			}else{
  				zigeemap.put("rc", "");
  			}
          	zigelist.add(zigeemap);
          }
          allzigelist.add(zigelist);
        }
        
        for(int j=0;j<packages.size();j++){
        List<DictionaryData> ddList = DictionaryDataUtil.find(23);
        List<MarkTerm> jinjiList = new ArrayList<MarkTerm>();
        List<MarkTerm> jishuList = new ArrayList<MarkTerm>();
        for (DictionaryData dictionaryData : ddList) {
       	 if(dictionaryData.getCode().equals("ECONOMY")){
       		jinjiList = getList(dictionaryData.getId(), dictionaryData.getName(),projectId,packages.get(j).getId());
       	 }
       	 if(dictionaryData.getCode().equals("TECHNOLOGY")){
       		jishuList = getList(dictionaryData.getId(), dictionaryData.getName(),projectId,packages.get(j).getId());
       	 }
        }
        if(jinjiList.size()>0){
        	double sum = 0.0;
        	for(int i=0;i<jinjiList.size();i++){
        		Map<String, Object> jinjimap = new HashMap<String, Object>();
        		jinjimap.put("sxuhao", i+1);
            	if(jinjiList.get(i).getName()!=null){
            		jinjimap.put("sproject",jinjiList.get(i).getName() );
    			}else{
    				jinjimap.put("sproject", "");
    			}
            	if(jinjiList.get(i).getName()!=null){
            		jinjimap.put("scontent",jinjiList.get(i).getName());
    			}else{
    				jinjimap.put("scontent", "");
    			}
            	if(jinjiList.get(i).getScscore()!=null){
            		jinjimap.put("sscore",jinjiList.get(i).getScscore());
            		sum=sum+jinjiList.get(i).getScscore();
    			}else{
    				jinjimap.put("sscore", "");
    			}
            	shangwulist.add(jinjimap);
        	}
        	allshangwulist.add(shangwulist);
        	dataMap.put("ssum", sum);
        }else{
        	dataMap.put("ssum", 0.0);
        }
        
        if(jishuList.size()>0){
        	double sum = 0.0;
        	for(int i=0;i<jishuList.size();i++){
        		Map<String, Object> jishumap = new HashMap<String, Object>();
        		jishumap.put("jumber", i+1);
            	if(jishuList.get(i).getName()!=null){
            		jishumap.put("jpinshen",jishuList.get(i).getName() );
    			}else{
    				jishumap.put("jpinshen", "");
    			}
            	if(jishuList.get(i).getName()!=null){
            		jishumap.put("jcontent",jishuList.get(i).getName());
    			}else{
    				jishumap.put("jcontent", "");
    			}
            	if(jishuList.get(i).getScscore()!=null){
            		jishumap.put("jscore",jishuList.get(i).getScscore());
            		sum=sum+jishuList.get(i).getScscore();
    			}else{
    				jishumap.put("jscore", "");
    			}
            	jishulist.add(jishumap);
        	}
        	alljishulist.add(jishulist);
        	dataMap.put("jsum", sum);
        }else{
        	dataMap.put("jsum", 0.0);
        }
        }
//        dataMap.put("gaikuang", gaikuang);
        dataMap.put("allgaikuang", allgaikuang);
        dataMap.put("allhuowu", allhuowu);
        dataMap.put("allfuhelist", allfuhelist);
        dataMap.put("allzigelist", allzigelist);
        dataMap.put("allshangwulist", allshangwulist);
        dataMap.put("alljishulist", alljishulist);
        dataMap.put("huowu", huowu);
        dataMap.put("zigelist", zigelist);
        dataMap.put("fuhelist", fuhelist);
        dataMap.put("shangwulist", shangwulist);
        dataMap.put("jishulist", jishulist);
        return productionDoc(request, dataMap);
    }
    
    public List<MarkTerm> getList(String id, String name ,String projectId, String packageId) {
        MarkTerm mt = new MarkTerm();
        mt.setTypeName(id);
        mt.setProjectId(projectId);
        mt.setPackageId(packageId);
        List<MarkTerm> mtList = markTermMapper.findListByMarkTerm(mt);
        List<MarkTerm> allList = new ArrayList<MarkTerm>();
        for (MarkTerm mtKey : mtList) {
            MarkTerm mt1 = new MarkTerm();
            mt1.setPid(mtKey.getId());
            mt1.setProjectId(projectId);
            mt1.setPackageId(packageId);
            List<MarkTerm> mtValue = markTermMapper.findListByMarkTerm(mt1);
            allList.addAll(mtValue);
        }
        return allList;
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
            filePath = rootFile.getPath().replaceAll("\\\\","/");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return filePath;
    }
  
  
}
