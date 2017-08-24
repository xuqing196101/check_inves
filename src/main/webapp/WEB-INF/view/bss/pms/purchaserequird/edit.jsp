<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/lock_table/moment.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/lock_table/pikaday.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/public/lock_table/ZeroClipboard.js"></script>

<link
	href="${pageContext.request.contextPath}/public/lock_table/handsontable.min.css"
	media="screen" rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/public/lock_table/pikaday.css"
	media="screen" rel="stylesheet" type="text/css">


<script type="text/javascript">
	$(function(){
		delId =[];
	})
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		 
		}
	
	function referenceNO(){
        var referenceNO = $("#referenceNo").val();
        if(referenceNO == ''){
            return;
        }        
        $.ajax({
            url: '${pageContext.request.contextPath}/purchaser/selectUniqueReferenceNO.do',
            data:{
                "referenceNO": referenceNO
            },
            success: function(data) {
                if(data.data > 1) {
                    $("#referenceNo").val("");
                    layer.msg("采购需求文号已存在");
                }
            }
        });
    }
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
  	
  	 
  	 
	
   	var flag=true;
	function checks(obj){
		  var name=$(obj).attr("name");
		  var planNo=$("#pNo").val();
		  var val=$(obj).val();
		  var defVal=obj.defaultValue;
			if(val!=defVal){
				$.ajax({
					url:"${pageContext.request.contextPath}/adjust/filed.html",
					type:"post",
					data:{
						planNo:planNo,
						name:name
					},
					success: function(data){
						 if(data=='exit'){
							 flag=false;
							 layer.tips("该字段不允许修改",obj);
						 }
					 },
					error:function(data){
						 
					 }
					 
				});
			} 
	}
	
 
  	 function sum2(obj){  //修改数量
	        var purchaseCount = $(obj).val()-0;//数量
	        var price2 = $($(obj).parent().next().children()[1]).val();//价钱
	        $($(obj).parent().next().next().children()[1]).val(purchaseCount*price2/10000);
	      	aa($(obj).next().val());//parentId
	    }

    function sum1(obj){//修改单价
        var price = $(obj).val()-0; //价钱
        if(price == 0){
            return;
        }
        var purchaseCount = $($(obj).parent().prev().children(":first").next()).val()-0;//数量
        var sum = purchaseCount*price/10000;
        $($(obj).parent().next().children(":first").next()).val(sum);
        var id=$($(obj).parent().prev().children(":last")).val(); //parentId
        aa(id);
    }
	       function aa(id){// id是指当前的父级parentid
	    	   var budget=0;
	    	   $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":first").next().next().val(); //parentId
	 	    		var same= $(this).find("td:eq(9)").children(":first").next().val()-0; //价格
		 	       if(id==cid){
		 	    	  budget=budget+same; //查出所有的子节点的值
		 	       }
	    	   });
	    	   budget = budget.toFixed(4); //保存两位小数
	    	   var bud;
	     
	    	    $("#table tr").each(function(){
		    	  var  pid= $(this).find("td:eq(9)").children(":first").val();//自己的ID
		    		
		    		if(id==pid){
		    			$(this).find("td:eq(9)").children(":first").next().val(budget); //使父级节点的预算金额为子级节点值和
		    			 var spid= $(this).find("td:eq(9)").children(":first").next().val();
		    			/* alert(spid) */
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
	    	     
	    	  var did=$("#table tr:eq(1)").find("td:eq(9)").children(":first").val();//超级节点id
	    	    var total=0;
	    	    $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(9)").children(":last").val();
	 	    		var same= $($(this).find("td:eq(9)").children()[1]).val()-0;
	 	    		 if(did==cid){
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	    $($("#table tr:eq(1)").find("td:eq(9)").children()[1]).val(total.toFixed(4));
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
        function ssl(obj) {
	    	  if($(obj).val()!="26E3925D38BB4295BEB342BDC82B65AC"){
	    		  $(obj).parent().next().children(":last").val("");
		    	 }
	    	  var next=$(obj).parent().parent().nextAll();
				var parent_id=$($(obj).parent().parent().children()[1]).children(":last").val();
				for(var i = 0; i < next.length; i++){
					 if(next[i].tagName=="TR"){
						 if(parent_id==$($(next[i]).children()[1]).children(":last").val()){
			              break;
			        }
			       $($(next[i]).children()[11]).children(":last").val($(obj).val());
					 }
	    	 }
        }
	  	 
	      function changeType(obj) {
                  /*if($(obj).val()!="26E3925D38BB4295BEB342BDC82B65AC"){
                      $(obj).parent().next().children(":last").val("");
                  }
                      var next=$(obj).parent().parent().nextAll();
                        var parent_id=$($(obj).parent().parent().children()[1]).children(":last").val();
                        for(var i = 0; i < next.length; i++){
                             if(next[i].tagName=="TR"){
                                 if(parent_id==$($(next[i]).children()[1]).children(":last").val()){
                                  break;
                            }
                           $($(next[i]).children()[11]).children(":last").val($(obj).val());
                             }
                     }*/
               /*  var val = $(obj).val();
                $("select option").each(function() {
                  var opt = $(this).val();
                  if (val == opt) {
                    $(this).attr("selected", "selected");
                  }
                }); */
		    	var defValue;
	    		 var org=$(obj).val();
	    		 var price=$(obj).parent().prev().prev().prev().prev().val();
	    		 if(price==""){
	    			/*var id=$(obj).prev().val();
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
	    			      	 		  $(options).each(function(){
						      	 		defValue=$(this).parent().parent().parent().children(":last").children(":last").val();
					      	 		 
	    			      	  		   var opt=$(this).val();
	    			      	  		   if(org==opt){
	    			      	  			$(this).prop("selected",true);
	    			      	  			
	    			      	  			if(defValue==org){
	    			      	  			    $(this).parent().next().val("");	
	    			      	  			}else{
	    			      	  			    var prevId=$(this).parent().prev().val();
	    			      	  			    $(this).parent().next().val(prevId);	
	    			      	  			}
	    			      	  		 
	    			      	  		   }else{
	    			      	  			  $(this).removeAttr("selected");
	    			      	  		   }
	    				      	  	   });
	    		      	 		   }  
	    		      	 	   });
	    		            }
	    		           }
	    		          });*/
                     var purchaseType = $(obj).find("option:selected").text(); //选中的文本
                     if($.trim(purchaseType) == "单一来源") {
                         $(obj).parent().next().find("input").removeAttr("readonly");
                     } else {
                         $(obj).parent().next().find("input").val("");
                         $(obj).parent().next().find("input").attr("readonly", "readonly");
                     }
                     var next=$(obj).parent().parent().nextAll();
                     var parent_id=$($(obj).parent().parent().children()[1]).children(":last").val();
                     for(var i = 0; i < next.length; i++){
                         if(parent_id==$($(next[i]).children()[1]).children(":last").val()){
                             break;
                         }
                         /* if(i == 8){
                             alert($($($(next[i]).children()[11]).children(":last")).find("option:selected").text());
                             alert($(obj).val());
                         } */

                         $($(next[i]).children()[11]).children(":last").val($(obj).val());
                         if($(obj).val() == "单一来源") {
                             $($(next[i]).children()[12]).find("input").removeAttr("readonly");
                         } else {
                             $($(next[i]).children()[12]).find("input").val("");
                             $($(next[i]).children()[12]).find("input").attr("readonly", "readonly");
                         }
                     }
	    		 }
		  }
	       
	
	      function historys(obj){
	        	var val = $(obj).val();
	            var  id=$(obj).prev().val();
	    		var defVal = obj.defaultValue;
	    		if(val != defVal) {
	    			$(obj).next().val(id);
	    		}else{
	    			$(obj).next().val("");
	    		} 
 	        }
	     function supplierReadOnly(obj){
	    	 if($(obj).parent().prev().find("select").val()=="26E3925D38BB4295BEB342BDC82B65AC"){
	    		 $(obj).removeAttr("readonly");
	    	 }else{
	    		 $(obj).val("");
	    		 $(obj).attr("readonly","readonly");
	    	 }
	     }
	     
	     function dyly(){
             var bool=true;
             $("#detailZeroRow tr").each(function(i){
            	 if($(this).find("td:eq(8)").children(":first").next().val()!=""){
            		 var  type= $(this).find("td:eq(11)").children(":last").val();
                     if($.trim(type) == "26E3925D38BB4295BEB342BDC82B65AC") {//单一来源
                         var  supp= $(this).find("td:eq(12)").children(":last").val();
                         if($.trim(supp)==''){
                             bool=false;
                             return bool;
                         }
                     }
            	 }
             });
             return bool;
             
         } 
	     
	      function submit(){
	    	  
	    	  var name=$("#jhmc").val();
	    	  if($.trim(name) == "") {
					 layer.alert("需求名称不允许为空"); 
					 return false;
				}
	    	   var dy=dyly(); 
	    	  
	    	 /*  $("#detailZeroRow tr").each(function(i){
                 var  type= $(this).find("td:eq(11)").children(":last").text();//上级id
                   if($.trim(type) == "单一来源") {
                       var  supp= $(this).find("td:eq(12)").children(":first").val();//上级id
                   }
             }); */
	    	  
	    	  if(!dy){
                  layer.alert("请填写供应商");
                  return false;
              }
	    	  var flgs=false;
	    	  $("#table tr").each(function(i){
	    		  var price = $($(this).find("td:eq(8)").children()[1]).val();
			    	if($.trim(price)!=""){
			    		 if($(this).find("td:eq(11)").children(":last").val()==""){
			    			 flgs=true;
			    		 }
			    	}
	    	  });
	    	  if(flgs){
				   layer.alert("子节点采购方式不能为空"); 
				   return false;
			   }
	    	  var no=$("#jhbh").val();
	    	  var refNo=$("#referenceNo").val();
	    	  var type=$("#wtype").val();
	    	  var mobile=$("#rec_mobile").val();
	    	  var uniqueId=$("#uniqueId").val();
	    	  $("input[name='planName']").val(name);
	    	  $("input[name='planNo']").val(no);
	    	  $("input[name='referenceNo']").val(refNo);
	    	  $("input[name='planType']").val(type);
	    	  $("input[name='mobile']").val(mobile);
	    	// $("#table").find("#edit_form").submit();
	    	$.each(delId,function(i,n){
	    		deleteRow(n);
	    	});
	    	 /* if($("#table tr").length>$("#listSize").val()){ */
	    	 var flag = true;
	    		 var jsonStr = [];
	 			 $("#table tr").each(function(i){ //遍历Table的所有Row
	 					 if(i>0){  //&&i<=$("#listSize").val()
	 				    var id =$(this).find("td:eq(1)").children(":first").val();
	 				    var parentId =$(this).find("td:eq(1)").children(":last").val();
	 				    var seq = $(this).find("td:eq(1)").children(":first").next().val();
	 				    //var department = $(this).find("td:eq(2)").children(":first").val();
	 				    var department = $.trim($(this).find("td:eq(2)").text());
	 					var goodsName =$(this).find("td:eq(3)").children(":last").children(":last").val();
	 					var stand = $(this).find("td:eq(4)").children(":last").val();
                             if(stand.length > 250){
                                 flag = false;
                                 layer.alert("第" + i +"行规格型号不能超过250字");
                                 return false;
                             }
	 					var qualitStand = $(this).find("td:eq(5)").children(":last").val();
                             if(qualitStand.length > 1000){
                                 flag = false;
                                 layer.alert("第" + i +"行质量技术标准不能超过1000字");
                                 return false;
                             }
	 					var item = $(this).find("td:eq(6)").children(":last").val();
                        var purchaseCount =$(this).find("td:eq(7)").children(":first").next().val();
                        var price = $(this).find("td:eq(8)").children(":first").next().val();
                        var budget = $(this).find("td:eq(9)").children(":first").next().val();
	 					var deliverDate = $(this).find("td:eq(10)").children(":last").val();
	 					var purchaseTypes = $(this).find("td:eq(11)").children(":last").val();
	 					var supplier = $(this).find("td:eq(12)").children(":last").val();
	 					var isFreeTax = $(this).find("td:eq(13)").children(":last").val();
	 					var goodsUse = $(this).find("td:eq(14)").children(":last").val();
	 					var useUnit =$(this).find("td:eq(15)").children(":last").val();
	 					var memo = $(this).find("td:eq(16)").children(":last").val(); 
	 					var json = {"seq":seq,"id":id,"parentId":parentId,"department":department, "goodsName":goodsName, "stand":stand,"qualitStand":qualitStand,
	 						"item":item, "purchaseCount":purchaseCount, "price":price, "budget":budget, 
	 						"deliverDate":deliverDate,"purchaseType":purchaseTypes,"supplier":supplier,
	 						"isFreeTax":isFreeTax,"goodsUse":goodsUse,"useUnit":useUnit,"memo":memo,"isMaster":i};

	 					jsonStr.push(json);  

	 				 	}
	 				});
	 			 if(!flag){
	 			     return;
                 }
	 			// return false;
	 		//	var forms=$("#add_form").serializeArray();
	 			  $.ajax({
	 	  		        type: "POST",
	 	  		        url: "${pageContext.request.contextPath}/purchaser/editdetail.do",
	 	  		        data: {"prList":JSON.stringify(jsonStr),
	 	  		        	"planType":type,
	 	  		        	"planNo":no,
	 	  		        	"planName":name,
	 	  		        	"recorderMobile":mobile,
	 	  		        	"referenceNo":refNo,
	 	  		        	"unqueId":uniqueId,
	 	  		        	"enterPort":$("#enterPort").val()},
	 	  		        success: function (message) {
	 	  		        	 window.location.href = "${pageContext.request.contextPath}/purchaser/list.do";
	 	  		        },
	 	  		        error: function (message) {
	 	  		        }
	 	  		    });
	    	 /* } */
	    	 //$("#table").find("#edit_form").submit();
	    	 // $("#edit_form").submit();
	      }
	       function gtype(obj){
	    	    var vals=$(obj).val();
				$("#import").attr("checked",false);
				$("input[name='enterPort']").val(0);
				$("td[name='userNone']").attr("style","display:none");
				$("th[name='userNone']").attr("style","display:none");
				if(vals == 'FC9528B2E74F4CB2A9E74735A8D6E90A'){
					  $("#dnone").show();
					  $("#dnone").next().attr("class","col-md-3 col-sm-6 col-xs-12");
				}else{
					 $("#dnone").hide();
					 $("#dnone").next().attr("class","col-md-3 col-sm-6 col-xs-12 mt25 ml5");
				}
			}
	       function imports(obj){
				var bool=$(obj).is(':checked');
				if(bool==true){
					$("td[name='userNone']").attr("style","");
					$("th[name='userNone']").attr("style","");
					$("input[name='enterPort']").val(1);
				}else{
					$("td[name='userNone']").attr("style","display:none");
					$("th[name='userNone']").attr("style","display:none");
					$("input[name='enterPort']").val(0);
				}
			}
       //重写下载方法，只下载最近上传的一个文件
	   function download(id,key,zipFileName,fileName){
    	  location.href="${pageContext.request.contextPath}/file/downloadOneFile.html?id="+ id +"&key="+key + "&zipFileName=" + encodeURI(encodeURI(zipFileName)) + "&fileName=" + encodeURI(encodeURI(fileName));
       }
       //下载模板
	   function down() {
			window.location.href = "${pageContext.request.contextPath}/purchaser/download.do";
		}
	 //鼠标移动显示全部内容
		var index;
		//查看编制说明
		function chakan() {
			index = layer.open({
				type: 1, //page层
				area: ['600px', '500px'],
				title: '编制说明',
				closeBtn: 1,
				shade: 0.01, //遮罩透明度
				moveType: 1, //拖拽风格，0是默认，1是传统拖动
				shift: 1, //0-6的动画形式，-1不开启
				offset: ['80px', '400px'],
				content: $('#organization'),
			});
		}

		function closeLayer() {
			layer.close(index);
		}
       //查看产品分类目录
       var datas;
			var treeObj;
			
			$(function() {
				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/purchaser/createtree.do",
						dataType: "json",
						type: "post",
					},
					callback: {
						onClick: zTreeOnClick, //点击节点触发的事件
						//beforeRemove: zTreeBeforeRemove,
						//beforeRename: zTreeBeforeRename, 
						//onRemove: zTreeOnRemove,
						//onRename: zTreeOnRename,
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: 0,
						}
					},
				};
				//控制树的显示和隐藏
				var expertsTypeId = $("#expertsTypeId").val();
				if(expertsTypeId == 1 || expertsTypeId == "1") {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").show();
				} else {
					treeObj = $.fn.zTree.init($("#ztree"), setting, datas);
					$("#ztree").hide();
				}
				
			});
	   function typeShow() {
			/* 	 var expertsTypeId = $("#expertsTypeId").val();
				 if(expertsTypeId==1 || expertsTypeId=="1"){ */
			$("#ztree").show();
			layer.open({
				type: 1,
				title: '信息',
				skin: 'layui-layer-rim',
				shadeClose: true,
				offset: ['20%', '20%'],
				area: ['45%', '70%'],
				content: $("#catalogue")
			});
			$(".layui-layer-shade").remove();
			/*  }else{
				 $("#ztree").hide();
			 } */

		}
		var treeid = null;
		/*树点击事件*/
		function zTreeOnClick(event, treeId, treeNode) {
			treeid = treeNode.id;

		}

		function typehide() {
			layer.closeAll();
		}

		function same(obj,parentId) {
			            $(obj).parent().parent().find("td:eq(1)").children(":last").val(parentId);
		 			  	$(obj).parent().parent().find("td:eq(7)").children(":last").val(parentId);
				    	$(obj).parent().parent().find("td:eq(8)").children(":last").val(parentId);
				    	$(obj).parent().parent().find("td:eq(9)").children(":last").val(parentId);  
		}

		function news(obj) {
			var s = $("#count").val();
			s++;
			$("#count").val(s);
			var trs = $(obj).parent().parent();
			$(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' name='list[" + s + "].id' />" +
				"<input style='border: 0px;' type='text' name='list[" + s + "].seq' /><input style='border: 0px;' value='" + id + "' type='hidden' name='list[" + s + "].parentId' /></td>" +
				"<td class='tc p0'> <input  style='border: 0px;'  type='text' name='list[" + s + "].department' /> </td>" +
				"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
				"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
				"<td class='tc p0'> <input  style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].isFreeTax' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
				"<td class='tc p0'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
				"<td class='tc p0'><input class='add' name='dyadds' type='button' onclick='aadd(this)' value='添加子节点'>" +
				"<input class='btn btn-windows add' name='delt' type='button' onclick='same(this)' value='添加同级节点'>" +
				" <input class='btn btn-windows add' name='delt' type='button' onclick='news(this)' value='新加任务'></td>" +
				+"<tr/>");

		}
      //导入
	   function uploadExcel() {
			index = layer.open({
				type: 1, //page层
				area: ['400px', '300px'],
				title: '导入采购需求',
				closeBtn: 1,
				shade: 0.01, //遮罩透明度
				moveType: 1, //拖拽风格，0是默认，1是传统拖动
				shift: 1, //0-6的动画形式，-1不开启
				offset: ['80px', '400px'],
				content: $('#file_div'),
			});
		}
	   function fileup(){
		  	   var detailRow = document.getElementsByName("detailRow");
			   var index = detailRow.length;
			   var planNo=$("#planNo").val();
	           $.ajaxFileUpload ( {
	                        url: "${pageContext.request.contextPath}/purchaser/upload.do?planNo="+planNo,  
	                        secureuri: false,  
	                        fileElementId: 'fileName', 
	                        dataType: 'json',
	                        success: function (data) { 
	                        	var bool=true;
	                           var chars = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
							   if(data=="0"){
							    	layer.alert("非法文件不能导入",{offset: ['222px', '390px'], shade:0.01});
							    } 
	                            if(data=="1"){
					        	   layer.alert("文件格式错误",{offset: ['222px', '390px'], shade:0.01});
					        	    
					           } 
	                            if(data=="2"){
	                            	layer.alert("excel编写错误，请重写编写",{offset: ['222px', '390px'], shade:0.01});
	                            }
	                            if(data=="3"){
	                            	layer.alert("节点错误，请重写编写",{offset: ['222px', '390px'], shade:0.01});
	                            }
	                            if(data=="4"){
	                            	layer.alert("父级节点不能填写数量，采购单价，请重写编写",{offset: ['222px', '390px'], shade:0.01});
	                            }
	                            
							      if(data.indexOf("行")!=-1){
							    	  bool=false;
							      }
	                            
							     if(bool!=true){
							        	   layer.alert(data,{offset: ['222px', '390px'], shade:0.01});
							        }
							           else{
							        	   layer.alert("上传成功",{offset: ['222px', '390px'], shade:0.01});
							             $("#detailZeroRow").empty();
							              var count=1;
							                $.ajax({
							  		        type: "POST",
							  		        url: "${pageContext.request.contextPath}/templet/uploaddetail.html",
							  		        data: {prList:JSON.stringify(data),
							  		        		index:index,
							  		        		type:"edit"},
							  		        success: function (result) {
							  		        	$("#detailZeroRow").append(result);
												init_web_upload();
												  var bool=$("input[name='import']").is(':checked');
												if(bool==true){
													$("td[name='userNone']").attr("style","");
													$("th[name='userNone']").attr("style","");
												}else{
													$("td[name='userNone']").attr("style","display:none");
													$("th[name='userNone']").attr("style","display:none");
												}  
												
							  		        },
							  		        error: function (message) {
							  		        }
							  		    });  
							           layer.close(index);
	                           }
	                        }
	                       
	                    }); 
				
			}

	 //动态添加
		function aadd() {
			var value = $("#xqbm").val();
			var detailRow = document.getElementsByName("detailRow");
			var index = detailRow.length;
			var id = null;
			$.ajax({
				url: "${pageContext.request.contextPath}/templet/detail.html",
				type: "post",
				data:{"index":index,
					  "type":"edit"},
				success: function(data) {
                    //$("table tr:last").after(data);
                    $("#detailZeroRow").append(data);
					init_web_upload();//加载附件上传按钮
				    var bool=$("input[name='import']").is(':checked');
					if(bool==true){
						$("td[name='userNone']").attr("style","");
						$("th[name='userNone']").attr("style","");
					}else{
						$("td[name='userNone']").attr("style","display:none");
						$("th[name='userNone']").attr("style","display:none");
					}
					
				}
			});
		}
		function getSeq(obj,index){
	    	  var id=$("table tr:eq(1)").find("td:eq(1)").children(":first").val();
	    	  var val= $(obj).parent().parent().find("td:eq(1)").children(":first").next().val();
	    	  var prev= $("#seq"+(index-1)).val();
	    	 
	    	  //二级节点
	    	  var ch=chniese(prev);
	    	  var con=conChniese(val);
	    	  var twoPrev=conChniese(prev);
	    	  var two=nums(prev);
	    	  var twonum=conNum(prev);
	    	  var twoen=eng(prev);
	    	  var twoconeng=conEng(prev);
	    	  
	    	  //三级节点
	    	  var conPrev=conChniese(prev);
	    	  var num=nums(val);
	    	  var threePrev=conChniese(prev);
	    	  var threenum=nums(prev);
	    	  var threeconNum=conNum(prev);
	    	  var threeen=eng(prev);
	    	  var threeConEn=conEng(prev);
	    	  
	    	  //四级节点
	    	  var numPrev=nums(prev);
	    	  var conum=conNum(val);
	    	  var fourPrev=conNum(prev);
	    	  
	    	  
	    	  var conumPrev=conNum(prev);
	    	  var en=eng(val); 
	    	  var five=conNum(prev);
	    	  
	    	  var enPrev=eng(prev);
	    	  var sixVal=conEng(val);  
	    	  
	    	  if(ch==true&&con==true){
			    	same(obj,id);
	    	  }else if(con==true&&twoPrev==true){
	    		  same(obj,id);
	    	  }else if(two==true&&con==true){
	    		  same(obj,id);
	    	  }else if(twonum==true&&con==true){
	    		  same(obj,id);
	    	  }else if(twoconeng==true&&con==true){
	    		  same(obj,id);
	    	  }else if(twoen==true&&con==true){
	    		  same(obj,id);
	    	  }
	    	  
	    	  
	    	  else if(conPrev==true&&num==true){
	    		  
	    		  var parentId= getPreviousElement("conChniese");
	    		  
	    		  same(obj,parentId);
	    	  }else if(threePrev==true&&num==true){
	    		  var parentId= getPreviousElement("conChniese");
	    		  same(obj,parentId);
	    	  }else if(threenum==true&&num==true){
	    		  var parentId= getPreviousElement("conChniese");
	    		  
	    		  same(obj,parentId);
	    	  }else if(threeconNum==true&&num==true){
	    		  var parentId= getPreviousElement("conChniese");
	    		  same(obj,parentId);
	    	  }else if(threeen==true&&num==true){
	    		  var parentId= getPreviousElement("conChniese");
	    		  same(obj,parentId);
	    	  }else if(threeConEn==true&&num==true){
	    		  var parentId= getPreviousElement("conChniese");
	    		  same(obj,parentId);
	    	  }
	    	  
	    	  
	         else if(numPrev==true&&conum==true){
	        	 var parentId= getPreviousElement("nums");
	        	 same(obj,parentId);
	    	  }else if(fourPrev==true&&conum==true){
	    		  var parentId= getPreviousElement("nums");
	    		  same(obj,parentId);
	    	  }
	    	  
	    	  else if(conumPrev==true&&en==true){
	    		  var parentId= getPreviousElement("conNum");
	    		  same(obj,parentId);
	    	  }else if(five==true&&en==true){
	    		  var parentId= getPreviousElement("conNum");
	    		  same(obj,parentId);
	    	  }
	    	  
	    	  else if(enPrev==true&&sixVal==true){
	    		  var parentId=getPreviousElement("conEng");
	    		  same(obj,parentId);
	    	  }else{
	    		 layer.alert("节点填写错误",{offset: ['222px', '390px'], shade:0.01});
	    	  }  
	       } 
		 //判断是否是中文
        function chniese(val){
        	
	         var bool=true;
	         var reg=/^[\u4e00-\u9fa5]+$/;
	         if(!reg.test(val)&&!val.indexOf("（")!=-1){
	        	 bool=false;
	          }
	         return  bool;
        }  
        //判断是否包含是中文
     function conChniese(val){
	         var bool=true;
	         var reg=/^.*[\u4e00-\u9fa5]+.*$/;
	         if(reg.test(val)&&val.indexOf("（")!=-1){
	        	 bool=true;
	          }else{
	        	  bool=false;
	          }
	         return  bool;
        }  
        //判断是否是数字
        function nums(val){
        	var bool=true;
        	var reg=/^\d{1,}$/;
        	if(reg.test(val)&&!val.indexOf("（")!=-1){
        		 bool=true;
        	}else{
	        	 bool=false;
	          }
        	return bool;
        }  
        //是包含数字
       function conNum(val){
        	var bool=true;
        	var reg=/^.*\d+.*$/;
        	if(reg.test(val)&&val.indexOf("（")!=-1){
        		bool=true;
        	}else{
        		bool=false;
        	}
        	return bool;
        }  
        //是否是英文
        function eng(val){
        	var bool=false;
            var chars = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
            for(var i=0;i<chars.length;i++){
            	if(chars[i]==val){
            		bool=true;
            	}
            }
            return  bool;
        }  
        
        //是否是英文
        function conEng(val){
        	var bool=false;
            var chars = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
            for(var i=0;i<chars.length;i++){
            	if(val.match(chars[i])&&val.indexOf("（")!=-1){
            		bool=true;
            	}
            }
            return  bool;
        }  
        
		//获取距离当前元素最近的上级元素
		function getPreviousElement(methodName) {
			var parentId;
			 $("#table tr").each(function(i){ //遍历Table的所有Row
				var seq=$('#seq'+i).val();
				if(eval(methodName+"(\""+seq+"\")")){
					parentId=$("#id"+i).val();
				}	
			  }
			 )
			 return parentId;
		}
		//校验供应商名称
        function checkSupplierName(obj) {
            var name=$(obj).val();
            if(name!=null){
                $.ajax({
                    type: "POST",
                    async:false,
                    dataType: "text",
                    data:{
                        "name":name
                    },
                    url: "${pageContext.request.contextPath }/purchaser/checkSupplierName.do",
                    success: function(data) {
                            if(data=='true'){
                                $(obj).val("");
                                layer.alert("库中没有此供应商，请重新输入");
                            }
                    }
                 });
            }
        }
	    function deleteRow(id){
	    	$.ajax({
	  		        type: "POST",
	  		        url: "${pageContext.request.contextPath}/purchaser/deleteRequired.do",
	  		        data: {"id":id},
	  		        success: function (data) {
	  		        },
	  		        error: function (message) {
	  		        }
	  		    });
	    }
      function delRowIndex(obj){//delobjId
			var detailRow = document.getElementsByName("detailRow");
			var index = detailRow.length;
			
			var input=$(obj).prev().val();
			delId.push(input); 
			/* var del = $("input[name='delobjId']").val(delId); */
			/* delId.push(del); */ 
			//alert(delId);
			if(index<3){
				 layer.alert("至少保留两行！",{offset: ['222px', '390px'], shade:0.01});
			}else{
			var tr=$(obj).parent().parent();
			var nextEle=$(obj).parent().parent().next().children();
			 var val=$(tr).find("td:eq(8)").children(":first").next().val();
			 if($.trim(val)!=""){
				 var input=$(obj).prev().val();
				 if(typeof(input)!="undefined"){
					/*  deleteRow(input);  */
				 }
				 $(obj).parent().parent().remove();
			 }
			 else if(nextEle.length<1){
				 var input=$(obj).prev().val();
				 if(typeof(input)!="undefined"){
					/*  deleteRow(input);  */
				 }
				 $(obj).parent().parent().remove(); 
			 }
			 else{
				 layer.alert("只能删除末级节点",{offset: ['222px', '390px'], shade:0.01});
			 }
			} 
			var cid = $(obj).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().children(":last");
            /* alert(cid); */
            sum2(cid);
		}
    //检索名字
		function listName(obj) {
			var name = $(obj).val();
			if(name == "" || name == null) {
				$("#materialName").html("");
				$("#materialName").addClass("dnone");
				return;
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "${pageContext.request.contextPath }/purchaser/listName.do?name=" + name,
				success: function(data) {
						if(data.length>0){
							var html = "";
							for(var i = 0; i < data.length; i++) {
								html += "<div style='width:178px;height:20px;' class='pointer' onmouseover='changeColor(this)' onclick='getValue(this)'>"+data[i].name+"</div>";
							}
							$("#materialName").html(html);
							$("#materialName").removeClass("dnone");
							$(obj).after($("#materialName"));
						}else{
							$("#materialName").html("");
							$("#materialName").addClass("dnone");
						}
				}
			});
		}
		//改变颜色
		function changeColor(obj){
			$(obj).css("background-color","#eee");
		}
		
		//获取值
		function getValue(obj){
			$(obj).parent().parent().find("textarea").val($(obj).html());
			$(obj).parent().addClass("dnone");
		}
