# Princeton Algorithms Course Assignments

This repository contains my solutions to assignments from Princeton University's Algorithms course. The assignments focus on implementing fundamental data structures and algorithms in Java, with an emphasis on geometric and puzzle-solving applications. Below is an overview of some of the included assignments and their purpose.

## Assignments Overview

### 1. KdTree and PointSET (KdTree.java, PointSET.java)
These assignments implement data structures for managing sets of 2D points in a unit square.

- **PointSET.java**: A brute-force implementation using a SET data structure to store points. It supports operations like inserting points, checking if a point exists, finding points within a rectangle, and finding the nearest neighbor to a given point.
- **KdTree.java**: A 2D tree implementation for efficient storage and querying of points. It partitions the space alternately by x- and y-coordinates, enabling faster range searches and nearest-neighbor queries compared to PointSET. The implementation includes methods for insertion, containment checks, range queries, nearest-neighbor searches, and visualization using StdDraw.

Both classes rely on the provided **Point2D.java** and **RectHV.java** from Princeton's standard library for point and rectangle operations.

### 2. 8-Puzzle Solver (Solver.java, Board.java, App.java)
This assignment implements a solution to the 8-puzzle problem (generalized to an n×n grid) using the A* search algorithm.

- **Board.java**: Represents an n×n sliding puzzle board. It includes methods to compute the Hamming and Manhattan distances (for heuristic evaluation), check if the board is the goal state, generate neighboring boards (valid moves), and create a twin board (for solvability detection).
- **Solver.java**: Implements the A* algorithm to find the shortest sequence of moves to solve the puzzle. It uses a priority queue to explore board states, prioritizing those with lower Manhattan distance plus moves. The solver also determines if the puzzle is solvable by running A* on both the initial board and its twin.
- **App.java**: A client program that reads a puzzle from a file, solves it using Solver, and outputs the solution (minimum moves and board sequence) or indicates if no solution exists.

## Dependencies
The assignments use Princeton's standard library (`algs4.jar`) for data structures (e.g., SET, MinPQ) and utilities (e.g., StdDraw, In, StdOut).