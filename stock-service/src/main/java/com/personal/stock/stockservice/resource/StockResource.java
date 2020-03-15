package com.personal.stock.stockservice.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * To query DB service use Rest Template 
 * @author ezaksch
 *
 */
@RestController
@RequestMapping("rest/stock")
public class StockResource {
	
	
	@Autowired
	RestTemplate restTemp;
	
	/**
	 * To retrieve the Stock
	 * @param userName
	 * @return
	 */
	@GetMapping("/{username}")
	public List<Stock> getStock(@PathVariable("username") final String userName){
			
		ResponseEntity<List<String>> quoteResponse=restTemp.exchange("http://db-service/rest/db/"+userName, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
		});
		
		List<String> quotes=quoteResponse.getBody();
		return quotes
				.stream()
					.map(this::getStockPrice)
					.collect(Collectors.toList());
	}
	
	/**
	 * For fetching the Currency of the Stock
	 * @param userName
	 * @return
	 */
	@GetMapping("/{username}/currency")
	public List<String> getStockCurrency(@PathVariable("username") final String userName){
			
		ResponseEntity<List<String>> quoteResponse=restTemp.exchange("http://db-service/rest/db/"+userName, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
		});
		
		List<String> quotes=quoteResponse.getBody();
		return quotes
				.stream()
					.map(this::getStockCurrencyName)
					.collect(Collectors.toList());
	}
	
	
	
	
	/**
	 * To get the details of the User
	 * @param userName
	 * @return
	 */
	@GetMapping("/{username}/details")
	public List<List<Object>> getUserDetails(@PathVariable("username")  final String userName){
		ResponseEntity<List<String>> quoteResponse=restTemp.exchange("http://db-service/rest/db/"+userName, HttpMethod.GET,null,new ParameterizedTypeReference<List<String>>() {
		});
		
		List<String> quotes=quoteResponse.getBody();
		
		
		return quotes
				.stream()
				.map(this::getDetails)
				.collect(Collectors.toList());
		
	}
	
	
	/**
	 * To get the Current Price of the Stock
	 * @param quote
	 * @return
	 */
	private Stock getStockPrice(String quote){
		try {
			return YahooFinance.get(quote);
		} catch (IOException e) {
			
			e.printStackTrace();
			return new Stock(quote);
		}
		
	}
	
	/**To get the Currency of the stock
	 * @param quote
	 * @return
	 */
	private String getStockCurrencyName(String quote){
		try {
			return YahooFinance.get(quote).getCurrency();
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	
	}
	/**
	 * To retrieve the StockExchange name,Dividend and statistics
	 * @param quote1
	 * @return
	 */
	private List<Object> getDetails(String quote1){
		List<Object> detail=new ArrayList<>();
		Stock stock;
		try {
			stock = YahooFinance.get(quote1);
		} catch (IOException e) {
			
			e.printStackTrace();
			stock=new Stock(quote1);
		}
		detail.add(stock.getStockExchange());
		detail.add(stock.getDividend());
		detail.add(stock.getStats());
		
		return detail;
	}
		
		
	}

