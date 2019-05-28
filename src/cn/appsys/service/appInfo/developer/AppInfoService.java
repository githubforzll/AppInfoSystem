package cn.appsys.service.appInfo.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;
public interface AppInfoService {
	


	/**
	 * 根据id删除指定的AppInfo
	 * @param id
	 * @return
	 */
	public boolean removeAppInfoById(@Param("id")Integer id);
	
	/**
	 * 删除上传文件的路径
	 * @param id
	 * @return
	 */
	public boolean delFile(@Param("id")Integer id);

	/**
	 * 通过id修改AppInfo
	 * @param id
	 * @return
	 */
	public boolean modifyAppInfoById(AppInfo appInfo); 	

	/**
	 * 通过id查询AppInfo
	 * @param id
	 * @return
	 */
	public AppInfo queryAppInfoById(@Param("id")Integer id);

	/**
	 * 
	 * @param APKName
	 * @return
	 */
	public boolean APKNameExists(@Param("APKName")String APKName);
	
	/**
	 * 新增App基础信息
	 * @param appInfo
	 * @return
	 */
	public boolean addAppInfo(AppInfo appInfo);
	
	/**
	 * 按条件查询AppInfo信息
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param devId
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<AppInfo> queryAppInfoListByConditions(@Param("softwareName")String softwareName,
										@Param("status")Integer status,
										@Param("flatformId")Integer flatformId,
										@Param("categoryLevel1")Integer categoryLevel1,
										@Param("categoryLevel2")Integer categoryLevel2,
										@Param("categoryLevel3")Integer categoryLevel3,
										@Param("devId")Integer devId,
										@Param("from")Integer from,
										@Param("pageSize")Integer pageSize);
	
	/**
	 * 按条件查询出APPInfo信息的记录数
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param devId
	 * @return
	 */
	public Integer queryCountByConditions(@Param("softwareName")String softwareName,
										@Param("status")Integer status,
										@Param("flatformId")Integer flatformId,
										@Param("categoryLevel1")Integer categoryLevel1,
										@Param("categoryLevel2")Integer categoryLevel2,
										@Param("categoryLevel3")Integer categoryLevel3,
										@Param("devId")Integer devId);
	
}
