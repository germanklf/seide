package net.sf.seide.core;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.sf.seide.thread.JMXConfigurableThreadPoolExecutor;
import net.sf.seide.thread.JMXEnabledThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMXHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMXHelper.class);

    public static boolean isThreadPoolExecutorJMXEnabled(ExecutorService executor) {
        return executor instanceof JMXEnabledThreadPoolExecutor || executor instanceof JMXConfigurableThreadPoolExecutor;
    }

    public static void registerMXBean(Object object, String name) {
        LOGGER.info("Registering MBean [" + name + "]...");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            mbs.registerMBean(object, new ObjectName(name));
        } catch (Exception e) {
            LOGGER.error("Error registering MBean: [" + name + "]", e);
        }
    }

    public static void unregisterMXBean(String name) {
        LOGGER.info("Unregistering MBean [" + name + "]...");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            mbs.unregisterMBean(new ObjectName(name));
        } catch (Exception e) {
            LOGGER.error("Error unregistering MBean: [" + name + "]", e);
        }
    }

}
