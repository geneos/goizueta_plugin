----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

CREATE TABLE CGFE_Invoice_Changelog(

cgfe_invoice_changelog_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
created timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
createdby integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
updatedby integer NOT NULL ,
ad_session_id integer NOT NULL ,
documentno character varying(30) NOT NULL ,
c_invoice_id numeric(18) NOT NULL ,
success character(1) NOT NULL DEFAULT 'N'::bpchar ,
requestxml character varying(4000) ,
responsexml character varying(4000) ,
msgerror character varying(200) ,
CONSTRAINT cgfe_invoice_changelog_key PRIMARY KEY (cgfe_invoice_changelog_id) );

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

