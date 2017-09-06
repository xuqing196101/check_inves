<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp"%>
    <title>任务管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

    <script type="text/javascript">
        /*分页  */
        $(function() {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${list.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${list.total}",
                startRow: "${list.startRow}",
                endRow: "${list.endRow}",
                groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
                curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

                    return "${list.pageNum}";
                }(),
                jump: function(e, first) { //触发分页后的回调
                    if(!first) { //一定要加此判断，否则初始时会无限刷新
                        //location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=${projectId}&page='+e.curr;
                    }
                }
            });

            var typeclassId = "${typeclassId}";

            if(typeclassId != null && typeclassId != "") {
                $(".star_red").each(function(){
                    for(var i = 2; i < 4; i++){
                        $("#red"+i).addClass("dnone");
                    }
                });
            } else {
                for(var i = 0; i < 4; i++){
                    $("#red"+i).addClass("dnone");
                }
            }


            //获取包id
            var projectId = "${projectId}";
            if(projectId != null && projectId != '') {
                $("#projectName").attr("readonly", true);
                $("#projectNumber").attr("readonly", true);
                $("#packageName").attr("readonly", true);
                $("#tenderTimeId").attr("disabled", true);
            } else {
                $("#projectName").attr("readonly", false);
                $("#projectNumber").attr("readonly", false);
                $("#packageName").attr("readonly", false);
                $("#tenderTimeId").attr("disabled", false);
            }
            var index = 0 ;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
            //对于采购机构人员进行判断
            var isCurment = '${isCurment}';
            if(isCurment == '1'){
                $('.isCurment_div').removeClass('hide');
                $('.isCurment_div').addClass('block');
            }else if(isCurment == '0'){
                $('.isCurment_div').removeClass('block');
                $('.isCurment_div').addClass('hide');
            }

        });


        function ycDiv(obj, index) {
            if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
                $(obj).removeClass("shrink");
                $(obj).addClass("spread");
            } else {
                if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                    $(obj).removeClass("spread");
                    $(obj).addClass("shrink");
                }
            }

            var divObj = new Array();
            divObj = $(".p0" + index);
            for (var i =0; i < divObj.length; i++) {
                if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                    $(divObj[i]).removeClass("hide");
                } else {
                    if ($(divObj[i]).hasClass("p0"+index)) {
                        $(divObj[i]).addClass("hide");
                    };
                };
            };
        }
        $(function() {
            $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
            var index=0;
            var divObj = $(".p0" + index);
            $(divObj).removeClass("hide");
            $("#package").removeClass("shrink");
            $("#package").addClass("spread");
        });

        function add(type) {

            var packageId=$("#packageId").val();
            var typeclassId = "${typeclassId}";
            $.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url: '${pageContext.request.contextPath}/SupplierExtracts/validateAddExtraction.do?type='+type,
                data: $('#form').serialize(), // 你的formid
                async: false,
                success: function(data) {
                    $("#projectNameError").text("");
                    $("#projectNumberError").text("");
                    $("#packageNameError").text("");
                    $("#dSupervise").text("");
                    $("#extractionSitesError").text("");
                    var map = data;
                    $("#projectNameError").text(map.projectNameError);
                    $("#projectNumberError").text(map.projectNumberError);
                    $("#packageNameError").text(map.packageNameError);
                    $("#dSupervise").text(map.supervise);
                    $("#extractionSitesError").text(map.extractionSitesError);
                    var projectId = map.projectId;
                    if(map.isSuccess=="false"){
                        layer.alert(map.msg, {shade: 0.01});
                        return false;
                    }
                    if(map.status != null && map.status != 0) {
                        layer.confirm('上次抽取未完成，是否继续上次抽取？', {
                            btn: ['确定','取消'], shade:0.01 //按钮
                        }, function(){
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }, function(){
                            layer.closeAll();
                        });
                    }
                    if(map.error == null && map.error != 'error'){
                        if(map.sccuess == "SCCUESS") {
                            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
                        }else if(map.packageError != null && map.packageError != ''){
                            layer.alert("请选择包", {
                                shade: 0.01
                            });
                        }else if(typeclassId != null && typeclassId != ''){
                            $("#projectId").val(projectId);
                            $("#pId").val(projectId);
                            if(map.type != null && map.type == '1'){
                                var iframeWin;
                                layer.open({
                                    type: 2,
                                    title: "选择包",
                                    shadeClose: true,
                                    shade: 0.01,
                                    offset: '20px',
                                    move: false,
                                    area: ['50%', '50%'],
                                    content: '${pageContext.request.contextPath}/SupplierExtracts/showPackage.do?projectId='+projectId,
                                    success: function(layero, index) {
                                        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                                    },
                                    btn: ['保存', '关闭'],
                                    yes: function() {
                                        iframeWin.add();

                                    },
                                    btn2: function() {
                                        layer.closeAll();
                                    }
                                });

                            }
                        }
                    }
                }
            });

        }
        /**抽取页面*/
        function opens(){

            window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + pachageId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
        }

        //选择监督人员
        function supervise() {
            //  iframe层
            var iframeWin;
            layer.open({
                type: 2,
                title: "选择监督人员",
                shadeClose: true,
                shade: 0.01,
                offset: '20px',
                move: false,
                area: ['90%', '50%'],
                content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do?projectId=${projectId}',
                success: function(layero, index) {
                    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
                btn: ['保存', '关闭'],
                yes: function() {
                    iframeWin.add();

                },
                btn2: function() {
                    layer.closeAll();
                }
            });
        }
    </script>

    <script type="text/javascript">
        function showPackageType() {
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {
                        "Y": "",
                        "N": ""
                    }
                },
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parentId"
                    }
                },
                callback: {
                    beforeClick: beforeClick,
                    onCheck: onCheck
                }
            };
            var projectId =$("#projectId").val();
            $.ajax({
                type: "GET",
                async: false,
                url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId="+projectId,
                dataType: "json",
                success: function(zNodes) {
                    tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
                    tree.expandAll(true); //全部展开
                }
            });
            var cityObj = $("#packageName");
            var cityOffset = $("#packageName").offset();
            $("#packageContent").css({
                left: cityOffset.left + "px",
                top: cityOffset.top + cityObj.outerHeight() + "px"
            }).slideDown("fast");
            $("body").bind("mousedown", onBodyDownPackageType);
        }

        function onBodyDownPackageType(event) {
            if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
                hidePackageType();
            }
        }

        function hidePackageType() {
            $("#packageContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDownPackageType);

        }

        function beforeClick(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
                nodes = zTree.getCheckedNodes(true),
                v = "";
            var rid = "";
            for(var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].name + ",";
                rid += nodes[i].id + ",";
            }
            if(v.length > 0) v = v.substring(0, v.length - 1);
            if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
            var cityObj = $("#packageName");
            cityObj.attr("value", v);
            cityObj.attr("title", v);
            $("#packageId").attr("value",rid);

        }
    </script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/ses/sms/supplierExtract.js"></script></head>
