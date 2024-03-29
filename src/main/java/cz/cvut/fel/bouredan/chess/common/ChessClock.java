package cz.cvut.fel.bouredan.chess.common;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * Chess clock provides timing for player turns. It notifies the updateConsumer every second about time left.
 */
public class ChessClock {

    private static final Logger logger = Logger.getLogger(ChessClock.class.getName());

    private Timer timer;
    private final BiConsumer<Boolean, Long> updateConsumer;
    private final Map<Boolean, Long> remainingSecondsMap = new ConcurrentHashMap<>();
    private boolean isWhitePlayerOnTurn;
    private boolean isGameRunning;

    /**
     * @param whitePlayerSeconds time left for white player
     * @param blackPlayerSeconds time left for black player
     * @param isWhitePlayerOnTurn starting player
     * @param updateConsumer ChessClock notifies this consumer every second about time left
     */
    public ChessClock(long whitePlayerSeconds, long blackPlayerSeconds, boolean isWhitePlayerOnTurn, BiConsumer<Boolean, Long> updateConsumer) {
        this.timer = new Timer(true);
        this.updateConsumer = updateConsumer;
        this.remainingSecondsMap.put(true, whitePlayerSeconds);
        this.remainingSecondsMap.put(false, blackPlayerSeconds);
        this.isWhitePlayerOnTurn = isWhitePlayerOnTurn;
        this.isGameRunning = false;
    }

    /**
     * Starts counting down the clock of player on turn.
     *
     * @throws IllegalStateException when clock is already started
     */
    public void startClock() {
        if (isGameRunning) {
            throw new IllegalStateException("Clock is already started.");
        }
        isGameRunning = true;
        startClockOfPlayer(isWhitePlayerOnTurn);
    }

    /**
     * Switches player on turn, so it will start counting down the new player on turn.
     */
    public void switchClock() {
        timer.cancel();
        isWhitePlayerOnTurn = !isWhitePlayerOnTurn;
        startClockOfPlayer(isWhitePlayerOnTurn);
    }

    /**
     * Pauses clock
     */
    public void pauseClock() {
        isGameRunning = false;
        timer.cancel();
    }

    /**
     * Ends the game, purges all tasks (threads)
     */
    public void endGame() {
        pauseClock();
        timer.purge();
    }

    private void startClockOfPlayer(boolean isWhitePlayer) {
        timer = new Timer(true);
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
            updateConsumer.accept(isWhitePlayer, remainingSeconds);
            if (remainingSeconds <= 0) {
                logger.info(Utils.getPlayerSideName(isWhitePlayer) + " has run out of time!");
                return;
            }
            logger.finer(Utils.getPlayerSideName(isWhitePlayer) + " player has " + remainingSeconds + "s remaining left.");
            remainingSecondsMap.put(isWhitePlayer, remainingSeconds - 1);
        }
    }
}
