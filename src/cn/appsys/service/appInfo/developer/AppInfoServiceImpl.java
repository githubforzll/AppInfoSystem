package cn.appsys.service.appInfo.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfo.developer.AppInfoMapper;
import cn.appsys.dao.appVersion.developer.AppVersionMapper;
import cn.appsys.pojo.AppInfo;

@Transactional
@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper appInfoMapper;
	@Resource
	private AppVersionMapper appVersionMapper;

	@Override
	public List<AppInfo> queryAppInfoListByConditions(String softwareName, Integer status, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer devId, Integer from,
			Integer pageSize) {
		return appInfoMapper.queryAppInfoListByConditions(softwareName, status, flatformId, categoryLevel1,
				categoryLevel2, categoryLevel3, devId, from, pageSize);
	}

	@Override
	public Integer queryCountByConditions(String softwareName, Integer status, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer devId) {
		return appInfoMapper.queryCountByConditions(softwareName, status, flatformId, categoryLevel1, categoryLevel2,
				categoryLevel3, devId);
	}

	@Override
	public boolean addAppInfo(AppInfo appInfo) {
		if (appInfoMapper.addAppInfo(appInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean APKNameExists(String APKName) {
		if (appInfoMapper.queryAppInfoByAPKName(APKName) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean modifyAppInfoById(AppInfo appInfo) {
		if (appInfoMapper.modifyAppInfoById(appInfo) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public AppInfo queryAppInfoById(Integer id) {
		return appInfoMapper.queryAppInfoById(id);
	}

	@Override
	public boolean delFile(Integer id) {
		if (appInfoMapper.delFile(id) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAppInfoById(Integer id) {
		if(appVersionMapper.queryAppVersionInfoByAppId(id).size()>0){
			if(appVersionMapper.removeAllAppVersionsUnderAppId(id)>0){
				return true;
			}else{
				return false;
			}
		}
		if (appInfoMapper.removeAppInfoById(id) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
