$(function(){
	// 页面加载完毕绑定点击事件
	// 绑定基础信息
	$("#analyzeSupplier").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzeSuppliers.html?judge=5";
	});
	$("#analyzeExpert").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzeExperts.html";
	});
	$("#analyzePurOrg").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzeOrgs.html";
	});
	$("#analyzePurMember").click(function(){
		window.location.href=globalPath + "/resAnalyze//purchaseMemList.html";
	});
	
	// 绑定业务信息
	$("#analyzePurReq").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzePurchaseProject.html";
	});
	$("#analyzePurPlan").click(function(){
		window.location.href=globalPath + "/";
	});
	$("#analyzePurProject").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzePurchaseProject.html";
	});
	$("#analyzePurContract").click(function(){
		window.location.href=globalPath + "/resAnalyze/analyzePurchaseContract.html";
	});
	$("#analyzePurNotice").click(function(){
		window.location.href=globalPath + "/";
	});
});