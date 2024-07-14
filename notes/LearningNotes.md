# Course Notes

## Use MVC, SOLID Principles, and Design Patterns in Java

### SOLID Design Principles
1. "S" is for Single responsibility
   A class should do one thing, and do it well. It should only have one reason to change.
   - unit tests are easier to write
   - somewhere that doesn't need to change
2. "O" is for Open/closed principle
   A class should be open to extension, but closed to modification.
   - algos that perform a calculation - likely to change. Create interface first, then add specific implementations
   - data coming or going from system - endpoint, database, external system. again, create interface
   - the "closed" class (our controller) is the class that doesn't change.
   - the "open" class (GameEvaluator interface) allows for flexibility of implementation, so we can easily change rules of the game.
3. "L" for Liskov substitutability
   Adding a subtype through inheritance should not break the existing code. I call this the "no surprises" principle. That is, if the system is working and I add a new class that is derived from another, the system should still work.
   - Barbara Liskov
   - Adding derived classes shouldn't break functionality of an already existing system
   - Ask yourself
     - Does the derived class have a meaningful implementation of all overridden methods? If so, good case
4. "I" for Interface segregation
   Essentially the single responsibility principle, applied to interfaces.
   - Interface does one specific thing really well.
5. "D" is for Dependency inversion
   High-level classes shouldn't have to change when a low-level class is modified. High-level classes should define an abstraction that the lower-level class conforms to.

### Model-View-Controller (MVC)
1. Model
   1. Manages system data structure. Holds the state information of the system
2. View
   1. Presents data to the user. Presents model information to the user
3. Controller
   1. Sequences program rules. Makes sure that user commands are executed correctly, modifying the appropriate model objects, and updating the commandLineView objects.

### Design Patterns
- Creational patterns add a level of indirection to object creation, adding flexibility to applications.
  - The factory pattern has an object create another object. You can configure the factory to make an object just the way you want it.
  - The prototype pattern creates a new object by cloning an existing object.
  - The builder pattern creates a complex object by assembling various other objects into the single, complex item.
- Structural patterns are ways of organizing classes or objects so that they are easy to use.
  - The adapter pattern changes the interface of a class from non-compatible, to one that is expected.
  - The composite pattern allows individual and collection objects to be treated the same.
  - The decorator pattern allows for additional behavior to be added and removed at runtime.
- Behavioral patterns
  - The observer pattern allows a state change in one object to be acted upon by many dependencies.
  - The strategy pattern allows for individual algorithms to be chosen for specific situations.
  - The state pattern allows for system behavior to vary depending on the current situation.


## Build Your Web Projects with REST APIs

### REST APIs
- API - Application Programming Interface
- Client requests data from server, and Server responds with data requested
- Private API enables only the authorized users within your organization or application to use the API to access the database.
- Public API allow others to access data, whether they are on your application or not. They allow developers to get data from other applications to enhance their own projects.
- Two types of Web APIs
  - SOAP APIs - Simple Object Access Protocol
  - REST APIs - REpresentational State Transfer
- REST Standards
  1. Client/server separation - enables the client to only deal with getting and displaying the information, while the server can focus on storing and manipulating the data
  2. Stateless - makes each request and response very purposeful and understandable
  3. Cacheable - the response should come with a version number. That way, if your user makes the same request twice (i.e., wants to see a page again) and the information hasn’t changed, your server doesn't have to do double the workload to get all the data.
  4. Uniform interface - a contract between the client and the service that all REST APIs share
  5. Layered System - a client that connects to “component A” has no idea if that component is interacting with “component B” or “component Z” afterward
  6. Code-on-demand architecture - the server can extend its functionality by sending the code to the client to download.
- REST data is represented by *Resources*. Each Resource has information about the data it contains.
  - ex. consider an app that lists Marvel heroes. One of the resources might be Superhero, and you might have a name, description, superpower, etc., as additional information
- A group of Resources is called a *Collection*
- *URI* (Uniform Resource Identifier) is the way you identify your resource
  - ex. Game of Thrones website, if the character of Jon Snow has the ID 890, the URI would be  /characters/890. The URL would be https://gameofthrones-information.com/characters/890
- Requests take the form: HTTP Verb + URI + HTTP Version + Headers + Optional Message Body
- Responses take the form: HTTP Response Code + HTTP Version + Headers + Message Body.
- CRUD Action - Associated HTTP Verb
  - Create - POST
  - Read - GET
  - Update - PUT
  - Delete - DELETE
- Create own REST API
  - Designing API
    1. What kind of endpoints will you need?
    2. What resources do you need to create?
    3. What resources will you need to preform CRUD operations on?
    4. Do you need all four CRUD operations for each resource?
- Advanced Endpoint Functionality
  1. Filter - ?
  2. Search - ?
  3. Sort - &
  4. Paginate - don't return all results
  5. Versioning - app stays backward compatible.
- REST API Frameworks
  1. Express.js - Javascript
  2. Ruby on Rails - Ruby
  3. Django - Python
  4. Flask - Python
  5. Spring - Java - https://spring.io/projects/spring-boot
  6. AWS API Gateway & AWS Lambda
