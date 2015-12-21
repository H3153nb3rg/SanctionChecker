package at.jps.sanction.core;

import org.avaje.agentloader.AgentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagementConfig {

    static final Logger logger = LoggerFactory.getLogger(EntityManagementConfig.class);

    private String      enhancementCP;

    public String getEnhancementCP() {
        return enhancementCP;
    }

    public void setEnhancementCP(String enhancementCP) {
        this.enhancementCP = enhancementCP;
    }

    private void initializeORM() {
        if (getEnhancementCP() != null) {
            if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent", getEnhancementCP())) {
                logger.error("avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
            }
        }
    }

    public void initialize() {
        initializeORM();
    }
}
