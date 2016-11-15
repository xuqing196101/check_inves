<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>专家黑名单添加</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
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
		        <li class="col-md-3 margin-0 padding-0 ">
	                <span class="col-md-12 padding-left-5"><i class="padding-left-5 red">＊</i>专家姓名：</span>
	                <div class="input-append">
		                <input type="hidden" name="expertId" readonly="readonly" value="${expertId }">
		                <input class="span5"  name="relName"  type="text" id="expert_name" readonly="readonly" value="${relName }">
		                <span class="add-on">i</span>
		            </div>
		            <span class="padding-left-5 padding-left-5 red">${err_relName}</span>
                 </li>
	             <li class="col-md-3 margin-0 padding-0  ">
		             <span class="col-md-12 padding-left-5"><i class="padding-left-5 red">＊</i>入库时间：</span>
		             <div class="input-append">
		                 <input class="Wdate w230"  readonly="readonly" name="storageTime"  type="text" onclick='WdatePicker()' id="txtBirthday" value="<fmt:formatDate value="${storageTime}" pattern='yyyy-MM-dd'/>" />
                     </div>
                     <span class="padding-left-5 red">${err_storageTime}</span>
		         </li>
		         <li class="col-md-3 margin-0 padding-0 ">
	                 <span class="col-md-12 padding-left-5"><i class="padding-left-5 red">＊</i>处罚时限：</span>
	                 <div class="select_common">
			             <select name="punishDate">
					  		 <option value="">请选择</option>
					  		 <option value="3个月" <c:if test="${'3个月' eq punishDate}">selected</c:if>>3个月</option>
                             <option value="6个月" <c:if test="${'6个月' eq punishDate}">selected</c:if>>6个月</option>
                             <option value="一年" <c:if test="${'一年' eq punishDate}">selected</c:if>>一年</option>
                             <option value="两年" <c:if test="${'两年' eq punishDate}">selected</c:if>>两年</option>
                             <option value="三年" <c:if test="${'三年' eq punishDate}">selected</c:if>>三年</option>
				        </select>
				     </div>
				     <span class="padding-left-5 red">${err_punishDate}</span>
			     </li>
			      <li class="col-md-3 margin-0 padding-0 ">
                     <span class="col-md-12 padding-left-5"><i class="padding-left-5 red">＊</i>处罚日期：</span>
                     <div class="input-append">
                         <input class="Wdate w230" readonly="readonly" name="dateOfPunishment"  type="text" onclick='WdatePicker()' value="<fmt:formatDate value="${dateOfPunishment}" pattern='yyyy-MM-dd'/>">
                     </div>
                     <font id="nameFont2"></font>
                     <span class="padding-left-5 red">${err_dateOfPunishment}</span>
                 </li>
	             <li class="col-md-3 margin-0 padding-0 ">
		             <span class="padding-left-5"><i class="padding-left-5 red">＊</i>处罚方式：</span>
		             <div class="select_common">
		  	             <select  name="punishType">
					  	     <option value="">请选择</option>
					  		 <option value="1" <c:if test="${'1' eq punishType}">selected</c:if>>警告</option>
                             <option value="2" <c:if test="${'2' eq punishType}">selected</c:if>>严重警告</option>
                             <option value="3" <c:if test="${'3' eq punishType}">selected</c:if>>取消资格</option>
			             </select>
			         </div>
			         <span class="padding-left-5 red">${err_punishType}</span>
		        </li> 
		        <li class="col-md-11 margin-0 padding-0 ">
		            <span class="col-md-12 padding-left-5"><i class="padding-left-5 red">＊</i>处罚理由：</span>
                    <div class="">
                        <textarea class="col-md-12" style="height:130px" title="不超过100个字" name="reason" >${reason }</textarea>
                    </div>
                    <span class="padding-left-5 red">${err_reason}</span>
                </li> 
	       </ul>
	       
	       <h2 class="count_flow"><i>2</i>附件上传</h2>
           <ul class="ul_list">
	           <li >
	              <span class="" ><i class="padding-left-5 red">＊</i>批准文件:</span>
	              <input class="span3" type="file" name="attachmentCertFile"/>
	              <span class="padding-left-5 red">${err_attachmentCert}</span>
	           </li>
            </ul>
            <div class="col-md-12 p0">
                <div class="col-md-12 add_regist tc">
                    <button class="btn btn-windows save" type="submit">保存</button>
                    <a class="btn btn-windows reset"  onclick="location.href='${pageContext.request.contextPath}/expertBlacklist/blacklist.html'">返回</a>
                </div>
             </div>
          </form>
      </div>
    </body>
</html>
