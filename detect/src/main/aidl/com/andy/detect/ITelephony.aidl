// ITelephony.aidl
package com.andy.detect;

// Declare any non-default types here with import statements

interface ITelephony {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     String getDeviceId(String callingPackage);

     String getString(in String key, in String defaultValue, in int userId);

}
