# Banking_project
My project for the PAO lab is a simple console app designed to manage a series of basic banking operations. 
## Entities
The package includes three "categories" of entities: accounts, cards and transfers. Each has its base class from which the other are extended. <br>
Accounts and cards can havethree types: basic, credit and salary. Credit allows the balance to go into the negatives by way of a distinct negative balance,
while salary implements a basic method of adding a fixed amount to the balance, for convenience. <br>
Transfers can either be of the basic one-time type or have a built-in recurrence, with a method that automatically checks for any due re-payments and creates the 
necessary new transfers.
