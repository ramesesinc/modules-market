package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketOtherFeeModel extends CrudFormModel {
    
    void afterCreate() {
        entity.system = 0;
        entity.acctid = caller.entity.objid;
    }
    
}