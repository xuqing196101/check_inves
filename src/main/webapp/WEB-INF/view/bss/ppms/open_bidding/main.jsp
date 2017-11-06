<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en">
<head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
  <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/bss/ppms/main.js"></script>
  <script type="text/javascript">
  var fflog=false;  
  function termination(projectId){
	  fflog=true;
 	  $.ajax({
		url:"${pageContext.request.contextPath}/termination/package.do",
		data:{"projectId":projectId},
		type:"post",
		dataType:"json",
   		success:function(data){
   			$("#openDiv_check").empty();
   			var html='';
   			if(data==null){
   				
   			}else if(data.length==1){
   				html+='<div class=" mt10 ml60 fl"><input type="checkbox" value="'+data[0].id+'" name="pack" />'+data[0].name+'</div>';
   			}else if(data.length>1){
   				for(var i=0;i<data.length;i++){
	   				if((i+1)%2==0){
	   					html+='<div class=" mt10 fl ml10"><input type="checkbox" value="'+data[i].id+'" name="pack" />'+data[i].name+'</div>';
	   					html+='<div class=" clear" name="clear"></div>';
	   				}else{
	   					html+='<div class=" mt10 ml40 fl"><input type="checkbox" value="'+data[i].id+'" name="pack" />'+data[i].name+'</div>';
	   				}
	   			}
	   		}
	   		html+='</div>';
	   		$("#openDiv_check").append(html);
	   		openDetail();
	   	 }
  		});
  	}
    
    var index;
  	function openDetail(){
  	  index =  layer.open({
  	    shift: 1, //0-6的动画形式，-1不开启
  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
  	    title: ['终止包','border-bottom:1px solid #e5e5e5'],
  	    shade:0.01, //遮罩透明度
	  		type : 1,
	  		area : [ '20%', '200px'  ], //宽高
	  		content : $('#openDiv'),
	  	  });
      }
  	function cancel(){
		  layer.close(index);
	  }
	  
  	
  	var ids="";
  	function bynSub(){
  		var val=[];
  		$('input[type="checkbox"]:checked').each(function(){ 
  			val.push($(this).val()); 
  		});
  		if(val.length==0){
  			layer.alert("请选择一个或多个包");
  			return false;
  		}
  		ids=val.join(',');
  		$.ajax({
			url:"${pageContext.request.contextPath}/termination/flowDefineId.do",
			data:{"currFlowDefineId":$("#currHuanjieId").val()},
			type:"post",
			dataType:"json",
	   	success:function(data){
	   		$("#openDiv_checkFlw").empty();
	   		var html='<div class="tl">';
	   		if(data!=null){
	   			html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="XMLX"/>项目立项</div>';
	   			html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="XMFB"/>项目分包</div>';
	   			for(var i=0;i<data.length;i++){
	   				html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="'+data[i].id+'"/>'+data[i].name+'</div>';
	   			}
	   		}
	   		html+='</div>';
	   		$("#openDiv_checkFlw").append(html);
	   		openDetailFlw();
	   	 }
  		});
  	}
    
  	var indexFlw;
  	function openDetailFlw(){
  		indexFlw =  layer.open({
  	    shift: 1, //0-6的动画形式，-1不开启
  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
  	    title: ['终止环节','border-bottom:1px solid #e5e5e5'],
  	    shade:0.01, //遮罩透明度
	  		type : 1,
	  		area : [ '20%', '400px'  ], //宽高
	  		content : $('#openDivFlw'),
	  	  });
      }
  	function cancelFlw(){
		  layer.close(indexFlw);
	  }
  	function bynSubFlw(){
  		var val=[];
  		$('input[type="radio"]:checked').each(function(){ 
  			val.push($(this).val()); 
  		});
  		if(val.length==0){
  			layer.alert("请选择终止流程");
  			return false;
  		}
  		indexAudit = layer.open({
  	    shift: 1, //0-6的动画形式，-1不开启
  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
  	    title: ['提示','border-bottom:1px solid #e5e5e5'],
  	    shade:0.01, //遮罩透明度
	  		type : 2,
	  		area : [ '30%', '400px'  ], //宽高
	  		content : '${pageContext.request.contextPath}/packageAdvice/auditFile.do?pachageIds=' + ids + '&projectId=${project.id}' + '&currHuanjieId='+$("#currHuanjieId").val() + '&type=1',
			});
  	}
  	
  	function auditSuspend(){
  		var val=[];
  		$('input[type="radio"]:checked').each(function(){ 
  			val.push($(this).val()); 
  		});
  		$.ajax({
			url:"${pageContext.request.contextPath}/termination/ter_package.do",
			data:{"packagesId":ids,"projectId":'${project.id}',"currFlowDefineId":val.join(','),"oldCurrFlowDefineId":$("#currHuanjieId").val()},
			type:"post",
			dataType:"json",
	   	success:function(data){
	   		if(data=="ok"){
	   			layer.alert("终止成功");
	   			layer.close(indexFlw);
	   			if(!fflog){
	   				saveSumitFlow($("#currHuanjieId").val(),"${project.id}");
	   			}else{
	   				document.getElementById('open_bidding_iframe').contentWindow.location.reload(true);
	   			}
	   			$('#openDiv_check input[type="checkbox"]:checked').each(function(){
	   	  			$(this).parent().empty();
	   	  		    $(this).parent().html("<div></div>");
	   	  		});
	   			var checkboxSize=$("#openDiv_check input[type='checkbox']").length;
	   			if(checkboxSize==0){
	   				layer.closeAll();
	   			}
	   			}
	   		}
  		});
  	}
  	
  	var indexAudit;
  	function upddatejzxtp() {
  		var pachageIds="";
  		var val=[];
  		$('input[type="checkbox"]:checked').each(function(){ 
  			val.push($(this).val()); 
  			$(this).parent().remove();
  		});
  		if(val.length==0){
  			layer.msg("请选择一个或多个包");
  			return false;
  		}
  		var currHuanjieId = $("#currHuanjieId").val();
  		pachageIds=val.join(',');
  		$("#packId").val(pachageIds);
  		indexAudit = layer.open({
  	    shift: 1, //0-6的动画形式，-1不开启
  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
  	    title: ['提示','border-bottom:1px solid #e5e5e5'],
  	    shade:0.01, //遮罩透明度
	  		type : 2,
	  		area : [ '500px', '400px'  ], //宽高
	  		content : '${pageContext.request.contextPath}/packageAdvice/auditFile.do?pachageIds=' + pachageIds + '&projectId=${project.id}' + '&currHuanjieId='+currHuanjieId+'&type=2',
			});
  		
  		
  		 /*$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/transformationJZXTP.do",
			data:{"packageIds":pachageIds,
				  "projectId":'${project.id}',
				  "currentFlowDefineId":$("#currHuanjieId").val()
				 },
			type:"post",
			dataType:"json",
	   		success:function(data){
	   		//layer.close(index);
	   		//$("#alertId").val(data.status);
	   			if(data.status=="ok"){
	   				saveSumitFlow($("#currHuanjieId").val(),"${project.id}");
	   				var checkboxSize=$("input[type='checkbox']").length;
	   	  		if(checkboxSize==0){
	   	  			layer.closeAll()
	   	  		};
	   				alert("成功转为竞争性谈判");
	   			} else if (data.status == "failed") {
	   				alert("该采购方式不能转竞谈");
	   			}
	   			} 
  		}); */
	}
	
	
	var indexFlw;
	function openDetailFlw(){
		indexFlw =  layer.open({
	    shift: 1, //0-6的动画形式，-1不开启
	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
	    title: ['终止环节','border-bottom:1px solid #e5e5e5'],
	    shade:0.01, //遮罩透明度
  		type : 1,
  		area : [ '20%', '400px'  ], //宽高
  		content : $('#openDivFlw'),
  	  });
    }
	function cancelFlw(){
	  layer.close(indexFlw);
  }
	
	
	function saveSumitFlow(currFlowDefineId,projectId){
		$.ajax({
		url: globalPath+"/open_bidding/submitHuanjie.html",
		data: {
			"currFlowDefineId": currFlowDefineId,
			"projectId": projectId
		},
		type: "post",
		dataType: "json",
		success: function(data2) {
			if(data2.success) {
				jumpLoad(data2.url, projectId, currFlowDefineId);
				$("#"+currFlowDefineId+"_exe").removeClass("executed");
				$("#"+currFlowDefineId+"_exe").addClass("executed");
				layer.msg("提交成功", {
					offset: '100px'
				});
				
			}
		},
		error: function() {
			layer.msg("提交失败", {
				offset: '100px'
			});
		}
	});
	}
	
	function cancels(){
  		saveSumitFlow($("#currHuanjieId").val(),"${project.id}");
  		layer.closeAll();
	  }
  </script>
