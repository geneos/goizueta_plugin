----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------
CREATE TABLE CG_Trip_Point(

cg_trip_point_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
created timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
createdby integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
updatedby integer NOT NULL ,
name character varying(30) NOT NULL ,
isorigin character(1) NOT NULL DEFAULT 'Y'::bpchar ,
isdestination character(1) NOT NULL DEFAULT 'Y'::bpchar ,
c_location_id integer NOT NULL ,
CONSTRAINT cg_trip_point_key PRIMARY KEY (cg_trip_point_id) ,
CONSTRAINT cg_trip_point_client FOREIGN KEY (ad_client_id) REFERENCES ad_client (ad_client_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cg_trip_point_location FOREIGN KEY (c_location_id) REFERENCES c_location (c_location_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cg_trip_point_org FOREIGN KEY (ad_org_id) REFERENCES ad_org (ad_org_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE CG_Trip(

cg_trip_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
created timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
createdby integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone ,
updatedby integer NOT NULL ,
name character varying(30) NOT NULL ,
cg_trip_point_origin_id integer NOT NULL ,
cg_trip_point_destination_id integer NOT NULL ,
finished character(1) NOT NULL DEFAULT 'N'::bpchar ,
c_project_id integer ,
distance numeric(10,2) NOT NULL DEFAULT 0 ,
CONSTRAINT cg_trip_key PRIMARY KEY (cg_trip_id) ,
CONSTRAINT cg_trip_client FOREIGN KEY (ad_client_id) REFERENCES ad_client (ad_client_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cg_trip_destination FOREIGN KEY (cg_trip_point_destination_id) REFERENCES cg_trip_point (cg_trip_point_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cg_trip_org FOREIGN KEY (ad_org_id) REFERENCES ad_org (ad_org_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cg_trip_origin FOREIGN KEY (cg_trip_point_origin_id) REFERENCES cg_trip_point (cg_trip_point_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

ALTER TABLE C_Invoice ADD COLUMN cg_declared_value numeric(24,2);
ALTER TABLE C_Invoice ADD COLUMN CG_Trip_Point_Destination_ID integer;
ALTER TABLE C_Invoice ADD COLUMN CG_Trip_Point_Origin_ID integer;
ALTER TABLE C_Invoice ADD COLUMN cg_sender_details character varying(150);
ALTER TABLE C_Invoice ADD COLUMN CG_Trip_ID integer;
ALTER TABLE C_Invoice ADD COLUMN cg_aditional_per_value numeric(10,2);

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

