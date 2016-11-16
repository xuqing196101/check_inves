<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
          <div class="drop_window">
              <ul class="list-unstyled">
                <li class="col-md-6">
                  <label class="col-md-12 padding-left-5">编号</label>
                  <span>
                   <input maxlength="11" id="planNo" name="planNo" type="text" class="col-md-12 p0">
                  </span>
                </li>
                
                <li class="col-md-6">
                  <label class="col-md-12 padding-left-5">交付时间</label>
                  <span class="col-md-12 p0">
                   <input maxlength="11" id="givetime" name="givetime" value="" type="text" class="col-md-12 p0">
                  </span>
                </li>
                <li class="col-md-6">
                  <label class="col-md-12 padding-left-5"><i class="red">*</i>名称</label>
                  <span class="col-md-12">
                   <input name="name" maxlength="30" class="title col-md-12" type="text">
                  </span>
                   
                </li>
                <li class="col-md-6">
                   <label class="col-md-12 padding-left-5"><i class="red">*</i>状态</label>
                  <span class="col-md-12">
                   <select name="status" class="w180 mb10">
                        <option value="0">可用</option>
                        <option value="1">禁用</option>
                    </select>
                  </span>
                </li>
                <li class="mt10 col-md-12 p0">
                  <label class="col-md-12 pl20">描述</label>
                   <span class="col-md-12">
                    <textarea class="text_area1 mt6" name="description" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                </li>
                <div class="clear"></div>
             </ul>
             <div class="tc mt10 col-md-12">
                 <button class="btn btn-windows save" id="save" type="button">保存</button>
                <button class="btn btn-windows back" id="backups" type="button">返回</button>
              </div>
           </div>  
    </div>         
 </body>
</html>
