package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.AuthValid;
import ses.dao.ems.ExpertBatchDetailsMapper;
import ses.dao.ems.ExpertBatchMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertBatch;
import ses.model.ems.ExpertBatchDetails;
import ses.service.ems.ExpertAgainAuditService;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
/**
 * @ExpertAgainAuditServiceImpl 
 * @Description:专家复审ServiceImpl类
 * @author ShiShuai
 * @date 2017-08-10上午10:10:40
 */
@Service("expertAgainAuditService")
public class ExpertAgainAuditServiceImpl implements ExpertAgainAuditService {
	@Autowired
	private ExpertMapper expertMapper;
	@Autowired
	private ExpertBatchMapper expertBatchMapper;
	@Autowired
	private ExpertBatchDetailsMapper expertBatchDetailsMapper;
	
	@Override
	public ExpertAgainAuditImg addAgainAudit(String ids) {
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		List<String> idsList = new ArrayList<String>();
		Expert e = new Expert();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		e.setIds(idsList);
		List<Expert> list = expertMapper.findExpertByInList(e);
		for (Expert expert : list) {
			if("1".equals(expert.getStatus())){
				expert.setStatus("11");
				expert.setUpdatedAt(new Date());
			}else{
				img.setStatus(false);
				img.setMessage("只有初审合格才可以提交复审");
				return img;
			}
		}
		for (Expert expert : list) {
			expertMapper.updateByPrimaryKey(expert);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg createBatch(String batchName, String batchNumber, String ids) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		ExpertBatch expertBatch = new ExpertBatch();
		expertBatch.setBatchId(WfUtil.createUUID());
		expertBatch.setBatchName(batchName);
		expertBatch.setBatchNumber(batchNumber);
		expertBatch.setCreatedAt(new Date());
		expertBatch.setUpdatedAt(new Date());
		expertBatchMapper.insert(expertBatch);
		List<String> idsList = new ArrayList<String>();
		Expert e = new Expert();
		String[] split = ids.split(",");
		for (String string : split) {
			if( string != null ){
				idsList.add(string);
			}
		}
		e.setIds(idsList);
		List<Expert> list = expertMapper.findExpertByInList(e);
		int count=1;
		for (Expert expert : list) {
			ExpertBatchDetails expertBatchDetails = new ExpertBatchDetails();
			expertBatchDetails.setId(WfUtil.createUUID()); 
			expertBatchDetails.setBatchId(expertBatch.getBatchId());
			expertBatchDetails.setExpertId(expert.getId());
			expertBatchDetails.setBatchNumber(batchNumber);
			String number=count>=10?(count>=100?count+"":"0"+count):"00"+count;
			expertBatchDetails.setBatchDetailsNumber(batchNumber+"-"+number);
			expertBatchDetails.setNumber(number);
			expertBatchDetails.setBatchName(batchName);
			expertBatchDetails.setCreatedAt(new Date());
			expertBatchDetails.setUpdatedAt(new Date());
			expertBatchDetailsMapper.insert(expertBatchDetails);
		}
		img.setStatus(true);
		img.setMessage("操作成功");
		return img;
	}

	@Override
	public ExpertAgainAuditImg findBatch(String batchNumber,String batchName, Date createdAt, Integer pageNum) {
		// TODO Auto-generated method stub
		ExpertAgainAuditImg img = new ExpertAgainAuditImg();
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("batchNumber", batchNumber);
		map.put("batchName", batchName);
		map.put("createdAt", createdAt);
		List<ExpertBatch> list = expertBatchMapper.getAllExpertBatch(map);
		PageInfo< ExpertBatch > result = new PageInfo < ExpertBatch > (list);
		img.setStatus(true);
		img.setMessage("操作成功");
		img.setObject(result);
		return img;
	}

}
