package bss.service.ob.impl;

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

import common.dao.FileUploadMapper;
import bss.dao.ob.OBProductMapper;
import bss.model.ob.OBProduct;
import bss.service.ob.OBProductService;

@Service("oBProductService")
public class OBProductServiceImpl implements OBProductService {

	@Autowired
	private OBProductMapper oBProductMapper;
	
	@Autowired
	private FileUploadMapper fileUploadMapper;
	  /** 记录service  **/
    @Autowired
    private SynchRecordService  SynchRecordService;

	@Override
	public List<OBProduct> selectByExample(OBProduct example,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<OBProduct> list = oBProductMapper.selectByExample(example);
		return list;
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		oBProductMapper.deleteByPrimaryKey(id);
		fileUploadMapper.deleteByBusinessId(id);
		
	}

	@Override
	public OBProduct selectByPrimaryKey(String id) {
		return oBProductMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertSelective(OBProduct record) {
		return oBProductMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(OBProduct record) {
		return oBProductMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int yzProductCode(String code,String id) {
		return oBProductMapper.yzProductCode(code,id);
	}

	@Override
	public int yzProductName(String name,String id) {
		return oBProductMapper.yzProductName(name,id);
	}

	@Override
	public int yzorg(String shortName) {
		return oBProductMapper.yzorg(shortName);
	}

	@Override
	public void fab(String id) {
		oBProductMapper.fab(id);
	}
	
	@Override
	public void chfab(String id) {
		oBProductMapper.chfab(id);
	}

	@Override
	public List<OBProduct> selectAllAmallPointsId(String name) {
		return oBProductMapper.selectAllAmallPointsId(name);
	}

	@Override
	public String selOrgByCategory(String smallPointsId,String id) {
		return oBProductMapper.selOrgByCategory(smallPointsId,id);
	}
    /**
     * 实现导出竞价定型产品数据
     */
	@Override
	public boolean exportProduct(String start, String end, Date synchDate) {
		// TODO Auto-generated method stub
		boolean boo=false;
		//获取创建的产品
		List<OBProduct> prodcutCList= oBProductMapper.selectByCreateDate(start, end);
		//获取修改的产品
		List<OBProduct> prodcutMList=oBProductMapper.selectByUpdateDate(start, end);
		int sum=0;
		if(prodcutCList!=null && prodcutCList.size()>0){
			sum=sum+prodcutCList.size();
			//生成json 并保存
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_OB_PRODUCT_FILENAME, 5),JSON.toJSONString(prodcutCList));
		}
		if(prodcutMList!=null && prodcutMList.size()>0){
			sum=sum+prodcutMList.size();
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.M_OB_PRODUCT_FILENAME, 5),JSON.toJSONString(prodcutMList));
		}
		SynchRecordService.synchBidding(synchDate, sum+"", Constant.DATE_SYNCH_BIDDING_PRODUCT, Constant.OPER_TYPE_EXPORT, Constant.PRODUCT_COMMIT);
		boo=true;
		return boo;
	}

}
