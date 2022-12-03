package mc.yuziouo.randomTP;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;


public class RTPCommand extends Command {
    public RTPCommand(String name) {
        super(name,"隨機傳送指令");
        setPermission("RandomTP.rtp");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!this.testPermission(commandSender)) return false;
        if (commandSender.isPlayer()){
            Player player = commandSender.asPlayer();
            if (RandomTP.getCoolDown(player.getName())<=0){
                String wordName = player.getLevelName();
                if (!RandomTP.getRandomTP().getConfig().getStringList("RandomTP.disableWorld").contains(wordName)){
                    Vector3 vector3 = RandomTP(player.getLevel());
                    if(!player.getLevel().isChunkLoaded((int)vector3.x>>4,(int)vector3.z>>4))
                        player.getLevel().loadChunk((int)vector3.x>>4,(int)vector3.z>>4);
                    if(RandomTP.getRandomTP().getConfig().getBoolean("Delay.enable")){
                        RandomTP.getRandomTP().getServer().getScheduler().scheduleRepeatingTask(RandomTP.getRandomTP(),new DelayTask(player,vector3),20);
                    }else {
                        player.teleport(vector3);
                    }
                    if(RandomTP.getRandomTP().getConfig().getBoolean("CoolDown.enable")){
                        RandomTP.getCoolDownMap().put(player.getName(),RandomTP.getRandomTP().getConfig().getInt("CoolDown.cd"));
                    }
                    player.sendMessage(TextFormat.GREEN+"傳送完成!");
                }else {
                    player.sendMessage(TextFormat.RED+"你在的世界並沒有被允許使用隨機傳送");
                }
            }else {
                commandSender.sendMessage(TextFormat.RED+"你的隨機傳送還在冷卻中 需要再"+RandomTP.getCoolDown(player.getName())+"秒後才能使用");
            }
        }
        return true;
    }
    public static Vector3 RandomTP(Level level){
        int x = Math.abs(RandomTP.getRandomTP().getConfig().getInt("RandomTP.limit.x"))*-1 + RandomTP.getRandomTP().getRandom().nextInt(Math.abs(RandomTP.getRandomTP().getConfig().getInt("RandomTP.limit.x"))*2);
        int z = Math.abs(RandomTP.getRandomTP().getConfig().getInt("RandomTP.limit.z"))*-1+ RandomTP.getRandomTP().getRandom().nextInt(Math.abs(RandomTP.getRandomTP().getConfig().getInt("RandomTP.limit.z"))*2);
        int y =  level.getDimension() == Level.DIMENSION_OVERWORLD? 317:125;
        int lmy = level.getDimension() == Level.DIMENSION_OVERWORLD? -64:0;
        boolean find = false;
        while (!find) {
            while (y >= lmy && !level.getBlock(x, y, z).isSolid()) {
                y--;
            }
            if(level.getBlock(x,y+1,z).getId() != BlockID.AIR||level.getBlock(x,y+2,z).getId() != BlockID.AIR){
                y--;
            }else{
                find = true;
            }
            if (y < lmy) return RandomTP(level);
        }
        return new Vector3(x,y+1,z);
    }

}
