# Bus Planner

## 📌 Project Description

This project aims to develop a program that generates optimized bus seating plans based on the closeness level between individuals. The goal is to seat people who are more familiar or comfortable with each other closer together, enhancing travel comfort and social satisfaction.

## 🚀 Features

* Input closeness levels between individuals
* Automatically generate optimal seating layout
* Customizable seating capacity and layout
* Visual representation of seating plan (optional)
* Exportable results (e.g., text, CSV)

## 📥 Input

* List of passengers
* Matrix or list of closeness scores (e.g., 0–10 scale, where 10 = very close)
* Bus layout (number of rows and columns)

## 📤 Output

* Optimized seating chart showing who sits where
* Total or average "closeness score" of the arrangement

## 🛠️ Technologies

* Python (core logic)
* NumPy / Pandas (data handling)
* Optional: Matplotlib / Tkinter (visualization)

## 🧠 Algorithm Idea

Uses combinatorial optimization techniques (e.g., greedy, simulated annealing, or genetic algorithms) to find the best arrangement that maximizes overall closeness.

## 📈 Example

```
Passengers: Alice, Bob, Carol, Dave  
Closeness:  
    Alice-Bob: 8  
    Alice-Carol: 3  
    Bob-Dave: 7  
...

Seating Plan:
Row 1: Alice - Bob  
Row 2: Carol - Dave  
```

## 📌 Future Work

* Add constraints (e.g., gender, age, preferences)
* Improve performance on large groups
* Web interface
