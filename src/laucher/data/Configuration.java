package laucher.data;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    public static List<Configuration> configurationList = new ArrayList<>();

    String alias;

    public Configuration(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return alias;
    }
}
