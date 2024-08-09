package sia.demo;


import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull; // libreria di supporto per validazione dei dati
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {

    private Long id;

    private Date date;

    @NotNull
    @Size(min=5,message = "Almeno 5 caratteri") // condizione + avvisso
    private String name;
    @Size(min=1,message = "Almeno un ingrediente") // condizione + avvisso
    private List<Ingredient> ingredients;

}
