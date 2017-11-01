package bss.service.ppms.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.dao.sms.SupplierMapper;
import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectAttachmentsMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.prms.FirstAuditMapper;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.prms.FirstAudit;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.SaleTenderService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Description:
 *
 * @author Wang Wenshuai
 * @version 2016年10月20日下午2:05:38
 * @since  JDK 1.7
 */
@Service
public class SaleTenderServiceImpl implements SaleTenderService {
    
    /** SCCUESS */
    private static final String SUCCESS = "SUCCESS";
    /** ERROR */
    private static final String ERROR = "ERROR";
    
    @Autowired
    private ProjectAttachmentsMapper attachmentMapper;
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    @Autowired
    private ProjectMapper promapper;
    @Autowired
    private MarkTermMapper markTermMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ProjectDetailMapper projectDetailMapper;
    @Autowired
    private FirstAuditMapper firmapper;
    @Autowired
    private SupplierMapper supplierMapper;
    /**
     * 包的持久层
     */
    @Autowired
    private PackageMapper packageMapper;
    
    /**
     * sqlSeesionFactory批量插入
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 项目明细
     */
    @Autowired
    private ProjectDetailService projectDetailService;
    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午2:04:10  
     * @param @param saleTender      
     * @return void
     */
    @Override
    public String insert(SaleTender saleTender) {
        saleTenderMapper.insertSelective(saleTender);
        return "sccuess";
    }

    /**
     * @Description:上传文件
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午2:05:09  
     * @param @param files      
     * @return void
     */
    @Override
    public String upload(String projectId,String saleId,String statusBid) {
        Integer uploadCount = saleTenderMapper.uploadCount(saleId);
        if (uploadCount != null && uploadCount >= 2 ){
            SaleTender saleTender = new SaleTender();
            saleTender.setId(saleId);
            saleTender.setStatusBond((short) 2 ); //保证金缴纳 1缴纳。2 未缴纳
            if (statusBid != null && "".equals(statusBid)) {
                saleTender.setStatusBid(new Short(statusBid)); //标书状态 1缴纳。2 未缴纳
            }
            saleTenderMapper.updateByPrimaryKeySelective(saleTender);
        } else {
            return "error";
        }
        return "sccuess";
    }


    /**
     * @Description:list
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午4:42:52  
     * @param @param saleTender
     * @param @return      
     * @return List<SaleTender>
     */
    public List<SaleTender> list(SaleTender saleTender,Integer pageNum){
        PageHelper.startPage(pageNum, PropUtil.getIntegerProperty("pageSize"));
        return saleTenderMapper.list(saleTender);
    }

    /**
     * @Description:
     *
     * @author Wang Wenshuai
     * @version 2016年10月21日 上午10:01:10  
     * @param @param projectId      
     * @return void
     */
    public void download(String projectId,String Id){
        SaleTender saleTender=new SaleTender();
        saleTender.setId(Id);
        saleTender.setStatusBid(new Short("2"));//标书状态 1缴纳。2 未缴纳
        saleTenderMapper.updateByPrimaryKeySelective(saleTender);
    }

    /**
     *〈简述〉条件查询
     *〈详细描述〉
     * @author Ye MaoLin
     * @param saleTender 
     * @return
     */
    public List<SaleTender> find(SaleTender saleTender){
        return saleTenderMapper.list(saleTender);
    }

    @Override
    public void update(SaleTender std) {
        saleTenderMapper.updateByPrimaryKeySelective(std);
    }

