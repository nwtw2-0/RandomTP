package mc.yuziouo.randomTP;

import cn.nukkit.Player;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.Task;

public class DelayTask extends Task {
    private int totalTick;
    private final Vector3 dest;
    private final Player player;
    public DelayTask(Player player,Vector3 dest){
        this.player = player;
        this.dest = dest;
        totalTick = 0;
    }
    @Override
    public void onRun(int i) {
        player.getLevel().addSound(player.getLocation(), Sound.RANDOM_FUSE);
        player.addEffect(Effect.getEffect(Effect.BLINDNESS));
        player.addEffect(Effect.getEffect(Effect.SLOWNESS));
        player.sendTitle("即將在"+(RandomTP.getRandomTP().getConfig().getInt("Delay.cd")-totalTick)+"秒後傳送");
        if (totalTick == RandomTP.getRandomTP().getConfig().getInt("Delay.cd")){
            player.teleport(dest);
            while (!player.getLocation().equals(dest));
            player.getLevel().addSound(player.getLocation(), Sound.RANDOM_EXPLODE);
            this.cancel();
        }
        totalTick++;
    }
}
