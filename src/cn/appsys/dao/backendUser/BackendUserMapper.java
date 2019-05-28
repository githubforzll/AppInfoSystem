package cn.appsys.dao.backendUser;

import org.apache.ibatis.annotations.Param;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;

public interface BackendUserMapper {
	/**
	 * 根据条件查询后台用户
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public BackendUser queryBackendUserByConditions(@Param("userCode")String userCode,@Param("userPassword")String userPassword);
	
	/**
	 * 根据登录用户名获得角色
	 * @param userCode
	 * @return
	 */
	public DataDictionary queryBackendUserBydictionary(@Param("userCode")String userCode);

}
