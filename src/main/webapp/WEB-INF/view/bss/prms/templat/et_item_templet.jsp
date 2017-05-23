<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  <script type="text/javascript">
    //新增一个评审项
    function addItem(){
    	layer.open({
            type: 1,
            title: '添加评审项信息',
            area: ['270px', '260px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: '150px',
            shadeClose: false,
            content: $("#openDiv"),
          });
    	
    }
    
    //修改评审项
    function editItem(){
    	
    	
    }
    
    //删除评审项 
    function delItem(){
    	
    	
    }
    
  </script>
  </head>
<body>  
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="${pageContext.request.contextPath}/" target="_parent">首页</a></li>
                <li><a href="javascript:void(0)">支撑系统</a></li>
                <li><a href="javascript:void(0)">后台管理</a></li>
                <li class="active"><a href="javascript:void(0)">模版管理</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <div class="container">
        <div class="headline-v2">
            <h2>模板编辑</h2>
        </div>
       <div class="content table_box">
           <table class="table table-bordered table-condensed table-hover">
               <thead>
                  <tr>
                     <th class="info" colspan="2">评审名称 </th>
                     <th class="info">评审内容</th>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <td rowspan="3" class="w150">
	                                                                符合性审查
	                     <a href="javascript:void(0);" title="编辑"><img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png"></a>
                     </td>
                  </tr>
                  <tr>
                    <td>
                                                            投标文件签署、盖章0
                     <img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png">
                     <img src="${pageContext.request.contextPath}/public/backend/images/sc.png">
                    </td>
                    <td>投标文件签署、盖章1</td>
                  </tr>
                  <tr>
                     <td>投标文件签署、盖章2
                     <img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png">
                     <img src="${pageContext.request.contextPath}/public/backend/images/sc.png">
                     </td>
                     <td>投标文件签署、盖章3</td>
                  </tr>
                  <tr>
                     <td rowspan="3" class="w150">
                                                                资格性审查
                     <a href="javascript:void(0);" title="编辑"><img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png"></a>                                           
                     </td>
                  </tr>
                  <tr>
                     <td>投标文件签署、盖章4
                     <img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png">
                     <img src="${pageContext.request.contextPath}/public/backend/images/sc.png">
                     </td>
                     <td>投标文件签署、盖章5</td>
                  </tr>
                  <tr>
                     <td class="">投标文件签署、盖章6 
                     <a href="javascript:void(0);" title="编辑"><img src="${pageContext.request.contextPath}/public/backend/images/light_icon.png"></a>
                     <a href="javascript:void(0);" title="删除"><img src="${pageContext.request.contextPath}/public/backend/images/sc.png"></a>
                     </td>
                     <td class="">投标文件签署、盖章7</td>
                  </tr>
               </tbody>
           </table>
       </div>
       <div class="mt40 tc mb50">
           <button class="btn btn-windows save" onclick="saveItem();">保存</button>
           <button class="btn btn-windows back" onclick="goBack();">返回</button>
       </div>
    </div>
    <div id="openDiv" class="dnone layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="id" id="userId" >
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>输入新密码：</label> 
                    <div class="col-md-12 col-xs-12 col-sm-12 p0 input-append input_group">
                        <input type="password" name="password">
                    </div>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="star_red">*</div>确认新密码：</label> 
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                      <input type="password" name="password2">
                    </div>
                  </li>
              </ul>
              <div class="tc">
                <input class="btn" id="inputb" name="addr" onclick="resetPasswSubmit();" value="确定" type="button"> 
                <input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>