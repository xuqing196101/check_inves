package ses.service.ems.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import bss.util.EncryptUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.EngCategoryMapper;
import ses.dao.bms.RoleMapper;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.*;
import ses.dao.sms.DeleteLogMapper;
import ses.model.bms.*;
import ses.model.ems.*;
import ses.model.sms.DeleteLog;
import ses.model.sms.Supplier;
import ses.service.bms.RoleServiceI;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertAttachmentService;
import ses.service.ems.ExpertService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;
import ses.util.WfUtil;
import bss.dao.ppms.ProjectMapper;
import bss.dao.prms.PackageExpertMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ext.ProjectExt;
import bss.model.prms.PackageExpert;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import common.dao.FileUploadMapper;


@Service("expertService")
public class ExpertServiceImpl implements ExpertService {

    private static Logger log = LoggerFactory.getLogger(ExpertServiceImpl.class);

	@Autowired
	private ExpertMapper mapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ExpertAttachmentMapper attachmentMapper;
	@Autowired
	private ExpertAuditMapper expertAuditMapper;
	@Autowired
	private ExpertCategoryMapper expertCategoryMapper;
	@Autowired
	private TodosMapper todosMapper;
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private EngCategoryMapper engCategoryMapper;
	@Autowired
    private RoleServiceI roleService;
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private PackageExpertMapper packageExpertMapper;
	@Autowired
	private AreaMapper areaMapper;
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    @Autowired
    private ProjectExtractMapper projectExtractMapper;
    @Autowired
    private RoleMapper roleMapper;
    
    //专家黑名单
    @Autowired
    private ExpertBlackListMapper expertBlackListMapper;
    
    
   @Autowired
   private FileUploadMapper fileUploadMapper;
   
   @Autowired
   private ExpertTitleMapper expertTitleMapper;
    
   @Autowired
   private DeleteLogMapper deleteLogMapper;
   
	@Override
	public void deleteByPrimaryKey(String id) {
		mapper.deleteByPrimaryKey(id);

	}

	@Override
	public int insertSelective(Expert record) {
		//诚信分数初始化
		record.setHonestyScore(0);
		record.setUpdatedAt(new Date());
		return mapper.insertSelective(record);
	}

