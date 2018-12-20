package spidaApp.domain;

public class ConstructConnection {

	private String userName;
	private String userPassword;
	private String urlForConnect;
	private String driverForConnect;
	private String schemaForConnect;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUrlForConnect() {
		return urlForConnect;
	}
	public void setUrlForConnect(String urlForConnect) {
		this.urlForConnect = urlForConnect;
	}
	public String getDriverForConnect() {
		return driverForConnect;
	}
	public void setDriverForConnect(String driverForConnect) {
		this.driverForConnect = driverForConnect;
	}
	public String getSchemaForConnect() {
		return schemaForConnect;
	}
	public void setSchemaForConnect(String schemaForConnect) {
		this.schemaForConnect = schemaForConnect;
	}
	@Override
	public String toString() {
		return "ConstructConnection [userName=" + userName + ", userPassword=" + userPassword + ", urlForConnect="
				+ urlForConnect + ", driverForConnect=" + driverForConnect + ", schemaForConnect=" + schemaForConnect
				+ "]";
	}
	
}
