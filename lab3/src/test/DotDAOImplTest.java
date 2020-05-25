package test;

import main.Dot;
import main.DotDAOImpl;
import main.PropertiesLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static main.DotDAOImpl.isHit;

public class DotDAOImplTest {
    private static Connection connection;
    private static Properties properties = PropertiesLoader.getProperties();
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";

    private static final String USER = getProperty("USER");
    private static final String PASS = getProperty("PASS");

    private static DotDAOImpl dao;

    @BeforeClass
    public static void setup() {
        dao = new DotDAOImpl();
        dao.activateComponent();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ignored) {
        }
        connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("База данных не существует.");
            System.exit(0);
        }
    }

    @Before
    public void cleanTable() throws SQLException {
        connection.createStatement().executeUpdate("delete from public.dots");
    }

    @Test
    public void testGetDots() throws SQLException {
        Dot toCreate = new Dot(4, 2, 3, true);
        connection.createStatement().executeUpdate(
                "insert into public.dots values (" + toCreate.getId() + ", " + toCreate.ishit() + ", " + toCreate.getR() + ", " + toCreate.getX() + "," + toCreate.getY() + ")");
        assert dao.getDots().size() == 1;
        Dot found = dao.getDots().get(0);
        assertDots(found, toCreate);
    }

    @Test
    public void testAddDot() {
        Dot toCreate = new Dot(1, 1, 2, 3, false);
        dao.addDot(toCreate);
        assert dao.getDots().size() == 1;
        Dot created = dao.getDots().get(0);
        assertDots(created, toCreate);
    }

    @Test
    public void testAddDots() {
        Dot first = new Dot(58, 1, 2, 3, true);
        Dot second = new Dot(85, 2, 3, 4, false);
        dao.addDots(Arrays.asList(first, second));
        List<Dot> dots = dao.getDots();
        assert dots.size() == 2;
        assertDots(first, dots.stream().filter(dot -> dot.getId() == first.getId()).findFirst().get());
        assertDots(second, dots.stream().filter(dot -> dot.getId() == second.getId()).findFirst().get());
    }

    @Test
    public void testGetDotById() {
        Dot dot = new Dot(48, 1, 2, 3, true);
        dao.addDot(dot);
        Dot found = dao.getDotById(dot.getId());
        assertDots(dot, found);
    }

    @Test
    public void testDeleteDotById() {
        Dot dot = new Dot(95, 1, 2, 3, true);
        Dot dot1 = new Dot(96, 1, 2, 3, true);
        dao.addDot(dot);
        dao.addDot(dot1);
        assert dao.getDots().size() == 2;
        dao.deleteDotById(dot.getId());
        assert dao.getDots().size() == 1;
    }

    private void assertDots(Dot created, Dot toCreate) {
        assert created.getR() == toCreate.getR();
        assert created.getX() == toCreate.getX();
        assert created.getY() == toCreate.getY();
    }

    private static String getProperty(String name) {
        return properties.get(name).toString();
    }
}
