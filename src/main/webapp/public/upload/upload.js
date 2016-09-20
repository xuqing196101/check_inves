/*$(function() {
	
	$("input[type=file]").change(function() {
		$(this).parents(".uploader").find(".filename").val($(this).val());
	});

	$("input[type=file]").each(function(){
		if($(this).val()=="") {
			$(this).parents(".uploader").find(".filename").val("未选择任何文件...");
		}
	});
	
});*/

(function($) {
	$("input[type=file]").change(function() {
		$(this).parents(".uploader").find(".filename").val($(this).val());
	});

	$("input[type=file]").each(function(){
		if($(this).val()=="") {
			$(this).parents(".uploader").find(".filename").val("未选择任何文件...");
		}
	});
}(jQuery));