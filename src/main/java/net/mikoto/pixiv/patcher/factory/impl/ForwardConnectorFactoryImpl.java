package net.mikoto.pixiv.patcher.factory.impl;

import net.mikoto.pixiv.api.connector.Connector;
import net.mikoto.pixiv.api.model.ForwardServer;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.SimpleForwardConnector;
import net.mikoto.pixiv.patcher.factory.ConnectorFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static net.mikoto.pixiv.patcher.manager.ConfigManager.FORWARD_SERVER;

/**
 * @author mikoto
 * @date 2022/5/28 15:42
 */
@Component("forwardConnectorFactory")
public class ForwardConnectorFactoryImpl implements ConnectorFactory {
    /**
     * Create a new forward connector.
     *
     * @param properties The properties.
     * @return The forward controller.
     */
    @Override
    public Connector createConnector(@NotNull Properties properties) {
        ForwardConnector forwardConnector = new SimpleForwardConnector();

        for (String forwardServer :
                properties.getProperty(FORWARD_SERVER).split(";")) {
            String[] data = forwardServer.split(",");
            forwardConnector.addForwardServer(new ForwardServer(data[0], Integer.parseInt(data[1]), data[2]));
        }

        return forwardConnector;
    }
}
