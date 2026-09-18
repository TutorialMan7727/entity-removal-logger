package uk.vrtxx.entityremovallogger;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityRemovalLogger extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Entity Removal Logger enabled.");
        getLogger().info("Logging all non-player entity removals.");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityRemove(EntityRemoveEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            return;
        }

        if (entity.getType() != org.bukkit.entity.EntityType.VILLAGER
                && entity.getType() != org.bukkit.entity.EntityType.ITEM) {
            return;
        }

        Location location = entity.getLocation();
        String customName = entity.getCustomName() == null ? "none" : entity.getCustomName();

        getLogger().warning(String.format(
                "ENTITY REMOVED | type=%s | uuid=%s | world=%s | x=%.2f y=%.2f z=%.2f | cause=%s | living=%s | name=%s",
                entity.getType(),
                entity.getUniqueId(),
                location.getWorld() != null ? location.getWorld().getName() : "unknown",
                location.getX(),
                location.getY(),
                location.getZ(),
                event.getCause(),
                entity instanceof org.bukkit.entity.LivingEntity,
                customName
        ));
    }
}
