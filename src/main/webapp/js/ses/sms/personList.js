 
/**
 * 人员信息 监督人员 抽取人员 公共js
 */
var personType ;
  	//加载人员列表
	$(function() {
		//人员类型
		personType = $("#personType").val();
  		//getPersonList();
  	}); 
  	
  	 //全选全不选
    function checkAll(obj){
    	$(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
    }
  	
  	
  	function getPersonList(){
  		 $("tbody").empty();
  		$.ajax({
            type: "POST",
            url: globalPath+"/"+personType+"/getPeronList.do?personType="+personType,
            data: $("form").serialize(),
            dataType: "json",
            success: function (data) {
            	for(var i=0;i<data.length;i++){
            	 var tr = "<tr>"+
		              "<td class='tc h30 lh30'> <input type='checkbox' name='id' value="+data[i].id+"> </td>"+
		              "<td class='tc h30 lh30'> "+(i+1)+" </td>"+
		              "<td class='tc h30 lh30'> "+data[i].name+" </td>"+
	              	  "<td class='tc h30 lh30'>"+data[i].compary+" </td>"+
	              	  "<td class='tc h30 lh30'>"+data[i].duty+" </td>"+
	              	  "<td class='tc h30 lh30'> "+data[i].rank+"</td>"+
		              "</tr>";
		            $("tbody").append(tr);
            	}
            }
        });
  	} 
  	 
        function chosePerson(obj){
        	//当前已添加的人员序号
			//var index = $(obj).parents("form").find("tr:last").find("td:eq(1)").html();
			//if(null==index ||''==index || "undefined"== index){
			//	index=0;
			//}
        	var index = 0;
			var j = 1;
			var str = '';
			$("#personList").find(":checked").each(function(){
				//选中的复选框
				 var checkbox_now = $(this);
				//人员类表中选中的每一行
				var tableTr = $(this).parent().next();
				//判断是否输入信息是否有相同的，有则替换，无则继续添加
				var list = $(obj).parents("form").find("tr");
				for ( var i = 0; i < list.length; i++) {
					var p = list[i];
					//遍历form中的信息
					if(checkbox_now.val() == $(p).find(":checkbox").val()){
						return true;
					}else if($(p).find(":checkbox").val() == null || $(p).find(":checkbox").val() == '' || $(p).find(":checkbox").val() == "undefined"){
					
						var count = 0;
						if($(p).find("[name='name']").val() == tableTr.find("td :eq(2)").html()){
							count ++;
						}
						if($(p).find("[name='compary']").val() == tableTr.find("td :eq(3)").html()){
							count ++;
						}
						if($(p).find("[name='duty']").val() == tableTr.find("td :eq(4)").html()){
							count ++;
						}
						if($(p).find("[name='rank']").val() == tableTr.find("td :eq(5)").html()){
							count ++;
						}
						
						if(count == 4){
							tableTr.find("td :eq(1)").html($(p).find("td :eq(1)").html());
							$(this).after(tableTr);
							$(this).remove();
							return true;
						}
					}
				}
				tableTr.html(parseInt(index)+j);
				str += '<tr>'+$(this).parents("tr").html()+'</tr>';
				j++;
			});
			
			$(obj).parents("form").find("tbody tr").each(function() {
				$(this).find('td').eq(1).html(j);
				j++;
			});
			$(obj).parents("form").find("tbody").prepend(str);
			var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
	        parent.layer.close(layerIndex);
        }
  	