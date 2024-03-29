package network;

import util.Digit;
import util.Score;

import java.util.Arrays;

public class NeuralNetwork {

    private InputNeuron[] inputNeurons;
    private OutputNeuron[] outputNeurons;

    /**
     * Generates the input- and outputLayer and connects them
     *
     * @param inputCount         amount of inputNeurons, e.g. amount of pixels in an image
     * @param outputCount        amount of inputNeurons, e.g. amount of different digits
     * @param activationFunction the activationsFunction which will be used to activate the outputNeuron's value
     */
    public NeuralNetwork(int inputCount, int outputCount, ActivationFunction activationFunction) {
        createInputLayer(inputCount);
        createOutputLayer(outputCount, activationFunction);
        createFullMesh();
    }

    /**
     * Initializes the input layer by filling the array with InputNeurons
     *
     * @param amount of input neurons, e.g. amount of pixels in an image
     */
    private void createInputLayer(int amount) {
        // TODO 1
        inputNeurons = new InputNeuron[amount];
        for (int i = 0; i < inputNeurons.length; i++) {
            inputNeurons[i] = new InputNeuron();
        }
    }

    /**
     * Initializes the output layer by filling the array with OutputNeurons
     *
     * @param amount             of output neurons, e.g. amount of different digits
     * @param activationFunction which modifies the sum, each OutputNeuron needs an activationFunction
     */
    private void createOutputLayer(int amount, ActivationFunction activationFunction) {
        // TODO 1
        outputNeurons = new OutputNeuron[amount];
        for (int i = 0; i < outputNeurons.length; i++) {
            outputNeurons[i] = new OutputNeuron(activationFunction);
        }
    }

    /**
     * Creates a Connection with a random weight between 0 and 1 for each InputNeuron OutputNeuron pair
     */
    private void createFullMesh() {
        // TODO 1
        for (int i = 0; i < inputNeurons.length; i++) {
            for (int j = 0; j < outputNeurons.length; j++) {
                outputNeurons[j].addConnection(new Connection(inputNeurons[i], Math.random()));
            }
        }
    }


    /**
     * Sets the image data on every inputNeuron, do not forget to check if there are enough inputNeurons
     *
     * @param data an image
     */
    private void setInput(double[][] data) {
        // TODO 2
        int counter = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                inputNeurons[counter++].setValue(data[i][j]);
            }
        }
    }


    /**
     * Takes a digit as input and adjusts the connection weights, using the Delta-Learning-Rule,
     * use one-hot-encoding for the MNIST-dataset => 10 outputNeurons, where values are either 1 or 0
     *
     * @param data    a grayscale image, for the input neurons
     * @param label   the label, train the corresponding output neuron with 1 and the rest with 0
     * @param epsilon the learning rate
     */
    public void train(double[][] data, int label, double epsilon) {
        // TODO 2
        setInput(data);
        for (int i = 0; i < outputNeurons.length; i++) {
            if (i == label) {
                outputNeurons[i].learn(1.0, epsilon);
            } else {
                outputNeurons[i].learn(0.0, epsilon);
            }
        }
    }

    /**
     * Guesses your drawing, take each outputNeurons value and use the comparability of Score,
     * to return the result in descending order
     *
     * @param data a grayscale image, for the input neurons
     * @return scores an ordered list, starting with the digit with the highest probability
     */
    public Score[] test(double[][] data) {
        // TODO 2
        setInput(data);
        Score[] scores = new Score[outputNeurons.length];
        for (int i = 0; i < outputNeurons.length; i++) {
            double likeliness = outputNeurons[i].getValue();
            scores[i] = new Score(i, likeliness);
        }
        Arrays.sort(scores);
        return scores;
    }

    public InputNeuron[] getInputNeurons() {
        return inputNeurons;
    }

    public OutputNeuron[] getOutputNeurons() {
        return outputNeurons;
    }
}
