package bss.service.sstps.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.BurningPowerMapper;
import bss.dao.sstps.ComCostDisMapper;
import bss.dao.sstps.ManufacturingCostMapper;
import bss.dao.sstps.PeriodCostMapper;
import bss.dao.sstps.WagesPayableMapper;
import bss.dao.sstps.YearPlanMapper;
import bss.model.sstps.BurningPower;
import bss.model.sstps.ComCostDis;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.ManufacturingCost;
import bss.model.sstps.PeriodCost;
import bss.model.sstps.WagesPayable;
import bss.model.sstps.YearPlan;
import bss.service.sstps.ComCostDisService;

@Service("/comCostDisService")
public class ComCostDisServiceImpl implements ComCostDisService {

	@Autowired
	private ComCostDisMapper comCostDisMapper;
	@Autowired
	private WagesPayableMapper wagesPayableMapper;
	@Autowired
	private YearPlanMapper yearPlanMapper;
	@Autowired
	private BurningPowerMapper burningPowerMapper;
	@Autowired
	private ManufacturingCostMapper manufacturingCostMapper;
	@Autowired
	private PeriodCostMapper periodCostMapper;
	@Override
	public List<ComCostDis> selectProduct(ComCostDis comCostDis) {
		return comCostDisMapper.selectProduct(comCostDis);
	}

	@Override
	public void insert(ComCostDis comCostDis) {
		comCostDisMapper.insert(comCostDis);
	}

	@Override
	public void update(ComCostDis comCostDis) {
		comCostDisMapper.update(comCostDis);
	}

	@Override
	public List<ComCostDis> selectProductIdAndName(
			HashMap<String, Object> hashMap) {
		return comCostDisMapper.selectProductIdAndName(hashMap);
	}

