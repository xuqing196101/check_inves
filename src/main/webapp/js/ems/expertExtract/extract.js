$(function() {
	// 地区
	$.ajax({
		url : globalPath + "/area/listByOne.do",
		dataType : "json",
		success : function(obj) {
			$.each(obj, function(i, result) {
				$("#province").append("<option value='" + result.id + "'>" + result.name + "</option>");
			});
		}
	});
});

// 加载地区
function functionArea() {
	var parentId = $("#province").val();
	$.ajax({
		url : globalPath + "/area/find_by_parent_id.do",
		data : {
			"id" : parentId
		},
		dataType : "json",
		async : false,
		success : function(response) {
			$("#city").empty();
			$("#city").append("<option value=''>选择地区</option>");
			$.each(response, function(i, result) {
				$("#city").append("<option value='" + result.id + "'>" + result.name + "</option>");
			});
		}
	});
}