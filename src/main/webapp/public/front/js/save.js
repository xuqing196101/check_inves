/**
 * 按照样式保存
 */
$(function(){
	var obj = $(".save");
	if (obj){
		var clickEvent = $(obj).attr("onclick");
		var url = $("form").attr("action");
		if (!clickEvent){
			$(".save").click(function(){
				saveAjax(url);
			});
		}
	}
}); 

/**
 * ajax同步保存
 * @param url
 */
function saveAjax(url){
	$.ajax({
		url:url ,
		type: 'POST',
		data:$("form").serialize(),
	    async: false,
	    success:function(data){
	    	loadHtml(globalPath + '/'+ data + ".html");
	    }
	});
}

