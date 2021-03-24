DROP VIEW IF EXISTS vw_market_rental_bill;
CREATE VIEW vw_market_rental_bill AS 
SELECT
mb.*,
mbs.year,
mbs.month,
mbs.fromdate,
mbs.todate,
mbs.duedate,
mai.rate,
mai.ratetype
FROM market_rental_bill mb
INNER JOIN market_bill_schedule mbs ON mb.billscheduleid = mbs.objid
INNER JOIN market_account_info mai ON mb.acctinfoid = mai.objid 


