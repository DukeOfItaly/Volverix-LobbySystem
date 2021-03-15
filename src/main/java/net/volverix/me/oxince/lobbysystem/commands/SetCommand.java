package net.volverix.me.oxince.lobbysystem.commands;

/*
 * Copyright © 2021 oxince. All Rights Reserved.
 * Created: 2021 / 07:39
 */

import net.volverix.me.oxince.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("volverix.admin")) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("spawn")) {
                        LobbySystem.getLobbySystem().getLocationPattern().setLocation(player.getLocation(), "spawn");
                        player.sendMessage(LobbySystem.getPrefix() + "You've successfully set the spawn point!");
                    } else {
                        player.sendMessage(LobbySystem.getPrefix() + "Please use /set <Spawn>");
                    }
                } else {
                    player.sendMessage(LobbySystem.getPrefix() + "Please use /set <Spawn>");
                }
            }
        }

        return false;
    }
}
