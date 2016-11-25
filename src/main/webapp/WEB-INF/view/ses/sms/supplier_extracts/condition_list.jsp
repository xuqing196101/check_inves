<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>任务管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">



<script type="text/javascript">
  
  /*分页  */
	  $(function(){
          laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${list.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${list.total}",
                startRow: "${list.startRow}",
                endRow: "${list.endRow}",
                groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
                curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
                   
                    return "${list.pageNum}";
                }(), 
                jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
//                     	location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=${projectId}&page='+e.curr;
                    }
                }
            });
          
          $("#area1").empty();   
          //抽取地址回显    
           var extractAddress="${extractionSites}";
           if(extractAddress != null && extractAddress != ''){
               var extractAddressArray=extractAddress.split(","); 
               city1=extractAddressArray[1];
               <c:forEach items="${listArea}" var="item" varStatus="status" >  
               if("${item.name}" == extractAddressArray[0]){
                      $("#area1").append("<option selected='selected' value='${item.id}'>${item.name}</option>");
               }else{
               $("#area1").append("<option  value='${item.id}'>${item.name}</option>");
               }
               </c:forEach> 
           }else{
                  <c:forEach items="${listArea}" var="item" varStatus="status" >  
                  $("#area1").append("<option value='${item.id}'>${item.name}</option>");
                  </c:forEach>   
           } 
           
           var areas1=$("#area1 option:selected").val();
           $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
                data:{area:areas1},
                dataType:"json",
                success: function(data){
                     var list = data;
                     $("#city1").empty();
                     for(var i=0;i<list.length;i++){
                         if(list[i].name==city1){
                             $("#city1").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
                         }
                          $("#city1").append("<option  value="+list[i].id+">"+list[i].name+"</option>");
                     }
                }
            }); 
           
           //获取包id
           var projectId = "${projectId}";
           if (projectId != null && projectId != ''){
               $("#projectName").attr("readonly",true);
               $("#projectNumber").attr("readonly",true);
               $("#packageName").attr("readonly",true);
           }else{
        	   $("#projectName").attr("readonly",false);
               $("#projectNumber").attr("readonly",false);
               $("#packageName").attr("readonly",false);
           }
          
      });
  
  
	  //抽取地区
	    function areas1(){
	        var areas1=$("#area1").find("option:selected").val();
	        $.ajax({
	            type:"POST",
	            url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
	            data:{area:areas1},
	            dataType:"json",
	            success: function(data){
	                 var list = data;
	                 $("#city1").empty();
	                 for(var i=0;i<list.length;i++){
	                      $("#city1").append("<option value="+list[i].id+">"+list[i].name+"</option>");
	                 }
	            }
	        });
	      }
  
  
  
  
  
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
//     	var projectName = $("#projectName").val();
//     	var projectNumber = $("#projectNumber").val();
    	  $("#extAddress").val($("#area1 option:selected").text()+","+$("#city1 option:selected").text());
