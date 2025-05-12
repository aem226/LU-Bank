# LUBANK Project Walkthrough & Setup Guide
## Overview
This project simulates a banking system that allows users to interact with various financial services through a Java-based interface connected to a SQL database. Key features include user authentication, account management, deposits, loans, card services, and investment tracking.

⚠️ Note: The system requires a live database connection. Without it, some features may not be visible or functional.

Bank Manager Login
The Bank Manager can insert a designated PIN to access backend functionalities.

Customer Login
Example PIN: 08-112-2026 (used throughout this walkthrough)

Once logged in, a menu will display options such as:

- Checking
- Savings
 
## Investments

- Payments & Purchases

- Deposits

- Loans

- Cards

As you navigate through the menu, your bank account details will become visible and interactive.

Checking Account
Enhanced Security: Requires two-step authentication.

PIN: 08-112-2026

Checking Number: 12345678

Security ID: 817234

Upon successful login, you can:

View balance

## View transaction history

To deposit:

- Select option 5 from the main menu.

- Enter your Checking Number and Security ID again.

Input your deposit amount and a description.

After successful submission, return to the checking account view (with re-authentication) to see your updated balance.

Loans (Secured & Unsecured)
System prompts to apply for a loan.

Example Loan ID: 1234

Choose either:

- Secured Loan

- Unsecured Loan

Based on your selection, relevant loan information will be displayed.

Credit & Debit Cards
To replace or view cards, enter your checking account ID.

For Debit Cards: Requires Check ID = 12345678

For Credit Cards: Requires valid PIN (e.g., 08-112-2026)

If incorrect PIN is entered, the system will prompt repeatedly until a valid one is provided.


## Project Requirements
- .jar file

- .java source files

- .class compiled files

- .txt data files

## Key Features
- Java + SQL integration

- Real-time input and response handling

- Authentication and validation

- Error handling for invalid input and logic flows

## Compilation & Execution:

Run Java Program with Oracle JDBC Driver:   
- java -cp .:aem226.jar LUBank

## ERD DIAGRAM
![image](https://github.com/user-attachments/assets/70ccc8bf-7ec6-48e8-b908-2a83745ee692)
