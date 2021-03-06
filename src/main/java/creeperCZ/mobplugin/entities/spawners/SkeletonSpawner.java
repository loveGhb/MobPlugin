/**
 * CreeperSpawner.java
 * <p>
 * Created on 10:39:49
 */
package creeperCZ.mobplugin.entities.spawners;

import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import creeperCZ.mobplugin.AutoSpawnTask;
import creeperCZ.mobplugin.FileLogger;
import creeperCZ.mobplugin.entities.autospawn.AbstractEntitySpawner;
import creeperCZ.mobplugin.entities.autospawn.SpawnResult;
import creeperCZ.mobplugin.entities.monster.walking.Skeleton;

/**
 * Each entity get it's own spawner class.
 *
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class SkeletonSpawner extends AbstractEntitySpawner {

    /**
     * @param spawnTask
     */
    public SkeletonSpawner(AutoSpawnTask spawnTask, Config pluginConfig) {
        super(spawnTask, pluginConfig);
    }

    @Override
    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockLightLevel > 7) { // lightlevel not working for now, but as lightlevel is always zero that should work
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 127 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else { // creeper is spawned
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 2.8, 0));
        }

        FileLogger.info(String.format("[%s] spawn for %s at %s,%s,%s with lightlevel %s, result: %s", getLogprefix(), iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, result));

        return result;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Skeleton.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Skeleton";
    }

    /* (@Override)
     * @see de.kniffo80.mobplugin.entities.autospawn.AbstractEntitySpawner#getLogprefix()
     */
    @Override
    protected String getLogprefix() {
        return this.getClass().getSimpleName();
    }

}
