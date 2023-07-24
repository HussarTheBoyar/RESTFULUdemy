insert into USER_DETAILS  (Id, name, birth_date)
values
  (1001, 'TungPL1', now())
, (1002, 'QuangPV1', now())
, (1003, 'ThoTV9', now());

insert into post(id, description, user_id)
values
 (2002, 'Clear session in tonight', 1001)
,(2003, 'Clear session Udemy in tonight', 1001)
,(2004, 'Clear session Git flow in tonight', 1001)
,(2005, 'Test data', 1002);