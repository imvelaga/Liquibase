--Log: Liquibase formatted sql
--Log: for count in student table

--Log: ChangeSet create_studentCount_function:Shankar  
DROP FUNCTION IF EXISTS STUDENT_FUNCTION();
CREATE OR REPLACE FUNCTION public.student_function()
 RETURNS integer
 LANGUAGE plpgsql
AS $function$
declare
	student_count integer ;
begin
	
	select count(*) into student_count from	student;

return student_count;
end;

$function$;