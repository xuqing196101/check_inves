package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.QualificationLevelMapper;
import ses.dao.bms.QualificationMapper;
import ses.dao.sms.SupplierAptituteMapper;
import ses.dao.sms.SupplierCertEngMapper;
import ses.dao.sms.SupplierMatEngMapper;
import ses.dao.sms.SupplierPorjectQuaMapper;
import ses.dao.sms.SupplierRegPersonMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.QualificationLevel;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.SupplierRegPerson;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationLevelService;
import ses.service.sms.SupplierMatEngService;
import ses.util.WfUtil;

@Service(value = "supplierMatEngService")
public class SupplierMatEngServiceImpl implements SupplierMatEngService {

	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;
	
	/** 供应商注册人员登记Mapper **/
	@Autowired
	private SupplierRegPersonMapper supplierRegPersonMapper;
	
	/** 供应商工程证书信息Mapper **/
	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;
	
	/** 供应商资质资格信息Mapper **/
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;

	@Autowired
	private SupplierPorjectQuaMapper supplierPorjectQuaMapper;
	
	@Autowired
	private QualificationMapper qualificationMapper;
	@Autowired
    private QualificationLevelService qualificationLevelService;
    @Autowired
    private QualificationLevelMapper qualificationLevelMapper;
	@Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
	@Override
	public void saveOrUpdateSupplierMatPro(Supplier supplier) {
		String id = supplier.getSupplierMatEng().getId();
		try{
            if (id != null && !"".equals(id)) {
                supplier.getSupplierMatEng().setUpdatedAt(new Date());
                supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
            } else {
                SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
                if(eng==null){
                    String sid = UUID.randomUUID().toString().replaceAll("-", "");
                    supplier.getSupplierMatEng().setId(sid);
                    supplier.getSupplierMatEng().setCreatedAt(new Date());
                    supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
                } else {
                    if (supplier.getSupplierMatEng().getId() == null) {
                        supplier.getSupplierMatEng().setId(eng.getId());
                    }
                    supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
                }

            }
            SupplierMatEng supplierMatEng = supplierMatEngMapper.getMatEngBySupplierId(supplier.getId());
            // 供应商注册人员登记
            List<SupplierRegPerson> listRegPersons = supplier.getSupplierMatEng().getListSupplierRegPersons();
            for (SupplierRegPerson regPerson : listRegPersons) {
                SupplierRegPerson regPersonBean = supplierRegPersonMapper.selectByPrimaryKey(regPerson.getId());
                // 判断是否已经存在,来选择insert还是update
                if (regPersonBean != null) {
                    // 修改
                    regPerson.setMatEngId(supplierMatEng.getId());
                    supplierRegPersonMapper.updateByPrimaryKeySelective(regPerson);
                } else {
                    // 新增
                    regPerson.setMatEngId(supplierMatEng.getId());
                    supplierRegPersonMapper.insertSelective(regPerson);
                }
            }
            // 供应商工程证书信息
            List<SupplierCertEng> listCertEngs = supplier.getSupplierMatEng().getListSupplierCertEngs();
            for (SupplierCertEng certEng : listCertEngs) {
                SupplierCertEng certEngBean = supplierCertEngMapper.selectByPrimaryKey(certEng.getId());
                // 判断是否已经存在,来选择insert还是update
                if (certEngBean != null) {
                    // 修改
                    certEng.setMatEngId(supplierMatEng.getId());
                    supplierCertEngMapper.updateByPrimaryKeySelective(certEng);
                } else {
                    // 新增
                    certEng.setMatEngId(supplierMatEng.getId());
                    supplierCertEngMapper.insertSelective(certEng);
                }
            }
            // 供应商资质资格信息
            List<SupplierAptitute> listAptitutes = supplier.getSupplierMatEng().getListSupplierAptitutes();
            for (SupplierAptitute aptitute : listAptitutes) {
                SupplierAptitute aptituteBean = supplierAptituteMapper.selectByPrimaryKey(aptitute.getId());
                // 判断是否已经存在,来选择insert还是update
                if (aptituteBean != null) {
                    //将自定义信息维护到字典表中
                    //是否已选择资质类型
                    if(!StringUtils.isEmpty(aptitute.getCertType())){
                        List<DictionaryData> byQuaId = new ArrayList<>();
                        byQuaId = qualificationLevelService.getByQuaId(aptitute.getAptituteLevel());//类型和等级关联关系
                        //根据已选择资质类型查询资质证书详细信息查询资质类型
                        Qualification qualification = qualificationMapper.getQualification(aptitute.getCertType());
                        //根据已选择资质等级查询字典中是否维护了资质等级
                        DictionaryData dictionaryDataExist = dictionaryDataServiceI.getDictionaryData(aptitute.getAptituteLevel());
                        //如果没有资质类型则是自定义,需插入SupplierPorjectQua表,如果有则去更新等级
                        //修改资质证书详细信息
                        aptitute.setMatEngId(supplierMatEng.getId());
                        if(qualification != null){
                            //资格等级先去字典表查询是否存在,如果存在则更新,否则新增(类型等级关联关系不存在&字典表中等级不存在&已选择等级值存在)
                            if(byQuaId.isEmpty() && dictionaryDataExist==null && null!=aptitute.getAptituteLevel()){//没有关联关系并且查不到已经存在的等级
                                DictionaryData dictionaryData = new DictionaryData();
                                dictionaryData.setCode("DEFINED_LEVEL");
                                dictionaryData.setCreatedAt(new Date());
                                dictionaryData.setUpdatedAt(new Date());
                                dictionaryData.setIsDeleted(0);
                                dictionaryData.setKind(31);
                                dictionaryData.setName(aptitute.getAptituteLevel());
                                dictionaryData.setPosition(100);
                                dictionaryDataServiceI.save(dictionaryData);
                                //建立等级和类型的关联关系
                                QualificationLevel qualificationLevel = new QualificationLevel();
                                qualificationLevel.setId(WfUtil.createUUID());
                                qualificationLevel.setQualificationId(qualification.getId());
                                qualificationLevel.setGrade(dictionaryData.getId());
                                qualificationLevelMapper.save(qualificationLevel);
                                //更新资质信息中等级的id
                                aptitute.setAptituteLevel(dictionaryData.getId());
                            }
                            if(!byQuaId.isEmpty()){//如果已经存在关联关系
                                DictionaryData dictionaryData = byQuaId.get(0);
                                dictionaryData.setUpdatedAt(new Date());
                                dictionaryData.setName(aptitute.getAptituteLevel());
                                dictionaryDataServiceI.update(dictionaryData);
                            }
                            supplierAptituteMapper.updateByPrimaryKeySelective(aptitute);
                        }else{
                            if(aptituteBean.getCertType()!=null){
        //                                qualification = qualificationMapper.getQualification(aptituteBean.getCertType());
                                String _name = aptitute.getCertType();
                                //更新资质文件信息中类型
                                aptitute.setCertType(_name);
                                //工程类型中自定义资质类型和等级的关联关系
                                List<SupplierPorjectQua> sQua = supplierPorjectQuaMapper.queryByNameAndSupplierId(_name, supplier.getId());
                                if(sQua.size()<1 && byQuaId.isEmpty()){
                                    SupplierPorjectQua projectQua=new SupplierPorjectQua();
                                    projectQua.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                                    projectQua.setName(_name);
                                    projectQua.setSupplierId(supplier.getId());
                                    projectQua.setIsDelete(0);
                                    if(dictionaryDataExist!=null){
                                        projectQua.setCertLevel(dictionaryDataExist.getName());
                                        aptitute.setAptituteLevel(dictionaryDataExist.getName());
                                    }else{
                                        projectQua.setCertLevel(aptitute.getAptituteLevel());
                                    }
                                    supplierPorjectQuaMapper.insert(projectQua);
                                }
                                if(sQua!=null&&sQua.size()>0){
                                    SupplierPorjectQua projectQua=new SupplierPorjectQua();
                                    projectQua.setName(_name);
                                    projectQua.setSupplierId(supplier.getId());
                                    if(dictionaryDataExist!=null){
                                        projectQua.setCertLevel(dictionaryDataExist.getName());
                                        aptitute.setAptituteLevel(dictionaryDataExist.getName());
                                    }else{
                                        projectQua.setCertLevel(aptitute.getAptituteLevel());
                                    }
                                    supplierPorjectQuaMapper.updateBysupplierIdAndName(projectQua);
                                }
                            }
                        }
                        supplierAptituteMapper.updateByPrimaryKeySelective(aptitute);
                    }
                } else {
                    // 新增
                    aptitute.setMatEngId(supplierMatEng.getId());
                    supplierAptituteMapper.insertSelective(aptitute);
                }
            }
        }catch (Exception e){
		    e.printStackTrace();
        }

	}
	
