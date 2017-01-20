<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
     /** 全选全不选 */
    function selectAll(index){
         var checklist = document.getElementsByName ("chkItemExpert"+index);
         var checkAll = document.getElementById("checkAllExpert"+index);
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
    
    /** 单选 */
    function check(index){
         var count=0;
         var checklist = document.getElementsByName ("chkItemExpert"+index);
         var checkAll = document.getElementById("checkAllExpert"+index);
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
         /**添加临时专家*/
         function addexp(){
               var id=[]; 
                $('input[name="chkItem"]:checked').each(function(){ 
                    id.push($(this).val());
                }); 
                if(id.length==1){
                	//$("#tab-1").load("${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?packageId="+id+"&&projectId=${project.id}&&&&flowDefineId=${flowDefineId}");
                    window.location.href="${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?packageId="+id+"&&projectId=${project.id}&&&&flowDefineId=${flowDefineId}";
                }else if(id.length>1){
                    layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
                }else{
                    layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
                }
         }
         
         /**添加组长*/
         function addLeader(){
               var id=[]; 
                $('input[name="chkItem"]:checked').each(function(){ 
                    id.push($(this).val());
                }); 
                if(id.length==1){
                    window.location.href="${pageContext.request.contextPath}/packageExpert/showExpert.html?packageId="+id+"&&flowDefineId=${flowDefineId}";
                }else if(id.length>1){
                    layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
                }else{
                    layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
                }
         }
         /** 执行完成*/
         function finish(){
             layer.confirm('确定之后不可修改，是否确定？', {
                  btn: ['确定','取消'],offset: ['100px', '300px'], shade:0.01
                }, function(index){
                    $.ajax({
                        type: "POST",
                        url:"${pageContext.request.contextPath}/packageExpert/executeFinish.html?flowDefineId=${flowDefineId}&&projectId=${project.id}",
                        dataType:"json",
                        success: function(data) {
                            if(data == "SCCUESS"){
                                  window.location.href = '${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId=${project.id}&&flowDefineId=${flowDefineId}';
                            }else{
                                   layer.alert("请选择组长",{offset: ['100px', '300px'], shade:0.01});
                            }
                        }
                    });
                    
                }, function(index){
                    layer.close(index);
                });
             
             
        
         }
     function ycDiv(obj, index) {
  	  	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
            $(obj).removeClass("shrink");
            $(obj).addClass("spread");
          } else {
            if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
              $(obj).removeClass("spread");
              $(obj).addClass("shrink");
            }
          }
          var divObj = new Array();
          divObj = $(".p0" + index);
          for (var i =0; i < divObj.length; i++) {
              if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                $(divObj[i]).removeClass("hide");
              } else {
                if ($(divObj[i]).hasClass("p0"+index)) {
                  $(divObj[i]).addClass("hide");
                };
              };
          };
      }
      
      $(function() {
       // $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
        var index=0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");        
        $("#package").addClass("spread");
      })
      
      //设置组长
	  function relate(packageId, index, packageName){
		 var id=[]; 
		 var obj = null;
         $('input[name="chkItemExpert'+index+'"]:checked').each(function(){ 
             id.push($(this).val());
             obj = $(this);
         }); 
         if(id.length == 1){
         	 var trObj = obj.parent().parent();	
         	 var tdArr = trObj.children();
         	 var inputObj = tdArr.eq(7).next();//组长列
         	 inputObj.val(1);
         	 tdArr.eq(7).html("是");
         	 
         	 var groupName = "";
         	 groupName = tdArr.eq(5).find("input").val();
         	 //选择临时专家为组长时时
         	 if (typeof(groupName) == "undefined") {
				groupName = tdArr.eq(5).html();
			 }
         	 layer.msg(groupName+"已设为【"+packageName+"】组长",{offset: '50px'});
         	 //未被选中的全置为否
         	 $('input[name="chkItemExpert'+index+'"]').not("input:checked").each(function(){ 
         	 	var trObj = $(this).parent().parent();	
	         	var tdArr = trObj.children();
	         	var inputObj = tdArr.eq(7).next();//组长列
	         	inputObj.val(0);
	         	tdArr.eq(7).html("否");
         	 });
         	 
         }else if(id.length>1){
             layer.alert("只能选择一个",{offset: '50px', shade:0.01});
         }else{
             layer.alert("请选择一名专家",{offset: '50px', shade:0.01});
         }
	  }
	  
	  //专家签到
	  function isSign(obj,expertId){
	  	var v = $(obj).val();
	  	$(".sign_"+expertId).each(function(i){
		  $(this).val(v);
		});
	  }
	  
	  //结束签到
	  function endSignIn(){
	  	$.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/packageExpert/endSignIn.html",  
               data: $("#save_sign").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['50px']});
                    }else{
                    	$('button[name="addExp_btn"]').each(function(){ 
                    		$(this).attr("class","dnone");
                    	});
                    	$("#end_submit").attr("class","dnone");
                    	$('button[name="viewExp_btn"]').each(function(){ 
                    		$(this).removeAttr("class","dnone");
                    		$(this).attr("class","btn");
                    	});
                        layer.msg(result.msg,{offset: ['50px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['50px']});
                }
            });
	  }
	  
	   /**重置密码*/
	 function resetPwd(index){
 	   	var id=[]; 
        $('input[name="chkItemExpert'+index+'"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length>=1){
     	   $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ExpExtract/resetPwd.do?eid="+id+"&&flowDefineId=${flowDefineId}",
                dataType: "json",
                success: function(data){
             	   if("sccuess" == data){
                      layer.alert("重置成功！默认密码：123456",{offset: '50px', shade:0.01});
                   }else{
                 	  layer.alert("重置失败！请尝试重新重置",{offset: '50px', shade:0.01});
                   }
                }
            });
        }else{
            layer.alert("请选择需要重置密码的专家",{offset: '50px', shade:0.01});
        }
	 }
	 
	 //添加临时专家
	 function addExpert(index,projectId,packageId){
	 		$("#tab-1").load("${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?projectId="+projectId+"&flowDefineId=${flowDefineId}");
	 		/* 列表里添加临时专家
	 		var listCount =  parseInt($("#listCountId").val()) + parseInt(1);
	 		$("#listCountId").val(listCount);
	 		var tbyId = "#tby_"+index+" tr";
	 		var xuhao = $(tbyId).length+1;
	 		$.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/packageExpert/returnNew.html",
                dataType: "json",
                success: function(result){
             	  if(!result.success){
                        layer.msg("添加失败",{offset: ['50px']});
                  }else{
                  	var trhtml = "<tr>";
                  		trhtml += "<input type='hidden' name='packageExperts["+listCount+"].isTempExpert' value='0'>";
				 	    trhtml += "<input type='hidden' name='packageExperts["+listCount+"].projectId' value='"+projectId+"'>";
						trhtml += "<input type='hidden' name='packageExperts["+listCount+"].packageId' value='"+packageId+"'>";
				 		trhtml += "<td class='tc opinter w50'>";
		            	trhtml += "<input type='checkbox' value='"+result.expertId+"' name='chkItemExpert"+index+"' onclick='check("+'"'+index+'"'+")'>";
		            	trhtml += "<input type='hidden' name='packageExperts["+listCount+"].expert.id' value='"+result.expertId+"'>";
						trhtml += "</td>";		
		            	trhtml += "<td class='tc'>"+xuhao+"</td>";		
		            	trhtml += "<td><input type='text' class='tc w100' maxlength='16' name='packageExperts["+listCount+"].expert.relName'/></td>";
				 		trhtml += "<td><select class='tc w80' name='packageExperts["+listCount+"].reviewTypeId'>";
				 		var ddL='${ddJson}';
				 		var data = $.parseJSON(ddL);
				 		for ( var i = 0; i < data.length; i++) {
				 			trhtml += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
						}
				 		trhtml += "</select></td>";
				 		trhtml += "<td class='tc'>否</td>";
				 		trhtml += "<input class='tc w100' type='hidden' name='packageExperts["+listCount+"].isGroupLeader' value='0' >";
				 		trhtml += "<td>";
				 		trhtml += "<select class='tc w80' name='packageExperts["+listCount+"].isSigin'>";
				 		trhtml += "<option value='1'>已到场</option><option value='0'>未到场</option></select>";
				 		trhtml += "</td>";
				 		trhtml += "<td><input type='text' class='tc w100' maxlength='40' name='packageExperts["+listCount+"].expert.idCardNumber'/></td>";
				 		trhtml += "<td><input type='text' class='tc w100' maxlength='50' name='packageExperts["+listCount+"].expert.atDuty'/></td>";
				 		trhtml += "<td><input type='text' class='tc w80' maxlength='16' name='packageExperts["+listCount+"].expert.mobile'/></td>";
				 		trhtml += "</tr>";
	 				$("#tby_"+index).append(trhtml);
                  }
                }
            }); */
	 }
  </script>
  <body>
        <h2 class="list_title">专家签到<button class="btn fr" name="addExp_btn" onclick="addExpert('${vs.index}','${project.id}','${pack.id}');" type="button">添加临时专家</button></h2>
        <input type="hidden" id="reviewTypeTds">
        <form id="save_sign"  method="post">
        	<input name="projectId" type="hidden" value="${project.id}">
        	<c:set var="listCount" value="0" />
	        <c:forEach items="${packageList}" var="pack" varStatus="vs">
		        <div class="over_hideen">
					<h2 onclick="ycDiv(this,'${vs.index}')" class="count_flow shrink hand fl clear" id="package">包名:<span class="f15 blue">${pack.name}</span>
		          	</h2>
	          		<div class="fl mt20 ml10">
		             <button class="btn" name="addExp_btn" onclick="relate('${pack.id}','${vs.index}','${pack.name}')" type="button">设为组长</button>
		             <button class="btn" name="viewExp_btn" onclick="resetPwd('${vs.index}');" type="button">重置密码</button>
		             <%-- <button class="btn" name="addExp_btn" onclick="addExpert('${vs.index}','${project.id}','${pack.id}');" type="button">添加临时专家</button> --%>
		           	</div>
		        </div>
	        	<div class="p0${vs.index} hide">
	        		
		          	<input type="hidden" id="packId" value="${pack.id}" />
		        	<table class="table table-bordered table-condensed table-hover table-striped mt5 space_nowrap table_input left_table">
			            <thead>
			              <tr>
			                <th class="info w50"><input id="checkAllExpert${vs.index}" type="checkbox" onclick="selectAll('${vs.index}')" /></th>
			                <th class="info w50">序号</th>
			                <th class="info">专家姓名</th>
			                <th class="info">专家类别</th>
			                <th class="info">是否组长</th>
			                <th class="info">是否到场</th>
			                <th class="info">证件号</th>
			                <th class="info">现任职务</th>
			                <th class="info">联系电话</th>
			              </tr>
			            </thead>
		            	<tbody id="tby_${vs.index}">
		            		<c:forEach items="${pack.listProjectExtract}" var="projectExtract" varStatus="v">
		            		<c:set var="listCount" value="${listCount+1}" />
		            		<tr>
		            			<input type="hidden" name="packageExperts[${listCount}].isTempExpert" value="1">
			            		<input type="hidden" name="packageExperts[${listCount}].projectId" value="${project.id}">
			            		<input type="hidden" name="packageExperts[${listCount}].packageId" value="${pack.id}">
		            			<td class="tc opinter w50">
		            				<input type="checkbox" value="${projectExtract.expert.id}" name="chkItemExpert${vs.index}" onclick="check('${vs.index}')">
		            				<input type="hidden" name="packageExperts[${listCount}].expertId" value="${projectExtract.expert.id}">
								</td>
		            			<td class="tc">${v.index+1}</td>
		            			<td>
		            				${projectExtract.expert.relName}
		            			</td>
		            			<td class="tc">
				                  <c:forEach var="expertType" items="${ddList}">
				                   <c:if test="${projectExtract.reviewType eq expertType.id}">
				                    ${expertType.name}
				                    <input type="hidden" name="packageExperts[${listCount}].reviewTypeId" value="${expertType.id}">
				                   </c:if>
				                  </c:forEach>
		                		</td>
				                <td class="tc">
				                	否
				                </td>
			                    <input type="hidden" name="packageExperts[${listCount}].isGroupLeader" value="0" >
				                <td>
				                	<select onchange="isSign(this,'${projectExtract.expert.id}');" class="sign_${projectExtract.expert.id}" name="packageExperts[${listCount}].isSigin">
										<option value="1">已到场</option>
										<option value="0">未到场</option>
									</select>
				                </td>
		                		<td class="tc">${projectExtract.expert.idCardNumber}</td>
				                <td class="tc">${projectExtract.expert.atDuty}</td>
				                <td class="tc">${projectExtract.expert.mobile}</td>
				            </tr>
		            		</c:forEach>
		            	</tbody>
	            	</table>
	            </div>
	        </c:forEach>
	        <input type="hidden" id="listCountId" value="${listCount}">
	        <div class="col-md-12 tc mt20" id="end_submit">
	  			<button class="btn" onclick="endSignIn();" type="button">结束签到</button>
	  	   	</div>
        </form>
        <%-- <c:if test="${execute != 'SCCUESS' }">
          <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
             <button class="btn btn-windows add" onclick="addexp();" type="button">添加专家</button>
             <button class="btn " onclick="addLeader();" type="button">分配组长</button>
             <button class="btn " onclick="finish();" type="button">完成</button>
          </div>
        </c:if>
        <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="w50 info">序号</th>
              <th class="info">包名</th>
              <th class="info">组长</th>
              <th class="info">专家人数</th>
            </tr>
            </thead>
            <c:forEach items="${packageList }" var="pack" varStatus="vs">
                <tr>
                    <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${pack.id}" /></td>
                    <td class="tc w30">${vs.count }</td>
                    <td class="tc">${pack.name }</td>
                     <td class="tc">
                      <c:set value="0" var="num"></c:set>
                      <c:forEach items="${selectList}" var="list"> 
                         <c:if test="${pack.id==list.packageId}">
                                ${list.expert.relName}
                           <c:set value="1" var="num"></c:set>
                         </c:if>
                      </c:forEach>
                      <c:if test="${num==0}">
                                                                             暂无
                      </c:if>
                    </td>
                    <td class="tc"><a href="${pageContext.request.contextPath}/packageExpert/showExpert.html?packageId=${pack.id}&&flowDefineId=${flowDefineId}&&execute=${execute}">${fn:length(pack.listExperts)}</td>
                </tr>
            </c:forEach>
        </table> --%>
  </body>
</html>
