// IDemo.aidl
package com.andy.androinfo;

// Declare any non-default types here with import statements

interface IDemo {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void sendData(String data);
    String getData();
}
