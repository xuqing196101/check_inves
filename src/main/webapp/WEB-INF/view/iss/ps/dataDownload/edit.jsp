<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
			$(function(){
				var ipAddressType = document.getElementsByName("ipAddressType");
				var ipAddress = "${data.ipAddressType}";
				if(ipAddress){
					for(var i=0;i<ipAddressType.length;i++){
						if($(ipAddressType[i]).val()==ipAddress){
							$(ipAddressType[i]).prop("checked","checked");
						}
					}
				}
			})
			
			//返回
			function back(){
				window.location.href = "${pageContext.request.contextPath}/dataDownload/getList.html";
			}
			
			//发布
			function publishData(){
				$("#form").attr("action","${pageContext.request.contextPath }/dataDownload/editPublish.html");
				$("#form").submit();
			}
		</script>
  </head>
  
  <body>
    <!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);">首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">信息服务</a>
					</li>
					<li>
						<a href="javascript:void(0);">资料下载管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
		<div class="container container_box">
			<form action="${pageContext.request.contextPath }/dataDownload/editData.html" method="post" id="form">
				<input type="hidden" name="id" value="${data.id }"/>
				<h2 class="list_title">修改资料</h2>
				
					<ul class="ul_list mb20">
						<li class="col-md-12 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>资料名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="name" name="name" value="${data.name }" type="text">
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            
           	<li class="col-md-3 col-sm-6 col-xs-12 mt10 mb20">
              <span class="fl"><div class="star_red">*</div>附件上传：</span>
              <div>
                <u:upload id="data_file_up" buttonName="附件上传"  businessId="${data.id }" sysKey="${sysKey}" typeId="${dataTypeId }" multiple="true" auto="true" />
                <u:show showId="data_file_show"  businessId="${data.id }" sysKey="${sysKey}" typeId="${dataTypeId }" />
                <div class="cue">${ERR_dataFile}</div>
              </div>
            </li>
             <li class="col-md-3 col-sm-6 col-xs-12 mt10 mb20">
              <span class="fl"><div class="star_red">*</div>发布范围：</span>
              <div  id="radio">
              <input type="radio" name="ipAddressType" id ="ipAddressType" value="0" >内网
	          <input type="radio" name="ipAddressType" id ="ipAddressType" value="1">内外网
                <div class="cue">${ERR_ipAddressType}</div>
              </div>
            </li>
            
					</ul>
									
							
				
				<!-- 按钮 -->
				<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
					<button class="btn btn-windows apply" type="button" onclick="publishData()">发布</button>
					<button class="btn btn-windows save" type="submit">暂存</button>
					<input class="btn btn-windows back" value="返回" type="button" onclick="back()">
				</div>
			</form>
		</div>
  </body>
</html>
