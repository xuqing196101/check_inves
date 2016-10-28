package bss.service.prms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.PackageExpertMapper;
import bss.model.prms.PackageExpert;
import bss.service.prms.PackageExpertService;
@Service("packageExpertService")
public class PackageExpertServiceImpl implements PackageExpertService {
	@Autowired
	private PackageExpertMapper mapper;
	/**
	 * 
	  * @Title: save
	  * @author ShaoYangYang
	  * @date 2016年10月18日 下午2:18:45  
	  * @Description: TODO 保存
	  * @param @param record
	  * @param @return      
	  * @return int
	 */
	@Override
	public int save(PackageExpert record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}
    /**
     * 
      * @Title: selectList
      * @author ShaoYangYang
      * @date 2016年10月18日 下午2:16:43  
      * @Description: TODO 条件查询
      * @param @param map
      * @param @return      
      * @return List<PackageExpert>
     */
	@Override
	public List<PackageExpert> selectList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectList(map);
	}
	/**
     * 
      * @Title: deleteByPackageId
      * @author ShaoYangYang
      * @date 2016年10月18日 下午2:17:16  
      * @Description: TODO 根据包id删除关联信息
      * @param @param packageId      
      * @return void
     */
	@Override
	public void deleteByPackageId(String packageId) {
		mapper.deleteByPackageId(packageId);
	}
	 /**
     * 
      * @Title: updateByBean
      * @author ShaoYangYang
      * @date 2016年10月27日 下午5:28:53  
      * @Description: TODO 根据条件修改信息
      * @param @param record      
      * @return void
     */
    public void updateByBean(PackageExpert record){
    	mapper.updateByBean(record);
    }
}
