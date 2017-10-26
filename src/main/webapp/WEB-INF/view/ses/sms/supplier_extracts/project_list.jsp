<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
    <%@ include file="../../../common.jsp" %>

    <title>项目管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">


    <script type="text/javascript">

        /*分页  */
        $(function () {
            laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${info.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${info.total}",
                startRow: "${info.startRow}",
                endRow: "${info.endRow}",
                groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
                curr: function () { //通过url获取当前页，也可以同上（pages）方式获取
//                  var page = location.search.match(/page=(\d+)/);
//                  return page ? page[1] : 1;
                    return "${info.pageNum}";
                }(),
                jump: function (e, first) { //触发分页后的回调
                    if (!first) { //一定要加此判断，否则初始时会无限刷新
                        $("#page").val(e.curr);
                        $("#form1").submit();

                    }
                }
            });
        });

        //查看明细
        function view(id) {
            window.location.href = "${pageContext.request.contextPath}/project/view.html?id=" + id;
        }


        /** 全选全不选 */
        function selectAll() {
            var checklist = document.getElementsByName("chkItem");
            var checkAll = document.getElementById("checkAll");
            if (checkAll.checked) {
                for (var i = 0; i < checklist.length; i++) {
                    checklist[i].checked = true;
                }
            } else {
                for (var j = 0; j < checklist.length; j++) {
                    checklist[j].checked = false;
                }
            }
        }

        /** 单选 */
        function check() {
            var count = 0;
            var checklist = document.getElementsByName("chkItem");
            var checkAll = document.getElementById("checkAll");
            for (var i = 0; i < checklist.length; i++) {
                if (checklist[i].checked == false) {
                    checkAll.checked = false;
                    break;
                }
                for (var j = 0; j < checklist.length; j++) {
                    if (checklist[j].checked == true) {
                        checkAll.checked = true;
                        count++;
                    }
                }
            }
        }

        function opens() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length > 1) {
                layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
            } else {
				conditionId = $("#conditionId").val();
                window.location.href = "${pageContext.request.contextPath}/SupplierExtracts_new/Extraction.html?id=" + id + "&&conditionId=";
            }
        }
        function record() {
            location.href = '${pageContext.request.contextPath}/SupplierExtracts_new/resuleRecordlist.do';
        }
        
    </script>
</head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
            <li><a href="javascript:void(0);">支撑环境系统</a></li>
            <li><a href="javascript:void(0);">供应商管理</a></li>
            <li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts_new/projectList.html?typeclassId=${typeclassId}')">供应商抽取记录</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<!-- 录入采购计划开始-->
<div class="container">
    <div class="headline-v2">
        <h2>立项列表</h2>
    </div>
    <!-- 项目戳开始 -->
    <h2 class="search_detail">
        <form action="${pageContext.request.contextPath}/SupplierExtracts_new/projectList.html" id="form1" method="post" class="mb0">
            <input type="hidden" name="page" id="page" value="1">
	        <ul class="demand_list">
	          <li>
	            <label>项目名称：</label>
	            <input type="text" name="projectName" id="projectName" value="${project.projectName }" />
	          </li>
	          <li>
	            <label class="fl">项目编号：</label>
	            <input type="text" name="projectCode" id="projectCode" value="${project.projectCode }" />
	          </li>
	          <li>
	            <label class="fl">采购方式：</label>
	            <select class="w178" name="purchaseType">
	              <option value="" <c:if test="${project.purchaseType == '' }">selected="selected"</c:if> >全部</option>
	              <c:forEach items="${purchaseTypeList}" var="map">
	                <option value="${map.id}" <c:if test="${project.purchaseType == map.id }">selected="selected"</c:if> >${map.name}</option>
	              </c:forEach>
	            </select>
	          </li>
	          <li>
	            <label class="fl">起始时间：</label>
	            <input id="startTime" name="startTime" type="text" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
	          </li>
	          <li>
	            <label class="fl">结束时间：</label>
	            <input id="endTime" name="endTime" type="text" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" readonly="readonly">
	          </li>
	          <li>
		          <button class="btn fl mt1" type="submit">查询</button>
		          <button type="button" class="btn fl mt1" onclick="form_reset()">重置</button>
	          </li>
	        </ul>
	        <div class="clear"></div>
        </form>
    </h2>
    <div class="col-md-12 pl20 mt10">
        <!-- <button class="btn"
                onclick="opens();">人工抽取
        </button>
        <button class="btn"
                onclick="record();">抽取记录
        </button> -->
        <button class="btn"
                onclick="printRecord();">下载抽取记录表
        </button>
    </div>
    <div class="content table_box">

        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()" alt=""></th>
                <th class="info w50">序号</th>
                <th class="info" >项目名称</th>
                <th class="info" >项目编号</th>
                <th class="info" >包名(标段)</th>
                <th class="info" >项目类型</th>
                <th class="info">采购方式</th>
                <th class="info" >抽取方式</th>
                <th class="info" >抽取人</th>
                <th class="info" >抽取时间</th>
                <th class="info" >抽取状态</th>
            </tr>
            </thead>


            <tbody id="tbody_id">
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
                <tr style="cursor: pointer;">
                    <td class="tc w30">
                        <input class="extract_status" type="hidden" value="${obj.status }"/>
                        <input type="hidden" class="conditionId" value="${obj.id }"/>
                        <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()" alt="">
                    </td>
                    <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                    <td>${obj.projectName}</td>
                    <td>${obj.projectCode}</td>
                    <td>${obj.packageName}</td>
                    <td>${obj.projectType}</td>
                    <td>${obj.purchaseType}</td>
                    <td>${obj.extractTheWay==0?"语音抽取":"人工抽取"}</td>
                    <td>${obj.extractUser}</td>
                    <td> <fmt:formatDate value="${obj.createdAt}" pattern="yyyy-M-d HH:mm:ss"/> </td>
                    <td>${obj.status==1?"结束":"抽取中"}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div id="pagediv" align="right"></div>
</div>
<
</body>
</html>
<script type="text/javascript">

	//下载记录表
	function printRecord() {
        	var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length != 1) {
                layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
            } else {
            	
            	var status =  $('input[name="chkItem"]:checked').prev().prev().val();
            	if("1"==status){
					conditionId = $("#conditionId").val();
	                window.location.href = "${pageContext.request.contextPath}/SupplierExtracts_new/printRecord.html?id=" + id + "&&conditionId=";
            	}else{
            		 layer.alert("当前记录并未结束抽取，无法下载");
            	}
            }
        }

    $(".channelBtn").click(function () {
        $("#projectNumber").val("");
        $("#proName").val("");
        window.location.href = "${pageContext.request.contextPath}/SupplierExtracts_new/projectList.html?typeclassId=${typeclassId}";
    })
    /* 重置 */
  function form_reset(){
    $("#form1").find("input").val("");
    var SelectArr = $("#form1").find("select");
    for (var i = 0; i < SelectArr.length; i++) {
      SelectArr[i].options[0].selected = true; 
    }
    $("#page").val("1");
  }
</script>