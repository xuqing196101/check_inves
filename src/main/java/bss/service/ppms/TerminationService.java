package bss.service.ppms;

import java.util.List;

import bss.model.ppms.FlowDefine;

public interface TerminationService {
  public void updateTermination(String packagesId,String projectId,String currFlowDefineId,String oldCurrFlowDefineId,String type);
  
  public List<FlowDefine> selectFlowDefineTermination(String projectId);
}
