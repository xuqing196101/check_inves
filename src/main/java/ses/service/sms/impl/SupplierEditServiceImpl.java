package ses.service.sms.impl;

import java.util.List;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierEditMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierEdit;
import ses.service.sms.SupplierEditService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
@Service
public class SupplierEditServiceImpl implements SupplierEditService {
	@Autowired
	private SupplierEditMapper supplierEditMapper;

	@Override
	public void insertSelective(SupplierEdit se) {
		supplierEditMapper.insertSelective(se);

	}

	@Override
	public SupplierEdit selectByPrimaryKey(String id) {
		SupplierEdit se=supplierEditMapper.selectByPrimaryKey(id);
		return se;
	}

	@Override
	public void updateByPrimaryKey(SupplierEdit se) {
		supplierEditMapper.updateByPrimaryKeySelective(se);

	}

	@Override
	public List<SupplierEdit> findAll(SupplierEdit se, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<SupplierEdit> seList=supplierEditMapper.getAll(se);
		return seList;
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public List<SupplierEdit> getAllbySupplierId(SupplierEdit se) {
		List<SupplierEdit> seList=supplierEditMapper.getAllbySupplierId(se);
		return seList;
	}

	@Override
	public List<SupplierEdit> getAllRecord(SupplierEdit se) {
		List<SupplierEdit> seList=supplierEditMapper.getAllbySupplierId(se);
		return seList;
	}

	@Override
	public Supplier getResult(SupplierEdit se, Supplier supplier) {
		Supplier result=new Supplier();
		if(StringUtils.isNotBlank(se.getSupplierName())&&StringUtils.isNotBlank(supplier.getSupplierName())){
			if(!se.getSupplierName().equals(supplier.getSupplierName())){
				result.setSupplierName(supplier.getSupplierName());
			}
		}
		if(StringUtils.isNotBlank(se.getWebsite())&&StringUtils.isNotBlank(supplier.getWebsite())){
			if(!se.getWebsite().equals(supplier.getWebsite())){
				result.setWebsite(supplier.getWebsite());
			}
		}
		if(se.getFoundDate()!=null&&supplier.getFoundDate()!=null&&"".equals(se.getFoundDate())&&"".equals(supplier.getFoundDate())){
			if(!se.getFoundDate().equals(supplier.getFoundDate())){
				result.setFoundDate(supplier.getFoundDate());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBusinessType())&&StringUtils.isNotBlank(supplier.getBusinessType())){
			if(!se.getBusinessType().equals(supplier.getBusinessType())){
				result.setBusinessType(supplier.getBusinessType());
			}
		}
		if(StringUtils.isNotBlank(se.getAddress())&&StringUtils.isNotBlank(supplier.getAddress())){
			if(!se.getAddress().equals(supplier.getAddress())){
				result.setAddress(supplier.getAddress());
			}
		}
		if(StringUtils.isNotBlank(se.getBankAccount())&&StringUtils.isNotBlank(supplier.getBankAccount())){
			if(!se.getBankAccount().equals(supplier.getBankAccount())){
				result.setBankAccount(supplier.getBankAccount());
			}
		}
		if(StringUtils.isNotBlank(se.getBankName())&&StringUtils.isNotBlank(supplier.getBankName())){
			if(!se.getBankName().equals(supplier.getBankName())){
				result.setBankName(supplier.getBankName());
			}
		}
		if(StringUtils.isNotBlank(se.getPostCode())&&StringUtils.isNotBlank(supplier.getPostCode())){
			if(!se.getPostCode().equals(supplier.getPostCode())){
				result.setPostCode(supplier.getPostCode());
			}
		}

		if(StringUtils.isNotBlank(se.getLegalName())&&StringUtils.isNotBlank(supplier.getLegalName())){
			if(!se.getLegalName().equals(supplier.getLegalName())){
				result.setLegalName(supplier.getLegalName());
			}
		}
		if(StringUtils.isNotBlank(se.getLegalIdCard())&&StringUtils.isNotBlank(supplier.getLegalIdCard())){
			if(!se.getLegalIdCard().equals(supplier.getLegalIdCard())){
				result.setLegalIdCard(supplier.getLegalIdCard());
			}
		}
		if(StringUtils.isNotBlank(se.getLegalTelephone())&&StringUtils.isNotBlank(supplier.getLegalTelephone())){
			if(!se.getLegalTelephone().equals(supplier.getLegalTelephone())){
				result.setLegalTelephone(supplier.getLegalTelephone());
			}
		}
		if(StringUtils.isNotBlank(se.getLegalMobile())&&StringUtils.isNotBlank(supplier.getLegalMobile())){
			if(!se.getLegalMobile().equals(supplier.getLegalMobile())){
				result.setLegalMobile(supplier.getLegalMobile());
			}
		}

		if(StringUtils.isNotBlank(se.getContactName())&&StringUtils.isNotBlank(supplier.getContactName())){
			if(!se.getContactName().equals(supplier.getContactName())){
				result.setContactName(supplier.getContactName());
			}
		}
		if(StringUtils.isNotBlank(se.getContactFax())&&StringUtils.isNotBlank(supplier.getContactFax())){
			if(!se.getContactFax().equals(supplier.getContactFax())){
				result.setContactFax(supplier.getContactFax());
			}
		}
		if(StringUtils.isNotBlank(se.getContactTelephone())&&StringUtils.isNotBlank(supplier.getContactTelephone())){
			if(!se.getContactTelephone().equals(supplier.getContactTelephone())){
				result.setContactTelephone(supplier.getContactTelephone());
			}
		}
		
		if(StringUtils.isNotBlank(se.getContactMobile())&&StringUtils.isNotBlank(supplier.getContactMobile())){
			if(!se.getContactMobile().equals(supplier.getContactMobile())){
				result.setContactMobile(supplier.getContactMobile());
			}
		}
		if(StringUtils.isNotBlank(se.getContactEmail())&&StringUtils.isNotBlank(supplier.getContactEmail())){
			if(!se.getContactEmail().equals(supplier.getContactEmail())){
				result.setContactEmail(supplier.getContactEmail());
			}
		}
		if(StringUtils.isNotBlank(se.getContactAddress())&&StringUtils.isNotBlank(supplier.getContactAddress())){
			if(!se.getContactAddress().equals(supplier.getContactAddress())){
				result.setContactAddress(supplier.getContactAddress());
			}
		}


		if(StringUtils.isNotBlank(se.getCreditCode())&&StringUtils.isNotBlank(supplier.getCreditCode())){
			if(!se.getCreditCode().equals(supplier.getCreditCode())){
				result.setCreditCode(supplier.getCreditCode());
			}
		}
		
		if(StringUtils.isNotBlank(se.getRegistAuthority())&&StringUtils.isNotBlank(supplier.getRegistAuthority())){
			if(!se.getRegistAuthority().equals(supplier.getRegistAuthority())){
				result.setRegistAuthority(supplier.getRegistAuthority());
			}
		}
		
		if(StringUtils.isNotBlank(se.getRegistFund()+"")&&StringUtils.isNotBlank(supplier.getRegistFund()+"")){
			if(!(se.getRegistFund()+"").equals(supplier.getRegistFund()+"")){
				result.setRegistFund(supplier.getRegistFund());
			}
		}
		if(se.getBusinessStartDate()!=null&&supplier.getBusinessStartDate()!=null&&"".equals(se.getBusinessStartDate())&&"".equals(supplier.getBusinessStartDate())){
			if(!se.getBusinessStartDate().equals(supplier.getBusinessStartDate())){
				result.setBusinessStartDate(supplier.getBusinessStartDate());
			}
		}
		
		if(se.getBusinessEndDate()!=null&&supplier.getBusinessEndDate()!=null&&"".equals(se.getBusinessEndDate())&&"".equals(supplier.getBusinessEndDate())){
			if(!se.getBusinessEndDate().equals(supplier.getBusinessEndDate())){
				result.setBusinessEndDate(supplier.getBusinessEndDate());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBusinessAddress())&&StringUtils.isNotBlank(supplier.getBusinessAddress())){
			if(!se.getBusinessAddress().equals(supplier.getBusinessAddress())){
				result.setBusinessAddress(supplier.getBusinessAddress());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBusinessPostCode()+"")&&StringUtils.isNotBlank(supplier.getBusinessPostCode()+"")){
			if(!(se.getBusinessPostCode()+"").equals(supplier.getBusinessPostCode()+"")){
				result.setBusinessPostCode(supplier.getBusinessPostCode());
			}
		}
		if(StringUtils.isNotBlank(se.getBusinessScope())&&StringUtils.isNotBlank(supplier.getBusinessScope())){
			if(!se.getBusinessScope().equals(supplier.getBusinessScope())){
				result.setBusinessScope(supplier.getBusinessScope());
			}
		}


		if(StringUtils.isNotBlank(se.getOverseasBranch()+"")&&StringUtils.isNotBlank(supplier.getOverseasBranch()+"")){
			if(!(se.getOverseasBranch()+"").equals(supplier.getOverseasBranch()+"")){
				result.setOverseasBranch(supplier.getOverseasBranch());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBranchCountry())&&StringUtils.isNotBlank(supplier.getBranchCountry())){
			if(!se.getBranchCountry().equals(supplier.getBranchCountry())){
				result.setBranchCountry(supplier.getBranchCountry());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBranchAddress())&&StringUtils.isNotBlank(supplier.getBranchAddress())){
			if(!se.getBranchAddress().equals(supplier.getBranchAddress())){
				result.setBranchAddress(supplier.getBranchAddress());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBranchName())&&StringUtils.isNotBlank(supplier.getBranchName())){
			if(!se.getBranchName().equals(supplier.getBranchName())){
				result.setBranchName(supplier.getBranchName());
			}
		}
		
		if(StringUtils.isNotBlank(se.getBranchBusinessScope())&&StringUtils.isNotBlank(supplier.getBranchBusinessScope())){
			if(!se.getBranchBusinessScope().equals(supplier.getBranchBusinessScope())){
				result.setBranchBusinessScope(supplier.getBranchBusinessScope());
			}
		}
		return result;
	}

	@Override
	public SupplierEdit setToSupplierEdit(Supplier supplier) {
		SupplierEdit supplierEdit1=new SupplierEdit();
		supplierEdit1.setRecordId(supplier.getId());
	    supplierEdit1.setSupplierName(supplier.getSupplierName());
	    supplierEdit1.setWebsite(supplier.getWebsite());
	    supplierEdit1.setFoundDate(supplier.getFoundDate());
	    supplierEdit1.setBusinessType(supplier.getBusinessType());
	    supplierEdit1.setAddress(supplier.getAddress());
	    supplierEdit1.setBankName(supplier.getBankName());
	    supplierEdit1.setBankAccount(supplier.getBankAccount());
	    supplierEdit1.setPostCode(supplier.getPostCode());
	    supplierEdit1.setTaxCert(supplier.getTaxCert());
	    supplierEdit1.setBillCert(supplier.getBillCert());
	    supplierEdit1.setSecurityCert(supplier.getSecurityCert());
	    supplierEdit1.setBreachCert(supplier.getBreachCert());
	    supplierEdit1.setLegalName(supplier.getLegalName());
	    supplierEdit1.setLegalIdCard(supplier.getLegalIdCard());
	    supplierEdit1.setLegalMobile(supplier.getLegalMobile());
	    supplierEdit1.setLegalTelephone(supplier.getLegalTelephone());
	    supplierEdit1.setContactName(supplier.getContactName());
	    supplierEdit1.setContactTelephone(supplier.getContactTelephone());
	    supplierEdit1.setContactMobile(supplier.getContactMobile());
	    supplierEdit1.setContactFax(supplier.getContactFax());
	    supplierEdit1.setContactEmail(supplier.getContactEmail());
	    supplierEdit1.setContactAddress(supplier.getContactAddress());
	    supplierEdit1.setCreditCode(supplier.getCreditCode());
	    supplierEdit1.setRegistAuthority(supplier.getRegistAuthority());
	    supplierEdit1.setRegistFund(supplier.getRegistFund());
	    supplierEdit1.setBusinessStartDate(supplier.getBusinessStartDate());
	    supplierEdit1.setBusinessEndDate(supplier.getBusinessEndDate());
	    supplierEdit1.setBusinessScope(supplier.getBusinessScope());
	    supplierEdit1.setBusinessPostCode(supplier.getBusinessPostCode());
	    supplierEdit1.setBusinessAddress(supplier.getBusinessAddress());
	    supplierEdit1.setOverseasBranch((short)Integer.parseInt(supplier.getOverseasBranch()+""));
	    supplierEdit1.setBranchAddress(supplier.getBranchAddress());
	    supplierEdit1.setBranchCountry(supplier.getBranchCountry());
	    supplierEdit1.setBranchName(supplier.getBranchName());
	    supplierEdit1.setBranchBusinessScope(supplier.getBranchBusinessScope());
	    supplierEdit1.setBusinessCert(supplier.getBusinessCert());
		return supplierEdit1;
	}

	@Override
	public Supplier setToSupplier(SupplierEdit supplierEdit) {
		Supplier supplier=new Supplier();
		supplier.setSupplierName(supplierEdit.getSupplierName());
		supplier.setWebsite(supplierEdit.getWebsite());
		supplier.setFoundDate(supplierEdit.getFoundDate());
		supplier.setBusinessType(supplierEdit.getBusinessType());
		supplier.setAddress(supplierEdit.getAddress());
		supplier.setBankName(supplierEdit.getBankName());
		supplier.setBankAccount(supplierEdit.getBankAccount());
		supplier.setPostCode(supplierEdit.getPostCode());
		supplier.setTaxCert(supplierEdit.getTaxCert());
		supplier.setBillCert(supplierEdit.getBillCert());
		supplier.setSecurityCert(supplierEdit.getSecurityCert());
		supplier.setBreachCert(supplierEdit.getBreachCert());
		supplier.setLegalName(supplierEdit.getLegalName());
		supplier.setLegalIdCard(supplierEdit.getLegalIdCard());
		supplier.setLegalMobile(supplierEdit.getLegalMobile());
		supplier.setLegalTelephone(supplierEdit.getLegalTelephone());
		supplier.setContactName(supplierEdit.getContactName());
		supplier.setContactTelephone(supplierEdit.getContactTelephone());
		supplier.setContactMobile(supplierEdit.getContactMobile());
		supplier.setContactFax(supplierEdit.getContactFax());
		supplier.setContactEmail(supplierEdit.getContactEmail());
		supplier.setContactAddress(supplierEdit.getContactAddress());
		supplier.setCreditCode(supplierEdit.getCreditCode());
		supplier.setRegistAuthority(supplierEdit.getRegistAuthority());
		supplier.setRegistFund(supplierEdit.getRegistFund());
		supplier.setBusinessStartDate(supplierEdit.getBusinessStartDate());
		supplier.setBusinessEndDate(supplierEdit.getBusinessEndDate());
		supplier.setBusinessScope(supplierEdit.getBusinessScope());
		supplier.setBusinessAddress(supplierEdit.getBusinessAddress());
		supplier.setBusinessPostCode(supplierEdit.getBusinessPostCode());
		supplier.setOverseasBranch(Integer.parseInt(supplierEdit.getOverseasBranch()+""));
		supplier.setBranchAddress(supplierEdit.getBranchAddress());
		supplier.setBranchCountry(supplierEdit.getBranchCountry());
		supplier.setBranchName(supplierEdit.getBranchName());
		supplier.setBranchBusinessScope(supplierEdit.getBranchBusinessScope());
		supplier.setBusinessCert(supplierEdit.getBusinessCert());
		return supplier;
	}
}
