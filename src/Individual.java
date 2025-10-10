public class Individual extends Customer {
    private String firstName;
    private String lastName;
    private String nationality;
    private String gender;

    public Individual(String id, String firstName, String lastName, String phone, String email) {
        this.customerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.phoneNumber = phone;
        this.email = email;
    }

    @Override
    public void getCustomerDetails() {
        System.out.println("Individual Customer: " + name + " | Phone: " + phoneNumber);
    }
}
