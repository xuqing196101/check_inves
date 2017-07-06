$(function(){
	// 页面加载完毕绑定点击事件
	// 绑定基础信息
	$("#analyzeSupplier").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzeSuppliers.html?judge=5";
		}
	});
	$("#analyzeExpert").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzeExperts.html";
		}
	});
	$("#analyzePurOrg").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzeOrgs.html";
		}
	});
	$("#analyzePurMember").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/purchaseMemList.html";
		}
	});
	
	// 绑定业务信息
	// 采购需求
	$("#analyzePurReq").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzePurchaseRequire.html";
		}
	});
	//采购计划
	$("#analyzePurPlan").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzePurchasePlan.html";
		}
	});
	$("#analyzePurProject").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzePurchaseProject.html";
		}
	});
	// 采购合同
	$("#analyzePurContract").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzePurchaseContract.html";
		}
	});
	// 采购公告
	$("#analyzePurNotice").click(function(){
		if(verifyPermission()){
			window.location.href=globalPath + "/resAnalyze/analyzePurchaseNotice.html";
		}
	});
	
	/**
	 * 返回统计界面
	 */
	$("#backAnalyzePage").click(function(){
		window.location.href=globalPath + "/resAnalyze/list.html";
	});
});

/**
 * 权限校验
 */
function verifyPermission(){
	if(typeName != "4"){
	   layer.msg("对不起，您没有权限操作！");
	   return false;
	}
	return true;
}