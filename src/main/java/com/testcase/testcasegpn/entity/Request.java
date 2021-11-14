package com.testcase.testcasegpn.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Schema(description = "Сущность запроса")
@Entity
@Table(name="request")
public class Request {
    @Id
    @Schema(description = "Хэш операции", accessMode = Schema.AccessMode.READ_ONLY)
    private long personalhash;
    @Schema(description = "Вычислительная операция(int)", accessMode = Schema.AccessMode.READ_ONLY)
    private int methodInt;
    @Schema(description = "Вычислительная операция", accessMode = Schema.AccessMode.READ_ONLY)
    private String method;
    @Schema(description = "Результат операции", accessMode = Schema.AccessMode.READ_ONLY)
    private int result;
    @Schema(description = "Аргумент 'A'", example = "30")
    private int intA;
    @Schema(description = "Аргумент 'B'", example = "15")
    private int intB;


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

    public int getMethodInt() {
        return methodInt;
    }
}
