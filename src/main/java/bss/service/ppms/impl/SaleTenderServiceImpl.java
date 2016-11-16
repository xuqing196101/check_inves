package bss.service.ppms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.ProjectAttachmentsMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.SaleTender;
import bss.service.ppms.SaleTenderService;

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
    public String insert(SaleTender saleTender) {
        List<SaleTender> list = saleTenderMapper.list(new SaleTender(saleTender.getProjectId(), saleTender.getSupplierId()));
        if (list != null && list.size() != 0){
            return "error";
        } else {
            saleTenderMapper.insertSelective(saleTender);
        }
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
}
