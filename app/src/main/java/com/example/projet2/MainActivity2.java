package com.example.projet2;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity2 extends AppCompatActivity {
    RelativeLayout linearLayout;
    Button btn5;
    private EditText display;
    private StringBuilder input = new StringBuilder();
    private double previousResult = 0;  // Variable pour stocker le dernier résultat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        display = findViewById(R.id.display);

        // Boutons pour les chiffres et opérations
        setButtonListener(R.id.button0);
        setButtonListener(R.id.button1);
        setButtonListener(R.id.buttonAc);
        setButtonListener(R.id.button7);
        setButtonListener(R.id.button8);
        setButtonListener(R.id.divBtn);
        setButtonListener(R.id.buttonNeuf);
        setButtonListener(R.id.buttonn);
        setButtonListener(R.id.buttonHuit);
        setButtonListener(R.id.multiBtn);
        setButtonListener(R.id.plusBtn);
        setButtonListener(R.id.button5);
        setButtonListener(R.id.buttonPen);
        setButtonListener(R.id.buttonnn);
        setButtonListener(R.id.virguleBtn);
        setButtonListener(R.id.buttonBtn);
        setButtonListener(R.id.buttonAC);
        setButtonListener(R.id.moinsBtn);
        setButtonListener(R.id.multiBtn);
        setButtonListener(R.id.divBtn);

        // Bouton de suppression
        findViewById(R.id.buttonAc).setOnClickListener(v -> {
            input.setLength(0); // Réinitialise l'entrée
            display.setText("");
        });

        // Bouton égal pour effectuer le calcul
        findViewById(R.id.equalBtn).setOnClickListener(v -> {
            try {
                double result = calculate(input.toString());
                display.setText(String.valueOf(result));
                input.setLength(0); // Réinitialise l'entrée après le calcul
                previousResult = result; // Mise à jour du dernier résultat
            } catch (Exception e) {
                Toast.makeText(MainActivity2.this, "Invalid Expression", Toast.LENGTH_SHORT).show();
            }
        });



    }
    // Méthode pour ajouter un écouteur aux boutons
    private void setButtonListener(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            input.append(button.getText().toString());
            display.setText(input.toString());
        });
    }

    // Méthode pour effectuer le calcul simple
    private double calculate(String expression) {
        double result = 0;
        char lastOperator = '+';
        double currentNumber = 0;
        double intermediateResult = 0; // Pour gérer les multiplications et divisions en priorité

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            // Construire le nombre courant
            if (Character.isDigit(currentChar)) {
                currentNumber = currentNumber * 10 + (currentChar - '0');
            }

            // Vérifier les opérateurs ou la fin de l'expression
            if (!Character.isDigit(currentChar) && currentChar != ' ' || i == expression.length() - 1) {
                switch (lastOperator) {
                    case '+':
                        result += intermediateResult;
                        intermediateResult = currentNumber; // Préparer le nombre pour la prochaine opération
                        break;
                    case '-':
                        result += intermediateResult;
                        intermediateResult = -currentNumber; // Soustraction
                        break;
                    case '*':
                        intermediateResult *= currentNumber;
                        break;
                    case '/':
                        if (currentNumber != 0) {
                            intermediateResult /= currentNumber;
                        } else {
                            throw new ArithmeticException("Cannot divide by zero");
                        }
                        break;
                    case '%':
                        intermediateResult %= currentNumber;
                        break;
                }
                // Mettre à jour pour le prochain cycle
                lastOperator = currentChar;
                currentNumber = 0;
            }
        }

        // Ajouter le dernier résultat intermédiaire
        result += intermediateResult;

        return result;
    }

}