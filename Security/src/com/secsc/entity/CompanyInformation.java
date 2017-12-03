package com.secsc.entity;

/**
 * @Description 企业基本信息实体类
 * @author 吴俊杰
 * @Date 2017/07/12
 */
public class CompanyInformation {

	private String companyName;
	
	private String legalPersonCode;
	
	private String industrySort;
	
	private String industrySmall;
	
	private String address;
	
	private String gps;
	
	private String logoUrl;
	
	private String regionAndCity;
	
	private int accountNumber;

	public CompanyInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompanyInformation(String companyName, String legalPersonCode,
			String industrySort, String industrySmall, String address,
			String gps, String logoUrl, int accountNumber) {
		super();
		this.companyName = companyName;
		this.legalPersonCode = legalPersonCode;
		this.industrySort = industrySort;
		this.industrySmall = industrySmall;
		this.address = address;
		this.gps = gps;
		this.logoUrl = logoUrl;
		this.accountNumber = accountNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLegalPersonCode() {
		return legalPersonCode;
	}

	public void setLegalPersonCode(String legalPersonCode) {
		this.legalPersonCode = legalPersonCode;
	}

	public String getIndustrySort() {
		return industrySort;
	}

	public void setIndustrySort(String industrySort) {
		this.industrySort = industrySort;
	}

	public String getIndustrySmall() {
		return industrySmall;
	}

	public void setIndustrySmall(String industrySmall) {
		this.industrySmall = industrySmall;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getRegionAndCity() {
		return regionAndCity;
	}

	public void setRegionAndCity(String regionAndCity) {
		this.regionAndCity = regionAndCity;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}



}