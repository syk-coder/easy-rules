package org.jeasy.rules.tutorials.shop;

import java.util.Objects;

public class Parent {

    private int age;
    private Boolean informed;

    public Parent(int age, Boolean informed) {
        this.age = age;
        this.informed = informed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getInformed() {
        return informed;
    }

    public void setInformed(Boolean informed) {
        this.informed = informed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent)) return false;
        Parent parent = (Parent) o;
        return getAge() == parent.getAge() && getInformed().equals(parent.getInformed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAge(), getInformed());
    }

    @Override
    public String toString() {
        return "Parent{" +
                "age=" + age +
                ", informed=" + informed +
                '}';
    }
}
