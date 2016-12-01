/**
 * 加载城市
 */
function loadCity(){
	var provincedId = $("#provinceId").val();
	$.ajax({
		type : 'post',
		url :  globalPath + "/purchaseManage/getProvinceList.do",
		data : {pid:provincedId},
		dataType:'json',
		success : function(data) {
			loadCityData(data);
		}
	});
}

/***
 * 加载城市
 * @param data
 * @returns
 */
function loadCityData(data){
	$("#cityId").empty();
	for (var i=0;i<data.length;i++){
		$("#cityId").append("<option value="+ data[i].id +">"+data[i].name+"</option>");
	}
}