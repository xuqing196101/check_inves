<%@ page language="java" import="java.net.URLEncoder" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@page contentType="application/vnd.ms-word;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="${pageContext.request.contextPath}/"/>

        <title>供应商抽取记录表</title>

        <meta http-equiv="pragma" content="no-cache"/>

        <meta http-equiv="cache-control" content="no-cache"/>

        <meta http-equiv="expires" content="0"/>

        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>

        <meta http-equiv="description" content="This is my page"/>
        <%
            String fileName = "供应商抽取记录表.doc";
            //对中文文件名编码
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                //解决IE下文件名乱码
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                //解决非IE下文件名乱码
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        %>
        <style type="text/css">
            table{
            text-align:center;
            table-layout:fixed;
            empty-cells:show;
            border-collapse: collapse;
            margin:0 auto;
            }
            h1,h2,h3{
            margin:0;
            padding:0;
            }
            .table{
            border:1px solid #000000;
            color:#000000;
            }
            .table th {
            background-repeat:repeat-x;
            }
            .table td,.table th{
            border:1px solid #000000;
            padding:0 1em 0;
            }
            .table tr.alter{
            background-color: #000000;
            }
        </style>
    </head>
    <body>
        <!-- 修改订列表开始-->
        <div style="width:85%;margin:auto;">
            <div style="text-align:center;margin-bottom:20px;">
                <h3><b>供应商抽取记录表</b></h3>
            </div>
            <div>
                <span style="float:left;margin-right:20px;">项目代码:</span>&nbsp;&nbsp;${ExpExtractRecord.projectCode}
            </div>
            <table width="100%" border="1">
                <tr>
                    <td colspan="2">项目名称</td>
                    <td colspan="7">${ExpExtractRecord.projectName}</td>
                </tr>
                <tr>
                    <td colspan="2">抽取时间</td>
                    <td colspan="2" ><fmt:formatDate value="${ExpExtractRecord.extractionTime}" pattern="yyyy年MM月dd日"/></td>
                    <td colspan="2">抽取地点</td>
                    <td colspan="3" >${fn:replace(ExpExtractRecord.extractionSites,',','')}</td>
                </tr>
                <tr>
                    <td align="center" colspan="2">抽取条件<br>抽取数量
                    </td>
                    <td colspan="7">
                        <div width="100%">
                            <c:forEach items="${conditionList}" var="con" varStatus="vs">
                                <c:if test="${con.listSupplierCondition != null && fn:length(con.listSupplierCondition) != 0}">
                                    <p style="font-size:16px!important;">
                                        包名：${con.name}
                                    </p>
                                    <c:forEach items="${ con.listSupplierCondition}" var="conlist" varStatus="vs">
                                        <p>第${(vs.index+1)}次抽取，抽取条件如下：</p>
                                        <p>供应商所在地区：${conlist.address }
                                            <c:if test="${conlist.addressId != null }">
                                                <c:if test="${conlist.addressReason != null}">
                                                    限制条件：${conlist.addressReason }
                                                </c:if>
                                            </c:if>
                                        </p>
                                        <c:if test="${conlist.categoryName != null}">
                                            <p>
                                                品目：${conlist.categoryName }
                                            </p>
                                        </c:if>
                                        <ol>
                                            <c:forEach items="${conlist.conTypes }" var="contypes">
                                                <li>供应商类型：${contypes.supplierType.name}
                                                    ，供应商数量：${contypes.supplierCount}</li>
                                            </c:forEach>
                                        </ol>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>

                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="9" align="center">抽取记录</td>
                </tr>
                <tr>
                    <td align="center">序号</td>
                    <td align="center" colspan="2">供应商名称</td>
                    <td align="center">联系人</td>
                    <td align="center" colspan="2">手机号</td>
                    <td align="center">传真</td>
                    <td align="center">能否参加</td>
                    <td align="center">不参加理由</td>
                </tr>
                <c:forEach items="${conditionList}" var="con" varStatus="vs">
                    <tr>
                        <td colspan="9" class="pl20">${con.name}</td>
                    </tr>
                    <c:forEach items="${con.listSupplierCondition}" var="pe" varStatus="vse">
                        <c:forEach items="${pe.extRelatesList}" var="ext" varStatus="vs">
                            <tr>
                                <td align="center">${vs.index+1 }</td>
                                <td align="center" colspan="2">${ext.supplier.supplierName}</td>
                                <td align="center">${ext.supplier.contactName}</td>
                                <td align="center" colspan="2">${ext.supplier.mobile}</td>
                                <td align="center">${ext.supplier.contactFax}</td>
                                <td align="center"><c:if test="${ext.operatingType==1 }">
                                    是
                                </c:if> <c:if test="${ext.operatingType==2 }">
                                    待定
                                </c:if> <c:if test="${ext.operatingType==3 }">
                                    否
                                </c:if></td>
                                <td align="center">${ext.reason}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:forEach>
                <tr>
                    <td colspan="9" align="center">抽取人员</td>
                </tr>
                <tr>
                    <td align="center">序号</td>
                    <td align="center">姓名</td>
                    <td align="center" colspan="2">手机号</td>
                    <td align="center" colspan="2">单位</td>
                    <td align="center">职务</td>
                    <td align="center">军衔</td>
                    <td align="center">签字</td>
                </tr>
                <tr>
                    <td align="center">1</td>
                    <td align="center">${ExpExtractRecord.perpleUser.relName}</td>
                    <td align="center" colspan="2">${ExpExtractRecord.perpleUser.mobile}</td>
                    <td align="center" colspan="2">${ExpExtractRecord.perpleUser.org.name}</td>
                    <td align="center">${ExpExtractRecord.perpleUser.duties}</td>
                    <td align="center"></td>
                    <td align="center"></td>
                </tr>
                <tr>
                    <td colspan="9" align="center">监督人员</td>
                </tr>
                <tr>
                    <td align="center">序号</td>
                    <td align="center">姓名</td>
                    <td align="center" colspan="2">单位</td>
                    <td align="center" colspan="2">手机号</td>
                    <td align="center">职务</td>
                    <td colspan="2" align="center">签字</td>
                </tr>
                <c:forEach items="${listUser}" var="tuser" varStatus="vs">
                    <tr>
                        <td align="center">${vs.index+1 }</td>
                        <td align="center">${tuser.relName}</td>
                        <td align="center" colspan="2">${tuser.company}</td>
                        <td align="center" colspan="2">${tuser.phone}</td>
                        <td align="center">${tuser.duties}</td>
                        <td colspan="2" align="center"></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
