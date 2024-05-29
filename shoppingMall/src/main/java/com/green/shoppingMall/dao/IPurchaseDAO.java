package com.green.shoppingMall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.shoppingMall.domain.PurchaseDetail;

@Mapper
public interface IPurchaseDAO {

	public List<PurchaseDetail> findGroupByMnoOrderByAmountDesc();
	
}
