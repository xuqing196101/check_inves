package bss.service.ppms.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;

import com.github.pagehelper.PageHelper;
import common.constant.StaticVariables;

import bss.dao.ppms.AdvancedDetailMapper;
import bss.dao.ppms.AdvancedPackageMapper;
import bss.dao.ppms.AdvancedProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.formbean.Jzjf;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.model.prms.SupplierRank;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.BidMethodService;

@Service("advancedPackageService")
public class AdvancedPackageServiceImpl implements AdvancedPackageService {

    @Autowired
    private AdvancedPackageMapper packageMapper;
    
    @Autowired
    private AdvancedProjectMapper projectMapper;
    
    @Autowired
    private AdvancedDetailMapper detailMapper;
    
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    
    @Autowired
    private QuoteMapper quoteMapper;
    
    @Autowired
    private ReviewProgressMapper reviewProgressMapper;
    
    @Autowired
    private PackageExpertMapper packageExpertMapper;
    
    @Autowired 
    private BidMethodService bidMethodService;
    
    private final static Short NUMBER_TWO = 2;
    
    @Override
    public AdvancedPackages selectById(String id) {
       
        return packageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(AdvancedPackages packages) {
       
        packageMapper.updateByPrimaryKeySelective(packages);
    }

    @Override
    public void save(AdvancedPackages packages) {
       
        packageMapper.insertSelective(packages);
    }

    @Override
    public AdvancedPackages deleteById(String id) {
        
        return packageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AdvancedPackages> selectByAll(HashMap<String, Object> map) {

        return packageMapper.selectByAll(map);
    }

    @Override
    public List<AdvancedPackages> find(AdvancedPackages packages, Integer page) {
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
        return packageMapper.find(packages);
    }

    @Override
    public List<AdvancedPackages> findPackageAndBidMethodById(HashMap<String, Object> map) {
        
        return packageMapper.findPackageAndBidMethodById(map);
    }

    @Override
    public void saves(AdvancedPackages packages) {
        packageMapper.insert(packages);   
    }

    @Override
    public List<AdvancedPackages> selectPackName(HashMap<String, Object> map, Integer pageNum) {
        PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
        return packageMapper.selectPackName(map);
    }

    @Override
    public List<AdvancedPackages> listProjectExtract(String projectId) {
        
        return packageMapper.listProjectExtract(projectId);
    }

    @Override
    public String merge(String projectId, String id) {
        String packageName = null;
        List<Integer> num = new ArrayList<Integer>();
        String[] packageId = id.split(StaticVariables.COMMA_SPLLIT);
        for (int i = 0; i < packageId.length; i++) {
            AdvancedPackages packages = packageMapper.selectByPrimaryKey(packageId[i]);
            if(packages != null){
                String substring = packages.getName().substring(1,2);
                if(Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(substring).matches()){
                    num.add(Integer.valueOf(substring));
                } else {
                    if(packageName == null){
                        packageName = packages.getName();
                    } else {
                        packageName = packageName + StaticVariables.COMMA_SPLLIT + String.valueOf(packages.getName());
                    }
                    
                }
                
            }
        }
    
        if(num != null && num.size() > 0){
            Boolean flag = IsSeries(num);
            for (Integer integer : num) {
                if(flag){
                    if(packageName == null){
                        packageName = String.valueOf(integer);
                    } else if(num.get(num.size() - 1) == integer){
                        packageName = packageName + "-" + String.valueOf(integer);
                    }
                } else {
                    if(packageName == null){
                        packageName = String.valueOf(integer);
                    } else {
                        packageName = packageName + StaticVariables.COMMA_SPLLIT + String.valueOf(integer);
                    }
                }
            }
        }
    
        AdvancedProject project = projectMapper.selectAdvancedProjectByPrimaryKey(projectId);
        if(project != null){
            //添加一个子项目
            AdvancedProject newProject = new AdvancedProject();
            if(StringUtils.isNotBlank(project.getName())){
                newProject.setName(project.getName()+"(第"+packageName+"包)");
            }
            if(StringUtils.isNotBlank(project.getProjectNumber())){
                newProject.setProjectNumber(project.getProjectNumber() + "(" + packageName + ")");
            }
            if(StringUtils.isNotBlank(project.getPrincipal())){
                newProject.setPrincipal(project.getPrincipal());
            }
            if(StringUtils.isNotBlank(project.getIpone())){
                newProject.setIpone(project.getIpone());
            }
            if(StringUtils.isNotBlank(project.getPurchaseType())){
                newProject.setPurchaseType(project.getPurchaseType());
            }
            if(StringUtils.isNotBlank(project.getPurchaseDepId())){
                newProject.setPurchaseDep(new PurchaseDep(project.getPurchaseDepId()));
            }
            if(StringUtils.isNotBlank(project.getPlanType())){
                newProject.setPlanType(project.getPlanType());
            }
            if(StringUtils.isNotBlank(project.getStatus())){
                newProject.setStatus(project.getStatus());
            }
            if(StringUtils.isNotBlank(project.getAppointMan())){
                newProject.setAppointMan(project.getAppointMan());
            }
            if(project.getIsImport() != null){
                newProject.setIsImport(project.getIsImport());
            }
            if(project.getIsRehearse() != null){
                newProject.setIsRehearse(project.getIsRehearse());
            }
            if(project.getIsProvisional() != null){
                newProject.setIsProvisional(project.getIsProvisional());
            }
            if(project.getStartTime() != null){
                newProject.setStartTime(project.getStartTime());
            }
            newProject.setCreateAt(new Date());
            newProject.setParentId(project.getId());
            newProject.setId(WfUtil.createUUID());
            projectMapper.insertSelective(newProject);
            
            
            for (int i = 0; i < packageId.length; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("packageId", packageId[i]);
                List<AdvancedDetail> details = detailMapper.selectByAll(map);
                if(details != null && details.size() > 0){
                    for (AdvancedDetail advancedDetail : details) {
                        advancedDetail.setAdvancedProject(newProject.getId());
                        detailMapper.updateByPrimaryKeySelective(advancedDetail);
                    }
                }
                AdvancedPackages packages = packageMapper.selectByPrimaryKey(packageId[i]);
                packages.setProject(newProject);
                packageMapper.updateByPrimaryKeySelective(packages);
            }
        }
    
        return id;
    }
    
    public Boolean IsSeries(List<Integer> list){
        Boolean flag = true;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) != i + 1){
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public Integer quotePrice(String projectId) {
        if(StringUtils.isNotBlank(projectId)){
            List<AdvancedPackages> packageId = packageMapper.getPackageId(projectId);
            if(packageId != null && packageId.size() > 0){
                SaleTender condition = new SaleTender();
                condition.setProjectId(projectId);
                condition.setPackages(packageId.get(0).getId());
                condition.setStatusBid(NUMBER_TWO);
                condition.setStatusBond(NUMBER_TWO);
                condition.setIsTurnUp(0);
                List<SaleTender> find = saleTenderMapper.find(condition);
                if(find != null && find.size() > 0){
                    Quote quote = new Quote();
                    quote.setProjectId(projectId);
                    quote.setPackageId(packageId.get(0).getId());
                    quote.setSupplierId(find.get(0).getSupplierId());
                    List<Quote> quotes = quoteMapper.selectByPrimaryKey(quote);
                    if(quotes != null && quotes.size() > 0){
                        if (quotes.get(0).getQuotePrice() == null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<AdvancedPackages> getPackageId(String projectId) {
        return packageMapper.getPackageId(projectId);
    }

    @Override
    public List<ReviewProgress> listResultExpert(String projectId) {
        List<ReviewProgress> reviewProgressList = new ArrayList<ReviewProgress>();
        if(StringUtils.isNotBlank(projectId)){
            List<AdvancedPackages> listResultExpert = packageMapper.listResultExpert(projectId);
            if(listResultExpert != null && !listResultExpert.isEmpty()){
                for (AdvancedPackages packages : listResultExpert) {
                    AdvancedPackages advancedPackages = packageMapper.selectByPrimaryKey(packages.getId());
                    if(advancedPackages != null && StringUtils.isNotBlank(advancedPackages.getProjectStatus())){
                        DictionaryData findById = DictionaryDataUtil.findById(advancedPackages.getProjectStatus());
                        advancedPackages.setProjectStatus(findById.getCode());
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("projectId", projectId);
                    map.put("packageId", packages.getId());
                    List<ReviewProgress> selectByMap = reviewProgressMapper.selectByMap(map);
                    List<PackageExpert> selectList = packageExpertMapper.selectList(map);
                    map.put("isAudit", 1);
                    List<PackageExpert> selectList2 = packageExpertMapper.selectList(map);
                    if (selectByMap == null || selectByMap.size() <= 0) {
                        //如果该包进度不存在
                        ReviewProgress reviewProgress = new ReviewProgress();
                        reviewProgress.setAuditStatus("0");
                        reviewProgress.setFirstAuditProgress(0.00);
                        reviewProgress.setPackageId(advancedPackages.getId());
                        reviewProgress.setPackageName(advancedPackages.getName());
                        reviewProgress.setProjectId(projectId);
                        reviewProgress.setScoreProgress(0.00);
                        reviewProgress.setTotalProgress(0.00);
                        reviewProgress.setIsGather(0);
                        reviewProgress.setFinishNum(0);
                        reviewProgress.setNoFinishNum(selectList.size());
                        reviewProgressList.add(reviewProgress);
                    } else {
                        ReviewProgress reviewProgress = selectByMap.get(0);
                        if (selectList2 != null && selectList2.size() > 0) {
                            reviewProgress.setFinishNum(selectList2.size());
                            reviewProgress.setNoFinishNum(selectList.size() - selectList2.size());
                        } else {
                            reviewProgress.setFinishNum(0);
                            reviewProgress.setNoFinishNum(selectList.size());
                        }
                        reviewProgressList.add(reviewProgress);
                    }
                }
            }
        }
        return reviewProgressList;
    }

    @Override
    public List<AdvancedPackages> resultExpert(String projectId) {
        
        return packageMapper.listResultExpert(projectId);
    }

    @Override
    public List<ProjectExt> getProjectExtList(List<AdvancedPackages> list, String expertId, String status, Integer pageNum) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        if(pageNum != null){
            PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
        }
        List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
        ProjectExt projectExt;
        for (AdvancedPackages packages : list) {
            AdvancedProject project = projectMapper.selectAdvancedProjectByPrimaryKey(packages.getProjectId());
            if (project != null) {
                projectExt = new ProjectExt();
                try {
                    PropertyUtils.copyProperties(projectExt, project);
                    projectExt.setPackageId(packages.getId());
                    projectExt.setPackageName(packages.getName());
                    //查询出关联表中包下已评审的数据
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("packageId", packages.getId());
                    map2.put("expertId", expertId);
                    List<PackageExpert> packageExpertList2 = packageExpertMapper.selectList(map2);
                    projectExt.setPackageExperts(packageExpertList2);
                    projectExtList.add(projectExt);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if ("0".equals(status)) {
            return projectExtList;
        } else {
            List<ProjectExt> projectList = new ArrayList<ProjectExt>();
            for (int i = 0; i < projectExtList.size(); i++ ) {
                List<PackageExpert> packageExpertList = projectExtList.get(i).getPackageExperts();
                if (packageExpertList != null && packageExpertList.size() > 0) {
                    PackageExpert packageExpert = packageExpertList.get(0);
                    if ("1".equals(status)) {
                        // 资格性和符合性审查
                        if (0 == packageExpert.getIsAudit() || 2 == packageExpert.getIsAudit() || (1 == packageExpert.getIsAudit() && 0 == packageExpert.getIsGather())) {
                            projectList.add(projectExtList.get(i));
                        }
                    } else if ("2".equals(status)) {
                        // 经济技术评审
                        if ((1 == packageExpert.getIsAudit() && 1 == packageExpert.getIsGather() && 0 == packageExpert.getIsGrade()) || (1 == packageExpert.getIsAudit() && 1 == packageExpert.getIsGather() && 2 == packageExpert.getIsGrade()) || (0 == packageExpert.getIsGatherGather() && 1 == packageExpert.getIsGather() && 1 == packageExpert.getIsGrade())) {
                            projectList.add(projectExtList.get(i));
                        }
                    } else if ("3".equals(status)) {
                        // 评审结束
                        if (1 == packageExpert.getIsGather() && 1 == packageExpert.getIsGatherGather()) {
                            projectList.add(projectExtList.get(i));
                        }
                    } else {
                        return projectExtList;
                    }
                }
            }
            return projectList;
        }
    }

    @Override
    public List<SupplierRank> rankList(List<SaleTender> suppList) {
        List<SupplierRank> list = new ArrayList<SupplierRank>();
        if(suppList != null && !suppList.isEmpty()){
            for (SaleTender supp : suppList) {
                SupplierRank rank = new SupplierRank();
                rank.setSupplierId(supp.getSuppliers().getId());
                rank.setPackageId(supp.getPackages());
                BigDecimal es = supp.getEconomicScore();
                if (es == null) {
                  rank.setEconScore(null);
                } else {
                  rank.setEconScore(es);
                }
                BigDecimal ts = supp.getTechnologyScore();
                if (ts == null) {
                  rank.setTechScore(null);
                } else {
                  rank.setTechScore(ts);
                }
                if (es == null || ts == null) {
                  rank.setSumScore(null);
                } else {
                  rank.setSumScore(supp.getEconomicScore().add(supp.getTechnologyScore()));
                }
                list.add(rank);
            }
        }
        if(list != null && !list.isEmpty()){
            for (SupplierRank rank : list) {
                int count = 0;
                int sum = 0;
                // 判断review_result是否不为空
                SaleTender saleTend = new SaleTender();
                saleTend.setPackages(rank.getPackageId());
                Supplier supplier = new Supplier();
                supplier.setId(rank.getSupplierId());
                saleTend.setSuppliers(supplier);
                String reviewResult = saleTenderMapper.find(saleTend).get(0).getReviewResult();
                if (StringUtils.isNotBlank(reviewResult)) {
                    rank.setRank(0);
                    rank.setReviewResult(reviewResult);
                } else {
                    for (SupplierRank temp : list) {
                        if (rank.getPackageId().equals(temp.getPackageId())) {
                            // 判断review_result是否不为空
                            SaleTender sale = new SaleTender();
                            sale.setPackages(temp.getPackageId());
                            Supplier supp = new Supplier();
                            supp.setId(temp.getSupplierId());
                            sale.setSuppliers(supp);
                            String review = saleTenderMapper.find(sale).get(0).getReviewResult();
                            if (StringUtils.isBlank(review)) {
                                sum++;
                                if (rank.getSumScore().compareTo(temp.getSumScore()) != -1 && rank != temp) {
                                    count++;
                                }
                            }
                        }
                    }
                    rank.setRank(sum - count);
                }
            }
        }
        return list;
    }

    @Override
    public List<PackageExpert> expList(String packageId) {
        List<PackageExpert> list = new ArrayList<PackageExpert>();
        if(StringUtils.isNotBlank(packageId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("packageId", packageId);
            List<PackageExpert> selectList = packageExpertMapper.selectList(map);
            if(selectList != null && !selectList.isEmpty()){
                for (PackageExpert packageExpert : selectList) {
                    DictionaryData data = DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                    if (data != null && "ECONOMY".equals(data.getCode())) {
                        list.add(packageExpert);
                    }
                }
                for (PackageExpert exp : selectList) {
                    DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                    if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                        list.add(exp);
                    }
                }
            }
        }
        if(list != null && !list.isEmpty()){
            // 遍历排好序的expertList设置rowspan
            // 获取经济类型的个数
            int count = 0;
            // 该包内的专家总数
            int sumCount = 0;
            for (PackageExpert packageExpert : list) {
                if (packageId.equals(packageExpert.getPackageId())) {
                    sumCount++;
                    DictionaryData data = DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                    if (data != null && "ECONOMY".equals(data.getCode())) {
                        count++;
                    }
                }
            }
            // 给指定位置设置rowspan
            int flag = 0;
            for (PackageExpert exp : list) {
                if (packageId.equals(exp.getPackageId())) {
                    if (count == 0 && flag == 0) {
                        // 如果没有经济类型,只有技术类型
                        exp.setCount(sumCount);
                    } else if (count == sumCount && flag == 0) {
                        // 如果全是经济类型
                        exp.setCount(sumCount);
                    } else if (count < sumCount && count > 0) {
                        // 都有
                        if (flag == 0) {
                            // 设置第一个rowspan为经济的个数
                            exp.setCount(count);
                        } else if (flag == count) {
                            // 设置第一个技术类型的rowspan为全部减去经济的个数
                            exp.setCount(sumCount - count);
                        } else {
                            exp.setCount(0);
                        };
                    } else {
                        exp.setCount(0);
                    }
                    flag++;
                }
            }
            // 将reviewTypeId的值改为name
            for (PackageExpert expert : list) {
                DictionaryData data = DictionaryDataUtil.findById(expert.getReviewTypeId());
                if (data != null) {
                    expert.setReviewTypeId(data.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<SaleTender> suppList(List<AdvancedPackages> packList, String projectId) {
        List<SaleTender> list = new ArrayList<SaleTender>();
        if(packList != null && !packList.isEmpty()){
            List<SaleTender> supplierList = new ArrayList<SaleTender>();
            SaleTender saleTender = new SaleTender();
            for (AdvancedPackages packages : packList) {
                saleTender.setPackages(packages.getId());
                saleTender.setIsFirstPass(1);
                saleTender.setIsRemoved("0");
                saleTender.setIsTurnUp(0);
                supplierList.addAll(saleTenderMapper.find(saleTender));
            }
            if(supplierList != null && !supplierList.isEmpty()){
                for (SaleTender tender : supplierList) {
                    String methodCode = bidMethodService.getMethod(projectId, tender.getPackages());
                    if (StringUtils.isNotBlank(methodCode)) {
                        if (("PBFF_JZJF".equals(methodCode))) {
                            JSONObject obj = JSONObject.fromObject(tender.getReviewResult());
                            Jzjf jzjf = (Jzjf)JSONObject.toBean(obj,Jzjf.class);
                            tender.setJzjf(jzjf);
                        }
                        if (!"OPEN_ZHPFF".equals(methodCode)) {
                            BigDecimal pass = new BigDecimal(100);
                            //合格的供应商
                            if (tender.getEconomicScore() != null && tender.getTechnologyScore() != null && tender.getEconomicScore().compareTo(pass) == 0 && tender.getTechnologyScore().compareTo(pass) == 0) {
                                list.add(tender);
                            }
                        } else {
                            list.add(tender);
                        }
                    }
                
                }
            }
        }
        return list;
    }

    @Override
    public List<AdvancedPackages> packList(String projectId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> list = packageMapper.selectByAll(map);
        List<AdvancedPackages> packList = new ArrayList<AdvancedPackages>();
        if(list != null && !list.isEmpty()){
            for (AdvancedPackages packages : list) {
                //获取评分办法数据字典编码
                String methodCode = bidMethodService.getMethod(projectId, packages.getId());
                if (StringUtils.isNotBlank(methodCode)) {
                    packages.setBidMethodTypeName(methodCode);
                }
                map.put("packageId", packages.getId());
                List<PackageExpert> selectList = packageExpertMapper.selectList(map);
                if (selectList != null && selectList.size() > 0 && selectList.get(0).getIsGatherGather() == 1) {
                    packList.add(packages);
                }
            }
        }
        return packList;
    }

    @Override
    public List<AdvancedPackages> listSupplierCheckPass(String projectId) {
        List<AdvancedPackages> list = packageMapper.listSupplierCheckPass(projectId);
        if(list != null && !list.isEmpty()){
            for (AdvancedPackages packages : list) {
                if(packages != null && StringUtils.isNotBlank(packages.getProjectStatus())){
                    packages.setProjectStatus(DictionaryDataUtil.findById(packages.getProjectStatus()).getCode());
                }
            }
        }
        return packageMapper.listSupplierCheckPass(projectId);
    }

    @Override
    public List<AdvancedPackages> notSupplierCheckPass(String projectId) {
        
        return packageMapper.notSupplierCheckPass(projectId);
    }

	@Override
	public List<AdvancedPackages> selectPackageOrderByCreated(HashMap<String, Object> map, Integer page) {
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		return packageMapper.selectPackageOrderByCreated(map);
	}

	@Override
	public List<AdvancedPackages> selectByPackageFirstAudit(HashMap<String, Object> map, Integer page) {
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		return packageMapper.selectByPackageFirstAudit(map);
	}

}
