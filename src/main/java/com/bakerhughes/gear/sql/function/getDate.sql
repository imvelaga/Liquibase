--Log: Liquibase formatted sql
--Log: ChangeSet create_getDate_function:Shankar

DROP FUNCTION IF EXISTS gear_persona.getDate();

CREATE OR replace FUNCTION gear_persona.getDate()
 RETURNS date 
 LANGUAGE plpgsql
AS $function$
declare
	Today_date date ;
begin
	
	select now() into Today_date;

return Today_date;
end;

$function$;