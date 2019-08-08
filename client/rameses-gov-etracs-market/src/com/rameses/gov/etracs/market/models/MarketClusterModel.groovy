package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;
import com.rameses.common.*;
import com.rameses.util.*;

/*******************************************************************************
* This class is used for Rental, Other Fees and Utilities
********************************************************************************/
public class MarketClusterModel extends CrudFormModel {
    
    public void afterCreate() {
        entity.market = caller.selectedProperty;
    }
    
    
}