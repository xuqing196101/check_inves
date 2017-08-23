 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
			$(function() {
				$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
					// 获取已激活的标签页的名称
					var activeTab = $(e.target).text();
					// 获取前一个激活的标签页的名称
					var previousTab = $(e.relatedTarget).text();
				});
			})



			function view(obj) {

			   window.location.href="${pageContext.request.contextPath}/look/views.html?id="+obj+"&type=required";
			}

			function aadd() {
				var s = $("#count").val();
				s++;
				$("#count").val(s);
				var tr = $("input[name=dyadds]").parent().parent();
				$(tr).before("<tr><td class='tc'><input type='text' name='list[" + s + "].seq' /></td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].department' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
					"<td class='tc'> <input style='border: 0px;'type='text' name='list[" + s + "].isFreeTax' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
					+"<tr/>");
			}

			function sets() {
				var id = $("#cid").val();
				submitForm()//审核人员调整前先保存审核数据
				window.location.href = "${pageContext.request.contextPath}/set/list.html?id=" + id+"&backAttr=ok";//backAttr是判断请求来源的标识
			}
		    //异步提交表单,审核人员调整前先保存审核数据
		    function submitForm(){
		    	 $.ajax({  
		    		    type: "POST",  
		    		    url:"${pageContext.request.contextPath}/look/submitForm.html",  
		    		    data:$('#audit_form').serialize(),  
		    		    async: false,  
		    		    error: function(request) {  
		    		        alert("暂存审核数据出错!");  
		    		    },  
		    		    success: function(data) {  
		    		          
		    		    }  
		    		  });
		    }
			function sel(obj) {
				var val = $(obj).val();
				$("select option").each(function() {
					var opt = $(this).val();
					if(val == opt) {
						$(this).attr("selected", "selected");
					}
				});
			}

			function ss() {
				var value = $("#reson").val();
				if(value != null && value != "") {
					$("#status").val(3);
					$("#acc_form").submit();
				} else {
					layer.tips("退回理由不允许为空", "#reson");
				}
			}
			/* 	function ss(obj){
			  	   var val=$(obj).val();
			  	   $("select option").each(function(){
			  		   var opt=$(this).val();
			  		   if(val==opt){
			  			   $(this).attr("selected", "selected");  
			  		   }
			  	   });
			     } */

			/* function org(obj){
    		 
    	   var val=$(obj).val();
    
    	   $(".org option").each(function(){
    	 
    		   var opt=$(this).val();
    		   if(val==opt){
    			   $(this).attr("selected", "selected");  
    		   }
    	   });
       } */

			var flag = true;
			function checks(obj) {
				var name = $(obj).attr("name");
				var planNo = $("#pNo").val();
				var val = $(obj).val();
				var defVal = obj.defaultValue;
				if(val != defVal) {
					$.ajax({
						url: "${pageContext.request.contextPath}/adjust/filed.html",
						type: "post",
						data: {
							planNo: planNo,
							name: name
						},
						success: function(data) {
							if(data == 'exit') {
								flag = false;
								layer.tips("该字段不允许修改", obj);
							}
						}
					});
				}
			}

			function audits() {
				if(flag == true) {
					$("#audit_form").submit();
				}
			}
			
			//只能输入数字
			function checkNum(obj,num){
				var vals = $(obj).val();
				var reg = /^\d+\.?\d*$/;  
				if(!reg.exec(vals)){
					$(obj).val("");
				}else{
					if(num==1){
						var count = $(obj).val();
						var price = $(obj).parent().next().find("input").val();
						$(obj).parent().next().next().find("input").val(count*price);
					}else if(num==2){
						var count = $(obj).parent().prev().find("input").val();
						var price = $(obj).val();
						$(obj).parent().next().find("input").val(count*price);
					}
				}
				
			}
			
				//单价
	 
		  function sum2(obj){  //数量
			  change(obj);
	        var purchaseCount = $(obj).val()-0;//数量
	        var price2 = $(obj).parent().next().children(":last").prev();//价钱
	        var price = $(price2).val()-0;
	        var sum = purchaseCount*price/10000;
	        var budget = $(obj).parent().next().next().children(":last").prev();
	        $(budget).val(sum);
	      	var id=$(obj).next().val(); //parentId
	      	aa(id);
	    } 
	    
	       function sum1(obj){
	    	   change(obj);
	        var purchaseCount = $(obj).val()-0; //价钱
	         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
	      	 var sum = purchaseCount*price2/10000;
	         $(obj).parent().next().children(":last").prev().val(sum);
		     	var id=$(obj).next().val(); //parentId
		     	aa(id);
	    }
	
	       function aa(id){// id是指当前的父级parentid
	    	  
	    	   
	    	   var budget=0;
	    	   $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val(); //parentId
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0; //价格
		 	       if(id==cid){
		 	    	 
		 	    	  budget=budget+same; //查出所有的子节点的值
		 	       }
	    	   });
	    	   budget = budget.toFixed(2); 
	    	   var bud;
	     
	    	    $("#table tr").each(function(){
		    	  var  pid= $(this).find("td:eq(8)").children(":first").val();//上级id
		    		
		    		if(id==pid){
		    			$(this).find("td:eq(8)").children(":first").next().val(budget);
		    			 var spid= $(this).find("td:eq(8)").children(":last").val();
		    			bud= calc(spid);
		    		}  
	    		}); 
	    /*     $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":first").val(); //的值
	 	    		   if(id==cid){ */
	 	    			 //  var sameBud=$(this).find("td:eq(8)").children(":last").next().val();
	 	    			   // alert(sameBud);
	 	    			 //   var   pid= $(this).find("td:eq(8)").children(":first").val();  
	 	    		  	    /*  calc(id); */
	 	    			   
	 	    		 //  $(this).find("td:eq(8)").children(":first").next().val(budget);
	 	    		 /*  } 
	 	    		}); */    
	    	     
	    	  var did=$("#table tr:eq(1)").find("td:eq(8)").children(":first").val();
	    	    var total=0;
	    	    $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
	 	    		 if(did==cid){
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	    $("#table tr:eq(1)").find("td:eq(8)").children(":first").next().val(total);
	       }   
	       
        function calc(id){
        	var bud=0;
	 	   	    $("#table tr").each(function(){
	 	   	           var pid= $(this).find("td:eq(8)").children(":last").val() ;
		 	   	       if(id==pid){
		 	   	         	var currBud=$(this).find("td:eq(8)").children(":first").next().val()-0;
		 	   	            bud=bud+currBud;
		 	   	            bud = bud.toFixed(2);
		 	   	            
		 	   	              var spid= $(this).find("td:eq(8)").children(":last").val();
		 	   	              aa(spid);
		 	   	             /*  var did= $(this).find("td:eq(8)").children(":first").val();
			 	   	           if(did=='1'){
			 	   	        	  return bud; 
			 	   	            }  
			 	   	 		    calc(spid); */ 
		 	   	           
		 	   	        // 	$(this).find("td:eq(8)").children(":first").next().val(budget);
		 	   	      }
	     		}); 
	 	    	   
	 	     }  
        
        
					
					
				// var defVal = obj.defaultValue;获得默认值
				
				function change(obj){
					var status=$("input[name='status']").val();
				
					var tr=obj.parentNode.parentNode;
					var val = $(obj).val();
					var defVal = obj.defaultValue;
					var index=tr.rowIndex; //获取第几行，然后给赋值
					var td=obj.parentNode;
					var tdIndex=td.cellIndex;
					var tdVal1= $("#table tr").find("th:eq("+tdIndex+")").text()+"：";
			  	   if(val!=defVal){
			  		
			  		   if(status=='3'){
			  			   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
				  			var curval=tdVal1+"由"+defVal+"变成"+val;
				  			var newVal=splitS(inpval,defVal,curval,val);
			  			 $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal); 
			  		   }
			  		 if(status=='5'){
			  		   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
			  			var curval=tdVal1+"由"+defVal+"变成"+val;
			  			var newVal=splitS(inpval,defVal,curval,val);
				  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
				  	   }
				  	 if(status=='7'){
				  	   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
			  			var curval=tdVal1+"由"+defVal+"变成"+val;
			  			var newVal=splitS(inpval,defVal,curval,val);
				  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
				  	   }  
					} 
				  
			  	 var tdVal2= $("#table tr").find("th:eq("+tdIndex+")").text();
			  	tdVal2=$.trim(tdVal2);
			  	  if(tdVal2=="采购数量"){
			  		var histVal = $(obj).val();
		            var  hisId=$(obj).prev().val();
		    		var hisVal = obj.defaultValue;
		    		if(histVal != hisVal) {
		    			var hisTd= $(obj).parent().parent().children(":last");
		    			$(hisTd).prev().children(":last").prev().val(hisId);
		    		}else{
		    			 $(obj).parent().parent().children(":last").prev().children(":last").prev().val("");
		    		} 
			  	 }else if(tdVal2=="单价（元）"){
				  		var histVal = $(obj).val();
			            var  hisId=$(obj).prev().val();
			    		var hisVal = obj.defaultValue;
			    		if(histVal != hisVal) {
			    			 $(obj).parent().parent().children(":last").prev().children(":last").val(hisId);
			    		}else{
			    			 $(obj).parent().parent().children(":last").prev().children(":last").val("");
			    		} 
				  	 }
			  	  
			  	 else 	if(tdVal2!="采购数量"&&tdVal2!="单价（元）"){
			  		var histVal = $(obj).val();
		            var  hisId=$(obj).prev().val();
		    		var hisVal = obj.defaultValue;
		    		if(histVal != hisVal) {
		    			$(obj).next().val(hisId);
		    		}else{
		    			$(obj).next().val("");
		    		} 
			  	 } 
		    		
				}
				
				function typeChange(obj){
					var status=$("input[name='status']").val();		
					var val = $(obj).find("option:selected").text();
					var defVal;
					var defValue;
		/* 			
					var opts=obj.getElementsByTagName('option'); 
						for (var i in opts) {
							if (opts[i].defaultSelected) {
								defVal = opts[i].text;
								break;
							}
						}
						var tr=obj.parentNode.parentNode;	
						var index=tr.rowIndex; //获取第几行，然后给赋值
						var td=obj.parentNode;
						var tdIndex=td.cellIndex;
						var tdVal1= $("#dep_table tr:eq(1)").find("th:eq("+tdIndex+")").text();
						 if(val!=defVal){
							  if(status=='3'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							       $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal);
							  }
							  if(status=='5'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
							  	   }
							 if(status=='7'){
								   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
							  	 }
							 
						}  */
						var flg=false;
						 var org=$(obj).val();
						 var price=$(obj).parent().prev().prev().prev().prev().val();
						 if(price==""){
							var id=$(obj).prev().val();
						 	  $.ajax({
						          url: "${pageContext.request.contextPath}/accept/detail.html",
						          data: "id=" + id,
						          type: "post",
						          dataType: "json",
						          success: function(result) {
						            for(var i = 0; i < result.length; i++) {
						                var v1 = result[i].id;
						                $("#table tr").each(function(){
						      			  var opt= $(this).find("td:eq(10)").children(":first").val() ;
						      	 		   if(v1==opt){
						      	 			 var td=$(this).find("td:eq(10)");
						      	 			var options= $(td).find("option");
							      	 		  $(options).each(function(i){
							      	 			defValue=$(this).parent().parent().parent().children(":last").children(":first").val();
							      	 			var dValue=$(this).val();
							      	 			if (defValue==dValue) {
													     defVal = options[i].text;
												   }
							      	 		  });
							      	 		$(options).each(function(i){
							      	 			var opt=$(this).val();
							      	  		   if(org==opt){
							      	  			$(this).prop("selected",true);
							      	  		  var o = this;
						      	  			   var tr=o.parentNode.parentNode.parentNode;	
												var index=tr.rowIndex; //获取第几行，然后给赋值
												var td=o.parentNode.parentNode;
												var tdIndex=td.cellIndex;
												var tdVal1= $("#table tr").find("th:eq("+tdIndex+")").text()+"：";
							      	  		 if(org!=defValue){
							      	  		 var pid=$(this).parent().prev().val();
					      	  	     		 $(this).parent().next().val(pid);
							      	  		   if($.trim(defVal)==""||defVal=="undefined"){
						      	  	     		   defVal="空值";
						      	  	     		 }  
												  if(status=='3'){
													  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
									 		  			var curval=tdVal1+"由"+defVal+"变成"+val;
									 		  			var newVal=splitS(inpval,defVal,curval,val);
												       $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal);
												  }
												  if(status=='5'){
													  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
											  			var curval=tdVal1+"由"+defVal+"变成"+val;
											  			var newVal=splitS(inpval,defVal,curval,val);
												  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
												  	   }
												 if(status=='7'){
													   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
											  			var curval=tdVal1+"由"+defVal+"变成"+val;
											  			var newVal=splitS(inpval,defVal,curval,val);
												  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
												  	 }
												 
											}else{
												$(this).parent().next().val("");
											} 
							      	  			  //  $(this).attr("selected", "selected");  
							      	  		   }else{
							      	  		     $(this).removeAttr("selected");
							      	  		   }
								      	  	   });
						      	 		   }  
						      	 	   });
						            }
						           }
						          });
						          
						          
						 }	 
						  	 
						  	 
				}
				
				function orgChange(obj){
					var status=$("input[name='status']").val();		
					var val = $(obj).find("option:selected").text();
					var defVal;
					var defValue;
				/* 	var opts=obj.getElementsByTagName('option'); 
						for (var i in opts) {
							if (opts[i].defaultSelected) {
								defVal = opts[i].text;
								break;
							}
						}
						var tr=obj.parentNode.parentNode;	
						var index=tr.rowIndex; //获取第几行，然后给赋值
						var td=obj.parentNode;
						var tdIndex=td.cellIndex;
						var tdVal1= $("#dep_table tr:eq(1)").find("th:eq("+tdIndex+")").text();
						 if(val!=defVal){
							  if(status=='3'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							       $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal);
							  }
							  if(status=='5'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
							  	   }
							 if(status=='7'){
								   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
							  	 }
							 
						}  */
						 
						 var org=$(obj).val();
						 var price=$(obj).parent().prev().prev().prev().prev().val();
						 if(price==""){
							var id=$(obj).prev().val();
						 	  $.ajax({
						          url: "${pageContext.request.contextPath}/accept/detail.html",
						          data: "id=" + id,
						          type: "post",
						          dataType: "json",
						          success: function(result) {
						            for(var i = 0; i < result.length; i++) {
						                var v1 = result[i].id;
						                $("#table tr").each(function(){
						      			  var opt= $(this).find("td:eq(11)").children(":first").val() ;
						      	 		   if(v1==opt){
						      	 			 var td=$(this).find("td:eq(11)");
						      	 			var options= $(td).find("option");
							      	 		  $(options).each(function(i){
							      	 			defValue=$($(this).parent().parent().parent().children(":last").children()[1]).val();
							      	 			var dValue=$(this).val();
							      	 			if (defValue==dValue) {
													defVal = options[i].text;
												}
							      	 		  })
							      	 			$(options).each(function(i){
							      	 			
							      	  		   var opt=$(this).val();
							      	  		   if(org==opt){
							      	  			$(this).prop("selected",true);
							      	  			   //  $(this).attr("selected", "selected");  
							      	  			  var o = this;
							      	  			   var tr=o.parentNode.parentNode.parentNode;	
													var index=tr.rowIndex; //获取第几行，然后给赋值
													var td=o.parentNode.parentNode;
													var tdIndex=td.cellIndex;
													var tdVal1= $("#table tr").find("th:eq("+tdIndex+")").text()+"：";
						
							      	  	     	 if(org!=defValue){
							      	  	     		 var pid=$(this).parent().prev().val();
							      	  	     		 $(this).parent().next().val(pid);
							      	  	     	   if($.trim(defVal)==""||defVal=="undefined"){
							      	  	     		   defVal="空值";
							      	  	     		 }  
												  if(status=='3'){
													  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
													  var curval=tdVal1+"由"+defVal+"变成"+val;
													  var newVal=splitS(inpval,defVal,curval,val);
												      $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal);
												  }
												  if(status=='5'){
													  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
											  			var curval=tdVal1+"由"+defVal+"变成"+val;
											  			var newVal=splitS(inpval,defVal,curval,val);
												  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
												  	   }
												 if(status=='7'){
													   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
											  			var curval=tdVal1+"由"+defVal+"变成"+val;
											  			var newVal=splitS(inpval,defVal,curval,val);
												  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
												  	 }
												 
											}else{
												$(this).parent().next().val("");
											} 
							      	  		   }else{
							      	  			
							      	  			$(this).removeAttr("selected");
							      	  		   }
								      	  	   });
						      	 		   }  
						      	 	   });
						            }
						           }
						          });
						          
						          
						 } 	 
						  	 
				}
				
		   function splitS(inpval,defVal,curval,val){
			   var newVal="";
			   if(inpval.length>0){
				    var flg=false;
		  			var str=inpval.split("；");
		  			for(var i=0;i<str.length;i++){
		  				if(str[i].indexOf("由"+defVal+"变成")>0){
		  					str[i]=curval;
		  					flg=true;
		  					break;
		  				}
		  			}
		  			if(flg){
		  				newVal=str.join("；");
		  			}else{
		  				newVal=str.join("；")+curval+"；";
		  			}
		  		}	else{
		  			newVal=inpval+curval+"；";
		  		}
			   return newVal;
		   }
				function eavlChildren(obj){
					
				}
				
				function downFiles(id){
					$.ajax({
						url: "${pageContext.request.contextPath}/purchaser/getfile.html",
						type: "post",
						data:{"id":id},
						success: function(data) {
						
						 	if($.trim(data)!=""){
						 		download(data,2,null,null);	
						 	}else{
						 		layer.alert("文件未上传",{offset: ['222px', '390px'], shade:0.01});
						 	}
						}	
					});
				}
				
				//生成评审报告页面
				function sc(id,size){
					var one = "";
					var two = "";
					var three = "";
					//第一轮
					for (var i = 0; i < size; i++){
						var a= $("#oneId"+i).val();
						if(typeof(a) != "undefined"){
							if(a == ''){
								layer.alert("请完善审核意见", {
				                    offset: ['30%', '40%']
				                  });
								return;
							}
							if(i == 0){
								one = a;
							}else{
								one += ","+a;
							}
						}
					}
					//第二轮
					for (var j = 0; j < size; j++){
						var b= $("#twoId"+j).val();
						if(typeof(b) != "undefined"){
							if(b == ''){
								layer.alert("请完善审核意见", {
				                    offset: ['30%', '40%']
				                  });
								return;
							}
							if(j == 0){
								two = b;
							}else{
								two += ","+b;
							}
						}
					}
					//第三轮
					for (var k = 0; k < size; k++){
						var c= $("#threeId"+k).val();
						if(typeof(c) != "undefined"){
							if(c == ''){
								layer.alert("请完善审核意见", {
				                    offset: ['30%', '40%']
				                  });
								return;
							}
							if(k == 0){
								three = c;
							}else{
								three += ","+c;
							}
						}
					}
					window.location.href="${pageContext.request.contextPath}/look/report.html?id="+id+"&&one="+one+"&&two="+two+"&&three="+three;
				}
		</script>
	</head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
	<div class="container">
		<ul class="breadcrumb margin-left-0">
			<li>
				<a href="javascript:void(0);"> 首页</a>
			</li>
			<li>
				<a href="javascript:void(0);">保障作业系统</a>
			</li>
			<li>
				<a href="javascript:void(0);">采购计划管理</a>
			</li>
			<li class="active">
				<a href="javascript:void(0);">采购计划审核</a>
			</li>
		</ul>
		<div class="clear"></div>
		</div>
