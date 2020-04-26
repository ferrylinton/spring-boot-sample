--
-- Turn off autocommit and start a transaction so that we can use the temp tables
--

SET AUTOCOMMIT FALSE;

START TRANSACTION;

--
-- Insert client information into the temporary tables. To add clients to the HSQL database, edit things here.
-- 

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client', 'secret', 'Test Client', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client1', 'secret', 'Test Client1', false, null, 3600, 600, true);

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client2', 'secret', 'Test Client2', false, null, 3600, 600, true);

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client3', 'secret', 'Test Client3', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client4', 'secret', 'Test Client4', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client5', 'secret', 'Test Client5', false, null, 3600, 600, true);

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client6', 'secret', 'Test Client6', false, null, 3600, 600, true);

INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client7', 'secret', 'Test Client7', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client8', 'secret', 'Test Client8', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client9', 'secret', 'Test Client9', false, null, 3600, 600, true);
	
INSERT INTO client_details_TEMP (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES
	('client10', 'secret', 'Test Client10', false, null, 3600, 600, true);

INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client', 'openid'),
	('client', 'profile'),
	('client', 'email'),
	('client', 'address'),
	('client', 'phone'),
	('client', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client1', 'openid'),
	('client1', 'profile'),
	('client1', 'email'),
	('client1', 'address'),
	('client1', 'phone'),
	('client1', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client2', 'openid'),
	('client2', 'profile'),
	('client2', 'email'),
	('client2', 'address'),
	('client2', 'phone'),
	('client2', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client3', 'openid'),
	('client3', 'profile'),
	('client3', 'email'),
	('client3', 'address'),
	('client3', 'phone'),
	('client3', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client4', 'openid'),
	('client4', 'profile'),
	('client4', 'email'),
	('client4', 'address'),
	('client4', 'phone'),
	('client4', 'offline_access');

INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client5', 'openid'),
	('client5', 'profile'),
	('client5', 'email'),
	('client5', 'address'),
	('client5', 'phone'),
	('client5', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client6', 'openid'),
	('client6', 'profile'),
	('client6', 'email'),
	('client6', 'address'),
	('client6', 'phone'),
	('client6', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client7', 'openid'),
	('client7', 'profile'),
	('client7', 'email'),
	('client7', 'address'),
	('client7', 'phone'),
	('client7', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client8', 'openid'),
	('client8', 'profile'),
	('client8', 'email'),
	('client8', 'address'),
	('client8', 'phone'),
	('client8', 'offline_access');

INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client9', 'openid'),
	('client9', 'profile'),
	('client9', 'email'),
	('client9', 'address'),
	('client9', 'phone'),
	('client9', 'offline_access');
	
INSERT INTO client_scope_TEMP (owner_id, scope) VALUES
	('client10', 'openid'),
	('client10', 'profile'),
	('client10', 'email'),
	('client10', 'address'),
	('client10', 'phone'),
	('client10', 'offline_access');
		
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client', 'http://localhost/'),
	('client', 'http://localhost:8080/');

INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client1', 'http://localhost/'),
	('client1', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client2', 'http://localhost/'),
	('client2', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client3', 'http://localhost/'),
	('client3', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client4', 'http://localhost/'),
	('client4', 'http://localhost:8080/');

INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client5', 'http://localhost/'),
	('client5', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client6', 'http://localhost/'),
	('client6', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client7', 'http://localhost/'),
	('client7', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client8', 'http://localhost/'),
	('client8', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client9', 'http://localhost/'),
	('client9', 'http://localhost:8080/');
	
INSERT INTO client_redirect_uri_TEMP (owner_id, redirect_uri) VALUES
	('client10', 'http://localhost/'),
	('client10', 'http://localhost:8080/');
		
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client', 'authorization_code'),
	('client', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client', 'implicit'),
	('client', 'refresh_token'),
	('client', 'password');

INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client1', 'authorization_code'),
	('client1', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client1', 'implicit'),
	('client1', 'refresh_token'),
	('client1', 'password');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client2', 'authorization_code'),
	('client2', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client2', 'implicit'),
	('client2', 'refresh_token'),
	('client2', 'password');	
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client3', 'authorization_code'),
	('client3', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client3', 'implicit'),
	('client3', 'refresh_token'),
	('client3', 'password');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client4', 'authorization_code'),
	('client4', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client4', 'implicit'),
	('client4', 'refresh_token'),
	('client4', 'password');	
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client5', 'authorization_code'),
	('client5', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client5', 'implicit'),
	('client5', 'refresh_token'),
	('client5', 'password');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client6', 'authorization_code'),
	('client6', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client6', 'implicit'),
	('client6', 'refresh_token'),
	('client6', 'password');	
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client7', 'authorization_code'),
	('client7', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client7', 'implicit'),
	('client7', 'refresh_token'),
	('client7', 'password');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client8', 'authorization_code'),
	('client8', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client8', 'implicit'),
	('client8', 'refresh_token'),
	('client8', 'password');	
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client9', 'authorization_code'),
	('client9', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client9', 'implicit'),
	('client9', 'refresh_token'),
	('client9', 'password');
	
INSERT INTO client_grant_type_TEMP (owner_id, grant_type) VALUES
	('client10', 'authorization_code'),
	('client10', 'urn:ietf:params:oauth:grant_type:redelegate'),
	('client10', 'implicit'),
	('client10', 'refresh_token'),
	('client10', 'password');	

--
-- Merge the temporary clients safely into the database. This is a two-step process to keep clients from being created on every startup with a persistent store.
--

MERGE INTO client_details 
  USING (SELECT client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection FROM client_details_TEMP) AS vals(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection)
  ON vals.client_id = client_details.client_id
  WHEN NOT MATCHED THEN 
    INSERT (client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection) VALUES(client_id, client_secret, client_name, dynamically_registered, refresh_token_validity_seconds, access_token_validity_seconds, id_token_validity_seconds, allow_introspection);

MERGE INTO client_scope 
  USING (SELECT id, scope FROM client_scope_TEMP, client_details WHERE client_details.client_id = client_scope_TEMP.owner_id) AS vals(id, scope)
  ON vals.id = client_scope.owner_id AND vals.scope = client_scope.scope
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, scope) values (vals.id, vals.scope);

MERGE INTO client_redirect_uri 
  USING (SELECT id, redirect_uri FROM client_redirect_uri_TEMP, client_details WHERE client_details.client_id = client_redirect_uri_TEMP.owner_id) AS vals(id, redirect_uri)
  ON vals.id = client_redirect_uri.owner_id AND vals.redirect_uri = client_redirect_uri.redirect_uri
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, redirect_uri) values (vals.id, vals.redirect_uri);

MERGE INTO client_grant_type 
  USING (SELECT id, grant_type FROM client_grant_type_TEMP, client_details WHERE client_details.client_id = client_grant_type_TEMP.owner_id) AS vals(id, grant_type)
  ON vals.id = client_grant_type.owner_id AND vals.grant_type = client_grant_type.grant_type
  WHEN NOT MATCHED THEN 
    INSERT (owner_id, grant_type) values (vals.id, vals.grant_type);
    
-- 
-- Close the transaction and turn autocommit back on
-- 
    
COMMIT;

SET AUTOCOMMIT TRUE;
		