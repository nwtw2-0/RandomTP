package mc.yuziouo.randomTP;

import cn.nukkit.plugin.PluginBase;

import java.util.HashMap;
import java.util.Random;

public class RandomTP extends PluginBase {
    private static HashMap<String,Integer> coolDown;
    private static RandomTP randomTP;
    private CoolDownTask task;
    private Random random;
    @Override
    public void onEnable() {
        randomTP = this;
        random = new Random();
        saveDefaultConfig();
        coolDown = new HashMap<>();
        task = new CoolDownTask();
        task.runTaskTimer(this,0,20);
        getServer().getCommandMap().register("rtp",new RTPCommand("rtp"));
        getLogger().info("RandomTP已開啟");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(task.getTaskId());
        coolDown = null;
        task = null;
        getLogger().info("RandomTP已關閉");
        super.onDisable();
    }

    public static RandomTP getRandomTP() {
        return randomTP;
    }

    public static HashMap<String, Integer> getCoolDownMap() {
        return coolDown;
    }

    public static void setCoolDownMap(HashMap<String, Integer> coolDown) {
        RandomTP.coolDown = coolDown;
    }

    public static int getCoolDown(String player){
        return coolDown.getOrDefault(player, 0);
    }
    public static void setCoolDown(String player,int cd){
        coolDown.replace(player,cd);
    }

    public Random getRandom() {
        return random;
    }
}
