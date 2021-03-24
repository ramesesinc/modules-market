package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.client.*;

class MarketRentalBillModel  {
        
    @Service("MarketRentalBillService")
    def billSvc;
    
    @Caller
    def caller;
    
    def entity; 
    def mode;
    
    void create() {
        entity = [:];
        entity.acctid = caller.entity.objid;
        entity.billgroupid = caller.entity.billgroupid;
        entity.amtpaid = 0;
        mode = "create"
    }
    
    def doOk() {
        if(mode=="create") {
            billSvc.create( entity )
            
        }
        return "_close";
    }

    def doCancel() {
        return "_close";        
    }
}