</div>
		
<div class="container">
	<div class="col-md-12 mt10 tab-v2 col-xs-12 col-sm-12 p0">
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sets()">调整审核人员</button>
		<a class="btn padding-left-10 padding-right-10 btn_back" onclick = "sc('${id}','${fn:length(list)}')">生成评审报告页面</a>
		<%-- 	<div class="fl">
		<u:upload id="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
		<u:show showId="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
		</div> --%>
	</div>	 
	<div class="row magazine-page">
		<div class="col-md-12 pt10 tab-v2">
			<ul class="nav nav-tabs bgwhite">
				<li class="active">
					<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">所有明细</a>
				</li>
				<li>
					<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">按需求部门</a>
				</li>
			</ul>	
			<form id="audit_form" action="${pageContext.request.contextPath}/look/audit.html" method="post">
				<div class="tab-content over_hideen">
					<div class="tab-pane fade active in" id="tab-1">
						<div class="col-md-8 col-sm-8 col-xs-12 over_auto h365" id="table_div">
							<table id="table" class="table table-bordered table-condensed mt5 lockout">
								<thead>
									<tr class="space_nowrap">
										<th class="info" colspan="17">事业部门需求</th>
								<%-- 		<c:if test="${audit==1 || audit==2 || audit==3 }">
										<th class="info" colspan="3">一轮审核</th>
									</c:if>
									<c:if test="${audit==2 || audit==3 }">
										<th class="info" colspan="2">二轮审核</th>
									</c:if>
										<c:if test="${audit==3 }">
										<th class="info" colspan="3">二轮审核</th>
									</c:if> --%>
									
									
							<%-- 	<c:forEach items="${bean }" var="obj">
									<th class="info" colspan="${obj.size}q">${obj.name }</th>
								</c:forEach> --%>
									</tr>
									<tr class="info">
									<th class="seq">序号</th>
									<th class="department">需求部门</th>
									<th class="goodsname">物资类别<br>及名称</th>
									<th class="stand">规格型号</th>
									<th class="qualitstand">质量技术标准</th>
									<th class="item">计量<br>单位</th>
									<th class="purchasecount">采购<br>数量</th>
									<th class="price">单价<br>（元）</th>
									<th class="budget">预算金额<br>（万元）</th>
									<th class="deliverdate">交货<br>期限</th>
									<th class="purchasetype">采购方式</th>
									<th class="organization">采购机构</th>
									<th class="purchasename">供应商名称</th>
									<th class="freetax">是否申请<br>办理免税</th>
								<!-- 	<th>物资用途<br>（仅进口）</th>
									<th>使用单位<br>（仅进口）</th> -->
									<th class="memo">备注</th>
									<th class="eatrafile">附件</th>
									</tr>
								</thead>
								<tbody >
									<c:forEach items="${list }" var="obj" varStatus="vs">
										<tr>
											<td><input readonly="readonly" type="text" class="seq" name="listDetail[${vs.index }].seq" onblur="checks(this)" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }"></td>
											<td>
											<div class="department">
											${obj.department }
											</div>
											<%-- 	<c:forEach items="${requires }" var="re">
								        			<c:if test="${re.id==obj.department }">
														<input type="hidden"  name="list[${vs.index }].department" value="${obj.id }">
														<input readonly="readonly" type="text" value="${re.name}">
													</c:if>
												</c:forEach> --%>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"  type="text" name="listDetail[${vs.index }].goodsName" value="${obj.goodsName }" class="goodsname">
											<input type="hidden"    name="history" value=""/>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"   type="text" name="listDetail[${vs.index }].stand" value="${obj.stand }" class="stand">
											<input type="hidden"    name="history" value=""/>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"   type="text" name="listDetail[${vs.index }].qualitStand" value="${obj.qualitStand }" class="qualitstand">
											<input type="hidden"    name="history" value=""/>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"   type="text" name="listDetail[${vs.index }].item" value="${obj.item }" class="item">
											<input type="hidden"    name="history" value=""/>
											</td>
											<td>
												<c:if test="${obj.purchaseCount!=null }">
								  				<input type="hidden" name="ss" value="${obj.id }">
								  				<input  maxlength="11" id="purchaseCount" onblur="sum2(this)" 
								    			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="listDetail[${vs.index }].purchaseCount"
													value="${obj.purchaseCount}" type="text" class="purchasecount"/>
								  				<input type="hidden" name="ss" value="${obj.parentId }">
												</c:if> 
												<c:if test="${obj.purchaseCount==null }">
								  				<input disabled="disabled" type="text" name="listDetail[${vs.index }].purchaseCount" value="${obj.purchaseCount }" class="purchasecount">
												</c:if>
											</td>
											<td class="tl">
												<c:if test="${obj.price!=null }">
									  				<input type="hidden" name="ss" value="${obj.id }">
								      				<input  maxlength="11" id="price" name="listDetail[${vs.index }].price" onblur="sum1(this)" value="${obj.price}" type="text" class="price"/>
									  				<input type="hidden" name="ss" value="${obj.parentId }">
												</c:if>
							    				<c:if test="${obj.price==null}">
								  					<input readonly="readonly"  type="text" name="listDetail[${vs.index }].price" value="${obj.price }" class="price">
												</c:if>
											</td>
											<td class="tr">
												<input type="hidden" name="ss" value="${obj.id }">
												<input type="text" name="listDetail[${vs.index }].budget" value="${obj.budget }" readonly="readonly" class="budget">
												<input type="hidden" name="ss" value="${obj.parentId }">
											</td>
											<td><input type="text" onblur="change(this)"   name="listDetail[${vs.index }].deliverDate" value="${obj.deliverDate }"  class="deliverdate"></td>
											<td>
											<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
											<input type="hidden" name="ss" value="${obj.id }">
												<select name="listDetail[${vs.index }].purchaseType" onchange="typeChange(this)" class="purchasetype">
													<option value="空值"  selected="selected"  >请选择</option> 
													<c:forEach items="${mType }" var="mt">
														<option value="${mt.id }" <c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
													</c:forEach>
												</select>
											 <input type="hidden"    name="history" value=""/>
												
											<%-- </c:if>	 --%>
											</td>
											<td>
											<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
											<input type="hidden" name="ss" value="${obj.id }">
												<select   name="listDetail[${vs.index }].organization" onchange="orgChange(this)" class="organization">
														<option value="空值" selected="selected" >请选择</option> 
													<c:forEach items="${org }" var="ss">
														<option value="${ss.orgId }" <c:if test="${ss.orgId==obj.organization }">selected="selected" </c:if> >${ss.name}</option>
													</c:forEach>
												</select>
											 <input type="hidden"    name="history" value=""/>
												
										<%--     </c:if> --%>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"  type="text" name="listDetail[${vs.index }].supplier" value="${obj.supplier }" class="purchasename">
											 <input type="hidden"    name="history" value=""/>
											</td>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"  type="text" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }" class="freetax">
											 <input type="hidden"    name="history" value=""/>
											</td>
	<%-- 										<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }"></td> --%>
											<td>
											<input type="hidden" name="ss" value="${obj.id }">
											<input onblur="change(this)"  type="text" name="listDetail[${vs.index }].memo" value="${obj.memo }" class="memo">
											 <input type="hidden"    name="history" value=""/>
											 <input type="hidden" class="count"   name="history" value=""/>
											 <input type="hidden" class="price"   name="history" value=""/>
											<td>
												<input type="hidden" class="ptype" name="ptype" value="${obj.purchaseType}"/>
												<input type="hidden" class="org" name="org" value="${obj.organization}"/>
												
												<c:if test="${obj.purchaseCount!=null }">
												  <a class="mt3 color7171C6" href='javascript:downFiles("${obj.id }");' > 下载</a>
												</c:if>												
							
												<%-- <div class="w160">
														<u:upload id="pUp${vs.index}" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
														<u:show showId="pShow${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
												   </div> --%>	
											</td>
											<!-- <td> -->
											<%-- 	<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
												<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
												<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
												<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
												<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
												<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
												<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
												<input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
												<input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
												<input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
												<input type="hidden" name="list[${vs.index }].status" value="${obj.status }"> --%>
											<!-- </td> -->
						 				</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
			
					<c:if test="${audit!=null ||audit!=0 }">
						<div class="col-md-4 col-sm-4 col-xs-12 over_scroll h365" id="audit_table_div">
							<table id="audit_table" class="table table-bordered table-condensed mt5 lockout">
								<thead>
									<tr class="space_nowrap">
										<c:if test="${status==3 || status==5 || status==7 }">
											<th class="info" colspan="3">一轮审核</th>
										</c:if>
										<c:if test="${status==5 || status==7 }">
											<th class="info" colspan="2">二轮审核</th>
										</c:if>
										<c:if test="${status==7 }">
											<th class="info" colspan="3">三轮审核</th>
										</c:if>
									</tr>
									<tr style="height:50px;">
										<c:if test="${status==3 || status==5 || status==7 }">
											<th colspan="3" >审核意见</th>
										 
										</c:if>
										<c:if test="${status==5 || status==7 }">
											 <th colspan="2" >审核意见</th>
										</c:if>
										<c:if test="${status==7 }">
										  <th colspan="3" >审核意见</th>
										</c:if>
									
									
						 
							</tr>
						</thead>
						<c:forEach items="${list }" var="objs" varStatus="vs">
							<tr>
								<c:if test="${status==3 || status==5 || status==7 }">
								<td colspan="3">
							<%-- 		<select name="listDetail[${vs.index }].onePurchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.onePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
									
								</td>		
								<td class="tc">
									<select name="listDetail[${vs.index}].oneOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc"> --%>
									<input type="text" class="seq" <c:if test="${status==5 || status==7 }"> readonly="readonly" </c:if>   style="width:330px;"  name="listDetail[${vs.index }].oneAdvice"  value="${objs.oneAdvice }" id = "oneId${vs.index }">
								</td>
								</c:if>
								
							<c:if test="${status==5 || status==7 }">
								<td colspan="2">
							<%-- 		<input type="text" name="listDetail[${vs.index }].twoTechAdvice" value="${obj.twoTechAdvice }" >
								</td>
								<td class="tc" --%>
									<input type="text"  <c:if test="${status==7 }"> readonly="readonly" </c:if>  name="listDetail[${vs.index }].twoAdvice"  style="width:330px;"   value="${objs.twoAdvice }" id = "twoId${vs.index }">
								<!-- </td> -->
							</c:if>
							<c:if test="${status==7 }">	
								
								<td colspan="3">
						<%-- 			<select name="listDetail[${vs.index }].threePurchaseType">
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.threePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tl pl20">
									<select name="listDetail[${vs.index}].threeOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.id }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tl pl20"> --%>
									<input type="text" name="listDetail[${vs.index }].threeAdvice" value="${objs.threeAdvice }" id = "threeId${vs.index }">
								<!-- </td> -->
								</c:if>
								
							<%--<c:forEach items="${all }" var="al" varStatus="avs">
								<td class="tc">
								<c:forEach items="${audits }" var="as">
									<c:if test="${as.purchaseId==obj.id and as.auditParamId==al.id }">
									 	<c:if test="${al.param=='1' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)" name="audit[${vs.index*5+avs.index}].paramValue">
												<option value="gkzb" <c:if test="${as.paramValue =='公开招标' }">  selected="selected" </c:if> >公开招标</option>
												<option value="yqzb" <c:if test="${as.paramValue =='邀请招标' }">  selected="selected" </c:if> >邀请招标</option>
												<option value="dyly" <c:if test="${as.paramValue =='单一来源'  }">  selected="selected" </c:if> >单一来源</option>
												<option value="jzxtp" <c:if test="${as.paramValue =='竞争性谈判' }">  selected="selected" </c:if> >竞争性谈判</option>
												<option value="xj" <c:if test="${as.paramValue =='询价' }">  selected="selected" </c:if> >询价</option> 
												<c:forEach items="${dicType }" var="mt">
													<option value="${mt.id }"<c:if test="${mt.id==as.paramValue  }"> selected="selected"</c:if> >${mt.name}</option>
												</c:forEach>
											</select>
									 	</c:if>
									  	<c:if test="${al.param=='2' }">
										  	<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
										  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)"  name="audit[${vs.index*5+avs.index }].paramValue">
												<c:forEach items="${org }" var="ss">
												  <option value="${ss.name }" <c:if test="${as.paramValue ==ss.name }">  selected="selected" </c:if> >${ss.name}</option>
												</c:forEach>
											</select>
									 	</c:if>
									 	<c:if test="${al.param=='3' or al.param=='4' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  		<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<input type="text" name="audit[${vs.index*5+avs.index }].paramValue" value="${as.paramValue }">
									 	</c:if>
									 </c:if>
								</c:forEach>
								</td>
							</c:forEach>--%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
						<div class="tab-pane fade in" id="tab-2">
							<div class="content table_box ">
								<table class="table table-bordered table-condensed mt5">
									<thead>
									<!-- 	<tr>
											<th class="info" colspan="2">事业部门需求</th>
										</tr> -->
										<tr class="info">
										<th>需求部门</th>
										</tr>
									</thead>
									    <c:if test="${detail != null}">
					            <c:forEach items="${detail}" var="objs" varStatus="vs">
					              <tr style="cursor: pointer;">
					              <%--   <td class="tc w50">${(vs.index+1)}</td>   --%>
					                <td class="tl"  onclick="view('${objs.id}')">${objs.department }</td>
					              </tr>                            
					           </c:forEach>
					           </c:if>
									</table>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
							<input type="hidden" name="id" id="cid" value="${id }"> 
							<input type="hidden" name="planNo" value="${planNo }">
							<input type="hidden" name="auditTurn" value="${audit }">
							<input type="hidden" id="status" name="status" value="${status }">
							<input class="btn btn-windows save" type="submit" value="保存">
							<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					</div>
					</form>			
					</div>
				</div>
		
		</div>
	</body>
	
	<script>
		(function($,w){
			w.onload=function(){
				$('#table_div').bind('scroll', function() {
					//$('#audit_table_div').scrollTop($(this).scrollTop());
					//$('#audit_table_div').scrollLeft($(this).scrollLeft());
				});
				$('#audit_table_div').bind('scroll', function() {
					$('#table_div').scrollTop($(this).scrollTop());
					//$('#table_div').scrollLeft($(this).scrollLeft());
				});
			}
		})(jQuery,window);
	</script>
	
	
</html>
