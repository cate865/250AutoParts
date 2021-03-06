// Authors: Liplan Lekipising and catherine Muthoni
package com.autoparts.autoparts.classes;
import java.time.LocalDate;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
@Entity
@Table(name = "users")
public class Account {

    @Id
    @Column(unique = true)
    private String username;
 
    @Column(name = "firstName", nullable = false)
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "secondName", nullable = false)
    @NotEmpty(message = "Please provide your second name")
    private String secondName;

    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "createdDate", nullable = false)
    private LocalDate createdDate;

    @Column(name = "confirmation_token")
	private String confirmationToken;

    @Builder.Default
	private Boolean locked = false;

	@Builder.Default
	private Boolean enabled = false;

    @Column(name = "role")
    private String role = "USER";
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
    
    public void setSecondName(String secondName) {
        this.secondName = secondName;
        
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        
    }



    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }



    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public String generateId(){
        Random random = new Random();
        int val = random.nextInt(999999);
        return String.format("%06d", val);
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Boolean getLocked() {
        return locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Account(){
        createdDate = LocalDate.now();
    }


}


