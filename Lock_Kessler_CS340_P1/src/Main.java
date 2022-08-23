import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final int num_voters = 20;
    private static final  int num_ID_checkers = 3;
    private static final int num_k = 3; //3 kiosk
    private static final int num_sm = 4; //4 scanning machines


    public static void main(String[] args) {
         Voters[] voters = new Voters[num_voters];
         ID_checkers[] idCheckers = new ID_checkers[num_ID_checkers];
         Kiosk_helper kioskHelper = new Kiosk_helper(num_k, num_voters);
         Scanning_helper scanningHelper = new Scanning_helper(num_sm, num_voters);
         AtomicBoolean mutex = new AtomicBoolean(false);

        for(int i = 0; i < num_voters; i++){
            voters[i] = new Voters(i);
        }
        for(Voters voter: voters){
          voter.start();
        }

        for(int i = 0; i < num_ID_checkers; i++){
            idCheckers[i] = new ID_checkers(i, num_voters, mutex);
        }


        for(ID_checkers idChecker: idCheckers){
            idChecker.start();
        }

        kioskHelper.start();
        scanningHelper.start();

    }


}
