package logic;

import common.Message;

public class LoginController 
{
	User userToSend;
	static User user;
	ClientConsole client;
	
	public LoginController(User u) 
	{
		this.client = new ClientConsole();
		userToSend=u;
	}
	
	public LoginController()
	{
		this.client = new ClientConsole();
	}

	public boolean checkIfUserIDExist() 
	{
		Message userMsg=new Message();
		userMsg.setSentObj(userToSend);
		userMsg.setqueryToDo("checkIfUserExist");
		userMsg.setClassType("User");
		client.accept(userMsg);
		
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userMsg=client.getMessage();
		user = (User)userMsg.getReturnObj();
		if(user.getuName()==null)
			return false;
		return true;
	}

	public String getPassword() 
	{
		return user.getPassword();
	}

	public boolean isConnected() 
	{
		if(user.getIsLoggedIn()==0)
			return false;
		return true;
	}
	
	public String getTitle()	
	{
		return user.getTitle();
	}

	public User getUser() {
		return user;
	}
	
}
