package bss.service.prms.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.sms.QuoteMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.ems.ExpExtPackage;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

import bss.dao.ppms.BidMethodMapper;
import bss.dao.ppms.MarkTermMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.dao.ppms.ScoreModelMapper;
import bss.dao.ppms.SupplierCheckPassMapper;
import bss.dao.prms.ExpertScoreMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.dao.prms.ReviewFirstAuditMapper;
import bss.dao.prms.ReviewProgressMapper;
import bss.formbean.Jzjf;
import bss.model.ppms.BidMethod;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.ExpertScore;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewFirstAudit;
import bss.model.prms.ReviewProgress;
import bss.service.ppms.SaleTenderService;
import bss.service.prms.PackageExpertService;
@Service("packageExpertService")
public class PackageExpertServiceImpl implements PackageExpertService {
	  @Autowired
	  private PackageExpertMapper mapper;
	  @Autowired
	  private ExpertScoreMapper expertScoremapper;
	  @Autowired
    private ReviewProgressMapper reviewProgressMapper;
	  @Autowired
    private ExpertScoreMapper expertScoreMapper;
    @Autowired
    private PackageExpertMapper packageExpertMapper;
    @Autowired
    private SaleTenderService saleTenderService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ProjectExtractService extractService; //关联表
    @Autowired
    RoleServiceI roleService; //权限
    @Autowired
    private PreMenuServiceI menuService;// 菜单
    @Autowired
    UserServiceI userServiceI;//用户管理
    @Autowired
    BidMethodMapper bidMethodMapper;
    @Autowired
    private QuoteMapper quoteMapper;
    @Autowired
    private ScoreModelMapper scoreModelMapper;
    @Autowired
    private MarkTermMapper markTermMapper;
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;
    @Autowired
    private SupplierCheckPassMapper checkPassMapper;
    @Autowired
    private SaleTenderMapper saleTenderMapper;
      
      
	  /**
	   * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月18日 下午2:18:45  
	  * @Description: TODO 保存
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	  @Override
	  public int save(PackageExpert record) {
	    // TODO Auto-generated method stub
	    return mapper.insert(record);
	  }
    /**
     * 
      * @Title: selectList
      * @author ShaoYangYang
      * @date 2016年10月18日 下午2:16:43  
      * @Description: TODO 条件查询
      * @param @param map
      * @param @return      
      * @return List<PackageExpert>
     */
	  @Override
	  public List<PackageExpert> selectList(Map<String, Object> map) {
	    // TODO Auto-generated method stub
	    return mapper.selectList(map);
	  }
	    /**
	     * 
      * @Title: deleteByPackageId
      * @author ShaoYangYang
      * @date 2016年10月18日 下午2:17:16  
      * @Description: TODO 根据包id删除关联信息
      * @param @param packageId      
      * @return void
     */
	  @Override
	  public void deleteByPackageId(String packageId) {
	    mapper.deleteByPackageId(packageId);
	  }
	    /**
	     * 
      * @Title: updateByBean
      * @author ShaoYangYang
      * @date 2016年10月27日 下午5:28:53  
      * @Description: TODO 根据条件修改信息
      * @param @param record      
      * @return void
     */
    public void updateByBean(PackageExpert record){
      mapper.updateByBean(record);
    }
    /**
     * 
      * @Title: updateScore
      * @author ShaoYangYang
      * @date 2016年11月18日 下午6:22:07  
      * @Description: TODO 修改评分状态  供PackageExpertController中调用
      * @param @param map      
      * @return void
     */
    public void updateScore(Map<String , Object> map){
    	List<PackageExpert> packageExpertList = selectList(map);
		for (PackageExpert packageExpert : packageExpertList) {
			packageExpert.setIsGrade((short) 0);
			updateByBean(packageExpert);
		}
    }
    /**
     * 
     * @Title: findMarkTypeByProId
     * @author WangHuijie
     * @date 2016年11月30日 上午10:22:07  
     * @Description: TODO 查询审查项的类型  供PackageExpertController中调用
     * @param @param projectId      
     * @return List<Map<String, Object>>
    */
    @Override
    public List<Map<String, Object>> findMarkTypeByProId(String projectId) {
        return mapper.findMarkTypeByProId(projectId);
    }
    /**
     *〈简述〉
     * 根据包id和项目id查询分数
     *〈详细描述〉
     * @author Wang Huijie
     * @param mapSearch
     * @return
     */
    @Override
    public List<Map<String, Object>> findScoreByMap(Map<String, Object> mapSearch) {
        // TODO Auto-generated method stub
        return mapper.findScoreByMap(mapSearch);
    }
    /**
     *〈简述〉
     * 退回分数
     *〈详细描述〉
     * @author Wang Huijie
     * @param mapSearch
     */
    @Override
    public void backScore(Map<String, Object> mapSearch) {
        // 将SaleTender表中的经济技术分清空
        Map<String, Object> edMap = new HashMap<String, Object>();
        edMap.put("packageId", (String) mapSearch.get("packageId"));
        edMap.put("economicScore", new BigDecimal(0));
        edMap.put("technologyScore", new BigDecimal(0));
        edMap.put("reviewResult","");
        SaleTender saleTender = new SaleTender();
        saleTender.setPackages((String) mapSearch.get("packageId"));
        List<SaleTender> supplierList = saleTenderService.find(saleTender);
        for (SaleTender st : supplierList) {
            edMap.put("supplierId", st.getSuppliers().getId());
            saleTenderService.editSumScore(edMap);
        }
        String expertIds = (String) mapSearch.get("expertId");
        String[] ids = expertIds.split(",");
        mapSearch.remove("expertId");
        // 查询包下有几个专家
        for (String expertId : ids) {
            //mapSearch.remove("expertId");
            //mapSearch.put("expertId", expertId.replace("undefined", ""));
            // 1.EXPERT_SCORE表中IS_HISTORY改为1
            mapSearch.put("expertId", expertId);
            //expertScoremapper.backScore(mapSearch);
            // 2.评分进度减去对应的值
            List<PackageExpert> list = mapper.selectList(mapSearch);
            // 遍历判断该专家有没有进行打分,如果有就需要减去评分进度
            /*boolean isGrade = true;
            for (PackageExpert packageExpert : list) {
                if (packageExpert.getIsGrade() == 0) {
                    isGrade = false;
                }
            }
            if (isGrade) {
                double score = 1/length;
                mapSearch.put("score", score);
                reviewProgressMapper.backScore(mapSearch);
            }*/
            // 3.PACKAGE_EXPERT表中的IS_GRADE改为0
            mapper.backScore(mapSearch);
        }
        mapSearch.remove("expertId");
        List<ReviewProgress> list = reviewProgressMapper.selectByMap(mapSearch);
        if(list != null && list.size() > 0){
            ReviewProgress reviewProgress3 = list.get(0);
           /* reviewProgress3.setTotalProgress((reviewProgress3.getFirstAuditProgress() + reviewProgress3.getScoreProgress())/2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", reviewProgress3.getId());
            map.put("totalProgress", reviewProgress3.getTotalProgress());
            reviewProgressMapper.updateTotalProgress(map);*/
            //设置状态为经济技术评审中
            reviewProgress3.setAuditStatus("3");
            //退回专家人数
            //double backs = (double) expertIdArr.length;
            //初审进度
            double firstProgress = 0;
            //总进度
            double totalProgress = 0;
            //评分进度
            double scoreProgress = 0;
            // 查询是否已评审
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("packageId", mapSearch.get("packageId"));
            map1.put("projectId", mapSearch.get("projectId"));
            map1.put("isGrade", 1);
            //查询包下已经的评审专家
            List<PackageExpert> expertAuditeds = mapper.selectList(map1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("packageId", mapSearch.get("packageId"));
            map.put("projectId", mapSearch.get("projectId"));
            //查询包下全部评审专家
            List<PackageExpert> packageExpertList = mapper.selectList(map);
            double second = 0;
            second = ((double)expertAuditeds.size())/(double)packageExpertList.size();
            BigDecimal b = new BigDecimal(second); 
            scoreProgress = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //评审进度更新
            reviewProgress3.setScoreProgress(scoreProgress);
            //初审进度
            firstProgress = reviewProgress3.getFirstAuditProgress();
            //总进度更新
            totalProgress =  (firstProgress+scoreProgress)/2;
            BigDecimal t = new BigDecimal(totalProgress); 
            totalProgress  = t.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //总进度更新
            reviewProgress3.setTotalProgress(totalProgress);
            reviewProgressMapper.updateByMap(reviewProgress3);
        }
    }
    /**
     *〈简述〉
     * 判断是否满足汇总条件
     *〈详细描述〉
     * @author WangHuijie
     * @param packageIds
     * @return
     */
    @Override
    public String isGather(String packageIds, String projectId) {
        Map<String, Object> mapSearch = new HashMap<String, Object>();
        mapSearch.put("projectId", projectId);
        String notPass = new String();
        String ids[] = packageIds.split(",");
        for (String packageId : ids) {
            // isok == 0 代表满足汇总条件
            mapSearch.put("packageId", packageId);
            // 判断如果该包的评分进度不是100%不能汇总
            List<ReviewProgress> reviewList = reviewProgressMapper.selectByMap(mapSearch);
            if (!reviewList.isEmpty()) {
                if (reviewList.get(0).getTotalProgress() != 1) {
                    notPass = "评审尚未全部完成,无法结束!";
                }
            }  else {
                notPass = "评审尚未全部完成,无法结束!";
            }
            // 判断如果包内的专家所给出的分数不同的话不能汇总
            List<ExpertScore> expertScoreList = expertScoreMapper.selectByMap(mapSearch);
            int flag = 0;
            String msg = "";
            for (int i = 0; i < expertScoreList.size() - 1; i++ ) {
                ExpertScore scoreExp1 = expertScoreList.get(i);
                for (int j = i+ 1; j < expertScoreList.size(); j++ ) {
                    ExpertScore scoreExp2 = expertScoreList.get(j);
                    if (scoreExp1.getSupplierId().equals(scoreExp2.getSupplierId()) && scoreExp1.getScoreModelId().equals(scoreExp2.getScoreModelId()) && !scoreExp1.getExpertId().equals(scoreExp2.getExpertId()) && !scoreExp1.getScore().equals(scoreExp2.getScore())) {
                      ScoreModel scoreModel = new ScoreModel();
                      scoreModel.setId(scoreExp2.getScoreModelId());
                      List<ScoreModel> scoreModels = scoreModelMapper.find(scoreModel);
                      if (scoreModels != null && scoreModels.size() > 0) {
                        msg += "【" + scoreModels.get(0).getName() + "】";
                        flag = 1;
                      }
                    }
                }
            } 
            if (flag == 1) {
              notPass += msg + "评审项各专家打分不一致,无法结束!";
            }
        }
        if (!"".equals(notPass)) {
            return notPass;
        } else {
            return "ok";
        }
    }
    @Override
    public String isFirstGather(String projectId, String packageId) {
      Map<String, Object> map= new HashMap<String, Object>();
      map.put("projectId", projectId);
      map.put("packageId", packageId);
      map.put("isAudit", 1);
      //查询出关联表中包下已评审的数据
      List<PackageExpert> packageExpertList = packageExpertMapper.selectList(map);
      Map<String,Object> map2 = new HashMap<String,Object>(); 
      map2.put("projectId", projectId);
      map2.put("packageId", packageId);
      //查询出关联表中包下所有的数据
      List<PackageExpert> packageExpertList2 = packageExpertMapper.selectList(map2);
      if (packageExpertList.size() < packageExpertList2.size() ) {
        return "符合性检查未完成不能结束！";
      } else {
        //更新T_BSS_PPMS_SALE_TENDER表中isFirstPass是否通过符合性审查
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        saleTender.setPackages(packageId);
        //查询该包下参与的供应商
        List<SaleTender> sl = saleTenderService.findByCon(saleTender);
        for (SaleTender saleTender2 : sl) {
          /*//评审该供应商合格的专家人数
          int isPass = 0;
          //评审该供应商不合格的专家人数
          int notPass = 0;*/
          for (PackageExpert packageExpert : packageExpertList) {
            packageExpert.setIsGather((short)1);
            //更新专家对该包的评审状态为结束
            packageExpertMapper.updateByBean(packageExpert);
            
            /*HashMap<String, Object> reviewFirstAuditMap = new HashMap<String, Object>();;
            reviewFirstAuditMap.put("supplierId", saleTender2.getSuppliers().getId());
            reviewFirstAuditMap.put("packageId", packageId);
            reviewFirstAuditMap.put("expertId", packageExpert.getExpertId());
            reviewFirstAuditMap.put("isBack", 0);
            List<ReviewFirstAudit> reviewFirstAudits = reviewFirstAuditMapper.selectList(reviewFirstAuditMap);
            if (reviewFirstAudits != null && reviewFirstAudits.size() > 0) {
              int count2 = 0;
              for (ReviewFirstAudit reviewFirstAudit : reviewFirstAudits) {
                  if (reviewFirstAudit.getIsPass() == 1) {
                      count2 ++;
                      break;
                  }
              }
              if (count2 > 0) {
                notPass ++;
              } else {
                isPass ++;
              }
            }*/
          }
          /*if (notPass > isPass) {
            //不通过的专家人数多于通过的专家人数
            saleTender2.setIsFirstPass(0);
            saleTenderService.update(saleTender2);
          } else if (isPass > notPass) {
            //通过的专家人数多于不通过的专家人数
            saleTender2.setIsFirstPass(1);
            saleTenderService.update(saleTender2);
          }*/
        }
        return "SUCCESS";
      }
    }
    @Override
    public void saveTempExpert(PackageExpert packageExpert, String packageId) {
      Expert expert = packageExpert.getExpert();
      expert.setIsProvisional(new Short("1"));
      expert.setStatus("5");
      expert.setIsSubmit("1");
      expert.setExpertsTypeId(packageExpert.getReviewTypeId());
      expertService.insertSelective(expert);
      //插入专家抽取关联表
      ProjectExtract extract = new ProjectExtract();
      extract.setExpertId(expert.getId());
      extract.setOperatingType((short)1);
      extract.setIsProvisional((short)1);
      ExpExtPackage expExtPackage=new ExpExtPackage();
      expExtPackage.setPackageId(packageId);
      extract.setProjectId(packageId);
      extract.setReviewType(packageExpert.getReviewTypeId());
      extractService.insertProjectExtract(extract);
      //插入登录表
      User user = new User();
      user.setLoginName(expert.getMobile());
      user.setTypeId(expert.getId());
      user.setPassword("123456");
      userServiceI.save(user, null);
      //新增权限
      Role role = new Role();
      role.setCode("EXPERT_R");
      List<Role> listRole = roleService.find(role);
      if (listRole != null && listRole.size() > 0) {
          Userrole userrole = new Userrole();
          userrole.setRoleId(listRole.get(0));
          userrole.setUserId(user);
          /** 删除用户之前的菜单权限*/
          UserPreMenu userPreMenu = new UserPreMenu();
          userPreMenu.setUser(user);
          userServiceI.deleteUserMenu(userPreMenu);
          /** 删除用户之前的角色信息*/
          /** 给该用户初始化专家角色 */
          userServiceI.saveRelativity(userrole);
          String[] roleIds = listRole.get(0).getId().split(",");
          List<String> listMenu = menuService.findByRids(roleIds);
          /** 给用户初始化专家菜单权限 */
          for (String menuId : listMenu) {
              UserPreMenu upm = new UserPreMenu();
              PreMenu preMenu = menuService.get(menuId);
              upm.setPreMenu(preMenu);
              upm.setUser(user);
              userServiceI.saveUserMenu(upm);
          }
      }
    }
    @Override
    public HashMap<String, Object> countMethod(List<SaleTender> supplierList, String projectId, String packageId, SaleTender saleTender, BigDecimal economicScore, BigDecimal technologyScore) {
      HashMap<String, Object> resultMap = new HashMap<String, Object>();
      int flag = 0;
      String msg = "";
      //包内供应商总得分
      BigDecimal totalScore = new BigDecimal(0);
      //包内供应商平均得分
      BigDecimal totalScoreAver = new BigDecimal(0);
      //低于标准的分数
      BigDecimal totalScoreStandard = new BigDecimal(0);
      //包内供应商总报价
      BigDecimal totalPrice = new BigDecimal(0);
      //包内供应商平均报价
      BigDecimal totalPriceAver = new BigDecimal(0);
      //高于报价的金额数
      BigDecimal totalPriceStandard = new BigDecimal(0);
      //报价比例
      BigDecimal totalPricePercent = new BigDecimal(0);
      //评分比例
      BigDecimal totalScorePercent = new BigDecimal(0);
      //该供应商总报价
      BigDecimal totalPriceSupplier = new BigDecimal(0);
      int supplierNum0 = supplierList.size();
      BigDecimal supplierNum = new BigDecimal(supplierNum0);
      
      //四种评分办法计算
      BidMethod condition = new BidMethod();
      condition.setProjectId(projectId);
      condition.setPackageId(packageId);
      List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
      List<DictionaryData> ddList = DictionaryDataUtil.find(27);
      ddList.get(Integer.parseInt(bmList.get(0).getTypeName()));
      if (bmList != null && bmList.size() > 0 && ddList != null && ddList.size() > 0) {
        Integer position = Integer.parseInt(bmList.get(0).getTypeName());
        String aduitMethodCode = ddList.get(position).getCode();
        //综合评分法
        if ("OPEN_ZHPFF".equals(aduitMethodCode)) {
          totalScorePercent = bmList.get(0).getBusiness().divide(new BigDecimal(100));
          totalPricePercent = bmList.get(0).getValid().divide(new BigDecimal(100));
          //计算所有供应商总分
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("packageId", packageId);
          //供应商总分数
          totalScore = getTotalScore(supplierList, map);
          //供应商平均得分
          totalScoreAver = totalScore.divide(supplierNum, 4, RoundingMode.HALF_UP);
          //平均得分偏离值
          totalScoreStandard = totalScoreAver.multiply(totalScorePercent);
          //计算总报价
          totalPrice = getTotalPrice(packageId, projectId, supplierList);
          //所有供应商平均报价
          totalPriceAver = totalPrice.divide(supplierNum, 4, RoundingMode.HALF_UP);
          //平均报价偏离值
          totalPriceStandard = totalPriceAver.multiply(totalPricePercent);
          
          msg += totalPriceAver+"_"+totalScoreAver+"_";
          //该供应商平均分偏离计算
          BigDecimal totalSupplier = new BigDecimal(0);
          totalSupplier= totalSupplier.add(economicScore);
          totalSupplier= totalSupplier.add(technologyScore);
          if (totalSupplier.compareTo(totalScoreAver) == -1) {
              //小于平均分的分数
              BigDecimal v = totalScoreAver.subtract(totalSupplier);
              //如果偏离值大于标准偏离值
              if (totalScoreStandard.compareTo(v) == -1) {
                BigDecimal percent = totalScorePercent.multiply(new BigDecimal(100));
                msg += "经济技术平均得分低于有效经济技术平均得分的"+percent+"%.";
                flag = 1;
              }
          } else {
            msg += "";
          }
          totalPriceSupplier = getTocalPriceSupplier(saleTender, projectId, packageId);
          saleTender.setTotalPrice(totalPriceSupplier);
          if (totalPriceSupplier.compareTo(totalPriceAver) == 1) {
              //供应商与平均报价金额的偏离值
              BigDecimal v = totalPriceSupplier.subtract(totalPriceAver);
              //如果偏离值大于标准偏离值
              if (totalPriceStandard.compareTo(v) == -1) {
                BigDecimal percent = totalPricePercent.multiply(new BigDecimal(100));
                msg += "报价高于有效平均报价的"+percent+"%.";
                flag = 1;
              }
          } else {
            msg += "";
          }
          
          if (flag == 1) {
            resultMap.put("finalSupplier", null);
          } 
          if (flag == 0) {
            //返回有效供应商+
            Jzjf jzjf = new Jzjf();
            jzjf.setEffectiveAverageQuotation(totalPriceAver.toString());
            jzjf.setEffectiveAverageScore(totalScoreAver.toString());
            saleTender.setJzjf(jzjf);
            resultMap.put("finalSupplier", saleTender);
          }
        }
        //基准价法
        if ("PBFF_JZJF".equals(aduitMethodCode)) {
          totalPricePercent = bmList.get(0).getValid().divide(new BigDecimal(100));
          //计算总报价
          totalPrice = getTotalPrice(packageId, projectId, supplierList);
          //所有供应商平均报价
          totalPriceAver = totalPrice.divide(supplierNum, 4, RoundingMode.HALF_UP);
          //平均报价偏离值
          totalPriceStandard = totalPriceAver.multiply(totalPricePercent);
          //该供应商平均报价偏离计算
          totalPriceSupplier =  getTocalPriceSupplier(saleTender, projectId, packageId);
          
          //如果供应商报价高于所有供应商的平均报价
          if (totalPriceSupplier.compareTo(totalPriceAver) == 1) {
              //小于平均分的分数
              BigDecimal v = totalPriceSupplier.subtract(totalPriceAver);
              //如果偏离值大于标准偏离值
              if (totalPriceStandard.compareTo(v) == -1) {
                BigDecimal percent = totalPricePercent.multiply(new BigDecimal(100));
                msg += "报价高于有效平均报价的"+percent+"%.";
                flag = 1;
              }
          } 
          if (flag == 1) {
            resultMap.put("finalSupplier", null);
          } 
          if (flag == 0) {
            //返回有效供应商
            resultMap.put("finalSupplier", saleTender);
          }
        }
        //性价比法
        if ("PBFF_XJBF".equals(aduitMethodCode)) {
          resultMap.put("finalSupplier", saleTender);
        }
        //最低价法
        if ("PBFF_ZDJF".equals(aduitMethodCode)) {
          resultMap.put("finalSupplier", saleTender);
        }
      }
      resultMap.put("reviewResult", msg);
      resultMap.put("flag", flag);
      resultMap.put("totalPriceSupplier", totalPriceSupplier);
      return resultMap;
    }
    
    
    //计算供应商总得分
    BigDecimal getTotalScore(List<SaleTender> supplierList, Map<String, Object> map){
        //包内供应商总得分
        BigDecimal totalScore = new BigDecimal(0);
        String packageId = (String) map.get("packageId");
        //获取该包的经济评审专家
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("packageId", packageId);
        map1.put("reviewTypeId", DictionaryDataUtil.getId("ECONOMY"));
        List<PackageExpert>  economyExperts = packageExpertMapper.selectList(map1);
        //获取该包的技术评审专家
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("packageId", packageId);
        map2.put("reviewTypeId", DictionaryDataUtil.getId("TECHNOLOGY"));
        List<PackageExpert> technologyExperts = packageExpertMapper.selectList(map2);
        //经济总得分
        BigDecimal economicScore = new BigDecimal(0);
        //技术总得分
        BigDecimal technologyScore = new BigDecimal(0);
        for (SaleTender saleTender : supplierList) {
            if (economyExperts != null && economyExperts.size() > 0) {
              Map<String, Object> map3 = new HashMap<String, Object>();
              map3.put("expertId", economyExperts.get(0).getExpert().getId());
              map3.put("packageId", packageId);
              map3.put("supplierId", saleTender.getSuppliers().getId());
              BigDecimal supplierEconomicScore = expertScoremapper.selectSumByMap(map3);
              economicScore = economicScore.add(supplierEconomicScore);
            }
            if (technologyExperts != null && technologyExperts.size() > 0) {
              Map<String, Object> map4 = new HashMap<String, Object>();
              map4.put("expertId", technologyExperts.get(0).getExpert().getId());
              map4.put("packageId", packageId);
              map4.put("supplierId", saleTender.getSuppliers().getId());
              BigDecimal supplierTechnologyScore = expertScoremapper.selectSumByMap(map4);
              technologyScore = technologyScore.add(supplierTechnologyScore);
            }
        }
        totalScore = totalScore.add(economicScore);
        totalScore = totalScore.add(technologyScore);
        return totalScore;
    }
    
    //计算供应商总报价
    public BigDecimal getTotalPrice(String packageId, String projectId, List<SaleTender> supplierList){
      BigDecimal totalPrice = new BigDecimal(0);
      for (SaleTender sale : supplierList) {
        BigDecimal totalPriceSupplier = new BigDecimal(0);
        Supplier supplier = sale.getSuppliers();
        totalPriceSupplier =  getTocalPriceSupplier(sale, projectId, packageId);
        /*Quote quote = new Quote();
        quote.setProjectId(projectId);
        quote.setPackageId(packageId);
        quote.setSupplierId(supplier.getId());
        List<Quote> allQuote = quoteMapper.selectByPrimaryKey(quote);
        
        if (allQuote != null && allQuote.size()>0) {
            if (allQuote.get(0).getQuotePrice() == null) {
              totalPriceSupplier = totalPriceSupplier.add(allQuote.get(0).getTotal());
            } else {
                BigDecimal totalPrice2 = BigDecimal.ZERO;
                for (Quote q : allQuote) {
                    totalPrice2 = q.getQuotePrice().add(totalPrice2);
                }
                totalPriceSupplier = totalPriceSupplier.add(totalPrice2);
            }
        }*/
        totalPrice = totalPrice.add(totalPriceSupplier);
      }
      return totalPrice;
    }
    
    /**
     *〈简述〉
     * 对List<ExpertScore>去重(供应商排名)
     *〈详细描述〉
     * @author WangHuijie
     * @param list
     * @return
     */
    private List<ExpertScore> removeRankSame(List<ExpertScore> list){
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1 ; j > i; j--) {
                if (list.get(i).getScoreModelId().equals(list.get(j).getScoreModelId()) && list.get(i).getSupplierId().equals(list.get(j).getSupplierId()) && list.get(i).getPackageId().equals(list.get(j).getPackageId())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    
    @Override
    public void rank(final String packageId, final String projectId, List<SaleTender> finalSupplier) {
      BidMethod condition = new BidMethod();
      condition.setProjectId(projectId);
      condition.setPackageId(packageId);
      List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
      List<DictionaryData> ddList = DictionaryDataUtil.find(27);
      Integer position = Integer.parseInt(bmList.get(0).getTypeName());
      String aduitMethodCode = ddList.get(position).getCode();
      //综合评分法
      if ("OPEN_ZHPFF".equals(aduitMethodCode)) {
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore());
        }
        //根据总得分排名
        Collections.sort(finalSupplier,new Comparator<SaleTender>(){
          public int compare(SaleTender o1, SaleTender o2) {  
            BigDecimal totalScore1 = new BigDecimal(0);
            totalScore1 = totalScore1.add(o1.getEconomicScore());
            totalScore1 = totalScore1.add(o1.getTechnologyScore());
            /*totalScore1 = totalScore1.add(o1.getPriceScore());*/
            BigDecimal totalScore2 = new BigDecimal(0);
            totalScore2 = totalScore2.add(o2.getEconomicScore());
            totalScore2 = totalScore2.add(o2.getTechnologyScore());
            /*totalScore2 = totalScore2.add(o2.getPriceScore());*/
            if(totalScore1.compareTo(totalScore2) == 1){  
                return -1;
            }  
            //总得分相同则按报价排名
            if(totalScore1.compareTo(totalScore2) == 0){
                BigDecimal price1 = o1.getTotalPrice();
                BigDecimal price2 = o2.getTotalPrice();
                if(price1.compareTo(price2) == 1){ 
                  return 1;
                }
                if(price1.compareTo(price2) == 0){ 
                  return 0;
                }
                if(price1.compareTo(price2) == -1){ 
                  return -1;
                }
                return 0;
            }
            if(totalScore1.compareTo(totalScore2) == -1){  
              return 1;  
            }  
            return 0; 
          }
        });   
        Integer ranking=1;
        if(finalSupplier!=null&&finalSupplier.size()>0){
          for(int i=0;i<finalSupplier.size();i++){
            SaleTender saleTender = finalSupplier.get(i);
            if(i==1){
              saleTender.setRanking(ranking);
            }else if(i!=finalSupplier.size()-1){
              SaleTender saleTender2 = finalSupplier.get(i+1);
              if(saleTender.getEconomicScore().add(saleTender.getTechnologyScore()).equals(saleTender2.getEconomicScore().add(saleTender2.getTechnologyScore()))){
                if(saleTender.getTotalPrice().doubleValue()<saleTender2.getTotalPrice().doubleValue()){
                  saleTender.setRanking(ranking);
                  ranking++;
                }else if(saleTender.getTotalPrice().doubleValue()>saleTender2.getTotalPrice().doubleValue()){
                  saleTender2.setRanking(ranking);
                  ranking++;
                  saleTender.setRanking(ranking);
                }else{
                  saleTender2.setRanking(ranking);
                  saleTender.setRanking(ranking);
                }
               
              }else if( saleTender.getEconomicScore().add(saleTender.getTechnologyScore()).doubleValue()<saleTender2.getEconomicScore().add(saleTender2.getTechnologyScore()).doubleValue()){
                saleTender.setRanking(ranking);
                ranking++;
              }
            }else{
              SaleTender saleTender1 = finalSupplier.get(i-1);
              if(saleTender.getEconomicScore().add(saleTender.getTechnologyScore()).equals(saleTender1.getEconomicScore().add(saleTender1.getTechnologyScore()))){
                saleTender.setRanking(ranking);
              }else if( saleTender1.getEconomicScore().add(saleTender1.getTechnologyScore()).doubleValue()<saleTender.getEconomicScore().add(saleTender.getTechnologyScore()).doubleValue()){
                saleTender.setRanking(ranking);
                ranking++;
              }
            }
          }
        }
        
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore()+"-=-="+obj.getRanking()+"-=-="+obj.getTotalPrice());
        }
      }
      
