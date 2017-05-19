<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->

</head>

<body>

	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="${pageContext.request.contextPath}"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">论坛管理</a>
				</li>
				<li class="active"><a href="${pageContext.request.contextPath}/templet/getAll.html">模板管理</a>
				</li>
				<li class="active"><a href="javascript:void(0)">模板详情</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<div class="container content pt0">
 	<div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
				<ul class="nav nav-tabs bgwhite">
	            	<li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">模板详情</a></li>
           	 	</ul>
           	 	<div class="tab-content padding-top-20 over_hideen">
            	<div class="tab-pane fade active in" id="tab-1">
                	<h2 class="count_flow jbxx">基本信息</h2>
                	<table class="table table-bordered">
                 		<tbody>
                     		<tr>
	                  			<td class="bggrey " width="10%">模板名称：</td>
	                  			<td width="40%">${templet.name}</td>
	                  			<td class="bggrey " width="10%">模板类型：</td>
	                  			<td width="40%">
	                  				<c:if test="${templet.temType ==0}">
	                  					采购公告-公开招标
	                  				</c:if>
	                  				<c:if test="${templet.temType ==1}">
	                  					采购公告-邀请招标
	                  				</c:if>
	                  				<c:if test="${templet.temType ==2}">
	                  					采购公告-询价采购
	                  				</c:if>
	                  				<c:if test="${templet.temType ==3}">
	                  					采购公告-竞争性谈判
	                  				</c:if>
	                  				<c:if test="${templet.temType ==4}">
	                  					单一来源公示
	                  				</c:if>
	                  				<c:if test="${templet.temType ==5}">
	                  					中标公示-公开招标
	                  				</c:if>
	                  				<c:if test="${templet.temType ==6}">
	                  					中标公示-邀请招标
	                  				</c:if>
	                  				<c:if test="${templet.temType ==7}">
	                  					中标公示-询价采购
	                  				</c:if>
	                  				<c:if test="${templet.temType ==8}">
	                  					中标公示-竞争性谈判
	                  				</c:if>
	                  			</td>
	                 		</tr>
	                 		<tr>
	                  			<td class="bggrey " width="10%">创建时间：</td>
	                  			<td width="40%"><fmt:formatDate value='${templet.createdAt}' pattern='yyyy-MM-dd'/></td>
	                  			<td class="bggrey " width="10%">修改时间：</td>
	                  			<td width="40%"><fmt:formatDate value='${templet.updatedAt}' pattern='yyyy-MM-dd'/></td>
	                 		</tr> 
                 		</tbody>
           	 		</table>
					<h2 class="count_flow jbxx">模板内容</h2>
                 <div class="col-md-12 col-sm-12 col-cs-12 p0">
                     <script id="editor" name="content" type="text/plain" class="ml125 mt20 w900"></script>
                    <!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
                	</div>
                 </div>
                 	<!-- 底部按钮 -->			          
 				 <div class="col-md-12 col-sm-12 col-cs-12 mt10 tc">
  					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
 				 </div>
                </div>
			</div>  	
     	</div>
     </div>
    </div>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue = UE.getEditor('editor');
		var content = '${templet.content}';
		ue.ready(function() {
			ue.setContent(content);
	  		ue.setDisabled([]);
		});
	</script>
</body>
</html>
