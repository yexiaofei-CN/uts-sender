package com.uts;

import com.uts.entity.TradeDetail;
import com.uts.service.ITradeDetailService;
import com.uts.util.GeneratorDataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	ITradeDetailService tradeDetailService;
	@Test
	public void contextLoads() {
		TradeDetail td = GeneratorDataUtil.generator();
		tradeDetailService.sender(td);
	}

}
