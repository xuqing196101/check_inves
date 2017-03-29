package ses.service.ems.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.CategoryMapper;
import ses.dao.bms.DictionaryDataMapper;
import ses.dao.bms.EngCategoryMapper;
import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAttachmentMapper;
import ses.dao.ems.ExpertAuditMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertHistory;
import ses.service.bms.RoleServiceI;
import ses.service.ems.ExpertService;
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


@Service("expertService")
public class ExpertServiceImpl implements ExpertService {

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
		if(expert.getRelName() != null && !"".equals(expert.getRelName())){
			expert.setRelName("%"+expert.getRelName()+"%");
		}
		if(expert.getMobile() != null && !"".equals(expert.getMobile())){
			expert.setMobile("%"+expert.getMobile()+"%");
		}
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
			    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
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
				} else if(expert.getStatus().equals("2")){
					//审核未通过
					map.put("expert", "5");
				}else if(expert.getIsBlack().equals("1")){
	                    //已拉黑
	                    map.put("expert", "1");
	            }else if(expert.getStatus().equals("0") && expert.getIsSubmit().equals("1") ){
					//未审核
					map.put("expert", "3");
				}else if(expert.getStatus().equals("3") && !expert.getIsBlack().equals("1")){
				    // 退回修改
				    map.put("expert", "2");
				}else if(expert.getStatus().equals("6")){
                    // 复审踢除
                    map.put("expert", "6");
                }else if(expert.getStatus().equals("7") && 1 == expert.getIsProvisional()){
                    // 临时专家,并且参加的评审项目已结束
                    map.put("expert", "7");
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
		todos.setPowerId(config.getString("zjdb"));
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
          expertAudit.setIsDelete(1);
          expertAudit.setIsHistory("1");
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
    todos.setPowerId(config.getString("zjdb"));
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
		/*PropertiesUtil config = new PropertiesUtil("config.properties");
		
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));*/
		PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
		
		//条件查询
		String relName = expert.getRelName();
		if(relName != null && !"".equals(relName)){
			expert.setRelName("%" +relName+ "%");
		}
		
		return mapper.findExpertAuditList(expert);
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
            return categoryMapper.searchByName(cateName, codeName);
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
    
}



