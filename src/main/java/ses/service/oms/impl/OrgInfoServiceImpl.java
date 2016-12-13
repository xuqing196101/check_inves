package ses.service.oms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.StaticVariables;

import ses.dao.oms.OrgInfoMapper;
import ses.model.oms.OrgInfo;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgInfoService;

@Service("OrgInfoService")
public class OrgInfoServiceImpl implements OrgInfoService {
    @Autowired
    private OrgInfoMapper infoMapper;

    @Override
    public OrgInfo selectByPrimaryKey(String id) {
        return infoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrgInfo> listByAll(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        return infoMapper.listByAll(map);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        infoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(OrgInfo orgInfo) {
        // TODO Auto-generated method stub
        infoMapper.updateByPrimaryKeySelective(orgInfo);
    }

    @Override
    public void insertSelective(OrgInfo orgInfo) {
        // TODO Auto-generated method stub
        infoMapper.insertSelective(orgInfo);
    }

    @Override
    public List<OrgInfo> selectedInfo(String[] purchaseUnitName, String[] purchaseUnitDuty) {
        List<OrgInfo> purchaseOrgList = new ArrayList<OrgInfo>();
        if(purchaseUnitName != null || purchaseUnitDuty != null){
            if(purchaseUnitName.length > 1 || purchaseUnitDuty.length > 1){
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                    OrgInfo orgInfo = new OrgInfo();
                    orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                    orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                    purchaseOrgList.add(orgInfo);
                }
            }else{
                for (int i = 0; i < purchaseUnitName.length; i++ ) {
                OrgInfo orgInfo = new OrgInfo();
                orgInfo.setPurchaseUnitName(purchaseUnitName[i]);
                orgInfo.setPurchaseUnitDuty(purchaseUnitDuty[i]);
                purchaseOrgList.add(orgInfo);
                }
            }
        }
        return purchaseOrgList;
    }

}
