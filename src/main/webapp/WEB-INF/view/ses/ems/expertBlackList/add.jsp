<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<%@ include file="/WEB-INF/view/common/validate.jsp"%>
		<script type="text/javascript">
		$().ready(function() {
			$("#extensionId").val("bmp,pmg,jpg,gif,png");
		   $("#form1").validForm();
		});
		
		  $(function() {
		  	$("#expert_name").click(function() {
		    	layer.open({
		      	type : 2,
		        title : '选择专家',
		        // skin : 'layui-layer-rim', //加上边框
		        area : [ '800px', '500px' ], //宽高
		        offset : '80px',
		        scrollbar : false,
		        content : '${pageContext.request.contextPath}/expertBlacklist/expert_list.html', //url
		        closeBtn : 1, //不显示关闭按钮
		      });
		    });
		  });
		</script>
	</head>
  <body>
  	<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
	  	<div class="container">
	    	<ul class="breadcrumb margin-left-0">
	      	<li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">不良专家名单管理</a></li><li class="active"><a href="#">增加信息</a></li>
		  	</ul>
	    </div>
		</div>
		<!-- 表格开始-->
    <div class="container container_box">
    	<form action="${pageContext.request.contextPath}/expertBlacklist/saveBlacklist.do" method="post" id="form1"  class="registerform">
      	<input type="hidden" name="operationType" value="0">
        	<h2 class="count_flow"><i>1</i>增加信息</h2>
          <ul class="ul_list">
		      	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
	          	<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>专家姓名：</span>
	            <div class="input-append input_group col-sm-12 col-xs-12 p0">
	            	<input type="hidden" name="id" readonly="readonly" value="${uuid }">
		           	<input type="hidden" name="expertId" readonly="readonly" value="${expertId }">
		            <input class="input_group"  name="relName"  type="text" id="expert_name" required="required" readonly="readonly" value="${relName }">
		            <span class="add-on cur_point cur_point">i</span>
		            <div class="cue"> ${err_relName } </div>
		          </div>
            </li>
	          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
            	<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>处罚方式：</span>
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select  name="punishType" required="required">
	                <option value="">请选择</option>
	                <option value="1" <c:if test="${'1' eq punishType}">selected</c:if>>警告</option>
	                <option value="2" <c:if test="${'2' eq punishType}">selected</c:if>>严重警告</option>
	                <option value="3" <c:if test="${'3' eq punishType}">selected</c:if>>取消资格</option>
                </select>
             		<div class="cue"> ${err_punishType } </div>
            </div>
          </li>
		      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>处罚时限：</span>
	          <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
	        		<select name="punishDate" required="required">
			  		 		<option value="">请选择</option>
			  		 		<option value="3个月" <c:if test="${'3个月' eq punishDate}">selected</c:if>>3个月</option>
                <option value="6个月" <c:if test="${'6个月' eq punishDate}">selected</c:if>>6个月</option>
                <option value="一年" <c:if test="${'一年' eq punishDate}">selected</c:if>>一年</option>
                <option value="两年" <c:if test="${'两年' eq punishDate}">selected</c:if>>两年</option>
                <option value="三年" <c:if test="${'三年' eq punishDate}">selected</c:if>>三年</option>
				      </select>
				     	<div class="cue"> ${err_punishDate } </div>
				     </div>
			     </li>
			     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
             <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>处罚日期：</span>
             <div class="input-append input_group col-sm-12 col-xs-12 p0">
               <input class="input_group" readonly="readonly" name="dateOfPunishment"  required="required" type="text" onclick='WdatePicker()' value="<fmt:formatDate value="${dateOfPunishment}" pattern='yyyy-MM-dd'/>">
               <div class="cue"> ${err_dateOfPunishment } </div>
             </div>
           </li>
		       <li class="col-md-3 col-sm-6 col-xs-12 pl15">
             <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>入库时间：</span>
             <div class="input-append input_group col-sm-12 col-xs-12 p0">
               <input class="input_group"  readonly="readonly" name="storageTime"  required="required" type="text" onclick='WdatePicker()' id="txtBirthday" value="<fmt:formatDate value="${storageTime}" pattern='yyyy-MM-dd'/>" />
               <div class="cue"> ${err_storageTime } </div>
             </div>
           </li> 
		       <li class="col-md-12 col-sm-12 col-xs-12 ">
		         <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>处罚理由：</span>
             <div class="col-md-12 col-sm-12 col-xs-12 p0">
               <textarea class="col-md-12 col-sm-12 col-xs-12 p0" style="height:130px" title="不超过200个字" name="reason" required="required" maxlength="200">${reason }</textarea>
             		<div class="cue"><sf:errors path="reason"/></div>
             </div>
             <span class=" red">${err_reason}</span>
          </li> 
	      </ul>
	       
	      <h2 class="count_flow"><i>2</i>附件上传</h2>
        <ul class="ul_list">
	        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12" ><div class="star_red">*</div>批准文件:</span>
	          <%-- <input class="span3" type="file" name="attachmentCertFile"/>
	          <span class=" red">${err_attachmentCert}</span> --%>
	          <u:upload id="id_up"  businessId="${uuid}" sysKey="${expertKey}" typeId="${typeId}" auto="true"/> 
          	<u:show showId="id_show" groups="a,b,c"  businessId="${uuid}" sysKey="${expertKey}" typeId="${typeId}" />
	      	<span class=" red">  ${err_attachmentCert }</span>
	      	</li>
        </ul>
        <div class="col-md-12 p0">
          <div class="col-md-12 add_regist tc">
            <button class="btn btn-windows save mt10" type="submit">保存</button>
          	<a class="btn btn-windows back mt10"  onclick="location.href='${pageContext.request.contextPath}/expertBlacklist/blacklist.html'">返回</a>
        	</div>
      	</div>
    	</form>
  	</div>
	</body>
</html>
