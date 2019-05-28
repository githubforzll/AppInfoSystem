package cn.appsys.service.appVersion.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appVersion.backend.AppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Transactional
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;

	@Override
	public boolean modifyAppVersionByAppId(AppVersion appVersion) {
		if(appVersionMapper.modifyAppVersionByAppId(appVersion)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public AppVersion getVersionByView(Integer versionid) {
		return appVersionMapper.getVersionByView(versionid);
	}
	
}
