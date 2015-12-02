----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

CREATE VIEW RV_CG_Project AS
SELECT invoiceline.ad_client_id, invoiceline.ad_org_id, invoiceline.isactive, invoiceline.created, invoiceline.createdby, invoiceline.updated, invoiceline.updatedby, inv.issotrx, inv.c_invoice_id, inv.dateinvoiced, inv.totallines, inv.grandtotal, (inv.grandtotal - inv.totallines) AS imp, inv.docstatus, part.c_bpartner_id, part.value AS codprov, part.name AS nameprov, prod.m_product_id, prod.value AS codprod, prod.name AS nameprod, cc.name, inv.documentno, cc.c_project_id, invoiceline.qtyinvoiced, invoiceline.pricelist, invoiceline.linenetamt, invoiceline.taxamt, invoiceline.linetotalamt, invoiceline.priceactual FROM ((((c_invoiceline invoiceline LEFT JOIN m_product prod ON ((prod.m_product_id = invoiceline.m_product_id))) LEFT JOIN c_invoice inv ON ((inv.c_invoice_id = invoiceline.c_invoice_id))) LEFT JOIN c_bpartner part ON ((part.c_bpartner_id = inv.c_bpartner_id))) LEFT JOIN c_project cc ON ((cc.c_project_id = inv.c_project_id))) WHERE (((inv.isactive = 'Y'::bpchar) AND (cc.isactive = 'Y'::bpchar)) AND (inv.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar])));

CREATE VIEW RV_CG_InvCC AS
SELECT inv.ad_client_id, inv.ad_org_id, inv.isactive, inv.created, inv.createdby, inv.updated, inv.updatedby, inv.issotrx, inv.c_invoice_id, inv.dateinvoiced, inv.totallines, inv.grandtotal, (inv.grandtotal - inv.totallines) AS imp, inv.docstatus, inv.documentno, part.c_bpartner_id, part.value AS codprov, part.name AS nameprov, cc.name AS namecc, cc.c_project_id, inv.cg_trip_point_origin_id, punto.name FROM ((((c_invoice inv LEFT JOIN c_bpartner part ON ((part.c_bpartner_id = inv.c_bpartner_id))) LEFT JOIN cg_trip cv ON ((cv.cg_trip_id = inv.cg_trip_id))) LEFT JOIN cg_trip_point punto ON ((punto.cg_trip_point_id = inv.cg_trip_point_origin_id))) LEFT JOIN c_project cc ON ((cc.c_project_id = cv.c_project_id))) WHERE (((inv.isactive = 'Y'::bpchar) AND (inv.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))) AND (inv.issotrx = 'Y'::bpchar));

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

