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
END RESUME_PKG;

*****************************************************************************************************
//4. write queries to insert the records

create or replace PACKAGE BODY RESUME_PKG IS
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
END RESUME_PKG;