<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>

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
              area: ['800px', '500px'],
              title: '配置权限',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              shift: 1, //0-6的动画形式，-1不开启
              offset: ['180px', '550px'],
              shadeClose: false,
              content: 'resultAnnouncement/getAll.html',
              success: function(layero, index){
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
              },
              btn: ['引用', '关闭'] 
              ,yes: function(index, layero){
            	   var id=[]; 
            	  var $a=$(layero);
                   $(a).find('input[name="chkItem"]:checked').each(function(){ 
                       id.push($(this).val());
                   }); 
            	  alert(id);
                  if(id.length==1){
                      window.location.href="${pageContext.request.contextPath}/templet/edit.do?id="+id;
                  }else if(id.length>1){
                      layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
                  }else{
                      layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
                  }
              }
              ,btn2: function(){
                layer.closeAll();
              }
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
            alert("发布");
        }
        //保存
        function save(){
            alert("保存");
            $("#form").attr("action",'${pageContext.request.contextPath}/resultAnnouncement/saveResultAnnouncement.do');   
            $("#form").submit();
        }
    </script>
    <script type="text/javascript">
	$(function(){
	     var pack="${packageName[0].id}";
	     change(pack);  
	})

    function supplierch(){
         var pack=$("#package").find("option:selected").val();
         change(pack);
    }

    function change(pack){
              $.ajax({
                  type:"POST",
                  url:"${pageContext.request.contextPath}/winningSupplier/getSupplierJosn.do",
                  data:{packageId:pack},
                  dataType:"json",
                  success: function(data){
                       var list = data;
                       $("#supplier").empty();
                       for(var i=0;i<list.length;i++){
                            $("#supplier").append("<option value="+list[i].id+">"+list[i].supplier.supplierName+"</option>");
                       }
                  }
              });
    }
</script>
    
    
</head>

<body>
	      <div class="col-md-12 p0">
                           <ul class="flow_step">
                             <li >
                               <a  href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}" >01、确认中标供应商</a>
                               <i></i>
                             </li>
                             <li >
                               <a  href="${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}" >02、中标通知书</a>
                               <i></i>                            
                             </li>
                              <li class="active">
                               <a  href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}">03、未中标通知书</a>
                             </li>
                           </ul>
                         </div>
	<div class="container content height-350">
		<div class="row">
			<div class="col-md-12" style="min-height: 400px;">
				<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
					<h2 class="padding-10 border1">
						<form action="" method="post" class="mb0">
							<ul class="demand_list">
                                <li class="fl"><label class="fl">包：</label><span> <select
                                        id="package" class="w100 " onchange="supplierch();">
                                            <c:forEach items="${packageName}" var="pack">
                                                <option value="${pack.id}">${pack.name}</option>
                                            </c:forEach>
                                    </select>
                                </span></li>

                                <li class="fl"><label class="fl">供应商名称：</label><span>
                                        <select class="w200" id="supplier">

                                    </select>
                                </span></li>
                            </ul>
							<div class="clear"></div>
						</form>
					</h2>

					<form method="post" id="form">
						<div class="row">
							<!-- 按钮 -->
							<div class="col-md-12">
								<input type="button" class="btn btn-windows input"
									onclick="inputTemplete()" value="模板导入"></input> <input
									type="button" class="btn btn-windows output"
									onclick="outputAnnouncement()" value="导出"></input> <input
									type="button" class="btn btn-windows git" onclick="preview()"
									value="预览"></input> <input type="button"
									class="btn btn-windows apply" onclick="publish()" value="发布"></input>
							</div>
							<!-- 文本编辑器 -->
							<div class="col-md-12">
								<script id="editor" name="content" type="text/plain"
									class="ml125 mt20 w900"></script>
							</div>

							<div class="tc mt20 clear col-md-12">

								<input type="button" class="btn btn-windows save"
									onclick="save()" value="保存"></input>
								<input type="button" class="btn btn-windows back"
									onclick="history.go(-1)" value="返回"></input>
							</div>
						</div>
					</form>

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



