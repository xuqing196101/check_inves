
/**
 * 翻页公共组件
 */
$(function() { 
	 laypage({
         cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
         pages: $("#pageNum").val(), //总页数
         skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
         skip: true, //是否开启跳页
         total: $("#pTotal").val(),
         startRow: $("#pStart").val(),
         endRow: $("#pEnd").val(),
         groups: $("#pageNum").val() >=5 ? 5 : $("#pageNum").val(), //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		       return $("#pageCurr").val()== 0 ? 1 : $("#pageCurr").val();
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	loadHtml(getPageUrl(e.curr));
		        }
		    }
		});
});
 
 /**
  * 获取到url
  * @param currPage
  * @returns {String}
  */
function getPageUrl(currPage){
	var pageUrl =  $("#url").val();
	if (pageUrl){
		if (pageUrl.indexOf("?") != -1){
			pageUrl = pageUrl + "&page=" +  currPage;
		} else {
			pageUrl = pageUrl + "?page=" +  currPage;
		}
		return pageUrl;
	}
	return "";
}



//鼠标移动显示全部内容
function titleMouseOver(content,obj){
	if (content == null || content == ""){
		return;
	}
	layer.tips(content,obj,{
		tips: [2,'#2c9fA6'],
		time:0
	});
}

function titleMouseOut(){
	layer.closeAll();
}

