<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>任务管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">




<link href="<%=basePath%>public/ZHH/css/common.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css"
	media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css"
	media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css"
	media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>


<script type="text/javascript">
  
  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
            skip: true, //是否开启跳页
            groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//                  var page = location.search.match(/page=(\d+)/);
//                  return page ? page[1] : 1;
                return "${info.pageNum}";
            }(), 
            jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                //  $("#page").val(e.curr);
                    // $("#form1").submit();
                    
                 location.href = '<%=basePath%>project/list.do?page='+e.curr;
                }  
            }
        });
  });
  
  
    /** 全选全不选 */
    function selectAll(){
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
               for(var i=0;i<checklist.length;i++)
               {
                  checklist[i].checked = true;
               } 
             }else{
              for(var j=0;j<checklist.length;j++)
              {
                 checklist[j].checked = false;
              }
            }
        }
    
    /** 单选 */
    function check(){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                 }
           }
    }
    
    function add(){
        layer.open({
          type: 2, //page层
          area: ['1000px', '500px'],
          title: '添加抽取条件',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['120px', '550px'],
          shadeClose: false,
          content: '<%=basePath%>ExpExtract/addExtraction.html?projectId=${projectId}'
        });
    }
    
    function extract(id){
    	  layer.open({
              type: 2, //page层
              area: ['1000px', '500px'],
              title: '添加抽取条件',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              moveType: 1, //拖拽风格，0是默认，1是传统拖动
              shift: 1, //0-6的动画形式，-1不开启
              offset: ['120px', '550px'],
              shadeClose: false,
              content: '<%=basePath%>ExpExtract/extractCondition.html?cId='+id
            });
    	
    }
    
    function update(id){
    	  layer.open({
              type: 2, //page层
              area: ['1000px', '500px'],
              title: '修改抽取条件',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              moveType: 1, //拖拽风格，0是默认，1是传统拖动
              shift: 1, //0-6的动画形式，-1不开启
              offset: ['120px', '550px'],
              shadeClose: false,
              content: '<%=basePath%>ExtCondition/showExtCondition.html?Id='+id
            });
  }
    

    
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
				<li><a href="#">采购项目管理</a></li>
				<li class="active"><a href="#">立项管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 录入采购计划开始-->
	<div class="container">
		<!-- 项目戳开始 -->
		<form id="add_form" action="<%=basePath%>project/list.html"
			method="post">
	


		</form>
		<div class="clear"></div>


		<span class="fr option_btn margin-top-10">

			<button class="btn padding-left-10 padding-right-10 btn_back"
				onclick="add();">添加抽取条件</button>
		</span>
		<div class="container clear margin-top-30">
			<table class="table table-bordered table-condensed mt5">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">抽取条件</th>
						<th class="info">状态</th>
						<th class="info">操作</th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="obj" varStatus="vs">
					<tr >
						<td class="tc w50">${vs.index+1}</td>
						<td class="tc">需抽取人员
						<c:forEach items="${obj.conTypes }" var="contypes">
						        ${contypes.expertsCount}
						</c:forEach>
						</td>
						<td class="tc"><c:if test="${obj.status==1}">
                                                    待抽取
                      </c:if> <c:if test="${obj.status==2}">
		                                                     已抽取
		                </c:if>
		                
                </td>
						<td class="tc w150" >
							<button
								class="btn padding-left-10 padding-right-10 btn_back fl margin-top-5 margin-right-10"
								id="save" type="button" onclick="extract('${obj.id }');">抽取</button>
								        <button
                                class="btn padding-left-10 padding-right-10 btn_back fl margin-top-5"
                                id="save" type="button" onclick="update('${obj.id }');">修改</button>
						</td>
					</tr>
				</c:forEach>
			</table>

			<div id="pagediv" align="right"></div>
		</div>
	</div>


	<div id="content" class="div_show">
		<p align="center" class="type">
			请选择类别 <br> <input type="radio" name="goods" value="1">:物资<br>
			<input type="radio" name="goods" value="2">:工程<br> <input
				type="radio" name="goods" value="3">:服务<br>
		</p>
		<button class="btn padding-left-10 padding-right-10 btn_back goods"
			onclick="closeLayer()">确定</button>

	</div>

</body>
</html>
