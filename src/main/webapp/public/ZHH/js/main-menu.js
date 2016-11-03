
/**
 * 点击按钮ajax加载url
 * @param url  服务器的请求url
 */
var homeUrl = globalPath + "/login/home.html";
function loadHtml(url){
	  var hash=location.hash; 
	  if(!isNull(hash)){  
	      loadActivePageByHash(url); //调用load方法，执行前进 后退  
	  }else {
		  initPage(url);
	  }
}
/**
 * 保存历史记录
 */
		
function loadHistory(){
	$(window).hashchange( function(){
		var hash =  location.hash.replace('#!',"");
		if (!isNull(hash)){
			loadPage(hash);
		}else{
			initPage(homeUrl);
		}
    })
    $(window).hashchange();
}
/**
 * 动态的加载按钮
 * @param url 菜单的URL
 */
function loadActivePageByHash(url){
	var hash = location.hash="#!"+ url;
	loadPage(url);
}

/**
 * 初始化默认界面
 * @param url 请求的url地址
 */
function initPage(url){
	var hash = location.hash="#!"+ url;
	loadPage(url);
}

/**
 * @param obj 判断的字符串
 * @returns {Boolean} 如果为空,返回true,否则返回true
 */
function isNull(obj){
	if (obj == null || obj == "" || obj == "undefined"){
		return true;
	}
	return false;
}

/**
 * 加载一个页面到div中
 * @param url
 */
function loadPage(url){
	var params = parseQueryString(url);
	if (!$.isEmptyObject(params)){
		url = url.split("?")[0];
		$("#homeDivId").load(url+"#body",params);
	} else {
		$("#homeDivId").load(url+"#body");
	}
}


function parseQueryString (url) {
	 var reg_url = /^[^\?]+\?([\w\W]+)$/,
	 reg_para = /([^&=]+)=([\w\W]*?)(&|$|#)/g,
	 arr_url = reg_url.exec(url),
	 ret = {};
	 if (arr_url && arr_url[1]) {
		 var str_para = arr_url[1], result;
		 while ((result = reg_para.exec(str_para)) != null) {
		   ret[result[1]] = result[2];
		  }
	 }
	 return ret;
}
