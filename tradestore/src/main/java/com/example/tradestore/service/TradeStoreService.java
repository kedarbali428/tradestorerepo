package com.example.tradestore.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.tradestore.dao.TradeStoreRepository;
import com.example.tradestore.helper.ErrorBean;
import com.example.tradestore.helper.ResponseBean;
import com.example.tradestore.helper.TradeStoreConstants;
import com.example.tradestore.helper.TradeStoreServiceException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.model.TradeDetailRequest;

@Service
public class TradeStoreService {
	
	@Autowired
	TradeStoreRepository tradeStoreRepo;
	
	@Autowired
	private Environment env;
	
	public ResponseBean saveTradeDetails(TradeDetailRequest tradeDetailRequest)  {

		ResponseBean responseBean = null;
		
		if (checkMaturity(tradeDetailRequest.getMaturityDate())) {

			int value = checkVersion(tradeDetailRequest);

			if (value == -1) {
				throw new TradeStoreServiceException(HttpStatus.EXPECTATION_FAILED,
						new ErrorBean(TradeStoreConstants.TSE101, env.getProperty("TSE101")));
			} else if (value == 1) {
				Trade trade = new Trade();
				trade.setTradeid(tradeDetailRequest.getTradeId());
				trade.setBookid(tradeDetailRequest.getBookId());
				trade.setCounterpartyid(tradeDetailRequest.getCounterPartyId());
				trade.setExpired(TradeStoreConstants.TRADE_NOT_EXPIRED);
				trade.setMaturitydate(convertToTimestamp(tradeDetailRequest.getMaturityDate()));
				trade.setVersion(tradeDetailRequest.getVersion());
				trade.setCreateddate(new Timestamp(System.currentTimeMillis()));
				tradeStoreRepo.save(trade);
				responseBean = new ResponseBean(trade, null);
			} else if (value == 0) {
				Optional<Trade> tradeObj = tradeStoreRepo.findByTradeidAndVersion(tradeDetailRequest.getTradeId(),
						tradeDetailRequest.getVersion());
				if (tradeObj.isPresent()) {
					Trade trade = tradeObj.get();
					trade.setBookid(tradeDetailRequest.getBookId());
					trade.setCounterpartyid(trade.getCounterpartyid());
					trade.setExpired(TradeStoreConstants.TRADE_NOT_EXPIRED);
					trade.setMaturitydate(convertToTimestamp(tradeDetailRequest.getMaturityDate()));
					tradeStoreRepo.save(trade);
					responseBean = new ResponseBean(trade, null);
				}

			}
		}
		return responseBean;
	}
	
	/**
	 * Method to check the maturity date is less than or greater than or equal to current date
	 * @param maturityDate
	 * @return true if maturity date is after/greater/equal to current date 
	 *         false if maturity date is before/lesser than current date
	 * @throws ParseException
	 */
	private boolean checkMaturity(String maturityDate) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
			Date d1;
			try {
				d1 = formatter.parse(maturityDate);
			} catch (ParseException e) {
				throw new TradeStoreServiceException(HttpStatus.INTERNAL_SERVER_ERROR,new ErrorBean("TSE102", env.getProperty(TradeStoreConstants.TSE102)));
			}
			Date d2 = new Date();
			if(d1.before(d2))
				return false;
			else
				return true;
		
		
		
	}

	private Timestamp convertToTimestamp(String maturityDate) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse(maturityDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
	
	private String convertTimestampToString(Timestamp maturityDate) {
		
		Date date = new Date(maturityDate.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate= formatter.format(date); 
		return strDate;
	}
	
	/**
	 * This method checks if the trade given in request is already present in db or is a new one.
	 * @param tradeDetailRequest
	 * @return Integer value = 0 if input version already exists and trade needs to be updated as per the request
	 * value = 1 if input version does not exists and a new trade needs to be inserted
	 * value = -1 if input version is lower than the version which already exists for the trade specified in input request. 
	 */
	private int checkVersion(TradeDetailRequest tradeDetailRequest) {
		int value = 0;
		List<Trade> tradeList = tradeStoreRepo.findByTradeidAndExpired(tradeDetailRequest.getTradeId(), TradeStoreConstants.TRADE_NOT_EXPIRED);
		
		if(tradeList != null && !tradeList.isEmpty()) {
			Trade t = null;
			
			if(tradeList.size() > 1) {
				t = tradeList.stream().sorted(Comparator.comparingInt(Trade::getVersion).reversed()).collect(Collectors.toList()).get(0);
			
				value = getValueByVersion(t.getVersion(), tradeDetailRequest.getVersion());
				return value;
			}else {
				t = tradeList.get(0);
				value = getValueByVersion(t.getVersion(), tradeDetailRequest.getVersion());
				return value;
			}
			
		}else {
			value = 1;
			return value;
		}
		
	}
	
	/**
	 * This method is to compare the trade version of input and the one obtained in db for given trade id in request
	 * @param dbVersion
	 * @param requestVersion
	 * @return Integer value = 0 if input version already exists and trade needs to be updated as per the request
	 * value = 1 if input version does not exists and a new trade needs to be inserted
	 * value = -1 if input version is lower than the version which already exists for the trade specified in input request.
	 */

	private int getValueByVersion(Integer dbVersion, Integer requestVersion) {
		
		if(dbVersion == requestVersion)
			return 0;
		else if(dbVersion > requestVersion)
			return  -1;
		else 
			return 1;
	}
	
	
	public void updateTradeAsExpired() throws ParseException {
		
		List<Trade> tradeList = tradeStoreRepo.findByExpired(TradeStoreConstants.TRADE_NOT_EXPIRED);
		boolean flag  = false;
		
		for (Trade trade : tradeList) {
			flag = checkMaturity(convertTimestampToString(trade.getMaturitydate()));
			if(!flag) {
				trade.setExpired(TradeStoreConstants.TRADE_EXPIRED);
				tradeStoreRepo.save(trade);	
			}
		}
	}

	
}
