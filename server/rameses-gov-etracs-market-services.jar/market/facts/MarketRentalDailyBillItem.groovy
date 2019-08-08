package market.facts;

import java.util.*;
import com.rameses.util.*;
import com.rameses.functions.*;
import treasury.facts.*;

class MarketRentalDailyBillItem extends BillItem {

	int year;
	int month;
	int day;
	Date date;

	public def toMap() {
		def m = super.toMap();
		m.mode = "DAILY";
		m.year = year;
		m.month = month;
		m.day = day;
		m.date = date;
		m.fromdate = date;
		m.todate = date;
		if( m.surcharge == null ) m.surcharge = 0;
		if(m.interest ==null) m.interest = 0;
		if(m.discount==null) m.discount = 0;
		return m;
	}	


}	