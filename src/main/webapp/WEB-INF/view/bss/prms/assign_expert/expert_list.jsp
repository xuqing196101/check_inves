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
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).hide();
            }); 
		}
       	var packs="${packIds}";
       	if(packs!=""&&packs!=null){
       		var ids=packs.split(",");
       		for(var i=0;i<ids.length;i++){
       			var peeid=getCookie(ids[i]+"_peeid");
       			if(peeid!=""){
       				$("#"+peeid).text("是");
             	$("#"+peeid).next().val('1');
       			}
       		}
       	}
       	var ppids=document.getElementsByName("ppid");
       	for(var i=0;i<ppids.length;i++){
       		var ppid=getCookie($(ppids[i]).attr("id"));
       		if(ppid!=''){
       			$(ppids[i]).find("select").val(ppid);
       		}
       	}
    })
    function getCookie(cname)
			{
			  var name = cname + "=";
			  var ca = document.cookie.split(';');
			  for(var i=0; i<ca.length; i++) 
			  {
			    var c = ca[i].trim();
			    if (c.indexOf(name)==0) return c.substring(name.length,c.length);
			  }
			  return "";
			}
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
      function setCookie(cname,cvalue,exdays)
			{
			  var d = new Date();
			  d.setTime(d.getTime()+(exdays*24*60*60*1000));
			  var expires = "expires="+d.toGMTString();
			  document.cookie = cname + "=" + cvalue + "; " + expires;
			}
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
         	 var inputObj = tdArr.eq(8).next();//组长列
         	 inputObj.val(1);
         	 tdArr.eq(8).html("是");
         	 
         	   
         	 var groupName = "";
         	 groupName = tdArr.eq(6).find("input").val();
         	 //选择临时专家为组长时时
         	 if (typeof(groupName) == "undefined") {
				groupName = tdArr.eq(6).html();
			 }
         	 layer.msg(groupName+"已设为【"+packageName+"】组长",{offset: '50px'});
         	 //未被选中的全置为否
         	 $('input[name="chkItemExpert'+index+'"]').not("input:checked").each(function(){ 
         	 	var trObj = $(this).parent().parent();	
	         	var tdArr = trObj.children();
	         	var inputObj = tdArr.eq(8).next();//组长列
	         	inputObj.val(0);
	         	tdArr.eq(8).html("否");
         	 });
         	setCookie(packageId+"_peeid",packageId+"_"+id[0],0.1);
         	/*  var packId = tdArr.eq(4).children("input:first-child").val(); */
         	/* $.ajax({
                url: "${pageContext.request.contextPath}/packageExpert/isGroupLeader.html",
                type: "POST",
                data:{
                	id:packId
                },
                dataType: "json",
                success: function(data){
                }
            });  */
         }else if(id.length>1){
             layer.alert("只能选择一个",{offset: '50px', shade:0.01});
         }else{
             layer.alert("请选择一名专家",{offset: '50px', shade:0.01});
         }
	  }
	  
	  //专家签到
	  function isSign(obj,expertId,packageId){
	  	/* var v = $(obj).val();
	  	$(".sign_"+expertId).each(function(i){
		  $(this).val(v);
		}); */
		setCookie($(obj).parent().attr("id"),$(obj).val(),0.1);
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
                    	//隐藏按钮
                    	$('button[name="addExp_btn"]').each(function(){ 
                    		$(this).attr("class","dnone");
                    	});
                    	//隐藏引用临时专家按钮
                    	$('button[name="citeExp_btn"]').each(function(){ 
                    		$(this).attr("class","dnone");
                    	});
                    	$("#end_submit").attr("class","dnone");
                    	$('button[name="viewExp_btn"]').each(function(){ 
                    		$(this).removeAttr("class","dnone");
                    		$(this).attr("class","btn");
                    	});
                    	$('button[name="editExp_btn"]').each(function(){ 
                    		$(this).attr("class","dnone");
                    	});
                    	//隐藏导入临时专家按钮
                    	$("#updateExcel").attr("class","dnone");
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
        $("input[name='excelFile']").on("change",function () {
            var formData = new FormData($( "#updateExcel" )[0]);
            $.ajax({
                url: '${pageContext.request.contextPath}/expert/readExcelExpert.do?packageId='+ $("#_packageId").val(),
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    var obj = eval('('+data+')')
                    if(obj.isSuccess){
                        if(obj.messageCode==10){
                            layer.alert("文件不存在",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==11){
                            layer.alert("文件内容为空",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==12){
                            layer.alert(obj.loginName+"账号已存在",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==13){
                            layer.alert("未填写名称和密码",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==14){
                            layer.alert(obj.loginName+"居民身份证已存在",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==15){
                            layer.alert(obj.loginName+"联系电话已存在",{offset: '50px', shade:0.01});
                        }else if(obj.messageCode==20){
                            layer.alert("保存成功",{offset: '50px', shade:0.01, time:500});
                            window.location.reload(true);
                        }else{
                            layer.alert("服务器异常",{offset: '50px', shade:0.01});
                        }
                    }else{
                        layer.alert("服务器异常",{offset: '50px', shade:0.01});
                    }
                },
                error: function () {

                }
            });
        })
      $("#packageName").click(function () {
          showPackageType();
      })
    //点击:导入临时专家(先进行包的校验后再运行)
      $("#exportExpertA").click(function () {
          var _packageId = $("#_packageId").val();
          if(_packageId == ""){
              layer.alert("请先选择包后导入",{offset: '50px', shade:0.01});
          }else{
              $("#excel_file").val("");
              $("#excel_file").click();
          }
      })
      $("#downTemplate").click(function () {
          window.location.href = "${pageContext.request.contextPath }/expert/downloadExpertTemplate.do";
      })
      
    //点击:引用临时专家
      $("button[name='citeExp_btn']").click(function () {
          var packageId = $(this).attr('package-id');
          var projectId = $("input[name='projectId']").val();
          var path = "${pageContext.request.contextPath}/expert/gotoCiteExpertView.html?packageId="+packageId+"&projectId="+projectId;
          $("#tab-1").load(path);

      })
      
      /* 修改临时专家 */
      function editExpert(packId,index){
    	  var projectId = $("input[name='projectId']").val();
      	  var ids =[]; 
          $('input[name="chkItemExpert'+index+'"]:checked').each(function(){ 
            ids.push($(this).val()); 
          }); 
          if(ids.length==1){
        	  id = ids.toString();
        	  //判断是否为临时专家
        	  $.ajax({
					url: "${pageContext.request.contextPath }/ExpExtract/isProvisional.html",
					type: "post",
					data: {
						id : id
					},
					success: function(data) {
						if(data == "true"){
							$("#tab-1").load("${pageContext.request.contextPath}/ExpExtract/showEditTemporaryExpert.html?projectId="+projectId+"&id="+id+"&index="+index+"&packageId="+packId);
						}else if(data == "false"){
							layer.alert("只能修改临时专家",{offset: ['222px', '390px'], shade:0.01});
						}
					},
					error: function() {

					}
				});
          }else if(ids.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
          }else{
            layer.alert("请选择要修改的临时专家",{offset: ['222px', '390px'], shade:0.01});
          }
    }
  </script>
  <body>
      <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999; ">
          <ul id="treePackageType" class="ztree" style="margin-top:0;width: 170px;height: 250px;"></ul>
      </div>
        <h2 class="list_title" style="margin-bottom: 30px;">专家签到
            <form enctype="multipart/form-data" id="updateExcel" >
                <a href="javascript:;" class="file btn fr" id="exportExpertA">导入临时专家</a>
                <input type="file" class="btn fr" name="excelFile" id="excel_file" style="display: none"/>
                <input type="hidden" id="_packageId" name="packageId"/>
                <input type="text" id="packageName" class="title col-md-12 fr w120" placeholder="请先选择包后导入" style="border-color: #2c9fa6"/>
            </form>
            <a href="javascript:;" class="btn btn-windows input fr" id="downTemplate">导出模板</a>
            <button class="btn fr" name="addExp_btn" onclick="addExpert('${vs.index}','${project.id}','${pack.id}');" type="button">添加临时专家</button>
        </h2>
        <input type="hidden" id="reviewTypeTds">
        <form id="save_sign"  method="post">
        	<input name="projectId" type="hidden" id="projectId" value="${project.id}">
        	<input name="flowDefineId" type="hidden" value="${flowDefineId}"/>
        	<c:set var="listCount" value="0" />
	        <c:forEach items="${packageList}" var="pack" varStatus="vs">
		        <div class="over_hideen">
					<h2 onclick="ycDiv(this,'${vs.index}')"  <c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ'}">class="count_flow hand fl spread"</c:if>class="count_flow shrink hand fl clear" id="package">
					包名:<span class="f15 blue">${pack.name}
					 <c:if test="${pack.projectStatus eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
					 <c:if test="${pack.projectStatus eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
					 <c:if test="${pack.projectStatus eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
				  </span>
		          	</h2>
	          		<div class="fl mt20 ml10">
		             <button class="btn"  <c:if test="${pack.projectStatus eq'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ'}">disabled="disabled"</c:if>name="addExp_btn" onclick="relate('${pack.id}','${vs.index}','${pack.name}')" type="button">设为组长</button>
		             <button class="btn" <c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ'}">disabled="disabled"</c:if>name="viewExp_btn" onclick="resetPwd('${vs.index}');" type="button">重置密码</button>
		             <button class="btn" <c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ'}">disabled="disabled"</c:if>name="citeExp_btn"  type="button" package-id="${pack.id}">引用临时专家</button>
		             <button class="btn" <c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ'}">disabled="disabled"</c:if>name="editExp_btn" onclick = "editExpert('${pack.id}','${vs.index}')" type="button">修改临时专家</button>
		             <%-- <button class="btn" name="addExp_btn" onclick="addExpert('${vs.index}','${project.id}','${pack.id}');" type="button">添加临时专家</button> --%>
		           	</div>
		        </div>
		        <c:if test="${pack.projectStatus ne 'YZZ' && pack.projectStatus ne 'ZJZXTP' && pack.projectStatus ne 'ZJTSHZ'}">
	        	<div class="p0${vs.index} hide">
	        		
		          	<input type="hidden" id="packId" value="${pack.id}" />
		        	<table class="table table-bordered table-condensed table-hover table-striped mt5 space_nowrap table_input left_table">
			            <thead>
			              <tr>
			                <th class="info w50"><input id="checkAllExpert${vs.index}" type="checkbox" onclick="selectAll('${vs.index}')" /></th>
			                <th class="info w50">序号</th>
			                <th class="info">用户名</th>
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
		            				${projectExtract.expertId}
		            			</td>
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
				                <td class="tc" name="peeid" id="${pack.id}_${projectExtract.expert.id}">
					                <c:if test="${projectExtract.expert.id eq peeid }">是</c:if>
					                <c:if test="${projectExtract.expert.id ne peeid }">否</c:if>
				                </td>
			                    <input type="hidden" name="packageExperts[${listCount}].isGroupLeader" value="0" >
				                <td name="ppid" id="${pack.id}_${projectExtract.expert.id}_${projectExtract.expertId}">
				                	<select onchange="isSign(this,'${projectExtract.expert.id}','${pack.id}');" class="sign_${projectExtract.expert.id}" name="packageExperts[${listCount}].isSigin">
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
	            </c:if>
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
<script type="text/javascript">
    function showPackageType() {
        var setting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "",
                    "N": ""
                }
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                }
            },
            callback: {
                beforeClick: beforeClick,
                onCheck: onCheck
            }
        };
        $.ajax({
            type: "GET",
            async: false,
            url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId="+$("#projectId").val(),
            dataType: "json",
            success: function(zNodes) {
                var _json = eval(zNodes);
                var _packageId = $("#_packageId").val();
                $.each(_json, function (n, obj) {
                    if(_packageId!= "" && _packageId.indexOf(obj.id)!=-1){
                        obj.checked = true;
                    }

                });
                tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
                tree.expandAll(true); //全部展开
            }
        });
        var cityObj = $("#packageName");
        var cityOffset = $("#packageName").offset();
        $("#packageContent").css({
            left: (cityOffset.left + 16) + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownPackageType);
    }

    function onBodyDownPackageType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
            hidePackageType();
        }
    }

    function hidePackageType() {
        $("#packageContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownPackageType);

    }

    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#packageName");
        cityObj.attr("value", v);
        cityObj.attr("title", v);
        $("#_packageId").val(rid);
    }
</script>