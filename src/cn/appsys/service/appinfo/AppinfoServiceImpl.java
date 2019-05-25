package cn.appsys.service.appinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Transactional
@Service("app")
public class AppinfoServiceImpl implements AppinfoService{
	
	@Resource
	private AppInfoMapper appinfoMapper;

	@Override
	public List<AppInfo> getAppInfoList(String softwareName, String status, Integer flatformId, String categoryLevel1,
			String categoryLevel2, String categoryLevel3, String from, String pageSize) {
		return appinfoMapper.getAppInfoList(softwareName, status, flatformId, categoryLevel1, 
				                            categoryLevel2, categoryLevel3, from, pageSize);
	}

	@Override
	public int getAppInfoCount() {
		return appinfoMapper.getAppInfoCount();
	}

}
