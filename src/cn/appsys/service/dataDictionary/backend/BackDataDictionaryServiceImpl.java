package cn.appsys.service.dataDictionary.backend;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.dataDictionary.backend.BackDataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Transactional
@Service("backDataDictionaryService")
public class BackDataDictionaryServiceImpl implements BackDataDictionaryService{

	@Resource
	private BackDataDictionaryMapper backDataDictionary;
	
	@Override
	public List<DataDictionary> queryFlatFormList() {
		return backDataDictionary.queryFlatFormList();
	}

}
