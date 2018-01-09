//默认加载      
$(function(){
	//绑定事件
	$("input[name='selectOption']").bind("click", function(){
		$("#cate_result").html("");
		var selectedVal = $(this).val();
		if(selectedVal == 1){
			$("#cate_result").html("复核合格。");
			return;
		}
	  if(selectedVal == '0'){
		  $("#cate_result").html("复核不合格 。");
	  }
	});
        
	//自动选中状态
	var flagAduit = $("#flagAduit").val();
	if(flagAduit !="" && flagAduit == 1){
		$("#qualified").attr("checked", "checked");
		$("#cate_result").html("复核合格。");
	}
	if(flagAduit !="" && flagAduit == 0){
		$("#unqualified").attr("checked", "checked");
		$("#cate_result").html("复核不合格 。");
	}
        
    var status = $("#status").val();
    if(status == 5 || status == 6){
    	//如果有意见就显示"重新复核"按钮，复核表
    	$("#review").removeClass("hidden");
    	$("#checkList").removeClass("hidden");
    	
    	//只读
    	$("input[type='text'],textArea").attr("disabled", true);
    	$("input[name='selectOption']").attr("disabled", true);
    	$("#downloadTable").attr("disabled", true);
    	$("button[name='isAccord']").each(function(){
   		 $(this).prop("disabled", true);
      });
    }
        
        
    //有不一致项就禁用《复核合格》
    var noPass = $("#noPass").val();
    if(noPass != 0 ){
    	$("#qualified").attr("disabled", true);
    }
}); 

//复核结束
function reviewEnd(){
	var supplierId = $("#supplierId").val();
	$.ajax({
		url: globalPath + "/supplierReview/reviewEnd.do",
		type: "post",
		data: {"supplierId" : supplierId},
		success: function(result){
			if(result.status == 200){
				//显示复核表
				$("#checkList").removeClass("hidden");
				//显示重新复审、返回按钮
				$("#review").removeClass("hidden");
				//隐藏复核结束、暂存按钮
				$("#reviewEnd").addClass("hidden");
				
				//只读
		    	$("input[type='text'],textArea").attr("disabled", true);
		    	$("input[name='selectOption']").attr("disabled", true);
		    	$("#downloadTable").attr("disabled", true);
		    	$("button[name='isAccord']").each(function(){
		    		 $(this).prop("disabled", true);
		    	});
			}else{
				layer.msg(result.msg, {offset: '100px'});
			}
		},
		error: function(){
			layer.msg("操作失败！", {offset: '100px'});
		}
	});
}


//重新复核
function restartReview(){
	var supplierId = $("#supplierId").val();
	$.ajax({
		url: globalPath + "/supplierReview/restartReview.do",
		type: "post",
		data: {"supplierId" : supplierId},
		success: function(result){
			if(result.status == 200){
				layer.msg(result.msg, {offset: '100px'});
				window.setTimeout(function() {
					$("#submitform").attr("action", globalPath + "/supplierReview/list.html");
					$("#submitform").submit();
				}, 1000);
			}else{
				layer.msg("操作失败！", {offset: '100px'});
			}
		},
		error: function(){
			layer.msg("操作失败！", {offset: '100px'});
		}
	});
}

//返回列表
function renturnList(){
	  $("#submitform").attr("action", globalPath +  "/supplierReview/list.html");
	  $("#submitform").submit();
}

//暂存/实时保存  type: 1 暂存， 2：实时保存
function temporary(type){
	var supplierId = $("#supplierId").val();
	//选择的意见
	var selectOption = $("input[name='selectOption']:checked").val();
	//手输入的意见
	var opinion = $("#opinion").val();
	if(selectOption == 1){
		opinion = "复核合格。" + opinion;
	}
	if(selectOption == 0){
		opinion = "复核不合格。" + opinion;
	}
  
	$.ajax({
		url: globalPath + "/supplierReview/temporary.do",
		type: "post",
		data: {"supplierId" : supplierId, "opinion" : opinion, "flagAduit" : selectOption},
		success: function(result){
			if(result.status == 200){
				if(type == 1){
					layer.msg(result.msg, {offset: '100px'});
				}
			}else{
				layer.msg("操作失败！", {offset: '100px'});
			}
		},
		error: function(){
			layer.msg("操作失败！", {offset: '100px'});
		}
	});
}

//下载复核表
function downloadTable (){
	$.ajax({
		url: globalPath + "/supplierReview/downloadTableCheck.do",
		data: {"supplierId" : supplierId},
		type: "post",
		success: function(result){
			if(result.status == 200){
				var supplierId = $("#supplierId").val();
				$("input[name='supplierId']").val(supplierId);
				$("#submitform").attr("action", globalPath + "/supplierReview/downloadTable.html");
			    $("#submitform").submit();
			}else{
				layer.msg(result.msg, {offset: '100px'});
			}
		},
		error: function(){
			layer.msg("操作失败！", {offset: '100px'});
		}
	});
}