package min.onlineshop.domains;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@EntityListeners(CommonEntityListener.class)
@EqualsAndHashCode(exclude = {"user"}, callSuper= false)
@Entity
public class BillingAddress extends Auditable<String> {

    private String phone;
    private String fullName;
    private String city;
    private String postal;
    private String addressLine;
    private String state;
    private String country;
    private Boolean active = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public String toString() {
        return  "Full Name : " + fullName +
                ", Phone Number : " + phone  +
                ", City : " + city +
                ", Postal Code : " + postal +
                ", Address Line : " + addressLine +
                ", State : " + state +
                ", Country : " + country;
    }
}

