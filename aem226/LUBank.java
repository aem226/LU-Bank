import java.sql.*;
import java.sql.Date;
import java.util.*;

public class LUBank {
 
    static final String DB_URL = "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241";

    public static void main(String[] args) {
        Connection conn = null; 
        Scanner in = new Scanner(System.in);
        int choice = 0;

        do {
            try {
            
                System.out.print("Enter Oracle user id: ");
                String user = in.nextLine();
                System.out.print("Enter Oracle user password: ");
                String pass = in.nextLine();

                conn = DriverManager.getConnection(DB_URL, user, pass);
                System.out.println("Super! I'm connected.");

                do {
                    System.out.println("\n------ LU Bank ------");
                    System.out.println("(1) Management Account");
                    System.out.println("(2) Customer Login");
                    System.out.println("(3) Quit");
                    System.out.print("Enter your choice: ");

                    choice = in.nextInt();
                    in.nextLine(); 
                    switch (choice) {
                        case 1:
                            Bankreview(conn);
                            break;
                        case 2:
                            AcctLogin(conn); 
                            break;
                        case 3:
                            System.out.println("Exiting... Thank you for using our bank!");
                            break;
                        default:
                            System.out.println("Invalid choice, please try again.");
                            break;
                    }
                } while (choice != 3);

            } catch (SQLException se) {
                se.printStackTrace();
                System.out.println("Error: Connection error. Re-enter login data:");
            }
        } while (conn == null);

        in.close();
    }

