package bss.service.sstps.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.AccessoriesConMapper;
import bss.dao.sstps.AuditOpinionMapper;
import bss.dao.sstps.ComCostDisMapper;
import bss.dao.sstps.ComprehensiveCostMapper;
import bss.dao.sstps.OutproductConMapper;
import bss.dao.sstps.OutsourcingConMapper;
import bss.dao.sstps.ProductQuotaMapper;
import bss.dao.sstps.SpecialCostMapper;
import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.AuditOpinion;
import bss.model.sstps.ComCostDis;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutproductCon;
import bss.model.sstps.OutsourcingCon;
import bss.model.sstps.ProductQuota;
import bss.model.sstps.SpecialCost;
import bss.service.sstps.AuditOpinionService;
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
	@Autowired
	private AuditOpinionMapper auditOpinionMapper;

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

	@Override
	public void appendSumApprovedComprehensiveCost(
			ContractProduct contractProduct) {
		BigDecimal addit = new BigDecimal(0);// 本产品定额工时
		BigDecimal additRg = new BigDecimal(0);// 工时及分配率、直接人工
		BigDecimal additRl = new BigDecimal(0);// 工时及分配率、燃料动力
		BigDecimal additZz = new BigDecimal(0);// 工时及分配率、制造费用
		BigDecimal additQj = new BigDecimal(0);// 工时及分配率、期间费用
		BigDecimal additHj = new BigDecimal(0);// 工时及分配率、工时分配率合计
		BigDecimal additScYf = new BigDecimal(0);// 生产成本、原辅材料
		BigDecimal additScWg = new BigDecimal(0);// 生产成本、外购成件
		BigDecimal additScWx = new BigDecimal(0);// 生产成本、外协成件
		BigDecimal additScRl = new BigDecimal(0);// 生产成本、燃料动力
		BigDecimal additScRg = new BigDecimal(0);// 生产成本、直接人工
		BigDecimal additScZy = new BigDecimal(0);// 生产成本、专用费用
		BigDecimal additScZz = new BigDecimal(0);// 生产成本、制造费用
		BigDecimal additScXj = new BigDecimal(0);// 生产成本、小计
		BigDecimal additQjGl = new BigDecimal(0);// 期间费用、管理费用
		BigDecimal additQjCw = new BigDecimal(0);// 期间费用、财务费用
		BigDecimal additQjXs = new BigDecimal(0);// 期间费用、销售费用
		BigDecimal additQjXj = new BigDecimal(0);// 期间费用、小计
		BigDecimal additJGCb = new BigDecimal(0);// 价格方案、成本
		BigDecimal additJGLr = new BigDecimal(0);// 价格方案、利润
		BigDecimal additJGSj = new BigDecimal(0);// 价格方案、税金
		BigDecimal additJGJg = new BigDecimal(0);// 价格方案、价格
		ComprehensiveCost comprehensiveCost=null;
		HashMap<String, Object> map=null;
		List<ComprehensiveCost> cc=null;
		// 本产品定额工时
		map= new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "本产品定额工时");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<ProductQuota> pq = productQuotaMapper
					.selectProductIdSum(contractProduct.getId());
			if(pq!=null&&pq.size()>0&&pq.get(0)!=null){
				addit=pq.get(0).getApprovedRatify();
				if(addit!=null){
					addit=new BigDecimal(addit+"");
				}else{
					addit=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(addit, cc);
		}
		// 工时及分配率、直接人工
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
				additRg=ccd.get(0).getSubtractFee();
				if(additRg!=null){
					additRg=new BigDecimal(additRg+"");
				}else{
					additRg=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additRg, cc);
		}
		// 工时及分配率、燃料动力
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
				additRl=ccd.get(0).getSubtractFee();
				if(additRl!=null){
					additRl=new BigDecimal(additRl+"");
				}else{
					additRl=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additRl, cc);
		}
		// 工时及分配率、制造费用
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
				additZz=ccd.get(0).getSubtractFee();
				if(additZz!=null){
					additZz=new BigDecimal(additZz+"");
				}else{
					additZz=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additZz, cc);
		}
		// 工时及分配率、期间费用
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
			BigDecimal additFeeGl = new BigDecimal(0);
			BigDecimal additFeeCw = new BigDecimal(0);
			if (ccdGl != null && ccdGl.size() > 0) {
				additFeeGl = ccdGl.get(0).getSubtractFee();
				if (additFeeGl != null) {
					additFeeGl = new BigDecimal(additFeeGl + "");
				} else {
					additFeeGl = new BigDecimal(0);
				}
			}
			if (ccd != null && ccd.size() > 0) {
				additFeeCw = ccd.get(0).getSubtractFee();
				if (additFeeCw != null) {
					additFeeCw = new BigDecimal(additFeeCw + "");
				} else {
					additFeeCw = new BigDecimal(0);
				}
			}
			additQj=additFeeCw.add(additFeeGl);
			updateComprehensiveCostAddit(additQj, cc);
		}
		// 工时及分配率、工时分配率合计
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "工时分配率合计");
		map.put("parentName", "工时及分配率");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additHj=additRg.add(additRl).add(additZz).add(additQj);
			updateComprehensiveCostAddit(additHj, cc);
		}
		//生产成本、原辅材料
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "原辅材料");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<AccessoriesCon> ac = accessoriesConMapper
					.selectProductIdAndParentId(contractProduct.getId());
			if(ac!=null&&ac.size()>0&&ac.get(0)!=null){
				additScYf=ac.get(0).getConsumeMoney();
				if(additScYf!=null){
					additScYf=new BigDecimal(additScYf+"");
				}else{
					additScYf=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additScYf, cc);
		}
		//生产成本、外购成件
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "外购成件");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<OutproductCon> oc = outproductConMapper
					.selectProductIdSum(contractProduct.getId());
			if (oc != null && oc.size() > 0) {
				additScWg=oc.get(0).getConsumeMoney();
				if(additScWg!=null){
					additScWg=new BigDecimal(additScWg+"");
				}else{
					additScWg=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additScWg, cc);
		}
		 //生产成本、外协部件
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "外协部件");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<OutsourcingCon> oc = outsourcingConMapper
					.selectProductIdSum(contractProduct.getId());
			if (oc != null && oc.size() > 0) {
				additScWx=oc.get(0).getConsumeMoney();
				if(additScWx!=null){
					additScWx=new BigDecimal(additScWx+"");
				}else{
					additScWx=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additScWx, cc);
		}
		// 生产成本、燃料动力
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "燃料动力");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additScRl = addit.multiply(additRl);
			updateComprehensiveCostAddit(additScRl, cc);
		}
		//生产成本、直接人工
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "直接人工");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additScRg = addit.multiply(additRg);
			updateComprehensiveCostAddit(additScRg, cc);
		}
		// 生产成本、专用费用
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "专用费用");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			List<SpecialCost> sc = specialCostMapper
					.selectByParentIdSum(contractProduct.getId());
			if (sc != null && sc.size() > 0&&sc.get(0)!=null) {
				additScZy=sc.get(0).getApprovedMoney();
				if(additScZy!=null){
					additScZy=new BigDecimal(additScZy+"");
				}else{
					additScZy=new BigDecimal(0);
				}
			}
			updateComprehensiveCostAddit(additScZy, cc);
		}
		// 生产成本、制造费用
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "制造费用");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additScZz = addit.multiply(additZz);
			updateComprehensiveCostAddit(additScZz, cc);
		}
		// 生产成本、小计
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "小计");
		map.put("parentName", "生产成本");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additScXj=additScYf.add(additScWg).add(additScWx).add(additScRl).add(additScRg).add(additScZy).add(additScZz);
			updateComprehensiveCostAddit(additScXj, cc);
		}
		// 期间费用、管理费用
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
			BigDecimal feeGl = new BigDecimal(0);
			if(ccdGl!=null&&ccdGl.size()>0){
				feeGl=ccdGl.get(0).getSubtractFee();
				if(feeGl!=null){
					feeGl=new BigDecimal(feeGl+"");
				}else{
					feeGl=new BigDecimal(0);
				}
			}
			additQjGl=addit.multiply(feeGl);
			updateComprehensiveCostAddit(additQjGl, cc);
		}
		// 期间费用、财务费用
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
			BigDecimal feeCw = new BigDecimal(0);
			if (ccd != null && ccd.size() > 0) {
				feeCw=ccd.get(0).getSubtractFee();
				if(feeCw!=null){
					feeCw=new BigDecimal(feeCw+"");
				}else{
					feeCw=new BigDecimal(0);
				}
			}
			additQjCw=addit.multiply(feeCw);
			updateComprehensiveCostAddit(additQjCw, cc);
		}
		// 期间费用、销售费用
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "销售费用");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additQjXs = cc.get(0).getAdditResult();
			if(additQjXs!=null){
				additQjXs=new BigDecimal(additQjXs+"");
			}else{
				additQjXs=new BigDecimal(0);
			}
			updateComprehensiveCostAddit(additQjXs, cc);
		}
		// 期间费用、小计
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "小计");
		map.put("parentName", "期间费用");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additQjXj=additQjGl.add(additQjCw).add(additQjXs);
			updateComprehensiveCostAddit(additQjXj, cc);
		}
		// 价格方案、成本
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "成本");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additJGCb=additScXj.add(additQjXj);
			updateComprehensiveCostAddit(additJGCb, cc);
		}
		// 价格方案、利润
		map.put("id", contractProduct.getId());
		map.put("name", "利润");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additJGLr=additJGCb.multiply(new BigDecimal(0.05+""));
			updateComprehensiveCostAddit(additJGLr, cc);
		}
		// 价格方案、税金
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "税金");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additJGSj = cc.get(0).getAdditResult();
			if(additJGSj!=null){
				additJGSj=new BigDecimal(additJGSj+"");
			}else{
				additJGSj=new BigDecimal(0);
			}
			updateComprehensiveCostAddit(additJGSj, cc);
		}
		// 价格方案、价格
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "价格");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		if (cc != null && cc.size() > 0) {
			additJGJg=additJGCb.add(additJGLr).add(additJGSj);
			updateComprehensiveCostAddit(additJGJg, cc);
		}
		//审核结果
		
		map = new HashMap<String, Object>();
		map.put("id", contractProduct.getId());
		map.put("name", "价格");
		map.put("parentName", "价格方案");
		cc = comprehensiveCostMapper
				.selectProductIdAndName(map);
		BigDecimal so=new BigDecimal(0);
		if (cc != null && cc.size() > 0) {
			so=cc.get(0).getSingleOffer();
			if(so!=null){
				so=new BigDecimal(so+"");
			}else{
				so=new BigDecimal(0);
			}
		}
		AuditOpinion ap = new AuditOpinion();
		ap.setContractProduct(contractProduct);
		ap = auditOpinionMapper.selectProduct(ap);
		ap.setCompanyPrice(so);
		ap.setAuditOpinion(additJGJg);
		ap.setUnitSubtract(so.subtract(additJGJg));
		if(ap.getOrderAcount()!=null){
			ap.setAcountSubtract(so.subtract(additJGJg).multiply(ap.getOrderAcount()));
		}else{
			ap.setAcountSubtract(new BigDecimal(0));
		}
		
		auditOpinionMapper.update(ap);
		
	}

	private void updateComprehensiveCostAddit(BigDecimal addit,
			List<ComprehensiveCost> cc) {
		ComprehensiveCost comprehensiveCost;
		comprehensiveCost=new ComprehensiveCost();
		comprehensiveCost.setId(cc.get(0).getId());
		comprehensiveCost.setAdditResult(addit);
		comprehensiveCostMapper.update(comprehensiveCost);
	}

	
	
}
