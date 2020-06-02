package cz.cvut.fel.bouredan.chess.common;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class ChessClock {

    private Timer timer;
    private final Map<Boolean, Long> remainingSecondsMap = new ConcurrentHashMap<>();
    private boolean isWhitePlayerOnTurn;
    private boolean isGameRunning;

    public ChessClock(long whitePlayerSeconds, long blackPlayerSeconds, boolean isWhitePlayerOnTurn) {
        this.timer = new Timer();
        this.remainingSecondsMap.put(true, whitePlayerSeconds);
        this.remainingSecondsMap.put(false, blackPlayerSeconds);
        this.isWhitePlayerOnTurn = isWhitePlayerOnTurn;
        this.isGameRunning = false;
    }

    public void startClock() {
        if (isGameRunning) {
            throw new IllegalStateException("Clock is already started.");
        }
        isGameRunning = true;
        startClockOfPlayer(isWhitePlayerOnTurn);
    }

    public void switchClock() {
        timer.cancel();
        isWhitePlayerOnTurn = !isWhitePlayerOnTurn;
        startClockOfPlayer(isWhitePlayerOnTurn);
    }

    public void pauseClock() {
        isGameRunning = false;
        timer.cancel();
    }

    private void startClockOfPlayer(boolean isWhitePlayer) {
        timer = new Timer();
        timer.schedule(new PlayerClockTick(isWhitePlayer), 0, 1000);
    }

    private class PlayerClockTick extends TimerTask {

        private final boolean isWhitePlayer;

        public PlayerClockTick(boolean isWhitePlayer) {
            this.isWhitePlayer = isWhitePlayer;
        }

        @Override
        public void run() {
            long remainingSeconds = remainingSecondsMap.get(isWhitePlayer);
            if (remainingSeconds <= 0) {
                System.out.println("Time has run out!");
                cancel();
                return;
            }
            System.out.println((isWhitePlayer ? "White" : "Black") + " player has " + remainingSeconds + " remainingSeconds left.");
            remainingSecondsMap.put(isWhitePlayer, remainingSeconds - 1);
        }
    }
}