</script>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script> -->
<%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
 --%>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaser/list.html');">采购需求编报</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box" id="container">
		<input type="hidden" value="${uniqueId}" id="uniqueId">
		<div>
			<h2 class="count_flow">
				<i>1</i>需求主信息
			</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span
					class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>需求名称</span>
					<div class="input-append input_group col-sm-12 col-xs-12 p0">
						<input type="text" class="input_group" id="jhmc"
							value="${list[0].planName}"> <span class="add-on">i</span>
					</div></li>
				<li class="col-md-3 col-sm-6 col-xs-12 hide"><span
					class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求编号</span>
					<div class="input-append input_group col-sm-12 col-xs-12 p0">
						<input type="text" class="input_group" id="jhbh"
							value="${list[0].planNo}"> <span class="add-on">i</span>
					</div></li>

				<li class="col-md-3 col-sm-6 col-xs-12"><span
					class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
					<div class="input-append input_group col-sm-12 col-xs-12 p0">
						<input type="text" class="input_group" id="referenceNo"
							name="referenceNo" value="${list[0].referenceNo}" onblur="referenceNO()"> <span
							class="add-on" >i</span>
					</div></li>



				<li class="col-md-3 col-sm-6 col-xs-12"><span
					class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>类别</span>
					<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
						<select name="planType" id="wtype" onchange="gtype(this)">
							<c:forEach items="${types }" var="tp">
								<c:if test="${tp.id==list[0].planType }">
									<option value="${tp.id}" selected="selected">${tp.name }</option>
								</c:if>
								<c:if test="${tp.id!=list[0].planType }">
									<option value="${tp.id}">${tp.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</div></li>


				<li class="col-md-3 col-sm-6 col-xs-12"><span
					class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
					<div class="input-append input_group col-sm-12 col-xs-12 p0">
						<input type="text" class="input_group" id="rec_mobile"
							name="mobile" value="${list[0].recorderMobile }"> <span
							class="add-on">i</span>
					</div></li>
				<c:if test="${list[0].planType=='FC9528B2E74F4CB2A9E74735A8D6E90A'}">
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" id="dnone">
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<input type="checkbox" id="import" name="" value="进口"
								<c:if test="${list[0].enterPort==1}">checked="checked"</c:if>
								onchange="imports(this)" />进口
						</div>
					</li>
				</c:if>
				<c:if test="${list[0].planType!='FC9528B2E74F4CB2A9E74735A8D6E90A'}">
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5 hide mt25 ml5"
						id="dnone">
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<input type="checkbox" id="import" name="" value="进口"
								onchange="imports(this)" />进口
						</div>
					</li>
				</c:if>
				<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"><span
					class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span> <u:upload
						id="detail" multiple="true" buttonName="上传附件"
						businessId="${fileId}" sysKey="2" typeId="${detailId}" auto="true" />
					<u:show showId="detailshow" businessId="${fileId}" sysKey="2"
						typeId="${detailId}" /></li>

			</ul>
		</div>

		<h2 class="count_flow">
			<i>2</i>需求明细
		</h2>
		<div class="content mt0 require_ul_list">
			<!-- -- -->
			<div class="col-md-12 p115 mt10">
				<button class="btn btn-windows add" onclick="aadd()">添加</button>
				<%--
	                    <button  class="btn btn-windows add" onclick="same()">添加同级</button>--%>
				<button class="btn btn-windows input" onclick="down()">下载模板</button>
				<button class="btn btn-windows input" onclick="uploadExcel();">导入</button>
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="typeShow()">查看产品分类目录</button>
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="chakan()">查看编制说明</button>
			</div>
			<!-- -- -->
			<div class="content " id="content">
				<input type="hidden" name="enterPort" id="enterPort"
					value="${list[0].enterPort}" /> <input type="hidden" id="oneNodeId"
					value=""> <input type="hidden" id="twoNodeId" value="">
				<input type="hidden" id="threeNodeId" value=""> <input
					type="hidden" id="fourNodeId" value=""> <input
					type="hidden" id="fiveNodeId" value=""> <input
					type="hidden" id="sixNodeId" value=""> <input type="hidden"
					id="oneNodeId" value="">
				<table id="table"
					class="table table-bordered table-condensed lockout">
					<thead>
						<tr id="scroll_top">
							<th class="seq">行号</th>
							<th class="info seq">序号</th>
							<th class="info department">需求部门</th>
							<th class="info goodsname">物资类别<br>及名称
							</th>
							<th class="info stand">规格型号</th>
							<th class="info qualitstand">质量技术标准</br>（技术参数）
							</th>
							<th class="info item">计量<br>单位
							</th>
							<th class="info purchasecount">采购<br>数量
							</th>
							<th class="info price">单价</br>（元）
							</th>
							<th class="info budget">预算金额</br>（万元）
							</th>
							<th class="info deliverdate">交货期限</th>
							<th class="info purchasetype">采购方式</th>
							<th class="info purchasename">供应商名称</th>
							<th class="info freetax" name="userNone" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>是否申请</br>办理免税
							</th>
							
								<th name="userNone" class="info" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>物资用途</br>（仅进口）
								</th>
								<th name="userNone" class="info" <c:if test="${list[0].enterPort==0}">style="display:none;"</c:if>>使用单位</br>（仅进口）
								</th>
							
							<!-- 		<th class="info">物资用途</br>（仅进口）</th>
							<th class="info">使用单位</br>（仅进口）</th> -->
							<th class="info memo">备注</th>
							<th class="extrafile">附件</th>
							<th class="w100">操作</th> 
						</tr>
					</thead>
					<form id="edit_form"
						action="${pageContext.request.contextPath}/purchaser/update.html"
						method="post">
						<input type="hidden" id="listSize" value="${listSize }" />
						<tbody id="detailZeroRow">
							<c:forEach items="${list }" var="obj" varStatus="vs">
								<tr style="cursor: pointer;" name="detailRow">
																		
									<td><div class="seq">${vs.index+1 }</div></td>
									<td class="tc">
									<input type="hidden" id="id${vs.index}" name="list[${vs.index }].id" value="${obj.id }">
									<input type="hidden" id="seq${vs.index}" value="${obj.seq}">
									<input type="hidden" id="parentId${vs.index}" name="list[${vs.index }].parentId" value="${obj.parentId }">
										${obj.seq}</td>
									<td>
										<%-- <input type="text" name="list[0].department" value="${obj.department}"> --%>
										<div class="department">${obj.department}</div> <%--  <c:forEach items="${requires }" var="re" >
					         <c:if test="${obj.department==re.name }"> <input readonly='readonly' type="text"  value="${re.name}" > </c:if>
			               </c:forEach> --%>
									</td>
									<td>
										<div class="goodsname">
											<input type="hidden" name="ss" value="${obj.id }">
											<textarea name="list[${vs.index }].goodsName"
												 class="target" onkeyup="listName(this)">${obj.goodsName}</textarea>
											<!-- <input type="hidden" name="history" value="" /> -->
										</div>
									</td>
									<td><input type="hidden" name="ss" value="${obj.id }">
										<input type="text" name="list[${vs.index }].stand"
										value="${obj.stand}"  class="stand">

										<!-- <input type="hidden" name="history" value="" /> -->
									</td>

									<td><input type="hidden" name="ss" value="${obj.id }">
										<input type="text" name="list[${vs.index }].qualitStand"
										value="${obj.qualitStand}" 
										class="qualitstand"> <!-- <input type="hidden"
										name="history" value="" /> -->
									</td>
									<td><input type="hidden" name="ss" value="${obj.id }">
										<input type="text" name="list[${vs.index }].item"
										value="${obj.item}"  class="item">
									</td>

									<!-- <input type="hidden" name="history" value="" /> -->
									<td><%--<c:if test="${obj.purchaseCount!=null}">
											<input type="hidden" name="ss" value="${obj.id }">
											<input maxlength="11" class="purchasecount"
												onblur="sum2(this);" type="text"
												onkeyup="this.value=this.value.replace(/\D/g,'')"
												onafterpaste="this.value=this.value.replace(/\D/g,'')"
												name="list[${vs.index }].purchaseCount"
												value="${obj.purchaseCount}" />
                                        <input type="hidden" name="ss" value="${obj.parentId }">
										</c:if> <c:if test="${obj.purchaseCount==null }">
											<input class="purchasecount" type="text" 
												name="list[${vs.index }].purchaseCount" class="w80"
												value="${obj.purchaseCount }" >
										</c:if>--%>
                                        <input type="hidden" name="ss" value="${obj.id }">
                                        <input maxlength="11" class="purchasecount"
                                               onblur="sum2(this);" type="text"
                                               onkeyup="this.value=this.value.replace(/\D/g,'')"
                                               onafterpaste="this.value=this.value.replace(/\D/g,'')"
                                               name="list[${vs.index }].purchaseCount"
                                               value="${obj.purchaseCount}" />
                                        <input type="hidden" name="ss" value="${obj.parentId }">
                                    </td>
									<td class="tl w80"><%--<c:if test="${obj.price!=null}">
											<input type="hidden" name="ss" value="${obj.id }">
											<input maxlength="11" class="price"
												name="list[${vs.index }].price" onblur="sum1(this);" value="${obj.price}" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                                        <input type="hidden" name="ss" value="${obj.parentId }">
										</c:if> <c:if test="${obj.price==null}">
											<input class="price"  type="text"
												name="list[${vs.index }].price" value="${obj.price }">
										</c:if>--%>
                                        <input type="hidden" name="ss" value="${obj.id }">
                                        <input maxlength="11" class="price"
                                               name="list[${vs.index }].price" onblur="sum1(this);" value="${obj.price}" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                                        <input type="hidden" name="ss" value="${obj.parentId }">
                                    </td>
									<td><input type="hidden" name="ss" value="${obj.id }">
										<input maxlength="11" id="budget"
										name="list[${vs.index }].budget" type="text"
										readonly="readonly" value="${obj.budget}" class="budget" />
                                        <input type="hidden" name="ss" value="${obj.parentId }">
                                    </td>
									<td class="tc"><input type="hidden" name="ss"
										value="${obj.id }"> <textarea
											name="list[${vs.index }].deliverDate" 
											class="target deliverdate">${obj.deliverDate}</textarea> <!-- <input
										type="hidden" name="history" value="" /> -->
										</td>

									<td>
										<%--     
										  <c:if test="${obj.price!=null}"> --%> <input
										type="hidden" name="ss" value="${obj.id}" /> <select
										name="list[${vs.index }].purchaseType"
										<c:if test="${obj.price==null}"> onchange="changeType(this);" </c:if>
										<c:if test="${obj.price!=null}"> onchange="ssl(this);" </c:if>
										class="purchasetype" id="select">
											<option value="">请选择</option>
											<c:forEach items="${kind}" var="kind">
												<option value="${kind.id}"
													<c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>>
													${kind.name}</option>
											</c:forEach>
									</select> <!-- <input type="hidden" name="history" value="" /> --> <%--    </c:if> --%>
									</td>
									<td><input type="hidden" name="ss" value="${obj.id }">
										<textarea name="list[${vs.index }].supplier"
											  onmouseover="supplierReadOnly(this)" onblur="checkSupplierName(this)"  class="target purchasename">${obj.supplier}</textarea>
										<!-- <input type="hidden" name="history" value="" /> --></td>
									<td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text" name="list[${vs.index }].isFreeTax"
										 value="${obj.isFreeTax}"
										class="freetax"></td>
									
										<td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text"
											name="list[${vs.index }].goodsUse" value="${obj.goodsUse}"></td>
										<td name="userNone" <c:if test="${list[0].enterPort==0}"> style="display:none;" </c:if>><input type="text"
											name="list[${vs.index }].userUnit" value="${obj.useUnit}"></td>
									
									<td><div class="memo">
											<input type="hidden" name="ss" value="${obj.id }">
											<textarea name="list[${vs.index }].memo"
												 class="target purchasename">${obj.memo}</textarea>
											<!-- <input type="hidden" name="history" value="" /> -->
										</div></td>
									<td><c:if test="${obj.purchaseCount!=null}">
											<div class="extrafile">
												<u:upload id="up_${vs.index}" multiple="true"
													businessId="${obj.id}" buttonName="上传文件" sysKey="2"
													typeId="${detailId}" auto="true" />
												<u:show showId="show_${vs.index}" businessId="${obj.id}"
													sysKey="2" typeId="${detailId}" />
											</div>
										</c:if> <input type="hidden" class="ptype" name="ptype"
										value="${obj.purchaseType}" /></td>
<td class="tc w100"><input type="hidden" value="${obj.id}"/>   <button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>

									<!--       <td class="tc w100"><input type="text" value="暂存" readonly="readonly"></td> -->
								</tr>

							</c:forEach>

							<input type="hidden" name="planName">
							<input type="hidden" name="planNo">
							<input type="hidden" id="planNo" value="${planNo }">
							<input type="hidden" name="planType">
							<input type="hidden" name="mobile">
							<input type="hidden" name="referenceNo" />
							<input type="hidden" name="delobjId" />
					</form>
				</table>
			</div>
			<div class="col-md-12  mt10 col-sm-12 col-xs-12 tc">
				<input class="btn btn-windows git" type="button" onclick="submit()"
					value="保存"> <input class="btn btn-windows back" value="返回"
					type="button" onclick="location.href='javascript:history.go(-1);'">
			</div>

		</div>
	</div>
	<div id="organization" class="dnone">
		<p align="center">编制说明
		<p style="margin-left: 20px;">1、请严格按照序号顺序为：一、（一）、1（1）、a、（a）的顺序填写序号，括号为中文括号</p>

		<p style="margin-left: 20px;">2、任务明细最多为六级,请勿多于六级</p>

		<p style="margin-left: 20px;">3、请勿空行填写</p>

		<p style="margin-left: 20px;">4、需求单位名称不能为空</p>

		<p style="margin-left: 20px;">5、请按表式填写需求明细。用户可以编辑行，但不能增加或删除列。</p>

		<p style="margin-left: 20px;">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)
		</p>

		<p style="margin-left: 20px;">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

		<p style="margin-left: 20px;">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

		<p style="margin-left: 20px;">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。并在Excel中对应的值写“另附”，详见Excel模板。</p>

		<p style="margin-left: 20px;">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
		<button class="btn padding-left-10 padding-right-10 btn_back"
			style="margin-left: 230px;" onclick="closeLayer()">确定</button>

	</div>

	<input type="hidden" id="count" value="0">
	<div id="catalogue" class="dnone">
		<div id="ztree" class="ztree"></div>
	</div>
	<div class=" clear margin-top-30" id="file_div" style="display: none;">
		<div class="col-md-12 col-sm-12 col-xs-12">

			<p class="red" style="font-size: 16px;">注：请选择无标签水印的文件！</p>


			<input type="file" id="fileName" class="input_group" name="file">
		</div>
		<div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
			<input type="button" class="btn input" onclick="fileup()" value="导入" />
		</div>
	</div>
