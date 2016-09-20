<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
  <head>
   <base href="<%=basePath%>">

<title>采购机构管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>public/oms/css/consume.css"  rel="stylesheet">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
    <script type="text/javascript">
    	$(function(){
    			/**查询*/
    			$("#findbtn").click(function(){
    				$("#findform").submit();
    			});
    			$("#importTemplate").click(function(){
    				    //导入模板下载 suncj
    				    location.href="${pageContext.request.contextPath}/retailer/downloadStaffTemplace.do";
    			});
    			$("#addbtn").click(function(){
    				parent.showiframe("店员新增",940,600,"${pageContext.request.contextPath}/retailer/OpenRetailerStaffAdd.do?","-4");
    				//screen.availWidth,screen.availHeight-100
    				//openFrame("店员新增",screen.availWidth,screen.availHeight-100,'${pageContext.request.contextPath}/retailer/OpenRetailerStaffAdd.do?');
    			});
    			$("#editbtn").click(function(){
    				var selectids = getSelectIds();
    				if(selectids.length==0){
    					parent.showalert("请选择需要编辑的店员",0);
    				}else if(selectids.indexOf(",")==-1){
    					parent.showiframe("店员编辑",900,600,"${pageContext.request.contextPath}/retailer/OpenRetailerStaffEdit.do?id="+selectids,"-4");
    					//openFrame("店员编辑",screen.availWidth,screen.availHeight-100,"${pageContext.request.contextPath}/retailer/OpenRetailerStaffEdit.do?id="+selectids);
    				}else{
    					parent.showalert("只能选择一个进行编辑",0);
    				}
    				
    				
    			});
    			$("#delbtn").click(function(){
    				var selectids = getSelectIds();
    				if(selectids.length==0){
    					parent.showalert("请选择需要删除的店员",0);
    				}
    				else{
    					parent.layer.confirm('您确定要删除吗？',{icon:3,offset:295}, function(index){
    						$.post("${pageContext.request.contextPath}/retailer/retailerStaffDel.do?",
        	    					{selectids:selectids},		
        	    					function(data){
        	    						parent.showalert(data.message,1,"295");
        	    						location.reload();
        	    					},"json"); 
    					});
    					
    					//parent.showalert("只能选择一个删除",0);
    				}
    			});
    			$("#exportbtn").click(function(){
				    var province = $("#province").val();
				    var city = $("#city").val();
				    var country = $("#countryId").val();
				    var staffLoginName = $("#staffLoginName").val();
				    var staffName = $("#staffName").val();
				    staffLoginName = encodeURI(encodeURI(staffLoginName));
				    staffName = encodeURI(encodeURI(staffName))
				    var staffMobile = $("#staffMobile").val();
				    var staffCode = $("#staffCode").val();
				    location.href="${pageContext.request.contextPath}/retailer/exportRetailerStaff.do?province="+province+"&city="+city+"&country="+country+
				    		"&staffLoginName="+staffLoginName+"&staffName="+staffName+"&staffMobile="+staffMobile+"&staffCode="+staffCode;
				});
     		});
		function winOpen() {
			parent.showiframe("我要提问","380","280","${pageContext.request.contextPath}/sales/problemConsulting.do?");
  			//var theObj = document.getElementById('win').style;
  			//if (  theObj.display == "block" ) theObj.display = "none"; else theObj.display = "block";
		}
		function saveProblem(){
			/* $.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/sales/saveProblem.do",
				data:"problemType=2",
				complete:function(){
					alert(2);
					winClose();
			}}); */
			var problemType = $("#formProblemType").find("option:selected").text();
			var askContents = $("#askContents").val();
			//alert(askContents);
			if(checkProblem(problemType,askContents)){
				$.post("${pageContext.request.contextPath}/sales/saveProblem.do?",
				{problemType:problemType,askContents:askContents},		
				function(data){
					//console.dir(data);
					parent.showalert(data.message,1);
					if(data.success){
						winClose();
					}
					
				},"json"); 
			}else{
				parent.showalert("请选择问题类型或者填写咨询问题内容",5);
			}
			
		}
		function checkProblem(problemType,askContents){
			if(askContents==""||askContents==null||askContents=="undefined"){
				$("#askContents").focus();
				return false;
			}
			if(problemType==""||problemType==null||problemType=="undefined"){
				$("#problemType").focus();
				return false;
			}
			return true;
		}

		function pageOnLoad(){
			//alert("${pageContext.request.contextPath}");
			findProCityCountry("${pageContext.request.contextPath}");
		}
		function openFrame(titles,width,height,url){
			var i=layer.open({
		        type: 2,
		        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
		        shade:true,
		        maxmin: true,
		        shadeClose: true, //点击遮罩关闭层 
		        area : [width+"px" , height+"px"],
		        content: url
		    });
			layer.full(i);
		}
		function selectAll(){
			//var cked = $("#checkedAll").attr("checked");
			var cked =  document.getElementById("checkedAll").checked;;
			var ids = document.getElementsByName("selectedStaffId");
			for (var i = 0; i < ids.length; i++) {
				ids[i].checked = cked;
			}
		}
		function getSelectIds(){
			var allIds = document.getElementsByName('selectedStaffId');
			var selectedIds = "";
			for (var i = 0; i < allIds.length; i++) {
				if (allIds[i].checked == true) {
					selectedIds += allIds[i].value + ",";
				}
			}
			//alert(selectedIds.length)
			selectedIds = selectedIds.substring(0,selectedIds.length-1);
			//alert(selectedIds.substring(0,selectedIds.length-1));
			return selectedIds;
		}
		function isNull(ids){
			if(ids!=null&&ids.length>0){
				return false;
			}else{
				return true;
			}
		}

		
		
		//批量导入店员
		 function doImportStaff(){
			 var h = "260";
		     var w = "900";
		     var top = (screen.availHeight - h) / 2;
		     var left = (screen.availWidth - w) / 2;
		     var optstr = "height=" + h + ",width=" + w + ",left=" + left + ",top=" + top + ",status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes";
		     var strUrl = "${pageContext.request.contextPath}/retailer/doImportStaff.do";
		     var newsWin = window.open(strUrl, "editRptHead", optstr);
		     if (newsWin != null) {
		         newsWin.focus();
		     }
		}
		

    </script>
