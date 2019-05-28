package cn.appsys.dao.appCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;


public interface AppCategoryMapper {
	
	/**
	 * 查询父级分类
	 * @param parentId
	 * @return
	 */
	public List<AppCategory> queryCategories(@Param("parentId")Integer parentId);
}
