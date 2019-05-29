package cn.appsys.dao.appCategory.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;


public interface BackAppCategoryMapper {
	
	/**
	 * 查询父级分类
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> queryCategories(@Param("parentId")Integer parentId);
}
