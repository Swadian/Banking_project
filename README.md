# Banking_project
My project for the PAO lab is a simple console app designed to manage a series of basic banking operations. 
## Entities
The package includes three "categories" of entities: accounts, cards and transfers. Each has its base class from which the others are extended. <br>
Accounts and cards can have one of three types: basic, credit and salary. Credit allows the balance to go into the negatives by way of a distinct negative balance,
while salary implements a method of adding a fixed amount to the balance, for convenience. <br>
Transfers can either be of the basic one-time type or have a built-in recurrence, with a method that automatically checks for any due re-payments and creates the 
necessary new transfers.
## Services
While the main class implements the GUI, the vast majority of the logic in the methods is located in the service class. One can declare multiple instances of the service class, if one desires to administrate different banking "databases" with their own lists and logs.
### Methods
The menu in the main class allows for the following methods to be called: <br>
0. Exit - self explanatory
1. Add Account - reads and adds a new account to the list
2. Add Card - reads and adds a new card to the list, associating it with either an already existing account or creating a new one
3. Add Transfer - does a one-time transfer of funds from one <b> card </b> to another
4. Print log of transfers - prints information about all the transfers in the log
5. Manually execute monthly operations - calls the monthly operations methods for all accounts (adds the salary to the balance and the interest to the credit)
6. Print card - accesses a card by code & PIN then prints its details
7. Print account balance - prints the balance (and credit where applicable) of an account
8. Print recurrent transfers of an account - prints details about all recurrent transfers that are set up to come from a certain account
9. Add new recurrent transfer - adds a new recurrent transfer
10. Check list for due transfers - checks if any recurrent transfers are due, makes the transactions and updates the timestamps as needed
