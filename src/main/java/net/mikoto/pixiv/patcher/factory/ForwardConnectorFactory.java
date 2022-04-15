package net.mikoto.pixiv.patcher.factory;

import net.mikoto.pixiv.api.pojo.ForwardServer;
import net.mikoto.pixiv.forward.connector.ForwardConnector;

import java.io.IOException;

import static net.mikoto.pixiv.patcher.constant.Properties.FORWARD_SERVER;
import static net.mikoto.pixiv.patcher.constant.Properties.MAIN_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/4/10 5:31
 */
public class ForwardConnectorFactory {
    private static final net.mikoto.pixiv.forward.connector.factory.ForwardConnectorFactory FORWARD_CONNECTOR_FACTORY = new net.mikoto.pixiv.forward.connector.factory.ForwardConnectorFactory();
    private static final ForwardConnectorFactory INSTANCE = new ForwardConnectorFactory();

    private ForwardConnectorFactory() {
    }

    public static ForwardConnectorFactory getInstance() {
        return INSTANCE;
    }

    public ForwardConnector create() throws IOException, NoSuchMethodException {
        ForwardConnector forwardConnector = FORWARD_CONNECTOR_FACTORY.create();

        for (String forwardServer :
                MAIN_PROPERTIES.getProperty(FORWARD_SERVER).split(";")) {
            String[] data = forwardServer.split(",");
            forwardConnector.addForwardServer(new ForwardServer(data[0], Integer.parseInt(data[1]), data[2]));
        }

        return forwardConnector;
    }
}
