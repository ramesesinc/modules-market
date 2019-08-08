package market.facts;

import java.util.*;
import com.rameses.util.*;
import com.rameses.functions.*;
import treasury.facts.*;

class MarketRentalWeeklyBillItem extends BillItem {

	int year;
	int month;

	Date fromdate;
	Date todate;
	int weekofyear;

	public def toMap() {
		def m = super.toMap();
		m.mode = "WEEKLY";
		m.year = year;
		m.month = month;
		m.fromdate = fromdate;
		m.todate = todate;
		m.weekofyear = weekofyear;

		if( m.surcharge == null ) m.surcharge = 0;
		if(m.interest ==null) m.interest = 0;
		if(m.discount==null) m.discount = 0;

		return m;
	}	

}	