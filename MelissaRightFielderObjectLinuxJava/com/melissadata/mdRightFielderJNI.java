package com.melissadata;

public class mdRightFielderJNI {
	static {
		try {
			System.loadLibrary("mdRightFielderJavaWrapper");
		} catch (UnsatisfiedLinkError ule) {
			System.out.println(ule);
			System.out.println("java.library.path="+System.getProperty("java.library.path"));
		}
	}

	public final static native long mdRightFielderCreate();
	public final static native void mdRightFielderDestroy(long I);
	public final static native boolean SetLicenseString(long I,String p1);
	public final static native void SetPathToRightFielderFiles(long I,String p1);
	public final static native String GetBuildNumber(long I);
	public final static native String GetDatabaseDate(long I);
	public final static native String GetLicenseExpirationDate(long I);
	public final static native int InitializeDataFiles(long I);
	public final static native String GetInitializeErrorString(long I);
	public final static native void SetDelimiter(long I,int p1);
	public final static native void SetAcceptFullName(long I,boolean p1);
	public final static native void SetAcceptDepartment(long I,boolean p1);
	public final static native void SetAcceptCompany(long I,boolean p1);
	public final static native void SetAcceptAddress(long I,boolean p1);
	public final static native void SetAcceptCityStateZip(long I,boolean p1);
	public final static native void SetAcceptCountry(long I,boolean p1);
	public final static native void SetAcceptPhone(long I,boolean p1);
	public final static native void SetAcceptEmail(long I,boolean p1);
	public final static native void SetAcceptURL(long I,boolean p1);
	public final static native boolean SetUserPattern(long I,String p1,String p2);
	public final static native boolean Parse(long I,String p1);
	public final static native boolean ParseFreeForm(long I,String p1);
	public final static native boolean ParseFielded(long I,String p1);
	public final static native String GetFullName(long I);
	public final static native boolean GetFullNameNext(long I);
	public final static native String GetDepartment(long I);
	public final static native boolean GetDepartmentNext(long I);
	public final static native String GetCompany(long I);
	public final static native boolean GetCompanyNext(long I);
	public final static native String GetAddress(long I);
	public final static native String GetAddress2(long I);
	public final static native String GetAddress3(long I);
	public final static native String GetCity(long I);
	public final static native String GetState(long I);
	public final static native String GetPostalCode(long I);
	public final static native String GetCountry(long I);
	public final static native String GetLastLine(long I);
	public final static native String GetPhone(long I);
	public final static native boolean GetPhoneNext(long I);
	public final static native String GetPhoneType(long I);
	public final static native boolean GetPhoneTypeNext(long I);
	public final static native String GetEmail(long I);
	public final static native boolean GetEmailNext(long I);
	public final static native String GetURL(long I);
	public final static native boolean GetURLNext(long I);
	public final static native String GetUserField(long I,String p1);
	public final static native boolean GetUserFieldNext(long I,String p1);
	public final static native String GetUnrecognized(long I);
	public final static native boolean GetUnrecognizedNext(long I);
	public final static native String GetResults(long I);
	public final static native String GetResultCodeDescription(long I,String resultCode,int opt);
	public final static native void SetReserved(long I,String p1,String p2);
	public final static native String GetReserved(long I,String p1);
}
