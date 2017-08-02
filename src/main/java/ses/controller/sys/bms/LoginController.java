package ses.controller.sys.bms;

import common.constant.Constant;
import common.service.LoginLogService;
import common.utils.AuthUtil;
import common.utils.RSAEncrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.bms.*;
import ses.service.ems.ExpertService;
import ses.service.sms.ImportSupplierService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Title:LoginController </p>
 * <p>Description: 用户登录</p>
 * <p>Company: ses </p> 
 * @author yyyml
 * @date 2016-7-15下午2:52:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginController {
    /**
     * 代办事项
     */
    @Autowired
    private TodosService todosService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ImportSupplierService importSupplierService;

    @Autowired
    private ExpertService expertService;//专家
    
    @Autowired
    private RoleServiceI roleService;//角色业务接口
    
    @Autowired
    private StationMessageService stationMessageService;//站内消息

    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupplierAuditService supplierAuditService;
    @Autowired
    private LoginLogService loginLogService;
    
    @Autowired PreMenuServiceI preMenuService;
    //数据权限
    @Autowired
    private UserDataRuleService UserDataRuleService;

    private static Logger logger = Logger.getLogger(LoginController.class); 
    //定义修改强制修改时间
    private static String modifyDate="2017-06-05";
    /**
     * Description: 用户登录
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param user
     * @param req
     * @param response
     * @param model
     * @param rqcode
     * @exception IOException
     * @throws Exception 
     */
    @RequestMapping("/login")
    public void login(User user, HttpServletRequest req, HttpServletResponse response, Model model, String rqcode) throws Exception {
        PrintWriter out = response.getWriter();
        user.setPassword(RSAEncrypt.decryptPrivate(user.getPassword()));
        if (StringUtils.isNotBlank(user.getLoginName()) && StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(rqcode)) {
            // 根据用户名查找
            List<User> list = userService.findByLoginName(user.getLoginName());
            // 获取当前登录用户名的随机码
            String randomCode = "";
            if (list != null && list.size() > 0) {
                if (list.get(0).getErrorNum() >= 5) {
                  logger.info("密码错误5次，账户被锁");
                  out.print("errorNumMax");
                } else {
                  randomCode = list.get(0).getRandomCode();
                  // 根据随机码+密码加密
                  Md5PasswordEncoder md5 = new Md5PasswordEncoder();
                  // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true 表示：生成24位的Base64版
                  md5.setEncodeHashAsBase64(false);
                  String pwd = md5.encodePassword(user.getPassword(), randomCode);
                  user.setPassword(pwd);
                  // 根据用户名、密码验证用户登录
                  List<User> ulist = userService.queryByLogin(user);
                  User u = null;
                  if (ulist.size() > 0) {
                    u = ulist.get(0);
                  }
                  // 获取验证码
                  String code = (String) req.getSession().getAttribute("img-identity-code");
                  if (!rqcode.toUpperCase().equals(code)) {
                    logger.info("验证码输入有误");
                    out.print("errorcode");
                  } else if (u != null) {
                    //清除用户登录密码错误次数
                    u.setErrorNum(0);
                    userService.update(u);
                    req.getSession().setAttribute("register", true);
                    //查询 是否有监管 中心数据
                    /*List<String> dataRule= UserDataRuleService.getOrgID(u.getId());
                     if(dataRule!=null&& dataRule.size()>0){
                       u.setUserDataRule(userService.getUserId(dataRule,u.getTypeName()));
                     }*/
                    //查询该用户的供应商角色
                    HashMap<String, Object> supplierMap = new HashMap<String, Object>();
                    supplierMap.put("userId", u.getId());
                    supplierMap.put("code", "SUPPLIER_R");
                    List<Role> srs = roleService.selectByUserIdCode(supplierMap);
                    //查询该用户的专家角色
                    HashMap<String, Object> expertMap = new HashMap<String, Object>();
                    expertMap.put("userId", u.getId());
                    expertMap.put("code", "EXPERT_R");
                    List<Role> ers = roleService.selectByUserIdCode(expertMap);
                    //查询该用户是否是内部超级管理员
                    HashMap<String, Object> adminMap = new HashMap<String, Object>();
                    adminMap.put("userId", u.getId());
                    adminMap.put("code", "ADMIN_R");
                    List<Role> adminRoles = roleService.selectByUserIdCode(adminMap);
                    //进入专家后台
                    if (ers != null && ers.size() > 0) {
                      try {
                        // 根据userId查询出Expert
                        Expert expert = expertService.selectByPrimaryKey(u.getTypeId());
                        if (expert != null) {
                          //校验是否在规定时间未提交审核,如时间>0说明不符合规定则注销信息
//                        int validateDay = expertService.logoutExpertByDay(expert);
//                        int validateDay = 0;
//                        if(0==validateDay){//通过
							Map<String, Object> map = expertService.loginRedirect(u);
							Object object = map.get("expert");
							if (object != null) {
							  req.getSession().setAttribute("loginName", u.getId());
							  // 拉黑 阻止登录
							  if (object.equals("1")) {
							    out.print("black");
							  } else if(object.equals("5")){
							    out.print("reject");
							  }else if (object.equals("2")) {
							    out.print("reset," + u.getId());
							  } else if (object.equals("3")) {
							    out.print("auditExp," + u.getId());
							  } else if (object.equals("4")) {
							    out.print("firset," + u.getId());
							  } else if (object.equals("6")) {
							    out.print("weed,"+u.getId());
							  } else if (object.equals("7")) {
							    out.print("notLogin");
							  } else if (object.equals("8")){
							    out.print("review");
							  } else if (("1").equals(object)){
                                    // 待复审状态
                                    out.print("expert_waitOnceCheck");
                                }else if (("5").equals(object)){
                                    // 复审未通过状态
                                    out.print("onceCheckNoPass");
                                } else if (("-2").equals(object)){
                                    // 审核预通过状态
                                    out.print("prepass");
                                } else if (("-3").equals(object)){
                                    // 公示中状态
                                    out.print("publicity");
                                }
                            }else {
                            req.getSession().setAttribute("loginUser", u);
                            // loginLog记录
                            loginLog(u, req);
                            List<PreMenu> resource = preMenuService.getMenu(u);
                            req.getSession().setAttribute("resource", resource);
                            //req.getSession().setAttribute("resource", u.getMenus());
                            req.getSession().setAttribute("loginUserType", "expert");
                            out.print("scuesslogin");
                          }
//                        }else if(0 < validateDay){//未按规定时间提交审核,注销信息
//                            out.print("expert_logout," + validateDay);
//                        }
                        }else {
                          out.print("deleteLogin");
                        }
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    } else if (srs != null  && srs.size() > 0) {//供应商
                      try{
                        // 根据userId查询出Supplier
                        Supplier supplier = supplierService.selectById(u.getTypeId());
                        if (supplier != null) {
                          //校验是否在规定时间未提交审核,如时间>0说明不符合规定则注销信息
//                        int validateDay = supplierService.logoutSupplierByDay(supplier);
//                        int validateDay = 0;
//                        if(0==validateDay) {//通过
                            Map<String, Object> map = supplierService.checkLogin(u);
                            String msg = (String) map.get("status");
                            String date = (String) map.get("date");
                            PurchaseDep orgnization = ( PurchaseDep ) map.get("orgnization");

                            req.getSession().setAttribute("loginName", u.getLoginName());
                            if ("success".equals(msg)) {
                                req.getSession().setAttribute("loginSupplier", map.get("supplier"));
                                req.getSession().setAttribute("loginUser", u);
                                // loginLog记录
                                loginLog(u, req);
                                List<PreMenu> resource = preMenuService.getMenu(u);
                                req.getSession().setAttribute("resource", resource);
                                //req.getSession().setAttribute("resource", u.getMenus());
                                req.getSession().setAttribute("loginUserType", "supplier");
                                out.print("scuesslogin");
                            } else  if("unperfect".equals(msg)){
                                if(orgnization!=null){
                                    out.print("unperfect," + u.getLoginName()+","+orgnization.getShortName()+","+orgnization.getSupplierContact()+","+orgnization.getSupplierPhone()+","+orgnization.getSupplierAddress()+","+orgnization.getSupplierPostcode());
                                }else{
                                    out.print("unperfect," + u.getLoginName());
                                }

                            } else  if("初审未通过".equals(msg)){
                                out.print("firstNotPass");
                            } else  if("考察不合格".equals(msg)){
                                out.print("thirdNotPass");
                            } else  if("复核未通过".equals(msg)){
                                out.print("secondNotPass");
                            } else  if("commit".equals(msg)){
                                out.print("commit," + u.getId());
                            } else  if("reject".equals(msg)){
                                out.print("reject," + u.getLoginName());
                            } else if("prepass".equals(msg)){
                                // 预通过状态
                                out.print("prepass");
                            } else if (("publicity").equals(msg)){
                                // 公示中状态
                                out.print("publicity");

                          }
//                        }else if(0 < validateDay){//未按规定时间提交审核,注销信息
//                            out.print("supplier_logout," + validateDay);
//                        }
                        }else {
                          out.print("deleteLogin");
                        }
                      }catch (Exception e){
                        e.printStackTrace();
                      }
                    } else {
                      /*if (adminRoles != null && adminRoles.size() > 0) {
                      //如果当前用户是管理员
                      //查看当前是内网还是外网
                      String ipAddressType = PropUtil.getProperty("ipAddressType");
                      if ("1".equals(ipAddressType)) {
                        //外网限制管理员登录
                        out.print("outer_net_limit");
                      } else if ("0".equals(ipAddressType)) {
                        req.getSession().setAttribute("loginUser", u);
                        List<PreMenu> resource = preMenuService.getMenu(u);
                        req.getSession().setAttribute("resource", resource);
                        //req.getSession().setAttribute("resource", u.getMenus());
                        out.print("scuesslogin");
                      }
                    } else {*/
                      req.getSession().setAttribute("loginUser", u);
                      // loginLog记录
                      loginLog(u, req);
                      List<PreMenu> resource = preMenuService.getMenu(u);
                      req.getSession().setAttribute("resource", resource);
                      //req.getSession().setAttribute("resource", u.getMenus());
                      out.print("scuesslogin");
                      /*}*/
                    }
                    
                  } else {
                    //用户名或密码错误时，更新用户密码错误次数
                    userService.updateUserLoginErrorNum(user.getLoginName());
                    logger.error("验证失败");
                    out.print("errorlogin");
                  }
                }
            }else {
              logger.error("用户名不存在");
              out.print("errorlogin");
            }
        } else {
            logger.error("请输入用户名密码或者验证码");
            out.print("nullcontext");
        }
        //权限解析
        AuthUtil.setAuth(req);
    }
    
    /**
     * 
    * @Title: loginLog 
    * @Description: 登录日志记录
    * @author Easong
    * @param @param user    设定文件 
    * @return void    返回类型 
    * @throws
     */
    public void loginLog(User user, HttpServletRequest req){
    	loginLogService.saveOnlineUser(user, req);
    }
    
    /**   
     * @Title: index
     * @author yyyml
     * @date 2016-8-30 下午3:01:15  
     * @Description: 跳转后台首页
     * @return String     
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest req,String type,String page,String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null){
        	//判断 该用户 是否是6.5号之前注册  修改日期不是6.5号的 用户 强制修改密码
        	Integer ischeck=userService.isUpdateUser(modifyDate, user.getLoginName());
        	if(ischeck>0){
            //站内
            StationMessage message=new StationMessage();
            message.setIsFinish((short)0);
            message.setReceiverId(user.getId());
            if (user.getOrg() != null && user.getOrg().getId() != null && !"".equals(user.getOrg().getId())){
                message.setOrgId(user.getOrg().getId());
            }
            if (user.getRoles() != null && user.getRoles().size() != 0){
                message.setRoleIdArray(user.getRoles());
            }
            List<StationMessage> listStationMessage = stationMessageService.listStationMessage(message,0);
            req.setAttribute("stationMessage", listStationMessage);
            Integer tenderKey = Constant.TENDER_SYS_KEY;
            req.setAttribute("sysId",tenderKey);
        	}else{
        		req.setAttribute("uid",user.getId());
        		//跳转自定义 强制修改密码页面
        		return "initPassword";
        	}
        }
        return "index";    
    }

    /**   
     * @Title: home
     * @author yyyml
     * @date 2016-8-30 下午3:04:14  
     * @Description: 后台默认内容
     * @return String     
     */
    @RequestMapping("/home")
    public String home(HttpServletRequest req,Model model,String type,String page,String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null){
            //站内
            StationMessage message = new StationMessage();
            message.setIsFinish((short)0);
            message.setReceiverId(user.getId());
            if (user.getOrg() != null && user.getOrg().getId() != null && !"".equals(user.getOrg().getId())){
                message.setOrgId(user.getOrg().getId());
            }
            if(user.getRoles() != null && user.getRoles().size() !=0){
                message.setRoleIdArray(user.getRoles());
            }
            List<StationMessage> listStationMessage = stationMessageService.listStationMessage(message,0);
            req.setAttribute("stationMessage", listStationMessage);
            Integer tenderKey = Constant.TENDER_SYS_KEY;
            req.setAttribute("sysId", tenderKey);
            // 判断内外网1外网 0内网
            String ipAddressType= PropUtil.getProperty("ipAddressType");
            //过滤附件类型
            model.addAttribute("ipAddressType", ipAddressType);
        }
        return "backend";
    }

    /**   
     * @Title: loginOut
     * @author yyyml
     * @date 2016-8-30 下午3:04:33  
     * @Description: 退出登录 
     * @param @param req
     * @return String     
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest req){
        req.getSession().invalidate();
        return "redirect:/";
    }
    
}
