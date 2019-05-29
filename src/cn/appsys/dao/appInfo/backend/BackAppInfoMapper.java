package cn.appsys.dao.appInfo.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface BackAppInfoMapper {
	
	/**
	 * 根据id修改app信息
	 * @param appInfo
	 * @return
	 */
	public Integer modifyAppInfoById(AppInfo appInfo);
	
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
			                            @Param("categoryLevel1")Integer categoryLevel1,
			                            @Param("categoryLevel2")Integer categoryLevel2,
			                            @Param("categoryLevel3")Integer categoryLevel3,
			                            @Param("from")Integer from,
			                            @Param("pageSize")Integer pageSize);
	
	/**
	 * 根据查询条件记录总数量
	 * @param softwareName
	 * @param status
	 * @param flatformId
	 * @param categoryLevel1
	 * @param categoryLevel2
	 * @param categoryLevel3
	 * @return
	 */
	public Integer getAppInfoCount(@Param("softwareName")String softwareName,
                               @Param("status")Integer status,
                               @Param("flatformId")Integer flatformId,
                               @Param("categoryLevel1")Integer categoryLevel1,
                               @Param("categoryLevel2")Integer categoryLevel2,
                               @Param("categoryLevel3")Integer categoryLevel3);

	/**
	 * 根据appinfoId来查看审核信息
	 * @return
	 */
	public AppInfo getAppInfoIdView(@Param("id")Integer id);
	
	
	/**
	 * 根据appId更新信息
	 * @param id
	 * @return
	 */
	public int updateAppinfo(@Param("id")Integer id);

}
