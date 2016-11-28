package ses.service.sms.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierEditMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierEdit;
import ses.service.sms.SupplierEditService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商变更服务层实现层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Service
public class SupplierEditServiceImpl implements SupplierEditService {
    /**
     * 供应商变更持久层
     */
    @Autowired
    private SupplierEditMapper supplierEditMapper;

    @Override
    public void insertSelective(SupplierEdit se) {
        supplierEditMapper.insertSelective(se);
    }

    @Override
    public SupplierEdit selectByPrimaryKey(String id) {
        SupplierEdit se = supplierEditMapper.selectByPrimaryKey(id);
        return se;
    }

    @Override
    public void updateByPrimaryKey(SupplierEdit se) {
        supplierEditMapper.updateByPrimaryKeySelective(se);
    }

    @Override
    public List<SupplierEdit> findAll(SupplierEdit se, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        List<SupplierEdit> seList = supplierEditMapper.getAll(se);
        return seList;
    }

    @Override
    public void delete(String id) {
    }

    @Override
    public List<SupplierEdit> getAllbySupplierId(SupplierEdit se) {
        List<SupplierEdit> seList = supplierEditMapper.getAllbySupplierId(se);
        return seList;
    }

    @Override
    public List<SupplierEdit> getAllRecord(SupplierEdit se) {
        List<SupplierEdit> seList = supplierEditMapper.getAllbySupplierId(se);
        return seList;
    }

