<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
			/* $(function (){
							var  pStatus = "${pStatus}";
							if(pStatus == 'ZBWJYTJ'){
								 //采购部门意见
							    $("#pcReason").removeAttr("readonly");
							    //事业部门意见
							    $("#causereason").removeAttr("readonly");
							    //财务部门意见
							    $("#financereason").removeAttr("readonly");
							    //最终意见
							    $("#finalreason").removeAttr("readonly");
							}
						}); */

			function initLength() {
				/* var txtLeng = $('#pcReason').val().length;
				$('#into1').text(2000 - txtLeng);
				$('#in1').text(txtLeng);
				txtLeng = $('#causereason').val().length;
				$('#into2').text(2000 - txtLeng);
				$('#in2').text(txtLeng);
				txtLeng = $('#financereason').val().length;
				$('#into3').text(2000 - txtLeng);
				$('#in3').text(txtLeng); */
				var txtLeng = $('#finalreason').val().length;
				$('#into4').text(2000 - txtLeng);
				$('#in4').text(txtLeng);
			}

			function lengthStr(id, obj) {
				var txtLeng = $(obj).val().length;
				if(txtLeng >= 2000) {
					$('#into' + id).text('0');
					$('#in' + id).text('2000');
				} else {
					$('#into' + id).text(2000 - txtLeng);
					$('#in' + id).text(txtLeng);
				}
			}
			$(function() {
				//获取查看或操作权限
				var isOperate = $('#isOperate', window.parent.document).val();
				if(isOperate == 0) {
					//只具有查看权限，隐藏操作按钮
					$(":button").each(function() {
						$(this).hide();
					});
				}
				initLength();
			})

			function showPhotoTo(bid, tid, key, id) {

				var params = {
					businessId: bid,
					typeId: tid,
					key: key
				};
				$.ajax({
					url: globalPath + '/file/displayFile.do',
					data: params,
					async: false,
					dataType: 'json',
					success: function(datas) {
						if(datas.length == 0) {
							layer.msg("请先上传审核意见!");
							return false;
						}
						var picture = new Object();
						picture.bid = bid;
						picture.tid = tid;
						picture.key = key;
						picture.id = id;
						window.sessionStorage.setItem(id, JSON.stringify(picture));
						window.sessionStorage.setItem("id", id);
						window.open(globalPath + "/openPic.jsp");
					}
				});

			}

			function download(bid, typeId, key, id) {
				var zipFileName = null;
				var fileName = null;
				var isExit = false;
				var params = {
					businessId: bid,
					typeId: typeId,
					key: key
				};
				$.ajax({
					url: globalPath + '/file/displayFile.do',
					data: params,
					async: false,
					dataType: 'json',
					success: function(datas) {
						if(datas.length == 0) {
							layer.msg("请先上传审核意见!");
							isExit = true;
						}
						isExit = false;
					}
				});
				if(isExit) {
					return false;
				}
				$.ajax({
					url: "${pageContext.request.contextPath }/open_bidding/downloadList.html",
					type: "post",
					data: {
						id: bid,
						typeId: typeId,
						key: key
					},
					dataType: "json",
					success: function(data) {

						location.href = "${pageContext.request.contextPath }/file/download.html?id=" + data + "&key=" + key + "&zipFileName=" + zipFileName + "&fileName=" + fileName;
					}
				});

			}

			/* function removeFile(bid,typeId,key,id){
				if(confirm("确定要删除意见吗？"))
				 {
				$.ajax({
			        url: "${pageContext.request.contextPath }/open_bidding/removeFile.html",
			        type: "post",
			        data: {
			            id:bid ,
			            typeId:typeId,
			            key:key
			        },
			        dataType:"json",
			        success: function(data) {
			        location.href="${pageContext.request.contextPath }/file/delfile.html?id="+ data +"&key="+key+"&pId="+"${pId}";//为能重定向回原页面 
			        }
			    }); 
			}
			} */

			function OpenFile(fileId) {
				setTimeout(open_file(fileId), 5000);
			}

			function open_file(fileId) {
				var obj = document.getElementById("TANGER_OCX");
				obj.Menubar = true;
				obj.Caption = "( 双击可放大 ! )";
				obj.FileSave = false; 
				if(fileId != '0') {
					obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId=" + fileId, true, false, 'word.document'); // 异步加载, 服务器文件路径
				} else {
					var filePath = "${filePath}";
					if(filePath != null && filePath != undefined && filePath != "") {
						obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + filePath, true, false, 'word.document'); // 异步加载, 服务器文件路径
					}
				}
			}

			function exportWord() {
				var obj = document.getElementById("TANGER_OCX");
				// 参数说明
				// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
				//obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
			}

			function queryVersion() {

				var obj = document.getElementById("TANGER_OCX");
				var v = obj.GetProductVerString();
				obj.ShowTipMessage("当前ntko版本", v);
			}

			function inputTemplete() {
				var obj = document.getElementById("TANGER_OCX");
			}

			function saveFile(flag) {
				var projectId = $("#projectId").val();
				var flowDefineId = $("#flowDefineId").val();
				var projectName = $("#projectName").val();
				var obj = document.getElementById("TANGER_OCX");
				//提交
				if(flag == "1"||flag == "5") {
					//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
					obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
					if(flag == "5"){
					  alert("采购文件已存档");
					}else{
					  alert("采购文件已提交");
					}
					$("#handle").attr("class", "dnone");
					$("#audit_file_add").attr("class", "dnone");
					$("#audit_file_view").removeAttr("class", "dnone");
					$("#cgdiv").addClass("dnone");
				}

				//暂存
				if(flag == "0") {
					//参数说明
					//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
					obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&flag=" + flag, "ntko", "", projectName + "_采购文件.doc", "MyFile");
					//obj.ShowTipMessage("提示","采购文件已上传至服务器");
					alert("采购文件已暂存");
				}
			}

			function closeFile() {
				var obj = document.getElementById("TANGER_OCX");
				obj.close();
			}

			/**
			通过|退回
			*/
			function updateAudit(status) {
				var text = $("#ii_disFileId").find("a");
				var aps = $("#ff1_disFileId").find("a");
				if(text.length<=0){
					layer.alert("请先上传最终意见附件");
					return false;
				}
				if(aps.length<=0){
					layer.alert("请先上传审批单");
					return false;
				}
				if($.trim($("#finalreason").val()) =="") {
					layer.alert("最终意见不能为空");
					return false;
				}
				if(status == 2) {
					ajax(status);
				} else if(status == 3 || status == 4) {
					ajax(status);
				}
			}

			function ajax(status) {
				var projectId = $("#projectId").val();
				var flowDefineId = $("#flowDefineId").val();
				var process = "${process}";
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath}/Auditbidding/updateAuditStatus.do?projectId=" + projectId + "&flowDefineId=" + flowDefineId + "&status=" + status,
					dataType: 'json',
					data:$("#form1").serialize(),
					success: function(result) {
						if(result == 'SUCCESS') {
							if(process != null && process == 1) {
								window.location.href = "${pageContext.request.contextPath}/Auditbidding/list.html";
							}
							$("#cgspan").addClass("dnone");
							$("#cgdiv").addClass("dnone");
						}
					},
					error: function(result) {
						layer.msg("失败", {
							offset: '222px'
						});
					}
				});
			}

			function jump(url) {
				$("#open_bidding_main").load(url);
			}

			function confirmOk(obj, id, flowDefineId) {
				layer.confirm('您已经确认了吗?', {
					title: '提示',
					offset: ['100px'],
					shade: 0.01
				}, function(index) {
					layer.close(index);
					$.ajax({
						url: "${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId=" + id + "&flowDefineId=" + flowDefineId,
						dataType: 'json',
						success: function(result) {
							layer.msg(result.msg, {
								offset: '222px'
							});
							$("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
							$("#queren").remove();
						},
						error: function(result) {
							layer.msg("确认失败", {
								offset: '222px'
							});
						}
					});
				});
			}

			/**生成正式的采购文件*/
			function oncreate() {
				/*   var projectId = $("#projectId").val();
        window.location.href = "${pageContext.request.contextPath}/Auditbidding/purchaseFile.html?projectId="+projectId; */
				var obj = document.getElementById("TANGER_OCX");

				obj.SaveToLocal("E:\\【${project.name}】采购文件.doc", false, true);

			}
		</script>
		<!-- 打开文档后调用  -->
		<script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
			//声明控件
			var obj = document.getElementById("TANGER_OCX");
			// 转换日期格式  如果是CST 日期  转换 GMT 日期
			function getTaskTime(strDate) {
				if(null == strDate || "" == strDate) {
					return "";
				}
				if(strDate.indexOf("GMT") > 0) {
					return new Date(strDate).Format("yyyy年MMdd日hh时mm分");
				}
				var dateStr = strDate.trim().split(" ");
				var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
				var date = new Date(Date.parse(strGMT));
				var y = date.getFullYear();
				var m = date.getMonth() + 1;
				m = m < 10 ? ('0' + m) : m;
				var d = date.getDate();
				d = d < 10 ? ('0' + d) : d;
				var h = date.getHours();
				var minute = date.getMinutes();
				minute = minute < 10 ? ('0' + minute) : minute;
				var second = date.getSeconds();
				second = second < 10 ? ('0' + second) : second;
				return y + "年" + m + "月" + d + "日" + h + "时" + minute + "分";
			};
			//通用方法 判断是否存在 存在则行
			function replaceContent(begin, end, date) {
				if(obj.ActiveDocument.Bookmarks.Exists(begin) && obj.ActiveDocument.Bookmarks.Exists(end)) {
					obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End, ActiveDocument.Bookmarks(end).Range.End).Select();
					obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
					obj.ActiveDocument.Application.Selection.TypeText(date);
					obj.ActiveDocument.Bookmarks.Add(end);
				}
			}

			function loadWord(begin, end, url) {
				obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End, ActiveDocument.Bookmarks(end).Range.Start).Select();
				obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
				obj.AddTemplateFromURL(url, false, true);

			}
			/**
			 * ntko 控件加载玩之后调用
			 * **/
			$(function() {
				// 组合word文档
				var marks = obj.ActiveDocument.Bookmarks; //获取所有的书签
				var filePath = "${filePath}";
				if(filePath != null && filePath != "") {
					var pathArray = filePath.split(",");
					if(pathArray.length > 1) {
						//项目名称
						/* replaceContent("SYS_1", "SYS_1_1", "${project.name}"); */
						//项目编号
						replaceContent("SYS_2", "SYS_2_2", "${project.projectNumber}");
						//招标人
						replaceContent("SYS_3", "SYS_3_1", "${project.sectorOfDemand}");
						//项目名称
						replaceContent("SYS_20171200", "SYS_20171201", "${project.name}");
						//项目编号
						replaceContent("SYS_20171202", "SYS_20171203", "${project.projectNumber}");
						//投标截止时间
						replaceContent("SYS_20171204", "SYS_20171205", "${project.deadline}");
						// 投标地点
						replaceContent("SYS_20171206", "SYS_20171207", "${project.bidAddress}");
						// 开标时间
						replaceContent("SYS_20171208", "SYS_20171209", "${project.bidDate}");
						//开标地点
						replaceContent("SYS_20171210", "SYS_20171211", "${project.bidAddress}");
						//招标人
						replaceContent("SYS_20171212", "SYS_20171213", "${project.sectorOfDemand}");
						//招标人
						replaceContent("SYS_20171214", "SYS_20171215", "${project.sectorOfDemand}");
						//招标人
						replaceContent("SYS_20171216", "SYS_20171217", "${project.sectorOfDemand}");

						//定位定义标签位置
						loadWord("DW_TWO_TWO", "DW_TWO_THREE", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + pathArray[1]);
						loadWord("DW_THREE_2", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath=" + pathArray[0]);
						obj.ActiveDocument.DeleteAllEditableRanges(-1); //取消编辑
					}
				}
				for(var i = 1; i <= marks.Count; i++) {
					// 判读 标签 可编辑
					if(marks(i).Name.indexOf("EDITOR") == 0) {
						obj.ActiveDocument.Bookmarks(marks(i).Name).Range.Select(); //选取书签区域保护
						obj.ActiveDocument.Application.Selection.Editors.Add(-1); //增加可编辑区域
						//添加 内容标识显示
						obj.ActiveDocument.ActiveWindow.View.ShadeEditableRanges = true;
						obj.ActiveDocument.ActiveWindow.View.ShowBookmarks = true;
					}
				}
				if(obj.ActiveDocument.ProtectionType == -1) {
					obj.ActiveDocument.Protect(3); //实现文档保护
				}
				obj.ActiveDocument.Bookmarks("OLE_LINK_TOP").Select();

			});
		</script>
	</head>

	<body onload="OpenFile('${fileId}')">
		<c:if test="${process == 1 }">
			<!--面包屑导航开始-->
			<div class="margin-top-10 breadcrumbs ">
				<div class="container">
					<ul class="breadcrumb margin-left-0">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
						</li>
						<li>
							<a href="javascript:void(0)">保障作业系统</a>
						</li>
						<li>
							<a href="javascript:void(0)">采购项目管理</a>
						</li>
						<li class="active">
							<a href="javascript:jumppage('${pageContext.request.contextPath}/Auditbidding/list.html')">采购文件审核</a>
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
			<div class="container">
		</c:if>
		<c:if test="${process != 1 }">
			<div class="col-md-12 p0">
				<ul class="flow_step">
					<c:if test="${ope == 'add' }">
						<li>
							<a href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
							<i></i>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
							<i></i>
						</li>
						 <li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${project.id}&flowDefineId=${flowDefineId}">03、编报说明</a>
						   <i></i>
						 </li>
						 <li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、评审项预览</a>
						   <i></i>
						 </li>
						<li class="active">
							<a href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}">
								05、采购文件
							</a>
							<i></i>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">06、审核意见</a>
						</li>
					</c:if>
					<c:if test="${ope == 'view' }">
						<li>
							<a href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">01、资格性和符合性审查</a>
							<i></i>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/intelligentScore/packageListView.html?projectId=${project.id}&flowDefineId=${flowDefineId }">02、经济和技术评审细则</a>
							<i></i>
						</li>
						 <li >
							   <a  href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${project.id}&flowDefineId=${flowDefineId}">03、编报说明</a>
							   <i></i>
							 </li>
							 <li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、评审项预览</a>
						   <i></i>
						 </li>
							<li class="active">
							<a href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${project.id}&flowDefineId=${flowDefineId }">
								05、采购文件
							</a>
							<i></i>
						</li>
						<li>
							<c:if test="${project.confirmFile == 0 || project.confirmFile==null}">
								<a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">06、确认</a>
							</c:if>
							<c:if test="${project.confirmFile == 1 }">
								<a>06、已确认</a>
							</c:if>
						</li>
					</c:if>
				</ul>
			</div>
		</c:if>
		<div class="clear"></div>
		<c:if test="${process != 1 && ope == 'add' }">
		<form id="MyFile" method="post" class="h800 mt10">
		</c:if>
		
		<c:if test="${process == 1 }">
		<div class="bggrey p20_15 mt10">
			<div class="count_flow m0"><i>1</i><span class="fw">编报说明</span></div>
			<div class="fl mt10 ml34">
				<span class="m_inline f14 lh20">编报说明：</span>
				<div class="m_inline m_uploadFiles f0">
					<u:show showId="g" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeApproval}" />
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<%-- <div class="fl mt10 ml20">
				<span class="m_inline f14 lh20">审批单：</span>
				<div class="m_inline m_uploadFiles f0">
					<u:show showId="h" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
				</div>
				<div class="clear"></div>
			</div> --%>
			<div class="clear"></div>
		</div>
		
		<div class="bggrey p20_15 mt10">
			<div class="count_flow m0"><i>2</i><span class="fw">采购文件</span></div>
			<div class="fl mt10 ml34">
				<span class="m_inline f14 lh20">采购文件：</span>
				<div class="m_inline m_uploadFiles f0">
					<u:show showId="j" groups="b,c,d" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${projectTypeId}" />
				</div>
				<div class="clear mt10"></div>
				<span class="m_inline f14 lh20">&nbsp;&nbsp;&nbsp;审批单：</span>
				<div class="m_inline m_uploadFiles f0">
					<ul id="hs_disFileId" class="uploadFiles"><li class="file_load"><a href="${pageContext.request.contextPath}/project/purchaseEmbodiment.html?id=${project.id}&type=17"></a></li></ul>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		</c:if>
			
			<!-- 按钮 -->
			<div class="mt5 mb5 fr" id="handle">
	    <c:if test="${process != 1 && project.confirmFile != 1 && project.confirmFile != 3 && project.confirmFile != 4 &&project.confirmFile != 5 && ope =='add'}">
        <input type="button" class="btn btn-windows save" onclick="saveFile('5')" value="直接下达">
        <input type="button" class="btn btn-windows save" onclick="saveFile('0')" value="暂存">
        <input type="button" class="btn btn-windows git" onclick="saveFile('1')" value="提交至采购管理部门"></input>
	    </c:if>
	    <c:if test="${project.confirmFile == 4 && process != 1}">
	      
	    </c:if>
	    <c:if test="${(project.confirmFile == 3 || project.confirmFile == 4||project.confirmFile == 5) && process != 1}">
        <input type="button" class="btn btn-windows save" onclick="oncreate();" value="生成正式采购文件">
	    </c:if>
	    </div>
			<div class="clear"></div>
			<input type="hidden" id="ope" value="${ope}">
			<input type="hidden" id="confirmFileId" value="${project.confirmFile}">
			<input type="hidden" id="flowDefineId" value="${flowDefineId }">
			<input type="hidden" id="projectId" value="${project.id}">
			<input type="hidden" id="projectName" value="${project.name}">
			<c:if test="${process != 1 && ope == 'add' }">
			<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</c:if>
			<div class="clear" id="cgdiv">
				<c:if test="${process == 1 }">
					<div class="mt10">
					<c:if test="${project.confirmFile != 1}">
						<div class="bggrey p20_15 mt10">
							<div class="count_flow m0"><i>3</i><span class="fw">部门意见</span></div>
							
							<%-- <div class="mt20 fw f14" id="cgspan">采购管理部门意见</div>
							<div class="mt10">
								<textarea class="w100p h80 resizen" disabled="disabled" id="pcReason" maxlength="2000" name="pcReason" title="不超过2000个字">${MapPa['pcId'].content}</textarea>
								<div class="m_uploadFiles mt10">
									<span class="m_inline f14 lh16">采购管理部门审核意见附件：</span>
									<div class="m_inline m_uploadFiles f0">
										<u:show delete="false" showId="r" businessId="${MapPa['pcId'].id}" sysKey="${sysKey}" typeId="${pcTypeId}" />
									</div>
								</div>
							</div> --%>

							<%-- <div class="mt20 fw f14" id="cgspan">事业部门意见</div>
							<div class="mt10">
								<textarea class="w100p h80 resizen" disabled="disabled" id="causereason" maxlength="2000" name="causeReason" title="不超过2000个字">${MapPa['causeId'].content}</textarea>
								<div class="m_uploadFiles mt10">
									<span class="m_inline f14 lh16">事业部门审核意见附件：</span>
									<div class="m_inline m_uploadFiles f0">
										<u:show delete="false" showId="y" businessId="${MapPa['causeId'].id}" sysKey="${sysKey}" typeId="${causeTypeId}" />
									</div>
								</div>
							</div> --%>

							<%-- <div class="mt20 fw f14" id="cgspan">财务部门意见</div>
							<div class="mt10">
								<textarea class="w100p h80 resizen" disabled="disabled" id="financereason" maxlength="2000" name="financeReason" title="不超过2000个字">${MapPa['financeId'].content}</textarea>
								<div class="m_uploadFiles mt10">
									<span class="m_inline f14 lh16">财务部门审核意见附件：</span>
									<div class="m_inline m_uploadFiles f0">
										<u:show delete="false" showId="o" businessId="${MapPa['financeId'].id}" sysKey="${sysKey}" typeId="${financeTypeId}" />
									</div>
								</div>
							</div> --%>

							<div class="mt20 fw f14" id="cgspan">最终意见</div>
							<div class="mt10">
								<textarea class="w100p h80 resizen" disabled="disabled" id="finalreason" maxlength="2000" name="finalReason" title="不超过2000个字">${MapPa['finalId'].content}</textarea>
								<div class="fl f0 mt10">
										<span class="m_inline f14 lh16">审批单：</span>
										<div class="m_inline m_uploadFiles">
											<u:show showId="ff1" delete="false" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
										</div>
									</div>
								<div class="m_uploadFiles mt10">
									<span class="m_inline f14 lh16">最终意见附件：</span>
									<div class="m_inline m_uploadFiles f0">
										<u:show delete="false" showId="ff" businessId="${MapPa['finalId'].id}" sysKey="${sysKey}" typeId="${finalTypeId}" />
									</div>
								</div>
							</div>
							
							<div class="clear"></div>
						</div>
					</c:if>
					
					<c:if test="${project.confirmFile == 1}">
						<form id="form1">
							<div class="bggrey p20_15 mt10">
								<div class="count_flow m0"><i>3</i><span class="fw">部门意见</span></div>
								
								<%-- <div class="mt20 fw f14" id="cgspan">采购管理部门意见</div>
								<div class="mt10">
									<textarea class="w100p h80 resizen" onkeyup="lengthStr('1',this)" id="pcReason" maxlength="2000" name="projectAdviceList[0].content" title="不超过2000个字">${reasons.pcReason}</textarea>
									<input type="hidden" name="projectAdviceList[0].typeId" value="${pcTypeId}"/>
									<input type="hidden" name="projectAdviceList[0].id" value="${pcId}"/>
									<input type="hidden" name="projectAdviceList[0].seq" value="1"/>
									<div class="fl f0 mt10">
										<span class="m_inline f14 lh16">采购管理部门审核意见附件：</span>
										<div class="m_inline m_uploadFiles">
											<u:upload id="r" multiple="true" exts=".jpg,.png" businessId="${pcId}" sysKey="${sysKey}" typeId="${pcTypeId}" auto="true" />
											<u:show showId="t" businessId="${pcId}" sysKey="${sysKey}" typeId="${pcTypeId}" />
										</div>
									</div>
									<div class="fr mt10">当前已经输入<span class="red" id="in1"></span>字，还可以输入<span class="red" id="into1"></span>字</div>
									<div class="clear"></div>
								</div> --%>
								
								<%-- <div class="mt20 fw f14" id="cgspan">事业部门意见</div>
								<div class="mt10">
									<textarea class="w100p h80 resizen" id="causereason" onkeyup="lengthStr('2',this)" maxlength="2000" name="projectAdviceList[1].content" title="不超过2000个字">${reasons.causeReason}</textarea>
									<input type="hidden" name="projectAdviceList[1].typeId" value="${causeTypeId}"/>
									<input type="hidden" name="projectAdviceList[1].id" value="${causeId}"/>
									<input type="hidden" name="projectAdviceList[1].seq" value="2"/>
									<div class="fl f0 mt10">
										<span class="m_inline f14 lh16">事业部门审核意见附件：</span>
										<div class="m_inline m_uploadFiles">
											<u:upload id="u" multiple="true" exts=".jpg,.png" businessId="${causeId}" sysKey="${sysKey}" typeId="${causeTypeId}" auto="true" />
											<u:show showId="i" businessId="${causeId}" sysKey="${sysKey}" typeId="${causeTypeId}" />
										</div>
									</div>
									<div class="fr mt10">当前已经输入<span class="red" id="in2"></span>字，还可以输入<span class="red" id="into2"></span>字</div>
									<div class="clear"></div>
								</div> --%>
								
								<%-- <div class="mt20 fw f14" id="cgspan">财务部门意见</div>
								<div class="mt10">
									<textarea class="w100p h80 resizen" onkeyup="lengthStr('3',this)" id="financereason" maxlength="2000" name="projectAdviceList[2].content" title="不超过2000个字">${reasons.financeReason}</textarea>
									<input type="hidden" name="projectAdviceList[2].typeId" value="${financeTypeId}"/>
									<input type="hidden" name="projectAdviceList[2].id" value="${financeId}"/>
									<input type="hidden" name="projectAdviceList[2].seq" value="3"/>
									<div class="fl f0 mt10">
										<span class="m_inline f14 lh16">财务部门审核意见附件：</span>
										<div class="m_inline m_uploadFiles">
											<u:upload id="p"  exts=".jpg,.png" multiple="true"  businessId="${financeId}"  sysKey="${sysKey}" typeId="${financeTypeId}" auto="true" />
											<u:show showId="s" businessId="${financeId}" sysKey="${sysKey}" typeId="${financeTypeId}" />
										</div>
									</div>
									<div class="fr mt10">当前已经输入<span class="red" id="in3"></span>字，还可以输入<span class="red" id="into3"></span>字</div>
									<div class="clear"></div>
								</div> --%>
								
								<div class="mt20 fw f14" id="cgspan"><span class="red">*</span>最终意见</div>
								<div class="mt10">
									<textarea class="w100p h80 resizen" id="finalreason" maxlength="2000" onkeyup="lengthStr('4',this)" name="projectAdviceList[0].content" title="不超过2000个字">${reasons.finalReason}</textarea>
									<input type="hidden" name="projectAdviceList[0].typeId" value="${finalTypeId}"/>
									<input type="hidden" name="projectAdviceList[0].id" value="${finalId}"/>
									<input type="hidden" name="projectAdviceList[0].seq" value="4"/>
									<div class="fl f0 mt10">
										<span class="m_inline f14 lh16"><span class="red">*</span>审批单：</span>
										<div class="m_inline m_uploadFiles">
											<u:upload id="aa1"  multiple="true" buttonName="文件上传" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
											<u:show showId="ff1" businessId="${project.id}" sysKey="${sysKey}" typeId="${typeId}" />
										</div>
									</div>
									<div class="fl f0 mt10">
										<span class="m_inline f14 lh16"><span class="red">*</span>其他部门意见附件：</span>
										<div class="m_inline m_uploadFiles">
											<u:upload id="kk" exts=".jpg,.png" multiple="true" businessId="${finalId}" sysKey="${sysKey}" typeId="${finalTypeId}" auto="true" />
											<u:show showId="ii" businessId="${finalId}" sysKey="${sysKey}" typeId="${finalTypeId}" />
										</div>
									</div>
									<div class="fr mt10">当前已经输入<span class="red" id="in4"></span>字，还可以输入<span class="red" id="into4"></span>字</div>
									<div class="clear"></div>
								</div>
							</div>
						</form>
					</c:if>
						
					</div>
					<div class="clear tc mt50">
						<c:if test="${exist == true && project.confirmFile == 1}">
							<input type="button" class="btn btn-windows check_pass " onclick="updateAudit('3')" value="审核通过"></input>
							<input type="button" class="btn btn-windows check_back " onclick="updateAudit('2')" value="退回重报 "></input>
							<input type="button" class="btn btn-windows edit " onclick="updateAudit('4')" value="修改报备 "></input>
						</c:if>
						<a class="btn btn-windows back " href="${pageContext.request.contextPath}/Auditbidding/list.html">返回 </a>
					</div>
			</div>
			</c:if>
			</div>
			<c:if test="${process != 1 && ope == 'add' }">
		</form>
		</c:if>
	</body>

</html>