package ses.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ses.dao.sms.SupplierAgentsMapper;
import ses.model.bms.StationMessage;
import ses.model.sms.SupplierAgents;
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
	@Test
	public void test1(){
		
		List<StationMessage> list=	stationMessageService.listStationMessage(new StationMessage());
//		list.size();
//		PageHelper.startPage(0, 0);
//		List<SupplierAgents> Sa =supplierAgentsMapper.selectAgents(new SupplierAgents(new Short("1")));
		
		PageInfo<SupplierAgents> pageinfo=new PageInfo<SupplierAgents>();
//		String a=Sa.toString();
//		List<SupplierAgents> list=JSON.parseArray(a,SupplierAgents.class);
		System.out.println("总条数："+pageinfo.getTotal());
		System.out.println("总条数："+pageinfo.getPageNum());
		System.out.println("总条数："+pageinfo.getPageSize());
		System.out.println("总条数："+pageinfo.getStartRow());
		System.out.println("总条数："+pageinfo.getEndRow());
		System.out.println("总条数："+pageinfo.getStartRow());
		System.out.println("总条数："+pageinfo.getStartRow());
		System.out.println("总条数："+pageinfo.getStartRow());
	}
}
