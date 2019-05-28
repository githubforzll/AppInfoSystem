package cn.appsys.service.appVersion.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfo.developer.AppInfoMapper;
import cn.appsys.dao.appVersion.developer.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;

@Transactional
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;
	@Resource
	private AppInfoMapper appInfoMapper;

	@Override
	public List<AppVersion> queryAppVersionInfoByAppId(Integer appId) {
		return appVersionMapper.queryAppVersionInfoByAppId(appId);
	}

	@Override
	public boolean addAppVersionUnderAppId(AppVersion appVersion) {
		if(appVersionMapper.addAppVersionUnderAppId(appVersion)==1){
			//查询出最新的App版本信息
			AppVersion lastestAppVersion=queryLastestVersionNo();
			AppInfo appInfo=appInfoMapper.queryAppInfoById(appVersion.getAppId());
			//将最新的版本号设值给AppInfo的versionId属性
			appInfo.setVersionId(lastestAppVersion.getId());
			if(appInfoMapper.modifyAppInfoById(appInfo)==1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public AppVersion queryLastestVersionNo() {
		return appVersionMapper.queryLastestVersionNo();
	}

	@Override
	public AppVersion queryAppVersionById(Integer id) {
		return appVersionMapper.queryAppVersionById(id);
	}

	@Override
	public boolean modifyLastestAppVersion(AppVersion appVersion) {
		if(appVersionMapper.modifyLastestAppVersion(appVersion)==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean delFile(Integer id) {
		if(appVersionMapper.delFile(id)==1){
			return true;
		}else{
			return false;
		}
	}

}
