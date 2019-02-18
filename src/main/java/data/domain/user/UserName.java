package data.domain.user;

public enum UserName {
    SITEUSER("SITEUSER"), ADMIN("ADMIN");
    
    private String value;

	private UserName(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
