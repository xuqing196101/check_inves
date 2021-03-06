package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.UserMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.dao.oms.PurchaseOrgMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import common.constant.StaticVariables;
import bss.dao.ppms.PackageAdviceMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.PackageAdvice;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.service.ppms.PackageAdviceService;
import bss.util.PropUtil;

@Service("packageAdviceService")
public class PackageAdviceServiceImpl implements PackageAdviceService {

	@Autowired
	private PackageAdviceMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PackageMapper packageMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private ProjectDetailMapper detailMapper;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	/**
	 * 待审核
	 */
	private static final Integer AUDIT_NOT = 1;
	
	/**
	 * 审核中
	 */
	private static final Integer AUDITING = 2;
	
	/**
	 * 审核通过
	 */
	private static final Integer AUDIT_PASS= 3;
	
	/**
	 * 审核不通过
	 */
	private static final Integer AUDIT_FALIED = 4;
	
	/**
	 * 中止
	 */
	private static final Integer SUSPEND = 1;
	
	/**
	 * 转竞谈
	 */
	private static final Integer ZJZXTP = 2;
	
	@Override
	public void savaAudit(String projectId, String packageIds, String advice, String flowDefineId, String auditCode, String type, String userId) {
		String[] packageId = packageIds.split(StaticVariables.COMMA_SPLLIT);
		if (packageId.length > 0) {
			PackageAdvice packageAdvice = null; 
			Date date=new Date();
			for (String packId : packageId) {
				packageAdvice = new PackageAdvice();
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("packageId", packId);
				List<PackageAdvice> findByList = mapper.findByList(hashMap);
				if (findByList != null && !findByList.isEmpty()) {
					for (PackageAdvice packageAdvice2 : findByList) {
						packageAdvice2.setIsDeleted(1);
						mapper.update(packageAdvice2);
					}
				}
				packageAdvice.setId(WfUtil.createUUID());
				packageAdvice.setProjectId(projectId);
				packageAdvice.setPackageId(packId);
				packageAdvice.setFlowDefineId(flowDefineId);
				packageAdvice.setStatus(AUDIT_NOT);
				packageAdvice.setAdvice(advice);
				if ("1".equals(type)) {
					packageAdvice.setType(SUSPEND);
				} else {
					packageAdvice.setType(ZJZXTP);
					packageAdvice.setProcessStatus(0);
				}
				packageAdvice.setCode(auditCode);
				packageAdvice.setCreatedAt(date);
				packageAdvice.setIsDeleted(0);
				packageAdvice.setProposer(userId);
				mapper.insert(packageAdvice);
				
				if ("2".equals(type)) {
					Packages packages = packageMapper.selectByPrimaryKeyId(packId);
					if (packages != null) {
						packages.setProjectStatus(DictionaryDataUtil.getId("ZJTSHZ"));
						packageMapper.updateByPrimaryKeySelective(packages);
					}
				}
			}
		}
	}

	@Override
	public List<PackageAdvice> list(PackageAdvice packageAdvice, User user, Integer page) {
		HashMap<String, Object> map = new HashMap<>();
		if (packageAdvice.getProject() != null && StringUtils.isNotBlank(packageAdvice.getProject().getName())) {
			map.put("name", packageAdvice.getProject().getName());
		}
		if (packageAdvice.getProject() != null && StringUtils.isNotBlank(packageAdvice.getProject().getProjectNumber())) {
			map.put("projectNumber", packageAdvice.getProject().getProjectNumber());
		}
		if (packageAdvice.getStatus() != null) {
			map.put("status", packageAdvice.getStatus());
		}
		map.put("orgId", user.getOrg().getId());
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
		List<PackageAdvice> list = mapper.findByProjectList(map);
		for (PackageAdvice packageAdvice2 : list) {
			User user2 = userMapper.queryById(packageAdvice2.getProject().getPrincipal());
			if (user2 != null) {
				packageAdvice2.getProject().setPrincipal(user2.getRelName());
			}
		}
		return list;
	}

