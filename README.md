# Auth Microservice

## Summary
This auth microservice handles OAuth2 login with Google. Users are registered or found in the MySQL table, oauth_users.
The purpose is to validate identity, then issue a signed JWT token that other services can trust.

## How it Works

* A user signs in with Google --> Auth Service:
  * Auth service gets Google user info
  * Auth service finds or creates record in oauth_users 
  * Auth service issues a JWT with internal user ID (or UUID)
  * Auth service does not store domain-specific user data 
  * A separate users table will contain a oauth_users_id as a foreign key and this users table will contain the details of the user.
  
* Frontend gets the token → sends it to other services:
  * E.g., frontend calls /users/me on the main app 
  * The main app verifies JWT signature 
  * Extracts the userId claim 
  * Loads data for that user from its own users table

## Relevant Tables

* oauth_users 
  * id (PK)
  * email 
  * provider 
  * providerId 
  * lastLogin

* users 
  * id (PK)
  * oauth_user_id (FK)
  * username 
  * bio 
  * dob