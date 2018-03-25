package by.htp.library.entity.helper;

public enum UserParameters {
	LOGIN("login"), PASSWORD("password"), NAME("name"), SURNAME("surname"), EMAIL("e-mail");

	private String parameter;

	UserParameters(String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}
}
