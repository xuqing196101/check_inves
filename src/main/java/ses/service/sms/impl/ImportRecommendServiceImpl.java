package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ImportRecommendMapper;
import ses.model.sms.ImportRecommend;
import ses.service.sms.ImportRecommendService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
@Service
public class ImportRecommendServiceImpl implements ImportRecommendService {

	@Autowired
	private ImportRecommendMapper irMapper;	
	
	@Override
	public void register(ImportRecommend ir) {
		irMapper.insertSelective(ir);
	}

	@Override
	public void update(ImportRecommend ir) {
		irMapper.updateByPrimaryKeySelective(ir);
	}

	@Override
	public ImportRecommend findById(String id) {
		ImportRecommend ir=irMapper.selectByPrimaryKey(id);
		return ir;
	}

	@Override
	public List<ImportRecommend> selectByRecommend(ImportRecommend ir, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<ImportRecommend> irList=irMapper.selectByRecommend(ir);
		return irList;
	}
}
