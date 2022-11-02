package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import steps.RestrictionsSteps;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Random;

public abstract class Utils {
    public static RequestSpecification req;

    public RequestSpecification requestSpecification(String resource) throws IOException {
        if(req==null)
        {
            PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
            APIResources resourceAPI = APIResources.valueOf(resource);
            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .setBasePath(RestrictionsSteps.cpfWithoutRestriction)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }
    public static String getGlobalValue(String key) throws IOException {
        Properties prop =new Properties();
        FileInputStream fis =new FileInputStream("src/test/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key) {
        String resp=response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

    public static String generateValidCPF(){
        Random r = new Random();
        StringBuilder sbCpfNumber = new StringBuilder();
        for(int i = 0; i < 9; i++){
            sbCpfNumber.append(r.nextInt(9));
        }
        return generateDigits(sbCpfNumber.toString());
    }

//    public boolean validateCPF(String cpf){
//        if(cpf.length() == 11){
//            if(cpf.equals(generateDigits(cpf.substring(0, 9)))){
//                return true;
//            }
//        }
//        return false;
//    }

    private static String generateDigits(String digitsBase) {
        StringBuilder sbCpfNumber = new StringBuilder(digitsBase);
        int total = 0;
        int multiple = digitsBase.length() + 1;
        for (char digit : digitsBase.toCharArray()) {
            long parcial = (long) Integer.parseInt(String.valueOf(digit)) * (multiple--);
            total += parcial;
        }
        int resto = Integer.parseInt(String.valueOf(Math.abs(total % 11)));
        if (resto < 2) {
            resto = 0;
        } else {
            resto = 11 - resto;
        }
        sbCpfNumber.append(resto);
        if (sbCpfNumber.length() < 11) {
            return generateDigits(sbCpfNumber.toString());
        }
        return sbCpfNumber.toString();
    }
}
