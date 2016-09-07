package yggc.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import yggc.dao.sms.SupplierAgentsMapper;
import yggc.model.sms.SupplierAgents;
import yggc.service.sms.SupplierAgentsService;

public class Ws extends BaseTest {
	@Autowired
	private SupplierAgentsMapper supplierAgentsMapper;
	
	@Autowired
	private SupplierAgentsService supplierAgentsSercice;
	@Test
	public void test1(){
//		PageHelper.startPage(0, 0);
//		List<Map> Sa =supplierAgentsMapper.selectAgents();
		List<SupplierAgents> ll=new ArrayList<SupplierAgents>();
		
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
