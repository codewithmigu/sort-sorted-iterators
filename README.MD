## Given

##### N sorted iterators of integers.

## Task

##### Implement iterator (Iterator<Integer>) that produces globally sorted sequence from the given N iterators. 
##### Task must be solved in the most efficient way possible(it's not allowed to copy entire content of iterators into some structure and then sort it).

## For example:
##### Iterator A1: 6, 8, 19, 21, 32, 66, 67, 77, 89
##### Iterator A2: 1, 3, 5,  24, 33, 45, 57, 59, 89
##### Iterator A3: 2, 4, 9,  18, 22, 44, 46, 89, 89

## Final globally sorted Iterator must produce:

- 1 (from A2)
- 2 (from A3)
- 3 (from A2)
- 4 (from A3)
- 5 (from A2)
- 6 (from A1)
- 8 (from A1)
- 9 (from A3)
- 18 (from A3)
- 19 (from A1)
- 21 (from A1)
- 22 (from A3)
...