    @Override
    public Supplier getResult(SupplierEdit se, Supplier supplier) {
        Supplier result = new Supplier();
        if (StringUtils.isNotBlank(se.getSupplierName()) && StringUtils.isNotBlank(supplier.getSupplierName())) {
            if (!se.getSupplierName().equals(supplier.getSupplierName())) {
                result.setSupplierName(supplier.getSupplierName());
            }
        }
        if (StringUtils.isNotBlank(se.getWebsite()) && StringUtils.isNotBlank(supplier.getWebsite())) {
            if (!se.getWebsite().equals(supplier.getWebsite())) {
                result.setWebsite(supplier.getWebsite());
            }
        }
        if (se.getFoundDate() != null && supplier.getFoundDate() != null && "".equals(se.getFoundDate()) && "".equals(supplier.getFoundDate())) {
            if (!se.getFoundDate().equals(supplier.getFoundDate())) {
                result.setFoundDate(supplier.getFoundDate());
            }
        }

        if (StringUtils.isNotBlank(se.getBusinessType()) && StringUtils.isNotBlank(supplier.getBusinessType())) {
            if (!se.getBusinessType().equals(supplier.getBusinessType())) {
                result.setBusinessType(supplier.getBusinessType());
            }
        }
        if (StringUtils.isNotBlank(se.getAddress()) && StringUtils.isNotBlank(supplier.getAddress())) {
            if (!se.getAddress().equals(supplier.getAddress())) {
                result.setAddress(supplier.getAddress());
            }
        }
        if (StringUtils.isNotBlank(se.getBankAccount()) && StringUtils.isNotBlank(supplier.getBankAccount())) {
            if (!se.getBankAccount().equals(supplier.getBankAccount())) {
                result.setBankAccount(supplier.getBankAccount());
            }
        }
        if (StringUtils.isNotBlank(se.getBankName()) && StringUtils.isNotBlank(supplier.getBankName())) {
            if (!se.getBankName().equals(supplier.getBankName())) {
                result.setBankName(supplier.getBankName());
            }
        }
        if (StringUtils.isNotBlank(se.getPostCode()) && StringUtils.isNotBlank(supplier.getPostCode())) {
            if (!se.getPostCode().equals(supplier.getPostCode())) {
                result.setPostCode(supplier.getPostCode());
            }
        }

        if (StringUtils.isNotBlank(se.getLegalName()) && StringUtils.isNotBlank(supplier.getLegalName())) {
            if (!se.getLegalName().equals(supplier.getLegalName())) {
                result.setLegalName(supplier.getLegalName());
            }
        }
        if (StringUtils.isNotBlank(se.getLegalIdCard()) && StringUtils.isNotBlank(supplier.getLegalIdCard())) {
            if (!se.getLegalIdCard().equals(supplier.getLegalIdCard())) {
                result.setLegalIdCard(supplier.getLegalIdCard());
            }
        }
        if (StringUtils.isNotBlank(se.getLegalTelephone()) && StringUtils.isNotBlank(supplier.getLegalTelephone())) {
            if (!se.getLegalTelephone().equals(supplier.getLegalTelephone())) {
                result.setLegalTelephone(supplier.getLegalTelephone());
            }
        }
        if (StringUtils.isNotBlank(se.getLegalMobile()) && StringUtils.isNotBlank(supplier.getLegalMobile())) {
            if (!se.getLegalMobile().equals(supplier.getLegalMobile())) {
                result.setLegalMobile(supplier.getLegalMobile());
            }
        }

        if (StringUtils.isNotBlank(se.getContactName()) && StringUtils.isNotBlank(supplier.getContactName())) {
            if (!se.getContactName().equals(supplier.getContactName())) {
                result.setContactName(supplier.getContactName());
            }
        }
        if (StringUtils.isNotBlank(se.getContactFax()) && StringUtils.isNotBlank(supplier.getContactFax())) {
            if (!se.getContactFax().equals(supplier.getContactFax())) {
                result.setContactFax(supplier.getContactFax());
            }
        }
        if (StringUtils.isNotBlank(se.getContactTelephone()) && StringUtils.isNotBlank(supplier.getContactTelephone())) {
            if (!se.getContactTelephone().equals(supplier.getContactTelephone())) {
                result.setContactTelephone(supplier.getContactTelephone());
            }
        }

        if (StringUtils.isNotBlank(se.getContactMobile()) && StringUtils.isNotBlank(supplier.getContactMobile())) {
            if (!se.getContactMobile().equals(supplier.getContactMobile())) {
                result.setContactMobile(supplier.getContactMobile());
            }
        }
        if (StringUtils.isNotBlank(se.getContactEmail()) && StringUtils.isNotBlank(supplier.getContactEmail())) {
            if (!se.getContactEmail().equals(supplier.getContactEmail())) {
                result.setContactEmail(supplier.getContactEmail());
            }
        }
        if (StringUtils.isNotBlank(se.getContactAddress()) && StringUtils.isNotBlank(supplier.getContactAddress())) {
            if (!se.getContactAddress().equals(supplier.getContactAddress())) {
                result.setContactAddress(supplier.getContactAddress());
            }
        }

        if (StringUtils.isNotBlank(se.getCreditCode()) && StringUtils.isNotBlank(supplier.getCreditCode())) {
            if (!se.getCreditCode().equals(supplier.getCreditCode())) {
                result.setCreditCode(supplier.getCreditCode());
            }
        }

        if (StringUtils.isNotBlank(se.getRegistAuthority()) && StringUtils.isNotBlank(supplier.getRegistAuthority())) {
            if (!se.getRegistAuthority().equals(supplier.getRegistAuthority())) {
                result.setRegistAuthority(supplier.getRegistAuthority());
            }
        }

        if (StringUtils.isNotBlank(se.getRegistFund() + "") && StringUtils.isNotBlank(supplier.getRegistFund() + "")) {
            if (!(se.getRegistFund() + "").equals(supplier.getRegistFund() + "")) {
                result.setRegistFund(supplier.getRegistFund());
            }
        }
        if (se.getBusinessStartDate() != null && supplier.getBusinessStartDate() != null
            && "".equals(se.getBusinessStartDate()) && "".equals(supplier.getBusinessStartDate())) {
            if (!se.getBusinessStartDate().equals(supplier.getBusinessStartDate())) {
                result.setBusinessStartDate(supplier.getBusinessStartDate());
            }
        }

        if (se.getBusinessEndDate() != null && supplier.getBusinessEndDate() != null
            && "".equals(se.getBusinessEndDate()) && "".equals(supplier.getBusinessEndDate())) {
            if (!se.getBusinessEndDate().equals(supplier.getBusinessEndDate())) {
                result.setBusinessEndDate(supplier.getBusinessEndDate());
            }
        }

        if (StringUtils.isNotBlank(se.getBusinessAddress()) && StringUtils.isNotBlank(supplier.getBusinessAddress())) {
            if (!se.getBusinessAddress().equals(supplier.getBusinessAddress())) {
                result.setBusinessAddress(supplier.getBusinessAddress());
            }
        }

        if (StringUtils.isNotBlank(se.getBusinessPostCode() + "") && StringUtils.isNotBlank(supplier.getBusinessPostCode() + "")) {
            if (!(se.getBusinessPostCode() + "").equals(supplier.getBusinessPostCode() + "")) {
                result.setBusinessPostCode(supplier.getBusinessPostCode());
            }
        }
        if (StringUtils.isNotBlank(se.getBusinessScope()) && StringUtils.isNotBlank(supplier.getBusinessScope())) {
            if (!se.getBusinessScope().equals(supplier.getBusinessScope())) {
                result.setBusinessScope(supplier.getBusinessScope());
            }
        }
        if (StringUtils.isNotBlank(se.getOverseasBranch() + "") && StringUtils.isNotBlank(supplier.getOverseasBranch() + "")) {
            if (!(se.getOverseasBranch() + "").equals(supplier.getOverseasBranch() + "")) {
                result.setOverseasBranch(supplier.getOverseasBranch());
            }
        }
        if (StringUtils.isNotBlank(se.getBranchCountry()) && StringUtils.isNotBlank(supplier.getBranchCountry())) {
            if (!se.getBranchCountry().equals(supplier.getBranchCountry())) {
                result.setBranchCountry(supplier.getBranchCountry());
            }
        }
        if (StringUtils.isNotBlank(se.getBranchAddress()) && StringUtils.isNotBlank(supplier.getBranchAddress())) {
            if (!se.getBranchAddress().equals(supplier.getBranchAddress())) {
                result.setBranchAddress(supplier.getBranchAddress());
            }
        }
        if (StringUtils.isNotBlank(se.getBranchName()) && StringUtils.isNotBlank(supplier.getBranchName())) {
            if (!se.getBranchName().equals(supplier.getBranchName())) {
                result.setBranchName(supplier.getBranchName());
            }
        }
        if (StringUtils.isNotBlank(se.getBranchBusinessScope()) && StringUtils.isNotBlank(supplier.getBranchBusinessScope())) {
            if (!se.getBranchBusinessScope().equals(supplier.getBranchBusinessScope())) {
                result.setBranchBusinessScope(supplier.getBranchBusinessScope());
            }
        }
        return result;
    }

