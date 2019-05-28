package cn.appsys.service.appVersion.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {


	/**
	 * 删除上传文件的路径
	 * @param id
	 * @return
	 */
	public boolean delFile(@Param("id")Integer id);

	/**
	 * 修改最新的版本信息
	 * @param appVersion
	 * @return
	 */
	public boolean modifyLastestAppVersion(AppVersion appVersion);
	
	/**
	 * 根据id查询版本信息
	 * @param id
	 * @return
	 */
	public AppVersion queryAppVersionById(@Param("id")Integer id);

	/**
	 * 查询最新的版本号 
	 * @return
	 */
	public AppVersion queryLastestVersionNo();
	
	/**
	 * 增加指定appId下的版本信息
	 * @param appVersion
	 * @return
	 */
	public boolean addAppVersionUnderAppId(AppVersion appVersion);

	/**
	 * 通过AppId查询App版本信息
	 * @param appId
	 * @return
	 */
	public List<AppVersion> queryAppVersionInfoByAppId(@Param("appId")Integer appId);
}
