<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>  
<%@ include file="/WEB-INF/view/common.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ppms/categoryparam/cateParameter.js"></script>
  </head>
  <body>
  <form>
  	<input type="hidden" name="orgId" id="orgId" value="${orgId}">
  </form>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品管理</a></li><li><a href="#">产品参数管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-400">
	  <!-- left tree -->
    <div class="col-md-3">
	  <div class="tag-box tag-box-v3">
	    <div>
		  <ul id="ztree" class="ztree" style="height: 400px;" />
		</div>
	  </div>
	</div>
	   
	<!-- right -->
    <div class="tag-box tag-box-v4 col-md-9 col-xs-12 col-sm-9" >
   	  
   	  <div class="col-md-12 col-sm-12 col-xs-12">
   	    <div class="pull-left">
 		  <button class="btn btn-windows add" onclick="addParams();" type="button">新增</button>
 		  <button class="btn btn-windows edit" onclick="editParams();" type="button">编辑</button>
		  <button class="btn btn-windows delete" onclick="delParams();" type="submit">删除</button>
	    </div>
   	  </div>
   	  <div class=" col-md-12 col-sm-12 col-xs-12" id="name"></div>
	  <div class=" col-md-12 col-sm-12 col-xs-12" id="uListId" ></div>
	  <div class=" col-md-12 col-sm-12 col-xs-12" id="check"  >
	    
	</div>
	  <!-- <table class="table table-bordered mt10" id="uListId">
	  
	  </table> -->
	    <!-- <ul id="uListId" class="list-unstyled ul_table" >
	    </ul> -->
	  </div>
	  
	  <div id="submitId" class="textc">
	    <button class="btn btn-windows git" onclick="submitParams();" type="button">提交</button>
	  </div>
	 </div>
    </div>
     
     <!-- 添加编辑页面 -->
      <div id="openDiv" class="dnone layui-layer-wrap" >
        <div class="drop_window">
      	  <ul class="list-unstyled">
      	    <li class="col-md-6 col-sm-12 col-xs-12 pl15">
      		  <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">参数名称<span class="red">*</span></label>
      		    <div class="col-md-12 col-xs-12 col-sm-12 input_group input-append p0">
      		    	<input type="text" name="paramName" />
      		    </div>
      		</li>
			<li class="col-md-6 col-sm-12 col-xs-12">
			  <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">参数类型<span class="red">*</span></label>
			    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			      <select  name="paramTypeId">
				    <c:forEach items="${dictionary}" var="dict">
					  <option value="${dict.id}">${dict.name}</option>
					</c:forEach>
				  </select>
				</div>
			</li>
			<li class="col-md-6 col-sm-12 col-xs-12">
			  <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">是否必填<span class="red">*</span></label>
			    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			      
			      <select  name="paramRequired">
			          <option value="0">非必填</option>
					  <option value="1">必填</option>
				  </select>
				</div>
			</li>
      	</ul>
	    <div  class="tc mt20 col-md-12 col-sm-12 col-xs-12">
          <button class="btn btn-windows save"  onclick="saveParameter();" type="button">保存</button>
          <button class="btn btn-windows cancel" onclick="cancel();" type="button">取消</button>
      	</div>
      </div>
    </div>
  </body>
</html>
