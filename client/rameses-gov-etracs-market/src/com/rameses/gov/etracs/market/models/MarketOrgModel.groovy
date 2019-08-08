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
public class MarketOrgModel  {
    
    
    def selectedProperty;
    def selectedCluster;
    def selectedUnit;

    def propertyListHandler;
    def clusterListHandler;
    def unitListHandler;
    
    def query = [:];
    
    @FormTitle
    def title = "Market Organization"
    
    @PropertyChangeListener
    def listener = [
        "selectedProperty" : { o->
            selectedCluster = null; selectedUnit = null;
            query.marketid = o.objid;
            query.clusterid = null;
            clusterListHandler.reload();
            unitListHandler.reload();
        },
        "selectedCluster" : { o->
            selectedUnit = null;
            query.clusterid = o.objid;
            unitListHandler.reload();
        }
    ];
     
}