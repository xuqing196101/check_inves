package extract.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import extract.dao.common.ExtractUserMapper;
import extract.model.common.ExtractUser;
import extract.service.common.ExtractUserService;

@Service
public class ExtractUserServiceImpl implements ExtractUserService {

	@Autowired ExtractUserMapper extractUserMapper;
	@Override
	public List<ExtractUser> getList(ExtractUser user) {
		return extractUserMapper.getList(user);
	}

}
