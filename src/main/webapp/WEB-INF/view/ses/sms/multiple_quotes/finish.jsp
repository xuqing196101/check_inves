<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>标书管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			function OpenFile() {
				var obj = document.getElementById("TANGER_OCX");
				obj.Menubar = true;
				obj.Caption = "( 双击可放大 ! )";
			}

			function queryVersion() {
				var obj = document.getElementById("TANGER_OCX");
				var v = obj.GetProductVerString();
				obj.ShowTipMessage("当前ntko版本", v);
			}

			function inputTemplete() {
				var obj = document.getElementById("TANGER_OCX");
			}

			function saveFile() {
				var obj = document.getElementById("TANGER_OCX");
				var s = obj.GetBookmarkValue("书签");
				alert(s);
			}

			function closeFile() {
				var obj = document.getElementById("TANGER_OCX");
				obj.close();
			}

			//标记
			function mark() {
				var obj = document.getElementById("TANGER_OCX");
				obj.ActiveDocument.BookMarks.Add("标记");
			}

			//获取标记内容并且定位
			function searchMark() {
				var obj = document.getElementById("TANGER_OCX");
				//判断标记是否存在
				if(obj.ActiveDocument.Bookmarks.Exists("标记")) {}
				alert(obj.GetBookmarkValue("标记"));
				//alert(obj.ActiveDocument.GetCurPageStart());
				//定位到书签内容
				obj.ActiveDocument.Bookmarks.Item("标记").Select();
				//alert(obj.ActiveDocument.GetPagesCount());
			}

			//删除标记
			function delMark() {
				var obj = document.getElementById("TANGER_OCX");
				obj.ActiveDocument.BookMarks.Item("标记").Delete();
			}
		</script>

		<!-- 打开文档后只读 -->
		<!-- <script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		obj.SetReadOnly(true);
</script> -->
	</head>

	<body onload="OpenFile()">
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">我的项目</a>
					</li>
					<li>
						<a href="javascript:void(0);">标书管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container clear mt20">
			<div class="list-unstyled padding-10 breadcrumbs-v3">
				<span>
			  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
			  <span class="green_link">→</span>
				</span>
				<span>
			  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
			  <span class="">→</span>
				</span>
				<span>
			  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${projectId}" class="img-v1">填写报价</a>
			  <span class="">→</span>
				</span>
				<span>
			  <a href="#" class="img-v2 orange_link">完成</a>
			</span>
			</div>
		</div>
		<div class="container content height-350 pt0">
			<span>投标完成</span>
		</div>
	</body>

</html>