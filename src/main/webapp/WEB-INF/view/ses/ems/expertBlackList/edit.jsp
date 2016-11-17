<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>专家黑名单修改</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
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
                    <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">专家黑名单</a></li><li class="active"><a href="#">修改信息</a></li>
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
                <li class="col-md-3 margin-0 padding-0 ">
                    <span class="col-md-12 padding-left-5"><i class="red">＊</i>专家姓名：</span>
                    <div class="input-append">
			            <input class="span5"  name="relName"  type="text" id="expert_name" readonly="readonly" value="${relName }">
			            <input name="expertId" value="${expert.expertId }" class="dnone"/>
			            <span class="add-on">i</span>
                    </div>
                    <span class="padding-left-5 padding-left-5 red">${err_relName}</span>
		        </li>
		        <li class="col-md-3 margin-0 padding-0 ">
                    <span class="col-md-12 padding-left-5"><i class="red">＊</i>入库时间：</span>
                    <div class="input-append">
                        <input class="Wdate w230"  readonly="readonly" name="storageTime"  type="text" value="<fmt:formatDate value="${expert.storageTime}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
                        <font id="nameFont2"></font>
                    </div>
                    <span class="padding-left-5 red">${err_storageTime}</span>
                  </li> 
		          <li class="col-md-3 margin-0 padding-0 ">
			        <span class="col-md-12 padding-left-5"><i class="red">＊</i>处罚时限：</span>
			        <div class="select_common">
				        <select class="span5" name="punishDate">
						  		<option value="">请选择</option>
						  		<option value="3个月" <c:if test="${'3个月' eq expert.punishDate}">selected</c:if>>3个月</option>
						  		<option value="6个月" <c:if test="${'6个月' eq expert.punishDate}">selected</c:if>>6个月</option>
						  		<option value="一年" <c:if test="${'一年' eq expert.punishDate}">selected</c:if>>一年</option>
						  		<option value="两年" <c:if test="${'两年' eq expert.punishDate}">selected</c:if>>两年</option>
						  		<option value="三年" <c:if test="${'三年' eq expert.punishDate}">selected</c:if>>三年</option>
					      </select>
					</div>
					<span class="padding-left-5 red">${err_punishDate}</span>
			      </li>
			      <li class="col-md-3 margin-0 padding-0 ">
                    <span class="col-md-12 padding-left-5"><i class="red">＊</i>处罚日期：</span>
                    <div class="input-append">
                        <input class="Wdate w230"  readonly="readonly" name="dateOfPunishment"  type="text" value="<fmt:formatDate value="${expert.dateOfPunishment}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
                        <font id="nameFont2"></font>
                    </div>
                    <span class="padding-left-5 red">${err_dateOfPunishment}</span>
                </li>
		        <li class="col-md-3 margin-0 padding-0 ">
			        <span class="col-md-12 padding-left-5"><i class="red">＊</i>处罚方式：</span>
			        <div class="select_common">
				  	    <select class="span5" name="punishType">
						  	<option value="">请选择</option>
						  	<option value="1" <c:if test="${'1' eq expert.punishType}">selected</c:if>>警告</option>
						    <option value="2" <c:if test="${'2' eq expert.punishType}">selected</c:if>>严重警告</option>
						    <option value="3" <c:if test="${'3' eq expert.punishType}">selected</c:if>>取消资格</option>
					    </select>
				    </div>
				    <span class="padding-left-5 red">${err_punishType}</span>
				</li> 
				<li class="col-md-11 margin-0 padding-0 ">
					<span class="col-md-12 padding-left-5"><i class="red">＊</i>处罚理由：</span>
		            <div class="">
		              <textarea class="col-md-12" style="height:130px" title="不超过100个字" name="reason" >${expert.reason }</textarea>
                    </div>
                    <span class="padding-left-5 red">${err_reason}</span>
		        </li> 
			</ul>
			<h2 class="count_flow"><i>2</i>附件上传</h2>
            <ul class="ul_list">
                <li class="col-md-3 margin-0 padding-0 ">
                    <span class="" ><i class="red">＊</i>批准文件:</span>
                    <%-- <input class="span5" type="file" name="attachmentCertFile"/>
                    <a onclick="downloadFile('${expert.attachmentCert}')" style="cursor:pointer" id="attachmentCert">${expert.attachmentCert}</a> --%>
                    <up:upload id="id_up"  businessId="${expert.id}" sysKey="${expertKey}" typeId="${expertDictionaryData.id}" auto="true" /> 
                    <up:show showId="id_show"  businessId="${expert.id}" sysKey="${expertKey}" typeId="${expertDictionaryData.id}" />
                </li>
            </ul>
	         <div class="margin-bottom-0  categories">
	            <div class="col-md-12 add_regist tc">
	                <button class="btn btn-windows save" type="submit">保存</button>
	                <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	            </div>
	        </div>
        </form>
     </div>
	  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/expertBlacklist/download.html" method="post">
	   <input type="hidden" name="fileName" />
	  </form>
    </body>
</html>
