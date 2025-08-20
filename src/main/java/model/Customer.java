package model;

public class Customer {
    private String accountNumber;
    private String customerName;
    private String address;
    private String telephone;
    private String email;

    public Customer() {}

    public Customer(String accountNumber, String customerName, String address, String telephone, String email) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
}