    @Override
    public SupplierEdit setToSupplierEdit(Supplier supplier) {
        SupplierEdit supplierEdit1 = new SupplierEdit();
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
        supplierEdit1.setOverseasBranch((short) Integer.parseInt(supplier.getOverseasBranch() + ""));
        supplierEdit1.setBranchAddress(supplier.getBranchAddress());
        supplierEdit1.setBranchCountry(supplier.getBranchCountry());
        supplierEdit1.setBranchName(supplier.getBranchName());
        supplierEdit1.setBranchBusinessScope(supplier.getBranchBusinessScope());
        supplierEdit1.setBusinessCert(supplier.getBusinessCert());
        return supplierEdit1;
    }

    @Override
    public Supplier setToSupplier(SupplierEdit supplierEdit) {
        Supplier supplier = new Supplier();
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
        supplier.setOverseasBranch(Integer.parseInt(supplierEdit.getOverseasBranch() + ""));
        supplier.setBranchAddress(supplierEdit.getBranchAddress());
        supplier.setBranchCountry(supplierEdit.getBranchCountry());
        supplier.setBranchName(supplierEdit.getBranchName());
        supplier.setBranchBusinessScope(supplierEdit.getBranchBusinessScope());
        supplier.setBusinessCert(supplierEdit.getBusinessCert());
        return supplier;
    }

