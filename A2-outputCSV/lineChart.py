import pandas as pd
import matplotlib.pyplot as plt

# Load the data from a CSV file
data = pd.read_csv('/Users/lucyli/Documents/文档/unimelb/a2/src/dataOutput.csv')

# Remove any leading or trailing whitespace characters in the column names
data.columns = data.columns.str.strip()

# Creating the first line chart: ticks vs muscleMass
plt.figure(figsize=(12, 6))
plt.plot(data['ticks'], data['muscleMass'], label='Muscle Mass', color='blue')
plt.title('Ticks vs Muscle Mass')
plt.xlabel('Ticks')
plt.ylabel('Muscle Mass')
plt.grid(True)
plt.legend()
plt.show()

# Creating the second line chart: ticks vs averageAnabolic and averageCatabolic
plt.figure(figsize=(12, 6))
plt.plot(data['ticks'], data['averageAnabolic'], label='Average Anabolic', color='green')
plt.plot(data['ticks'], data['averageCatabolic'], label='Average Catabolic', color='red')
plt.title('Ticks vs Anabolic and Catabolic Averages')
plt.xlabel('Ticks')
plt.ylabel('Values')
plt.grid(True)
plt.legend()
plt.show()