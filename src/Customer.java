import java.util.List;
import java.util.ArrayList;

public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String customerType;
    private List<Account> accounts;
    
    public Customer(String customerId, String firstName, String lastName, String username, 
                   String password, String phone, String email, String customerType) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.customerType = customerType;
        this.accounts = new ArrayList<>();
    }
    
    // Getters and setters
    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCustomerType() { return customerType; }
    public List<Account> getAccounts() { return accounts; }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public boolean validateCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}