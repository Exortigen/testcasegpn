package com.testcase.testcasegpn.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Data
@Table(name="request")
public class Request {
    @Id
    private long personalhash;
    private int intA;
    private int intB;
    private String method;
    private int result;
    private int methodInt;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Request() {
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
        personalhash = Objects.hash(intA, intB, methodInt);
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

    public long getPersonalhash() {
        return personalhash;
    }

    public void setMethodInt(int methodInt) {
        this.methodInt = methodInt;
    }
}
