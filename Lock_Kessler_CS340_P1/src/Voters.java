import java.util.Random;


public class Voters extends Thread {
    public static long time = System.currentTimeMillis();
    private int assignedKiosk;
    private int scannerMachine;
    private boolean idChecked;

    // Default constructor
    public Voters(int id) {
        setName("Voter_" + (id + 1));
        this.assignedKiosk = -1;
        this.scannerMachine = -1;
        this.idChecked = false;
    }

    @Override
    public void run() {
        Random rand = new Random();
        msg("Arrived at the designated voting place");
        try {
            sleep(rand.nextInt(2000) + 500);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        //Voters' ID need to be checked by an ID checker.
        ID_checkers.checkIdLine.add(this);

        while (!idChecked){
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        msg("Checked by an IDChecker. ");

        //KioskHelper will control all this movement.
        msg("Entering the voting kiosk line..");
        Kiosk_helper.votingKioskLine.add(this); //add voter to the line/queue
        while (true) {
            if(interrupted()) { //when voter is interrupted
                msg("Has been called by the Kiosk Helper, goes to vote now");
                break;
            }
        }

        while (this.assignedKiosk == -1) {
            try{
                Thread.sleep(50);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            msg("Got in front of the line and is entering their information at kiosk " + assignedKiosk);
            sleep(rand.nextInt(4000) + 1000);
            msg("Done vote. letting helper know...");
            Kiosk_helper.doneVote(assignedKiosk); //done ballot
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        //Finally, last step.....
        this.setPriority(getPriority()+1);
        try {
            msg("Rush to the room with scanning machines.");
            sleep(rand.nextInt(1000)+500);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.setPriority(5);


       //Next voters will busy_wait for the scanning helper to allow them to use the scanning machines
        msg("Waiting the scanning helper to allow them to use the scanning machines..");
        Scanning_helper.scanningLine.add(this);

        while (this.scannerMachine == -1) {
            try{
                Thread.sleep(50);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        //However, the voters get nervous and slowdown a bit. Each of them will yield (implement this by
        //using yield() two times). Finally, they will scan their ballot (simulate by sleep of random time)
        //and leave the scanning room.
        try {
            msg("Nervous...");
            Thread.yield();
            Thread.yield();
            msg("Scanning their ballot with scanning machine " + this.scannerMachine);
            sleep(rand.nextInt(1500)+500);
            Scanning_helper.doneScan(this.scannerMachine);
            msg("Finish scanning the ballot.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        msg("Leaving scanning room..");


        msg("Leaving the building with a very patriotic feeling. ");
        ID_checkers.votersForChecker--;
        Kiosk_helper.votersForKiosk--;
        Scanning_helper.votersForScanner--;
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+ m);
    }

   public void useScannerMachine(int scannerMachine){
        this.scannerMachine = scannerMachine;
   }

    public void enterKiosk(int kiosk){
        this.assignedKiosk = kiosk;
    }

    public void idChecked(boolean idChecked){
      this.idChecked = idChecked;
    }

}
