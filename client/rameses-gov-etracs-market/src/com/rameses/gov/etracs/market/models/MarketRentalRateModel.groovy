package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketRentalRateModel extends CrudFormModel {
    
    @Service("LOVService")
    def lovSvc;
    
    def rateTypes;
    
    void afterInit() {
        rateTypes = lovSvc.get("MARKET_RATE_TYPES")*.key;
    }
    
    void afterCreate() {
        entity.unitid = caller.entity.objid;
        entity.ratetype = "DAY";
    }
    
}