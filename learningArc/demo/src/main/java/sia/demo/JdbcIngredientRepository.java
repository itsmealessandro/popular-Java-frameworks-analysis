package sia.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // for JDBC template
public class JdbcIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Ingredient findOne(String id) {
        Ingredient ing = jdbc.queryForObject("SELECT id,name,type, FROM Ingredient where id=?",
                this::mapRowToIngredient, id);
        return ing;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        Iterable<Ingredient> it;
        it = jdbc.query("SELECT id,name,type FROM Ingredient", this::mapRowToIngredient);

        return it;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("INSERT into Ingredient (id,name,type) values (?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int numRows) throws SQLException {
        Ingredient ing = new Ingredient(rs.getString("id"), rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
        return ing;

    }

}