package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketItemAccountModel extends CrudFormModel {
    
    @Service("LOVService")
    def lovSvc;
    
    def itemTypes;
    
    void afterCreate() {
        entity.system = 0;
    }
    
    void afterInit() {
        itemTypes = lovSvc.get("MARKET_ITEM_TYPES")*.key;
    } 
    
}