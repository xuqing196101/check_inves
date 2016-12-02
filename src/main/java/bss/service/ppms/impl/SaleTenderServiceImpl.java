package bss.service.ppms.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectAttachmentsMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.SaleTender;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageHelper;

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
     * 包的持久层
     */
    @Autowired
    private PackageMapper packageMapper;
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
}

