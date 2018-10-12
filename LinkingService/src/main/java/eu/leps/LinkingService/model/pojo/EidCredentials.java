/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.leps.LinkingService.model.pojo;

/**
 *
 * @author nikos
 */
public class EidCredentials {
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJlaWRcIjpcIkVTL0dSLzk5OTk5MTQySFwiLFwicGVyc29uSWRlbnRpZmllclwiOlwiRVMvR1IvOTk5OTkxNDJIXCIsXCJkYXRlT2ZCaXJ0aFwiOlwiMjAwMC0wMS0wMVwiLFwiY3VycmVudEZhbWlseU5hbWVcIjpcIlBBUEVMTElETzE0MiBTQVBFTExJRE8xNDIgLSBETkkgOTk5OTkxNDJIXCIsXCJjdXJyZW50R2l2ZW5OYW1lXCI6XCJOT01CUkUxNDJcIn0iLCJvcmlnaW4iOiJlSURBUyJ9.NHG4dCunZ6HUvlomQD48Df0vNwjjYrysKbn4jnb6BEk

    //eidas and UAegean
    private String eid;
    private String personIdentifier;
    private String dateOfBirth;
    private String currentFamilyName;
    private String currentGivenName;
    private String origin;

    //linkedin
    private String firstName;
    private String lastName;
    private String email;

    public EidCredentials(String eid, String personIdentifier, String dateOfBirth, String currentFamilyName, String currentGivenName, String origin, String firstName, String lastName, String email) {
        this.eid = eid;
        this.personIdentifier = personIdentifier;
        this.dateOfBirth = dateOfBirth;
        this.currentFamilyName = currentFamilyName;
        this.currentGivenName = currentGivenName;
        this.origin = origin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public EidCredentials() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getPersonIdentifier() {
        return personIdentifier;
    }

    public void setPersonIdentifier(String personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCurrentFamilyName() {
        return currentFamilyName;
    }

    public void setCurrentFamilyName(String currentFamilyName) {
        this.currentFamilyName = currentFamilyName;
    }

    public String getCurrentGivenName() {
        return currentGivenName;
    }

    public void setCurrentGivenName(String currentGivenName) {
        this.currentGivenName = currentGivenName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

}