//     	  var projectId="${projectId}";
//     	  alert(projectId);
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/SupplierExtracts/validateAddExtraction.do',
            data:$('#form').serialize(),// 你的formid
            async: false,
            success: function(data) {
                $("#projectNameError").text("");
                $("#projectNumberError").text("");
                $("#packageNameError").text("");
                $("#dSupervise").text("");
                var map =data;
                $("#projectNameError").text(map.projectNameError);
                $("#projectNumberError").text(map.projectNumberError);
                $("#packageNameError").text(map.packageNameError);
                $("#dSupervise").text(map.supervise);
                if(map.status != null && map.status!=0){
                	 layer.alert("请全部抽取完之后在添加条件",{shade:0.01});
                }
                
                if(map.sccuess=="SCCUESS"){
                	var projectId=map.projectId;
                      window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId='+projectId+'&&typeclassId=${typeclassId}';
                }
            }
        });

    }
    
    function extract(id,btn){
    	  layer.open({
              type: 2, //page层
              area: ['90%', '50%'],
              title: '供应商抽取 项目名称： ${projectName}',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              shadeClose: true,
              offset: '30px',
              move:false,
              content: '${pageContext.request.contextPath}/SupplierExtracts/extractCondition.html?cId='+id,
              end:function(){
            	  window.location.reload();
              }
            });
    	  $(btn).next().remove();
    	 $(btn).parent().parent().find("td:eq(2)").html("抽取中");
    	  
    }
    function update(id){
    	  location.href = '${pageContext.request.contextPath}/SupplierCondition/showSupplierCondition.html?Id='+id+'&&typeclassId=${typeclassId}';
  }
    //选择监督人员
    function supervise(){
        //  iframe层
        var iframeWin;
            layer.open({
              type: 2,
              title:"选择监督人员",
              shadeClose: true,
              shade: 0.01,
              offset: '20px',
              move: false,
              area: ['90%', '50%'],
              content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
              success: function(layero, index){
                  iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
              btn: ['保存', '关闭'] 
                  ,yes: function(){
                      iframeWin.add();
                  
                  }
                  ,btn2: function(){
                    layer.closeAll();
                  }
            }); 
        }
    
    
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !='' }">
	<div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a></li>
                <li><a href="#">支撑环境系统</a></li>
                <li><a href="#">供应商管理</a></li>
                       <li><a href="#">供应商抽取</a></li>
                <li class="active"><a href="#">供应商抽取列表</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
	</c:if>
	
	<!-- 项目戳开始 -->
	<div class="container container_box">
	       <form id="form">
	        <!-- 抽取地区 -->
            <input type="hidden" name="extAddress" id="extAddress"  value="${extractionSites}">
            <!-- 监督人员 -->
            <input type="hidden" name="sids" id="sids" value="${userId}" />
            <!-- 打开类型 -->
	        <input type="hidden" value="${typeclassId}" name="typeclassId"/>
            <!-- 项目id  -->
	        <input type="hidden" id="projectId" value="${projectId}" name="id">
	           <div>
	            <h2 class="count_flow"><i>1</i>必填项</h2>
	           <ul class="ul_list">
	             <li class="col-md-4 col-sm-6 col-xs-12">
	               <span class="col-md-12 padding-left-5"><i class="red">*</i>抽取地区:</span>
	               <div class="input-append">
	                <select class="w133" id="area1" onchange="areas1();">
                            </select> <select name="extractionSites" class="w93" id="city1"></select>
	               </div>
	             </li>
	             <li class="col-md-4 margin-0 padding-0 ">
	               <span class="col-md-12 padding-left-5"><i class="red">*</i>监督人员:</span>
	               <div class="input-append">
	                   <input class="span5" readonly id="supervises"  title="${userName}"
                                value="${userName}" onclick="supervise();" type="text">
	                <span class="add-on">i</span>
	                <div class="cue" id="dSupervise"></div>
	               </div>
	             </li>
	             <li class="col-md-4 margin-0 padding-0 ">
                   <span class="col-md-12 padding-left-5"><i class="red">*</i>项目名称:</span>
                   <div class="input-append">
                    <input class="span5"  id="projectName"  name="name" value="${projectName}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="projectNameError"></div>
                   </div>
                 </li>
                 <li class="col-md-4 margin-0 padding-0 ">
                   <span class="col-md-12 padding-left-5"><i class="red">*</i>项目编号:</span>
                   <div class="input-append">
                    <input class="span5"  id="projectNumber" name="projectNumber" value="${projectNumber}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="projectNumberError"></div>
                   </div>
                 </li>
	               <li class="col-md-4 margin-0 padding-0 ">
                   <span class="col-md-12 padding-left-5"><i class="red">*</i>包名:</span>
                   <div class="input-append">
                    <input class="span5"   id="packageName" name="packageName" value="${packageName}" type="text">
                    <span class="add-on">i</span>
                     <div class="cue" id="packageNameError"></div>
                   </div>
                 </li>
	             <li class="col-md-4 margin-0 padding-0 ">
                   <span class="col-md-12 padding-left-5"><i class="red">*</i>采购方式:</span>
                   <div class="input-append">
                   <select class="w230" name="purchaseType">
                        <c:forEach items="${findByMap}" var="map">
                            <option value="${map.id}">${map.name}</option>
                        </c:forEach>
                   </select>
                   </div>
                 </li>
                  <div class="margin-bottom-5" >
                    <button class="btn btn-windows add" type="button" onclick="add();">添加条件</button>
                </div>
                 <table class="table table-bordered table-condensed ">
                <thead>
                    <tr>
                        <th class="info w50">序号</th>
                        <th class="info">抽取条件</th>
                        <th class="info">状态</th>
                        <th class="info">操作</th>
                    </tr>
                </thead>
                <c:forEach items="${list.list}" var="obj" varStatus="vs">
                    <tr >
                        <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                        <td class="w800">第【${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}】次抽取，供应商所在地区【${ obj.address}】
                        <c:forEach items="${obj.conTypes }" var="contypes">
                    
                         <c:choose>
                          <c:when test="${'1^2^' == contypes.supplieTypeId  }">
                                           ，  供应商类型【 生产型,销售型 】
                          </c:when>
                          <c:when test='${contypes.supplieTypeId == "1^"}'>
                                                         ，  供应商类型【生产型】
                          </c:when>
                          <c:when test='${contypes.supplieTypeId == "2^" }'>
                            ， 供应商类型【销售型】
                          </c:when>
                         </c:choose>
                          <c:if test="${contypes.categoryName!=null}">
                          <c:set var="re" value="${fn:substring(contypes.categoryName, 0, contypes.categoryName.length()-1)}"/>
                                                                               ， 采购类别【${fn:replace(re,'^',',')}】
                          </c:if>   
                          <c:if test="${contypes.supplieCount!=null }">
                                                                         ，供应商数量【${contypes.supplieCount }】
                        </c:if>  
                        </c:forEach>
                        </td>
                        <td class="tc w75" id="status">
                        <c:if test="${obj.status==1}">
                                                                           待抽取
                        </c:if> 
                        <c:if test="${obj.status==2}">
                                                             抽取中
                        </c:if>
                        <c:if test="${obj.status==3}">
                                                                             已抽取
                        </c:if>
                </td>
                        <td class="tc w100" align="center" >
                            <button
                                class="btn"
                                id="save" type="button" onclick="extract('${obj.id }',this);">抽取</button>
                                <c:if test="${obj.status==1 }">
                                        <button
                                class="btn margin-top-10"
                                id="save" type="button" onclick="update('${obj.id }');">修改</button>
                                </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="pagediv" align="right"></div>
	           </ul>
	          </div>
	      </form>
	 </div> 
	
    <!-- 项目戳开始 -->
<!--     <div class="container container_box"> -->
<!--                <div> -->
<!--                 <h2 class="count_flow"><i>2</i>条件列表</h2> -->
<!--                 <ul class="ul_list"> -->
               
<!-- 			</ul> -->
<!-- 		</div> -->
<!-- 		</div> -->




</body>
</html>
