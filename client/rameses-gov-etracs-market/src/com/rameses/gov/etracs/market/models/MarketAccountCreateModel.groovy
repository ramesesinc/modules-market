package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.client.*;

class MarketAccountCreateModel extends CrudFormModel {

    def paymentModes = ["MONTHLY", "DAILY", "SEMI-MONTHLY"];
    def rateTypes = ["MONTH", "DAY"]; 
    def selectedUnit;
    
    void afterCreate() {
        //load first the org
        def q = [:];
        q.onselect = { o->
            entity.org = o;
        }
        def m = Modal.show( "market_org:lookup", q, [title:"Select an Org"] );
        
        if(!entity.org) throw new BreakException();
        
        entity.orgid = entity.org.objid;
        entity.paymentmode = "MONTHLY";
        entity.state = "DRAFT";
        entity.txnmode = "CAPTURE";        
        entity.units = [];
    }
    
    def addUnit() {
        def q = [:];
        q.onselect = { o->
            o.each { v->
                entity.units << v;
            }
            binding.refresh();
        }
        q.query = [orgid: entity.org.objid];
        def op = Inv.lookupOpener("market_rental_unit:available:lookup", q);
        return op;
    }
    
    void removeUnit() {
        if(!selectedUnit) throw new Exception("Please select a unit");
        entity.units.remove( selectedUnit );
    }
    
    public def getBillGroups() {
        if(!entity.paymentmode) return false;
        def z = [_schemaname: "market_billgroup"];
        z.findBy = [paymentmode: entity.paymentmode];
        z.select = "objid";
        return queryService.getList(z)*.objid;
    }

}