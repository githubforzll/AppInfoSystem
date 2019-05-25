package cn.appsys.service.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppinfoService {
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
			                            @Param("status")String status,
			                            @Param("flatformId")Integer flatformId,
			                            @Param("categoryLevel1")String categoryLevel1,
			                            @Param("categoryLevel2")String categoryLevel2,
			                            @Param("categoryLevel3")String categoryLevel3,
			                            @Param("from")String from,
			                            @Param("pageSize")String pageSize);
	
	/**
	 * 记录总数量
	 * @return
	 */
	public int getAppInfoCount();
}
