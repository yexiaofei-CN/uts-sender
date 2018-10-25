package com.uts.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;


public class KeyUtil {

	/**
	 * <B>方法名称：</B>generatorUUID<BR>
	 * <B>概要说明：</B>主键生成策略<BR>
	 * @author baihezhuo
	 * @since 2017年2月21日 下午2:00:06
	 * @return UUID String
	 */
	public static String generatorUUID(){
		TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		return timeBasedGenerator.generate().toString();
	}
	
	
	
}
