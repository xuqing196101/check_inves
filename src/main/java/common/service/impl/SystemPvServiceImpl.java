package common.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import common.dao.SystemPVMapper;
import common.model.SystemPV;
import common.service.SystemPvService;
import common.utils.DateUtils;
import common.utils.JedisUtils;
/**
 * 
 * Description:计数系统接口实现类
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
@Service
public class SystemPvServiceImpl implements SystemPvService {

	// 日志记录
	Logger logger = LoggerFactory.getLogger(SystemPvServiceImpl.class);
	
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	// 注入PV Mapper
	@Autowired
	private SystemPVMapper systemPVMapper;
	
	/**
	 * 
	 * Description:查询总访问数
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	@Override
	public BigDecimal selectPvTotalCount() {
		return systemPVMapper.selectPvTotalCount();
	}

	/**
	 * 
	 * Description:处理访问量统计同步
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 */
	@Override
	public void handlePvSync() {
		// 获取当前日期
		Date subDayDate = DateUtils.subDayDate(new Date(), 1);
		String dateOfFormat = DateUtils.getDateOfFormat(subDayDate);
		// 从缓存中获取数据
		Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedisByFactory(jedisConnectionFactory);
			String dayNum = jedis.get(dateOfFormat);
			if(dayNum != null){
				// 先查询数据库是否有此记录
				SystemPV pv = systemPVMapper.selectByPrimaryKey(Integer.parseInt(dateOfFormat));
				if(pv != null){
					// 更新数据
					pv.setDayNum(new BigDecimal(dayNum));
					systemPVMapper.updateByPrimaryKeySelective(pv);
				}else{
					// 新插入数据
					pv = new SystemPV();
					pv.setId(Integer.parseInt(dateOfFormat));
					pv.setDayNum(new BigDecimal(dayNum));
					systemPVMapper.insertSelective(pv);
				}
			}
		} catch (Exception e) {
			logger.info("redis连接异常...");
		}
	}

	/**
	 * 
	 * Description:查询是否存在记录
	 * 
	 * @author Easong
	 * @version 2017年6月13日
	 * @return
	 */
	@Override
	public Integer selectCountById(Integer id) {
		return systemPVMapper.selectCountById(id);
	}

}
