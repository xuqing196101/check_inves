// 搜索
function againAudit_search(url) {
  var batchNumber = $('[name=batchNumber]').val();  // 获取批次编号
  var batchName = $('[name=batchName]').val();  // 获取批次名称
  var createdAt = $('[name=createdAt]').val();  // 获取批次创建时间
  $('#list_content').listConstructor({
    data: {
      batchNumber: batchNumber,
      batchName: batchName,
      createdAt: createdAt
    },
    url: url
  });
}