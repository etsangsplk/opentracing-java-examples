package com.example.demoopentracing;

import com.lightstep.tracer.jre.JRETracer;
import io.opentracing.util.GlobalTracer;
import com.lightstep.tracer.shared.Options;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConcreteTracer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcreteTracer.class);

    public static final String LIGHTSTEP_ENABLED_ENVVAR  = "LIGHTSTEP_ENABLED";
    public static final String LIGHTSTEP_ACCESSTOKEN_ENVVAR = "LIGHTSTEP_ACCESS_TOKEN";
    public static final String LIGHTSTEP_VERBOSE_ENVVAR = "LIGHTSTEP_VERBOSE";

    public static final String LIGHTSTEP_COLLECTORHOST_DEFAULT = "collector.lightstep.com";
    public static final int LIGHTSTEP_COLLECTORPORT_DEFAULT = 443;
    public static final String LIGHTSTEP_COLLECTORPROTOCOL_DEFAUlT = "http";

    public static final String LIGHTSTEP_ENABLED_DEFAUlT = "false";
    public static int LIGHTSTEP_VERBOSITY_DEFAULT = Options.VERBOSITY_INFO;

    public static Tracer getTracer(String serviceName) {
        Tracer tracer = NoopTracerFactory.create();
        GlobalTracer.register(tracer);
        try {
            Map<String, String> config = getSettings();
            String enabled = config.getOrDefault(LIGHTSTEP_ENABLED_ENVVAR, LIGHTSTEP_ENABLED_DEFAUlT);

            Boolean tracerEnabled = Boolean.parseBoolean(enabled);
            if (tracerEnabled == false) {
                LOGGER.info("Tracer disabled. Returning NoopTracer.");
                return tracer;
            }

            String verbose = config.getOrDefault(LIGHTSTEP_VERBOSE_ENVVAR, String.valueOf(LIGHTSTEP_VERBOSITY_DEFAULT));
            String accesstoken = config.getOrDefault(LIGHTSTEP_ACCESSTOKEN_ENVVAR, "");
            int verbosity = Integer.parseInt(verbose);
            LOGGER.info("Initializing tracer with service name {}.", serviceName);
            // TODO unable to use http to our http collector.
            Options options = new Options.OptionsBuilder()
                    .withAccessToken(accesstoken)
                    .withComponentName(serviceName)
                    .withVerbosity(verbosity)
                    .build();
            tracer = new JRETracer(options);
            LOGGER.info("Tracer initialized with serviceName", serviceName);
            GlobalTracer.register(tracer);
            return tracer;

        } catch (MalformedURLException ex) {
            // TODO: Don't know whay Malformed exception is not caught here.
            LOGGER.warn("Tracer initialized failed with exception={}. returning NoopTracer. ", ex.getMessage());
            return tracer;
        }
    }

    public static void flush(Tracer tracer) {
         if(tracer instanceof com.lightstep.tracer.jre.JRETracer) {
            ((com.lightstep.tracer.jre.JRETracer) tracer).flush(20000);
         }
    }

    private static Map<String, String> getSettings() {
        String enabled = getEnv(LIGHTSTEP_ENABLED_ENVVAR, LIGHTSTEP_ENABLED_DEFAUlT);
        String accessToken = getEnv(LIGHTSTEP_ACCESSTOKEN_ENVVAR, "");
        String verbose  = getEnv(LIGHTSTEP_VERBOSE_ENVVAR, String.valueOf(LIGHTSTEP_VERBOSITY_DEFAULT));
        HashMap<String, String> config = new HashMap<>();
        config.put(LIGHTSTEP_ENABLED_ENVVAR, enabled);
        config.put(LIGHTSTEP_ACCESSTOKEN_ENVVAR, accessToken);
        config.put(LIGHTSTEP_VERBOSE_ENVVAR, verbose);
        return config;
    }


    private static String getEnv(String key, String defaultValue) {
        String result = System.getenv(key);
        return result != null ? result : defaultValue;
    }
}