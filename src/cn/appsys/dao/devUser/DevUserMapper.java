package cn.appsys.dao.devUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {

	/**
	 * 根据条件查询开发者用户
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public DevUser queryDevUserByConditions(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
}
