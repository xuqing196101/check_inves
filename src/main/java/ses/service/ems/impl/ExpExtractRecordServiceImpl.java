/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpExtractRecordMapper;
import ses.model.bms.User;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectExtractService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日下午1:45:37
 * @since  JDK 1.7
 */
@Service
public class ExpExtractRecordServiceImpl implements ExpExtractRecordService {
    @Autowired
    ExpExtractRecordMapper expExtractRecordMapper;
    @Autowired
    ExpertService ExpertService;//专家管理
    @Autowired
    ProjectExtractService extractService; //关联表
    @Autowired
    UserServiceI userServiceI;//用户管理
    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public void insert(ExpExtractRecord record) {
        expExtractRecordMapper.insertSelective(record);
    }

    /**
     * @Description:集合
     *
     * @author Wang Wenshuai
     * @version 2016年9月27日 下午4:32:28  
     * @param @param record      
     * @return void
     */
    @Override
    public List<ExpExtractRecord> listExtractRecord(
        ExpExtractRecord expExtractRecord,Integer pageNum) {
        if(pageNum!=0){
            PageHelper.startPage(pageNum, 10);
        }
        return expExtractRecordMapper.list(expExtractRecord);
    }

    /**
     * @Description:单个记录
     *
     * @author Wang Wenshuai
     * @version 2016年9月29日 下午2:19:50  
     * @param @param expExtractRecordService
     * @param @return      
     * @return ExpExtractRecord
     */
    @Override
    public ExpExtractRecord showExpExtractRecord(ExpExtractRecordService expExtractRecordService) {
        return expExtractRecordMapper.selectByPrimaryKey("21321");
    }

    /**
     * 临时专家
     * @see ses.service.ems.ExpExtractRecordService#addTemporaryExpert(ses.model.ems.Expert, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<ProjectExtract> addTemporaryExpert(Expert expert, String projectId,
        String loginName, String loginPwd) {
        //插入专家表一条数据
       String uuId=WfUtil.createUUID();
        expert.setId(uuId);
        ExpertService.insertSelective(expert);
        //插入专家抽取关联表
        ProjectExtract extract = new ProjectExtract();
        extract.setExpertId(uuId);
        extract.setOperatingType((short)1);
        extract.setProjectId(projectId);
        extractService.insertProjectExtract(extract);
        //生成15位随机码
        String randomCode = generateString(15);
        //根据随机码+密码加密
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(loginPwd, randomCode);
        //插入登录表
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(pwd);
        user.setTypeName(DictionaryDataUtil.get("EXPERT_U").getId());
        user.setTypeId(uuId);
        userServiceI.save(user, null);
        //查询条件
        ProjectExtract projectExtract = new ProjectExtract();
        projectExtract.setProjectId(projectId);
        projectExtract.setReason("1");
        //项目抽取的专家信息
        List<ProjectExtract> expertList = extractService.list(projectExtract );
        return expertList;
    }
    
    /**
     * Description: 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @author Ye MaoLin
     * @version 2016-9-14
     * @param length
     * @return String
     * @exception IOException
     */
    public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }

    
    /**
     * 
     *〈简述〉修改
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    @Override
    public void update(ExpExtractRecord extracts) {
        expExtractRecordMapper.updateByPrimaryKeySelective(extracts);
    }

}
