package cn.appsys.service.dataDictionary;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * 查询所属平台
	 * @return
	 */
	public List<DataDictionary> queryFlatFormList();
}
