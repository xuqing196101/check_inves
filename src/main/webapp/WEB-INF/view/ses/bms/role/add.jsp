<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
  
  </head>
  <script type="text/javascript">
    $(function(){
        $("#save").click(function(){
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/role/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/role/list.html";
                        }, 1000);
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
                }
            });
            
        });
        $("#backups").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
        });
    });
  </script>
 <body>
    <div class="layui-layer-wrap" >
    	<form action="" id="form1" method="post">
          <div class="drop_window">
              <ul class="list-unstyled">
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                  <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 input_group input-append p0">
                   <input name="name" maxlength="30" class="title col-md-12" type="text">
                  </span>
                   
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>状态</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 select_common p0">
                   <select name="status" class="">
                        <option value="0">可用</option>
                        <option value="1">禁用</option>
                    </select>
                  </span>
                </li>
                <li class="col-md-12 col-sm-12 col-xs-12">
                  <label class="col-md-12 pl20 col-xs-12 padding-left-5">描述</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="description" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                </li>
                <div class="clear"></div>
             </ul>
             <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
                 <button class="btn btn-windows save" id="save" type="button">保存</button>
                <button class="btn btn-windows back" id="backups" type="button">返回</button>
              </div>
           </div>
        </form>  
    </div>         
 </body>
</html>
