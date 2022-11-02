package resources;

import com.github.javafaker.Faker;
import pojo.Simulation;

public class TestDataBuild extends Utils{

    protected static Faker faker = new Faker();
    public static Simulation addSimulation(double value, int instalments, boolean hasRestriction) {
        Simulation simulation = new Simulation();
        simulation.setCpf(generateValidCPF());
        simulation.setName(String.valueOf(faker.name().fullName()));
        simulation.setEmail(faker.internet().emailAddress());
        simulation.setValue(value);
        simulation.setInstalments(instalments);
        simulation.setInsurance(false);
        return simulation;
    }

    public static void main(String[] args) {
        TestDataBuild testDataBuild = new TestDataBuild();
        Simulation simulation = testDataBuild.addSimulation(1000D, 3, false);
        System.out.println(simulation.getCpf());
        System.out.println(simulation.getEmail());
        System.out.println(simulation.getInstalments());
        System.out.println(simulation.getName());
        System.out.println(simulation.getValue());
        System.out.println(simulation.isInsurance());
    }
}
