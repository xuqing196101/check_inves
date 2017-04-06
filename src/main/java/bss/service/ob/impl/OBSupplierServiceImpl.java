package bss.service.ob.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.dao.FileUploadMapper;
import common.model.UploadFile;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBSupplierService;

@Service("oBSupplierService")
public class OBSupplierServiceImpl implements OBSupplierService {

	@Autowired
	private OBSupplierMapper oBSupplierMapper;
	
	@Autowired
    private FileUploadMapper fileDao;
	  /** 记录service  **/
    @Autowired
    private SynchRecordService  SynchRecordService;

	@Override
	public List<OBSupplier> selectByProductId(String id, Integer page,
			Integer status,String supplierName,String smallPointsName,String smallPointsId) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> list = null;
		if (status == 1) {
			list = oBSupplierMapper.selectByProductId1(id,supplierName,smallPointsName,smallPointsId);
		} else if (status == 2) {
			list = oBSupplierMapper.selectByProductId2(id,supplierName,smallPointsName,smallPointsId);
		}else{
			list = oBSupplierMapper.selectByProductId(id,supplierName,smallPointsName,smallPointsId);
		}
		return list;
	}

	@Override
	public List<OBSupplier> selectSupplierNum() {
		return oBSupplierMapper.selectSupplierNum();
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		oBSupplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(OBSupplier record) {
		return oBSupplierMapper.insertSelective(record);
	}

	@Override
	public OBSupplier selectByPrimaryKey(String id) {
		return oBSupplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OBSupplier record) {
		return oBSupplierMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int yzSupplierName(String supplierId, String smallPointsId,String id) {
		return oBSupplierMapper.yzSupplierName(supplierId, smallPointsId,id);
	}

	@Override
	public int yzShangchuan(String id) {
		return oBSupplierMapper.yzShangchuan(id);
	}

	@Override
	public List<UploadFile> findBybusinessId(String businessId, Integer key) {
		String tableName = Constant.fileSystem.get(key);
		return fileDao.findBybusinessId(businessId, tableName);
	}

	@Override
	public List<OBSupplier> selOfferSupplier(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)map.get("page"),
				Integer.parseInt(config.getString("pageSize")));
		List<OBSupplier> list = oBSupplierMapper.selOfferSupplier(map);
		return list;
	}

	@Override
	public Integer yzzsCode(String certCode,String id) {
		return oBSupplierMapper.yzzsCode(certCode,id);
	}
    /***
     * 实现竞价供应商导出数据
     */
	@Override
	public boolean exportSupplier(String start, String end, Date synchDate) {
		// TODO Auto-generated method stub
		boolean boo=false;
		int sum=0;
		List<OBSupplier> createlist= oBSupplierMapper.selectByCreateDate(start, end);
		List<OBSupplier> updatelist= oBSupplierMapper.selectByUpdateDate(start, end);
		if(createlist!=null&& createlist.size()>0){
			sum=sum+createlist.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_SUPPLIER_FILENAME, 5),JSON.toJSONString(createlist));
		}
		if(updatelist!=null&&updatelist.size()>0){
			sum=sum+updatelist.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.M_OB_SUPPLIER_FILENAME, 5),JSON.toJSONString(updatelist));
			
		}
		//供应商数据只需导出数据即可 不用导出 附件 只是内网导出数据
		SynchRecordService.synchBidding(synchDate, sum+"", synchro.util.Constant.DATE_SYNCH_BIDDING_SUPPLIER, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.OB_SUPPLIER_COMMIT);
		boo=true;
		return boo;
	}
}
