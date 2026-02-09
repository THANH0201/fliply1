package model.dao;

import model.datasource.MariaDbJPAConnection;
import model.entity.User;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
    }

    private User newUser() {
        String uid = UUID.randomUUID().toString().substring(0, 8);

        User u = new User();
        u.setFirstName("Nhut");
        u.setLastName("Vo");
        u.setEmail("nhut+" + uid + "@test.com");
        u.setGoogleId("google-" + uid);
        u.setRole(1);
        return u;
    }

    @Test
    void crudUser() {
        // CREATE
        User u = newUser();
        userDao.persist(u);
        assertNotNull(u.getUserId());

        Integer id = u.getUserId();

        // READ
        User found = userDao.find(id);
        assertNotNull(found);
        assertEquals(u.getEmail(), found.getEmail());
        assertEquals(u.getGoogleId(), found.getGoogleId());

        // UPDATE
        found.setLastName("VoUpdated");
        found.setRole(2);
        userDao.update(found);

        User updated = userDao.find(id);
        assertNotNull(updated);
        assertEquals("VoUpdated", updated.getLastName());
        assertEquals(2, updated.getRole());

        // DELETE
        userDao.delete(updated);
        assertNull(userDao.find(id));
    }

    @Test
    void queryUser() {
        // setup
        User u = newUser();
        userDao.persist(u);
        Integer id = u.getUserId();
        assertNotNull(id);

        // findByEmail
        User byEmail = userDao.findByEmail(u.getEmail());
        assertNotNull(byEmail);
        assertEquals(id, byEmail.getUserId());

        // existsByGoogleId
        assertTrue(userDao.existsByGoogleId(u.getGoogleId()));

        // findByGoogleId
        User byGid = userDao.findByGoogleId(u.getGoogleId());
        assertNotNull(byGid);
        assertEquals(id, byGid.getUserId());

        // cleanup
        userDao.delete(byGid);
        assertFalse(userDao.existsByGoogleId(u.getGoogleId()));
    }
}
