package market.actions;

import com.rameses.rules.common.*;
import market.facts.*;
import com.rameses.util.*;
import java.util.*;
import com.rameses.osiris3.common.*;
import treasury.facts.*;
import treasury.utils.*;
import treasury.actions.*;

public class AddMarketRentalBillItemsByDay extends AddMonthBillItem {

	public void execute(def params, def drools) {
		def ct = RuleExecutionContext.getCurrentContext();
		def facts = ct.facts;
		def cal = Calendar.instance;
        def df = new java.text.SimpleDateFormat("yyyy-MM-dd");

		def fromdate = params.fromdate;
		def todate = params.todate;

		if(!fromdate) throw new Exception( "AddMarketRentalBillItemByDay action error. fromdate is required");
		if(!todate) throw new Exception( "AddMarketRentalBillItemByDay action error. todate is required");

		//ensure first the date does not include times
        fromdate = df.parse( df.format(fromdate) );
        todate = df.parse(df.format(todate));

        (fromdate..todate).each {
            cal.setTime(it);
            int yr = cal.get(Calendar.YEAR);
            int mon = cal.get(Calendar.MONTH)+1;
            int day = cal.get( Calendar.DATE );
            def dfrom = df.parse( yr +"-"+mon + "-"+day );
            facts << new MarketRentalBillItem( year:yr, month: mon, fromdate: dfrom, todate: dfrom);           
        }
	}

}