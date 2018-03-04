package by.htp.library.service;

import by.htp.library.entity.UserParameters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	private static final String LOGIN_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
	private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{4,20}$";
	private static final String NAME_PATTERN = "^[а-яА-ЯёЁa-zA-Z0-9]+$";
	private static final String SURNAME_PATTERN = "^[а-яА-ЯёЁa-zA-Z0-9]+$";
	private static final String EMAIL_PATTERN = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
	
	public boolean validate(String param, UserParameters paramType) {
		
		Pattern pattern;  
	    Matcher matcher;
		
		if(param == null) {
			return false;
		}
		
		switch(paramType) {
		case LOGIN:
			pattern = Pattern.compile(LOGIN_PATTERN);
			matcher = pattern.matcher(param);
			return matcher.matches();
		case PASSWORD:
			pattern = Pattern.compile(PASSWORD_PATTERN);
			matcher = pattern.matcher(param);
			return matcher.matches();
		case NAME:
			pattern = Pattern.compile(NAME_PATTERN);
			matcher = pattern.matcher(param);
			return matcher.matches();
		case SURNAME:
			pattern = Pattern.compile(SURNAME_PATTERN);
			matcher = pattern.matcher(param);
			return matcher.matches();
		case EMAIL:
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(param);
			return matcher.matches();
		default:
			break;
		}
		
		return false;
	}
}