	@Override
	public Expert selectByPrimaryKey(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
    public List<Expert> getAllExpert() {
        return mapper.getAllExpert();
    }
	
	@Override
	public void updateByPrimaryKeySelective(Expert record) {
		mapper.updateByPrimaryKeySelective(record);
	}

	@Override
    public int daysBetween(Date date) throws ParseException {
	    // 获取当前时间
        Date nowDate = new Date();
        // SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        date = sdf.parse(sdf.format(date));  
        nowDate = sdf.parse(sdf.format(nowDate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(date);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(nowDate);    
        long time2 = cal.getTimeInMillis();         
        // 算出两个时间差,单位毫秒所以除以(1000*3600*24)
        long betweenDays = (time2 - time1)/(1000*3600*24);  
        // 精确小数
        return Integer.parseInt(String.valueOf(betweenDays)); 
    }
/*@Override
	public List<Expert> selectLoginNameList(String loginName) {
		List<Expert> expertList = mapper.selectLoginNameList(loginName);
		return expertList;
	}*/
/**
 * 查询所有专家 带分页 可条件查询
 */
	@Override
	public List<Expert> selectAllExpert(Integer pageNum,Expert expert) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		/*Map<String, Object> map = new HashMap<String, Object>();
		if(expert!=null){
		    map.put("relName", expert.getRelName());
		    map.put("expertsFrom", expert.getExpertsFrom());
		    map.put("status", expert.getStatus());
		    map.put("expertsTypeId", expert.getExpertsTypeId());
		    map.put("isPublish", expert.getIsPublish());
		}else{
			map.put("relName", null);
			map.put("expertsFrom", null);
			map.put("status", null);
			map.put("expertsTypeId", null);
		
		}*/
		return mapper.selectAllExpert(expert);
	}
	/**
	 * 查询所有待复审,通过和未通过的专家 带分页 可条件查询
	 */
	@Override
    public List<Expert> selectSecondAuditExpert(Integer pageNum,Expert expert) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
        Map<String, Object> map = new HashMap<String, Object>();
        if(expert!=null){
            map.put("relName", expert.getRelName());
            map.put("expertsFrom", expert.getExpertsFrom());
            map.put("expertsTypeId", expert.getExpertsTypeId());
        }else{
            map.put("relName", null);
            map.put("expertsFrom", null);
            map.put("expertsTypeId", null);
        }
        return mapper.selectSecondAuditExpert(map);
    }
	  /***
     * 
      * @Title: getCount
      * @author ShaoYangYang
      * @date 2016年9月12日 下午4:00:10  
      * @Description: TODO 查询审核专家数量
      * @param @param expert
      * @param @return      
      * @return Integer
     */
	@Override
	public Integer getCount(Expert expert) {
		
		return mapper.getCount(expert);
	}
	 /**
     * 
      * @Title: getUserById
      * @author ShaoYangYang
      * @date 2016年9月13日 下午6:13:59  
      * @Description: TODO 根据用户id查询用户
      * @param @param userId
      * @param @return      
      * @return User
     */
	@Override
    public User getUserById(String userId){
		User u = new User();
		u.setId(userId);
		List<User> list = userMapper.selectUser(u);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
    }
	/**
     * 
      * @Title: uploadFile
      * @author ShaoYangYang
      * @date 2016年9月22日 下午1:53:44  
      * @Description: TODO 文件上传
      * @param @param files
      * @param @param realPath      
      * @return void
     */
	@Override
    public void uploadFile(MultipartFile[] files, String realPath,String expertId){
		try {
			ExpertAttachment attachment;
			if(files!=null && files.length>0){
				 for(MultipartFile myfile : files){  
			            if(myfile.isEmpty()){  
			            }else{  
			                //String filename = myfile.getOriginalFilename();
			               // String uuid = WfUtil.createUUID();
			                //文件名处理
			               // filename=uuid+filename;
			                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload_file\\文件夹中  
			                //String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");  
			                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
			                //FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));
			                /*Ftp文件上传*/
			                attachment = new ExpertAttachment();
			                attachment.setId(WfUtil.createUUID());
			                attachmentMapper.insert(attachment);
			            }  
			        }
				
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	 /**
     * 
      * @Title: downloadFile
      * @author ShaoYangYang
      * @date 2016年9月22日 下午2:07:23  
      * @Description: TODO 文件下载
      * @param @param fileName 文件名
      * @param @param filePath 文件地址
      * @param @param downFileName 下载文件名
      * @param @return      
      * @return ResponseEntity<byte[]>
     */
	@Override
    public ResponseEntity<byte[]> downloadFile(String fileName,String filePath,String downFileName){
    	 try {
			File file=new File(filePath+"/"+fileName);  
			    HttpHeaders headers = new HttpHeaders(); 
			    headers.setContentDispositionFormData("attachment", downFileName);   
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK); 
			    file.delete();
			    return entity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
	 * 
	  * @Title: editBasicInfo
	  * @author ShaoYangYang
	  * @date 2016年9月27日 下午1:51:24  
	  * @Description: TODO 修改个人信息
	  * @param @param expert
	  * @param @param user      
	  * @return void
	 */
    @Override
	public void editBasicInfo(Expert expert,User user){
    	//判断用户的类型为专家类型
        HashMap<String, Object> expertMap = new HashMap<String, Object>();
        expertMap.put("userId", user.getId());
        expertMap.put("code", "EXPERT_R");
        List<Role> ers = roleService.selectByUserIdCode(expertMap);
        //进入专家后台
        if (ers != null && ers.size() > 0) {
    		//Expert expert = service.selectByPrimaryKey(user.getTypeId());
    		if(user.getTypeId()!=null && StringUtils.isNotEmpty(user.getTypeId())){
    			//id不为空为修改个人信息
    			Expert expert2 = mapper.selectByPrimaryKey(user.getTypeId());
    			expert2.setTelephone(expert.getTelephone());
    			expert2.setUnitAddress(expert.getUnitAddress());
    			expert2.setMobile(expert.getMobile());
    			mapper.updateByPrimaryKeySelective(expert2);
    		}else{
    			Expert expert3 = new Expert();
    			//否则为新增个人信息
    			expert3.setId(WfUtil.createUUID());
    			expert3.setTelephone(expert.getTelephone());
    			expert3.setUnitAddress(expert.getUnitAddress());
    			expert3.setMobile(expert.getMobile());
    			user.setTypeId(expert3.getId());
    			//新增个人信息
    			mapper.insertSelective(expert3);
    			//更新登录用户信息
    			userMapper.updateByPrimaryKeySelective(user);
    		}
    	}
	}
    /**
	 * 
	  * @Title: loginRedirect
	  * @author ShaoYangYang
	  * @date 2016年9月30日 下午2:47:43  
	  * @Description: TODO 登录判断跳转
	  * @param @param user
	  * @param @throws Exception      
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> loginRedirect(User user) throws Exception {
		String typeId = user.getTypeId();
		HashMap<String, Object> expertMap = new HashMap<String, Object>();
        expertMap.put("userId", user.getId());
        expertMap.put("code", "EXPERT_R");
        List<Role> ers = roleService.selectByUserIdCode(expertMap);
        HashMap<String, Object> map = new HashMap<String, Object>();
        //进入专家后台
        if (ers != null && ers.size() > 0) {
			//查出当前登录的用户个人信息
			Expert expert = mapper.selectByPrimaryKey(typeId);
			if(expert!=null){
				if(expert.getIsSubmit().equals("0") && !expert.getIsBlack().equals("1") && !expert.getStatus().equals("3")){
					//未提交
					map.put("expert", "4");
				} else if(expert.getStatus().equals("2") || expert.getStatus().equals("16")){
					//审核未通过
					map.put("expert", "5");
				} else if(expert.getStatus().equals("4") || expert.getStatus().equals("15")){
					//初审已通过，待复审
					map.put("expert", "8");
				} else if(expert.getIsBlack().equals("1") || expert.getStatus().equals("12")){
	                    //已拉黑
	                    map.put("expert", "expertBlack");
	            }else if((expert.getStatus().equals("0") || expert.getStatus().equals("9")) && expert.getIsSubmit().equals("1") || expert.getStatus().equals("10")){
					//未审核
					map.put("expert", "3");
				}else if(expert.getStatus().equals("3") && !expert.getIsBlack().equals("1")){
				    // 退回修改
				    map.put("expert", "2");
				}else if(expert.getStatus().equals("6")){
                    // 复查合格
                    map.put("expert", "7");
                }else if(expert.getStatus().equals("7") && 1 == expert.getIsProvisional()){
                    // 临时专家,并且参加的评审项目已结束
                    map.put("expert", "7");
                }else if (("1").equals(expert.getStatus())){
					// 待复审状态
					map.put("expert", "1");
				}else if (("5").equals(expert.getStatus())){
					// 复审未通过状态
					map.put("expert", "5");
				}else if (("-2").equals(expert.getStatus())){
                	// 审核预通过状态
					map.put("expert", "-2");
				}else if (("-3").equals(expert.getStatus())){
					// 公示中状态
					map.put("expert", "-3");
				} else if(expertBlackListMapper.countByExpertId(expert.getId()) > 0){
					// 黑名单处罚中
					map.put("expert", "expertBlack");
				}else if(("8").equals(expert.getStatus())){
					//复查不合格
					map.put("expert", "reviewFailed");
				}else if(("11").equals(expert.getStatus()) || ("14").equals(expert.getStatus())){
					//复审中的状态
					map.put("expert", "inReview");
				}
			}else{
				//如果专家信息为空 证明还没有填写过个人信息
				map.put("expert", "4");
			}
		}else{
			//如果用户关联的专家id为空 证明还没有填写过个人信息
			map.put("expert", "4");
		}
		return map;
	}
    
	/**
	 * 
	  * @Title: zanCunInsert
	  * @author ShaoYangYang
	  * @date 2016年9月30日 下午5:11:37  
	  * @Description: TODO 暂存方法提取
	  * @param @param expert
	  * @param @throws Exception      
	  * @return void
	 */
	@Override
	public void zanCunInsert(Expert expert ,String expertId,String categoryIds) throws Exception{
		if(StringUtils.isEmpty(expert.getId())){//id为空就新增 否则就修改
			expert.setId(expertId);
			//已提交
			expert.setIsSubmit("0");
			//未考试
			expert.setIsDo("0");
			//未审核
			expert.setStatus("0");
			//修改时间
			expert.setUpdatedAt(new Date());
			mapper.insertSelective(expert);
			//附件上传
			//uploadFile(files, realPath, expertId);
			//保存品目
			saveCategory(expert,categoryIds);
		}else{
			//已提交
			//expert.setIsSubmit("0");
			//未考试
			expert.setIsDo("0");
			//未审核
			//expert.setStatus("0");
			//修改时间
			expert.setUpdatedAt(new Date());
			User user = userMapper.findUserByTypeId(expert.getId());
			user.setMobile(expert.getMobile());
			userMapper.updateByPrimaryKeySelective(user);
			try {
				mapper.updateByPrimaryKey(expert);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//保存品目
			saveCategory(expert,categoryIds);
		}
	}
	
	/**
	 * 查看该用户选中的产品类别
	 */
	@Override
	public List<Expert> querySelect(String typeId) {
		return mapper.querySelect(typeId);
	}

    /**
     * 根据数据保存临时专家,用户,专家/包关联关系,用户/角色关联关系
     * @param expertList
     * @param userList
     * @param packageId
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> saveBatchExpert(List<Expert> expertList, List<User> userList, String packageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess = false;
        int message = 0;
        //先校验分配账号名称是否存在
        List<String> userNameList = new ArrayList<String>();
        int num = 0,ajaxNum = 0,ajaxMobile = 0,emptyNum = 0;
        String idNumber = null,moblie=null,loginName=null;
        if(null != userList && !userList.isEmpty()){
            for(User user:userList){
                userNameList.add(user.getLoginName());
                if(StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getPassword())){//如果存在没有填写账号名称或者密码,则返回
                    emptyNum = emptyNum + 1;
                    break;
                }
                List<User> users = userMapper.ajaxIdNumber(user);
                if(null != users && !users.isEmpty()){
                    ajaxNum = ajaxNum + 1;
                    idNumber = user.getRelName();
                }
                List<User> userAjaxMoblie = userMapper.ajaxMoblie(user);
                if(null != userAjaxMoblie && !userAjaxMoblie.isEmpty()){
                    ajaxMobile = ajaxMobile + 1;
                    moblie = user.getRelName();
                }
                List<User> loginNames = userMapper.queryByLoginName(user.getLoginName());
                if(loginNames != null && loginNames.size() > 0){
                    loginName = user.getRelName();
                }
            }
            //如果存在的化,num>0,反之不存在
            num = userMapper.ajaxUserName(userNameList);
        }
        if(num != 0){
            map.put("isSuccess", true);
            map.put("messageCode", 12);
            map.put("loginName", loginName);
            return map;//账号存在
        }
        if(emptyNum != 0){
            map.put("isSuccess", true);
            map.put("messageCode", 13);
            return map;//未填写名称和密码
        }
        if(ajaxNum != 0){
            map.put("isSuccess", true);
            map.put("messageCode", 14);
            map.put("loginName", idNumber);
            return map;//居民身份证已存在
        }
        if(ajaxMobile != 0){
            map.put("isSuccess", true);
            map.put("messageCode", 15);
            map.put("loginName", moblie);
            return map;//联系电话已存在
        }
        try{
            List<DictionaryData> ddList = expExtractRecordService.ddList();//专家类型
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code","EXPERT_R");
            List<Role> roleList = roleService.selectByUserIdCode(paramMap);//角色:专家
            //分别保存专家表,用户表,包/专家关联表,用户/角色关联表
            if(null != expertList && !expertList.isEmpty()){
                for(int i=0; i<expertList.size();i++){
                    //重新封装Expert
                    Expert expert = expertList.get(i);
                    String expertTypeName = expert.getExpertsTypeId();
                    String expertTypeId = "";
                    //根据所选类型获取id
                    if(null!=ddList && !ddList.isEmpty()){
                        for(int j=0;j<ddList.size();j++){
                            if(!StringUtils.isEmpty(expertTypeName) && expertTypeName.equals(ddList.get(j).getName())){
                                expertTypeId = ddList.get(j).getId();
                                break;
                            }
                        }
                    }
                    expert.setExpertsTypeId(expertTypeId);
                    //保存专家,并且返回主键ID
                    mapper.insertBackId(expert);
                    String expertId = expert.getId();
                    //重新封装User
                    User user = userList.get(i);
                    user.setTypeId(expertId);
                    String randomCode = EncryptUtil.generateString(15);
                    String password = EncryptUtil.md5Encrypt(user.getPassword(),randomCode);
                    user.setPassword(password);
                    user.setRandomCode(randomCode);
                    //保存用户,并且返回主键ID
                    userMapper.insertSelective(user);
                    //用户和角色绑定
                    Userrole userrole = new Userrole();
                    userrole.setRoleId(roleList.get(0));
                    userrole.setUserId(user);
                    userMapper.saveRelativity(userrole);
                    //对包进行拆解
                    if(!StringUtils.isEmpty(packageId)){
                        String[] packageIds = packageId.split(",");
                        for(int k=0;k<packageIds.length;k++){
                            if(!StringUtils.isEmpty(packageIds[k])){
                                ProjectExtract projectExtract = new ProjectExtract();
                                projectExtract.setProjectId(packageIds[k]);
                                projectExtract.setExpertId(expertId);
                                projectExtract.setReviewType(expertTypeId);
                                projectExtract.setIsProvisional((short)1);
                                projectExtract.setCreatedAt(new Date());
                                projectExtract.setUpdatedAt(new Date());
                                projectExtract.setOperatingType((short)1);
                                projectExtract.setIsDeleted((short)0);
                                //插入包/专家关联表
                                projectExtractMapper.insertSelective(projectExtract);
                            }
                        }
                    }
                }
            }
            isSuccess = true;
            message = 20;
        }catch (Exception e){
            log.error("临时专家数据处理异常, expertService.saveBatchExpert,error= "+e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            isSuccess = false;
            message = 1001;
        }

        map.put("isSuccess", isSuccess);
        map.put("messageCode", message);
        return map;
    }
    /**
     * 保存引用临时专家关联数据
     * @param packageId
     * @param expertIds
     * @return
     */
    @Override
    public Map<String, Object> saveCiteTemporaryExpert(String packageId, String expertIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isSuccess = false;
        int message = 0;
        List<String> strList = null;
        if(!StringUtils.isEmpty(expertIds)){
            String[] ids = expertIds.split(",");
            strList = new ArrayList<String>();
            for(String str :ids){
                strList.add(str);
            }
        }
        try{
            //数据集合
            if(null != strList && !strList.isEmpty()){
                List<ProjectExtract> projectExtractList = new ArrayList<>();
                Expert expert = new Expert();
                expert.setIds(strList);
                List<Expert> expertAll = mapper.findExpertByInList(expert);
                if(null != expertAll && !expertAll.isEmpty()){
                    for(Expert expertTo:expertAll){
                        projectExtractList.add(packProjectExtract(packageId, expertTo));
                    }
                }
                //批量添加
                if(null != projectExtractList && !projectExtractList.isEmpty()){
                    projectExtractMapper.insertBatch(projectExtractList);
                }
                isSuccess = true;
                message = 20;
            }else{
                isSuccess = false;
                message = 10;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("保存引用临时专家数据异常, expertService.saveCiteTemporaryExpert,exception= "+e);
            isSuccess = false;
            message = 1001;
        }
        map.put("isSuccess", isSuccess);
        map.put("messageCode", message);
        return map;
    }
    /**
     * 根据条件分页查询临时专家
     * @return
     */
    @Override
    public List<Expert> findCiteExpertByCondition(Expert expert, String packageId, Integer page) {
        List<Expert> experts = null;
        try{
            //根据packageId查询出所有已之关联的expertId
            ProjectExtract projectExtract = new ProjectExtract();
            projectExtract.setProjectId(packageId);
            List<ProjectExtract> projectExtracts = projectExtractMapper.findProjectExtractByCondition(projectExtract);
            List<String> existsExperts = null;
            if(null != projectExtracts && !projectExtracts.isEmpty()){
                existsExperts = new ArrayList<>();
                for(int i=0;i<projectExtracts.size();i++){
                    existsExperts.add(projectExtracts.get(i).getExpert().getId());
                }
            }
            if(page!=null){
                PropertiesUtil config = new PropertiesUtil("config.properties");
                PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
            }
            experts = mapper.findExpertByCondition(expert, existsExperts);

        }catch (Exception e){
            e.printStackTrace();
            log.error("临时专家数据获取失败, expertService.findExpertByCondition,exception= "+e);
        }
        return experts;
    }

    /**
     * 封装POJO
     * @param packageId
     * @param expert
     * @return
     */
    public ProjectExtract packProjectExtract(String packageId, Expert expert){
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(packageId);
        projectExtract.setExpertId(expert.getId());
        projectExtract.setReviewType(expert.getExpertsTypeId());
        projectExtract.setIsProvisional((short)1);
        projectExtract.setCreatedAt(new Date());
        projectExtract.setUpdatedAt(new Date());
        projectExtract.setOperatingType((short)1);
        projectExtract.setIsDeleted((short)0);
        return projectExtract;
    }

    /**
	 * 
	  * @Title: saveOrUpdate
	  * @author ShaoYangYang
	  * @date 2016年9月30日 下午5:46:47  
	  * @Description: TODO controller调用 逻辑 抽取
	  * @param @param expert
	  * @param @param expertId
	  * @param @param files
	  * @param @param realPath
	  * @param @throws Exception      
	  * @return void
	 */
	@Override
	public Map<String, Object> saveOrUpdate(Expert expertTemp,String expertId,String categoryIds, String gitFlag, String userId) throws Exception{
		Map<String,Object> map;
		Expert expert = mapper.selectByPrimaryKey(expertId);
		//如果id不为空 则为专家 暂存  或专家退回重新修改提交
		if(StringUtils.isNotEmpty(expert.getId())){
			expert.setIsDo("0");
			//已提交
			expert.setIsSubmit("1");
			//未审核
			if(!"3".equals(expert.getStatus())){
			    expert.setStatus("0");
			}
			//修改时间
			expert.setUpdatedAt(new Date());
			//执行校验并修改
			 map = Validate(expert,2,gitFlag);
			//mapper.updateByPrimaryKeySelective(expert);
			/*获取之前的审核信息
			List<ExpertAudit> auditList = expertAuditMapper.selectByExpertId(expert.getId());
			if(auditList!=null && auditList.size()>0){
				for (ExpertAudit expertAudit : auditList) {
					//修改之前的审核信息为删除 和历史状态
					expertAudit.setIsDelete((long) 1);
					expertAudit.setIsHistory("1");
					expertAuditMapper.updateByPrimaryKeySelective(expertAudit);
				}
			}*/
		}else{
    		expert.setId(expertId);
    		//未考试
    		expert.setIsDo("0");
    		//已提交
    		expert.setIsSubmit("1");
    		//未审核
            if(!"3".equals(expert.getStatus())){
                expert.setStatus("0");
            }
    		//创建时间
    		expert.setCreatedAt(new Date());
    		//修改时间
    		expert.setUpdatedAt(new Date());
    		//执行校验并保存
    		 map = Validate(expert,1,gitFlag);
    		mapper.insertSelective(expert);
    		//文件上传
    		//uploadFile(files, realPath,expertId);
    		//保存品目
    		//saveCategory(expert, categoryIds);
		}
		//发送待办
		Todos todos = new Todos();
		todos.setId(WfUtil.createUUID());
		todos.setIsDeleted((short)0);
		todos.setIsFinish((short)0);
		//待办类型
		todos.setName("【" + mapper.selectByPrimaryKey(expertId).getRelName() + "】-评审专家初审");
		//todos.setReceiverId();
		//接受人id
		todos.setOrgId(expert.getPurchaseDepId());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		todos.setPowerId(config.getString("zjcs"));
		//发送人id
		todos.setSenderId(userId);
		todos.setUndoType((short)2);
		//发送人姓名
		todos.setSenderName(expert.getRelName());
		//审核地址
		todos.setUrl("expertAudit/basicInfo.html?expertId=" + expert.getId());
		todosMapper.insertSelective(todos);
		return map;
	}
	
	/**
	 * 
	  * @Title: userManager
	  * @author ShaoYangYang
	  * @date 2016年9月30日 下午6:04:36  
	  * @Description: TODO 处理用户信息
	  * @param @throws Exception      
	  * @return void
	 */
	@Override
	public void userManager(User user,String userId,Expert expert,String expertId) throws Exception{
	    //个人信息关联用户
		if(userId!=null && userId.length()>0){
			//直接注册完之后填写个人信息
			User u = getUserById(userId);
			if(u==null){
				throw new RuntimeException("该用户不存在！");
			}
			//u.setTypeName(DictionaryDataUtil.get("EXPERT_U").getId());
			String address = expert.getAddress();
			if (address != null) {
			    Area area = areaMapper.selectById(address);
			    // 市
			    String cityName = area.getName();
			    // 省
			    String provinceName = areaMapper.selectById(area.getParentId()).getName();
			    u.setAddress(provinceName.concat(cityName));
			}
			u.setRelName(expert.getRelName());
			u.setTelephone(expert.getTelephone());
			u.setGender(expert.getGender());
			u.setEmail(expert.getEmail());
			u.setIdNumber(expert.getIdCardNumber());
			if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
				u.setTypeId(expertId);
			}else{
				u.setTypeId(expert.getId());
			}
			userMapper.updateByPrimaryKeySelective(u);
		}else{
			//注册完账号  过段时间又填写个人信息
		    String address = expert.getAddress();
            if (address != null) {
                Area area = areaMapper.selectById(address);
                // 市
                String cityName = area.getName();
                // 省
                String provinceName = areaMapper.selectById(area.getParentId()).getName();
                user.setAddress(provinceName.concat(cityName));
            }
			user.setRelName(expert.getRelName());
			user.setTelephone(expert.getTelephone());
			user.setGender(expert.getGender());
			user.setEmail(expert.getEmail());
			user.setIdNumber(expert.getIdCardNumber());
			if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
				user.setTypeId(expertId);
			}else{
				user.setTypeId(expert.getId());
			}
			userMapper.updateByPrimaryKeySelective(user);
		}
	}
	/**
	 * 
	  * @Title: saveCategory
	  * @author ShaoYangYang
	  * @date 2016年9月30日 下午5:54:05  
	  * @Description: TODO 品目代码提取 保存品目
	  * @param @param expert
	  * @param @param ids      
	  * @return void
	 */
	private void saveCategory(Expert expert,String ids) {
		if(ids!=null && StringUtils.isNotEmpty(ids) && ids != null){
			String[] code = ids.split(",");
			ExpertCategory expertCategory = new ExpertCategory();
			expertCategoryMapper.deleteByExpertId(expert.getId());
			//循环品目id集合
			for (String id : code) {
				//根据编码查询id
				//String id = DictionaryDataUtil.getId(string);
				expertCategory.setCategoryId(id);
				expertCategory.setExpertId(expert.getId());
				//逐条保存
				expertCategoryMapper.insert(expertCategory);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see ses.service.ems.ExpertService#listExtractionExpert(ses.model.ems.ExpExtCondition)
	 */
	@Override
	public List<Expert> listExtractionExpert(ExpExtCondition conType) {
		// TODO Auto-generated method stub
		return mapper.listExtractionExpert(conType);
	}

	
	@Override
	public List<Expert> findAllExpert(HashMap<String, Object> map) {
		return mapper.findAllExpert(map);
	}
	
	public Map<String,Object> Validate(Expert expert, int flag, String gitFlag){
		Map<String,Object> map = new HashMap<>();
		if (expert != null) {
			if(!ValidateUtils.isNotNull(expert.getRelName())){
				map.put("realName", "姓名不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getNation())){
				map.put("nation", "民族不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getGender())){
				map.put("gender", "姓别不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getIdType())){
				map.put("idType", "证件类型不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getIdNumber())){
				map.put("idNumber", "证件号码不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getAddress())){
				map.put("address", "地区不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getHightEducation())){
				map.put("hightEducation", "学历不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getGraduateSchool())){
				map.put("graduateSchool", "毕业院校不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getMajor())){
				map.put("major", "专业不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getExpertsFrom())){
				map.put("expertsFrom", "来源不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getUnitAddress())){
				map.put("unitAddress", "单位地址不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getTelephone())){
				map.put("telephone", "联系电话不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getMobile())){
				map.put("mobile", "手机不能为空！");
			}
			if(!ValidateUtils.isNotNull(expert.getHealthState())){
				map.put("healthState", "健康状态不能为空！");
			}
			
			if(!ValidateUtils.Mobile(expert.getMobile())){
				map.put("mobile2", "手机号码不符合规则！");
			}
			String idType = expert.getIdType();
			DictionaryData data = dictionaryDataMapper.selectByPrimaryKey(idType);
			if(data != null){
			    if("ID_CARD".equals(data.getCode())){
			        if(expert.getIdNumber() != null && !ValidateUtils.IDcard(expert.getIdNumber())){
			            map.put("idNumber2", "证件号码无效！");
			         }
			    }
			}
			
			// ========== 验证证件图片是否上传start ==========
			
			String businessId = expert.getId();
			
			// 近期免冠彩色证件照
			List<ExpertAttachment> att1 = getExpertAttachmentList(businessId, ExpertPictureType.HEADPORTRAIT_PROOF.getSign() + "");//50
			// 身份证复印件
			List<ExpertAttachment> att2 = getExpertAttachmentList(businessId, ExpertPictureType.IDENTITY_CARD_PROOF.getSign() + "");//3
			// 军队人员身份证件
			List<ExpertAttachment> att3 = getExpertAttachmentList(businessId, ExpertPictureType.ARMY_PROOF.getSign() + "");//12
			// 专业技术职称证书
			List<ExpertAttachment> att4 = getExpertAttachmentList(businessId, ExpertPictureType.TECHNOLOGY_PROOF.getSign() + "");//4
			
			if(att1 == null){
				map.put("att1", "近期免冠彩色证件照必须上传！");
			}
			if(att2 == null){
				map.put("att2", "身份证复印件必须上传！");
			}
			if(att3 == null){
				map.put("att3", "军队人员身份证件必须上传！");
			}
			if(att4 == null){
				map.put("att4", "专业技术职称证书必须上传！");
			}
			
			// ========== 验证证件图片是否上传end ==========
			
			if(gitFlag != null){
				//修改
				mapper.updateByPrimaryKeySelective(expert);
		    }
			if(map.isEmpty()){
				if(flag==1){
					//新增
					mapper.insert(expert);
				}else if(flag==2){
					//修改
					mapper.updateByPrimaryKeySelective(expert);
				}
				return null;
			}else{
				return map;
			}
		}else{
			return null;
		}
	}

  @Override
  public void saveOrUpdateInfo(Expert expert, String expertId, String categoryIds)
    throws Exception {
    // TODO Auto-generated method stub
    Map<String,Object> map;
    //如果id不为空 则为专家 暂存  或专家退回重新修改提交
    if(StringUtils.isNotEmpty(expert.getId())){
      expert.setIsDo("0");
      //已提交
      expert.setIsSubmit("1");
      //未审核
      expert.setStatus("0");
      //修改时间
      expert.setUpdatedAt(new Date());
      //执行校验并修改
       map = Validate(expert,2,null);
      //mapper.updateByPrimaryKeySelective(expert);
      //获取之前的审核信息
      List<ExpertAudit> auditList = expertAuditMapper.selectByExpertId(expert.getId());
      if(auditList!=null && auditList.size()>0){
        for (ExpertAudit expertAudit : auditList) {
          //修改之前的审核信息为删除 和历史状态
          expertAudit.setIsDeleted(1);
//          expertAudit.setIsHistory("1");
          expertAuditMapper.updateByPrimaryKeySelective(expertAudit);
        }
      }
      if(expert.getExpertsTypeId().equals("1")){
      //保存品目
        saveCategory(expert, categoryIds);
      }else{
        //不是技术专家就删除品目关联信息
          expertCategoryMapper.deleteByExpertId(expert.getId());
      }
    }else{
    expert.setId(expertId);
    //未考试
    expert.setIsDo("0");
    //已提交
    expert.setIsSubmit("1");
    //未审核
    expert.setStatus("0");
    //创建时间
    expert.setCreatedAt(new Date());
    //修改时间
    expert.setUpdatedAt(new Date());
    //执行校验并保存
     map = Validate(expert,1,null);
    mapper.insertSelective(expert);
    //文件上传
    //uploadFile(files, realPath,expertId);
    //保存品目
    saveCategory(expert, categoryIds);
    }
    //发送待办
    Todos todos = new Todos();
    todos.setId(WfUtil.createUUID());
    todos.setCreatedAt(new Date());
    todos.setIsDeleted((short)0);
    todos.setIsFinish((short)0);
    //待办类型
    todos.setName("评审专家注册");
    //todos.setReceiverId();
    //接受人id
    todos.setOrgId(expert.getPurchaseDepId());
    PropertiesUtil config = new PropertiesUtil("config.properties");
    todos.setPowerId(config.getString("zjcs"));
    //发送人id
    todos.setSenderId(expert.getId());
    todos.setUndoType((short)2);
    //发送人姓名
    todos.setSenderName(expert.getRelName());
    //审核地址
    todos.setUrl("expertAudit/basicInfo.html?expertId=" + expert.getId());
    todosMapper.insert(todos );
  }

    @Override
    public List<Expert> validatePhone(String phone) {
      return mapper.validatePhone(phone);
    }

    @Override
    public List<Expert> validateIdCardNumber(String idCardNumber, String expertId) {
      // TODO Auto-generated method stub
      return mapper.validateIdCardNumber(idCardNumber, expertId);
    }
    
    @Override
    public List<Expert> validateIdNumber(String idNumber, String expertId) {
      // TODO Auto-generated method stub
      return mapper.validateIdNumber(idNumber, expertId);
    }
    /**
     *〈简述〉
     * 注册时点击下一步,将表中的STRP_NUMBER进行同步
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @param stepNumber
     */
    @Override
    public void updateStepNumber(String expertId, String stepNumber) {
        // TODO Auto-generated method stub
        mapper.updateStepNumber(expertId, stepNumber);
    }

    /**
     *〈简述〉
     * 专家审核列表
     *〈详细描述〉
     * @author XuQing
     * @param expert
     */
	@Override
	public List<Expert> findExpertAuditList(Expert expert, Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		
		
		return mapper.findExpertAuditList(expert);
	}
	public boolean checkMobile(String mobile,String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("id", id);
		int count = mapper.countByMobile(map);
		return count > 0 ? false : true;
	}
	/**
     *〈简述〉
     * 未退回修改的专家存储历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    @Override
    public void insertExpertHistory(ExpertHistory expert) {
    	expert.setExpertId(expert.getId());
        mapper.insertExpertHistory(expert);
    }
    
    /**
     *〈简述〉
     * 根据id查询专家的历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expertId
     * @return
     */
    @Override
    public ExpertHistory selectOldExpertById(String expertId) {
        // TODO Auto-generated method stub
        return mapper.selectOldExpertById(expertId);
    }

    /**
     *〈简述〉
     * 删除退回修改的专家存储历史信息
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    @Override
    public void deleteExpertHistory(String expertId) {
        // TODO Auto-generated method stub
        mapper.deleteExpertHistory(expertId);
    }
    /**
     *〈简述〉
     * 删除专家帐号
     *〈详细描述〉
     * @author WangHuijie
     * @param expert
     */
    @Override
	public int deleteExpertsAccount(String expertId){
    	mapper.deleteExpertsAccount(expertId);
    	return 0;
    }

    /**
     * @see ses.service.ems.ExpertService#getCommitExpertByDate(java.lang.String)
     */
    @Override

    public List<Expert> getCommitExpertByDate(String startDate,String endDate) {
        return mapper.getCommitExpertByDate(startDate,endDate);
    }

    /**
     * @see ses.service.ems.ExpertService#getModifyExpertByDate(java.lang.String)
     */
    public List<Expert> getModifyExpertByDate(String updateDate) {
        return mapper.getModifyExpertByDate(updateDate);
    }

    @Override
    public List<Category> searchByName(String cateName, String flag, String codeName) {
        if (flag == null) {
        	List<Category> list = categoryMapper.searchByName(cateName, codeName);
        	List<Category> listNot =new  LinkedList<Category>();
        	for(Category cate:list){
        		boolean bool = isPublish(cate.getId());
        		if(bool!=true){
        			listNot.add(cate);
        		}
        	}
        	list.removeAll(listNot);
            return list;
        } else {
            return engCategoryMapper.searchByName(cateName, codeName);
        }
    }

    /**
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see ses.service.ems.ExpertService#getProjectExtList(java.util.List)
     */
    @Override
    public List<ProjectExt> getProjectExtList(List<Packages> packageList, String expertId, String status, Integer pageNum) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        PropertiesUtil config = new PropertiesUtil("config.properties");
        if(pageNum != null){
            PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
        }
        
        List<ProjectExt> projectExtList = new ArrayList<ProjectExt>();
        ProjectExt projectExt;
        for (Packages packages : packageList) {
            Project project = projectMapper.selectProjectByPrimaryKey(packages.getProjectId());
            if (project != null) {
                projectExt = new ProjectExt();
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
            }
        }
        // 状态查询
        // 全部
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
	public void deleteExpert(String expertId) {
//		Expert expert = mapper.selectByPrimaryKey(expertId);
		
		DeleteLog dlog=new DeleteLog();
    	String id = UUID.randomUUID().toString().replaceAll("-", "");
          Expert expert = mapper.selectByPrimaryKey(expertId);
    	dlog.setId(id);
    	dlog.setTypeId(expertId);
    	dlog.setCreateAt(new Date());
    	if(expert.getIdCardNumber() !=null){
    		dlog.setUniqueCode(expert.getIdCardNumber());
    	}
    	deleteLogMapper.insertSelective(dlog);
    	
//		mapper.deleteByPrimaryKey(expertId);
//		
//		User user = userMapper.findUserByTypeId(expertId);
//    	Userrole userRole=new Userrole();
//    	if(user != null){
//    		userRole.setUserId(user);
//        	roleMapper.deleteRoelUser(userRole);
//        	userMapper.deleteByPrimaryKey(user.getId());
//    	}
//    	expertCategoryMapper.deleteByExpertId(expertId);
//    	fileUploadMapper.deleteByBusinessId(expertId);
//    	expertTitleMapper.deleteByExpertId(expertId);
	}

	/**
     * @Title: findLogoutList
     * @author XuQing 
     * @date 2017-4-11 下午4:08:04  
     * @Description:注销列表
     * @param @param expert
     * @param @return      
     * @return List<Expert>
     */
	@Override
	public List<Expert> findLogoutList(Expert expert, Integer page) {
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
	
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		
		return mapper.findLogoutList(expert);
	}

	 /**
     * @Title: updateExtractOrgidById
     * @author XuQing 
     * @date 2017-4-24 下午1:45:35  
     * @Description:抽取的机构id
     * @param @param expert      
     * @return void
     */
	@Override
	public void updateExtractOrgidById(Expert expert) {
		mapper.updateExtractOrgidById(expert);
		
	}

	/**
     * @Title: updateIsDeleteById
     * @author XuQing 
     * @date 2017-5-2 下午5:25:39  
     * @Description:软删除历史信息
     * @param @param expertId      
     * @return void
     */
	@Override
	public void updateIsDeleteById(String expertId) {
		mapper.updateIsDeleteById(expertId);
		
	}

	@Override
	public List<Expert> selectRuKuExpert(Expert expert, Integer page) {
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		return mapper.selectRuKuExpert(expert);
	}

    @Override
    public int logoutExpertByDay(Expert expert) throws Exception {
	    if(null != expert){
            String ex_status = expert.getStatus();
            if(StringUtils.isNotBlank(ex_status)){
                Date createAt = expert.getCreatedAt();
                Date auditAt = expert.getAuditAt();
                //注册后,90天内未提交审核
                if("-1".equals(ex_status)){
                    //根据创建注册信息时间计算间隔天数
                    int betweenDays = this.daysBetween(createAt);
//                    int days = Integer.parseInt(PropUtil.getProperty("logout.expert.first.overdue"));
                    if(betweenDays > 90){
                        this.deleteExpert(expert.getId());
                        return 90;
                    }
                }
                //退回修改后,60天内未重新提交审核
                if("3".equals(ex_status)){
                    //根据审核时间计算间隔天数
                    int betweenDays = this.daysBetween(auditAt);
//                    int days = Integer.parseInt(PropUtil.getProperty("logout.expert.back.overdue"));
                    if(betweenDays > 60){
                        this.deleteExpert(expert.getId());
                        return 60;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public ExpertHistory selectOldExpertByPrimaryKey(String id) {
        return mapper.selectOldExpertByPrimaryKey(id);
    }

    @Override
    public void insertExpertHistoryById(ExpertHistory expertHistory) {
        mapper.insertExpertHistoryById(expertHistory);
    }

    @Override
    public List<Expert> getAuditExpertByDate(String startDate, String endDate) {
        return mapper.getAuditExpertByDate(startDate, endDate,DictionaryDataUtil.getId("LOCAL"));
    }

    /**
     * @Title: updateById
     * @date 2017-5-9 上午9:54:00  
     * @Description:假删除专家（临时）
     * @param @param id      
     * @return void
     */
	@Override
	public void updateById(String id) {
		Expert info = mapper.selectByPrimaryKey(id);
		StringBuffer buff = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
    	String date = format.format(new Date());
		buff.append("_del_bak_");
	    buff.append(date);
		
		Expert expert = new Expert();
		expert.setId(id);
		expert.setRelName(info.getRelName() + buff);
		expert.setMobile(info.getMobile() + buff);
		expert.setIdCardNumber(info.getIdCardNumber() + buff);
		expert.setIdNumber(info.getIdNumber() + buff);
		mapper.updateById(expert);
		
	}
	
	// 获取专家上传的附件
	private List<ExpertAttachment> getExpertAttachmentList(String businessId, String typeId){
		Map<String, Object> attachmentMap = new HashMap<>();
		attachmentMap.put("isDeleted", 0);
		attachmentMap.put("businessId", businessId);
		attachmentMap.put("typeId", typeId);
		List<ExpertAttachment> attList = attachmentMapper.selectListByMap(attachmentMap);
		return attList;
	}

	@Override
	public boolean isPublish(String id) {
		boolean bool=true;
	   List<Category> categorys = categoryMapper.getParentByChildren(id);
	   for(Category cate:categorys ){
		   if(cate.getIsPublish().equals(1)){
			   bool=false;
		   }
	   }
	   
		return bool;
	}

	/**
	 * 首恶专家名录查询
	 */
	@Override
	public List<Expert> selectIndexExpert(Integer pageNum,Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return mapper.selectIndexExpert(map);
	}

	@Override
	public List<Expert> yzCardNumber(Map<String, Object> map) {
		return mapper.yzCardNumber(map);
	}

  @Override
  public boolean isExpertCheckedParent(String categoryId, String expertId, String typeId, String flag,List < ExpertAudit > auditList) {
         List < ExpertCategory > allCategoryList = expertCategoryMapper.selectListByExpertId(expertId, typeId, null);
         if(auditList!=null && auditList.size()>0){
           for(ExpertAudit audit: auditList) {
                 if(audit.getAuditFieldId().equals(categoryId)) {
                   return false;
                   }
           }
         }
         int count=0;
         if (allCategoryList != null && allCategoryList.size() > 0 ) {
        	 for (ExpertCategory expertCategory : allCategoryList) {
                 if (!DictionaryDataUtil.findById(expertCategory.getTypeId()).getCode().equals("ENG_INFO_ID")) {
                     Category data = categoryMapper.findById(expertCategory.getCategoryId());
                     List<Category> findPublishTree = categoryMapper.findPublishTree(expertCategory.getCategoryId(), null);
                     if (findPublishTree.size() == 0) {
                        count++;
                     } else if (data != null && data.getCode().length() == 7) {
                    	count++;
                     }
                 } else {
                     Category data = engCategoryMapper.findById(expertCategory.getCategoryId());
                     List<Category> findPublishTree = engCategoryMapper.findPublishTree(expertCategory.getCategoryId(), null);
                     if (findPublishTree.size() == 0) {
                    	 count++;
                     } else if (data != null && data.getCode().length() == 7) {
                    	 count++;
                     }
                 }
             }
        	 int notCount=0;
        	 if(auditList!=null && auditList.size()>0){
        		 for(ExpertAudit audit: auditList) {
            		 for (ExpertCategory expertCategory : allCategoryList) {
            			 
         				if(expertCategory.getCategoryId().equals(audit.getAuditFieldId())){
         					notCount++;
         				}
         			}
            	 }
        	 }
        	
        	 if(count>notCount){
        		 return true;
        	 }else{
        		 return false;
        	 }
         }
         return false;
  }
}



