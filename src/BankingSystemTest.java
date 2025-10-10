public class BankingSystemTest {
    public static void main(String[] args) {
        // Create an individual customer
        Individual cust1 = new Individual("C001", "Bianca", "Kedikaetswe", "71234567", "bianca@example.com");
        cust1.getCustomerDetails();

        // Create accounts for the customer
        Savings savings = new Savings("A001", "Gaborone", "C001", 1000);
        Investment invest = new Investment("A002", "Gaborone", "C001", 600);
        Cheque cheque = new Cheque("A003", "Gaborone", "C001", "ABC Ltd", "Gaborone", 1500);

        // Display account details
        System.out.println("\n--- Account Details ---");
        savings.getAccountDetails();
        invest.getAccountDetails();
        cheque.getAccountDetails();

        // Perform transactions
        System.out.println("\n--- Performing Transactions ---");
        invest.deposit(200);
        invest.withdraw(300);
        invest.applyMonthlyInterest();
        invest.getAccountDetails();

        savings.applyMonthlyInterest();
        savings.getAccountDetails();

        cheque.withdraw(500);
        cheque.getAccountDetails();

        // Demonstrate a transaction object
        System.out.println("\n--- Executing a Transaction Object ---");
        Transaction t1 = new Transaction("T001", "deposit", 250);
        t1.execute(cheque);
        cheque.getAccountDetails();
    }
}
