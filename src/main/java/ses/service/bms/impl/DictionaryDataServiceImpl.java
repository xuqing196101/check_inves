package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.DictionaryDataMapper;
import ses.model.bms.DictionaryData;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropertiesUtil;

@Service("dictionaryDataService")
public class DictionaryDataServiceImpl implements DictionaryDataServiceI {

	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;

    @Override
    public List<DictionaryData> find(DictionaryData dd) {
        List<DictionaryData> dds = dictionaryDataMapper.findList(dd);
        return dds;
    }

    @Override
    public void delete(String id) {
        dictionaryDataMapper.delete(id);
    }

    @Override
    public void save(DictionaryData dd) {
        dictionaryDataMapper.insert(dd);
    }

    @Override
    public void update(DictionaryData dd) {
        dictionaryDataMapper.update(dd);
    }

    @Override
    public List<DictionaryData> listByPage(DictionaryData dd, int page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
        List<DictionaryData> dds = dictionaryDataMapper.findList(dd);
        return dds;
    }

    @Override
    public List<DictionaryData> findRepeat(DictionaryData dd) {
        return dictionaryDataMapper.findRepeat(dd);
    }

	@Override
	public List<DictionaryData> queryAudit(DictionaryData dd) {
		// TODO Auto-generated method stub
		return dictionaryDataMapper.queryAudit(dd);
	}

}
