package Dao;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    public void setup() {
        userDAO = new UserDAO();
    }

    @Test
    public void testValidLogin() {
        User user = userDAO.authenticate("admin", "admin123");
        assertNotNull(user, "User should be returned for valid credentials");
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void testInvalidLogin() {
        User user = userDAO.authenticate("wronguser", "wrongpass");
        assertNull(user, "No user should be returned for invalid credentials");
    }


    @Test
    public void testEmptyCredentials() {
        User user = userDAO.authenticate("", "");
        assertNull(user, "No user should be returned for empty credentials");
    }


@Test
public void testCashierLogin() {
    User user = userDAO.authenticate("cashier1", "cashier123");
    assertNotNull(user, "User should be returned for valid cashier credentials");
    assertEquals("cashier", user.getRole(), "Role should be cashier");
}
}