      //基准价法
      if ("PBFF_JZJF".equals(aduitMethodCode)) {
        //合格供应商总报价
        BigDecimal passSupplierPrice = getTotalPrice(packageId, projectId, finalSupplier);
        //合格供应商数量
        int passNum = finalSupplier.size();
        BigDecimal passSupplierNum = new BigDecimal(passNum);
        //基准价
        BigDecimal benchmarkPrice = passSupplierPrice.divide(passSupplierNum, 4, RoundingMode.HALF_UP);
        //浮动比例
        BigDecimal slidingCales = new BigDecimal(bmList.get(0).getFloatingRatio());
        slidingCales = slidingCales.divide(new BigDecimal(100));
        
        //中标参考价
        BigDecimal temp = new BigDecimal(1);
        temp = temp.subtract(slidingCales);
        final BigDecimal winConsultPrice = temp.multiply(benchmarkPrice);
        //根据最接近中标参考价排名
        Collections.sort(finalSupplier,new Comparator<SaleTender>(){
          public int compare(SaleTender o1, SaleTender o2) {  
            //供应商1报价
            BigDecimal price1 = o1.getTotalPrice();
            //供应商1报价与中标参考价的绝对值
            BigDecimal temp1 = price1.subtract(winConsultPrice);
            BigDecimal priceAbsolute1 = temp1.abs();
            //供应商2报价
            BigDecimal price2 = o2.getTotalPrice();
            //供应商2报价与中标参考价的绝对值
            BigDecimal temp2 = price2.subtract(winConsultPrice);
            BigDecimal priceAbsolute2 = temp2.abs();
            if(priceAbsolute1.compareTo(priceAbsolute2) == 1){  
                return 1;
            }  
            //总得分相同则按报价排名
            if(priceAbsolute1.compareTo(priceAbsolute2) == 0){
                return 0;
            }
            if(priceAbsolute1.compareTo(priceAbsolute2) == -1){  
              return -1;  
            }  
            return 0; 
          }
        });
      }
      //性价比法
      if ("PBFF_XJBF".equals(aduitMethodCode)) {
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore());
        }
        //根据供应商的（经济分数+技术分数）/最新报价排名
        Collections.sort(finalSupplier,new Comparator<SaleTender>(){
          public int compare(SaleTender o1, SaleTender o2) {  
            BigDecimal scorePrice1 = new BigDecimal(0);
            BigDecimal totalScore1 = new BigDecimal(0);
            BigDecimal totalPrice1 = getTocalPriceSupplier(o1, projectId, packageId);
            totalScore1 = totalScore1.add(o1.getEconomicScore());
            totalScore1 = totalScore1.add(o1.getTechnologyScore());
            scorePrice1 = totalScore1.divide(totalPrice1, 4, RoundingMode.HALF_UP);
                
            BigDecimal scorePrice2 = new BigDecimal(0);
            BigDecimal totalScore2 = new BigDecimal(0);
            BigDecimal totalPrice2 = getTocalPriceSupplier(o2, projectId, packageId);
            totalScore2 = totalScore2.add(o2.getEconomicScore());
            totalScore2 = totalScore2.add(o2.getTechnologyScore());
            scorePrice2 = totalScore2.divide(totalPrice2, 4, RoundingMode.HALF_UP);
            if(scorePrice1.compareTo(scorePrice2) == 1){  
                return -1;
            }  
            if(scorePrice1.compareTo(scorePrice2) == 0){  
                return 0;  
            }
            if(scorePrice1.compareTo(scorePrice2) == -1){  
              return 1;  
            }  
            return 0; 
          }
        });   
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore());
        }
      }
      //最低价法
      if ("PBFF_ZDJF".equals(aduitMethodCode)) {
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore());
        }
        //根据供应商的最新报价排名
        Collections.sort(finalSupplier,new Comparator<SaleTender>(){
          public int compare(SaleTender o1, SaleTender o2) {  
            BigDecimal totalPrice1 = getTocalPriceSupplier(o1, projectId, packageId);
            BigDecimal totalPrice2 = getTocalPriceSupplier(o2, projectId, packageId);
            if(totalPrice1.compareTo(totalPrice2) == 1){  
                return 1;
            }  
            if(totalPrice1.compareTo(totalPrice2) == 0){  
                return 0;  
            }
            if(totalPrice1.compareTo(totalPrice2) == -1){  
              return -1;  
            }  
            return 0; 
          }
        });   
        for (SaleTender obj : finalSupplier) {
          System.out.println(obj.getEconomicScore()+"--"+obj.getTechnologyScore());
        }
      }
      
    }
    
    //获取供应商报价
    BigDecimal getTocalPriceSupplier(SaleTender saleTender, String projectId, String packageId){
      BigDecimal totalPriceSupplier = new BigDecimal(0);
      Quote quote = new Quote();
      quote.setProjectId(projectId);
      quote.setPackageId(packageId);
      quote.setSupplierId(saleTender.getSuppliers().getId());
      List<Date> listDate = quoteMapper.selectQuoteCount(quote);
      //如果时间只有一次则不是多次报价
      if (listDate != null && listDate.size() == 1) {
          List<Quote> allQuote = quoteMapper.selectByPrimaryKey(quote);
          if (allQuote != null && allQuote.size()>0) {
              if (allQuote.get(0).getQuotePrice() == null) {
                totalPriceSupplier = totalPriceSupplier.add(allQuote.get(0).getTotal());
              } else {
                  BigDecimal totalPrice2 = BigDecimal.ZERO;
                  for (Quote q : allQuote) {
                      totalPrice2 = q.getTotal().add(totalPrice2);
                  }
                  totalPriceSupplier = totalPriceSupplier.add(totalPrice2);
              }
          }
      }
      
      if (listDate != null && listDate.size() > 1) {
          quote.setCreatedAt(new Timestamp(listDate.get(listDate.size() - 1 ).getTime()));
          List<Quote> allQuote = quoteMapper.selectByPrimaryKey(quote);
          if (allQuote != null && allQuote.size()>0) {
              if (allQuote.get(0).getQuotePrice() == null) {
                totalPriceSupplier = totalPriceSupplier.add(allQuote.get(0).getTotal());
              } else {
                  BigDecimal totalPrice2 = BigDecimal.ZERO;
                  for (Quote q : allQuote) {
                      totalPrice2 = q.getQuotePrice().add(totalPrice2);
                  }
                  totalPriceSupplier = totalPriceSupplier.add(totalPrice2);
              }
          }
      }
      
      return totalPriceSupplier;
    }
    
    @Override
    public BigDecimal ranks(SaleTender saleTender, String projectId, String packageId) {
        BigDecimal aa = getTocalPriceSupplier(saleTender, projectId, packageId);
        return aa;
        
    }
    @Override
    public HashMap<String, List<SaleTender>> jzjf(BigDecimal valid0, String projectId, String packageId, BigDecimal effectiveAverageQuotation, List<SaleTender> supplierList) {
        //高于有效报价的供应商和低于有效报价的供应商的map集合
        HashMap<String, List<SaleTender>> jzjfMap = new HashMap<String, List<SaleTender>>();  
        //低于有效平均报价的供应商
        List<SaleTender> finaList = new ArrayList<SaleTender>();
        //高于有效平均报价的供应商
        List<SaleTender> noPassSuppList = new ArrayList<SaleTender>();
        for (SaleTender saleTender : supplierList) {
            //供应商报价
            BigDecimal v = getTocalPriceSupplier(saleTender, projectId, packageId);
            saleTender.setTotalPrice(v);
            if (effectiveAverageQuotation.compareTo(v) == -1) {
                noPassSuppList.add(saleTender);
            } else {
                finaList.add(saleTender);
            }
        }
        jzjfMap.put("finalSupplier", finaList);
        jzjfMap.put("noPassSuppList", noPassSuppList);
        return jzjfMap;
    }
    @Override
    public void jzjfRank(JSONObject jsonObj, final BigDecimal bidPrice, BigDecimal benchmarkPrice, String packageId,
        String projectId, List<SaleTender> finalSupplier) {
        //找出报价在中标参考价和基准价之间的供应商
        List<SaleTender> supplist0 = new ArrayList<SaleTender>();
        for (SaleTender saleTender : finalSupplier) {
            //供应商报价
            BigDecimal v = saleTender.getTotalPrice();
            //比较中标参考价和基准价的大小
            if (bidPrice.compareTo(benchmarkPrice) <= 0) {
                //如果中标参考价小于或等于基准价，判断供应商报价是否大于等于中标参考价并且低于等于基准价
                if (v.compareTo(bidPrice) >= 0 && v.compareTo(benchmarkPrice) <= 0) {
                    saleTender.setTotalPrice(v);
                    supplist0.add(saleTender);
                }
            }  else {
                //如果中标参考价比基准价高，判断供应商报价是否小于等于中标参考价并且大于等于基准价
                if (v.compareTo(bidPrice) <= 0 && v.compareTo(benchmarkPrice) >= 0) {
                    saleTender.setTotalPrice(v);
                    supplist0.add(saleTender);
                }
            }
        }
        if (supplist0.size() > 0) {
            //如果报价在中标参考价和基准价之间的供应商大于0
            /**
             * 最接近中标参考价的供应商
             */
            //供应商报价与中标参考价差的绝对值按由小到大排序
          Collections.sort(supplist0,new Comparator<SaleTender>(){
            public int compare(SaleTender o1, SaleTender o2) {  
              BigDecimal dValue1 = o1.getTotalPrice().subtract(bidPrice).abs();
              BigDecimal dValue2 = o2.getTotalPrice().subtract(bidPrice).abs();
              if(dValue1.compareTo(dValue2) == 1){  
                  return 1;
              } 
              if(dValue1.compareTo(dValue2) == 0){  
                  return 0;  
              }
              if(dValue1.compareTo(dValue2) == -1){  
                return -1;  
              }  
              return 0; 
            }
          });   
          //最接近中标参考价的供应商报价为第一名
          final BigDecimal nearBidPrice = supplist0.get(0).getTotalPrice();
          BigDecimal economicScore = new BigDecimal(100);
          BigDecimal technologyScore = new BigDecimal(100);
          //排除低于第一名报价的供应商
          List<SaleTender> supplist1 = new ArrayList<SaleTender>();
          for (int i = 0; i < finalSupplier.size(); i++) {
              SaleTender saleTender = finalSupplier.get(i);
              Map<String, Object> map2 = new HashMap<String, Object>();
              map2.put("supplierId", saleTender.getSuppliers().getId());
              map2.put("packageId", packageId);
              map2.put("economicScore", economicScore);
              map2.put("technologyScore", technologyScore);
              //供应商报价
              BigDecimal v = saleTender.getTotalPrice();
              if (v.compareTo(nearBidPrice) == -1) {
                  jsonObj.put("isRank", false);
                  jsonObj.put("rank", "未列入排名");
                  jsonObj.put("supplierPrice", v);
                  map2.put("reviewResult", jsonObj.toString());
                  saleTenderService.editSumScore(map2);
              } else {
                  supplist1.add(saleTender);
              }
          }
          //高于第一名供应商报价供应商按报价按由低到高排序
          Collections.sort(supplist1,new Comparator<SaleTender>(){
            public int compare(SaleTender o1, SaleTender o2) {  
              BigDecimal v1 = o1.getTotalPrice().subtract(nearBidPrice);
              BigDecimal v2 = o2.getTotalPrice().subtract(nearBidPrice);
              if(v1.compareTo(v2) == 1){  
                  return 1;
              } 
              if(v1.compareTo(v2) == 0){  
                  return 0;  
              }
              if(v1.compareTo(v2) == -1){  
                return -1;  
              }  
              return 0; 
            }
          });   
          for (int i = 0; i < supplist1.size(); i++) {
              Map<String, Object> map3 = new HashMap<String, Object>();
              map3.put("supplierId", supplist1.get(i).getSuppliers().getId());
              map3.put("packageId", packageId);
              map3.put("economicScore", economicScore);
              map3.put("technologyScore", technologyScore);
              jsonObj.put("isRank", true);
              jsonObj.put("rank", i+1);
              jsonObj.put("supplierPrice", supplist1.get(i).getTotalPrice());
              map3.put("reviewResult", jsonObj.toString());
              saleTenderService.editSumScore(map3);
              //向SUPPLIER_CHECK_PASS表中插入预中标供应商
              BigDecimal totalSupplier = new BigDecimal(0);
              totalSupplier= totalSupplier.add(economicScore);
              totalSupplier= totalSupplier.add(technologyScore);
              SupplierCheckPass record = new SupplierCheckPass();
              record.setId(WfUtil.createUUID());
              record.setPackageId(packageId);
              record.setProjectId(projectId);
              record.setSupplierId(supplist1.get(i).getSuppliers().getId());
              record.setTotalScore(totalSupplier);
              record.setTotalPrice(supplist1.get(i).getTotalPrice());
              record.setRanking(i+1);
              SupplierCheckPass checkPass = new SupplierCheckPass();
              checkPass.setPackageId(packageId);
              checkPass.setSupplierId(supplist1.get(i).getSuppliers().getId());
              //判断是否有旧数据
              List<SupplierCheckPass> oldList= checkPassMapper.listCheckPass(checkPass);
              if (oldList != null && oldList.size() > 0) {
                for (SupplierCheckPass supplierCheckPass : oldList) {
                  //删除原数据
                  checkPassMapper.deleteByPrimaryKey(supplierCheckPass.getId());
                }
              }
              checkPassMapper.insertSelective(record);
          }
        } else {
              //如果报价在中标参考价和基准价之间的供应商等于0
              //查找小于中标参考价的供应商
              List<SaleTender> supplist2 = new ArrayList<SaleTender>();
              for (SaleTender saleTender : finalSupplier) {
                  BigDecimal v = saleTender.getTotalPrice();
                  if (v.compareTo(bidPrice) == -1) {
                      supplist2.add(saleTender);
                  }
              }
              //供应商报价与中标参考价差的绝对值按由小到大排序
              Collections.sort(supplist2,new Comparator<SaleTender>(){
                public int compare(SaleTender o1, SaleTender o2) {  
                  BigDecimal dValue1 = o1.getTotalPrice().subtract(bidPrice).abs();
                  BigDecimal dValue2 = o2.getTotalPrice().subtract(bidPrice).abs();
                  if(dValue1.compareTo(dValue2) == 1){  
                      return 1;
                  } 
                  if(dValue1.compareTo(dValue2) == 0){  
                      return 0;  
                  }
                  if(dValue1.compareTo(dValue2) == -1){  
                    return -1;  
                  }  
                  return 0; 
                }
              }); 
              //最接近中标参考价的供应商报价为第一名
              final BigDecimal nearBidPrice = supplist2.get(0).getTotalPrice();
              BigDecimal economicScore = new BigDecimal(100);
              BigDecimal technologyScore = new BigDecimal(100);
              //排除低于第一名报价的供应商
              List<SaleTender> supplist3 = new ArrayList<SaleTender>();
              for (int i = 0; i < finalSupplier.size(); i++) {
                  SaleTender saleTender = finalSupplier.get(i);
                  Map<String, Object> map2 = new HashMap<String, Object>();
                  map2.put("supplierId", saleTender.getSuppliers().getId());
                  map2.put("packageId", packageId);
                  map2.put("economicScore", economicScore);
                  map2.put("technologyScore", technologyScore);
                  //供应商报价
                  BigDecimal v = saleTender.getTotalPrice();
                  if (v.compareTo(nearBidPrice) == -1) {
                      jsonObj.put("isRank", false);
                      jsonObj.put("rank", "未列入排名");
                      jsonObj.put("supplierPrice", v);
                      map2.put("reviewResult", jsonObj.toString());
                      saleTenderService.editSumScore(map2);
                  } else {
                      supplist3.add(saleTender);
                  }
              }
              //高于第一名供应商报价供应商按报价按由低到高排序
              Collections.sort(supplist3,new Comparator<SaleTender>(){
                public int compare(SaleTender o1, SaleTender o2) {  
                  BigDecimal v1 = o1.getTotalPrice().subtract(nearBidPrice);
                  BigDecimal v2 = o2.getTotalPrice().subtract(nearBidPrice);
                  if(v1.compareTo(v2) == 1){  
                      return 1;
                  } 
                  if(v1.compareTo(v2) == 0){  
                      return 0;  
                  }
                  if(v1.compareTo(v2) == -1){  
                    return -1;  
                  }  
                  return 0; 
                }
              });   
              for (int i = 0; i < supplist3.size(); i++) {
                  Map<String, Object> map3 = new HashMap<String, Object>();
                  map3.put("supplierId", supplist3.get(i).getSuppliers().getId());
                  map3.put("packageId", packageId);
                  map3.put("economicScore", economicScore);
                  map3.put("technologyScore", technologyScore);
                  jsonObj.put("isRank", true);
                  jsonObj.put("rank", i+1);
                  jsonObj.put("supplierPrice", supplist3.get(i).getTotalPrice());
                  map3.put("reviewResult", jsonObj.toString());
                  System.out.println(supplist3.get(i).getSupplierId()+"--"+supplist3.get(i).getTotalPrice()+"--"+jsonObj.toString());
                  saleTenderService.editSumScore(map3);
                //向SUPPLIER_CHECK_PASS表中插入预中标供应商
                  BigDecimal totalSupplier = new BigDecimal(0);
                  totalSupplier= totalSupplier.add(economicScore);
                  totalSupplier= totalSupplier.add(technologyScore);
                  SupplierCheckPass record = new SupplierCheckPass();
                  record.setId(WfUtil.createUUID());
                  record.setPackageId(packageId);
                  record.setProjectId(projectId);
                  record.setSupplierId(supplist3.get(i).getSuppliers().getId());
                  record.setTotalScore(totalSupplier);
                  record.setTotalPrice(supplist3.get(i).getTotalPrice());
                  record.setRanking(i+1);
                  SupplierCheckPass checkPass = new SupplierCheckPass();
                  checkPass.setPackageId(packageId);
                  checkPass.setSupplierId(supplist3.get(i).getSuppliers().getId());
                  //判断是否有旧数据
                  List<SupplierCheckPass> oldList= checkPassMapper.listCheckPass(checkPass);
                  if (oldList != null && oldList.size() > 0) {
                    for (SupplierCheckPass supplierCheckPass : oldList) {
                      //删除原数据
                      checkPassMapper.deleteByPrimaryKey(supplierCheckPass.getId());
                    }
                  }
                  checkPassMapper.insertSelective(record);
              }
        }
    }
    @Override
    public BigDecimal getPriceSupplier(SaleTender st, String packageId, String projectId) {
      return getTocalPriceSupplier(st, projectId, packageId);
    }
    @Override
    public BigDecimal getTotalScore(String packageId, List<SaleTender> supplierList) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("packageId", packageId);
      return getTotalScore(supplierList, map);
    }
    @Override
    public BigDecimal getTotalScoreRejectByPrice(String packageId, List<SaleTender> supplierList) {
        //包内供应商去除价格分后的总得分
        BigDecimal totalScore = new BigDecimal(0);
        MarkTerm markTerm = new MarkTerm();
        markTerm.setPackageId(packageId);
        markTerm.setChecked("1");
        //查询包下价格评审项
        List<MarkTerm> markTerms = markTermMapper.findListByMarkTerm(markTerm);
        //获取该包的经济评审专家
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("packageId", packageId);
        map1.put("reviewTypeId", DictionaryDataUtil.getId("ECONOMY"));
        List<PackageExpert>  economyExperts = packageExpertMapper.selectList(map1);
        //获取该包的技术评审专家
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("packageId", packageId);
        map2.put("reviewTypeId", DictionaryDataUtil.getId("TECHNOLOGY"));
        List<PackageExpert> technologyExperts = packageExpertMapper.selectList(map2);
        //经济总得分
        BigDecimal economicScore = new BigDecimal(0);
        //技术总得分
        BigDecimal technologyScore = new BigDecimal(0);
        for (SaleTender saleTender : supplierList) {
            if (economyExperts != null && economyExperts.size() > 0) {
              Map<String, Object> map3 = new HashMap<String, Object>();
              map3.put("expertId", economyExperts.get(0).getExpert().getId());
              map3.put("packageId", packageId);
              map3.put("supplierId", saleTender.getSuppliers().getId());
              if (markTerms != null && markTerms.size() > 0) {
                map3.put("isReject", 1);
                map3.put("scoreModelId", markTerms.get(0).getSmId());
              }
              BigDecimal supplierEconomicScore = expertScoremapper.selectSumByMap(map3);
              economicScore = economicScore.add(supplierEconomicScore);
            }
            if (technologyExperts != null && technologyExperts.size() > 0) {
              Map<String, Object> map4 = new HashMap<String, Object>();
              map4.put("expertId", technologyExperts.get(0).getExpert().getId());
              map4.put("packageId", packageId);
              map4.put("supplierId", saleTender.getSuppliers().getId());
              if (markTerms != null && markTerms.size() > 0) {
                map4.put("isReject", 1);
                map4.put("scoreModelId", markTerms.get(0).getSmId());
              }
              BigDecimal supplierTechnologyScore = expertScoremapper.selectSumByMap(map4);
              technologyScore = technologyScore.add(supplierTechnologyScore);
            }
        }
        totalScore = totalScore.add(economicScore);
        totalScore = totalScore.add(technologyScore);
        return totalScore;
    }
    @Override
    public HashMap<String, Object> rejectByPrice(String packageId, String projectId,
        List<SaleTender> supplierList) {
        List<SaleTender> passList = new ArrayList<SaleTender>();
        List<SaleTender> noPassList = new ArrayList<SaleTender>();
        //该包供应商总报价
        BigDecimal totalPrice = getTotalPrice(packageId, projectId, supplierList);
        //供应商报价不得超过有效供应商报价平均值百分比
        BigDecimal totalPricePercent = new BigDecimal(0);
        BidMethod condition = new BidMethod();
        condition.setProjectId(projectId);
        condition.setPackageId(packageId);
        List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
        BigDecimal valid = new BigDecimal(0);
        if (bmList != null && bmList.size() > 0) {
            valid = bmList.get(0).getValid();
            totalPricePercent = valid.divide(new BigDecimal(100));
        }
        //供应商数量
        int suppNum = supplierList.size();
        BigDecimal supplierNum = new BigDecimal(suppNum);
        //供应商平均报价
        BigDecimal supplierAver = totalPrice.divide(supplierNum, 4, RoundingMode.HALF_UP);
        //供应商有效平均报价
        BigDecimal effectiveAverageQuotation = supplierAver.add(supplierAver.multiply(totalPricePercent));
        for (SaleTender saleTender : supplierList) {
            //供应商报价
            BigDecimal v = getTocalPriceSupplier(saleTender, projectId, packageId);
            saleTender.setTotalPrice(v);
            if (v.compareTo(effectiveAverageQuotation) == 1) {
                noPassList.add(saleTender);
            } else {
                passList.add(saleTender);
            }
        }
        HashMap<String, Object> rejectByPriceMap = new HashMap<String, Object>();
        rejectByPriceMap.put("passSupplier", passList);
        rejectByPriceMap.put("noPassSupplier", noPassList);
        rejectByPriceMap.put("effectiveAverageQuotation", effectiveAverageQuotation);
        rejectByPriceMap.put("valid", valid);
        return rejectByPriceMap;
    }
    @Override
    public List<SaleTender> rejectByScore(String packageId, String projectId,
        HashMap<String, Object> rejectByPriceMap) {
        //最终剩余供应商
        List<SaleTender> finals = new ArrayList<SaleTender>();
      
        //根据报价被剔除的供应商
        List<SaleTender> noPassSupplier = (List<SaleTender>) rejectByPriceMap.get("noPassSupplier");
        
        //根据报价剔除后的供应商
        List<SaleTender> finalSupplier = (List<SaleTender>) rejectByPriceMap.get("passSupplier");
        
        //供应商有效平均报价
        BigDecimal effectiveAverageQuotation = (BigDecimal) rejectByPriceMap.get("effectiveAverageQuotation");
        //不得高于有效平均报价的百分比
        BigDecimal valid = (BigDecimal) rejectByPriceMap.get("valid");
        //该包供应商总分数
        BigDecimal totalScore = getTotalScore(packageId, finalSupplier);
        //该包供应商去除价格得分总分数
        BigDecimal totalScoreRejectByPrice = getTotalScoreRejectByPrice(packageId, finalSupplier);
        //经济技术评分不得低于经济技术评分百分比
        BigDecimal totalScorePercent = new BigDecimal(0);
        BidMethod condition = new BidMethod();
        condition.setProjectId(projectId);
        condition.setPackageId(packageId);
        List<BidMethod> bmList = bidMethodMapper.findScoreMethod(condition);
        BigDecimal bussiness = new BigDecimal(0);
        if (bmList != null && bmList.size() > 0) {
            bussiness = bmList.get(0).getBusiness();
            totalScorePercent = bussiness.divide(new BigDecimal(100));
        }
        //供应商数量
        int suppNum = finalSupplier.size();
        BigDecimal supplierNum = new BigDecimal(suppNum);
        //供应商平均得分
        BigDecimal supplierAver = totalScoreRejectByPrice.divide(supplierNum, 4, RoundingMode.HALF_UP);
        //供应商有效平均的得分
        BigDecimal effectiveAverageScore = supplierAver.subtract(supplierAver.multiply(totalScorePercent));
        for (SaleTender saleTender : finalSupplier) {
            //经济得分
            BigDecimal economicScore = getSupplierScore(0, "ECONOMY", saleTender, packageId, projectId);
            //技术得分
            BigDecimal technologyScore = getSupplierScore(0, "TECHNOLOGY", saleTender, packageId, projectId);
            saleTender.setEconomicScore(economicScore);
            saleTender.setTechnologyScore(technologyScore);
            //去除价格得分的经济得分
            BigDecimal economicScoreRejectPrice = getSupplierScore(1, "ECONOMY", saleTender, packageId, projectId);
            //去除价格得分的技术得分
            BigDecimal technologyScoreRejectPrice = getSupplierScore(1, "TECHNOLOGY", saleTender, packageId, projectId);
            //去除价格得分后的总分
            BigDecimal supplierScoreRejectPrice = economicScoreRejectPrice.add(technologyScoreRejectPrice);
            if (supplierScoreRejectPrice.compareTo(effectiveAverageScore) == -1) {
                String msg = "";
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("supplierId", saleTender.getSuppliers().getId());
                map1.put("packageId", packageId);
                map1.put("economicScore", economicScore);
                map1.put("technologyScore", technologyScore);
                msg += effectiveAverageQuotation + "_" + effectiveAverageScore + "_";
                msg += "经济技术平均得分低于有效经济技术平均得分的"+bussiness+"%";
                map1.put("reviewResult", msg);
                saleTenderService.editSumScore(map1);
            } else {
                String reviewResult = effectiveAverageQuotation + "_" + effectiveAverageScore + "_";
                saleTender.setReviewResult(reviewResult);
                finals.add(saleTender);
            }
        }
        for (SaleTender saleTender : noPassSupplier) {
            String msg = "";
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("supplierId", saleTender.getSuppliers().getId());
            map1.put("packageId", packageId);
            BigDecimal economicScore = getSupplierScore(0, "ECONOMY", saleTender, packageId, projectId);
            BigDecimal technologyScore = getSupplierScore(0, "TECHNOLOGY", saleTender, packageId, projectId);
            map1.put("economicScore", economicScore);
            map1.put("technologyScore", technologyScore);
            msg += effectiveAverageQuotation + "_" + effectiveAverageScore + "_";
            msg += "报价高于有效平均报价的"+valid+"%";
            map1.put("reviewResult", msg);
            saleTenderService.editSumScore(map1);
        }
        return finals;
    }
    private BigDecimal getSupplierScore(int i, String str, SaleTender saleTender, String packageId,
        String projectId) {
        BigDecimal score = new BigDecimal(0);
        List<MarkTerm> markTerms = new ArrayList<MarkTerm>();
        if (i == 1) {
            MarkTerm markTerm = new MarkTerm();
            markTerm.setPackageId(packageId);
            markTerm.setChecked("1");
            //查询包下价格评审项
            markTerms = markTermMapper.findListByMarkTerm(markTerm);
        }
        //获取该包的经济评审专家
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("packageId", packageId);
        map1.put("reviewTypeId", DictionaryDataUtil.getId("ECONOMY"));
        List<PackageExpert>  economyExperts = packageExpertMapper.selectList(map1);
        //获取该包的技术评审专家
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("packageId", packageId);
        map2.put("reviewTypeId", DictionaryDataUtil.getId("TECHNOLOGY"));
        List<PackageExpert> technologyExperts = packageExpertMapper.selectList(map2);
        if (economyExperts != null && economyExperts.size() > 0 && "ECONOMY".equals(str)) {
            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("expertId", economyExperts.get(0).getExpert().getId());
            map3.put("packageId", packageId);
            map3.put("projectId", projectId);
            map3.put("supplierId", saleTender.getSuppliers().getId());
            if (i == 1 && markTerms != null && markTerms.size() > 0) {
                map3.put("isReject", 1);
                map3.put("scoreModelId", markTerms.get(0).getSmId());
            }
            //获取供应商经济得分
            score = expertScoremapper.selectSumByMap(map3);
        }
        if (technologyExperts != null && technologyExperts.size() > 0 && "TECHNOLOGY".equals(str)) {
            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("expertId", technologyExperts.get(0).getExpert().getId());
            map3.put("packageId", packageId);
            map3.put("projectId", projectId);
            map3.put("supplierId", saleTender.getSuppliers().getId());
            if (i == 1 && markTerms != null && markTerms.size() > 0) {
              map3.put("isReject", 1);
              map3.put("scoreModelId", markTerms.get(0).getSmId());
          }
            //获取供应商技术得分
            score = expertScoremapper.selectSumByMap(map3);
        }
        return score;
    }
 
}
