import java.util.LinkedList;
import java.util.Queue;

public class Kiosk_helper extends Thread {
    public static long time = System.currentTimeMillis();
    public static Queue<Voters> votingKioskLine = new LinkedList<>();
    public static Queue<Integer> kiosks;
    public static int votersForKiosk;

    public Kiosk_helper(int numberOfKiosk, int numberOfVoters) {
        setName("KioskHelper");
        kiosks = new LinkedList<>();
        for(int i = 1; i <= numberOfKiosk; i++){
            kiosks.add(i);
        }
        Kiosk_helper.votersForKiosk = numberOfVoters;
    }


    @Override
    public void run() {
        while (votersForKiosk != 0) {
            if (!kiosks.isEmpty() && !votingKioskLine.isEmpty()) { //if there is available kiosk and there is still voters.
                Voters voter = votingKioskLine.remove();
                msg("Picking the kiosk with shortest line...");
                msg("Calls " + voter.getName());
                voter.interrupt();
                voter.enterKiosk(kiosks.remove()); //voter assigned to the available kiosk.
            }
            try {
                Thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
       msg("No more voters, going home..");
  }


    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }

    public static void doneVote(int kiosk){
        //when voter done, add back the available kiosk.
        kiosks.add(kiosk);
    }


}
