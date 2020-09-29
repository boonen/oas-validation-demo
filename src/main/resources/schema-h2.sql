-- Needed to add H2GIS support for spatial data types
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();

alter table document_metadata alter column id type bigint auto_increment;