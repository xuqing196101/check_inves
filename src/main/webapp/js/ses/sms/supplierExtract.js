/*var products = 0;
var projects = 0;
var services = 0;
var sales = 0;*/

var scount = 0;
var successCount = 1;
var proError = 0;
var code ="";

//$("#quaName").keyup(function(event){ 
	//alert();
function selectQua(){
	if(event.keyCode==32){
		var name = $("#projectQuaName").val();
		if(""!=name && null!=name){
			$.ajax({
				type: "POST",
				url: globalPath+"/SupplierCondition_new/qualificationList.do",
				data:  {name:name,type:4},
				dataType: "json",
				success: function (msg) {
					if(null !=msg){
						//console.log(msg);
						loadQuaList(msg.obj);
					}
				}
			});
		}else{
			$("#projectQuaId").val("");
			$("[name='projectLevel']").val("");
			$("#projectLevel").val("全部等级");
			$("#projectQuaName").val("全部资质");
		}
	}
}
//});




$(function () {
        loadAreaZtree();
        //loadTypeTree();
        //查询条件中供应商类型
        loadSupplierType();
        
        for ( var i = 0; i < 2; i++) {
			addPerson($("#eu"));
		}
        addPerson($("#su"));
    });
    
	//生成UUId
    function uuid() {
    	  var s = [];
    	  var hexDigits = "0123456789abcdef";
    	  for (var i = 0; i < 36; i++) {
    	    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    	  }
    	  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    	  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    	  var uuid = s.join("");
    	  return uuid;
    	}
    
    /**
     * 项目信息地区选择
     * @returns
     */
    function selectArea(obj){
    	var city = "";
    	var provinceId = $(obj).val();
    	if(provinceId == ''){
    		$(obj).next().empty();
    		$(obj).next().append("<option value=''>选择地区</option>");
            return;
    	}
    	$.ajax({
            type: "POST",
            url: globalPath+"/area/find_by_parent_id.do",
            data: {id: provinceId},
            dataType: "json",
            success: function (data) {
            	$(obj).next().empty();
            	$(obj).next().append("<option value=''>选择地区</option>");
            	for(var i=0;i<data.length;i++){
            		city += "<option value="+data[i].id+">"+data[i].name+"</option>";
            	}
            	$(obj).next().append(city);
            }
        });
    }
    
    /**
     * 项目编号唯一校验
     * @param obj
     */
    function checkSole(obj){
		var projectCode = $(obj).val();
		$.ajax({
    		type: "POST",
    		url: globalPath+"/SupplierExtracts_new/checkSole.do",
    		data:  {projectCode:projectCode},
    		dataType: "json",
    		success: function (msg) {
    			if(msg>0){
    				$("#projectCodeError").html("该项目编号已存在");
    				proError ++;
    			}else{
    				$("#projectCodeError").html("");
    				proError = 0;
    			}
    		}
    	});
	}
    
    
    //比较售领时间是否输入合理
    function checkTime(){
    	if(null != $("#sellEnd").val()){
    		var startTime = new Date(Date.parse($("#sellBegin").val()));
    		var endTime = new Date(Date.parse($("#sellEnd").val()));
    		if(startTime>=endTime){
    			layer.msg("结束时间不能小于起始时间");
    			$("#sellEnd").val("");
    		}
    	}else{
    		layer.msg("请选择售领起始时间");
    	}
    	if(null != $("#sellBegin").val()){
    		var startTime = new Date(Date.parse($("#sellBegin").val()));
    		var endTime = new Date(Date.parse($("#sellEnd").val()));
			if(startTime>=endTime){
				layer.msg("结束时间不能小于起始时间");
				$("#sellEnd").val("");
			}
    	}else{
    		layer.msg("请选择售领起始时间");
    	}
    }
    
    
    /**
     * 人员信息操作
     */
    
    //增加
    function addPerson(obj){
    	var index = $(obj).parents("form").find("tr:last").find("td:eq(1)").html();
    	var input = $(obj).parents("form").find("tr:last").find("td:first").find("input").prop("name");//.substring(4,6);//.attr("req");
    	var req ;
			console.log(input);
    	if(null==input ||''==input || "undefined"== input){
    		req=0;
    	}else{
    		req = parseInt(input.substring(5,6)) + 1;
    	}
    	if(null==index ||''==index || "undefined"== index){
			index=0;
		}
    	var id = uuid();//生成id
    	var tr = "<tr class='inp'><td class='tc'><input type='checkbox' name='list["+req+"].id'  value='"+id+"'><input type='hidden' name='list["+req+"].id'  value='"+id+"'></td><td class='tc'> "+(parseInt(index)+1)+" </td><td class='tc'> <input type='text' name='list["+req+"].name' maxlength='5'> </td><td class='tc'> <input type='text' class='w100p' name='list["+req+"].compary' ></td><td class='tc'> <input type='text' name='list["+req+"].duty'></td><td class='tc'> <input type='text' name='list["+req+"].rank'></td></tr>";
    	$(obj).parents("form").find("tbody").append(tr);
    }
    
    //引用历史人员
    function selectHistory(obj){
    	//当前是抽取人员还是监督人员
    	var personType = $(obj).parents("form").attr("id");
    	//弹窗加载人员列表
    	var iframeWin;
        layer.open({
            type: 2,
            title: "引用历史人员",
            shadeClose: true,
            shade: 0.01,
            area: ['600px', '400px'],
            offset: '20px',
            content: globalPath+'/'+personType+'/toPeronList.do?personType='+personType, //iframe的url
            success: function (layero, index) {
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            },
            btn: ['保存']
            , yes: function () {
                iframeWin.chosePerson(obj);
            }
        });
    	
    }
    
    //删除
    function delPerson(obj){
    	$(obj).parents("form").find("tbody").find(":checked").each(function(){
    		$(this).parents("tr").remove();
    	});
    	//更新序号
    	var i=1;
    	$(obj).parents("form").find("tbody").find("tr").each(function(){
    		var o = $(this).find("td").eq(1).html(i++);
    	});
    	/*for ( var i = 1; i <= trs.length; i++) {
			$(trs[i-1]).find("td :eq(1)").html(i);
		}*/
    	
    	//修改inputlist序号
    	var j = 0;
    	$(obj).parents("form").find("tr").each(function(){
    		if($(this).find("input[type='text']").length>0){
    			$(this).find("td:eq(0) input[type=hidden]").prop('name', 'list['+j+'].id');
    			$(this).find("td:eq(0) input[type=checkbox]").prop('name', 'list['+j+'].id');
    			$(this).find("td:eq(2) input[type=text]").prop('name', 'list['+j+'].name');
    			$(this).find("td:eq(3) input[type=text]").prop('name', 'list['+j+'].compary');
    			$(this).find("td:eq(4) input[type=text]").prop('name', 'list['+j+'].duty');
    			$(this).find("td:eq(5) input[type=text]").prop('name', 'list['+j+'].rank');
    			j++;
    		}
    	});
    }
    
    //全选全不选
    function checkAll(obj){
    	$(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
    }
    
    
    /**供应商地区*/
    function areas() {
        var areas = $("#area").find("option:selected").val();
        $.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts_new/city.do",
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
                        skin: 'layer-default',
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
    
    function resetCondition(obj){
    	document.getElementById("form1").reset();
    	selectLikeSupplier();
    }
    
    /**满足条件供应商人数查询*/
    function selectLikeSupplier() {
        var area = document.getElementById("area").value;
        var typeCode = $("#supplierType").val();
        code = typeCode.toLowerCase();
        $.ajax({
            cache: true,
            type: "POST",
            dataType: "json",
            url: globalPath+'/SupplierCondition_new/selectLikeSupplierCount.do',
            data: $('#form1').serialize(),// 你的formid
            async: false,
            success: function (data) {
            	if(null!=data && "" !=null){
            		
            		for ( var k in data) {
            			;
            			if(k.substring(0,k.lastIndexOf("C")) == typeCode){
            				
            				scount = data[k];
            				if(parseInt(scount)==0){
            					$("#"+code+"Count").parents("button").prop("style","background-color: red;");
            				}else{
            					$("#"+code+"Count").parents("button").removeAttr("style");
            				}
            				$("#"+code+"Count").html(scount);
            			}
					}
            		
            		/*if(null!=data.PRODUCTCount){
            			products = data.PRODUCTCount;
            			$("#productCount").html(products);
            			//$(".productCount").html(productCount);
            			//window.sessionStorage.setIterm("products",data.products);
            		}
            		if(null!=data.SERVICECount){
            			services = data.SERVICECount;
            			$("#serviceCount").html(services);
            			//$(".serviceCount").html(serviceCount);
            			//window.sessionStorage.setIterm("services",data.services);
            		}
            		if(null!=data.SALESCount){
            			sales = data.SALESCount;
            			$("#salesCount").html(sales);
            			//$(".salesCount").html(salesCount);
            			//window.sessionStorage.setIterm("sales",data.sales);
            		}
            		if(null!=data.PROJECTCount){
            			projects = data.PROJECTCount;
            			$("#projectCount").html(projects);
            		}
            		if(null!=data.GOODSCount){
            			goods = data.GOODSCount;
            			$("#goodsCount").html(goods);
            		}*/
            	}else{
            		//layer.msg("没有满足条件的供应商,检查抽取条件");
            	}
            }
        });
        return false;
    }
    /**点击抽取--对参数进行校验*/
    function checkEmpty(){
    	
    	//$(".cue").empty();
    	/*$("#eError").empty();
    	$("#sError").empty();*/
    	$("#areaError").empty();
    	
    	var count = 0;
    	$(".star_red").each(function(){
    		$($(this).parents("li").find("input")).each(function(index, ele){
    			$(ele).parents("li").find(".cue").html("");
    			if(!ele.value){
    				count++;
    				$(ele).parents("li").find(".cue").html("不能为空");
    			}else{
    				$(ele).parents("li").find(".cue").html("");
    			}
    		});
    		
    		$($(this).parents("li").find("select")).each(function(index, ele){
    			if(!ele.value){
    				$(ele).parents("li").find(".cue").html("不能为空");
    				count++;
    			}else{
    				$(ele).parents("li").find(".cue").html("");
    			}
    		});
    	});
    	//限制地区理由是否填写
		if("0"!=$("#province").val() && (null ==$("#areaReson").val() ||""==$("#areaReson").val())){
			$("#areaError").html("不能为空");
			count++;
		}else{
			$("#areaError").val("");
		}
    	
    	if(count>0){
    		layer.msg("存在错误信息，检查及修改");
		}
    	
    	//校验人员信息
    	var count1 = 0;
    	var count2 = 0;
    	var count3 = 0;
    	$("#eError").html("");
    	$("#sError").html("");
    	$("#extractUser").find("input").each(function(){
    		if($(this).val().length<1){
    			count1++;
    		}
    	});
    	
    	
    	$("#supervise").find("input").each(function(){
    		if($(this).val().length<1){
    			count2++;
    		}
    	});
    	
    	if($("#extractUser").find("tr").length<3){
    		count1++;
    	}
    	if($("#supervise").find("tr").length<2){
    		count2++;
    	}
    	if(count1>0){
    		$("#eError").html("抽取人员必须两人以上且信息必须填写完整");
    		layer.msg("抽取人员必须两人以上且信息必须填写完整");
    	}
    	if(count2>0){
    		$("#sError").html("监督人员必填且必须填写完整");
    		layer.msg("监督人员必填且必须填写完整");
    	}
    	
    	$("#contactNumError").html("");  
    	//校验手机
    	var phone = $("[name='contactNum']").val();
        if(!phone){ 
            $("#contactNumError").html("不能为空");  
            count3++;
        }
        /*var phone = $("[name='contactNum']").val();
        if(!(/^1[34578]\d{9}$/.test(phone))){ 
            $("#contactNumError").html("手机号码有误，请重填");  
            count3++;
        }*/
    	
    	return count+count1+count2+count3;
    }
    
    function extractVerify() {
    	//清空错误提示
    	$("#extractUser").find("span").remove();
    	$("#supervise").find("span").remove();
    	
    	//所有的必填项写一个class 验证必填 输入框要验证长度
    	if(checkEmpty()+proError>0){
    		if(proError>0){
    			layer.msg("项目编号已存在");
    		}
    		return false;
    	}
    	$("#status").val(1);//修改状态为抽取中
    	
    	
    	var ExtractNum = $("#"+code+"ExtractNum").val();
    	if(parseInt(ExtractNum)>0){
    		$("#supplierType").find(".cue").html("");
    		if(ExtractNum>scount){
    			layer.msg("人数不足，无法抽取");
    		}else{
    			$("#"+code+"Result").find("tbody").empty();
    			extractSupplier(code);
    		}
    	}else{
    		$("#supplierType").find(".cue").html("请输入抽取数量");
    		layer.msg("请输入抽取数量");
    	}
    	
    	
    	//验证抽取条件中数量是否正确
       // var eCount = $("#supplierCount").val();//抽取总数量
        //获取每个类别的抽取数量
       /* if (positiveRegular(eCount)) {
            $("#countSupplier").text("");
            var projectExtractNum = $("#projectExtractNum").val()==""?0:$("#projectExtractNum").val();
            var productExtractNum = $("#productExtractNum").val()==""?0:$("#productExtractNum").val();
            var serviceExtractNum = $("#serviceExtractNum").val()==""?0:$("#serviceExtractNum").val();
            var salesExtractNum = $("#salesExtractNum").val()==""?0:$("#salesExtractNum").val();
            var goodsExtractNum = $("#goodsExtractNum").val()==""?0:$("#goodsExtractNum").val();
            var count = parseInt(projectExtractNum)+parseInt(productExtractNum)+parseInt(serviceExtractNum)+parseInt(salesExtractNum)+parseInt(goodsExtractNum);
            var count = $("#extractNumber").find("input").val();
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
                	extractSupplier();
                }
            } else {
                layer.msg("请输入有效人数(正整数)");
            }
        } else {
            $("#countSupplier").text("请输入有效总人数(正整数)");
        }*/
        return false;
    }
    /**点击抽取--判断是否完成本次抽取*/
   /* function fax() {
        $.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts_new/isFinish.do",
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
    }*/
    /**点击抽取--当选择参加与否后保存状态*/
    
    function extractSupplier(code) {
    	
    	var flag = 0;
		//存储项目信息
    	$.ajax({
    		type: "POST",
    		url: $("#projectForm").attr('action'),
    		data:$("#projectForm").serialize(),
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			
    			if(null!=msg && "" !=msg){
    				flag ++;
    				for ( var k in msg) {
    					$("#"+k+"Error").html(msg[k]);
    				}
    			}
    		}
    	});
    	//存储人员信息
    	$.ajax({
    		type: "POST",
    		url: $("#supervise").attr('action'),
    		data:  $("#supervise").serialize(),
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			$("#supervise").find("span").empty();
    			if(null !=msg){
    				flag++;
    				for ( var k in msg) {
    					if("All"!=k){
    						$("#supervise").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
    					}else{
    						$("#sError").html(msg[k]);
    					}
					}
    			}else{
    				$("#sError").empty();
    			}
    		}
    	});
    	
    	$.ajax({
    		type: "POST",
    		url: $("#extractUser").attr('action'),
    		data:  $("#extractUser").serialize(),
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			$("#extractUser").find("span").empty();
    			if(null !=msg){
    				flag++;
    				for ( var k in msg) {
    					if("All"==k){
    						$("#eError").html(msg[k]);
    					}else{
    						$("#extractUser").find("[name='"+k+"']").parent().append("<span class='red'>"+msg[k]+"</span>");
    					}
    				}
    			}else{
    				$("#eError").empty();
    			}
    		}
    	});	
		
	    if(flag!=0){
	    	layer.msg("存在错误信息，检查及修改");
	    	return false;
		}
	    formData = $('#form1').serialize();
	    
	    //存储条件
	    $.ajax({
    		type: "POST",
    		url: globalPath+"/SupplierCondition_new/saveCondition.html",
    		data:  formData,
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    		}
    	});	
	    
	    //显示抽取结果表
	    $("#result").removeClass("dnone");
	    $("#"+code+"Result").removeClass("dnone");
	    //追加抽取结果
	    appendTd(0,$("#"+code+"Result").find("tbody"),null);
	   
    	/*var count = 0;
    	var projectExtractNum = $("#projectExtractNum").val();
    	if(projectExtractNum>0){
    		if(projectExtractNum<=projects){
    			$("#projectResult").find("tbody").empty();
    			appendTd(0,$("#projectResult").find("tbody"),null);
    		}else{
    			layer.msg("工程条件不满足");count++;
    		}
    	}else if(projectExtractNum=="0"){
    		layer.msg("请输入工程抽取数量");count++;
    	}
    	
    	var productExtractNum = $("#productExtractNum").val();
    	if(productExtractNum>0){
    		if(productExtractNum<=products){
    			$("#productResult").find("tbody").empty();
    			appendTd(0,$("#productResult").find("tbody"),null);
    		}else{
    			layer.msg("生产条件不满足");count++;
    		}
    	}else if(productExtractNum=="0"){
    		layer.msg("请输入生产抽取数量");count++;
    	}
    	
    	var serviceExtractNum = $("#serviceExtractNum").val();
    	if(serviceExtractNum>0){
    		if(serviceExtractNum<=services){
    			$("#serviceResult").find("tbody").empty();
    			appendTd(0,$("#serviceResult").find("tbody"),null);
    		}else{
    			layer.msg("服务条件不满足");count++;
    		}
    	}else if(serviceExtractNum=="0"){
    		layer.msg("请输入服务抽取数量");count++;
    	}
    	
    	var salesExtractNum = $("#salesExtractNum").val();
    	if(salesExtractNum>0){
    		if(salesExtractNum<=sales){
    			$("#salesResult").find("tbody").empty();
    			appendTd(0,$("#salesResult").find("tbody"),null);
    		}else{
    			layer.msg("销售条件不满足");count++;
    		}
    	}else if(salesExtractNum=="0"){
    		layer.msg("请输入销售抽取数量");count++;
    	}
    	
    	
    	var goodsExtractNum = $("#goodsExtractNum").val();
    	if(goodsExtractNum>0){
    		if(salesExtractNum<=goods){
    			$("#goodsResult").find("tbody").empty();
    			appendTd(0,$("#goodsResult").find("tbody"),null);
    		}else{
    			layer.msg("销售条件不满足");count++;
    		}
    	}else if(goodsExtractNum=="0"){
    		layer.msg("请输入销售抽取数量");count++;
    	}
    	
    	
    	if(count>0){
    		return false;
    	}*/
    	
    	//输入框设置只读
    	$('.extractVerify_disabled input,.extractVerify_disabled select').each(function() {
    		$(this).prop('disabled', true);
    	});
    	//按钮置灰
    		
		$(".bu").each(function(){
			$(this).attr("disabled",true);
		});
    	
    	//存储项目信息
    	/*	$.ajax({
    		type: "POST",
    		url: $("#projectForm").attr('action'),
    		data:$("#projectForm").serialize(),
    		dataType: "text",
    		success: function (msg) {
    			
    		}
    	});
    	//存储人员信息
    	$.ajax({
    		type: "POST",
    		url: $("#supervise").attr('action'),
    		data:  $("#supervise").serialize(),
    		dataType: "json",
    		success: function (msg) {
    			
    		}
    	});
    	$.ajax({
    		type: "POST",
    		url: $("#extractUser").attr('action'),
    		data:  $("#extractUser").serialize(),
    		dataType: "json",
    		success: function (msg) {
    			
    		}
    	});*/
    		
    }
    
    //显示结果   obj 是当前操作的行所在的tbody
    function appendTd(num,obj,result){
    	var flag = false;
    	
    	//只能有一个请选择
    	$(obj).find("select").each(function(){
    		if($(this).val()==0){
    			flag = true;
    			return ;
    		}
    	});
    	
    	if(flag){
    		return;
    	}
    	
    	//获取类型
    	var type =  $(obj).parents("div").attr("id").substring(0,$(obj).parents("div").attr("id").lastIndexOf("R"));
    	//需要判断能参加，满足后不再追加
    	var agreeCount=0;
    	
    	//计数  确认参加人数
    	//agreeCount = parseInt($(type+"ExtractNum").val())-$(obj).find("select").length;
    	
    	agreeCount = parseInt($(obj).parent().prev().find("span:first").html());
    	
    	
    	if($("#"+type+"ExtractNum").val()==agreeCount){
    		$("#end").removeClass("dnone");
    		return false;
    	}
    	if($("#"+type+"ExtractNum").val()==$(obj).find("tr").length ){
    		//$("#end").removeClass("dnone");
    		return false;
    	}
    	
    	var data;
    	var projects;
    	var products;
    	var services;
    	var sales;
    	var goods;
    	//去后台请求一条数据
    	$.ajax({
    		type: "POST",
    		url: globalPath+'/SupplierCondition_new/selectLikeSupplier.do',
    		data: formData ,
    		dataType: "json",
    		async:false,
    		success: function (msg) {
    			if(null != msg.list){
    				var su = msg.list;
    				if(null !=su.PROJECT){
    					projects =su.PROJECT;
    				}
    				if(null !=su.SERVICE){
    					services = su.SERVICE;
    				}
    				if(null !=su.PRODUCT){
    					products = su.PRODUCT;
    				}
    				if(null !=su.SALES){
    					sales = su.SALES;
    				}
    				if(null !=su.GOODS){
    					goods = su.GOODS;
    				}
    			}else{
    				flag = true;
    			}
    			if(null !=msg){
	    			$("#"+msg.error).html("不能为空");
	    			flag = false;
	    		}
    		}
    	});
    	
    	if(agreeCount==$(obj).find("tr").length && flag){
    		$("#end").removeClass("dnone");
    		return ;
    	}
    	
    	if(flag){
    		return;
    	}
    	
    	var typeName;
    	
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
    	}else if("goods" == type){
    		data = goods;
    		typeName = data[0].supplierType;
    	}
    	if(null ==data || "undefined" ==data || data.length<1){
    		return false;
    	}
    	
    	var i = 0;
    	var armyBuinessTelephone = "";
    	if(data[i].armyBuinessTelephone){
    		armyBuinessTelephone = data[i].armyBuinessTelephone;
    	}
    	var armyBuinessMobile = "";
    	if(data[i].armyBuinessMobile){
    		armyBuinessMobile = data[i].armyBuinessMobile;
    	}
    	var armyBusinessName = "";
    	if(data[i].armyBusinessName){
    		armyBusinessName = data[i].armyBusinessName;
    	}
    	var tex = "<tr class='cursor' typeCode='"+type.toUpperCase()+"' sid='"+data[i].id+"' index='"+i+"'>" +
	   	 "<td  >"+(parseInt(num)+1)+"</td>" +
		 "<td   >"+data[i].supplierName+"</td>" +
	     "<td  >"+typeName+"</td>" +
	     "<td >"+armyBusinessName+"</td>" +
	     "<td  >"+armyBuinessMobile+"</td>" +
	     "<td  >"+armyBuinessTelephone+"</td>" +
	     "<td  class='res'><select onchange='operation(this)'> <option value='0'>请选择</option> <option value='1'>能参加</option> <option value='2'>待定</option> <option value='3'>不能参加</option> </td>" +
	     "</tr>";
    	$(obj).append(tex);
    	//更新序号
    	var i=1;
    	$(obj).find("tr").each(function(){
    		var o = $(this).find("td").eq(0).html(i++);
    	});
    	
    	return true;
    }
    
    
    /**
     * 追加结果到项目实施页面
     */
    function appendParent(obj){
		var tbody=window.opener.document.getElementById("supplierList");
		var index = $(tbody).find("tr:last").find("td:first").html();
		if(index){
			index ++;
		}else{
			index = 1;
		}
		$(obj).find("td:first").html(index);
		$(tbody).append(obj);
	}
    
    /**暂存*/
    function temporary(status) {
    	//存储项目信息
    	 $.ajax({
             cache: true,
             type: "POST",
             dataType: "json",
             url: globalPath+'/SupplierCondition_new/saveSupplierCondition_new.do',
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
            btn: ['打印', '取消'], offset: ['40%', '40%'], shade: 0.01, skin: 'layer-default'
        }, function (index) {
            window.location.href = globalPath+"/SupplierExtracts_new/showRecord.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
        }, function (index) {
            window.location.href = globalPath+"/SupplierExtracts_new/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
            layer.close(index);
        });
    }
    /**展示品目*/
    function opens(cate) {
    	//获取类别
    	//var typeId = $("#supplierTypeId").val();
    	var typeCode = $(cate).attr("typeCode");
    	//重置资质tree
    	//loadQuaList("init");
    	//cate.value = "";
        //  iframe层
        var iframeWin;
        var categoryId = $("#"+code+"CategoryIds").val();
        var parentId = $(cate).prev(".parentId");
        if(parentId.length<=0){
        	$(cate).before("<input class='parentId' type='hidden' name='"+code+"ParentId'>");
        }else if($(parentId).val()){
        	categoryId += ","+ $(parentId).val();
        }
        layer.open({
            type: 2,
            title: "选择条件",
            shadeClose: true,
            shade: 0.01,
            area: ['430px', '400px'],
            skin: 'layer-default',
            offset: '20px',
            content: globalPath+'/SupplierExtracts_new/addHeading.do?supplierTypeCode='+typeCode+'&categoryId='+categoryId, //iframe的url
            //content: globalPath+'/supplier/category_type.do?code='+supplierCode, 
            success: function (layero, index) {
                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            },
            btn: ['保存', '重置']
            , yes: function () {
                iframeWin.getChildren(cate);
                initTypeLevelId();
                //若是工程类，需要根据品目去动态生成等级树
                //if(typeCode == "PROJECT"){
                	//加载资质类型
                	emptyQuaInfo(code);
                	loadQuaList(null);
               // }
                selectLikeSupplier();
            }
            , btn2: function () {
            	$(cate).parents("li").find(".categoryId").val("");
            	$(cate).parents("li").find(".parentId").val("");
                $(cate).val("");
                selectLikeSupplier();
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
    	            url: globalPath+"/SupplierExtracts_new/supplieType.do",
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
	
     //加载供应商类型下拉框
     function loadSupplierType(){
    	 var typeCode = $("#projectType").val();
    	 //项目实施地区
    	 if("GOODS"==typeCode){
    		 $("#xmss").html("");
    		 $("#xmss").removeClass("star_red");
    	 }else{
    		 $("#xmss").html("*");
    		 $("#xmss").addClass("star_red");
    	 }
    	 if(null!=typeCode&&''!=typeCode){
    		 $.ajax({
 	            type: "POST",
 	            async: false,
 	            url: globalPath+"/SupplierExtracts_new/supplieType2.do",
 	            dataType: "json",
 	            data:{typeCode:typeCode},
 	            success: function (data) {
 	            	$("#supplierType").empty();
 	            	initCategoryAndLevel(null);
 	            	if(data.length>1){
        				$("#supplierType").append("<option value='GOODS'> 不限 </option>");
        			}
 	            	
            		for(var i=0;i<data.length;i++){
            			$("#supplierType").append("<option value='"+data[i].code+"'> "+data[i].name+" </option>");
            		}
 	            	showCategoryAndLevel(typeCode);
            		selectLikeSupplier();
 	            }
 	        });
    	 }
     }
     
     //根据初始化 品目 等级div
     function initCategoryAndLevel(obj){
    	 var mycars=new Array("service","project","product","sales","goods");
    	 for ( var i=0;i<5;i++) {
    		 $("."+mycars[i]+"Count").addClass("dnone");
    		 $("#result").addClass("dnone");
    		 $("#"+mycars[i]+"Result").addClass("dnone");
    		// $("#"+mycars[i]+"CategoryIds").val("");
    		 $("#"+mycars[i]+"ExtractNum").val("");
    		 $("#"+mycars[i]).val("");
		}
    	//obj 供应商类型
    	if(null!=obj){
    		/*var types = $(obj).val().split(",");
    		for(var type in types ){
    			showCategoryAndLevel(types[type]);
    		}*/
    		var type = $(obj).val();
    		showCategoryAndLevel(type);
    		selectLikeSupplier();
    	}
     }
     
     //显示品目等级信息div
     function showCategoryAndLevel(code){
    	 /*if ('PROJECT' == code) {
    		 //查询数量
    		 //selectTypeCount("PROJECT");
    		 $(".projectCount").removeClass("dnone");
    		 //追加结果表
    		 $("#projectResult").removeClass("dnone");
    	 } else if ('SERVICE' == code) {
    		 //selectTypeCount("SERVICE");
    		 $(".serviceCount").removeClass("dnone");
    		 //加载服务等级树
    		 loadLevelTree("serviceLevelTree");
    		 //追加结果表
    		 $("#serviceResult").removeClass("dnone");
    	 } else if ('PRODUCT' == code) {
    		 //selectTypeCount("PRODUCT");
    		 $(".productCount").removeClass("dnone");
    		 //加载生产等级树
    		 loadLevelTree("productLevelTree");
    		 //追加结果表
    		 $("#productResult").removeClass("dnone");
    	 } else if ('SALES' == code) {
    		 //selectTypeCount("SALES");
    		 $(".salesCount").removeClass("dnone");
    		 //销售等级树
    		 loadLevelTree("salesLevelTree");
    		 //追加结果表
    		 $("#salesResult").removeClass("dnone");
    	 }*/
    	var typeCode = $("#supplierType").val();
    	code = typeCode.toLowerCase();
    	
    	$("#extractNumber").find("input").attr("id",code+"ExtractNum");
    	$("#extractNumber").find("input").attr("name",code+"ExtractNum");
    	 $("."+code+"Count").removeClass("dnone");
		 //追加结果表
		// $("#"+code+"Result").removeClass("dnone");
		 //$("#"+code+"ExtractNum").val("0");
		 /*if(code != "project"){
			 loadLevelTree(code+"LevelTree");
		 }*/
		 loadQuaList(null);
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
            url: globalPath+"/SupplierExtracts_new/city.do",
            dataType: "json",
            type: "post",
          },
          check: {
            enable: true,
            chkboxType: {
              "Y": "s",
              "N": "ps"
            },
            chkStyle : "checkbox" 
           // autoCheckTrigger: true
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
                onAsyncSuccess:selectAllArea
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
	
	//默认选中全国
	function selectAllArea(){
		var treeObj=$.fn.zTree.getZTreeObj("treeArea");
		treeObj.checkAllNodes(true);
		showCheckArea(treeObj);
		
	}
	
	//地区树选中处理
	function showCheckArea(treeObj){
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
				if(areas[i].id == "0"){
					//隐藏地区限制理由
					$("#areaReson").parents("li").addClass("dnone");
					break;
				}else{
					$("#areaReson").parents("li").removeClass("dnone");
				}
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
	
	//递归取消父节点选中状态
	function dischecked(treeNode,treeObj){
		var node = treeNode.getParentNode();
		if(null !=node){
			treeObj.checkNode(node, false);
			dischecked(node,treeObj);
		}
	}
	//获取选中节点地区
	function choseArea(event,treeId,treeNode){
		var treeObj=$.fn.zTree.getZTreeObj("treeArea");
		dischecked(treeNode,treeObj);
		showCheckArea(treeObj);
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
        $(".projectCount").addClass("dnone");
        $(".serviceCount").addClass("dnone");
        $(".productCount").addClass("dnone");
        $(".salesCount").addClass("dnone");
        
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
            var typeCode = nodes[i].code.toLowerCase();
            $("#"+typeCode+"Result").removeClass("dnone");
           /* if ('PROJECT' == nodes[i].code) {
            	//查询数量
            	//selectTypeCount("PROJECT");
                $(".projectCount").removeClass("dnone");
              //追加结果表
                $("#projectResult").removeClass("dnone");
            } else if ('SERVICE' == nodes[i].code) {
            	//selectTypeCount("SERVICE");
                $(".serviceCount").removeClass("dnone");
                //加载服务等级树
                loadLevelTree("serviceLevelTree");
                //追加结果表
                $("#serviceResult").removeClass("dnone");
            } else if ('PRODUCT' == nodes[i].code) {
            	//selectTypeCount("PRODUCT");
                $(".productCount").removeClass("dnone");
                //加载生产等级树
                loadLevelTree("productLevelTree");
              //追加结果表
                $("#productResult").removeClass("dnone");
            } else if ('SALES' == nodes[i].code) {
            	//selectTypeCount("SALES");
                $(".salesCount").removeClass("dnone");
                //销售等级树
                loadLevelTree("salesLevelTree");
              //追加结果表
                $("#salesResult").removeClass("dnone");
            }*/
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
    		url:globalPath+'/SupplierCondition_new/selectLikeSupplier.do',
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
    
    //清空资质显示
    function emptyQuaInfo(code){
		$("#"+code+"QuaName").val("");
		$("#"+code+"QuaId").val("");
    }
    
    
    //加载资质信息
    function loadQuaList(nodes){
    	//获取当前供应商code
    	code = $("#supplierType").val().toLowerCase();
    	
    	if(nodes==null){
    		//emptyQuaInfo(code);
    		if("project"!=code){
    			$("#goodsQuaContent").find("ul").prop("id",code+"QuaTree");
    		}
    		var cateId = $("#"+code+"CategoryIds").val();
    		var parentId = $("[name='"+code+"ParentId']").val();
    		
    		$.ajax({
    			url:globalPath+"/SupplierCondition_new/getQuaByCid.do",
    			type: "POST",
    			dataType: "json",
    			async:false,
    			data:{categoryId:cateId,supplierTypeCode:code,parentId:parentId},
    			success:function(data){
    				nodes = data;
    			}
    		});
    	}
    	
    	
    	nodes = ajaxDataFilter(nodes);
    	/*if(!nodes){
    		return ;
    	}*/
    	
    	var setting = {
         check: {
        	 autoCheckTrigger: true,
           enable: true,
           chkboxType: {
             "Y": "s",
             "N": "ps"
           },
           chkStyle : "checkbox" 
          // autoCheckTrigger: true
         },
         data: {
           simpleData: {
             enable: true,
             idKey: "id",
             pIdKey: "parentId",
           },
           key: {
				children: "nodes"
			}
         },
         callback: {
        	 //beforeClick: beforeClickQua,
             onCheck: choseQua
         }
       };
	
    	var quaTree = $.fn.zTree.init($("#"+code+"QuaTree"), setting,nodes);
		   //var treeObj = $.fn.zTree.getZTreeObj(code+"QuaTree");
       //treeObj.checkAllNodes(true);
       //choseQua('',"quaTree",'');
    }
    
    //加载资质树时回显
    function ajaxDataFilter(responseData) {
		//获取要选中的数据
    	var checkedNode = "";
    	if($("#"+code+"QuaId").val()){
    		checkedNode = $("#"+code+"QuaId").val().split(",");
    	}else{
    		return responseData;
    	}
		if(responseData) {
			// 判断如果
			for(var i = 0; i < responseData.length; i++) {
				for ( var k in checkedNode) {
					if(responseData[i].id == checkedNode[k]){
						responseData[i].checked = true;
					}
				}
			}
		}
		return responseData;
	}
    
    
    //加载工程等级树
    function loadprojectLevelTree(){
    	//var cateId = $("#projectCategoryIds").val();
    	var qid = $("#projectQuaId").val();
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
    	if(null==qid || ''==qid){
    		qid =$("#projectCategoryIds").val();
    		if(null!=qid&&""!=qid){
    			$.ajax({
    				url:globalPath+"/SupplierCondition_new/getEngLevelByCid.do",//根据品目ID 获取资质等级
    				data:{categoryId:qid},
    				//url:globalPath+"/qualification/getLevelByQid.do",//根据资质编号ID 获取资质等级
    				async:false,
    				dataType:"json",
    				success:function(datas){
    					if(null != datas && "undefind"!= datas && ''!=datas){
    						
    						var treeLevelType = $.fn.zTree.init($("#projectLevelTree"), setting, datas);
    						//checkAllNodes("projectLevelTree");//选中所有节点
    						//onCheckLevel("projectLevelTree");//处理选中节点
    					}else{
    						layer.msg("未能查询出结果");
    					}
    				}
    			});
    		}
    	}else{
    		$.ajax({
    			/*url:globalPath+"/category/getEngLevelByCid.do",//根据品目ID 获取资质等级
		 	data:{categoryId:cateId},*/
    			url:globalPath+"/SupplierCondition_new/getLevelByQid.do",//根据资质编号ID 获取资质等级
    			data:{qid:qid},
    			async:false,
    			dataType:"json",
    			success:function(datas){
    					if(null != datas && "undefind"!= datas && ''!=datas){
    					
    						var treeLevelType = $.fn.zTree.init($("#projectLevelTree"), setting, datas);
    						//checkAllNodes("projectLevelTree");//选中所有节点
    						//onCheckLevel("projectLevelTree");//处理选中节点
    				}else{
    					layer.msg("未能查询出结果");
    				}
    			}
    		});
    	}
    }
    //加载等级树
    function loadLevelTree(treeName){
    		//var typeCode = $("#supplierType").val();
    		var zNodes ;
    		/* 判断类别 生成不同的级别菜单 */
			if(treeName == "productLevelTree" || treeName == "goodsLevelTree"){
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
	            	//onAsyncSuccess:checkAllNodes(treeName),
	                beforeClick: beforeClickLevel,
	                onCheck: onCheckLevel
	            }
	        };
	        var treeLevelType = $.fn.zTree.init($("#"+treeName), setting, zNodes);
	        //checkAllNodes(treeName);//选中所有节点
	       
    }
	
	//等级树加载完成后全选等级
	function checkAllNodes(treeName){
		var treeObj = $.fn.zTree.getZTreeObj(treeName);
		treeObj.checkAllNodes(true);
		onCheckLevel(treeName);//处理选中节点
	}
	
	//展示等级树
	function showLevel(obj){
		var cateId = $(obj).parents("li").find(".categoryId").val();
		var levelType = $(obj);
        var treeHome = levelType.attr("treeHome");
        var levelOffset = $(obj).offset();
        var LevelType =  $(obj).attr("id");
        
        if(null == $.fn.zTree.getZTreeObj(LevelType+"Tree")){
        	loadLevelTree(LevelType+"Tree");
        }
        
        if($(obj).attr("id")=="projectLevel"){
        	var quaId = $("#projectQuaId").val();
        	if(null!=quaId&& ""!=quaId){
        		$("#"+treeHome).css({
                    left: levelOffset.left + "px",
                    top: levelOffset.top + levelType.outerHeight() + "px"
                }).slideDown("fast");
        		$("body").bind("mousedown", onBodyDownProjectLevel);
        	}else{
        		layer.msg("请选择工程资质");
        	}
        }else{
        	//if(cateId !="undefined" && cateId != ""){
    		$("#"+treeHome).css({
                left: levelOffset.left + "px",
                top: levelOffset.top + levelType.outerHeight() + "px"
            }).slideDown("fast");
    		if("productLevel" == LevelType){
    			$("body").bind("mousedown", onBodyDownProductLevel);
    		}else  if("salesLevel" == LevelType){
    			$("body").bind("mousedown", onBodyDownSalesLevel);
    		}else  if("serviceLevel" == LevelType){
    			$("body").bind("mousedown", onBodyDownServiceLevel);
    		}else  if("goodsLevel" == LevelType){
    			$("body").bind("mousedown", onBodyDownGoodsLevel);
    		}
        	//}else{
        	//	layer.msg("请选择品目");
        	//}
        }
        
	}
	
	//展示资质等级
	/*function showQuaLevel(obj){
		var quaId = $("#quaId").val();
		if(null!=quaId&& ""!=quaId){
			$("body").bind("mousedown", onBodyDownProjectLevel);
		}else{
			layer.msg("请选择工程资质");
		}
		
	}*/
	
	//显示资质信息
	function showQua(obj){
		var levelType = $(obj);
		var treeHome = $(levelType).attr("treeHome");
			var levelOffset = $(obj).offset();
			if("projectQuaContent"==treeHome){
				$("#projectQuaContent").css({
				left: levelOffset.left + "px",
				top: levelOffset.top + levelType.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownQua);
			}else{
				$("#goodsQuaContent").css({
					left: levelOffset.left + "px",
					top: levelOffset.top + levelType.outerHeight() + "px"
					}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownElseQua);
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
    function onBodyDownGoodsLevel(event) {
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("goodsLevelContent");
    	}
    }
    function onBodyDownQua(event){
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("projectQuaContent");
    	}
    }
    function onBodyDownElseQua(event){
    	if (!(event.target.nodeName == "SPAN")) {
    		hideLevelType("goodsQuaContent");
    	}
    }
    //隐藏等级树
    function hideLevelType(obj) {
        $("#"+obj).fadeOut("fast");
        if("serviceLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownServiceLevel);
        	selectLikeSupplier();
        }else if("salesLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownSalesLevel);
        	selectLikeSupplier();
        }else if("projectLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownProjectLevel);
        	selectLikeSupplier();
        }else if("productLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownProductLevel);
        	selectLikeSupplier();
        }else if("projectQuaContent"==obj){
        	$("body").unbind("mousedown", onBodyDownQua);
        	selectLikeSupplier();
        }else if("goodsLevelContent"==obj){
        	$("body").unbind("mousedown", onBodyDownGoodsLevel);
        	selectLikeSupplier();
        }else if("goodsQuaContent"==obj){
        	$("body").unbind("mousedown", onBodyDownElseQua);
        	selectLikeSupplier();
        }
        //selectLikeSupplier();

    }

    function beforeClickLevel(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeLevelType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }
    function beforeClickArea(treeId, treeNode) {
       
    }

    function beforeClickQua(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("quaTree");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
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
    
    //工程资质被选中后
    function choseQua(event, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj(treeId);
    	if(null != zTree){
    		var nodes = zTree.getCheckedNodes(true);
    		v = "";
    		var rid = "";
    		for (var i = 0, l = nodes.length; i < l; i++) {
    			v += nodes[i].name + ",";
    			rid += nodes[i].id + ",";
    		}
    		if (v.length > 0) v = v.substring(0, v.length - 1);
    		if (rid.length > 0) rid = rid.substring(0, rid.length - 1);
    		//加载资质
    		$("#"+code+"QuaId").val(rid);
    		$("#"+code+"QuaName").val(v);
    		if("project"==code){
    			loadprojectLevelTree();
    		}
    	}
    }
    
   
    
    
    /**抽取级别----end----*/
    /**选择参加与否选项后自动触发*/
   /* function operation(select) {
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
                    saveResult(objTr, value,2);
                    layer.close(index);
                    //select.options[0].selected = true;
                	appendTd(req,obj,"不能参加");
                	//删除
                	$(objTr).remove();
                	
                });
            } else if(v == "1"){
                //select.disabled = true;
            	$(select).parents("td").html("能参加");
            	select.remove();
                saveResult(objTr, '',1);
            	appendTd(req,obj,"能参加");
            }else{
            	appendTd(req,obj,"待定");
            }
        }, function (index) {
            layer.close(index);
            select.options[0].selected = true;
        });
    }*/
    
    
    function operation(select) {
        var x, y;
        var oRect = select.getBoundingClientRect();
        x = oRect.left - 450;
        y = oRect.top - 150;
        layer.confirm('确定本次操作吗？', {
            btn: ['确定', '取消'], shade: 0.01, skin: 'layer-default'
        }, function (index) {
            var strs = new Array();
            var v = select.value;
            var obj = $(select).parents("tbody");
            var objTr = $(select).parents("tr");
            var req = $(obj).find("tr:last").find("td:first").html();
            if (objTr.next().length > 0) {
            	req = obj.find('tr').last().index();
            }
           // strs = v.split(",");
            layer.close(index);
            if (v == "3") {
                layer.prompt({
                    formType: 2,
                    shade: 0.01,
                    offset: [y, x],
                    title: ['*  不参加理由','color:red'],
                    btn:'确定',
                    closeBtn: 0
                }, function (value, index, elem) {
                	saveResult(objTr, value,0);
                	var notJoin = $(obj).parents("table").prev().find(".notJoin").html();
                	$(obj).parents("table").prev().find(".notJoin").html(parseInt(notJoin)+1);
                	//$(obj).prev().find(".notJoin").html(parseInt($(obj).panrents("table").prev().find(".notJoin").html())+1);
                	if(value){
                		layer.close(index);
                	}
                    //select.options[0].selected = true;
                    $(objTr).remove();
                	if(!appendTd(parseInt(req)-1,obj,"不能参加")){
                		var i=1;
                    	$(obj).find("tr").each(function(){
                    		var o = $(this).find("td").eq(0).html(i++);
                    	});
                	}
                	//删除
                });
            } else if(v == "1"){
                //select.disabled = true;
            	var parentsTr = objTr;
            	saveResult(parentsTr, '',1);
            	var yes = $(obj).parents("table").prev().find("span:first").html();
            	$(obj).parents("table").prev().find("span:first").html(parseInt(yes)+1);
            	//$(select).parents("td").empty();
            	$(select).parents("td").html("能参加");
            	appendTd(req,obj,"能参加");
            }else{
            	saveResult(objTr, '',2);
            	
    			appendTd(req,obj,"待定");
            }
        }, function (index) {
            layer.close(index);
            select.options[0].selected = true;
        });
    }
    
    
    //存储成功
    var successCount = 0;
    /**
     * 存储抽取结果
     */
    function saveResult(objTr, reason,join) {//obj:当前处理完成供应商信息、行  v:不能参加理由
    	//成功通知次数
    	var successCount = 0;
    	var supplierType = objTr.attr("typeCode");
    	var conditionId = $("#conditionId").val();
    	var recordId = $("#recordId").val();
    	var packageId = $("#packageId").val();
    	var sid = objTr.attr("sid");
    	$.ajax({
            type: "POST",
            url: globalPath+"/SupplierExtracts_new/saveResult.do",
            data: {reason: reason, conditionId: conditionId,supplierId:sid,supplierType:supplierType,join:join,recordId:recordId,projectType:projectType,packageId:packageId},
            dataType: "json",
            async:false,
            success: function () {
            	successCount++;
            }
    	});
    	
    	//追加到项目实施页面
    	
    	if(projectType){
    		var parentsTr = $(objTr).clone();
    		$(parentsTr).find("td:last").remove();
    		appendParent(parentsTr);
    	}
    	
    }
    
    
    
    
    
    
  //文本编译器计数
    function size(par) { 
    	var max = 500; 
    	if (par.value.length < max) 
    	var str = max - par.value.length;
    	$("#textCount").html(str.toString()); 
    } 
