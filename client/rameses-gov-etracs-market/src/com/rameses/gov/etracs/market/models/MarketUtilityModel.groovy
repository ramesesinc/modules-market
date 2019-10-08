package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.common.*
import com.rameses.rcp.annotations.*
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.seti2.models.*;

public class MarketUtilityModel {
    
    @Caller
    def caller;

    @Binding
    def binding;
    
    @Service("MarketUtilityService")
    def utilitySvc;
    
    @Service("LOVService")
    def lovSvc;

    @Service("PersistenceService")
    def persistenceSvc;

    @Invoker
    def invoker;
    
    def entity;
    def entry;
    def prev;
    def utilityType;
    def capacityList;
    
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
    
    void onInit() {
        capacityList = lovSvc.get( "MARKET_UTILITY_CAPACITY")*.key.collect{ it.toInteger() };
        utilityType = invoker.properties.utilitytype;
    }
    
    void createNew() {
        onInit();
        entity = caller.entity;
        entry = [acctid:entity.objid, amtpaid: 0, amount: 0, capacity: 1000, reading : 0 ];
        entry.type = utilityType;
    }
    
    void addNextEntry() {
        onInit();
        entity = caller.entity;
        prev = entity.get( utilityType.toLowerCase() );
        if( !prev ) throw new Exception("Error! There is no previous in "+ utilityType + ". Please check the market account read interceptor")
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
        entry.type = utilityType;
    }
    
    void open() {
        onInit();
        entity = caller.selectedItem;
        //open the existing record
        def p = [ _schemaname: 'market_account', objid: entity.acctid ];
        entity = persistenceSvc.read( p );
        prev = entity.get( utilityType.toLowerCase() );
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
        entry.type = utilityType;
    }
    
    
    public void calculate() {
        def m = [:];
        m.acctid = entity.objid;
        m.putAll(entry);
        m.type = utilityType.toLowerCase();
        def res = utilitySvc.calculate( m );
        if( res.amount == 0 ) res.remove("amount");
        entry.putAll(res);
        binding.refresh();
    }
    
    public def doOk() {
        int c = entry.capacity.toInteger();
        if( entry.reading >= c ) {
            throw new Exception("Reading must be less than capacity");
        }
        if( entry.volume?.toInteger() > 0  ) {
            if( entry.amount == 0 ) 
                throw new Exception("Amount must not be zero if volume is not zero. Please run calculate");
        }
        utilitySvc.create( entry );
        caller.reload();
        return "_close";
    }
    
    public def doCancel() {
        return "_close";
    }
 
    
    public void removeEntity() { 
        def m = [_schemaname: 'market_utility']; 
        m.findBy = [ objid: entity?.objid.toString() ]; 
        persistenceSvc.removeEntity( m ); 
    } 
}