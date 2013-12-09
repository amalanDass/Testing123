package com.mcw.utility;

/**
 * <b>Log</b> 
 * <br><p>
 *  Just modify the boolean variable LOG to true for Enable Log
 * 
 * And false for Disable Log.</p>
 * <hr/><h2>
 * - CG</h2>
 *
 */
public class Log {
	
	static final boolean LOG = false;
/**
 *    Info
 * @param TAG - TAG
 * @param string - Msg
 */
	public static void i(String TAG, String string) {
	  if (LOG) android.util.Log.i(TAG, string);
	}
	/**
	 *    Error
	 * @param TAG - TAG
	 * @param string - Msg
	 */
	
	
	public static void e(String TAG, String string) {
	    if (LOG) android.util.Log.e(TAG, string);
	}
	/**
	 *    Debug
	 * @param TAG - TAG
	 * @param string - Msg
	 */
	public static void d(String TAG, String string) {
	    if (LOG) android.util.Log.d(TAG, string);
	}
	/**
	 *    VERBOSE
	 * @param TAG - TAG
	 * @param string - Msg
	 */
	public static void v(String TAG, String string) {
	    if (LOG) android.util.Log.v(TAG, string);
	}
	/**
	 *    Warning
	 * @param TAG - TAG
	 * @param string - Msg
	 */
	public static void w(String TAG, String string) {
	   if (LOG) android.util.Log.w(TAG, string);
	}
	public static void e(String TAG, String string, Exception e) {
		if (LOG) android.util.Log.e(TAG, string,e);
	}
	
}