    @Override
    public List<String> getList(List<SupplierEdit> listEdit) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < listEdit.size() - 1; i++) {
            StringBuffer sb = new StringBuffer("");
            if ((listEdit.get(i).getSupplierName() == null && listEdit.get(i + 1).getSupplierName() != null)) {
                sb.append(" 供应商姓名:" + listEdit.get(i + 1).getSupplierName() + ",");
            } else if ((listEdit.get(i).getSupplierName() == null && listEdit.get(i + 1).getSupplierName() == null)) {
            } else if (!listEdit.get(i).getSupplierName().equals(listEdit.get(i + 1).getSupplierName())) {
                sb.append(" 供应商姓名:" + listEdit.get(i + 1).getSupplierName() + ",");
            }
            if ((listEdit.get(i).getWebsite() == null && listEdit.get(i + 1).getWebsite() != null)) {
                sb.append(" 公司网址:" + listEdit.get(i + 1).getWebsite() + ",");
            } else if ((listEdit.get(i).getWebsite() == null && listEdit.get(i + 1).getWebsite() == null)) {
            } else if (!listEdit.get(i).getWebsite().equals(listEdit.get(i + 1).getWebsite())) {
                sb.append(" 公司网址:" + listEdit.get(i + 1).getWebsite() + ",");
            }
            if ((listEdit.get(i).getFoundDate() == null && listEdit.get(i + 1).getFoundDate() != null)) {
                sb.append(" 成立日期:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getFoundDate()) + ",");
            } else if ((listEdit.get(i).getFoundDate() == null && listEdit.get(i + 1).getFoundDate() == null)) {
            } else if (!listEdit.get(i).getFoundDate().equals(listEdit.get(i + 1).getFoundDate())){
                sb.append(" 成立日期:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getFoundDate()) + ",");
            }
            if ((listEdit.get(i).getBusinessType() == null && listEdit.get(i + 1).getBusinessType() != null)) {
                sb.append(" 营业执照类型:" + listEdit.get(i + 1).getBusinessType() + ",");
            } else if ((listEdit.get(i).getBusinessType() == null && listEdit.get(i + 1).getBusinessType() == null)) {
            } else if (!listEdit.get(i).getBusinessType().equals(listEdit.get(i + 1).getBusinessType())) {
                sb.append(" 营业执照类型:" + listEdit.get(i + 1).getBusinessType() + ",");
            }
            if ((listEdit.get(i).getAddress() == null && listEdit.get(i + 1).getAddress() != null)) {
                sb.append(" 企业地址:" + listEdit.get(i + 1).getAddress() + ",");
            } else if ((listEdit.get(i).getAddress() == null && listEdit.get(i + 1).getAddress() == null)) {
            } else if (!listEdit.get(i).getAddress().equals(listEdit.get(i + 1).getAddress())) {
                sb.append(" 企业地址:" + listEdit.get(i + 1).getAddress() + ",");
            }
            if ((listEdit.get(i).getBankName() == null && listEdit.get(i + 1).getBankName() != null)) {
                sb.append(" 开户行名称:" + listEdit.get(i + 1).getBankName() + ",");
            } else if ((listEdit.get(i).getBankName() == null && listEdit.get(i + 1).getBankName() == null)) {
            } else if (!listEdit.get(i).getBankName().equals(listEdit.get(i + 1).getBankName())) {
                sb.append(" 开户行名称:" + listEdit.get(i + 1).getBankName() + ",");
            }
            if ((listEdit.get(i).getBankAccount() == null && listEdit.get(i + 1).getBankAccount() != null)) {
                sb.append(" 开户行账户:" + listEdit.get(i + 1).getBankAccount() + ",");
            } else if ((listEdit.get(i).getBankAccount() == null && listEdit.get(i + 1).getBankAccount() == null)) {
            } else if (!listEdit.get(i).getBankAccount().equals(listEdit.get(i + 1).getBankAccount())) {
                sb.append(" 开户行账户:" + listEdit.get(i + 1).getBankAccount() + ",");
            }
            if ((listEdit.get(i).getPostCode() == null && listEdit.get(i + 1).getPostCode() != null)) {
                sb.append(" 邮编:" + listEdit.get(i + 1).getPostCode() + ",");
            } else if ((listEdit.get(i).getPostCode() == null && listEdit.get(i + 1).getPostCode() == null)) {
            } else if (!listEdit.get(i).getPostCode().equals(listEdit.get(i + 1).getPostCode())) {
                sb.append(" 邮编:" + listEdit.get(i + 1).getPostCode() + ",");
            }
            if ((listEdit.get(i).getLegalName() == null && listEdit.get(i + 1).getLegalName() != null)) {
                sb.append(" 法人姓名:" + listEdit.get(i + 1).getLegalName() + ",");
            } else if ((listEdit.get(i).getLegalName() == null && listEdit.get(i + 1).getLegalName() == null)) {
            } else if (!listEdit.get(i).getLegalName().equals(listEdit.get(i + 1).getLegalName())){
                sb.append(" 法人姓名:" + listEdit.get(i + 1).getLegalName() + ",");
            }
            if ((listEdit.get(i).getLegalIdCard() == null && listEdit.get(i + 1).getLegalIdCard() != null)) {
                sb.append(" 法人身份证:" + listEdit.get(i + 1).getLegalIdCard() + ",");
            } else if ((listEdit.get(i).getLegalIdCard() == null && listEdit.get(i + 1).getLegalIdCard() == null)) {
            } else if (!listEdit.get(i).getLegalIdCard().equals(listEdit.get(i + 1).getLegalIdCard())) {
                sb.append(" 法人身份证:" + listEdit.get(i + 1).getLegalIdCard() + ",");
            }
            if ((listEdit.get(i).getLegalMobile() == null && listEdit.get(i + 1).getLegalMobile() != null)) {
                sb.append(" 法人手机:" + listEdit.get(i + 1).getLegalMobile() + ",");
            } else if ((listEdit.get(i).getLegalMobile() == null && listEdit.get(i + 1).getLegalMobile() == null)) {
            } else if (!listEdit.get(i).getLegalMobile().equals(listEdit.get(i + 1).getLegalMobile())) {
                sb.append(" 法人手机:" + listEdit.get(i + 1).getLegalMobile() + ",");
            }
            if ((listEdit.get(i).getLegalTelephone() == null && listEdit.get(i + 1).getLegalTelephone() != null)) {
                sb.append(" 法人电话:" + listEdit.get(i + 1).getLegalTelephone() + ",");
            } else if ((listEdit.get(i).getLegalTelephone() == null && listEdit.get(i + 1).getLegalTelephone() == null)) {
            } else if (!listEdit.get(i).getLegalTelephone().equals(listEdit.get(i + 1).getLegalTelephone())) {
                sb.append(" 法人电话:" + listEdit.get(i + 1).getLegalTelephone() + ",");
            }
            if ((listEdit.get(i).getContactName() == null && listEdit.get(i + 1).getContactName() != null)) {
                sb.append(" 联系人姓名:" + listEdit.get(i + 1).getContactName() + ",");
            } else if ((listEdit.get(i).getContactName() == null && listEdit.get(i + 1).getContactName() == null)) {
            } else if (!listEdit.get(i).getContactName().equals(listEdit.get(i + 1).getContactName())) {
                sb.append(" 联系人姓名:" + listEdit.get(i + 1).getContactName() + ",");
            }
            if ((listEdit.get(i).getContactTelephone() == null && listEdit.get(i + 1).getContactTelephone() != null)) {
                sb.append(" 联系人手机:" + listEdit.get(i + 1).getContactTelephone() + ",");
            } else if ((listEdit.get(i).getContactTelephone() == null && listEdit.get(i + 1).getContactTelephone() == null)) {
            } else if (!listEdit.get(i).getContactTelephone().equals(listEdit.get(i + 1).getContactTelephone())) {
                sb.append(" 联系人手机:" + listEdit.get(i + 1).getContactTelephone() + ",");
            }
            if ((listEdit.get(i).getContactMobile() == null && listEdit.get(i + 1).getContactMobile() != null)) {
                sb.append(" 联系人电话:" + listEdit.get(i + 1).getContactMobile() + ",");
            } else if ((listEdit.get(i).getContactMobile() == null && listEdit.get(i + 1).getContactMobile() == null)) {
            } else if (!listEdit.get(i).getContactMobile().equals(listEdit.get(i + 1).getContactMobile())) {
                sb.append(" 联系人电话:" + listEdit.get(i + 1).getContactMobile() + ",");
            }
            if ((listEdit.get(i).getContactFax() == null && listEdit.get(i + 1).getContactFax() != null)) {
                sb.append(" 联系人传真:" + listEdit.get(i + 1).getContactFax() + ",");
            } else if ((listEdit.get(i).getContactFax() == null && listEdit.get(i + 1).getContactFax() == null)) {
            } else if (!listEdit.get(i).getContactFax().equals(listEdit.get(i + 1).getContactFax())) {
                sb.append(" 联系人传真:" + listEdit.get(i + 1).getContactFax() + ",");
            }
            if ((listEdit.get(i).getContactEmail() == null && listEdit.get(i + 1).getContactEmail() != null)) {
                sb.append(" 联系人邮箱:" + listEdit.get(i + 1).getContactEmail() + ",");
            } else if ((listEdit.get(i).getContactEmail() == null && listEdit.get(i + 1).getContactEmail() == null)) {
            } else if (!listEdit.get(i).getContactEmail().equals(listEdit.get(i + 1).getContactEmail())) {
                sb.append(" 联系人邮箱:" + listEdit.get(i + 1).getContactEmail() + ",");
            }
            if ((listEdit.get(i).getContactAddress() == null && listEdit.get(i + 1).getContactAddress() != null)) {
                sb.append(" 联系人地址:" + listEdit.get(i + 1).getContactAddress() + ",");
            } else if ((listEdit.get(i).getContactAddress() == null && listEdit.get(i + 1).getContactAddress() == null)) {
            } else if (!listEdit.get(i).getContactAddress().equals(listEdit.get(i + 1).getContactAddress())){
                sb.append(" 联系人地址:" + listEdit.get(i + 1).getContactAddress() + ",");
            }
            if ((listEdit.get(i).getCreditCode() == null && listEdit.get(i + 1).getCreditCode() != null)) {
                sb.append(" 社会信用码:" + listEdit.get(i + 1).getContactAddress() + ",");
            } else if ((listEdit.get(i).getCreditCode() == null && listEdit.get(i + 1).getCreditCode() == null)) {
            } else if (!listEdit.get(i).getCreditCode().equals(listEdit.get(i + 1).getCreditCode())){
                sb.append(" 社会信用码:" + listEdit.get(i + 1).getContactAddress() + ",");
            }
            if ((listEdit.get(i).getRegistAuthority() == null && listEdit.get(i + 1).getRegistAuthority() != null)) {
                sb.append(" 注册机关:" + listEdit.get(i + 1).getContactAddress() + ",");
            } else if ((listEdit.get(i).getRegistAuthority() == null && listEdit.get(i + 1).getRegistAuthority() == null)) {
            } else if (!listEdit.get(i).getRegistAuthority().equals(listEdit.get(i + 1).getRegistAuthority())) {
                sb.append(" 注册机关:" + listEdit.get(i + 1).getContactAddress() + ",");
            }
            if ((listEdit.get(i).getRegistFund() == null && listEdit.get(i + 1).getRegistFund() != null)) {
                sb.append(" 注册资本:" + listEdit.get(i + 1).getContactAddress() + ",");
            } else if ((listEdit.get(i).getRegistFund() == null && listEdit.get(i + 1).getRegistFund() == null)) {
            } else if (!listEdit.get(i).getRegistFund().equals(listEdit.get(i + 1).getRegistFund())) {
                sb.append(" 注册资本:" + listEdit.get(i + 1).getContactAddress() + ",");
            }
            if ((listEdit.get(i).getBusinessStartDate() == null && listEdit.get(i + 1).getBusinessStartDate() != null)) {
                sb.append(" 营业期限开始时间:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getBusinessStartDate()) + ",");
            } else if ((listEdit.get(i).getBusinessStartDate() == null && listEdit.get(i + 1).getBusinessStartDate() == null)) {
            } else if (!listEdit.get(i).getBusinessStartDate().equals(listEdit.get(i + 1).getBusinessStartDate())) {
                sb.append(" 营业期限开始时间:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getBusinessStartDate()) + ",");
            }
            if ((listEdit.get(i).getBusinessEndDate() == null && listEdit.get(i + 1).getBusinessEndDate() != null)) {
                sb.append(" 营业期限结束时间:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getBusinessEndDate()) + ",");
            } else if ((listEdit.get(i).getBusinessEndDate() == null && listEdit.get(i + 1).getBusinessEndDate() == null)) {
            } else if (!listEdit.get(i).getBusinessEndDate().equals(listEdit.get(i + 1).getBusinessEndDate())) {
                sb.append(" 营业期限结束时间:" + new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i + 1).getBusinessEndDate()) + ",");
            }
            if ((listEdit.get(i).getBusinessScope() == null && listEdit.get(i + 1).getBusinessScope() != null)){
                sb.append(" 营业范围:" + listEdit.get(i + 1).getBusinessScope() + ",");
            } else if ((listEdit.get(i).getBusinessScope() == null && listEdit.get(i + 1).getBusinessScope() == null)) {
            } else if (!listEdit.get(i).getBusinessScope().equals(listEdit.get(i + 1).getBusinessScope())) {
                sb.append(" 营业范围:" + listEdit.get(i + 1).getBusinessScope() + ",");
            }
            if ((listEdit.get(i).getBusinessPostCode() == null && listEdit.get(i + 1).getBusinessPostCode() != null)) {
                sb.append(" 境外分支邮编:" + listEdit.get(i + 1).getBusinessPostCode() + ",");
            } else if ((listEdit.get(i).getBusinessPostCode() == null && listEdit.get(i + 1).getBusinessPostCode() == null)) {
            } else if (!listEdit.get(i).getBusinessPostCode().equals(listEdit.get(i + 1).getBusinessPostCode())) {
                sb.append(" 境外分支邮编:" + listEdit.get(i + 1).getBusinessPostCode() + ",");
            }
            if ((listEdit.get(i).getBusinessAddress() == null && listEdit.get(i + 1).getBusinessAddress() != null)) {
                sb.append(" 分支地址:" + listEdit.get(i + 1).getBusinessAddress() + ",");
            } else if ((listEdit.get(i).getBusinessAddress() == null && listEdit.get(i + 1).getBusinessAddress() == null)) {
            } else if (!listEdit.get(i).getBusinessAddress().equals(listEdit.get(i + 1).getBusinessAddress())) {
                sb.append(" 分支地址:" + listEdit.get(i + 1).getBusinessAddress() + ",");
            }
            if ((listEdit.get(i).getOverseasBranch() == null && listEdit.get(i + 1).getOverseasBranch() != null)) {
                sb.append(" 是否是境外分支结构:" + listEdit.get(i + 1).getOverseasBranch() + ",");
            } else if ((listEdit.get(i).getOverseasBranch() == null && listEdit.get(i + 1).getOverseasBranch() == null)) {
            } else if (!listEdit.get(i).getOverseasBranch().equals(listEdit.get(i + 1).getOverseasBranch())){
                sb.append(" 是否是境外分支结构:" + listEdit.get(i + 1).getOverseasBranch() + ",");
            }
            if ((listEdit.get(i).getBranchCountry() == null && listEdit.get(i + 1).getBranchCountry() != null)) {
                sb.append(" 分支所在国家范围:" + listEdit.get(i + 1).getBranchCountry() + ",");
            } else if ((listEdit.get(i).getBranchCountry() == null && listEdit.get(i + 1).getBranchCountry() == null)) {
            } else if (!listEdit.get(i).getBranchCountry().equals(listEdit.get(i + 1).getBranchCountry())) {
                sb.append(" 分支所在国家范围:" + listEdit.get(i + 1).getBranchCountry() + ",");
            }
            if ((listEdit.get(i).getBranchAddress() == null && listEdit.get(i + 1).getBranchAddress() != null)) {
                sb.append(" 详细地址:" + listEdit.get(i + 1).getBranchAddress() + ",");
            } else if ((listEdit.get(i).getBranchAddress() == null && listEdit.get(i + 1).getBranchAddress() == null)) {
            } else if (!listEdit.get(i).getBranchAddress().equals(listEdit.get(i + 1).getBranchAddress())) {
                sb.append(" 详细地址:" + listEdit.get(i + 1).getBranchAddress() + ",");
            }
            if ((listEdit.get(i).getBranchBusinessScope() == null && listEdit.get(i + 1).getBranchBusinessScope() != null)) {
                sb.append(" 生产经营范围:" + listEdit.get(i + 1).getBranchBusinessScope() + ",");
            } else if ((listEdit.get(i).getBranchBusinessScope() == null && listEdit.get(i + 1).getBranchBusinessScope() == null)){
            } else if (!listEdit.get(i).getBranchBusinessScope().equals(listEdit.get(i + 1).getBranchBusinessScope())){
                sb.append(" 生产经营范围:" + listEdit.get(i + 1).getBranchBusinessScope() + ",");
            }
            if ((listEdit.get(i).getBranchName() == null && listEdit.get(i + 1).getBranchName() != null)) {
                sb.append(" 分支名称:" + listEdit.get(i + 1).getBranchName() + ",");
            } else if ((listEdit.get(i).getBranchName() == null && listEdit.get(i + 1).getBranchName() == null)) {
            } else if (!listEdit.get(i).getBranchName().equals(listEdit.get(i + 1).getBranchName())) {
                sb.append(" 分支名称:" + listEdit.get(i + 1).getBranchName() + ",");
            }
            String str = sb.toString();
            //大于5说明有修改否则无修改
            if (str.length() > 5) {
                list.add(str + "^-^" + new SimpleDateFormat("HH:mm:ss YYYY-MM-dd").format(listEdit.get(i + 1).getCreateDate()));
            }
        }
        return list;
    }

    @Override
    public String getProvince(String str) {
        String province = "";
        if ("Guangxi".equals(str)) {
            province = "广西";
        } else if ("Inner Mongol".equals(str)) {
            province = "内蒙古";
        } else if ("Jiangsu".equals(str)) {
            province = "江苏";
        } else if ("Chongqing".equals(str)) {
            province = "重庆";
        } else if ("Guizhou".equals(str)) {
            province = "贵州";
        } else if ("Fujian".equals(str)) {
            province = "福建";
        } else if ("Gansu".equals(str)) {
            province = "甘肃";
        } else if ("Henan".equals(str)) {
            province = "河南";
        } else if ("Hebei".equals(str)) {
            province = "河北";
        } else if ("Xinjiang".equals(str)) {
            province = "新疆";
        } else if ("Anhui".equals(str)) {
            province = "安徽";
        } else if ("Hunan".equals(str)) {
            province = "湖南";
        } else if ("Hubei".equals(str)) {
            province = "湖北";
        } else if ("Jiangxi".equals(str)) {
            province = "江西";
        } else if ("Qinghai".equals(str)) {
            province = "青海";
        } else if ("Ningxia".equals(str)) {
            province = "宁夏";
        } else if ("Taiwan".equals(str)) {
            province = "台湾";
        } else if ("Hainan".equals(str)) {
            province = "海南";
        } else if ("Sichuan".equals(str)) {
            province = "四川";
        } else if ("Shaanxi".equals(str)) {
            province = "陕西";
        } else if ("Xizang".equals(str)) {
            province = "西藏";
        } else if ("Macau".equals(str)) {
            province = "澳门";
        } else if ("Guangdong".equals(str)) {
            province = "广东";
        } else if ("Beijing".equals(str)) {
            province = "北京";
        } else if ("Shanghai".equals(str)) {
            province = "上海";
        } else if ("Zhejiang".equals(str)) {
            province = "浙江";
        } else if ("HongKong".equals(str)) {
            province = "香港";
        } else if ("Liaoning".equals(str)) {
            province = "辽宁";
        } else if ("Yunnan".equals(str)) {
            province = "云南";
        } else if ("Heilongjiang".equals(str)) {
            province = "黑龙江";
        } else if ("Shanxi".equals(str)) {
            province = "山西";
        } else if ("Shandong".equals(str)) {
            province = "山东";
        } else if ("Tianjin".equals(str)) {
            province = "天津";
        } else if ("Jilin".equals(str)) {
            province = "吉林";
        }
        return province;
    }

    @Override
    public Map<String, Object> getAllProvince() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("安徽", "an_hui");
        map.put("湖南", "hu_nan");
        map.put("湖北", "hu_bei");
        map.put("江西", "jiang_xi");
        map.put("青海", "qing_hai");
        map.put("宁夏", "ning_xia");
        map.put("台湾", "tai_wan");
        map.put("海南", "hai_nan");
        map.put("四川", "si_chuan");
        //陕西
        map.put("陕西", "shan_xi_1");
        map.put("西藏", "xi_zang");
        map.put("澳门", "ao_men");
        map.put("广东", "guang_dong");
        map.put("北京", "bei_jing");
        map.put("上海", "shang_hai");
        map.put("浙江", "zhe_jiang");
        map.put("香港", "xiang_gang");
        map.put("辽宁", "liao_ning");
        map.put("云南", "yun_nan");
        map.put("黑龙江", "hei_long_jiang");
        map.put("广西", "guang_xi");
        map.put("内蒙古", "nei_meng_gu");
        map.put("江苏", "jiang_su");
        map.put("重庆", "chong_qing");
        map.put("贵州", "gui_zhou");
        map.put("福建", "fu_jian");
        map.put("甘肃", "gan_su");
        map.put("河南", "he_nan");
        map.put("河北", "he_bei");
        map.put("新疆", "xin_jiang");
        map.put("山西", "shan_xi_2");
        map.put("山东", "shan_dong");
        map.put("天津", "tian_jin");
        map.put("吉林", "ji_lin");
        map.put("南海诸岛", "nan_hai_zhu_dao");
        return map;
    }
    
    @Override
    public Map<String, Integer> getMap() {
        Map<String, Integer> map = new HashMap<String, Integer>(40);
        map.put("安徽", 0);
        map.put("湖南", 0);
        map.put("湖北", 0);
        map.put("江西", 0);
        map.put("青海", 0);
        map.put("宁夏", 0);
        map.put("台湾", 0);
        map.put("海南", 0);
        map.put("四川", 0);
        map.put("陕西", 0);
        
        map.put("西藏", 0);
        map.put("澳门", 0);
        map.put("广东", 0);
        map.put("北京", 0);
        map.put("上海", 0);
        map.put("浙江", 0);
        map.put("香港", 0);
        map.put("辽宁", 0);
        map.put("云南", 0);
        map.put("黑龙江", 0);
        
        map.put("广西", 0);
        map.put("内蒙古", 0);
        map.put("江苏", 0);
        map.put("重庆", 0);
        map.put("贵州", 0);
        map.put("福建", 0);
        map.put("甘肃", 0);
        map.put("河南", 0);
        map.put("河北",0);
        map.put("新疆", 0);
        
        map.put("山西", 0);
        map.put("山东",0);
        map.put("天津", 0);
        map.put("吉林", 0);
        map.put("南海诸岛", 0);
        return map;
    }
}
