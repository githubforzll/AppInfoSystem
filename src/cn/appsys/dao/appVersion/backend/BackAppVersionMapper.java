package cn.appsys.dao.appVersion.backend;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface BackAppVersionMapper {

	/**
	 * 根据id更新版本信息
	 * @param appVersion
	 * @return
	 */
	public Integer modifyAppVersionByAppId(AppVersion appVersion);
	

	/**
	 * 根据versionId查询版本信息
	 * @param versionid
	 * @return
	 */
	public AppVersion getVersionByView(@Param("versionid")Integer versionid);
}
