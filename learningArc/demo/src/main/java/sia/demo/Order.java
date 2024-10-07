package sia.demo;

import java.util.Date;

import org.hibernate.validator.constraints.CreditCardNumber;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Order {

    private Long id;
    private Date placedAt;

    @NotEmpty(message = "Name the Taco")
    private String name;
    @NotEmpty(message = "Street is required")
    private String street;
    @NotEmpty(message = "City is required")
    private String city;
    @NotEmpty(message = "State is required")
    private String state;
    @NotEmpty(message = "Zip is required")
    private String zip;
    @CreditCardNumber(message = "Credit card number non valid")
    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0,message = "invalid CVV")
    private String ccCVV;


    private Taco taco;
}
