package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import java.rmi.server.*;
import com.rameses.seti2.models.*;

public class MarketAccountNewModel extends CrudFormModel {

    @Service("LOVService")
    def lovSvc;
    
    def paymentModes;
    
    @PropertyChangeListener
    def listener = [
        'entity.owner': { o->
            if(entity.acctname==null) {
                entity.acctname = o.name;
                binding.refresh("entity.acctname");
            }
        }
    ];        
    
    void afterCreate() {
        paymentModes = lovSvc.get("MARKET_PAYMENTMODE")*.key;
        entity.txnmode = "CAPTURE";
        entity.extrate = 0;
        entity.extarea = 0;
        entity.partialbalance = 0;
    }
    
    def save() {
        super.save();
        MsgBox.alert('save successful. Account No ' + entity.acctno + ' is created');
        return Inv.lookupOpener("market_account:open", [entity: [objid: entity.objid ]  ]);
    }
    
    def viewUnit() {
        if(!entity.unit?.objid) 
            throw new Exception("Please select a unit first");
        def op = Inv.lookupOpener("market_rentalunit:open", [ entity: [objid: entity.unit?.objid ]])
        op.target = "popup";
        return  op;
    }
}