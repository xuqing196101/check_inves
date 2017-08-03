package iss.service.hl.impl;

import iss.dao.hl.ServiceHotlineMapper;
import iss.model.hl.ServiceHotline;
import iss.service.hl.ServiceHotlineService;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

/**
 * 
 * Description： 服务热线Service接口实现类
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年5月25日 上午11:26:07 
 *
 */
@Service("/serviceHotlineService")
public class ServiceHotlineServiceImpl implements ServiceHotlineService{

	@Autowired
	private ServiceHotlineMapper serviceHotlineMapper;
	
	/** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;

	@Override
	public List<ServiceHotline> selectAll(ServiceHotline record,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ServiceHotline> list = serviceHotlineMapper.selectAll(record);
		return list;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return serviceHotlineMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(ServiceHotline record) {
		return serviceHotlineMapper.insertSelective(record);
	}

	@Override
	public ServiceHotline selectByPrimaryKey(String id) {
		return serviceHotlineMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ServiceHotline record) {
		return serviceHotlineMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 
	 * Description: 服务热线数据导出
	 * 
	 * @author zhang shubin
	 * @data 2017年8月3日
	 * @param 
	 * @return
	 */
	@Override
	public boolean exportHotLine(String start, String end,Date synchDate) {
		boolean boo=false;
		//获取创建
		List<ServiceHotline> hotLineCList= serviceHotlineMapper.selectByCreateDate(start, end);
		//获取修改
		List<ServiceHotline> hotLineMList=serviceHotlineMapper.selectByUpdateDate(start, end);
		int sum=0;
		if(hotLineCList != null && hotLineCList.size() > 0){
			sum=sum+hotLineCList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_HOT_LINE_PATH_FILENAME, 30),JSON.toJSONString(hotLineCList));
		}
		if(hotLineMList!=null && hotLineMList.size()>0){
			sum=sum+hotLineMList.size();
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_HOT_LINE_PATH_FILENAME, 30),JSON.toJSONString(hotLineMList));
		}
		synchRecordService.synchBidding(synchDate, sum+"", Constant.DATE_SYNCH_HOT_LINE, Constant.OPER_TYPE_EXPORT, Constant.HOT_LINE_COMMIT);
		boo=true;
		return boo;
	}

	@Override
	public boolean importHotLine(File file) {
		boolean boo=false;
		 List<ServiceHotline> list = FileUtils.getBeans(file, ServiceHotline.class); 
	        if (list != null && list.size() > 0){
	        	for (ServiceHotline serviceHotline : list) {
		        	Integer count=	serviceHotlineMapper.countById(serviceHotline.getId());
					if (count == 0) {
						serviceHotlineMapper.addHotline(serviceHotline);
					} else {
						serviceHotlineMapper
								.updateByPrimaryKeySelective(serviceHotline);
					}
	        	}
	        	synchRecordService.synchBidding(new Date(), list.size()+"", Constant.DATE_SYNCH_HOT_LINE, Constant.OPER_TYPE_IMPORT, Constant.HOT_LINE_COMMIT_IMPORT);
	        }
	        boo=true;
		return boo;
	}
	
}
