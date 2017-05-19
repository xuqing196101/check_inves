<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>My JSP 'view.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page"> 
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  <script type="text/javascript">
    //新增一个评审项
    function addItem(obj,kindId){
    	$("#remainScore").val('');
    	$("#remark").val('');
    	$("#name").val('');
    	$("#id").val('');
    	//得到点击坐标。
        var y;  
        oRect = obj.getBoundingClientRect();  
        y=oRect.top;  
    	$("#typeName").val(kindId);
    	layer.open({
            type: 1,
            title: '添加评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '40%',
            shadeClose: false,
            content: $("#openDiv"),
          });
    }
    
    function editItem(id){
    	$.ajax({   
            type: "get",  
            url: "${pageContext.request.contextPath}/intelligentScore/editScore.do?id="+id,        
            dataType:'json',
            success:function(result){
            	$("#openDiv").removeClass("dnone");
            	$("#remainScore").val(result.remainScore);
            	$("#remark").val(result.remark);
            	$("#name").val(result.name);
            	$("#id").val(result.id);
            	$("#typeName").val(result.type);
            	layer.open({
                    type: 1,
                    title: '添加评审项信息',
                    area: ['500px', '300px'],
                    closeBtn: 1,
                    shade:0.01, //遮罩透明度
                    moveType: 1, //拖拽风格，0是默认，1是传统拖动
                    shift: 1, //0-6的动画形式，-1不开启
                    offset: 100,
                    shadeClose: false,
                    content: $("#openDiv"),
                });
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
            }
       });   
    }
    
    function addModel(obj,kindId,status){
        var projectId = $("#projectId").val();
        var name = encodeURI(obj);
		name = encodeURI(name);
    	window.location.href="${pageContext.request.contextPath}/auditTemplat/gettreebody.html?projectId="+projectId+"&id="+kindId+"&name="+name+"&addStatus="+status;
    }
    
    
    //删除评审项 
    function delItem(id,status){
     	var projectId = $("#projectId").val();
        var packageId = $("#packageId").val();
    	//为2 为顶级结点     1 为子节点
    	window.location.href="${pageContext.request.contextPath}/auditTemplat/deleteScoreModel.html?id="+id+"&deleteStatus="+status+"&projectId="+projectId+"&packageId="+packageId;
    }
    
    //关闭弹窗
    function cancel(){
        layer.closeAll();
    }
    
    //返回模板列表
    function goBack(){
    	
    }
    
    //保存评审项
    function saveItem(){
    	$.ajax({   
            type: "POST",  
            url: "${pageContext.request.contextPath}/intelligentScore/saveScore.html",        
            data : $('#form2').serializeArray(),
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                	var templetKind = $("#templetKind").val();
                	var projectId = $("#projectId").val();
                    window.location.href = '${pageContext.request.contextPath}/auditTemplat/editTemplat.html?templetId='+projectId+"&templetKind="+templetKind;
                    layer.closeAll();
                    layer.msg(result.msg,{offset: ['150px']});
                }
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
            }
       });    
    }
    
    //引入模板内容
    function loadTemplat(projectId, packageId){
    	var fatId = $("#fatId").val();
    	$.ajax({   
            type: "POST",  
            url: "${pageContext.request.contextPath}/firstAudit/loadTemplat.html",   
            data:{"id":fatId,"projectId":projectId,"packageId":packageId},
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                    var packageId = $("#packageId").val();
                    var projectId = $("#projectId").val();
                    window.location.href = '${pageContext.request.contextPath}/firstAudit/editPackageFirstAudit.html?packageId='+packageId+'&projectId='+projectId;
                    layer.closeAll();
                    layer.msg(result.msg,{offset: ['150px']});
                }
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
            }
       }); 
    }
    
    //引入其他项目包的评审项
    function loadOtherPackage(packageId,projectId){
    	layer.open({
            type: 2,
            title: '引入模板',
            area: ['800px', '600px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '20px',
            shadeClose: false,
            content: '${pageContext.request.contextPath}/firstAudit/loadOtherPackage.html?oldPackageId='+packageId+'&oldProjectId='+projectId
          });
    	
    }
    
    function getTotal(){
		var allTr = document.getElementsByTagName("tr");
		var totalScore = 0.0 ;
		for(var i = 1; i < allTr.length; i++) {
			var score = $(allTr[i]).find("td:last").text();
			var reg = /^\d+\.?\d*$/;
			var flag = false;
			if(!reg.exec(score)) {
				score = 0;
			}
			totalScore += parseFloat(score) ;
		};
			$("#totalScore").text(totalScore);
        var score = $("#totalScore").text();
        var projectId = '${projectId}';
        var packageId = '${packageId}';
        if (score == 100) {
           $.ajax({   
                type: "get",  
                url: "${pageContext.request.contextPath}/intelligentScore/checkIsCheck.do?projectId="+projectId+"&packageId="+packageId,  
                dataType:'json',
                success:function(result){
                    if (result == 1) {
                        layer.msg("请选择评审计算价格得分的唯一标识,必须要有一个",{offset: ['150px']});
                    }
                },
                error: function(result){
                }
           });
        }
    }
    
    function getBack(){
    	window.location.href="${pageContext.request.contextPath}/auditTemplat/list.html";
    }
 </script>
<body onload="getTotal()">  
 <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="${pageContext.request.contextPath}">首页</a></li>
                <li><a href="javascript:void(0)">支撑系统</a></li>
                <li><a href="javascript:void(0)">后台管理</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/auditTemplat/list.html">评审模版管理</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <div class="container">
        <div class="headline-v2">
            <h2>经济技术审查项编辑</h2>
        </div>
        <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                  <th class="info" width="15%">评审类别</th>
                  <th class="info" width="20%">评审项目</th>
                  <th class="info" width="15%">评审指标</th>
                  <th class="info" width="10%">所属模型</th>
                  <th class="info" width="30%">评审内容</th>
                  <th class="info" width="10%">分值</th>
               </tr>
            </thead>
                 ${str }
        </table>
         <div class="tr col-md-12 col-sm-12 col-xs-12">
          <div ><b>总分:</b><span class="red f16" id="totalScore"></span></div>
        </div>
        </div>
    </div>
	    <div class="mt40 tc mb50">
	        <button class="btn btn-windows back" onclick="getBack()">返回</button>
	    </div>
    <div id="openDiv" class="dnone layui-layer-wrap">
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" id="projectId" name="projectId" value="${templetId}" />
              <input type="hidden" id="templetKind" name="templetKind" value="${templetKind}" />
              <input type="hidden" name="typeName" id="typeName">
              <input type="hidden" name="id" id="id">
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审项目</div>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input name="name" id="name" maxlength="30" type="text">
	                </div>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</div>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input  name="remainScore" id="remainScore" maxlength="10" type="text">
	                </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12">
                   <div class="col-md-12 pl20 col-xs-12 padding-left-5">评审内容</div>
                   <div class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" maxlength="200" title="" placeholder=""></textarea>
                   </div>
                 </li>
              </ul>
              <div class="mt20 tc col-md-12 col-sm-12 col-xs-12">
                <input class="btn btn-windows save"  onclick="saveItem();" value="保存" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>