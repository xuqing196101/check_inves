function jump(str){
  var action;
  if('${reqType}' != ''){
    if(str == "basicInfo") {
      action = globalPath + "/expertQuery/view.html?reqType=${reqType}&address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}";
    }
    if(str == "expertType") {
      action = globalPath + "/expertQuery/expertType.html?reqType=${reqType}&address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}";
    }
    if(str == "product") {
      action = globalPath + "/expertQuery/product.html?reqType=${reqType}&address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}";
    }
    if(str == "expertFile") {
      action = globalPath + "/expertQuery/expertFile.html?reqType=${reqType}&address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}";
    }
    if(str == "auditInfo") {
      action = globalPath + "/expertQuery/auditInfo.html?reqType=${reqType}&address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}";
    }
  }else{
    if(str == "basicInfo") {
      action = globalPath + "/expertQuery/view.html";
	}
    if(str=="expertType"){
      action =globalPath + "/expertQuery/expertType.html";
    }
    if(str=="product"){
      action = globalPath + "/expertQuery/product.html";
    }
    if(str=="expertFile"){
      action = globalPath + "/expertQuery/expertFile.html";
    }
    if(str=="auditInfo"){
      action = globalPath + "/expertQuery/auditInfo.html";
    }
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}