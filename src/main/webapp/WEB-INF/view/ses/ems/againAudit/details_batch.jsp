<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
  <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
</head>
<body>

	<!-- 面包屑导航开始 -->
	<div class="margin-top-10 breadcrumbs">
	<div class="container">
	<ul class="breadcrumb margin-left-0">
		<li>
		  <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a>
		</li>
		<li>
		  <a href="javascript:void(0)">支撑系统</a>
		</li>
		<li>
		  <a href="javascript:void(0)">专家管理</a>
		</li>
		<li>
			<c:if test="${sign == 1}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
			</c:if>
			<c:if test="${sign == 2}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=2')">专家复审</a>
			</c:if>
			<c:if test="${sign == 3}">
			<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复审批次列表</a>
			</c:if>
		</li>
	</ul>
	<div class="clear"></div>
	</div>
	</div>
	<!-- 面包屑导航结束 -->

  <!-- 内容开始 -->
  <div class="container">
    <div class="headline-v2"><h2 id="head_tit"></h2></div>
    
    <!-- 表格开始-->
   
    <div class="col-md-12 pl20 mt10 mb10" id="btn_group">
      
      <div class="fr pic_upload">
        <div class="fl h30 lh30">上传批准复审表：</div>
        <u:upload id="pic_checkword" businessId="${batchId}" sysKey="3" typeId="da6ab7e73b8d464d8d8d46013dd70e43" buttonName="上传彩色扫描件" auto="true" multiple="true"/>
        <u:show showId="pic_checkword" businessId="${batchId}" sysKey="3" typeId="da6ab7e73b8d464d8d8d46013dd70e43" />
      </div>
      <div class="clear"></div>
    </div>
    
    <div class="content table_box">
      <div id="table_content"></div>
      <%-- <div id="pagediv" align="right"></div> --%>
    </div>
    
    <div class="mt20 pl20 text-center">
      <button type="button" class="btn btn-windows back" onclick="javascript:history.back()">返回</button>
    </div>
      
  </div>
  <form id="form_id" action="${pageContext.request.contextPath}/expertAudit/basicInfo.html" method="post">
  <input name="expertId" type="hidden" />
  <input name="tableType" type="hidden" value=""/>
  </form>
  <!-- 内容结束 -->
  
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/batchDetails.js"></script>
  <script src="${pageContext.request.contextPath}/js/ses/ems/againAudit/processing.js"></script>
  <script>
    var root_url = '${pageContext.request.contextPath}';  // 根目录地址
    var list_url = '${pageContext.request.contextPath}/expertAgainAudit/findBatchDetails.do';  // 列表地址
    var audit_url = '${pageContext.request.contextPath}/expertAgainAudit/checkGroupStatus.do';  // 校验地址
    var jump_auditBatch_url = '${pageContext.request.contextPath}/expertAgainAudit/groupBatch.html?batchId='+getUrlParam('batchId');
    var select_ids = [];  // 选择的专家id集合
    
    $(function () {
      $('#table_content').listConstructor({
        url: list_url,
        data: {
          batchId: getUrlParam('batchId')
        }
      });
    });
    
    // 跳转批次审核
    function jump_auditBatch() {
      window.location.href = '${pageContext.request.contextPath}/expertAgainAudit/auditBatch.html?batchId='+getUrlParam('batchId');
    }
    //下载
    function downloadTable(id) {
        var state = $("#" + id + "").parent("tr").find("td").eq(10).text(); //.trim();
        state = trim(state);
        if(state =="预复审结束" || state =="公示中" || state == "复审预合格" ||state == "复审合格" || state == "复审不合格"|| state == "复审退回修改" || state == "复查合格" || state == "复查未合格") {
          $("input[name='tableType']").val('2');
          $("input[name='expertId']").val(id);
          $("#form_id").attr("action", "${pageContext.request.contextPath}/expertAudit/download.html");
          $("#form_id").submit();
        } else {
          layer.msg("请选择审核过的专家 !", {
            offset: '100px',
          });
        }
    }
    function trim(str) { //删除左右两端的空格
      return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    
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
                 //checkAll.checked = true;
                 count++;
               }
           }
         }
    }
    
    //复审结束（审核专家操作）
    function reviewEnd(expertId){
    	$.ajax({
        url: "${pageContext.request.contextPath}/expertAgainAudit/reviewEnd.do",
        data: {"expertId" : expertId},
        success: function (data) {
          if(data.status == 200){
        	  layer.msg("操作成功",{offset:'100px'});
        	  window.setTimeout(function(){
        		  window.location.reload();
        	  },1000);
          }
        },error: function(){
        	layer.msg("操作失败",{offset:'100px'});
        }
      });
    }
    
    //复审确认（资源服务中心）
    function reviewConfirm(){
    	var ids = [];
    	var flag = true;
    	$('input[type="checkbox"]:checked').each(function() {
        var id = $(this).val();
       	var state = $("#" + id + "").parent("tr").find("td").eq(10).text(); //.trim();
        state = trim(state);
        if(state == "复审结束"){
        	ids.push(id);
        }else{
        	flag = false;
        }
      });
    	if(flag){
    		$.ajax({
 	        url: "${pageContext.request.contextPath}/expertAgainAudit/reviewConfirm.do",
 	        data: {"expertIds" : ids},
 	        type: "post",
 	        traditional:true,
 	        success: function (data) {
 	          if(data.status == 500){
 	            layer.msg("操作成功",{offset:'100px'});
 	            window.setTimeout(function(){
 	              window.location.reload();
 	            },1000);
 	          }
 	          if(data.status == 503){
 	            layer.msg("请选择复审结束的专家",{offset:'100px'});
 	          }
 	        },error: function(){
 	          layer.msg("操作失败",{offset:'100px'});
 	        }
 	      });
    	}else{
    		layer.msg("请选择复审结束的专家",{offset:'100px'});
    	}
    }
  </script>
    
</body>
</html>