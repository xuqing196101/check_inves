<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ page import="ses.constants.SupplierConstants" %>
<%@ page import="ses.model.bms.User" %>
<% 
	String account = ((User)session.getAttribute(SupplierConstants.KEY_SESSION_LOGIN_USER)).getLoginName();
	boolean isAccountToAudit = SupplierConstants.isAccountToAudit(account);
%>
<c:set var="isAccountToAudit" value="<%=isAccountToAudit %>" />

<!DOCTYPE HTML >
<html>
	<head>
		<%@ include file="../../../common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/supplier_query/select_supplier_by_province.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/supplier_query/select_supplier_common.js"></script>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listSupplier.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listSupplier.total}",
					startRow: "${listSupplier.startRow}",
					endRow: "${listSupplier.endRow}",
					groups: "${listSupplier.pages}" >= 5 ? 5 : "${listSupplier.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						/* var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1; */
						return "${listSupplier.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
							/* location.href = '${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.do?page=' + e.curr + "&address=" + encodeURI(encodeURI('${address}')); */
						}
					}
				});
			});

			$(function() {
				var optionStatus = $("#status").find("option");
				for(var i = 1; i < optionStatus.length; i++) {
					if("${supplier.status}" == $(optionStatus[i]).val()) {
						optionStatus[i].selected = true;
					}
				}
				var optionScore = $("#score").find("option");
				for(var i = 1; i < optionScore.length; i++) {
					if("${supplier.score}" == $(optionScore[i]).val()) {
						optionScore[i].selected = true;
					}
				}
                var supplierLevel = '${supplier.supplierLevel}';
                // 获取供应商品目
                var supplierCateQuery = $("#supplierGradeInput").val();
                if(supplierCateQuery != ''){
                    $("#supplierLevelLi").css("display", "block");
                    $("#supplierLevel").val(supplierLevel);
                }
				// 回显地区
                $("#address").val('${supplier.address}');
			});

            //窗口
            function openDiy(){
                layer.open({
                    type: 1,
                    title: 'DIY',
                    area: ['840px', '400px'],
                    closeBtn: 1,
                    shade:0.01, //遮罩透明度
                    moveType: 1, //拖拽风格，0是默认，1是传统拖动
                    shift: 1, //0-6的动画形式，-1不开启
                    offset: '10px',
                    shadeClose: false,
                    content: $("#openDiv"),
                });
            }
		</script>
	</head>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li>
					<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
				</li>
				<li>
					<a href="javascript:void(0);">支撑系统</a>
				</li>
				<li>
            <a href="javascript:void(0);">供应商管理</a>
          </li>
				<li>
					<a href="javascript:void(0);">全部供应商查询</a>
				</li>
				<li class="active">
					<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<input type="text" id="key" value="" class="empty" /><br/>
		<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	</div>
	<div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
	</div>
    <div id="supplierGradeTreeContent" class="supplierTypeContent" style="display:none; width: 250px; position: absolute;left:0px; top:0px; z-index:999;">
        <div class="col-md-12 col-xs-8 col-sm-8 p0">
            <input type="text" id="search" class="input_group" value="">
            <img src="${pageContext.request.contextPath }/public/backend/images/view.png" onclick="loadZtree()">
        </div>
        <ul id="supplierGradeTree" class="ztree" style="margin-top:0;"></ul>
    </div>

	<body>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商信息</h2>
			</div>
			<h2 class="search_detail">
                <!--下载Excel查询条件表单-->
                <form id="exportExcelCond">
                    <input type="hidden" name="supplierName" value="${supplier.supplierName}"/>
                    <input type="hidden" name="businessNature" value="${supplier.businessNature}"/>
                    <input type="hidden" name="status" value="${supplier.status}"/>
                    <input type="hidden" name="isProvisional" value="${supplier.isProvisional}"/>
                    <input type="hidden" name="creditCode" value="${supplier.creditCode}"/>
                    <input type="hidden" name="orgName" value="${supplier.orgName}"/>
                    <input type="hidden" name="address" value="${supplier.address}"/>
                    <input type="hidden" name="queryCategory" value="${supplier.queryCategory }"/>
					<input type="hidden" name="supplierTypeIds" value="${supplierTypeIds}" />
					<input type="hidden" name="supplierLevel" value="${supplier.supplierLevel }"/>
                    <input type="hidden" name="sign" value="${sign}"/>
                </form>
	      <form id="form1" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=${sign}" method="post" class="mb0">
	      	<c:if test="${sign != 1 }">
	      		<input type="hidden" name="address" value="${address }">
	      	</c:if>
	        <input type="hidden" name="page" id="page">
	        <ul class="demand_list">
	        	<li>
	          	<label class="fl">供应商名称：</label><span><input class="w220" id="supplierName" name="supplierName" value="${supplier.supplierName }" type="text"></span>
	          </li>
	          <%-- <li>
	          	<label class="fl">用户名：</label><span><input class="w220" id="loginName" name="loginName" value="${supplier.loginName }" type="text"></span>
	          </li> --%>
	          <%-- <li>
	            <label class="fl">联系人：</label><span><input class="w220" id="contactName" name="contactName" value="${supplier.contactName }" type="text"></span>
	          </li>
	          <li>
							<label class="fl">手机号：</label>
							<input id="mobile" class="w220" name="mobile" value="${supplier.mobile }" type="text">
						</li> --%>
            <li>
            	<label class="fl">企业性质：</label>
	            <select name="businessNature" id="businessNature" class="w220">
	              <option value=''>全部</option>
	              <c:forEach items="${businessNature}" var="list">
	              	<option <c:if test="${supplier.businessNature eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
	              </c:forEach>
	            </select>
         	  </li>
         	  <%--<li>
              <label class="fl">供应商类型：</label><span><input  class="w220" id="supplierType" class="span2 mt5" type="text" name="supplierType"  readonly value="${supplierType }" onclick="showSupplierType();" />
              <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" /></span>
            </li>--%>
                <li>
                    <label class="fl">供应商状态：</label>
                    <span>
                <select id="status" name="status" class="w220">
	                <option  selected="selected" value=''>全部</option>
									<c:forEach items="<%=SupplierConstants.STATUSMAP %>" var="item">
                                        <option value="${item.key}">${item.value}</option>
                                    </c:forEach>
                </select>
              </span>
                </li>
            <%-- <li>
              <label class="fl">品目：</label><span><input id="category" type="text" name="categoryNames" value="${categoryNames }" readonly onclick="showCategory();" class="w220"/>
              <input type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }" /></span>
            </li> --%>
	          <!-- <li>
			        <label class="fl">供应商级别:</label>
			        	<span>
			          	<select id="score" name="score" class="w220">
	                  <option  selected="selected" value=''>-请选择-</option>
	                  <option  value="1">一级</option>
	                  <option  value="2">二级</option>
	                  <option  value="3">三级</option>
	                  <option  value="4">四级</option>
	                  <option  value="5">五级</option>
			            </select>
			       	 </span>
	      		</li> -->
	          <li>
            	<label class="fl">临时供应商：</label>
	            <select name="isProvisional" id="isProvisional" class="w220">
	              <option value=''>全部</option>
	              <option value='1' <c:if test="${supplier.isProvisional eq '1' }">selected</c:if>>是</option>
	              <option value='0' <c:if test="${supplier.isProvisional eq '0' }">selected</c:if>>否</option>
	            </select>
	         	</li>
	         	<li>
	          	<label class="fl">社会信用代码：</label><span><input class="w220" id="creditCode" name="creditCode" value="${supplier.creditCode }" type="text"></span>
	          </li>
	          <%-- <li>
              <label class="fl">采购机构：</label><span><input class="w220" id="orgName" name="orgName" value="${supplier.orgName }" type="text"></span>
            </li> --%>
            <li>
              <label class="fl">采购机构：</label>
              <select name="orgName" id="orgName" class="w220">
                <option value=''>全部</option>
                <c:forEach items="${allOrg}" var="org">
                  <c:if test="${org.isAuditSupplier == 1}">
                    <option value="${org.shortName}" <c:if test="${supplier.orgName eq org.shortName}">selected</c:if>>${org.shortName}</option>
                  </c:if>
                </c:forEach>
              </select>
            </li>
                <c:if test ="${sign == 1 }">
                    <li>
                        <label class="fl">供应商品目：</label>
                          <span><input  class="w220" name="queryCategoryName" id="supplierGradeInput" class="span2 mt5" type="text" name=""  readonly value="${supplier.queryCategoryName }" onclick="initZtree(true);" />
                            <input type="hidden" name="queryCategory" id="supplierGradeInputVal" value="${supplier.queryCategory}"/>
                            <input type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds}" />
                          </span>
                    </li>
                    <li class="hide"  id="supplierLevelLi">
                        <label class="fl">供应商等级：</label>
                        <select name="supplierLevel" id="supplierLevel" class="w220">
                            <option selected="selected" value=''>全部</option>
                            <option value="一级">一级</option>
                            <option value="二级">二级</option>
                            <option value="三级">三级</option>
                            <option value="四级">四级</option>
                            <option value="五级">五级</option>
                            <option value="六级">六级</option>
                            <option value="七级">七级</option>
                            <option value="八级">八级</option>
                        </select>
                    </li>
                    <li>
                        <label class="fl">地区：</label>
                        <select name="address" id="address" class="w220">
                            <option value=''>全部</option>
                            <c:forEach items="${privnce}" var="list">
                                <option value="${list.id}">${list.name }</option>
                            </c:forEach>
                        </select>
                    </li>
                </c:if>
	        </ul>
	          <div class="col-md-12 clear tc mt10">
            	<button type="button" onclick="submit()" class="btn">查询</button>
              <button type="reset" onclick="chongzhi()" class="btn">重置</button>

              <!-- <button type="reset" onclick="openDiy()" class="btn">自定义查询</button> -->
              <c:choose>
								<c:when test="${sign == 1 }">
								 		<a href="${pageContext.request.contextPath}/supplierQuery/highmaps.html" class="btn">切换到地图</a>
								 		<a href="javascript:;" class="btn" id="export_result">将结果导出Excel</a>
								</c:when>
								<c:otherwise>
										<button class="btn btn-windows back" type="button" onclick="location.href='${pageContext.request.contextPath}/supplierQuery/highmaps.html'">返回</button>
										<!-- <button class="btn btn-windows delete" type="button" onclick="cancellation();">注销</button> -->
								</c:otherwise>
							</c:choose>
							<!-- <button class="btn btn-windows edit" type="button" onclick="resetPwd()">重置密码</button> -->
							<!-- <button class="btn btn-windows delete" type="button" onclick="cancellation();">注销</button> -->
	          </div>
	          <div class="clear"></div>
	       </form>
     </h2>
			<div class="col-md-12 pl20 mt10">

			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<!-- <th class="info w50">选择</th> -->
							<th class="info w50">序号</th>
							<th class="info w150">采购机构</th><!-- width="10%" -->
							<th class="info w250">供应商名称</th><!-- width="20%" -->
							<th class="info w150">地区</th><!-- width="8%" -->
							<th class="info w70">企业性质</th><!-- width="6%" -->
							<th class="info w150">供应商类型</th><!-- width="15%" -->
							<th class="info w90">注册日期</th>
							<th class="info w90">最新提交日期</th>
							<th class="info w90">最新审核日期</th>
							<th class="info w90">入库日期</th>
							<th class="info w100">状态</th><!-- width="10%" -->
							<c:if test="${isAccountToAudit}"><th class="info w50">操作</th></c:if><!-- width="3%" -->
						</tr>
					</thead>
					<tbody>
						<c:set var="supplierStatusMap" value="<%=SupplierConstants.STATUSMAP %>"/>
						<c:set var="supplierAuditTemporaryStatusMap" value="<%=SupplierConstants.STATUSMAP_AUDITTEMPORARY %>"/>
						<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
                <tr>
                    <%-- <td class="tc w30"><input type="radio" value="${list.id }" name="chkItem"  id="${list.id}"></td> --%>
                    <td class="tc">${(vs.count)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
                    <td class="tl">${list.orgName}</td>
                    <td class="hand" title="${list.supplierName}">
                        <c:choose>
                            <c:when test="${list.status == 5 and list.isProvisional == 1 }">
                                <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/temporarySupplier.html?supplierId=${list.id}&sign=${sign}')">
                                    <c:if test="${fn:length (list.supplierName) > 15}">${fn:substring(list.supplierName,0,15)}...</c:if>
                                    <c:if test="${fn:length (list.supplierName) <= 15}">${list.supplierName}</c:if>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/essential.html?supplierId=${list.id}&sign=${sign}')">
                                    <c:if test="${fn:length (list.supplierName) > 15}">${fn:substring(list.supplierName,0,15)}...</c:if>
                                    <c:if test="${fn:length (list.supplierName) <= 15}">${list.supplierName}</c:if>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="tl">${list.name }</td>
                    <td class="tc">${list.businessNature }</td>
                    <td class="">${list.supplierType }</td>
                    <td class="tc">
                        <fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td class="tc">
                        <fmt:formatDate value="${list.submitAt }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td class="tc">
                        <fmt:formatDate value="${list.auditDate }" pattern="yyyy-MM-dd"/>
                    </td>
                    <td class="tc">
                        <c:if test="${!empty list.instorageAt}">
                            <fmt:formatDate value="${list.instorageAt }" pattern="yyyy-MM-dd"/>
                        </c:if>
                    </td>
                    <td class="tc">
                            <%-- <c:if test="${list.status==5 and list.isProvisional == 1}"><span class="label rounded-2x label-dark">临时</span></c:if>
                            <c:if test="${list.status==-1 }"><span class="label rounded-2x label-dark">暂存</span></c:if>
                            <c:if test="${list.status==0 }"><span class="label rounded-2x label-dark">待审核</span></c:if>
                            <c:if test="${list.status==-2 }"><span class="label rounded-2x label-dark">预审核结束</span></c:if>
                            <c:if test="${list.status==-3 }"><span class="label rounded-2x label-dark">公示中</span></c:if>
                            <c:if test="${list.status==1 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
                            <c:if test="${list.status==2 }"><span class="label rounded-2x label-dark">退回修改</span></c:if>
                            <c:if test="${list.status==9 }"><span class="label rounded-2x label-dark">退回再审核</span></c:if>
                            <c:if test="${list.status==3 }"><span class="label rounded-2x label-dark">审核未通过</span></c:if>
                            <c:if test="${list.status==4 }"><span class="label rounded-2x label-dark">待复核</span></c:if>
                            <c:if test="${list.status==5 and list.isProvisional == 0}"><span class="label rounded-2x label-u">复核通过</span></c:if>
                            <c:if test="${list.status==6 }"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
                            <c:if test="${list.status==7 }"><span class="label rounded-2x label-u">考察合格</span></c:if>
                            <c:if test="${list.status==8 }"><span class="label rounded-2x label-dark">考察不合格</span></c:if> --%>

                            <%-- <c:if test="${list.status==5 and list.isProvisional == 1}"><span class="label rounded-2x label-dark">临时</span></c:if>
                            <c:if test="${list.status==-1 }"><span class="label rounded-2x label-dark">暂存</span></c:if>
                            <c:if test="${list.status==0 }"><span class="label rounded-2x label-dark">待审核</span></c:if>
                            <c:if test="${list.status==-2 }"><span class="label rounded-2x label-dark">预审核结束</span></c:if>
                            <c:if test="${list.status==-3 }"><span class="label rounded-2x label-dark">公示中</span></c:if>
                            <c:if test="${list.status==2 }"><span class="label rounded-2x label-dark">退回修改</span></c:if>
                            <c:if test="${list.status==9 }"><span class="label rounded-2x label-dark">退回再审核</span></c:if>
                            <c:if test="${list.status==3 }"><span class="label rounded-2x label-dark">审核不通过</span></c:if>
                            <c:if test="${list.status==1 }"><span class="label rounded-2x label-dark">待复核</span></c:if>
                            <c:if test="${list.status==5 and list.isProvisional == 0}"><span class="label rounded-2x label-u">复核合格</span></c:if>
                            <c:if test="${list.status==6 }"><span class="label rounded-2x label-dark">复核不合格</span></c:if>
                            <c:if test="${list.status==7 }"><span class="label rounded-2x label-u">考察合格</span></c:if>
                            <c:if test="${list.status==8 }"><span class="label rounded-2x label-dark">考察不合格</span></c:if>
                            <c:if test="${list.status==-4 }"><span class="label rounded-2x label-dark">预复核结束</span></c:if>
                            <c:if test="${list.status==-5 }"><span class="label rounded-2x label-dark">预考察结束</span></c:if>
                            <c:if test="${list.status==10 }"><span class="label rounded-2x label-dark">异议处理</span></c:if> --%>

                            <%-- <c:set var="label_color" value="label-dark"/>
                            <c:if test="${list.status==5 || list.status==7 }"><c:set var="label_color" value="label-u"/></c:if>
                            <c:if test="${list.status==5 and list.isProvisional == 1}"><span class="label rounded-2x label-dark">临时</span></c:if>
                            <c:if test="${list.status==5 and list.isProvisional == 0}"><span class="label rounded-2x label-u">${supplierStatusMap[list.status]}</span></c:if>
                            <c:if test="${list.status!=5 }"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if> --%>

                        <c:set var="label_color" value="label-dark"/>
                        <c:if test="${list.status == 5 || list.status == 7}"><c:set var="label_color" value="label-u"/></c:if>
                        <c:if test="${list.status == 5 and list.isProvisional == 1}"><span
                                class="label rounded-2x label-dark">临时</span></c:if>
                        <c:if test="${list.status == 0 and list.auditTemporary != 1}"><span
                                class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
                        <c:if test="${list.status == 9 and list.auditTemporary != 1}"><span
                                class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
                        <c:if test="${(list.status == 0 or list.status == 9) and list.auditTemporary == 1}"><span
                                class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
                        <c:if test="${list.status == 1 and list.auditTemporary != 2}"><span
                                class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
                        <c:if test="${list.status == 1 and list.auditTemporary == 2}"><span
                                class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
                        <c:if test="${list.status == 5 and list.auditTemporary != 3 and list.isProvisional != 1}"><span
                                class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
                        <c:if test="${list.status == 5 and list.auditTemporary == 3 and list.isProvisional != 1}"><span
                                class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
                        <c:if test="${list.status != 0 && list.status != 9 && list.status != 1 && list.status != 5}"><span
                                class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
                    </td>
                    <c:if test="${isAccountToAudit and list.status == 2}">
                    	<td class="tc">
                    		<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/essential.html?supplierId=${list.id}&sign=${sign}')">标记</a>
                    	</td>
                    </c:if>
                    <c:if test="${isAccountToAudit and list.status != 2}">
                    	<td class="tc">
                    		<c:if test="${list.status == 5 and list.isProvisional == 1}">
                    			<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/temporarySupplier.html?supplierId=${list.id}&sign=${sign}')">查看</a>
                    		</c:if>
                    		<c:if test="${list.isProvisional != 1}">
                    			<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierQuery/essential.html?supplierId=${list.id}&sign=${sign}')">查看</a>
                    		</c:if>
                    	</td>
                    </c:if>
                </tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>

		<div id="openDiv" class="dnone layui-layer-wrap" >
		  <form id="form2" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=${sign}" method="post" class="mb0">
		  	<div class="drop_window">
		  		<input type="hidden" name="typeId" id="typeId" >
				  <ul class="demand_list">
	          <li>
	          	<label class="fl">联系人：</label><span><input class="w220" id="contactName" name="contactName" value="${supplier.contactName }" type="text"></span>
	          </li>
	          <li>
	          	<label class="fl">手机号：</label><span><input class="w220" id="mobile" name="mobile" value="${supplier.mobile }" type="text"></span>
	          </li>
	          <li>
	          	<label class="fl">注册日期：</label><span><input id="startDate" name="startDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endDate" name="endDate" value='<fmt:formatDate value="${supplier.endDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
	            </span>
	          </li>
	          <li>
	          	<label class="fl">提交日期：</label><span><input id="startSubimtDate" name="startSubimtDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startSubimtDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startSubimtDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startSubimtDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endSubimtDate" name="endSubimtDate" value='<fmt:formatDate value="${supplier.endSubimtDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endSubimtDate\')}'})"/>
	            </span>
	          </li>
	          <li>
	          	<label class="fl">审核日期：</label><span><input id="startAuditDate" name="startAuditDate" class="Wdate w110 fl" type="text"  value='<fmt:formatDate value="${supplier.startAuditDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startAuditDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startAuditDate\')}'})"/>
	            <span class="f14">至</span>
	            <input id="endAuditDate" name="endAuditDate" value='<fmt:formatDate value="${supplier.endAuditDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100 fl" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endAuditDate\')}'})"/>
	            </span>
	          </li>
				  </ul>
          <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
            <div class="col-md-12 clear tc mt10">
            	<button type="button" onclick="submit()" class="btn">查询</button>
            </div>
        	</div>
		    </div>
			 </form>
	  </div>
	</body>

</html>