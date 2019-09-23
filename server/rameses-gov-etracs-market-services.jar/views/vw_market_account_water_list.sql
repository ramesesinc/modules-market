DROP VIEW IF EXISTS vw_market_account_water_list;
CREATE VIEW vw_market_account_water_list AS
SELECT mw.*, 
ma.acctno, 
ma.acctname,  
mu.code AS unitno
FROM market_account_water mw 
INNER JOIN market_account ma ON mw.acctid = ma.objid
INNER JOIN market_rentalunit mu ON ma.unitid = mu.objid