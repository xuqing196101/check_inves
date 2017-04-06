<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

<title>供应商黑名单添加</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
    $(function() {
        $("#suppllier_name_input_id").click(function() {
            var id = $("input[name='id']").val();
            if (id) return;
            layer.open({
                type : 2,
                title : '选择供应商',
                // skin : 'layui-layer-rim', //加上边框
                area : [ '80%', '420px' ], //宽高
                offset : '100px',
                scrollbar : false,
                content : '${pageContext.request.contextPath}/supplier_blacklist/list_supplier.html', //url
                closeBtn : 1, //不显示关闭按钮
            });
        });
        
        autoSelected("term_select_id", "${supplierBlacklist.term}");
        autoSelected("punish_type_select_id", "${supplierBlacklist.punishType}");
        autoSelected("release_type_select_id", "${supplierBlacklist.releaseType}");
    });
    
    function autoSelected(id, v) {
        if (v) {
            $("#" + id).find("option").each(function() {
                var value = $(this).val();
                if(value == v) {
                    $(this).prop("selected", true);
                } else {
                    $(this).prop("selected", false);
                }
            });
        }
    }
</script>
</head>

<body>
        <!--面包屑导航开始-->
        <div class="margin-top-10 breadcrumbs ">
            <div class="container">
                <ul class="breadcrumb margin-left-0">
                    <li><a href="#"> 首页</a></li>
                    <li><a href="#">业务管理</a></li>
                    <li><a href="#">供应商黑名单</a></li>
                    <li class="active"><a href="#">添加供应商</a></li>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
        <div class="container container_box">
        <form action="${pageContext.request.contextPath}/supplier_blacklist/save_or_update_supplier_black.html" method="post">
            <div>
                 <h2 class="count_flow"><i>1</i>添加供应商</h2>
                    <ul class="ul_list">
                            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                             <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商名称</span>
                               <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                                  <input name="id" type="hidden" value="${supplierBlacklist.id}">
                                  <input name="supplierId" type="hidden" value="${supplier.id}">
                                <input class="input_group" name="supplierName" readonly="readonly" id="suppllier_name_input_id" type="text" value="${supplier.supplierName}">
                                <span class="add-on">i</span>
                               </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12">
                       <span class="col-md-12  col-sm-12 col-xs-12 padding-left-5">起始时间</span>
                               <div class="input-append input_group  col-md-12 col-sm-12 col-xs-12 p0">
                                    <fmt:formatDate value="${supplierBlacklist.startTime}" pattern="yyyy-MM-dd" var="startTime" />
                                    <input class="input_group" name="startTime" readonly="readonly" onClick="WdatePicker()" type="text" value="${startTime}"> 
                                </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12">
                               <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">期限</span>
                               <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                <select id="term_select_id" name="term">
                                    <option selected="selected" value="3">3个月</option>
                                    <option value="6">6个月</option>
                                    <option value="12">1年</option>
                                    <option value="24">2年</option>
                                    <option value="36">3年</option>
                                    <option value="0">永久</option>
                                </select>
                                </div>
                             </li>
                             <li class="col-md-3 col-sm-6 col-xs-12">
                               <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">处罚形式</span>
                                <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                <select id="punish_type_select_id"  name="punishType">
                                    <option selected="selected" value="0">警告</option>
                                    <option value="1">不得参加采购活动</option>
                                </select>
                                </div>
                             </li>
                            <li class="col-md-3 col-sm-6 col-xs-12 fl" style = "clear : both;">
                               <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">发布范围</span>
                               <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                <select id="release_type_select_id"  name="releaseType">
                                    <option selected="selected" value="0">内外网发布</option>
                                    <option value="1">内网发布</option>
                                    <option value="2">外网发布</option>
                                </select>
                                </div>
                             </li>
                             <li class="col-md-12 col-sm-12 col-xs-12">
                                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">理由</span>
                              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                <textarea class="col-md-12 col-sm-12 col-xs-12" name="reason"  style="height:130px" title="不超过800个字" placeholder="不超过800个字">${supplierBlacklist.reason}</textarea>
                               </div>
                             </li>
                    </ul>
                </div>
                <div class="col-md-12 tc col-sm-12 col-xs-12 mt10">
                    <input class="btn btn-windows save" type="submit" value="保存" />
                    <input class="btn btn-windows back" onclick="location='${pageContext.request.contextPath}/supplier_blacklist/list_blacklist.html'" type="button" value="返回">
                </div>
        </form>
    </div>
</body>
</html>
