<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
    <%@ include file="../../../common.jsp" %>
    <style type="text/css">
        .panel-title > a {
            color: #333
        }

    </style>
    <script type="text/javascript">
        function save() {
            var index = parent.layer.getFrameIndex(window.name);
            var pid = parent.$("#parentid").val();
            console.dir(pid);
            $.ajax({
                type: 'post',
                url: "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
                data: $.param({'parentId': pid}) + '&' + $('#formID').serialize(),
                //data: {'pid':pid,$("#formID").serialize()},
                success: function (data) {
                    truealert(data.message, data.success == false ? 5 : 1);
                }
            });

        }
        function truealert(text, iconindex) {
            layer.open({
                content: text,
                icon: iconindex,
                shade: [0.3, '#000'],
                yes: function (index) {
                    //do something
                    parent.location.reload();
                    layer.closeAll();
                    parent.layer.close(index); //执行关闭
                    //parent.location.href="${pageContext.request.contextPath}/purchaseManage/list.do";
                }
            });
        }
        function back(){
			var parentName = $("#parentName").val();
			window.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseDepdetailList.html?parentName="+parentName; 
        }
    </script>
</head>
<body>

<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
            <li><a href="javascript:void(0);">支撑系统</a></li>
            <li><a href="javascript:void(0);">机构管理</a></li>
            <li class="active"><a href="javascript:void(0);">采购机构管理</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<!-- 修改订列表开始-->
<div class="container mt10">
    <div class="tab-content">
        <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
                <li class="active"><a href="#dep_tab-0" data-toggle="tab" class="f18">采购机构信息</a></li>
            </ul>
            <form action="${pageContext.request.contextPath}/purchaseManage/createPurchaseDep.do" method="post"
                  id="formID">
                <div class="tab-content padding-top-20">
                    <div class="tab-pane fade active in" id="tab-1">
                        <h2 class="count_flow jbxx mt0">基本信息</h2>
                        <input type="hidden" value="2" name="typeName"/>

                        <table class="table table-bordered">
                            <tbody>
                                <tr>
                                    <td class="bggrey w160 tr">采购机构名称：</td>
                                    <td>${purchaseDep.name }</td>
                                    <td class="bggrey w160 tr">采购业务范围：</td>
                                    <td>${purchaseDep.businessRange }</td>
                                </tr>
                                <tr>
                                    <td class="bggrey tr">单位主要领导及电话：</td>
                                    <td>${purchaseDep.leaderTelephone }</td>
                                    <td class="bggrey tr">单位地址：</td>
                                    <td>${purchaseDep.address }</td>
                                </tr>
                            </tbody>
                        </table>
                        <!-- <h2 class="count_flow jbxx">上级部门</h2>
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <td class="bggrey">上级监管部门：</td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table> -->
                        <h2 class="count_flow jbxx">采购人员</h2>
                        <table id="tb1" class="table table-bordered table-condensed table-hover table-striped mb0">
                            <thead>
                                <tr>
                                    <th class="w50">序号</th>
                                    <th>姓名</th>
                                    <th>所属采购机构</th>
                                    <th>人员类别</th>
                                    <th>性别</th>
                                    <!-- <th>年龄</th> -->
                                    <th>职务</th>
                                    <th>职称</th>
                                    <th>采购资格等级</th>
                                    <th>学历</th>
                                    <th>办公号码</th>
                                    <!-- <th>资质证书类型</th> -->
                                    <th>资质证书编号</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchaselist }" var="p" varStatus="vs">
                                <tr>
                                    <td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
                                    <!-- 标题 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.relName}</td>
                                    <!-- 内容 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.purchaseDepName}</td>
                                    <!-- 创建人-->
                                    <td class="tc" onclick="show('${p.id}');">
                                        <c:choose>
                                            <c:when test="${p.purcahserType==0}">
                                                军人
                                            </c:when>
                                            <c:when test="${p.purcahserType==1}">
                                                文职
                                            </c:when>
                                            <c:when test="${p.purcahserType==2}">
                                                职工
                                            </c:when>
                                            <c:when test="${p.purcahserType==3}">
                                                战士
                                            </c:when>
                                            <c:otherwise>

                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">
										<c:forEach items="${genders}" var="g">
											<c:if test="${g.id eq p.gender}">
												${g.name}
											</c:if>
										</c:forEach>
									</td>
                                    <!-- 是否发布 -->
                                    <%-- <td class="tc" onclick="show('${p.id}');">${p.age}</td> --%>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.duties}</td>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.professional}</td>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">
                                        <c:choose>
                                            <c:when test="${p.quaLevel==0}">
                                                初
                                            </c:when>
                                            <c:when test="${p.quaLevel==1}">
                                                中
                                            </c:when>
                                            <c:when test="${p.quaLevel==2}">
                                                高
                                            </c:when>
                                            <c:when test="${p.quaLevel==3}">

                                            </c:when>
                                            <c:otherwise>

                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <!-- 创建人-->
                                    <td class="tc" onclick="show('${p.id}');">${p.topStudy}</td>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.telephone}</td>
                                    <!-- 是否发布 -->
                                        <%-- <td class="tc" onclick="show('${p.id}');">${p.quaCode}</td> --%>
                                    <!-- 是否发布 -->
                                    <td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
                                </tr>
                            </c:forEach>
                            </tbody>

                        </table>
                    </div>
                    <div class="mt20 tc col-md-12 col-sm-12 col-xs-12">
                    <input type="hidden" id="parentName" value="${parentName }">
                		<input type="button" class="btn btn-windows back" onclick="back();" value="返回" />
              		</div>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 伸缩层 -->

<div class="col-md-12">

</div>
</body>
</html>
