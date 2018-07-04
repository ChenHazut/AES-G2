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
	public void testCheckUserDetailsPasswordNull() {

		Boolean expected = false;
		User IDNoPassword = new User("11111", null);
		Boolean result = lc.checkUserDetails(IDNoPassword);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsPasswordEmpty() {

		Boolean expected = false;
		User IDNoPassword = new User("11111", "");
		Boolean result = lc.checkUserDetails(IDNoPassword);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsIDNull() {

		Boolean expected = false;
		User passwordNoID = new User(null, "qqqq");
		Boolean result = lc.checkUserDetails(passwordNoID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsIDEmpty() {

		Boolean expected = false;
		User passwordNoID = new User("", "qqqq");
		Boolean result = lc.checkUserDetails(passwordNoID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsIDNullPasswordNull() {

		Boolean expected = false;
		User noPasswordNoID = new User(null, null);
		Boolean result = lc.checkUserDetails(noPasswordNoID);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckUserDetailsIDAndPasswordCorrect() {
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
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			assertTrue(expected == result);
		} catch (IllegalArgumentException e) {
			fail("this should have not thrown an exception");
		}
	}

	@Test
	public void testLoginUserCaseUserIsNullExceptionCorrect() {
		User userToLog = null;
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("entered user is null"));
		}
	}

	@Test
	public void testLoginUserCaseUserIsNullExceptionWrong() {
		User userToLog = null;
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("user is null"));
		}
	}

	@Test
	public void testLoginUserCaseHandlerWrongExceptionCorrect() {
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = null;
		try {
			result = lc.loginUser(userToLog, "handler");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("handler is wrong"));
		}
	}

	@Test
	public void testLoginUserCaseHandlerWrongExceptionWrong() {
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = null;
		try {
			result = lc.loginUser(userToLog, "handler");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("exception here"));
		}
	}

	// ****************************************************
	// checkPort
	// ****************************************************
	@Test
	public void testCheckPortCasePortIsNull() {
		int expected = 5555;
		User userToLog = new User();
		userToLog.setuID("11111");
		int result = 0;
		try {
			result = lc.checkPort(null);
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	@Test
	public void testCheckPortCasePortIsEmptyString() {
		int expected = 5555;
		User userToLog = new User();
		userToLog.setuID("11111");
		int result = 0;
		try {
			result = lc.checkPort("");
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	@Test
	public void testCheckPortCasePortIsnotNumber() {
		int expected = 5555;
		int result = 0;
		try {
			result = lc.checkPort("aa");
			fail("this should have thrown an exception");

		} catch (NumberFormatException e) {
			assertTrue(e.getMessage().equals("For input string: \"aa\""));
		}
	}

	@Test
	public void testCheckPortCasePortIsCorrect() {
		int expected = 1234;
		int result = 0;
		try {
			result = lc.checkPort("1234");
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	// ****************************************************
	// checkIP
	// ****************************************************
	@Test
	public void testCheckIPCaseIPIsNull() {
		String expected = "localhost";
		String result;
		result = lc.checkIP(null);
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckIPCaseIPIsEmptyString() {
		String expected = "localhost";
		String result;
		result = lc.checkIP("");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckIPCaseIPIsCorrect() {
		String expected = "192.168.171.47";
		String result;
		result = lc.checkIP("192.168.171.47");
		assertTrue(expected.equals(result));
	}

	// ****************************************************
	// checkAllLoginDetails
	// ****************************************************
	@Test
	public void testCheckAllLoginDetailsCaseIDNull() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User(null, "1212"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCasePasswordNull() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", null), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseIDEmpty() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("", "1121"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCasePasswordEmpty() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", ""), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserIDNotExist() {
		String expected = "User ID doesn't exist";
		String result;
		result = lc.checkAllLoginDetails(new User("55555", "1212"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserIDExistPasswordWrong() {
		String expected = "password is wrong";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", "3434"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserAlreadyConnected() {
		String expected = "user already logged in";
		String result;
		result = lc.checkAllLoginDetails(new User("33333", "5555"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserOKTeacher() {
		String expected = "OKTeacher";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", "0000"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserOKStudent() {
		String expected = "OKStudent";
		String result;
		result = lc.checkAllLoginDetails(new User("12121", "1111"), "test");
		assertTrue(expected.equals(result));
	}

	@Test
	public void testCheckAllLoginDetailsCaseUserOKPrincipal() {
		String expected = "OKPrincipal";
		String result;
		result = lc.checkAllLoginDetails(new User("22222", "1212"), "test");
		assertTrue(expected.equals(result));
	}
}
