package cn.appsys.dao.appCategory.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	/**
	 * 根据parentId查询分类列表信息
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> queryAppCategoryListByParentId(@Param("parentId")Integer parentId);
}
