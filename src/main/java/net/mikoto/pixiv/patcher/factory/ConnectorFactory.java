package net.mikoto.pixiv.patcher.factory;

import net.mikoto.pixiv.api.connector.Connector;

import java.io.IOException;
import java.util.Properties;

/**
 * @author mikoto
 * @date 2022/5/28 15:41
 */
public interface ConnectorFactory {
    /**
     * Create a new forward connector.
     *
     * @param properties The properties.
     * @return The forward controller.
     */
    Connector createConnector(Properties properties) throws IOException, NoSuchMethodException;
}
