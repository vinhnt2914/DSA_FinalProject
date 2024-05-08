package com.example.testapi.services;

public class ServiceMapper {
    private final String[] services;
    private static ServiceMapper single_ServiceMapper = null;

    private ServiceMapper() {
        this.services = new String[]{"Cafe", "Hospital", "School", "Library", "Market",
                "Restaurant", "Park", "Museum", "Gym", "Cinema"};
    }

    public String getService(int index) {
        return services[index];
    }

    public Byte getIndex(String service) {
        for (int i = 0; i < services.length; i++) {
            if (services[i].equalsIgnoreCase(service)) {
                return (byte) i;
            }
        }
        return -1;
    }

    public static synchronized ServiceMapper getInstance()
    {
        if (single_ServiceMapper == null)
            single_ServiceMapper = new ServiceMapper();

        return single_ServiceMapper;
    }
}
