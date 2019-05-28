package cn.appsys.service.dataDictionary.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {


	/**
	 * 根据typeCode查询字典标表中的相关列表信息
	 * @param typeCode
	 * @return
	 */
	public List<DataDictionary> queryDataDictionaryList(@Param("typeCode")String typeCode);
}
