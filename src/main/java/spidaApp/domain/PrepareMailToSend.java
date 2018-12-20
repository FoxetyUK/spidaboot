package sqrm.integration.domain;

public class PrepareMailToSend {

	private String id;
	private String name;
	private String eMail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	@Override
	public String toString() {
		return "PrepareMailToSend [id=" + id + ", name=" + name + ", eMail=" + eMail + "]";
	}
}
