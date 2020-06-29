package min.onlineshop.domains;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class User extends Auditable<String> {

    @Email( message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @NotEmpty(message = "*Please provide your password")
    @org.springframework.data.annotation.Transient
    private String password;

    @NotEmpty(message = "*Please provide your name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<BillingAddress> addresses = new HashSet<>();

    public User addAddress(BillingAddress address){
        address.setUser(this);
        this.addresses.add(address);
        return this;
    }

    public User addPayment(Payment payment){
        payment.setUser(this);
        this.payments.add(payment);
        return this;
    }

    public User addOrder(Order order){
        order.setUser(this);
        this.orders.add(order);
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

