package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	
	/**
	 * 根据条件查询并分页
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<AppInfo> getAppInfoList(@Param("softwareName")String softwareName,
			                            @Param("status")Integer status,
			                            @Param("flatformId")Integer flatformId,
			                            @Param("categoryLevel1")String categoryLevel1,
			                            @Param("categoryLevel2")String categoryLevel2,
			                            @Param("categoryLevel3")String categoryLevel3,
			                            @Param("from")Integer from,
			                            @Param("pageSize")Integer pageSize);
	
	/**
	 * 根据查询条件记录总数量
	 * @return
	 */
	public int getAppInfoCount(@Param("softwareName")String softwareName,
                               @Param("status")Integer status,
                               @Param("flatformId")Integer flatformId,
                               @Param("categoryLevel1")String categoryLevel1,
                               @Param("categoryLevel2")String categoryLevel2,
                               @Param("categoryLevel3")String categoryLevel3);

	/**
	 * 根据versionId来查看审核信息
	 * @return
	 */
	public AppInfo getVersionIdlist(@Param("versionid")Integer versionId);

}
