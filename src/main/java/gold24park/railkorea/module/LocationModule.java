package gold24park.railkorea.module;

import gold24park.railkorea.util.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationModule {

    private Plugin main;
    private static LocationModule instance;
    private final static String MAP_BOOK_TITLE = "공용 좌표책";
    private final static String MAP_BOOK_AUTHOR = "Rail";
    private HashMap<Biome, String> allowedBiomes = new HashMap<>();

    public static LocationModule getInstance(Plugin main) {
        if (instance == null)
            instance = new LocationModule(main);
        return instance;
    }

    private LocationModule(Plugin main) {
        this.main = main;
        allowedBiomes.put(Biome.JUNGLE, "정글");
        allowedBiomes.put(Biome.BAMBOO_JUNGLE, "대나무 정글");
        allowedBiomes.put(Biome.MUSHROOM_FIELDS, "버섯 들판");
        allowedBiomes.put(Biome.SAVANNA, "사바나");
        allowedBiomes.put(Biome.DESERT, "사막");
        allowedBiomes.put(Biome.TAIGA, "타이가");
        allowedBiomes.put(Biome.FROZEN_OCEAN, "얼어붙은 바다");
        allowedBiomes.put(Biome.WARM_OCEAN, "따뜻한 바다");
        allowedBiomes.put(Biome.SWAMP, "늪");
        allowedBiomes.put(Biome.BADLANDS, "악지 지형");
        allowedBiomes.put(Biome.FLOWER_FOREST, "꽃 숲");
        allowedBiomes.put(Biome.BIRCH_FOREST, "자작나무 숲");
        allowedBiomes.put(Biome.DARK_FOREST, "어두운 숲");
        allowedBiomes.put(Biome.SNOWY_TUNDRA, "눈 덮인 툰드라");
        allowedBiomes.put(Biome.ICE_SPIKES, "역고드름");
    }

    public void run() {
        // tick 마다 좌표 표시
        int tick = 10;
        Bukkit.getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    showLocationHud(player);
                }
            }
        }, 0L, tick);
    }

    public void setHudVisibility(Player player, String[] args) {
        if (args != null && args.length > 0) {
            if (args[0].equalsIgnoreCase("on")) {
                main.getConfig().set("hide_hud." + player.getName(), 0);
                player.sendMessage(ChatColor.DARK_GRAY + "[!] HUD를 띄웁니다.");
            }
            else if (args[0].equalsIgnoreCase("off")) {
                main.getConfig().set("hide_hud." + player.getName(), 1);
                player.sendMessage(ChatColor.DARK_GRAY + "[!] HUD가 숨겨졌습니다.");
            }
            else {
                player.sendMessage(ChatColor.RED + "[!] /hud on 또는 off를 입력 해 주시오.");
            }
            main.saveConfig();
        }
    }

    private void showLocationHud(Player player) {
        boolean hideHud = main.getConfig().getInt("hide_hud." + player.getName()) == 1;
        if (!hideHud) {
            Location location = player.getLocation();
            String destMessage = "";
            int x = main.getConfig().getInt("destination." + player.getName() + ".x");
            int z = main.getConfig().getInt("destination." + player.getName() + ".z");
            if (x != 0 || z != 0) {
                destMessage = ChatColor.GOLD + "[목적지] " + ChatColor.GRAY + Util.getLocationText(x, z) + " ";
            }
            String message = destMessage + ChatColor.GRAY + Util.getLocationText(location);
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(message)
            );
        }
    }

    public void openMapGUI(Player player) {
        Inventory gui = Bukkit.getServer().createInventory(player, 9, "좌표를 기록하거나 기록된 좌표를 보세요.");
        ItemStack book = new ItemStack(Material.WRITABLE_BOOK);

        BookMeta bookMeta = (BookMeta) book.getItemMeta();

        List<String> pages = main.getConfig().getStringList("map_book_meta");
        bookMeta.setPages(pages);
        bookMeta.setDisplayName(MAP_BOOK_TITLE);
        bookMeta.setTitle(MAP_BOOK_TITLE);
        bookMeta.setAuthor(MAP_BOOK_AUTHOR);
        bookMeta.setGeneration(BookMeta.Generation.TATTERED); // Unused; unobtainable by players.

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("내용을 기록한 후 완료를 누르면 정보가 공유됩니다.");
        bookMeta.setLore(lore);
        book.setItemMeta(bookMeta);

        gui.addItem(book);

        player.openInventory(gui);
    }

    public void openMap(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.WRITABLE_BOOK) {
            BookMeta bookMeta = (BookMeta) item.getItemMeta();
            if (isMapBook(bookMeta)) {
                List<String> pages = main.getConfig().getStringList("map_book_meta");
                bookMeta.setPages(pages);
                item.setItemMeta(bookMeta);
            }
        }
    }

    private boolean isMapBook(BookMeta bookMeta) {
        return bookMeta != null && bookMeta.getGeneration() == BookMeta.Generation.TATTERED &&
                bookMeta.getTitle().equalsIgnoreCase(MAP_BOOK_TITLE) &&
                bookMeta.getDisplayName().equalsIgnoreCase(MAP_BOOK_TITLE);
    }

    public void saveMap(PlayerEditBookEvent event) {
        BookMeta newBookMeta = event.getNewBookMeta();
        if (isMapBook(newBookMeta)) {
            // 지도 수정 책이라면 저장
            main.getConfig().set("map_book_meta", newBookMeta.getPages());
            event.getPlayer().sendMessage(ChatColor.GREEN + "[!] 등록하신 좌표가 공유되었습니다.");
        }
    }

    public void saveMap(PlayerTakeLecternBookEvent event) {
        BookMeta newBookMeta = (BookMeta) event.getBook().getItemMeta();
        if (isMapBook(newBookMeta)) {
            // 지도 수정 책이라면 저장
            main.getConfig().set("map_book_meta", newBookMeta.getPages());
            event.getPlayer().sendMessage(ChatColor.GREEN + "[!] 등록하신 좌표가 공유되었습니다.");
        }
    }


    // 바이옴 최초 발견 체크
    public void checkBiome(Player player) {
        Biome biome = player.getWorld().getBiome(
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ()
        );
        if (allowedBiomes.containsKey(biome)) {
            final String xKey = "found_biomes." + biome.name() + ".x";
            final String zKey = "found_biomes." + biome.name() + ".z";
            int x = main.getConfig().getInt(xKey);
            int z = main.getConfig().getInt(zKey);
            if (x == 0 && z == 0) {
                // 아직 아무도 가지 않은 땅일때
                x = player.getLocation().getBlockX();
                z = player.getLocation().getBlockZ();
                String message = ChatColor.AQUA + "■ " + player.getName() + "님이 " +
                        allowedBiomes.get(biome) +
                        " 바이옴을 발견하였습니다. " +
                        Util.getLocationText(x, player.getLocation().getBlockY(), z);

                main.getConfig().set(xKey, x);
                main.getConfig().set(zKey, z);
                    
                autoRecord(allowedBiomes.get(biome), x, z);
                
                Bukkit.broadcastMessage(message);
                Bukkit.broadcastMessage(ChatColor.AQUA + "해당 좌표가 자동기록되었습니다. map 커맨드로 확인하세요 :)");

                main.saveConfig();
            }
        }
    }
    
    // 책에 좌표 자동 기록
    private void autoRecord(String name, int x, int z) {
        List<String> pages = main.getConfig().getStringList("map_book_meta");
        String page = pages.get(pages.size() - 1);
        int lines = page.split("\n").length + 1;
        final int maxLines = 13; // 한 페이지에 쓸 수있는 최대 행
        String bookMessage = name + ": " + x + " " + z;
        if (lines < maxLines) {
            page += "\n" + bookMessage;
            pages.set(pages.size() - 1, page);
        } else {
            pages.add(bookMessage);
        }
        main.getConfig().set("map_book_meta", pages);
    }
}
