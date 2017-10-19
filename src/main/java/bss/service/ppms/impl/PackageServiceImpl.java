/**
 * 
 */
package bss.service.ppms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectDetailMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.service.ppms.PackageService;

/**
 *  @Title:PackageServiceImpl  @Description:   @author ZhaoBo
 *  @date 2016-10-9下午2:14:45
 */
@Service("packageService")
public class PackageServiceImpl implements PackageService {

	@Autowired
	private PackageMapper packageMapper;

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ProjectDetailMapper detailMapper;

	@Autowired
	private OrgnizationMapper orgnizationMapper;

	@Override
	public int insertSelective(Packages pack) {
		return packageMapper.insertSelective(pack);
	}

	/**
	 * @see bss.service.ppms.PackageService#selectPackageById(java.util.HashMap)
	 */
	@Override
	public List<Packages> selectPackageById(HashMap<String, Object> map) {
		return packageMapper.selectPackageById(map);
	}

	@Override
	public int updateByPrimaryKeySelective(Packages pack) {
		return packageMapper.updateByPrimaryKeySelective(pack);
	}

	@Override
	public List<Packages> findPackageById(HashMap<String, Object> map) {
		return packageMapper.findPackageById(map);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return packageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Packages> selectAllByIsWon(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return packageMapper.selectAllByIsWon(map);
	}

	public List<Packages> findPackageAndBidMethodById(HashMap<String, Object> map) {
		
		return packageMapper.findPackageAndBidMethodById(map);
	}

	/**
	 * 
	 * 〈简述〉根据包返回抽取专家 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public List<Packages> listResultExpert(String projectId) {
		return packageMapper.listResultExpert(projectId);
	}

	/**
	 * 
	 * 〈简述〉根据包返回抽取专家 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public List<Packages> listResultExpert(String projectId, Integer isProvisional) {
		return packageMapper.listResultExpert(projectId);
	}

	/**
	 * 
	 * 〈简述〉根据包返回抽取供应商 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public List<Packages> listResultSupplier(String projectId) {
		
		return packageMapper.listResultSupplier(projectId);
	}

	/**
	 * 
	 * 〈简述〉根据包返回抽取中标供应商 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public List<Packages> listSupplierCheckPass(String projectId) {
		
		return packageMapper.listSupplierCheckPass(projectId);
	}

	@Override
	public List<Packages> findPackageByPage(Packages packages, int pageNum) {
		PageHelper.startPage(pageNum, Integer.parseInt(PropUtil.getProperty("pageSize")));
		return packageMapper.find(packages);
	}

	/**
	 * 返回所有专家
	 * 
	 * @see bss.service.ppms.PackageService#listResultAllExpert(java.lang.String)
	 */
	@Override
	public List<Packages> listResultAllExpert(String projectId) {
		
		return packageMapper.listResultAllExpert(projectId);
	}

	/**
	 * 
	 * @see bss.service.ppms.PackageService#find(bss.model.ppms.Packages)
	 */
	@Override
	public List<Packages> find(Packages packages) {
		
		return packageMapper.find(packages);
	}

	/**
	 * 根据 参数 返回 数据
	 */
	@Override
	public List<Packages> findByID(Map<String, Object> map) {

		return packageMapper.findByID(map);
	}

	@Override
	public int insertPackage(Packages pack) {
		
		return packageMapper.insertPackage(pack);
	}

	@Override
	public List<Packages> listProjectExtract(String projectId) {

		return packageMapper.listProjectExtract(projectId);
	}

	/**
	 * 
	 * 〈简述〉根据包返回抽取记录 〈详细描述〉
	 * 
	 * @author Wang Wenshuai
	 * @return
	 */
	public List<Packages> listExpExtCondition(String projectId) {

		return packageMapper.listExpExtCondition(projectId);
	}

	@Override
	public Packages selectByPrimaryKeyId(String id) {

		return packageMapper.selectByPrimaryKeyId(id);
	}

	/**
	 * 供应商抽取关联表
	 * 
	 * @see bss.service.ppms.PackageService#selectByPrimaryKeyId(java.lang.String)
	 */
	@Override
	public List<Packages> listExtRelate(String projectId) {
		return packageMapper.listSupplierExtract(projectId);
	}

	@Override
	public List<Packages> supplierCheckPa(String projectId) {

		return packageMapper.supplierCheckPa(projectId);
	}

	@Override
	public List<Packages> selectByProjectKey(HashMap<String, Object> map) {

		return packageMapper.selectByPrimaryKey(map);
	}

	@Override
	public String merge(String projectId, String id) {
		String packageName = null;
		List<Integer> num = new ArrayList<Integer>();
		String[] packageId = id.split(StaticVariables.COMMA_SPLLIT);
		for (int i = 0; i < packageId.length; i++) {
			Packages packages = packageMapper.selectByPrimaryKeyId(packageId[i]);
			if (packages != null) {
				packages.setProjectStatus(DictionaryDataUtil.getId("FBWC"));
				packageMapper.updateByPrimaryKeySelective(packages);
				String substring = packages.getName().substring(1, packages.getName().length() - 1);
				if (Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(substring).matches()) {
					num.add(Integer.valueOf(substring));
				} else {
					if (packageName == null) {
						packageName = packages.getName();
					} else {
						packageName = packageName + StaticVariables.COMMA_SPLLIT + String.valueOf(packages.getName());
					}

				}

			}
		}

		if (num != null && num.size() > 0) {
			Boolean flag = IsSeries(num);
			for (Integer integer : num) {
				if (flag) {
					if (packageName == null) {
						packageName = String.valueOf(integer);
					} else if (num.get(num.size() - 1) == integer) {
						packageName = packageName + "-" + String.valueOf(integer);
					}
				} else {
					if (packageName == null) {
						packageName = String.valueOf(integer);
					} else {
						packageName = packageName + StaticVariables.COMMA_SPLLIT + String.valueOf(integer);
					}
				}
			}
		}

		Project project = projectMapper.selectProjectByPrimaryKey(projectId);
		if (project != null) {
			Orgnization org = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
			// 添加一个子项目
			Project newProject = new Project();
			if (StringUtils.isNotBlank(project.getName())) {
				newProject.setName(project.getName() + "（第" + packageName + "包）");
			}
			if (StringUtils.isNotBlank(project.getProjectNumber())) {
				newProject.setProjectNumber(project.getProjectNumber() + "（" + packageName + "）");
			}
			if (StringUtils.isNotBlank(project.getPrincipal())) {
				newProject.setPrincipal(project.getPrincipal());
			}
			if (StringUtils.isNotBlank(project.getIpone())) {
				newProject.setIpone(project.getIpone());
			}
			if (StringUtils.isNotBlank(project.getPurchaseType())) {
				newProject.setPurchaseType(project.getPurchaseType());
			}
			if (StringUtils.isNotBlank(project.getPurchaseDepId())) {
				newProject.setPurchaseDep(new PurchaseDep(project.getPurchaseDepId()));
			}
			if (StringUtils.isNotBlank(project.getPlanType())) {
				newProject.setPlanType(project.getPlanType());
			}
			if (StringUtils.isNotBlank(project.getStatus())) {
				newProject.setStatus(DictionaryDataUtil.getId("FBWC"));
			}
			if (StringUtils.isNotBlank(project.getAppointMan())) {
				newProject.setAppointMan(project.getAppointMan());
			}
			if (project.getIsImport() != null) {
				newProject.setIsImport(project.getIsImport());
			}
			if (project.getIsRehearse() != null) {
				newProject.setIsRehearse(project.getIsRehearse());
			}
			if (project.getIsProvisional() != null) {
				newProject.setIsProvisional(project.getIsProvisional());
			}
			if (project.getStartTime() != null) {
				newProject.setStartTime(project.getStartTime());
			}
			newProject.setSectorOfDemand(org.getShortName());
			newProject.setCreateAt(new Date());
			newProject.setParentId(project.getId());
			projectMapper.insertSelective(newProject);

			for (int i = 0; i < packageId.length; i++) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("packageId", packageId[i]);
				List<ProjectDetail> details = detailMapper.selectById(map);
				if (details != null && details.size() > 0) {
					for (ProjectDetail projectDetail : details) {
						projectDetail.setProject(newProject);
						detailMapper.updateByPrimaryKeySelective(projectDetail);
					}
				}
				Packages packages = packageMapper.selectByPrimaryKeyId(packageId[i]);
				packages.setProjectId(newProject.getId());
				packageMapper.updateByPrimaryKeySelective(packages);
			}
		}

		return id;
	}

