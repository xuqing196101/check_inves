/**
 * 
 */
package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ppms.PackageMapper;
import bss.model.ppms.Packages;
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
    public List<SupplierCheckPass> listSupplierCheckPass(String projectId){
        return packageMapper.listSupplierCheckPass(projectId);
    }
}

