package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.client.*;

class MarketRentalUnitModel extends CrudFormModel {
    
    def rateTypes = ["DAY", "MONTH"];
    def unitTypes = ["STALL", "BLOCK", "TABLE"]; 

    void afterCreate() {
        if( caller.schemaName == "market_org" ) {
            entity.org = caller.entity;
        }
        else {
            def q = [:];
            q.onselect = { o->
                entity.org = o;
            }
            Modal.show( "market_org:lookup", q, [title:"Select an Org"] );
            if(!entity.org) throw new BreakException();
        }
    }
    
}