//暂存
function isTemporary() {
    $.post(globalPath + "/mulQuo/openBidSave.do?", $("#myForm").serialize(),
        function (data) {
            if (data.status == 200) {
                alert("暂存成功");
            }
        });
}
/**
 * 保存
 */
function save() {
    var projectId = $("#projectId").val();
    $.post(globalPath + "/mulQuo/openBidSave.do?", $("#myForm").serialize(),
        function (data) {
            if (data.status == 200) {
                window.location.href = globalPath + "/mulQuo/priceBuild.html?projectId=" + projectId;
            }
        });
}
