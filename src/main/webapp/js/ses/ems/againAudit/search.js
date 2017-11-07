// 复审分配列表搜索
function allotList_search() {
  var orgName = $('[name=orgName] option:selected').val();  // 获取采购机构
  var expertsFrom = $('[name=expertsFrom] option:selected').val();  // 获取专家类型
  var expertsTypeId = $('[name=expertsTypeId]').val();  // 获取专家类别
  var startTime = $('[name=startTime]').val();  // 初审合格开始时间
  var endTime = $('[name=endTime]').val();  // 初审合格结束时间
  if (expertsTypeId != null) {
    expertsTypeId = expertsTypeId.join(',');
  }
  $('#list_content').listConstructor({
    data: {
      orgName: orgName,
      expertsFrom: expertsFrom,
      expertsTypeId: expertsTypeId,
      startTime: startTime,
      endTime: endTime
    },
    url: list_url
  });
}

// 搜索
function batchList_search() {
  var batchName = $('[name=batchName]').val();  // 获取批次名称
  var createdAt = $('[name=createdAt]').val();  // 获取批次创建时间
  $('#list_content').listConstructor({
    data: {
      batchName: batchName,
      createdAt: createdAt
    },
    url: list_url,
    batch_url: batch_url
  });
}

// 专家批次列表搜索
function expert_auditBatch_search() {
  var batchName = $('[name=batchName]').val();  // 获取批次名称
  var createdAt = $('[name=createdAt]').val();  // 获取批次创建时间
  $('#list_content').listConstructor({
    data: {
      batchName: batchName,
      createdAt: createdAt
    },
    url: list_url
  });
}