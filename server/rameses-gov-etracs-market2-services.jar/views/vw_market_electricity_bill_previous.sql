DROP VIEW IF EXISTS vw_market_electricity_bill_previous;
CREATE VIEW vw_market_electricity_bill_previous AS 
SELECT 
mbe.acctid,
mbs.billgroupid, 
mbs.year,
mbs.month, 
mbe.reading 
FROM market_electricity_bill mbe 
INNER JOIN market_bill_schedule mbs ON mbe.billscheduleid = mbs.objid;