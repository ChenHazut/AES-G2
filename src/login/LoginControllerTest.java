package login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import logic.User;

public class LoginControllerTest {
	LoginController lc;

	@Before
	public void setUp() throws Exception {
		lc = new LoginController();
	}

	@Test
	public void testCheckUserDetailsNoPassword() {

		Boolean expected = false;
		User IDNoPassword = new User("11111", null);
		Boolean result = lc.checkUserDetails(IDNoPassword);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsNoID() {

		Boolean expected = false;
		User passwordNoID = new User(null, "qqqq");
		Boolean result = lc.checkUserDetails(passwordNoID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsNoIDNoPassword() {

		Boolean expected = false;
		User noPasswordNoID = new User(null, null);
		Boolean result = lc.checkUserDetails(noPasswordNoID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsIDAndPassword() {
		Boolean expected = true;
		User passwordAndID = new User("11111", "aaaa");
		Boolean result = lc.checkUserDetails(passwordAndID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckIfUserIDExistWithCorrectID() {
		Boolean expected = true;
		User userEntered = new User("11111", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered);
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckIfUserIDExistWithWrongID() {
		Boolean expected = false;
		User userEntered = new User("11133", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered);
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckPasswordCaseUsersIDsDiffrentCorrectException() {

		User passwordCorrect = new User("12121", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			fail("This should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("ID of userToCompare is diffrent from ID of user"));
		}
	}

	@Test
	public void testCheckPasswordCaseUsersIDsDiffrentWrongException() {

		User passwordCorrect = new User("12121", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			fail("This should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("wrong message"));
		}
	}

	@Test
	public void testCheckPasswordCaseUsersIDsSameWrongPassword() {
		Boolean expected = false;
		User passwordCorrect = new User("11111", "3333");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			assertTrue(result.equals(expected));
		} catch (IllegalArgumentException e) {
			fail("This should not have thrown an exception");
		}
	}

	@Test
	public void testCheckPasswordCaseUsersIDsSameCorrectPassword() {
		Boolean expected = true;
		User passwordCorrect = new User("11111", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			assertTrue(result.equals(expected));
		} catch (IllegalArgumentException e) {
			fail("This should not have thrown an exception");
		}
	}

	@Test
	public void testLoginUser() {
		fail("Not yet implemented");
	}

	// @Test
	// public void testGetUser() {
	// fail("Not yet implemented");
	// }

}
