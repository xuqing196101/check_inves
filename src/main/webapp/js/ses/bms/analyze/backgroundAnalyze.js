$(function() {
	// 登录
	$.post(globalPath+"/analyze/getLoginCountByEmp.do?",
			function(data) {
				var loginTotal = '共' + data.subtext + '人';
				var loginjson = data.analyzes;
				var login;
				var loginOption = {
					title : {
						text : '用户登录统计',
						subtext : loginTotal,
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{a} <br/>{b} : {c} 人"
					},
					legend : {
						orient : 'vertical',
						x : 'left',
						data : [ '后台用户', '供应商', '专家' ]
					},
					calculable : true,
					series : [ {
						name : '用户登录统计',
						type : 'pie',
						radius : '55%',// 饼图的半径大小
						center : [ '50%', '60%' ],// 饼图的位置
						data : loginjson
					} ]
				};
				login = echarts.init(document.getElementById('login'));
				login.setOption(loginOption);
			});

	// 注册
	$.post(globalPath+"/analyze/getRegisterCountByEmp.do?",
		function(data) {
			var registerTotal = '共' + data.subtext + '人';
			var registerjson = data.analyzes;
			var register;
			var registerOption = {
				title : {
					text : '用户注册统计',
					subtext : registerTotal,
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} 人"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : [ '后台用户', '供应商', '专家' ]
				},
				calculable : true,
				series : [ {
					name : '用户注册统计',
					type : 'pie',
					radius : '55%',// 饼图的半径大小
					center : [ '50%', '60%' ],// 饼图的位置
					data : registerjson
				} ]
			};
			register = echarts.init(document
					.getElementById('register'));
			register.setOption(registerOption);
	});

	// 附件
	$.post(globalPath+"/analyze/getFileCountByEmp.do?",
		function(data) {
			var uploadTotal = '共' + data.subtext + '个附件';
			var uploadPicjson = data.analyzes;
			var uploadPic;
			var uploadPicOption = {
				title : {
					text : '附件上传统计',
					subtext : uploadTotal,
					x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} "
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : [ '后台用户', '供应商', '专家' ]
				},
				calculable : true,
				series : [ {
					name : '图片上传统计',
					type : 'pie',
					radius : '55%',// 饼图的半径大小
					center : [ '50%', '60%' ],// 饼图的位置
					data : uploadPicjson
				} ]
			};
			uploadPic = echarts.init(document.getElementById('uploadPic'));
			uploadPic.setOption(uploadPicOption);
	});
});

