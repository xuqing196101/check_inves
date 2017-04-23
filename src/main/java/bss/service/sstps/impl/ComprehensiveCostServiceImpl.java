package bss.service.sstps.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.AccessoriesConMapper;
import bss.dao.sstps.ComCostDisMapper;
import bss.dao.sstps.ComprehensiveCostMapper;
import bss.dao.sstps.OutproductConMapper;
import bss.dao.sstps.OutsourcingConMapper;
import bss.dao.sstps.ProductQuotaMapper;
import bss.dao.sstps.SpecialCostMapper;
import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.ComCostDis;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutproductCon;
import bss.model.sstps.OutsourcingCon;
import bss.model.sstps.ProductQuota;
import bss.model.sstps.SpecialCost;
import bss.service.sstps.ComprehensiveCostService;

@Service("/comprehensiveCostService")
public class ComprehensiveCostServiceImpl implements ComprehensiveCostService {

	@Autowired
	private ComprehensiveCostMapper comprehensiveCostMapper;
	@Autowired
	private ComCostDisMapper comCostDisMapper;

	@Autowired
	private ProductQuotaMapper productQuotaMapper;
	@Autowired
	private AccessoriesConMapper accessoriesConMapper;
	@Autowired
	private OutproductConMapper outproductConMapper;
	@Autowired
	private OutsourcingConMapper outsourcingConMapper;
	@Autowired
	private SpecialCostMapper specialCostMapper;

	@Override
	public void insert(ComprehensiveCost comprehensiveCost) {
		comprehensiveCostMapper.insert(comprehensiveCost);
	}

	@Override
	public void update(ComprehensiveCost comprehensiveCost) {
		comprehensiveCostMapper.update(comprehensiveCost);
	}

	@Override
	public List<ComprehensiveCost> select(ComprehensiveCost comprehensiveCost) {
		return comprehensiveCostMapper.selectProduct(comprehensiveCost);
	}

	@Override
	public void updateInfo(ComprehensiveCost comprehensiveCost) {
		comprehensiveCostMapper.updateInfo(comprehensiveCost);
	}

