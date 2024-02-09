package com.melissadata;

public class mdRightFielder {
	private long I;
	protected boolean ownMemory;

	protected static long getI(mdRightFielder obj) {
		return (obj==null ? 0 : obj.I);
	}

	protected void finalize() {
		delete();
	}

public final static class ProgramStatus {
	public final static mdRightFielder.ProgramStatus NoError=new mdRightFielder.ProgramStatus("NoError",0);
	public final static mdRightFielder.ProgramStatus ConfigFile=new mdRightFielder.ProgramStatus("ConfigFile",1);
	public final static mdRightFielder.ProgramStatus LicenseExpired=new mdRightFielder.ProgramStatus("LicenseExpired",2);
	public final static mdRightFielder.ProgramStatus Unknown=new mdRightFielder.ProgramStatus("Unknown",4);

	private final String enumName;
	private final int enumValue;
	private static ProgramStatus[] enumValues={NoError,ConfigFile,LicenseExpired,Unknown};

	private ProgramStatus(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static ProgramStatus toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+ProgramStatus.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class Delimiter {
	public final static mdRightFielder.Delimiter Tab=new mdRightFielder.Delimiter("Tab",0);
	public final static mdRightFielder.Delimiter Comma=new mdRightFielder.Delimiter("Comma",1);
	public final static mdRightFielder.Delimiter Pipe=new mdRightFielder.Delimiter("Pipe",2);
	public final static mdRightFielder.Delimiter CRLF=new mdRightFielder.Delimiter("CRLF",3);

	private final String enumName;
	private final int enumValue;
	private static Delimiter[] enumValues={Tab,Comma,Pipe,CRLF};

	private Delimiter(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static Delimiter toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+Delimiter.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

public final static class ResultCdDescOpt {
	public final static mdRightFielder.ResultCdDescOpt ResultCodeDescriptionLong=new mdRightFielder.ResultCdDescOpt("ResultCodeDescriptionLong",0);
	public final static mdRightFielder.ResultCdDescOpt ResultCodeDescriptionShort=new mdRightFielder.ResultCdDescOpt("ResultCodeDescriptionShort",1);

	private final String enumName;
	private final int enumValue;
	private static ResultCdDescOpt[] enumValues={ResultCodeDescriptionLong,ResultCodeDescriptionShort};

	private ResultCdDescOpt(String name,int val) {
		enumName=name;
		enumValue=val;
	}

	public static ResultCdDescOpt toEnum(int val) {
		for (int i=0;i<enumValues.length;i++)
			if (enumValues[i].enumValue==val)
				return enumValues[i];
		throw new IllegalArgumentException("No enum "+ResultCdDescOpt.class+" with value "+val+".");
	}

	public String toString() {
		return enumName;
	}

	public int toValue() {
		return enumValue;
	}
}

	protected mdRightFielder(long i,boolean own) {
		ownMemory=own;
		I=i;
	}

	public mdRightFielder() {
		this(mdRightFielderJNI.mdRightFielderCreate(),true);
	}

	public synchronized void delete() {
		if (I!=0) {
			if (ownMemory) {
				ownMemory=false;
				mdRightFielderJNI.mdRightFielderDestroy(I);
			}
			I=0;
		}
	}

	public boolean SetLicenseString(String p1) {
		return mdRightFielderJNI.SetLicenseString(I,p1);
	}

	public void SetPathToRightFielderFiles(String p1) {
		mdRightFielderJNI.SetPathToRightFielderFiles(I,p1);
	}

	public String GetBuildNumber() {
		return mdRightFielderJNI.GetBuildNumber(I);
	}

	public String GetDatabaseDate() {
		return mdRightFielderJNI.GetDatabaseDate(I);
	}

	public String GetLicenseExpirationDate() {
		return mdRightFielderJNI.GetLicenseExpirationDate(I);
	}

	public ProgramStatus InitializeDataFiles() {
		return ProgramStatus.toEnum(mdRightFielderJNI.InitializeDataFiles(I));
	}

	public String GetInitializeErrorString() {
		return mdRightFielderJNI.GetInitializeErrorString(I);
	}

	public void SetDelimiter(mdRightFielder.Delimiter p1) {
		mdRightFielderJNI.SetDelimiter(I,p1.toValue());
	}

	public void SetAcceptFullName(boolean p1) {
		mdRightFielderJNI.SetAcceptFullName(I,p1);
	}

	public void SetAcceptDepartment(boolean p1) {
		mdRightFielderJNI.SetAcceptDepartment(I,p1);
	}

	public void SetAcceptCompany(boolean p1) {
		mdRightFielderJNI.SetAcceptCompany(I,p1);
	}

	public void SetAcceptAddress(boolean p1) {
		mdRightFielderJNI.SetAcceptAddress(I,p1);
	}

	public void SetAcceptCityStateZip(boolean p1) {
		mdRightFielderJNI.SetAcceptCityStateZip(I,p1);
	}

	public void SetAcceptCountry(boolean p1) {
		mdRightFielderJNI.SetAcceptCountry(I,p1);
	}

	public void SetAcceptPhone(boolean p1) {
		mdRightFielderJNI.SetAcceptPhone(I,p1);
	}

	public void SetAcceptEmail(boolean p1) {
		mdRightFielderJNI.SetAcceptEmail(I,p1);
	}

	public void SetAcceptURL(boolean p1) {
		mdRightFielderJNI.SetAcceptURL(I,p1);
	}

	public boolean SetUserPattern(String p1, String p2) {
		return mdRightFielderJNI.SetUserPattern(I,p1,p2);
	}

	public boolean Parse(String p1) {
		return mdRightFielderJNI.Parse(I,p1);
	}

	public boolean ParseFreeForm(String p1) {
		return mdRightFielderJNI.ParseFreeForm(I,p1);
	}

	public boolean ParseFielded(String p1) {
		return mdRightFielderJNI.ParseFielded(I,p1);
	}

	public String GetFullName() {
		return mdRightFielderJNI.GetFullName(I);
	}

	public boolean GetFullNameNext() {
		return mdRightFielderJNI.GetFullNameNext(I);
	}

	public String GetDepartment() {
		return mdRightFielderJNI.GetDepartment(I);
	}

	public boolean GetDepartmentNext() {
		return mdRightFielderJNI.GetDepartmentNext(I);
	}

	public String GetCompany() {
		return mdRightFielderJNI.GetCompany(I);
	}

	public boolean GetCompanyNext() {
		return mdRightFielderJNI.GetCompanyNext(I);
	}

	public String GetAddress() {
		return mdRightFielderJNI.GetAddress(I);
	}

	public String GetAddress2() {
		return mdRightFielderJNI.GetAddress2(I);
	}

	public String GetAddress3() {
		return mdRightFielderJNI.GetAddress3(I);
	}

	public String GetCity() {
		return mdRightFielderJNI.GetCity(I);
	}

	public String GetState() {
		return mdRightFielderJNI.GetState(I);
	}

	public String GetPostalCode() {
		return mdRightFielderJNI.GetPostalCode(I);
	}

	public String GetCountry() {
		return mdRightFielderJNI.GetCountry(I);
	}

	public String GetLastLine() {
		return mdRightFielderJNI.GetLastLine(I);
	}

	public String GetPhone() {
		return mdRightFielderJNI.GetPhone(I);
	}

	public boolean GetPhoneNext() {
		return mdRightFielderJNI.GetPhoneNext(I);
	}

	public String GetPhoneType() {
		return mdRightFielderJNI.GetPhoneType(I);
	}

	public boolean GetPhoneTypeNext() {
		return mdRightFielderJNI.GetPhoneTypeNext(I);
	}

	public String GetEmail() {
		return mdRightFielderJNI.GetEmail(I);
	}

	public boolean GetEmailNext() {
		return mdRightFielderJNI.GetEmailNext(I);
	}

	public String GetURL() {
		return mdRightFielderJNI.GetURL(I);
	}

	public boolean GetURLNext() {
		return mdRightFielderJNI.GetURLNext(I);
	}

	public String GetUserField(String p1) {
		return mdRightFielderJNI.GetUserField(I,p1);
	}

	public boolean GetUserFieldNext(String p1) {
		return mdRightFielderJNI.GetUserFieldNext(I,p1);
	}

	public String GetUnrecognized() {
		return mdRightFielderJNI.GetUnrecognized(I);
	}

	public boolean GetUnrecognizedNext() {
		return mdRightFielderJNI.GetUnrecognizedNext(I);
	}

	public String GetResults() {
		return mdRightFielderJNI.GetResults(I);
	}

	public String GetResultCodeDescription(String resultCode) {
		return mdRightFielderJNI.GetResultCodeDescription(I,resultCode,0);
	}
	public String GetResultCodeDescription(String resultCode, mdRightFielder.ResultCdDescOpt opt) {
		return mdRightFielderJNI.GetResultCodeDescription(I,resultCode,opt.toValue());
	}

	public void SetReserved(String p1, String p2) {
		mdRightFielderJNI.SetReserved(I,p1,p2);
	}

	public String GetReserved(String p1) {
		return mdRightFielderJNI.GetReserved(I,p1);
	}

}
