package com.practicum.currencyconverter.data.models;

import com.google.gson.annotations.SerializedName;

public class CurrencyExchange{

	@SerializedName("result")
	private String result;

	@SerializedName("time_next_update_unix")
	private int timeNextUpdateUnix;

	@SerializedName("conversion_rates")
	private ConversionRates conversionRates;

	@SerializedName("time_next_update_utc")
	private String timeNextUpdateUtc;

	@SerializedName("documentation")
	private String documentation;

	@SerializedName("time_last_update_unix")
	private int timeLastUpdateUnix;

	@SerializedName("base_code")
	private String baseCode;

	@SerializedName("time_last_update_utc")
	private String timeLastUpdateUtc;

	@SerializedName("terms_of_use")
	private String termsOfUse;

	public String getResult(){
		return result;
	}

	public int getTimeNextUpdateUnix(){
		return timeNextUpdateUnix;
	}

	public ConversionRates getConversionRates(){
		return conversionRates;
	}

	public String getTimeNextUpdateUtc(){
		return timeNextUpdateUtc;
	}

	public String getDocumentation(){
		return documentation;
	}

	public int getTimeLastUpdateUnix(){
		return timeLastUpdateUnix;
	}

	public String getBaseCode(){
		return baseCode;
	}

	public String getTimeLastUpdateUtc(){
		return timeLastUpdateUtc;
	}

	public String getTermsOfUse(){
		return termsOfUse;
	}
}