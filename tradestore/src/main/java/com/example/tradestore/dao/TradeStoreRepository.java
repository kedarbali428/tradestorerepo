package com.example.tradestore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tradestore.model.Trade;

@Repository
public interface TradeStoreRepository extends CrudRepository<Trade, Long> {
	
	public List<Trade> findByTradeidAndExpired(String tradeId, Character expired);

	public Optional<Trade> findByTradeidAndVersion(String tradeId, Integer Version);

	public List<Trade> findByExpired(Character tradeNotExpired);
	

}
