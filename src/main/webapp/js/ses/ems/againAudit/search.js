// 搜索
function allotList_search() {
  var relName = $('[name=relName]').val();  // 获取采购机构名称
  var auditAt = $('[name=auditAt]').val();  // 获取提交复审时间
  $('#list_content').listConstructor({
    data: {
      orgName: relName,
      updatedAt: auditAt
    },
    url: list_url
  });
}

// 搜索
function batchList_search() {
  var batchNumber = $('[name=batchNumber]').val();  // 获取批次编号
  var batchName = $('[name=batchName]').val();  // 获取批次名称
  var createdAt = $('[name=createdAt]').val();  // 获取批次创建时间
  $('#list_content').listConstructor({
    data: {
      batchNumber: batchNumber,
      batchName: batchName,
      createdAt: createdAt
    },
    url: list_url
  });
}