package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	
	/**
	 * 
	 * @param frame
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public List<AppInfo> getAppInfoList(@Param("from")Integer from,
			                            @Param("pageSize")Integer pageSize);

}
