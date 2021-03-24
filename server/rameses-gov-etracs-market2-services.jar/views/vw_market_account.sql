DROP VIEW IF EXISTS vw_market_account;
CREATE VIEW vw_market_account AS 
SELECT
ma.*,
mai.acctname,
mai.areasqm,
mai.owner_objid,
mai.owner_name,
mai.owner_address_text,
mai.contact_mobileno,
mai.contact_email,
mai.contact_phoneno,
mai.rate,
mai.ratetype,
mai.units,
mai.unitids,
mai.paymentmode,
mai.txnid,
mai.txntype,
mo.name AS org_name

FROM market_account ma 
INNER JOIN market_account_info mai ON ma.acctinfoid = mai.objid
INNER JOIN market_org mo ON ma.orgid = mo.objid


