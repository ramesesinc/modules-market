package com.rameses.gov.etracs.market.models;

import com.rameses.rcp.annotations.*;
import com.rameses.rcp.common.*;
import com.rameses.osiris2.client.*
import com.rameses.osiris2.common.*
import com.rameses.enterprise.treasury.models.*;
import com.rameses.util.*;
import com.rameses.functions.*;

public class MarketCashReceiptModel extends com.rameses.treasury.common.models.BasicBillingCashReceiptModel {
    
    @Service("MarketCashReceiptService")
    def cashReceiptSvc;
     
    @Service("LOVService")
    def lovSvc;
    
    @Service('DateService') 
    def dateSvc; 
    
    String entityName = "misc_cashreceipt";
    String title = "Market Rental";
    def billdate;
    def selectedItem;
    def selectedType;
    
    def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
    def acctFilter;
    
    def typeListHandler = [
        isMultiSelect: {
            return true;
        },
        fetchList: {
            return lovSvc.get("MARKET_COLLECTION_OPTIONS").collect{ [objid: it.key] };
        }
    ] as BasicListModel;
    
    def start() {
        entity.entries = [];
        entity.items = [];
        return super.start();
    }
    
    void findTxn() {
        def vals = typeListHandler.getSelectedValue();
       if(!vals) 
        throw new Exception("Please select at least one txn type");
       acctFilter = vals*.objid;
    }
    
    def itemHandler = [
        fetchList: {
            return entity.entries;
        },
        onRemoveItem: { o->
            if( MsgBox.confirm("You are about to remove this item. Proceed")) {
               entity.entries.remove(o);
               updateReceipt();
               return true;
            }
            return false;
        },
        onColumnUpdate: {i,n->
            if(n=="todate") {
               i.todate = df.parse( i.todate );
               processBillingItem(i);
               updateReceipt();
            }
            else if( n=="amount") {
               def m = [objid:i.objid, partial: i.amount, todate: i.todate, fromdate: i.fromdate];
               processBillingItem( m );
               i.putAll(m);
               updateReceipt();
               itemHandler.reload();   
            }
        }
    ] as EditorListModel;  
    
    void processBillingItem( def itm ) {
         def p = [:];
         if( acctFilter ) p.filters = acctFilter;
         p.acctid = itm.acctid;
         p.billdate = itm.todate;
         def entry = cashReceiptSvc.getBilling( p ); 
         itm.putAll(entry);
     }   
    
    void updateReceipt() {
       entity.amount = 0;
       entity.items.clear();
       entity.entries.each { b->
           entity.items.addAll( b.items );
       }
       entity.amount = entity.items.sum{ it.amount };
       updateBalances();
       binding.refresh("entity.amount");
    } 

    def getMarketAccountLookup() {
        def h = { o->
           if( entity.entries.find{ it.objid == o.objid } )
               throw new Exception("Item already added.");
            def z = [acctid: o.objid, billdate: billdate ];
            def entry = cashReceiptSvc.getBilling( z ); 
            entity.entries << entry;
            itemHandler.reload();
            updateReceipt();

        }
        def pp = [onselect:h];
        if( entity.payer?.objid ) pp.ownerid = entity.payer.objid;
       return Inv.lookupOpener( "market_account:lookup", pp ); 
    }
     
    void clearAll() {
        if( entity.entries == null ) entity.entries = [];
        if( entity.items ) entity.items.clear();
        entity.entries.clear();
        updateReceipt();
        binding.refresh();
    }
     
    def viewDetails() {
        if(!selectedItem ) throw new Exception("Please select an item");
        return Inv.lookupOpener("market:billitem:details", [entity:selectedItem] );
    }
     
    def viewCashReceipt() {
        if(!entity.items)
           throw new Exception("Please select at least one item")
        return Inv.lookupOpener( "cashreceipt_preview", [entity:entity]);
    }

    /*
     //we specify this so print detail will appear.
    
    public def getPaymentInfo(def o) {
         return null;
    }
     
     
    
     
     def applyPartial() {
         if(!selectedItem) throw new Exception("Please select an item");
         def h = { o->
            def m = [objid:selectedItem.objid, partial: o, todate: selectedItem.todate, fromdate: selectedItem.fromdate];
            processBillingItem( m );
            selectedItem.putAll(m);
            itemHandler.reload();
            updateReceipt();
         }
         return Inv.lookupOpener( "decimal:prompt", [handler:h, title:'Enter Partial amount'])
     }
     */
   

}

