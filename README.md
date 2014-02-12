twitter-challenge
=================

# Content Negotiation
Responses can be sent in both JSON or XML at the client criteria.
The default is **JSON**.
A request can influence its response format by setting the appropriated headers:

** JSON **
Content-Type=application/json
Accept=application/json

** XML **
Content-Type=application/json
Accept=application/json

# Security
_ALL_ requests, with the obvious exception of the "login" are secured to non authorised access.
The authentication process is started by a call to the login endpoint:
	[POST]   /rest/security/login
	**example:**
	Request
		POST /rest/security/login HTTP/1.1
		Host: twitter-challenge.twimba.com
		Content-Type: application/json
		Accept: application/json
		Cache-Control: no-cache
	Response
		1852cba4-c8b3-4d86-b2b0-972d3d345408

The response of the login endpoint is a valid and authenticated token that has to be sent as a query parameter in all the reuquests
	**example:**
	Request
		GET /rest/users/?token=1852cba4-c8b3-4d86-b2b0-972d3d345408 HTTP/1.1
		Host: twitter-challenge.twimba.com
		Content-Type: application/json
		Accept: application/json
		Cache-Control: no-cache

	Response
		{
		    "users": [
		        {
		            "id": 1,
		            "username": "luis.santos",
		            "name": "Luis Santos"
		        },
		        {
		            "id": 2,
		            "username": "luis",
		            "name": "Luis"
		        },
		        {
		            "id": 3,
		            "username": "filipe",
		            "name": "Filipe"
		        },
    ]
}


# Available actions

## Security
* [POST]   /rest/security/login
	* Issues a token that has to be used in all consequent request for them to be authorised
* [POST]   /rest/security/logout
	* Invalidates the authentication token. It can no longer be used.

## Tweets	
* [GET]    /rest/tweets/{username}/timeline [?search=...]
	* Return the list of tweets that compose the user timeline (his, and the ones from the users that he follows). An optional search string can be used to filter out tweets
* [PUT]    /rest/tweets/{username}
	* Adds a new tweet to a user timeline

## Users
* [PUT]    /rest/users/{username}?name=...
	* Creates a new user in the system
* [GET]    /rest/users/{username}	
	* Gets all the available user information from a username
* [GET]    /rest/users/{username}/followers
	* Gets all the users that follow a specified user
* [GET]    /rest/users/{username}/following
	* Gets all the users that a user follows
* [POST]   /rest/users/{username}/follow/{otherUsername}
	* Makes a user follow another user.
* [DELETE] /rest/users/{username}/follow/{otherUsername}
	*Makes a user unfollow another user.
* [GET]    /rest/users/
	* Lists all the users in the system

# Client
A command line client can be found here: https://github.com/tuxetuxe/twitter-challenge-client