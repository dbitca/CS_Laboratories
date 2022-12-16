# Web Authentication and Authorisation

### Course: Cryptography & Security
### Author: Bitca Dina

----

## Objectives:

* Take classes from previous laboratory works and add them in a web service.
* Implement basic authentication and MFA
* Simulate user authorisation

## Implementation description

Authentication technology provides access control for systems by checking to see if a user's credentials match the 
credentials in a database of authorized users or in a data authentication server. In doing this, 
authentication assures secure systems, secure processes and enterprise information security.
The terms authentication and authorization are often used interchangeably.
While they are often implemented together, they are two distinct functions. 
Authentication is the process of validating the identity of a registered user or process before enabling access to 
protected networks and systems. Authorization is a more granular process that validates that the authenticated user or 
process has been granted permission to gain access to the specific resource that has been requested.
For the scope of this laboratory work, no database has been used. The authentication credentials are stored in-memory, 
using a simple ArrayList.
This laboratory has been implemented using Spring Web and Spring Security tools.
Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto 
standard for securing Spring-based applications.

Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. 
Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements

* Authentication implementation

The Security Configuration has a fairly simple implementation.
The user details are comprised in a predefined UserDetails object and comprised in an array list.
See code snippet below:

```
List<UserDetails> userDetailsList = new ArrayList<>();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
```

This functionality is defined in class "InMemoryUserDetailsManagement" 
which is part of Spring security.

This class provides multiple methods that allow the creation of pre-populated list
users and their roles. However, this class can be modified so it would allow
dynamic user addition and removal. However this is outside the scope of this laboratory work.
Another important Spring Security functionality is that it automatically generates a log in page and, if
credentials are not provided in the code, it will autogenerate a Sign In token along with the default username: "user".
After adding however many users needed, this class returns a new object of type "InMemoryUserDetailsManager".
See code snippet below:
````
return new InMemoryUserDetailsManager(userDetailsList);
````

* Providing access to simple Encryption methods

Access for encryption methods have been provided in the controller class using GET requests.
The plaintext and key are passed into the url along with the getmapping request. Before being able to access any 
encryption method the user has to log in.
See code snippet for Simple Caesar method acces:
````
 @GetMapping("/caesar/{plaintext}/{key}")
    public String accesCaesarCipher(@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult) {
        SimpleCaesar simpleCaesar = new SimpleCaesar();
        return simpleCaesar.encrypt(plaintext, key);
    }
````
The access method for all the other simple encryption method have been provided in the exact same way.
See another code snippet for Playfair encryption method:
````
 @GetMapping("/playfair/{plaintext}/{key}")
    public String accessPlayfair (@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult){
        Playfair playfair = new Playfair();
        return playfair.encrypt(plaintext, key);
    }
````

## Conclusions 

This laboratory work has been a valuable insight into the numerous authentication methods and provided valuable information
about its implementation. The work with web servers has been a step forward toward the reality and complexity of real life 
projects and gave new views into their complexity.