package ses.service.oms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgLocaleMapper;
import ses.model.oms.OrgInfo;
import ses.model.oms.OrgLocale;
import ses.service.oms.OrgLocaleService;

@Service("orgLocaleService")
public class OrgLocaleServiceImpl implements OrgLocaleService {
    @Autowired
    private OrgLocaleMapper localeMapper;

    @Override
    public OrgLocale selectByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        return localeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrgLocale> listByAll(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        return localeMapper.listByAll(map);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        // TODO Auto-generated method stub
        localeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(OrgLocale orgLocale) {
        // TODO Auto-generated method stub
        localeMapper.updateByPrimaryKeySelective(orgLocale);
    }

    @Override
    public void insertSelective(OrgLocale orgLocale) {
        // TODO Auto-generated method stub
        localeMapper.insertSelective(orgLocale);
    }


    @Override
    public List<OrgLocale> selectedInfo(String[] siteType, String[] siteNumber, String[] location,
         String[] area, String[] crewSize) {
        List<OrgLocale> purchaseOrgList = new ArrayList<OrgLocale>();
        if(siteType != null || siteNumber != null || location != null || area != null || crewSize != null){
            if(siteType.length > 1 || siteNumber.length > 1 || location.length > 1 || area.length > 1|| crewSize.length > 1){
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    purchaseOrgList.add(locale);
                }
            }else{
                for (int i = 0; i < siteNumber.length; i++ ) {
                    OrgLocale locale = new OrgLocale();
                    locale.setArea(area[i]);
                    locale.setCrewSize(crewSize[i]);
                    locale.setLocation(location[i]);
                    locale.setSiteNumber(siteNumber[i]);
                    locale.setSiteType(siteType[i]);
                    purchaseOrgList.add(locale);
                }
            }
        }
        return purchaseOrgList;
    }

}
