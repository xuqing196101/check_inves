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
		
  /** 初始化下拉框*/
  $(function(id) {
    var optionNodes = $("option");
    for(var i = 0; i < optionNodes.length; i++) {
      if("${expert.relName}" == $(optionNodes[i]).val()) {
        optionNodes[i].selected = true;
      }
    }
  });
  
  //文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }

</script>
</head>
    <body>
        <!--面包屑导航开始-->
        <div class="margin-top-10 breadcrumbs ">
            <div class="container">
                <ul class="breadcrumb margin-left-0">
                    <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
                    <li><a href="javascript:void(0)">支撑系统</a></li>
                    <li><a href="javascript:void(0);">专家管理</a></li>
                    <li><a href="javascript:void(0);"  onclick="jumppage('${pageContext.request.contextPath}/expertBlacklist/blacklist.html')">专家黑名单</a></li>
                    <li class="active"><a href="javascript:void(0);">修改信息</a></li>
                </ul>
            </div>
        </div>
<!-- 表格开始-->
        <div class="container container_box">
            <form action="${pageContext.request.contextPath}/expertBlacklist/updateBlacklist.html" method="post" id="form1">
            <input type="hidden" name="id" value="${expert.id}">
            <input type="hidden" name="operationType" value="1">
            <h2 class="count_flow"><i>1</i>修改信息</h2>
            <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12  col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>专家姓名：</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 p0">
			            <input class="input_group"  name="relName"  type="text" id="expert_name" readonly="readonly" required="required" value="${relName }">
			            <input name="expertId" value="${expert.expertId }" type="hidden"/>
			            <span class="add-on cur_point cur_point">i</span>
			            <div class="cue"> ${err_relName } </div>
                    </div>
		        </li>
		        <li class="col-md-3 col-sm-6 col-xs-12 ">
                     <span class="col-md-12  col-sm-12 col-xs-12 padding-left-5" ><div class="star_red">*</div>处罚方式：</span>
                     <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                        <select name="punishType" required="required">
                            <option value="">请选择</option>
                            <option value="1" <c:if test="${'1' eq expert.punishType}">selected</c:if>>警告</option>
                            <option value="2" <c:if test="${'2' eq expert.punishType}">selected</c:if>>严重警告</option>
                            <option value="3" <c:if test="${'3' eq expert.punishType}">selected</c:if>>取消资格</option>
                        </select>
                        <div class="cue"> ${err_punishType } </div>
                    </div>
                 </li>   
		          <li class="col-md-3 col-sm-6 col-xs-12 ">
                     <span class="col-md-12  col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>处罚时限：</span>
                     <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
				        <select  name="punishDate" required="required">
						  		<option value="">请选择</option>
						  		<option value="3个月" <c:if test="${'3个月' eq expert.punishDate}">selected</c:if>>3个月</option>
						  		<option value="6个月" <c:if test="${'6个月' eq expert.punishDate}">selected</c:if>>6个月</option>
						  		<option value="一年" <c:if test="${'一年' eq expert.punishDate}">selected</c:if>>一年</option>
						  		<option value="两年" <c:if test="${'两年' eq expert.punishDate}">selected</c:if>>两年</option>
						  		<option value="三年" <c:if test="${'三年' eq expert.punishDate}">selected</c:if>>三年</option>
					      </select>
					      <div class="cue"> ${err_punishDate } </div>
					</div>
			      </li>
			      <li class="col-md-3 col-sm-6 col-xs-12 ">
                     <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>处罚日期：</span>
                     <div class="input-append input_group col-sm-12 col-xs-12 p0">
                        <input class="input_group"  readonly="readonly" name="dateOfPunishment"  required="required" type="text" value="<fmt:formatDate value="${expert.dateOfPunishment}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
                        <div class="cue"> ${err_dateOfPunishment } </div>
                    </div>
                </li>
				<li class="col-md-3 col-sm-6 col-xs-12 ">
                     <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>入库时间：</span>
                     <div class="input-append input_group col-sm-12 col-xs-12 p0">
                        <input class="input_group"  readonly="readonly" name="storageTime"  required="required" type="text" value="<fmt:formatDate value="${expert.storageTime}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
                        <div class="cue"> ${err_storageTime } </div>
                    </div>
                </li>  
				<li class="col-md-12 col-sm-12 col-xs-12">
                    <span class="col-md-12  col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>处罚理由：</span>
                    <div class="col-md-12  col-sm-12 col-xs-12 p0">
		              <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" title="这是必填字段，字数不超过200个字" required="required" maxlength="200" name="reason" >${expert.reason }</textarea>
                    <div class="cue"><sf:errors path="reason"/></div>
                    </div>
                    <span class=" red">${err_reason}</span>
                    
		        </li> 
			</ul>
			<h2 class="count_flow"><i>2</i>附件上传</h2>
            <ul class="ul_list">
                <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12" ><div class="star_red">*</div>批准文件:</span>
                    <%-- <input class="span5" type="file" name="attachmentCertFile"/>
                    <a onclick="downloadFile('${expert.attachmentCert}')" style="cursor:pointer" id="attachmentCert">${expert.attachmentCert}</a> --%>
                    <u:upload id="id_up"  businessId="${expert.id}" sysKey="${expertKey}" typeId="${expertDictionaryData.id}" auto="true" /> 
                    <u:show showId="id_show"  businessId="${expert.id}" sysKey="${expertKey}" typeId="${expertDictionaryData.id}" />
               		<span class=" red">  ${err_attachmentCert }</span>
                </li>
            </ul>
	         <div class="margin-bottom-0  categories">
	            <div class="col-md-12 add_regist tc">
	                <button class="btn btn-windows edit" type="submit">更新</button>
	                <a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	            </div>
	        </div>
        </form>
     </div>
	  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/expertBlacklist/download.html" method="post">
	   <input type="hidden" name="fileName" />
	  </form>
    </body>
</html>
