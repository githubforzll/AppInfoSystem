package cn.appsys.service.appCategory;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appCategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Transactional
@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService{
	
	@Resource
	private AppCategoryMapper appCategoryMapper;

	@Override
	public List<AppCategory> queryCategories(Integer parentId) {
		return appCategoryMapper.queryCategories(parentId);
	}

}
