package pojo;

import java.util.List;

public class Simulation {
    
    private Restriction hasRestriction;
    private String name;
    private String email;
    private double value;
    private int instalments;
    private boolean insurance;
    private List<String> errors;

    public Restriction getHasRestriction() {
        return hasRestriction;
    }

    public void setHasRestriction(Restriction hasRestriction) {
        this.hasRestriction = hasRestriction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getInstalments() {
        return instalments;
    }

    public void setInstalments(int instalments) {
        this.instalments = instalments;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
