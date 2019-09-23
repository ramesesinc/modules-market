package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketElectricityModel {
    
    @Caller
    def caller;

    @Binding
    def binding;
    
    @Service("MarketUtilityService")
    def utilitySvc;

    def entity;
    def entry;
    def prev;
    
    def capacityList = [1000,10000];
    
    @PropertyChangeListener
    def listener = [
        "entry.reading" : { o->
            if( prev?.reading !=null  ) {
                if( o < prev.reading ) {
                    def c = (entry.capacity+"").toInteger();
                    entry.volume = (o+c) - prev.reading;
                }
                else {
                    entry.volume = o - prev.reading;                                    
                }
                binding.refresh( "entry.volume" );
            }
        }
    ]
    
    void createNew() {
        entity = caller.entity;
        entry = [acctid:entity.objid, amtpaid: 0, amount: 0 ];
    }
    
    void addLink() {
        entity = caller.entity;
        prev = entity.electricity;
        entry = [acctid:entity.objid, amtpaid: 0, amount: 0, capacity: prev.capacity];
        entry.year = prev.year;
        entry.month = prev.month + 1;
        if( entry.month > 12 ) {
            entry.year = entry.year + 1;
            entry.month = 1;
        }
        entry.monthname = entry.month;
        entry.reading = prev.reading;
        entry.volume = 0;
        entry.prev = prev;
    }
    
    public def doOk() {
        int c = entry.capacity.toInteger();
        if( entry.reading >= c ) {
            throw new Exception("Reading must be less than capacity");
        }
        
        utilitySvc.create( entry );
        caller.reload();
        return "_close";
    }
    
    public def doCancel() {
        return "_close";
    }
    
    public void calculate() {
        def m = [:];
        m.account = [objid: entity.objid];
        m.putAll(entry);
        m.prev = prev;
        def res = utilitySvc.calculate( entry );
        entry.putAll(res);
    }
    
}