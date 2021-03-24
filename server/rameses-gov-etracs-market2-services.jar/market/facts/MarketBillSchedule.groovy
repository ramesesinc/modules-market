package market.facts;

import java.util.*;
import com.rameses.util.*;
import treasury.facts.*;

public class MarketBillSchedule  {

	int year;
	int month;

	Date fromdate;
	Date todate;
	Date billdate;
	Date discdate;
	Date duedate;
	Date nextbilldate;

	public Map toMap() {
		def m = [:];
		m.year = year;
		m.month = month;
		m.fromdate=fromdate;
		m.todate=todate;
		m.billdate=billdate;		
		m.discdate=discdate;
		m.duedate=duedate;
		m.nextbilldate = nextbilldate;
		return m;
	}

	public MarketBillingPeriod() {}

	public MarketBillingPeriod(def m) {
		if(m.year) year = m.year;
		if(m.month) month = m.month;
		if(m.fromdate) fromdate = m.fromdate;
		if(m.todate) todate = m.todate;
		if(m.billdate) billdate = m.billdate;					
		if(m.discdate) discdate = m.discdate;
		if(m.duedate) duedate = m.duedate;
		if(m.nextbilldate) nextbilldate = m.nextbilldate;
	}

}	