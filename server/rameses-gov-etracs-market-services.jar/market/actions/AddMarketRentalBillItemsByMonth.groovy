package market.actions;

import com.rameses.rules.common.*;
import market.facts.*;
import com.rameses.util.*;
import java.util.*;
import com.rameses.osiris3.common.*;
import treasury.facts.*;
import treasury.utils.*;
import treasury.actions.*;

public class AddMarketRentalBillItemsByMonth extends AddMonthBillItem {

	public void execute(def params, def drools) {
		def ct = RuleExecutionContext.getCurrentContext();
		def facts = ct.facts;
		def cal = Calendar.instance;
        def df = new java.text.SimpleDateFormat("yyyy-MM-dd");

		def fromdate = params.fromdate;
		def todate = params.todate;

		if(!fromdate) throw new Exception( "AddMarketRentalBillItemByMonth action error. fromdate is required");
		if(!todate) throw new Exception( "AddMarketRentalBillItemByMonth action error. todate is required");

		//ensure first the date does not include times
        fromdate = df.parse( df.format(fromdate) );
        todate = df.parse(df.format(todate));

        //get from year and from month
        cal.setTime( fromdate );        
        int y1 = cal.get(Calendar.YEAR);
        int m1 = cal.get(Calendar.MONTH)+1;

        //get toyear and to month
        cal.setTime( todate );
        int y2 = cal.get(Calendar.YEAR);
        int m2 = cal.get(Calendar.MONTH)+1;

        int d1 = (y1*12)+m1;
        int d2 = (y2*12)+m2;

        (d1..d2).each {
            def mon = (it % 12);
            def yr = (int)(it / 12);
            if( mon == 0 ) {
                mon = 12;
                yr-= 1;
            }
            def dfrom = df.parse( yr + "-" + mon + "-01");
            if( dfrom.before( fromdate ) ) {
                dfrom = fromdate;
            }
            cal.setTime( dfrom ); 
            def numdays = cal.getActualMaximum( Calendar.DATE );
            cal.set( Calendar.DATE, numdays );
            def dto = cal.getTime();
            if( todate.before( dto )) {
                dto = todate;
            } 
            facts << new MarketRentalBillItem( year:yr, month: mon, fromdate: dfrom, todate: dto ); 
        }

	}

}