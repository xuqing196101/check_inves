package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import bss.model.ppms.BidMethod;

public interface BidMethodService {
	
	public List<BidMethod> findListByBidMethod(BidMethod bidMethod);
	
	public int saveBidMethod(BidMethod bidMethod);
	
	public int updateBidMethod(BidMethod bidMethod);
	
	public int delBidMethodByid(HashMap<String, Object> map);
	
	public int delBidMethodByMap(HashMap<String, Object> map);
	
	public int delSoftBidMethodByid(HashMap<String, Object> map);
	
	public void save(BidMethod bidMethod);
	
	public List<BidMethod> findScoreMethod(BidMethod bidMethod);
	
	public String getMethod(String projectId, String packageId);
	
	Boolean saveLoadPackage(String id, String projectId, String packageId);
  
}
