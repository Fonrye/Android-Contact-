package com.example.addressbook;

/**
 * @ProjectName: AddressBook
 * @Package: com.example.addressbook
 * @QQ: 1025377230
 * @Author: Fonrye
 * @CreateDate: 2021/4/8 23:24
 * @Email: fonrye@163.com
 * @Version: 1.0
 */
public class Contact {
    private String no;
    private String name;
    private String phoneNumber;

    public Contact() {
    }

    public Contact(String _name, String _phoneNumber) {
        this.name = _name;
        this.phoneNumber = _phoneNumber;
    }

    public Contact(String _no, String _name, String _phoneNumber) {
        this.no = _no;
        this.name = _name;
        this.phoneNumber = _phoneNumber;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String _no) {
        this.no = _no;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String _phoneNumber) {
        this.phoneNumber = _phoneNumber;
    }

}
