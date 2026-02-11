import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.svm import SVC
import pickle

# Load the dataset
heart_data = pd.read_csv('E:\/DataSEt.csv')

# Drop missing values
heart_data = heart_data.dropna()

# Selecting features and target
X = heart_data[['age', 'Weight', 'Height']]
Y = heart_data['Diet_plan']

# Splitting the data into train and test sets
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.2, stratify=Y, random_state=2)

# Initialize the Support Vector Classifier
svc = SVC()

# Train the classifier on the training data
svc.fit(X_train, Y_train)

# Make predictions on the test data
test_data_prediction = svc.predict(X_test)

# Calculate accuracy
error_score = accuracy_score(Y_test, test_data_prediction)
print("Accuracy:", error_score)

# New input with only three features: age, weight, height
new_input = [[55, 170, 50]]  # Example values, you should provide actual values

# Get prediction for new input
new_output = svc.predict(new_input)

# Summarize input and output
print("Input:", new_input, "Predicted Diet Plan:", new_output)

# Save the model to a file
with open('svc.pkl', 'wb') as file:
    pickle.dump(svc, file)
