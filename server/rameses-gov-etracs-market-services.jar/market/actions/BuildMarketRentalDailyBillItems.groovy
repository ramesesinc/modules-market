package market.actions;

import com.rameses.rules.common.*;
import market.facts.*;
import com.rameses.util.*;
import java.util.*;
import com.rameses.osiris3.common.*;
import treasury.facts.*;
import treasury.utils.*;
import treasury.actions.*;

public class BuildMarketRentalDailyBillItems implements RuleActionHandler  {

	public void execute(def params, def drools) {
		def ct = RuleExecutionContext.getCurrentContext();
		def facts = ct.facts;
		def cal = Calendar.instance;

		def fromdate = params.fromdate;
		def todate = params.todate;

		(fromdate..todate).each {
			cal.setTime(it);
			int yr = cal.get(Calendar.YEAR);
			int mon = cal.get(Calendar.MONTH)+1;
			int day = cal.get( Calendar.DATE );
			facts << new MarketRentalDailyBillItem(year:yr, month: mon, day: day, date: it) ;			
		}

	}
}