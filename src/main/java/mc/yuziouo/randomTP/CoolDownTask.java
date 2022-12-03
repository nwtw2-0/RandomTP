package mc.yuziouo.randomTP;

import cn.nukkit.scheduler.NukkitRunnable;

import java.util.HashMap;


public class CoolDownTask extends NukkitRunnable {
    @Override
    public void run() {
        if (RandomTP.getCoolDownMap().isEmpty()) return;
        HashMap<String,Integer> clone = new HashMap<>(RandomTP.getCoolDownMap());
        for(String keys : RandomTP.getCoolDownMap().keySet()){
            int val = RandomTP.getCoolDownMap().get(keys)-1;
            if(val >0){
                clone.replace(keys,val);
            }else {
                clone.remove(keys);
            }
        }
        RandomTP.setCoolDownMap(clone);
    }
}
