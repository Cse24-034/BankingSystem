public class Company extends Customer {
    private String registrationNumber;
    private String companyAddress;
    private String companyType;

    public Company(String id, String name, String regNo, String address, String phone, String email) {
        this.customerId = id;
        this.name = name;
        this.registrationNumber = regNo;
        this.companyAddress = address;
        this.companyType = "Registered Business";
        this.phoneNumber = phone;
        this.email = email;
    }

    @Override
    public void getCustomerDetails() {
        System.out.println("Company: " + name + " | RegNo: " + registrationNumber);
    }
}
