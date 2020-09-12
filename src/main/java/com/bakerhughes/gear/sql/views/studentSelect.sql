--Log: Liquibase formatted sql
--Log: for select from student table
--Log: ChangeSet create_view:Shankar

DROP VIEW IF EXISTS STUDENT_V;

CREATE OR REPLACE VIEW PUBLIC.STUDENT_V
AS SELECT ID, STUDENT_NAME, HEIGHT FROM PUBLIC.STUDENT;