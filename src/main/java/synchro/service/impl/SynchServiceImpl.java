package synchro.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.util.FileUtil;

import com.github.pagehelper.PageHelper;

import ses.util.PropUtil;
import synchro.dao.SynchRecordMapper;
import synchro.model.SynchRecord;
import synchro.service.SynchService;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述>实现类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class SynchServiceImpl implements SynchService {
    
    /** 数据同步mapper **/
    @Autowired
    private SynchRecordMapper mapper;
    
    /**
     * 
     * @see synchro.service.SynchService#getList(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
     */
    @Override
    public List<SynchRecord> getList(Integer optype, Integer page, String searchType, String startTime, String endTime) {
        if (page == null){
            page = 1;
        }
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("file.upload.maxFileSize")));
        return mapper.getSynchRecordByOperType(optype, searchType, startTime, endTime);
    }

	@Override
	public void imageHandler() {
		Date date=new Date();
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMdd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_MONTH, -1);
		String src = sdf.format(cale.getTime());//昨天的文件夹名字
		String supplierPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商所有图片
		String expertPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.expert.system.path")+"/"+src;//专家所有图片
		String synchExport=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.export")+"/"+src;
		FileUtil.copyFolder(supplierPath, synchExport);
		FileUtil.copyFolder(expertPath, synchExport);
		
	}

	@Override
	public void imageImportHandler() {
		Date date=new Date();
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyyMMdd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.add(Calendar.DAY_OF_MONTH, -1);
		String src = sdf.format(cale.getTime());//昨天的文件夹名字
		String supplierPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商专路径
		String expertPath=PropUtil.getProperty("file.base.path")+PropUtil.getProperty("file.expert.system.path")+"/"+src;//专家路径
		String supplier=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.import")+"/"+PropUtil.getProperty("file.supplier.system.path")+"/"+src;//供应商图片
		String expert=PropUtil.getProperty("file.sync.base")+PropUtil.getProperty("file.sync.import")+"/"+PropUtil.getProperty("file.expert.system.path")+"/"+src;//专家图片
		FileUtil.copyFolder(supplier, supplierPath);
		FileUtil.copyFolder(expert, expertPath);
		
	}

}
