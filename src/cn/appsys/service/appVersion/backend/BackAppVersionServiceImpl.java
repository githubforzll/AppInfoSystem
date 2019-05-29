package cn.appsys.service.appVersion.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appVersion.backend.BackAppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Transactional
@Service("backAppVersionService")
public class BackAppVersionServiceImpl implements BackAppVersionService {
	@Resource
	private BackAppVersionMapper backAppVersionMapper;

	@Override
	public boolean modifyAppVersionByAppId(AppVersion appVersion) {
		if(backAppVersionMapper.modifyAppVersionByAppId(appVersion)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public AppVersion getVersionByView(Integer versionid) {
		return backAppVersionMapper.getVersionByView(versionid);
	}
	
}
