// 查看附件
function viewAttach(url, title) {
	layer.open({
	  type: 2, // page层
	  area: ['880px', '440px'],
	  title: title,
	  closeBtn: 1,
	  shade: 0.01, // 遮罩透明度
	  moveType: 1, // 拖拽风格，0是默认，1是传统拖动
	  shift: 1, // 0-6的动画形式，-1不开启
	  offset: '60px',
	  shadeClose: false,
	  content: globalPath + url,
	});
}