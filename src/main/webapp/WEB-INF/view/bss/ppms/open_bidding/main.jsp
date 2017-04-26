<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">

<script type="text/javascript">
	$(function(){
	    $("#menu a").click(function() {
		    $('#menu li').each(function(index) {
			    $(this).removeClass('active');  // 删除其他兄弟元素的样式
			  });
	        $(this).parent().addClass('active'); // 添加当前元素的样式
	    });
	    /*上面一个的问题就是 $(this).parent().addClass('active')这个a标签不是循环出来的 是自定义的它的id是as */
	    $(document).ready(function() {
		    $("#menu li").click(function(){
				$(this).addClass("active").siblings().removeClass("active");
			});
		});
		var projectId = "${project.id}";
		$.ajax({
			url: "${pageContext.request.contextPath }/open_bidding/getNextFd.do?flowDefineId=0&projectId="+projectId,
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data.success) {
					//当前环节经办人
					$("#currHuanjieId").val(data.currFlowDefineId);
					$("#currPrincipal").empty();
					if (data.users != null && data.users != '') {
						$.each(data.users, function(i, user) {
							$("#currPrincipal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
						});
					} else {
						$("#currPrincipal").append("<option  value=" + data.currOperatorId + ">" + data.operateName + "</option>");
					}
					$("#currPrincipal").select2();
					$("#currPrincipal").select2("val", data.currOperatorId);
					$("#currPrincipalId").val(data.currOperatorId);
					$("#isOperate").val(data.isOperate);
					//禁止变更经办人操作
					if (data.isOperate == 0) { 
						$("#submitdiv").attr("disabled", true);
						$("#principal").attr("disabled", true);
						$("#currPrincipal").attr("disabled", true);
					} else {
						$("#submitdiv").attr("disabled", false);
						$("#principal").attr("disabled", false);
						$("#currPrincipal").attr("disabled", false);
					}
					if (!data.isEnd) {
						$("#nextHaunjie").show();
						$("#updateOperateId").show();
						$("#huanjie").html(data.flowDefineName);
						$("#huanjieId").val(data.flowDefineId);
						$("#principal").empty();
						if (data.users != null && data.users != '') {
							$.each(data.users, function(i, user) {
								$("#principal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
							});
						} else {
							$("#principal").append("<option  value=" + data.operatorId + ">" + data.nextOperatorName + "</option>");
						}
						
						$("#principal").select2();
						$("#principal").select2("val", data.operatorId);
						$("#principalId").val(data.operatorId);
					}
					if (data.isEnd) {
						$("#nextHaunjie").hide();
						$("#updateOperateId").hide();
					}
				} 
			}
		});
		$("#onmouse").addClass("btmfixs");
	}); 
	
	function back(){
	  var type = "${type}";
	  if(type == '1'){
	    location.href = '${pageContext.request.contextPath}/project/projectByAll.html';
	  }else{
	    location.href = '${pageContext.request.contextPath}/project/list.html';
	  }
	}
	
	function jumpLoad(url, projectId, flowDefineId){
		$.ajax({
			url: "${pageContext.request.contextPath }/open_bidding/getNextFd.do?flowDefineId="+flowDefineId+"&projectId="+projectId,
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data.success) {
					//当前环节经办人
					$("#currHuanjieId").val(data.currFlowDefineId);
					$("#currPrincipal").empty();
					if (data.users != null && data.users != '') {
						$.each(data.users, function(i, user) {
							$("#currPrincipal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
						});
					} else {
						$("#currPrincipal").append("<option  value=" + data.currOperatorId + ">" + data.operateName + "</option>");
					}
					$("#currPrincipal").select2();
					$("#currPrincipal").select2("val", data.currOperatorId);
					$("#currPrincipalId").val(data.currOperatorId);
					$("#isOperate").val(data.isOperate);
					//禁止变更经办人操作
					if (data.isOperate == 0) { 
						$("#submitdiv").attr("disabled", true);
						$("#principal").attr("disabled", true);
						$("#currPrincipal").attr("disabled", true);
					} else {
						$("#submitdiv").attr("disabled", false);
						$("#principal").attr("disabled", false);
						$("#currPrincipal").attr("disabled", false);
					}
					if (!data.isEnd) {
						$("#nextHaunjie").show();
						$("#updateOperateId").show();
						$("#huanjie").html(data.flowDefineName);
						$("#huanjieId").val(data.flowDefineId);
						$("#principal").empty();
						if (data.users != null && data.users != '') {
							$.each(data.users, function(i, user) {
								$("#principal").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
							});
						} else {
							$("#principal").append("<option  value=" + data.operatorId + ">" + data.nextOperatorName + "</option>");
						}
						$("#principal").select2();
						$("#principal").select2("val", data.operatorId);
						$("#principalId").val(data.operatorId);
					}
					if (data.isEnd) {
						$("#nextHaunjie").hide();
						$("#updateOperateId").hide();
					}
				}
			}
		});
		var urls="${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
      	$("#as").attr("href",urls);
      	var el=document.getElementById('as');
       	el.click();//触发打开事件
      	// $("#open_bidding_main").load(urls);
	}
	
	//提交下一环节经办人
	function updateOperator(){
		$.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/open_bidding/updateOperator.html",
				dataType: "json", //返回格式为json
                data:$('#updateLinkId').serialize(),
                success: function(data) {
                    if(data.success){
                    	layer.msg("提交下一环节经办人成功",{offset: '100px'});
                    }
                },
                error: function(data){
                    layer.msg("请稍后再试",{offset: '100px'});
                }
            });
	}
	
	//变更当前环节经办人
	function updateCurrOperator(){
		var currUpdateoperator = $("#currPrincipal").select2("data").text;
		layer.confirm('您确认变更当前环节经办人为 【'+currUpdateoperator+'】吗?', {
			btn: ['确定','取消']
		}, function(){
			var projectId = "${project.id}";
			var currFlowDefineId = $("#currHuanjieId").val();
			var currUpdateUserId = $("#currPrincipal").val();
			$.ajax({
	                type: "POST",
	                url: "${pageContext.request.contextPath}/open_bidding/updateCurrOperator.html",
					dataType: "json", //返回格式为json
	                data:{"currFlowDefineId":currFlowDefineId ,"currUpdateUserId":currUpdateUserId, "projectId":projectId},
	                success: function(data) {
	                    if(data.success){
	                    	layer.msg("变更当前环节经办人成功",{offset: '100px'});
	                    	$("#currPrincipalId").val(currUpdateUserId);
	                    	jumpLoad(data.url, projectId, currFlowDefineId);
	                    }
	                },
	                error: function(data){
	                    layer.msg("请稍后再试",{offset: '100px'});
	                }
	          });
	   }, function(){
      		$("#currPrincipal").select2();
			$("#currPrincipal").select2("val", $("#currPrincipalId").val());
      });
	}
	
	function jumpChild(url){
		$("#open_bidding_main").load(url +"#TANGER_OCX");
	}
	
	//页面初始加载将要执行的页面
	function initLoad(){
		var url = $("#initurl").val();
		$("#open_bidding_main").load("${pageContext.request.contextPath}/"+url);
	}
	
	function tips(step){
		if(step != 1){
			layer.msg("请先执行前面步骤",{offset: ['220px']});
		}
	}
	
	//变更下一环节经办人
	function submitCurrOperator(){
		var nextUpdateOperator = $("#principal").select2("data").text;
		layer.confirm('您确认要变更下一环节经办人为【'+nextUpdateOperator+'】吗?', {
			btn: ['确定','取消']
		}, function(){
			var projectId = "${project.id}";
			var nextFlowDefineId = $("#huanjieId").val();
			var nextUpdateUserId = $("#principal").val();
			$.ajax({
	                type: "POST",
	                url: "${pageContext.request.contextPath}/open_bidding/updateCurrOperator.html",
					dataType: "json", //返回格式为json
	                data:{"currFlowDefineId":nextFlowDefineId ,"currUpdateUserId":nextUpdateUserId, "projectId":projectId},
	                success: function(data) {
	                    if(data.success){
	                    	$("#principalId").val(nextUpdateUserId);	
	                    	layer.msg(data.flowDefineName+ "经办人设置成功",{offset: '100px'});
	                    }
	                },
	                error: function(data){
	                    layer.msg("请稍后再试",{offset: '100px'});
	                }
	         });
      	}, function(){
      		$("#principal").select2();
			$("#principal").select2("val", $("#principalId").val());
      	});
	}
	