	@Override
	public void appendSumComprehensiveCost(ContractProduct contractProduct) {
		ComprehensiveCost comprehensiveCost = null;
		BigDecimal subtotalOffer = new BigDecimal(0);// 本产品定额工时
		BigDecimal oyaFeeRg = new BigDecimal(0);// 工时及分配率、直接人工
		BigDecimal oyaFeeRl = new BigDecimal(0);// 工时及分配率、燃料动力
		BigDecimal oyaFeeZz = new BigDecimal(0);// 工时及分配率、制造费用
		BigDecimal oyaFeeQj = new BigDecimal(0);// 工时及分配率、期间费用
		BigDecimal oyaFeeGsHj = new BigDecimal(0);// 工时及分配率、工时分配率合计
		BigDecimal oyaFeeYf = new BigDecimal(0);// 生产成本、原辅材料
		BigDecimal oyaFeeWg = new BigDecimal(0);// 生产成本、外购成件
		BigDecimal oyaFeeWx = new BigDecimal(0);// 生产成本、外协成件
		BigDecimal oyaFeeScRl = new BigDecimal(0);// 生产成本、燃料动力
		BigDecimal oyaFeeScRg = new BigDecimal(0);// 生产成本、直接人工
		BigDecimal oyaFeeScZy = new BigDecimal(0);// 生产成本、专用费用
		BigDecimal oyaFeeScZz = new BigDecimal(0);// 生产成本、制造费用
		BigDecimal oyaFeeScXj = new BigDecimal(0);// 生产成本、小计
		BigDecimal oyaFeeQjGl = new BigDecimal(0);// 期间费用、管理费用
		BigDecimal oyaFeeQjCw = new BigDecimal(0);// 期间费用、财务费用
		BigDecimal oyaFeeQjXs = new BigDecimal(0);// 期间费用、销售费用
		BigDecimal oyaFeeQjXj = new BigDecimal(0);// 期间费用、小计
		BigDecimal oyaFeeJGCb = new BigDecimal(0);// 价格方案、成本
		BigDecimal oyaFeeJGLr = new BigDecimal(0);// 价格方案、利润
		BigDecimal oyaFeeJGSj = new BigDecimal(0);// 价格方案、税金
		BigDecimal oyaFeeJGJg = new BigDecimal(0);// 价格方案、价格
		// 工时及分配率、本产品定额工时
		subtotalOffer = subtotalOffer(contractProduct, subtotalOffer);
		// 工时及分配率、直接人工
		oyaFeeRg = oyaFeeRg(contractProduct, oyaFeeRg);
		// 工时及分配率、燃料动力
		oyaFeeRl = oyaFeeRl(contractProduct, oyaFeeRl);
		// 工时及分配率、制造费用
		oyaFeeZz = oyaFeeZz(contractProduct, oyaFeeZz);
		// 工时及分配率、期间费用
		oyaFeeQj = oyaFeeQj(contractProduct, oyaFeeQj);
		// 工时及分配率、期间费用
		oyaFeeGsHj(contractProduct, oyaFeeRg, oyaFeeRl, oyaFeeZz, oyaFeeQj);
		// 生产成本、原辅材料
		oyaFeeYf = oyaFeeYf(contractProduct, oyaFeeYf);
		// 生产成本、外购成件
		oyaFeeWg = oyaFeeWg(contractProduct, oyaFeeWg);
		// 生产成本、外协部件
		oyaFeeWx = oyaFeeWx(contractProduct, oyaFeeWx);
		// 生产成本、燃料动力
		oyaFeeScRl = oyaFeeScRl(contractProduct, subtotalOffer, oyaFeeRl,
				oyaFeeScRl);
		// 生产成本、直接人工
		oyaFeeScRg = oyaFeeScRg(contractProduct, subtotalOffer, oyaFeeRg,
				oyaFeeScRg);
		// 生产成本、专用费用
		oyaFeeScZy = oyaFeeScZy(contractProduct, oyaFeeScZy);
		// 生产成本、专用费用
		oyaFeeScZz = oyaFeeScZz(contractProduct, subtotalOffer, oyaFeeZz,
				oyaFeeScZz);
		// 生产成本、小计
		oyaFeeScXj = oyaFeeScXj(contractProduct, oyaFeeYf, oyaFeeWg, oyaFeeWx,
				oyaFeeScRl, oyaFeeScRg, oyaFeeScZy, oyaFeeScZz, oyaFeeScXj);
		// 期间费用、管理费用
		oyaFeeQjGl = oyaFeeQjGl(contractProduct, subtotalOffer, oyaFeeQjGl);
		// 期间费用、财务费用
		oyaFeeQjCw = oyaFeeQjCw(contractProduct, subtotalOffer, oyaFeeQjCw);
		// 期间费用、销售费用
		oyaFeeQjXs = oyaFeeQjXs(contractProduct, oyaFeeQjXs);
		// 期间费用、小计
		oyaFeeQjXj = oyaFeeQjXj(contractProduct, oyaFeeQjGl, oyaFeeQjCw,
				oyaFeeQjXs, oyaFeeQjXj);
		// 价格方案、成本
		oyaFeeJGCb=oyaFeeJGCb(contractProduct, oyaFeeScXj, oyaFeeQjXj,oyaFeeJGCb);
		// 价格方案、利润
		oyaFeeJGLr = oyaFeeJGLr(contractProduct, oyaFeeJGCb, oyaFeeJGLr);
		// 价格方案、利润
		oyaFeeJGSj = oyaFeeJGSj(contractProduct, oyaFeeJGSj);
		// 价格方案、价格
		oyaFeeJGJg(contractProduct, oyaFeeJGCb, oyaFeeJGLr, oyaFeeJGSj);

	}

