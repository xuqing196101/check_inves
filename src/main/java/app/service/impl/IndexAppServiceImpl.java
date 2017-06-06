package app.service.impl;

import iss.model.ps.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.app.AppSupplierBlackListMapper;
import app.dao.app.AppSupplierMapper;
import app.dao.app.IndexAppMapper;
import app.model.AppBlackList;
import app.model.AppSupplier;
import app.service.IndexAppService;

@Service("indexAppService")
public class IndexAppServiceImpl implements IndexAppService{
	
	//分页条数
	private static final Integer PAGESIZE = 10;
	
	//首页通知
    @Autowired
    private IndexAppMapper indexAppMapper;
    
    //供应商
    @Autowired
    private AppSupplierMapper appSupplierMapper;
    
    //供应商黑名单
    @Autowired
    private AppSupplierBlackListMapper appSupplierBlackListMapper;
    
	/**
	 * 分页查询公告列表
	 */
	@Override
	public List<Article> selectAppArticleListByTypeId(String articleTypeId,Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("articleTypeId", articleTypeId);
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return indexAppMapper.selectAppArticleListByTypeId(map);
	}

	/**
	 * 分页查询供应商名录
	 */
	@Override
	public List<AppSupplier> selectAppSupplierList(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return appSupplierMapper.selectAppSupplierList(map);
	}

	/**
	 * 分页查询供应商黑名单
	 */
	@Override
	public List<AppBlackList> findAppSupplierBlacklist(Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return appSupplierBlackListMapper.findAppSupplierBlacklist(map);
	}

	/**
	 * 分页查询专家名录
	 */
	@Override
	public List<AppSupplier> selectAppExpertList(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return appSupplierMapper.selectAppExpertList(map);
	}

	/**
	 * 专家黑名单
	 */
	@Override
	public List<AppBlackList> findAppExpertBlacklist(Integer page) {
		Map<String, Object> map = new HashMap<>();
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return appSupplierBlackListMapper.findAppExpertBlacklist(map);
	}

	/**
	 * 法规
	 */
	@Override
	public List<Article> selectAppRegulations(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return indexAppMapper.selectAppRegulations(map);
	}

	/**
	 * 处罚公告
	 */
	@Override
	public List<Article> selectAppPunishment(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return indexAppMapper.selectAppPunishment(map);
	}

	/**
	 * 搜索
	 */
	@Override
	public List<Article> search(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		map.put("start", (page-1)*PAGESIZE+1);
		map.put("end", page*PAGESIZE);
		return indexAppMapper.search(map);
	}

}
