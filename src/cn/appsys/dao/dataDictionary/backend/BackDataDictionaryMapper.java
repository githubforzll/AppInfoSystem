package cn.appsys.dao.dataDictionary.backend;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface BackDataDictionaryMapper {
	
	/**
	 * 查询所属平台
	 * @return
	 */
	public List<DataDictionary> queryFlatFormList();
	
}
