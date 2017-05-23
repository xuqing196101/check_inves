<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>	
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
						<a href="javascript:void(0);">采购模板管理</a>
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
							<li class="active">
								<a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">资料详情</a>
							</li>
						</ul>
						<div class="tab-content padding-top-20 over_hideen">
							<div class="tab-pane fade active in" id="tab-1">
								<table class="table table-bordered">
									<tbody>
										<tr>
											<td class="bggrey " width="10%">采购模板名称：</td>
											<td colspan="3">${data.name }</td>
											<%--<td class="bggrey " width="10%">发布范围：</td>
											<td width="40%">
												<c:if test="${data.ipAddressType==0 }">
													内网
												</c:if>
												<c:if test="${data.ipAddressType==1 }">
													内外网
												</c:if>
											</td>
										--%></tr>
										<tr>
											<td class="bggrey " width="10%">附件：</td>
											<td width="40%"><u:show showId="data_file_show"  delete="false" businessId="${data.id }" sysKey="${sysKey}" typeId="${dataTypeId }" /></td>
											<td class="bggrey " width="10%">发布时间：</td>
											<td width="40%"><fmt:formatDate value="${data.publishAt }" pattern="yyyy-MM-dd" /></td>
										</tr>
										<tr>
											<td class="bggrey " width="10%">创建时间：</td>
											<td width="40%">
												<fmt:formatDate value="${data.createdAt }" pattern="yyyy-MM-dd" />
											</td>
											<td class="bggrey" width="10%">修改时间：</td>
											<td width="40%">
												<fmt:formatDate value="${data.updatedAt }" pattern="yyyy-MM-dd" />
											</td>
										</tr>
										<tr>
											<td class="bggrey " width="10%">发布范围：</td>
											<td width="40%">
												<c:if test="${ data.ipAddressType == 0 }">内网</c:if>
												<c:if test="${ data.ipAddressType == 1 }">内外网</c:if>
											</td>
											<td class="bggrey" width="10%"></td>
											<td width="40%"></td>
										</tr>
									</tbody>
								</table>

							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 底部按钮 -->
		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
		</div>
	</body>
</html>
