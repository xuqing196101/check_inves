 $(function () {
        loadAreaZtree();
        loadTypeTree();
        chane();//对人数进行计算
    });
    /**各类别人数变动后触发,用于统计输入总人数*/
    function chane() {
        var sun = "0";
        var projectCount = $("#project").val();
        if (projectCount != null && projectCount != '') {
            sun = parseInt(sun) + parseInt(projectCount);
        }
        var serviceCount = $("#service").val();
        if (serviceCount != null && serviceCount != '') {
            sun = parseInt(sun) + parseInt(serviceCount);
        }
        var productCount = $("#product").val();
        if (productCount != null && productCount != '') {
            sun = parseInt(sun) + parseInt(productCount);
        }
        var salesCount = $("#sales").val();
        if (salesCount != null && salesCount != '') {
            sun = parseInt(sun) + parseInt(salesCount);
        }
        $("#sunCount").val(sun);

    }
    /**供应商地区*/
    function areas() {
        var areas = $("#area").find("option:selected").val();
        $.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts/city.do",
            data: {area: areas},
            dataType: "json",
            success: function (data) {
                var list = data;
                $("#city").empty();

                var html = "";
                var areas = $("#area").find("option:selected").text();
                if (areas == '全国') {
                    html = "<option value='' selected='selected' >所有地区</option>";
                } else {
                    layer.prompt({
                        formType: 2,
                        shade: 0.01,
                        title: '限制地区原因',
                        btn: ['确定', '取消'],
                        cancel: function (index, layero) {
                            $("#area option:first").prop("selected", 'selected');
                            $("#city").empty();
                            $("#city").append("<option value=''>所有省市</option>");
                            selectLikeSupplier();
                            layer.close(index);
                        }
                    }, function (value, ix, elem) {
                        $("#addressReason").val(value);
                        selectLikeSupplier();
                        layer.close(ix);
                    });
                    html = "<option value=''>所有市</option>";

                }
                for (var i = 0; i < list.length; i++) {
                    html += "<option value=" + list[i].id + ">" + list[i].name + "</option>";
                }
                $("#city").append(html);
                selectLikeSupplier();
            }
        });

    }
    /**满足条件供应商人数查询*/
    function selectLikeSupplier() {
        var area = document.getElementById("area").value;
        $.ajax({
            cache: true,
            type: "POST",
            dataType: "json",
            url: globalPath+'/SupplierCondition/selectLikeSupplier.do',
            data: $('#form1').serialize(),// 你的formid
            async: false,
            success: function (data) {
            	if(null!=data){
            		if(null!=data.products){
            			var productCont = data.products.length;
            			$("#productResult").find("span:first").html(productCont);
            			//$(".productCount").html(productCont);
            			//window.sessionStorage.setIterm("products",data.products);
            			products = data.products;
            		}
            		if(null!=data.services){
            			var serviceCont = data.services.length;
            			$("#serviceResult").find("span:first").html(serviceCont);
            			//$(".serviceCount").html(serviceCont);
            			//window.sessionStorage.setIterm("services",data.services);
            			services = data.services;
            		}
            		if(null!=data.sales){
            			var salesCont = data.sales.length;
            			$("#salesResult").find("span:first").html(salesCont);
            			//$(".salesCount").html(salesCont);
            			//window.sessionStorage.setIterm("sales",data.sales);
            			sales = data.sales;
            		}
            		if(null!=data.projects){
            			var projectCont = data.projects.length;
            			$("#projectResult").find("span:first").html(projectCont);
            			projects = data.projects;
            		}
            	}else{
            		
            	}
            }
        });
        return false;
    }
    /**点击抽取--对参数进行校验*/
    function extractVerify() {
        var eCount = $("#supplierCount").val();//抽取总数量
        $("#status").val(1);//修改状态为抽取中
        //获取每个类别的抽取数量
        if (positiveRegular(eCount)) {
            $("#countSupplier").text("");
            var projectExtractNum = $("#projectExtractNum").val();
            var productExtractNum = $("#productExtractNum").val();
            var serviceExtractNum = $("#serviceExtractNum").val();
            var salesExtractNum = $("#salesExtractNum").val();
            var count = parseInt(projectExtractNum)+parseInt(productExtractNum)+parseInt(serviceExtractNum)+parseInt(salesExtractNum);
           // var typeName = $("input[name='supplierTypeName']").attr('title');
           var typeName = $("#supplierType").val();
            if (typeName =="undefined" || typeName == "") {
                layer.msg("请选择抽取类型");
                return false;
            }
            if (positiveRegular(count)) {
                if (parseInt(count) > parseInt(eCount)) {
                    layer.msg("数量不能大于总数量");
                } else {
                	ext();
                }
            } else {
                layer.msg("请输入有效人数(正整数)");
            }
        } else {
            $("#countSupplier").text("请输入有效总人数(正整数)");
        }
        return false;
    }
    /**点击抽取--判断是否完成本次抽取*/
    function fax() {
        $.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts/isFinish.do",
            data: {packageId: "${packageId}"},
            dataType: "json",
            success: function (data) {
                if (data == "SUCCESS") {
                    layer.confirm('是否完成本次抽取？', {
                        btn: ['确定', '取消'], offset: ['40%', '40%'], shade: 0.01
                    }, function (index) {
                        ext();
                    }, function (index) {
                        layer.close(index);
                    });
                } else {
                    ext();
                }
            }
        });
    }
    /**点击抽取--当选择参加与否后保存状态*/
    function ext() {
    	//selectLikeSupplier();
    	//获取到要抽取的数量
    	var projectExtractNum = $("#projectExtractNum").val();
    	if(projectExtractNum>0){
    		if(projectExtractNum<=projects.length){
    			$("#projectResult").find("tbody").empty();
    			appendTd(-1,$("#projectResult").find("tbody"),null);
    		}else{
    			layer.msg("工程条件不满足");
    		}
    	}
    	
    	var productExtractNum = $("#productExtractNum").val();
    	if(productExtractNum>0){
    		if(productExtractNum<=products.length){
    			$("#productResult").find("tbody").empty();
    			appendTd(-1,$("#productResult").find("tbody"),null);
    		}else{
    			alert("生产条件不满足");
    		}
    	}
    	
    	var serviceExtractNum = $("#serviceExtractNum").val();
    	if(serviceExtractNum>0){
    		if(serviceExtractNum<=services.length){
    			$("#serviceResult").find("tbody").empty();
    			appendTd(-1,$("#serviceResult").find("tbody"),null);
    		}else{
    			alert("服务条件不满足");
    		}
    	}
    	
    	var salesExtractNum = $("#salesExtractNum").val();
    	if(salesExtractNum>0){
    		if(salesExtractNum<=sales.length){
    			$("#salesResult").find("tbody").empty();
    			appendTd(-1,$("#salesResult").find("tbody"),null);
    		}else{
    			alert("销售条件不满足");
    		}
    	}
    }
    
    //显示结果   obj 是当前操作的行所在的tbody
    function appendTd(num,obj,result){
    	//获取类型
    	var type =  $(obj).parents("div").attr("id").substring(0,$(obj).parents("div").attr("id").lastIndexOf("R"));
    	//需要判断能参加，满足后不再追加
    	var agreeCount=0;
    	var IDName = 
    	$(obj).find("option:selected").each(function(){
    		if($(this).val()==1){
    			agreeCount++;
    		}
    	});
    	if($("#"+type+"ExtractNum").val()==agreeCount){
    		return true;
    	}
    	var typeName;
    	var data;
    	if("project" == type){
    		data = projects;
    		typeName = "工程";
    	}else if("sales" == type){
    		data = sales;
    		typeName = "物资销售";
    	}else if("product" == type){
    		data = products;
    		typeName = "物资生产";
    	}else if("service" == type){
    		data = services;
    		typeName = "服务";
    	}
    	
    	var i = parseInt(num)+1;
    	var tex = "<tr class='cursor' typeCode='"+type.toUpperCase()+"' sid='"+data[i].id+"' index='"+i+"'>" +
	   	 "<td class='tc' >"+(i+1)+"</td>" +
		 "<td class='tc'  >"+data[i].supplierName+"</td>" +
	     "<td class='tc' >"+typeName+"</td>" +
	     "<td class='tc' >"+data[i].contactName+"</td>" +
	     "<td class='tc' >"+data[i].contactMobile+"</td>" +
	     "<td class='tc' >"+data[i].contactTelephone+"</td>" +
	     "<td class='tc' class='res'><select onchange='operation(this)'> <option value='0'>请选择</option> <option value='1'>能参加</option> <option value='2'>待定</option> <option value='3'>不能参加</option> </td>" +
	     "</tr>";
    	$(obj).append(tex);
    }
    
    /**暂存*/
    function temporary(status) {
    	//存储项目信息
    	 $.ajax({
             cache: true,
             type: "POST",
             dataType: "json",
             url: globalPath+'/SupplierCondition/saveSupplierCondition.do',
             data: $('#form').serialize(),// 你的formid
             success: function (data) {
            	 if(status == 1){
            		 layer.msg("暂存成功");
            	 }
             }
    	 });
    	//存储查询条件 
    }
    /**完成所有抽取后执行**/
    function finish() {
        layer.confirm('是否需要打印', {
            btn: ['打印', '取消'], offset: ['40%', '40%'], shade: 0.01
        }, function (index) {
            window.location.href = globalPath+"/SupplierExtracts/showRecord.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
        }, function (index) {
            window.location.href = globalPath+"/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
            layer.close(index);
        });
    }
    /**展示品目*/
    function opens(cate) {
    	//获取类别
    	//var typeId = $("#supplierTypeId").val();
    	var typeCode = $(cate).attr("typeCode");
    	cate.value = "";
        //  iframe层
        var iframeWin;
        layer.open({
            type: 2,
            title: "选择条件",
            shadeClose: true,
            shade: 0.01,
            area: ['430px', '400px'],
            offset: '20px',
            content: globalPath+'/SupplierExtracts/addHeading.do?supplierTypeCode='+typeCode, //iframe的url
            //content: globalPath+'/supplier/category_type.do?code='+supplierCode, 
            success: function (layero, index) {
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            },
            btn: ['保存', '重置']
            , yes: function () {
                iframeWin.getChildren(cate);
                initTypeLevelId();
                //若是工程类，需要根据品目去动态生成等级树
                if(typeCode == "PROJECT"){
                	loadprojectLevelTree();
                }
                selectLikeSupplier();
            }
            , btn2: function () {
                opens();
            }
        });
    }
    /**供应商类别----begin----*/
    function loadTypeTree(){
    	 var setting = {
    	            check: {
    	                enable: true,
    	                chkboxType: {
    	                    "Y": "",
    	                    "N": ""
    	                }
    	            },
    	            view: {
    	                dblClickExpand: false
    	            },
    	            data: {
    	                simpleData: {
    	                    enable: true,
    	                    idKey: "id",
    	                    pIdKey: "parentId"
    	                }
    	            },
    	            callback: {
    	                beforeClick: beforeClick,
    	                onCheck: checkType,
    	            }
    	        };
    	        var projectId = "${projectInfo.projectId}";
    	        $.ajax({
    	            type: "POST",
    	            async: false,
    	            url: globalPath+"/SupplierExtracts/supplieType.do",
    	            dataType: "json",
    	            data:{projectId:projectId},
    	            success: function (zNodes) {
    	                for (var i = 0; i < zNodes.length; i++) {
    	                    if (zNodes[i].isParent) {

    	                    } else {
    	                        //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标
    	                    }
    	                }
    	                tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
    	                tree.expandAll(true); //全部展开
    	            }
    	        });
    }
     function showSupplierType() {
        var cityObj = $("#supplierType");
        var cityOffset = $("#supplierType").offset();
        $("#supplierTypeContent").css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownSupplierType);
    } 
	
	//选择抽取类型
	function choseType(obj){
		$("#extCategoryNames").val("");
		$("#extCategoryId").val("");
		$("#isSatisfy").val("");
		$("#extCategoryName").val("");
		var typeId = $("#supplierType :checked").attr("typeId");
		$("#supplierTypeId").val(typeId);
		$("#expertsTypeId").val(typeId);
		selectLikeSupplier();
	}
	
	//加载地区树形结构
	function loadAreaZtree(){
		var treeNodes; 
		 var setting = {
          async: {
            autoParam: ["id=area"],
            enable: true, 
            url: globalPath+"/SupplierExtracts/city.do",
            dataType: "json",
            type: "post",
          },
          check: {
            enable: true,
            chkboxType: {
              "Y": "s",
              "N": "s"
            }
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            },
            key: {
				children: "nodes"
			}
          },
          callback: {
               // beforeCheck: beforeClickArea,
                onCheck: choseArea,
          },
          view: {
                dblClickExpand: false
          }        
        };
        treeArea = $.fn.zTree.init($("#treeArea"), setting, treeNodes);
	}

	//显示地区树
	function showTree(){
		var areaObj = $("#area");
        var areaOffset = $("#area").offset();
        $("#areaContent").css({
            left: areaOffset.left + "px",
            top: areaOffset.top + areaObj.outerHeight() + "px"
        }).slideDown("fast");
         $("body").bind("mousedown", onBodyDownArea);
	}
	//获取选中节点地区
	function choseArea(){
		var treeObj=$.fn.zTree.getZTreeObj("treeArea");
        var areas=treeObj.getCheckedNodes(true);
        //省，直辖市
       	var pids = "";
       	//二级 市 区
       	var ids = "";
       	var idArr = new Array();
       	var names = "";
       	for(var i=0; i<areas.length;i++){
       		if(areas[i].isParent){
				pids += areas[i].id + ",";
				names += areas[i].name + ",";
				idArr.push(areas[i].id);
       		}else{
       			var flag = true;
       			
       			for(var v=0;v<idArr.length;v++){
       				if(areas[i].parentId == idArr[v]){
       					flag = false;
       					break;
       				}
				}
       			
       			if(flag){
       				ids += areas[i].id + ",";
       				names += areas[i].name + ",";
       			}
       		}
		}
       	
		$("#province").val(pids.substring(0,pids.lastIndexOf(",")));
		$("#addressId").val(ids.substring(0,ids.lastIndexOf(",")));
		$("#area").val(names.substring(0,names.lastIndexOf(",")));
	}
	
	//地区树绑定事件
	function onBodyDownArea(event) {
        if (!(event.target.id == "menuBtn" || $(event.target).parents("#areaContent").length > 0)) {
            hideArea();
        }
    }

    function hideArea() {
        $("#areaContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownArea);
        selectLikeSupplier();
    }
	
    function onBodyDownSupplierType(event) {
        if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
            hideSupplierType();
        }
    }

    function hideSupplierType() {
        $("#supplierTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownSupplierType);
        selectLikeSupplier();
    }

    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    function checkType(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
        var rid = "";
        var codes = "";
        //设置隐藏展示
        $("#projectCount").addClass("dnone");
        $("#serviceCount").addClass("dnone");
        $("#productCount").addClass("dnone");
        $("#salesCount").addClass("dnone");
        
        $("#salesResult").addClass("dnone");
        $("#projectResult").addClass("dnone");
        $("#serviceResult").addClass("dnone");
        $("#productResult").addClass("dnone");
        
        $("#projectCategoryIds").val("");
        $("#productCategoryIds").val("");
        $("#serviceCategoryIds").val("");
        $("#salesCategoryIds").val("");
        
        $("#projectExtractNum").val(0);
        $("#productExtractNum").val(0);
    	$("#serviceExtractNum").val(0);
    	$("#salesExtractNum").val(0);
        
        
        $("#project").val("");
        $("#service").val("");
        $("#product").val("");
        $("#sales").val("");
        
        
        //初始化每个选择的值
        //chane();

        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i].name + ",";
            rid += nodes[i].id + ",";
            codes += nodes[i].code + ",";
            if ('PROJECT' == nodes[i].code) {
            	//查询数量
            	//selectTypeCount("PROJECT");
                $("#projectCount").removeClass("dnone");
              //追加结果表
                $("#projectResult").removeClass("dnone");
            } else if ('SERVICE' == nodes[i].code) {
            	//selectTypeCount("SERVICE");
                $("#serviceCount").removeClass("dnone");
                //加载服务等级树
                loadLevelTree("serviceLevelTree");
                //追加结果表
                $("#serviceResult").removeClass("dnone");
            } else if ('PRODUCT' == nodes[i].code) {
            	//selectTypeCount("PRODUCT");
                $("#productCount").removeClass("dnone");
                //加载生产等级树
                loadLevelTree("productLevelTree");
              //追加结果表
                $("#productResult").removeClass("dnone");
            } else if ('SALES' == nodes[i].code) {
            	//selectTypeCount("SALES");
                $("#salesCount").removeClass("dnone");
                //销售等级树
                loadLevelTree("salesLevelTree");
              //追加结果表
                $("#salesResult").removeClass("dnone");
            }
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
        if (codes.length > 0) codes = codes.substring(0, codes.length - 1);
        $("#supplierType").val(v);
        $("#supplierType").attr("title", v);
        $("#supplierTypeId").val(rid);
        $("#supplierTypeCode").val(codes);
        //selectLikeSupplier();
    }
    
    //查询类别数量
    function selectTypeCount(code){
    	$.ajax({
    		url:globalPath+'/SupplierCondition/selectLikeSupplier.do',
             type: "POST",
             dataType: "json",
             data:{supplierTypeCode:code,type:"count"},
             success:function(count){
            	 $("#"+code.toLowerCase()+"Result").find("span:first").html(count);
             }
    	});
    }
    
    /**供应商类别----eng----*/
    /**抽取级别----begin----*/
    function initTypeLevelId(){
    	$("#levelTypeId").val("");
    	$("#levelType").val("所有级别");
    }
    
    //加载工程等级树
    function loadprojectLevelTree(){
    	var cateId = $("#projectCategoryIds").val();
    	var setting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "",
                    "N": ""
                }
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                }
            },
            callback: {
                beforeClick: beforeClickLevel,
                onCheck: onCheckLevel
            }
        };
		// $.post(globalPath+"/category/getEngLevelByCid.html",{categoryId:cateId,},function(data){zNodes = data},"json");
		 $.ajax({
		 	url:globalPath+"/category/getEngLevelByCid.do",
		 	data:{categoryId:cateId},
		 	async:false,
		 	dataType:"json",
		 	success:function(datas){
		 		var treeLevelType = $.fn.zTree.init($("#projectLevelTree"), setting, datas);
		        checkAllNodes("projectLevelTree");//选中所有节点
		        onCheckLevel("projectLevelTree");//处理选中节点
		 	}
		 });
    }
    //加载等级树
    function loadLevelTree(treeName){
    		//var typeCode = $("#supplierType").val();
    		var zNodes ;
    		/* 判断类别 生成不同的级别菜单 */
			if(treeName == "productLevelTree"){
				zNodes= [
		            {id: "一级", pid: 0, name: "一级"},
		            {id: "二级", pid: 0, name: "二级"},
		            {id: "三级", pid: 0, name: "三级"},
		            {id: "四级", pid: 0, name: "四级"},
		            {id: "五级", pid: 0, name: "五级"},
		            {id: "六级", pid: 0, name: "六级"},
		            {id: "七级", pid: 0, name: "七级"},
		            {id: "八级", pid: 0, name: "八级"}
		        ];
			}else if(treeName == "serviceLevelTree"){
				zNodes= [
		            {id: "一级", pid: 0, name: "一级"},
		            {id: "二级", pid: 0, name: "二级"},
		            {id: "三级", pid: 0, name: "三级"},
		            {id: "四级", pid: 0, name: "四级"},
		            {id: "五级", pid: 0, name: "五级"}
		        ];
			}else if(treeName == "salesLevelTree"){
				zNodes= [
		            {id: "一级", pid: 0, name: "一级"},
		            {id: "二级", pid: 0, name: "二级"},
		            {id: "三级", pid: 0, name: "三级"},
		            {id: "四级", pid: 0, name: "四级"},
		            {id: "五级", pid: 0, name: "五级"}
		        ];
			}		
	        var setting = {
	            check: {
	                enable: true,
	                chkboxType: {
	                    "Y": "",
	                    "N": ""
	                }
	            },
	            view: {
	                dblClickExpand: false
	            },
	            data: {
	                simpleData: {
	                    enable: true,
	                    idKey: "id",
	                }
	            },
	            callback: {
	                beforeClick: beforeClickLevel,
	                onCheck: onCheckLevel
	            }
	        };
	        var treeLevelType = $.fn.zTree.init($("#"+treeName), setting, zNodes);
	        checkAllNodes(treeName);//选中所有节点
	        onCheckLevel(treeName);//处理选中节点
    }
	
	//等级树加载完成后全选等级
	function checkAllNodes(treeName){
		var treeObj = $.fn.zTree.getZTreeObj(treeName);
		treeObj.checkAllNodes(true);
	}
	
	//展示等级树
	function showLevel(obj){
		var cateId = $(obj).parents("li").find(".categoryId").val();
    	if(cateId !="undefined" && cateId != ""){
	        var levelType = $(obj);
	        var treeHome = levelType.attr("treeHome");
	        var levelOffset = $(obj).offset();
	        $("#"+treeHome).css({
	            left: levelOffset.left + "px",
	            top: levelOffset.top + levelType.outerHeight() + "px"
	        }).slideDown("fast");
	        var LevelType =  $(obj).attr("id");
	        if("projectLevel" == LevelType){
	        	$("body").bind("mousedown", onBodyDownProjectLevel);
	        }else  if("productLevel" == LevelType){
	        	$("body").bind("mousedown", onBodyDownProductLevel);
	        }else  if("salesLevel" == LevelType){
	        	$("body").bind("mousedown", onBodyDownSalesLevel);
	        }else  if("serviceLevel" == LevelType){
	        	$("body").bind("mousedown", onBodyDownServiceLevel);
	        }
    	}else{
    		layer.msg("请选择品目");
    	}
	}
	
	//触发函数判断是否隐藏等级树
    function onBodyDownProjectLevel(event) {
        if (!(event.target.nodeName == "SPAN")) {
            hideLevelType("projectLevelContent");
        }
    }
    function onBodyDownProductLevel(event) {
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("productLevelContent");
    	}
    }
    function onBodyDownSalesLevel(event) {
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("salesLevelContent");
    	}
    }
    function onBodyDownServiceLevel(event) {
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("serviceLevelContent");
    	}
    }
    //隐藏等级树
    function hideLevelType(obj) {
        $("#"+obj).fadeOut("fast");
        if("serviceLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownServiceLevel);
        }else if("salesLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownSalesLevel);
        }else if("projectLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownProjectLevel);
        }else if("productLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownProductLevel);
        }
        selectLikeSupplier();

    }

    function beforeClickLevel(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeLevelType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }
    function beforeClickArea(treeId, treeNode) {
       
    }

    //工程等级树被选中后
    function onCheckLevel(obj) {
    	var zTree = $.fn.zTree.getZTreeObj(obj);
    	if(null == zTree){
    		zTree = $.fn.zTree.getZTreeObj(obj.target.id);
    		obj = obj.target.id;
    	}
    	
    	var input = obj.substring(0,obj.lastIndexOf("T"));
    	
    	var nodes = zTree.getCheckedNodes(true);
    	v = "";
    	var rid = "";
    	for (var i = 0, l = nodes.length; i < l; i++) {
    		v += nodes[i].name + ",";
    		rid += nodes[i].id + ",";
    	}
    	if (v.length > 0) v = v.substring(0, v.length - 1);
    	if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
    	var levelTypeObj = $("#"+input);
    	levelTypeObj.val(v);
    	levelTypeObj.attr("title", v);
    	$(levelTypeObj).parents("li").find("[name='"+input+"']").val(rid);
    }
    
   
    
    
    /**抽取级别----end----*/
    /**选择参加与否选项后自动触发*/
    function operation(select) {
        var x, y;
        var oRect = select.getBoundingClientRect();
        x = oRect.left - 450;
        y = oRect.top - 150;
        layer.confirm('确定本次操作吗？', {
            btn: ['确定', '取消'], shade: 0.01
        }, function (index) {
            var strs = new Array();
            var v = select.value;
            var obj = $(select).parents("tbody");
            var objTr = $(select).parents("tr");
            var req = objTr.attr("index");
           // strs = v.split(",");
            layer.close(index);
            if (v == "3") {
                layer.prompt({
                    formType: 2,
                    shade: 0.01,
                    offset: [y, x],
                    title: '不参加理由'
                }, function (value, index, elem) {
                    ajaxs(objTr, value,2);
                    layer.close(index);
                    //select.options[0].selected = true;
                	appendTd(req,obj,"不能参加");
                });
            } else if(v == "1"){
                select.disabled = true;
                ajaxs(objTr, '',1);
            	appendTd(req,obj,"能参加");
            }else{
            	appendTd(req,obj,"待定");
            }
        }, function (index) {
            layer.close(index);
            select.options[0].selected = true;
        });
    }

    /**
     * 存储抽取结果
     */
    function ajaxs(objTr, reason,attend) {//obj:当前处理完成供应商信息、行  v:不能参加理由
    	
    	var reviewType = objTr.attr("typeCode");
    	var reviewType = objTr.attr("typeCode");
    	var sid = objTr.attr("sid");
    	$.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts/resultextract.do",
            data: {reason: reason, conditionId: "${condition.id}",supplierId:sid,reviewType:reviewType,attend:attend,recordId:"${projectInfo.id}"},
            dataType: "json",
            success: function (data) {
            	
            }
    	});
    }