<body>
<div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
</div>
<!--面包屑导航开始-->
<c:if test="${typeclassId==null }">
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
                </li>
                <li>
                    <a href="javascript:void(0);">支撑环境</a>
                </li>
                <li>
                    <a href="javascript:void(0);">供应商管理</a>
                </li>
                <li>
                    <a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts/projectList.html?typeclassId=${typeclassId}')">供应商抽取</a>
                </li>
                <li class="active">
                    <a href="javascript:void(0);">供应商抽取列表</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
</c:if>

<!-- 项目戳开始 -->
<div class="container">
<!-- 项目信息开始 -->
<div class="container_box col-md-12 col-sm-12 col-xs-12">
    <form id="form">
        <!-- 监督人员 -->
        <input type="hidden" name="sids" id="sids" value="${userId}" />
        <!-- 打开类型 -->
        <input type="hidden" value="${typeclassId}" name="typeclassId"/>
        <!-- 项目id  -->
        <input type="hidden" id="pId" value="${projectId}" name="id">
        <!-- 监督人员id  -->
        <input type="hidden" id="superviseId" value="${superviseId}" name="superviseId">
        <!-- 包id  -->
        <%--           <input type="hidden" id="packageId" value="${packageId}" name="packageId"> --%>
         <h2 class="count_flow"><i>1</i>项目信息</h2>
         <ul class="ul_list border0 m0">
             <li class="col-md-3 col-sm-4 col-xs-12 pl15">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目名称:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectName" name="projectName"  value="${projectInfo.projectName}" type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 项目编号:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="projectNumber" name="projectCode" value="${projectInfo.projectCode}" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="projectNumberError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购方式:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
           			<c:if test="${projectInfo.purchaseType ==null }">
                     	<select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0">
                    </c:if>
                   	<c:if test="${projectInfo.purchaseType !=null }">
                		 <select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0" disabled="disabled">
                   			<option value="${projectInfo.purchaseType}" selected="selected">${projectInfo.purchaseTypeName}</option>
                   	</c:if>
                             <option value="3CF3C643AE0A4499ADB15473106A7B80" >竞争性谈判</option>
                             <option value="EF33590F956F4450A43C1B510EBA7923" >询价采购</option>
                             <option value="209C109291F241D88188521A7F8FA308" >邀请招标</option>
                     </select>
                 	<div class="cue" id="purchaseTypeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">包名(标段):</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="packageName" name="packageName" value="${projectInfo.projectCode}" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="packageNameError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领采购文件起始时间:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="sellBegin" readonly="readonly"  name="sellBegin" value="<fmt:formatDate value='${project}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="sellBeginError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领采购文件结束时间:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="sellEnd" readonly="readonly"  name="sellEnd" value="<fmt:formatDate value='${bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="sellEndError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领地区</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <select class="col-md-6 col-sm-6 col-xs-6 p0" id="sellProvince" name="sellProvince" onchange="selectArea(this);">
                            <c:forEach items="${province }" var="pro">
                                    <option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="sellAddress" class="col-md-6 col-sm-6 col-xs-6 p0" id="sellAddress">
                            <option value="" >选择地区</option>
                        </select>
                     <div class="cue" id="sellAreaError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>售领详细地址</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="sellSite" name="sellSite" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="sellSiteError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>项目类型</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <select id="projectType" name="projectType" class="col-md-12 col-sm-12 col-xs-6 p0" onchange="loadSupplierType()">
                          <option value="GOODS" ${projectInfo.projectType == 'GOODS' ? 'selected' : '' }>物资</option>
                          <option value="PROJECT" ${projectInfo.projectType == 'PROJECT' ? 'selected' : '' }>工程</option>
                          <option value="SERVICE" ${projectInfo.projectType == 'SERVICE' ? 'selected' : '' }>服务</option>
                     </select>
                     <span class="add-on">i</span>
                     <div class="cue" id="projectTypeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>项目实施地区</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                    <select class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionPro" name="constructionPro" onchange="selectArea(this);">
                            <c:forEach items="${province }" var="pro">
                                    <option value="${pro.id }">${pro.name }</option>
                            </c:forEach>
                        </select>
                        <select name="constructionAddr" class="col-md-6 col-sm-6 col-xs-6 p0" id="constructionAddr">
                            <option value="" >选择地区</option>
                        </select>
                     <span class="add-on">i</span>
                     <div class="cue" id="constructionProError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系人</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="contactPerson" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="sellSiteError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系电话</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="contactNum" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="sellSiteError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>其他要求</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input name="remark" value="" type="text" >
                     <span class="add-on">i</span>
                     <div class="cue" id="sellSiteError"></div>
                 </div>
             </li>
            <%--  <li class="col-md-3 col-sm-4 col-xs-12 ">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red" id="red3">*</span> 开标日期:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input class="col-md-12 col-sm-12 col-xs-6 p0"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  id="tenderTimeId" readonly="readonly"  name="bidDate" value="<fmt:formatDate value='${bidDate}'
                             pattern='yyyy-MM-dd HH:mm:ss' />" maxlength="30" type="text">
                     <div class="cue" id="tenderTimeError"></div>
                 </div>
             </li>
             <li class="col-md-3 col-sm-4 col-xs-12 ">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span> 监督人员:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input readonly id="supervises" title="${supervises}" value="${supervises}" onclick="supervise();" type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="dSupervise"></div>
                 </div>
             </li>
           <li class="col-md-3 col-sm-4 col-xs-12 ">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><span class="star_red">*</span> 抽取地区:</span>
                 <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <input id="extractionSites" name="extractionSites" value="${extractionSites}" type="text">
                     <span class="add-on">i</span>
                     <div class="cue" id="extractionSitesError"></div>
                 </div>
             </li> --%>
         </ul>
	 </form>
	</div><!-- 项目信息结束 -->
	<!-- 人员信息开始-->
	<div class="container_box col-md-12 col-sm-12 col-xs-12">
		 <h2 class="count_flow"><i>2</i>人员信息</h2>
		 <span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="star_red">*</span><b> 抽取人员:</b></span>
		 <form action="" id="extractUser">
		 <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		 	<input type="button" class="btn btn-windows add" onclick="addPerson(this)" value="新增">
		 	<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
		 	<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
		 </div>
		 <div class="clear"></div>
		  <table class="table table-bordered table-condensed table_input mt10">
              <thead>				
	              <tr>
	                  <th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
	                  <th class="info">序号</th>
	                  <th class="info" width="15%">姓名</th>
	                  <th class="info" width="40%">单位</th>
	                  <th class="info" width="15%">职务</th>
	                  <th class="info" width="15%">军衔</th>
	              </tr>
              </thead>
              <tbody>
              <tr>
              	<td class="tc"> <input type="checkbox"> </td>
              	<td class="tc"> 1 </td>
              	<td> <input name="list[0].name" type="text"> <span class="name_0_Error"></span> </td>
              	<td> <input name="list[0].compary" type="text" class="w100p"> <span class="compary_0_Error"></span> </td>
              	<td> <input name="list[0].duty" type="text">  <span class="duty_0_Error"></span></td>
              	<td> <input name="list[0].rank" type="text">  <span class="rank_0_Error"></span></td>
              </tr>
              <tr>
              	<td class="tc"> <input type="checkbox"> </td>
              	<td class="tc"> 2 </td>
              	<td> <input name="list[1].name" type="text">  <span class="name_1_Error"></span></td>
              	<td> <input name="list[1].compary" type="text" class="w100p">  <span class="compary_1_Error"></span></td>
              	<td> <input name="list[1].duty" type="text">  <span class="duty_1_Error"></span></td>
              	<td> <input name="list[1].rank" type="text"> <span class="rank_1_Error"></span> </td>
              </tr>
            </tbody>
          </table>
       </form>      
		 <span class="col-md-12 col-sm-12 col-xs-12 p0"><span class="star_red">*</span><b> 监督人员:</b></span>
		  <form action="" id="supervise">
		  <div class="col-md-12 col-sm-12 col-xs-12 p0 mt10">
		 	<input type="button" class="btn btn-windows add" onclick="addPerson(this)" value="新增">
		 	<input type="button" class="btn btn-windows delete" onclick="delPerson(this)" value="删除">
		 	<input type="button" class="btn btn-windows input" onclick="selectHistory(this)" value="引用历史人员">
		 </div>
		 <div class="clear"></div>
		  <table class="table table-bordered table-condensed table_input mt10">
              <thead>				
	              <tr>
	                  <th class="info"><input type="checkbox" onclick="checkAll(this)"> </th>
	                  <th class="info">序号</th>
	                  <th class="info" width="15%">姓名</th>
	                  <th class="info" width="40%">单位</th>
	                  <th class="info" width="15%">职务</th>
	                  <th class="info" width="15%">军衔</th>
	              </tr>
              </thead>
              <tbody>
              <tr>
              	<td class="tc"> <input type="checkbox"> </td>
              	<td class="tc"> 1 </td>
              	<td> <input name="list[0].name" type="text"> <span class="name_0_Error"></span> </td>
              	<td> <input name="list[0].compary" type="text" class="w100p"> <span class="compary_0_Error"></span> </td>
              	<td> <input name="list[0].duty" type="text">  <span class="duty_0_Error"></span></td>
              	<td> <input name="list[0].rank" type="text">  <span class="rank_0_Error"></span></td>
              </tr>
            </tbody>
          </table>
       </form>      
	</div>	
	<!-- 条件开始 -->
	<div class="container_box col-md-12 col-sm-12 col-xs-12">
    <form id="form1" method="post">
        <input id="sunCount" type="hidden">
        <!--    地區id -->
        <input type="hidden" name="addressId" id="addressId">
        <!--        项目id -->
        <input type="hidden" name="projectId" id="pid" value="${packageId}">
        <!-- 记录id -->
        <input type="hidden" name="recordId" id="pid" value="${projectInfo.id}">
        <!-- 地区 -->
        <input type="hidden" name="address" id="address">
		<input type="hidden" name="id" value="${projectInfo.conditionId }">
        <!-- 类型id -->
        <input type="hidden" name="supplierTypeId" id="supplierTypeId">
        <input type="hidden" name="expertsTypeId" id="expertsTypeId">

        <!--  满足多个条件 -->
        <!-- <input type="hidden" name="isMulticondition" id="isSatisfy" value="1"> -->
        <!-- 品目Name ， -->
        <input type="hidden" name="categoryName" id="extCategoryNames">
        <!--     品目id -->
       <!--  <input type='hidden' name='categoryId' id='extCategoryId'> -->
        <input type="hidden" name="addressReason" id="addressReason">
        <!--         省 -->
        <input type="hidden" name="province" id="province"/>
        <input type="hidden" name="" id="hiddentype">
          <h2 class="count_flow"><i>3</i>抽取条件</h2>
          <ul class="ul_list m0" style="background-color: #fbfbfb">
              <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div
                          class="star_red">*</div>所在地区：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  	<input class="input_group " readonly  id="area" onclick="showTree();">
                  	 <span class="add-on">i</span>
                  	  <div class="cue" id="dCategoryName"></div>
                  </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div
                          class="star_red">*</div>抽取类型：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  		<%-- <input name="supplierTypeCode" id="supplierTypeCode" type="hidden">
                      <input id="supplierType" class="" type="text" readonly
                             value="${listCon.conTypes[0].supplierTypeName }" name="supplierTypeName"
                             onclick="showSupplierType();"/> --%>
                      <select id=supplierType name="supplierTypeCode" onchange="initCategoryAndLevel(this)" class="w100p">
                      </select>
                      <span class="add-on">i</span>
                      <div class="cue" id="dCount"></div>
                  </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>抽取总数量：</span>
                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierCount" id="supplierCount" value="${sumCount}"
                             type="text">
                      <span class="add-on">i</span>
                      <div class="cue" id="countSupplier"></div>
                  </div>
              </li>
              
				<li class="clear"></li>
				<li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>工程品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="projectIsMulticondition" class="isSatisfy">
          <input type="hidden" name="projectCategoryIds" id="projectCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="PROJECT"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"></span>工程资质：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="qua" >
            <input type="text" readonly  id="quaName" treeHome="quaContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showQua(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>工程等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="projectLevel" >
            <input type="text" readonly  id="projectLevel" treeHome="projectLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone projectCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='projectExtractNum' name="projectExtractNum" onchange="chane();"
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        
        <li class="clear"></li>
        <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>服务品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="serviceIsMulticondition" class="isSatisfy">
          <input type="hidden" name="serviceCategoryIds" id="serviceCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="SERVICE"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>服务等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="serviceLevel" >
            <input type="text" readonly  id="serviceLevel" treeHome="serviceLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone serviceCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='serviceExtractNum' name="serviceExtractNum" onchange="chane();"
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        
        <li class="clear"></li>
        <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>生产品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="productIsMulticondition" class="isSatisfy">
          <input type="hidden" name="productCategoryIds" id="productCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="PRODUCT"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>生产等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="productLevel" >
            <input type="text" readonly  id="productLevel" treeHome="productLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone productCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='productExtractNum' name="productExtractNum" onchange="chane();"
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
        
        <li class="clear"></li>
        <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>销售品目：</span>
          <!--  满足多个条件 -->
          <input type="hidden" name="salesIsMulticondition" class="isSatisfy">
          <input type="hidden" name="salesCategoryIds" id="salesCategoryIds" class="categoryId">
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="input_group " readonly  typeCode="SALES"
                 value="${listCon.conTypes[0].categoryName}" onclick="opens(this);" type="text">
            <span class="add-on">i</span>
            <div class="cue" id="dCategoryName"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>销售等级：</span>
           <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input type="hidden" name="salesLevel" >
            <input type="text" readonly  id="salesLevel" treeHome="salesLevelContent"
                 value="${listCon.supplierLevel == null? '所有级别':listCon.supplierLevel}" onclick="showLevel(this);"/>
            <span class="add-on">i</span>
            <div class="cue" id="dCount"></div>
          </div>
          </li>
          <li class="col-md-3 col-sm-3 col-xs-3 dnone salesCount">
          <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>供应商数量：</span>
          <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input class="title col-md-12" id='salesExtractNum' name="salesExtractNum" onchange="chane();"
                 maxlength="11" type="text">
            <span class="add-on">i</span>
            <div class="cue">${loginPwdError}</div>
          </div>
        </li>
          </ul>
          <div class="clear"></div>
	         <div class="col-xs-12 tc mt20">
	           <button class="btn" onclick="extractVerify();" type="button">人工抽取</button>
	           <button class="btn" onclick="finish();" type="button">自动抽取</button>
	           <button class="btn" onclick="temporary();" type="button">重置</button>
	         </div>
          </form>
          <!--=== Content Part ===-->
          </div>
          <div class="container_box col-md-12 col-sm-12 col-xs-12">
          <h2 class="count_flow"><i>4</i>抽取结果</h2>
	         <div class="ul_list" id="projectResult">
	          	<div align="center" id="countdnone" class="f26    ">满足条件共有<span class="f26 red" id="count">0</span>人</div>
	           	<!-- Begin Content -->
                 <table id="table" class="table table-bordered table-condensed">
                     <thead>
                     <tr>
                         <th class="info w50">序号</th>
                         <th class="info" width="15%">供应商名称</th>
                         <th class="info" width="15%">类型</th>
                         <th class="info" width="15%">联系人名称</th>
                         <th class="info" width="18%">联系人电话</th>
                         <th class="info" width="18%">联系人手机</th>
                         <th class="info">操作</th>
                     </tr>
                     </thead>
                     <tbody>
                     
                     </tbody>
                </table>
			</div>
          <div class="ul_list dnone clear" id="serviceResult">
          <div align="center" id="countdnone" class="f26">满足条件共有<span class="f26 red" id="count">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
          <div class="ul_list dnone clear" id="productResult">
          <div align="center" id="countdnone" class="f26    ">满足条件共有<span class="f26 red" id="count">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
          <div class="ul_list dnone clear" id="salesResult">
          <div align="center" id="countdnone" class="f26    ">满足条件共有<span class="f26 red" id="count">0</span>人</div>
           <!-- Begin Content -->
                  <table id="table" class="table table-bordered table-condensed">
                      <thead>
                      <tr>
                          <th class="info w50">序号</th>
                          <th class="info" width="15%">供应商名称</th>
                          <th class="info" width="15%">类型</th>
                          <th class="info" width="15%">联系人名称</th>
                          <th class="info" width="18%">联系人电话</th>
                          <th class="info" width="18%">联系人手机</th>
                          <th class="info">操作</th>
                      </tr>
                      </thead>
                      <tbody>
                      
                      </tbody>
                 </table>
			</div>
		</div>
</div>
<!-- 地区树 -->
<div id="areaContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul  id="treeArea" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 类别树 -->
<div id=supplierTypeContent class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
</div>

<!-- 工程等级树 -->
<div id="projectLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="projectLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 服务等级树 -->
<div id="serviceLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="serviceLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 生产等级树 -->
<div id="productLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="productLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 销售等级树 -->
<div id="salesLevelContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="salesLevelTree" class="ztree" style="margin-top:0;"></ul>
</div>
<!-- 工程资质树 -->
<div id="quaContent" class="levelTypeContent"
     style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="quaTree" class="ztree" style="margin-top:0;"></ul>
</div>

</body>
</html>