	/**
	 * 
	 * @see ses.service.sms.SupplierMatEngService#getMatEng(java.lang.String)
	 */
	public SupplierMatEng getMatEng(String supplierId){
	    return  supplierMatEngMapper.getMatEngBySupplierId(supplierId);
	}

    @Override
    public String getMatEngIdBySupplierId(String supplierId) {
        SupplierMatEng eng = supplierMatEngMapper.getMatEngBySupplierId(supplierId);
        if (eng != null) {
            return eng.getId();
        } else {
            return null;
        }
    }

    @Override
    public SupplierMatEng init() {
        SupplierAptitute aptitute = new SupplierAptitute();
        aptitute.setId(WfUtil.createUUID());
        List<SupplierAptitute> aptitutes = new ArrayList<SupplierAptitute>();
        aptitutes.add(aptitute);
        
        SupplierCertEng certEng = new SupplierCertEng();
        certEng.setId(WfUtil.createUUID());
        List<SupplierCertEng> certEngs = new ArrayList<SupplierCertEng>();
        certEngs.add(certEng);
        
        SupplierRegPerson regPerson = new SupplierRegPerson();
        regPerson.setId(WfUtil.createUUID());
        List<SupplierRegPerson> regPersons = new ArrayList<SupplierRegPerson>();
        regPersons.add(regPerson);
        
        SupplierMatEng eng = new  SupplierMatEng();
        eng.setListSupplierAptitutes(aptitutes);
        eng.setListSupplierCertEngs(certEngs);
        eng.setListSupplierRegPersons(regPersons);
        return eng;
    }
    
}
