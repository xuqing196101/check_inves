<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
            area: ['70%', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: y,
            shadeClose: false,
            content: $("#openDiv"),
          });
    }
    
    
     function editItem(id){
      $.ajax({   
            type: "get",  
            url: "${pageContext.request.contextPath}/adIntelligentScore/editScore.do?id="+id,        
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
                    area: ['70%', '300px'],
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
        var packageId = $("#packageId").val();
        var name = encodeURI(obj);
    name = encodeURI(name);
      window.location.href="${pageContext.request.contextPath}/adIntelligentScore/gettreebody.html?projectId="+projectId+"&packageId="+packageId+"&id="+kindId+"&name="+name+"&addStatus="+status;
    }
    
    
    
    //删除评审项 
    function delItem(id,status){
      var projectId = $("#projectId").val();
        var packageId = $("#packageId").val();
      //为2 为顶级结点     1 为子节点
      window.location.href="${pageContext.request.contextPath}/adIntelligentScore/deleteScoreModel.html?id="+id+"&deleteStatus="+status+"&projectId="+projectId+"&packageId="+packageId;
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
            url: "${pageContext.request.contextPath}/adIntelligentScore/saveScore.html",        
            data : $('#form2').serializeArray(),
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                    var packageId = $("#packageId").val();
                    var projectId = $("#projectId").val();
                    window.location.href = '${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
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
      if (fatId != null && fatId != "") {
        $('#loadTemp').attr("disabled",true); 
	      $.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/adIntelligentScore/loadTemplat.html",   
	            data:{"id":fatId,"projectId":projectId,"packageId":packageId},
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
                      layer.msg(result.msg,{offset: ['150px']});
                  }else{
                      $('#loadTemp').removeAttr("disabled");
                      var packageId = $("#packageId").val();
                      var projectId = $("#projectId").val();
	                    window.location.href = '${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
	                    layer.closeAll();
	                    layer.msg(result.msg,{offset: ['150px']});
	                }
	            },
	            error: function(result){
	                layer.msg("添加失败",{offset: ['150px']});
	            }
	       });
	   } else {
       layer.msg("请选择模板",{offset: ['150px']});
     }
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
            content: '${pageContext.request.contextPath}/adFirstAudit/loadOtherPackage.html?oldPackageId='+packageId+'&oldProjectId='+projectId
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
    }
 </script>
<body onload="getTotal()">  
    <h2 class="list_title">${packages.name}  经济技术审查项编辑</h2>
    <c:if test="${project.confirmFile != 1 }">
  <div class="search_detail ml0">
          <ul class="demand_list">
            <li>
              <label class="fl">选择模板：</label>
                <select id="fatId">
                  <option value="">请选择</option>
                  <c:forEach items="${firstAuditTemplats}" var="fat">
                      <option value="${fat.id}">${fat.name}</option>
                  </c:forEach>
                </select>
             </li>
             <button type="button" onclick="loadTemplat('${projectId}','${packageId}')" id="loadTemp" class="btn">确定</button>
            <%--  <div class="pull-right">
                <button type="button" onclick="loadOtherPackage('${packageId}','${projectId}')" class="btn">引入模板</button>
             </div> --%>
          </ul>
          <div class="clear"></div>
       </div>
      </c:if>
    <div class="content">
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
                 ${str}
        </table>
        <div class="tr col-md-12 col-sm-12 col-xs-12">
          <div ><b>总分:</b><span class="red f16" id="totalScore"></span></div>
        </div>
    </div>
      <div class="mt40 tc mb50">
      <c:if test="${project.confirmFile != 1 }">
          <button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}'">返回</button>
          </c:if>
      </div>
    <div id="openDiv" class="dnone layui-layer-wrap">
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="projectId" id="projectId" value="${projectId}">
              <input type="hidden" name="packageId" id="packageId" value="${packageId}">
              <input type="hidden" name="typeName" id="typeName">
              <input type="hidden" name="id" id="id">
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审项目</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="name" id="name" maxlength="30" type="text">
                  </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0">
                     <input  name="remainScore" id="remainScore" maxlength="10" type="text">
                  </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5">评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                 </li>
              </ul>
              <div class="mt40 tc mb50">
                <input class="btn btn-windows save"  onclick="saveItem();" value="保存" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>