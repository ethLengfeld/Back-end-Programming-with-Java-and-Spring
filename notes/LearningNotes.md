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
4. "I" for Interface segregation
   Essentially the single responsibility principle, applied to interfaces.
5. "D" is for Dependency inversion
   High-level classes shouldn't have to change when a low-level class is modified. High-level classes should define an abstraction that the lower-level class conforms to.

### Model-View-Controller (MVC)
1. Model
   1. Manages system data structure. Holds the state information of the system
2. View
   1. Presents data to the user. Presents model information to the user
3. Controller
   1. Sequences program rules. Makes sure that user commands are executed correctly, modifying the appropriate model objects, and updating the commandLineView objects.
