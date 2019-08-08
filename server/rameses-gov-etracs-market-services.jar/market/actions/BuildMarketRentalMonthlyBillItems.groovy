package market.actions;

import com.rameses.rules.common.*;
import market.facts.*;
import com.rameses.util.*;
import java.util.*;
import com.rameses.osiris3.common.*;
import treasury.facts.*;
import treasury.utils.*;
import treasury.actions.*;

public class BuildMarketRentalMonthlyBillItems implements RuleActionHandler  {


	public void execute(def params, def drools) {

		def df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		def ct = RuleExecutionContext.getCurrentContext();
		def facts = ct.facts;
		def cal = Calendar.instance;

		def fromdate = params.fromdate;
		def todate = params.todate;

		cal.setTime( fromdate );
		int dy1 = (cal.get( Calendar.YEAR )*12) + (cal.get( Calendar.MONTH )+1);
		int day = cal.get( Calendar.DATE );

		cal.setTime( todate );
		int dy2 = (cal.get( Calendar.YEAR )*12) + (cal.get( Calendar.MONTH )+1);

		(dy1..dy2).each {
		    def m = (it % 12);
		    def y = (int) (it / 12);
		    if( m == 0 ) {
		        m = 12;
		        y = y-1;
		    }    
		    def cfromdate = df.parse( y + "-" + m + "-" + day );
            cal.setTime( cfromdate );
            cal.add( Calendar.MONTH, 1 );
            def ctodate = cal.getTime();

		    facts << new MarketRentalMonthlyBillItem( month: m, year: y, fromdate: cfromdate, todate: ctodate );
		}

	}
}