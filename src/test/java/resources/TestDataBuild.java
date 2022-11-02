package resources;

import com.github.javafaker.Faker;
import pojo.Simulation;

public class TestDataBuild extends Utils{

    protected static Faker faker = new Faker();
    public static Simulation addSimulation(double value, int instalments, boolean hasRestriction) {
        Simulation simulation = new Simulation();
        simulation.setCpf(generateValidCPF());
        simulation.setNome(String.valueOf(faker.name().fullName()));
        simulation.setEmail(faker.internet().emailAddress());
        simulation.setValor(value);
        simulation.setParcelas(instalments);
        simulation.setSeguro(hasRestriction);
        return simulation;
    }
}
