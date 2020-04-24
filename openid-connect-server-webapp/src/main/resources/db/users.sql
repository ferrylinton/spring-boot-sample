--
-- Turn off autocommit and start a transaction so that we can use the temp tables
--

SET AUTOCOMMIT FALSE;

START TRANSACTION;

--
-- Insert user information into the temporary tables. To add users to the HSQL database, edit things here.
-- 

INSERT INTO users_TEMP (username, password, enabled) VALUES
  ('admin','password',true),
  ('admin1','password',true),
  ('admin2','password',true),
  ('admin3','password',true),
  ('admin4','password',true),
  ('admin5','password',true),
  ('admin6','password',true),
  ('admin7','password',true),
  ('admin8','password',true),
  ('admin9','password',true),
  ('admin10','password',true),
  ('user','password',true),
  ('user1','password',true),
  ('user2','password',true),
  ('user3','password',true),
  ('user4','password',true),
  ('user5','password',true),
  ('user6','password',true),
  ('user7','password',true),
  ('user8','password',true),
  ('user9','password',true),
  ('user10','password',true);


INSERT INTO authorities_TEMP (username, authority) VALUES
  ('admin','ROLE_ADMIN'),
  ('admin','ROLE_USER'),
  ('admin1','ROLE_ADMIN'),
  ('admin1','ROLE_USER'),
  ('admin2','ROLE_ADMIN'),
  ('admin2','ROLE_USER'),
  ('admin3','ROLE_ADMIN'),
  ('admin3','ROLE_USER'),
  ('admin4','ROLE_ADMIN'),
  ('admin4','ROLE_USER'),
  ('admin5','ROLE_ADMIN'),
  ('admin5','ROLE_USER'),
  ('admin6','ROLE_ADMIN'),
  ('admin6','ROLE_USER'),
  ('admin7','ROLE_ADMIN'),
  ('admin7','ROLE_USER'),
  ('admin8','ROLE_ADMIN'),
  ('admin8','ROLE_USER'),
  ('admin9','ROLE_ADMIN'),
  ('admin9','ROLE_USER'),
  ('admin10','ROLE_ADMIN'),
  ('admin10','ROLE_USER'),
  ('user','ROLE_USER'),
  ('user1','ROLE_USER'),
  ('user2','ROLE_USER'),
  ('user3','ROLE_USER'),
  ('user4','ROLE_USER'),
  ('user5','ROLE_USER'),
  ('user6','ROLE_USER'),
  ('user7','ROLE_USER'),
  ('user8','ROLE_USER'),
  ('user9','ROLE_USER'),
  ('user10','ROLE_USER');
    
-- By default, the username column here has to match the username column in the users table, above
INSERT INTO user_info_TEMP (sub, preferred_username, name, email, email_verified) VALUES
  ('90342.ASDFJWFA','admin','Demo Admin','admin@example.com', true),
  ('90311.ASDFJWFA','admin1','Demo Admin1','admin1@example.com', true),
  ('90322.ASDFJWFA','admin2','Demo Admin2','admin2@example.com', true),
  ('90333.ASDFJWFA','admin3','Demo Admin3','admin3@example.com', true),
  ('90344.ASDFJWFA','admin4','Demo Admin4','admin4@example.com', true),
  ('90355.ASDFJWFA','admin5','Demo Admin5','admin5@example.com', true),
  ('90366.ASDFJWFA','admin6','Demo Admin6','admin6@example.com', true),
  ('90377.ASDFJWFA','admin7','Demo Admin7','admin7@example.com', true),
  ('90388.ASDFJWFA','admin8','Demo Admin8','admin8@example.com', true),
  ('90399.ASDFJWFA','admin9','Demo Admin9','admin9@example.com', true),
  ('90300.ASDFJWFA','admin10','Demo Admin10','admin10@example.com', true),
  ('01921.FLANRJQW','user','Demo User','user@example.com', true),
  ('01911.FLANRJQW','user1','Demo User1','user1@example.com', true),
  ('01922.FLANRJQW','user2','Demo User2','user2@example.com', true),
  ('01933.FLANRJQW','user3','Demo User3','user3@example.com', true),
  ('01944.FLANRJQW','user4','Demo User4','user4@example.com', true),
  ('01955.FLANRJQW','user5','Demo User5','user5@example.com', true),
  ('01966.FLANRJQW','user6','Demo User6','user6@example.com', true),
  ('01977.FLANRJQW','user7','Demo User7','user7@example.com', true),
  ('01988.FLANRJQW','user8','Demo User8','user8@example.com', true),
  ('01999.FLANRJQW','user9','Demo User9','user9@example.com', true),
  ('01900.FLANRJQW','user10','Demo User10','user10@example.com', true);

 
--
-- Merge the temporary users safely into the database. This is a two-step process to keep users from being created on every startup with a persistent store.
--

MERGE INTO users 
  USING (SELECT username, password, enabled FROM users_TEMP) AS vals(username, password, enabled)
  ON vals.username = users.username
  WHEN NOT MATCHED THEN 
    INSERT (username, password, enabled) VALUES(vals.username, vals.password, vals.enabled);

MERGE INTO authorities 
  USING (SELECT username, authority FROM authorities_TEMP) AS vals(username, authority)
  ON vals.username = authorities.username AND vals.authority = authorities.authority
  WHEN NOT MATCHED THEN 
    INSERT (username,authority) values (vals.username, vals.authority);

MERGE INTO user_info 
  USING (SELECT sub, preferred_username, name, email, email_verified FROM user_info_TEMP) AS vals(sub, preferred_username, name, email, email_verified)
  ON vals.preferred_username = user_info.preferred_username
  WHEN NOT MATCHED THEN 
    INSERT (sub, preferred_username, name, email, email_verified) VALUES (vals.sub, vals.preferred_username, vals.name, vals.email, vals.email_verified);

    
-- 
-- Close the transaction and turn autocommit back on
-- 
    
COMMIT;

SET AUTOCOMMIT TRUE;

