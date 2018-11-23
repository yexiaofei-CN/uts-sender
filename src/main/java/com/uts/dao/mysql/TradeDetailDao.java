package com.uts.dao.mysql;

import com.uts.config.mysql.BaseMapper;
import com.uts.entity.TradeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeDetailDao extends BaseMapper<TradeDetail> {

	int updateStatusById(@Param("id") String id);
	
	List<TradeDetail> queryByNotSync();
	
}