package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ComCostSummaryMapper;
import bss.model.sstps.ComCostSummary;
import bss.service.sstps.ComCostSummaryService;

@Service("/comCostSummaryService")
public class ComCostSummaryServiceImpl implements ComCostSummaryService {

	@Autowired
	private ComCostSummaryMapper comCostSummaryMapper;
	
	@Override
	public List<ComCostSummary> selectProduct(ComCostSummary comCostSummary) {
		return comCostSummaryMapper.selectProduct(comCostSummary);
	}

	@Override
	public void insert(ComCostSummary comCostSummary) {
		comCostSummaryMapper.insert(comCostSummary);
	}

	@Override
	public void update(ComCostSummary comCostSummary) {
		comCostSummaryMapper.update(comCostSummary);
	}

}
