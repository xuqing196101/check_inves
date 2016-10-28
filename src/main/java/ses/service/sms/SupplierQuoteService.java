package ses.service.sms;

import java.util.List;

import ses.model.sms.Quote;

public interface SupplierQuoteService {
	List<Quote> getAllQuote(Quote quote,Integer page);
	
	void insert(Quote quote);
}
