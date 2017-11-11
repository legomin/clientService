Client service
===


## Deployment/running

* Download and unzip the source repository
* cd into root app dir `client-service`
* execute `mvn package && java -jar target/client-service-0.1.0.jar`

## Api description

 ### Get clients list
 
 #### Uri 
 GET `/clients`
 
 #### Output
 
 array of client names
 
 
 ```JSON
  ["Mike Tyson", "Frodo Baggins"]
 ```
 
 ***
 
 ### Add new client
 
 #### Uri 
 POST `/clients` 
 
 #### Params
 
  Parameter | Type 		| Description
  ---		|---		|---
  name      |String     | new client name 
  
 #### Output
  
  empty with 201 code response when success
  or error description with 403 code
  
 ***
 
 ### Get client info
 
 #### Uri 
 GET `/clients/{clientName}` 
 
 #### Params
 
  Parameter | Type 		| Description
  ---		|---		|---
  name      |String     | path variable client name 
  
 #### Output
  
  client name & client phone numbers array with 200 code response when success
  or error description with 403 code

  ```JSON
   {"name":"Mike Tyson", "phoneNumbers":["+12345678901","+23456789012"]}
  ```

 ***
 
 ### Add/update client phone number
 
 #### Uri 
 POST `/clients/{clientName}` 
 
 #### Params
 
  Parameter | Type 		| Description
  ---		|---		|---
  name      |String     | path variable client name 
  number    |String     | phone number 
  id        |Integer    | id(list position) of updated number or empty, non-optional 
  
 #### Output
  
  empty with 201(added) or 202(updated) code response when success
  or error description with 403 code
  