	@Override
	public PackageAdvice audit(String code) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("code", code);
		List<PackageAdvice> findByProjectList = mapper.findByProjectList(map);
		if (findByProjectList != null && !findByProjectList.isEmpty()) {
			for (PackageAdvice packageAdvice : findByProjectList) {
				User user2 = userMapper.queryById(packageAdvice.getProject().getPrincipal());
				if (user2 != null) {
					packageAdvice.getProject().setPrincipal(user2.getRelName());
				}
				DictionaryData data = DictionaryDataUtil.findById(packageAdvice.getProject().getStatus());
				if (data != null) {
					packageAdvice.getProject().setStatus(data.getName());
				}
				String orgName = orgnizationMapper.selectById(packageAdvice.getProject().getPurchaseDepId());
				if (StringUtils.isNotBlank(orgName)) {
					packageAdvice.getProject().setPurchaseDepName(orgName);
				}
			}
			List<Packages> packList = new ArrayList<Packages>();
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("code", code);
			hashMap.put("projectId", findByProjectList.get(0).getProjectId());
			List<PackageAdvice> findByList = mapper.findByList(map);
			if (findByList != null && !findByList.isEmpty()) {
				for (PackageAdvice packageAdvice : findByList) {
					Packages packages = packageMapper.selectByPrimaryKeyId(packageAdvice.getPackageId());
					if (packageAdvice.getStatus() == 3 || packageAdvice.getStatus() == 4) {
						packages.setStatus(5);
					}
					packList.add(packages);
				}
			}
			findByProjectList.get(0).setPackageList(packList);
		}
		return findByProjectList.get(0);
	}

	@Override
	public List<PackageAdvice> find(HashMap<String, Object> map) {
		
		return mapper.findByList(map);
	}

	@Override
	public void update(User user,List<PackageAdvice> find, String removedReason, Integer status) {
		for (PackageAdvice packageAdvice : find) {
			packageAdvice.setStatus(status);
			packageAdvice.setUserId(user.getId());
			if (StringUtils.isNotBlank(removedReason)) {
				packageAdvice.setReason(removedReason);
			}
			packageAdvice.setAduitTime(new Date());
			mapper.update(packageAdvice);
		}
	}

	@Override
	public BigDecimal selectbudget(String code) {
		BigDecimal budgets = BigDecimal.ZERO;
		HashMap<String, Object> map = new HashMap<>();
		map.put("code", code);
		List<PackageAdvice> findByList = mapper.findByList(map);
		if (findByList != null && !findByList.isEmpty()) {
			for (PackageAdvice packageAdvice : findByList) {
				List<ProjectDetail> selectByAll= detailMapper.selectByPackageId(packageAdvice.getPackageId());
				if (selectByAll != null && !selectByAll.isEmpty()) {
					for (ProjectDetail projectDetail : selectByAll) {
						BigDecimal num = BigDecimal.valueOf(projectDetail.getBudget());
						budgets = budgets.add(num);
					}
				}
			}
		}
		return budgets;
	}

	@Override
	public void recheck(String packageIds) {
		String[] id = packageIds.split(StaticVariables.COMMA_SPLLIT);
		for (String string : id) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("packageId", string);
			List<PackageAdvice> findByList = mapper.findByList(map);
			if (findByList != null && !findByList.isEmpty()) {
				findByList.get(0).setStatus(AUDIT_NOT);
				mapper.update(findByList.get(0));
			}
			Packages packages = packageMapper.selectByPrimaryKeyId(string);
			if (packages != null) {
				packages.setProjectStatus(DictionaryDataUtil.getId("ZJTSHZ"));
				packageMapper.updateByPrimaryKeySelective(packages);
			}
		}
	}

  @Override
  public List<PackageAdvice> selectByStatus(String projectId) {
    return mapper.selectByStatus(projectId);
  }

  @Override
  public void update(PackageAdvice find) {
    mapper.update(find);
    
  }
}