/* 	function abandoned(id){
	  layer.confirm('您确定要废标吗?',{
              title : '提示',
              offset: ['30%', '40%'],
              shade : 0.01
            },
          function(index) {
            layer.close(index);
           $.ajax({
            url : "${pageContext.request.contextPath}/project/abandoned.html",
            data : "id=" + id,
            type : "post",
            dateType : "text",
            success : function(data) {
              if(data == "\"SCCUESS\"") {
                layer.msg("废标成功", {
                 time: 2000, 
                });
                window.setTimeout(function() {
		              window.location.href = "${pageContext.request.contextPath}/project/list.html";
		            }, 1000);
                
              }
               
               window.setTimeout(function() {
              location.reload();
              }, 1000); 
            },
            error : function() {
              layer.msg("废标失败", {
                offset: ['30%', '40%'],
              });
            }
            });
          });
	} */
	
	
	 function abandoned(id){
	   layer.open({
        type: 2, //page层
        area: ['600px', '400px'],
        title: '废标',
        shade: 0.01, //遮罩透明度
        moveType: 1, //拖拽风格，0是默认，1是传统拖动
        shift: 1, //0-6的动画形式，-1不开启
        shadeClose: true,
        content: '${pageContext.request.contextPath}/project/feibiao.html?id=' + id,
     });
   }
   
   
   
   function bigImg(x){
     $(x).removeClass("btmfixs");
     $(x).addClass("btmfix");
     
   }
   
   function normalImg(x){
     $(x).removeClass("btmfix");
     $(x).addClass("btmfixs");
   }
   
   //提交当前环节
   function submitcurr(){
      var projectId = "${project.id}";
	  var currFlowDefineId = $("#currHuanjieId").val();
	  var currUpdateUserId = $("#currPrincipal").val();
	  layer.confirm('您确定已经完成当前环节操作吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
	      //校验当前环节是否完成
	  	  $.ajax({
	          url : "${pageContext.request.contextPath}/open_bidding/isSubmit.html",
	          data :{"currFlowDefineId":currFlowDefineId , "projectId":projectId},
	          type : "post",
	          dataType: "json", //返回格式为json
	          success : function(data) {
	            if(data.success) {
	              //提交当前环节
	              $.ajax({
			          url : "${pageContext.request.contextPath}/open_bidding/submitHuanjie.html",
			          data :{"currFlowDefineId":currFlowDefineId, "projectId":projectId},
			          type : "post",
			          dataType : "json",
			          success : function(data2) {
			            if(data2.success) {
			              layer.msg("提交成功",{offset: '100px'});
			              jumpLoad(data2.url, projectId, currFlowDefineId);
			            }
			          },
			          error : function() {
			            layer.msg("提交失败",{offset: '100px'});
			          }
	              });
	            }else{
	              if(data.flowType == "XMFB"){
	                //如果是项目分包环节
	                layer.confirm(data.msg,{
                  shade:0.01,
                  btn:['确定','取消'],
                  },function(){
                    $.ajax({
			                url : "${pageContext.request.contextPath}/project/savePackage.html",
			                data :{"projectId":projectId},
			                type : "post",
			                dataType : "json",
			                success : function(data) {
			                  if(data == "1") {
			                    $.ajax({
					                url : "${pageContext.request.contextPath}/open_bidding/submitHuanjie.html",
					                data :{"currFlowDefineId":currFlowDefineId, "projectId":projectId},
					                type : "post",
					                dataType : "json",
					                success : function(data2) {
					                  if(data2.success) {
					                    layer.msg("提交成功",{offset: '100px'});
					                    jumpLoad(data2.url, projectId, currFlowDefineId);
					                  }
					                },
					                error : function() {
					                  layer.msg("提交失败",{offset: '100px'});
					                }
					                });
			                  }
			                },
			                error : function() {
			                  layer.msg("提交失败",{offset: '100px'});
			                }
			                });
                  },function(){
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                  }
                );
	              }
	            }
	          },
	          error : function() {
	            layer.msg("提交失败",{offset: '100px'});
	          }
	      });
	  });
   }
