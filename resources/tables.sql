-- Create table CG_TRIP_POINT
CREATE TABLE libertya.cg_trip_point
(
  cg_trip_point_id integer NOT NULL,
  ad_client_id integer NOT NULL,
  ad_org_id integer NOT NULL,
  isactive character(1) NOT NULL DEFAULT 'Y'::bpchar,
  created timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone,
  createdby integer NOT NULL,
  updated timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone,
  updatedby integer NOT NULL,
  name character varying(30) NOT NULL,
  isorigin character(1) NOT NULL DEFAULT 'Y'::bpchar,
  isdestination character(1) NOT NULL DEFAULT 'Y'::bpchar,
  c_location_id integer NOT NULL
  CONSTRAINT cg_trip_point_key PRIMARY KEY (cg_trip_point_id),
  CONSTRAINT cg_trip_point_client FOREIGN KEY (ad_client_id)
      REFERENCES libertya.ad_client (ad_client_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cg_trip_point_org FOREIGN KEY (ad_org_id)
      REFERENCES libertya.ad_org (ad_org_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cg_trip_point_location FOREIGN KEY (c_location_id)
      REFERENCES libertya.c_location (c_location_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=TRUE
);
ALTER TABLE libertya.cg_trip_point
  OWNER TO libertya;
-- End create table CG_TRIP_POINT

-- Create table CG_TRIP
CREATE TABLE libertya.cg_trip
(
  cg_trip_id integer NOT NULL,
  ad_client_id integer NOT NULL,
  ad_org_id integer NOT NULL,
  isactive character(1) NOT NULL DEFAULT 'Y'::bpchar,
  created timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone,
  createdby integer NOT NULL,
  updated timestamp without time zone NOT NULL DEFAULT ('now'::text)::timestamp(6) with time zone,
  updatedby integer NOT NULL,
  name character varying(30) NOT NULL,
  cg_trip_point_origin_id integer NOT NULL,
  cg_trip_point_destination_id integer NOT NULL,
  finished character(1) NOT NULL DEFAULT 'N'::bpchar,
  c_project_id integer,
  distance numeric(10,2) NOT NULL DEFAULT 0,
  CONSTRAINT cg_trip_key PRIMARY KEY (cg_trip_id),
  CONSTRAINT cg_trip_client FOREIGN KEY (ad_client_id)
      REFERENCES libertya.ad_client (ad_client_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cg_trip_org FOREIGN KEY (ad_org_id)
      REFERENCES libertya.ad_org (ad_org_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cg_trip_origin FOREIGN KEY (cg_trip_point_origin_id)
      REFERENCES libertya.cg_trip_point (cg_trip_point_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT cg_trip_destination FOREIGN KEY (cg_trip_point_destination_id)
      REFERENCES libertya.cg_trip_point (cg_trip_point_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=TRUE
);
ALTER TABLE libertya.cg_trip
  OWNER TO libertya;
-- End create table CG_TRIP
