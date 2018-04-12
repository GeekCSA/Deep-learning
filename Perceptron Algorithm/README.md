# Deep-learning

A parasitron is a single neuron that is constructed as follows:

# ![diagram](https://github.com/GeekCSA/Deep-learning/blob/master/Perceptron%20Algorithm/Single-Perceptron.png?raw=true "Perceptron")
###### from https://pythonmachinelearning.pro/perceptrons-the-first-neural-networks/

1. The input is bits of '0' or '1'
2. Each input bit has a number that represents the weight of the input on the same bit.
3. In sum, we sum the product W_i * X_i when 1 <= i <= n. (equals to z)
4. In activation function, if the above amount has passed a certain threshold then another output '1' will be output '0'.

If we passed the threshold then the input should be received otherwise it should not be accepted. In order for the neuron to produce only 1 on the inputs to be obtained, the weights W_1 to W_n should be in the correct values.

When we have infinite input space, we can not find the correct W values ​​by equations, but we will need to "train" the neuron on inputs that we already know whether they are being received or not, and then the neuron will correct its W values.

In the files in this folder, there is the realization of one code that trains the neuron to find the most correct weights (W) given a set of inputs.

The code receives a folder of files containing vectors of inputs that represent letters in Hebrew. (Each file represents single-letter abbreviations)

The code divides the data of each file in a ratio of 80% to 20%, with 80% studying the neuron and the remaining 20% ​​being the neuron.

In order for the code to work, you must type in the main.java file in a row

```java
File folder = new File ("????");
```

Instead of ???? The path of the folder with the data files

See also link to [link](https://github.com/GeekCSA/Letter-arrays-database) database