	private void oyaFeeJGJg(ContractProduct contractProduct,
			BigDecimal oyaFeeJGCb, BigDecimal oyaFeeJGLr, BigDecimal oyaFeeJGSj) {
		BigDecimal oyaFeeJGJg;
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "价格");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeJGJg=oyaFeeJGCb.add(oyaFeeJGLr).add(oyaFeeJGSj);
			updateComprehensiveCost(oyaFeeJGJg, cc);
		}
	}

	private BigDecimal oyaFeeJGSj(ContractProduct contractProduct,
			BigDecimal oyaFeeJGSj) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "税金");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeJGSj=cc.get(0).getSingleOffer();
			if(oyaFeeJGSj!=null){
				oyaFeeJGSj=new BigDecimal(oyaFeeJGSj+"");
			}else{
				oyaFeeJGSj=new BigDecimal(0);
			}
			updateComprehensiveCost(oyaFeeJGSj, cc);
		}
		return oyaFeeJGSj;
	}

	private BigDecimal oyaFeeJGLr(ContractProduct contractProduct,
			BigDecimal oyaFeeJGCb, BigDecimal oyaFeeJGLr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "利润");
		map.put("parentName", "价格方案");
		List<ComprehensiveCost> cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeJGLr=oyaFeeJGCb.multiply(new BigDecimal(0.05+""));
			updateComprehensiveCost(oyaFeeJGLr, cc);
		}
		return oyaFeeJGLr;
	}

	private BigDecimal subtotalOffer(ContractProduct contractProduct,
			BigDecimal subtotalOffer) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "本产品定额工时");
		map.put("parentName", "工时及分配率");
		List<ComprehensiveCost> cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<ProductQuota> pq = productQuotaMapper
					.selectProductIdSum(contractProduct.getId());
			if (pq != null && pq.size() > 0) {
				subtotalOffer = pq.get(0).getSubtotalOffer();
				if (subtotalOffer != null) {
					subtotalOffer = new BigDecimal(subtotalOffer + "");
				} else {
					subtotalOffer = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(subtotalOffer, cc);
		}
		return subtotalOffer;
	}

	private BigDecimal oyaFeeRg(ContractProduct contractProduct,
			BigDecimal oyaFeeRg) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "直接人工");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "直接人工费");
			List<ComCostDis> ccd = comCostDisMapper.selectProductIdAndName(map);
			if (ccd != null && ccd.size() > 0) {
				oyaFeeRg = ccd.get(0).getOyaFee();
				if (oyaFeeRg != null) {
					oyaFeeRg = new BigDecimal(oyaFeeRg + "");
				} else {
					oyaFeeRg = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeRg, cc);
		}
		return oyaFeeRg;
	}

	private BigDecimal oyaFeeRl(ContractProduct contractProduct,
			BigDecimal oyaFeeRl) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "燃料动力");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "燃料动力费");
			List<ComCostDis> ccd = comCostDisMapper.selectProductIdAndName(map);
			if (ccd != null && ccd.size() > 0) {
				oyaFeeRl = ccd.get(0).getOyaFee();
				if (oyaFeeRl != null) {
					oyaFeeRl = new BigDecimal(oyaFeeRl + "");
				} else {
					oyaFeeRl = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeRl, cc);
		}
		return oyaFeeRl;
	}

	private BigDecimal oyaFeeZz(ContractProduct contractProduct,
			BigDecimal oyaFeeZz) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "制造费用");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "制造费用");
			List<ComCostDis> ccd = comCostDisMapper.selectProductIdAndName(map);
			if (ccd != null && ccd.size() > 0) {
				oyaFeeZz = ccd.get(0).getOyaFee();
				if (oyaFeeZz != null) {
					oyaFeeZz = new BigDecimal(oyaFeeZz + "");
				} else {
					oyaFeeZz = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeZz, cc);
		}
		return oyaFeeZz;
	}

	private BigDecimal oyaFeeQj(ContractProduct contractProduct,
			BigDecimal oyaFeeQj) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "期间费用");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "财务费用");
			List<ComCostDis> ccd = comCostDisMapper.selectProductIdAndName(map);
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "管理费用");
			List<ComCostDis> ccdGl = comCostDisMapper
					.selectProductIdAndName(map);
			BigDecimal oyaFeeGl = new BigDecimal(0);
			BigDecimal oyaFeeCw = new BigDecimal(0);
			if (ccdGl != null && ccdGl.size() > 0) {
				oyaFeeGl = ccdGl.get(0).getOyaFee();
				if (oyaFeeGl != null) {
					oyaFeeGl = new BigDecimal(oyaFeeGl + "");
				} else {
					oyaFeeGl = new BigDecimal(0);
				}
			}
			if (ccd != null && ccd.size() > 0) {
				oyaFeeCw = ccd.get(0).getOyaFee();
				if (oyaFeeCw != null) {
					oyaFeeCw = new BigDecimal(oyaFeeCw + "");
				} else {
					oyaFeeCw = new BigDecimal(0);
				}
			}
			oyaFeeQj = oyaFeeCw.add(oyaFeeGl);
			updateComprehensiveCost(oyaFeeQj, cc);
		}
		return oyaFeeQj;
	}

	private void oyaFeeGsHj(ContractProduct contractProduct,
			BigDecimal oyaFeeRg, BigDecimal oyaFeeRl, BigDecimal oyaFeeZz,
			BigDecimal oyaFeeQj) {
		BigDecimal oyaFeeGsHj;
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "工时分配率合计");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeGsHj = oyaFeeRg.add(oyaFeeRl).add(oyaFeeZz).add(oyaFeeQj);
			updateComprehensiveCost(oyaFeeGsHj, cc);
		}
	}

	private BigDecimal oyaFeeYf(ContractProduct contractProduct,
			BigDecimal oyaFeeYf) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "原辅材料");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<AccessoriesCon> ac = accessoriesConMapper
					.selectProductIdAndParentId(contractProduct.getId());
			if (ac != null && ac.size() > 0) {
				oyaFeeYf = ac.get(0).getWorkMoney();
				if (oyaFeeYf != null) {
					oyaFeeYf = new BigDecimal(oyaFeeYf + "");
				} else {
					oyaFeeYf = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeYf, cc);

		}
		return oyaFeeYf;
	}

	private BigDecimal oyaFeeWg(ContractProduct contractProduct,
			BigDecimal oyaFeeWg) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "外购成件");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<OutproductCon> oc = outproductConMapper
					.selectProductIdSum(contractProduct.getId());
			if (oc != null && oc.size() > 0) {
				oyaFeeWg = oc.get(0).getWorkMoney();
				if (oyaFeeWg != null) {
					oyaFeeWg = new BigDecimal(oyaFeeWg + "");
				} else {
					oyaFeeWg = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeWg, cc);
		}
		return oyaFeeWg;
	}

	private BigDecimal oyaFeeWx(ContractProduct contractProduct,
			BigDecimal oyaFeeWx) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "外协部件");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<OutsourcingCon> oc = outsourcingConMapper
					.selectProductIdSum(contractProduct.getId());
			if (oc != null && oc.size() > 0) {
				oyaFeeWx = oc.get(0).getWorkMoney();
				if (oyaFeeWx != null) {
					oyaFeeWx = new BigDecimal(oyaFeeWx + "");
				} else {
					oyaFeeWx = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeWx, cc);
		}
		return oyaFeeWx;
	}

	private BigDecimal oyaFeeScRl(ContractProduct contractProduct,
			BigDecimal subtotalOffer, BigDecimal oyaFeeRl, BigDecimal oyaFeeScRl) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "燃料动力");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeScRl = subtotalOffer.multiply(oyaFeeRl);
			updateComprehensiveCost(oyaFeeScRl, cc);
		}
		return oyaFeeScRl;
	}

	private BigDecimal oyaFeeScRg(ContractProduct contractProduct,
			BigDecimal subtotalOffer, BigDecimal oyaFeeRg, BigDecimal oyaFeeScRg) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "直接人工");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeScRg = subtotalOffer.multiply(oyaFeeRg);
			updateComprehensiveCost(oyaFeeScRg, cc);
		}
		return oyaFeeScRg;
	}

	private BigDecimal oyaFeeScZy(ContractProduct contractProduct,
			BigDecimal oyaFeeScZy) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "专用费用");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<SpecialCost> sc = specialCostMapper
					.selectByParentIdSum(contractProduct.getId());
			if (sc != null && sc.size() > 0) {
				oyaFeeScZy = sc.get(0).getProportionPrice();
				if (oyaFeeScZy != null) {
					oyaFeeScZy = new BigDecimal(oyaFeeScZy + "");
				} else {
					oyaFeeScZy = new BigDecimal(0);
				}
			}
			updateComprehensiveCost(oyaFeeScZy, cc);
		}
		return oyaFeeScZy;
	}

	private BigDecimal oyaFeeScZz(ContractProduct contractProduct,
			BigDecimal subtotalOffer, BigDecimal oyaFeeZz, BigDecimal oyaFeeScZz) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "制造费用");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeScZz = subtotalOffer.multiply(oyaFeeZz);
			updateComprehensiveCost(oyaFeeScZz, cc);
		}
		return oyaFeeScZz;
	}

	private BigDecimal oyaFeeScXj(ContractProduct contractProduct,
			BigDecimal oyaFeeYf, BigDecimal oyaFeeWg, BigDecimal oyaFeeWx,
			BigDecimal oyaFeeScRl, BigDecimal oyaFeeScRg,
			BigDecimal oyaFeeScZy, BigDecimal oyaFeeScZz, BigDecimal oyaFeeScXj) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "小计");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeScXj = oyaFeeYf.add(oyaFeeWg).add(oyaFeeWx).add(oyaFeeScRl)
					.add(oyaFeeScRg).add(oyaFeeScZy).add(oyaFeeScZz);
			updateComprehensiveCost(oyaFeeScXj, cc);
		}
		return oyaFeeScXj;
	}

	private BigDecimal oyaFeeQjGl(ContractProduct contractProduct,
			BigDecimal subtotalOffer, BigDecimal oyaFeeQjGl) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "管理费用");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "管理费用");
			List<ComCostDis> ccdGl = comCostDisMapper
					.selectProductIdAndName(map);
			BigDecimal oyaFeeGl = new BigDecimal(0);
			if (ccdGl != null && ccdGl.size() > 0) {
				oyaFeeGl = ccdGl.get(0).getOyaFee();
				if (oyaFeeGl != null) {
					oyaFeeGl = new BigDecimal(oyaFeeGl + "");
				} else {
					oyaFeeGl = new BigDecimal(0);
				}
			}
			oyaFeeQjGl=subtotalOffer.multiply(oyaFeeGl);
			updateComprehensiveCost(oyaFeeQjGl, cc);
			
		}
		return oyaFeeQjGl;
	}

	private BigDecimal oyaFeeQjCw(ContractProduct contractProduct,
			BigDecimal subtotalOffer, BigDecimal oyaFeeQjCw) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "财务费用");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("id", contractProduct.getId());
			map.put("name", "财务费用");
			List<ComCostDis> ccd = comCostDisMapper.selectProductIdAndName(map);
			BigDecimal oyaFeeCw = new BigDecimal(0);
			if (ccd != null && ccd.size() > 0) {
				oyaFeeCw = ccd.get(0).getOyaFee();
				if (oyaFeeCw != null) {
					oyaFeeCw = new BigDecimal(oyaFeeCw + "");
				} else {
					oyaFeeCw = new BigDecimal(0);
				}
			}
			oyaFeeQjCw=subtotalOffer.multiply(oyaFeeCw);
			updateComprehensiveCost(oyaFeeQjCw, cc);
		}
		return oyaFeeQjCw;
	}

	private BigDecimal oyaFeeQjXs(ContractProduct contractProduct,
			BigDecimal oyaFeeQjXs) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "销售费用");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeQjXs = cc.get(0).getSingleOffer();
			if(oyaFeeQjXs!=null){
				oyaFeeQjXs=new BigDecimal(oyaFeeQjXs+"");
			}else{
				oyaFeeQjXs=new BigDecimal(0);
			}
			updateComprehensiveCost(oyaFeeQjXs, cc);
		}
		return oyaFeeQjXs;
	}

	private BigDecimal oyaFeeJGCb(ContractProduct contractProduct,
			BigDecimal oyaFeeScXj, BigDecimal oyaFeeQjXj,BigDecimal oyaFeeJGCb) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "成本");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeJGCb=oyaFeeScXj.add(oyaFeeQjXj);
			updateComprehensiveCost(oyaFeeJGCb, cc);
		}
		return oyaFeeJGCb;
		
	}

	private BigDecimal oyaFeeQjXj(ContractProduct contractProduct,
			BigDecimal oyaFeeQjGl, BigDecimal oyaFeeQjCw,
			BigDecimal oyaFeeQjXs, BigDecimal oyaFeeQjXj) {
		HashMap<String, Object> map;
		List<ComprehensiveCost> cc;
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "小计");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			oyaFeeQjXj=oyaFeeQjGl.add(oyaFeeQjCw).add(oyaFeeQjXs);
			updateComprehensiveCost(oyaFeeQjXj, cc);
		}
		return oyaFeeQjXj;
	}

	private void updateComprehensiveCost(BigDecimal oyaFeeWg,
			List<ComprehensiveCost> cc) {
		ComprehensiveCost comprehensiveCost;
		comprehensiveCost = new ComprehensiveCost();
		comprehensiveCost.setId(cc.get(0).getId());
		comprehensiveCost.setSingleOffer(oyaFeeWg);
		comprehensiveCostMapper.update(comprehensiveCost);
	}

	public void updateComprehensiveCost() {

	}
}
