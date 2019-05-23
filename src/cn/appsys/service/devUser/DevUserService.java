package cn.appsys.service.devUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserService {

	/**
	 * 根据条件查询开发者用户
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public DevUser queryDevUserByConditions(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
}