<div id="materialName" class="dnone" style="width:178px;max-height:400px;overflow:scroll;border:1px solid grey;">
				
		</div>
</body>
<script type="text/javascript">
/*  	window.onload = function () {
    $.ajax({
        url: "${pageContext.request.contextPath}/purchaser/getInfoByNo.html",
        type: "post",
        data: {
            planNo: "${planNo}",
            type: "${org_advice}"
        },
        dataType: "json",
        success: function (data) {
            var kind = [], sources = [], kinds = data.kind;
            kinds.forEach(function (item) {
                kind.push(item.name);
            });
            var check = function (_kind, _purchaseType) {
                var type = "";
                _kind.forEach(function (item) {
                    if (item.id == _purchaseType) {
                        type = item.name;
                        return;
                    }
                });
                return type;
            }
            data.list.forEach(function (item) {
                var source = {
                    id:item.id,
                    department: item.department,
                    goodsName: item.goodsName,
                    stand: item.stand,
                    qualitstand: item.qualitStand,
                    item: item.item,
                    purchaseCount: item.purchaseCount,
                    price: item.price,
                    budget: item.budget,
                    deliverDate: item.deliverDate,
                    purchaseType: check(kinds, item.purchaseType),
                    purchasename: item.supplier,
                    isFreeTax: item.isFreeTax,
                    memo: item.memo,
                    extrafile: "<a href='http://www.amazon.com/Professional-JavaScript-Developers-Nicholas-Zakas/dp/1118026691'>点击查看附件</a>"
                }
                sources.push(source);
            });
            var args = {
                id: "container",
                data: sources,
                colHeaders: ["需求部门", "物资类别及名称", "规格型号", "质量技术标准(技术参数)", "计量单位", "采购数量", "单价(元)", "预算金额(万元)", "交货日期", "采购方式", "供应商名称", "是否申请办理免税", "备注", "附件"],
                columns: {
                    source: kind
                }
            };
            var table = showData(args);

        },
        error: function (data) {
        }
    });
}
var showData = function (options) {
    options.config = {
        data: options.data,
        rowHeaders: true,
        colHeaders: options.colHeaders,
        colWidths: 120,
        manualColumnMove: false,
        manualRowMove: true,
        minSpareRows: 1,
        persistentState: true,
        manualColumnResize: true,
        manualRowResize: true,
        fixedColumnsLeft: 1,
        autoColumnSize: true,
        contextMenu: false,
        search: true,
        columns: [
            {data: 'department', type: "text"},
            {data: 'goodsname', type: "text"},
            {data: 'stand', type: "text"},
            {data: 'qualitstand', type: "text"},
            {data: 'item', type: "text"},
            {data: 'purchasecount', type: "numeric", format: "0"},
            {data: 'price', type: "numeric", format: "0.00"},
            {data: 'budget', type: "numeric", format: "0,000.00"},
            {data: 'deliverdate', type: "date", dateFormat: "YYYY-DD-MM", correctFormat: true},
            {data: 'purchasetype', type: 'dropdown', source: options.columns.source},
            {data: 'purchasename', type: "text"},
            {data: 'freetax', type: "dropdown", source: ["是", "否"]},
            {data: 'memo', type: "text"},
            {data: 'extrafile', renderer: "html"}
        ],
        cells: function (row, col, prop) {
            var cellProperties = {};
            if (col === 0) {
                cellProperties.readOnly = true;
            }
            return cellProperties;
        },
        afterChange: function (change, source) {
            if (source === 'loadData') {
                return;
            }
            options.data.forEach(function (item, index, array) {
                if (index === change[0][0]) {
                    item[change[0][1]] = change[0][3];
                    array[index] = item;
                    options.updata = array;  //TODO  更新数据
                    return;
                }
            });

        }
    };
    options.container = document.getElementById(options.id);
    options.handsonTable = new Handsontable(options.container, options.config);
    return options;
}  */
</script>
</html>
