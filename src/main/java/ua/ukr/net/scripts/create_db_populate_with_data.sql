drop table if exists DEPARTMENT;

drop table if exists EMPLOYEE;

drop table if exists EMPLOYEE_DEPARTMENT;

create table "EMPLOYEE"
(
  "ID"         BIGINT       NOT NULL AUTO_INCREMENT,
  "FIRST_NAME" VARCHAR(100) NOT NULL,
  "EMAIL"      VARCHAR(100) NOT NULL,
  "BIRTHDAY"   DATE         NOT NULL,
  DEPART_ID    INT REFERENCES DEPARTMENT (ID)
);


insert into EMPLOYEE (ID, FIRST_NAME, EMAIL, BIRTHDAY, DEPART_ID)
values
  (1, 'Mark', 'Markovich@mail.net', '1988-12-13', 1),
  (2, 'Marina', 'poli@ukr.net', '2000-05-13', 3),
  (3, 'Petr', 'petrosik@gmail.net', '2005-08-23', 2),
  (4, 'Gosh', 'lombok@meta.net', '1986-09-30', 1),
  (5, 'Mila', 'romik@ukr.net', '1996-06-27', 4),
  (6, 'Yaroslav', '113Yar@yahoo.com', '1980-10-21', 2),
  (7, 'Gena', '98Gen@.ru', '1996-06-27', 4),
  (8, 'Sergey', 'qqqq@ukr.com', '1975-04-15', 3),
  (9, 'Igor', 'igorNarkoman@ukr.org', '2000-01-27', 3),
  (10, 'Alex', 'face@ukr.org', '2008-06-06', 2);

create table "DEPARTMENT"
(
  "ID"   BIGINT       NOT NULL AUTO_INCREMENT,
  "NAME" VARCHAR(100) NOT NULL,
  CONSTRAINT "DEPARTMENT_PKEY" PRIMARY KEY ("ID")
);

insert into DEPARTMENT (ID, NAME)
values
  (1, 'Google'),
  (2, 'Yahoo'),
  (3, 'Oracle'),
  (4, 'Linux');

/*create table EMPLOYEE_DEPARTMENT
(
  "EMPLOYEE_ID"   BIGINT NOT NULL,
  "DEPARTMENT_ID" BIGINT NOT NULL,
  PRIMARY KEY (EMPLOYEE_ID, DEPARTMENT_ID),
  FOREIGN KEY (EMPLOYEE_ID) REFERENCES EMPLOYEE (ID)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT (ID)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);*/


//JOIN
// Это тебе образец как сджойнить три таблицы
SELECT
  EMPLOYEE_ID,
  FIRST_NAME,
  EMAIL,
  BIRTHDAY,
  DEPARTMENT_ID,
  NAME
FROM EMPLOYEE
  INNER JOIN EMPLOYEE_DEPARTMENT ON EMPLOYEE.ID = EMPLOYEE_DEPARTMENT.EMPLOYEE_ID
  INNER JOIN DEPARTMENT ON EMPLOYEE_DEPARTMENT.DEPARTMENT_ID = DEPARTMENT.ID;

// Запрос чтоб вытащить количество сотрудников по департаментам
SELECT
  NAME,
  COUNT(EMPLOYEE_ID)
FROM (
  SELECT
    EMPLOYEE_ID,
    FIRST_NAME,
    EMAIL,
    BIRTHDAY,
    DEPARTMENT_ID,
    NAME
  FROM EMPLOYEE
    INNER JOIN EMPLOYEE_DEPARTMENT ON EMPLOYEE.ID = EMPLOYEE_DEPARTMENT.EMPLOYEE_ID
    INNER JOIN DEPARTMENT ON EMPLOYEE_DEPARTMENT.DEPARTMENT_ID = DEPARTMENT.ID
)
GROUP BY NAME;

// ВОТ ЭТОТ ЗАПРОС РАЗБЕРИ И ИСПОЛЬЗУЕШЬ ДЛЯ МЕТОДА
SELECT
  NAME,
  COUNT(*) as EMPL_COUNT
FROM (
  SELECT NAME
  FROM EMPLOYEE
    INNER JOIN EMPLOYEE_DEPARTMENT ON EMPLOYEE.ID = EMPLOYEE_DEPARTMENT.EMPLOYEE_ID
    INNER JOIN DEPARTMENT ON EMPLOYEE_DEPARTMENT.DEPARTMENT_ID = DEPARTMENT.ID
)
GROUP BY NAME;

SELECT *
FROM department
WHERE name = 'Oracle';
