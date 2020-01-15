//1. create sequence
CREATE SEQUENCE prf_seq START WITH 1;
CREATE SEQUENCE resume_seq START WITH 1;

**************************************************************************************************

//2. create table
CREATE TABLE PROFILES (PROFILE_ID NUMBER(20) DEFAULT prf_seq.nextval NOT NULL, NAME VARCHAR2(20) NOT NULL, ROLE VARCHAR2(20) NOT NULL, CONSTRAINT PROFILE_PK PRIMARY KEY(PROFILE_ID));
create table RESUME_RECORDS (RESUME_ID NUMBER(20) DEFAULT resume_seq.nextval NOT NULL, SKILLSET VARCHAR2(300) NOT NULL, HOBBIES VARCHAR2(300) NOT NULL, PROFILE_ID NUMBER(20) NOT NULL REFERENCES PROFILES(PROFILE_ID));

***************************************************************************************************

//3. create procedures
create or replace PACKAGE RESUME_PKG IS

PROCEDURE createResume(
i_profile_id IN VARCHAR2,
i_skills IN VARCHAR2,
i_hobbies IN VARCHAR2,
o_resume_id OUT NUMBER
);
PROCEDURE createProfile(
i_name IN VARCHAR2,
i_role IN VARCHAR2,
o_profile_id OUT NUMBER
);
PROCEDURE getProfile(
i_profile_id IN NUMBER,
o_name OUT VARCHAR2,
o_role OUT VARCHAR2
);
PROCEDURE getResume(
i_resume_id IN NUMBER,
o_profile_id OUT NUMBER,
o_skills OUT VARCHAR2,
o_hobbies OUT VARCHAR2
);
----------------------------
/* update */
PROCEDURE updateResume(
i_resume_id IN NUMBER,
i_skills IN VARCHAR2,
i_hobbies IN VARCHAR2,
i_name IN VARCHAR2,
i_role IN VARCHAR2,
o_resume_id OUT NUMBER
);
END RESUME_PKG;
*****************************************************************************************************
//4. write queries to insert the records

create or replace PACKAGE BODY RESUME_PKG IS

/*create the resume*/

PROCEDURE createResume(
i_profile_id IN VARCHAR2,
i_skills IN VARCHAR2,
i_hobbies IN VARCHAR2,
o_resume_id OUT NUMBER
)
IS
BEGIN
insert into RESUME_RECORDS(SKILLSET,HOBBIES,PROFILE_ID) values(i_skills,i_hobbies,i_profile_id) RETURNING RESUME_ID into o_resume_id;
commit;
end createResume;
-------------------------------------------------------------------------------------
/*First create the profile*/

PROCEDURE createProfile(
i_name IN VARCHAR2,
i_role IN VARCHAR2,
o_profile_id OUT NUMBER
)
IS
BEGIN
insert into PROFILES(name,role) values(i_name,i_role) RETURNING PROFILE_ID into o_profile_id;
commit;
end createProfile;
---------------------------------------------------------------------------------------
/*retrieve profile*/

PROCEDURE getProfile(
i_profile_id IN NUMBER,
o_name OUT VARCHAR2,
o_role OUT VARCHAR2
)
IS
BEGIN
select name,role into o_name,o_role from PROFILES where PROFILE_ID = i_profile_Id;
end getProfile;

----------------------------------------------------------------------------------------
/*retrieve resume*/

PROCEDURE getResume(
i_resume_id IN NUMBER,
o_profile_id OUT NUMBER,
o_skills OUT VARCHAR2,
o_hobbies OUT VARCHAR2
)
IS
BEGIN
select profile_id,SKILLSET,hobbies into o_profile_id,o_skills,o_hobbies from RESUME_RECORDS where RESUME_ID = i_resume_id;;
end getResume;

------------------------------------------------------------------------------------------
/* update */
PROCEDURE updateResume(
i_resume_id IN NUMBER,
i_skills IN VARCHAR2,
i_hobbies IN VARCHAR2,
i_name IN VARCHAR2,
i_role IN VARCHAR2,
o_resume_id OUT NUMBER
)
IS
v_profile_id NUMBER;
BEGIN
update RESUME_RECORDS SET SKILLSET = i_skills, HOBBIES = i_hobbies where RESUME_ID = i_resume_id;
select profile_id into v_profile_id from RESUME_RECORDS where RESUME_ID = i_resume_id;
update PROFILES SET name = i_name, role = i_role where PROFILE_ID = v_profile_id;
select resume_id into o_resume_id from RESUME_RECORDS where PROFILE_ID = v_profile_id;
end updateResume;

END RESUME_PKG;