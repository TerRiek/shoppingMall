package com.green.shoppingMall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.shoppingMall.domain.StockDetail;

@Mapper
public interface IStockDAO {

	public List<StockDetail> findOrderByOrderdatetimeDesc();
	public Long getAmount(@Param("mno") Long mno);
}
