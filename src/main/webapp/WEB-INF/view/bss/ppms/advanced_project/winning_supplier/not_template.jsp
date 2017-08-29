<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
       
				//导入模板
				function inputTemplete(){
				  var iframeWin;
				    layer.open({
				      type: 2, //page层
				      area: ['80%', '50%'],
				      title: '模版管理',
				      closeBtn: 1,
				      shade:0.01, //遮罩透明度
				      shift: 1, //0-6的动画形式，-1不开启
				      shadeClose: false,
				      content: '${pageContext.request.contextPath}/resultAnnouncement/getAll.html',
				      success: function(layero, index){
				        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				      },
				    });
				}
        //导出
        function outputAnnouncement(){
            alert("导出");
            $("#form").attr("action",'${pageContext.request.contextPath}/resultAnnouncement/outputResultAnnouncement.do');   
            $("#form").submit();
        }
        //预览
        function preview(){
             alert("预览");
             $("#form").attr("action",'${pageContext.request.contextPath}/resultAnnouncement/preViewResultAnnouncement.do');   
             $("#form").submit();
        }
        //发布
        function publish(){
           $("#form").attr("action",'${pageContext.request.contextPath}/winningSupplier/publish.do');   
           $("#form").submit();
        }
        //保存
        function save(){
            alert("保存");
            $("#form").attr("action",'${pageContext.request.contextPath}/resultAnnouncement/saveResultAnnouncement.do');   
            $("#form").submit();
        }
    </script>
    <script type="text/javascript">
    
    	  function  change(select){
    		   var vl= select.value;
    		   var array=vl.split("^");
    		   $("#bidid").text(array[1]);  
       }
    	  /** 中标供应商 */
          function tabone(){
            window.location.href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}";
          }
          
          /** 中标通知 */
          function tabtwo(){
            var error = "${error}";
            if(error != null && error == "ERROR"){
              layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
            }else{
              window.location.href="${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}";
            }
            
            
          }
          
          /** 未中标通知 */
          function tabthree(){
            var error = "${error}";
            if (error != null && error == "ERROR" ){
              layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
            } else{
                window.location.href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}";  
            }
          }
</script>
    
    
</head>

<body>
	<div class="container content height-350">
		<div class="row">
			<div class="col-md-12" style="min-height: 400px;">
				<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
					<h2 class="padding-10 border1">
						<form action="" method="post"  id="form" class="mb0">
						<input type="hidden" value="${projectId}" name="projectId">
						  <input type="hidden" value="0" name="isWon">
					 <ul class="demand_list">
                <li class="fl"><label class="fl">供应商名称：</label><span>
                    <select class="w200" name="supplierId" id="supplier" onchange="change(this);">
                        <c:forEach items="${listSupplierCheckPass}" var="pass">
                          <option value="${pass.supplier.id}^${pass.packageId }">${pass.supplier.supplierName}</option>
                        </c:forEach>
                  </select>
                  </span>
                </li>
                  <li class="fl"><label class="fl">中标包名：</label>
                  <span id="bidid">
                     ${listSupplierCheckPass[0].packageId}
                  </span>
                </li>
              </ul>
							<div class="clear"></div>
			
						<div class="row">
							<!-- 按钮 -->
							<div class="col-md-12">
								<input type="button" class="btn btn-windows input"
									onclick="inputTemplete()" value="模板导入"></input> <input
									type="button" class="btn btn-windows output"
									onclick="outputAnnouncement()" value="导出"></input> <input
									type="button" class="btn btn-windows git" onclick="preview()"
									value="预览"></input> 
							</div>
							<!-- 文本编辑器 -->
							<div class="col-md-12">
								<script id="editor" name="content" type="text/plain"
									class="ml125 mt20 w900"></script>
							</div>

							<div class="tc mt20 clear col-md-12">

								  <input type="button" class="btn btn-windows save"
                  onclick="publish()" value="发布"></input> 
								<input type="button" class="btn btn-windows back"
									onclick="history.go(-1)" value="返回"></input>
							</div>
						</div>
					</form>
    </h2>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
    var ue = UE.getEditor('editor'); 
    ue.ready(function(){
        //需要ready后执行，否则可能报错
        ue.setHeight(500);
    })
    </script>
</body>
</html>



