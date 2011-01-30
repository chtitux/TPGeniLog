package fr.uha.ensisa.gl.lift.soft.model;

public class AdapterFactory{
    private static final String ADAPTER_IMPLEMENTATION = "AdapterImpl";

    public static AdapterInterface getForSuite(final String projectName, final String suiteName) {
        final ClassLoader classloader = AdapterFactory.class.getClassLoader();
        try {
            return instanciate(
                    (Class<AdapterInterface>) classloader.loadClass(
                            projectName + '.' + suiteName + '.' + ADAPTER_IMPLEMENTATION));
        } catch (ClassNotFoundException e) {
            try {
                return instanciate(
                        (Class<AdapterInterface>) classloader.loadClass(projectName + '.' + ADAPTER_IMPLEMENTATION));
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException("cannot find an Adapter implementation");
            }
        }
    }

    private static AdapterInterface instanciate(final Class<AdapterInterface> klass) {
        try {
            return klass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("cannot instanciate the Adapter implementation");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("cannot access the Adapter implementation");
        }
    }
}