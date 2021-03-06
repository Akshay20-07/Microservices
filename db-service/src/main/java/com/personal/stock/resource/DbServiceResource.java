package com.personal.stock.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.stock.dbservice.model.Quote;
import com.personal.stock.dbservice.model.Quotes;
import com.personal.stock.dbservice.repository.QuoteRepository;


/**
 * Api for performing the DB-Service(MicroService) operations
 * @author ezaksch
 *
 */
@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {
	
	@Autowired
	private QuoteRepository quoteRepo;
	
	/**
	 * Quote::getQuote is same as quote->quote.getQuote()
	 * @param username
	 * @return
	 */
	@GetMapping("/{username}")
	public List<String> getQuotes(@PathVariable("username") final String username){

		return getQuotesByUserName(username); 
		
	}


	/**
	 * To delete the user details from catalog(db) stock 
	 * @param username
	 * @return
	 */
	@PostMapping("/delete/{username}")
	public List<String> delete(@PathVariable("username") final String username){
		
		List<Quote> quotes=quoteRepo.findByUserName(username).stream()
		.filter(quote -> quote.getUserName().equals(username)).collect(Collectors.toList());
		
		quoteRepo.deleteAll(quotes);
		
		return getQuotesByUserName(username);
		
	}
	
	
	/**
	 * To add the new user to the catalog
	 * @param quotes
	 * @return
	 */
	@PostMapping("/add")
	public List<String> add(@RequestBody final Quotes quotes){
		
		quotes.getQuotes()
			.stream()
			.map(quote -> new Quote(quotes.getUserName(),quote))
			.forEach(quote ->quoteRepo.save(quote));
		
		return getQuotesByUserName(quotes.getUserName());
		
	}
	
	private List<String> getQuotesByUserName(final String username) {
		return quoteRepo.findByUserName(username)
			.stream().map(Quote::getQuote).collect(Collectors.toList());
	}
	
	
}
