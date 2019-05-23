package cn.appsys.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

//字符串转日期格式转换器
public class StringToDateConverter implements Converter<String, Date> {

	private String datePattern;
	
	public StringToDateConverter(String datePattern) {
		System.out.println("StringToDateConverter converter:"+datePattern);
		this.datePattern = datePattern;
	}

	@Override
	public Date convert(String str) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datePattern).parse(str);
			System.out.println("StringToDateConverter converter date:"+date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