    public static void Bankreview(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        String correctPin = "1324"; // Manager's PIN
        String enteredPin = "";
    
       
        while (!correctPin.equals(enteredPin)) {
            System.out.println("Submit PIN: ");
            System.out.println("The Manager's PIN is 1324");
            enteredPin = scanner.nextLine(); // Get the entered PIN
    
            if (correctPin.equals(enteredPin)) {
                System.out.println("PIN accepted. Access granted.");
                displayAllinfo(conn);  // Proceed to display the information
            } else {
                System.out.println("Incorrect PIN. Access denied. Please try again.");
            }
        }
    }
    public static void displayAllinfo(Connection conn) {
        String accountQuery = "SELECT * FROM ACCOUNT";
        String customerQuery = "SELECT * FROM COSTUMER";
        String loanTransactionQuery = "SELECT * FROM LOANTRANSACTION";
        String investmentAccountQuery = "SELECT * FROM INVESTMENTACCOUNT";
        String savingAccountQuery = "SELECT * FROM SAVINGACCOUNT";
        String checkingAccountQuery = "SELECT * FROM CHECKACCOUNT";
    
        System.out.println("\n--- Account Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(accountQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String accountId = rs.getString("ACCOUNT_ID");
                String pin = rs.getString("PIN");
                String acctType = rs.getString("ACCT_TYPE");
                double acctBalance = rs.getDouble("ACCT_BAL");
                long routNum = rs.getLong("ROUT_NUM");
    
                System.out.printf("Account ID: %s | PIN: %s | Type: %s | Balance: %.2f | Routing Number: %d%n",
                        accountId, pin, acctType, acctBalance, routNum);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving account information: " + e.getMessage());
        }
    
        System.out.println("\n--- Customer Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(customerQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                long phoneNumber = rs.getLong("PHONENUMBER");
                String email = rs.getString("EMAIL");
                String billingAddress = rs.getString("BILLINGADDRESS");
                String dateOfBirth = rs.getString("DATEOFBIRTH");
                String pin = rs.getString("PIN");
    
                System.out.printf("Name: %s %s | Phone: %d | Email: %s | Address: %s | DOB: %s | PIN: %s%n",
                        firstName, lastName, phoneNumber, email, billingAddress, dateOfBirth, pin);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customer information: " + e.getMessage());
        }
    
        // Display loan transaction info from the LOANTRANSACTION table
        System.out.println("\n--- Loan Transaction Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(loanTransactionQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String loanTransId = rs.getString("LOANTRANS_ID");
                String transId = rs.getString("TRANS_ID");
                Date currentDate = rs.getDate("CURRENT_DATE");
                double tAmount = rs.getDouble("T_AMOUNT");
    
                System.out.printf("Loan Trans ID: %s | Trans ID: %s | Date: %s | Amount: %.2f%n",
                        loanTransId, transId, currentDate, tAmount);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving loan transaction information: " + e.getMessage());
        }
    
      
        System.out.println("\n--- Investment Account Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(investmentAccountQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String investId = rs.getString("INVEST_ID");
                String clientId = rs.getString("CLIENT_ID");
                double amountInvest = rs.getDouble("AMOUNT_INVEST");
                double interested = rs.getDouble("INTERESTED");
                String investType = rs.getString("INVEST_TYPE");
    
                System.out.printf("Invest ID: %s | Client ID: %s | Amount Invested: %.2f | Interest: %.2f | Type: %s%n",
                        investId, clientId, amountInvest, interested, investType);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving investment account information: " + e.getMessage());
        }
    
        // Display saving account info from the SAVINGACCOUNT table
        System.out.println("\n--- Saving Account Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(savingAccountQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String savingId = rs.getString("SAVING_ID");
                double balance = rs.getDouble("BALANCE");
                double interestRate = rs.getDouble("INTEREST_RATE");
                String accountId = rs.getString("ACCOUNT_ID");
    
                System.out.printf("Saving ID: %s | Balance: %.2f | Interest Rate: %.2f | Account ID: %s%n",
                        savingId, balance, interestRate, accountId);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving saving account information: " + e.getMessage());
        }
    
        // Display checking account info from the CHECKACCOUNT table
        System.out.println("\n--- Checking Account Information ---");
        try (PreparedStatement pstmt = conn.prepareStatement(checkingAccountQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String checkId = rs.getString("CHECK_ID");
                String checkTransId = rs.getString("CHECKTRANS_ID");
                double balance = rs.getDouble("BALANCE");
                double overdraftLimit = rs.getDouble("OVERDRAFT_LIM");
    
                System.out.printf("Check ID: %s | Check Trans ID: %s | Balance: %.2f | Overdraft Limit: %.2f%n",
                        checkId, checkTransId, balance, overdraftLimit);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving checking account information: " + e.getMessage());
        }
    }
    

    public static void AcctLogin(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean isValidLogin = false;
    
        // Keep asking for the PIN until it is correct
        while (!isValidLogin) {

            System.out.print("Enter your PIN: ");
            String pin = scanner.nextLine();
    
            try {
                
                if (isLoginValid(conn, pin)) {
                    System.out.println("Login successful!");
                    System.out.println(" ");
                    Accounts(conn);  
                    isValidLogin = true; 
                } else {
                    System.out.println("Incorrect PIN. Please try again.");
           
                }
            } catch (SQLException e) {
                // Handle SQL exceptions gracefully
                System.out.println("An error occurred while checking the PIN. Please try again later.");
                e.printStackTrace();  
            
            }
        }
    }

    public static void Accounts(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        int accountChoice;
       do{
        System.out.println(" ");
        System.out.println("(1) Checking Account"); //done
        System.out.println("(2) Savings Account");
        System.out.println("(3) Investment Account");
        System.out.println("(4) Payment & Transactions");
        System.out.println("(5) Account Deposit"); //done
        System.out.println("(6) Secured & Unsecured Loans");//done
        System.out.println("(7) New or replacement card"); //done
        System.out.println("(8) Back to Login Menu");
        
        System.out.print("Enter your choice: ");
         accountChoice = scanner.nextInt();

        switch (accountChoice) {
            case 1:
                checkingAccounts(conn);
                break;
            case 2:
                savingAccounts(conn);
                break;
            case 3:
            investmentAccounts(conn);
            
                break;
            case 4:
               // paymentTransactions();
                break;
            case 5:
                accountDeposit(conn);
                break;
            case 6:
                loans(conn);
                break;
            case 7:
                cardTransaction(conn);
                break;
        }
    } while (accountChoice != 8); 
    }

    public static void investmentAccounts(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your CLIENT_ID: ");
        String clientId = scanner.nextLine(); // Read the user input for CLIENT_ID

        // Define the SQL query to fetch investment account details based on CLIENT_ID
        String sql = "SELECT * FROM INVESTMENTACCOUNT WHERE CLIENT_ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the CLIENT_ID parameter in the query
            pstmt.setString(1, clientId);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if there are results
                if (rs.next()) {
                    // Print the details of the investment account
                    System.out.println("Investment Account Details:");
                    System.out.println("INVEST_ID: " + rs.getString("INVEST_ID"));
                    System.out.println("CLIENT_ID: " + rs.getString("CLIENT_ID"));
                    System.out.println("AMOUNT_INVEST: " + rs.getDouble("AMOUNT_INVEST"));
                    System.out.println("INTERESTED: " + rs.getDouble("INTERESTED"));
                    System.out.println("INVEST_TYPE: " + rs.getString("INVEST_TYPE"));
                } else {
                    // If no investment account found with the given CLIENT_ID
                    System.out.println("No investment account found for the provided CLIENT_ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving investment account details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loans(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        int loanChoice = -1;

   System.out.println(" ");
    System.out.println("(1) Sign up for a loan?");
    System.out.println("(2) Exit ");
    System.out.print("Enter your choice: ");
        loanChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (loanChoice) {
            case 1:
                signUploan(conn);
                break;
            case 3:
                System.out.println("Exiting menu. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
        }while (loanChoice != 3); 
    }
        public static void signUploan(Connection conn) {
            Scanner scanner = new Scanner(System.in);
        
            try {
                // Generate a unique Loan ID
                String loanId = generateLoanTransactionId();
        
                int loanTypeChoice = 0;
                while (true) {
                    System.out.println(" ");
                    System.out.println("Select Loan Type:");
                    System.out.println("(1) Secured Loan");
                    System.out.println("(2) Unsecured Loan");
                    System.out.print("Enter your choice: ");
                    if (scanner.hasNextInt()) {
                        loanTypeChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        if (loanTypeChoice == 1 || loanTypeChoice == 2) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select 1 or 2.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                        scanner.nextLine(); // Consume invalid input
                    }
                }
        
                // Generate random loan details
                double loanAmount = 5000 + (Math.random() * 75000);
                double interestRate = 5 + (Math.random() * 15);
                double monthlyPayment = 300 + (Math.random() * 4700);
        
                if (loanTypeChoice == 1) { // Secured Loan
                    System.out.print("Enter collateral: ");
                    String collateral = scanner.nextLine().trim();
                    String securedLoanId = generateSecuredLoanId();
                    String insertSecuredSql =  "INSERT INTO SECUREDLOAN (LOAN_ID, LOAN_AMOUNT, MONTHLY_PAY, COLLATERAL, SECUREDLOAN_ID) " +
                      "VALUES (?, ?, ?, ?, ?);";;
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSecuredSql)) {
                        pstmt.setString(1, loanId);
                        pstmt.setDouble(2, loanAmount);
                        pstmt.setDouble(3, monthlyPayment);
                        pstmt.setString(4, collateral);
                        pstmt.setString(5, securedLoanId);
        
                        if (pstmt.executeUpdate() > 0) {
                            System.out.println("Secured loan signed up successfully!");
                            displayLoanDetails(loanId, loanAmount, monthlyPayment, null, securedLoanId, collateral);
                        }
                    }
                } else if (loanTypeChoice == 2) { // Unsecured Loan
                    String unsecuredLoanId = generateUnsecuredLoanId();
                    String insertUnsecuredSql = "INSERT INTO UNSECUREDLOAN (LOAN_ID, UNSECURED_ID, ULOAN_AMOUNT, AMOUNT_OWED, INTEREST_RATE) " +
                            "VALUES (?, ?, ?, ?, ?);";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertUnsecuredSql)) {
                        pstmt.setString(1, loanId);
                        pstmt.setString(2, unsecuredLoanId);
                        pstmt.setDouble(3, loanAmount);
                        pstmt.setDouble(4, loanAmount); // AMOUNT_OWED = LOAN_AMOUNT
                        pstmt.setDouble(5, interestRate);
        
                        if (pstmt.executeUpdate() > 0) {
                            System.out.println("Unsecured loan signed up successfully!");
                            displayLoanDetails(loanId, loanAmount, monthlyPayment, interestRate, unsecuredLoanId, null);
                        }
                    }
                }
        
                System.out.println("Returning to the main menu...");
        
            } catch (SQLException e) {
                System.out.println("Error occurred when signing up for loan: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error occurred. Please try again.");
                e.printStackTrace();
                scanner.nextLine(); // Clear scanner buffer
            }
        }
        
