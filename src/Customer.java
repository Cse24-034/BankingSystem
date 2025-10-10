public abstract class Customer {
    protected String customerId;
    protected String name;
    protected String phoneNumber;
    protected String email;

    public abstract void getCustomerDetails();

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
}
