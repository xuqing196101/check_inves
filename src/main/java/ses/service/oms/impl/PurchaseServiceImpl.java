package ses.service.oms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import common.constant.StaticVariables;
import ses.dao.oms.PurchaseInfoMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseInfo;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.util.DictionaryDataUtil;
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseServiceI{
	
	@Autowired
	private PurchaseInfoMapper purchaseInfoMapper;
	
	@Autowired
	private OrgnizationServiceI orgService;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private PreMenuServiceI preMenuService;

	@Override
	public List<PurchaseInfo> findPurchaseList(HashMap<String, Object> map) {
		return purchaseInfoMapper.findPurchaseList(map);
	}

	@Override
	public int savePurchase(PurchaseInfo purchaseInfo, User currUser) {
		
		User user = new User();
		user.setLoginName(purchaseInfo.getLoginName());
		user.setPassword(purchaseInfo.getPassword());
		user.setPassword2(purchaseInfo.getPassword2());
		user.setIsDeleted(0);
		user.setTypeId(purchaseInfo.getId());
		user.setRelName(purchaseInfo.getRelName());
		user.setGender(purchaseInfo.getGender());
		user.setMobile(purchaseInfo.getMobile());
		user.setAddress(purchaseInfo.getAddress());
		user.setTelephone(purchaseInfo.getTelephone());
		user.setEmail(purchaseInfo.getEmail());
		user.setDuties(purchaseInfo.getDuites());

		/*user.setTypeName(purchaseInfo.getTypeName());*/
		user.setOrgId(purchaseInfo.getOrgId());
		user.setCreatedAt(new Date());
		
		Orgnization org = new Orgnization();
		if(purchaseInfo.getOrgId()!=null && !purchaseInfo.getOrgId().equals("")){
			org.setId(purchaseInfo.getOrgId());
			user.setOrg(org);
		}
		
		
		userServiceI.save(user,currUser);
		//保存角色
		saveRoles(user,purchaseInfo.getRoleId());
		purchaseInfo.setPurchaseDepId(purchaseInfo.getOrgId());
		
		return purchaseInfoMapper.savePurchase(purchaseInfo);
	}
	
	/**
	 * 
	 * @see ses.service.oms.PurchaseServiceI#updatePurchase(ses.model.oms.PurchaseInfo)
	 */
	@Override
	public int updatePurchase(PurchaseInfo purchaseInfo) {
		
		User user = userServiceI.findByTypeId(purchaseInfo.getId());
		if (user != null){
			user.setIsDeleted(0);
			user.setTypeId(purchaseInfo.getId());
			user.setRelName(purchaseInfo.getRelName());
			user.setGender(purchaseInfo.getGender());
			user.setMobile(purchaseInfo.getMobile());
			user.setAddress(purchaseInfo.getAddress());
			user.setTelephone(purchaseInfo.getTelephone());
			user.setEmail(purchaseInfo.getEmail());
			user.setDuties(purchaseInfo.getDuites());

			/*user.setTypeName(purchaseInfo.getTypeName());*/
			user.setOrgId(purchaseInfo.getOrgId());
			
			Orgnization org = new Orgnization();
			if(purchaseInfo.getOrgId()!=null && !purchaseInfo.getOrgId().equals("")){
				org.setId(purchaseInfo.getOrgId());
				user.setOrg(org);
			}
			userServiceI.update(user);
			//保存角色
			saveRoles(user,purchaseInfo.getRoleId());
		}
		purchaseInfo.setPurchaseDepId(purchaseInfo.getOrgId());
		return purchaseInfoMapper.updatePurchase(purchaseInfo);
	}

	
	@Override
	public int delPurchaseByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseInfoMapper.delPurchaseByMap(map);
	}
	
	/**
	 * 
	 * @see ses.service.oms.PurchaseServiceI#initPurchaser(org.springframework.ui.Model)
	 */
	@Override
	public void initPurchaser(Model model, String orgId) {
		
		model.addAttribute("purchaserTypeList", initPurchase());
		
		if (StringUtils.isNotBlank(orgId)){
			Orgnization org = orgService.findByCategoryId(orgId);
			List<Orgnization> list = new ArrayList<Orgnization>();
			list.add(org);
			model.addAttribute("purchaserOrgList", list);
		} else {
			model.addAttribute("purchaserOrgList", initPurchaseOrg());
		}
		List<DictionaryData> genders = DictionaryDataUtil.find(13);
		List<DictionaryData> politicals = DictionaryDataUtil.find(10);
        model.addAttribute("genders", genders);
        model.addAttribute("politicals", politicals);
	}
	
	
	/**
	 * 
	 *〈简述〉
	 *   初始化采购机构
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	private List<Orgnization> initPurchaseOrg(){
		
		return orgService.initOrgByType(StaticVariables.ORG_TYPE_PURCHASE);
	}
	
	/**
	 * 
	 *〈简述〉初始化采购人员类型
	 *〈详细描述〉
	 * @author myc
	 * @return
	 */
	private List<DictionaryData> initPurchase(){
		
		List<DictionaryData> purchaseList = new ArrayList<DictionaryData>();
		DictionaryData purchaserDict = DictionaryDataUtil.get("PURCHASER_U");
		DictionaryData purchaserManagerDict = DictionaryDataUtil.get("PUR_MG_U");
		purchaseList.add(purchaserDict);
		purchaseList.add(purchaserManagerDict);
		
		return purchaseList;
	}
	
	/***
	 * 
	 *〈简述〉保存角色
	 *〈详细描述〉
	 * @author myc
	 * @param user 用户user
	 * @param rolesIds 角色Ids
	 */
	private void saveRoles(User user,String rolesIds){
		if(StringUtils.isNotBlank(rolesIds)){
			String[] roleIds = rolesIds.split(StaticVariables.COMMA_SPLLIT);
			for (int i = 0; i < roleIds.length; i++) {
				Userrole userrole = new Userrole();
				Role role = roleService.get(roleIds[i]);
				userrole.setRoleId(role);
				userrole.setUserId(user);
				//保存角色-用户关联信息
				userServiceI.saveRelativity(userrole);
			}
			//保存用户与角色多对应权限的关联id
			List<String> mids = preMenuService.findByRids(roleIds);
			List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
			for (String mid : mids) {
				UserPreMenu userPreMenu = new UserPreMenu();
				PreMenu menu = preMenuService.get(mid);
				userPreMenu.setPreMenu(menu);
				userPreMenu.setUser(user);
				userPreMenus.add(userPreMenu);
			}
			userServiceI.saveUserMenuBatch(userPreMenus);
		}
	}
	
	/**
	 * 
	 * @see ses.service.oms.PurchaseServiceI#busDelPurchase(java.lang.String)
	 */
	@Override
	public void busDelPurchase(String id) {
		purchaseInfoMapper.busDelPurchase(id);
	}
	
	
}
