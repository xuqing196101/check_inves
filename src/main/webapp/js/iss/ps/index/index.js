/**
 * PV模块
 */
var pvModule = new Object({
	/**
	 * 首页今日访问量、总访问量统计
	 */
	pvFunction: function (){
		$.ajax({
			url: globalPath+"/cacheManage/getPVDate.do",
			type: "get",
			dataType: "json",
			success: function(data){
				// 今日访问量
				$("#pvThisDay").text(data.data.dayNum);
				// 总访问量
				$("#pvTotal").text(data.data.totalCount);
			}
		})
	}
});

// 页面加载完毕
$(function(){
	// 调用PV方法
	pvModule.pvFunction();
});
