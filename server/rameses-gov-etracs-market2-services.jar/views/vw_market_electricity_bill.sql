DROP VIEW IF EXISTS vw_market_electricity_bill;
CREATE VIEW vw_market_electricity_bill AS 
SELECT 
mbe.*,
mbs.year, 
mbs.month, 
prev.reading AS prevreading 
FROM market_electricity_bill mbe 
INNER JOIN market_bill_schedule mbs ON mbe.billscheduleid = mbs.objid 
LEFT JOIN vw_market_electricity_bill_previous prev 
  ON  mbe.acctid = prev.acctid 
	AND  ((mbs.year*12 )+mbs.month-1) = ((prev.year*12)+prev.month);