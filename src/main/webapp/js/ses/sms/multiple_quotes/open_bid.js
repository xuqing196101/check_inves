//暂存
function isTemporary() {
	$.post(globalPath + "/mulQuo/openBidSave.do?", $("#myForm").serialize(),
			function(data) {
				alert(data);
			});
}
