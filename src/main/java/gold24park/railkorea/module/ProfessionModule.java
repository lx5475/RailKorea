package gold24park.railkorea.module;

import gold24park.railkorea.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ProfessionModule {

    private static ProfessionModule instance;
    private Plugin main;

    public static ProfessionModule getInstance(Plugin main) {
        if (instance == null)
            instance = new ProfessionModule(main);
        return instance;
    }

    private ProfessionModule(Plugin main) {
        this.main = main;
    }

    private ChatColor getProfessionColor(int level) {
        ChatColor chatColor = ChatColor.GRAY;
        if (level >= 5) {
            chatColor = ChatColor.RED;
        }
        else if (level >= 4) {
            chatColor = ChatColor.DARK_PURPLE;
        }
        else if (level >= 3) {
            chatColor = ChatColor.GREEN;
        }
        else if (level >= 2) {
            chatColor = ChatColor.AQUA;
        }
        else if (level >= 1) {
            chatColor = ChatColor.GOLD;
        }
        return chatColor;
    }

    public void register(Player player, String[] args) {
        if (args == null || args.length < 2) {
            player.sendMessage(ChatColor.RED + "[!] 이 명령어는 '/register [닉네임] [직업] [0~5]' 이렇게 써주세요!");
        } else {
            String nickname = args[0];
            String profession = args[1];
            int level = 0;
            if (args.length >= 3) {
                level = Util.parseInt(args[2]);
            }
            main.getConfig().set("profession." + nickname, profession);
            main.getConfig().set("level." + nickname, level);
            player.setPlayerListName("[" + getProfessionColor(level) +  profession + ChatColor.WHITE + "] " + player.getName());
            player.sendMessage(ChatColor.GREEN + "[!] " + nickname + "(은)는 " + profession + "입니다. (설정레벨: " + level + ")");
            main.saveConfig();
        }
    }

    public String getOfflinePlayerList() {
        String message = "(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getWhitelistedPlayers().size() + ")\n";
        for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            if (!player.isOnline()) {
                message += getPlayerListText(player);
            }
        }
        return message;
    }

    public void chat(Player player, String message) {
        String chat = player.getName();
        String profession = main.getConfig().getString("profession." + player.getName());
        int level = main.getConfig().getInt("level." + player.getName());
        if (profession != null) {
            chat = "[" + getProfessionColor(level) +  profession + ChatColor.WHITE + "] " + player.getName();
        }
        chat += ": " + message;
        Bukkit.broadcastMessage(chat);
    }

    private String getPlayerListText(OfflinePlayer player) {
        String profession = main.getConfig().getString("profession." + player.getName());
        int level = main.getConfig().getInt("level." + player.getName());
        String message = "";
        if (Util.isEmpty(profession)) {
            message += ChatColor.GRAY + "[직업없음] ";
        } else {
            message += getProfessionColor(level) + "[" + profession + "] ";
        }
        message += ChatColor.WHITE + player.getName();
        if (player.isOnline()) {
            message += ChatColor.GREEN + "" + ChatColor.ITALIC + " (온라인)";
        } else {
            message += ChatColor.RED + "" + ChatColor.ITALIC + " (오프라인)";
        }
        return message + "\n";
    }
}
