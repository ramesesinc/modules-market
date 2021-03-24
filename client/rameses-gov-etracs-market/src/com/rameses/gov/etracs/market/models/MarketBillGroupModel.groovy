package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.client.*;

class MarketBillGroupModel extends CrudFormModel {
    
    def paymentModes = ["MONTHLY", "DAILY", "SEMI-MONTHLY"];
    
    void afterCreate() {
        entity.objid = "";
    }
    
    def scheduleList;
    
    def addSchedule() {
        def h = { o->
            scheduleList.reload();
        }
        return Inv.lookupOpener("market_bill_schedule:create", [onselect:h, billgroupid:entity.objid] );
    }
    

}