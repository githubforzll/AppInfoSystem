package cn.appsys.service.devUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.devUser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Transactional
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {
	@Resource
	private DevUserMapper devUserMapper;

	@Override
	public DevUser queryDevUserByConditions(String devCode, String devPassword) {
		return devUserMapper.queryDevUserByConditions(devCode, devPassword);
	}
	
}
