package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.seti2.models.*;

public class MarketRentalUnitModel extends CrudFormModel {
        
    @Service("LOVService")
    def lovService;
    
    def rateTypes;
    def unitTypes;
    
    def historyListModel = [
        fetchList: { o->
            def m = [_schemaname:'market_account'];
            m.select = 'objid,acctname,owner.name,dateclosed';
            m.findBy = [ 'unitid': entity.objid ];
            return qryService.getList(m);
        }
    ] as BasicListModel;
        
    public void afterCreate() {
        entity.cluster = caller.selectedCluster;
    }
    
    void afterInit() {
        rateTypes = lovService.get("MARKET_RATE_TYPES")*.key;
        unitTypes = lovService.get("MARKET_UNIT_TYPES")*.key;
    }
    
}    