package cn.appsys.service.appinfo.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfo.backend.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Transactional
@Service("appinfoService")
public class AppinfoServiceImpl implements AppinfoService{
	
	@Resource
	private AppInfoMapper appInfoMapper;

	
	@Override
	public AppInfo getAppInfoIdView(Integer id) {
		return appInfoMapper.getAppInfoIdView(id);
	}


	@Override
	public List<AppInfo> getAppInfoList(String softwareName, Integer status, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, Integer from, Integer pageSize) {
		return appInfoMapper.getAppInfoList(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, from, pageSize);
	}


	@Override
	public int getAppInfoCount(String softwareName, Integer status, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3) {
		return appInfoMapper.getAppInfoCount(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
	}

	@Override
	public int updateAppinfo(Integer id) {
		return appInfoMapper.updateAppinfo(id);
	}


	@Override
	public boolean modifyAppInfoById(AppInfo appInfo) {
		if(appInfoMapper.modifyAppInfoById(appInfo)==1){
			return true;
		}else{
			return false;
		}
	}

}
