<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">

	function addRows() {
		 $("#guding").before("<tr><td><span class='star_red'>*</span>选择项名称</td><td><input onkeyup='gernerator();' ></td><td><span class='star_red'>*</span>对应分数</td><td><input onkeyup='gernerator();'></td><td class='tc'><button class='btn btn-windows delete' type=button onclick=deleteRow(this)>删除</button></td></tr>");
	}
	function deleteRow(obj) {
		  if ($("#show_table").get(0).rows.length == 5) {
		  	layer.msg("请填写数据");
		  } else {
		  	$(obj).parent().parent().remove(); 
		  }
	}

	function judge(index) {
		if (index == 0) {
			var trArr = new Array();
			trArr = $("tr");
			var i;
			for (i = 0; i < trArr.length; i++) {
				if ($(trArr[i]).hasClass("show")) {
					$(trArr[i]).removeClass("hide");
				}
			}
		} else {
			var trArr = new Array();
			trArr = $("tr");
			var i;
			for (i = 0; i < trArr.length; i++) {
				if ($(trArr[i]).hasClass("show")) {
					$(trArr[i]).addClass("hide");
				}
			}
		}
	}
	
	$(function(){
		if ('${scoreModel.isHave}' == 1) {
			var trArr = new Array();
			trArr = $("tr");
			var i;
			for (i = 0; i < trArr.length; i++) {
				if ($(trArr[i]).hasClass("show")) {
					$(trArr[i]).addClass("hide");
				}
			}
		}
	});
	
	function judgeRelationScore(index) {
	    var relation = $("#relation").find("option:checked").val();
		if (index == 1) {
			if (relation == 1) {
				//最低分
				 	$("#relationScore").val(1);
			} else {
				//最高分	
					$("#relationScore").val(0);
			}
		} else {
			if (relation == 1) {
				//最高分
				 	$("#relationScore").val(0);
			} else {
				//最低分
					$("#relationScore").val(1);
			}
		}
	}
	
	function judgeRelationScore1(index) {
		var relation = $("#addSubtractTypeName").find("option:checked").val();
		if (index == 1) {
			if (relation == 1) {
				//最低分
				$("#relationScore").val(1);
			} else {
				//最高分
				$("#relationScore").val(0);
			}
		} else {
			if (relation == 1) {
				//最高分
				$("#relationScore").val(0);
			} else {
				//最低分
				$("#relationScore").val(1);
			}
		}
	}
	

	function choseModel(){
		var model = $("#model").val();
		console.dir(model);
		$("#showParamButton").hide();
		$("#model73").hide();//隐藏区间参数table
		if(model==""){
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#show_table tbody tr").remove();
			if(addSubtractTypeName=="0"){
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}else if(addSubtractTypeName=="1"){
				$("#model22 tbody tr").clone().appendTo("#show_table tbody");
			}else{
				//默认加分实例
				$("#model21 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			//$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				$("#model72 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				$("#model82 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		} else if (model == "8") {
			$("#show_table tbody tr").remove();
			$("#model9 tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		} else if (model == "9") {
			$("#show_table tbody tr").remove();
			$("#model4B tbody tr").clone().appendTo("#show_table tbody");
			$("#showbutton").show();
		}
	}
	function modelTwoAddSubstact21(){
		var model = $("#addSubtractTypeName").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelTwoAddSubstact22(){
		var model = $("#addSubtractTypeName").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model21 tbody tr").clone().appendTo("#show_table tbody");
		}else{
			$("#show_table tbody tr").remove();
			$("#model22 tbody tr").clone().appendTo("#show_table tbody");
		}
	}
	function modelSevenAddSubstact71(){
		var model = $("#intervalTypeName71").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model71 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model72 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact72(){
		var model = $("#intervalTypeName72").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model71 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model72 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact81(){
		var model = $("#intervalTypeName81").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model81 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model82 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function modelSevenAddSubstact82(){
		var model = $("#intervalTypeName82").val();
		if(model=="0"){
			$("#show_table tbody tr").remove();
			$("#model81 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
			$("#model73").hide();
			$("#showParamButton").hide();
			$("#showbutton").show();
		}else{
			$("#show_table tbody tr").remove();
			$("#model82 tbody tr").clone().appendTo("#show_table tbody");
			$("#model73").show();
			$("#showParamButton").show();
			$("#showbutton").hide();
		}
	}
	function gernerator(){
		var model = $("#model").val();
		if(model=="0"){
			gerneratorOne();
		}else if(model=="1"){
			gerneratorTwo();
		}else if(model=="2"){
			gerneratorThree();
		}else if(model=="3"){
			gerneratorFour();
		}else if(model=="4"){
			gerneratorFive();
		}else if(model=="5"){
			gerneratorSix();
		}else if(model=="6"){
			gerneratorSeven();
		}else if(model=="7"){
			gerneratorEight();
		}else if(model=="8"){
			gerneratorNine();
		}else if(model=="9"){
			gerneratorTen();
		}
	}
	//动态添加参数区间
	var num2 =1;
	function addParamInterval(){
		var pinum = $("#num2").val();
		if(pinum>0){
			num2 = Number(pinum) + Number(1);
		}
		var tr ="";
		tr += "<tr>";
		tr += "<td class='w30'>"+num2+"</td>";
		tr += "<td ><input class='w40' type='text' id=startParam"+num2+" name='pi.startParam'></td>";
		tr += "<td ><input class='w40' type='text' id=endParam"+num2+" name='pi.endParam'></td>";
		tr += "<td ><input class='w40' type='text' id=score"+num2+" name='pi.score'></td>";
		tr += "<td ><textarea class='' id="+num2+" name='pi.explain'></textarea></td>";
		tr += "<td ><a href='javascript:void(0);' onclick='delTr(this)'>删除</a></td>";
		tr += "</tr>";
		$("#model73 tbody").append(tr);
		num2++;
	}
	function delTr(obj){
		var tr=obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
		//$(obj).parent.remove();//删除当前行   
		var num = $("#model73 tbody tr").length;
		var trs = $("#model73 tbody tr");
		console.dir(trs.find("td:eq(0)"));
		for (i = 0; i < num; i++) {
			trs.find("td:eq(0)").each(function(i) {
				$(this).text(i + 1);
			});
		}  
		num2--;
	}
	function gerneratorOne(){
		var judgeContent = $("#judgeContent").val();
		var standardScore = $("#standardScore").val();
		//var judgeNumber = $("#judgeNumber").val();
		var str = judgeContent  + " "+"是"+standardScore+"分 "+"否0分";
		$("#easyUnderstandContent1").val(str);
	}
	function gerneratorTwo(){
		var reviewParam = $("#reviewParam").val();
		var addSubtractTypeName = $("#addSubtractTypeName").val();
		var reviewStandScore = $("#reviewStandScore").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var unitScore = $("#unitScore").val();
		var unit = $("#unit").val();
		
		var type ="";
		if(addSubtractTypeName=="0"){
			type = " 加分类型" + " 每单位得" +unitScore +"分" + " 起始分值为" + reviewStandScore+"分"+" 最高分不超过"+maxScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent21").val(str);
		}else{
			type = " 减分类型" +" 基准分值为"+reviewStandScore+"分" +" 每单位减"+unitScore+"分"+" 最低分值为"+minScore+"分";
			var str = reviewParam + type ; 
			$("#easyUnderstandContent21").val(str);
		}
		
	}
	function gerneratorThree(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		//var addSubtractTypeName = $("#addSubtractTypeName").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，第一名得"+maxScore+"分,依次递减"+score+"分,最低分为"+minScore+"分";
		$("#easyUnderstandContent3").val(str);
	}
	function gerneratorFour(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，第一名得"+minScore+"分,依次递增"+score+"分,最高分为"+maxScore+"分";
		$("#easyUnderstandContent4").val(str);
	}
	function gerneratorFive(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最高为基准,得分=("+reviewParam+"/基准值)*"+standardScore;
		$("#easyUnderstandContent5").val(str);
	}
	function gerneratorSix(){
		var reviewParam = $("#reviewParam").val();
		var standardScore = $("#standardScore").val();
		var unit = $("#unit").val();
		var str = "以" + reviewParam +"最低为基准,得分=(基准值/"+reviewParam+")*"+standardScore;
		$("#easyUnderstandContent6").val(str);
	}
	function gerneratorSeven(){
		var reviewParam  = $("#reviewParam").val();
		var unit   = $("#unit").val();
		var reviewStandScore   = $("#reviewStandScore").val();
		var intervalNumber    = $("#intervalNumber").val();
		var score   = $("#score").val();
		var deadlineNumber   = $("#deadlineNumber").val();
		var maxScore   = $("#maxScore").val();
		var minScore  = $("#minScore").val();
		var str =  reviewParam +",低于" +reviewStandScore+"为0分,没增加"+intervalNumber+"加"+score+ " 最高分"+maxScore+" 最低分"+minScore+" 高于"+deadlineNumber+ "得"+maxScore+"分";
		$("#easyUnderstandContent7").val(str);
	}
	function gerneratorEight(){
		var reviewParam  = $("#reviewParam").val();
		var unit   = $("#unit").val();
		var reviewStandScore   = $("#reviewStandScore").val();
		var intervalNumber    = $("#intervalNumber").val();
		var score   = $("#score").val();
		var deadlineNumber   = $("#deadlineNumber").val();
		var maxScore   = $("#maxScore").val();
		var minScore  = $("#minScore").val();
		var str =  reviewParam +",高于" +reviewStandScore+"为"+maxScore+"分,没减少"+intervalNumber+"减"+score+ " 最低分分"+minScore+" 低于"+deadlineNumber+ "得"+minScore+"分";
		$("#easyUnderstandContent8").val(str);
	}
	
	function gerneratorNine(){
		var str = "";
		for (var i = 1; i<$("#show_table").get(0).rows.length -3 ; i++) {
			var name = $("#show_table").find("tr").eq(i).find("td").eq("1").find("input").val();
			var score = $("#show_table").find("tr").eq(i).find("td").eq("3").find("input").val();
			if (name == "" || score == "") {
			} else {
				if (name.trim() != "" && score.trim() != "") {
					str = str + name + "等于" +score + "分  ";
				}
			}
		}
		$("#easyUnderstandContent9").val(str);
	}
	
	function gerneratorTen(){
		var reviewParam = $("#reviewParam").val();
		var unit = $("#unit").val();
		var score = $("#score").val();
		var maxScore = $("#maxScore").val();
		var minScore = $("#minScore").val();
		var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，第一名得"+minScore+"分,依次递增"+score+"分,最高分为"+maxScore+"分";
		$("#easyUnderstandContent4").val(str);
	}

	function associate(){
		var text = $("#show_table").find("tr").eq("1").find("td:last").text();
		if (text == '删除') {
			var result = "";
			var standardScore = 0;
			for (var i = 1; i<$("#show_table").get(0).rows.length -3 ; i++) {
				var name = $("#show_table").find("tr").eq(i).find("td").eq("1").find("input").val();
				var score = $("#show_table").find("tr").eq(i).find("td").eq("3").find("input").val();
				if (name == "" || score == "") {
					layer.msg("请填写选择项名称");
					return;
				}
				
				if (name.trim() == "" || score.trim() == "") {
					layer.msg("请填写选择项名称");
					return;
				}
				
				if (score > standardScore) {
					standardScore = score;
				}
				result = result + name.replace(/\|/g, "").replace("-", "") + "-" +score.replace(/\|/g, "").replace("-", "") + "|";
			}
			$("#standardScore").val(standardScore);
			$("#judgeContent").val(result);
		}
	    var standScore = $("#standardScore").val();
	    var maxScore = $("#maxScore").val();
	    var id = $("#id").val();
		var s = validteModel().form();
		console.dir(s);
		if(s){
			$.ajax({   
	            type: "get",  
	            url: "${pageContext.request.contextPath}/intelligentScore/checkScore.do?standScore="+standScore+"&id="+id+"&maxScore="+maxScore+"&projectId=${projectId}"+"&packageId=${packageId}",
	            dataType:'json',
	            success:function(result){
	                  if (result == 0){
					     layer.msg("评分项已超过100分,请检查",{offset: ['150px']});     	
	                  } else {
	                  	$("#formID").attr('action','${pageContext.request.contextPath}/auditTemplat/operatorScoreModel.do').submit();
	                  }
	            },
	            error: function(result){
	                layer.msg("添加失败",{offset: ['150px']});
	            }
       		});   
			
		}else{
			return;
		} 
	}
	function pageOnLoad(){
		var model = $("#sm").val();
		$("#showParamButton").hide();
		if('${addStatus}' !=1){
			$("#model").val(model);
		}
		//console.dir(model==undefined);
		if(model !=undefined && model==""){
			$("#show_table tbody tr").remove();
		}else if(model=="0"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model1 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="1"){
			var addSubtractTypeName = $("#sm2").val();
			$("#addSubtractTypeName").val(addSubtractTypeName);
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				if(addSubtractTypeName!=undefined){
					$("#model21 tbody tr").clone().appendTo("#show_table tbody");
				}else if(addSubtractTypeName=="1"){
					$("#model22 tbody tr").clone().appendTo("#show_table tbody");
				}
			}
			$("#showbutton").show();
		}else if(model=="2"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model3 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="3"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model4 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="4"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model5 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}else if(model=="5"){
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model6 tbody tr").clone().appendTo("#show_table tbody");
			}	
			$("#showbutton").show();
		}else if(model=="6"){
			$("#show_table tbody tr").remove();
			//$("#model7 tbody tr").clone().appendTo("#show_table tbody");
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				if('${addStatus}' !=1){
					$("#model72 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73").show();
				$("#model73").append('${scoreStr}');
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				}	
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model71 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		}else if(model=="7"){
			$("#show_table tbody tr").remove();
			var intervalTypeName71 = $("#sm7").val();
			if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
				$("#showParamButton").show();
				$("#showbutton").hide();
				if('${addStatus}' !=1){
					$("#model82 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73").append('${scoreStr}');
				$("#model73").show();
			}else if(intervalTypeName71=="0"){
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}else{
				$("#showbutton").show();
				$("#showParamButton").hide();
				if('${addStatus}' !=1){
					$("#model81 tbody tr").clone().appendTo("#show_table tbody");
				}
				$("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
				$("#model73").hide();
			}
		} else if (model == "8") {
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model9 tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		} else if (model == "9") {
			$("#show_table tbody tr").remove();
			if('${addStatus}' !=1){
				$("#model4B tbody tr").clone().appendTo("#show_table tbody");
			}
			$("#showbutton").show();
		}
		
	}
</script>

<script type="text/javascript">
	//validate
	function validteModel(){
		return $("#formID").validate({
			ignore: [],
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules : {
				standardScore : {required : true,number:true},
				judgeContent : {required : true},
				judgeNumber :{required : true,number:true},
				reviewParam : {required : true},
				reviewStandScore : {required : true,number:true},
				maxScore : {required : true,number:true},
				unitScore : {required : true,number:true},
				minScore : {required : true,number:true},
				intervalNumber : {required : true,number:true},
				"pi.startParam" : {required : true,number:true},
			    "pi.endParam" : {required : true,number:true}, 
				"pi.score" : {required : true,number:true},
				reviewContent : {required : true},
				name : {required : true}
			},
			messages : {
				standardScore : {required : "该项满分值为必填项",number:"必须为数字"},
				judgeContent : {required : "该项内容为必填项"},
				judgeNumber :{required : "该项内容为必填项",number:"必须为数字"},
				reviewParam : {required : "该项内容为必填项"},
				reviewStandScore : {required : "该项内容为必填项",number:"必须为数字"},
				maxScore : {required : "该项内容为必填项",number:"必须为数字"},
				unitScore : {required : "该项内容为必填项",number:"必须为数字"},
				unit : {required : "该项内容为必填项"},
				minScore : {required : "该项内容为必填项",number:"必须为数字"},
				intervalNumber : {required : "该项内容为必填项",number:"必须为数字"},
			    "pi.startParam" : {required : "必填",number:"数字项"},
				"pi.endParam" : {required : "必填",number:"数字项"},  
				"pi.score" : {required : "必填",number:"数字项"},
				reviewContent : {required : "必填"},
				name : {required : "必填"}
			},
			showErrors: function(errorMap, errorList) {
	           $.each(this.successList, function(index, value) {
	             return $(value).popover("hide");
	           });
           	   return $.each(errorList, function(index, value) {
             		var _popover;
             		_popover = $(value.element).popover({
                    trigger: "manual",
                    placement: "top",
                    content: value.message,
                    template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
               });
             _popover.data("bs.popover").options.content = value.message;
             return _popover.popover("show");
           });
         }
		}); 
	}
</script>
    </head>
<body onload="pageOnLoad();">
 <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="javascript:void(0)">首页</a></li>
                <li><a href="javascript:void(0)">支撑系统</a></li>
                <li><a href="javascript:void(0)">后台管理</a></li>
                <li><a href="javascript:void(0)">模版管理</a></li>
                <li class="active"><a href="javascript:void(0)">新增评审指标</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
	<input type="hidden" id="sm" value="${scoreModel.typeName }">
	<input type="hidden" id="sm2" value="${scoreModel.addSubtractTypeName }">
	<input type="hidden" id="sm7" value="${scoreModel.intervalTypeName }">
	<div class="container">
		<form action="" method="post"  id="formID">
		      <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>评审指标名称：</div>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input name="name" id="name" value="${scoreModel.name}" type="text">
	                </div>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                     <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>选择模型 ：</div>
	                 <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
	                   <select id="model" name="typeName" onchange="choseModel();">
							<option value="">请选择</option>
							<option value="0">模型一A（是否判断）</option>
							<option value="8">模型一B（按项匹配分值）</option>
							<option value="1">模型二（按项加减分）</option>
							<option value="2">模型三（以评审数额最高分值为基准排序递减）</option>
							<option value="3">模型四A（以评审数额最低值为基准排序递增）</option>
							<option value="9">模型四B（按照排名递减/递增分值）</option>
							<option value="4">模型五（以评审数额最高值为基准按比例计算）</option>
							<option value="5">模型六（以评审数额最低为基准按比例计算）</option>
							<option value="6">模型七（以评审数额最低区间为基准递增排序）</option>
							<option value="7">模型八（以评审数额最高区间为基准递减排序）</option>
				    </select>
	                </div>
                  </li>
                  <li class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>评审指标内容及规则说明</div>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0">
	                  <textarea  class="col-md-12 col-sm-12 col-xs-12 h80 mb10" name="reviewContent" id="reviewContent" >${scoreModel.reviewContent}</textarea>
		            </div>
                  </li>
              </ul>
            <div class="col-md-12 col-sm-12 col-xs-12">
			<input id="projectId" name="projectId" type="hidden" value="${projectId }">
			<input id="markTermId" name="markTermId" type="hidden" value="${markTermId }">
			<c:if test="${addStatus != 1 }">
				<input id="id" type="hidden" name="id" value="${scoreModel.id }">
			</c:if>
			<input type="hidden" id="num2" value="${fn:length(scoreModel.paramIntervalList)}">
			<table class="table table-bordered mt20"  id="show_table">
				<tbody>
				</tbody>
			</table>
			<table id="model73" style="display: none;" class="table table-bordered mt20">
				<thead>
					<tr id="paramIntervalTr">
						<th class="">序号</th>
						<th class="">起始值</th>
						<th class="">结束值</th>
						<th class="">得分</th>
						<th class="">解释</th>
						<th class="">操作</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${scoreModel.paramIntervalList }" var="pi" varStatus="vs">
							<tr>
								<td align="center">${vs.index+1 }</td>
								<td align="center"><input class='w40' type='text' value="${pi.startParam }" id="startParam${vs.index+1 }" name='pi.startParam'></td>
								<td align="center"><input class='w40' type='text' value="${pi.endParam }" id="endParam${vs.index+1 }" name='pi.endParam'></td>
								<td align="center"><input class='w40' type='text' value="${pi.score }" id="score${vs.index+1 }" name='pi.score'></td>
								<td align="center"><textarea class='w40' id="explain${vs.index+1 }" name='pi.explain'>${pi.explain }</textarea></td>
								<td ><a href='javascript:void(0);' onclick='delTr(this)'>删除</a></td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</form>
	</div>
	<div class="col-md-12" id="showbutton">
		<div class="mt40 tc mb50">
			<input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	</div>
	<div class="col-md-12 hide" id="showParamButton">
		<div class="mt40 tc mb50">
			<input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="addParamInterval();" value="添加参数区间"> 
			<input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	</div>
	<!-- 八大模型 -->
	<table id="model1" class="hand table hide">
		<tbody>
		    <tr>
				<td class=" tc w300"><span class="star_red">*</span>判断内容</td>
				<td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" onkeyup="gernerator();" name="judgeContent" id="judgeContent">${scoreModel.judgeContent }</textarea></td>
				<td><span class="blue">*该项内容为判断的唯一依据</span></td>
			</tr>
			<tr>
				<td class=" tc"><span class="star_red">*</span>标准分值</td>
				<td><input name="standardScore" type="text" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
				<td><span class="blue">*该项的满分值为多少</span></td>
			</tr>
			
			<tr>
				<td class=" tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent1" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">是否判断。采购文件明确满足或不满足项的指标临界值或有无的项目要求。评审系统自动识别满足或不满足项，生成通过或否决的结果。(如：必要设备，员工人数，关键技术指标参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<table id="model21" class="hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" id="reviewParam" onkeyup="gernerator();" value="${scoreModel.reviewParam }" ></td>
				<td><span class="blue">
					*该参数代表需要供应商录入的参数<br/>
					减分例子：近三年企业在投标过程中违规次数,1项减0.5分,最高分为2分,最低得0分,其中</span><span class="red">近三年企业在投标过程中违规次数</span><span class="blue">就为评审参数<br/>
					加分例子：近五年内获得过省以上工商部门颁发知名品牌商标的数量,1项得0.5分,最多得2分,其中近<span class="red">五年内获得过省以上工商部门颁发知名品牌商标的数量</span>就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型<input type="hidden" name="judgeModel" value="2" /></td>
				<td>
					<select name="addSubtractTypeName" id="addSubtractTypeName">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
					</select>
				</td>
				
				<td><span class="blue">*该项加分类型或减分类型</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }">
				</td>
				<td><span class="blue">*最高分为多少分,[加分]类型时起始分为[最低分],最高分为此分数,[减分]类型此分数为减分基准分,依次递减</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();"  value="${scoreModel.minScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>每单位分值</td>
				<td><input name="unitScore" onkeyup="gernerator();" id="unitScore" value="${scoreModel.unitScore }">
				</td>
				<td><span class="blue">*该项为每单位的对应的值,加分每单位加多少分,减分每单位减多少分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项为评审参数的单位</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent21">${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">按项加减分。采购文件明确标准分值，加减分项，加减分值和最高最低分值限制。按照加，减分项的项目名称，系统自动识别计数，并按加减分规则计算得分。(如：技术偏离表中任何一项，正偏离，负偏离等)</span></td>
			</tr>
		</tbody>
	</table>
	
	<table id="model3" class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数名称。<br/>
					减分例子：上年度缴纳社保总金额由大至小排序评分,第一名得1分,其余依次递减0.15分,最低分为0分,其中</span><span class="red">上年度缴纳社保总金额</span><span class="blue">就为评审参数<br/>
					加分例子：上年度消费管理局罚款金额大小排序评分,第一名得0分,其余依次递增0.15分,最高分为1分,其中</span><span class="red">上年度消费管理局罚款金额</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
				<td>
					<select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
					</select>
				</td>
				<td><span class="blue">*以最高值为基准值排序递减,是加分还是减分</span></td>
			</tr>
			 <tr>
		    	<td class=" w300 tc"><span class="star_red">*</span>是否有基准数额</td>
		    	<td>
		    		<select name="isHave" id="isHave" onchange="judge(this.options[this.options.selectedIndex].value)">
		    			<option value="0" <c:if test="${scoreModel.isHave == 0}"> selected="selected"</c:if>>是</option>
		    			<option value="1" <c:if test="${scoreModel.isHave == 1}"> selected="selected"</c:if>>否</option>
		    		</select>
		    	</td>
		    	<td><span class="blue">*是否有基准数额</span></td>
		    </tr>
		    <tr class="show">
		   		 <td class=" w300 tc"><span class="star_red">*</span>基准数额</td>
		    	 <td><input name="standScores" id="standScores" value="${scoreModel.standScores }"  /></td>
		    	 <td><span class="blue">*评审数额低于（等于）[基准数额]时，[加分]类型得[最高分]，[减分]类型得[最低分],其他按照排序得分</span></td>
		    </tr>
		    <tr class="show">
		    	<td class=" w300 tc"><span class="star_red">*</span>与基准数额关系</td>
		    	<td>
		    	    <select name="relation" id="relation" onchange="judgeRelationScore1(this.options[this.options.selectedIndex].value)">
		    	           <option <c:if test="${scoreModel.relation == 0}"> selected="selected" </c:if> value="0">大于等于</option>
		    	           <option <c:if test="${scoreModel.relation == 1}"> selected="selected" </c:if> value="1">小于等于</option>
		    	     </select>
		    	</td>
		    	<td><span class="blue">*与基准数额关系,大于等于还是小于等于</span></td>
		    </tr>
		    <tr class="show">
		    	<td class=" w300 tc"><span class="star_red">*</span>关系分数</td>
		    	<td>
		    	    <select name="relationScore" id="relationScore" disabled>
		    	    	<option value="1" <c:if test="${scoreModel.relationScore == 1}"> selected="selected"</c:if> >最低分</option>
		    	     	<option value="0" <c:if test="${scoreModel.relationScore == 0}"> selected="selected"</c:if> >最高分</option>
		    	     </select>
		    	</td>
		    	<td><span class="blue">*基准数额为限制,最高分还是最低分</span></td>
		    </tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>分差</td>
				<td><input name="unitScore" onkeyup="gernerator();" id="score" value="${scoreModel.unitScore }"></td>
				<td><span class="blue">*依次排序递减/递增分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项目内容为评审参数的单位</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent3" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最高值为基准排序递减。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：业绩，销售额，资产总额，净资产，指标参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<table id="model4" class="w499 hide">
		<tbody>
			 <tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数名称。<br/>
					减分例子：碳纤维自行车重量参数由小至大排序评分,第一名得1分,其余依次递减0.15分,最低分为0分,其中</span><span class="red">碳纤维自行车重量</span><span class="blue">就为评审参数<br/>
					加分例子：矿泉水容量大小排序评分,第一名得0分,其余依次递增0.15分,最高分为1分,其中</span><span class="red">矿泉水容量</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
				<td>
					<select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
					</select>
				</td>
				<td><span class="blue">*以最高值为基准值排序递减,是加分还是减分</span></td>
			</tr>
			 <tr>
		    	<td class=" w300 tc"><span class="star_red">*</span>是否有基准数额</td>
		    	<td>
		    		<select name="isHave" id="isHave" onchange="judge(this.options[this.options.selectedIndex].value)">
		    			<option value="0" <c:if test="${scoreModel.isHave == 0} "> selected="selected"</c:if>>是</option>
		    			<option value="1" <c:if test="${scoreModel.isHave == 1}"> selected="selected"</c:if>>否</option>
		    		</select>
		    	</td>
		    	<td><span class="blue">*是否有基准数额</span></td>
		    </tr>
		    <tr class="show">
		   		 <td class=" w300 tc"><span class="star_red">*</span>基准数额</td>
		    	 <td><input name="standScores" id="standScores" value="${scoreModel.standScores }"  /></td>
		    	 <td><span class="blue">*评审数额低于（等于）[基准数额]时，[加分]类型得[最高分]，[减分]类型得[最低分],其他按照排序得分</span></td>
		    </tr>
		    <tr class="show">
		    	<td class=" w300 tc"><span class="star_red">*</span>与基准数额关系</td>
		    	<td>
		    	    <select name="relation" id="relation" onchange="judgeRelationScore1(this.options[this.options.selectedIndex].value)">
		    	           <option <c:if test="${scoreModel.relation == 0}"> selected="selected" </c:if> value="0">大于等于</option>
		    	           <option <c:if test="${scoreModel.relation == 1}"> selected="selected" </c:if> value="1">小于等于</option>
		    	     </select>
		    	</td>
		    	<td><span class="blue">*与基准数额关系,大于等于还是小于等于</span></td>
		    </tr>
		    <tr class="show">
		    	<td class=" w300 tc"><span class="star_red">*</span>关系分数</td>
		    	<td>
		    	    <select name="relationScore" id="relationScore" disabled>
		    	    	<option value="1" <c:if test="${scoreModel.relationScore == 1}"> selected="selected"</c:if> >最低分</option>
		    	     	<option value="0" <c:if test="${scoreModel.relationScore == 0}"> selected="selected"</c:if> >最高分</option>
		    	     </select>
		    	</td>
		    	<td><span class="blue">*基准数额为限制,最高分还是最低分</span></td>
		    </tr>
			
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>分差</td>
				<td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
				<td><span class="blue">*依次排序递减/递增分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项目内容为评审参数的单位</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent4" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最低值为基准排序递增。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：产品重量，包装品重量，某些工艺指标用品参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<table id="model5" class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
					*该参数代表需要供应商需要录入的参数。<br/>
					例：根据企业近三年平均资产总额评分，平均资产总额最高的为评审基准值，得分=（企业平均资产总额/基准值）*2，其中</span><span class="red">平均资产总额</span><span class="blue">就是评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>标准分值</td>
				<td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
				<td><span class="blue">*该项内容代表当前评审项的满分值是多少</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项内容为评审参数的单位,如果没有单位请为空</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent5" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最高值为基准按比例计算。采购文件明确标准分值和最低最高分限制。系统自动按公式计算得分。评审得分=(投标人数值/绝对值最高数值)×满分。(如：售后服务，合同金额，财务指标，技术指标参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<table id="model6"  class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
					*该参数代表需要供应商需要录入的参数。<br/>
					例：满足招标文件要求且报价最低得评审基准价，得分=（评审基准价/企业报价）*标准分值，其中</span><span class="red">企业报价</span><span class="blue">就是评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>标准分值</td>
				<td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
				<td><span class="blue">*该项内容代表评审项的满分值是多少</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项内容为评审参数的单位,如果没有单位请为空</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent6" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最低值为基准按比例计算。采购文件明确标准分值和最低最高分限制。系统自动按公式计算得分。评审得分=(投标人数值/绝对值最高数值)×满分。(如：售后服务，合同金额，财务指标，技术指标参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<table id="model71" class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数。<br/>
					减分例子：百公里耗油,6升（不包括此值）以下为满分,每增加1升扣0.5分,最低分为0分,其中</span><span class="red">百公里耗油</span><span class="blue">就为评审参数<br/>
					加分例子：手机按键正常次数,低于10万次（不包括此值）以下为0分,每增加1万次加0.5分,最高分为10分高于15万次,得10分,其中</span><span class="red">手机按键正常次数</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
				<td>
					<select name="addSubtractTypeName"  id="addSubtractTypeName7">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
					</select>
				</td>
				<td><span class="blue">*如果为[加分],那么低于[评审基准数]为0分,高于[评审基准数]按照规则加分;如果为[减分]，那么低于[评审基准数]为满分,高于[评审基准数]按照规则减分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分分值</td>
				<td><input name="score" id="score" onkeyup="gernerator();" value="${scoreModel.score }"></td>
				<td><span class="blue">*每个区间的分之差,加分加多少分,减分减多少分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName71" onchange="modelSevenAddSubstact71();"><option value="0" selected="selected">差额相等</option><option value="1">差额区间</option></select></td>
				<td><span class="blue">*如果每个区间差额都相等建议选用此区间类型</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>每区间等差额</td>
				<td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }"></td>
				<td><span class="blue">*该项内容为每个区间之间的差额</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审基准数</td>
				<td><input name="reviewStandScore" onkeyup="gernerator();" id="reviewStandScore" value="${scoreModel.reviewStandScore }"></td>
				<td><span class="blue">*该项内容为评审参数的参照数值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数截止数</td>
				<td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }"></td>
				<td><span class="blue">*评审参数的数额高于[截止数],如果[加分],高于[截止数]就是满分,如果[减分],高于[截止数]就是0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" onkeyup="gernerator();" id="minScore" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最低分,通常为0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }" ></td>
				<td><span class="blue">*该项内容为评审参数的单位</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent7" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最低区间为基准递增排序。采购文件明确标准分值，区间排序分差和最高最低分值限制。系统自动识别区间由低到高进行排序，并按分差计分规则计算得分。(如:汽车油耗，耗水耗电量等指标)</span></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model72"  class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数。<br/>
					减分例子：百公里耗油,6升（不包括此值）以下为满分,每增加1升扣0.5分,最低分为0分,其中</span><span class="red">百公里耗油</span><span class="blue">就为评审参数<br/>
					加分例子：手机按键正常次数,低于10万次（不包括此值）以下为0分,每增加1万次加0.5分,最高分为10分高于15万次,得10分,其中</span><span class="red">手机按键正常次数</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName72" onchange="modelSevenAddSubstact72();"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
				<td><span class="blue">*如果每个区间差额都相等建议选用此区间类型</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最低分,通常为0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项内容为评审参数的单位</span></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model81" class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数。<br/>
					减分例子：生产工序,10道（不包括此值）以上为满分,每减少2道工序扣0.5分,最低分为0分,其中</span><span class="red">生产工序</span><span class="blue">就为评审参数<br/>
					加分例子：汽车尾气排放量,高于100立方米（不包括此值）以下为0分,每减少5立方米加0.5分,最高为15分,低于50立方米得15分,其中</span><span class="red">汽车尾气排放量</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
				<td>
				<select name="addSubtractTypeName" id="addSubtractTypeName8">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
				</select>
				</td>
				<td><span class="blue">*如果为[加分],那么低于[评审基准数]为0分,高于[评审基准数]按照规则加分;如果为[减分]，那么低于[评审基准数]为满分,高于[评审基准数]按照规则减分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分分值</td>
				<td><input name="score" id="score" onkeyup="gernerator();" value="${scoreModel.score }"></td>
				<td><span class="blue">*每个区间的分之差,加分加多少分,减分减多少分</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName81" onchange="modelSevenAddSubstact81();"><option value="0" selected="selected">差额相等</option><option value="1">差额区间</option></select></td>
				<td><span class="blue">*如果每个区间差额都相等建议选用此区间类型</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>每区间等差额</td>
				<td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }"></td>
				<td><span class="blue">*该项内容为每个区间之间的差额</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审基准数</td>
				<td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }"></td>
				<td><span class="blue">*该项内容为评审参数的参照数值</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数截止数</td>
				<td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }"></td>
				<td><span class="blue">*评审参数的数额高于[截止数],如果[加分],高于[截止数]就是满分,如果[减分],高于[截止数]就是0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" onkeyup="gernerator();" id="minScore" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最低分,通常为0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项内容为评审参数的单位</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent8" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最高区间为基准递减排序。采购文件明确标准分值，区间排序分差和最高最低分值限制。系统自动识别区间由高到低进行排序，并按分差计分规则计算得分。(如:制氧量，工序等指标)</span></td>
			</tr>
			
		</tbody>
	</table>
	<table id="model82"  class="w499 hide">
		<tbody>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
				<td><span class="blue">
				*该参数代表需要录入供应商的参数。<br/>
				减分例子：生产工序,10道（不包括此值）以上为满分,每减少2道工序扣0.5分,最低分为0分,其中</span><span class="red">生产工序</span><span class="blue">就为评审参数<br/>
				加分例子：汽车尾气排放量,高于100立方米（不包括此值）以下为0分,每减少5立方米加0.5分,最高为15分,低于50立方米得15分,其中</span><span class="red">汽车尾气排放量</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*该项为评审项供应商所得最低分,通常为0分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>区间类型</td>
				<td><select name="intervalTypeName" id="intervalTypeName82" onchange="modelSevenAddSubstact82();"><option value="0">差额相等</option><option value="1" selected="selected">差额区间</option></select></td>
				<td><span class="blue">*如果每个区间差额都相等建议选用此区间类型</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项内容为评审参数的单位</span></td>
			</tr>
		</tbody>
	</table>
	
	<!-- 模型一B -->
	<table id="model9" class="w499 hide">
		<tbody>
			<tr>
				<td class="w300">评审参数<input type="hidden" name="judgeContent" id="judgeContent" /><input type="hidden" name="standardScore" id="standardScore" /></td>
				<td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam}" ></td>
				<td colspan="3"><span class="blue">*该参数代表需要供应商录入的参数</span>
			</tr>
			<c:if test="${not empty scoreModel.model1BJudgeContent}">
				<c:forEach items="${scoreModel.model1BJudgeContent}" var="se" varStatus="vs"> 
					<tr>
					<td><span class="star_red">*</span>选择项名称</td>
					<td><input value="${fn:substringBefore(se, '-')}" ></td>
					<td><span class="star_red">*</span>对应分数</td>
					<td><input value="${fn:substringAfter(se, '-')}" ></td>
					<td class="tc"><button class="btn btn-windows delete" type="button" onclick="deleteRow(this)">删除</button></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty scoreModel.model1BJudgeContent}">
				<tr>
					<td><span class="star_red">*</span>选择项名称</td>
					<td><input onkeyup="gernerator();" ></td>
					<td><span class="star_red">*</span>对应分数</td>
					<td><input onkeyup="gernerator();"  ></td>
					<td class="tc"><button class="btn btn-windows delete" type="button" onclick="deleteRow(this)">删除</button></td>
				</tr>
			</c:if>
			<tr id="guding">
				<td colspan="5" class="tc"><button class="btn btn-windows add" type="button" onclick="addRows()">添加一行</button></td>
			</tr>
			<tr>
				<td>翻译成白话文内容</td>
				<td colspan="4"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent9" >${scoreModel.easyUnderstandContent}</textarea></td>
			</tr>
			<tr>
				<td>当前模型标准解释</td>
				<td colspan="4"><span class="blue">按项匹配分值</span></td>
			</tr>
		</tbody>
	</table>	
	
	<table id="model4B" class="w499 hide">
		<tbody>
			 <tr>
				<td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
				<td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
				<td><span class="blue">
					*该参数代表需要录入供应商的参数名称。<br/>
					减分例子：碳纤维自行车重量参数由小至大排序评分,第一名得1分,其余依次递减0.15分,最低分为0分,其中</span><span class="red">碳纤维自行车重量</span><span class="blue">就为评审参数<br/>
					加分例子：矿泉水容量大小排序评分,第一名得0分,其余依次递增0.15分,最高分为1分,其中</span><span class="red">矿泉水容量</span><span class="blue">就为评审参数</span>
				</td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
				<td>
					<select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
						<option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
						<option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
					</select>
				</td>
				<td><span class="blue">*以最高值为基准值排序递减,是加分还是减分</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最高分</td>
				<td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>最低分</td>
				<td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
				<td><span class="blue">*最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
			</tr>
			<tr>
				<td class=" w300 tc"><span class="star_red">*</span>分差</td>
				<td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
				<td><span class="blue">*依次排序递减/递增分值</span></td>
			</tr>
			<tr>
				<td class=" w300 tc">单位</td>
				<td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
				<td><span class="blue">*该项目内容为评审参数的单位</span></td>
			</tr>
			
			<tr>
				<td class=" w300 tc">翻译成白话文内容</td>
				<td colspan="2"><textarea readonly="readonly" class="col-md-12 col-sm-12 col-xs-12 h80" name="easyUnderstandContent" id="easyUnderstandContent4" >${scoreModel.easyUnderstandContent }</textarea></td>
			</tr>
			<tr>
				<td class=" w300 tc">当前模型标准解释</td>
				<td colspan="2"><span class="blue">以评审数额最低值为基准排序递增。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：产品重量，包装品重量，某些工艺指标用品参数等)</span></td>
			</tr>
		</tbody>
	</table>
	<!-- 八大模型 -->
</body>
