package ee.tlu.cwpc.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ee.tlu.cwpc.dto.Country;

public class Countries {

	public static List<Country> listAll() {
		List<Country> countries = new ArrayList<>();
		String[] countryCodes = Locale.getISOCountries();
		
		for (String countryCode : countryCodes) {
			Locale locale = new Locale("", countryCode);
			countries.add(new Country(locale.getDisplayCountry(), locale.getCountry()));
		}
		
		Collections.sort(countries);
		return countries;
	}
	
}