            public static String generateLoanTransactionId() {
                return "L" + (int) (Math.random() * 10);
            }

            public static String generateUnsecuredLoanId() {
                return "S" + (int) (Math.random() * 100);
            }

            public static String generateSecuredLoanId() {
                return "U" + (int) (Math.random() * 100);
            }

            private static boolean doesLoanIdExist(Connection conn, String loanId) throws SQLException {
                String query = "SELECT COUNT(*) FROM LOAN WHERE LOAN_ID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, loanId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        return rs.next() && rs.getInt(1) > 0;
                    }
                }
            }

            public static void displayLoanDetails(String loanId, double loanAmount, double monthlyPayment, 
            Double interestRate, String loanTypeId, String collateral) {
                System.out.println(" ");
                System.out.println("Loan ID: " + loanId);
                System.out.println("Loan Amount: $" + loanAmount);
                System.out.println("Monthly Payment: $" + monthlyPayment);
                System.out.println(" ");
                if (collateral != null) { // Secured loan
                System.out.println("Secured Loan:");
                System.out.println("Collateral: " + collateral);
                System.out.println("Secured Loan ID: " + loanTypeId);
                } else { // Unsecured loan
                    System.out.println(" ");
                System.out.println("Unsecured Loan:");
                System.out.println("Interest Rate: " + interestRate + "%");
                System.out.println("Unsecured Loan ID: " + loanTypeId);
                System.out.println(" ");
                }
                }


          
    
    // Generate a transaction ID (this could be more sophisticated based on your needs)
    public static String generateTransactionId() {
        return String.valueOf((int) (Math.random() * 100000));
    }

    public static void cardTransaction(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        int cardChoice;

        do{
        System.out.println(" ");
        System.out.println("(1) Sign up for a debit card. ");
        System.out.println("(2) Replace a Debit card?");
        System.out.println("(3) Want to sign up for a new LU credit card ???");
        System.out.println("(4) Replace a Credit card?");
        System.out.println("(5) Exit ");
        System.out.print("Enter your choice: ");
        System.out.println(" ");
        
        cardChoice = scanner.nextInt();
        
        switch (cardChoice) {
            case 1:
                newDebitCard(conn);
                break;
            case 2:
                replaceDebitCard(conn);
                break;
            case 3:
              
              newCreditCard(conn);
             break;
            case 4:
              replaceCreditCard(conn);
                break;
        }
      } while (cardChoice != 5); 
       }

       public static void newCreditCard(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("Sign up for LU Credit Card!");
        while (true) {
            System.out.print("Enter your PIN: ");
            String Id = scanner.nextLine();
    
            try {
            
                if (!isIDValid(conn, Id)) {
                    System.out.println("Invalid PIN. Please try again.");
                    continue;
                }
    
            
                String creditCardId = generateCreditCardId();
    
                double interestRate = 10 + (Math.random() * 10);
    
                double creditAmount = 500 + (Math.random() * 19500);
    
     
                double monthlyPayments = 50 + (Math.random() * 950);
    
            
                String insertSql =  "INSERT INTO CREDITCARD (CREDIT_ID, PIN, INTEREST_RATE, CREDIT_AMOUNT, MONTHLY_PAYMENTS) " +
                   "VALUES (?, ?, ?, ?, ?)";
    
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, creditCardId);
                    pstmt.setString(2, Id);
                    pstmt.setDouble(3, interestRate);
                    pstmt.setDouble(4, creditAmount);
                    pstmt.setDouble(5, monthlyPayments);
    
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println(" ");
                        System.out.println("You have been approved for the LU Credit Card!");
                        System.out.println("Credit Card ID: " + creditCardId);
                        System.out.println("Interest Rate: " + interestRate + "%");
                        System.out.println("Credit Amount: $" + creditAmount);
                        System.out.println("IMPORTANT: Monthly Payment: $" + monthlyPayments);
                        System.out.println(" ");
                    } else {
                        System.out.println("Failed to create the new Credit Card.");
                    }
                }
                return;
    
            } catch (SQLException e) {
                System.out.println("Error occurred while creating a new credit card.");
                e.printStackTrace();
            }
        }
    }
    
    
    // Helper method to generate a new Credit Card ID (random 16-digit format)
    private static String generateCreditCardId() {
        return String.format("%04d-%04d-%04d", 
                             (int)(Math.random() * 10000), 
                             (int)(Math.random() * 10000), 
                             (int)(Math.random() * 10000)); 
    }
    
    // Helper method to check if the Checking Account already has a Credit Card
    private static boolean CreditCardExistance(Connection conn, String checkId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CREDITCARD WHERE PIN = ?";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, checkId);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Return true if a credit card exists
                }
            }
        }
        return false;  // Return false if no card exists
    }
    
    private static boolean isIDValid(Connection conn, String Id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM COSTUMER WHERE PIN = ?";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Id);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Return true if valid
                }
            }
        }
        return false;  // Return false if not valid
    }
    
    public static void replaceCreditCard(Connection conn) {
        Scanner scanner = new Scanner(System.in);
    
        try {
            // Prompt for user PIN
            String pinId;
            do {
                System.out.print("Enter your PIN: ");
                pinId = scanner.nextLine();
            } while (!isIDValid(conn, pinId));
    
            // Fetch all credit cards associated with the costumer
            String fetchSql = "SELECT CREDIT_ID, INTEREST_RATE, CREDIT_AMOUNT, MONTHLY_PAYMENTS " +
                              "FROM CREDITCARD WHERE PIN = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(fetchSql)) {
                pstmt.setString(1, pinId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (!rs.isBeforeFirst()) { // Check if no results were found
                        System.out.println("No credit card found. Sign up for a new card instead!");
                        return;
                    }
    
                    // Display available credit cards
                    System.out.println("Your credit cards:");
                    List<String> creditCardIds = new ArrayList<>();
                    while (rs.next()) {
                        String creditId = rs.getString("CREDIT_ID");
                        double interestRate = rs.getDouble("INTEREST_RATE");
                        double creditAmount = rs.getDouble("CREDIT_AMOUNT");
                        double monthlyPayments = rs.getDouble("MONTHLY_PAYMENTS");
    
                        creditCardIds.add(creditId);
                        System.out.printf("Credit Card ID: %s | Interest Rate: %.2f%% | Credit Amount: $%.2f | Monthly Payments: $%.2f%n",
                                          creditId, interestRate, creditAmount, monthlyPayments);
                    }
    
                    // Prompt the user to select the card to replace
                    String selectedCardId;
                    while (true) {
                        System.out.print("Enter the Credit Card ID to replace: ");
                        selectedCardId = scanner.nextLine();
                        if (creditCardIds.contains(selectedCardId)) {
                            break;
                        } else {
                            System.out.println("Invalid Credit Card ID. Please select a valid card from the list.");
                        }
                    }
    
                    // Generate new card details
                    String newCardId = generateCreditCardId();
                    double newInterestRate = 10 + (Math.random() * 10);
                    double newCreditAmount = 500 + (Math.random() * 19500);
                    double newMonthlyPayments = 50 + (Math.random() * 950);
    
                    // Update the selected credit card in the database
                    String updateSql = "UPDATE CREDITCARD " +
                                       "SET CREDIT_ID = ?, INTEREST_RATE = ?, CREDIT_AMOUNT = ?, MONTHLY_PAYMENTS = ? " +
                                       "WHERE CREDIT_ID = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                        updatePstmt.setString(1, newCardId);
                        updatePstmt.setDouble(2, newInterestRate);
                        updatePstmt.setDouble(3, newCreditAmount);
                        updatePstmt.setDouble(4, newMonthlyPayments);
                        updatePstmt.setString(5, selectedCardId);
    
                        int rowsAffected = updatePstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println(" ");
                            System.out.println("Your Credit Card has been successfully replaced!");
                            System.out.println("New Credit Card ID: " + newCardId);
                            System.out.println("New Interest Rate: " + newInterestRate + "%");
                            System.out.println("New Credit Amount: $" + newCreditAmount);
                            System.out.println("New Monthly Payments: $" + newMonthlyPayments);  
                            System.out.println(" ");
                        } else {
                            System.out.println("Failed to replace the credit card.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while replacing the credit card.");
            e.printStackTrace();
        }
    }
    
    
    public static void replaceDebitCard(Connection conn) {
        Scanner scanner = new Scanner(System.in);
    
        try {
            // Prompt user for their Checking Account ID
            System.out.print("Enter your Checking Account ID: ");
            String checkId = scanner.nextLine();
    
            // Check if the Checking Account ID is valid
            if (!isCheckIdValid(conn, checkId)) {
                System.out.println("Invalid Checking Account ID. Please try again.");
                return;
            }
    
            // Fetch all debit cards associated with the given Checking Account ID
            String fetchSql = "SELECT CARD_ID, CVC, WITHDRAWAL_LIMIT FROM DEBITCARD WHERE CHECK_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(fetchSql)) {
                pstmt.setString(1, checkId);
    
                try (ResultSet rs = pstmt.executeQuery()) {
                    // If no debit cards are found, notify the user
                    if (!rs.isBeforeFirst()) {
                        System.out.println("No debit cards found for the given Checking Account ID.");
                        return;
                    }
    
                    // Display a list of debit cards to the user
                    System.out.println("The following debit cards are associated with your account:");
                    while (rs.next()) {
                        System.out.println("Card ID: " + rs.getString("CARD_ID") +
                                           ", CVC: " + rs.getString("CVC") +
                                           ", Withdrawal Limit: $" + rs.getInt("WITHDRAWAL_LIMIT"));
                    }
                }
            }
    
            
            System.out.print("Enter the Card ID of the debit card you want to replace: ");
            String cardIdToReplace = scanner.nextLine();
    
            String newCardId = generateUniqueCardId(conn);
            int newCvc = (int) (Math.random() * 900) + 100;
            int withdrawalLimit = 500;
    
            String updateSql = "UPDATE DEBITCARD SET CARD_ID = ?, CVC = ?, WITHDRAWAL_LIMIT = ? WHERE CARD_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, newCardId);
                pstmt.setInt(2, newCvc);
                pstmt.setInt(3, withdrawalLimit);
                pstmt.setString(4, cardIdToReplace);
    
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("The debit card has been successfully replaced!");
                    System.out.println("New Card ID: " + newCardId);
                    System.out.println("New CVC: " + newCvc);
                    System.out.println("Withdrawal Limit: $" + withdrawalLimit);
                } else {
                    System.out.println("Failed to replace the debit card. Please ensure the Card ID is correct.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while replacing the debit card.");
            e.printStackTrace();
        }
    }
    
    private static boolean DebitCardExistance(Connection conn, String checkId) throws SQLException {
        String query = "SELECT COUNT(*) FROM DEBITCARD WHERE CHECK_ID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, checkId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false; 
    }
    


 public static void newDebitCard(Connection conn) {
    Scanner scanner = new Scanner(System.in);

    try {
        // Prompt user for their checking ID
        System.out.print("Enter your Checking Account ID: ");
        String checkId = scanner.nextLine();

        // Validate the checking account ID
        if (!isCheckIdValid(conn, checkId)) {
            System.out.println("Invalid Checking Account ID. Please try again.");
            return;
        }

        // Generate a new card ID (format xxx-xxx-xxxx)
        String cardId = generateCardId();

        // Generate a random 3-digit CVC
        int cvc = (int) (Math.random() * 900) + 100;

        // Default withdrawal limit
        int withdrawalLimit = 500;

        // Insert the new card into the DEBITCARD table
        String insertSql = "INSERT INTO DEBITCARD (CARD_ID, CVC, WITHDRAWAL_LIMIT, CHECK_ID) " +
                           "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, cardId);
            pstmt.setInt(2, cvc);
            pstmt.setInt(3, withdrawalLimit);
            pstmt.setString(4, checkId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(" ");
                System.out.println("Your Debit Card has been successfully created!");
                System.out.println("Card ID: " + cardId);
                System.out.println("CVC: " + cvc);
                System.out.println("Withdrawal Limit: $" + withdrawalLimit);
                System.out.println(" ");
            } else {
                System.out.println("Failed to create a new debit card.");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error occurred while creating a new debit card.");
        e.printStackTrace();
    }
    
}

        //Generate a card ID in the format xxx-xxx-xxxx
     private static String generateCardId() {
         int part1 = (int) (Math.random() * 900) + 100; // Generate a 3-digit number
         int part2 = (int) (Math.random() * 900) + 100; // Generate another 3-digit number
         int part3 = (int) (Math.random() * 9000) + 1000; // Generate a 4-digit number
         return part1 + "-" + part2 + "-" + part3;
    }

    private static String generateUniqueCardId(Connection conn) throws SQLException {
        String newCardId;
        do {
            newCardId = generateCardId(); // Generate a new ID
        } while (isCardIdDuplicate(conn, newCardId)); // Check if it's unique
        return newCardId;
    }
    
    private static boolean isCardIdDuplicate(Connection conn, String cardId) throws SQLException {
        String query = "SELECT COUNT(*) FROM DEBITCARD WHERE CARD_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    public static void insertCheckTransId(Connection conn, String checkTransId, String transId) throws SQLException {
        String insertTransSql = "INSERT INTO ACCOUNTTRANSACTION (CHECKTRANS_ID, TRANS_ID) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertTransSql)) {
            pstmt.setString(1, checkTransId);
            pstmt.setString(2, transId);    
            pstmt.executeUpdate(); 
        }
    }
    public static void accountDeposit(Connection conn) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("----- Account Deposit -----");
    
        System.out.print("Enter your Checking Account ID: ");
        String checkId = scanner.nextLine();
    
        System.out.print("Enter your Security ID: ");
        String transId = scanner.nextLine();
    
        try {
            // Step 1: Check if the Checking Account ID exists
            if (!isAccountExist(conn, checkId)) {
                System.out.println("The Checking Account ID does not exist.");
                return;
            }
    
            // Step 2: Check if the Transaction ID exists
            if (!isTransactionExist(conn, transId)) {
                System.out.println("The Transaction ID does not exist.");
                return;
            }
    
            // Proceed with the deposit process if both IDs exist
            double currentBalance = getAccountBalance(conn, checkId);
            System.out.println("Current balance: $" + currentBalance);
    
            System.out.print("Enter the amount you want to deposit: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine(); // To consume the newline character left by nextDouble()
    
            System.out.print("Enter a description for the deposit: ");
            String description = scanner.nextLine();
    
            // Use SYSDATE for the transaction date
            Date depositDate = new Date(System.currentTimeMillis()); // Get current date/time
    
            // Calculate new balance after deposit
            double newBalance = currentBalance + depositAmount;
    
            // Generate a new unique CHECKTRANS_ID with three random numbers
            String newCheckTransId = generateUniqueCheckTransId(conn);
    
            // Now insert the deposit transaction into ACCOUNTTRANSACTION
           String insertSql = "INSERT INTO ACCOUNTTRANSACTION (TRANS_ID, CHECKTRANS_ID, TRANS_DATE, DESCRIPTION, AMOUNT) " +
                   "VALUES (?, ?, SYSDATE, ?, ?)";
    
            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                insertPstmt.setString(1, transId);         // Set the TRANS_ID provided by the user
                insertPstmt.setString(2, newCheckTransId); // Set the new unique CHECKTRANS_ID
                insertPstmt.setString(3, description);     // Set description for the deposit
                insertPstmt.setDouble(4, depositAmount);   // Set the deposit amount
    
                int rowsAffected = insertPstmt.executeUpdate(); // Execute the query to insert the transaction
                if (rowsAffected > 0) {
                    // Step 5: Update the balance in CHECKACCOUNT
                    String updateBalanceSql = "UPDATE CHECKACCOUNT SET BALANCE = ? WHERE CHECK_ID = ?";
    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateBalanceSql)) {
                        updateStmt.setDouble(1, newBalance); // Set the new balance
                        updateStmt.setString(2, checkId);    // Set the Checking Account ID
    
                        int balanceUpdate = updateStmt.executeUpdate(); // Execute the update query
                        if (balanceUpdate > 0) {
                            System.out.println("Account balance updated successfully!");
                        } else {
                            System.out.println("Failed to update the account balance.");
                        }
                    }
                } else {
                    System.out.println("Failed to record the deposit.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while processing the deposit.");
            e.printStackTrace();
        }
    }
    
    // Method to generate a unique CHECKTRANS_ID with three random numbers
    private static String generateUniqueCheckTransId(Connection conn) throws SQLException {
        String newCheckTransId = null;
        boolean isUnique = false;
    
        while (!isUnique) {
            newCheckTransId = String.format("%03d", (int) (Math.random() * 1000)); // Generate a random number between 000 and 999
    
            String checkSql = "SELECT COUNT(*) FROM ACCOUNTTRANSACTION WHERE CHECKTRANS_ID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, newCheckTransId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    isUnique = true; // The generated ID is unique
                }
            }
        }
    
        return newCheckTransId;
    }
    
    // Method to check if the Transaction ID exists in the TRANSACTION table
    private static boolean isTransactionExist(Connection conn, String transId) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM TRANSACTION WHERE TRANS_ID = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, transId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // The Transaction ID exists
            }
        }
        return false; // The Transaction ID does not exist
    }
    
    // Helper method to check if the CHECKTRANS_ID exists in the parent table
    public static boolean doesCheckTransIdExist(Connection conn, String checkTransId) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM ACCOUNTTRANSACTION WHERE CHECKTRANS_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkExistSql)) {
            pstmt.setString(1, checkTransId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;  // Return true if the CHECKTRANS_ID exists
        }
    }
    

    public static void insertCheckTransId(Connection conn, String checkTransId) throws SQLException {
        String insertTransSql = "INSERT INTO ACCOUNTTRANSACTION (CHECKTRANS_ID) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertTransSql)) {
            pstmt.setString(1, checkTransId);
            pstmt.executeUpdate(); // Insert the new CHECKTRANS_ID into the parent table
        }
    }
    
    
    // Helper method to check if the account exists
    public static boolean isAccountExist(Connection conn, String checkId) throws SQLException {
        String checkExistSql = "SELECT COUNT(*) FROM CHECKACCOUNT WHERE CHECK_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkExistSql)) {
            pstmt.setString(1, checkId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;  // Return true if the account exists
        }
    }
    

    public static String generateValidCheckTransId(Connection conn, String checkId) throws SQLException {
        String checkTransId = null;
        String seqSql = "SELECT checktrans_seq.NEXTVAL FROM DUAL";
        try (PreparedStatement pstmt = conn.prepareStatement(seqSql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                checkTransId = checkId + String.format("%04d", rs.getInt(1)); // Append the sequence number to checkId
            }
        }
        return checkTransId;
    }
    

    // Helper method to generate a random ID with specified length
    private static String generateRandomId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));  // Append a random digit between 0 and 9
        }
        return sb.toString();
    }
    

    public static void checkingAccounts(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        String checkId = null;
        String transId = null;
        boolean isValidCheckId = false;
        
        while (!isValidCheckId) {
            System.out.print("Enter your Check ID: ");
            checkId = scanner.nextLine(); 

            System.out.print("Enter your Trans ID: ");
            transId = scanner.nextLine(); 
            
            try {
                isValidCheckId = isCheckIdValid(conn, checkId);
                
                if (isValidCheckId) {
                    System.out.println("Valid Checking Account ID.");
                    System.out.println(" ");
                    // Get the balance for the valid account (you can write this query or logic as needed)
                    double balance = getAccountBalance(conn, checkId);  // Implement this method to retrieve the balance
                    
                    // Print table headers
                    printCheckTableHeader();
                    
                    // Print the account details (checkId and balance)
                    printAccountDetails(checkId, balance);
                    printTransactionHeader();
                    // Call displayTransactions with the checkId provided by the user
                    displayTransactions(conn, transId);
                } else {
                    System.out.println("Invalid Checking Account ID. Please try again.");
                }
            } catch (SQLException e) {
                // Handle any SQL exceptions during the validation
                System.out.println("An error occurred while validating the Checking Account ID.");
                e.printStackTrace();
            }
        }
    }

       
    public static void printSavingTableHeader() {
        System.out.println("------------Hello, Welcome to your LU Checking Account-----------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("| Saving    |                       | Available Balance       |");
        System.out.println("-----------------------------------------------------------------");
    
    
    }
    
    public static void printCheckTableHeader() {
        System.out.println("------------Hello, Welcome to your LU Checking Account-----------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("| Checking    |                       | Available Balance       |");
        System.out.println("-----------------------------------------------------------------");
    
    
    }

    public static void printAccountDetails(String checkId, double balance) {

        System.out.printf("| %-12s | %-22s | $%-23.2f|\n", checkId, "       ", balance);
        System.out.println("----------------------------------------------------------------------");
    }


    public static void printTransactionHeader() {
        System.out.println(" ");
        System.out.println("-----------------------------Tranactions ------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("|  Date      | Description                        | History log  |");
        System.out.println("-----------------------------------------------------------------");
    
    
    }

    public static void savingAccounts(Connection conn) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your ACCOUNT_ID: ");
            String accountId = scanner.nextLine(); 
    
            
            String sql = "SELECT * FROM SAVINGACCOUNT WHERE ACCOUNT_ID = ?";
    
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
                pstmt.setString(1, accountId);
    
                
                try (ResultSet rs = pstmt.executeQuery()) {
                   
                    if (rs.next()) {
                    
                        System.out.println("Saving Account Details:");
                        System.out.println("SAVING_ID: " + rs.getString("SAVING_ID"));
                        System.out.println("BALANCE: " + rs.getDouble("BALANCE"));
                        System.out.println("INTEREST_RATE: " + rs.getDouble("INTEREST_RATE"));
                        System.out.println("ACCOUNT_ID: " + rs.getString("ACCOUNT_ID"));
                    } else {
                        // If no account found with the given ACCOUNT_ID
                        System.out.println("No saving account found for the provided ACCOUNT_ID.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while retrieving saving account details: " + e.getMessage());
                e.printStackTrace();
            }
        }
        

    public static void payMents(){

    }

    private static boolean isLoginValid(Connection conn, String pin) throws SQLException {
        String sql = "SELECT COUNT(*) FROM COSTUMER WHERE PIN = ?";
        try (PreparedStatement preState = conn.prepareStatement(sql)) {
            preState.setString(1, pin);
            try (ResultSet rs = preState.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Return true if count > 0 (user exists)
                }
            }
        }
        return false;  // Return false if no matching user found
    }
    
    public static void displayTransactions(Connection conn, String transId) {
            String sql = "SELECT TRANS_DATE, DESCRIPTION, AMOUNT " +
                 "FROM ACCOUNTTRANSACTION " +
                 "WHERE TRANS_ID = ? " +
                 "ORDER BY TRANS_DATE DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if there are no transactions
                if (!rs.next()) {
                    System.out.println("No transactions found for this account.");
                    return;
                }
    
                do {
                    Date transDate = rs.getDate("TRANS_DATE");
                    String description = rs.getString("DESCRIPTION");
                    double amount = rs.getDouble("AMOUNT");
                       
                

                    System.out.printf("| %-8s | %-35s | $%.2f |\n", transDate.toString(), description,  amount);
                } while (rs.next());  
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
            e.printStackTrace();  
        }
    }
    
   
    private static boolean isCheckIdValid(Connection conn, String checkId) throws SQLException {
        String query = "SELECT COUNT(*) FROM DEBITCARD WHERE CHECK_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, checkId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private static double getAccountBalance(Connection conn, String checkId) throws SQLException {
        String sql = "SELECT BALANCE FROM CHECKACCOUNT WHERE CHECK_ID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, checkId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("BALANCE");
                }
            }
        }
        
        return 0.0; 
    }

}

