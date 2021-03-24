package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.seti2.models.*;
import com.rameses.osiris2.common.*;
import com.rameses.util.*;
import com.rameses.osiris2.client.*;

class MarketAccountModel extends CrudFormModel {
    
    @Service("MarketAccountService")
    def acctSvc;
    
    public void approve() {
        if(!MsgBox.confirm("You are about to approve this record. Proceed?")) return;
        def e = acctSvc.approve( [objid: entity.objid]);
        entity.state = e.state;
    }
        
}