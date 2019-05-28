package cn.appsys.service.dataDictionary.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.dataDictionary.developer.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Transactional
@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;

	@Override
	public List<DataDictionary> queryDataDictionaryList(String typeCode) {
		return dataDictionaryMapper.queryDataDictionaryList(typeCode);
	}

}
