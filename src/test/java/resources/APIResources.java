package resources;

public enum APIResources {

    getRestrictionAPI("/api/v1/restricoes/"),
    getSimulationAPI("/api/v1/simulacoes/"),
    getAllSimulationsAPI("/api/v1/simulacoes"),
    postSimulationAPI("/api/v1/simulacoes"),
    putSimulationAPI("/api/v1/simulacoes/"),
    deleteSimulationAPI("/api/v1/simulacoes/");

    private String resource;

    APIResources(final String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
