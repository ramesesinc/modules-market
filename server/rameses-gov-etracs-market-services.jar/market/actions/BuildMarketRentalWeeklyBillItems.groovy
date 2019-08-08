package market.actions;

import com.rameses.rules.common.*;
import market.facts.*;
import com.rameses.util.*;
import java.util.*;
import com.rameses.osiris3.common.*;
import treasury.facts.*;
import treasury.utils.*;
import treasury.actions.*;

public class BuildMarketRentalWeeklyBillItem implements RuleActionHandler  {

	public void execute(def params, def drools) {

		def df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		def cal = Calendar.instance;
		def ct = RuleExecutionContext.getCurrentContext();
		def facts = ct.facts;

		def fromdate = params.fromdate;
		def todate = params.todate;

        def cfromdate = fromdate;
        while( cfromdate < todate ) {
      		def ctodate = cfromdate + 6;
            cal.setTime( cfromdate );
            int woy = cal.get( Calendar.WEEK_OF_YEAR );
            int yr = cal.get( Calendar.YEAR );
            int mon = cal.get( Calendar.MONTH )+1;

            facts << new MarketRentalWeeklyBillItem( month: mon, year: yr, fromdate: cfromdate, todate: ctodate, weekofyear: woy );
            cfromdate = ctodate + 1;
        }
    

	}
}