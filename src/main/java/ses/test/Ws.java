package ses.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ses.dao.bms.TodosMapper;
import ses.dao.sms.SupplierAgentsMapper;
import ses.model.bms.Todos;
import ses.service.bms.StationMessageService;
import ses.service.sms.SupplierAgentsService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


public class Ws extends BaseTest {
	@Autowired
	private SupplierAgentsMapper supplierAgentsMapper;
	
	@Autowired
	private SupplierAgentsService supplierAgentsSercice;
	
	@Autowired
	private StationMessageService stationMessageService;
	
	/**
	 * 代办事项
	 */
	@Autowired
	private TodosMapper mapper;
	
	@Test
	public void test1(){
		Todos record=new Todos();
		record.setName("s");
		mapper.insert(record);
	}
}
