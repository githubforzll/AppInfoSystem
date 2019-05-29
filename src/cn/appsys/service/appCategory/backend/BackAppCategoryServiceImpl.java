package cn.appsys.service.appCategory.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.appCategory.backend.BackAppCategoryMapper;
import cn.appsys.pojo.AppCategory;

@Transactional
@Service("backAppCategoryService")
public class BackAppCategoryServiceImpl implements BackAppCategoryService{
	
	/*@Resource
	private AppCategoryMapper appCategoryMapper;*/
	@Resource
	private BackAppCategoryMapper backAppCategoryMapper;

	@Override
	public List<AppCategory> queryCategories(Integer parentId) {
		return backAppCategoryMapper.queryCategories(parentId);
	}

}
