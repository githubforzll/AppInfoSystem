package cn.appsys.service.backendUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.backendUser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;

@Transactional
@Service("backend")
public class BackendUserServiceImpl implements BackendUserService{
	
	@Resource
	private BackendUserMapper backendUserMapper;
	
	@Override
	public BackendUser queryBackendUserByConditions(String userCode, String userPassword) {
		return backendUserMapper.queryBackendUserByConditions(userCode, userPassword);
	}

	@Override
	public DataDictionary queryBackendUserBydictionary(String userCode) {
		return backendUserMapper.queryBackendUserBydictionary(userCode);
	}
	
}
