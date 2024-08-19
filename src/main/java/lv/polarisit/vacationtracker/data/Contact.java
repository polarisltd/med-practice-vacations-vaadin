package lv.polarisit.vacationtracker.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.jna.platform.win32.Winspool;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "vacations_contact")
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @Email
    @NotEmpty
    private String email = "";

    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
