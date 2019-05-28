package cn.appsys.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.dataDictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Transactional
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService{

	@Resource
	private DataDictionaryMapper dataDictionary;
	
	@Override
	public List<DataDictionary> queryFlatFormList() {
		return dataDictionary.queryFlatFormList();
	}

}
