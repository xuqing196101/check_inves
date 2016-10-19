package ses.service.ems.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.TodosMapper;
import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAttachmentMapper;
import ses.dao.ems.ExpertAuditMapper;
import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.model.ems.ExpertAudit;
import ses.model.ems.ExpertCategory;
import ses.service.ems.ExpertService;
import ses.util.FtpUtil;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;


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
	private ExpertCategoryMapper categoryMapper;
	@Autowired
	private TodosMapper todosMapper;
	@Override
	public void deleteByPrimaryKey(String id) {
		mapper.deleteByPrimaryKey(id);

	}

	@Override
	public int insertSelective(Expert record) {
		return mapper.insertSelective(record);
	}

	@Override
	public Expert selectByPrimaryKey(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(Expert record) {
		mapper.updateByPrimaryKeySelective(record);

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
		Map<String, Object> map = new HashMap<String, Object>();
		if(expert!=null){
		map.put("relName", expert.getRelName());
		map.put("expertsFrom", expert.getExpertsFrom());
		map.put("status", expert.getStatus());
		map.put("expertsTypeId", expert.getExpertsTypeId());
		}else{
			map.put("relName", null);
			map.put("expertsFrom", null);
			map.put("status", null);
			map.put("expertsTypeId", null);
		}
		return mapper.selectAllExpert(map);
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
				short fileType=0;
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
			                String filepath = FtpUtil.upload2("expertFile",myfile);
			                //截取文件名
			                String filename=filepath.substring(filepath.lastIndexOf("/")+1);
			                //截取文件路径
			                String path = filepath.substring(0,filepath.lastIndexOf("/")+1);
			                String path2 = path.replace("\\", "/");
			                //存附件信息到数据库
			                attachment = new ExpertAttachment();
			                attachment.setContentType(myfile.getContentType());
			                attachment.setCreateAt(new Date());
			                attachment.setExpertId(expertId);
			                attachment.setFileName(filename);
			                attachment.setFilePath(path2);
			                attachment.setFileSize((double)myfile.getSize());
			                attachment.setId(WfUtil.createUUID());
			                attachment.setIsDelete((short)0);
			                attachment.setIsHistory((short)0);
			                attachment.setFileType(fileType);
			                attachmentMapper.insert(attachment);
			                fileType++;
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
    	if(user!=null && user.getTypeName()==5){
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
		Integer typeName = user.getTypeName();
		Map<String,Object> map =  new HashMap<>();
		if(StringUtils.isNotEmpty(typeId) && typeName==5){
			//查出当前登录的用户个人信息
			Expert expert = mapper.selectByPrimaryKey(typeId);
			if(expert!=null){
				if((expert.getIsSubmit().equals("0") || expert.getStatus().equals("3"))&&!expert.getIsBlack().equals("1")){
						//如果专家信息不为null 并且状态为暂存  或者为退回修改 就证明该专家填写过个人信息 需要重新填写 并注册提交审核
						//放入专家信息   用于前台回显数据
						map.put("expert", expert);
				} else if(expert.getStatus().equals("2") || expert.getIsBlack().equals("1")){
					//如果审核未通过 或者已拉黑 则根据此状态阻止登录
					map.put("flag", false);
				} 
			}else{
				//如果专家信息为空 证明还没有填写过个人信息
				map.put("flag", false);
			}
		}else{
			//如果用户关联的专家id为空 证明还没有填写过个人信息
			map.put("flag", false);
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
	public void zanCunInsert(Expert expert ,String expertId,MultipartFile[] files,String realPath,String categoryIds) throws Exception{
		if(StringUtils.isEmpty(expert.getId())){//id为空就新增 否则就修改
			expert.setId(expertId);
			//已提交
			expert.setIsSubmit("0");
			//未考试
			expert.setIsDo("0");
			//未审核
			expert.setStatus("0");
			//修改时间
			//expert.setUpdatedAt(new Date());
			mapper.insertSelective(expert);
			//附件上传
			uploadFile(files, realPath, expertId);
			//保存品目
			saveCategory(expert,categoryIds);
		}else{
			//已提交
			expert.setIsSubmit("0");
			//未考试
			expert.setIsDo("0");
			//未审核
			expert.setStatus("0");
			//修改时间
			expert.setUpdatedAt(new Date());
			//获取之前的附件
			List<ExpertAttachment> attachList = attachmentMapper.selectListByExpertId(expert.getId());
			//删除之前的附件
			if(attachList!=null && attachList.size()>0){
				for (ExpertAttachment expertAttachment : attachList) {
					//修改之前的附件信息为删除状态 和历史状态
					expertAttachment.setIsDelete((short)1);
					expertAttachment.setIsHistory((short)1);
					attachmentMapper.updateByPrimaryKeySelective(expertAttachment);
				}
			}
			mapper.updateByPrimaryKey(expert);
			//重新上传新的附件
			uploadFile(files, realPath, expert.getId());
			//保存品目
			saveCategory(expert,categoryIds);
		}
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
	public void saveOrUpdate(Expert expert,String expertId,MultipartFile[] files,String realPath,String categoryIds) throws Exception{
		//如果id不为空 则为专家 暂存  或专家退回重新修改提交
		if(StringUtils.isNotEmpty(expert.getId())){
			expert.setIsDo("0");
			//已提交
			expert.setIsSubmit("1");
			//未审核
			expert.setStatus("0");
			//修改时间
			expert.setUpdatedAt(new Date());
			//执行修改
			mapper.updateByPrimaryKeySelective(expert);
			//获取之前的附件
			List<ExpertAttachment> attachList = attachmentMapper.selectListByExpertId(expert.getId());
			//删除之前的附件
			if(attachList!=null && attachList.size()>0){
				for (ExpertAttachment expertAttachment : attachList) {
					//修改之前的附件信息为删除状态 和历史状态
					expertAttachment.setIsDelete((short)1);
					expertAttachment.setIsHistory((short)1);
					attachmentMapper.updateByPrimaryKeySelective(expertAttachment);
				}
			}
			//获取之前的审核信息
			List<ExpertAudit> auditList = expertAuditMapper.selectByExpertId(expert.getId());
			if(auditList!=null && auditList.size()>0){
				for (ExpertAudit expertAudit : auditList) {
					//修改之前的审核信息为删除 和历史状态
					expertAudit.setIsDelete((long) 1);
					expertAudit.setIsHistory("1");
					expertAuditMapper.updateByPrimaryKeySelective(expertAudit);
				}
			}
			//附件上传
			uploadFile(files, realPath,expert.getId());
			if(expert.getPurchaseDepId()=="1"){
			//保存品目
				saveCategory(expert, categoryIds);
			}else{
				//不是技术专家就删除品目关联信息
				categoryMapper.deleteByExpertId(expert.getId());
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
		//执行保存
		mapper.insertSelective(expert);
		//文件上传
		uploadFile(files, realPath,expertId);
		//保存品目
		saveCategory(expert, categoryIds);
		}
		//发送待办
		Todos todos = new Todos();
		todos.setCreatedAt(new Date());
		todos.setIsDeleted((short)0);
		todos.setIsFinish((short)0);
		todos.setName("评审专家注册");
		todos.setReceiverId(expert.getPurchaseDepId());
		todos.setSenderId(expert.getId());
		todos.setUndoType((short)2);
		todos.setUrl("expert/toShenHe?id="+expert.getId());
		todosMapper.insert(todos );
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
			u.setTypeName(5);
			if(expert.getId()==null || expert.getId()=="" || expert.getId().length()==0){
				u.setTypeId(expertId);
			}else{
				u.setTypeId(expert.getId());
			}
			userMapper.updateByPrimaryKeySelective(u);
		}else{
			//注册完账号  过段时间又填写个人信息
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
		if(ids!=null && StringUtils.isNotEmpty(ids)){
			String[] idArray = ids.split(",");
			ExpertCategory expertCategory = new ExpertCategory();
			//循环品目id集合
			for (String string : idArray) {
				expertCategory.setCategoryId(string);
				expertCategory.setExpertId(expert.getId());
				//逐条保存
				categoryMapper.insert(expertCategory);
			}
		}
		
	}
}