	@Override
	public void appendSumComCostDis(ContractProduct contractProduct) {
		ComCostDis costDis=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","制度总工时");
		List<ComCostDis> ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			map=new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "职工人数");
			List<WagesPayable> wps = wagesPayableMapper.selectProductIdName(map);
			if(wps!=null&&wps.size()>0){
				BigDecimal oyaProduceUser = wps.get(0).getOyaProduceUser();
				if(oyaProduceUser!=null){
					oyaProduceUser=new BigDecimal(oyaProduceUser+"").multiply(new BigDecimal("1568.64"));
				}else{
					oyaProduceUser=new BigDecimal(0);
				}
				BigDecimal newProduceUser = wps.get(0).getNewProduceUser();
				if(newProduceUser!=null){
					newProduceUser=new BigDecimal(newProduceUser+"").multiply(new BigDecimal("1568.64"));
				}else{
					newProduceUser=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				costDis.setId(ccds.get(0).getId());
				costDis.setNewActual(newProduceUser);
				costDis.setOyaActual(oyaProduceUser);
				comCostDisMapper.update(costDis);
			}
			
		}
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","计划任务总工时");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			List<YearPlan> listYearPlan = yearPlanMapper.selectByParentIdZero("0");
			if(listYearPlan!=null&&listYearPlan.size()>0){
				BigDecimal oyaProduceUser = listYearPlan.get(0).getOyaHourTotal();
				if(oyaProduceUser!=null){
					oyaProduceUser=new BigDecimal(oyaProduceUser+"");
				}else{
					oyaProduceUser=new BigDecimal(0);
				}
				BigDecimal newProduceUser = listYearPlan.get(0).getNewHourTotal();
				if(newProduceUser!=null){
					newProduceUser=new BigDecimal(newProduceUser+"");
				}else{
					newProduceUser=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				costDis.setId(ccds.get(0).getId());
				costDis.setNewActual(newProduceUser);
				costDis.setOyaActual(oyaProduceUser);
				comCostDisMapper.update(costDis);
			}
		}
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","燃料动力费");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","制度总工时");
		List<ComCostDis> ccdList = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			map=new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "燃料动力费");
			List<BurningPower> listBurningPower = burningPowerMapper.selectProductIdAndName(map);
			if(listBurningPower!=null&&listBurningPower.size()>0){
				BigDecimal tyaMoney = listBurningPower.get(0).getTyaMoney();
				if(tyaMoney!=null){
					tyaMoney=new BigDecimal(tyaMoney+"");
				}else{
					tyaMoney=new BigDecimal(0);
				}
				BigDecimal oyaMoney = listBurningPower.get(0).getOyaMoney();
				if(oyaMoney!=null){
					oyaMoney=new BigDecimal(oyaMoney+"");
				}else{
					oyaMoney=new BigDecimal(0);
				}
				BigDecimal newMoney = listBurningPower.get(0).getNewMoney();
				if(newMoney!=null){
					newMoney=new BigDecimal(newMoney+"");
				}else{
					newMoney=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				if(ccdList!=null&&ccdList.size()>0){
					BigDecimal oyaAct = ccdList.get(0).getOyaActual();
					if(oyaAct!=null){
						oyaAct=new BigDecimal(oyaAct+"");
						BigDecimal divide = oyaMoney.divide(oyaAct,2,BigDecimal.ROUND_HALF_UP);
						costDis.setOyaFee(divide);
					}else{
						costDis.setOyaFee(new BigDecimal(0));
					}
					BigDecimal newAct = ccdList.get(0).getNewActual();
					if(newAct!=null){
						newAct=new BigDecimal(newAct+"");
						costDis.setNewFee(newMoney.divide(newAct,2,BigDecimal.ROUND_HALF_UP));
					}else{
						costDis.setNewFee(new BigDecimal(0));
					}
				}
				costDis.setId(ccds.get(0).getId());
				costDis.setTyaAmount(tyaMoney);
				costDis.setNewAmount(newMoney);
				costDis.setOyaAmout(oyaMoney);
				comCostDisMapper.update(costDis);
			}
		}

		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","直接人工费");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			List<WagesPayable> wps = wagesPayableMapper.selectProductIdSumUser("0");
			if(wps!=null&&wps.size()>0){
				BigDecimal oyaProduceUser = wps.get(0).getOyaProduceUser();
				if(oyaProduceUser!=null){
					oyaProduceUser=new BigDecimal(oyaProduceUser+"");
				}else{
					oyaProduceUser=new BigDecimal(0);
				}
				BigDecimal newProduceUser = wps.get(0).getNewProduceUser();
				if(newProduceUser!=null){
					newProduceUser=new BigDecimal(newProduceUser+"");
				}else{
					newProduceUser=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				if(ccdList!=null&&ccdList.size()>0){
					BigDecimal oyaAct = ccdList.get(0).getOyaActual();
					if(oyaAct!=null){
						oyaAct=new BigDecimal(oyaAct+"");
						BigDecimal divide = oyaProduceUser.divide(oyaAct,2,BigDecimal.ROUND_HALF_UP);
						costDis.setOyaFee(divide);
					}else{
						costDis.setOyaFee(new BigDecimal(0));
					}
					BigDecimal newAct = ccdList.get(0).getNewActual();
					if(newAct!=null){
						newAct=new BigDecimal(newAct+"");
						costDis.setNewFee(newProduceUser.divide(newAct,2,BigDecimal.ROUND_HALF_UP));
					}else{
						costDis.setNewFee(new BigDecimal(0));
					}
				}
				costDis.setId(ccds.get(0).getId());
				costDis.setNewAmount(newProduceUser);
				costDis.setOyaAmout(oyaProduceUser);
				comCostDisMapper.update(costDis);
			}
		}
		
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","制造费用");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			List<ManufacturingCost> mc = manufacturingCostMapper.selectProductIdSum(contractProduct.getId());
			if(mc!=null&&mc.size()>0){
				BigDecimal tyaQuoteprice = mc.get(0).getTyaQuoteprice();
				if(tyaQuoteprice!=null){
					tyaQuoteprice=new BigDecimal(tyaQuoteprice+"");
				}else{
					tyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal oyaQuoteprice = mc.get(0).getOyaQuoteprice();
				if(oyaQuoteprice!=null){
					oyaQuoteprice=new BigDecimal(oyaQuoteprice+"");
				}else{
					oyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal newQuoteprice = mc.get(0).getNewQuoteprice();
				if(newQuoteprice!=null){
					newQuoteprice=new BigDecimal(newQuoteprice+"");
				}else{
					newQuoteprice=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				if(ccdList!=null&&ccdList.size()>0){
					BigDecimal oyaAct = ccdList.get(0).getOyaActual();
					if(oyaAct!=null){
						oyaAct=new BigDecimal(oyaAct+"");
						BigDecimal divide = oyaQuoteprice.divide(oyaAct,2,BigDecimal.ROUND_HALF_UP);
						costDis.setOyaFee(divide);
					}else{
						costDis.setOyaFee(new BigDecimal(0));
					}
					BigDecimal newAct = ccdList.get(0).getNewActual();
					if(newAct!=null){
						newAct=new BigDecimal(newAct+"");
						costDis.setNewFee(newQuoteprice.divide(newAct,2,BigDecimal.ROUND_HALF_UP));
					}else{
						costDis.setNewFee(new BigDecimal(0));
					}
				}
				costDis.setId(ccds.get(0).getId());
				costDis.setTyaAmount(tyaQuoteprice);
				costDis.setNewAmount(newQuoteprice);
				costDis.setOyaAmout(oyaQuoteprice);
				comCostDisMapper.update(costDis);
			}
			
		}
		
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","管理费用");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			map=new HashMap<String, Object>();
			map.put("id",contractProduct.getId());
			map.put("name","管理费用合计");
			List<PeriodCost> pclist = periodCostMapper.selectProductIdAndName(map);
			if(pclist!=null&&pclist.size()>0){
				BigDecimal tyaQuoteprice = pclist.get(0).getTyaQuoteprice();
				if(tyaQuoteprice!=null){
					tyaQuoteprice=new BigDecimal(tyaQuoteprice+"");
				}else{
					tyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal oyaQuoteprice = pclist.get(0).getOyaQuoteprice();
				if(oyaQuoteprice!=null){
					oyaQuoteprice=new BigDecimal(oyaQuoteprice+"");
				}else{
					oyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal newQuoteprice = pclist.get(0).getNewQuoteprice();
				if(newQuoteprice!=null){
					newQuoteprice=new BigDecimal(newQuoteprice+"");
				}else{
					newQuoteprice=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				if(ccdList!=null&&ccdList.size()>0){
					BigDecimal oyaAct = ccdList.get(0).getOyaActual();
					if(oyaAct!=null){
						oyaAct=new BigDecimal(oyaAct+"");
						BigDecimal divide = oyaQuoteprice.divide(oyaAct,2,BigDecimal.ROUND_HALF_UP);
						costDis.setOyaFee(divide);
					}else{
						costDis.setOyaFee(new BigDecimal(0));
					}
					BigDecimal newAct = ccdList.get(0).getNewActual();
					if(newAct!=null){
						newAct=new BigDecimal(newAct+"");
						costDis.setNewFee(newQuoteprice.divide(newAct,2,BigDecimal.ROUND_HALF_UP));
					}else{
						costDis.setNewFee(new BigDecimal(0));
					}
				}
				costDis.setId(ccds.get(0).getId());
				costDis.setTyaAmount(tyaQuoteprice);
				costDis.setNewAmount(newQuoteprice);
				costDis.setOyaAmout(oyaQuoteprice);
				comCostDisMapper.update(costDis);
			}
		}
		map=new HashMap<String, Object>();
		map.put("id",contractProduct.getId());
		map.put("name","财务费用");
		ccds = comCostDisMapper.selectProductIdAndName(map);
		if(ccds!=null&&ccds.size()>0){
			map=new HashMap<String, Object>();
			map.put("id",contractProduct.getId());
			map.put("name","财务费用合计");
			List<PeriodCost> pclist = periodCostMapper.selectProductIdAndName(map);
			if(pclist!=null&&pclist.size()>0){
				BigDecimal tyaQuoteprice = pclist.get(0).getTyaQuoteprice();
				if(tyaQuoteprice!=null){
					tyaQuoteprice=new BigDecimal(tyaQuoteprice+"");
				}else{
					tyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal oyaQuoteprice = pclist.get(0).getOyaQuoteprice();
				if(oyaQuoteprice!=null){
					oyaQuoteprice=new BigDecimal(oyaQuoteprice+"");
				}else{
					oyaQuoteprice=new BigDecimal(0);
				}
				BigDecimal newQuoteprice = pclist.get(0).getNewQuoteprice();
				if(newQuoteprice!=null){
					newQuoteprice=new BigDecimal(newQuoteprice+"");
				}else{
					newQuoteprice=new BigDecimal(0);
				}
				costDis=new ComCostDis();
				if(ccdList!=null&&ccdList.size()>0){
					BigDecimal oyaAct = ccdList.get(0).getOyaActual();
					if(oyaAct!=null){
						oyaAct=new BigDecimal(oyaAct+"");
						BigDecimal divide = oyaQuoteprice.divide(oyaAct,2,BigDecimal.ROUND_HALF_UP);
						costDis.setOyaFee(divide);
					}else{
						costDis.setOyaFee(new BigDecimal(0));
					}
					BigDecimal newAct = ccdList.get(0).getNewActual();
					if(newAct!=null){
						newAct=new BigDecimal(newAct+"");
						costDis.setNewFee(newQuoteprice.divide(newAct,2,BigDecimal.ROUND_HALF_UP));
					}else{
						costDis.setNewFee(new BigDecimal(0));
					}
				}
				costDis.setId(ccds.get(0).getId());
				costDis.setTyaAmount(tyaQuoteprice);
				costDis.setNewAmount(newQuoteprice);
				costDis.setOyaAmout(oyaQuoteprice);
				comCostDisMapper.update(costDis);
			}
			
			
			
		}
		
	}

}
