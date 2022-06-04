package net.mikoto.pixiv.patcher.factory.impl;

import net.mikoto.pixiv.api.connector.Connector;
import net.mikoto.pixiv.database.connector.SimpleDatabaseConnector;
import net.mikoto.pixiv.patcher.factory.ConnectorFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author mikoto
 * @date 2022/5/28 17:01
 */
@Component("databaseConnectorFactory")
public class DatabaseConnectorFactoryImpl implements ConnectorFactory {

    @Override
    public Connector createConnector(Properties properties) {
        return new SimpleDatabaseConnector();
    }
}
