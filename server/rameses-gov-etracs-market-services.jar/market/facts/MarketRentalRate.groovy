package market.facts;

import java.util.*;
import treasury.facts.*;

class MarketRentalRate {
	
	int year;
	Date fromdate;
	Date todate;
	double rate;
	String payfrequency;	//DAILY, WEEKLY, MONTHLY

	public String toString() {
		return "rental rate-> " + fromdate + " to " + todate + ": " + rate;
	}

}