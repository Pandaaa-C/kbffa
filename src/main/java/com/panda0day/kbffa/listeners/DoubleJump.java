package com.panda0day.kbffa.listeners;

import com.panda0day.kbffa.Main;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (Main.getDoubleJumpAddon().canDoubleJump(player) && !player.isFlying()) {
            event.setCancelled(true);

            player.setAllowFlight(false);
            player.setVelocity(player.getLocation().getDirection().setY(0.5));
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 30, 0.5, 0.1, 0.5, 0.1);
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1f, 1f);
        }
    }

    @EventHandler
    public void onPlayerLand(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (((Entity) player).isOnGround() && Main.getDoubleJumpAddon().canDoubleJump(player)) {
            player.setAllowFlight(true);
        }
    }
}
