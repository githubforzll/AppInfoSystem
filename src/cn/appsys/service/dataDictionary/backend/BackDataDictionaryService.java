package cn.appsys.service.dataDictionary.backend;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface BackDataDictionaryService {
	/**
	 * 查询所属平台
	 * @return
	 */
	public List<DataDictionary> queryFlatFormList();
}
