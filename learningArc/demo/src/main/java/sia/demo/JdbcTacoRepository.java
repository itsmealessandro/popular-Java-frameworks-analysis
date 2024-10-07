package sia.demo;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    JdbcTemplate jdbcTemplate;

    public JdbcTacoRepository(JdbcTemplate jTemplate) {
        this.jdbcTemplate = jTemplate;
    }

    /*
     * 1- Salvo le info fondamentali del Taco
     * Necessito quindi di associare a questo taco una lista di ingredienti
     * Mi serve dunque una tabella che associa ad ogni taco gli ingredienti
     * associati Taco_ingredients
     * 2- Inserisco gli ingredienti e li associo al taco nella tabella
     */
    @Override
    public Taco save(Taco taco) {
        long idTaco = saveTacoInfo(taco);

        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, idTaco);

        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setDate(new Date());
        Timestamp dateToTimestamp = new Timestamp(taco.getDate().getTime());

        PreparedStatementCreatorFactory pCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco (name,date)values (?,?)",Types.VARCHAR, Types.TIMESTAMP);
            
        pCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscf = pCreatorFactory
                .newPreparedStatementCreator(Arrays.asList(taco.getName(), dateToTimestamp));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rows = jdbcTemplate.update(pscf, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            return key.longValue();
        } else {
            throw new RuntimeException("Failed to retrieve generated key after inserting Taco\n" + "RA: " + rows
                    + "\nKH:" + keyHolder.getKeyList());
        }

    }

    private void saveIngredientToTaco(Ingredient ingredient, long idTaco) {
        jdbcTemplate.update("insert into Taco_Ingredients values (?,?)", idTaco, ingredient.getId());

    }

}
