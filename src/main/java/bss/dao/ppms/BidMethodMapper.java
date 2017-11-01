package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.BidMethod;

public interface BidMethodMapper {
	public List<BidMethod> findListByBidMethod(BidMethod bidMethod);
	public int saveBidMethod(BidMethod bidMethod);
	public int updateBidMethod(BidMethod bidMethod);
	public int delBidMethodByid(HashMap<String, Object> map);
	public int delBidMethodByMap(HashMap<String, Object> map);
	public int delSoftBidMethodByid(HashMap<String, Object> map);
	public List<BidMethod> findScoreMethod(BidMethod bidMethod);
	public List<BidMethod> findScoreMethodByPackageId(BidMethod bidMethod);
}
