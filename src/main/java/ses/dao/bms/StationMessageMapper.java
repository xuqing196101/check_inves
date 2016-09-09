package ses.dao.bms;

import java.util.List;

import ses.model.bms.StationMessage;


public interface StationMessageMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(StationMessage record);

    /**
     *
     * @param record
     */
    int insertSelective(StationMessage record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    StationMessage selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(StationMessage record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(StationMessage record);
    
	/**
	 * @Description:分页获取集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:51  
	 * @param @param stationMessage
	 * @param @return      
	 * @return List<StationMessage>
	 */
	public List<StationMessage> listStationMessage(StationMessage stationMessage);
}