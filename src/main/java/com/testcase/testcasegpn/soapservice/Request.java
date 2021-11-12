package com.testcase.testcasegpn.soapservice;

import java.util.Objects;

public class Request {
    private int intA;
    private int intB;
    private int result;
    private long hashcode;
    private String method;

    private int methodInt;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Request() {
    }

    public Request(int intA, int intB, int methodInt) {
        this.intA = intA;
        this.intB = intB;
        this.methodInt = methodInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return intA == request.intA &&
                intB == request.intB &&
                methodInt == request.methodInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(intA, intB, methodInt);
    }

    public int getIntA() {
        return intA;
    }

    public void setIntA(int intA) {
        this.intA = intA;
    }

    public int getIntB() {
        return intB;
    }

    public void setIntB(int intB) {
        this.intB = intB;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getHashcode() {
        return hashcode;
    }

    public void setHashcode(long hashcode) {
        this.hashcode = hashcode;
    }

    public int getMethodInt() {
        return methodInt;
    }

    public void setMethodInt(int methodInt) {
        this.methodInt = methodInt;
    }
}