</script>
</head>

<body onload="initLoad()">
  
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="">保障作业</a></li><li><a href="">采购项目管理</a></li><li><a href="">采购项目实施</a></li> 
		</ul>
	  </div>
   </div>
   <!--=== End Breadcrumbs ===-->

   <!--=== Content Part ===-->
   <div class="container content height-350">
            <div class="row">
                <!-- Begin Content -->
                  <div class="col-md-12" style="min-height:400px;">
                      <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
	                     <ul class="btn_list" id="menu">
	                       <c:forEach items="${fds}" var="fd">
								  <!-- 已执行 -->
								  <c:if test="${fd.status == 1}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
		                       			<a class="executed son-menu">${fd.name }</a>
		                       		</li> 
								  </c:if> 
								  <!-- 执行中 -->
								  <c:if test="${fd.status == 2}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:if>
								  <!-- 环节结束，不可在操作 -->
								  <c:if test="${fd.status == 3}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="executed son-menu " >${fd.name }</a>
		                       		</li> 
								  </c:if>
								  <!-- 未执行 -->
								  <c:if test="${fd.status == 0}">
		                       		<li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
		                       			<a class="son-menu">${fd.name }</a>
		                       		</li> 
								  </c:if>
	                       </c:forEach>
						 </ul>
					  </div>
					  <!-- 右侧内容开始-->
					  <input type="hidden" id="initurl" value="${url}">
					  <!-- <div class="tag-box tag-box-v4 col-md-9 "  id="open_bidding_main">
					  		
					  </div> -->
                        <script type="text/javascript" language="javascript">   
                          function getContentSize() {
	         				var he = document.documentElement.clientHeight;
							var btn = $("#iframe_btns").outerHeight(true);
	   						var bread= $("#bread_crumbs").outerHeight(true) ;
							ch = (he - btn - bread) + "px";
							document.getElementById("open_bidding_iframe").style.height = ch;
							}
							window.onload = getContentSize;
							window.onresize = getContentSize;
 					  </script>
                      <!-- 右侧内容开始-->
                      <div class="tag-box tag-box-v4 col-md-9 pt10" >
                      	  <input type="hidden" id="isOperate">
                      	  <form id="updateLinkId" action="" method="post" class="w100p fl mb10 border1 padding-10 bg11">
					      	 <input type="hidden" id="projectId" name="projectId" value="${project.id}">
					      	 <div class="fr" id="updateOperateId">
					      		<span class="fl h30 lh30">经办人：</span>
					      		<div class="w120 fl">
					      			<input type="hidden" id="principalId">
					      			<select id="principal" name="principal" onchange="submitCurrOperator();"></select>
					      		</div>
					      	</div>
					      	<div class="fr mr10" id="nextHaunjie">
					      		<span class="fl h30 lh30">下一环节：</span>
					      		<div  class="fl">
					      		    <input type="hidden" id="huanjieId" name="huanjieId"><span id="huanjie" class="h30 lh30"></span>
					      		</div>
					        </div>
					        <div class="fl mr10">
					      		<span class="fl h30 lh30">变更经办人：</span>
					      		<div  class="w120 fl">
					      			<input type="hidden" id="currPrincipalId">
					      			<input type="hidden" id="currHuanjieId">
					      		    <select id="currPrincipal" name="currPrincipal" onchange="updateCurrOperator()"></select>
					      		</div>
					      		<div class="fl ml5" >
					      			<input id="submitdiv" type="button" class="btn btn-windows git" onclick="submitcurr();" value="环节结束" />
					      		</div>
					        </div>
                      	  </form>
                         <iframe  frameborder="0" name="open_bidding_main" id="open_bidding_iframe"  scrolling="auto" marginheight="0"  width="100%" onLoad="iFrameHeight('open_bidding_iframe')"  src="${pageContext.request.contextPath}/${url}"></iframe>
                      </div>
					  <div id="onmouse" onmouseover="bigImg(this)" onmouseout="normalImg(this)">
					    <div class="mt5 mb5 tc">
					       <%-- <button class="btn btn-windows delete" onclick="abandoned('${project.id}');" type="button">废标</button> --%>
					       <button class="btn btn-windows back" onclick="back();" type="button">返回列表</button>
					    </div>
       	   	</div>
				  </div>
                </div>
        </div><!--/container-->
        	<a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
</body>
</html>
