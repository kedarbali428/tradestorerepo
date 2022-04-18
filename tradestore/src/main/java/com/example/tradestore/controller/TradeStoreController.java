package com.example.tradestore.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tradestore.helper.ResponseBean;
import com.example.tradestore.model.TradeDetailRequest;
import com.example.tradestore.service.TradeStoreService;

@RestController
@RequestMapping(value = "/tradestore/")
public class TradeStoreController {
	
	@Autowired
	TradeStoreService tradeStoreService;
	
	
	@PostMapping(value = "/v1/transmittrade", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveTradeDetails(@RequestBody TradeDetailRequest tradeDetailRequest,
			@RequestHeader HttpHeaders headers) {
		
		ResponseBean response = tradeStoreService.saveTradeDetails(tradeDetailRequest);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
