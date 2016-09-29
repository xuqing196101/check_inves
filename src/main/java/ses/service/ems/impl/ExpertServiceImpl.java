package ses.service.ems.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import ses.dao.bms.UserMapper;
import ses.dao.ems.ExpertAttachmentMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAttachment;
import ses.service.ems.ExpertService;
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
				 for(MultipartFile myfile : files){  
			            if(myfile.isEmpty()){  
			            }else{  
			                String filename = myfile.getOriginalFilename();
			                String uuid = WfUtil.createUUID();
			                //文件名处理
			                filename=uuid+filename;
			                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload_file\\文件夹中  
			                //String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");  
			                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
			                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, filename));
			                //存附件信息到数据库
			                attachment = new ExpertAttachment();
			                attachment.setContentType(myfile.getContentType());
			                attachment.setCreateAt(new Date());
			                attachment.setExpertId(expertId);
			                attachment.setFileName(filename);
			                attachment.setFilePath(realPath);
			                attachment.setFileSize((double)myfile.getSize());
			                attachment.setId(WfUtil.createUUID());
			                attachment.setIsDelete((short)0);
			                attachment.setIsHistory((short)0);
			                attachmentMapper.insert(attachment);
			            }  
			        }  
				}
		} catch (IOException e) {
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
    			//否则为新增个人信息
    			expert.setId(WfUtil.createUUID());
    			user.setTypeId(expert.getId());
    			//新增个人信息
    			mapper.insertSelective(expert);
    			//更新登录用户信息
    			userMapper.updateByPrimaryKeySelective(user);
    		}
    	}
	}
}
