<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			/*分页  */
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${info.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					total: "${info.total}",
					startRow: "${info.startRow}",
					endRow: "${info.endRow}",
					skip: true, //是否开启跳页
					groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						//			        var page = location.search.match(/page=(\d+)/);
						//			        return page ? page[1] : 1;
						return "${info.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#add_form").submit();

						}
					}
				});
			});

			/** 全选全不选 */
			function selectAll() {
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				if(checkAll.checked) {
					for(var i = 0; i < checklist.length; i++) {
						checklist[i].checked = true;
					}
				} else {
					for(var j = 0; j < checklist.length; j++) {
						checklist[j].checked = false;
					}
				}
			}

			/** 单选 */
			function check() {
				var count = 0;
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				for(var i = 0; i < checklist.length; i++) {
					if(checklist[i].checked == false) {
						checkAll.checked = false;
						break;
					}
					for(var j = 0; j < checklist.length; j++) {
						if(checklist[j].checked == true) {
							checkAll.checked = true;
							count++;
						}
					}
				}
			}

			function view(no) {

				window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + no + "&&type=1";
			}

			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {

					window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + id + "&&type=2";;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的版块", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			function del() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['222px', '360px'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							url: "${pageContext.request.contextPath}/purchaser/delete.html",
							type: "post",
							data: {
								planNo: $('input[name="chkItem"]:checked').val()
							},
							success: function() {
								window.location.reload();

							},
							error: function() {

							}
						});
					});
				} else {
					layer.alert("请选择要删除的版块", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}
			var index;

			function collect() {
				var no=generateMixed();
				$("#cno").val(no);
				var flag = true;
				var ceck = $('input[name="chkItem"]:checked:first').prev().val();

				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					var type = $(this).prev().val();
					if(ceck != type) {
						flag = false;
					}
					id.push($(this).val());
				});
				if(flag == false) {
					layer.alert("物资类别需要一样", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(id.length >= 1) {

					index = layer.open({
						type: 1, //page层
						area: ['500px', '300px'],
						title: '生成采购计划',
						closeBtn: 1,
						shade: 0.01, //遮罩透明度
						moveType: 1, //拖拽风格，0是默认，1是传统拖动
						shift: 1, //0-6的动画形式，-1不开启
						offset: ['80px', '600px'],
						content: $('#content'),
					});
				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}

			}

			//鼠标移动显示全部内容
			function out(content) {
				if(content.length > 10) {
					layer.msg(content, {
						icon: 6,
						shade: false,
						area: ['600px'],
						time: 1000 //默认消息框不关闭
					}); //去掉msg图标
				} else {
					layer.closeAll(); //关闭消息框
				}
			}
			
			function loadplan() {
				index = layer.open({
					type: 1, //page层
					area: ['400px', '300px'],
					title: '导入需求计划',
					closeBtn: 1,
					shade: 0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['80px', '400px'],
					content: $('#file_div'),
				});
			}

			function closeLayer() {

				var id = [];
				var de = [];
				var type = "";

				$('input[name="chkItem"]:checked').each(function() {
					type = $(this).prev().val();
					var dep = $(this).next().val();
					de.push(dep);

					id.push($(this).val());
				});
				$("#goodsType").val(type);
				$("#plannos").val(id);
				$("#dep").val(de);
				$("#collect_form").submit();

				layer.close(index);
			}

			function cancels() {
				layer.close(index);
			}
			var ids = [];

			function collected() {
				var flag = true;
				var ceck = $('input[name="chkItem"]:checked:first').prev().val();
				var goodsType = "";
				$('input[name="chkItem"]:checked').each(function() {
					goodsType = $(this).prev().val();
					if(ceck != goodsType) {
						flag = false;
					}
					ids.push($(this).val());
				});

				if(flag == false) {
					layer.alert("物资类别需要一样", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else if(ids.length >= 1) {
					layer.open({
						type: 2, //page层
						area: ['900px', '400px'],
						title: '汇入采购计划',
						closeBtn: 1,
						shade: 0.01, //遮罩透明度
						moveType: 0, //拖拽风格，0是默认，1是传统拖动
						shift: 1, //0-6的动画形式，-1不开启
						offset: ['100px', '600px'],
						content: '${pageContext.request.contextPath}/collect/collectlist.html?type=' + goodsType,
					});

				} else {
					layer.alert("请选中一条", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}

			}

			function advanced() {
				var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1){
          window.location.href = "${pageContext.request.contextPath}/advancedProject/add.html?id="+id;
        } else {
          layer.alert("请选择计划", {
            shade: 0.01
          });
        }
			}
			
			//重置
			function resetQuery(){
				$("#planName").val("");
				$("#planNo").val("");
				var status = document.getElementById("status").options;
				status[0].selected = true;
			}
			
			function generateMixed() {
				var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
			     var res = "";
			     for(var i = 0; i < 6 ; i ++) {
			         var id = Math.ceil(Math.random()*35);
			         res += chars[id];
			     }
			     return res;
			}
			
			
 
			function fileup(){
				$("#up_form").submit();
			} 
 
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购需求汇总</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 录入采购计划开始-->
		<div class="container">
			<div class="headline-v2 fl">
				<h2>需求计划列表</h2>
			</div>

			<h2 class="search_detail">
    		<form id="add_form" class="mb0" action="${pageContext.request.contextPath }/collect/list.html" method="post" >
	   			<ul class="demand_list">
			    	  <li>
					    	<label class="fl">需求计划名称：</label>
					    	<span>
					    		<input type="hidden" name="page" id="page">
					  	 		<input type="text" name="planName" id="planName" value="${inf.planName }"/>
					    	</span>
				      </li>
				   		<li>
				    	<label class="fl">需求计划编号：</label>
				    		<span>
				  	  		<input type="text" name="planNo" id="planNo" value="${inf.planNo }"/>
				    		</span>
				    	</li>
				      <li>
				    	<label class="fl">状态：</label>
				    		<span>
				    	 		<select name="status" id="status">
										<option value=""> 请选择</option>
								   	<option value="1" <c:if test="${inf.status=='1'}"> selected</c:if> >已编制为采购计划</option>
								   	<option value="2" <c:if test="${inf.status=='2'}"> selected</c:if> >已提交</option>
								   	<option value="3" <c:if test="${inf.status=='3'}"> selected</c:if> >受理退回</option>
								   	<option value="4" <c:if test="${inf.status=='4'}"> selected</c:if> >已受理</option>
								   	<option value="5" <c:if test="${inf.status=='5'}"> selected</c:if> >已汇总</option>
								   	<option value="6" <c:if test="${inf.status=='6'}"> selected</c:if> >审核通过</option>
						   	   	<option value="6" <c:if test="${inf.status=='7'}"> selected</c:if> >审核暂存</option>
			 	   	   		</select>
				    		</span>
				      </li>
			    	</ul>
			    	<button type="submit" class="btn fl">查询</button>
	 				<button type="button" onclick="resetQuery()" class="btn fl">重置</button>
			    	<div class="clear"></div>
   
   </form>
  	</h2>
   
   <div class="col-md-12 col-xs-12 col-sm-12 pl20 mt10">
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="collect()">汇总</button>
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="collected()">添加至已有计划中</button>
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="advanced()">预研项目</button>
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="loadplan()">导入采购计划</button>
	 </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped ">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">需求部门</th>
		  <th class="info">需求计划名称</th>
		  <!-- <th class="info">编报人</th> -->
		  <th class="info">物资类别</th> 
		  <th class="info">提交日期</th>
		  <th class="info">预算总金额（万元）</th>
		  <th class="info">状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30">
			  <input type="hidden" value="${obj.planType }"> 
			
			 <c:if test="${obj.status==3 }">
              <input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt="">
              </c:if>
               <c:if test="${obj.status=='4 ' }">
              <input type="checkbox" disabled="disabled"  value="${obj.planNo }" name="chkItem" onclick="check()"  alt="">
              </c:if>
			   <input type="hidden"  value="${obj.department }">
			  </td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  <td class="tl pl20">
			    <c:forEach items="${requires }" var="re" >
					  <c:if test="${obj.department==re.id }"> ${re.name }</c:if>
			  	</c:forEach>
			 	</td>
			    
			    
			  <td class="tl pl20"  >${obj.planName }</td>
			
			  <td class="tl pl20"  >
			   <c:forEach items="${dic }" var="dic">
				   <c:if test="${obj.planType==dic.id}">
				   ${dic.name }
				   </c:if>
			   </c:forEach>
			  
			  </td>
			  <td class="tc"  ><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tr pr20"><fmt:formatNumber>${obj.budget }</fmt:formatNumber> </td>
			  <td class="tc"  >
	<%-- 		 <c:if test="${obj.status=='1' }">
			 	 已编制为采购计划
			  </c:if>
			  
			     <c:if test="${obj.status=='2' }">
			 	已提交
			  </c:if> --%>
			  <c:if test="${obj.status=='3' }">
			 		未汇总
			  </c:if>
			    <c:if test="${obj.status=='4' }">
				已汇总
			  </c:if>
				<%--    <c:if test="${obj.status=='5' }">
			 	已汇总
			  </c:if>
		   <c:if test="${obj.status=='6' }">
			 	审核通过
			  </c:if>
			   <c:if test="${obj.status=='7' }">
			 	审核暂存
			  </c:if> --%>
			  
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>


 <div id="content" class="dnone layui-layer-wrap">
	 
	<form id="collect_form" action="${pageContext.request.contextPath }/collect/add.html" method="post">
	<div class="drop_window">
	<ul class="list-unstyled">
	   
                 <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">计划名称</span>
                   <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                        <input class="title col-md-12" name="fileName"   type="text">
                   </div>
                 </li>
                 <li class="mt10 col-md-12 col-sm-12 col-xs-12">
                  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">计划编号</span>
                  <div class="col-md-12 col-xs-12 col-sm-12 input-append input_group p0">
                        <input class="col-xs-12 h80 mt6" name="cno" id="cno"  maxlength="300" type="text">
                    </div>
                 </li>
                 <!-- <li class="col-sm-6 col-md-6 p0 col-lg-6 col-xs-6">
                  <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">密码</label>
                    <span class="col-md-12 col-xs-12">
                        <input class="title col-md-12" name="password" maxlength="200" type="password">
                    </span>
						</li> -->
						<div class="clear"></div>
					</ul>
				</div>
				<div class="tc mt10 col-md-12 col-xs-12 col-dm-12">
					<input type="hidden" name="planNo" id="plannos" value="">
					<input type="hidden" name="department" id="dep" value="">
					<input type="hidden" name="goodsType" id="goodsType" value="">
					<button class="btn padding-left-10 padding-right-10 btn_back" onclick="closeLayer()">生成采购计划</button>
				</div>

			</form>
		</div>
		<div  class="container clear margin-top-30" id="file_div"  style="display:none;" >
    	<form id="up_form" action="${pageContext.request.contextPath}/collect/upload.do" method="post" enctype="multipart/form-data">
    		<input type="file" class="input_group" name="file">
    		 <input type="button" class="btn" onclick="fileup()"   value="导入" />
    	</form>
    </div>
	</body>

</html>