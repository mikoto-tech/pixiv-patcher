package net.mikoto.pixiv.patcher.manager.impl;

import net.mikoto.pixiv.api.pojo.ForwardServer;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.factory.ForwardConnectorFactory;
import net.mikoto.pixiv.patcher.manager.ForwardConnectorManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static net.mikoto.pixiv.patcher.manager.ConfigManager.FORWARD_SERVER;

/**
 * @author mikoto
 * @date 2022/5/8 4:28
 */
@Component("forwardConnectorManager")
public class ForwardConnectorManagerImpl implements ForwardConnectorManager {
    private static final ForwardConnectorFactory FORWARD_CONNECTOR_FACTORY = new ForwardConnectorFactory();
    private static final Map<String, ForwardConnector> FORWARD_CONNECTOR_MAP = new ConcurrentHashMap<>();

    /**
     * Save forward connector.
     *
     * @param forwardConnectorName The forward connector name.
     * @param forwardConnector     The forward connector.
     */
    @Override
    public void saveForwardConnector(String forwardConnectorName, ForwardConnector forwardConnector) {
        FORWARD_CONNECTOR_MAP.put(forwardConnectorName, forwardConnector);
    }

    /**
     * Create a new forward connector.
     *
     * @param properties The properties.
     * @return The forward controller.
     */
    @Override
    public ForwardConnector createForwardConnector(@NotNull Properties properties) throws IOException, NoSuchMethodException {
        ForwardConnector forwardConnector = FORWARD_CONNECTOR_FACTORY.create();

        for (String forwardServer :
                properties.getProperty(FORWARD_SERVER).split(";")) {
            String[] data = forwardServer.split(",");
            forwardConnector.addForwardServer(new ForwardServer(data[0], Integer.parseInt(data[1]), data[2]));
        }

        return forwardConnector;
    }

    /**
     * Get a forward connector by forward connector name.
     *
     * @param forwardConnectorName The forward connector name.
     * @return The forward connector.
     */
    @Override
    public ForwardConnector getForwardConnector(String forwardConnectorName) {
        return FORWARD_CONNECTOR_MAP.get(forwardConnectorName);
    }

    /**
     * Get all the forward connector name.
     *
     * @return The forward connector names.
     */
    @Override
    public Set<String> getAllForwardConnectorName() {
        return FORWARD_CONNECTOR_MAP.keySet();
    }
}
