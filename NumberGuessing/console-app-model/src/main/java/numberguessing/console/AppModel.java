package numberguessing.console;

import numberguessing.PositiveIntegerGenerator;

import java.util.Arrays;

public final class AppModel {
    private final static String NEW_LINE = System.lineSeparator();
    private final PositiveIntegerGenerator generator;
    private static final String SELECT_MODE_MESSAGE = "1: Single player game" + NEW_LINE + "2: Multiplayer game" + NEW_LINE + "3: Exit" + NEW_LINE + "Enter selection: ";
    private boolean completed;
    private StringBuffer outputBuffer;
    private Processor processor;
    
    @FunctionalInterface
    interface Processor{
        Processor run(String input);
    }

    public AppModel(PositiveIntegerGenerator generator) {
        this.generator = generator;
        completed = false;
        processor = this::processModeSelection;
        outputBuffer = new StringBuffer(SELECT_MODE_MESSAGE);
    }

    public boolean isCompleted() {
        return completed;
    }

    public String flushOutput() {
        String message = outputBuffer.toString();
        outputBuffer.setLength(0);
        return message;
    }

    public void processInput(String input) {
        processor = processor.run(input);
    }

    private Processor processModeSelection(String input){
        if(input.equals("1")){
            int answer = generator.generateLessThanOrEqualToHundread();
            outputBuffer.append("Single player game" + NEW_LINE + "I'm thinking of a number between 1 and 100."+ NEW_LINE + "Enter your guess: ");
            return getSinglePlayerGameProcessor(answer, 1);
        }
        else if(input.equals("2")){
            outputBuffer.append("Multiplayer game" + NEW_LINE + "Enter player names separated with commas: ");
            return startMultiPlayerGamerProcessor();
        }
        else{
            completed = true;
            return null;
        }
       
    }

    private Processor startMultiPlayerGamerProcessor() {
        return input -> {
            Object[] players =  Arrays.stream(input.split(",")).map(String::trim).toArray();
            outputBuffer.append("I'm thinking of a number between 1 and 100.");
            int answer = generator.generateLessThanOrEqualToHundread();
            return getMultiplayerGameProcessor(answer, players,1);
        };
    }

    private Processor getMultiplayerGameProcessor(int answer, Object[] players, int tries) {
        Object player = players[(tries - 1)%players.length];
        outputBuffer.append(NEW_LINE + "Enter " + player + "'s guess: ");
        return input -> {
            if(Integer.parseInt(input) < answer){
                outputBuffer.append(players[(tries - 1)%players.length] + "'s guess is too low." + NEW_LINE);
            }
            else if(Integer.parseInt(input) > answer){
                outputBuffer.append(players[(tries - 1)%players.length] + "'s guess is too high." + NEW_LINE);
            }
            else{
                outputBuffer.append("Correct! "+player+ " wins." + NEW_LINE+SELECT_MODE_MESSAGE);
                return this::processModeSelection;
            }
            
            return getMultiplayerGameProcessor(answer, players, tries + 1);
        };
    }


    private Processor getSinglePlayerGameProcessor(int answer, int tries) {
        return input -> {
            if(Integer.parseInt(input) < answer){

                outputBuffer.append("Your guess is too low." + NEW_LINE + "Enter your guess: ");
                return getSinglePlayerGameProcessor(answer, tries +1);
            }
            else if(Integer.parseInt(input) > answer){
                outputBuffer.append("Your guess is too high." + NEW_LINE + "Enter your guess: ");
                return getSinglePlayerGameProcessor(answer, tries +1);
            }
            else{
                outputBuffer.append("Correct! "+tries+(tries == 1 ? " guess.":" guesses.") + NEW_LINE+SELECT_MODE_MESSAGE);
                return this::processModeSelection;
            }
        };
    }

}
