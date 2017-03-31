/**
 * 
 */
package bss.service.ppms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ProjectExtract;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.PackageMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.service.ppms.PackageService;

/**
 * @Title:PackageServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-9下午2:14:45
 */
@Service("packageService")
public class PackageServiceImpl implements PackageService{

  @Autowired
  private PackageMapper packageMapper;

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
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return packageMapper.selectAllByIsWon(map);
  }
  public List<Packages> findPackageAndBidMethodById(
    HashMap<String, Object> map) {
    // TODO Auto-generated method stub
    return packageMapper.findPackageAndBidMethodById(map);
  }

  /**
   * 
   *〈简述〉根据包返回抽取专家
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public List<Packages> listResultExpert(String projectId){
    return packageMapper.listResultExpert(projectId);
  }

  /**
   * 
   *〈简述〉根据包返回抽取专家
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public List<Packages> listResultExpert(String projectId,Integer isProvisional){
    return packageMapper.listResultExpert(projectId);
  }

  /**
   * 
   *〈简述〉根据包返回抽取供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public List<Packages> listResultSupplier(String projectId){
    return  packageMapper.listResultSupplier(projectId);
  }

  /**
   * 
   *〈简述〉根据包返回抽取中标供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public List<Packages> listSupplierCheckPass(String projectId){
    return packageMapper.listSupplierCheckPass(projectId);
  }

  @Override
  public List<Packages> findPackageByPage(Packages packages, int pageNum) {
    PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
    return packageMapper.find(packages);
  }

  /**
   * 返回所有专家
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
		    // TODO Auto-generated method stub
	 return packageMapper.findByID(map);
   }

  @Override
  public int insertPackage(Packages pack) {
    return packageMapper.insertPackage(pack);
  }

  @Override
  public List<Packages> listProjectExtract(String projectId) {
    // TODO Auto-generated method stub
    return packageMapper.listProjectExtract(projectId);
  }

  /**
   * 
   *〈简述〉根据包返回抽取记录
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  public List<Packages> listExpExtCondition(String projectId){
    return packageMapper.listExpExtCondition(projectId);
  }

  @Override
  public Packages selectByPrimaryKeyId(String id) {
    // TODO Auto-generated method stub
    return packageMapper.selectByPrimaryKeyId(id);
  }

  /**
   * 供应商抽取关联表
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
	// TODO Auto-generated method stub
	return packageMapper.selectByPrimaryKey(map);
}

}

