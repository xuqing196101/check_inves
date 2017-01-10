package bss.service.ppms.impl;

import iss.model.ps.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.TempletMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Templet;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;

import bss.dao.ppms.NegotiationMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectMapper;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.Negotiation;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.NegotiationService;

@Service("negotiationService")
public class NegotiationServiceImpl implements NegotiationService {
    
    @Autowired
    private NegotiationMapper negotiationMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private TempletMapper templetMapper;
    
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    
    @Autowired
    private SaleTenderMapper saleTenderMapper;
    
    @Autowired
    private PackageMapper packageMapper;
    
    @Autowired
    private SupplierService supplierService;
    

    @Override
    public void deleteByPrimaryKey(String id) {
        
        negotiationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Negotiation record) {

        negotiationMapper.insertSelective(record);
    }

    @Override
    public Negotiation selectById(String id) {
        
        return negotiationMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Negotiation record) {

        negotiationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Negotiation> listByNegotiation(HashMap<String, Object> map) {
        
        return negotiationMapper.listByNegotiation(map);
    }

    @Override
    public Negotiation selectByProjectId(String id) {
        
        return negotiationMapper.selectByProjectId(id);
    }

    @Override
    public Negotiation selectByPackageId(String id) {

        return negotiationMapper.selectByPackageId(id);
    }

    @Override
    public Article getDefaultTemplates(String projectId, Article article) {
        Project project = projectMapper.selectProjectByPrimaryKey(projectId);
        List<Templet> templets = null;
        Templet templet = new Templet();
        templet.setTemType("4");
        templets = templetMapper.selectByType(templet);
        Article article1 = new Article();
        if (templets != null && templets.size() > 0) {
            String content = templets.get(0).getContent();
            String purchaseTypeName = DictionaryDataUtil.findById(project.getPurchaseType()).getName();
            Orgnization org = orgnizationMapper.findOrgByPrimaryKey(project.getPurchaseDepId());
            StringBuilder builder = new StringBuilder();
            List<SaleTender> selectListByProjectId = saleTenderMapper.selectListByProjectId(project.getId());
            for (SaleTender saleTender : selectListByProjectId) {
              Packages packages = packageMapper.selectByPrimaryKeyId(saleTender.getPackages());
              builder.append("<p>"+packages.getName()+"供应商名称:</p>");
              Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
              if(supplier != null){
                 builder.append("<p>&nbsp;&nbsp;"+supplier.getSupplierName()+"</p>");
              }
            }
            content = content.replace("supplier", builder.toString());
            content = content.replace("purchaserName", org.getName()).replace("purchaseType", purchaseTypeName).replace("projectName", project.getName()).replace("projectNum", project.getProjectNumber());
            article1.setContent(content);
        }
        return article1;
    }

}
