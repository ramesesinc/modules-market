DROP VIEW IF EXISTS vw_market_electricity_current;
CREATE VIEW vw_market_electricity_current AS
SELECT me.*,
prev.objid AS prev_objid,
prev.reading AS prev_reading,
prev.month AS prev_month,
prev.year AS prev_year
FROM market_electricity me 
LEFT JOIN market_electricity prev ON me.previd = prev.objid
WHERE me.nextid IS NULL


