package com.oceangrave.fallendawn;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Oceangrave (ssaxalin) ru community
 */
@Mod(Main.MODID)
public final class Main {

    public static final String MODID = "main";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Main() {
        LOGGER.debug("Hello from Example Mod!");

    }

}
