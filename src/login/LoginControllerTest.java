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

	// ****************************************************
	// checkUserDetails
	// ****************************************************
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

	// ****************************************************
	// checkIfUserIDExist
	// ****************************************************
	@Test
	public void testCheckIfUserIDExistWithCorrectID() {
		Boolean expected = true;
		User userEntered = new User("11111", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered, "test");
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckIfUserIDExistWithWrongID() {
		Boolean expected = false;
		User userEntered = new User("11133", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered, "test");
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	// ****************************************************
	// CheckPassword
	// ****************************************************
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

	// ****************************************************
	// loginUser
	// ****************************************************
	@Test
	public void testLoginUserCaseUserDoesNotExist() {
		User expected = null;
		User userToLog = new User();
		userToLog.setuID("33243");
		User result = lc.loginUser(userToLog, "test");
		assertTrue(result == expected);
	}

	@Test
	public void testLoginUserCaseUserExistAndNotConnected() {
		User expected = new User("11111", "Donald Duck", "0000", "Teacher", "YES");
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = lc.loginUser(userToLog, "test");
		assertTrue(result.getuID().equals(expected.getuID()));
		assertTrue(result.getuName().equals(expected.getuName()));
		assertTrue(result.getTitle().equals(expected.getTitle()));
		assertTrue(result.getIsLoggedIn().equals(expected.getIsLoggedIn()));
		assertTrue(result.getPassword().equals(expected.getPassword()));
	}

	@Test
	public void testLoginUserCaseUserExistAndIsConnected() {
		User expected = null;
		User userToLog = new User();
		userToLog.setuID("33333");
		User result = lc.loginUser(userToLog, "test");
		assertTrue(result == expected);
	}

}
