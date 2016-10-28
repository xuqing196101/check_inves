/**
 * 
 */
package bss.service.ppms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;

import ses.util.FtpUtil;
import ses.util.WfUtil;
import bss.dao.ppms.ProjectAttachmentsMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.SaleTenderService;
import bss.model.ppms.ProjectAttachments;

/**
 * @Description:
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月20日下午2:05:38
 * @since  JDK 1.7
 */
@Service
public class SaleTenderServiceImpl implements SaleTenderService {
    @Autowired
    private ProjectAttachmentsMapper attachmentMapper;
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    @Autowired
    private ProjectMapper promapper;
    /**
     * @Description:插入记录
     *
     * @author Wang Wenshuai
     * @version 2016年10月20日 下午2:04:10  
     * @param @param saleTender      
     * @return void
     */
    @Override
    public void insert(SaleTender saleTender) {
        saleTenderMapper.insertSelective(saleTender);
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
    public void upload(MultipartFile bill,MultipartFile voucher,String projectId,String saleId,String statusBid) {
        Project selectProjectByPrimaryKey = promapper.selectProjectByPrimaryKey(projectId);
        uploadFile(bill, selectProjectByPrimaryKey);
        uploadFile(voucher, selectProjectByPrimaryKey);
        SaleTender saleTender = new SaleTender();
        saleTender.setId(saleId);
        saleTender.setStatusBond((short)2);//保证金缴纳 1缴纳。2 未缴纳
        saleTender.setStatusBid(new Short(statusBid));//标书状态 1缴纳。2 未缴纳
        saleTenderMapper.updateByPrimaryKeySelective(saleTender);
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
    public void uploadFile(MultipartFile files,Project project){
        try {
            ProjectAttachments attachment;
            if(files!=null ){
                short fileType=0;
                if(files.isEmpty()){  
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
                    String filepath = FtpUtil.upload2("saleTenderFile",files);
                    //截取文件名
                    String filename=filepath.substring(filepath.lastIndexOf("/")+1);
                    //截取文件路径
                    String path = filepath.substring(0,filepath.lastIndexOf("/")+1);
                    String path2 = path.replace("\\", "/");
                    //存附件信息到数据库
                    attachment = new ProjectAttachments();
                    attachment.setContentType(files.getContentType());
                    attachment.setCreatedAt(new Date());
                    attachment.setFileName(filename);
                    attachment.setId(WfUtil.createUUID());
                    attachment.setIsDeleted(0);
                    attachment.setFileSize((float)files.getSize());;
                    attachment.setUpdatedAt(new Date());
                    attachment.setProject(project);
                    attachmentMapper.insert(attachment);
                    fileType++;
                }  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        PageHelper.startPage(pageNum, 10);
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


}
