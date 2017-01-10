package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.NegotiationReportMapper;
import bss.model.ppms.NegotiationReport;
import bss.service.ppms.NegotiationReportService;

@Service("negotiationReportService")
public class NegotiationReportServiceImpl implements NegotiationReportService {
    @Autowired
    private NegotiationReportMapper negotiationReportMapper;

    @Override
    public void deleteByPrimaryKey(String id) {

        negotiationReportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(NegotiationReport record) {

        negotiationReportMapper.insertSelective(record);
    }

    @Override
    public NegotiationReport selectById(String id) {

        return negotiationReportMapper.selectByPrimaryKey(id);
    }

    @Override
    public NegotiationReport selectByProjectId(String id) {
        
        return negotiationReportMapper.selectByProjectId(id);
    }

    @Override
    public void update(NegotiationReport record) {

        negotiationReportMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<NegotiationReport> listByNegotiation(HashMap<String, Object> map) {
        
        return negotiationReportMapper.listByNegotiation(map);
    }

    @Override
    public NegotiationReport selectByPackageId(String id) {
        
        return negotiationReportMapper.selectByPackageId(id);
    }

}