<style>
 .topFormArea p {margin: 2px;}
</style>
  </head>
  
  <body onload="pageOnLoad();">
   
<div class="rigCon">
<form id="findform" action="${pageContext.request.contextPath}/" method="post">
<div class="topFormArea">
	<p>
	</p>
	<br/>
	<div class="clearfloat"></div>
</div>
	
<div class="list" >
	<p class="marB5">
		   <a href="javascript:void(0);" id="addbtn" class="addIcon"></a>    <!--新增    -->
		   <a href="javascript:void(0);" id="editbtn" class="editIcon2"></a><!--编辑    -->
		   <a href="javascript:void(0);" id="delbtn" class="delIcon2"></a><!--删除    -->
		   <a href="javascript:void(0);" id="exportbtn" class="exportIcon"></a><!--导出    -->
		   <a href="javascript:doImportStaff();" id="BImportbtn" class="BImportIcon"></a> <!--批量导入    -->
		  <!--  <a href="javascript:void(0);" id="Transferbtn" class="TransferIcon"></a> -->
		   <!--<a href="javascript:void(0);" id="importTemplate" class="exportdownload"></a>导入下载模板    -->
	</p>
	<div style="overflow-x:scroll;width:862px;">
				<table cellpadding="0" cellspacing="0" border="0" id="table1" style="width:1605px;">
					<tr>
						<th><input type="checkbox" name="checkedAll" id="checkedAll" onclick="selectAll()" /></th>
						<th>采购机构名称</th>
						<th>邮编</th>
						<th>单位地址</th>
						<th>采购业务范围</th>
						<th>采购资质编号</th>
						<th>采购业务等级</th>
						<th>采购资质范围</th>
					</tr>
					<c:forEach items="${purchaseDepList}" var ="purchaseDep" varStatus="stuts">
					<tr <c:if test="${stuts.index % 2 != 0}">class="colorG"</c:if>>
						<td><input type="checkbox" name="selectedStaffId" value="${purchaseDep.id}" /></td>
						<td>${purchaseDep.name}</td>
						<td><span class="w10" title="${purchaseDep.name}">${purchaseDep.name}</span></td>
						<td><span class="w10" title="${purchaseDep.name}">${purchaseDep.name}</span></td>
						<td><span class="w10" title="${purchaseDep.name}">${purchaseDep.name}</span></td>
						<td>${purchaseDep.name}</td>
						<td><span class="w10" title="${purchaseDep.name}">${purchaseDep.name}</span></td>
						<td>${purchaseDep.name}</td>
					</tr>
					</c:forEach>
				</table>
				</div>
				<%-- <div class="page">${pagesql}</div> --%>
				<c:if test="${empty  purchaseDepList}">
					<div class="noData">
						<p>此条件下尚无数据，请重新选择！</p>
					</div>
				</c:if>
			  <p class="pagestyle">${pagesql}</p> 
<div class="clearfloat"></div>
</div>
<div class=" clearfloat"> </div>
</form>
	<%-- <div style="position:fixed;top: 200px; right: 10px;">
		<a onclick="winOpen();" style="width: 28px;height: 96px;display: inline-block;text-decoration: none;background: url('${pageContext.request.contextPath}/images/chatIcon.png');"></a>
	</div> --%>
</div>
  </body>
</html>



