package ses.service.sms.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.BlacklistLogMapper;
import ses.dao.sms.SupplierBlacklistMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.User;
import ses.model.sms.BlacklistLog;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.model.sms.SupplierBlacklistVO;
import ses.service.sms.SupplierBlacklistService;
import ses.util.PropertiesUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

@Service(value = "supplierBlacklistService")
public class SupplierBlacklistServiceImpl implements SupplierBlacklistService {

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private SupplierBlacklistMapper supplierBlacklistMapper;

	@Autowired
	private BlacklistLogMapper blacklistLogMapper;
	
	/** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;

	@Override
	public List<Supplier> findSupplier(Supplier supplier, int page) {
		// 过滤到已经添加到黑名单的  不包含 手动移除
		List<String> listSupplierBlacklists = supplierBlacklistMapper.findByStatus("2");
		if (listSupplierBlacklists != null && listSupplierBlacklists.size() > 0) {
			supplier.setItem(listSupplierBlacklists);
		}

		supplier.setStatus(1);
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		List<Supplier> listSuppliers = supplierMapper.findSupplier(supplier);
		return listSuppliers;
	}

	@Override
	public void saveOrUpdateSupplierBlack(SupplierBlacklist supplierBlacklist, User user) {
		Date startTime = supplierBlacklist.getStartTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		if (supplierBlacklist.getTerm() != 0) {
			calendar.add(Calendar.MONTH, supplierBlacklist.getTerm());
		} else {
			calendar.add(Calendar.MONTH, 100 * 12);
		}
		supplierBlacklist.setStatus(0);
		supplierBlacklist.setEndTime(calendar.getTime());
		BlacklistLog blacklistLog = new BlacklistLog();
		BeanUtils.copyProperties(supplierBlacklist, blacklistLog, new String[] { "serialVersionUID", "id", "endTime", "status", "supplierName" });
		blacklistLog.setOperationDate(new Date());
		blacklistLog.setOperationId(user.getId());
		blacklistLog.setOperationName(user.getRelName());
		if (supplierBlacklist.getId() != null && !"".equals(supplierBlacklist.getId())) {
			supplierBlacklist.setUpdatedAt(new Date());
			supplierBlacklistMapper.updateByPrimaryKeySelective(supplierBlacklist);
			blacklistLog.setOperationType(1);
		} else {
			supplierBlacklist.setCreatedAt(new Date());
			supplierBlacklistMapper.insertSelective(supplierBlacklist);
			blacklistLog.setOperationType(0);
		}
		blacklistLogMapper.insertSelective(blacklistLog);
	}

	@Override
	public List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		String supplierName = supplierBlacklist.getSupplierName();
		if (supplierName != null && !"".equals(supplierName)) {
			supplierBlacklist.setSupplierName(supplierName);
		}
		List<SupplierBlacklist> listSupplierBlacklists = supplierBlacklistMapper.findSupplierBlacklist(supplierBlacklist);
		return listSupplierBlacklists;
	}

	@Override
	public SupplierBlacklist getSupplierBlacklist(String supplierBlacklistId) {
		SupplierBlacklist supplierBlacklist = supplierBlacklistMapper.selectByPrimaryKey(supplierBlacklistId);
		return supplierBlacklist;
	}

	@Override
	public void operatorRemove(String ids, User user) {
		String[] split = ids.split(",");
		for (String id : split) {
			SupplierBlacklist supplierBlacklist = new SupplierBlacklist();
			supplierBlacklist.setId(id);
			supplierBlacklist.setStatus(2);
			supplierBlacklistMapper.updateStatusById(supplierBlacklist);

			// 记录表
			SupplierBlacklist sbl = supplierBlacklistMapper.selectByPrimaryKey(id);
			BlacklistLog blacklistLog = new BlacklistLog();
			BeanUtils.copyProperties(sbl, blacklistLog, new String[] { "serialVersionUID", "id", "endTime", "status", "supplierName" });
			blacklistLog.setOperationDate(new Date());
			blacklistLog.setOperationId(user.getId());
			blacklistLog.setOperationName(user.getRelName());
			blacklistLog.setOperationType(2);
			blacklistLogMapper.insertSelective(blacklistLog);
		}
	}
	
	@Override
	public void updateStatusTask() {
		SupplierBlacklist supplierBlacklist = new SupplierBlacklist();
		supplierBlacklist.setStatus(2);
		List<SupplierBlacklist> list = supplierBlacklistMapper.findSupplierBlacklist(supplierBlacklist);
		for(SupplierBlacklist sbk : list) {
			Date endTime = sbk.getEndTime();
			if (endTime.before(new Date())) {
				sbk.setStatus(1);
				supplierBlacklistMapper.updateByPrimaryKeySelective(sbk);
			}
		}
	}

	@Override
	public List<SupplierBlacklist> getIndexSupplierBlacklist() {
		PageHelper.startPage(0, 5);
		List<SupplierBlacklist> supplierBlackList = supplierBlacklistMapper.findSupplierBlacklist(null);
		return supplierBlackList;
	}

	@Override
	public List<SupplierBlacklistVO> findSupplierBlacklist(
			SupplierBlacklist supplierBlacklist, String supplierTypeIds,
			int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
		String supplierName = supplierBlacklist.getSupplierName();
		if (supplierName != null && !"".equals(supplierName)) {
			supplierBlacklist.setSupplierName("%" + supplierName + "%");
		}
		String[] supplierTypeIdAry = null;
		if(supplierTypeIds != null){
			supplierTypeIdAry = supplierTypeIds.split(",");
		}
		List<SupplierBlacklistVO> list = supplierBlacklistMapper.selectSupplierBlacklist(supplierBlacklist, supplierTypeIdAry);
		return list;
	}

	/**
	 * 导出供应商黑名单信息
	 */
	@Override
	public boolean exportSupplierBlacklist(String start, String end,
			Date synchDate) {
		boolean boo=false;
		//导出供应商黑名单表数据
		//获取创建的
		List<SupplierBlacklist> supplierBlacklistCList= supplierBlacklistMapper.selectByCreateDate(start, end);
		//获取修改的
		List<SupplierBlacklist> supplierBlacklistMList=supplierBlacklistMapper.selectByUpdateDate(start, end);
		int sum=0;
		if(supplierBlacklistCList != null && supplierBlacklistCList.size() > 0){
			sum=sum+supplierBlacklistCList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SUPPLIER_BLACKLIST_PATH_FILENAME, 26),JSON.toJSONString(supplierBlacklistCList));
		}
		if(supplierBlacklistMList!=null && supplierBlacklistMList.size()>0){
			sum=sum+supplierBlacklistMList.size();
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SUPPLIER_BLACKLIST_PATH_FILENAME, 26),JSON.toJSONString(supplierBlacklistMList));
		}
		synchRecordService.synchBidding(synchDate, sum+"", Constant.DATE_SYNCH_SUPPLIER_BLACKLIST, Constant.OPER_TYPE_EXPORT, Constant.SUPPLIER_BLACKLIST_COMMIT);
		//导出供应商黑名单记录表数据
		List<BlacklistLog> supplierBlacklistLogList = blacklistLogMapper.selectByDate(start, end);
		int log=0;
		if(supplierBlacklistLogList != null && supplierBlacklistLogList.size() > 0){
			log=log+supplierBlacklistLogList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SUPPLIER_BLACKLIST_LOG_PATH_FILENAME, 27),JSON.toJSONString(supplierBlacklistLogList));
		}
		synchRecordService.synchBidding(synchDate, log+"", Constant.DATE_SYNCH_SUPPLIER_BLACKLIST_LOG, Constant.OPER_TYPE_EXPORT, Constant.SUPPLIER_BLACKLIST_LOG_COMMIT);
		boo=true;
		return boo;
	}

	/**
	 * 导入供应商黑名单信息
	 */
	@Override
	public boolean importSupplierBlacklist(File file) {
		boolean boo=false;
		 List<SupplierBlacklist> list = FileUtils.getBeans(file, SupplierBlacklist.class); 
	        if (list != null && list.size() > 0){
	        	for (SupplierBlacklist supplierBlacklist : list) {
	        	Integer count=	supplierBlacklistMapper.countById(supplierBlacklist.getId());
	        	  if(count==0){
	        		  supplierBlacklistMapper.insertSelective(supplierBlacklist);
	        	  }else{
	        		  supplierBlacklistMapper.updateByPrimaryKeySelective(supplierBlacklist);
	        	  }
				}
	        	synchRecordService.synchBidding(new Date(), list.size()+"", Constant.DATE_SYNCH_SUPPLIER_BLACKLIST, Constant.OPER_TYPE_IMPORT, Constant.SUPPLIER_BLACKLIST_COMMIT_IMPORT);
	        }
	        boo=true;
		return boo;
	}

	/**
	 * 导入供应商黑名单记录信息
	 */
	@Override
	public boolean importSupplierBlacklistLog(File file) {
		boolean boo=false;
		 List<BlacklistLog> list = FileUtils.getBeans(file, BlacklistLog.class); 
	        if (list != null && list.size() > 0){
	        	for (BlacklistLog blacklistLog : list) {
	        	Integer count=	blacklistLogMapper.countById(blacklistLog.getId());
	        	  if(count==0){
	        		  blacklistLogMapper.insertSelective(blacklistLog);
	        	  }else{
	        		  blacklistLogMapper.updateByPrimaryKeySelective(blacklistLog);
	        	  }
				}
	        	synchRecordService.synchBidding(new Date(), list.size()+"", Constant.DATE_SYNCH_SUPPLIER_BLACKLIST_LOG, Constant.OPER_TYPE_IMPORT, Constant.SUPPLIER_BLACKLIST_LOG_COMMIT_IMPORT);
	        }
	        boo=true;
		return boo;
	}
	
}
