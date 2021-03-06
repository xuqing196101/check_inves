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
  
  // 默认情况为未选列表搜索，反之为已选列表搜索
  if ($('#selected_tab li.active').index() == 0) {
    $('#list_content').listConstructor({
      data: {
        orgName: orgName,
        expertsFrom: expertsFrom,
        expertsTypeId: expertsTypeId,
        startTime: startTime,
        endTime: endTime
      }
    });
  } else {
    $('#selected_content').listConstructor_t({
      data: {
        orgName: orgName,
        expertsFrom: expertsFrom,
        expertsTypeId: expertsTypeId,
        startTime: startTime,
        endTime: endTime
      }
    });
  }
}

// 复审分配列表搜索
function detailsBatch_search() {
  var orgName = $('[name=orgName] option:selected').val();  // 获取采购机构
  var expertsFrom = $('[name=expertsFrom] option:selected').val();  // 获取专家类型
  var expertsTypeId = $('[name=expertsTypeId]').val();  // 获取专家类别
  var groupId = $('[name=groupId] option:selected').val();  // 获取审核组
  var status = $('[name=status] option:selected').val();  // 获取审核状态
  if (expertsTypeId != null) {
    expertsTypeId = expertsTypeId.join(',');
  }
  
  $('#table_content').listConstructor({
    data: {
      orgName: orgName,
      expertsFrom: expertsFrom,
      expertsTypeId: expertsTypeId,
      groupId: groupId,
      status: status,
      batchId: getUrlParam('batchId'),
      requestType: 'select'
    }
  });
}

// 复审批次列表搜索
function batchList_search() {
  var batchName = $('[name=batchName]').val();  // 获取批次名称
  var createdAt = $('[name=createdAt]').val();  // 获取批次创建时间
  
  $('#list_content').listConstructor({
    data: {
      batchName: batchName,
      createdAt: createdAt
    }
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
    }
  });
}