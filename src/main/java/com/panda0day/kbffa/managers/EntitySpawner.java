package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class EntitySpawner {
    public EntitySpawner(EntityType entity, Location location) {
        spawn(entity, location, "");
    }
    public EntitySpawner(EntityType entity, Location location, String name) {
        spawn(entity, location, name);
    }

    public void spawn(EntityType entity, Location location, String name) {
        World world = location.getWorld();
        if (world == null) {
            Main.getInstance().getLogger().warning("World not found for location: " + location);
            return;
        }

        assert entity.getEntityClass() != null;
        Entity _entity = world.spawn(location, entity.getEntityClass());
        _entity.setCustomName(name);
        _entity.setCustomNameVisible(true);
        _entity.setInvulnerable(true);

        if (_entity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) _entity;
            villager.setAI(false);
            villager.setSilent(true);
        }
    }
}
