package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketRentalPaymentStatusModel {
    
    @Service("PersistenceService")
    def persistenceSvc;
    
    @Caller
    def caller;
    
    def entity;
    
    public void create() {
        entity = [:];
        entity.acctid = caller.entity.objid;
        entity.partialbalance = 0;
    }

    def doOk() {
        entity._schemaname = "market_rental_payment_status";
        persistenceSvc.create( entity );
        caller.reload();
        return "_close";
    }
    
    def doCancel() {
        return "_close";
    }
    
}