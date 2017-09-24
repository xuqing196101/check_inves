<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp"%>
    <title>任务管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript">
        function extractVerify(projectId,packageIds){
        	window.open("${pageContext.request.contextPath}/SupplierExtracts_new/Extraction.html?projectId="+projectId+"&packageId="+packageIds+"&projectType=relPro");
        }
        function ddd(){
        	alert(1);
        }
    </script>
<body>
<!-- 项目戳开始 -->
<div class="container">
<div class="container_box col-md-12 col-sm-12 col-xs-12">
    <form id="form">
         <h2 class="count_flow"><i>1</i>项目信息</h2>
         <ul class="ul_list">
             <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red0">*</span> 项目名称:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="name" name="name"  value="${project.name}" type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red1">*</span> 项目编号:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectNumber" name="projectNumber" value="${project.projectNumber}" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNumberError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red2">*</span>采购方式:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input id="purchaseType" name="purchaseType" value="${project.purchaseType}" type="text" >
                     <span class="add-on">i</span>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12 ">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red3">*</span> 开标日期:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="tenderTimeId" readonly="readonly"  name="bidDate" value="<fmt:formatDate value='${project.bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="tenderTimeError"></div>
                 </div>
             </li>
         </ul>
	 </form>
	</div><!-- 项目信息结束 -->
	<!-- 条件开始 -->
	<div class="container container_box">
    <form id="form1" method="post">
          <ul class="ul_list" style="background-color: #fbfbfb">
              <li class="col-md-12 col-sm-12 col-xs-12 tc">
                  <div>
                      <button class="btn " onclick="extractVerify('${project.id}','${packageId}');" type="button">抽取</button>
                      <button class="btn  " onclick="finish();" type="button">完成抽取</button>
                      <button class="btn " onclick="temporary();" type="button">暂存</button>
                  </div>
              </li>
          </ul>
          <!--=== Content Part ===-->
          <h2 class="count_flow"><i>2</i>抽取结果</h2>
	         <div class="ul_list" id="projectResult">
	           	<!-- Begin Content -->
                 <table id="table" class="table table-bordered table-condensed">
                     <thead>
                     <tr>
                         <th class="info w50">序号</th>
                         <th class="info" width="15%">供应商名称</th>
                         <th class="info" width="15%">类型</th>
                         <th class="info" width="15%">联系人名称</th>
                         <th class="info" width="18%">联系人电话</th>
                         <th class="info" width="18%">联系人手机</th>
                     </tr>
                     </thead>
                     <tbody id="tbody">
                      
                     </tbody>
                </table>
			</div>
    	</form>
	</div>
</div>
</body>
</html>