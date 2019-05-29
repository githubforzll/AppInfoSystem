package cn.appsys.service.appInfo.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appInfo.backend.BackAppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Transactional
@Service("backAppInfoService")
public class BackAppInfoServiceImpl implements BackAppInfoService{
	
	@Resource
	private BackAppInfoMapper backAppInfoMapper;

	
	@Override
	public AppInfo getAppInfoIdView(Integer id) {
		return backAppInfoMapper.getAppInfoIdView(id);
	}


	@Override
	public List<AppInfo> getAppInfoList(String softwareName, Integer status, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3, Integer from, Integer pageSize) {
		return backAppInfoMapper.getAppInfoList(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, from, pageSize);
	}


	@Override
	public int getAppInfoCount(String softwareName, Integer status, Integer flatformId, Integer categoryLevel1,
			Integer categoryLevel2, Integer categoryLevel3) {
		return backAppInfoMapper.getAppInfoCount(softwareName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
	}

	@Override
	public int updateAppinfo(Integer id) {
		return backAppInfoMapper.updateAppinfo(id);
	}


	@Override
	public boolean modifyAppInfoById(AppInfo appInfo) {
		if(backAppInfoMapper.modifyAppInfoById(appInfo)==1){
			return true;
		}else{
			return false;
		}
	}

}
