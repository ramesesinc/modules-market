package market.facts;

import java.util.*;
import com.rameses.util.*;
import com.rameses.functions.*;
import treasury.facts.*;

class MarketRentalMonthlyBillItem extends BillItem {

	int year;
	int month;

	Date fromdate;
	Date todate;

	public def toMap() {
		def m = super.toMap();
		m.mode = "MONTHLY";
		m.year = year;
		m.month = month;
		m.fromdate = fromdate;
		m.todate = todate;

		if( m.surcharge == null ) m.surcharge = 0;
		if(m.interest ==null) m.interest = 0;
		if(m.discount==null) m.discount = 0;

		return m;
	}	


	public String toString() {
		return "MONTHLY BILLITEM " + toMap();
	}

}	