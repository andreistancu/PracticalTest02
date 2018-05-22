package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by student on 22.05.2018.
 */

public class Information {

    private String city;
//    private String windSpeed;
//    private String condition;
//    private String pressure;
//    private String humidity;

    public Information() {
//        this.temperature = null;
//        this.windSpeed = null;
//        this.condition = null;
//        this.pressure = null;
//        this.humidity = null;
        this.city = null;
    }

//    public Information(String temperature, String windSpeed, String condition, String pressure, String humidity) {
//        this.temperature = temperature;
//        this.windSpeed = windSpeed;
//        this.condition = condition;
//        this.pressure = pressure;
//        this.humidity = humidity;
//    }
    public Information(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "city='" + city + '\'';
    }

}