    @Override
    public List<SaleTender> getPackageNames(List<SaleTender> stList) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (SaleTender saleTender : stList) {
            List<String> packageIds = null;
            if (saleTender.getPackages() != null){
                packageIds = Arrays.asList(saleTender.getPackages().split(","));
                StringBuilder packageNames = new StringBuilder("");
                for (String string : packageIds) {
                    map.put("id", string);
                    List<Packages> packages = packageMapper.findPackageById(map);
                    //按照id查询 返回有且只有一条 所以使用get(0)
                    packageNames.append(packages.get(0).getName());
                }
                saleTender.setPackageNames(packageNames.toString());
            }
        }
        return stList;
    }

    
    /**
     *  根据项目包名，获取供应商
     */
	@Override
	public List<SaleTender> getPackegeSupplier(SaleTender record) {
		return saleTenderMapper.getPackegeSupplier(record);
	}

	
	@Override
	public List<SaleTender> getPackegeSuppliers(SaleTender record) {
		// TODO Auto-generated method stub
		return saleTenderMapper.getPackegeSuppliers(record);
	}
	
    @Override
    public List<Packages> getPackageIds(String projectId){
        return saleTenderMapper.getPackageIds(projectId);
    }

    @Override
    public HashMap<String, String> downloadBidFile(String projectId, HttpServletRequest request, String supplierId) {
      Project pro = projectMapper.selectProjectByPrimaryKey(projectId);
      SaleTender saleTender = new SaleTender();
      saleTender.setProject(pro);
      Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
      if (supplier != null) {
        saleTender.setSuppliers(supplier);
      }
      //查询该供应商参与的包
      List<Packages> packages = new ArrayList<Packages>();
      List<SaleTender> records = saleTenderMapper.find(saleTender);
      if (records != null && records.size() > 0) {
        for (SaleTender saleTender2 : records) {
          HashMap<String, Object> map = new HashMap<String, Object>();
          map.put("id", saleTender2.getPackages());
          List<Packages> list = packageMapper.findPackageById(map);
          if (list != null && list.size() > 0) {
            packages.add(list.get(0));
          }
        }
      }
      HashMap<String, Object> map1 = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if(pro.getName() != null){
          dataMap.put("name", pro.getName());
        }else{
          dataMap.put("name", "");
        }
        if(pro.getProjectNumber() != null){
          dataMap.put("code", pro.getProjectNumber());
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
        
        List<List<Map<String, Object>>> allgaikuang = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allhuowu = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allzigelist = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> allfuhelist = new ArrayList<List<Map<String, Object>>>();
        List<Map<String, Object>> zigelist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> fuhelist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> shangwulist = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> jishulist = new ArrayList<Map<String,Object>>();
        List<List<Map<String, Object>>> allshangwulist = new ArrayList<List<Map<String, Object>>>();
        List<List<Map<String, Object>>> alljishulist = new ArrayList<List<Map<String, Object>>>();
        for(int i=0;i<packages.size();i++){
          List<Map<String, Object>> gaikuang = new ArrayList<Map<String,Object>>();
           List<Map<String, Object>> huowu = new ArrayList<Map<String,Object>>();
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
    
    private HashMap<String, String> productionDoc(HttpServletRequest request, Map<String,Object> dataMap){
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
        String filePath = "";
        HashMap<String, String> returnMap = new HashMap<String, String>();
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
            returnMap.put("filePath", filePath);
            returnMap.put("fileName", fileName);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    @Override
    public List<SaleTender> findByCon(SaleTender saleTender) {
      return saleTenderMapper.find(saleTender);
    }
    
    @Override
    public List<SaleTender> finds(SaleTender saleTender) {
      return saleTenderMapper.finds(saleTender);
    }

    @Override
    public void updateResult(HashMap<String, Object> stMap) {
      saleTenderMapper.updateResult(stMap);
    }
    /**
     *〈简述〉
     * 更改SaleTender的移除状态
     *〈详细描述〉
     * @author Dell
     * @param map
     */
    @Override
    public void removeSaleTender(Map<String, Object> map) {
        saleTenderMapper.removeSaleTender(map);
    }
    
    /**
     *〈简述〉
     * 根据项目id查询所有saleTender
     *〈详细描述〉
     * @author WangHuijie
     * @param projectId
     * @return
     */
    public List<SaleTender> selectListByProjectId(String projectId) {
        return saleTenderMapper.selectListByProjectId(projectId);
    }
    
    /**
     *〈简述〉
     * 根据包id和供应商id设置经济技术总分
     *〈详细描述〉
     * @author WangHuijie
     * @param map
     */
    public void editSumScore(Map<String, Object> map) {
        saleTenderMapper.editSumScore(map);
    }
    
    /**
     * 
     *〈简述〉移除供应商
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param supplierId
     * @param packagesId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteSale")
    public String delSale(String supplierId,String packagesId){
        Map<String, String> map = new HashMap<String, String>();
        map.put("supplierId", supplierId);
        map.put("packagesId", packagesId);
        saleTenderMapper.delSaleDelete(map);
        return JSON.toJSONString(SUCCESS);
    }

    @Override
    public void updateRank(HashMap<String, Object> ranMap) {
      saleTenderMapper.updateRank(ranMap);
    }

    @Override
    public void batchUpdate(List<SaleTender> stList) {
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            //每批commit的个数
            int batchCount = 50; 
            for (int index = 0; index < stList.size(); index++){
                SaleTender st = stList.get(index);
                batchSqlSession.getMapper(SaleTenderMapper.class).updateIsTurnUpByPrimaryKey(st);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
    }
    /**
     * 实现 参数 获取项目状态
     */
    @Override
    public List<String> getBidFinish(String projectId, String supplierId) {
      return saleTenderMapper.findBySupplierIdProjectId(supplierId, projectId);
    }
    /**
     * 实现根据供应商id 和项目id获取参与项目的有效包数据
     */
    @Override
    public List<SaleTender> findPackages(String supplierId, String projectId) {
      List<SaleTender> saleTenderList =saleTenderMapper.findPackageBySupplierIdProjectId(supplierId, projectId);
      HashMap<String, Object> map=null;
      if(saleTenderList!=null && !saleTenderList.isEmpty()){
          for (SaleTender saleTender : saleTenderList) {
            map=new HashMap<String,Object>();
            map.put("projectId", saleTender.getProjectId());
            map.put("packageId", saleTender.getId());
            List<ProjectDetail> pd=projectDetailService.findByIdPackageId(map);
            saleTender.setProjectDetail(pd);
         }
      }
      return saleTenderList;
    }

    @Override
    public List<SaleTender> getAdPackegeSuppliers(HashMap<String, Object> map) {
        
        return saleTenderMapper.getAdPackegeSuppliers(map);
    }
}

