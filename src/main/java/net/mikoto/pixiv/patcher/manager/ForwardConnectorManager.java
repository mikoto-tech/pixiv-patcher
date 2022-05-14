package net.mikoto.pixiv.patcher.manager;

import net.mikoto.pixiv.forward.connector.ForwardConnector;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/5/8 4:28
 */
public interface ForwardConnectorManager {
    /**
     * Save forward connector.
     *
     * @param forwardConnectorName The forward connector name.
     * @param forwardConnector     The forward connector.
     */
    void saveForwardConnector(String forwardConnectorName, ForwardConnector forwardConnector);

    /**
     * Create a new forward connector.
     *
     * @param properties The properties.
     * @return The forward controller.
     */
    ForwardConnector createForwardConnector(Properties properties) throws IOException, NoSuchMethodException;

    /**
     * Get a forward connector by forward connector name.
     *
     * @param forwardConnectorName The forward connector name.
     * @return The forward connector.
     */
    ForwardConnector getForwardConnector(String forwardConnectorName);

    /**
     * Get all the forward connector name.
     *
     * @return The forward connector names.
     */
    Set<String> getAllForwardConnectorName();
}
