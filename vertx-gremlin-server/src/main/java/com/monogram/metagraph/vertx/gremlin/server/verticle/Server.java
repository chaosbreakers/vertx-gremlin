package com.monogram.metagraph.vertx.gremlin.server.verticle;

import org.apache.commons.lang3.StringUtils;
import org.apache.tinkerpop.gremlin.server.GremlinServer;
import org.apache.tinkerpop.gremlin.server.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

/**
 * gremlin server.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/26.
 */
public enum Server {
    /**
     * server use singleton design patterns to ensure only one instance.
     */
    single;

    static {
        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    }

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private GremlinServer gremlinServer;
    private Settings settings = null;

    public Server init(String configFilePath) {
        if(gremlinServer != null){
            return this;
        }
        if (StringUtils.isNotEmpty(configFilePath)) {
            logger.error("start server failed, the config file path is empty.");
        }

        File configFile = new File(configFilePath);
        if (!configFile.exists() || !configFile.isFile()) {
            logger.error("the file {} is not exists or is not a file.", configFilePath);
        }

        try {
            settings = Settings.read(configFilePath);
        } catch (Exception e) {
            logger.error("start server failed", e.getMessage());
        }
        gremlinServer = new GremlinServer(settings);
        return this;
    }

    /**
     * start gremlin server.
     *
     * @throws Exception
     */
    public Server start() throws Exception {
        gremlinServer.start().exceptionally(t -> {
            logger.error("Server was unable to start and will now begin shutdown: {}", t.getMessage());
            this.stop();
            return null;
        }).join();
        return this;
    }

    /**
     * get gremlin server.
     *
     * @return gremlinServer
     */
    public GremlinServer getGremlinServer() {
        return gremlinServer;
    }

    /**
     * stop gremlin server.
     */
    public void stop() {
        if (gremlinServer != null) {
            gremlinServer.stop();
        }
    }

}
