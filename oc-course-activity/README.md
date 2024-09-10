# TODO
Move all the logic regarding initializing and searching the rental data into a repository class called RentRepository.

Move the calculation logic regarding rent calculation in the target currency into a service class RentService.

Move the logic to call the remote API, and get the conversion rate into a service called CurrencyService.

Instead of creating  new  service and repository instances, create constructors and use dependency injection annotations to wire your components to each other.

Organize classes into packages.
