package sars.vetting.system.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import sars.vetting.system.common.Gender;
import sars.vetting.system.common.MaritalStatus;
import sars.vetting.system.common.PersonType;
import sars.vetting.system.common.Province;

/**
 *
 * @author S2028398
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class Person extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "title")
    private String title;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_names")
    private String fullNames;

    @Column(name = "identity_num")
    private String identityNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "date_of_separion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfSeparation;

    @OneToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "postal_addr_id")
    private Address postalAddress;
    
    @Column(name = "previous_lastname")
    private String previousLastName;

    @Column(name = "previous_fullname")
    private String previousFullName;
    
     @Column(name = "maiden_name")
    private String maidenName;
     
      @Column(name = "name_called_by")
    private String nameCalledBy;
    
    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @OneToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "res_addr_id")
    private Address residentialAddress;

    @OneToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "business_addr_id")
    private Address businessAddress;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id")
    private List<Address> addresses = new ArrayList<>();
    
     @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "person_id")
    private List<PreviousName> previousNames = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "refernce_id")
    private List<Address> addresse = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "relative_id")
    private List<Address> addresses1 = new ArrayList();
    

    @Column(name = "province")
    @Enumerated(EnumType.STRING)
    private Province province;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "person_type")
    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @Column(name = "gender_type")
    @Enumerated(EnumType.STRING)
    private Gender genderType;

    @Column(name = "birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @OneToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "contact_detail_id")
    private ContactDetail contactDetail;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<Citizenship> citizenships = new ArrayList();

    public void addCitizenship(Citizenship citizenship) {
        this.citizenships.add(citizenship);
    }

    public void removeCitizenship(Citizenship citizenship) {
        this.citizenships.remove(citizenship);
    }
    
     public void addPreviousName(PreviousName prevoiusName) {
        this.previousNames.add(prevoiusName);
    }

    public void removePreviousName(PreviousName prevoiusName) {
        this.previousNames.remove(prevoiusName);
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }
}