	public Boolean IsSeries(List<Integer> list) {
		Boolean flag = true;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != i + 1) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	@Override
	public List<Packages> findPackage(HashMap<String, Object> map, int pageNum) {
		PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
		return packageMapper.selectPackageOrderByCreated(map);
	}

	@Override
	public List<Packages> selectByPackageFirstAudit(HashMap<String, Object> map) {
		PageHelper.startPage((Integer) map.get("page"),Integer.parseInt(PropUtil.getProperty("pageSize")));
		List<Packages> packages = packageMapper.selectByPackageFirstAudit(map);
		return packages;
	}

	@Override
	public Boolean savePackage(String ids, String projectId) {
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(ids)) {
			Project project = projectMapper.selectProjectByPrimaryKey(projectId);
			if (project != null) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("parentId", project.getId());
				List<Project> selectByList = projectMapper.selectByList(map);
				if (selectByList != null && !selectByList.isEmpty()) {
					Integer number = 0;
					for (Project project2 : selectByList) {
						HashMap<String, Object> pack = new HashMap<String, Object>();
						pack.put("projectId", project2.getId());
						List<Packages> packList = packageMapper.findPackageById(pack);
						if (packList != null && !packList.isEmpty()) {
							number += packList.size();
						}
					}
					addPackage(number, project);
				} else {
					addPackage(null, project);
				}

				HashMap<String, Object> pack = new HashMap<String, Object>();
				pack.put("projectId", projectId);
				List<Packages> packList = packageMapper.findPackageById(pack);
				String[] id = ids.split(StaticVariables.COMMA_SPLLIT);
				for (String detailId : id) {
					ProjectDetail detail = detailMapper.selectByPrimaryKey(detailId);
					if (detail != null) {
						pack.put("id", detail.getRequiredId());
						List<ProjectDetail> details = detailMapper.selectByParentId(pack);
						if (details != null && !details.isEmpty() && details.size() == 1) {
							details.get(0).setPackageId(packList.get(packList.size() - 1).getId());
							details.get(0).setUpdateAt(new Date());
							detailMapper.updateByPrimaryKeySelective(details.get(0));
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	private void addPackage(Integer number, Project project) {
		HashMap<String, Object> pack = new HashMap<String, Object>();
		pack.put("projectId", project.getId());
		List<Packages> packList = packageMapper.findPackageById(pack);
		Packages pg = new Packages();
		pg.setProjectId(project.getId());
		pg.setIsDeleted(0);
		pg.setProjectStatus(DictionaryDataUtil.getId("FBWC"));
		if (number != null) {
			pg.setName("第" + (number + 1 + packList.size()) + "包");
			pg.setPackageNumber("(" + (number + 1 + packList.size()) + ")");
		} else {
			pg.setName("第" + (packList.size() + 1) + "包");
			pg.setPackageNumber("(" + (packList.size() + 1) + ")");
		}
		if (project.getIsImport() == 1) {
			pg.setIsImport(1);
		} else {
			pg.setIsImport(0);
		}
		pg.setPurchaseType(project.getPurchaseType());
		pg.setCreatedAt(new Date());
		pg.setUpdatedAt(new Date());
		packageMapper.insertSelective(pg);
	}

}
