package com.alexpersaud.tictactoe;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.alexpersaud.tictactoe.R.id.winnerMessage;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = false;

    // 0 = blue player, 1 = red player
    int activePlayer = 0;


    // 2 = unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    public void startGame(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.welcomeLayout);
        layout.setVisibility(View.INVISIBLE);
        ImageView playerTurn = (ImageView) findViewById(R.id.playerTurn);
        playerTurn.setImageResource(R.drawable.blue_chip);
        gameIsActive = true;
        activePlayer = 0;

    }

    public void goHome(View view){
        LinearLayout layout = (LinearLayout) findViewById(R.id.welcomeLayout);
        layout.setVisibility(View.VISIBLE);
        gameIsActive = false;

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gameBoardLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    public void dropIn(View view){

        ImageView counter = (ImageView) view;
        ImageView playerTurn = (ImageView) findViewById(R.id.playerTurn);

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive){
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if(activePlayer == 0){
                playerTurn.setImageResource(R.drawable.red_chip);
                counter.setImageResource(R.drawable.blue_chip);
                activePlayer = 1;
            } else{
                playerTurn.setImageResource(R.drawable.blue_chip);
                counter.setImageResource(R.drawable.red_chip);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).setDuration(350);

            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    // Winner!
                    gameIsActive = false;
                    String winner;
                    if(gameState[winningPosition[0]] == 0){
                        winner = "Blue";
                    }else{
                        winner = "Red";
                    }
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);

                } else {
                    boolean gameIsOver = true;
                    for(int counterState : gameState){
                        if(counterState == 2) gameIsOver = false;
                    }
                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        ImageView playerTurn = (ImageView) findViewById(R.id.playerTurn);
        playerTurn.setImageResource(R.drawable.blue_chip);

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gameBoardLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
;        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