</head>

<body onload="initLoad()">
  
  <!-- 面包屑导航开始 -->
  <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
  <div class="container">
  <ul class="breadcrumb margin-left-0">
    <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
    <li><a href="javascript:void(0)">保障作业</a></li>
    <li><a href="javascript:void(0)">采购项目管理</a></li>
    <li><a href="javascript:void(0)">采购项目实施</a></li>
  </ul>
  </div>
  </div>
  <!-- 面包屑导航结束 -->

    <!-- 主要内容开始 -->
    <div class="pr mt20 mb40">
      <!-- 左侧导航开始 -->
      <div class="btn_toggle open" onclick="tree_toggle()"><img src="${pageContext.request.contextPath }/public/backend/images/btn_toggle.png" alt=""></div>
      <div class="m_tree_nav" id="show_tree_div">
        <ul class="btn_list" id="menu">
          <c:forEach items="${fds}" var="fd">
            <!-- 已执行 -->
            <c:if test="${fd.status == 1}">
            <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
            <a class="executed son-menu" id='${fd.id}_exe'>${fd.name }</a>
            </li>
            </c:if>
            <!-- 执行中 -->
            <c:if test="${fd.status == 2}">
            <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
            <a class="son-menu" id='${fd.id}_exe'>${fd.name }</a>
            </li>
            </c:if>
            <!-- 环节结束，不可在操作 -->
            <c:if test="${fd.status == 3}">
            <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
            <a class="executed son-menu " id='${fd.id}_exe'>${fd.name }</a>
            </li>
            </c:if>
            <!-- 未执行 -->
            <c:if test="${fd.status == 0}">
            <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
            <a class="son-menu" id='${fd.id}_exe'>${fd.name }</a>
            </li>
            </c:if>
          </c:forEach>
        </ul>
      </div>
      <!-- 左侧导航结束 -->
    
      <!-- 右侧内容开始 -->
      <div class="container">
      <div class="row">
        
        <input type="hidden" id="typeSave" value="">
        <input type="hidden" id="initurl" value="${url}">
        <!-- <div class="tag-box tag-box-v4 col-md-9" id="open_bidding_main"></div> -->
        <script type="text/javascript" language="javascript">
          function getContentSize() {
            var he = document.documentElement.clientHeight;
            var btn = $("#iframe_btns").outerHeight(true);
            var bread = $("#bread_crumbs").outerHeight(true);
            ch = (he - btn - bread) + "px";
            document.getElementById("open_bidding_iframe").style.height = ch;
          }
          window.onload = getContentSize;
          window.onresize = getContentSize;
        </script>
        
        <div class="tag-box tag-box-v4 col-md-12 col-sm-12 col-xs-12 mb0 pb0">
        <input type="hidden" id="isOperate">
        <div id="headline-v2" class="headline-v2 ml0"><h2>当前环节：<span id="tree_place"></span></h2></div>
        <form id="updateLinkId" action="" method="post" class="w100p fl mb10 border1 padding-10 bg11">
        <input type="hidden" id="projectId" name="projectId" value="${project.id}">
        <input type="hidden" id="type" name="type" value="${type}">
          
          <div class="fr" id="updateOperateId">
            <span class="fl h30 lh30">经办人：</span>
            <div class="w120 fl">
              <input type="hidden" id="principalId">
              <select id="principal" name="principal" onchange="submitCurrOperator();"></select>
            </div>
          </div>
          <div class="fr mr10" id="nextHaunjie">
            <span class="fl h30 lh30">下一环节：</span>
            <div class="fl">
              <input type="hidden" id="huanjieId" name="huanjieId"><span id="huanjie" class="h30 lh30"></span>
            </div>
          </div>
          <div class="fl mr10">
            <span class="fl h30 lh30">变更经办人：</span>
            <div class="w120 fl">
              <input type="hidden" id="currPrincipalId">
              <input type="hidden" id="currHuanjieId">
              <select id="currPrincipal" name="currPrincipal" onchange="updateCurrOperator()"></select>
            </div>
            <div class="fl ml5">
              <input id="submitdiv" type="button" class="btn btn-windows git" onclick="submitcurr();" value="环节结束" />
            </div>
          </div>
        </form>
        <iframe frameborder="0" name="open_bidding_main" id="open_bidding_iframe" scrolling="auto" marginheight="0" width="100%" onLoad="iFrameHeight('open_bidding_iframe')" src="${pageContext.request.contextPath}/${url}"></iframe>
        </div>
        
        <div id="onmouse" onmouseover="bigImg(this)" onmouseout="normalImg(this)">
        <div class="mt5 mb5 tc">
          <%-- <button class="btn btn-windows delete" onclick="abandoned('${project.id}');" type="button">废标</button> --%>
          <button class="btn btn-windows back" onclick="back();" type="button">返回列表</button>
          <button class="btn btn-windows end bgred" onclick="termination('${project.id}');" type="button">终止</button>
        </div>
        </div>
        
	    </div>
      </div>
      <!-- 右侧内容结束 -->
    </div>
    <!-- 主要内容结束 -->
    
    <div id="openDiv" class="dnone layui-layer-wrap">
      <div class="drop_window tc" id="openDiv_check"></div>
      <div class="tc col-md-12 mt50">
        <input class="btn" id="inputb" name="addr" type="button" onclick="bynSub();" value="确定"> 
        <input class="btn" id="inputa" name="addr" type="button" onclick="cancel();" value="取消"> 
      </div>
    </div>
    
    <div id="openDivFlw" class="dnone layui-layer-wrap">
      <div class="drop_window tc" id="openDiv_checkFlw"></div>
      <div class="tc col-md-12 mt350">
        <input class="btn" id="inputbFLw" name="addr" type="button" onclick="bynSubFlw();" value="确定"> 
        <input class="btn" id="inputaFlw" name="addr" type="button" onclick="cancelFlw();" value="取消"> 
      </div>
    </div>
    
    <div id="openDivPackages" class="dnone layui-layer-wrap p20">
    	<div class="tc ">以下包的参与供应商数量不足项目要求的最少供应商数量，请选择操作！</div>
      <div class="tc mt20" id="openDiv_packages"></div>
      <div class="tc mt50">
        <input class="btn" id="jzxtp" name="addr" type="button" onclick="upddatejzxtp();" value="转为竞争性谈判"> 
        <input class="btn" id="inputa" name="addr" type="button" onclick="bynSub();" value="终止实施"> 
        <input class="btn" id="inputa" name="addr" type="button" onclick="cancels();" value="继续实施"> 
      </div>
      <div class="clear"></div>
    </div>
    
    <a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
    
</body>
</html>